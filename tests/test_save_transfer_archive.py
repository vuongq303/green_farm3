"""Exercise the production Kotlin archive reader/writer, without Android or player data."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

PROBE = r'''
import com.quanhoang.greenfarm3.migration.SaveArchive
import java.io.*
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun fixture(): SaveArchive.Snapshot {
    val binding="12345678-abcd-1234-abcd-123456789012".toByteArray(Charsets.UTF_8)
    val gameplay=ByteArray(96)
    gameplay[0]=4
    ByteBuffer.wrap(gameplay,1,4).order(ByteOrder.LITTLE_ENDIAN).putInt(binding.size)
    binding.copyInto(gameplay,5)
    fun words(magic:Int, count:Int, size:Int):ByteArray {
        val buffer=ByteBuffer.allocate(12+count*size*4).order(ByteOrder.BIG_ENDIAN)
        buffer.putInt(magic).putInt(1).putInt(count)
        repeat(count*size){buffer.putInt(0)}
        return buffer.array()
    }
    return SaveArchive.Snapshot("com.gameloft.android.ANMP.GloftGF2F",9,8,1,1,1000,2000,listOf(
        SaveArchive.Record("RMSHeader",ByteArray(35).also{it[4]=16},2),
        SaveArchive.Record("save_gameplay",gameplay,8),
        SaveArchive.Record("save_farm0",ByteArray(128){(it*7).toByte()},7),
        SaveArchive.Record("pending_rewards_v1",words(0x47465257,1,28),8),
        SaveArchive.Record("entity_pose_v1",words(0x47465053,1,6),7),
        SaveArchive.Record("RMS_TimeClock",ByteArray(24){it.toByte()},7),
        SaveArchive.Record("đồng_hồ",byteArrayOf(0,-1,12),1),
        SaveArchive.Record("removed-item",null,9)))
}
fun encoded(snapshot:SaveArchive.Snapshot=fixture()):ByteArray = ByteArrayOutputStream().also{SaveArchive.write(snapshot,it)}.toByteArray()
fun rejected(block:()->Unit){try{block();error("accepted invalid archive")}catch(expected:IOException){}}
fun check(condition:Boolean,message:String){if(!condition)throw AssertionError(message)}
fun main(args:Array<String>){
 when(args.single()) {
  "roundtrip" -> {
    val original=fixture();val bytes=encoded(original)
    val restored=SaveArchive.read(ByteArrayInputStream(bytes))
    check(restored.sourcePackage==original.sourcePackage && restored.generation==9L && restored.completeSnapshotGeneration==8L,"metadata")
    check(restored.snapshotFormat==1 && restored.poseSnapshot==1 && restored.createdAtMillis==1000L && restored.updatedAtMillis==2000L,"format/times")
    check(restored.records.size==original.records.size,"record count")
    for(before in original.records){val after=restored.records.single{it.name==before.name};check(before.payload.contentEquals(after.payload),"payload" );check(before.generation==after.generation,"generation")}
    check(SaveArchive.gameplayBinding(restored.records.single{it.name=="save_gameplay"}.payload!!)=="12345678-abcd-1234-abcd-123456789012","binding")
    check(encoded(restored).contentEquals(bytes),"deterministic re-encoding")
  }
  "corruption" -> {
    val bytes=encoded()
    for(index in bytes.indices){val modified=bytes.copyOf();modified[index]=(modified[index].toInt() xor 1).toByte();rejected{SaveArchive.read(ByteArrayInputStream(modified))}}
  }
  "truncation" -> {
    val bytes=encoded()
    for(length in bytes.indices)rejected{SaveArchive.read(ByteArrayInputStream(bytes.copyOf(length)))}
    rejected{SaveArchive.read(ByteArrayInputStream(bytes+byteArrayOf(1)))}
  }
  "snapshot_validation" -> {
    val valid=fixture()
    rejected{SaveArchive.validate(valid.copy(sourcePackage="other.application"))}
    rejected{SaveArchive.validate(valid.copy(generation=0))}
    rejected{SaveArchive.validate(valid.copy(completeSnapshotGeneration=10))}
    rejected{SaveArchive.validate(valid.copy(snapshotFormat=2))}
    rejected{SaveArchive.validate(valid.copy(poseSnapshot=-1))}
    rejected{SaveArchive.validate(valid.copy(records=valid.records+valid.records[0]))}
    rejected{SaveArchive.validate(valid.copy(records=valid.records.filter{it.name!="save_farm0"}))}
    rejected{SaveArchive.validate(valid.copy(records=valid.records.map{if(it.name=="RMSHeader")it.copy(payload=ByteArray(34)) else it}))}
    rejected{SaveArchive.validate(valid.copy(records=valid.records.map{if(it.name=="save_gameplay")it.copy(generation=10) else it}))}
    rejected{SaveArchive.validate(valid.copy(records=listOf(SaveArchive.Record("bad/path",ByteArray(1),1))))}
    val brokenReward=valid.records.map{if(it.name=="pending_rewards_v1")it.copy(payload=it.payload!!.copyOf().also{b->ByteBuffer.wrap(b,12+19*4,4).putInt(4)})else it}
    rejected{SaveArchive.validate(valid.copy(records=brokenReward))}
    val brokenPose=valid.records.map{if(it.name=="entity_pose_v1")it.copy(payload=it.payload!!.copyOf().also{b->ByteBuffer.wrap(b,12+5*4,4).putInt(8)})else it}
    rejected{SaveArchive.validate(valid.copy(records=brokenPose))}
  }
  "binding_bounds" -> {
    val data=fixture().records.single{it.name=="save_gameplay"}.payload!!
    rejected{SaveArchive.gameplayBinding(data.copyOf(5))}
    rejected{SaveArchive.gameplayBinding(data.copyOf().also{it[0]=3})}
    rejected{SaveArchive.gameplayBinding(data.copyOf().also{ByteBuffer.wrap(it,1,4).order(ByteOrder.LITTLE_ENDIAN).putInt(-1)})}
    rejected{SaveArchive.gameplayBinding(data.copyOf().also{ByteBuffer.wrap(it,1,4).order(ByteOrder.LITTLE_ENDIAN).putInt(Int.MAX_VALUE)})}
    rejected{SaveArchive.gameplayBinding(data.copyOf().also{it[5]=0})}
    rejected{SaveArchive.gameplayBinding(data.copyOf().also{it[5]=0xFF.toByte()})}
  }
  "bounded_header" -> {
    fun hostile(count:Int,size:Int):ByteArray=ByteArrayOutputStream().also{out->DataOutputStream(out).use{
      it.write("GF3SAVE1".toByteArray());it.writeInt(1);it.writeInt(4)
      fun string(value:String){val b=value.toByteArray();it.writeInt(b.size);it.write(b)}
      string("com.quanhoang.greenfarm3");it.writeLong(1);it.writeLong(1);it.writeInt(1);it.writeInt(1);it.writeLong(0);it.writeLong(0);it.writeInt(count)
      if(count==1){string("RMSHeader");it.writeLong(1);it.writeInt(size)}
    }}.toByteArray()
    for(count in listOf(-1,0,2049,Int.MAX_VALUE))rejected{SaveArchive.read(ByteArrayInputStream(hostile(count,0)))}
    for(size in listOf(-2,SaveArchive.MAX_RECORD_BYTES+1,Int.MAX_VALUE))rejected{SaveArchive.read(ByteArrayInputStream(hostile(1,size)))}
  }
  else -> error("unknown probe")
 }
 println("PASS "+args.single())
}
'''

class SaveTransferArchiveTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.work = tempfile.TemporaryDirectory(prefix='greenfarm-save-transfer-')
        cls.directory = Path(cls.work.name)
        probe = cls.directory / 'SaveTransferArchiveProbe.kt'
        probe.write_text(PROBE, encoding='utf-8')
        compile_mixed([ROOT/'app/src/main/kotlin/com/quanhoang/greenfarm3/migration/SaveArchive.kt',probe], [], cls.directory/'classes')

    @classmethod
    def tearDownClass(cls):
        cls.work.cleanup()

    def run_case(self, name):
        result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(self.directory/'classes'),
                                 'SaveTransferArchiveProbeKt', name], capture_output=True, text=True, timeout=30)
        self.assertEqual(result.returncode,0,result.stdout+result.stderr)
        self.assertIn('PASS '+name,result.stdout)

    def test_byte_exact_roundtrip_tombstones_times_and_language(self): self.run_case('roundtrip')
    def test_every_single_bit_mutation_is_rejected(self): self.run_case('corruption')
    def test_every_truncation_and_trailing_bytes_are_rejected(self): self.run_case('truncation')
    def test_required_records_versions_generations_and_codec_flags(self): self.run_case('snapshot_validation')
    def test_binding_uses_verified_format4_layout_and_utf8(self): self.run_case('binding_bounds')
    def test_count_and_size_are_bounded_before_allocation(self): self.run_case('bounded_header')

if __name__=='__main__': unittest.main()
