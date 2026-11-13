package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.util.ArrayList

open class bm(event: String?, occurrenceIndex: Int) {
    private var eventOccurrenceIndex = occurrenceIndex
    private var parentNode: bm? = null
    private var childNodes = ArrayList<bm?>()
    private var eventName: String? = event
    open fun a(child: bm?) {
        child!!.parentNode = this
        if (childNodes.size >= w.Zq) childNodes.removeAt(0)
        childNodes.add(child)
    }
    open fun nD(): bm? = parentNode
    open fun getName(): String? = eventName
}
