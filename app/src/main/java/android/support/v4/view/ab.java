package android.support.v4.view;

import android.view.View;

/* JADX INFO: compiled from: ViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class ab extends al {
    ab() {
    }

    @Override // android.support.v4.view.al
    public boolean q(View view) {
        return view.hasOnClickListeners();
    }
}
