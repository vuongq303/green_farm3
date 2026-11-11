package com.gameloft.android.ANMP.GloftGF2F;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class ax extends ProgressDialog {
    Toast Ds;
    final /* synthetic */ InGameBrowserActivity Ex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ax(InGameBrowserActivity inGameBrowserActivity, Context context) {
        super(context);
        this.Ex = inGameBrowserActivity;
        this.Ds = null;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        if (this.Ds == null) {
            this.Ds = Toast.makeText(com.gameloft.android.wrapper.au.getContext(), com.gameloft.android.wrapper.au.getActivity().getString(InGameBrowserActivity.Dc[InGameBrowserActivity.DG]), 1);
            this.Ds.setText(com.gameloft.android.wrapper.au.getActivity().getString(InGameBrowserActivity.Dc[InGameBrowserActivity.DG]));
            this.Ds.show();
        }
    }

    @Override // android.app.ProgressDialog, android.app.Dialog
    public void onStop() {
        super.onStop();
        this.Ds = null;
    }
}
