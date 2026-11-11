package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.e.a;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.measurement.h;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzih extends zzhk {
    protected zzik zzapc;
    private volatile zzig zzapd;
    private zzig zzape;
    private long zzapf;
    private final Map<Activity, zzik> zzapg;
    private final CopyOnWriteArrayList<h> zzaph;
    private boolean zzapi;
    private zzig zzapj;
    private String zzapk;

    public zzih(zzgl zzglVar) {
        super(zzglVar);
        this.zzapg = new a();
        this.zzaph = new CopyOnWriteArrayList<>();
    }

    private final void zza(Activity activity, zzik zzikVar, boolean z) {
        zzig zzigVar;
        boolean z2;
        boolean zA = true;
        if (this.zzapd != null) {
            zzigVar = this.zzapd;
        } else {
            zzigVar = (this.zzape == null || Math.abs(zzbt().elapsedRealtime() - this.zzapf) >= 1000) ? null : this.zzape;
        }
        zzig zzigVar2 = zzigVar != null ? new zzig(zzigVar) : null;
        this.zzapi = true;
        try {
            try {
                Iterator<h> it = this.zzaph.iterator();
                while (it.hasNext()) {
                    try {
                        zA &= it.next().a(zzigVar2, zzikVar);
                    } catch (Exception e) {
                        zzgg().zzil().zzg("onScreenChangeCallback threw exception", e);
                    }
                }
                this.zzapi = false;
                z2 = zA;
            } catch (Exception e2) {
                z2 = zA;
                zzgg().zzil().zzg("onScreenChangeCallback loop threw exception", e2);
                this.zzapi = false;
            }
            zzig zzigVar3 = this.zzapd == null ? this.zzape : this.zzapd;
            if (z2) {
                if (zzikVar.zzapa == null) {
                    zzikVar.zzapa = zzbu(activity.getClass().getCanonicalName());
                }
                zzik zzikVar2 = new zzik(zzikVar);
                this.zzape = this.zzapd;
                this.zzapf = zzbt().elapsedRealtime();
                this.zzapd = zzikVar2;
                zzgf().zzc(new zzii(this, z, zzigVar3, zzikVar2));
            }
        } catch (Throwable th) {
            this.zzapi = false;
            throw th;
        }
    }

    public static void zza(zzig zzigVar, Bundle bundle, boolean z) {
        if (bundle != null && zzigVar != null && (!bundle.containsKey("_sc") || z)) {
            if (zzigVar.zzug != null) {
                bundle.putString("_sn", zzigVar.zzug);
            } else {
                bundle.remove("_sn");
            }
            bundle.putString("_sc", zzigVar.zzapa);
            bundle.putLong("_si", zzigVar.zzapb);
            return;
        }
        if (bundle != null && zzigVar == null && z) {
            bundle.remove("_sn");
            bundle.remove("_sc");
            bundle.remove("_si");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzik zzikVar) {
        zzfs().zzk(zzbt().elapsedRealtime());
        if (zzge().zzm(zzikVar.zzapq)) {
            zzikVar.zzapq = false;
        }
    }

    private static String zzbu(String str) {
        String[] strArrSplit = str.split("\\.");
        String str2 = strArrSplit.length > 0 ? strArrSplit[strArrSplit.length - 1] : "";
        return str2.length() > 100 ? str2.substring(0, 100) : str2;
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzapg.remove(activity);
    }

    public final void onActivityPaused(Activity activity) {
        zzik zzikVarZze = zze(activity);
        this.zzape = this.zzapd;
        this.zzapf = zzbt().elapsedRealtime();
        this.zzapd = null;
        zzgf().zzc(new zzij(this, zzikVarZze));
    }

    public final void onActivityResumed(Activity activity) {
        zza(activity, zze(activity), false);
        zzdx zzdxVarZzfs = zzfs();
        zzdxVarZzfs.zzgf().zzc(new zzea(zzdxVarZzfs, zzdxVarZzfs.zzbt().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zzik zzikVar;
        if (bundle == null || (zzikVar = this.zzapg.get(activity)) == null) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putLong(ShareConstants.WEB_DIALOG_PARAM_ID, zzikVar.zzapb);
        bundle2.putString("name", zzikVar.zzug);
        bundle2.putString("referrer_name", zzikVar.zzapa);
        bundle.putBundle("com.google.firebase.analytics.screen_service", bundle2);
    }

    public final void registerOnScreenChangeCallback(h hVar) {
        if (hVar == null) {
            zzgg().zzin().log("Attempting to register null OnScreenChangeCallback");
        } else {
            this.zzaph.remove(hVar);
            this.zzaph.add(hVar);
        }
    }

    public final void setCurrentScreen(Activity activity, String str, String str2) {
        zzgf();
        if (!zzgg.isMainThread()) {
            zzgg().zzin().log("setCurrentScreen must be called from the main thread");
            return;
        }
        if (this.zzapi) {
            zzgg().zzin().log("Cannot call setCurrentScreen from onScreenChangeCallback");
            return;
        }
        if (this.zzapd == null) {
            zzgg().zzin().log("setCurrentScreen cannot be called while no activity active");
            return;
        }
        if (this.zzapg.get(activity) == null) {
            zzgg().zzin().log("setCurrentScreen must be called with an activity in the activity lifecycle");
            return;
        }
        if (str2 == null) {
            str2 = zzbu(activity.getClass().getCanonicalName());
        }
        boolean zEquals = this.zzapd.zzapa.equals(str2);
        boolean zZzs = zzjv.zzs(this.zzapd.zzug, str);
        if (zEquals && zZzs) {
            zzgg().zzio().log("setCurrentScreen cannot be called with the same class and name");
            return;
        }
        if (str != null && (str.length() <= 0 || str.length() > 100)) {
            zzgg().zzin().zzg("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            return;
        }
        if (str2 != null && (str2.length() <= 0 || str2.length() > 100)) {
            zzgg().zzin().zzg("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            return;
        }
        zzgg().zzir().zze("Setting current screen to name, class", str == null ? "null" : str, str2);
        zzik zzikVar = new zzik(str, str2, zzgc().zzkt());
        this.zzapg.put(activity, zzikVar);
        zza(activity, zzikVar, true);
    }

    public final void unregisterOnScreenChangeCallback(h hVar) {
        this.zzaph.remove(hVar);
    }

    public final void zza(String str, zzig zzigVar) {
        zzab();
        synchronized (this) {
            if (this.zzapk == null || this.zzapk.equals(str) || zzigVar != null) {
                this.zzapk = str;
                this.zzapj = zzigVar;
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    final zzik zze(Activity activity) {
        bf.checkNotNull(activity);
        zzik zzikVar = this.zzapg.get(activity);
        if (zzikVar != null) {
            return zzikVar;
        }
        zzik zzikVar2 = new zzik(null, zzbu(activity.getClass().getCanonicalName()), zzgc().zzkt());
        this.zzapg.put(activity, zzikVar2);
        return zzikVar2;
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

    public final zzik zzkk() {
        zzch();
        zzab();
        return this.zzapc;
    }

    public final zzig zzkl() {
        zzig zzigVar = this.zzapd;
        if (zzigVar == null) {
            return null;
        }
        return new zzig(zzigVar);
    }
}
