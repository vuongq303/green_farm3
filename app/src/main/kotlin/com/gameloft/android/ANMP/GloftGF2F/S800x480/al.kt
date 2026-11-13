package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class al {
    companion object {
        @JvmStatic fun q(value: Long): Long {
            val firstMix = ((value shr 16) xor value) * 73244475L
            val secondMix = (firstMix xor (firstMix shr 16)) * 73244475L
            return secondMix xor (secondMix shr 16)
        }
    }
}
