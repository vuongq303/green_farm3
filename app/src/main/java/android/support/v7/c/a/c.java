package android.support.v7.c.a;

import android.support.v7.widget.bt;

/* JADX INFO: compiled from: ActionMenuItemView.java */
/* JADX INFO: loaded from: classes.dex */
class c extends bt {
    final /* synthetic */ b pk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public c(b bVar) {
        super(bVar);
        this.pk = bVar;
    }

    @Override // android.support.v7.widget.bt
    public ao cJ() {
        if (this.pk.pe != null) {
            return this.pk.pe.cJ();
        }
        return null;
    }

    @Override // android.support.v7.widget.bt
    protected boolean cK() {
        ao aoVarCJ;
        return this.pk.pc != null && this.pk.pc.a(this.pk.pb) && (aoVarCJ = cJ()) != null && aoVarCJ.isShowing();
    }
}
