package com.quanhoang.greenfarm3.S800x480

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.Collections
import java.util.IdentityHashMap

/** Retains transforms omitted by the original entity codec, including lazy building sprites. */
object EntityPoseState {
    const val RECORD = "entity_pose_v1"
    private const val MAGIC = 0x47465053
    private const val VERSION = 1
    private const val WORDS = 6
    private const val MAX_ENTITIES = 16384
    private var restored = false
    private var deferred = IdentityHashMap<FarmEntity, Int>()

    @JvmStatic fun resetForLoad() { restored = false; deferred.clear() }
    // Kotlin visibility follows the package-private Java actor; JVM name preserves its old hook.
    @JvmStatic @JvmName("spriteUpdated") internal fun spriteUpdated(entity: FarmEntity) {
        if (entity.aoO == null) return
        val transform = deferred.remove(entity)
        if (transform != null) entity.aoO.aaN = transform
    }

    @JvmStatic fun writeSnapshot() {
        requireWorldList()
        val entries = ArrayList<IntArray>()
        val occurrences = HashMap<String, Int>()
        val liveEntities = HashSet<FarmEntity>()
        var entity = FarmEntity.als[1]
        while (entity != null) {
            if (persistent(entity.Kx, entity.aoC)) {
                liveEntities.add(entity)
                val gridX = FarmGameEngine.bG(entity.aol, entity.aom).toShort().toInt()
                val gridY = FarmGameEngine.bH(entity.aol, entity.aom).toShort().toInt()
                val definition = definitionKey(entity.Kx, entity.aoC)
                val key = baseKey(entity.Kx, definition, gridX, gridY)
                val occurrence = nextOccurrence(occurrences, key)
                val pending = deferred[entity]
                if (entity.aoO != null || pending != null) {
                    if (entries.size == MAX_ENTITIES) throw invalid("Too many entity transforms", null)
                    val transform = if (entity.aoO == null) pending!! else entity.aoO.aaN
                    if (transform < 0 || transform > 7) throw invalid("Invalid live sprite transform", null)
                    entries.add(intArrayOf(entity.Kx, definition, gridX, gridY, occurrence, transform))
                }
            }
            entity = entity.aoi
        }
        deferred.keys.retainAll(liveEntities)
        try {
            val bytes = ByteArrayOutputStream(12 + entries.size * WORDS * 4)
            val output = DataOutputStream(bytes)
            output.writeInt(MAGIC)
            output.writeInt(VERSION)
            output.writeInt(entries.size)
            for (entry in entries) for (value in entry) output.writeInt(value)
            output.flush()
            GameRuntime.a(RECORD, bytes.toByteArray(), FarmGameEngine.rv())
        } catch (failure: IOException) { throw invalid("Cannot encode entity transforms", failure) }
    }

    @JvmStatic fun restoreSnapshot() {
        if (restored) return
        requireWorldList()
        val bytes = GameRuntime.B(RECORD, FarmGameEngine.rv())
        if (bytes == null) { restored = true; return }
        val entries = decode(bytes)
        val entities = HashMap<String, ArrayList<FarmEntity>>()
        var entity = FarmEntity.als[1]
        while (entity != null) {
            if (persistent(entity.Kx, entity.aoC)) {
                val key = baseKey(entity.Kx, definitionKey(entity.Kx, entity.aoC),
                    FarmGameEngine.bG(entity.aol, entity.aom).toShort().toInt(), FarmGameEngine.bH(entity.aol, entity.aom).toShort().toInt())
                var group = entities[key]
                if (group == null) { group = ArrayList(); entities[key] = group }
                group.add(entity)
            }
            entity = entity.aoi
        }
        for (group in entities.values) {
            // The decoder allocates in stream order; render insertion may reverse ties.
            Collections.sort(group, Comparator { first, second ->
                if (first.aoW == second.aoW) 0 else if (first.aoW < second.aoW) -1 else 1
            })
            for (index in 1 until group.size) {
                if (group[index - 1].aoW == group[index].aoW) throw invalid("Loaded entity occurrence order is ambiguous", null)
            }
        }
        val targets = arrayOfNulls<aa>(entries.size)
        val before = IntArray(entries.size)
        val pending = IdentityHashMap<FarmEntity, Int>()
        val assigned = HashSet<String>()
        for (index in entries.indices) {
            val entry = entries[index]
            val base = baseKey(entry[0], entry[1], entry[2], entry[3])
            val key = base + ":" + entry[4]
            val group = entities[base]
            val targetEntity = if (group == null || entry[4] >= group.size) null else group[entry[4]]
            if (!assigned.add(key) || targetEntity == null) throw invalid("Saved entity transform does not match the loaded farm", null)
            targets[index] = targetEntity.aoO
            if (targetEntity.aoO == null) pending[targetEntity] = entry[5]
            else before[index] = targetEntity.aoO.aaN
        }
        try {
            for (index in targets.indices) targets[index]?.aaN = entries[index][5]
            deferred = pending
            restored = true
        } catch (failure: RuntimeException) {
            for (index in targets.indices) targets[index]?.aaN = before[index]
            throw failure
        }
    }

    private fun decode(bytes: ByteArray): Array<IntArray> {
        if (bytes.size < 12 || bytes.size > 12 + MAX_ENTITIES * WORDS * 4) throw invalid("Invalid entity transform record length", null)
        try {
            val input = DataInputStream(ByteArrayInputStream(bytes))
            if (input.readInt() != MAGIC || input.readInt() != VERSION) throw invalid("Unsupported entity transform record", null)
            val count = input.readInt()
            if (count < 0 || count > MAX_ENTITIES || bytes.size != 12 + count * WORDS * 4) throw invalid("Invalid entity transform count", null)
            val entries = Array(count) { IntArray(WORDS) }
            for (entry in entries) {
                for (index in 0 until WORDS) entry[index] = input.readInt()
                if (!persistent(entry[0], entry[1]) || entry[1] != definitionKey(entry[0], entry[1]) ||
                    entry[2] != entry[2].toShort().toInt() || entry[3] != entry[3].toShort().toInt() ||
                    entry[4] < 0 || entry[5] < 0 || entry[5] > 7) throw invalid("Invalid entity transform entry", null)
            }
            return entries
        } catch (failure: IOException) { throw invalid("Cannot decode entity transforms", failure) }
    }

    /** Exactly the entity types emitted by ci.p(byte[], int). */
    private fun persistent(type: Int, definition: Int): Boolean = when (type) {
        13, 20, 22, 42, 48, 49, 52, 69, 54 -> true
        50 -> definition != 22 && definition != 23
        else -> false
    }
    private fun nextOccurrence(occurrences: HashMap<String, Int>, key: String): Int {
        val occurrence = occurrences[key] ?: 0
        occurrences[key] = occurrence + 1
        return occurrence
    }
    private fun definitionKey(type: Int, definition: Int): Int = if (type == 13) -1 else definition.toByte().toInt()
    private fun baseKey(type: Int, definition: Int, gridX: Int, gridY: Int): String = "$type:$definition:$gridX:$gridY"
    private fun requireWorldList() {
        if (FarmEntity.als == null || FarmEntity.als.size <= 1) throw invalid("Entity list is not ready", null)
    }
    private fun invalid(message: String, cause: Throwable?): IllegalStateException = IllegalStateException(message, cause)
}
