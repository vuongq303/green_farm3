package com.quanhoang.greenfarm3;

import android.app.AlertDialog;
import android.webkit.WebView;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class at implements Runnable {
    final /* synthetic */ WebView Dq;
    final /* synthetic */ as EB;

    at(as asVar, WebView webView) {
        this.EB = asVar;
        this.Dq = webView;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.Dq.setVisibility(4);
        try {
            AlertDialog alertDialogCreate = new AlertDialog.Builder(this.EB.Ex).setPositiveButton(this.EB.Ex.getString(this.EB.Ex.Db[InGameBrowserActivity.DG]), new av(this)).setOnCancelListener(new au(this)).setMessage(this.EB.Ex.getString(this.EB.Ex.Da[InGameBrowserActivity.DG])).create();
            alertDialogCreate.setCanceledOnTouchOutside(false);
            alertDialogCreate.show();
        } catch (Exception e) {
        }
    }
}
