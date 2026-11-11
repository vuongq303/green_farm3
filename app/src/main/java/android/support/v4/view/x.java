package android.support.v4.view;

import android.view.View;

/* JADX INFO: compiled from: NestedScrollingParentHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class x {
    private int hp;

    public void onNestedScrollAccepted(View view, View view2, int i) {
        b(view, view2, i, 0);
    }

    public void b(View view, View view2, int i, int i2) {
        this.hp = i;
    }

    public int getNestedScrollAxes() {
        return this.hp;
    }

    public void onStopNestedScroll(View view) {
        a(view, 0);
    }

    public void a(View view, int i) {
        this.hp = 0;
    }
}
