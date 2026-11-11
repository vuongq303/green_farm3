package android.support.v4.widget;

import android.widget.EdgeEffect;

/* JADX INFO: compiled from: EdgeEffectCompat.java */
/* JADX INFO: loaded from: classes.dex */
class i extends j {
    i() {
    }

    @Override // android.support.v4.widget.j
    public void a(EdgeEffect edgeEffect, float f, float f2) {
        edgeEffect.onPull(f, f2);
    }
}
