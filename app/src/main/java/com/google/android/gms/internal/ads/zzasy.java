package com.google.android.gms.internal.ads;

import android.webkit.WebView;
import com.google.android.gms.common.d.s;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class zzasy {

    @GuardedBy("InvokeJavascriptWorkaround.class")
    private static Boolean zzdfk;

    private zzasy() {
    }

    static void zza(WebView webView, String str) {
        if (s.abA() && zzb(webView)) {
            webView.evaluateJavascript(str, null);
        } else {
            String strValueOf = String.valueOf(str);
            webView.loadUrl(strValueOf.length() != 0 ? "javascript:".concat(strValueOf) : new String("javascript:"));
        }
    }

    private static boolean zzb(WebView webView) {
        boolean zBooleanValue;
        synchronized (zzasy.class) {
            if (zzdfk == null) {
                try {
                    webView.evaluateJavascript("(function(){})()", null);
                    zzdfk = true;
                } catch (IllegalStateException e) {
                    zzdfk = false;
                }
                zBooleanValue = zzdfk.booleanValue();
            } else {
                zBooleanValue = zzdfk.booleanValue();
            }
            throw th;
        }
        return zBooleanValue;
    }
}
