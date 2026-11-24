package com.quanhoang.greenfarm3.S800x480

open class cq(@JvmField val bbN: FarmGameEngine?) : Runnable {
    override fun run() {
        while (FarmGameEngine.bbF != null) {
            try {
                if (FarmGameEngine.bbG) {
                    FarmGameEngine.bbG = false
                    // Only serialize after completed game-thread actions.
                    ProgressSaveController.requestSave()
                }
                Thread.sleep(50L)
            } catch (failure: Exception) {}
        }
    }
}
