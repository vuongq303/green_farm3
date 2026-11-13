package com.quanhoang.greenfarm3.S800x480

open class cf {
    private var storedValue = 0L
    open fun get(): Long = storedValue
    open fun set(value: Long) { storedValue = value }
}
