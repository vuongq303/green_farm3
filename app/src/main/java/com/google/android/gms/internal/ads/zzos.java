package com.google.android.gms.internal.ads;

import android.support.v4.e.r;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzos extends zzqt implements zzpb {
    private final Object mLock = new Object();
    private final zzoj zzbie;
    private zzlo zzbif;
    private View zzbig;
    private zzoz zzbij;
    private final String zzbio;
    private final r<String, zzon> zzbip;
    private final r<String, String> zzbiq;

    public zzos(String str, r<String, zzon> rVar, r<String, String> rVar2, zzoj zzojVar, zzlo zzloVar, View view) {
        this.zzbio = str;
        this.zzbip = rVar;
        this.zzbiq = rVar2;
        this.zzbie = zzojVar;
        this.zzbif = zzloVar;
        this.zzbig = view;
    }

    static /* synthetic */ zzoz zza(zzos zzosVar, zzoz zzozVar) {
        zzosVar.zzbij = null;
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final void destroy() {
        zzakk.zzcrm.post(new zzou(this));
        this.zzbif = null;
        this.zzbig = null;
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final List<String> getAvailableAssetNames() {
        int i = 0;
        String[] strArr = new String[this.zzbip.size() + this.zzbiq.size()];
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzbip.size(); i3++) {
            strArr[i2] = this.zzbip.keyAt(i3);
            i2++;
        }
        while (i < this.zzbiq.size()) {
            strArr[i2] = this.zzbiq.keyAt(i);
            i++;
            i2++;
        }
        return Arrays.asList(strArr);
    }

    @Override // com.google.android.gms.internal.ads.zzqs, com.google.android.gms.internal.ads.zzpb
    public final String getCustomTemplateId() {
        return this.zzbio;
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final zzlo getVideoController() {
        return this.zzbif;
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final void performClick(String str) {
        synchronized (this.mLock) {
            if (this.zzbij == null) {
                zzane.e("#001 Attempt to perform click before app native ad initialized.");
            } else {
                this.zzbij.zza(null, str, null, null, null);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final void recordImpression() {
        synchronized (this.mLock) {
            if (this.zzbij == null) {
                zzane.e("#002 Attempt to record impression before native ad initialized.");
            } else {
                this.zzbij.zza((View) null, (Map<String, WeakReference<View>>) null);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final String zzao(String str) {
        return this.zzbiq.get(str);
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final zzpw zzap(String str) {
        return this.zzbip.get(str);
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final void zzb(zzoz zzozVar) {
        synchronized (this.mLock) {
            this.zzbij = zzozVar;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final boolean zzh(a aVar) {
        if (this.zzbij == null) {
            zzane.e("Attempt to call renderVideoInMediaView before ad initialized.");
            return false;
        }
        if (this.zzbig == null) {
            return false;
        }
        zzot zzotVar = new zzot(this);
        this.zzbij.zza((FrameLayout) d.b(aVar), zzotVar);
        return true;
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final a zzka() {
        return d.F(this.zzbij);
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final String zzkb() {
        return "3";
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final zzoj zzkc() {
        return this.zzbie;
    }

    @Override // com.google.android.gms.internal.ads.zzpb
    public final View zzkd() {
        return this.zzbig;
    }

    @Override // com.google.android.gms.internal.ads.zzqs
    public final a zzkh() {
        return d.F(this.zzbij.getContext().getApplicationContext());
    }
}
