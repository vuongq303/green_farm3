package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/** Saves rewards still owned by the original pickup animation, with the farm snapshot. */
final class PendingRewardState {
    static final String RECORD = "pending_rewards_v1";
    private static final int MAGIC = 0x47465257; // GFRW
    private static final int VERSION = 1;
    private static final int WORDS = 28;
    private static final int MAX_PENDING = 16384;
    private static boolean restored;

    private PendingRewardState() {}

    static void resetForLoad() { restored = false; }

    /** The caller includes this write in the same transaction as gameplay and farm records. */
    static void writeSnapshot() {
        requireWorldList();
        ArrayList<int[]> entries = new ArrayList<int[]>();
        for (ch effect = ch.als[3]; effect != null; effect = effect.aoi) {
            if (effect.Kx == 47 && effect.anX) {
                if (entries.size() == MAX_PENDING) throw invalid("Too many pending rewards", null);
                entries.add(capture(effect));
            }
        }
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream(12 + entries.size() * WORDS * 4);
            DataOutputStream output = new DataOutputStream(bytes);
            output.writeInt(MAGIC);
            output.writeInt(VERSION);
            output.writeInt(entries.size());
            for (int[] entry : entries) for (int value : entry) output.writeInt(value);
            output.flush();
            GLLib.a(RECORD, bytes.toByteArray(), ci.rv());
        } catch (IOException failure) {
            throw invalid("Cannot encode pending rewards", failure);
        }
    }

    /** Call once after the original farm, player and sprite tables have finished loading. */
    static void restoreSnapshot() {
        if (restored) return;
        requireWorldList();
        byte[] bytes = GLLib.B(RECORD, ci.rv());
        if (bytes == null) { restored = true; return; }
        int[][] entries = decode(bytes); // Reject the whole record before changing the live queue.
        if (entries.length != 0) {
            for (ch effect = ch.als[3]; effect != null; effect = effect.aoi) {
                if (effect.Kx == 47 && effect.anX) {
                    throw invalid("Pending rewards already exist before restore", null);
                }
            }
        }
        // The original kind-60 initializer selects a new random chest reward. Load only the
        // captured sprite here; restoring its bounce stage must not select or award it again.
        for (int[] entry : entries) {
            if (entry[12] == 60) {
                ci.s(1, entry[10] & ~32768, 1, 24);
                GLLib.bk("/11");
                try { ci.fP(1); } finally { GLLib.ke(); }
            }
        }
        ch[] created = new ch[entries.length];
        try {
            for (int index = 0; index < entries.length; index++) {
                int[] entry = entries[index];
                ch effect = ch.b(47, 3, new int[]{entry[1], entry[2], entry[3]});
                created[index] = effect;
                effect.oN(); // The factory sorts using its initial pose; restore recorded order below.
                apply(effect, entry);
            }
            for (int index = created.length - 1; index >= 0; index--) {
                ch effect = created[index];
                effect.aok = 3;
                effect.aoj = null;
                effect.aoi = ch.als[3];
                if (effect.aoi != null) effect.aoi.aoj = effect;
                ch.als[3] = effect;
            }
            restored = true;
        } catch (RuntimeException failure) {
            for (ch effect : created) if (effect != null) effect.oN();
            throw failure;
        }
    }

    private static int[][] decode(byte[] bytes) {
        if (bytes.length < 12 || bytes.length > 12 + MAX_PENDING * WORDS * 4) {
            throw invalid("Invalid pending reward record length", null);
        }
        try {
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes));
            if (input.readInt() != MAGIC || input.readInt() != VERSION) {
                throw invalid("Unsupported pending reward record", null);
            }
            int count = input.readInt();
            if (count < 0 || count > MAX_PENDING || bytes.length != 12 + count * WORDS * 4) {
                throw invalid("Invalid pending reward count", null);
            }
            int[][] entries = new int[count][WORDS];
            for (int[] entry : entries) {
                for (int index = 0; index < WORDS; index++) entry[index] = input.readInt();
                if ((entry[19] & ~3) != 0) throw invalid("Invalid pickup flags", null);
            }
            return entries;
        } catch (IOException failure) {
            throw invalid("Cannot decode pending rewards", failure);
        }
    }

    private static int[] capture(ch e) {
        return new int[]{e.HL, e.aol, e.aom, e.aon, e.aot, e.aou, e.aov, e.aoo,
                e.aoE, e.aoF, e.aoG, e.aoH, e.anG, e.anH, e.anI, e.anJ, e.anK,
                e.anL, e.anM, (e.anN ? 1 : 0) | (e.anO ? 2 : 0),
                e.anP, e.anQ, e.anR, e.anS, e.anT, e.anU, e.anV, e.anW};
    }

    private static void apply(ch e, int[] v) {
        e.HL = v[0]; e.aol = v[1]; e.aom = v[2]; e.aon = v[3];
        e.aot = v[4]; e.aou = v[5]; e.aov = v[6]; e.aoo = v[7];
        e.aoE = v[8]; e.aoF = v[9]; e.aoG = v[10]; e.aoH = v[11];
        e.anG = v[12]; e.anH = v[13]; e.anI = v[14]; e.anJ = v[15]; e.anK = v[16];
        e.anL = v[17]; e.anM = v[18]; e.anN = (v[19] & 1) != 0; e.anO = (v[19] & 2) != 0;
        e.anP = v[20]; e.anQ = v[21]; e.anR = v[22]; e.anS = v[23];
        e.anT = v[24]; e.anU = v[25]; e.anV = v[26]; e.anW = v[27];
        e.anX = true;
    }

    private static void requireWorldList() {
        if (ch.als == null || ch.als.length <= 3) throw invalid("Reward queue is not ready", null);
    }

    private static IllegalStateException invalid(String message, Throwable cause) {
        return new IllegalStateException(message, cause);
    }
}
