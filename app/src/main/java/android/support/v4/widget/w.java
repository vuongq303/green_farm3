package android.support.v4.widget;

import android.widget.PopupWindow;

/* JADX INFO: compiled from: PopupWindowCompat.java */
/* JADX INFO: loaded from: classes.dex */
class w extends v {
    w() {
    }

    @Override // android.support.v4.widget.v, android.support.v4.widget.x
    public void a(PopupWindow popupWindow, boolean z) {
        popupWindow.setOverlapAnchor(z);
    }

    @Override // android.support.v4.widget.x
    public void a(PopupWindow popupWindow, int i) {
        popupWindow.setWindowLayoutType(i);
    }
}
