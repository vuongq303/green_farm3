package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.d.u;
import com.google.android.gms.common.e.c;
import com.google.android.gms.common.internal.bf;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public final class zzeh extends zzhj {
    private Boolean zzxu;

    zzeh(zzgl zzglVar) {
        super(zzglVar);
    }

    public static long zzhj() {
        return zzew.zzaho.get().longValue();
    }

    public static long zzhk() {
        return zzew.zzago.get().longValue();
    }

    public static boolean zzhm() {
        return zzew.zzagj.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final long zza(String str, zzex<Long> zzexVar) {
        if (str == null) {
            return zzexVar.get().longValue();
        }
        String strZzm = zzgd().zzm(str, zzexVar.getKey());
        if (TextUtils.isEmpty(strZzm)) {
            return zzexVar.get().longValue();
        }
        try {
            return zzexVar.get(Long.valueOf(Long.parseLong(strZzm))).longValue();
        } catch (NumberFormatException e) {
            return zzexVar.get().longValue();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    public final int zzar(String str) {
        return zzb(str, zzew.zzagz);
    }

    final Boolean zzas(String str) {
        Boolean boolValueOf = null;
        bf.id(str);
        try {
            if (getContext().getPackageManager() == null) {
                zzgg().zzil().log("Failed to load metadata: PackageManager is null");
            } else {
                ApplicationInfo applicationInfo = c.aV(getContext()).getApplicationInfo(getContext().getPackageName(), 128);
                if (applicationInfo == null) {
                    zzgg().zzil().log("Failed to load metadata: ApplicationInfo is null");
                } else if (applicationInfo.metaData == null) {
                    zzgg().zzil().log("Failed to load metadata: Metadata bundle is null");
                } else if (applicationInfo.metaData.containsKey(str)) {
                    boolValueOf = Boolean.valueOf(applicationInfo.metaData.getBoolean(str));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            zzgg().zzil().zzg("Failed to load metadata: Package name not found", e);
        }
        return boolValueOf;
    }

    public final boolean zzat(String str) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zzgd().zzm(str, "gaia_collection_enabled"));
    }

    final boolean zzau(String str) {
        return zzd(str, zzew.zzahx);
    }

    final boolean zzav(String str) {
        return zzd(str, zzew.zzaic);
    }

    public final int zzb(String str, zzex<Integer> zzexVar) {
        if (str == null) {
            return zzexVar.get().intValue();
        }
        String strZzm = zzgd().zzm(str, zzexVar.getKey());
        if (TextUtils.isEmpty(strZzm)) {
            return zzexVar.get().intValue();
        }
        try {
            return zzexVar.get(Integer.valueOf(Integer.parseInt(strZzm))).intValue();
        } catch (NumberFormatException e) {
            return zzexVar.get().intValue();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    public final double zzc(String str, zzex<Double> zzexVar) {
        if (str == null) {
            return zzexVar.get().doubleValue();
        }
        String strZzm = zzgd().zzm(str, zzexVar.getKey());
        if (TextUtils.isEmpty(strZzm)) {
            return zzexVar.get().doubleValue();
        }
        try {
            return zzexVar.get(Double.valueOf(Double.parseDouble(strZzm))).doubleValue();
        } catch (NumberFormatException e) {
            return zzexVar.get().doubleValue();
        }
    }

    public final boolean zzd(String str, zzex<Boolean> zzexVar) {
        if (str == null) {
            return zzexVar.get().booleanValue();
        }
        String strZzm = zzgd().zzm(str, zzexVar.getKey());
        return TextUtils.isEmpty(strZzm) ? zzexVar.get().booleanValue() : zzexVar.get(Boolean.valueOf(Boolean.parseBoolean(strZzm))).booleanValue();
    }

    public final boolean zzds() {
        if (this.zzxu == null) {
            synchronized (this) {
                if (this.zzxu == null) {
                    ApplicationInfo applicationInfo = getContext().getApplicationInfo();
                    String strAbF = u.abF();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzxu = Boolean.valueOf(str != null && str.equals(strAbF));
                    }
                    if (this.zzxu == null) {
                        this.zzxu = Boolean.TRUE;
                        zzgg().zzil().log("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzxu.booleanValue();
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

    public final boolean zzhi() {
        Boolean boolZzas = zzas("firebase_analytics_collection_deactivated");
        return boolZzas != null && boolZzas.booleanValue();
    }

    public final String zzhl() {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class).invoke(null, "debug.firebase.analytics.app", "");
        } catch (ClassNotFoundException e) {
            zzgg().zzil().zzg("Could not find SystemProperties class", e);
            return "";
        } catch (IllegalAccessException e2) {
            zzgg().zzil().zzg("Could not access SystemProperties.get()", e2);
            return "";
        } catch (NoSuchMethodException e3) {
            zzgg().zzil().zzg("Could not find SystemProperties.get() method", e3);
            return "";
        } catch (InvocationTargetException e4) {
            zzgg().zzil().zzg("SystemProperties.get() threw an exception", e4);
            return "";
        }
    }
}
