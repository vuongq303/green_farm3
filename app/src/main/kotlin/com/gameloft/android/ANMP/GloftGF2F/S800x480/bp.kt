package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class bp {
    @JvmField var data: ByteArray? = null
    constructor()
    constructor(source: ByteArray?) {
        val original = source!!
        data = ByteArray(original.size)
        System.arraycopy(original, 0, data, 0, original.size)
    }
}
