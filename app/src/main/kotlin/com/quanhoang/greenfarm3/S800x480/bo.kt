package com.quanhoang.greenfarm3.S800x480

open class bo {
    companion object {
        @JvmField var ajP = 2016
        @JvmStatic fun aq(value: Int, multiplier: Int): Int {
            val lowValue = value % 10000
            val lowMultiplier = multiplier % 10000
            return ((((((value / 10000) * lowMultiplier) + (0 * lowValue)) % 10000) * 10000) + (lowValue * lowMultiplier)) % 100000000
        }
        @JvmStatic fun next(): Int {
            ajP = (aq(ajP, 3141581) + 1) % 100000000
            return ajP
        }
    }
}
