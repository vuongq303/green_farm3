package com.gameloft.android.ANMP.GloftGF2F.S800x480

class p : Runnable {
    override fun run() {
        n.iF()
        n.iG()
        try { Thread.sleep(5000L) } catch (failure: Exception) {}
        if (n.Lr) n.setResult(2) else {
            n.setResult(10)
            n.Lz = true
        }
    }
}
