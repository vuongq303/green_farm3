package com.gameloft.android.ANMP.GloftGF2F;

import android.content.Intent;
import android.os.Build;
import com.gameloft.android.wrapper.NativeUtils;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: Pt.java */
/* JADX INFO: loaded from: classes.dex */
class bh implements Runnable {
    final /* synthetic */ Pt Fb;

    bh(Pt pt) {
        this.Fb = pt;
    }

    @Override // java.lang.Runnable
    public void run() {
        NativeUtils.sp(((NativeUtils.tm() % 800) * 100) + ((long) (this.Fb.EZ * 50)));
        String strD = MIDlet.d(MIDlet.cnA, new String(this.Fb.EV));
        long jA = 0;
        if (strD != null) {
            try {
                jA = Long.parseLong(strD);
                jA ^= (long) this.Fb.a(this.Fb.EW);
            } catch (NumberFormatException e) {
            }
        }
        if (!(com.gameloft.android.wrapper.b.Ou().eb(new String(this.Fb.EX)) == jA)) {
            try {
                MIDlet.cnz.akA();
                if (Build.VERSION.SDK_INT < 15) {
                    try {
                        Thread.sleep(500L);
                        MIDlet.cnz.akA();
                    } catch (Exception e2) {
                    }
                }
                Intent intent = new Intent(com.gameloft.android.wrapper.au.getActivity(), (Class<?>) Nt.class);
                intent.addFlags(276856832);
                intent.putExtra(this.Fb.hc(), true);
                com.gameloft.android.wrapper.au.getActivity().startActivity(intent);
            } catch (Exception e3) {
            }
            try {
                Thread.sleep(500L);
            } catch (Exception e4) {
            }
            MIDlet.cnz.akE();
        }
    }
}
