package android.support.v7.widget;

import android.R;
import android.view.View;

/* JADX INFO: compiled from: ToolbarWidgetWrapper.java */
/* JADX INFO: loaded from: classes.dex */
class dg implements View.OnClickListener {
    final android.support.v7.c.a.a yO;
    final /* synthetic */ df yP;

    dg(df dfVar) {
        this.yP = dfVar;
        this.yO = new android.support.v7.c.a.a(this.yP.yD.getContext(), 0, R.id.home, 0, 0, this.yP.jp);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.yP.mB != null && this.yP.yK) {
            this.yP.mB.onMenuItemSelected(0, this.yO);
        }
    }
}
