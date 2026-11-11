package com.moat.analytics.mobile.glft;

import android.view.View;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
abstract class g<PlayerOrIMAAd> extends c<PlayerOrIMAAd> {
    int EZ;
    private a l;
    private int m;
    private double n;
    private int o;
    private int p;

    public enum a {
        UNINITIALIZED,
        PAUSED,
        PLAYING,
        STOPPED,
        COMPLETED
    }

    g(String str) {
        super(str);
        this.o = Integer.MIN_VALUE;
        this.EZ = Integer.MIN_VALUE;
        this.m = Integer.MIN_VALUE;
        this.n = Double.NaN;
        this.p = 0;
        this.l = a.UNINITIALIZED;
    }

    private void k() {
        this.ckS.postDelayed(new Runnable() { // from class: com.moat.analytics.mobile.glft.g.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (g.this.ckU.get() == null || g.this.f() || !Boolean.valueOf(g.this.ajI()).booleanValue()) {
                        g.this.e();
                    } else {
                        g.this.ckS.postDelayed(this, 200L);
                    }
                } catch (Exception e) {
                    g.this.e();
                    l.j(e);
                }
            }
        }, 200L);
    }

    @Override // com.moat.analytics.mobile.glft.c
    protected JSONObject a(MoatAdEvent moatAdEvent) {
        Integer numValueOf;
        if (moatAdEvent.ckK.equals(MoatAdEvent.ckJ)) {
            try {
                numValueOf = g();
            } catch (Exception e) {
                numValueOf = Integer.valueOf(this.m);
            }
            moatAdEvent.ckK = numValueOf;
        } else {
            numValueOf = moatAdEvent.ckK;
        }
        if (moatAdEvent.ckK.intValue() < 0 || (moatAdEvent.ckK.intValue() == 0 && moatAdEvent.ckM == MoatAdEventType.AD_EVT_COMPLETE && this.m > 0)) {
            numValueOf = Integer.valueOf(this.m);
            moatAdEvent.ckK = numValueOf;
        }
        if (moatAdEvent.ckM == MoatAdEventType.AD_EVT_COMPLETE) {
            if (numValueOf.intValue() == Integer.MIN_VALUE || this.EZ == Integer.MIN_VALUE || !a(numValueOf, Integer.valueOf(this.EZ))) {
                this.l = a.STOPPED;
                moatAdEvent.ckM = MoatAdEventType.AD_EVT_STOPPED;
            } else {
                this.l = a.COMPLETED;
            }
        }
        return super.a(moatAdEvent);
    }

    @Override // com.moat.analytics.mobile.glft.c
    public boolean a(Map<String, String> map, PlayerOrIMAAd playerorimaad, View view) {
        try {
            k();
        } catch (Exception e) {
            l.j(e);
        }
        return super.a(map, playerorimaad, view);
    }

    /* JADX WARN: Code duplicated, block: B:58:0x00e5  */
    boolean ajI() {
        MoatAdEventType moatAdEventType;
        boolean z = false;
        if (this.ckU.get() == null || f()) {
            return false;
        }
        try {
            int iIntValue = g().intValue();
            if (this.m >= 0 && iIntValue < 0) {
                return false;
            }
            this.m = iIntValue;
            if (iIntValue == 0) {
                return true;
            }
            int iIntValue2 = i().intValue();
            boolean zH = h();
            double d = ((double) iIntValue2) / 4.0d;
            double dAii = p.aii();
            MoatAdEventType moatAdEventType2 = null;
            if (iIntValue > this.o) {
                this.o = iIntValue;
            }
            if (this.EZ == Integer.MIN_VALUE) {
                this.EZ = iIntValue2;
            }
            if (zH) {
                if (this.l == a.UNINITIALIZED) {
                    moatAdEventType2 = MoatAdEventType.AD_EVT_START;
                    this.l = a.PLAYING;
                } else if (this.l == a.PAUSED) {
                    moatAdEventType2 = MoatAdEventType.AD_EVT_PLAYING;
                    this.l = a.PLAYING;
                } else {
                    int iFloor = ((int) Math.floor(((double) iIntValue) / d)) - 1;
                    if (iFloor <= -1 || iFloor >= 3) {
                        moatAdEventType = null;
                    } else {
                        moatAdEventType = ckR[iFloor];
                        if (this.f.containsKey(moatAdEventType)) {
                            moatAdEventType = null;
                        } else {
                            this.f.put(moatAdEventType, 1);
                        }
                    }
                    moatAdEventType2 = moatAdEventType;
                }
            } else if (this.l != a.PAUSED) {
                moatAdEventType2 = MoatAdEventType.AD_EVT_PAUSED;
                this.l = a.PAUSED;
            }
            boolean z2 = moatAdEventType2 != null;
            if (!z2 && !Double.isNaN(this.n) && Math.abs(this.n - dAii) > 0.05d) {
                moatAdEventType2 = MoatAdEventType.AD_EVT_VOLUME_CHANGE;
                z2 = true;
            }
            if (z2) {
                dispatchEvent(new MoatAdEvent(moatAdEventType2, Integer.valueOf(iIntValue), Double.valueOf(dAii)));
            }
            this.n = dAii;
            this.p = 0;
            z = true;
            return true;
        } catch (Exception e) {
            int i = this.p;
            this.p = i + 1;
            return i < 5 ? true : z;
        }
    }

    protected abstract Integer g();

    protected abstract boolean h();

    protected abstract Integer i();

    @Override // com.moat.analytics.mobile.glft.c, com.moat.analytics.mobile.glft.b
    public void stopTracking() {
        try {
            dispatchEvent(new MoatAdEvent(MoatAdEventType.AD_EVT_COMPLETE));
        } catch (Exception e) {
            l.j(e);
        }
    }
}
