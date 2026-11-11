package android.support.v7.app;

import android.view.View;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class al implements android.support.v4.view.y {
    final /* synthetic */ aj lR;

    al(aj ajVar) {
        this.lR = ajVar;
    }

    @Override // android.support.v4.view.y
    public android.support.v4.view.ay a(View view, android.support.v4.view.ay ayVar) {
        int systemWindowInsetTop = ayVar.getSystemWindowInsetTop();
        int iT = this.lR.T(systemWindowInsetTop);
        if (systemWindowInsetTop != iT) {
            ayVar = ayVar.a(ayVar.getSystemWindowInsetLeft(), iT, ayVar.getSystemWindowInsetRight(), ayVar.getSystemWindowInsetBottom());
        }
        return android.support.v4.view.aa.a(view, ayVar);
    }
}
