package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.e.b;
import com.google.android.gms.common.e.c;
import com.google.android.gms.common.stats.a;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzfx {
    private final zzgl zzacr;
    volatile zzr zzakv;
    private ServiceConnection zzakw;

    zzfx(zzgl zzglVar) {
        this.zzacr = zzglVar;
    }

    private final boolean zzje() {
        boolean z = false;
        try {
            b bVarAV = c.aV(this.zzacr.getContext());
            if (bVarAV == null) {
                this.zzacr.zzgg().zzip().log("Failed to retrieve Package Manager to check Play Store compatibility");
            } else if (bVarAV.getPackageInfo("com.android.vending", 128).versionCode >= 80837300) {
                z = true;
            }
        } catch (Exception e) {
            this.zzacr.zzgg().zzip().zzg("Failed to retrieve Play Store version", e);
        }
        return z;
    }

    final void zzc(Bundle bundle) {
        this.zzacr.zzgf().zzab();
        if (bundle == null) {
            return;
        }
        long j = bundle.getLong("install_begin_timestamp_seconds", 0L) * 1000;
        if (j == 0) {
            this.zzacr.zzgg().zzil().log("Service response is missing Install Referrer install timestamp");
            return;
        }
        String string = bundle.getString("install_referrer");
        if (string == null || string.isEmpty()) {
            this.zzacr.zzgg().zzil().log("No referrer defined in install referrer response");
            return;
        }
        this.zzacr.zzgg().zzir().zzg("InstallReferrer API result", string);
        zzjv zzjvVarZzgc = this.zzacr.zzgc();
        String strValueOf = String.valueOf(string);
        Bundle bundleZza = zzjvVarZzgc.zza(Uri.parse(strValueOf.length() != 0 ? "?".concat(strValueOf) : new String("?")));
        if (bundleZza == null) {
            this.zzacr.zzgg().zzil().log("No campaign params defined in install referrer result");
            return;
        }
        String string2 = bundleZza.getString(FirebaseAnalytics.Param.MEDIUM);
        if ((string2 == null || "(not set)".equalsIgnoreCase(string2) || "organic".equalsIgnoreCase(string2)) ? false : true) {
            long j2 = bundle.getLong("referrer_click_timestamp_seconds", 0L) * 1000;
            if (j2 == 0) {
                this.zzacr.zzgg().zzil().log("Install Referrer is missing click timestamp for ad campaign");
                return;
            }
            bundleZza.putLong("click_timestamp", j2);
        }
        if (j == this.zzacr.zzgh().zzajz.get()) {
            this.zzacr.zzgg().zzir().log("Campaign has already been logged");
            return;
        }
        bundleZza.putString("_cis", "referrer API");
        this.zzacr.zzgh().zzajz.set(j);
        this.zzacr.zzfu().zza("auto", "_cmp", bundleZza);
        if (this.zzakw != null) {
            a.aaY().a(this.zzacr.getContext(), this.zzakw);
        }
    }

    protected final void zzjd() {
        zzfy zzfyVar = null;
        this.zzacr.zzgf().zzab();
        if (!zzje()) {
            this.zzacr.zzgg().zzip().log("Install Referrer Reporter is not available");
            this.zzakw = null;
            return;
        }
        this.zzakw = new zzfz(this);
        this.zzacr.zzgg().zzip().log("Install Referrer Reporter is initializing");
        this.zzacr.zzgf().zzab();
        Intent intent = new Intent("com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE");
        intent.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.externalreferrer.GetInstallReferrerService"));
        PackageManager packageManager = this.zzacr.getContext().getPackageManager();
        if (packageManager == null) {
            this.zzacr.zzgg().zzin().log("Failed to obtain Package Manager to verify binding conditions");
            return;
        }
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
            this.zzacr.zzgg().zzip().log("Play Service for fetching Install Referrer is unavailable on device");
            return;
        }
        ResolveInfo resolveInfo = listQueryIntentServices.get(0);
        if (resolveInfo.serviceInfo != null) {
            String str = resolveInfo.serviceInfo.packageName;
            if (resolveInfo.serviceInfo.name == null || this.zzakw == null || !"com.android.vending".equals(str) || !zzje()) {
                this.zzacr.zzgg().zzip().log("Play Store missing or incompatible. Version 8.3.73 or later required");
                return;
            }
            try {
                this.zzacr.zzgg().zzip().zzg("Install Referrer Service is", a.aaY().a(this.zzacr.getContext(), new Intent(intent), this.zzakw, 1) ? "available" : "not available");
            } catch (Exception e) {
                this.zzacr.zzgg().zzil().zzg("Exception occurred while binding to Install Referrer Service", e.getMessage());
            }
        }
    }

    final Bundle zzjf() {
        this.zzacr.zzgf().zzab();
        if (this.zzakv == null) {
            this.zzacr.zzgg().zzin().log("Attempting to use Install Referrer Service while it is not initialized");
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("package_name", this.zzacr.getContext().getPackageName());
        try {
            Bundle bundleZza = this.zzakv.zza(bundle);
            if (bundleZza != null) {
                return bundleZza;
            }
            this.zzacr.zzgg().zzil().log("Install Referrer Service returned a null response");
            return null;
        } catch (Exception e) {
            this.zzacr.zzgg().zzil().zzg("Exception occurred while retrieving the Install Referrer", e.getMessage());
            return null;
        }
    }
}
