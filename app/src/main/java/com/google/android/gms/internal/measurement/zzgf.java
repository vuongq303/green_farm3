package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.v4.e.a;
import android.text.TextUtils;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.measurement.c;
import com.google.android.gms.measurement.f;
import com.google.android.gms.measurement.g;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzgf extends zzhk {
    private static int zzald = 65535;
    private static int zzale = 2;
    private final Map<String, Map<String, String>> zzalf;
    private final Map<String, Map<String, Boolean>> zzalg;
    private final Map<String, Map<String, Boolean>> zzalh;
    private final Map<String, zzkf> zzali;
    private final Map<String, Map<String, Integer>> zzalj;
    private final Map<String, String> zzalk;

    zzgf(zzgl zzglVar) {
        super(zzglVar);
        this.zzalf = new a();
        this.zzalg = new a();
        this.zzalh = new a();
        this.zzali = new a();
        this.zzalk = new a();
        this.zzalj = new a();
    }

    private final zzkf zza(String str, byte[] bArr) {
        if (bArr == null) {
            return new zzkf();
        }
        zzaba zzabaVarZza = zzaba.zza(bArr, 0, bArr.length);
        zzkf zzkfVar = new zzkf();
        try {
            zzkfVar.zzb(zzabaVarZza);
            zzgg().zzir().zze("Parsed config. version, gmp_app_id", zzkfVar.zzask, zzkfVar.zzadh);
            return zzkfVar;
        } catch (IOException e) {
            zzgg().zzin().zze("Unable to merge remote config. appId", zzfg.zzbh(str), e);
            return new zzkf();
        }
    }

    private static Map<String, String> zza(zzkf zzkfVar) {
        a aVar = new a();
        if (zzkfVar != null && zzkfVar.zzasm != null) {
            for (zzkg zzkgVar : zzkfVar.zzasm) {
                if (zzkgVar != null) {
                    aVar.put(zzkgVar.zznt, zzkgVar.value);
                }
            }
        }
        return aVar;
    }

    private final void zza(String str, zzkf zzkfVar) {
        a aVar = new a();
        a aVar2 = new a();
        a aVar3 = new a();
        if (zzkfVar != null && zzkfVar.zzasn != null) {
            for (zzke zzkeVar : zzkfVar.zzasn) {
                if (TextUtils.isEmpty(zzkeVar.name)) {
                    zzgg().zzin().log("EventConfig contained null event name");
                } else {
                    String strIz = c.iz(zzkeVar.name);
                    if (!TextUtils.isEmpty(strIz)) {
                        zzkeVar.name = strIz;
                    }
                    aVar.put(zzkeVar.name, zzkeVar.zzash);
                    aVar2.put(zzkeVar.name, zzkeVar.zzasi);
                    if (zzkeVar.zzasj != null) {
                        if (zzkeVar.zzasj.intValue() < zzale || zzkeVar.zzasj.intValue() > zzald) {
                            zzgg().zzin().zze("Invalid sampling rate. Event name, sample rate", zzkeVar.name, zzkeVar.zzasj);
                        } else {
                            aVar3.put(zzkeVar.name, zzkeVar.zzasj);
                        }
                    }
                }
            }
        }
        this.zzalg.put(str, aVar);
        this.zzalh.put(str, aVar2);
        this.zzalj.put(str, aVar3);
    }

    private final void zzbo(String str) throws Throwable {
        zzch();
        zzab();
        bf.id(str);
        if (this.zzali.get(str) == null) {
            byte[] bArrZzaz = zzga().zzaz(str);
            if (bArrZzaz != null) {
                zzkf zzkfVarZza = zza(str, bArrZzaz);
                this.zzalf.put(str, zza(zzkfVarZza));
                zza(str, zzkfVarZza);
                this.zzali.put(str, zzkfVarZza);
                this.zzalk.put(str, null);
                return;
            }
            this.zzalf.put(str, null);
            this.zzalg.put(str, null);
            this.zzalh.put(str, null);
            this.zzali.put(str, null);
            this.zzalk.put(str, null);
            this.zzalj.put(str, null);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    protected final boolean zza(String str, byte[] bArr, String str2) {
        zzch();
        zzab();
        bf.id(str);
        zzkf zzkfVarZza = zza(str, bArr);
        if (zzkfVarZza == null) {
            return false;
        }
        zza(str, zzkfVarZza);
        this.zzali.put(str, zzkfVarZza);
        this.zzalk.put(str, str2);
        this.zzalf.put(str, zza(zzkfVarZza));
        zzee zzeeVarZzft = zzft();
        zzjy[] zzjyVarArr = zzkfVarZza.zzaso;
        bf.checkNotNull(zzjyVarArr);
        for (zzjy zzjyVar : zzjyVarArr) {
            for (zzjz zzjzVar : zzjyVar.zzari) {
                String strIz = c.iz(zzjzVar.zzarl);
                if (strIz != null) {
                    zzjzVar.zzarl = strIz;
                }
                zzka[] zzkaVarArr = zzjzVar.zzarm;
                for (zzka zzkaVar : zzkaVarArr) {
                    String strIz2 = f.iz(zzkaVar.zzart);
                    if (strIz2 != null) {
                        zzkaVar.zzart = strIz2;
                    }
                }
            }
            for (zzkc zzkcVar : zzjyVar.zzarh) {
                String strIz3 = g.iz(zzkcVar.zzasa);
                if (strIz3 != null) {
                    zzkcVar.zzasa = strIz3;
                }
            }
        }
        zzeeVarZzft.zzga().zza(str, zzjyVarArr);
        try {
            zzkfVarZza.zzaso = null;
            byte[] bArr2 = new byte[zzkfVarZza.zzwg()];
            zzkfVarZza.zza(zzabb.zzb(bArr2, 0, bArr2.length));
            bArr = bArr2;
        } catch (IOException e) {
            zzgg().zzin().zze("Unable to serialize reduced-size config. Storing full config instead. appId", zzfg.zzbh(str), e);
        }
        zzei zzeiVarZzga = zzga();
        bf.id(str);
        zzeiVarZzga.zzab();
        zzeiVarZzga.zzch();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr);
        try {
            if (zzeiVarZzga.getWritableDatabase().update("apps", contentValues, "app_id = ?", new String[]{str}) == 0) {
                zzeiVarZzga.zzgg().zzil().zzg("Failed to update remote config (got 0). appId", zzfg.zzbh(str));
            }
        } catch (SQLiteException e2) {
            zzeiVarZzga.zzgg().zzil().zze("Error storing remote config. appId", zzfg.zzbh(str), e2);
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    protected final zzkf zzbp(String str) {
        zzch();
        zzab();
        bf.id(str);
        zzbo(str);
        return this.zzali.get(str);
    }

    protected final String zzbq(String str) {
        zzab();
        return this.zzalk.get(str);
    }

    protected final void zzbr(String str) {
        zzab();
        this.zzalk.put(str, null);
    }

    final void zzbs(String str) {
        zzab();
        this.zzali.remove(str);
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

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return false;
    }

    final String zzm(String str, String str2) {
        zzab();
        zzbo(str);
        Map<String, String> map = this.zzalf.get(str);
        if (map != null) {
            return map.get(str2);
        }
        return null;
    }

    final boolean zzn(String str, String str2) throws Throwable {
        Boolean bool;
        zzab();
        zzbo(str);
        if (zzgc().zzce(str) && zzjv.zzcb(str2)) {
            return true;
        }
        if (zzgc().zzcf(str) && zzjv.zzbv(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzalg.get(str);
        if (map != null && (bool = map.get(str2)) != null) {
            return bool.booleanValue();
        }
        return false;
    }

    final boolean zzo(String str, String str2) throws Throwable {
        Boolean bool;
        zzab();
        zzbo(str);
        if (FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzalh.get(str);
        if (map != null && (bool = map.get(str2)) != null) {
            return bool.booleanValue();
        }
        return false;
    }

    final int zzp(String str, String str2) throws Throwable {
        Integer num;
        zzab();
        zzbo(str);
        Map<String, Integer> map = this.zzalj.get(str);
        if (map != null && (num = map.get(str2)) != null) {
            return num.intValue();
        }
        return 1;
    }
}
