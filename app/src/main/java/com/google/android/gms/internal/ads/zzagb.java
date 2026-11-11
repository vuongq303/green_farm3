package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.d.i;
import com.google.android.gms.common.d.s;
import com.google.android.gms.common.e.c;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class zzagb {
    private float zzagu;
    private int zzcde;
    private int zzcdf;
    private int zzcjk;
    private boolean zzcjl;
    private boolean zzcjm;
    private String zzcjn;
    private String zzcjo;
    private boolean zzcjp;
    private boolean zzcjq;
    private boolean zzcjr;
    private boolean zzcjs;
    private String zzcjt;
    private String zzcju;
    private String zzcjv;
    private int zzcjw;
    private int zzcjx;
    private int zzcjy;
    private int zzcjz;
    private int zzcka;
    private int zzckb;
    private double zzckc;
    private boolean zzckd;
    private boolean zzcke;
    private int zzckf;
    private String zzckg;
    private String zzckh;
    private boolean zzcki;

    public zzagb(Context context) {
        DisplayMetrics displayMetrics;
        PackageManager packageManager = context.getPackageManager();
        zzn(context);
        zzo(context);
        zzp(context);
        Locale locale = Locale.getDefault();
        this.zzcjl = zza(packageManager, "geo:0,0?q=donuts") != null;
        this.zzcjm = zza(packageManager, "http://www.google.com") != null;
        this.zzcjo = locale.getCountry();
        zzkb.zzif();
        this.zzcjp = zzamu.zzsg();
        this.zzcjq = i.aO(context);
        this.zzcjt = locale.getLanguage();
        this.zzcju = zzb(context, packageManager);
        this.zzcjv = zza(context, packageManager);
        Resources resources = context.getResources();
        if (resources == null || (displayMetrics = resources.getDisplayMetrics()) == null) {
            return;
        }
        this.zzagu = displayMetrics.density;
        this.zzcde = displayMetrics.widthPixels;
        this.zzcdf = displayMetrics.heightPixels;
    }

    public zzagb(Context context, zzaga zzagaVar) {
        context.getPackageManager();
        zzn(context);
        zzo(context);
        zzp(context);
        this.zzckg = Build.FINGERPRINT;
        this.zzckh = Build.DEVICE;
        this.zzcki = s.abw() && zzoh.zzh(context);
        this.zzcjl = zzagaVar.zzcjl;
        this.zzcjm = zzagaVar.zzcjm;
        this.zzcjo = zzagaVar.zzcjo;
        this.zzcjp = zzagaVar.zzcjp;
        this.zzcjq = zzagaVar.zzcjq;
        this.zzcjt = zzagaVar.zzcjt;
        this.zzcju = zzagaVar.zzcju;
        this.zzcjv = zzagaVar.zzcjv;
        this.zzagu = zzagaVar.zzagu;
        this.zzcde = zzagaVar.zzcde;
        this.zzcdf = zzagaVar.zzcdf;
    }

    private static ResolveInfo zza(PackageManager packageManager, String str) {
        try {
            return packageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)), NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(th, "DeviceInfo.getResolveInfo");
            return null;
        }
    }

    private static String zza(Context context, PackageManager packageManager) {
        try {
            PackageInfo packageInfo = c.aV(context).getPackageInfo("com.android.vending", 128);
            if (packageInfo == null) {
                return null;
            }
            int i = packageInfo.versionCode;
            String str = packageInfo.packageName;
            return new StringBuilder(String.valueOf(str).length() + 12).append(i).append(".").append(str).toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static String zzb(Context context, PackageManager packageManager) {
        ActivityInfo activityInfo;
        ResolveInfo resolveInfoZza = zza(packageManager, "market://details?id=com.google.android.gms.ads");
        if (resolveInfoZza == null || (activityInfo = resolveInfoZza.activityInfo) == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = c.aV(context).getPackageInfo(activityInfo.packageName, 0);
            if (packageInfo == null) {
                return null;
            }
            int i = packageInfo.versionCode;
            String str = activityInfo.packageName;
            return new StringBuilder(String.valueOf(str).length() + 12).append(i).append(".").append(str).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private final void zzn(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (audioManager != null) {
            try {
                this.zzcjk = audioManager.getMode();
                this.zzcjr = audioManager.isMusicActive();
                this.zzcjs = audioManager.isSpeakerphoneOn();
                this.zzcjw = audioManager.getStreamVolume(3);
                this.zzcka = audioManager.getRingerMode();
                this.zzckb = audioManager.getStreamVolume(2);
                return;
            } catch (Throwable th) {
                com.google.android.gms.ads.internal.zzbv.zzeo().zza(th, "DeviceInfo.gatherAudioInfo");
            }
        }
        this.zzcjk = -2;
        this.zzcjr = false;
        this.zzcjs = false;
        this.zzcjw = 0;
        this.zzcka = 0;
        this.zzckb = 0;
    }

    private final void zzo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.zzcjn = telephonyManager.getNetworkOperator();
        this.zzcjy = telephonyManager.getNetworkType();
        this.zzcjz = telephonyManager.getPhoneType();
        this.zzcjx = -2;
        this.zzcke = false;
        this.zzckf = -1;
        com.google.android.gms.ads.internal.zzbv.zzek();
        if (zzakk.zzl(context, "android.permission.ACCESS_NETWORK_STATE")) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                this.zzcjx = activeNetworkInfo.getType();
                this.zzckf = activeNetworkInfo.getDetailedState().ordinal();
            } else {
                this.zzcjx = -1;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                this.zzcke = connectivityManager.isActiveNetworkMetered();
            }
        }
    }

    private final void zzp(Context context) {
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (intentRegisterReceiver == null) {
            this.zzckc = -1.0d;
            this.zzckd = false;
        } else {
            int intExtra = intentRegisterReceiver.getIntExtra(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, -1);
            this.zzckc = intentRegisterReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1) / intentRegisterReceiver.getIntExtra("scale", -1);
            this.zzckd = intExtra == 2 || intExtra == 5;
        }
    }

    public final zzaga zzoo() {
        return new zzaga(this.zzcjk, this.zzcjl, this.zzcjm, this.zzcjn, this.zzcjo, this.zzcjp, this.zzcjq, this.zzcjr, this.zzcjs, this.zzcjt, this.zzcju, this.zzcjv, this.zzcjw, this.zzcjx, this.zzcjy, this.zzcjz, this.zzcka, this.zzckb, this.zzagu, this.zzcde, this.zzcdf, this.zzckc, this.zzckd, this.zzcke, this.zzckf, this.zzckg, this.zzcki, this.zzckh);
    }
}
