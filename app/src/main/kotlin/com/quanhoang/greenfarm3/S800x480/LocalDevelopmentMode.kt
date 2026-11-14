package com.quanhoang.greenfarm3.S800x480

/** Compile-time opt-in for isolated local play. It never represents authentication. */
object LocalDevelopmentMode {
    @JvmStatic fun isEnabled(): Boolean = LocalDevelopmentConfig.ENABLED
}
