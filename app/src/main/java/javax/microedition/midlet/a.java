package javax.microedition.midlet;

import android.view.ViewTreeObserver;

/* JADX INFO: compiled from: App.java */
/* JADX INFO: loaded from: classes.dex */
class a implements ViewTreeObserver.OnWindowAttachListener {
    final /* synthetic */ App cnt;

    a(App app) {
        this.cnt = app;
    }

    @Override // android.view.ViewTreeObserver.OnWindowAttachListener
    public void onWindowAttached() {
    }

    @Override // android.view.ViewTreeObserver.OnWindowAttachListener
    public void onWindowDetached() {
        this.cnt.akw();
    }
}
