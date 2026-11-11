package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: TooltipCompat.java */
/* JADX INFO: loaded from: classes.dex */
class dk implements dm {
    private dk() {
    }

    @Override // android.support.v7.widget.dm
    public void a(View view, CharSequence charSequence) {
        view.setTooltipText(charSequence);
    }
}
