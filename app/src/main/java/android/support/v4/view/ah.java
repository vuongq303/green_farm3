package android.support.v4.view;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: compiled from: ViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class ah implements View.OnApplyWindowInsetsListener {
    final /* synthetic */ ag hr;
    private final /* synthetic */ y hs;

    ah(ag agVar, y yVar) {
        this.hr = agVar;
        this.hs = yVar;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        return (WindowInsets) ay.a(this.hs.a(view, ay.l(windowInsets)));
    }
}
