package com.gameloft.android2d.socialnetwork;

import java.io.InputStream;
import java.net.URL;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class w implements Runnable {
    final /* synthetic */ String bru;

    w(String str) {
        this.bru = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        s.Vc();
        if (s.brk.size() < 2) {
            s.brk.add(this.bru);
        }
        synchronized (s.lock) {
            if (s.brq.containsKey(this.bru) && s.brr.get(this.bru).intValue() > 0) {
                InputStream inputStreamHh = s.hh(this.bru);
                if (inputStreamHh == null) {
                    z = true;
                } else {
                    try {
                        javax.microedition.lcdui.ao aoVarA = javax.microedition.lcdui.ao.a(inputStreamHh, s.bqy, s.bqz);
                        inputStreamHh.close();
                        z = aoVarA == null;
                        aoVarA.recycle();
                    } catch (Exception e) {
                        z = true;
                    }
                }
                if (z) {
                    s.n(this.bru, 0, 0);
                }
            }
        }
        try {
            URL urlHf = s.hf(this.bru);
            if (urlHf != null) {
                s.a(this.bru, urlHf);
            }
        } catch (Exception e2) {
        }
    }
}
