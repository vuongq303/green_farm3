package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzoo extends zzql implements zzpc {
    private Bundle mExtras;
    private Object mLock = new Object();
    private String zzbhw;
    private List<zzon> zzbhx;
    private String zzbhy;
    private zzpw zzbhz;
    private String zzbia;
    private double zzbib;
    private String zzbic;
    private String zzbid;
    private zzoj zzbie;
    private zzlo zzbif;
    private View zzbig;
    private a zzbih;
    private String zzbii;
    private zzoz zzbij;

    public zzoo(String str, List<zzon> list, String str2, zzpw zzpwVar, String str3, double d, String str4, String str5, zzoj zzojVar, Bundle bundle, zzlo zzloVar, View view, a aVar, String str6) {
        this.zzbhw = str;
        this.zzbhx = list;
        this.zzbhy = str2;
        this.zzbhz = zzpwVar;
        this.zzbia = str3;
        this.zzbib = d;
        this.zzbic = str4;
        this.zzbid = str5;
        this.zzbie = zzojVar;
        this.mExtras = bundle;
        this.zzbif = zzloVar;
        this.zzbig = view;
        this.zzbih = aVar;
        this.zzbii = str6;
    }

    static /* synthetic */ zzoz zza(zzoo zzooVar, zzoz zzozVar) {
        zzooVar.zzbij = null;
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final void destroy() {
        zzakk.zzcrm.post(new zzop(this));
        this.zzbhw = null;
        this.zzbhx = null;
        this.zzbhy = null;
        this.zzbhz = null;
        this.zzbia = null;
        this.zzbib = 0.0d;
        this.zzbic = null;
        this.zzbid = null;
        this.zzbie = null;
        this.mExtras = null;
        this.mLock = null;
        this.zzbif = null;
        this.zzbig = null;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final String getBody() {
        return this.zzbhy;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final String getCallToAction() {
        return this.zzbia;
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final String getCustomTemplateId() {
        return "";
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final Bundle getExtras() {
        return this.mExtras;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final String getHeadline() {
        return this.zzbhw;
    }

    @Override // com.google.android.gms.internal.ads.zzqk, com.google.android.gms.internal.ads.zzpc
    public final List getImages() {
        return this.zzbhx;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final String getMediationAdapterClassName() {
        return this.zzbii;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final String getPrice() {
        return this.zzbid;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final double getStarRating() {
        return this.zzbib;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final String getStore() {
        return this.zzbic;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final zzlo getVideoController() {
        return this.zzbif;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final void performClick(Bundle bundle) {
        synchronized (this.mLock) {
            if (this.zzbij == null) {
                zzakb.e("#001 Attempt to perform click before app native ad initialized.");
            } else {
                this.zzbij.performClick(bundle);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final boolean recordImpression(Bundle bundle) {
        boolean zRecordImpression;
        synchronized (this.mLock) {
            if (this.zzbij == null) {
                zzakb.e("#002 Attempt to record impression before native ad initialized.");
                zRecordImpression = false;
            } else {
                zRecordImpression = this.zzbij.recordImpression(bundle);
            }
        }
        return zRecordImpression;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final void reportTouchEvent(Bundle bundle) {
        synchronized (this.mLock) {
            if (this.zzbij == null) {
                zzakb.e("#003 Attempt to report touch event before native ad initialized.");
            } else {
                this.zzbij.reportTouchEvent(bundle);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final void zzb(zzoz zzozVar) {
        synchronized (this.mLock) {
            this.zzbij = zzozVar;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final zzpw zzjz() {
        return this.zzbhz;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final a zzka() {
        return d.F(this.zzbij);
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final String zzkb() {
        return "2";
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final zzoj zzkc() {
        return this.zzbie;
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final View zzkd() {
        return this.zzbig;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final a zzke() {
        return this.zzbih;
    }

    @Override // com.google.android.gms.internal.ads.zzqk
    public final zzps zzkf() {
        return this.zzbie;
    }
}
