package android.support.v7.c.a;

import android.os.SystemClock;
import android.support.v7.widget.ci;
import android.view.MenuItem;

/* JADX INFO: compiled from: CascadingMenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
class k implements ci {
    final /* synthetic */ h pS;

    k(h hVar) {
        this.pS = hVar;
    }

    @Override // android.support.v7.widget.ci
    public void b(s sVar, MenuItem menuItem) {
        this.pS.py.removeCallbacksAndMessages(sVar);
    }

    @Override // android.support.v7.widget.ci
    public void c(s sVar, MenuItem menuItem) {
        int i;
        this.pS.py.removeCallbacksAndMessages(null);
        int i2 = 0;
        int size = this.pS.pA.size();
        while (true) {
            if (i2 >= size) {
                i = -1;
                break;
            } else {
                if (sVar == this.pS.pA.get(i2).lZ) {
                    i = i2;
                    break;
                }
                i2++;
            }
        }
        if (i != -1) {
            int i3 = i + 1;
            this.pS.py.postAtTime(new l(this, i3 < this.pS.pA.size() ? this.pS.pA.get(i3) : null, menuItem, sVar), sVar, SystemClock.uptimeMillis() + 200);
        }
    }
}
