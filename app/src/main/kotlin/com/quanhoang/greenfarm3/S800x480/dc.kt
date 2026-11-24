package com.quanhoang.greenfarm3.S800x480

open class dc(itemType: Int, encodedItem: Int) {
    @JvmField var anG = itemType
    @JvmField var anM = FarmGameEngine.ih(encodedItem)
    @JvmField var aoG = 0
    @JvmField var aoH = 0
    init {
        if (itemType != 59) {
            aoG = 1
            aoH = anG
        } else {
            val rewardKind = FarmGameEngine.ii(anM) and 65535
            aoG = FarmGameEngine.aZM[rewardKind].toInt()
            aoH = FarmGameEngine.aZN[rewardKind].toInt()
        }
    }
    open fun MT(): Int = if (anG == 37 || anG == 36 || anG == 6) FarmGameEngine.ii(anM) else (FarmGameEngine.ii(anM) and -65536) shr 16
    open fun MU() {
        if (anG == 37) { FarmGameEngine.hX(FarmGameEngine.ii(anM)); return }
        if (anG == 36) { FarmGameEngine.hS(FarmGameEngine.ii(anM)); return }
        if (anG == 6) { FarmGameEngine.hT(FarmGameEngine.ii(anM)); return }
        if (anG == 59) {
            val rewardKind = FarmGameEngine.ii(anM) and 65535
            val rewardCount = (FarmGameEngine.ii(anM) and -65536) shr 16
            if (rewardKind == 110) { FarmGameEngine.hV(rewardCount); return }
            if (rewardKind == 5) { FarmGameEngine.hU(rewardCount); return }
            if (rewardKind in 111..116) { FarmGameEngine.bN(rewardCount, rewardKind - 111); return }
            FarmGameEngine.O(0, rewardKind, rewardCount)
            if (rewardCount > 0) FarmGameEngine.hI(-rewardCount)
            FarmGameEngine.Fy()
        }
    }
    open fun MV(): Int = anG
}
