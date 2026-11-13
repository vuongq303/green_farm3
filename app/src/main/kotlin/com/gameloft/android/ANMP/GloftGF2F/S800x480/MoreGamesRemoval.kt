package com.gameloft.android.ANMP.GloftGF2F.S800x480

/** Packed GUI identities for the retired More Games feature in every text pack. */
class MoreGamesRemoval private constructor() {
    companion object {
        @JvmStatic fun isWidget(layerId: Int, itemId: Int): Boolean =
            (layerId == 1 && (itemId == 34 || itemId == 38)) ||
                (layerId == 27 && (itemId == 22 || itemId == 23))

        /** Clear the original packed visibility/input flags when rebuilding a row. */
        @JvmStatic fun removeWidget(layerId: Int, itemId: Int, row: ShortArray?) {
            if (!isWidget(layerId, itemId)) return
            val packedFlags = row!!
            when (packedFlags[0].toInt()) {
                205 -> {
                    packedFlags[10] = 0
                    packedFlags[18] = 0
                }
                204 -> packedFlags[15] = 0
                202 -> packedFlags[11] = 0
            }
        }
    }
}
