package com.google.android.gms.ads.internal;

import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
final class ae {
    private final String bCj;
    private final Map<String, String> bCk = new TreeMap();
    private String bCl;
    private String bCm;

    public ae(String str) {
        this.bCj = str;
    }

    public final String Xe() {
        return this.bCm;
    }

    public final String Xf() {
        return this.bCj;
    }

    public final Map<String, String> Xg() {
        return this.bCk;
    }

    public final void a(zzjj zzjjVar, zzang zzangVar) {
        this.bCl = zzjjVar.zzaqd.zzatn;
        Bundle bundle = zzjjVar.zzaqg != null ? zzjjVar.zzaqg.getBundle(AdMobAdapter.class.getName()) : null;
        if (bundle == null) {
            return;
        }
        String str = (String) zzkb.zzik().zzd(zznk.zzbda);
        for (String str2 : bundle.keySet()) {
            if (str.equals(str2)) {
                this.bCm = bundle.getString(str2);
            } else if (str2.startsWith("csa_")) {
                this.bCk.put(str2.substring(4), bundle.getString(str2));
            }
        }
        this.bCk.put("SDKVersion", zzangVar.zzcw);
    }

    public final String getQuery() {
        return this.bCl;
    }
}
