package com.quanhoang.greenfarm3.S800x480

import java.util.Vector

/** Original StoreOfflineItem response model. */
open class bv {
    private var itemIdentifier: String? = null
    private var quantity = 0
    private var categoryCount = 0
    private val categories = Vector<String?>()
    private val billingMethods = d()
    private var replacedQuantity = -1

    open fun a(response: bc?): Int {
        val item = response!!.cd("item") ?: return -2147483646
        itemIdentifier = item as String?
        if (response.cd("quantity") == null) return -2147483646
        try { quantity = response.ce("quantity") } catch (_: Exception) { /* Original parser retains prior value. */ }
        if (response.cd("replaced_quantity") == null) return -2147483646
        try { replacedQuantity = response.ce("replaced_quantity") } catch (_: Exception) { /* Original parser retains prior value. */ }
        val categoryValue = response.cd("category")
        if (categoryValue != null && categoryValue.javaClass.isArray) {
            val array = categoryValue as Array<*>
            categoryCount = array.size
            for (index in 0 until categoryCount) categories.addElement(array[index] as String?)
        }
        val billingValue = response.cd("billing_methods") ?: return 0
        if (billingValue.javaClass.isArray) {
            val array = billingValue as Array<*>
            val methods = arrayOfNulls<bc>(array.size)
            for (index in array.indices) methods[index] = array[index] as bc?
            billingMethods.a(methods)
        }
        return if (billingMethods.hP() < 1) -2147483646 else 0
    }
    open fun od(): String? = itemIdentifier
    open fun oe(): Double = quantity.toDouble()
    open fun of(): Double = replacedQuantity.toDouble()
    open fun og(): Boolean = replacedQuantity >= 0 && quantity > replacedQuantity
    open fun a(method: c?): Boolean = method!!.hN().hP() == 1 && method.hO().hP() == 1 && method.hN().eG(0)!!.equals(method.hO().eG(0))
    open fun oh(): Int = categoryCount
    open fun eN(index: Int): String? = categories.elementAt(index)
    open fun bX(index: Int): c? = billingMethods.bX(index)
}
