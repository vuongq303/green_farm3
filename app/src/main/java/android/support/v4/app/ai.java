package android.support.v4.app;

import android.os.Build;
import android.view.View;
import android.view.animation.Animation;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class ai extends ak {
    View mView;

    ai(View view, Animation.AnimationListener animationListener) {
        super(animationListener, null);
        this.mView = view;
    }

    @Override // android.support.v4.app.ak, android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        if (android.support.v4.view.aa.isAttachedToWindow(this.mView) || Build.VERSION.SDK_INT >= 24) {
            this.mView.post(new aj(this));
        } else {
            this.mView.setLayerType(0, null);
        }
        super.onAnimationEnd(animation);
    }
}
