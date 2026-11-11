package android.support.v7.c;

import android.support.v4.view.ar;
import android.support.v4.view.av;
import android.support.v4.view.aw;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: ViewPropertyAnimatorCompatSet.java */
/* JADX INFO: loaded from: classes.dex */
public class l {
    private boolean lb;
    private Interpolator mInterpolator;
    av oE;
    private long oD = -1;
    private final aw oF = new m(this);
    final ArrayList<ar> oC = new ArrayList<>();

    public l a(ar arVar) {
        if (!this.lb) {
            this.oC.add(arVar);
        }
        return this;
    }

    public l a(ar arVar, ar arVar2) {
        this.oC.add(arVar);
        arVar2.e(arVar.getDuration());
        this.oC.add(arVar2);
        return this;
    }

    public void start() {
        if (!this.lb) {
            for (ar arVar : this.oC) {
                if (this.oD >= 0) {
                    arVar.d(this.oD);
                }
                if (this.mInterpolator != null) {
                    arVar.a(this.mInterpolator);
                }
                if (this.oE != null) {
                    arVar.a(this.oF);
                }
                arVar.start();
            }
            this.lb = true;
        }
    }

    void cA() {
        this.lb = false;
    }

    public void cancel() {
        if (this.lb) {
            Iterator<ar> it = this.oC.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            this.lb = false;
        }
    }

    public l g(long j) {
        if (!this.lb) {
            this.oD = j;
        }
        return this;
    }

    public l b(Interpolator interpolator) {
        if (!this.lb) {
            this.mInterpolator = interpolator;
        }
        return this;
    }

    public l b(av avVar) {
        if (!this.lb) {
            this.oE = avVar;
        }
        return this;
    }
}
