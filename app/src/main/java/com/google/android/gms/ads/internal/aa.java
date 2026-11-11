package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;

/* JADX INFO: loaded from: classes.dex */
final class aa extends WebViewClient {
    private final /* synthetic */ zzbp bCi;

    aa(zzbp zzbpVar) {
        this.bCi = zzbpVar;
    }

    @Override // android.webkit.WebViewClient
    public final void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        if (this.bCi.zzxs != null) {
            try {
                this.bCi.zzxs.onAdFailedToLoad(0);
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (str.startsWith(this.bCi.zzeb())) {
            return false;
        }
        if (str.startsWith((String) zzkb.zzik().zzd(zznk.zzbcw))) {
            if (this.bCi.zzxs != null) {
                try {
                    this.bCi.zzxs.onAdFailedToLoad(3);
                } catch (RemoteException e) {
                    zzakb.zzd("#007 Could not call remote method.", e);
                }
            }
            this.bCi.ne(0);
            return true;
        }
        if (str.startsWith((String) zzkb.zzik().zzd(zznk.zzbcx))) {
            if (this.bCi.zzxs != null) {
                try {
                    this.bCi.zzxs.onAdFailedToLoad(0);
                } catch (RemoteException e2) {
                    zzakb.zzd("#007 Could not call remote method.", e2);
                }
            }
            this.bCi.ne(0);
            return true;
        }
        if (str.startsWith((String) zzkb.zzik().zzd(zznk.zzbcy))) {
            if (this.bCi.zzxs != null) {
                try {
                    this.bCi.zzxs.onAdLoaded();
                } catch (RemoteException e3) {
                    zzakb.zzd("#007 Could not call remote method.", e3);
                }
            }
            this.bCi.ne(this.bCi.hF(str));
            return true;
        }
        if (str.startsWith("gmsg://")) {
            return true;
        }
        if (this.bCi.zzxs != null) {
            try {
                this.bCi.zzxs.onAdLeftApplication();
            } catch (RemoteException e4) {
                zzakb.zzd("#007 Could not call remote method.", e4);
            }
        }
        this.bCi.hH(this.bCi.hG(str));
        return true;
    }
}
