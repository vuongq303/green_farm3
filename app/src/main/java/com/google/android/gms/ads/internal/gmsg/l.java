package com.google.android.gms.ads.internal.gmsg;

import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaqw;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class l implements zzv<zzaqw> {
    l() {
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzaqw zzaqwVar, Map map) {
        zzaqw zzaqwVar2 = zzaqwVar;
        com.google.android.gms.ads.internal.overlay.zzd zzdVarZzub = zzaqwVar2.zzub();
        if (zzdVarZzub != null) {
            zzdVarZzub.close();
            return;
        }
        com.google.android.gms.ads.internal.overlay.zzd zzdVarZzuc = zzaqwVar2.zzuc();
        if (zzdVarZzuc != null) {
            zzdVarZzuc.close();
        } else {
            zzakb.zzdk("A GMSG tried to close something that wasn't an overlay.");
        }
    }
}
