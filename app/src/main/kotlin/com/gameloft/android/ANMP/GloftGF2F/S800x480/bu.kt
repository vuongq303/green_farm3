package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class bu {
    companion object {
        @JvmField var akl: ay? = null
        @JvmField var userId: String? = ""
        @JvmField var ako = 0
        @JvmField var akp = 50
        @JvmField var akm = arrayOfNulls<ay>(akp)
        @JvmField var akn = arrayOfNulls<String>(akp)
        @JvmField var akq = -1
        @JvmStatic fun clear() {
            for (index in 0 until akp) {
                akn[index] = null
                if (akm[index] != null) akm[index]!!.nk()
                akm[index] = null
            }
            if (akl != null) akl!!.nk()
            akl = null
        }
        @JvmStatic fun cE(userId: String?): Boolean {
            akq = -1
            for (index in 0 until akp) {
                if (akn[index] != null && akn[index]!!.compareTo(userId!!) == 0) {
                    akq = index
                    return true
                }
            }
            return false
        }
        @JvmStatic fun a(userId: String?, image: ay?) {
            ako++
            ako %= akp
            if (akm[ako] != null) akm[ako]!!.nk()
            akm[ako] = image
            akn[ako] = userId
        }
        @JvmStatic fun oc(): ay? = if (akq > -1) akm[akq] else null
    }
}
