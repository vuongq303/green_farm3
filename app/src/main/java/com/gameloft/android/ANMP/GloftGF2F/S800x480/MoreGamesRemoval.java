package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/** Packed GUI identities for the retired More Games feature, shared by all languages. */
final class MoreGamesRemoval {
    private MoreGamesRemoval() {
    }

    static boolean isWidget(int layer, int item) {
        return (layer == 1 && (item == 34 || item == 38))
                || (layer == 27 && (item == 22 || item == 23));
    }

    /** Remove the packed visibility and input flags when a GUI row is reconstructed. */
    static void removeWidget(int layer, int item, short[] row) {
        if (!isWidget(layer, item)) {
            return;
        }
        if (row[0] == 205) {
            row[10] = 0;
            row[18] = 0;
        } else if (row[0] == 204) {
            row[15] = 0;
        } else if (row[0] == 202) {
            row[11] = 0;
        }
    }
}
