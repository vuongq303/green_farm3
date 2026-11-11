package android.support.v4.view;

import android.os.Build;
import android.view.View;

/* JADX INFO: compiled from: ViewPropertyAnimatorCompat.java */
/* JADX INFO: loaded from: classes.dex */
class au implements av {
    ar hH;
    boolean hI;

    au(ar arVar) {
        this.hH = arVar;
    }

    @Override // android.support.v4.view.av
    public void r(View view) {
        this.hI = false;
        if (this.hH.hD > -1) {
            view.setLayerType(2, null);
        }
        if (this.hH.hB != null) {
            Runnable runnable = this.hH.hB;
            this.hH.hB = null;
            runnable.run();
        }
        Object tag = view.getTag(2113929216);
        av avVar = tag instanceof av ? (av) tag : null;
        if (avVar != null) {
            avVar.r(view);
        }
    }

    @Override // android.support.v4.view.av
    public void s(View view) {
        if (this.hH.hD > -1) {
            view.setLayerType(this.hH.hD, null);
            this.hH.hD = -1;
        }
        if (Build.VERSION.SDK_INT >= 16 || !this.hI) {
            if (this.hH.hC != null) {
                Runnable runnable = this.hH.hC;
                this.hH.hC = null;
                runnable.run();
            }
            Object tag = view.getTag(2113929216);
            av avVar = tag instanceof av ? (av) tag : null;
            if (avVar != null) {
                avVar.s(view);
            }
            this.hI = true;
        }
    }

    @Override // android.support.v4.view.av
    public void t(View view) {
        av avVar;
        Object tag = view.getTag(2113929216);
        if (!(tag instanceof av)) {
            avVar = null;
        } else {
            avVar = (av) tag;
        }
        if (avVar != null) {
            avVar.t(view);
        }
    }
}
