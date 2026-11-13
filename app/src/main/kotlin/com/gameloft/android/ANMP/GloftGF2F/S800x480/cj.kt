package com.gameloft.android.ANMP.GloftGF2F.S800x480

class cj : Runnable {
    override fun run() {
        val anonymousId = s.iY()
        val anonymousPassword = s.aR(anonymousId)
        s.f(if (GLLib.jr() != null) GLLib.jr() else "NULL_ID", true)
        s.aX(GloftGF2F.acx!!.bY("GAME-PRODUCT-ID"))
        s.aY(GloftGF2F.acx!!.bY("GAME-GGI"))
        s.aZ(GloftGF2F.acx!!.bY("MIDlet-Version"))
        s.ba(GloftGF2F.acx!!.bY("GAME-PLATFORM"))
        s.bb(GloftGF2F.acx!!.bY("GAME-SHOP"))
        if (bt.nY()) {
            while (true) {
                if (bt.nZ().getId() != null && !bt.nZ().getId()!!.equals("")) break
            }
            s.aT(bt.nZ().getId())
            s.aU(bt.getPassword())
            s.aV("facebook")
        } else {
            s.aV("anonymous")
            s.aT(anonymousId)
            s.aU(anonymousPassword)
        }
        val diagnostic = "#quy Username" + s.js()
        s.jn()
    }
}
