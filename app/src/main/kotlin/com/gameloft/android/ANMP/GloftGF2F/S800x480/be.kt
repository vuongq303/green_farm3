package com.gameloft.android.ANMP.GloftGF2F.S800x480

/** Original mutable4x4 matrix; multiplication and in-place vector alias order are retained. */
open class be {
    @JvmField var aiV: Array<FloatArray>?
    constructor() { aiV = Array(4) { FloatArray(4) } }
    constructor(elements: Array<FloatArray>?) { aiV = elements }
    open fun nr() { for (index in aiV!!.indices) aiV!![index][index] = 1.0f }
    open fun ns() {
        val transposed = Array(4) { FloatArray(4) }
        for (row in 0 until 4) for (column in 0 until 4) transposed[column][row] = aiV!![row][column]
        aiV = transposed
    }
    open fun b(other: be?): be {
        val result = be()
        for (row in 0 until 4) for (column in 0 until 4) {
            var sum = 0.0f
            for (index in 0 until 4) sum += aiV!![row][index] * other!!.aiV!![index][column]
            result.aiV!![row][column] = sum
        }
        return result
    }
    open fun a(input: cd?, output: cd?): cd? {
        for (column in 0 until 4) {
            var sum = 0.0f
            for (index in 0 until 4) sum += input!!.alf[index] * aiV!![index][column]
            output!!.alf[column] = sum
        }
        return output
    }
    open fun c(other: be?): be {
        val result = be()
        for (row in 0 until 4) for (column in 0 until 4) result.aiV!![row][column] = aiV!![row][column] + other!!.aiV!![row][column]
        return result
    }
    companion object {
        @JvmStatic fun g(angle: Float): be = be(arrayOf(
            floatArrayOf(1f, 0f, 0f, 0f), floatArrayOf(0f, Math.cos(angle.toDouble()).toFloat(), Math.sin(angle.toDouble()).toFloat(), 0f),
            floatArrayOf(0f, (-Math.sin(angle.toDouble())).toFloat(), Math.cos(angle.toDouble()).toFloat(), 0f), floatArrayOf(0f, 0f, 0f, 1f)))
        @JvmStatic fun h(angle: Float): be = be(arrayOf(
            floatArrayOf(Math.cos(angle.toDouble()).toFloat(), 0f, (-Math.sin(angle.toDouble())).toFloat(), 0f), floatArrayOf(0f, 1f, 0f, 0f),
            floatArrayOf(Math.sin(angle.toDouble()).toFloat(), 0f, Math.cos(angle.toDouble()).toFloat(), 0f), floatArrayOf(0f, 0f, 0f, 1f)))
        @JvmStatic fun i(angle: Float): be = be(arrayOf(
            floatArrayOf(Math.cos(angle.toDouble()).toFloat(), Math.sin(angle.toDouble()).toFloat(), 0f, 0f), floatArrayOf((-Math.sin(angle.toDouble())).toFloat(), Math.cos(angle.toDouble()).toFloat(), 0f, 0f),
            floatArrayOf(0f, 0f, 1f, 0f), floatArrayOf(0f, 0f, 0f, 1f)))
    }
}
