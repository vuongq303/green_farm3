package android.support.v4.view;

import android.view.LayoutInflater;

/* JADX INFO: compiled from: LayoutInflaterCompat.java */
/* JADX INFO: loaded from: classes.dex */
class m {
    m() {
    }

    public void b(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
        layoutInflater.setFactory2(factory2);
        LayoutInflater.Factory factory = layoutInflater.getFactory();
        if (factory instanceof LayoutInflater.Factory2) {
            k.a(layoutInflater, (LayoutInflater.Factory2) factory);
        } else {
            k.a(layoutInflater, factory2);
        }
    }
}
