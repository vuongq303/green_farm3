package com.moat.analytics.mobile.glft;

import android.app.Activity;
import android.app.Application;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.gameloft.glads.GLURLConnection;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class t {

    public class a extends MoatAnalytics {
        @Override // com.moat.analytics.mobile.glft.MoatAnalytics
        public void prepareNativeDisplayTracking(String str) {
        }

        @Override // com.moat.analytics.mobile.glft.MoatAnalytics
        public void start(Application application) {
        }

        @Override // com.moat.analytics.mobile.glft.MoatAnalytics
        public void start(MoatOptions moatOptions, Application application) {
        }
    }

    public class b extends MoatFactory {
        @Override // com.moat.analytics.mobile.glft.MoatFactory
        public <T> T createCustomTracker(MoatPlugin<T> moatPlugin) {
            return null;
        }

        @Override // com.moat.analytics.mobile.glft.MoatFactory
        public NativeDisplayTracker createNativeDisplayTracker(View view, Map<String, String> map) {
            return new c();
        }

        @Override // com.moat.analytics.mobile.glft.MoatFactory
        public NativeVideoTracker createNativeVideoTracker(String str) {
            return new d();
        }

        @Override // com.moat.analytics.mobile.glft.MoatFactory
        public WebAdTracker createWebAdTracker(ViewGroup viewGroup) {
            return new f();
        }

        @Override // com.moat.analytics.mobile.glft.MoatFactory
        public WebAdTracker createWebAdTracker(WebView webView) {
            return new f();
        }
    }

    class c implements NativeDisplayTracker {
        c() {
        }

        @Override // com.moat.analytics.mobile.glft.NativeDisplayTracker
        public void setActivity(Activity activity) {
        }

        @Override // com.moat.analytics.mobile.glft.NativeDisplayTracker
        public void startTracking() {
        }

        @Override // com.moat.analytics.mobile.glft.NativeDisplayTracker
        public void stopTracking() {
        }
    }

    class d implements NativeVideoTracker {
        d() {
        }

        @Override // com.moat.analytics.mobile.glft.NativeVideoTracker
        public void changeTargetView(View view) {
        }

        @Override // com.moat.analytics.mobile.glft.NativeVideoTracker
        public void dispatchEvent(MoatAdEvent moatAdEvent) {
        }

        @Override // com.moat.analytics.mobile.glft.NativeVideoTracker
        public void setActivity(Activity activity) {
        }

        @Override // com.moat.analytics.mobile.glft.NativeVideoTracker
        public void setPlayerVolume(Double d) {
        }

        @Override // com.moat.analytics.mobile.glft.NativeVideoTracker
        public void stopTracking() {
        }

        @Override // com.moat.analytics.mobile.glft.NativeVideoTracker
        public boolean trackVideoAd(Map<String, String> map, MediaPlayer mediaPlayer, View view) {
            return false;
        }
    }

    class e extends u {
        e() {
        }

        @Override // com.moat.analytics.mobile.glft.u
        public u.d a() {
            return u.d.OFF;
        }

        @Override // com.moat.analytics.mobile.glft.u
        public void a(u.b bVar) {
        }

        @Override // com.moat.analytics.mobile.glft.u
        public boolean b() {
            return false;
        }

        @Override // com.moat.analytics.mobile.glft.u
        public int c() {
            return GLURLConnection.HTTP_OK;
        }
    }

    class f implements WebAdTracker {
        f() {
        }

        @Override // com.moat.analytics.mobile.glft.WebAdTracker
        public void setActivity(Activity activity) {
        }

        @Override // com.moat.analytics.mobile.glft.WebAdTracker
        public void startTracking() {
        }

        @Override // com.moat.analytics.mobile.glft.WebAdTracker
        public void stopTracking() {
        }
    }

    t() {
    }
}
