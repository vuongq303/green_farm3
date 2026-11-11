package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
final class cj implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        String strIY = s.iY();
        String strAR = s.aR(strIY);
        s.f(GLLib.jr() != null ? GLLib.jr() : "NULL_ID", true);
        s.aX(GloftGF2F.acx.bY("GAME-PRODUCT-ID"));
        s.aY(GloftGF2F.acx.bY("GAME-GGI"));
        s.aZ(GloftGF2F.acx.bY("MIDlet-Version"));
        s.ba(GloftGF2F.acx.bY("GAME-PLATFORM"));
        s.bb(GloftGF2F.acx.bY("GAME-SHOP"));
        if (bt.nY()) {
            while (true) {
                if (bt.nZ().getId() != null && !bt.nZ().getId().equals("")) {
                    break;
                }
            }
            s.aT(bt.nZ().getId());
            s.aU(bt.getPassword());
            s.aV("facebook");
        } else {
            s.aV("anonymous");
            s.aT(strIY);
            s.aU(strAR);
        }
        String str = "#quy Username" + s.js();
        s.jn();
    }
}
