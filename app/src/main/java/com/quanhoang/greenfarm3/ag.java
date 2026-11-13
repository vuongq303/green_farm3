package com.quanhoang.greenfarm3;

import android.content.Context;
import android.widget.Toast;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class ag extends javax.microedition.lcdui.ap {
    final /* synthetic */ IGPFreemiumActivity Dj;
    Toast Ds;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ag(IGPFreemiumActivity iGPFreemiumActivity, Context context) {
        super(context);
        this.Dj = iGPFreemiumActivity;
        this.Ds = null;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        this.Ds = Toast.makeText(com.gameloft.android.wrapper.au.getContext(), com.gameloft.android.wrapper.au.getActivity().getString(IGPFreemiumActivity.Dc[IGPFreemiumActivity.CJ]), 1);
        this.Ds.setText(com.gameloft.android.wrapper.au.getActivity().getString(IGPFreemiumActivity.Dc[IGPFreemiumActivity.CJ]));
        this.Ds.show();
    }

    @Override // android.app.ProgressDialog, android.app.Dialog
    public void onStop() {
        super.onStop();
        this.Ds = null;
    }
}
