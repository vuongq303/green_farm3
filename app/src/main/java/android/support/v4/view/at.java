package android.support.v4.view;

import android.animation.ValueAnimator;
import android.view.View;

/* JADX INFO: compiled from: ViewPropertyAnimatorCompat.java */
/* JADX INFO: loaded from: classes.dex */
class at implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ ar hE;
    private final /* synthetic */ ax hG;
    private final /* synthetic */ View val$view;

    at(ar arVar, ax axVar, View view) {
        this.hE = arVar;
        this.hG = axVar;
        this.val$view = view;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.hG.u(this.val$view);
    }
}
