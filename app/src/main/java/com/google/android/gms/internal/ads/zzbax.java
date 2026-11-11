package com.google.android.gms.internal.ads;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzbax implements zzbey {
    private final zzbav zzdpv;

    private zzbax(zzbav zzbavVar) {
        this.zzdpv = (zzbav) zzbbq.zza(zzbavVar, "output");
        this.zzdpv.zzdqn = this;
    }

    public static zzbax zza(zzbav zzbavVar) {
        return zzbavVar.zzdqn != null ? zzbavVar.zzdqn : new zzbax(zzbavVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, double d) {
        this.zzdpv.zza(i, d);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, float f) {
        this.zzdpv.zza(i, f);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, long j) {
        this.zzdpv.zza(i, j);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, zzbah zzbahVar) {
        this.zzdpv.zza(i, zzbahVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final <K, V> void zza(int i, zzbcn<K, V> zzbcnVar, Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zzdpv.zzl(i, 2);
            this.zzdpv.zzca(zzbcm.zza(zzbcnVar, entry.getKey(), entry.getValue()));
            zzbcm.zza(this.zzdpv, zzbcnVar, entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, Object obj) {
        if (obj instanceof zzbah) {
            this.zzdpv.zzb(i, (zzbah) obj);
        } else {
            this.zzdpv.zza(i, (zzbcu) obj);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, Object obj, zzbdm zzbdmVar) {
        this.zzdpv.zza(i, (zzbcu) obj, zzbdmVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, List<String> list) {
        int i2 = 0;
        if (!(list instanceof zzbcd)) {
            while (i2 < list.size()) {
                this.zzdpv.zzf(i, list.get(i2));
                i2++;
            }
            return;
        }
        zzbcd zzbcdVar = (zzbcd) list;
        while (true) {
            int i3 = i2;
            if (i3 >= list.size()) {
                return;
            }
            Object objZzcp = zzbcdVar.zzcp(i3);
            if (objZzcp instanceof String) {
                this.zzdpv.zzf(i, (String) objZzcp);
            } else {
                this.zzdpv.zza(i, (zzbah) objZzcp);
            }
            i2 = i3 + 1;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, List<?> list, zzbdm zzbdmVar) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzbdmVar);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zza(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzm(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzce = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzce += zzbav.zzce(list.get(i3).intValue());
        }
        this.zzdpv.zzca(iZzce);
        while (i2 < list.size()) {
            this.zzdpv.zzbz(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final int zzacn() {
        return zzbbo.zze.zzdul;
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzb(int i, long j) {
        this.zzdpv.zzb(i, j);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzb(int i, Object obj, zzbdm zzbdmVar) {
        zzbav zzbavVar = this.zzdpv;
        zzbavVar.zzl(i, 3);
        zzbdmVar.zza((zzbcu) obj, zzbavVar.zzdqn);
        zzbavVar.zzl(i, 4);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzb(int i, List<zzbah> list) {
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= list.size()) {
                return;
            }
            this.zzdpv.zza(i, list.get(i3));
            i2 = i3 + 1;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzb(int i, List<?> list, zzbdm zzbdmVar) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzbdmVar);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzb(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzp(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzch = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzch += zzbav.zzch(list.get(i3).intValue());
        }
        this.zzdpv.zzca(iZzch);
        while (i2 < list.size()) {
            this.zzdpv.zzcc(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzc(int i, long j) {
        this.zzdpv.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzc(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zza(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzp = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzp += zzbav.zzp(list.get(i3).longValue());
        }
        this.zzdpv.zzca(iZzp);
        while (i2 < list.size()) {
            this.zzdpv.zzm(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzcm(int i) {
        this.zzdpv.zzl(i, 3);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzcn(int i) {
        this.zzdpv.zzl(i, 4);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzd(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zza(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzq = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzq += zzbav.zzq(list.get(i3).longValue());
        }
        this.zzdpv.zzca(iZzq);
        while (i2 < list.size()) {
            this.zzdpv.zzm(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zze(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzc(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzs = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzs += zzbav.zzs(list.get(i3).longValue());
        }
        this.zzdpv.zzca(iZzs);
        while (i2 < list.size()) {
            this.zzdpv.zzo(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzf(int i, String str) {
        this.zzdpv.zzf(i, str);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzf(int i, List<Float> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zza(i, list.get(i2).floatValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzc = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzc += zzbav.zzc(list.get(i3).floatValue());
        }
        this.zzdpv.zzca(iZzc);
        while (i2 < list.size()) {
            this.zzdpv.zzb(list.get(i2).floatValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzf(int i, boolean z) {
        this.zzdpv.zzf(i, z);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzg(int i, List<Double> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zza(i, list.get(i2).doubleValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzc = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzc += zzbav.zzc(list.get(i3).doubleValue());
        }
        this.zzdpv.zzca(iZzc);
        while (i2 < list.size()) {
            this.zzdpv.zzb(list.get(i2).doubleValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzh(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzm(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzcj = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzcj += zzbav.zzcj(list.get(i3).intValue());
        }
        this.zzdpv.zzca(iZzcj);
        while (i2 < list.size()) {
            this.zzdpv.zzbz(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzi(int i, long j) {
        this.zzdpv.zza(i, j);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzi(int i, List<Boolean> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzf(i, list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzaq = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzaq += zzbav.zzaq(list.get(i3).booleanValue());
        }
        this.zzdpv.zzca(iZzaq);
        while (i2 < list.size()) {
            this.zzdpv.zzap(list.get(i2).booleanValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzj(int i, long j) {
        this.zzdpv.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzj(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzn(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzcf = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzcf += zzbav.zzcf(list.get(i3).intValue());
        }
        this.zzdpv.zzca(iZzcf);
        while (i2 < list.size()) {
            this.zzdpv.zzca(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzk(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzp(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzci = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzci += zzbav.zzci(list.get(i3).intValue());
        }
        this.zzdpv.zzca(iZzci);
        while (i2 < list.size()) {
            this.zzdpv.zzcc(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzl(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzc(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzt = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzt += zzbav.zzt(list.get(i3).longValue());
        }
        this.zzdpv.zzca(iZzt);
        while (i2 < list.size()) {
            this.zzdpv.zzo(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzm(int i, int i2) {
        this.zzdpv.zzm(i, i2);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzm(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzo(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzcg = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzcg += zzbav.zzcg(list.get(i3).intValue());
        }
        this.zzdpv.zzca(iZzcg);
        while (i2 < list.size()) {
            this.zzdpv.zzcb(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzn(int i, int i2) {
        this.zzdpv.zzn(i, i2);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzn(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzdpv.zzb(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzdpv.zzl(i, 2);
        int iZzr = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzr += zzbav.zzr(list.get(i3).longValue());
        }
        this.zzdpv.zzca(iZzr);
        while (i2 < list.size()) {
            this.zzdpv.zzn(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzo(int i, int i2) {
        this.zzdpv.zzo(i, i2);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzp(int i, int i2) {
        this.zzdpv.zzp(i, i2);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzw(int i, int i2) {
        this.zzdpv.zzp(i, i2);
    }

    @Override // com.google.android.gms.internal.ads.zzbey
    public final void zzx(int i, int i2) {
        this.zzdpv.zzm(i, i2);
    }
}
