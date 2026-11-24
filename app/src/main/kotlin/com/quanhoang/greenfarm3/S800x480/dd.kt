package com.quanhoang.greenfarm3.S800x480

open class dd {
    @JvmField var bcm = -1
    @JvmField var bcn: Array<String?>? = null
    constructor()
    constructor(eventCode: Int, arguments: Array<String?>?) {
        bcm = eventCode
        val source = arguments!!
        bcn = arrayOfNulls(source.size)
        for (index in source.indices) bcn!![index] = source[index]
    }
}
