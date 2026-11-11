package android.support.v4.app;

import android.graphics.Rect;
import android.transition.Transition;

/* JADX INFO: compiled from: FragmentTransitionCompat21.java */
/* JADX INFO: loaded from: classes.dex */
class bc extends Transition.EpicenterCallback {
    private final /* synthetic */ Rect cd;

    bc(Rect rect) {
        this.cd = rect;
    }

    @Override // android.transition.Transition.EpicenterCallback
    public Rect onGetEpicenter(Transition transition) {
        return this.cd;
    }
}
