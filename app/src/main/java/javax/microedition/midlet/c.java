package javax.microedition.midlet;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

/* JADX INFO: compiled from: App.java */
/* JADX INFO: loaded from: classes.dex */
final class c implements Application.ActivityLifecycleCallbacks {
    final /* synthetic */ App cnt;

    c(App app) {
        this.cnt = app;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (!this.cnt.cno) {
            this.cnt.cno = true;
            this.cnt.aku();
        }
        Window.Callback callback = activity.getWindow().getCallback();
        if (!(callback instanceof aa)) {
            activity.getWindow().setCallback(new aa(callback));
        }
        if (App.cnm >= 18) {
            View rootView = activity.getWindow().getDecorView().getRootView();
            View rootView2 = null;
            if (this.cnt.cns != null) {
                rootView2 = this.cnt.cns.getWindow().getDecorView().getRootView();
            }
            View viewAkT = ad.akT();
            if (viewAkT != null && viewAkT != rootView && viewAkT != rootView2) {
                ViewTreeObserver viewTreeObserver = viewAkT.getViewTreeObserver();
                viewTreeObserver.addOnWindowAttachListener(this.cnt.cnq);
                viewTreeObserver.addOnWindowFocusChangeListener(this.cnt.cnr);
            }
        }
        this.cnt.cns = activity;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        Window.Callback callback = activity.getWindow().getCallback();
        if (callback instanceof aa) {
            aa aaVar = (aa) callback;
            aaVar.akR();
            activity.getWindow().setCallback(aaVar.akQ());
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }
}
