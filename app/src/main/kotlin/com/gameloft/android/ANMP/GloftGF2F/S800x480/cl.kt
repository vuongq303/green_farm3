package com.gameloft.android.ANMP.GloftGF2F.S800x480

class cl : Runnable {
    override fun run() {
        while (ci.bbI != null) {
            if (!ci.bbM.isEmpty()) {
                try {
                    val event = ci.bbM.firstElement() as dd
                    ci.baf.b(event.bcm, event.bcn)
                    ci.bbM.removeElementAt(0)
                    Thread.sleep(62L)
                } catch (failure: Exception) {
                    // Preserve the original event-worker retry behavior.
                }
            }
        }
    }
}
