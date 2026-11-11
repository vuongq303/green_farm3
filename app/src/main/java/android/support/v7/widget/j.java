package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.R;
import android.view.View;

/* JADX INFO: compiled from: ActionMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
class j extends android.support.v7.c.a.ag {
    final /* synthetic */ i sV;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j(i iVar, Context context, android.support.v7.c.a.as asVar, View view) {
        View view2;
        super(context, asVar, view, false, R.attr.actionOverflowMenuStyle);
        this.sV = iVar;
        if (!((android.support.v7.c.a.w) asVar.getItem()).ds()) {
            if (iVar.sA == null) {
                view2 = (View) iVar.pq;
            } else {
                view2 = iVar.sA;
            }
            setAnchorView(view2);
        }
        c(iVar.sT);
    }

    @Override // android.support.v7.c.a.ag
    protected void onDismiss() {
        this.sV.sQ = null;
        this.sV.sU = 0;
        super.onDismiss();
    }
}
