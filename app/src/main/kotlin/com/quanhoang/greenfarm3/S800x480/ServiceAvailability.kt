package com.quanhoang.greenfarm3.S800x480

/** Temporary retirement of provider features; backend guest sessions stay enabled. */
object ServiceAvailability {
    @JvmStatic fun facebookLoginEnabled(): Boolean = false
    @JvmStatic fun advertisementsEnabled(): Boolean = false

    @JvmStatic fun isAdPlacement(placement: String?): Boolean =
        placement == "enter_section_iap_shop" || placement == "enter_section_video_bubble" ||
            placement == "enter_section_video_lucky_wheel" || placement == "enter_section_video_daily_stamp"

    /** State 47 is the Facebook Connect screen, independently of Janus auth. */
    @JvmStatic fun startupDestination(requestedState: Int, farmIndex: Int): Int =
        if (!facebookLoginEnabled() && requestedState == 47) {
            if (farmIndex == 0) 18 else 37
        } else requestedState

    /** These four screens only launch or explain the retired rewarded ads. */
    @JvmStatic fun isRetiredAdScreen(state: Int): Boolean =
        !advertisementsEnabled() && (state == 61 || state == 62 || state == 63 || state == 65)

    @JvmStatic fun isUnavailableWidget(layerId: Int, itemId: Int): Boolean =
        (!facebookLoginEnabled() && (
            layerId == 29 || // Entire Connect screen: startupDestination removes navigation to it.
                (layerId == 15 && (itemId == 32 || itemId == 33 || itemId == 40)) ||
                (layerId == 9 && (itemId == 4 || itemId == 8)))) ||
            (!advertisementsEnabled() && (
                (layerId == 1 && itemId == 81) || // Daily rewarded-video icon on the farm.
                    (layerId == 8 && (itemId == 318 || itemId == 319)))) // Shop watch-video button and label.

    /** Packed flags must be cleared again when GUI rows are reconstructed. */
    @JvmStatic fun removeWidget(layerId: Int, itemId: Int, row: ShortArray?) {
        if (!isUnavailableWidget(layerId, itemId)) return
        val flags = row!!
        when (flags[0].toInt()) {
            205 -> { flags[10] = 0; flags[18] = 0 }
            204 -> flags[15] = 0
            202 -> flags[11] = 0
        }
    }
}
