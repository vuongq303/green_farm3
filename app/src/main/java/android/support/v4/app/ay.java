package android.support.v4.app;

import android.graphics.Rect;
import android.view.View;

/* JADX INFO: compiled from: FragmentTransition.java */
/* JADX INFO: loaded from: classes.dex */
class ay implements Runnable {
    private final /* synthetic */ Fragment bV;
    private final /* synthetic */ Fragment bZ;
    private final /* synthetic */ boolean ca;
    private final /* synthetic */ android.support.v4.e.a cb;
    private final /* synthetic */ View cc;
    private final /* synthetic */ Rect cd;

    ay(Fragment fragment, Fragment fragment2, boolean z, android.support.v4.e.a aVar, View view, Rect rect) {
        this.bV = fragment;
        this.bZ = fragment2;
        this.ca = z;
        this.cb = aVar;
        this.cc = view;
        this.cd = rect;
    }

    @Override // java.lang.Runnable
    public void run() {
        av.a(this.bV, this.bZ, this.ca, (android.support.v4.e.a<String, View>) this.cb, false);
        if (this.cc != null) {
            bb.a(this.cc, this.cd);
        }
    }
}
