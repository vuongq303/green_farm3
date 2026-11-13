package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.util.Vector

open class d {
    private var billingMethods = Vector<c>()
    open fun a(methodData: Array<bc?>?): Int {
        billingMethods.removeAllElements()
        for (entry in methodData!!) {
            val method = c()
            method.a(entry)
            billingMethods.addElement(method)
        }
        return 0
    }
    open fun hP(): Int = billingMethods.size
    open fun bX(index: Int): c? = if (index < billingMethods.size) billingMethods.elementAt(index) else null
}
