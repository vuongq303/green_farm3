package com.google.android.gms.internal.ads;

import android.webkit.ValueCallback;
import android.webkit.WebView;

/* JADX INFO: loaded from: classes.dex */
final class zzgm implements Runnable {
    final /* synthetic */ zzgk zzaik;
    private ValueCallback<String> zzail = new zzgn(this);
    final /* synthetic */ zzge zzaim;
    final /* synthetic */ WebView zzain;
    final /* synthetic */ boolean zzaio;

    zzgm(zzgk zzgkVar, zzge zzgeVar, WebView webView, boolean z) {
        this.zzaik = zzgkVar;
        this.zzaim = zzgeVar;
        this.zzain = webView;
        this.zzaio = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzain.getSettings().getJavaScriptEnabled()) {
            try {
                this.zzain.evaluateJavascript("(function() { return  {text:document.body.innerText}})();", this.zzail);
            } catch (Throwable th) {
                this.zzail.onReceiveValue("");
            }
        }
    }
}
