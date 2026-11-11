package com.gameloft.glads;

import android.os.Build;
import android.webkit.WebView;
import com.moat.analytics.mobile.glft.MoatAnalytics;
import com.moat.analytics.mobile.glft.MoatFactory;
import com.moat.analytics.mobile.glft.WebAdTracker;

/* JADX INFO: loaded from: classes.dex */
public class MoatSDK {
    long parent;
    WebAdTracker tracker = null;

    public MoatSDK(long j) {
        this.parent = j;
    }

    public static void StartAnalytics() {
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                MoatAnalytics.getInstance().start(AndroidUtils.GetActivity().getApplication());
            } catch (Exception e) {
            }
        }
    }

    public void OnStartTracking(final Object obj) {
        if (Build.VERSION.SDK_INT >= 16) {
            AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.MoatSDK.1
                @Override // java.lang.Runnable
                public void run() {
                    MoatSDK.this.HandleStopTracking();
                    WebView webViewGetWebView = ((AndroidWebView) obj).GetWebView();
                    try {
                        MoatSDK.this.tracker = MoatFactory.create().createWebAdTracker(webViewGetWebView);
                        MoatSDK.this.tracker.setActivity(AndroidUtils.GetActivity());
                        MoatSDK.this.tracker.startTracking();
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    public void StopTracking() {
        if (Build.VERSION.SDK_INT >= 16) {
            AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.MoatSDK.2
                @Override // java.lang.Runnable
                public void run() {
                    MoatSDK.this.HandleStopTracking();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void HandleStopTracking() {
        if (this.tracker != null) {
            try {
                this.tracker.stopTracking();
            } catch (Exception e) {
            }
            this.tracker = null;
        }
    }
}
