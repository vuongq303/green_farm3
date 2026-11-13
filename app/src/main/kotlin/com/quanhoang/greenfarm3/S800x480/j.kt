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
            GLLib.be("START LOADING CLASSES")
            GLLib.be("START Opening file")
            GLLib.bk("/13")
            GLLib.be("START load currentClass = " + index)
            loadTable(index)
            GLLib.ke()
        }
        @JvmStatic fun il() {
            GLLib.be("START LOADING CLASSES")
            KT = arrayOfNulls(34)
            GLLib.be("START Opening file")
            GLLib.bk("/13")
            GLLib.be("START loop")
            for (index in 0 until 34) loadTable(index)
            GLLib.ke()
        }
        private fun loadTable(index: Int) {
            KT!![index] = j()
            val encoded = GLLib.db(index)
            GLLib.be("Open File " + index)
            val table = KT!![index]!!
            table.KV = GLLib.l(encoded, 0)
            table.KU = GLLib.l(encoded, 4)
            var byteOffset = 8
            if (table.KV > 0 && table.KU > 0) {
                val columnWidths = ByteArray(table.KU)
                for (column in 0 until table.KU) { columnWidths[column] = encoded[byteOffset]; byteOffset++ }
                table.KW = Array(table.KV) { IntArray(table.KU) }
                for (row in 0 until table.KV) for (column in 0 until table.KU) {
                    when (columnWidths[column].toInt()) {
                        1 -> table.KW!![row][column] = encoded[byteOffset].toInt()
                        2 -> table.KW!![row][column] = GLLib.k(encoded, byteOffset).toInt()
                        4 -> table.KW!![row][column] = GLLib.l(encoded, byteOffset)
                    }
                    byteOffset += columnWidths[column].toInt()
                }
            }
        }
    }
}
