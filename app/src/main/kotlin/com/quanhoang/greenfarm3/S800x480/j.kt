package com.quanhoang.greenfarm3.S800x480

/** Original /13 packed-definition tables; widths are signed bytes exactly as in the decoder. */
open class j {
    @JvmField var KU = 0
    @JvmField var KV = 0
    @JvmField var KW: Array<IntArray>? = null
    companion object {
        private var KT: Array<j?>? = null
        @JvmStatic fun bY(index: Int): j? = KT!![index]
        @JvmStatic fun bZ(index: Int) {
            GameRuntime.be("START LOADING CLASSES")
            GameRuntime.be("START Opening file")
            GameRuntime.bk("/13")
            GameRuntime.be("START load currentClass = " + index)
            loadTable(index)
            GameRuntime.ke()
        }
        @JvmStatic fun il() {
            GameRuntime.be("START LOADING CLASSES")
            KT = arrayOfNulls(34)
            GameRuntime.be("START Opening file")
            GameRuntime.bk("/13")
            GameRuntime.be("START loop")
            for (index in 0 until 34) loadTable(index)
            GameRuntime.ke()
        }
        private fun loadTable(index: Int) {
            KT!![index] = j()
            val encoded = GameRuntime.db(index)
            GameRuntime.be("Open File " + index)
            val table = KT!![index]!!
            table.KV = GameRuntime.l(encoded, 0)
            table.KU = GameRuntime.l(encoded, 4)
            var byteOffset = 8
            if (table.KV > 0 && table.KU > 0) {
                val columnWidths = ByteArray(table.KU)
                for (column in 0 until table.KU) { columnWidths[column] = encoded[byteOffset]; byteOffset++ }
                table.KW = Array(table.KV) { IntArray(table.KU) }
                for (row in 0 until table.KV) for (column in 0 until table.KU) {
                    when (columnWidths[column].toInt()) {
                        1 -> table.KW!![row][column] = encoded[byteOffset].toInt()
                        2 -> table.KW!![row][column] = GameRuntime.k(encoded, byteOffset).toInt()
                        4 -> table.KW!![row][column] = GameRuntime.l(encoded, byteOffset)
                    }
                    byteOffset += columnWidths[column].toInt()
                }
            }
        }
    }
}
