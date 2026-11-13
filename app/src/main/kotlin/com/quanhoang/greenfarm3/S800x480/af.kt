package com.quanhoang.greenfarm3.S800x480

open class af {
    companion object {
        @JvmField val acf = 1 shl w.VP
        @JvmField val acg = acf shr 1
        @JvmField val ach = 1 shl w.VQ
        @JvmField val aci = (90 * ach) / 360
        @JvmField val acj = (ach * 180) / 360
        @JvmField val ack = (270 * ach) / 360
        @JvmField val acl = (ach * 360) / 360
        @JvmField var Rw = 3.1415927f
        @JvmField val acm = 1686629713 shr (29 - w.VP)
        @JvmField val acn = 1459366444 shr (29 - w.VP)
        @JvmField val Rz = af(180 shl w.VP, acm)
        @JvmField val RA = af(1 shl w.VQ, 360)
        private var scratchMatrix = Array(2) { IntArray(2) }
        @JvmStatic fun af(numerator: Int, denominator: Int): Int {
            GLLib.c(denominator != 0, "FixedPoint_Divide(): Division by Zero")
            return (((((numerator.toLong() shl w.VP) shl 1) / denominator.toLong()) + 1L).toInt()) shr 1
        }
    }
}
