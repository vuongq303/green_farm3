package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class ag extends AnimatorListenerAdapter {
    final /* synthetic */ ad bv;
    private final /* synthetic */ Fragment bw;
    private final /* synthetic */ ViewGroup bx;
    private final /* synthetic */ View by;

    ag(ad adVar, ViewGroup viewGroup, View view, Fragment fragment) {
        this.bv = adVar;
        this.bx = viewGroup;
        this.by = view;
        this.bw = fragment;
    }

    @Override // android.animation.Animator.AnimatorListener
    public /* synthetic */ void onAnimationEnd(Animator animator, boolean z) {
        throw new Error("Unresolved compilation problem: \n\tThe type new AnimatorListenerAdapter(){} must implement the inherited abstract method Animator.AnimatorListener.onAnimationEnd(Animator, boolean)\n");
    }

    @Override // android.animation.Animator.AnimatorListener
    public /* synthetic */ void onAnimationStart(Animator animator, boolean z) {
        throw new Error("Unresolved compilation problem: \n\tThe type new AnimatorListenerAdapter(){} must implement the inherited abstract method Animator.AnimatorListener.onAnimationStart(Animator, boolean)\n");
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        if (this.bx != null) {
            this.bx.endViewTransition(this.by);
        }
        if (this.bw.getAnimator() != null) {
            this.bw.setAnimator(null);
            this.bv.a(this.bw, this.bw.getStateAfterAnimating(), 0, 0, false);
        }
    }
}
