package android.support.v4.app;

import android.view.animation.Animation;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class af extends ak {
    final /* synthetic */ ad bv;
    private final /* synthetic */ Fragment bw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    af(ad adVar, Animation.AnimationListener animationListener, Fragment fragment) {
        super(animationListener, null);
        this.bv = adVar;
        this.bw = fragment;
    }

    @Override // android.support.v4.app.ak, android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        super.onAnimationEnd(animation);
        if (this.bw.getAnimatingAway() != null) {
            this.bw.setAnimatingAway(null);
            this.bv.a(this.bw, this.bw.getStateAfterAnimating(), 0, 0, false);
        }
    }
}
