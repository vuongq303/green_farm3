package com.gameloft.a.b;

/* JADX INFO: compiled from: JW_Federation.java */
/* JADX INFO: loaded from: classes.dex */
final class c extends Thread {
    final /* synthetic */ String bwP;

    c(String str) {
        this.bwP = str;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        e eVar = new e();
        a.a(this.bwP, "", 0, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a.a(eVar)) {
                if (a.bwM != null) {
                    a.bwM.a(5, null, eVar.Bg, eVar.acH, eVar.acI, null);
                }
                a.Wl();
                if (eVar != null) {
                }
                return;
            }
        } while (eVar.BM == null);
        a.b(eVar);
        String strHu = h.hu(h.ht(eVar.BM));
        if (a.bwM != null) {
            a.bwM.a(5, strHu, eVar.Bg, eVar.acH, eVar.acI, null);
        }
        a.Wl();
        if (eVar != null) {
        }
    }
}
