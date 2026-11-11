package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: AppCompatSpinner.java */
/* JADX INFO: loaded from: classes.dex */
class at extends bt {
    final /* synthetic */ av us;
    final /* synthetic */ as ut;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    at(as asVar, View view, av avVar) {
        super(view);
        this.ut = asVar;
        this.us = avVar;
    }

    @Override // android.support.v7.widget.bt
    public android.support.v7.c.a.ao cJ() {
        return this.us;
    }

    @Override // android.support.v7.widget.bt
    public boolean cK() {
        if (!this.ut.uq.isShowing()) {
            this.ut.uq.show();
            return true;
        }
        return true;
    }
}
