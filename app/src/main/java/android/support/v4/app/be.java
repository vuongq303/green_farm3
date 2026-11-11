package android.support.v4.app;

import android.view.View;
import java.util.ArrayList;

/* JADX INFO: compiled from: FragmentTransitionCompat21.java */
/* JADX INFO: loaded from: classes.dex */
class be implements Runnable {
    private final /* synthetic */ ArrayList bW;
    private final /* synthetic */ ArrayList ch;
    private final /* synthetic */ int cq;
    private final /* synthetic */ ArrayList cr;
    private final /* synthetic */ ArrayList cs;

    be(int i, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
        this.cq = i;
        this.bW = arrayList;
        this.cr = arrayList2;
        this.ch = arrayList3;
        this.cs = arrayList4;
    }

    @Override // java.lang.Runnable
    public void run() {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.cq) {
                ((View) this.bW.get(i2)).setTransitionName((String) this.cr.get(i2));
                ((View) this.ch.get(i2)).setTransitionName((String) this.cs.get(i2));
                i = i2 + 1;
            } else {
                return;
            }
        }
    }
}
