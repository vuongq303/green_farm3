package android.support.v4.view;

import android.os.Build;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: ViewPropertyAnimatorCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class ar {
    private WeakReference<View> hA;
    Runnable hB = null;
    Runnable hC = null;
    int hD = -1;

    ar(View view) {
        this.hA = new WeakReference<>(view);
    }

    public ar d(long j) {
        View view = this.hA.get();
        if (view != null) {
            view.animate().setDuration(j);
        }
        return this;
    }

    public ar a(float f) {
        View view = this.hA.get();
        if (view != null) {
            view.animate().alpha(f);
        }
        return this;
    }

    public ar b(float f) {
        View view = this.hA.get();
        if (view != null) {
            view.animate().translationY(f);
        }
        return this;
    }

    public long getDuration() {
        View view = this.hA.get();
        if (view != null) {
            return view.animate().getDuration();
        }
        return 0L;
    }

    public ar a(Interpolator interpolator) {
        View view = this.hA.get();
        if (view != null) {
            view.animate().setInterpolator(interpolator);
        }
        return this;
    }

    public ar e(long j) {
        View view = this.hA.get();
        if (view != null) {
            view.animate().setStartDelay(j);
        }
        return this;
    }

    public void cancel() {
        View view = this.hA.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public void start() {
        View view = this.hA.get();
        if (view != null) {
            view.animate().start();
        }
    }

    public ar a(av avVar) {
        View view = this.hA.get();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                a(view, avVar);
            } else {
                view.setTag(2113929216, avVar);
                a(view, new au(this));
            }
        }
        return this;
    }

    private void a(View view, av avVar) {
        if (avVar != null) {
            view.animate().setListener(new as(this, avVar, view));
        } else {
            view.animate().setListener(null);
        }
    }

    public ar a(ax axVar) {
        View view = this.hA.get();
        if (view != null && Build.VERSION.SDK_INT >= 19) {
            at atVar = null;
            if (axVar != null) {
                atVar = new at(this, axVar, view);
            }
            view.animate().setUpdateListener(atVar);
        }
        return this;
    }
}
