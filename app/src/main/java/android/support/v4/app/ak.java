package android.support.v4.app;

import android.view.animation.Animation;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class ak implements Animation.AnimationListener {
    private final Animation.AnimationListener bB;

    private ak(Animation.AnimationListener animationListener) {
        this.bB = animationListener;
    }

    /* synthetic */ ak(Animation.AnimationListener animationListener, ak akVar) {
        this(animationListener);
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
        if (this.bB != null) {
            this.bB.onAnimationStart(animation);
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        if (this.bB != null) {
            this.bB.onAnimationEnd(animation);
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
        if (this.bB != null) {
            this.bB.onAnimationRepeat(animation);
        }
    }
}
