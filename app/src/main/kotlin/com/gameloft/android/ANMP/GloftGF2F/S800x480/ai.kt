package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class ai : com.gameloft.android2d.socialnetwork.i() {
    @JvmField var acy = -1
    open fun ml(): ay? {
        if (acy == -2) {
            if (bu.akl == null || (getId() != null && bu.userId != null && bu.userId!!.compareTo(getId()!!) != 0)) {
                val image = super.UN() ?: return null
                bu.akl = ay(image)
                bu.userId = getId()
            }
            return bu.akl
        }
        if (!bu.cE(getId())) {
            val image = super.UN() ?: return null
            val imageBuffer = ay(image)
            bu.a(getId(), imageBuffer)
            return imageBuffer
        }
        return bu.oc()
    }
    open fun eu(userIndex: Int) { acy = userIndex }
}
