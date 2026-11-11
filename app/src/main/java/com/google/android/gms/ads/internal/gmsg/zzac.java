package com.google.android.gms.ads.internal.gmsg;

import com.google.android.gms.ads.internal.zzx;
import com.google.android.gms.internal.ads.zzaab;
import com.google.android.gms.internal.ads.zzaad;
import com.google.android.gms.internal.ads.zzaae;
import com.google.android.gms.internal.ads.zzaam;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzzy;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzac implements zzv<zzaqw> {
    private static final Map<String, Integer> bzY = com.google.android.gms.common.d.f.c(new String[]{"resize", "playVideo", "storePicture", "createCalendarEvent", "setOrientationProperties", "closeResizedAd", "unload"}, new Integer[]{1, 2, 3, 4, 5, 6, 7});
    private final zzx zzbmw;
    private final zzaab zzbmx;
    private final zzaam zzbmy;

    public zzac(zzx zzxVar, zzaab zzaabVar, zzaam zzaamVar) {
        this.zzbmw = zzxVar;
        this.zzbmx = zzaabVar;
        this.zzbmy = zzaamVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzaqw zzaqwVar, Map map) {
        zzaqw zzaqwVar2 = zzaqwVar;
        int iIntValue = bzY.get((String) map.get("a")).intValue();
        if (iIntValue != 5 && iIntValue != 7 && this.zzbmw != null && !this.zzbmw.zzcy()) {
            this.zzbmw.zzs(null);
        }
        switch (iIntValue) {
            case 1:
                this.zzbmx.zzk(map);
                break;
            case 2:
            default:
                zzakb.zzdj("Unknown MRAID command called.");
                break;
            case 3:
                new zzaae(zzaqwVar2, map).execute();
                break;
            case 4:
                new zzzy(zzaqwVar2, map).execute();
                break;
            case 5:
                new zzaad(zzaqwVar2, map).execute();
                break;
            case 6:
                this.zzbmx.zzm(true);
                break;
            case 7:
                if (((Boolean) zzkb.zzik().zzd(zznk.zzawg)).booleanValue()) {
                    this.zzbmy.zzcz();
                }
                break;
        }
    }
}
