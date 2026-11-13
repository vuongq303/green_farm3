package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class cq(@JvmField val bbN: ci?) : Runnable {
    override fun run() {
        while (ci.bbF != null) {
            try {
                if (ci.bbG) {
                    ci.bbG = false
                    // Only serialize after completed game-thread actions.
                    ProgressSaveController.requestSave()
                }
                Thread.sleep(50L)
            } catch (failure: Exception) {}
        }
    }
}
