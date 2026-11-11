package com.moat.analytics.mobile.glft;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import com.gameloft.glads.GLURLConnection;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class i {
    private final WeakReference<WebView> e;
    private final a l;
    private int b = 0;
    private boolean c = false;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    boolean f10a = false;
    private boolean d = false;
    private final AtomicBoolean h = new AtomicBoolean(false);
    private final BroadcastReceiver m = new BroadcastReceiver() { // from class: com.moat.analytics.mobile.glft.i.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                i.this.c();
            } catch (Exception e) {
                l.j(e);
            }
            if (System.currentTimeMillis() - i.this.i > 10000) {
                i.this.e();
            }
        }
    };
    private final BroadcastReceiver n = new BroadcastReceiver() { // from class: com.moat.analytics.mobile.glft.i.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                i.this.d();
            } catch (Exception e) {
                l.j(e);
            }
        }
    };
    private com.moat.analytics.mobile.glft.a.b.a<String> j = com.moat.analytics.mobile.glft.a.b.a.a();
    private final LinkedList<String> g = new LinkedList<>();
    private final List<String> k = new ArrayList();
    private final Map<b, String> f = new WeakHashMap();
    private final long i = System.currentTimeMillis();

    enum a {
        WEBVIEW,
        NATIVE_DISPLAY,
        NATIVE_VIDEO
    }

    i(WebView webView, a aVar) {
        this.e = new WeakReference<>(webView);
        this.l = aVar;
        IntentFilter intentFilter = new IntentFilter("UPDATE_METADATA");
        IntentFilter intentFilter2 = new IntentFilter("UPDATE_VIEW_INFO");
        android.support.v4.content.i.h(g()).a(this.m, intentFilter);
        android.support.v4.content.i.h(g()).a(this.n, intentFilter2);
        try {
            n.a(3, "MoatJavaScriptBridge", this, b() ? "bridge installed" : "bridge not installed");
        } catch (Exception e) {
            l.j(e);
        }
    }

    private boolean a(WebView webView, String str) {
        if (webView == null) {
            n.a(6, "MoatJavaScriptBridge", this, "WebView is null. Can't " + str);
            return false;
        }
        if (webView.getSettings().getJavaScriptEnabled()) {
            return true;
        }
        n.a(6, "MoatJavaScriptBridge", this, "JavaScript is not enabled in the given WebView. Can't " + str);
        return false;
    }

    private boolean b() {
        if (i() != null && !a(i(), "installBridge")) {
            return false;
        }
        this.f10a = true;
        h.ajJ().a(g(), this);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        try {
            if (u.ajL().a() == u.d.OFF) {
                return;
            }
            if (!this.d) {
                n.a(3, "MoatJavaScriptBridge", this, "Ready for communication (setting environment variables).");
                this.d = true;
            }
            String str = String.format("javascript:(function(d,l){function m(){function d(a){var b=a.c,c=a.a,f=a.b;a=a.f;var g=[];if(c)b[c]&&g.push(b[c].fn[0]);else for(key in b)if(b[key])for(var e=0,h=b[key].fn.length;e<h;e++)g.push(b[key].fn[e]);e=0;for(h=g.length;e<h;e++){var k=g[e];if('function'===typeof k)try{f?k(f):k()}catch(l){}a&&delete b[c]}}function f(a,b,c){'function'===typeof a&&(b===kuea&&c[b]?c[b].fn.push(a):c[b]={ts:+new Date,fn:[a]})}kuea=+new Date;iymv={};briz=!1;ewat=+new Date;bnkr=[];bjmk={};dptk={};uqaj={};ryup={};yhgt={};this.g=function(a){this.namespace=a.namespace;this.version=a.version;this.appName=a.appName;this.deviceOS=a.deviceOS;this.isNative=a.isNative;this.versionHash=a.versionHash;this.aqzx=a.aqzx;this.appId=a.appId};this.nvsj=function(a){briz||(f(a,ewat,iymv),briz=!0)};this.bpsy=function(a,b){var c=b||kuea;c!==kuea&&bjmk[c]||f(a,c,bjmk)};this.qmrv=function(a,b){var c=b||kuea;c!==kuea&&uqaj[c]||f(a,c,uqaj)};this.lgpr=function(a,b){f(a,b||kuea,yhgt)};this.xrnk=function(a){delete yhgt[a||kuea]};this.vgft=function(a){return dptk[a||kuea]||!1};this.lkpu=function(a){return ryup[a||kuea]||!1};this.crts=function(a){var b={c:iymv,b:a,a:ewat};briz?d(b):bnkr.push(a)};this.mqjh=function(a){var b=a||kuea;dptk[b]=!0;var c={c:bjmk,f:!0};b!==kuea&&(c.b=a,c.a=a);d(c)};this.egpw=function(a){var b=a||kuea;ryup[b]=!0;var c={c:uqaj,f:!0};b!==kuea&&(c.b=a,c.a=a);d(c)};this.sglu=function(a){var b={c:yhgt,b:a.event||a,f:!1};(a.adKey||kuea)!==kuea&&(b.a=a.adKey);d(b);return 0<Object.keys(yhgt).length}}'undefined'===typeof d.MoatMAK&&(d.MoatMAK=new m,d.MoatMAK.g(l),d.__zMoatInit__=!0)})(window,%s);", k());
            if (h()) {
                i().loadUrl(str);
            }
        } catch (Exception e) {
            n.a("MoatJavaScriptBridge", this, "Failed to initialize communication (did not set environment variables).", e);
        }
    }

    private boolean c(String str) {
        if (this.f10a) {
            return true;
        }
        n.a(6, "MoatJavaScriptBridge", this, "Bridge is not installed in the given WebView. Can't " + str);
        return false;
    }

    static /* synthetic */ int d(i iVar) {
        int i = iVar.b;
        iVar.b = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        try {
            if (u.ajL().a() == u.d.OFF) {
                return;
            }
            if (this.e == null || !h() || (this.c && i().getUrl() == null)) {
                if (this.e == null) {
                    n.a(3, "MoatJavaScriptBridge", this, "WebView ref became null, stopping tracking loop");
                } else {
                    n.a(3, "MoatJavaScriptBridge", this, "WebView became null" + (i() == null ? "" : "based on null url") + ", stopping tracking loop");
                }
                f();
                return;
            }
            if (i().getUrl() != null) {
                this.c = true;
            }
            Iterator<Map.Entry<b, String>> it = this.f.entrySet().iterator();
            while (it.hasNext()) {
                b key = it.next().getKey();
                if (key == null || key.ahS() == null) {
                    n.a(3, "MoatJavaScriptBridge", this, "Tracker has no subject");
                    if (!key.c) {
                        c(key);
                    }
                }
                if (key.d) {
                    d(String.format("javascript: MoatMAK.mqjh(\"%s\")", key.ceu));
                    String str = String.format("javascript: MoatMAK.sglu(%s)", key.ahA());
                    if (Build.VERSION.SDK_INT >= 19) {
                        i().evaluateJavascript(str, new ValueCallback<String>() { // from class: com.moat.analytics.mobile.glft.i.1
                            @Override // android.webkit.ValueCallback
                            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                            public void onReceiveValue(String str2) {
                                if (str2 == null || str2.equalsIgnoreCase("null") || str2.equalsIgnoreCase("false")) {
                                    n.a(3, "MoatJavaScriptBridge", i.this, "Received value is:" + (str2 == null ? "null" : "(String)" + str2));
                                    if (i.this.b >= 50) {
                                        i.this.f();
                                    }
                                    i.d(i.this);
                                    return;
                                }
                                if (!str2.equalsIgnoreCase(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                                    n.a(3, "MoatJavaScriptBridge", i.this, "Received unusual value from Javascript:" + str2);
                                    return;
                                }
                                if (i.this.b != 0) {
                                    n.a(3, "MoatJavaScriptBridge", i.this, "Javascript has found ad");
                                    i.this.e();
                                }
                                i.this.b = 0;
                            }
                        });
                    } else {
                        i().loadUrl(str);
                    }
                }
            }
        } catch (Exception e) {
            l.j(e);
            f();
        }
    }

    private void d(b bVar) {
        n.a(3, "MoatJavaScriptBridge", this, "Stopping view update loop");
        if (bVar != null) {
            h.ajJ().a(bVar);
        }
    }

    private void d(String str) {
        if (h()) {
            i().loadUrl(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        n.a(3, "MoatJavaScriptBridge", this, "Stopping metadata reporting loop");
        h.ajJ().a(this);
        android.support.v4.content.i.h(g()).unregisterReceiver(this.m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        n.a(3, "MoatJavaScriptBridge", this, "Cleaning up");
        e();
        Iterator<Map.Entry<b, String>> it = this.f.entrySet().iterator();
        while (it.hasNext()) {
            d(it.next().getKey());
        }
        this.f.clear();
        android.support.v4.content.i.h(g()).unregisterReceiver(this.n);
    }

    private Context g() {
        return ((j) MoatAnalytics.getInstance()).clb.get();
    }

    private boolean h() {
        return i() != null;
    }

    private WebView i() {
        return this.e.get();
    }

    private String j() {
        if (this.j.c()) {
            return this.j.b();
        }
        String string = "_unknown_";
        try {
            if (h()) {
                Context context = i().getContext();
                string = context.getPackageManager().getApplicationLabel(context.getApplicationContext().getApplicationInfo()).toString();
                this.j = com.moat.analytics.mobile.glft.a.b.a.a(string);
            } else {
                n.a(3, "MoatJavaScriptBridge", this, "Can't get app name, WebView is null.");
            }
            return string;
        } catch (Exception e) {
            l.j(e);
            return string;
        }
    }

    private String k() {
        try {
            HashMap map = new HashMap();
            String strJ = j();
            String string = Integer.toString(Build.VERSION.SDK_INT);
            String strB = p.b();
            String str = this.l == a.WEBVIEW ? AppEventsConstants.EVENT_PARAM_VALUE_NO : AppEventsConstants.EVENT_PARAM_VALUE_YES;
            map.put("versionHash", "0bbafd1ca55aa33048dfa8f9ed6e5644a7625d79");
            map.put("appName", strJ);
            map.put("namespace", "GLFT");
            map.put("version", "2.1.0");
            map.put("deviceOS", string);
            map.put("isNative", str);
            if (strB != null) {
                map.put("aqzx", strB);
            }
            return new JSONObject(map).toString();
        } catch (Exception e) {
            return "{}";
        }
    }

    void a() {
        n.a(3, "MoatJavaScriptBridge", this, "webViewReady");
        this.h.compareAndSet(false, true);
        e();
        Iterator<String> it = this.k.iterator();
        while (it.hasNext()) {
            d(it.next());
        }
        this.k.clear();
    }

    void a(b bVar) {
        if (bVar != null) {
            n.a(3, "MoatJavaScriptBridge", this, "adding tracker" + bVar.ceu);
            this.f.put(bVar, "");
        }
    }

    void a(String str) {
        String str2 = String.format("javascript: MoatMAK.crts(%s)", str);
        if (this.h.get()) {
            d(str2);
        } else {
            this.k.add(str2);
        }
    }

    boolean b(b bVar) {
        try {
            if (!h() || !a(i(), "startTracking") || !c("startTracking")) {
                return false;
            }
            if (bVar.ahS() == null) {
                if (!bVar.c) {
                    n.a(3, "MoatJavaScriptBridge", this, "Tracker subject is null, won't start tracking");
                    return false;
                }
                n.a(3, "MoatJavaScriptBridge", this, "Tracker subject is null at start");
            }
            n.a(3, "MoatJavaScriptBridge", this, "Starting tracking on tracker" + bVar.ceu);
            d(String.format("javascript: MoatMAK.mqjh(\"%s\")", bVar.ceu));
            h.ajJ().a(g(), bVar);
            return true;
        } catch (Exception e) {
            n.a("MoatJavaScriptBridge", this, "Failed to initialize impression start.", e);
            return false;
        }
    }

    boolean c(b bVar) {
        boolean z = true;
        if (h() && a(i(), "stopTracking") && c("stopTracking")) {
            try {
                n.a(3, "MoatJavaScriptBridge", this, "Ending tracking on tracker" + bVar.ceu);
                d(String.format("javascript: MoatMAK.egpw(\"%s\")", bVar.ceu));
            } catch (Exception e) {
                n.a("MoatJavaScriptBridge", this, "Failed to end impression.", e);
            }
        } else {
            z = false;
        }
        if (this.l == a.NATIVE_DISPLAY) {
            d(bVar);
        } else {
            f();
        }
        this.f.remove(bVar);
        return z;
    }

    protected void finalize() throws Throwable {
        try {
            super.finalize();
            n.a(3, "MoatJavaScriptBridge", this, "finalize");
            f();
        } catch (Exception e) {
            l.j(e);
        }
    }

    void h(String str, JSONObject jSONObject) {
        String string = jSONObject.toString();
        if (this.h.get() && h()) {
            d(String.format("javascript:%s.dispatchEvent(%s);", str, string));
        } else {
            this.g.add(string);
        }
    }

    void iK(String str) {
        n.a(3, "MoatJavaScriptBridge", this, "flushDispatchQueue");
        this.h.compareAndSet(false, true);
        if (this.g.size() >= 200) {
            LinkedList linkedList = new LinkedList();
            for (int i = 0; i < 10; i++) {
                linkedList.addFirst(this.g.removeFirst());
            }
            int iMin = Math.min(Math.min(this.g.size() / GLURLConnection.HTTP_OK, 10) + GLURLConnection.HTTP_OK, this.g.size());
            for (int i2 = 0; i2 < iMin; i2++) {
                this.g.removeFirst();
            }
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                this.g.addFirst((String) it.next());
            }
        }
        int i3 = 0;
        while (!this.g.isEmpty() && i3 < 200) {
            i3++;
            StringBuilder sb = new StringBuilder();
            boolean z = true;
            while (!this.g.isEmpty() && i3 < 200) {
                int i4 = i3 + 1;
                String first = this.g.getFirst();
                if (sb.length() + first.length() > 2000) {
                    i3 = i4;
                    break;
                }
                this.g.removeFirst();
                if (z) {
                    z = false;
                } else {
                    sb.append(",");
                }
                sb.append(first);
                i3 = i4;
            }
            d(String.format("javascript:%s.dispatchMany([%s])", str, sb.toString()));
        }
        this.g.clear();
    }
}
