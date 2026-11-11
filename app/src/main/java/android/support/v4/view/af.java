package android.support.v4.view;

import android.view.View;

/* JADX INFO: compiled from: ViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class af extends ae {
    af() {
    }

    @Override // android.support.v4.view.al
    public boolean p(View view) {
        return view.isLaidOut();
    }

    @Override // android.support.v4.view.al
    public boolean isAttachedToWindow(View view) {
        return view.isAttachedToWindow();
    }
}
