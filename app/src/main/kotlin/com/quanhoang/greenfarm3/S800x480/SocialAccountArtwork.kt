package com.quanhoang.greenfarm3.S800x480

/** Account security mark from the original GUI atlas, owned by the game renderer. */
class SocialAccountArtwork private constructor() {
    companion object {
        /** GL thread only. Frame 137 is the original 21 x 29 padlock, centered at (-10, -14). */
        @JvmStatic fun drawAccountIcon(x: Int, y: Int, size: Int): Boolean {
            if (size !in 32..64 || x < 0 || y < 0 ||
                x > Int.MAX_VALUE - size || y > Int.MAX_VALUE - size) return false
            val sprites = FarmGameEngine.atf ?: return false
            if (sprites.size <= 20) return false
            val atlas = sprites[20] ?: return false
            if (atlas.hv() <= 137) return false
            // Preserve native pixels and the button's existing icon footprint.
            atlas.a(GameRuntime.Qj, 137, (x + size / 2).toFloat(), (y + size / 2).toFloat(), 0)
            return true
        }
    }
}
