package android.support.v4.widget;

import android.view.View;
import android.widget.PopupWindow;

/* JADX INFO: compiled from: PopupWindowCompat.java */
/* JADX INFO: loaded from: classes.dex */
class u extends x {
    u() {
    }

    @Override // android.support.v4.widget.x
    public void a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        popupWindow.showAsDropDown(view, i, i2, i3);
    }
}
