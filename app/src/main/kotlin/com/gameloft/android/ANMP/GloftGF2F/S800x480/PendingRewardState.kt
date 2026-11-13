package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

/** Rewards still owned by original pickup animations, committed with their farm snapshot. */
object PendingRewardState {
    const val RECORD = "pending_rewards_v1"
    private const val MAGIC = 0x47465257
    private const val VERSION = 1
    private const val WORDS = 28
    private const val MAX_PENDING = 16384
    private var restored = false

    @JvmStatic fun resetForLoad() { restored = false }

    @JvmStatic fun writeSnapshot() {
        requireWorldList()
        val entries = ArrayList<IntArray>()
        var effect = ch.als[3]
        while (effect != null) {
            if (effect.Kx == 47 && effect.anX) {
                if (entries.size == MAX_PENDING) throw invalid("Too many pending rewards", null)
                entries.add(capture(effect))
            }
            effect = effect.aoi
        }
        try {
            val bytes = ByteArrayOutputStream(12 + entries.size * WORDS * 4)
            val output = DataOutputStream(bytes)
            output.writeInt(MAGIC)
            output.writeInt(VERSION)
            output.writeInt(entries.size)
            for (entry in entries) for (value in entry) output.writeInt(value)
            output.flush()
            GLLib.a(RECORD, bytes.toByteArray(), ci.rv())
        } catch (failure: IOException) { throw invalid("Cannot encode pending rewards", failure) }
    }

    @JvmStatic fun restoreSnapshot() {
        if (restored) return
        requireWorldList()
        val bytes = GLLib.B(RECORD, ci.rv())
        if (bytes == null) { restored = true; return }
        val entries = decode(bytes)
        if (entries.isNotEmpty()) {
            var effect = ch.als[3]
            while (effect != null) {
                if (effect.Kx == 47 && effect.anX) throw invalid("Pending rewards already exist before restore", null)
                effect = effect.aoi
            }
        }
        // Kind60's initializer chooses a random chest reward; only reload the captured sprite.
        for (entry in entries) {
            if (entry[12] == 60) {
                ci.s(1, entry[10] and 32768.inv(), 1, 24)
                GLLib.bk("/11")
                try { ci.fP(1) } finally { GLLib.ke() }
            }
        }
        val created = arrayOfNulls<ch>(entries.size)
        try {
            for (index in entries.indices) {
                val entry = entries[index]
                val effect = ch.b(47, 3, intArrayOf(entry[1], entry[2], entry[3]))
                created[index] = effect
                effect.oN()
                apply(effect, entry)
            }
            for (index in created.size - 1 downTo 0) {
                val effect = created[index]!!
                effect.aok = 3
                effect.aoj = null
                effect.aoi = ch.als[3]
                effect.aoi?.aoj = effect
                ch.als[3] = effect
            }
            restored = true
        } catch (failure: RuntimeException) {
            for (effect in created) effect?.oN()
            throw failure
        }
    }

    private fun decode(bytes: ByteArray): Array<IntArray> {
        if (bytes.size < 12 || bytes.size > 12 + MAX_PENDING * WORDS * 4) throw invalid("Invalid pending reward record length", null)
        try {
            val input = DataInputStream(ByteArrayInputStream(bytes))
            if (input.readInt() != MAGIC || input.readInt() != VERSION) throw invalid("Unsupported pending reward record", null)
            val count = input.readInt()
            if (count < 0 || count > MAX_PENDING || bytes.size != 12 + count * WORDS * 4) throw invalid("Invalid pending reward count", null)
            val entries = Array(count) { IntArray(WORDS) }
            for (entry in entries) {
                for (index in 0 until WORDS) entry[index] = input.readInt()
                if ((entry[19] and 3.inv()) != 0) throw invalid("Invalid pickup flags", null)
            }
            return entries
        } catch (failure: IOException) { throw invalid("Cannot decode pending rewards", failure) }
    }

    private fun capture(effect: ch): IntArray = intArrayOf(
        effect.HL, effect.aol, effect.aom, effect.aon, effect.aot, effect.aou, effect.aov, effect.aoo,
        effect.aoE, effect.aoF, effect.aoG, effect.aoH, effect.anG, effect.anH, effect.anI, effect.anJ,
        effect.anK, effect.anL, effect.anM, (if (effect.anN) 1 else 0) or (if (effect.anO) 2 else 0),
        effect.anP, effect.anQ, effect.anR, effect.anS, effect.anT, effect.anU, effect.anV, effect.anW)

    private fun apply(effect: ch, words: IntArray) {
        effect.HL = words[0]; effect.aol = words[1]; effect.aom = words[2]; effect.aon = words[3]
        effect.aot = words[4]; effect.aou = words[5]; effect.aov = words[6]; effect.aoo = words[7]
        effect.aoE = words[8]; effect.aoF = words[9]; effect.aoG = words[10]; effect.aoH = words[11]
        effect.anG = words[12]; effect.anH = words[13]; effect.anI = words[14]; effect.anJ = words[15]
        effect.anK = words[16]; effect.anL = words[17]; effect.anM = words[18]
        effect.anN = (words[19] and 1) != 0; effect.anO = (words[19] and 2) != 0
        effect.anP = words[20]; effect.anQ = words[21]; effect.anR = words[22]; effect.anS = words[23]
        effect.anT = words[24]; effect.anU = words[25]; effect.anV = words[26]; effect.anW = words[27]
        effect.anX = true
    }
    private fun requireWorldList() {
        if (ch.als == null || ch.als.size <= 3) throw invalid("Reward queue is not ready", null)
    }
    private fun invalid(message: String, cause: Throwable?): IllegalStateException = IllegalStateException(message, cause)
}
