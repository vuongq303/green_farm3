package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.util.Vector

open class cz {
    @JvmField var bbZ = 0
    @JvmField var bca = 0
    @JvmField var bcb = Vector<Any?>()
    @JvmField var bcc = Vector<Any?>()
    open fun MS(): Int = bbZ
    open fun a(entry: dc?, value: Int) {
        bbZ++
        bcb.addElement(entry)
        bcc.addElement(Integer(value))
    }
    open fun mq(index: Int): Any? = bcb.elementAt(index)
}
