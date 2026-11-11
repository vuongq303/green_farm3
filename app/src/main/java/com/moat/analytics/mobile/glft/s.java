package com.moat.analytics.mobile.glft;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class s extends g<MediaPlayer> implements NativeVideoTracker {

    class a implements v.c<NativeVideoTracker> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> f16a;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> b;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> c;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> d;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> e;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> f;

        static {
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA2 = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA3 = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA4 = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA5 = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA6 = com.moat.analytics.mobile.glft.a.b.a.a();
            try {
                Method method = NativeVideoTracker.class.getMethod("setActivity", Activity.class);
                Method method2 = NativeVideoTracker.class.getMethod("trackVideoAd", Map.class, MediaPlayer.class, View.class);
                Method method3 = NativeVideoTracker.class.getMethod("setPlayerVolume", Double.class);
                Method method4 = NativeVideoTracker.class.getMethod("changeTargetView", View.class);
                Method method5 = NativeVideoTracker.class.getMethod("dispatchEvent", MoatAdEvent.class);
                Method method6 = NativeVideoTracker.class.getMethod("stopTracking", new Class[0]);
                aVarA = com.moat.analytics.mobile.glft.a.b.a.a(method);
                aVarA2 = com.moat.analytics.mobile.glft.a.b.a.a(method2);
                aVarA3 = com.moat.analytics.mobile.glft.a.b.a.a(method3);
                aVarA4 = com.moat.analytics.mobile.glft.a.b.a.a(method4);
                aVarA5 = com.moat.analytics.mobile.glft.a.b.a.a(method5);
                aVarA6 = com.moat.analytics.mobile.glft.a.b.a.a(method6);
            } catch (NoSuchMethodException e2) {
                l.j(e2);
            }
            f16a = aVarA;
            b = aVarA2;
            c = aVarA3;
            d = aVarA4;
            e = aVarA5;
            f = aVarA6;
        }

        a() {
        }

        @Override // com.moat.analytics.mobile.glft.v.c
        public Class<NativeVideoTracker> a() {
            return NativeVideoTracker.class;
        }
    }

    s(String str) {
        super(str);
    }

    @Override // com.moat.analytics.mobile.glft.c
    protected Map<String, Object> d() {
        MediaPlayer mediaPlayer = (MediaPlayer) this.ckU.get();
        HashMap map = new HashMap();
        map.put("width", Integer.valueOf(mediaPlayer.getVideoWidth()));
        map.put("height", Integer.valueOf(mediaPlayer.getVideoHeight()));
        map.put("duration", Integer.valueOf(mediaPlayer.getDuration()));
        return map;
    }

    @Override // com.moat.analytics.mobile.glft.g
    protected Integer g() {
        return Integer.valueOf(((MediaPlayer) this.ckU.get()).getCurrentPosition());
    }

    @Override // com.moat.analytics.mobile.glft.g
    protected boolean h() {
        return ((MediaPlayer) this.ckU.get()).isPlaying();
    }

    @Override // com.moat.analytics.mobile.glft.g
    protected Integer i() {
        return Integer.valueOf(((MediaPlayer) this.ckU.get()).getDuration());
    }

    @Override // com.moat.analytics.mobile.glft.g, com.moat.analytics.mobile.glft.c
    /* JADX INFO: renamed from: trackVideoAd, reason: merged with bridge method [inline-methods] */
    public boolean a(Map<String, String> map, MediaPlayer mediaPlayer, View view) {
        if (mediaPlayer == null) {
            n.a(3, "MoatNativeVideoTracker", this, "Null player instance. Not tracking.");
            return false;
        }
        try {
            mediaPlayer.getCurrentPosition();
            return super.a(map, mediaPlayer, view);
        } catch (Exception e) {
            n.a(3, "MoatNativeVideoTracker", this, "Playback has already completed. Not tracking.");
            return false;
        }
    }
}
