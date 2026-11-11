package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.google.android.gms.a.l;
import com.google.android.gms.common.internal.bf;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzad extends l<zzad> {
    private String zzty;
    private String zztz;
    private String zzua;
    private String zzub;
    private boolean zzuc;
    private String zzud;
    private boolean zzue;
    private double zzuf;

    public final void setClientId(String str) {
        this.zztz = str;
    }

    public final void setUserId(String str) {
        this.zzua = str;
    }

    public final String toString() {
        HashMap map = new HashMap();
        map.put("hitType", this.zzty);
        map.put("clientId", this.zztz);
        map.put("userId", this.zzua);
        map.put("androidAdId", this.zzub);
        map.put("AdTargetingEnabled", Boolean.valueOf(this.zzuc));
        map.put("sessionControl", this.zzud);
        map.put("nonInteraction", Boolean.valueOf(this.zzue));
        map.put("sampleRate", Double.valueOf(this.zzuf));
        return zza((Object) map);
    }

    public final void zza(boolean z) {
        this.zzuc = z;
    }

    public final String zzay() {
        return this.zzty;
    }

    public final String zzaz() {
        return this.zztz;
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzad zzadVar = (zzad) lVar;
        if (!TextUtils.isEmpty(this.zzty)) {
            zzadVar.zzty = this.zzty;
        }
        if (!TextUtils.isEmpty(this.zztz)) {
            zzadVar.zztz = this.zztz;
        }
        if (!TextUtils.isEmpty(this.zzua)) {
            zzadVar.zzua = this.zzua;
        }
        if (!TextUtils.isEmpty(this.zzub)) {
            zzadVar.zzub = this.zzub;
        }
        if (this.zzuc) {
            zzadVar.zzuc = true;
        }
        if (!TextUtils.isEmpty(this.zzud)) {
            zzadVar.zzud = this.zzud;
        }
        if (this.zzue) {
            zzadVar.zzue = this.zzue;
        }
        if (this.zzuf != 0.0d) {
            double d = this.zzuf;
            bf.b(d >= 0.0d && d <= 100.0d, "Sample rate must be between 0% and 100%");
            zzadVar.zzuf = d;
        }
    }

    public final void zzb(boolean z) {
        this.zzue = true;
    }

    public final String zzba() {
        return this.zzua;
    }

    public final String zzbb() {
        return this.zzub;
    }

    public final boolean zzbc() {
        return this.zzuc;
    }

    public final String zzbd() {
        return this.zzud;
    }

    public final boolean zzbe() {
        return this.zzue;
    }

    public final double zzbf() {
        return this.zzuf;
    }

    public final void zzl(String str) {
        this.zzty = str;
    }

    public final void zzm(String str) {
        this.zzub = str;
    }
}
