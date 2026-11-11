package com.gameloft.android.ANMP.GloftGF2F;

import android.webkit.WebView;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class ad implements Runnable {
    final /* synthetic */ y Dn;
    final /* synthetic */ WebView Dq;

    ad(y yVar, WebView webView) {
        this.Dn = yVar;
        this.Dq = webView;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.Dq.setVisibility(4);
        try {
            this.Dn.Dj.gI();
            javax.microedition.lcdui.a aVar = new javax.microedition.lcdui.a(this.Dn.Dj.getContext());
            aVar.setButton(-1, com.gameloft.android.wrapper.au.getActivity().getString(IGPFreemiumActivity.Db[IGPFreemiumActivity.CJ]), new ae(this));
            aVar.setOnCancelListener(new af(this));
            aVar.setMessage(com.gameloft.android.wrapper.au.getActivity().getString(IGPFreemiumActivity.Da[IGPFreemiumActivity.CJ]));
            aVar.setCanceledOnTouchOutside(false);
            aVar.show();
        } catch (Exception e) {
        }
    }
}
