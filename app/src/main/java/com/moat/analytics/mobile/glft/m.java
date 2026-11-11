package com.moat.analytics.mobile.glft;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import java.lang.ref.WeakReference;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class m extends MoatFactory {
    m() throws l {
        if (a()) {
            return;
        }
        n.a(3, "MoatFactory", this, "Failed to initialize MoatFactory. Please check that you've initialized the Moat SDK correctly.");
        throw new l();
    }

    private NativeDisplayTracker a(View view, final Map<String, String> map) {
        com.moat.analytics.mobile.glft.a.a.a.a(view);
        com.moat.analytics.mobile.glft.a.a.a.a(map);
        final WeakReference weakReference = new WeakReference(view);
        return (NativeDisplayTracker) v.a(new v.a<NativeDisplayTracker>() { // from class: com.moat.analytics.mobile.glft.m.3
            @Override // com.moat.analytics.mobile.glft.v.a
            public com.moat.analytics.mobile.glft.a.b.a<NativeDisplayTracker> a() {
                View view2 = (View) weakReference.get();
                if (view2 == null) {
                    n.a(3, "MoatFactory", this, "Target view is null. Not creating NativeDisplayTracker.");
                    return com.moat.analytics.mobile.glft.a.b.a.a();
                }
                if (map == null || map.isEmpty()) {
                    n.a(3, "MoatFactory", this, "adIds is null or empty. NativeDisplayTracker initialization failed.");
                    return com.moat.analytics.mobile.glft.a.b.a.a();
                }
                n.a(3, "MoatFactory", this, "Creating NativeDisplayTracker for " + view2.getClass().getSimpleName() + "@" + view2.hashCode());
                return com.moat.analytics.mobile.glft.a.b.a.a(new q(view2, map));
            }
        }, new q.a());
    }

    private NativeVideoTracker a(final String str) {
        return (NativeVideoTracker) v.a(new v.a<NativeVideoTracker>() { // from class: com.moat.analytics.mobile.glft.m.4
            @Override // com.moat.analytics.mobile.glft.v.a
            public com.moat.analytics.mobile.glft.a.b.a<NativeVideoTracker> a() {
                if (str == null || str.isEmpty()) {
                    n.a(3, "MoatFactory", this, "partnerCode is null or empty. NativeVideoTracker initialization failed.");
                    return com.moat.analytics.mobile.glft.a.b.a.a();
                }
                n.a(3, "MoatFactory", this, "Creating NativeVideo tracker.");
                return com.moat.analytics.mobile.glft.a.b.a.a(new s(str));
            }
        }, new s.a());
    }

    private WebAdTracker a(ViewGroup viewGroup) {
        com.moat.analytics.mobile.glft.a.a.a.a(viewGroup);
        final WeakReference weakReference = new WeakReference(viewGroup);
        return (WebAdTracker) v.a(new v.a<WebAdTracker>() { // from class: com.moat.analytics.mobile.glft.m.2
            @Override // com.moat.analytics.mobile.glft.v.a
            public com.moat.analytics.mobile.glft.a.b.a<WebAdTracker> a() {
                ViewGroup viewGroup2 = (ViewGroup) weakReference.get();
                if (viewGroup2 == null) {
                    n.a(3, "MoatFactory", this, "Target ViewGroup is null. Not creating WebAdTracker.");
                    return com.moat.analytics.mobile.glft.a.b.a.a();
                }
                n.a(3, "MoatFactory", this, "Creating WebAdTracker for " + viewGroup2.getClass().getSimpleName() + "@" + viewGroup2.hashCode());
                com.moat.analytics.mobile.glft.a.b.a<WebView> aVarA = x.a(viewGroup2);
                n.a(3, "MoatFactory", this, "WebView " + (aVarA.c() ? "" : "not ") + "found inside of ad container.");
                return com.moat.analytics.mobile.glft.a.b.a.a(new w(aVarA.c(null)));
            }
        }, new w.a());
    }

    private WebAdTracker a(WebView webView) {
        com.moat.analytics.mobile.glft.a.a.a.a(webView);
        final WeakReference weakReference = new WeakReference(webView);
        return (WebAdTracker) v.a(new v.a<WebAdTracker>() { // from class: com.moat.analytics.mobile.glft.m.1
            @Override // com.moat.analytics.mobile.glft.v.a
            public com.moat.analytics.mobile.glft.a.b.a<WebAdTracker> a() {
                WebView webView2 = (WebView) weakReference.get();
                if (webView2 == null) {
                    n.a(3, "MoatFactory", this, "Target ViewGroup is null. Not creating WebAdTracker.");
                    return com.moat.analytics.mobile.glft.a.b.a.a();
                }
                n.a(3, "MoatFactory", this, "Creating WebAdTracker for " + webView2.getClass().getSimpleName() + "@" + webView2.hashCode());
                return com.moat.analytics.mobile.glft.a.b.a.a(new w(webView2));
            }
        }, new w.a());
    }

    private <T> T a(MoatPlugin<T> moatPlugin) {
        return moatPlugin.a();
    }

    private boolean a() {
        return ((j) j.getInstance()).a();
    }

    @Override // com.moat.analytics.mobile.glft.MoatFactory
    public <T> T createCustomTracker(MoatPlugin<T> moatPlugin) {
        try {
            return (T) a(moatPlugin);
        } catch (Exception e) {
            l.j(e);
            return moatPlugin.b();
        }
    }

    @Override // com.moat.analytics.mobile.glft.MoatFactory
    public NativeDisplayTracker createNativeDisplayTracker(View view, Map<String, String> map) {
        try {
            return a(view, map);
        } catch (Exception e) {
            l.j(e);
            return new t.c();
        }
    }

    @Override // com.moat.analytics.mobile.glft.MoatFactory
    public NativeVideoTracker createNativeVideoTracker(String str) {
        try {
            return a(str);
        } catch (Exception e) {
            l.j(e);
            return new t.d();
        }
    }

    @Override // com.moat.analytics.mobile.glft.MoatFactory
    public WebAdTracker createWebAdTracker(ViewGroup viewGroup) {
        try {
            return a(viewGroup);
        } catch (Exception e) {
            l.j(e);
            return new t.f();
        }
    }

    @Override // com.moat.analytics.mobile.glft.MoatFactory
    public WebAdTracker createWebAdTracker(WebView webView) {
        try {
            return a(webView);
        } catch (Exception e) {
            l.j(e);
            return new t.f();
        }
    }
}
