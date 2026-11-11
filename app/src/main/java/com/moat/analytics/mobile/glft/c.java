package com.moat.analytics.mobile.glft;

import android.os.Handler;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
abstract class c<PlayerOrIMAAd> extends b {
    static final MoatAdEventType[] ckR = {MoatAdEventType.AD_EVT_FIRST_QUARTILE, MoatAdEventType.AD_EVT_MID_POINT, MoatAdEventType.AD_EVT_THIRD_QUARTILE};
    final Handler ckS;
    Map<String, String> ckT;
    WeakReference<PlayerOrIMAAd> ckU;
    WeakReference<View> ckV;
    final Map<MoatAdEventType, Integer> f;
    private boolean k;
    private Double l;
    private final f m;
    private final String n;

    c(String str) {
        super(null, false, true);
        n.a(3, "MoatBaseVideoTracker", this, "Initializing.");
        this.n = str;
        this.m = new f(a.ajG(), f.a.VIDEO);
        this.m.a(str);
        super.a(this.m.ckX);
        super.b(this.m.cet);
        this.f = new HashMap();
        this.ckS = new Handler();
        this.k = false;
        this.l = Double.valueOf(1.0d);
    }

    private void b(MoatAdEvent moatAdEvent) {
        JSONObject jSONObjectA = a(moatAdEvent);
        n.a(3, "MoatBaseVideoTracker", this, String.format("Received event: %s", jSONObjectA.toString()));
        this.ckO.h(this.m.c, jSONObjectA);
        MoatAdEventType moatAdEventType = moatAdEvent.ckM;
        if (moatAdEventType == MoatAdEventType.AD_EVT_COMPLETE || moatAdEventType == MoatAdEventType.AD_EVT_STOPPED || moatAdEventType == MoatAdEventType.AD_EVT_SKIPPED) {
            this.f.put(moatAdEventType, 1);
            e();
        }
    }

    private void g() {
        Map<String, Object> mapD = d();
        Integer num = (Integer) mapD.get("width");
        Integer num2 = (Integer) mapD.get("height");
        Integer num3 = (Integer) mapD.get("duration");
        n.a(3, "MoatBaseVideoTracker", this, String.format(Locale.ROOT, "Player metadata: height = %d, width = %d, duration = %d", num2, num, num3));
        this.m.a(this.n, this.ckT, num, num2, num3);
        super.changeTargetView(this.ckV.get());
        super.startTracking();
    }

    JSONObject a(MoatAdEvent moatAdEvent) {
        if (Double.isNaN(moatAdEvent.ckL.doubleValue())) {
            try {
                moatAdEvent.ckL = Double.valueOf(p.aii());
            } catch (Exception e) {
                moatAdEvent.ckL = Double.valueOf(1.0d);
            }
        }
        n.a(3, "MoatBaseVideoTracker", this, String.format(Locale.ROOT, "adVolume before adjusting for player volume %f", moatAdEvent.ckL));
        moatAdEvent.ckL = Double.valueOf(moatAdEvent.ckL.doubleValue() * this.l.doubleValue());
        n.a(3, "MoatBaseVideoTracker", this, String.format(Locale.ROOT, "adVolume after adjusting for player volume %f", moatAdEvent.ckL));
        return new JSONObject(moatAdEvent.ajF());
    }

    boolean a(Integer num, Integer num2) {
        return ((double) Math.abs(num2.intValue() - num.intValue())) <= Math.min(750.0d, ((double) num2.intValue()) * 0.05d);
    }

    public boolean a(Map<String, String> map, PlayerOrIMAAd playerorimaad, View view) {
        boolean z = true;
        boolean z2 = false;
        if (map == null) {
            try {
                n.a(3, "MoatBaseVideoTracker", this, "trackVideoAd received null adIds object. Not tracking.");
                z = false;
            } catch (Exception e) {
                l.j(e);
            }
        }
        if (view == null) {
            n.a(3, "MoatBaseVideoTracker", this, "trackVideoAd received null video view instance");
        }
        if (playerorimaad == null) {
            n.a(3, "MoatBaseVideoTracker", this, "trackVideoAd received null ad instance. Not tracking.");
            z = false;
        }
        if (z) {
            Object[] objArr = new Object[3];
            objArr[0] = new JSONObject(map).toString();
            objArr[1] = playerorimaad.toString();
            objArr[2] = view != null ? view.getClass().getSimpleName() + "@" + view.hashCode() : "null";
            n.a(3, "MoatBaseVideoTracker", this, String.format("trackVideoAd tracking ids: %s | ad: %s | view: %s", objArr));
            this.ckT = map;
            this.ckU = new WeakReference<>(playerorimaad);
            this.ckV = new WeakReference<>(view);
            g();
        }
        z2 = z;
        n.a(3, "MoatBaseVideoTracker", this, "Attempt to start tracking ad was " + (z2 ? "" : "un") + "successful.");
        return z2;
    }

    @Override // com.moat.analytics.mobile.glft.b
    public void changeTargetView(View view) {
        n.a(3, "MoatBaseVideoTracker", this, "changing view to " + (view != null ? view.getClass().getSimpleName() + "@" + view.hashCode() : "null"));
        this.ckV = new WeakReference<>(view);
        try {
            super.changeTargetView(view);
        } catch (Exception e) {
            l.j(e);
        }
    }

    protected abstract Map<String, Object> d();

    public void dispatchEvent(MoatAdEvent moatAdEvent) {
        try {
            b(moatAdEvent);
        } catch (Exception e) {
            l.j(e);
        }
    }

    void e() {
        if (this.k) {
            return;
        }
        this.ckS.postDelayed(new Runnable() { // from class: com.moat.analytics.mobile.glft.c.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    n.a(3, "MoatBaseVideoTracker", this, "Shutting down.");
                    c.this.m.a();
                } catch (Exception e) {
                    l.j(e);
                }
            }
        }, 500L);
        this.k = true;
    }

    boolean f() {
        return this.f.containsKey(MoatAdEventType.AD_EVT_COMPLETE) || this.f.containsKey(MoatAdEventType.AD_EVT_STOPPED) || this.f.containsKey(MoatAdEventType.AD_EVT_SKIPPED);
    }

    public void setPlayerVolume(Double d) {
        if (d.equals(this.l)) {
            return;
        }
        n.a(3, "MoatBaseVideoTracker", this, String.format(Locale.ROOT, "player volume changed to %f ", d));
        this.l = d;
        dispatchEvent(new MoatAdEvent(MoatAdEventType.AD_EVT_VOLUME_CHANGE, MoatAdEvent.ckJ));
    }

    @Override // com.moat.analytics.mobile.glft.b
    public void stopTracking() {
        try {
            super.stopTracking();
            e();
        } catch (Exception e) {
            l.j(e);
        }
    }
}
