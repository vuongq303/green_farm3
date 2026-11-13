package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.util.Vector

open class da {
    @JvmField var bbZ = 0
    @JvmField var bcb = Vector<Any?>()
    @JvmField var bcd = Vector<Any?>()
    @JvmField var bce = Vector<Any?>()
    open fun MS(): Int = bbZ
    open fun a(entry: dc?, firstValue: Int, secondValue: Int) {
        bbZ++
        bcb.addElement(entry)
        bcd.addElement(Integer(firstValue))
        bce.addElement(Integer(secondValue))
    }
}
