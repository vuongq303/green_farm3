package android.support.v4.app;

import android.app.SharedElementCallback;
import android.view.View;
import java.util.List;

/* JADX INFO: compiled from: ActivityCompat.java */
/* JADX INFO: loaded from: classes.dex */
class f extends e {
    public f(dm dmVar) {
        super(dmVar);
    }

    @Override // android.app.SharedElementCallback
    public void onSharedElementsArrived(List<String> list, List<View> list2, SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
        this.H.a(list, list2, new g(this, onSharedElementsReadyListener));
    }
}
