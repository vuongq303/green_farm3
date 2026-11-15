package com.quanhoang.greenfarm3.S800x480

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.quanhoang.greenfarm3.R

/** The Google mark already shipped with the game, drawn by its existing GL renderer. */
class SocialAccountArtwork private constructor() {
    companion object {
        @Volatile private var context: Context? = null
        private var googleMark: ay? = null
        private var markSize = 0
        private var unavailable = false

        @JvmStatic fun initialize(value: Context) {
            context = value.applicationContext
        }

        /** GL thread only. Handles from the old EGL context must never be deleted here. */
        @JvmStatic fun onSurfaceCreated() {
            googleMark = null
            markSize = 0
            unavailable = false
        }

        /** GL thread only. Coordinates and footprint belong to the original game button. */
        @JvmStatic fun drawGoogleIcon(x: Int, y: Int, size: Int): Boolean {
            if (size !in 16..64 || unavailable) return false
            return try {
                var image = googleMark
                if (image == null || markSize != size) {
                    val resources = context?.resources ?: return false
                    val options = BitmapFactory.Options().apply { inScaled = false }
                    val source = BitmapFactory.decodeResource(resources, R.drawable.googleg_standard_color_18, options)
                        ?: throw IllegalStateException("Bundled Google mark is unavailable")
                    var scaled: Bitmap? = null
                    val pixels: IntArray
                    try {
                        scaled = Bitmap.createScaledBitmap(source, size, size, true)
                        pixels = IntArray(size * size)
                        scaled.getPixels(pixels, 0, size, 0, 0, size, size)
                    } finally {
                        if (scaled !== source) scaled?.recycle()
                        source.recycle()
                    }
                    image?.nk()
                    image = ay.a(pixels, size, size, true)
                    googleMark = image
                    markSize = size
                }
                // LEFT|TOP (20), same image renderer/coordinate system as original UI sprites.
                GameRuntime.a(GameRuntime.Qj, image, x, y, 20, true)
                true
            } catch (_: RuntimeException) {
                unavailable = true
                Log.w("GreenFarmAccount", "google_mark_render_failed")
                false
            }
        }
    }
}
