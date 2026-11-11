package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/* JADX INFO: compiled from: Fragment.java */
/* JADX INFO: loaded from: classes.dex */
class p extends x {
    final /* synthetic */ Fragment am;

    p(Fragment fragment) {
        this.am = fragment;
    }

    @Override // android.support.v4.app.x
    public View onFindViewById(int i) {
        if (this.am.mView == null) {
            throw new IllegalStateException("Fragment does not have a view");
        }
        return this.am.mView.findViewById(i);
    }

    @Override // android.support.v4.app.x
    public boolean onHasView() {
        return this.am.mView != null;
    }

    @Override // android.support.v4.app.x
    public Fragment instantiate(Context context, String str, Bundle bundle) {
        return this.am.mHost.instantiate(context, str, bundle);
    }
}
