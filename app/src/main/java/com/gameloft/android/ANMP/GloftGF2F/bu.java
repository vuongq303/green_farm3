package com.gameloft.android.ANMP.GloftGF2F;

import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: Start.java */
/* JADX INFO: loaded from: classes.dex */
class bu implements Runnable {
    final /* synthetic */ bt bcY;

    bu(bt btVar) {
        this.bcY = btVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!MIDlet.cos) {
        }
        if (!MIDlet.coi || (MIDlet.coi && !this.bcY.bcX.isFinishing())) {
            com.gameloft.android.wrapper.ae.bfS = 800;
            com.gameloft.android.wrapper.ae.bfT = 480;
            com.gameloft.android.wrapper.ae.bfU = "GloftGF2F";
            com.gameloft.android.wrapper.ae.bfZ = true;
            new com.gameloft.android.wrapper.ae(Start.bcW, 0.01f, "".split(","), "".split(","), "com.gameloft.android.ANMP.GloftGF2F");
            com.gameloft.android.wrapper.ae.a(this.bcY.bcX.getPackageManager(), this.bcY.bcX.bcT, this.bcY.bcX.bcU);
            if (!MIDlet.coi) {
                this.bcY.bcX.finish();
            }
        }
    }
}
