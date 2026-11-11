package android.support.v7.widget;

import android.view.ViewTreeObserver;

/* JADX INFO: compiled from: AppCompatSpinner.java */
/* JADX INFO: loaded from: classes.dex */
class ax implements ViewTreeObserver.OnGlobalLayoutListener {
    final /* synthetic */ av uz;

    ax(av avVar) {
        this.uz = avVar;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        if (!this.uz.F(this.uz.ut)) {
            this.uz.dismiss();
        } else {
            this.uz.el();
            super/*android.support.v7.widget.by*/.show();
        }
    }
}
