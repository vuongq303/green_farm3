package com.quanhoang.greenfarm3.S800x480

/** Compile-time opt-in for isolated local play. It never represents authentication. */
object LocalDevelopmentMode {
    @JvmStatic fun isEnabled(): Boolean = LocalDevelopmentConfig.ENABLED
    /** Online accounts replace the old anonymous SDK, without enabling local bonuses. */
    @JvmStatic fun skipLegacyBackend(): Boolean =
        LocalDevelopmentConfig.ENABLED || LocalDevelopmentConfig.ONLINE_ACCOUNTS
}
