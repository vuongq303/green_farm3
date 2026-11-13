package com.quanhoang.greenfarm3.S800x480

import java.util.Vector

open class `as` {
    companion object {
        private var workers: Array<am?>? = null
        private var requestSlots: Array<aq?>? = null
        private var pendingRequests: Vector<aq?>? = null
        @JvmStatic fun Init() {
            if (workers == null) {
                workers = arrayOfNulls(3)
                requestSlots = arrayOfNulls(3)
                for (slotIndex in 0 until 3) {
                    workers!![slotIndex] = am(false)
                    requestSlots!![slotIndex] = aq()
                }
            }
            pendingRequests = Vector()
        }
        @JvmStatic fun a(url: String?, payload: String?, requestMethod: Int, callback: ar?, timeoutMillis: Int) {
            val request = aq(url, payload, requestMethod, callback, timeoutMillis)
            synchronized(pendingRequests!!) { pendingRequests!!.addElement(request) }
        }
        @JvmStatic fun a(url: String?, payload: String?, callback: ar?, timeoutMillis: Int) {
            a(url, payload, 1, callback, timeoutMillis)
        }
        @JvmStatic fun dV(elapsedMillis: Int) {
            dispatchPendingRequests()
            for (slotIndex in 0 until 3) requestSlots!![slotIndex]!!.dV(elapsedMillis)
        }
        private fun availableSlotIndex(): Int = synchronized(`as`::class.java) {
            for (slotIndex in 0 until 3) if (requestSlots!![slotIndex]!!.mK()) return@synchronized slotIndex
            -1
        }
        private fun dispatchPendingRequests() = synchronized(`as`::class.java) {
            while (true) {
                val slotIndex = availableSlotIndex()
                if (pendingRequests!!.size <= 0) break
                val request = pendingRequests!!.elementAt(0)
                if (slotIndex == -1 || request == null) break
                requestSlots!![slotIndex]!!.a(request, workers!![slotIndex])
                pendingRequests!!.removeElementAt(0)
            }
        }
    }
}
