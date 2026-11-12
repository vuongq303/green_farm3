package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/** Retains sprite transforms omitted by the original farm entity codec. */
final class EntityPoseState {
    static final String RECORD = "entity_pose_v1";
    private static final int MAGIC = 0x47465053; // GFPS
    private static final int VERSION = 1;
    private static final int WORDS = 6;
    private static final int MAX_ENTITIES = 16384;
    private static boolean restored;

    private EntityPoseState() {}

    static void resetForLoad() { restored = false; }

    /** Include only with a whole own-farm snapshot, never a partial visiting-farm save. */
    static void writeSnapshot() {
        requireWorldList();
        ArrayList<int[]> entries = new ArrayList<int[]>();
        HashMap<String, Integer> occurrences = new HashMap<String, Integer>();
        for (ch entity = ch.als[1]; entity != null; entity = entity.aoi) {
            if (!persistent(entity.Kx, entity.aoC)) continue;
            int x = (short) ci.bG(entity.aol, entity.aom);
            int y = (short) ci.bH(entity.aol, entity.aom);
            int definition = definitionKey(entity.Kx, entity.aoC);
            String key = baseKey(entity.Kx, definition, x, y);
            int occurrence = nextOccurrence(occurrences, key);
            if (entity.aoO == null) continue;
            if (entries.size() == MAX_ENTITIES) throw invalid("Too many entity transforms", null);
            int transform = entity.aoO.aaN; // lL()/dS() address aaR, the elapsed animation time.
            if (transform < 0 || transform > 7) throw invalid("Invalid live sprite transform", null);
            entries.add(new int[]{entity.Kx, definition, x, y, occurrence, transform});
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
            throw invalid("Cannot encode entity transforms", failure);
        }
    }

    /** Match the original farm decoder's grid keys before changing any sprite. */
    static void restoreSnapshot() {
        if (restored) return;
        requireWorldList();
        byte[] bytes = GLLib.B(RECORD, ci.rv());
        if (bytes == null) { restored = true; return; }
        int[][] entries = decode(bytes);
        HashMap<String, ArrayList<ch>> entities = new HashMap<String, ArrayList<ch>>();
        for (ch entity = ch.als[1]; entity != null; entity = entity.aoi) {
            if (!persistent(entity.Kx, entity.aoC)) continue;
            String key = baseKey(entity.Kx, definitionKey(entity.Kx, entity.aoC),
                    (short) ci.bG(entity.aol, entity.aom), (short) ci.bH(entity.aol, entity.aom));
            ArrayList<ch> group = entities.get(key);
            if (group == null) { group = new ArrayList<ch>(); entities.put(key, group); }
            group.add(entity);
        }
        for (ArrayList<ch> group : entities.values()) {
            // ci.p emits traversal order; the decoder allocates in that same stream order.
            // ch.a may reverse tied entities when inserting them into the render list. Its
            // original allocation counter recovers occurrence order, without persisting IDs.
            Collections.sort(group, new Comparator<ch>() {
                public int compare(ch first, ch second) {
                    return first.aoW == second.aoW ? 0 : (first.aoW < second.aoW ? -1 : 1);
                }
            });
            for (int index = 1; index < group.size(); index++) {
                if (group.get(index - 1).aoW == group.get(index).aoW) {
                    throw invalid("Loaded entity occurrence order is ambiguous", null);
                }
            }
        }
        aa[] targets = new aa[entries.length];
        int[] before = new int[entries.length];
        HashSet<String> assigned = new HashSet<String>();
        for (int index = 0; index < entries.length; index++) {
            int[] entry = entries[index];
            String base = baseKey(entry[0], entry[1], entry[2], entry[3]);
            String key = base + ":" + entry[4];
            ArrayList<ch> group = entities.get(base);
            ch entity = group == null || entry[4] >= group.size() ? null : group.get(entry[4]);
            if (!assigned.add(key) || entity == null || entity.aoO == null) {
                throw invalid("Saved entity transform does not match the loaded farm", null);
            }
            targets[index] = entity.aoO;
            before[index] = entity.aoO.aaN;
        }
        try {
            for (int index = 0; index < targets.length; index++) targets[index].aaN = entries[index][5];
            restored = true;
        } catch (RuntimeException failure) {
            for (int index = 0; index < targets.length; index++) targets[index].aaN = before[index];
            throw failure;
        }
    }

    private static int[][] decode(byte[] bytes) {
        if (bytes.length < 12 || bytes.length > 12 + MAX_ENTITIES * WORDS * 4) {
            throw invalid("Invalid entity transform record length", null);
        }
        try {
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes));
            if (input.readInt() != MAGIC || input.readInt() != VERSION) {
                throw invalid("Unsupported entity transform record", null);
            }
            int count = input.readInt();
            if (count < 0 || count > MAX_ENTITIES || bytes.length != 12 + count * WORDS * 4) {
                throw invalid("Invalid entity transform count", null);
            }
            int[][] entries = new int[count][WORDS];
            for (int[] entry : entries) {
                for (int index = 0; index < WORDS; index++) entry[index] = input.readInt();
                if (!persistent(entry[0], entry[1]) || entry[1] != definitionKey(entry[0], entry[1])
                        || entry[2] != (short) entry[2]
                        || entry[3] != (short) entry[3] || entry[4] < 0
                        || entry[5] < 0 || entry[5] > 7) {
                    throw invalid("Invalid entity transform entry", null);
                }
            }
            return entries;
        } catch (IOException failure) {
            throw invalid("Cannot decode entity transforms", failure);
        }
    }

    /** Mirrors exactly the entity types emitted by ci.p(byte[], int). */
    private static boolean persistent(int type, int definition) {
        switch (type) {
            case 13: case 20: case 22: case 42: case 48: case 49: case 52: case 69: case 54:
                return true;
            case 50:
                return definition != 22 && definition != 23;
            default:
                return false;
        }
    }

    private static int nextOccurrence(HashMap<String, Integer> occurrences, String key) {
        Integer prior = occurrences.get(key);
        int occurrence = prior == null ? 0 : prior.intValue();
        occurrences.put(key, Integer.valueOf(occurrence + 1));
        return occurrence;
    }

    private static int definitionKey(int type, int definition) {
        // Type 13 omits aoC; every other entity codec writes a signed byte. In
        // particular the original empty-plot sentinel 65535 reloads as -1.
        return type == 13 ? -1 : (byte) definition;
    }

    private static String baseKey(int type, int definition, int x, int y) {
        return type + ":" + definition + ":" + x + ":" + y;
    }

    private static void requireWorldList() {
        if (ch.als == null || ch.als.length <= 1) throw invalid("Entity list is not ready", null);
    }

    private static IllegalStateException invalid(String message, Throwable cause) {
        return new IllegalStateException(message, cause);
    }
}
