package com.quanhoang.greenfarm3.S800x480

open class cc {
    @JvmField var alf: FloatArray
    constructor(x: Float, y: Float, z: Float) { alf = floatArrayOf(x, y, z) }
    constructor() { alf = FloatArray(3) }
}
