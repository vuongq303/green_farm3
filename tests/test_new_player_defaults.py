"""Execute the server-default helper with original protected engine setters.

JSONObject is a map peer; this tests the real strict parser/hash and original
language branches, not Android HTTP parsing or player data.
"""
from pathlib import Path
import json
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath
from test_aa_tile_renderer import extract_method

PACKAGE = 'com.quanhoang.greenfarm3.S800x480'
JAVA = ROOT / 'app/src/main/java' / PACKAGE.replace('.', '/')
KOTLIN = ROOT / 'app/src/main/kotlin' / PACKAGE.replace('.', '/')
JSON_PEER = r'''package org.json;
public class JSONObject {
 private final java.util.LinkedHashMap<String,Object> values=new java.util.LinkedHashMap<>();
 public JSONObject put(String name,Object value){values.put(name,value);return this;}
 public Object get(String name){if(!values.containsKey(name))throw new IllegalArgumentException("missing");return values.get(name);}
 public java.util.Iterator<String>keys(){return values.keySet().iterator();}
 public Object remove(String name){return values.remove(name);}
 public String toString(){StringBuilder s=new StringBuilder("{");for(java.util.Map.Entry<String,Object>e:values.entrySet()){
  if(s.length()>1)s.append(',');s.append('"').append(e.getKey()).append("\":");Object v=e.getValue();
  if(v instanceof String)s.append('"').append(v).append('"');else s.append(v);
 }return s.append('}').toString();}
}'''
FIXTURE = r'''
package com.quanhoang.greenfarm3.S800x480;
import java.util.*;import java.nio.charset.StandardCharsets;import java.security.MessageDigest;
import org.json.JSONObject;
class BackendCloudController {static JSONObject initialization;public static JSONObject newPlayerInitialization(){return initialization;}}
class GameSaveStore {
 static Map<String,byte[]>durable=new HashMap<>(),capturing;static int reads;
 public static byte[]read(String key){reads++;return durable.get(key);}
 public static void write(String key,byte[]data,int offset,int size){if(capturing==null)throw new AssertionError("marker outside snapshot");capturing.put(key,Arrays.copyOfRange(data,offset,offset+size));}
}
class GloftGF2F {static GameRuntime acw=new GameRuntime();}
class bt {static int language;static void eL(int value){language=value;}}
public class FarmGameEngine {
 static bk aBc,aCP;static bi aBd,aCQ,aBb,aCO;
 static boolean aBI,aBH,hasUnsavedGameChanges,aYX,asM;
 static int aBe=99_999_999,aBg=3_000_000,aZB,objectives,aBa,aCN,aBl,aCS,aBn,aCT,aBp,aCU,aBs,aCV,aCY=9,aCZ=-1413816336;
 static int currentTutorialStepIndex=23,inventory=12,aYU=-1,aYV=-1,asL;
 static void aa(int a,int b,int c){objectives++;}static int previousEngineState(){return 1;}
 METHODS
 static void check(boolean b,String message){if(!b)throw new AssertionError(message);}
 static void reset(){BackendCloudController.initialization=null;NewPlayerDefaults.onWorldReady();GameSaveStore.durable.clear();GameSaveStore.capturing=null;
  aZB=0;hJ(1900);hK(50);hL(1);hM(7);ia(10);ib(10);ic(10);id(1);aBI=false;aBH=false;currentTutorialStepIndex=23;objectives=0;hasUnsavedGameChanges=false;}
 static JSONObject defaults(int coins,String language){return new JSONObject().put("schemaVersion",1).put("initialLevel",1).put("initialExperience",0)
  .put("initialCoins",coins).put("initialCash",50).put("initialTutorialStep",0).put("initialLanguage",language)
  .put("initialCapacityLimits",new JSONObject().put("plots",10).put("trees",10).put("animals",10).put("ponds",1)).put("initialWorldTemplate","original-4.4.2");}
 static JSONObject supplied(JSONObject values)throws Exception{byte[]digest=MessageDigest.getInstance("SHA-256").digest(values.toString().getBytes(StandardCharsets.UTF_8));StringBuilder hex=new StringBuilder();for(byte b:digest)hex.append(String.format("%02x",b&255));return new JSONObject().put("configHash",hex.toString()).put("defaults",values);}
 static Map<String,byte[]>capture(){GameSaveStore.capturing=new HashMap<>();GameSaveStore.capturing.put("save_gameplay",new byte[]{4});NewPlayerDefaults.captureInitializationMarker();Map<String,byte[]>out=GameSaveStore.capturing;GameSaveStore.capturing=null;return out;}
 static int originalLanguageBranch(){LANGUAGE_BRANCH;return aYU;}
 static void rejects(JSONObject values)throws Exception{BackendCloudController.initialization=supplied(values);try{NewPlayerDefaults.validateInitialization(BackendCloudController.initialization);throw new AssertionError("invalid config accepted");}catch(IllegalArgumentException expected){} }
 public static void main(String[]args)throws Exception{
  reset();int reads=GameSaveStore.reads;check(!NewPlayerDefaults.onWorldReady()&&NewPlayerDefaults.startupLanguageOverride()==null,"existing-profile fast path");
  check(FA()==1900&&FB()==50&&FC()==1&&FD()==7&&currentTutorialStepIndex==23&&GameSaveStore.reads==reads,"old progress/settings unchanged");
  JSONObject good=supplied(defaults(2500,"auto"));BackendCloudController.initialization=good;NewPlayerDefaults.validateInitialization(good);
  check(NewPlayerDefaults.startupLanguageOverride()==null,"auto leaves native locale selection");
  aBI=true;aBH=true;
  check(NewPlayerDefaults.onWorldReady(),"fresh server defaults require immediate snapshot");
  check(FA()==2500&&FB()==50&&FC()==1&&FD()==0&&currentTutorialStepIndex==0,"server values applied before original tutorial step advances");
  check(aBc==aCP&&aBd==aCQ&&aBa==aCN&&aBb==aCO&&!aBI&&!aBH,"original protected setters and shadow fields match");
  check(FI()==10&&FJ()==10&&FK()==10&&FL()==1&&aBl==aCS&&aBn==aCT&&aBp==aCU&&aBs==aCV,"configured limits preserve protected validation");
  check(inventory==12&&objectives==0&&hasUnsavedGameChanges,"no inventory/quest reward replay");
  Map<String,byte[]>failed=capture();check(GameSaveStore.durable.isEmpty(),"marker not persisted separately from first farm");
  hJ(2000);hM(7);currentTutorialStepIndex=23;check(NewPlayerDefaults.onWorldReady()&&FA()==2500&&FD()==0&&currentTutorialStepIndex==0,"failed first write can reinitialize the same still-unsaved profile");
  Map<String,byte[]>complete=capture();byte[]marker=complete.get("new_player_defaults_v1");
  check(marker.length==64&&new String(marker,StandardCharsets.US_ASCII).equals(good.get("configHash")),"marker contains only pinned server config hash");
  GameSaveStore.durable.putAll(complete);hS(-100);hT(-5);currentTutorialStepIndex=1;
  check(!NewPlayerDefaults.onWorldReady()&&FA()==2400&&FB()==45&&currentTutorialStepIndex==1,"committed initialization never refills or resets tutorial");
  reset();BackendCloudController.initialization=good;GameSaveStore.durable.put("save_gameplay",new byte[]{4});
  try{NewPlayerDefaults.onWorldReady();throw new AssertionError("old farm reseeded");}catch(IllegalStateException expected){}
  check(FA()==1900&&FD()==7,"existing gameplay cannot be reseeded by a stale startup flag");
  reset();BackendCloudController.initialization=good;aZB=1;
  try{NewPlayerDefaults.onWorldReady();throw new AssertionError("visitor seeded");}catch(IllegalStateException expected){}aZB=0;
  reset();rejects(defaults(-1,"auto"));rejects(defaults(2000,"auto").put("initialCash",3000001));rejects(defaults(2000,"auto").put("initialLevel",51));
  rejects(defaults(2000,"auto").put("initialExperience",13));rejects(defaults(2000,"auto").put("initialLevel",50).put("initialExperience",1));
  rejects(defaults(2000,"auto").put("initialCoins",2.0));rejects(defaults(2000,"auto").put("initialTutorialStep",1));rejects(defaults(2000,"zh"));
  rejects(defaults(2000,"auto").put("initialWorldTemplate","custom-unverified"));rejects(defaults(2000,"auto").put("extra",1));
  JSONObject bad=defaults(2000,"auto");((JSONObject)bad.get("initialCapacityLimits")).put("plots",0);rejects(bad);
  bad=defaults(2000,"auto");((JSONObject)bad.get("initialCapacityLimits")).put("extra",0);rejects(bad);
  bad=defaults(2000,"auto");bad.remove("initialCash");rejects(bad);
  JSONObject wrongHash=supplied(defaults(2000,"auto")).put("configHash","a".repeat(64));
  try{NewPlayerDefaults.validateInitialization(wrongHash);throw new AssertionError("wrong hash accepted");}catch(IllegalArgumentException expected){}
  check(FA()==1900&&FD()==7&&currentTutorialStepIndex==23,"invalid configurations do not touch existing state");
  reset();System.setProperty("microedition.locale","es");System.setProperty("microedition.locale.country","MX");System.setProperty("microedition.local.country","MX");
  BackendCloudController.initialization=supplied(defaults(2000,"auto"));aYU=-1;check(originalLanguageBranch()==20&&bt.language==20,"auto retains original ES regional branch");
  BackendCloudController.initialization=supplied(defaults(2000,"en"));aYU=-1;check(GloftGF2F.acw.kR()==0&&originalLanguageBranch()==0&&bt.language==0,"explicit server language survives ES override before text assets");
  System.setProperty("microedition.locale","pt");System.setProperty("microedition.locale.country","BR");System.setProperty("microedition.local.country","BR");
  BackendCloudController.initialization=supplied(defaults(2000,"auto"));aYU=-1;check(originalLanguageBranch()==5&&bt.language==5,"auto retains original PT-BR regional branch");
  BackendCloudController.initialization=supplied(defaults(2000,"en"));aYU=-1;check(originalLanguageBranch()==0&&bt.language==0,"explicit server language survives PT-BR override");
  BackendCloudController.initialization=null;aYU=16;check(originalLanguageBranch()==16&&bt.language==16,"saved language remains unchanged for an existing player");
  System.out.println("NEW_PLAYER_DEFAULTS_HOST_PASS original setters, strict schema/hash/ranges, atomic marker, retry, no old-profile changes, actual ES/PT language branches");
 }
}
'''


