package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.e.a;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.internal.bf;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzdx extends zzhj {
    private final Map<String, Long> zzada;
    private final Map<String, Integer> zzadb;
    private long zzadc;

    public zzdx(zzgl zzglVar) {
        super(zzglVar);
        this.zzadb = new a();
        this.zzada = new a();
    }

    private final void zza(long j, zzig zzigVar) {
        if (zzigVar == null) {
            zzgg().zzir().log("Not logging ad exposure. No active activity");
            return;
        }
        if (j < 1000) {
            zzgg().zzir().zzg("Not logging ad exposure. Less than 1000 ms. exposure", Long.valueOf(j));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("_xt", j);
        zzih.zza(zzigVar, bundle, true);
        zzfu().zza("am", "_xa", bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(String str, long j) {
        zzab();
        bf.id(str);
        if (this.zzadb.isEmpty()) {
            this.zzadc = j;
        }
        Integer num = this.zzadb.get(str);
        if (num != null) {
            this.zzadb.put(str, Integer.valueOf(num.intValue() + 1));
        } else if (this.zzadb.size() >= 100) {
            zzgg().zzin().log("Too many ads visible");
        } else {
            this.zzadb.put(str, 1);
            this.zzada.put(str, Long.valueOf(j));
        }
    }

    private final void zza(String str, long j, zzig zzigVar) {
        if (zzigVar == null) {
            zzgg().zzir().log("Not logging ad unit exposure. No active activity");
            return;
        }
        if (j < 1000) {
            zzgg().zzir().zzg("Not logging ad unit exposure. Less than 1000 ms. exposure", Long.valueOf(j));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("_ai", str);
        bundle.putLong("_xt", j);
        zzih.zza(zzigVar, bundle, true);
        zzfu().zza("am", "_xu", bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(String str, long j) {
        zzab();
        bf.id(str);
        Integer num = this.zzadb.get(str);
        if (num == null) {
            zzgg().zzil().zzg("Call to endAdUnitExposure for unknown ad unit id", str);
            return;
        }
        zzik zzikVarZzkk = zzfy().zzkk();
        int iIntValue = num.intValue() - 1;
        if (iIntValue != 0) {
            this.zzadb.put(str, Integer.valueOf(iIntValue));
            return;
        }
        this.zzadb.remove(str);
        Long l = this.zzada.get(str);
        if (l == null) {
            zzgg().zzil().log("First ad unit exposure time was never set");
        } else {
            long jLongValue = j - l.longValue();
            this.zzada.remove(str);
            zza(str, jLongValue, zzikVarZzkk);
        }
        if (this.zzadb.isEmpty()) {
            if (this.zzadc == 0) {
                zzgg().zzil().log("First ad exposure time was never set");
            } else {
                zza(j - this.zzadc, zzikVarZzkk);
                this.zzadc = 0L;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzl(long j) {
        Iterator<String> it = this.zzada.keySet().iterator();
        while (it.hasNext()) {
            this.zzada.put(it.next(), Long.valueOf(j));
        }
        if (this.zzada.isEmpty()) {
            return;
        }
        this.zzadc = j;
    }

    public final void beginAdUnitExposure(String str) {
        if (str == null || str.length() == 0) {
            zzgg().zzil().log("Ad unit id must be a non-empty string");
        } else {
            zzgf().zzc(new zzdy(this, str, zzbt().elapsedRealtime()));
        }
    }

    public final void endAdUnitExposure(String str) {
        if (str == null || str.length() == 0) {
            zzgg().zzil().log("Ad unit id must be a non-empty string");
        } else {
            zzgf().zzc(new zzdz(this, str, zzbt().elapsedRealtime()));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfq() {
        super.zzfq();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzdx zzfs() {
        return super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzee zzft() {
        return super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzhm zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfb zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeo zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzil zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzih zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfc zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzei zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfe zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjv zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgf zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjk zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgg zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfg zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfr zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeh zzgi() {
        return super.zzgi();
    }

    public final void zzk(long j) {
        zzik zzikVarZzkk = zzfy().zzkk();
        for (String str : this.zzada.keySet()) {
            zza(str, j - this.zzada.get(str).longValue(), zzikVarZzkk);
        }
        if (!this.zzada.isEmpty()) {
            zza(j - this.zzadc, zzikVarZzkk);
        }
        zzl(j);
    }
}
