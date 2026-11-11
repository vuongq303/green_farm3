package android.support.v4.app;

import android.app.SharedElementCallback;

/* JADX INFO: compiled from: ActivityCompat.java */
/* JADX INFO: loaded from: classes.dex */
class g implements dn {
    final /* synthetic */ f I;
    private final /* synthetic */ SharedElementCallback.OnSharedElementsReadyListener J;

    g(f fVar, SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
        this.I = fVar;
        this.J = onSharedElementsReadyListener;
    }

    @Override // android.support.v4.app.dn
    public void onSharedElementsReady() {
        this.J.onSharedElementsReady();
    }
}
