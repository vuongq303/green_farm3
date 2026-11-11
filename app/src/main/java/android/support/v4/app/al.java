package android.support.v4.app;

import android.animation.Animator;
import android.view.animation.Animation;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class al {
    public final Animation bC;
    public final Animator bD;

    private al(Animation animation) {
        this.bC = animation;
        this.bD = null;
        if (animation == null) {
            throw new IllegalStateException("Animation cannot be null");
        }
    }

    /* synthetic */ al(Animation animation, al alVar) {
        this(animation);
    }

    private al(Animator animator) {
        this.bC = null;
        this.bD = animator;
        if (animator == null) {
            throw new IllegalStateException("Animator cannot be null");
        }
    }

    /* synthetic */ al(Animator animator, al alVar) {
        this(animator);
    }
}
