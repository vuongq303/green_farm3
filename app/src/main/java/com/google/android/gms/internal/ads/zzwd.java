package com.google.android.gms.internal.ads;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzwd implements zzuo, zzwc {
    private final zzwb zzbqz;
    private final HashSet<AbstractMap.SimpleEntry<String, com.google.android.gms.ads.internal.gmsg.zzv<? super zzwb>>> zzbra = new HashSet<>();

    public zzwd(zzwb zzwbVar) {
        this.zzbqz = zzwbVar;
    }

    @Override // com.google.android.gms.internal.ads.zzwb
    public final void zza(String str, com.google.android.gms.ads.internal.gmsg.zzv<? super zzwb> zzvVar) {
        this.zzbqz.zza(str, zzvVar);
        this.zzbra.add(new AbstractMap.SimpleEntry<>(str, zzvVar));
    }

    @Override // com.google.android.gms.internal.ads.zzue
    public final void zza(String str, Map map) {
        zzup.zza(this, str, map);
    }

    @Override // com.google.android.gms.internal.ads.zzuo, com.google.android.gms.internal.ads.zzue
    public final void zza(String str, JSONObject jSONObject) {
        zzup.zzb(this, str, jSONObject);
    }

    @Override // com.google.android.gms.internal.ads.zzwb
    public final void zzb(String str, com.google.android.gms.ads.internal.gmsg.zzv<? super zzwb> zzvVar) {
        this.zzbqz.zzb(str, zzvVar);
        this.zzbra.remove(new AbstractMap.SimpleEntry(str, zzvVar));
    }

    @Override // com.google.android.gms.internal.ads.zzve
    public final void zzb(String str, JSONObject jSONObject) {
        zzup.zza(this, str, jSONObject);
    }

    @Override // com.google.android.gms.internal.ads.zzuo, com.google.android.gms.internal.ads.zzve
    public final void zzbe(String str) {
        this.zzbqz.zzbe(str);
    }

    @Override // com.google.android.gms.internal.ads.zzuo
    public final void zzf(String str, String str2) {
        zzup.zza(this, str, str2);
    }

    @Override // com.google.android.gms.internal.ads.zzwc
    public final void zzmd() {
        for (AbstractMap.SimpleEntry<String, com.google.android.gms.ads.internal.gmsg.zzv<? super zzwb>> simpleEntry : this.zzbra) {
            String strValueOf = String.valueOf(simpleEntry.getValue().toString());
            zzakb.v(strValueOf.length() != 0 ? "Unregistering eventhandler: ".concat(strValueOf) : new String("Unregistering eventhandler: "));
            this.zzbqz.zzb(simpleEntry.getKey(), simpleEntry.getValue());
        }
        this.zzbra.clear();
    }
}
