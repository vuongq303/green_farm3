package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaql implements com.google.android.gms.ads.internal.gmsg.zzv<zzapw> {
    private static Integer zze(Map<String, String> map, String str) {
        if (!map.containsKey(str)) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(map.get(str)));
        } catch (NumberFormatException e) {
            String str2 = map.get(str);
            zzakb.zzdk(new StringBuilder(String.valueOf(str).length() + 39 + String.valueOf(str2).length()).append("Precache invalid numeric parameter '").append(str).append("': ").append(str2).toString());
            return null;
        }
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzapw zzapwVar, Map map) {
        zzapw zzapwVar2 = zzapwVar;
        com.google.android.gms.ads.internal.zzbv.zzff();
        if (map.containsKey("abort")) {
            if (zzaqg.zzb(zzapwVar2)) {
                return;
            }
            zzakb.zzdk("Precache abort but no precache task running.");
            return;
        }
        String str = (String) map.get("src");
        if (str != null) {
            if (zzaqg.zzc(zzapwVar2) != null) {
                zzakb.zzdk("Precache task is already running.");
                return;
            }
            if (zzapwVar2.zzbi() == null) {
                zzakb.zzdk("Precache requires a dependency provider.");
                return;
            }
            zzapv zzapvVar = new zzapv((String) map.get("flags"));
            Integer numZze = zze(map, "player");
            if (numZze == null) {
                numZze = 0;
            }
            new zzaqe(zzapwVar2, zzapwVar2.zzbi().zzwy.zza(zzapwVar2, numZze.intValue(), null, zzapvVar), str).zznt();
        } else if (zzaqg.zzc(zzapwVar2) == null) {
            zzakb.zzdk("Precache must specify a source.");
            return;
        }
        Integer numZze2 = zze(map, "minBufferMs");
        if (numZze2 != null) {
            numZze2.intValue();
        }
        Integer numZze3 = zze(map, "maxBufferMs");
        if (numZze3 != null) {
            numZze3.intValue();
        }
        Integer numZze4 = zze(map, "bufferForPlaybackMs");
        if (numZze4 != null) {
            numZze4.intValue();
        }
        Integer numZze5 = zze(map, "bufferForPlaybackAfterRebufferMs");
        if (numZze5 != null) {
            numZze5.intValue();
        }
    }
}
