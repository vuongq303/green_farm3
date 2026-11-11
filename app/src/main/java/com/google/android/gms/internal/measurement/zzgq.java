package com.google.android.gms.internal.measurement;

import android.os.Binder;
import android.text.TextUtils;
import com.google.android.gms.common.d.z;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.m;
import com.google.android.gms.common.n;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes.dex */
public final class zzgq extends zzez {
    private final zzgl zzacr;
    private Boolean zzanw;
    private String zzanx;

    public zzgq(zzgl zzglVar) {
        this(zzglVar, null);
    }

    private zzgq(zzgl zzglVar, String str) {
        bf.checkNotNull(zzglVar);
        this.zzacr = zzglVar;
        this.zzanx = null;
    }

    private final void zzb(zzec zzecVar, boolean z) {
        bf.checkNotNull(zzecVar);
        zzc(zzecVar.packageName, false);
        this.zzacr.zzgc().zzbz(zzecVar.zzadh);
    }

    private final void zzc(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            this.zzacr.zzgg().zzil().log("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (this.zzanw == null) {
                    this.zzanw = Boolean.valueOf("com.google.android.gms".equals(this.zzanx) || z.isGooglePlayServicesUid(this.zzacr.getContext(), Binder.getCallingUid()) || n.aB(this.zzacr.getContext()).nv(Binder.getCallingUid()));
                }
                if (this.zzanw.booleanValue()) {
                    return;
                }
            } catch (SecurityException e) {
                this.zzacr.zzgg().zzil().zzg("Measurement Service called with invalid calling package. appId", zzfg.zzbh(str));
                throw e;
            }
        }
        if (this.zzanx == null && m.uidHasPackageName(this.zzacr.getContext(), Binder.getCallingUid(), str)) {
            this.zzanx = str;
        }
        if (str.equals(this.zzanx)) {
        } else {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", str));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final List<zzjs> zza(zzec zzecVar, boolean z) {
        zzb(zzecVar, false);
        try {
            List<zzju> list = (List) this.zzacr.zzgf().zzb(new zzhg(this, zzecVar)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzju zzjuVar : list) {
                if (z || !zzjv.zzcb(zzjuVar.name)) {
                    arrayList.add(new zzjs(zzjuVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzacr.zzgg().zzil().zze("Failed to get user attributes. appId", zzfg.zzbh(zzecVar.packageName), e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final List<zzef> zza(String str, String str2, zzec zzecVar) {
        zzb(zzecVar, false);
        try {
            return (List) this.zzacr.zzgf().zzb(new zzgy(this, zzecVar, str, str2)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zzacr.zzgg().zzil().zzg("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final List<zzjs> zza(String str, String str2, String str3, boolean z) {
        zzc(str, true);
        try {
            List<zzju> list = (List) this.zzacr.zzgf().zzb(new zzgx(this, str, str2, str3)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzju zzjuVar : list) {
                if (z || !zzjv.zzcb(zzjuVar.name)) {
                    arrayList.add(new zzjs(zzjuVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzacr.zzgg().zzil().zze("Failed to get user attributes. appId", zzfg.zzbh(str), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final List<zzjs> zza(String str, String str2, boolean z, zzec zzecVar) {
        zzb(zzecVar, false);
        try {
            List<zzju> list = (List) this.zzacr.zzgf().zzb(new zzgw(this, zzecVar, str, str2)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzju zzjuVar : list) {
                if (z || !zzjv.zzcb(zzjuVar.name)) {
                    arrayList.add(new zzjs(zzjuVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzacr.zzgg().zzil().zze("Failed to get user attributes. appId", zzfg.zzbh(zzecVar.packageName), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zza(long j, String str, String str2, String str3) {
        this.zzacr.zzgf().zzc(new zzhi(this, str2, str3, str, j));
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zza(zzec zzecVar) {
        zzb(zzecVar, false);
        zzhh zzhhVar = new zzhh(this, zzecVar);
        if (this.zzacr.zzgf().zzjg()) {
            zzhhVar.run();
        } else {
            this.zzacr.zzgf().zzc(zzhhVar);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zza(zzef zzefVar, zzec zzecVar) {
        bf.checkNotNull(zzefVar);
        bf.checkNotNull(zzefVar.zzael);
        zzb(zzecVar, false);
        zzef zzefVar2 = new zzef(zzefVar);
        zzefVar2.packageName = zzecVar.packageName;
        if (zzefVar.zzael.getValue() == null) {
            this.zzacr.zzgf().zzc(new zzgs(this, zzefVar2, zzecVar));
        } else {
            this.zzacr.zzgf().zzc(new zzgt(this, zzefVar2, zzecVar));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zza(zzeu zzeuVar, zzec zzecVar) {
        bf.checkNotNull(zzeuVar);
        zzb(zzecVar, false);
        this.zzacr.zzgf().zzc(new zzhb(this, zzeuVar, zzecVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zza(zzeu zzeuVar, String str, String str2) {
        bf.checkNotNull(zzeuVar);
        bf.id(str);
        zzc(str, true);
        this.zzacr.zzgf().zzc(new zzhc(this, zzeuVar, str));
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zza(zzjs zzjsVar, zzec zzecVar) {
        bf.checkNotNull(zzjsVar);
        zzb(zzecVar, false);
        if (zzjsVar.getValue() == null) {
            this.zzacr.zzgf().zzc(new zzhe(this, zzjsVar, zzecVar));
        } else {
            this.zzacr.zzgf().zzc(new zzhf(this, zzjsVar, zzecVar));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final byte[] zza(zzeu zzeuVar, String str) {
        bf.id(str);
        bf.checkNotNull(zzeuVar);
        zzc(str, true);
        this.zzacr.zzgg().zziq().zzg("Log and bundle. event", this.zzacr.zzgb().zzbe(zzeuVar.name));
        long jNanoTime = this.zzacr.zzbt().nanoTime() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zzacr.zzgf().zzc(new zzhd(this, zzeuVar, str)).get();
            if (bArr == null) {
                this.zzacr.zzgg().zzil().zzg("Log and bundle returned null. appId", zzfg.zzbh(str));
                bArr = new byte[0];
            }
            this.zzacr.zzgg().zziq().zzd("Log and bundle processed. event, size, time_ms", this.zzacr.zzgb().zzbe(zzeuVar.name), Integer.valueOf(bArr.length), Long.valueOf((this.zzacr.zzbt().nanoTime() / 1000000) - jNanoTime));
            return bArr;
        } catch (InterruptedException | ExecutionException e) {
            this.zzacr.zzgg().zzil().zzd("Failed to log and bundle. appId, event, error", zzfg.zzbh(str), this.zzacr.zzgb().zzbe(zzeuVar.name), e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zzb(zzec zzecVar) {
        zzb(zzecVar, false);
        this.zzacr.zzgf().zzc(new zzgr(this, zzecVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zzb(zzef zzefVar) {
        bf.checkNotNull(zzefVar);
        bf.checkNotNull(zzefVar.zzael);
        zzc(zzefVar.packageName, true);
        zzef zzefVar2 = new zzef(zzefVar);
        if (zzefVar.zzael.getValue() == null) {
            this.zzacr.zzgf().zzc(new zzgu(this, zzefVar2));
        } else {
            this.zzacr.zzgf().zzc(new zzgv(this, zzefVar2));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final String zzc(zzec zzecVar) {
        zzb(zzecVar, false);
        return this.zzacr.zzf(zzecVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final void zzd(zzec zzecVar) {
        zzc(zzecVar.packageName, false);
        this.zzacr.zzgf().zzc(new zzha(this, zzecVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final List<zzef> zze(String str, String str2, String str3) {
        zzc(str, true);
        try {
            return (List) this.zzacr.zzgf().zzb(new zzgz(this, str, str2, str3)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zzacr.zzgg().zzil().zzg("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }
}
