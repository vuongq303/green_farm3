package com.quanhoang.greenfarm3.S800x480

class cm : Runnable {
    override fun run() {
        try { FarmGameEngine.JM() } catch (failure: Exception) {}
        try { FarmGameEngine.Jd() } catch (failure: Exception) {}
        if (FarmGameEngine.arm == 1) {
            com.gameloft.android2d.socialnetwork.l.mU(1)
            bt.nV()
        } else {
            com.gameloft.android2d.socialnetwork.l.mU(2)
            bt.nV()
        }
        FarmGameEngine.ark = true
    }
}
