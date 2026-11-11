package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class am extends AnimatorListenerAdapter {
    View mView;

    @Override // android.animation.Animator.AnimatorListener
    public /* synthetic */ void onAnimationEnd(Animator animator, boolean z) {
        throw new Error("Unresolved compilation problem: \n\tThe type FragmentManagerImpl.AnimatorOnHWLayerIfNeededListener must implement the inherited abstract method Animator.AnimatorListener.onAnimationEnd(Animator, boolean)\n");
    }

    @Override // android.animation.Animator.AnimatorListener
    public /* synthetic */ void onAnimationStart(Animator animator, boolean z) {
        throw new Error("Unresolved compilation problem: \n\tThe type FragmentManagerImpl.AnimatorOnHWLayerIfNeededListener must implement the inherited abstract method Animator.AnimatorListener.onAnimationStart(Animator, boolean)\n");
    }

    am(View view) {
        this.mView = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.mView.setLayerType(2, null);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.mView.setLayerType(0, null);
        animator.removeListener(this);
    }
}
