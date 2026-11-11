package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.r;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.e.a;
import com.google.firebase.iid.FirebaseInstanceId;
import java.math.BigInteger;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class zzfb extends zzhk {
    private String zzadh;
    private String zzado;
    private long zzads;
    private int zzaei;
    private int zzaie;
    private long zzaif;
    private String zztb;
    private String zztc;
    private String zztd;

    zzfb(zzgl zzglVar) {
        super(zzglVar);
    }

    private final String zzgl() {
        zzab();
        if (zzgi().zzd(this.zztd, zzew.zzaib) && !this.zzacr.isEnabled()) {
            return null;
        }
        try {
            return FirebaseInstanceId.getInstance().getId();
        } catch (IllegalStateException e) {
            zzgg().zzin().log("Failed to retrieve Firebase Instance Id");
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    final String getGmpAppId() {
        zzch();
        return this.zzadh;
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    final String zzah() {
        zzch();
        return this.zztd;
    }

    final zzec zzbd(String str) {
        zzab();
        String strZzah = zzah();
        String gmpAppId = getGmpAppId();
        zzch();
        String str2 = this.zztc;
        long jZzii = zzii();
        zzch();
        String str3 = this.zzado;
        zzch();
        zzab();
        if (this.zzaif == 0) {
            this.zzaif = this.zzacr.zzgc().zzd(getContext(), getContext().getPackageName());
        }
        long j = this.zzaif;
        boolean zIsEnabled = this.zzacr.isEnabled();
        boolean z = !zzgh().zzakm;
        String strZzgl = zzgl();
        zzch();
        long jZzju = this.zzacr.zzju();
        int iZzij = zzij();
        Boolean boolZzas = zzgi().zzas("google_analytics_adid_collection_enabled");
        boolean zBooleanValue = Boolean.valueOf(boolZzas == null || boolZzas.booleanValue()).booleanValue();
        Boolean boolZzas2 = zzgi().zzas("google_analytics_ssaid_collection_enabled");
        return new zzec(strZzah, gmpAppId, str2, jZzii, str3, 12451L, j, str, zIsEnabled, z, strZzgl, 0L, jZzju, iZzij, zBooleanValue, Boolean.valueOf(boolZzas2 == null || boolZzas2.booleanValue()).booleanValue(), zzgh().zzja());
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
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final void zzig() {
        boolean z;
        String installerPackageName = "unknown";
        String str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        int i = Integer.MIN_VALUE;
        String string = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        String packageName = getContext().getPackageName();
        PackageManager packageManager = getContext().getPackageManager();
        if (packageManager == null) {
            zzgg().zzil().zzg("PackageManager is null, app identity information might be inaccurate. appId", zzfg.zzbh(packageName));
        } else {
            try {
                installerPackageName = packageManager.getInstallerPackageName(packageName);
            } catch (IllegalArgumentException e) {
                zzgg().zzil().zzg("Error retrieving app installer package name. appId", zzfg.zzbh(packageName));
            }
            if (installerPackageName == null) {
                installerPackageName = "manual_install";
            } else if ("com.android.vending".equals(installerPackageName)) {
                installerPackageName = "";
            }
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
                if (packageInfo != null) {
                    CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                    if (!TextUtils.isEmpty(applicationLabel)) {
                        string = applicationLabel.toString();
                    }
                    str = packageInfo.versionName;
                    i = packageInfo.versionCode;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                zzgg().zzil().zze("Error retrieving package info. appId, appName", zzfg.zzbh(packageName), string);
            }
        }
        this.zztd = packageName;
        this.zzado = installerPackageName;
        this.zztc = str;
        this.zzaie = i;
        this.zztb = string;
        this.zzaif = 0L;
        Status statusAE = r.aE(getContext());
        boolean z2 = statusAE != null && statusAE.Sp();
        if (!z2) {
            if (statusAE == null) {
                zzgg().zzil().log("GoogleService failed to initialize (no status)");
            } else {
                zzgg().zzil().zze("GoogleService failed to initialize, status", Integer.valueOf(statusAE.getStatusCode()), statusAE.YR());
            }
        }
        if (z2) {
            Boolean boolZzas = zzgi().zzas("firebase_analytics_collection_enabled");
            if (zzgi().zzhi()) {
                zzgg().zzip().log("Collection disabled with firebase_analytics_collection_deactivated=1");
                z = false;
            } else if (boolZzas != null && !boolZzas.booleanValue()) {
                zzgg().zzip().log("Collection disabled with firebase_analytics_collection_enabled=0");
                z = false;
            } else if (boolZzas == null && r.Zi()) {
                zzgg().zzip().log("Collection disabled with google_app_measurement_enable=0");
                z = false;
            } else {
                zzgg().zzir().log("Collection enabled");
                z = true;
            }
        } else {
            z = false;
        }
        this.zzadh = "";
        this.zzads = 0L;
        try {
            String strZh = r.Zh();
            if (TextUtils.isEmpty(strZh)) {
                strZh = "";
            }
            this.zzadh = strZh;
            if (z) {
                zzgg().zzir().zze("App package, google app id", this.zztd, this.zzadh);
            }
        } catch (IllegalStateException e3) {
            zzgg().zzil().zze("getGoogleAppId or isMeasurementEnabled failed with exception. appId", zzfg.zzbh(packageName), e3);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.zzaei = a.aT(getContext()) ? 1 : 0;
        } else {
            this.zzaei = 0;
        }
    }

    final String zzih() {
        byte[] bArr = new byte[16];
        zzgc().zzku().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new BigInteger(1, bArr));
    }

    final int zzii() {
        zzch();
        return this.zzaie;
    }

    final int zzij() {
        zzch();
        return this.zzaei;
    }
}
