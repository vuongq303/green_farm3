package com.quanhoang.greenfarm3;

import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: Zoro.java */
/* JADX INFO: loaded from: classes.dex */
class ca implements Runnable {
    final /* synthetic */ bz beA;

    ca(bz bzVar) {
        this.beA = bzVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        String strD = MIDlet.d(MIDlet.cnA, new String(this.beA.EV));
        long jA = 0;
        if (strD != null) {
            try {
                jA = Long.parseLong(strD);
                jA ^= (long) this.beA.a(this.beA.EW);
            } catch (NumberFormatException e) {
            }
        }
        long jEb = com.gameloft.android.wrapper.b.Ou().eb(new String(this.beA.EX));
        String str = "classesSize: " + jEb;
        String str2 = "classesSizeSaved: " + jA;
        if (jEb != jA) {
            this.beA.bey = true;
            this.beA.Op();
        }
        if (!this.beA.bey) {
            com.gameloft.android.wrapper.au.a("tz_param", "yes", com.gameloft.android.wrapper.au.getContext().getPackageName());
        }
    }
}
