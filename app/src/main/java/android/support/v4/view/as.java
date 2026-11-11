package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* JADX INFO: compiled from: ViewPropertyAnimatorCompat.java */
/* JADX INFO: loaded from: classes.dex */
class as extends AnimatorListenerAdapter {
    final /* synthetic */ ar hE;
    private final /* synthetic */ av hF;
    private final /* synthetic */ View val$view;

    as(ar arVar, av avVar, View view) {
        this.hE = arVar;
        this.hF = avVar;
        this.val$view = view;
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
    public void onAnimationCancel(Animator animator) {
        this.hF.t(this.val$view);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.hF.s(this.val$view);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.hF.r(this.val$view);
    }
}
