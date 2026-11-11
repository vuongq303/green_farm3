package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.e.a;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.FirebaseApp;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseInstanceId {
    private static final long zzbqi = TimeUnit.HOURS.toSeconds(8);
    private static Map<String, FirebaseInstanceId> zzbqj = new a();
    private static zzaa zzbqk;

    @GuardedBy("FirebaseInstanceId.class")
    private static ScheduledThreadPoolExecutor zzbql;
    private final FirebaseApp zzbqm;
    private final zzw zzbqn;
    private final zzx zzbqo;

    @GuardedBy("this")
    private KeyPair zzbqp;

    @GuardedBy("this")
    private boolean zzbqq = false;

    @GuardedBy("this")
    private boolean zzbqr;

    private FirebaseInstanceId(FirebaseApp firebaseApp, zzw zzwVar) {
        if (zzw.zza(firebaseApp) == null) {
            throw new IllegalStateException("FirebaseInstanceId failed to initialize, FirebaseApp is missing project ID");
        }
        this.zzbqm = firebaseApp;
        this.zzbqn = zzwVar;
        this.zzbqo = new zzx(firebaseApp.getApplicationContext(), zzwVar);
        this.zzbqr = zzsm();
        if (zzso()) {
            zzse();
        }
    }

    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    public static synchronized FirebaseInstanceId getInstance(FirebaseApp firebaseApp) {
        FirebaseInstanceId firebaseInstanceId;
        firebaseInstanceId = zzbqj.get(firebaseApp.getOptions().getApplicationId());
        if (firebaseInstanceId == null) {
            if (zzbqk == null) {
                zzbqk = new zzaa(firebaseApp.getApplicationContext());
            }
            firebaseInstanceId = new FirebaseInstanceId(firebaseApp, new zzw(firebaseApp.getApplicationContext()));
            zzbqj.put(firebaseApp.getOptions().getApplicationId(), firebaseInstanceId);
        }
        return firebaseInstanceId;
    }

    private final synchronized KeyPair getKeyPair() {
        if (this.zzbqp == null) {
            this.zzbqp = zzbqk.zzfd("");
        }
        if (this.zzbqp == null) {
            this.zzbqp = zzbqk.zzfb("");
        }
        return this.zzbqp;
    }

    private final synchronized void startSync() {
        if (!this.zzbqq) {
            zzan(0L);
        }
    }

    static void zza(Runnable runnable, long j) {
        synchronized (FirebaseInstanceId.class) {
            if (zzbql == null) {
                zzbql = new ScheduledThreadPoolExecutor(1);
            }
            zzbql.schedule(runnable, j, TimeUnit.SECONDS);
        }
    }

    private final String zzb(String str, String str2, Bundle bundle) throws IOException {
        bundle.putString("scope", str2);
        bundle.putString("sender", str);
        bundle.putString("subtype", str);
        bundle.putString("appid", getId());
        bundle.putString("gmp_app_id", this.zzbqm.getOptions().getApplicationId());
        bundle.putString("gmsv", Integer.toString(this.zzbqn.zzsx()));
        bundle.putString("osv", Integer.toString(Build.VERSION.SDK_INT));
        bundle.putString("app_ver", this.zzbqn.zzsv());
        bundle.putString("app_ver_name", this.zzbqn.zzsw());
        bundle.putString("cliv", "fiid-12451000");
        Bundle bundleZzi = this.zzbqo.zzi(bundle);
        if (bundleZzi == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String string = bundleZzi.getString("registration_id");
        if (string != null || (string = bundleZzi.getString("unregistered")) != null) {
            return string;
        }
        String string2 = bundleZzi.getString("error");
        if ("RST".equals(string2)) {
            zzsk();
            throw new IOException("INSTANCE_ID_RESET");
        }
        if (string2 != null) {
            throw new IOException(string2);
        }
        String strValueOf = String.valueOf(bundleZzi);
        Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf).length() + 21).append("Unexpected response: ").append(strValueOf).toString(), new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private final void zzse() {
        zzab zzabVarZzsg = zzsg();
        if (zzabVarZzsg == null || zzabVarZzsg.zzff(this.zzbqn.zzsv()) || zzbqk.zztc() != null) {
            startSync();
        }
    }

    static zzaa zzsi() {
        return zzbqk;
    }

    static boolean zzsj() {
        return Log.isLoggable("FirebaseInstanceId", 3) || (Build.VERSION.SDK_INT == 23 && Log.isLoggable("FirebaseInstanceId", 3));
    }

    private final boolean zzsm() {
        ApplicationInfo applicationInfo;
        Context applicationContext = this.zzbqm.getApplicationContext();
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("com.google.firebase.messaging", 0);
        if (sharedPreferences.contains("auto_init")) {
            return sharedPreferences.getBoolean("auto_init", true);
        }
        try {
            PackageManager packageManager = applicationContext.getPackageManager();
            if (packageManager != null && (applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128)) != null && applicationInfo.metaData != null && applicationInfo.metaData.containsKey("firebase_messaging_auto_init_enabled")) {
                return applicationInfo.metaData.getBoolean("firebase_messaging_auto_init_enabled");
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return zzsn();
    }

    private final boolean zzsn() {
        try {
            Class.forName("com.google.firebase.messaging.FirebaseMessaging");
            return true;
        } catch (ClassNotFoundException e) {
            Context applicationContext = this.zzbqm.getApplicationContext();
            Intent intent = new Intent("com.google.firebase.MESSAGING_EVENT");
            intent.setPackage(applicationContext.getPackageName());
            ResolveInfo resolveInfoResolveService = applicationContext.getPackageManager().resolveService(intent, 0);
            return (resolveInfoResolveService == null || resolveInfoResolveService.serviceInfo == null) ? false : true;
        }
    }

    public void deleteInstanceId() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Bundle bundle = new Bundle();
        bundle.putString("iid-operation", "delete");
        bundle.putString("delete", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        zzb("*", "*", bundle);
        zzsk();
    }

    public void deleteToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Bundle bundle = new Bundle();
        bundle.putString("delete", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        zzb(str, str2, bundle);
        zzbqk.zzk("", str, str2);
    }

    public long getCreationTime() {
        return zzbqk.zzfa("");
    }

    public String getId() {
        zzse();
        return zzw.zza(getKeyPair());
    }

    public String getToken() {
        zzab zzabVarZzsg = zzsg();
        if (zzabVarZzsg == null || zzabVarZzsg.zzff(this.zzbqn.zzsv())) {
            startSync();
        }
        if (zzabVarZzsg != null) {
            return zzabVarZzsg.zzbsb;
        }
        return null;
    }

    public String getToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        zzab zzabVarZzj = zzbqk.zzj("", str, str2);
        if (zzabVarZzj != null && !zzabVarZzj.zzff(this.zzbqn.zzsv())) {
            return zzabVarZzj.zzbsb;
        }
        String strZzb = zzb(str, str2, new Bundle());
        if (strZzb == null) {
            return strZzb;
        }
        zzbqk.zza("", str, str2, strZzb, this.zzbqn.zzsv());
        return strZzb;
    }

    final synchronized void zzan(long j) {
        zza(new zzac(this, this.zzbqn, Math.min(Math.max(30L, j << 1), zzbqi)), j);
        this.zzbqq = true;
    }

    public final synchronized void zzev(String str) {
        zzbqk.zzev(str);
        startSync();
    }

    final void zzew(String str) throws IOException {
        zzab zzabVarZzsg = zzsg();
        if (zzabVarZzsg == null || zzabVarZzsg.zzff(this.zzbqn.zzsv())) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String strValueOf = String.valueOf("/topics/");
        String strValueOf2 = String.valueOf(str);
        bundle.putString("gcm.topic", strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
        String str2 = zzabVarZzsg.zzbsb;
        String strValueOf3 = String.valueOf("/topics/");
        String strValueOf4 = String.valueOf(str);
        zzb(str2, strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3), bundle);
    }

    final void zzex(String str) throws IOException {
        zzab zzabVarZzsg = zzsg();
        if (zzabVarZzsg == null || zzabVarZzsg.zzff(this.zzbqn.zzsv())) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String strValueOf = String.valueOf("/topics/");
        String strValueOf2 = String.valueOf(str);
        bundle.putString("gcm.topic", strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
        bundle.putString("delete", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        String str2 = zzabVarZzsg.zzbsb;
        String strValueOf3 = String.valueOf("/topics/");
        String strValueOf4 = String.valueOf(str);
        zzb(str2, strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3), bundle);
    }

    final FirebaseApp zzsf() {
        return this.zzbqm;
    }

    final zzab zzsg() {
        return zzbqk.zzj("", zzw.zza(this.zzbqm), "*");
    }

    final String zzsh() {
        return getToken(zzw.zza(this.zzbqm), "*");
    }

    final synchronized void zzsk() {
        zzbqk.zztd();
        this.zzbqp = null;
        if (zzso()) {
            startSync();
        }
    }

    final void zzsl() {
        zzbqk.zzfc("");
        startSync();
    }

    public final synchronized boolean zzso() {
        return this.zzbqr;
    }

    final synchronized void zzu(boolean z) {
        this.zzbqq = z;
    }

    public final synchronized void zzv(boolean z) {
        SharedPreferences.Editor editorEdit = this.zzbqm.getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit();
        editorEdit.putBoolean("auto_init", z);
        editorEdit.apply();
        if (!this.zzbqr && z) {
            zzse();
        }
        this.zzbqr = z;
    }
}
