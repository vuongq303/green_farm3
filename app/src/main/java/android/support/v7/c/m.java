package android.support.v7.c;

import android.support.v4.view.aw;
import android.view.View;

/* JADX INFO: compiled from: ViewPropertyAnimatorCompatSet.java */
/* JADX INFO: loaded from: classes.dex */
class m extends aw {
    private boolean oG = false;
    private int oH = 0;
    final /* synthetic */ l oI;

    m(l lVar) {
        this.oI = lVar;
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void r(View view) {
        if (!this.oG) {
            this.oG = true;
            if (this.oI.oE != null) {
                this.oI.oE.r(null);
            }
        }
    }

    void cB() {
        this.oH = 0;
        this.oG = false;
        this.oI.cA();
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void s(View view) {
        int i = this.oH + 1;
        this.oH = i;
        if (i == this.oI.oC.size()) {
            if (this.oI.oE != null) {
                this.oI.oE.s(null);
            }
            cB();
        }
    }
}
