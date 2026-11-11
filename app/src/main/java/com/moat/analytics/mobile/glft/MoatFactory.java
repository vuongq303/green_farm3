package com.moat.analytics.mobile.glft;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class MoatFactory {
    public static MoatFactory create() {
        try {
            return new m();
        } catch (Exception e) {
            l.j(e);
            return new t.b();
        }
    }

    public abstract <T> T createCustomTracker(MoatPlugin<T> moatPlugin);

    public abstract NativeDisplayTracker createNativeDisplayTracker(View view, Map<String, String> map);

    public abstract NativeVideoTracker createNativeVideoTracker(String str);

    public abstract WebAdTracker createWebAdTracker(ViewGroup viewGroup);

    public abstract WebAdTracker createWebAdTracker(WebView webView);
}
