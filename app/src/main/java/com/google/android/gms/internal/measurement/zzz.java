package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.google.android.gms.a.l;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzz extends l<zzz> {
    private String zztp;
    public int zztq;
    public int zztr;
    public int zzts;
    public int zztt;
    public int zztu;

    public final String getLanguage() {
        return this.zztp;
    }

    public final void setLanguage(String str) {
        this.zztp = str;
    }

    public final String toString() {
        HashMap map = new HashMap();
        map.put("language", this.zztp);
        map.put("screenColors", Integer.valueOf(this.zztq));
        map.put("screenWidth", Integer.valueOf(this.zztr));
        map.put("screenHeight", Integer.valueOf(this.zzts));
        map.put("viewportWidth", Integer.valueOf(this.zztt));
        map.put("viewportHeight", Integer.valueOf(this.zztu));
        return zza((Object) map);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzz zzzVar = (zzz) lVar;
        if (this.zztq != 0) {
            zzzVar.zztq = this.zztq;
        }
        if (this.zztr != 0) {
            zzzVar.zztr = this.zztr;
        }
        if (this.zzts != 0) {
            zzzVar.zzts = this.zzts;
        }
        if (this.zztt != 0) {
            zzzVar.zztt = this.zztt;
        }
        if (this.zztu != 0) {
            zzzVar.zztu = this.zztu;
        }
        if (TextUtils.isEmpty(this.zztp)) {
            return;
        }
        zzzVar.zztp = this.zztp;
    }
}
