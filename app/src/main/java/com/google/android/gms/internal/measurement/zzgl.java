package com.google.android.gms.internal.measurement;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.api.a.r;
import com.google.android.gms.common.d.b;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.d.h;
import com.google.android.gms.common.e.c;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.measurement.a;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class zzgl {
    private static volatile zzgl zzamc;
    private final long zzaeh;
    private final zzeh zzamd;
    private final zzfr zzame;
    private final zzfg zzamf;
    private final zzgg zzamg;
    private final zzjk zzamh;
    private final zzgf zzami;
    private final a zzamj;
    private final FirebaseAnalytics zzamk;
    private final zzjv zzaml;
    private final zzfe zzamm;
    private final zzfk zzamn;
    private final zzih zzamo;
    private final zzhm zzamp;
    private final zzdx zzamq;
    private zzei zzamr;
    private zzfc zzams;
    private zzil zzamt;
    private zzeo zzamu;
    private zzfb zzamv;
    private zzfp zzamw;
    private zzjq zzamx;
    private zzee zzamy;
    private zzfx zzamz;
    private boolean zzana;
    private Boolean zzanb;
    private long zzanc;
    private FileLock zzand;
    private FileChannel zzane;
    private List<Long> zzanf;
    private List<Runnable> zzang;
    private int zzanh;
    private int zzani;
    private long zzanj;
    private long zzank;
    private boolean zzanl;
    private boolean zzanm;
    private boolean zzann;
    private final Context zzqs;
    private final e zzrj;
    private boolean zzvj = false;

    final class zza implements zzek {
        zzkl zzans;
        List<Long> zzant;
        List<zzki> zzanu;
        private long zzanv;

        private zza() {
        }

        /* synthetic */ zza(zzgl zzglVar, zzgm zzgmVar) {
            this();
        }

        private static long zza(zzki zzkiVar) {
            return ((zzkiVar.zzasw.longValue() / 1000) / 60) / 60;
        }

        @Override // com.google.android.gms.internal.measurement.zzek
        public final boolean zza(long j, zzki zzkiVar) {
            bf.checkNotNull(zzkiVar);
            if (this.zzanu == null) {
                this.zzanu = new ArrayList();
            }
            if (this.zzant == null) {
                this.zzant = new ArrayList();
            }
            if (this.zzanu.size() > 0 && zza(this.zzanu.get(0)) != zza(zzkiVar)) {
                return false;
            }
            long jZzwg = this.zzanv + ((long) zzkiVar.zzwg());
            if (jZzwg >= Math.max(0, zzew.zzagt.get().intValue())) {
                return false;
            }
            this.zzanv = jZzwg;
            this.zzanu.add(zzkiVar);
            this.zzant.add(Long.valueOf(j));
            return this.zzanu.size() < Math.max(1, zzew.zzagu.get().intValue());
        }

        @Override // com.google.android.gms.internal.measurement.zzek
        public final void zzb(zzkl zzklVar) {
            bf.checkNotNull(zzklVar);
            this.zzans = zzklVar;
        }
    }

    private zzgl(zzhl zzhlVar) {
        bf.checkNotNull(zzhlVar);
        this.zzqs = zzhlVar.zzqs;
        this.zzanj = -1L;
        this.zzrj = h.abr();
        this.zzaeh = this.zzrj.currentTimeMillis();
        this.zzamd = new zzeh(this);
        zzfr zzfrVar = new zzfr(this);
        zzfrVar.zzm();
        this.zzame = zzfrVar;
        zzfg zzfgVar = new zzfg(this);
        zzfgVar.zzm();
        this.zzamf = zzfgVar;
        zzjv zzjvVar = new zzjv(this);
        zzjvVar.zzm();
        this.zzaml = zzjvVar;
        zzfe zzfeVar = new zzfe(this);
        zzfeVar.zzm();
        this.zzamm = zzfeVar;
        this.zzamq = new zzdx(this);
        zzfk zzfkVar = new zzfk(this);
        zzfkVar.zzm();
        this.zzamn = zzfkVar;
        zzih zzihVar = new zzih(this);
        zzihVar.zzm();
        this.zzamo = zzihVar;
        zzhm zzhmVar = new zzhm(this);
        zzhmVar.zzm();
        this.zzamp = zzhmVar;
        this.zzamj = new a(this);
        this.zzamk = new FirebaseAnalytics(this);
        zzjk zzjkVar = new zzjk(this);
        zzjkVar.zzm();
        this.zzamh = zzjkVar;
        zzgf zzgfVar = new zzgf(this);
        zzgfVar.zzm();
        this.zzami = zzgfVar;
        zzgg zzggVar = new zzgg(this);
        zzggVar.zzm();
        this.zzamg = zzggVar;
        if (this.zzqs.getApplicationContext() instanceof Application) {
            zzhm zzhmVarZzfu = zzfu();
            if (zzhmVarZzfu.getContext().getApplicationContext() instanceof Application) {
                Application application = (Application) zzhmVarZzfu.getContext().getApplicationContext();
                if (zzhmVarZzfu.zzaoi == null) {
                    zzhmVarZzfu.zzaoi = new zzif(zzhmVarZzfu, null);
                }
                application.unregisterActivityLifecycleCallbacks(zzhmVarZzfu.zzaoi);
                application.registerActivityLifecycleCallbacks(zzhmVarZzfu.zzaoi);
                zzhmVarZzfu.zzgg().zzir().log("Registered activity lifecycle callback");
            }
        } else {
            zzgg().zzin().log("Application context is not an Application");
        }
        this.zzamg.zzc(new zzgm(this, zzhlVar));
    }

    private final int zza(FileChannel fileChannel) {
        int i = 0;
        zzgf().zzab();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzgg().zzil().log("Bad channel to read from");
        } else {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
            try {
                fileChannel.position(0L);
                int i2 = fileChannel.read(byteBufferAllocate);
                if (i2 == 4) {
                    byteBufferAllocate.flip();
                    i = byteBufferAllocate.getInt();
                } else if (i2 != -1) {
                    zzgg().zzin().zzg("Unexpected data length. Bytes read", Integer.valueOf(i2));
                }
            } catch (IOException e) {
                zzgg().zzil().zzg("Failed to read from channel", e);
            }
        }
        return i;
    }

    private final zzec zza(Context context, String str, String str2, boolean z, boolean z2, boolean z3) {
        String string;
        String installerPackageName = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        String str3 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        int i = Integer.MIN_VALUE;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            zzgg().zzil().log("PackageManager is null, can not log app install information");
            return null;
        }
        try {
            installerPackageName = packageManager.getInstallerPackageName(str);
        } catch (IllegalArgumentException e) {
            zzgg().zzil().zzg("Error retrieving installer package name. appId", zzfg.zzbh(str));
        }
        if (installerPackageName == null) {
            installerPackageName = "manual_install";
        } else if ("com.android.vending".equals(installerPackageName)) {
            installerPackageName = "";
        }
        try {
            PackageInfo packageInfo = c.aV(context).getPackageInfo(str, 0);
            if (packageInfo != null) {
                CharSequence charSequenceIn = c.aV(context).in(str);
                string = !TextUtils.isEmpty(charSequenceIn) ? charSequenceIn.toString() : AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                try {
                    str3 = packageInfo.versionName;
                    i = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e2) {
                    zzgg().zzil().zze("Error retrieving newly installed package info. appId, appName", zzfg.zzbh(str), string);
                    return null;
                }
            }
            return new zzec(str, str2, str3, i, installerPackageName, 12451L, zzgc().zzd(context, str), (String) null, z, false, "", 0L, 0L, 0, z2, z3, false);
        } catch (PackageManager.NameNotFoundException e3) {
            string = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    private static void zza(zzhj zzhjVar) {
        if (zzhjVar == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static void zza(zzhk zzhkVar) {
        if (zzhkVar == null) {
            throw new IllegalStateException("Component not created");
        }
        if (zzhkVar.isInitialized()) {
            return;
        }
        String strValueOf = String.valueOf(zzhkVar.getClass());
        throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 27).append("Component not initialized: ").append(strValueOf).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzhl zzhlVar) {
        zzfi zzfiVarZzip;
        String strConcat;
        zzgf().zzab();
        zzeo zzeoVar = new zzeo(this);
        zzeoVar.zzm();
        this.zzamu = zzeoVar;
        zzfb zzfbVar = new zzfb(this);
        zzfbVar.zzm();
        this.zzamv = zzfbVar;
        zzei zzeiVar = new zzei(this);
        zzeiVar.zzm();
        this.zzamr = zzeiVar;
        zzfc zzfcVar = new zzfc(this);
        zzfcVar.zzm();
        this.zzams = zzfcVar;
        zzee zzeeVar = new zzee(this);
        zzeeVar.zzm();
        this.zzamy = zzeeVar;
        zzil zzilVar = new zzil(this);
        zzilVar.zzm();
        this.zzamt = zzilVar;
        zzjq zzjqVar = new zzjq(this);
        zzjqVar.zzm();
        this.zzamx = zzjqVar;
        this.zzamw = new zzfp(this);
        this.zzaml.zzkd();
        this.zzame.zzkd();
        this.zzamz = new zzfx(this);
        this.zzamv.zzkd();
        zzgg().zzip().zzg("App measurement is starting up, version", 12451L);
        zzgg().zzip().log("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String strZzah = zzfbVar.zzah();
        if (zzgc().zzcc(strZzah)) {
            zzfiVarZzip = zzgg().zzip();
            strConcat = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
        } else {
            zzfiVarZzip = zzgg().zzip();
            String strValueOf = String.valueOf(strZzah);
            strConcat = strValueOf.length() != 0 ? "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(strValueOf) : new String("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ");
        }
        zzfiVarZzip.log(strConcat);
        zzgg().zziq().log("Debug-level message logging enabled");
        if (this.zzanh != this.zzani) {
            zzgg().zzil().zze("Not all components initialized", Integer.valueOf(this.zzanh), Integer.valueOf(this.zzani));
        }
        this.zzvj = true;
    }

    private final boolean zza(int i, FileChannel fileChannel) {
        zzgf().zzab();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzgg().zzil().log("Bad channel to read from");
            return false;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.putInt(i);
        byteBufferAllocate.flip();
        try {
            fileChannel.truncate(0L);
            fileChannel.write(byteBufferAllocate);
            fileChannel.force(true);
            if (fileChannel.size() == 4) {
                return true;
            }
            zzgg().zzil().zzg("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            return true;
        } catch (IOException e) {
            zzgg().zzil().zzg("Failed to write to channel", e);
            return false;
        }
    }

    private final boolean zza(String str, zzeu zzeuVar) {
        long jLongValue;
        zzju zzjuVar;
        String string = zzeuVar.zzafo.getString("currency");
        if (FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(zzeuVar.name)) {
            double dDoubleValue = zzeuVar.zzafo.zzbc(FirebaseAnalytics.Param.VALUE).doubleValue() * 1000000.0d;
            if (dDoubleValue == 0.0d) {
                dDoubleValue = zzeuVar.zzafo.getLong(FirebaseAnalytics.Param.VALUE).longValue() * 1000000.0d;
            }
            if (dDoubleValue > 9.223372036854776E18d || dDoubleValue < -9.223372036854776E18d) {
                zzgg().zzin().zze("Data lost. Currency value is too big. appId", zzfg.zzbh(str), Double.valueOf(dDoubleValue));
                return false;
            }
            jLongValue = Math.round(dDoubleValue);
        } else {
            jLongValue = zzeuVar.zzafo.getLong(FirebaseAnalytics.Param.VALUE).longValue();
        }
        if (!TextUtils.isEmpty(string)) {
            String upperCase = string.toUpperCase(Locale.US);
            if (upperCase.matches("[A-Z]{3}")) {
                String strValueOf = String.valueOf("_ltv_");
                String strValueOf2 = String.valueOf(upperCase);
                String strConcat = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
                zzju zzjuVarZzg = zzga().zzg(str, strConcat);
                if (zzjuVarZzg == null || !(zzjuVarZzg.value instanceof Long)) {
                    zzei zzeiVarZzga = zzga();
                    int iZzb = this.zzamd.zzb(str, zzew.zzahp) - 1;
                    bf.id(str);
                    zzeiVarZzga.zzab();
                    zzeiVarZzga.zzch();
                    try {
                        zzeiVarZzga.getWritableDatabase().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);", new String[]{str, str, String.valueOf(iZzb)});
                    } catch (SQLiteException e) {
                        zzeiVarZzga.zzgg().zzil().zze("Error pruning currencies. appId", zzfg.zzbh(str), e);
                    }
                    zzjuVar = new zzju(str, zzeuVar.zzaek, strConcat, this.zzrj.currentTimeMillis(), Long.valueOf(jLongValue));
                } else {
                    zzjuVar = new zzju(str, zzeuVar.zzaek, strConcat, this.zzrj.currentTimeMillis(), Long.valueOf(jLongValue + ((Long) zzjuVarZzg.value).longValue()));
                }
                if (!zzga().zza(zzjuVar)) {
                    zzgg().zzil().zzd("Too many unique user properties are set. Ignoring user property. appId", zzfg.zzbh(str), zzgb().zzbg(zzjuVar.name), zzjuVar.value);
                    zzgc().zza(str, 9, (String) null, (String) null, 0);
                }
            }
        }
        return true;
    }

    private final zzkh[] zza(String str, zzkn[] zzknVarArr, zzki[] zzkiVarArr) {
        bf.id(str);
        return zzft().zza(str, zzkiVarArr, zzknVarArr);
    }

    private final void zzb(zzeb zzebVar) {
        android.support.v4.e.a aVar;
        zzgf().zzab();
        if (TextUtils.isEmpty(zzebVar.getGmpAppId())) {
            zzb(zzebVar.zzah(), 204, null, null, null);
            return;
        }
        String gmpAppId = zzebVar.getGmpAppId();
        String appInstanceId = zzebVar.getAppInstanceId();
        Uri.Builder builder = new Uri.Builder();
        Uri.Builder builderEncodedAuthority = builder.scheme(zzew.zzagp.get()).encodedAuthority(zzew.zzagq.get());
        String strValueOf = String.valueOf(gmpAppId);
        builderEncodedAuthority.path(strValueOf.length() != 0 ? "config/app/".concat(strValueOf) : new String("config/app/")).appendQueryParameter("app_instance_id", appInstanceId).appendQueryParameter("platform", Constants.PLATFORM).appendQueryParameter("gmp_version", "12451");
        String string = builder.build().toString();
        try {
            URL url = new URL(string);
            zzgg().zzir().zzg("Fetching remote configuration", zzebVar.zzah());
            zzkf zzkfVarZzbp = zzgd().zzbp(zzebVar.zzah());
            String strZzbq = zzgd().zzbq(zzebVar.zzah());
            if (zzkfVarZzbp == null || TextUtils.isEmpty(strZzbq)) {
                aVar = null;
            } else {
                android.support.v4.e.a aVar2 = new android.support.v4.e.a();
                aVar2.put("If-Modified-Since", strZzbq);
                aVar = aVar2;
            }
            this.zzanl = true;
            zzfk zzfkVarZzjq = zzjq();
            String strZzah = zzebVar.zzah();
            zzgp zzgpVar = new zzgp(this);
            zzfkVarZzjq.zzab();
            zzfkVarZzjq.zzch();
            bf.checkNotNull(url);
            bf.checkNotNull(zzgpVar);
            zzfkVarZzjq.zzgf().zzd(new zzfo(zzfkVarZzjq, strZzah, url, null, aVar, zzgpVar));
        } catch (MalformedURLException e) {
            zzgg().zzil().zze("Failed to parse config URL. Not fetching. appId", zzfg.zzbh(zzebVar.zzah()), string);
        }
    }

    private final zzec zzbt(String str) {
        zzeb zzebVarZzax = zzga().zzax(str);
        if (zzebVarZzax == null || TextUtils.isEmpty(zzebVarZzax.zzag())) {
            zzgg().zziq().zzg("No app data available; dropping", str);
            return null;
        }
        Boolean boolZzc = zzc(zzebVarZzax);
        if (boolZzc == null || boolZzc.booleanValue()) {
            return new zzec(str, zzebVarZzax.getGmpAppId(), zzebVarZzax.zzag(), zzebVarZzax.zzgo(), zzebVarZzax.zzgp(), zzebVarZzax.zzgq(), zzebVarZzax.zzgr(), (String) null, zzebVarZzax.isMeasurementEnabled(), false, zzebVarZzax.zzgl(), zzebVarZzax.zzhe(), 0L, 0, zzebVarZzax.zzhf(), zzebVarZzax.zzhg(), false);
        }
        zzgg().zzil().zzg("App version does not match; dropping. appId", zzfg.zzbh(str));
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0056  */
    private final Boolean zzc(zzeb zzebVar) {
        boolean z;
        try {
            if (zzebVar.zzgo() != -2147483648L) {
                if (zzebVar.zzgo() == c.aV(this.zzqs).getPackageInfo(zzebVar.zzah(), 0).versionCode) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                String str = c.aV(this.zzqs).getPackageInfo(zzebVar.zzah(), 0).versionName;
                if (zzebVar.zzag() == null || !zzebVar.zzag().equals(str)) {
                    z = false;
                } else {
                    z = true;
                }
            }
            return z;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:143:0x0689 A[EDGE_INSN: B:143:0x0689->B:128:0x05de BREAK  A[LOOP:1: B:123:0x05c8->B:149:?]] */
    private final void zzc(zzeu zzeuVar, zzec zzecVar) {
        zzeq zzeqVarZzac;
        zzep zzepVar;
        boolean z;
        zzeb zzebVarZzax;
        bf.checkNotNull(zzecVar);
        bf.id(zzecVar.packageName);
        long jNanoTime = System.nanoTime();
        zzgf().zzab();
        zzch();
        String str = zzecVar.packageName;
        zzgc();
        if (zzjv.zzd(zzeuVar, zzecVar)) {
            if (!zzecVar.zzadr) {
                zzh(zzecVar);
                return;
            }
            if (zzgd().zzn(str, zzeuVar.name)) {
                zzgg().zzin().zze("Dropping blacklisted event. appId", zzfg.zzbh(str), zzgb().zzbe(zzeuVar.name));
                boolean z2 = zzgc().zzce(str) || zzgc().zzcf(str);
                if (!z2 && !"_err".equals(zzeuVar.name)) {
                    zzgc().zza(str, 11, "_ev", zzeuVar.name, 0);
                }
                if (!z2 || (zzebVarZzax = zzga().zzax(str)) == null) {
                    return;
                }
                if (Math.abs(this.zzrj.currentTimeMillis() - Math.max(zzebVarZzax.zzgu(), zzebVarZzax.zzgt())) > zzew.zzahk.get().longValue()) {
                    zzgg().zziq().log("Fetching config for blacklisted app");
                    zzb(zzebVarZzax);
                    return;
                }
                return;
            }
            if (zzgg().isLoggable(2)) {
                zzgg().zzir().zzg("Logging event", zzgb().zzb(zzeuVar));
            }
            zzga().beginTransaction();
            try {
                zzh(zzecVar);
                if (("_iap".equals(zzeuVar.name) || FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(zzeuVar.name)) && !zza(str, zzeuVar)) {
                    zzga().setTransactionSuccessful();
                    zzga().endTransaction();
                    return;
                }
                boolean zZzbv = zzjv.zzbv(zzeuVar.name);
                boolean zEquals = "_err".equals(zzeuVar.name);
                zzej zzejVarZza = zzga().zza(zzjv(), str, true, zZzbv, false, zEquals, false);
                long jIntValue = zzejVarZza.zzafd - ((long) zzew.zzagv.get().intValue());
                if (jIntValue > 0) {
                    if (jIntValue % 1000 == 1) {
                        zzgg().zzil().zze("Data loss. Too many events logged. appId, count", zzfg.zzbh(str), Long.valueOf(zzejVarZza.zzafd));
                    }
                    zzga().setTransactionSuccessful();
                    zzga().endTransaction();
                    return;
                }
                if (zZzbv) {
                    long jIntValue2 = zzejVarZza.zzafc - ((long) zzew.zzagx.get().intValue());
                    if (jIntValue2 > 0) {
                        if (jIntValue2 % 1000 == 1) {
                            zzgg().zzil().zze("Data loss. Too many public events logged. appId, count", zzfg.zzbh(str), Long.valueOf(zzejVarZza.zzafc));
                        }
                        zzgc().zza(str, 16, "_ev", zzeuVar.name, 0);
                        zzga().setTransactionSuccessful();
                        zzga().endTransaction();
                        return;
                    }
                }
                if (zEquals) {
                    long jMax = zzejVarZza.zzaff - ((long) Math.max(0, Math.min(1000000, this.zzamd.zzb(zzecVar.packageName, zzew.zzagw))));
                    if (jMax > 0) {
                        if (jMax == 1) {
                            zzgg().zzil().zze("Too many error events logged. appId, count", zzfg.zzbh(str), Long.valueOf(zzejVarZza.zzaff));
                        }
                        zzga().setTransactionSuccessful();
                        zzga().endTransaction();
                        return;
                    }
                }
                Bundle bundleZzif = zzeuVar.zzafo.zzif();
                zzgc().zza(bundleZzif, "_o", zzeuVar.zzaek);
                if (zzgc().zzcc(str)) {
                    zzgc().zza(bundleZzif, "_dbg", (Object) 1L);
                    zzgc().zza(bundleZzif, "_r", (Object) 1L);
                }
                long jZzay = zzga().zzay(str);
                if (jZzay > 0) {
                    zzgg().zzin().zze("Data lost. Too many events stored on disk, deleted. appId", zzfg.zzbh(str), Long.valueOf(jZzay));
                }
                zzep zzepVar2 = new zzep(this, zzeuVar.zzaek, str, zzeuVar.name, zzeuVar.zzafz, 0L, bundleZzif);
                zzeq zzeqVarZze = zzga().zze(str, zzepVar2.name);
                if (zzeqVarZze != null) {
                    zzep zzepVarZza = zzepVar2.zza(this, zzeqVarZze.zzafr);
                    zzeqVarZzac = zzeqVarZze.zzac(zzepVarZza.timestamp);
                    zzepVar = zzepVarZza;
                } else {
                    if (zzga().zzbb(str) >= 500 && zZzbv) {
                        zzgg().zzil().zzd("Too many event names used, ignoring event. appId, name, supported count", zzfg.zzbh(str), zzgb().zzbe(zzepVar2.name), 500);
                        zzgc().zza(str, 8, (String) null, (String) null, 0);
                        zzga().endTransaction();
                        return;
                    }
                    zzeqVarZzac = new zzeq(str, zzepVar2.name, 0L, 0L, zzepVar2.timestamp, 0L, null, null, null);
                    zzepVar = zzepVar2;
                }
                zzga().zza(zzeqVarZzac);
                zzgf().zzab();
                zzch();
                bf.checkNotNull(zzepVar);
                bf.checkNotNull(zzecVar);
                bf.id(zzepVar.zztd);
                bf.ca(zzepVar.zztd.equals(zzecVar.packageName));
                zzkl zzklVar = new zzkl();
                zzklVar.zzatc = 1;
                zzklVar.zzatk = Constants.PLATFORM;
                zzklVar.zztd = zzecVar.packageName;
                zzklVar.zzado = zzecVar.zzado;
                zzklVar.zztc = zzecVar.zztc;
                zzklVar.zzatw = zzecVar.zzadn == -2147483648L ? null : Integer.valueOf((int) zzecVar.zzadn);
                zzklVar.zzato = Long.valueOf(zzecVar.zzadp);
                zzklVar.zzadh = zzecVar.zzadh;
                zzklVar.zzats = zzecVar.zzadq == 0 ? null : Long.valueOf(zzecVar.zzadq);
                Pair<String, Boolean> pairZzbj = zzgh().zzbj(zzecVar.packageName);
                if (pairZzbj == null || TextUtils.isEmpty((CharSequence) pairZzbj.first)) {
                    if (!zzfw().zzf(this.zzqs) && zzecVar.zzadu) {
                        String string = Settings.Secure.getString(this.zzqs.getContentResolver(), "android_id");
                        if (string == null) {
                            zzgg().zzin().zzg("null secure ID. appId", zzfg.zzbh(zzklVar.zztd));
                            string = "null";
                        } else if (string.isEmpty()) {
                            zzgg().zzin().zzg("empty secure ID. appId", zzfg.zzbh(zzklVar.zztd));
                        }
                        zzklVar.zzatz = string;
                    }
                } else if (zzecVar.zzadt) {
                    zzklVar.zzatq = (String) pairZzbj.first;
                    zzklVar.zzatr = (Boolean) pairZzbj.second;
                }
                zzfw().zzch();
                zzklVar.zzatm = Build.MODEL;
                zzfw().zzch();
                zzklVar.zzatl = Build.VERSION.RELEASE;
                zzklVar.zzatn = Integer.valueOf((int) zzfw().zzic());
                zzklVar.zzafl = zzfw().zzid();
                zzklVar.zzatp = null;
                zzklVar.zzatf = null;
                zzklVar.zzatg = null;
                zzklVar.zzath = null;
                zzklVar.zzaub = Long.valueOf(zzecVar.zzads);
                if (isEnabled() && zzeh.zzhm()) {
                    zzklVar.zzauc = null;
                }
                zzeb zzebVarZzax2 = zzga().zzax(zzecVar.packageName);
                if (zzebVarZzax2 == null) {
                    zzebVarZzax2 = new zzeb(this, zzecVar.packageName);
                    zzebVarZzax2.zzal(zzfv().zzih());
                    zzebVarZzax2.zzao(zzecVar.zzadj);
                    zzebVarZzax2.zzam(zzecVar.zzadh);
                    zzebVarZzax2.zzan(zzgh().zzbk(zzecVar.packageName));
                    zzebVarZzax2.zzr(0L);
                    zzebVarZzax2.zzm(0L);
                    zzebVarZzax2.zzn(0L);
                    zzebVarZzax2.setAppVersion(zzecVar.zztc);
                    zzebVarZzax2.zzo(zzecVar.zzadn);
                    zzebVarZzax2.zzap(zzecVar.zzado);
                    zzebVarZzax2.zzp(zzecVar.zzadp);
                    zzebVarZzax2.zzq(zzecVar.zzadq);
                    zzebVarZzax2.setMeasurementEnabled(zzecVar.zzadr);
                    zzebVarZzax2.zzaa(zzecVar.zzads);
                    zzga().zza(zzebVarZzax2);
                }
                zzklVar.zzadg = zzebVarZzax2.getAppInstanceId();
                zzklVar.zzadj = zzebVarZzax2.zzgl();
                List<zzju> listZzaw = zzga().zzaw(zzecVar.packageName);
                zzklVar.zzate = new zzkn[listZzaw.size()];
                for (int i = 0; i < listZzaw.size(); i++) {
                    zzkn zzknVar = new zzkn();
                    zzklVar.zzate[i] = zzknVar;
                    zzknVar.name = listZzaw.get(i).name;
                    zzknVar.zzaui = Long.valueOf(listZzaw.get(i).zzaqu);
                    zzgc().zza(zzknVar, listZzaw.get(i).value);
                }
                try {
                    long jZza = zzga().zza(zzklVar);
                    zzei zzeiVarZzga = zzga();
                    if (zzepVar.zzafo == null) {
                        z = false;
                        break;
                    }
                    Iterator<String> it = zzepVar.zzafo.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            boolean zZzo = zzgd().zzo(zzepVar.zztd, zzepVar.name);
                            zzej zzejVarZza2 = zzga().zza(zzjv(), zzepVar.zztd, false, false, false, false, false);
                            if (!zZzo || zzejVarZza2.zzafg >= this.zzamd.zzar(zzepVar.zztd)) {
                                z = false;
                                break;
                            } else {
                                z = true;
                                break;
                            }
                        }
                        if ("_r".equals(it.next())) {
                            z = true;
                            break;
                        }
                    }
                    if (zzeiVarZzga.zza(zzepVar, jZza, z)) {
                        this.zzank = 0L;
                    }
                } catch (IOException e) {
                    zzgg().zzil().zze("Data loss. Failed to insert raw event metadata. appId", zzfg.zzbh(zzklVar.zztd), e);
                }
                zzga().setTransactionSuccessful();
                if (zzgg().isLoggable(2)) {
                    zzgg().zzir().zzg("Event recorded", zzgb().zza(zzepVar));
                }
                zzga().endTransaction();
                zzjy();
                zzgg().zzir().zzg("Background event processing time, ms", Long.valueOf(((System.nanoTime() - jNanoTime) + 500000) / 1000000));
            } catch (Throwable th) {
                zzga().endTransaction();
                throw th;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:106:0x0346 A[Catch: all -> 0x01c5, EDGE_INSN: B:106:0x0346->B:17:0x0086 BREAK  A[LOOP:10: B:99:0x0312->B:422:?], TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:110:0x035e A[Catch: all -> 0x036b, SQLiteException -> 0x0c21, TRY_LEAVE, TryCatch #5 {SQLiteException -> 0x0c21, blocks: (B:80:0x02b0, B:82:0x02b6, B:100:0x0313, B:101:0x0327, B:103:0x032b, B:110:0x035e, B:109:0x034c), top: B:381:0x02b0 }] */
    /* JADX WARN: Code duplicated, block: B:113:0x0366 A[Catch: all -> 0x01c5, EDGE_INSN: B:113:0x0366->B:17:0x0086 BREAK  A[LOOP:10: B:99:0x0312->B:422:?], TRY_ENTER, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:124:0x0395 A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:126:0x039b A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:129:0x03a7 A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:131:0x03b4 A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:133:0x03c2 A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:135:0x03cd A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:140:0x041b A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:143:0x0486  */
    /* JADX WARN: Code duplicated, block: B:146:0x048c A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:150:0x04a4 A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:153:0x04ae A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:165:0x0523 A[Catch: all -> 0x01c5, LOOP:2: B:144:0x0487->B:165:0x0523, LOOP_END, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:184:0x05ae A[PHI: r17
  0x05ae: PHI (r17v4 boolean) = (r17v3 boolean), (r17v3 boolean), (r17v3 boolean), (r17v3 boolean), (r17v1 boolean) binds: [B:156:0x04bf, B:157:0x04c1, B:159:0x04ef, B:183:0x0596, B:123:0x0393] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:373:0x0c35  */
    /* JADX WARN: Code duplicated, block: B:374:0x0c39  */
    /* JADX WARN: Code duplicated, block: B:395:0x049b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:420:0x0344 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:421:0x0364 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:422:? A[LOOP:10: B:99:0x0312->B:422:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x01ac A[Catch: SQLiteException -> 0x02f6, all -> 0x0c1a, TRY_LEAVE, TryCatch #9 {SQLiteException -> 0x02f6, all -> 0x0c1a, blocks: (B:44:0x0182, B:46:0x01ac, B:71:0x023d, B:72:0x024c, B:73:0x024f, B:75:0x0255, B:76:0x0267, B:78:0x0275, B:79:0x0288, B:91:0x02e9, B:87:0x02d0), top: B:385:0x0182 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x01c0 A[Catch: all -> 0x01c5, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x023c  */
    /* JADX WARN: Code duplicated, block: B:75:0x0255 A[Catch: SQLiteException -> 0x02f6, all -> 0x0c1a, TryCatch #9 {SQLiteException -> 0x02f6, all -> 0x0c1a, blocks: (B:44:0x0182, B:46:0x01ac, B:71:0x023d, B:72:0x024c, B:73:0x024f, B:75:0x0255, B:76:0x0267, B:78:0x0275, B:79:0x0288, B:91:0x02e9, B:87:0x02d0), top: B:385:0x0182 }] */
    /* JADX WARN: Code duplicated, block: B:78:0x0275 A[Catch: SQLiteException -> 0x02f6, all -> 0x0c1a, TryCatch #9 {SQLiteException -> 0x02f6, all -> 0x0c1a, blocks: (B:44:0x0182, B:46:0x01ac, B:71:0x023d, B:72:0x024c, B:73:0x024f, B:75:0x0255, B:76:0x0267, B:78:0x0275, B:79:0x0288, B:91:0x02e9, B:87:0x02d0), top: B:385:0x0182 }] */
    /* JADX WARN: Code duplicated, block: B:82:0x02b6 A[Catch: all -> 0x036b, SQLiteException -> 0x0c21, TRY_LEAVE, TryCatch #5 {SQLiteException -> 0x0c21, blocks: (B:80:0x02b0, B:82:0x02b6, B:100:0x0313, B:101:0x0327, B:103:0x032b, B:110:0x035e, B:109:0x034c), top: B:381:0x02b0 }] */
    /* JADX WARN: Code duplicated, block: B:84:0x02ca A[Catch: all -> 0x01c5, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x01c5, blocks: (B:3:0x0007, B:16:0x0083, B:17:0x0086, B:19:0x008c, B:23:0x0099, B:24:0x00c1, B:26:0x00cd, B:28:0x00ed, B:30:0x011f, B:34:0x0132, B:36:0x013d, B:120:0x0378, B:122:0x038a, B:173:0x054d, B:175:0x0558, B:177:0x055c, B:185:0x05b1, B:187:0x05bf, B:180:0x057a, B:188:0x05d9, B:179:0x0561, B:124:0x0395, B:126:0x039b, B:127:0x03a0, B:129:0x03a7, B:131:0x03b4, B:132:0x03be, B:133:0x03c2, B:135:0x03cd, B:138:0x03db, B:140:0x041b, B:141:0x0459, B:144:0x0487, B:146:0x048c, B:148:0x049b, B:150:0x04a4, B:151:0x04ab, B:153:0x04ae, B:154:0x04b7, B:165:0x0523, B:155:0x04b9, B:158:0x04c3, B:160:0x04f1, B:162:0x0512, B:164:0x051f, B:166:0x0527, B:171:0x0539, B:182:0x0586, B:183:0x0596, B:189:0x05e0, B:191:0x05ea, B:193:0x05fa, B:195:0x060b, B:233:0x07a9, B:198:0x062b, B:199:0x064b, B:201:0x0652, B:203:0x0663, B:234:0x07d0, B:205:0x066c, B:208:0x0696, B:197:0x060f, B:209:0x06ad, B:211:0x06d1, B:213:0x06ef, B:216:0x071b, B:218:0x072a, B:220:0x0742, B:221:0x0755, B:223:0x0759, B:225:0x0765, B:226:0x0779, B:228:0x077d, B:230:0x0785, B:231:0x079d, B:232:0x07a1, B:235:0x07d4, B:243:0x081a, B:245:0x0822, B:247:0x082c, B:249:0x0830, B:240:0x07e9, B:242:0x07ff, B:263:0x0859, B:265:0x0867, B:267:0x087b, B:268:0x08b4, B:271:0x08c5, B:273:0x08ce, B:275:0x08d8, B:277:0x08dc, B:279:0x08e0, B:281:0x08e4, B:283:0x08f8, B:285:0x0902, B:287:0x0925, B:288:0x0932, B:289:0x0949, B:291:0x0960, B:293:0x099b, B:294:0x09ac, B:295:0x09c3, B:297:0x09c9, B:251:0x0838, B:253:0x083c, B:255:0x0844, B:257:0x0848, B:261:0x0854, B:299:0x09dc, B:301:0x09e5, B:302:0x09f3, B:303:0x09fb, B:305:0x0a01, B:306:0x0a15, B:307:0x0a2d, B:309:0x0a34, B:311:0x0a4c, B:312:0x0a52, B:314:0x0a64, B:315:0x0a6a, B:316:0x0a6d, B:318:0x0a7d, B:319:0x0a95, B:321:0x0a9c, B:323:0x0aac, B:349:0x0b91, B:328:0x0ac6, B:325:0x0ab0, B:327:0x0abc, B:348:0x0b77, B:329:0x0ad1, B:330:0x0aec, B:333:0x0af4, B:334:0x0afa, B:350:0x0b99, B:352:0x0bb5, B:353:0x0bcf, B:354:0x0bd7, B:355:0x0be6, B:359:0x0bf7, B:335:0x0b0b, B:337:0x0b12, B:339:0x0b1c, B:340:0x0b20, B:344:0x0b34, B:345:0x0b38, B:360:0x0c0a, B:48:0x01c0, B:84:0x02ca, B:106:0x0346, B:113:0x0366, B:97:0x030d, B:89:0x02e4, B:116:0x036e, B:117:0x0371, B:63:0x021d, B:72:0x024c), top: B:376:0x0007, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:91:0x02e9 A[Catch: SQLiteException -> 0x02f6, all -> 0x0c1a, TRY_ENTER, TRY_LEAVE, TryCatch #9 {SQLiteException -> 0x02f6, all -> 0x0c1a, blocks: (B:44:0x0182, B:46:0x01ac, B:71:0x023d, B:72:0x024c, B:73:0x024f, B:75:0x0255, B:76:0x0267, B:78:0x0275, B:79:0x0288, B:91:0x02e9, B:87:0x02d0), top: B:385:0x0182 }] */
    /* JADX WARN: Code duplicated, block: B:99:0x0312 A[LOOP:10: B:99:0x0312->B:422:?, LOOP_START] */
    private final boolean zzd(String str, long j) {
        boolean z;
        zzeq zzeqVarZza;
        int i;
        boolean z2;
        boolean z3;
        zzkj[] zzkjVarArr;
        int length;
        int i2;
        boolean z4;
        int i3;
        zzkj[] zzkjVarArr2;
        zzkj zzkjVar;
        boolean z5;
        long jLongValue;
        int i4;
        String str2;
        Cursor cursor;
        String str3;
        Cursor cursorQuery;
        String str4;
        String[] strArr;
        long j2;
        zzki zzkiVar;
        zzga().beginTransaction();
        try {
            zza zzaVar = new zza(this, null);
            zzei zzeiVarZzga = zzga();
            String str5 = null;
            long j3 = this.zzanj;
            bf.checkNotNull(zzaVar);
            zzeiVarZzga.zzab();
            zzeiVarZzga.zzch();
            Cursor cursorQuery2 = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = zzeiVarZzga.getWritableDatabase();
                    if (TextUtils.isEmpty(null)) {
                        String[] strArr2 = j3 != -1 ? new String[]{String.valueOf(j3), String.valueOf(j)} : new String[]{String.valueOf(j)};
                        String str6 = j3 != -1 ? "rowid <= ? and " : "";
                        Cursor cursorRawQuery = writableDatabase.rawQuery(new StringBuilder(String.valueOf(str6).length() + 148).append("select app_id, metadata_fingerprint from raw_events where ").append(str6).append("app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;").toString(), strArr2);
                        if (cursorRawQuery.moveToFirst()) {
                            String string = cursorRawQuery.getString(0);
                            String string2 = cursorRawQuery.getString(1);
                            cursorRawQuery.close();
                            str2 = string2;
                            cursor = cursorRawQuery;
                            str3 = string;
                            try {
                                cursorQuery = writableDatabase.query("raw_events_metadata", new String[]{"metadata"}, "app_id = ? and metadata_fingerprint = ?", new String[]{str3, str2}, null, null, "rowid", "2");
                                if (!cursorQuery.moveToFirst()) {
                                    byte[] blob = cursorQuery.getBlob(0);
                                    zzaba zzabaVarZza = zzaba.zza(blob, 0, blob.length);
                                    zzkl zzklVar = new zzkl();
                                    try {
                                        zzklVar.zzb(zzabaVarZza);
                                        if (cursorQuery.moveToNext()) {
                                            zzeiVarZzga.zzgg().zzin().zzg("Get multiple raw event metadata records, expected one. appId", zzfg.zzbh(str3));
                                        }
                                        cursorQuery.close();
                                        zzaVar.zzb(zzklVar);
                                        if (j3 != -1) {
                                            str4 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
                                            strArr = new String[]{str3, str2, String.valueOf(j3)};
                                        } else {
                                            str4 = "app_id = ? and metadata_fingerprint = ?";
                                            strArr = new String[]{str3, str2};
                                        }
                                        cursorQuery2 = writableDatabase.query("raw_events", new String[]{"rowid", "name", "timestamp", ShareConstants.WEB_DIALOG_PARAM_DATA}, str4, strArr, null, null, "rowid", null);
                                        try {
                                            if (!cursorQuery2.moveToFirst()) {
                                                while (true) {
                                                    j2 = cursorQuery2.getLong(0);
                                                    byte[] blob2 = cursorQuery2.getBlob(3);
                                                    zzaba zzabaVarZza2 = zzaba.zza(blob2, 0, blob2.length);
                                                    zzkiVar = new zzki();
                                                    try {
                                                        zzkiVar.zzb(zzabaVarZza2);
                                                        zzkiVar.name = cursorQuery2.getString(1);
                                                        zzkiVar.zzasw = Long.valueOf(cursorQuery2.getLong(2));
                                                        if (!zzaVar.zza(j2, zzkiVar)) {
                                                            if (cursorQuery2 != null) {
                                                                break;
                                                            }
                                                            cursorQuery2.close();
                                                            break;
                                                        }
                                                        if (!cursorQuery2.moveToNext()) {
                                                            if (cursorQuery2 != null) {
                                                                break;
                                                            }
                                                            cursorQuery2.close();
                                                            break;
                                                        }
                                                    } catch (IOException e) {
                                                        zzeiVarZzga.zzgg().zzil().zze("Data loss. Failed to merge raw event. appId", zzfg.zzbh(str3), e);
                                                    }
                                                }
                                            } else {
                                                zzeiVarZzga.zzgg().zzin().zzg("Raw event data disappeared while in transaction. appId", zzfg.zzbh(str3));
                                                if (cursorQuery2 != null) {
                                                    cursorQuery2.close();
                                                }
                                            }
                                        } catch (SQLiteException e2) {
                                            e = e2;
                                            str5 = str3;
                                            zzeiVarZzga.zzgg().zzil().zze("Data loss. Error selecting raw event. appId", zzfg.zzbh(str5), e);
                                            if (cursorQuery2 != null) {
                                                cursorQuery2.close();
                                            }
                                        }
                                    } catch (IOException e3) {
                                        zzeiVarZzga.zzgg().zzil().zze("Data loss. Failed to merge raw event metadata. appId", zzfg.zzbh(str3), e3);
                                        if (cursorQuery != null) {
                                            cursorQuery.close();
                                        }
                                    }
                                } else {
                                    zzeiVarZzga.zzgg().zzil().zzg("Raw event metadata record is missing. appId", zzfg.zzbh(str3));
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                }
                            } catch (SQLiteException e4) {
                                e = e4;
                                cursorQuery2 = cursor;
                                str5 = str3;
                            } catch (Throwable th) {
                                th = th;
                                cursorQuery2 = cursor;
                                if (cursorQuery2 != null) {
                                    cursorQuery2.close();
                                }
                                throw th;
                            }
                        } else if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                    } else {
                        String[] strArr3 = j3 != -1 ? new String[]{null, String.valueOf(j3)} : new String[]{null};
                        String str7 = j3 != -1 ? " and rowid <= ?" : "";
                        Cursor cursorRawQuery2 = writableDatabase.rawQuery(new StringBuilder(String.valueOf(str7).length() + 84).append("select metadata_fingerprint from raw_events where app_id = ?").append(str7).append(" order by rowid limit 1;").toString(), strArr3);
                        if (cursorRawQuery2.moveToFirst()) {
                            String string3 = cursorRawQuery2.getString(0);
                            cursorRawQuery2.close();
                            str2 = string3;
                            cursor = cursorRawQuery2;
                            str3 = null;
                            cursorQuery = writableDatabase.query("raw_events_metadata", new String[]{"metadata"}, "app_id = ? and metadata_fingerprint = ?", new String[]{str3, str2}, null, null, "rowid", "2");
                            if (!cursorQuery.moveToFirst()) {
                                byte[] blob3 = cursorQuery.getBlob(0);
                                zzaba zzabaVarZza3 = zzaba.zza(blob3, 0, blob3.length);
                                zzkl zzklVar2 = new zzkl();
                                zzklVar2.zzb(zzabaVarZza3);
                                if (cursorQuery.moveToNext()) {
                                    zzeiVarZzga.zzgg().zzin().zzg("Get multiple raw event metadata records, expected one. appId", zzfg.zzbh(str3));
                                }
                                cursorQuery.close();
                                zzaVar.zzb(zzklVar2);
                                if (j3 != -1) {
                                    str4 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
                                    strArr = new String[]{str3, str2, String.valueOf(j3)};
                                } else {
                                    str4 = "app_id = ? and metadata_fingerprint = ?";
                                    strArr = new String[]{str3, str2};
                                }
                                cursorQuery2 = writableDatabase.query("raw_events", new String[]{"rowid", "name", "timestamp", ShareConstants.WEB_DIALOG_PARAM_DATA}, str4, strArr, null, null, "rowid", null);
                                if (!cursorQuery2.moveToFirst()) {
                                    while (true) {
                                        j2 = cursorQuery2.getLong(0);
                                        byte[] blob4 = cursorQuery2.getBlob(3);
                                        zzaba zzabaVarZza4 = zzaba.zza(blob4, 0, blob4.length);
                                        zzkiVar = new zzki();
                                        zzkiVar.zzb(zzabaVarZza4);
                                        zzkiVar.name = cursorQuery2.getString(1);
                                        zzkiVar.zzasw = Long.valueOf(cursorQuery2.getLong(2));
                                        if (!zzaVar.zza(j2, zzkiVar)) {
                                            if (cursorQuery2 != null) {
                                                break;
                                            }
                                            cursorQuery2.close();
                                            break;
                                        }
                                        if (!cursorQuery2.moveToNext()) {
                                            if (cursorQuery2 != null) {
                                                break;
                                            }
                                            cursorQuery2.close();
                                            break;
                                        }
                                    }
                                } else {
                                    zzeiVarZzga.zzgg().zzin().zzg("Raw event data disappeared while in transaction. appId", zzfg.zzbh(str3));
                                    if (cursorQuery2 != null) {
                                        cursorQuery2.close();
                                    }
                                }
                            } else {
                                zzeiVarZzga.zzgg().zzil().zzg("Raw event metadata record is missing. appId", zzfg.zzbh(str3));
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                            }
                        } else if (cursorRawQuery2 != null) {
                            cursorRawQuery2.close();
                        }
                    }
                } catch (SQLiteException e5) {
                    e = e5;
                }
                if (zzaVar.zzanu == null || zzaVar.zzanu.isEmpty()) {
                    zzga().setTransactionSuccessful();
                    zzga().endTransaction();
                    return false;
                }
                boolean z6 = false;
                zzkl zzklVar3 = zzaVar.zzans;
                zzklVar3.zzatd = new zzki[zzaVar.zzanu.size()];
                int i5 = 0;
                long j4 = 0;
                boolean zZzau = this.zzamd.zzau(zzklVar3.zztd);
                int i6 = 0;
                while (i6 < zzaVar.zzanu.size()) {
                    zzki zzkiVar2 = zzaVar.zzanu.get(i6);
                    if (zzgd().zzn(zzaVar.zzans.zztd, zzkiVar2.name)) {
                        zzgg().zzin().zze("Dropping blacklisted raw event. appId", zzfg.zzbh(zzaVar.zzans.zztd), zzgb().zzbe(zzkiVar2.name));
                        if ((zzgc().zzce(zzaVar.zzans.zztd) || zzgc().zzcf(zzaVar.zzans.zztd)) || "_err".equals(zzkiVar2.name)) {
                            jLongValue = j4;
                            i4 = i5;
                            z4 = z6;
                        } else {
                            zzgc().zza(zzaVar.zzans.zztd, 11, "_ev", zzkiVar2.name, 0);
                            jLongValue = j4;
                            i4 = i5;
                            z4 = z6;
                        }
                    } else {
                        boolean zZzo = zzgd().zzo(zzaVar.zzans.zztd, zzkiVar2.name);
                        if (zZzo) {
                            z2 = false;
                            z3 = false;
                            if (zzkiVar2.zzasv == null) {
                                zzkiVar2.zzasv = new zzkj[0];
                            }
                            zzkjVarArr = zzkiVar2.zzasv;
                            length = zzkjVarArr.length;
                            i2 = 0;
                            while (i2 < length) {
                                zzkjVar = zzkjVarArr[i2];
                                if ("_c".equals(zzkjVar.name)) {
                                    zzkjVar.zzasz = 1L;
                                    z2 = true;
                                    z5 = z3;
                                } else if ("_r".equals(zzkjVar.name)) {
                                    zzkjVar.zzasz = 1L;
                                    z5 = true;
                                } else {
                                    z5 = z3;
                                }
                                i2++;
                                z3 = z5;
                            }
                            if (!z2) {
                                zzgg().zzir().zzg("Marking event as conversion", zzgb().zzbe(zzkiVar2.name));
                                zzkj[] zzkjVarArr3 = (zzkj[]) Arrays.copyOf(zzkiVar2.zzasv, zzkiVar2.zzasv.length + 1);
                                zzkj zzkjVar2 = new zzkj();
                                zzkjVar2.name = "_c";
                                zzkjVar2.zzasz = 1L;
                                zzkjVarArr3[zzkjVarArr3.length - 1] = zzkjVar2;
                                zzkiVar2.zzasv = zzkjVarArr3;
                            }
                            if (!z3) {
                                zzgg().zzir().zzg("Marking event as real-time", zzgb().zzbe(zzkiVar2.name));
                                zzkj[] zzkjVarArr4 = (zzkj[]) Arrays.copyOf(zzkiVar2.zzasv, zzkiVar2.zzasv.length + 1);
                                zzkj zzkjVar3 = new zzkj();
                                zzkjVar3.name = "_r";
                                zzkjVar3.zzasz = 1L;
                                zzkjVarArr4[zzkjVarArr4.length - 1] = zzkjVar3;
                                zzkiVar2.zzasv = zzkjVarArr4;
                            }
                            if (zzga().zza(zzjv(), zzaVar.zzans.zztd, false, false, false, false, true).zzafg > this.zzamd.zzar(zzaVar.zzans.zztd)) {
                                for (i3 = 0; i3 < zzkiVar2.zzasv.length; i3++) {
                                    if ("_r".equals(zzkiVar2.zzasv[i3].name)) {
                                        zzkjVarArr2 = new zzkj[zzkiVar2.zzasv.length - 1];
                                        if (i3 > 0) {
                                            System.arraycopy(zzkiVar2.zzasv, 0, zzkjVarArr2, 0, i3);
                                        }
                                        if (i3 < zzkjVarArr2.length) {
                                            System.arraycopy(zzkiVar2.zzasv, i3 + 1, zzkjVarArr2, i3, zzkjVarArr2.length - i3);
                                        }
                                        zzkiVar2.zzasv = zzkjVarArr2;
                                        break;
                                    }
                                }
                            } else {
                                z6 = true;
                            }
                            if (zzjv.zzbv(zzkiVar2.name)) {
                                z4 = z6;
                            } else {
                                z4 = z6;
                            }
                        } else {
                            zzgc();
                            if (zzjv.zzcg(zzkiVar2.name)) {
                                z2 = false;
                                z3 = false;
                                if (zzkiVar2.zzasv == null) {
                                    zzkiVar2.zzasv = new zzkj[0];
                                }
                                zzkjVarArr = zzkiVar2.zzasv;
                                length = zzkjVarArr.length;
                                i2 = 0;
                                while (i2 < length) {
                                    zzkjVar = zzkjVarArr[i2];
                                    if ("_c".equals(zzkjVar.name)) {
                                        zzkjVar.zzasz = 1L;
                                        z2 = true;
                                        z5 = z3;
                                    } else if ("_r".equals(zzkjVar.name)) {
                                        zzkjVar.zzasz = 1L;
                                        z5 = true;
                                    } else {
                                        z5 = z3;
                                    }
                                    i2++;
                                    z3 = z5;
                                }
                                if (!z2 && zZzo) {
                                    zzgg().zzir().zzg("Marking event as conversion", zzgb().zzbe(zzkiVar2.name));
                                    zzkj[] zzkjVarArr5 = (zzkj[]) Arrays.copyOf(zzkiVar2.zzasv, zzkiVar2.zzasv.length + 1);
                                    zzkj zzkjVar4 = new zzkj();
                                    zzkjVar4.name = "_c";
                                    zzkjVar4.zzasz = 1L;
                                    zzkjVarArr5[zzkjVarArr5.length - 1] = zzkjVar4;
                                    zzkiVar2.zzasv = zzkjVarArr5;
                                }
                                if (!z3) {
                                    zzgg().zzir().zzg("Marking event as real-time", zzgb().zzbe(zzkiVar2.name));
                                    zzkj[] zzkjVarArr6 = (zzkj[]) Arrays.copyOf(zzkiVar2.zzasv, zzkiVar2.zzasv.length + 1);
                                    zzkj zzkjVar5 = new zzkj();
                                    zzkjVar5.name = "_r";
                                    zzkjVar5.zzasz = 1L;
                                    zzkjVarArr6[zzkjVarArr6.length - 1] = zzkjVar5;
                                    zzkiVar2.zzasv = zzkjVarArr6;
                                }
                                if (zzga().zza(zzjv(), zzaVar.zzans.zztd, false, false, false, false, true).zzafg > this.zzamd.zzar(zzaVar.zzans.zztd)) {
                                    while (i3 < zzkiVar2.zzasv.length) {
                                        if ("_r".equals(zzkiVar2.zzasv[i3].name)) {
                                            zzkjVarArr2 = new zzkj[zzkiVar2.zzasv.length - 1];
                                            if (i3 > 0) {
                                                System.arraycopy(zzkiVar2.zzasv, 0, zzkjVarArr2, 0, i3);
                                            }
                                            if (i3 < zzkjVarArr2.length) {
                                                System.arraycopy(zzkiVar2.zzasv, i3 + 1, zzkjVarArr2, i3, zzkjVarArr2.length - i3);
                                            }
                                            zzkiVar2.zzasv = zzkjVarArr2;
                                            break;
                                        }
                                    }
                                } else {
                                    z6 = true;
                                }
                                if (zzjv.zzbv(zzkiVar2.name) || !zZzo || zzga().zza(zzjv(), zzaVar.zzans.zztd, false, false, true, false, false).zzafe <= this.zzamd.zzb(zzaVar.zzans.zztd, zzew.zzagy)) {
                                    z4 = z6;
                                } else {
                                    zzgg().zzin().zzg("Too many conversions. Not logging as conversion. appId", zzfg.zzbh(zzaVar.zzans.zztd));
                                    boolean z7 = false;
                                    zzkj zzkjVar6 = null;
                                    zzkj[] zzkjVarArr7 = zzkiVar2.zzasv;
                                    int length2 = zzkjVarArr7.length;
                                    int i7 = 0;
                                    while (i7 < length2) {
                                        zzkj zzkjVar7 = zzkjVarArr7[i7];
                                        if (!"_c".equals(zzkjVar7.name)) {
                                            if ("_err".equals(zzkjVar7.name)) {
                                                z7 = true;
                                                zzkjVar7 = zzkjVar6;
                                            } else {
                                                zzkjVar7 = zzkjVar6;
                                            }
                                        }
                                        i7++;
                                        zzkjVar6 = zzkjVar7;
                                    }
                                    if (z7 && zzkjVar6 != null) {
                                        zzkiVar2.zzasv = (zzkj[]) b.a((Object[]) zzkiVar2.zzasv, (Object[]) new zzkj[]{zzkjVar6});
                                        z4 = z6;
                                    } else if (zzkjVar6 != null) {
                                        zzkjVar6.name = "_err";
                                        zzkjVar6.zzasz = 10L;
                                        z4 = z6;
                                    } else {
                                        zzgg().zzil().zzg("Did not find conversion parameter. appId", zzfg.zzbh(zzaVar.zzans.zztd));
                                        z4 = z6;
                                    }
                                }
                            } else {
                                z4 = z6;
                            }
                        }
                        if (!zZzau || !"_e".equals(zzkiVar2.name)) {
                            jLongValue = j4;
                        } else if (zzkiVar2.zzasv == null || zzkiVar2.zzasv.length == 0) {
                            zzgg().zzin().zzg("Engagement event does not contain any parameters. appId", zzfg.zzbh(zzaVar.zzans.zztd));
                            jLongValue = j4;
                        } else {
                            zzgc();
                            Long l = (Long) zzjv.zzb(zzkiVar2, "_et");
                            if (l == null) {
                                zzgg().zzin().zzg("Engagement event does not include duration. appId", zzfg.zzbh(zzaVar.zzans.zztd));
                                jLongValue = j4;
                            } else {
                                jLongValue = j4 + l.longValue();
                            }
                        }
                        i4 = i5 + 1;
                        zzklVar3.zzatd[i5] = zzkiVar2;
                    }
                    i6++;
                    j4 = jLongValue;
                    i5 = i4;
                    z6 = z4;
                }
                if (i5 < zzaVar.zzanu.size()) {
                    zzklVar3.zzatd = (zzki[]) Arrays.copyOf(zzklVar3.zzatd, i5);
                }
                if (zZzau) {
                    zzju zzjuVarZzg = zzga().zzg(zzklVar3.zztd, "_lte");
                    zzju zzjuVar = (zzjuVarZzg == null || zzjuVarZzg.value == null) ? new zzju(zzklVar3.zztd, "auto", "_lte", this.zzrj.currentTimeMillis(), Long.valueOf(j4)) : new zzju(zzklVar3.zztd, "auto", "_lte", this.zzrj.currentTimeMillis(), Long.valueOf(((Long) zzjuVarZzg.value).longValue() + j4));
                    zzkn zzknVar = new zzkn();
                    zzknVar.name = "_lte";
                    zzknVar.zzaui = Long.valueOf(this.zzrj.currentTimeMillis());
                    zzknVar.zzasz = (Long) zzjuVar.value;
                    boolean z8 = false;
                    for (int i8 = 0; i8 < zzklVar3.zzate.length; i8++) {
                        if ("_lte".equals(zzklVar3.zzate[i8].name)) {
                            zzklVar3.zzate[i8] = zzknVar;
                            z8 = true;
                            break;
                        }
                    }
                    if (!z8) {
                        zzklVar3.zzate = (zzkn[]) Arrays.copyOf(zzklVar3.zzate, zzklVar3.zzate.length + 1);
                        zzklVar3.zzate[zzaVar.zzans.zzate.length - 1] = zzknVar;
                    }
                    if (j4 > 0) {
                        zzga().zza(zzjuVar);
                        zzgg().zziq().zzg("Updated lifetime engagement user property with value. Value", zzjuVar.value);
                    }
                }
                zzklVar3.zzatv = zza(zzklVar3.zztd, zzklVar3.zzate, zzklVar3.zzatd);
                if (zzew.zzagk.get().booleanValue()) {
                    if (AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(this.zzamd.zzgd().zzm(zzaVar.zzans.zztd, "measurement.event_sampling_enabled"))) {
                        HashMap map = new HashMap();
                        zzki[] zzkiVarArr = new zzki[zzklVar3.zzatd.length];
                        int i9 = 0;
                        SecureRandom secureRandomZzku = zzgc().zzku();
                        zzki[] zzkiVarArr2 = zzklVar3.zzatd;
                        int length3 = zzkiVarArr2.length;
                        int i10 = 0;
                        while (i10 < length3) {
                            zzki zzkiVar3 = zzkiVarArr2[i10];
                            if (zzkiVar3.name.equals("_ep")) {
                                zzgc();
                                String str8 = (String) zzjv.zzb(zzkiVar3, "_en");
                                zzeq zzeqVarZze = (zzeq) map.get(str8);
                                if (zzeqVarZze == null) {
                                    zzeqVarZze = zzga().zze(zzaVar.zzans.zztd, str8);
                                    map.put(str8, zzeqVarZze);
                                }
                                if (zzeqVarZze.zzaft == null) {
                                    if (zzeqVarZze.zzafu.longValue() > 1) {
                                        zzgc();
                                        zzkiVar3.zzasv = zzjv.zza(zzkiVar3.zzasv, "_sr", zzeqVarZze.zzafu);
                                    }
                                    if (zzeqVarZze.zzafv != null && zzeqVarZze.zzafv.booleanValue()) {
                                        zzgc();
                                        zzkiVar3.zzasv = zzjv.zza(zzkiVar3.zzasv, "_efs", (Object) 1L);
                                    }
                                    i = i9 + 1;
                                    zzkiVarArr[i9] = zzkiVar3;
                                } else {
                                    i = i9;
                                }
                            } else {
                                Long l2 = 1L;
                                if (TextUtils.isEmpty("_dbg") || l2 == null) {
                                    z = false;
                                } else {
                                    zzkj[] zzkjVarArr8 = zzkiVar3.zzasv;
                                    int length4 = zzkjVarArr8.length;
                                    int i11 = 0;
                                    while (true) {
                                        if (i11 >= length4) {
                                            z = false;
                                            break;
                                        }
                                        zzkj zzkjVar8 = zzkjVarArr8[i11];
                                        if ("_dbg".equals(zzkjVar8.name)) {
                                            if ((!(l2 instanceof Long) || !l2.equals(zzkjVar8.zzasz)) && ((!(l2 instanceof String) || !l2.equals(zzkjVar8.zzajf)) && (!(l2 instanceof Double) || !l2.equals(zzkjVar8.zzaqx)))) {
                                                z = false;
                                                break;
                                            }
                                            z = true;
                                            break;
                                        }
                                        i11++;
                                    }
                                }
                                int iZzp = !z ? zzgd().zzp(zzaVar.zzans.zztd, zzkiVar3.name) : 1;
                                if (iZzp <= 0) {
                                    zzgg().zzin().zze("Sample rate must be positive. event, rate", zzkiVar3.name, Integer.valueOf(iZzp));
                                    i = i9 + 1;
                                    zzkiVarArr[i9] = zzkiVar3;
                                } else {
                                    zzeq zzeqVar = (zzeq) map.get(zzkiVar3.name);
                                    if (zzeqVar == null) {
                                        zzeqVarZza = zzga().zze(zzaVar.zzans.zztd, zzkiVar3.name);
                                        if (zzeqVarZza == null) {
                                            zzgg().zzin().zze("Event being bundled has no eventAggregate. appId, eventName", zzaVar.zzans.zztd, zzkiVar3.name);
                                            zzeqVarZza = new zzeq(zzaVar.zzans.zztd, zzkiVar3.name, 1L, 1L, zzkiVar3.zzasw.longValue(), 0L, null, null, null);
                                        }
                                    } else {
                                        zzeqVarZza = zzeqVar;
                                    }
                                    zzgc();
                                    Long l3 = (Long) zzjv.zzb(zzkiVar3, "_eid");
                                    Boolean boolValueOf = Boolean.valueOf(l3 != null);
                                    if (iZzp == 1) {
                                        i = i9 + 1;
                                        zzkiVarArr[i9] = zzkiVar3;
                                        if (boolValueOf.booleanValue() && (zzeqVarZza.zzaft != null || zzeqVarZza.zzafu != null || zzeqVarZza.zzafv != null)) {
                                            map.put(zzkiVar3.name, zzeqVarZza.zza(null, null, null));
                                        }
                                    } else if (secureRandomZzku.nextInt(iZzp) == 0) {
                                        zzgc();
                                        zzkiVar3.zzasv = zzjv.zza(zzkiVar3.zzasv, "_sr", Long.valueOf(iZzp));
                                        i = i9 + 1;
                                        zzkiVarArr[i9] = zzkiVar3;
                                        if (boolValueOf.booleanValue()) {
                                            zzeqVarZza = zzeqVarZza.zza(null, Long.valueOf(iZzp), null);
                                        }
                                        map.put(zzkiVar3.name, zzeqVarZza.zzad(zzkiVar3.zzasw.longValue()));
                                    } else if (Math.abs(zzkiVar3.zzasw.longValue() - zzeqVarZza.zzafs) >= 86400000) {
                                        zzgc();
                                        zzkiVar3.zzasv = zzjv.zza(zzkiVar3.zzasv, "_efs", (Object) 1L);
                                        zzgc();
                                        zzkiVar3.zzasv = zzjv.zza(zzkiVar3.zzasv, "_sr", Long.valueOf(iZzp));
                                        i = i9 + 1;
                                        zzkiVarArr[i9] = zzkiVar3;
                                        if (boolValueOf.booleanValue()) {
                                            zzeqVarZza = zzeqVarZza.zza(null, Long.valueOf(iZzp), true);
                                        }
                                        map.put(zzkiVar3.name, zzeqVarZza.zzad(zzkiVar3.zzasw.longValue()));
                                    } else {
                                        if (boolValueOf.booleanValue()) {
                                            map.put(zzkiVar3.name, zzeqVarZza.zza(l3, null, null));
                                        }
                                        i = i9;
                                    }
                                }
                            }
                            i10++;
                            i9 = i;
                        }
                        if (i9 < zzklVar3.zzatd.length) {
                            zzklVar3.zzatd = (zzki[]) Arrays.copyOf(zzkiVarArr, i9);
                        }
                        Iterator it = map.entrySet().iterator();
                        while (it.hasNext()) {
                            zzga().zza((zzeq) ((Map.Entry) it.next()).getValue());
                        }
                    }
                }
                zzklVar3.zzatg = Long.MAX_VALUE;
                zzklVar3.zzath = Long.MIN_VALUE;
                for (int i12 = 0; i12 < zzklVar3.zzatd.length; i12++) {
                    zzki zzkiVar4 = zzklVar3.zzatd[i12];
                    if (zzkiVar4.zzasw.longValue() < zzklVar3.zzatg.longValue()) {
                        zzklVar3.zzatg = zzkiVar4.zzasw;
                    }
                    if (zzkiVar4.zzasw.longValue() > zzklVar3.zzath.longValue()) {
                        zzklVar3.zzath = zzkiVar4.zzasw;
                    }
                }
                String str9 = zzaVar.zzans.zztd;
                zzeb zzebVarZzax = zzga().zzax(str9);
                if (zzebVarZzax == null) {
                    zzgg().zzil().zzg("Bundling raw events w/o app info. appId", zzfg.zzbh(zzaVar.zzans.zztd));
                } else if (zzklVar3.zzatd.length > 0) {
                    long jZzgn = zzebVarZzax.zzgn();
                    zzklVar3.zzatj = jZzgn != 0 ? Long.valueOf(jZzgn) : null;
                    long jZzgm = zzebVarZzax.zzgm();
                    if (jZzgm != 0) {
                        jZzgn = jZzgm;
                    }
                    zzklVar3.zzati = jZzgn != 0 ? Long.valueOf(jZzgn) : null;
                    zzebVarZzax.zzgv();
                    zzklVar3.zzatt = Integer.valueOf((int) zzebVarZzax.zzgs());
                    zzebVarZzax.zzm(zzklVar3.zzatg.longValue());
                    zzebVarZzax.zzn(zzklVar3.zzath.longValue());
                    zzklVar3.zzaef = zzebVarZzax.zzhd();
                    zzga().zza(zzebVarZzax);
                }
                if (zzklVar3.zzatd.length > 0) {
                    zzkf zzkfVarZzbp = zzgd().zzbp(zzaVar.zzans.zztd);
                    if (zzkfVarZzbp != null && zzkfVarZzbp.zzask != null) {
                        zzklVar3.zzaua = zzkfVarZzbp.zzask;
                    } else if (TextUtils.isEmpty(zzaVar.zzans.zzadh)) {
                        zzklVar3.zzaua = -1L;
                    } else {
                        zzgg().zzin().zzg("Did not find measurement config or missing version info. appId", zzfg.zzbh(zzaVar.zzans.zztd));
                    }
                    zzga().zza(zzklVar3, z6);
                }
                zzei zzeiVarZzga2 = zzga();
                List<Long> list = zzaVar.zzant;
                bf.checkNotNull(list);
                zzeiVarZzga2.zzab();
                zzeiVarZzga2.zzch();
                StringBuilder sb = new StringBuilder("rowid in (");
                for (int i13 = 0; i13 < list.size(); i13++) {
                    if (i13 != 0) {
                        sb.append(",");
                    }
                    sb.append(list.get(i13).longValue());
                }
                sb.append(")");
                int iDelete = zzeiVarZzga2.getWritableDatabase().delete("raw_events", sb.toString(), null);
                if (iDelete != list.size()) {
                    zzeiVarZzga2.zzgg().zzil().zze("Deleted fewer rows from raw events table than expected", Integer.valueOf(iDelete), Integer.valueOf(list.size()));
                }
                zzei zzeiVarZzga3 = zzga();
                try {
                    zzeiVarZzga3.getWritableDatabase().execSQL("delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)", new String[]{str9, str9});
                } catch (SQLiteException e6) {
                    zzeiVarZzga3.zzgg().zzil().zze("Failed to remove unused event metadata. appId", zzfg.zzbh(str9), e6);
                }
                zzga().setTransactionSuccessful();
                zzga().endTransaction();
                return true;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            zzga().endTransaction();
            throw th3;
        }
    }

    static void zzfq() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    public static zzgl zzg(Context context) {
        bf.checkNotNull(context);
        bf.checkNotNull(context.getApplicationContext());
        if (zzamc == null) {
            synchronized (zzgl.class) {
                if (zzamc == null) {
                    zzamc = new zzgl(new zzhl(context));
                }
            }
        }
        return zzamc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final zzeb zzh(zzec zzecVar) {
        boolean z = true;
        zzgf().zzab();
        zzch();
        bf.checkNotNull(zzecVar);
        bf.id(zzecVar.packageName);
        zzeb zzebVarZzax = zzga().zzax(zzecVar.packageName);
        String strZzbk = zzgh().zzbk(zzecVar.packageName);
        boolean z2 = false;
        if (zzebVarZzax == null) {
            zzebVarZzax = new zzeb(this, zzecVar.packageName);
            zzebVarZzax.zzal(zzfv().zzih());
            zzebVarZzax.zzan(strZzbk);
            z2 = true;
        } else if (!strZzbk.equals(zzebVarZzax.zzgk())) {
            zzebVarZzax.zzan(strZzbk);
            zzebVarZzax.zzal(zzfv().zzih());
            z2 = true;
        }
        if (!TextUtils.isEmpty(zzecVar.zzadh) && !zzecVar.zzadh.equals(zzebVarZzax.getGmpAppId())) {
            zzebVarZzax.zzam(zzecVar.zzadh);
            z2 = true;
        }
        if (!TextUtils.isEmpty(zzecVar.zzadj) && !zzecVar.zzadj.equals(zzebVarZzax.zzgl())) {
            zzebVarZzax.zzao(zzecVar.zzadj);
            z2 = true;
        }
        if (zzecVar.zzadp != 0 && zzecVar.zzadp != zzebVarZzax.zzgq()) {
            zzebVarZzax.zzp(zzecVar.zzadp);
            z2 = true;
        }
        if (!TextUtils.isEmpty(zzecVar.zztc) && !zzecVar.zztc.equals(zzebVarZzax.zzag())) {
            zzebVarZzax.setAppVersion(zzecVar.zztc);
            z2 = true;
        }
        if (zzecVar.zzadn != zzebVarZzax.zzgo()) {
            zzebVarZzax.zzo(zzecVar.zzadn);
            z2 = true;
        }
        if (zzecVar.zzado != null && !zzecVar.zzado.equals(zzebVarZzax.zzgp())) {
            zzebVarZzax.zzap(zzecVar.zzado);
            z2 = true;
        }
        if (zzecVar.zzadq != zzebVarZzax.zzgr()) {
            zzebVarZzax.zzq(zzecVar.zzadq);
            z2 = true;
        }
        if (zzecVar.zzadr != zzebVarZzax.isMeasurementEnabled()) {
            zzebVarZzax.setMeasurementEnabled(zzecVar.zzadr);
            z2 = true;
        }
        if (!TextUtils.isEmpty(zzecVar.zzaef) && !zzecVar.zzaef.equals(zzebVarZzax.zzhc())) {
            zzebVarZzax.zzaq(zzecVar.zzaef);
            z2 = true;
        }
        if (zzecVar.zzads != zzebVarZzax.zzhe()) {
            zzebVarZzax.zzaa(zzecVar.zzads);
            z2 = true;
        }
        if (zzecVar.zzadt != zzebVarZzax.zzhf()) {
            zzebVarZzax.zzd(zzecVar.zzadt);
            z2 = true;
        }
        if (zzecVar.zzadu != zzebVarZzax.zzhg()) {
            zzebVarZzax.zze(zzecVar.zzadu);
        } else {
            z = z2;
        }
        if (z) {
            zzga().zza(zzebVarZzax);
        }
        return zzebVarZzax;
    }

    private final zzfp zzjr() {
        if (this.zzamw == null) {
            throw new IllegalStateException("Network broadcast receiver not created");
        }
        return this.zzamw;
    }

    private final zzjq zzjs() {
        zza((zzhk) this.zzamx);
        return this.zzamx;
    }

    private final boolean zzjt() {
        zzgf().zzab();
        try {
            this.zzane = new RandomAccessFile(new File(this.zzqs.getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzand = this.zzane.tryLock();
            if (this.zzand != null) {
                zzgg().zzir().log("Storage concurrent access okay");
                return true;
            }
            zzgg().zzil().log("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            zzgg().zzil().zzg("Failed to acquire storage lock", e);
        } catch (IOException e2) {
            zzgg().zzil().zzg("Failed to access storage lock file", e2);
        }
    }

    private final long zzjv() {
        long jCurrentTimeMillis = this.zzrj.currentTimeMillis();
        zzfr zzfrVarZzgh = zzgh();
        zzfrVarZzgh.zzch();
        zzfrVarZzgh.zzab();
        long jNextInt = zzfrVarZzgh.zzajx.get();
        if (jNextInt == 0) {
            jNextInt = 1 + ((long) zzfrVarZzgh.zzgc().zzku().nextInt(86400000));
            zzfrVarZzgh.zzajx.set(jNextInt);
        }
        return ((((jNextInt + jCurrentTimeMillis) / 1000) / 60) / 60) / 24;
    }

    private final boolean zzjx() {
        zzgf().zzab();
        zzch();
        return zzga().zzhs() || !TextUtils.isEmpty(zzga().zzhn());
    }

    private final void zzjy() {
        long jMax;
        long jMax2;
        zzgf().zzab();
        zzch();
        if (zzkb()) {
            if (this.zzank > 0) {
                long jAbs = 3600000 - Math.abs(this.zzrj.elapsedRealtime() - this.zzank);
                if (jAbs > 0) {
                    zzgg().zzir().zzg("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(jAbs));
                    zzjr().unregister();
                    zzjs().cancel();
                    return;
                }
                this.zzank = 0L;
            }
            if (!zzjk() || !zzjx()) {
                zzgg().zzir().log("Nothing to upload or uploading impossible");
                zzjr().unregister();
                zzjs().cancel();
                return;
            }
            long jCurrentTimeMillis = this.zzrj.currentTimeMillis();
            long jMax3 = Math.max(0L, zzew.zzahl.get().longValue());
            boolean z = zzga().zzht() || zzga().zzho();
            if (z) {
                String strZzhl = this.zzamd.zzhl();
                jMax = (TextUtils.isEmpty(strZzhl) || ".none.".equals(strZzhl)) ? Math.max(0L, zzew.zzahf.get().longValue()) : Math.max(0L, zzew.zzahg.get().longValue());
            } else {
                jMax = Math.max(0L, zzew.zzahe.get().longValue());
            }
            long j = zzgh().zzajt.get();
            long j2 = zzgh().zzaju.get();
            long jMax4 = Math.max(zzga().zzhq(), zzga().zzhr());
            if (jMax4 != 0) {
                long jAbs2 = jCurrentTimeMillis - Math.abs(jMax4 - jCurrentTimeMillis);
                long jAbs3 = jCurrentTimeMillis - Math.abs(j - jCurrentTimeMillis);
                long jAbs4 = jCurrentTimeMillis - Math.abs(j2 - jCurrentTimeMillis);
                long jMax5 = Math.max(jAbs3, jAbs4);
                jMax2 = jAbs2 + jMax3;
                if (z && jMax5 > 0) {
                    jMax2 = Math.min(jAbs2, jMax5) + jMax;
                }
                if (!zzgc().zza(jMax5, jMax)) {
                    jMax2 = jMax5 + jMax;
                }
                if (jAbs4 != 0 && jAbs4 >= jAbs2) {
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= Math.min(20, Math.max(0, zzew.zzahn.get().intValue()))) {
                            jMax2 = 0;
                            break;
                        }
                        jMax2 += (1 << i2) * Math.max(0L, zzew.zzahm.get().longValue());
                        if (jMax2 > jAbs4) {
                            break;
                        } else {
                            i = i2 + 1;
                        }
                    }
                }
            } else {
                jMax2 = 0;
            }
            if (jMax2 == 0) {
                zzgg().zzir().log("Next upload time is 0");
                zzjr().unregister();
                zzjs().cancel();
                return;
            }
            if (!zzjq().zzew()) {
                zzgg().zzir().log("No network");
                zzjr().zzet();
                zzjs().cancel();
                return;
            }
            long j3 = zzgh().zzajv.get();
            long jMax6 = Math.max(0L, zzew.zzahc.get().longValue());
            long jMax7 = !zzgc().zza(j3, jMax6) ? Math.max(jMax2, jMax6 + j3) : jMax2;
            zzjr().unregister();
            long jCurrentTimeMillis2 = jMax7 - this.zzrj.currentTimeMillis();
            if (jCurrentTimeMillis2 <= 0) {
                jCurrentTimeMillis2 = Math.max(0L, zzew.zzahh.get().longValue());
                zzgh().zzajt.set(this.zzrj.currentTimeMillis());
            }
            zzgg().zzir().zzg("Upload scheduled in approximately ms", Long.valueOf(jCurrentTimeMillis2));
            zzjs().zzh(jCurrentTimeMillis2);
        }
    }

    private final boolean zzkb() {
        zzgf().zzab();
        zzch();
        return this.zzana;
    }

    private final void zzkc() {
        zzgf().zzab();
        if (this.zzanl || this.zzanm || this.zzann) {
            zzgg().zzir().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzanl), Boolean.valueOf(this.zzanm), Boolean.valueOf(this.zzann));
            return;
        }
        zzgg().zzir().log("Stopping uploading service(s)");
        if (this.zzang != null) {
            Iterator<Runnable> it = this.zzang.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
            this.zzang.clear();
        }
    }

    public final Context getContext() {
        return this.zzqs;
    }

    public final boolean isEnabled() {
        boolean zBooleanValue = false;
        zzgf().zzab();
        zzch();
        if (this.zzamd.zzhi()) {
            return false;
        }
        Boolean boolZzas = this.zzamd.zzas("firebase_analytics_collection_enabled");
        if (boolZzas != null) {
            zBooleanValue = boolZzas.booleanValue();
        } else if (!r.Zi()) {
            zBooleanValue = true;
        }
        return zzgh().zzg(zBooleanValue);
    }

    protected final void start() {
        zzgf().zzab();
        zzga().zzhp();
        if (zzgh().zzajt.get() == 0) {
            zzgh().zzajt.set(this.zzrj.currentTimeMillis());
        }
        if (Long.valueOf(zzgh().zzajy.get()).longValue() == 0) {
            zzgg().zzir().zzg("Persisting first open", Long.valueOf(this.zzaeh));
            zzgh().zzajy.set(this.zzaeh);
        }
        if (zzjk()) {
            if (!TextUtils.isEmpty(zzfv().getGmpAppId())) {
                String strZziv = zzgh().zziv();
                if (strZziv == null) {
                    zzgh().zzbl(zzfv().getGmpAppId());
                } else if (!strZziv.equals(zzfv().getGmpAppId())) {
                    zzgg().zzip().log("Rechecking which service to use due to a GMP App Id change");
                    zzgh().zziy();
                    this.zzamt.disconnect();
                    this.zzamt.zzdf();
                    zzgh().zzbl(zzfv().getGmpAppId());
                    zzgh().zzajy.set(this.zzaeh);
                    zzgh().zzaka.zzbn(null);
                }
            }
            zzfu().zzbm(zzgh().zzaka.zzjc());
            if (!TextUtils.isEmpty(zzfv().getGmpAppId())) {
                boolean zIsEnabled = isEnabled();
                if (!zzgh().zzjb() && !this.zzamd.zzhi()) {
                    zzgh().zzh(zIsEnabled ? false : true);
                }
                if (!this.zzamd.zzav(zzfv().zzah()) || zIsEnabled) {
                    zzfu().zzkj();
                }
                zzfx().zza(new AtomicReference<>());
            }
        } else if (isEnabled()) {
            if (!zzgc().zzx("android.permission.INTERNET")) {
                zzgg().zzil().log("App is missing INTERNET permission");
            }
            if (!zzgc().zzx("android.permission.ACCESS_NETWORK_STATE")) {
                zzgg().zzil().log("App is missing ACCESS_NETWORK_STATE permission");
            }
            if (!c.aV(this.zzqs).abO()) {
                if (!zzgb.zza(this.zzqs)) {
                    zzgg().zzil().log("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzjf.zza(this.zzqs, false)) {
                    zzgg().zzil().log("AppMeasurementService not registered/enabled");
                }
            }
            zzgg().zzil().log("Uploading is not possible. App measurement disabled");
        }
        zzjy();
    }

    protected final void zza(int i, Throwable th, byte[] bArr, String str) {
        zzgf().zzab();
        zzch();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzanm = false;
                zzkc();
                throw th2;
            }
        }
        List<Long> list = this.zzanf;
        this.zzanf = null;
        if ((i == 200 || i == 204) && th == null) {
            try {
                zzgh().zzajt.set(this.zzrj.currentTimeMillis());
                zzgh().zzaju.set(0L);
                zzjy();
                zzgg().zzir().zze("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzga().beginTransaction();
                try {
                    for (Long l : list) {
                        zzei zzeiVarZzga = zzga();
                        long jLongValue = l.longValue();
                        zzeiVarZzga.zzab();
                        zzeiVarZzga.zzch();
                        try {
                            if (zzeiVarZzga.getWritableDatabase().delete("queue", "rowid=?", new String[]{String.valueOf(jLongValue)}) != 1) {
                                throw new SQLiteException("Deleted fewer rows from queue than expected");
                            }
                        } catch (SQLiteException e) {
                            zzeiVarZzga.zzgg().zzil().zzg("Failed to delete a bundle in a queue table", e);
                            throw e;
                        }
                    }
                    zzga().setTransactionSuccessful();
                    zzga().endTransaction();
                    if (zzjq().zzew() && zzjx()) {
                        zzjw();
                    } else {
                        this.zzanj = -1L;
                        zzjy();
                    }
                    this.zzank = 0L;
                } catch (Throwable th3) {
                    zzga().endTransaction();
                    throw th3;
                }
            } catch (SQLiteException e2) {
                zzgg().zzil().zzg("Database error while trying to delete uploaded bundles", e2);
                this.zzank = this.zzrj.elapsedRealtime();
                zzgg().zzir().zzg("Disable upload, time", Long.valueOf(this.zzank));
            }
        } else {
            zzgg().zzir().zze("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            zzgh().zzaju.set(this.zzrj.currentTimeMillis());
            if (i == 503 || i == 429) {
                zzgh().zzajv.set(this.zzrj.currentTimeMillis());
            }
            if (this.zzamd.zzd(str, zzew.zzaia)) {
                zzga().zzc(list);
            }
            zzjy();
        }
        this.zzanm = false;
        zzkc();
    }

    public final byte[] zza(zzeu zzeuVar, String str) {
        long j;
        zzkn zzknVar;
        zzch();
        zzgf().zzab();
        zzfq();
        bf.checkNotNull(zzeuVar);
        bf.id(str);
        zzkk zzkkVar = new zzkk();
        zzga().beginTransaction();
        try {
            zzeb zzebVarZzax = zzga().zzax(str);
            if (zzebVarZzax == null) {
                zzgg().zziq().zzg("Log and bundle not available. package_name", str);
                byte[] bArr = new byte[0];
                zzga().endTransaction();
                return bArr;
            }
            if (!zzebVarZzax.isMeasurementEnabled()) {
                zzgg().zziq().zzg("Log and bundle disabled. package_name", str);
                byte[] bArr2 = new byte[0];
                zzga().endTransaction();
                return bArr2;
            }
            if (("_iap".equals(zzeuVar.name) || FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(zzeuVar.name)) && !zza(str, zzeuVar)) {
                zzgg().zzin().zzg("Failed to handle purchase event at single event bundle creation. appId", zzfg.zzbh(str));
            }
            boolean zZzau = this.zzamd.zzau(str);
            Long l = 0L;
            if (zZzau && "_e".equals(zzeuVar.name)) {
                if (zzeuVar.zzafo == null || zzeuVar.zzafo.size() == 0) {
                    zzgg().zzin().zzg("The engagement event does not contain any parameters. appId", zzfg.zzbh(str));
                } else if (zzeuVar.zzafo.getLong("_et") == null) {
                    zzgg().zzin().zzg("The engagement event does not include duration. appId", zzfg.zzbh(str));
                } else {
                    l = zzeuVar.zzafo.getLong("_et");
                }
            }
            zzkl zzklVar = new zzkl();
            zzkkVar.zzata = new zzkl[]{zzklVar};
            zzklVar.zzatc = 1;
            zzklVar.zzatk = Constants.PLATFORM;
            zzklVar.zztd = zzebVarZzax.zzah();
            zzklVar.zzado = zzebVarZzax.zzgp();
            zzklVar.zztc = zzebVarZzax.zzag();
            long jZzgo = zzebVarZzax.zzgo();
            zzklVar.zzatw = jZzgo == -2147483648L ? null : Integer.valueOf((int) jZzgo);
            zzklVar.zzato = Long.valueOf(zzebVarZzax.zzgq());
            zzklVar.zzadh = zzebVarZzax.getGmpAppId();
            zzklVar.zzats = Long.valueOf(zzebVarZzax.zzgr());
            if (isEnabled() && zzeh.zzhm() && this.zzamd.zzat(zzklVar.zztd)) {
                zzklVar.zzauc = null;
            }
            Pair<String, Boolean> pairZzbj = zzgh().zzbj(zzebVarZzax.zzah());
            if (zzebVarZzax.zzhf() && pairZzbj != null && !TextUtils.isEmpty((CharSequence) pairZzbj.first)) {
                zzklVar.zzatq = (String) pairZzbj.first;
                zzklVar.zzatr = (Boolean) pairZzbj.second;
            }
            zzfw().zzch();
            zzklVar.zzatm = Build.MODEL;
            zzfw().zzch();
            zzklVar.zzatl = Build.VERSION.RELEASE;
            zzklVar.zzatn = Integer.valueOf((int) zzfw().zzic());
            zzklVar.zzafl = zzfw().zzid();
            zzklVar.zzadg = zzebVarZzax.getAppInstanceId();
            zzklVar.zzadj = zzebVarZzax.zzgl();
            List<zzju> listZzaw = zzga().zzaw(zzebVarZzax.zzah());
            zzklVar.zzate = new zzkn[listZzaw.size()];
            zzju zzjuVar = null;
            if (zZzau) {
                zzju zzjuVarZzg = zzga().zzg(zzklVar.zztd, "_lte");
                zzjuVar = (zzjuVarZzg == null || zzjuVarZzg.value == null) ? new zzju(zzklVar.zztd, "auto", "_lte", this.zzrj.currentTimeMillis(), l) : l.longValue() > 0 ? new zzju(zzklVar.zztd, "auto", "_lte", this.zzrj.currentTimeMillis(), Long.valueOf(((Long) zzjuVarZzg.value).longValue() + l.longValue())) : zzjuVarZzg;
            }
            zzkn zzknVar2 = null;
            int i = 0;
            while (i < listZzaw.size()) {
                zzkn zzknVar3 = new zzkn();
                zzklVar.zzate[i] = zzknVar3;
                zzknVar3.name = listZzaw.get(i).name;
                zzknVar3.zzaui = Long.valueOf(listZzaw.get(i).zzaqu);
                zzgc().zza(zzknVar3, listZzaw.get(i).value);
                if (zZzau && "_lte".equals(zzknVar3.name)) {
                    zzknVar3.zzasz = (Long) zzjuVar.value;
                    zzknVar3.zzaui = Long.valueOf(this.zzrj.currentTimeMillis());
                    zzknVar = zzknVar3;
                } else {
                    zzknVar = zzknVar2;
                }
                i++;
                zzknVar2 = zzknVar;
            }
            if (zZzau && zzknVar2 == null) {
                zzkn zzknVar4 = new zzkn();
                zzknVar4.name = "_lte";
                zzknVar4.zzaui = Long.valueOf(this.zzrj.currentTimeMillis());
                zzknVar4.zzasz = (Long) zzjuVar.value;
                zzklVar.zzate = (zzkn[]) Arrays.copyOf(zzklVar.zzate, zzklVar.zzate.length + 1);
                zzklVar.zzate[zzklVar.zzate.length - 1] = zzknVar4;
            }
            if (l.longValue() > 0) {
                zzga().zza(zzjuVar);
            }
            Bundle bundleZzif = zzeuVar.zzafo.zzif();
            if ("_iap".equals(zzeuVar.name)) {
                bundleZzif.putLong("_c", 1L);
                zzgg().zziq().log("Marking in-app purchase as real-time");
                bundleZzif.putLong("_r", 1L);
            }
            bundleZzif.putString("_o", zzeuVar.zzaek);
            if (zzgc().zzcc(zzklVar.zztd)) {
                zzgc().zza(bundleZzif, "_dbg", (Object) 1L);
                zzgc().zza(bundleZzif, "_r", (Object) 1L);
            }
            zzeq zzeqVarZze = zzga().zze(str, zzeuVar.name);
            if (zzeqVarZze == null) {
                zzga().zza(new zzeq(str, zzeuVar.name, 1L, 0L, zzeuVar.zzafz, 0L, null, null, null));
                j = 0;
            } else {
                j = zzeqVarZze.zzafr;
                zzga().zza(zzeqVarZze.zzac(zzeuVar.zzafz).zzie());
            }
            zzep zzepVar = new zzep(this, zzeuVar.zzaek, str, zzeuVar.name, zzeuVar.zzafz, j, bundleZzif);
            zzki zzkiVar = new zzki();
            zzklVar.zzatd = new zzki[]{zzkiVar};
            zzkiVar.zzasw = Long.valueOf(zzepVar.timestamp);
            zzkiVar.name = zzepVar.name;
            zzkiVar.zzasx = Long.valueOf(zzepVar.zzafn);
            zzkiVar.zzasv = new zzkj[zzepVar.zzafo.size()];
            int i2 = 0;
            for (String str2 : zzepVar.zzafo) {
                zzkj zzkjVar = new zzkj();
                zzkiVar.zzasv[i2] = zzkjVar;
                zzkjVar.name = str2;
                zzgc().zza(zzkjVar, zzepVar.zzafo.get(str2));
                i2++;
            }
            zzklVar.zzatv = zza(zzebVarZzax.zzah(), zzklVar.zzate, zzklVar.zzatd);
            zzklVar.zzatg = zzkiVar.zzasw;
            zzklVar.zzath = zzkiVar.zzasw;
            long jZzgn = zzebVarZzax.zzgn();
            zzklVar.zzatj = jZzgn != 0 ? Long.valueOf(jZzgn) : null;
            long jZzgm = zzebVarZzax.zzgm();
            if (jZzgm != 0) {
                jZzgn = jZzgm;
            }
            zzklVar.zzati = jZzgn != 0 ? Long.valueOf(jZzgn) : null;
            zzebVarZzax.zzgv();
            zzklVar.zzatt = Integer.valueOf((int) zzebVarZzax.zzgs());
            zzklVar.zzatp = 12451L;
            zzklVar.zzatf = Long.valueOf(this.zzrj.currentTimeMillis());
            zzklVar.zzatu = Boolean.TRUE;
            zzebVarZzax.zzm(zzklVar.zzatg.longValue());
            zzebVarZzax.zzn(zzklVar.zzath.longValue());
            zzga().zza(zzebVarZzax);
            zzga().setTransactionSuccessful();
            zzga().endTransaction();
            try {
                byte[] bArr3 = new byte[zzkkVar.zzwg()];
                zzabb zzabbVarZzb = zzabb.zzb(bArr3, 0, bArr3.length);
                zzkkVar.zza(zzabbVarZzb);
                zzabbVarZzb.zzvy();
                return zzgc().zza(bArr3);
            } catch (IOException e) {
                zzgg().zzil().zze("Data loss. Failed to bundle and serialize. appId", zzfg.zzbh(str), e);
                return null;
            }
        } catch (Throwable th) {
            zzga().endTransaction();
            throw th;
        }
    }

    final void zzb(zzef zzefVar, zzec zzecVar) {
        boolean z = true;
        bf.checkNotNull(zzefVar);
        bf.id(zzefVar.packageName);
        bf.checkNotNull(zzefVar.zzaek);
        bf.checkNotNull(zzefVar.zzael);
        bf.id(zzefVar.zzael.name);
        zzgf().zzab();
        zzch();
        if (TextUtils.isEmpty(zzecVar.zzadh)) {
            return;
        }
        if (!zzecVar.zzadr) {
            zzh(zzecVar);
            return;
        }
        zzef zzefVar2 = new zzef(zzefVar);
        zzefVar2.zzaen = false;
        zzga().beginTransaction();
        try {
            zzef zzefVarZzh = zzga().zzh(zzefVar2.packageName, zzefVar2.zzael.name);
            if (zzefVarZzh != null && !zzefVarZzh.zzaek.equals(zzefVar2.zzaek)) {
                zzgg().zzin().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", zzgb().zzbg(zzefVar2.zzael.name), zzefVar2.zzaek, zzefVarZzh.zzaek);
            }
            if (zzefVarZzh != null && zzefVarZzh.zzaen) {
                zzefVar2.zzaek = zzefVarZzh.zzaek;
                zzefVar2.zzaem = zzefVarZzh.zzaem;
                zzefVar2.zzaeq = zzefVarZzh.zzaeq;
                zzefVar2.zzaeo = zzefVarZzh.zzaeo;
                zzefVar2.zzaer = zzefVarZzh.zzaer;
                zzefVar2.zzaen = zzefVarZzh.zzaen;
                zzefVar2.zzael = new zzjs(zzefVar2.zzael.name, zzefVarZzh.zzael.zzaqu, zzefVar2.zzael.getValue(), zzefVarZzh.zzael.zzaek);
                z = false;
            } else if (TextUtils.isEmpty(zzefVar2.zzaeo)) {
                zzefVar2.zzael = new zzjs(zzefVar2.zzael.name, zzefVar2.zzaem, zzefVar2.zzael.getValue(), zzefVar2.zzael.zzaek);
                zzefVar2.zzaen = true;
            } else {
                z = false;
            }
            if (zzefVar2.zzaen) {
                zzjs zzjsVar = zzefVar2.zzael;
                zzju zzjuVar = new zzju(zzefVar2.packageName, zzefVar2.zzaek, zzjsVar.name, zzjsVar.zzaqu, zzjsVar.getValue());
                if (zzga().zza(zzjuVar)) {
                    zzgg().zziq().zzd("User property updated immediately", zzefVar2.packageName, zzgb().zzbg(zzjuVar.name), zzjuVar.value);
                } else {
                    zzgg().zzil().zzd("(2)Too many active user properties, ignoring", zzfg.zzbh(zzefVar2.packageName), zzgb().zzbg(zzjuVar.name), zzjuVar.value);
                }
                if (z && zzefVar2.zzaer != null) {
                    zzc(new zzeu(zzefVar2.zzaer, zzefVar2.zzaem), zzecVar);
                }
            }
            if (zzga().zza(zzefVar2)) {
                zzgg().zziq().zzd("Conditional property added", zzefVar2.packageName, zzgb().zzbg(zzefVar2.zzael.name), zzefVar2.zzael.getValue());
            } else {
                zzgg().zzil().zzd("Too many conditional properties, ignoring", zzfg.zzbh(zzefVar2.packageName), zzgb().zzbg(zzefVar2.zzael.name), zzefVar2.zzael.getValue());
            }
            zzga().setTransactionSuccessful();
        } finally {
            zzga().endTransaction();
        }
    }

    final void zzb(zzeu zzeuVar, zzec zzecVar) {
        List<zzef> listZzb;
        List<zzef> listZzb2;
        List<zzef> listZzb3;
        bf.checkNotNull(zzecVar);
        bf.id(zzecVar.packageName);
        zzgf().zzab();
        zzch();
        String str = zzecVar.packageName;
        long j = zzeuVar.zzafz;
        zzgc();
        if (zzjv.zzd(zzeuVar, zzecVar)) {
            if (!zzecVar.zzadr) {
                zzh(zzecVar);
                return;
            }
            zzga().beginTransaction();
            try {
                zzei zzeiVarZzga = zzga();
                bf.id(str);
                zzeiVarZzga.zzab();
                zzeiVarZzga.zzch();
                if (j < 0) {
                    zzeiVarZzga.zzgg().zzin().zze("Invalid time querying timed out conditional properties", zzfg.zzbh(str), Long.valueOf(j));
                    listZzb = Collections.emptyList();
                } else {
                    listZzb = zzeiVarZzga.zzb("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzef zzefVar : listZzb) {
                    if (zzefVar != null) {
                        zzgg().zziq().zzd("User property timed out", zzefVar.packageName, zzgb().zzbg(zzefVar.zzael.name), zzefVar.zzael.getValue());
                        if (zzefVar.zzaep != null) {
                            zzc(new zzeu(zzefVar.zzaep, j), zzecVar);
                        }
                        zzga().zzi(str, zzefVar.zzael.name);
                    }
                }
                zzei zzeiVarZzga2 = zzga();
                bf.id(str);
                zzeiVarZzga2.zzab();
                zzeiVarZzga2.zzch();
                if (j < 0) {
                    zzeiVarZzga2.zzgg().zzin().zze("Invalid time querying expired conditional properties", zzfg.zzbh(str), Long.valueOf(j));
                    listZzb2 = Collections.emptyList();
                } else {
                    listZzb2 = zzeiVarZzga2.zzb("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(listZzb2.size());
                for (zzef zzefVar2 : listZzb2) {
                    if (zzefVar2 != null) {
                        zzgg().zziq().zzd("User property expired", zzefVar2.packageName, zzgb().zzbg(zzefVar2.zzael.name), zzefVar2.zzael.getValue());
                        zzga().zzf(str, zzefVar2.zzael.name);
                        if (zzefVar2.zzaet != null) {
                            arrayList.add(zzefVar2.zzaet);
                        }
                        zzga().zzi(str, zzefVar2.zzael.name);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    zzc(new zzeu((zzeu) obj, j), zzecVar);
                }
                zzei zzeiVarZzga3 = zzga();
                String str2 = zzeuVar.name;
                bf.id(str);
                bf.id(str2);
                zzeiVarZzga3.zzab();
                zzeiVarZzga3.zzch();
                if (j < 0) {
                    zzeiVarZzga3.zzgg().zzin().zzd("Invalid time querying triggered conditional properties", zzfg.zzbh(str), zzeiVarZzga3.zzgb().zzbe(str2), Long.valueOf(j));
                    listZzb3 = Collections.emptyList();
                } else {
                    listZzb3 = zzeiVarZzga3.zzb("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                ArrayList arrayList3 = new ArrayList(listZzb3.size());
                for (zzef zzefVar3 : listZzb3) {
                    if (zzefVar3 != null) {
                        zzjs zzjsVar = zzefVar3.zzael;
                        zzju zzjuVar = new zzju(zzefVar3.packageName, zzefVar3.zzaek, zzjsVar.name, j, zzjsVar.getValue());
                        if (zzga().zza(zzjuVar)) {
                            zzgg().zziq().zzd("User property triggered", zzefVar3.packageName, zzgb().zzbg(zzjuVar.name), zzjuVar.value);
                        } else {
                            zzgg().zzil().zzd("Too many active user properties, ignoring", zzfg.zzbh(zzefVar3.packageName), zzgb().zzbg(zzjuVar.name), zzjuVar.value);
                        }
                        if (zzefVar3.zzaer != null) {
                            arrayList3.add(zzefVar3.zzaer);
                        }
                        zzefVar3.zzael = new zzjs(zzjuVar);
                        zzefVar3.zzaen = true;
                        zzga().zza(zzefVar3);
                    }
                }
                zzc(zzeuVar, zzecVar);
                ArrayList arrayList4 = arrayList3;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList4.get(i2);
                    i2++;
                    zzc(new zzeu((zzeu) obj2, j), zzecVar);
                }
                zzga().setTransactionSuccessful();
            } finally {
                zzga().endTransaction();
            }
        }
    }

    final void zzb(zzeu zzeuVar, String str) {
        zzeb zzebVarZzax = zzga().zzax(str);
        if (zzebVarZzax == null || TextUtils.isEmpty(zzebVarZzax.zzag())) {
            zzgg().zziq().zzg("No app data available; dropping event", str);
            return;
        }
        Boolean boolZzc = zzc(zzebVarZzax);
        if (boolZzc == null) {
            if (!"_ui".equals(zzeuVar.name)) {
                zzgg().zzin().zzg("Could not find package. appId", zzfg.zzbh(str));
            }
        } else if (!boolZzc.booleanValue()) {
            zzgg().zzil().zzg("App version does not match; dropping event. appId", zzfg.zzbh(str));
            return;
        }
        zzb(zzeuVar, new zzec(str, zzebVarZzax.getGmpAppId(), zzebVarZzax.zzag(), zzebVarZzax.zzgo(), zzebVarZzax.zzgp(), zzebVarZzax.zzgq(), zzebVarZzax.zzgr(), (String) null, zzebVarZzax.isMeasurementEnabled(), false, zzebVarZzax.zzgl(), zzebVarZzax.zzhe(), 0L, 0, zzebVarZzax.zzhf(), zzebVarZzax.zzhg(), false));
    }

    final void zzb(zzhk zzhkVar) {
        this.zzanh++;
    }

    final void zzb(zzjs zzjsVar, zzec zzecVar) {
        int length = 0;
        zzgf().zzab();
        zzch();
        if (TextUtils.isEmpty(zzecVar.zzadh)) {
            return;
        }
        if (!zzecVar.zzadr) {
            zzh(zzecVar);
            return;
        }
        int iZzby = zzgc().zzby(zzjsVar.name);
        if (iZzby != 0) {
            zzgc();
            zzgc().zza(zzecVar.packageName, iZzby, "_ev", zzjv.zza(zzjsVar.name, 24, true), zzjsVar.name != null ? zzjsVar.name.length() : 0);
            return;
        }
        int iZzi = zzgc().zzi(zzjsVar.name, zzjsVar.getValue());
        if (iZzi != 0) {
            zzgc();
            String strZza = zzjv.zza(zzjsVar.name, 24, true);
            Object value = zzjsVar.getValue();
            if (value != null && ((value instanceof String) || (value instanceof CharSequence))) {
                length = String.valueOf(value).length();
            }
            zzgc().zza(zzecVar.packageName, iZzi, "_ev", strZza, length);
            return;
        }
        Object objZzj = zzgc().zzj(zzjsVar.name, zzjsVar.getValue());
        if (objZzj != null) {
            zzju zzjuVar = new zzju(zzecVar.packageName, zzjsVar.zzaek, zzjsVar.name, zzjsVar.zzaqu, objZzj);
            zzgg().zziq().zze("Setting user property", zzgb().zzbg(zzjuVar.name), objZzj);
            zzga().beginTransaction();
            try {
                zzh(zzecVar);
                boolean zZza = zzga().zza(zzjuVar);
                zzga().setTransactionSuccessful();
                if (zZza) {
                    zzgg().zziq().zze("User property set", zzgb().zzbg(zzjuVar.name), zzjuVar.value);
                } else {
                    zzgg().zzil().zze("Too many unique user properties are set. Ignoring user property", zzgb().zzbg(zzjuVar.name), zzjuVar.value);
                    zzgc().zza(zzecVar.packageName, 9, (String) null, (String) null, 0);
                }
            } finally {
                zzga().endTransaction();
            }
        }
    }

    final void zzb(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        boolean z = true;
        zzgf().zzab();
        zzch();
        bf.id(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzanl = false;
                zzkc();
                throw th2;
            }
        }
        zzgg().zzir().zzg("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zzga().beginTransaction();
        try {
            zzeb zzebVarZzax = zzga().zzax(str);
            boolean z2 = (i == 200 || i == 204 || i == 304) && th == null;
            if (zzebVarZzax == null) {
                zzgg().zzin().zzg("App does not exist in onConfigFetched. appId", zzfg.zzbh(str));
            } else if (z2 || i == 404) {
                List<String> list = map != null ? map.get("Last-Modified") : null;
                String str2 = (list == null || list.size() <= 0) ? null : list.get(0);
                if (i == 404 || i == 304) {
                    if (zzgd().zzbp(str) == null && !zzgd().zza(str, null, null)) {
                        zzga().endTransaction();
                        this.zzanl = false;
                        zzkc();
                        return;
                    }
                } else if (!zzgd().zza(str, bArr, str2)) {
                    zzga().endTransaction();
                    this.zzanl = false;
                    zzkc();
                    return;
                }
                zzebVarZzax.zzs(this.zzrj.currentTimeMillis());
                zzga().zza(zzebVarZzax);
                if (i == 404) {
                    zzgg().zzio().zzg("Config not found. Using empty config. appId", str);
                } else {
                    zzgg().zzir().zze("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                }
                if (zzjq().zzew() && zzjx()) {
                    zzjw();
                } else {
                    zzjy();
                }
            } else {
                zzebVarZzax.zzt(this.zzrj.currentTimeMillis());
                zzga().zza(zzebVarZzax);
                zzgg().zzir().zze("Fetching config failed. code, error", Integer.valueOf(i), th);
                zzgd().zzbr(str);
                zzgh().zzaju.set(this.zzrj.currentTimeMillis());
                if (i != 503 && i != 429) {
                    z = false;
                }
                if (z) {
                    zzgh().zzajv.set(this.zzrj.currentTimeMillis());
                }
                zzjy();
            }
            zzga().setTransactionSuccessful();
            zzga().endTransaction();
            this.zzanl = false;
            zzkc();
        } catch (Throwable th3) {
            zzga().endTransaction();
            throw th3;
        }
    }

    public final e zzbt() {
        return this.zzrj;
    }

    final void zzc(zzef zzefVar, zzec zzecVar) {
        bf.checkNotNull(zzefVar);
        bf.id(zzefVar.packageName);
        bf.checkNotNull(zzefVar.zzael);
        bf.id(zzefVar.zzael.name);
        zzgf().zzab();
        zzch();
        if (TextUtils.isEmpty(zzecVar.zzadh)) {
            return;
        }
        if (!zzecVar.zzadr) {
            zzh(zzecVar);
            return;
        }
        zzga().beginTransaction();
        try {
            zzh(zzecVar);
            zzef zzefVarZzh = zzga().zzh(zzefVar.packageName, zzefVar.zzael.name);
            if (zzefVarZzh != null) {
                zzgg().zziq().zze("Removing conditional user property", zzefVar.packageName, zzgb().zzbg(zzefVar.zzael.name));
                zzga().zzi(zzefVar.packageName, zzefVar.zzael.name);
                if (zzefVarZzh.zzaen) {
                    zzga().zzf(zzefVar.packageName, zzefVar.zzael.name);
                }
                if (zzefVar.zzaet != null) {
                    zzc(zzgc().zza(zzefVar.zzaet.name, zzefVar.zzaet.zzafo != null ? zzefVar.zzaet.zzafo.zzif() : null, zzefVarZzh.zzaek, zzefVar.zzaet.zzafz, true, false), zzecVar);
                }
            } else {
                zzgg().zzin().zze("Conditional user property doesn't exist", zzfg.zzbh(zzefVar.packageName), zzgb().zzbg(zzefVar.zzael.name));
            }
            zzga().setTransactionSuccessful();
        } finally {
            zzga().endTransaction();
        }
    }

    final void zzc(zzjs zzjsVar, zzec zzecVar) {
        zzgf().zzab();
        zzch();
        if (TextUtils.isEmpty(zzecVar.zzadh)) {
            return;
        }
        if (!zzecVar.zzadr) {
            zzh(zzecVar);
            return;
        }
        zzgg().zziq().zzg("Removing user property", zzgb().zzbg(zzjsVar.name));
        zzga().beginTransaction();
        try {
            zzh(zzecVar);
            zzga().zzf(zzecVar.packageName, zzjsVar.name);
            zzga().setTransactionSuccessful();
            zzgg().zziq().zzg("User property removed", zzgb().zzbg(zzjsVar.name));
        } finally {
            zzga().endTransaction();
        }
    }

    final void zzch() {
        if (!this.zzvj) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }

    final void zzd(zzec zzecVar) {
        zzei zzeiVarZzga = zzga();
        String str = zzecVar.packageName;
        bf.id(str);
        zzeiVarZzga.zzab();
        zzeiVarZzga.zzch();
        try {
            SQLiteDatabase writableDatabase = zzeiVarZzga.getWritableDatabase();
            String[] strArr = {str};
            int iDelete = writableDatabase.delete("main_event_params", "app_id=?", strArr) + writableDatabase.delete("apps", "app_id=?", strArr) + 0 + writableDatabase.delete("events", "app_id=?", strArr) + writableDatabase.delete("user_attributes", "app_id=?", strArr) + writableDatabase.delete("conditional_properties", "app_id=?", strArr) + writableDatabase.delete("raw_events", "app_id=?", strArr) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr) + writableDatabase.delete("queue", "app_id=?", strArr) + writableDatabase.delete("audience_filter_values", "app_id=?", strArr);
            if (iDelete > 0) {
                zzeiVarZzga.zzgg().zzir().zze("Reset analytics data. app, records", str, Integer.valueOf(iDelete));
            }
        } catch (SQLiteException e) {
            zzeiVarZzga.zzgg().zzil().zze("Error resetting analytics data. appId, error", zzfg.zzbh(str), e);
        }
        zzec zzecVarZza = zza(this.zzqs, zzecVar.packageName, zzecVar.zzadh, zzecVar.zzadr, zzecVar.zzadt, zzecVar.zzadu);
        if (!this.zzamd.zzav(zzecVar.packageName) || zzecVar.zzadr) {
            zzg(zzecVarZza);
        }
    }

    final void zzd(zzef zzefVar) {
        zzec zzecVarZzbt = zzbt(zzefVar.packageName);
        if (zzecVarZzbt != null) {
            zzb(zzefVar, zzecVarZzbt);
        }
    }

    final void zze(zzec zzecVar) {
        zzgf().zzab();
        zzch();
        bf.id(zzecVar.packageName);
        zzh(zzecVar);
    }

    final void zze(zzef zzefVar) {
        zzec zzecVarZzbt = zzbt(zzefVar.packageName);
        if (zzecVarZzbt != null) {
            zzc(zzefVar, zzecVarZzbt);
        }
    }

    final void zze(Runnable runnable) {
        zzgf().zzab();
        if (this.zzang == null) {
            this.zzang = new ArrayList();
        }
        this.zzang.add(runnable);
    }

    public final String zzf(zzec zzecVar) {
        try {
            return (String) zzgf().zzb(new zzgn(this, zzecVar)).get(30000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            zzgg().zzil().zze("Failed to get app instance id. appId", zzfg.zzbh(zzecVar.packageName), e);
            return null;
        }
    }

    public final zzdx zzfs() {
        zza(this.zzamq);
        return this.zzamq;
    }

    public final zzee zzft() {
        zza((zzhk) this.zzamy);
        return this.zzamy;
    }

    public final zzhm zzfu() {
        zza((zzhk) this.zzamp);
        return this.zzamp;
    }

    public final zzfb zzfv() {
        zza((zzhk) this.zzamv);
        return this.zzamv;
    }

    public final zzeo zzfw() {
        zza((zzhk) this.zzamu);
        return this.zzamu;
    }

    public final zzil zzfx() {
        zza((zzhk) this.zzamt);
        return this.zzamt;
    }

    public final zzih zzfy() {
        zza((zzhk) this.zzamo);
        return this.zzamo;
    }

    public final zzfc zzfz() {
        zza((zzhk) this.zzams);
        return this.zzams;
    }

    public final void zzg(zzec zzecVar) {
        int i;
        ApplicationInfo applicationInfo;
        zzgf().zzab();
        zzch();
        bf.checkNotNull(zzecVar);
        bf.id(zzecVar.packageName);
        if (TextUtils.isEmpty(zzecVar.zzadh)) {
            return;
        }
        zzeb zzebVarZzax = zzga().zzax(zzecVar.packageName);
        if (zzebVarZzax != null && TextUtils.isEmpty(zzebVarZzax.getGmpAppId()) && !TextUtils.isEmpty(zzecVar.zzadh)) {
            zzebVarZzax.zzs(0L);
            zzga().zza(zzebVarZzax);
            zzgd().zzbs(zzecVar.packageName);
        }
        if (!zzecVar.zzadr) {
            zzh(zzecVar);
            return;
        }
        long jCurrentTimeMillis = zzecVar.zzaeh;
        if (jCurrentTimeMillis == 0) {
            jCurrentTimeMillis = this.zzrj.currentTimeMillis();
        }
        int i2 = zzecVar.zzaei;
        if (i2 == 0 || i2 == 1) {
            i = i2;
        } else {
            zzgg().zzin().zze("Incorrect app type, assuming installed app. appId, appType", zzfg.zzbh(zzecVar.packageName), Integer.valueOf(i2));
            i = 0;
        }
        zzga().beginTransaction();
        try {
            zzeb zzebVarZzax2 = zzga().zzax(zzecVar.packageName);
            if (zzebVarZzax2 != null && zzebVarZzax2.getGmpAppId() != null && !zzebVarZzax2.getGmpAppId().equals(zzecVar.zzadh)) {
                zzgg().zzin().zzg("New GMP App Id passed in. Removing cached database data. appId", zzfg.zzbh(zzebVarZzax2.zzah()));
                zzei zzeiVarZzga = zzga();
                String strZzah = zzebVarZzax2.zzah();
                zzeiVarZzga.zzch();
                zzeiVarZzga.zzab();
                bf.id(strZzah);
                try {
                    SQLiteDatabase writableDatabase = zzeiVarZzga.getWritableDatabase();
                    String[] strArr = {strZzah};
                    int iDelete = writableDatabase.delete("audience_filter_values", "app_id=?", strArr) + writableDatabase.delete("events", "app_id=?", strArr) + 0 + writableDatabase.delete("user_attributes", "app_id=?", strArr) + writableDatabase.delete("conditional_properties", "app_id=?", strArr) + writableDatabase.delete("apps", "app_id=?", strArr) + writableDatabase.delete("raw_events", "app_id=?", strArr) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr) + writableDatabase.delete("event_filters", "app_id=?", strArr) + writableDatabase.delete("property_filters", "app_id=?", strArr);
                    if (iDelete > 0) {
                        zzeiVarZzga.zzgg().zzir().zze("Deleted application data. app, records", strZzah, Integer.valueOf(iDelete));
                    }
                } catch (SQLiteException e) {
                    zzeiVarZzga.zzgg().zzil().zze("Error deleting application data. appId, error", zzfg.zzbh(strZzah), e);
                }
                zzebVarZzax2 = null;
            }
            if (zzebVarZzax2 != null) {
                if (zzebVarZzax2.zzgo() != -2147483648L) {
                    if (zzebVarZzax2.zzgo() != zzecVar.zzadn) {
                        Bundle bundle = new Bundle();
                        bundle.putString("_pv", zzebVarZzax2.zzag());
                        zzb(new zzeu("_au", new zzer(bundle), "auto", jCurrentTimeMillis), zzecVar);
                    }
                } else if (zzebVarZzax2.zzag() != null && !zzebVarZzax2.zzag().equals(zzecVar.zztc)) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("_pv", zzebVarZzax2.zzag());
                    zzb(new zzeu("_au", new zzer(bundle2), "auto", jCurrentTimeMillis), zzecVar);
                }
            }
            zzh(zzecVar);
            zzeq zzeqVarZze = null;
            if (i == 0) {
                zzeqVarZze = zzga().zze(zzecVar.packageName, "_f");
            } else if (i == 1) {
                zzeqVarZze = zzga().zze(zzecVar.packageName, "_v");
            }
            if (zzeqVarZze == null) {
                long j = (1 + (jCurrentTimeMillis / 3600000)) * 3600000;
                if (i == 0) {
                    zzb(new zzjs("_fot", jCurrentTimeMillis, Long.valueOf(j), "auto"), zzecVar);
                    zzgf().zzab();
                    zzch();
                    Bundle bundle3 = new Bundle();
                    bundle3.putLong("_c", 1L);
                    bundle3.putLong("_r", 1L);
                    bundle3.putLong("_uwa", 0L);
                    bundle3.putLong("_pfo", 0L);
                    bundle3.putLong("_sys", 0L);
                    bundle3.putLong("_sysu", 0L);
                    if (this.zzamd.zzav(zzecVar.packageName) && zzecVar.zzaej) {
                        bundle3.putLong("_dac", 1L);
                    }
                    if (this.zzqs.getPackageManager() == null) {
                        zzgg().zzil().zzg("PackageManager is null, first open report might be inaccurate. appId", zzfg.zzbh(zzecVar.packageName));
                    } else {
                        PackageInfo packageInfo = null;
                        try {
                            packageInfo = c.aV(this.zzqs).getPackageInfo(zzecVar.packageName, 0);
                        } catch (PackageManager.NameNotFoundException e2) {
                            zzgg().zzil().zze("Package info is null, first open report might be inaccurate. appId", zzfg.zzbh(zzecVar.packageName), e2);
                        }
                        if (packageInfo != null && packageInfo.firstInstallTime != 0) {
                            boolean z = false;
                            if (packageInfo.firstInstallTime != packageInfo.lastUpdateTime) {
                                bundle3.putLong("_uwa", 1L);
                            } else {
                                z = true;
                            }
                            zzb(new zzjs("_fi", jCurrentTimeMillis, Long.valueOf(z ? 1L : 0L), "auto"), zzecVar);
                        }
                        try {
                            applicationInfo = c.aV(this.zzqs).getApplicationInfo(zzecVar.packageName, 0);
                        } catch (PackageManager.NameNotFoundException e3) {
                            zzgg().zzil().zze("Application info is null, first open report might be inaccurate. appId", zzfg.zzbh(zzecVar.packageName), e3);
                            applicationInfo = null;
                        }
                        if (applicationInfo != null) {
                            if ((applicationInfo.flags & 1) != 0) {
                                bundle3.putLong("_sys", 1L);
                            }
                            if ((applicationInfo.flags & 128) != 0) {
                                bundle3.putLong("_sysu", 1L);
                            }
                        }
                    }
                    zzei zzeiVarZzga2 = zzga();
                    String str = zzecVar.packageName;
                    bf.id(str);
                    zzeiVarZzga2.zzab();
                    zzeiVarZzga2.zzch();
                    long jZzl = zzeiVarZzga2.zzl(str, "first_open_count");
                    if (jZzl >= 0) {
                        bundle3.putLong("_pfo", jZzl);
                    }
                    zzb(new zzeu("_f", new zzer(bundle3), "auto", jCurrentTimeMillis), zzecVar);
                } else if (i == 1) {
                    zzb(new zzjs("_fvt", jCurrentTimeMillis, Long.valueOf(j), "auto"), zzecVar);
                    zzgf().zzab();
                    zzch();
                    Bundle bundle4 = new Bundle();
                    bundle4.putLong("_c", 1L);
                    bundle4.putLong("_r", 1L);
                    if (this.zzamd.zzav(zzecVar.packageName) && zzecVar.zzaej) {
                        bundle4.putLong("_dac", 1L);
                    }
                    zzb(new zzeu("_v", new zzer(bundle4), "auto", jCurrentTimeMillis), zzecVar);
                }
                Bundle bundle5 = new Bundle();
                bundle5.putLong("_et", 1L);
                zzb(new zzeu("_e", new zzer(bundle5), "auto", jCurrentTimeMillis), zzecVar);
            } else if (zzecVar.zzaeg) {
                zzb(new zzeu("_cd", new zzer(new Bundle()), "auto", jCurrentTimeMillis), zzecVar);
            }
            zzga().setTransactionSuccessful();
            zzga().endTransaction();
        } catch (Throwable th) {
            zzga().endTransaction();
            throw th;
        }
    }

    public final zzei zzga() {
        zza((zzhk) this.zzamr);
        return this.zzamr;
    }

    public final zzfe zzgb() {
        zza((zzhj) this.zzamm);
        return this.zzamm;
    }

    public final zzjv zzgc() {
        zza((zzhj) this.zzaml);
        return this.zzaml;
    }

    public final zzgf zzgd() {
        zza((zzhk) this.zzami);
        return this.zzami;
    }

    public final zzjk zzge() {
        zza((zzhk) this.zzamh);
        return this.zzamh;
    }

    public final zzgg zzgf() {
        zza((zzhk) this.zzamg);
        return this.zzamg;
    }

    public final zzfg zzgg() {
        zza((zzhk) this.zzamf);
        return this.zzamf;
    }

    public final zzfr zzgh() {
        zza((zzhj) this.zzame);
        return this.zzame;
    }

    public final zzeh zzgi() {
        return this.zzamd;
    }

    public final void zzi(boolean z) {
        zzjy();
    }

    protected final boolean zzjk() {
        boolean z = false;
        zzch();
        zzgf().zzab();
        if (this.zzanb == null || this.zzanc == 0 || (this.zzanb != null && !this.zzanb.booleanValue() && Math.abs(this.zzrj.elapsedRealtime() - this.zzanc) > 1000)) {
            this.zzanc = this.zzrj.elapsedRealtime();
            if (zzgc().zzx("android.permission.INTERNET") && zzgc().zzx("android.permission.ACCESS_NETWORK_STATE") && (c.aV(this.zzqs).abO() || (zzgb.zza(this.zzqs) && zzjf.zza(this.zzqs, false)))) {
                z = true;
            }
            this.zzanb = Boolean.valueOf(z);
            if (this.zzanb.booleanValue()) {
                this.zzanb = Boolean.valueOf(zzgc().zzbz(zzfv().getGmpAppId()));
            }
        }
        return this.zzanb.booleanValue();
    }

    public final zzfg zzjl() {
        if (this.zzamf == null || !this.zzamf.isInitialized()) {
            return null;
        }
        return this.zzamf;
    }

    public final zzfx zzjm() {
        return this.zzamz;
    }

    final zzgg zzjn() {
        return this.zzamg;
    }

    public final a zzjo() {
        return this.zzamj;
    }

    public final FirebaseAnalytics zzjp() {
        return this.zzamk;
    }

    public final zzfk zzjq() {
        zza((zzhk) this.zzamn);
        return this.zzamn;
    }

    final long zzju() {
        Long lValueOf = Long.valueOf(zzgh().zzajy.get());
        return lValueOf.longValue() == 0 ? this.zzaeh : Math.min(this.zzaeh, lValueOf.longValue());
    }

    public final void zzjw() {
        zzeb zzebVarZzax;
        String str;
        List<Pair<zzkl, Long>> listSubList;
        zzgf().zzab();
        zzch();
        this.zzann = true;
        try {
            Boolean boolZzkn = zzfx().zzkn();
            if (boolZzkn == null) {
                zzgg().zzin().log("Upload data called on the client side before use of service was decided");
                this.zzann = false;
                zzkc();
                return;
            }
            if (boolZzkn.booleanValue()) {
                zzgg().zzil().log("Upload called in the client side when service should be used");
                this.zzann = false;
                zzkc();
                return;
            }
            if (this.zzank > 0) {
                zzjy();
                this.zzann = false;
                zzkc();
                return;
            }
            zzgf().zzab();
            if (this.zzanf != null) {
                zzgg().zzir().log("Uploading requested multiple times");
                this.zzann = false;
                zzkc();
                return;
            }
            if (!zzjq().zzew()) {
                zzgg().zzir().log("Network not connected, ignoring upload request");
                zzjy();
                this.zzann = false;
                zzkc();
                return;
            }
            long jCurrentTimeMillis = this.zzrj.currentTimeMillis();
            zzd(null, jCurrentTimeMillis - zzeh.zzhk());
            long j = zzgh().zzajt.get();
            if (j != 0) {
                zzgg().zziq().zzg("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(jCurrentTimeMillis - j)));
            }
            String strZzhn = zzga().zzhn();
            if (TextUtils.isEmpty(strZzhn)) {
                this.zzanj = -1L;
                String strZzab = zzga().zzab(jCurrentTimeMillis - zzeh.zzhk());
                if (!TextUtils.isEmpty(strZzab) && (zzebVarZzax = zzga().zzax(strZzab)) != null) {
                    zzb(zzebVarZzax);
                }
            } else {
                if (this.zzanj == -1) {
                    this.zzanj = zzga().zzhu();
                }
                List<Pair<zzkl, Long>> listZzb = zzga().zzb(strZzhn, this.zzamd.zzb(strZzhn, zzew.zzagr), Math.max(0, this.zzamd.zzb(strZzhn, zzew.zzags)));
                if (!listZzb.isEmpty()) {
                    Iterator<Pair<zzkl, Long>> it = listZzb.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str = null;
                            break;
                        }
                        zzkl zzklVar = (zzkl) it.next().first;
                        if (!TextUtils.isEmpty(zzklVar.zzatq)) {
                            str = zzklVar.zzatq;
                            break;
                        }
                    }
                    if (str == null) {
                        listSubList = listZzb;
                        break;
                    }
                    int i = 0;
                    while (true) {
                        if (i >= listZzb.size()) {
                            listSubList = listZzb;
                            break;
                        }
                        zzkl zzklVar2 = (zzkl) listZzb.get(i).first;
                        if (!TextUtils.isEmpty(zzklVar2.zzatq) && !zzklVar2.zzatq.equals(str)) {
                            listSubList = listZzb.subList(0, i);
                            break;
                        }
                        i++;
                    }
                    zzkk zzkkVar = new zzkk();
                    zzkkVar.zzata = new zzkl[listSubList.size()];
                    ArrayList arrayList = new ArrayList(listSubList.size());
                    boolean z = zzeh.zzhm() && this.zzamd.zzat(strZzhn);
                    for (int i2 = 0; i2 < zzkkVar.zzata.length; i2++) {
                        zzkkVar.zzata[i2] = (zzkl) listSubList.get(i2).first;
                        arrayList.add((Long) listSubList.get(i2).second);
                        zzkkVar.zzata[i2].zzatp = 12451L;
                        zzkkVar.zzata[i2].zzatf = Long.valueOf(jCurrentTimeMillis);
                        zzkkVar.zzata[i2].zzatu = false;
                        if (!z) {
                            zzkkVar.zzata[i2].zzauc = null;
                        }
                    }
                    String strZza = zzgg().isLoggable(2) ? zzgb().zza(zzkkVar) : null;
                    byte[] bArrZzb = zzgc().zzb(zzkkVar);
                    String str2 = zzew.zzahb.get();
                    try {
                        URL url = new URL(str2);
                        bf.ca(!arrayList.isEmpty());
                        if (this.zzanf != null) {
                            zzgg().zzil().log("Set uploading progress before finishing the previous upload");
                        } else {
                            this.zzanf = new ArrayList(arrayList);
                        }
                        zzgh().zzaju.set(jCurrentTimeMillis);
                        zzgg().zzir().zzd("Uploading data. app, uncompressed size, data", zzkkVar.zzata.length > 0 ? zzkkVar.zzata[0].zztd : "?", Integer.valueOf(bArrZzb.length), strZza);
                        this.zzanm = true;
                        zzfk zzfkVarZzjq = zzjq();
                        zzgo zzgoVar = new zzgo(this, strZzhn);
                        zzfkVarZzjq.zzab();
                        zzfkVarZzjq.zzch();
                        bf.checkNotNull(url);
                        bf.checkNotNull(bArrZzb);
                        bf.checkNotNull(zzgoVar);
                        zzfkVarZzjq.zzgf().zzd(new zzfo(zzfkVarZzjq, strZzhn, url, bArrZzb, null, zzgoVar));
                    } catch (MalformedURLException e) {
                        zzgg().zzil().zze("Failed to parse upload URL. Not uploading. appId", zzfg.zzbh(strZzhn), str2);
                    }
                }
            }
            this.zzann = false;
            zzkc();
        } catch (Throwable th) {
            this.zzann = false;
            zzkc();
            throw th;
        }
    }

    final void zzjz() {
        this.zzani++;
    }

    final void zzka() {
        zzgf().zzab();
        zzch();
        if (this.zzana) {
            return;
        }
        zzgg().zzip().log("This instance being marked as an uploader");
        zzgf().zzab();
        zzch();
        if (zzkb() && zzjt()) {
            int iZza = zza(this.zzane);
            int iZzii = zzfv().zzii();
            zzgf().zzab();
            if (iZza > iZzii) {
                zzgg().zzil().zze("Panic: can't downgrade version. Previous, current version", Integer.valueOf(iZza), Integer.valueOf(iZzii));
            } else if (iZza < iZzii) {
                if (zza(iZzii, this.zzane)) {
                    zzgg().zzir().zze("Storage version upgraded. Previous, current version", Integer.valueOf(iZza), Integer.valueOf(iZzii));
                } else {
                    zzgg().zzil().zze("Storage version upgrade failed. Previous, current version", Integer.valueOf(iZza), Integer.valueOf(iZzii));
                }
            }
        }
        this.zzana = true;
        zzjy();
    }
}
