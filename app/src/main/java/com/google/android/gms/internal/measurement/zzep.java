package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.bf;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class zzep {
    final String name;
    final long timestamp;
    private final String zzaek;
    final long zzafn;
    final zzer zzafo;
    final String zztd;

    zzep(zzgl zzglVar, String str, String str2, String str3, long j, long j2, Bundle bundle) {
        zzer zzerVar;
        bf.id(str2);
        bf.id(str3);
        this.zztd = str2;
        this.name = str3;
        this.zzaek = TextUtils.isEmpty(str) ? null : str;
        this.timestamp = j;
        this.zzafn = j2;
        if (this.zzafn != 0 && this.zzafn > this.timestamp) {
            zzglVar.zzgg().zzin().zzg("Event created with reverse previous/current timestamps. appId", zzfg.zzbh(str2));
        }
        if (bundle == null || bundle.isEmpty()) {
            zzerVar = new zzer(new Bundle());
        } else {
            Bundle bundle2 = new Bundle(bundle);
            Iterator<String> it = bundle2.keySet().iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next == null) {
                    zzglVar.zzgg().zzil().log("Param name can't be null");
                    it.remove();
                } else {
                    Object objZzh = zzglVar.zzgc().zzh(next, bundle2.get(next));
                    if (objZzh == null) {
                        zzglVar.zzgg().zzin().zzg("Param value can't be null", zzglVar.zzgb().zzbf(next));
                        it.remove();
                    } else {
                        zzglVar.zzgc().zza(bundle2, next, objZzh);
                    }
                }
            }
            zzerVar = new zzer(bundle2);
        }
        this.zzafo = zzerVar;
    }

    private zzep(zzgl zzglVar, String str, String str2, String str3, long j, long j2, zzer zzerVar) {
        bf.id(str2);
        bf.id(str3);
        bf.checkNotNull(zzerVar);
        this.zztd = str2;
        this.name = str3;
        this.zzaek = TextUtils.isEmpty(str) ? null : str;
        this.timestamp = j;
        this.zzafn = j2;
        if (this.zzafn != 0 && this.zzafn > this.timestamp) {
            zzglVar.zzgg().zzin().zze("Event created with reverse previous/current timestamps. appId, name", zzfg.zzbh(str2), zzfg.zzbh(str3));
        }
        this.zzafo = zzerVar;
    }

    public final String toString() {
        String str = this.zztd;
        String str2 = this.name;
        String strValueOf = String.valueOf(this.zzafo);
        return new StringBuilder(String.valueOf(str).length() + 33 + String.valueOf(str2).length() + String.valueOf(strValueOf).length()).append("Event{appId='").append(str).append("', name='").append(str2).append("', params=").append(strValueOf).append('}').toString();
    }

    final zzep zza(zzgl zzglVar, long j) {
        return new zzep(zzglVar, this.zzaek, this.zztd, this.name, this.timestamp, j, this.zzafo);
    }
}
