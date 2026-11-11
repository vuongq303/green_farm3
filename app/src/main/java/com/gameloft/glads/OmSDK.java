package com.gameloft.glads;

import android.content.Context;
import android.webkit.WebView;
import com.a.a.a.a.a;
import com.a.a.a.a.b.b;
import com.a.a.a.a.b.c;
import com.a.a.a.a.b.d;
import com.a.a.a.a.b.f;
import com.a.a.a.a.b.g;

/* JADX INFO: loaded from: classes.dex */
public class OmSDK {
    static boolean analyticsStarted = false;
    static g s_partner = null;
    boolean isVideo;
    long parent;
    d sessionContext = null;
    c sessionConfig = null;
    b adSession = null;

    public static native String NativeGetLibraryVersion();

    public static native void NativeOnSDKActivated(boolean z);

    public OmSDK(long j) {
        this.parent = j;
    }

    public static void StartAnalytics() {
        if (!analyticsStarted) {
            analyticsStarted = true;
            try {
                String version = a.getVersion();
                Context contextGetContext = AndroidUtils.GetContext();
                boolean zB = false;
                if (version != null && contextGetContext != null) {
                    zB = a.b(version, contextGetContext);
                }
                NativeOnSDKActivated(zB);
                if (zB) {
                    s_partner = g.aB("Gameloft", NativeGetLibraryVersion());
                }
            } catch (Exception e) {
            }
        }
    }

    public void Init(boolean z) {
        this.isVideo = z;
    }

    public void OnCreateWebView(final Object obj) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.OmSDK.1
            @Override // java.lang.Runnable
            public void run() {
                WebView webViewGetWebView = ((AndroidWebView) obj).GetWebView();
                try {
                    OmSDK.this.sessionContext = d.a(OmSDK.s_partner, webViewGetWebView, "");
                    if (OmSDK.this.isVideo) {
                        OmSDK.this.sessionConfig = c.a(f.JAVASCRIPT, f.JAVASCRIPT, false);
                    } else {
                        OmSDK.this.sessionConfig = c.a(f.NATIVE, null, false);
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    public void OnStartTracking(final Object obj) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.OmSDK.2
            @Override // java.lang.Runnable
            public void run() {
                OmSDK.this.HandleStopTracking();
                try {
                    WebView webViewGetWebView = ((AndroidWebView) obj).GetWebView();
                    OmSDK.this.adSession = b.a(OmSDK.this.sessionConfig, OmSDK.this.sessionContext);
                    OmSDK.this.adSession.R(webViewGetWebView);
                    OmSDK.this.adSession.start();
                    if (!OmSDK.this.isVideo) {
                        com.a.a.a.a.b.a.a(OmSDK.this.adSession).ahD();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    public void StopTracking() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.OmSDK.3
            @Override // java.lang.Runnable
            public void run() {
                OmSDK.this.HandleStopTracking();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void HandleStopTracking() {
        if (this.adSession != null) {
            this.adSession.finish();
            this.adSession = null;
        }
    }
}
