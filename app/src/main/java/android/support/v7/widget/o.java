package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.R;
import android.view.View;

/* JADX INFO: compiled from: ActionMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
class o extends android.support.v7.c.a.ag {
    final /* synthetic */ i sV;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public o(i iVar, Context context, android.support.v7.c.a.s sVar, View view, boolean z) {
        super(context, sVar, view, z, R.attr.actionOverflowMenuStyle);
        this.sV = iVar;
        setGravity(8388613);
        c(iVar.sT);
    }

    @Override // android.support.v7.c.a.ag
    protected void onDismiss() {
        if (this.sV.nw != null) {
            this.sV.nw.close();
        }
        this.sV.sP = null;
        super.onDismiss();
    }
}
