package com.quanhoang.greenfarm3.S800x480

class cl : Runnable {
    override fun run() {
        while (FarmGameEngine.bbI != null) {
            if (!FarmGameEngine.bbM.isEmpty()) {
                try {
                    val event = FarmGameEngine.bbM.firstElement() as dd
                    FarmGameEngine.baf.b(event.bcm, event.bcn)
                    FarmGameEngine.bbM.removeElementAt(0)
                    Thread.sleep(62L)
                } catch (failure: Exception) {
                    // Preserve the original event-worker retry behavior.
                }
            }
        }
    }
}
