package com.quanhoang.greenfarm3.S800x480

open class ce {
    private var storedValue = 0
    open fun get(): Int = storedValue
    open fun set(value: Int) { storedValue = value }
}
