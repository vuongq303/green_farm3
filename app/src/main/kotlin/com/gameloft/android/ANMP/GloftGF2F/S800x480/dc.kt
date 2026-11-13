package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class dc(itemType: Int, encodedItem: Int) {
    @JvmField var anG = itemType
    @JvmField var anM = ci.ih(encodedItem)
    @JvmField var aoG = 0
    @JvmField var aoH = 0
    init {
        if (itemType != 59) {
            aoG = 1
            aoH = anG
        } else {
            val rewardKind = ci.ii(anM) and 65535
            aoG = ci.aZM[rewardKind].toInt()
            aoH = ci.aZN[rewardKind].toInt()
        }
    }
    open fun MT(): Int = if (anG == 37 || anG == 36 || anG == 6) ci.ii(anM) else (ci.ii(anM) and -65536) shr 16
    open fun MU() {
        if (anG == 37) { ci.hX(ci.ii(anM)); return }
        if (anG == 36) { ci.hS(ci.ii(anM)); return }
        if (anG == 6) { ci.hT(ci.ii(anM)); return }
        if (anG == 59) {
            val rewardKind = ci.ii(anM) and 65535
            val rewardCount = (ci.ii(anM) and -65536) shr 16
            if (rewardKind == 110) { ci.hV(rewardCount); return }
            if (rewardKind == 5) { ci.hU(rewardCount); return }
            if (rewardKind in 111..116) { ci.bN(rewardCount, rewardKind - 111); return }
            ci.O(0, rewardKind, rewardCount)
            if (rewardCount > 0) ci.hI(-rewardCount)
            ci.Fy()
        }
    }
    open fun MV(): Int = anG
}
