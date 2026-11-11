package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.google.android.gms.a.l;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzu extends l<zzu> {
    private String zztb;
    private String zztc;
    private String zztd;
    private String zzte;

    public final void setAppId(String str) {
        this.zztd = str;
    }

    public final void setAppInstallerId(String str) {
        this.zzte = str;
    }

    public final void setAppName(String str) {
        this.zztb = str;
    }

    public final void setAppVersion(String str) {
        this.zztc = str;
    }

    public final String toString() {
        HashMap map = new HashMap();
        map.put("appName", this.zztb);
        map.put("appVersion", this.zztc);
        map.put("appId", this.zztd);
        map.put("appInstallerId", this.zzte);
        return zza((Object) map);
    }

    @Override // com.google.android.gms.a.l
    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final void zzb(zzu zzuVar) {
        if (!TextUtils.isEmpty(this.zztb)) {
            zzuVar.zztb = this.zztb;
        }
        if (!TextUtils.isEmpty(this.zztc)) {
            zzuVar.zztc = this.zztc;
        }
        if (!TextUtils.isEmpty(this.zztd)) {
            zzuVar.zztd = this.zztd;
        }
        if (TextUtils.isEmpty(this.zzte)) {
            return;
        }
        zzuVar.zzte = this.zzte;
    }

    public final String zzaf() {
        return this.zztb;
    }

    public final String zzag() {
        return this.zztc;
    }

    public final String zzah() {
        return this.zztd;
    }

    public final String zzai() {
        return this.zzte;
    }
}
