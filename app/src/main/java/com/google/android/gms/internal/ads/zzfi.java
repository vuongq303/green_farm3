package com.google.android.gms.internal.ads;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class zzfi implements zzaoo<zzwb> {
    private final /* synthetic */ JSONObject zzagb;

    zzfi(zzff zzffVar, JSONObject jSONObject) {
        this.zzagb = jSONObject;
    }

    @Override // com.google.android.gms.internal.ads.zzaoo
    public final /* synthetic */ void zze(zzwb zzwbVar) {
        zzwbVar.zzb("AFMA_updateActiveView", this.zzagb);
    }
}