class NewPlayerDefaultsTest(unittest.TestCase):
    def test_public_jvm_oracle_keeps_verbatim_original_methods_and_vectors(self):
        oracle = ROOT / 'docs/backend-restoration-validation/new-player-oracle/OriginalGameplayOracle.java'
        text = oracle.read_text()
        sources = (
            (JAVA / 'FarmGameEngine.java', ('static int ih(int i)', 'static int ii(int i)', 'static long w(long j)', 'static long x(long j)')),
            (JAVA / 'GameRuntime.java', ('public static int d(byte[] bArr, int i, int i2)', 'public static int a(byte[] bArr, int i, long j)',
                                        'public static int l(byte[] bArr, int i)', 'public static long m(byte[] bArr, int i)')),
        )
        for source, signatures in sources:
            actual = source.read_text()
            for signature in signatures:
                self.assertEqual(extract_method(actual, signature), extract_method(text, signature))
        with tempfile.TemporaryDirectory(prefix='greenfarm-original-oracle-') as directory:
            built = subprocess.run([str(jdk_tool('javac')), '-d', directory, str(oracle)], text=True, capture_output=True, timeout=30)
            self.assertEqual(built.returncode, 0, built.stdout + built.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', directory, 'OriginalGameplayOracle'],
                                    input='', text=True, capture_output=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            proof = json.loads(oracle.with_name('original-gameplay-oracle.json').read_text())
            self.assertEqual(json.loads(result.stdout)['vectors'], proof['vectors'])

    def test_real_helper_original_protected_setters_atomic_marker_and_language_branches(self):
        engine = (JAVA / 'FarmGameEngine.java').read_text()
        runtime = (JAVA / 'GameRuntime.java').read_text()
        signatures = ('static int FA()', 'static int FB()', 'static int FC()', 'static int FD()',
                      'static void hJ(int i)', 'static void hK(int i)', 'static void hL(int i)', 'static void hM(int i)',
                      'static void hS(int i)', 'static void hT(int i)', 'static int FI()', 'static int FJ()', 'static int FK()', 'static int FL()',
                      'static void ia(int i)', 'static void ib(int i)', 'static void ic(int i)', 'static void id(int i)',
                      'static int ih(int i)', 'static int ii(int i)', 'static int a(bi biVar)', 'static long a(bk bkVar)',
                      'static bi ij(int i)', 'static bk v(long j)')
        methods = '\n'.join(extract_method(engine, signature) for signature in signatures)
        language = extract_method(engine[engine.index('cK("COMMON TEXT")'):], 'if (previousEngineState() == 1)')
        with tempfile.TemporaryDirectory(prefix='greenfarm-new-player-') as temporary:
            folder = Path(temporary)
            json = folder / 'org/json/JSONObject.java'
            json.parent.mkdir(parents=True)
            json.write_text(JSON_PEER)
            peer = folder / 'FarmGameEngine.java'
            peer.write_text(FIXTURE.replace('METHODS', methods).replace('LANGUAGE_BRANCH', language))
            runtime_peer = folder / 'GameRuntime.java'
            runtime_peer.write_text('package ' + PACKAGE + ';public class GameRuntime {' + extract_method(runtime, 'public int kR()') + '}')
            classes = folder / 'classes'
            compile_mixed([KOTLIN / f'{name}.kt' for name in ('NewPlayerDefaults', 'bk', 'bi', 'cf', 'ce', 'al', 'bo')],
                          [json, peer, runtime_peer], classes)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(classes), PACKAGE + '.FarmGameEngine'],
                                    text=True, capture_output=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('NEW_PLAYER_DEFAULTS_HOST_PASS', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
