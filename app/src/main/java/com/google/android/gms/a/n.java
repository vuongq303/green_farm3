package com.google.android.gms.a;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.measurement.zzdd;
import com.google.android.gms.internal.measurement.zzu;
import com.google.android.gms.internal.measurement.zzz;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
public final class n {
    private static volatile n bEE;
    private final List<t> bEF;
    private final h bEG;
    private final o bEH;
    private Thread.UncaughtExceptionHandler bEI;
    private final Context zzqs;
    private volatile zzu zzsa;

    private n(Context context) {
        Context applicationContext = context.getApplicationContext();
        bf.checkNotNull(applicationContext);
        this.zzqs = applicationContext;
        this.bEH = new o(this);
        this.bEF = new CopyOnWriteArrayList();
        this.bEG = new h();
    }

    public static n ax(Context context) {
        bf.checkNotNull(context);
        if (bEE == null) {
            synchronized (n.class) {
                if (bEE == null) {
                    bEE = new n(context);
                }
            }
        }
        return bEE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(j jVar) {
        bf.hW("deliver should be called from worker thread");
        bf.b(jVar.Xt(), "Measurement must be submitted");
        List<u> listZzq = jVar.zzq();
        if (listZzq.isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        for (u uVar : listZzq) {
            Uri uriXo = uVar.Xo();
            if (!hashSet.contains(uriXo)) {
                hashSet.add(uriXo);
                uVar.b(jVar);
            }
        }
    }

    public static void zzab() {
        if (!(Thread.currentThread() instanceof q)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final zzu Xx() {
        if (this.zzsa == null) {
            synchronized (this) {
                if (this.zzsa == null) {
                    zzu zzuVar = new zzu();
                    PackageManager packageManager = this.zzqs.getPackageManager();
                    String packageName = this.zzqs.getPackageName();
                    zzuVar.setAppId(packageName);
                    zzuVar.setAppInstallerId(packageManager.getInstallerPackageName(packageName));
                    String str = null;
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(this.zzqs.getPackageName(), 0);
                        if (packageInfo != null) {
                            CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                            if (!TextUtils.isEmpty(applicationLabel)) {
                                packageName = applicationLabel.toString();
                            }
                            str = packageInfo.versionName;
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        String strValueOf = String.valueOf(packageName);
                        Log.e("GAv4", strValueOf.length() != 0 ? "Error retrieving package info: appName set to ".concat(strValueOf) : new String("Error retrieving package info: appName set to "));
                    }
                    zzuVar.setAppName(packageName);
                    zzuVar.setAppVersion(str);
                    this.zzsa = zzuVar;
                }
            }
        }
        return this.zzsa;
    }

    public final zzz Xy() {
        DisplayMetrics displayMetrics = this.zzqs.getResources().getDisplayMetrics();
        zzz zzzVar = new zzz();
        zzzVar.setLanguage(zzdd.zza(Locale.getDefault()));
        zzzVar.zztr = displayMetrics.widthPixels;
        zzzVar.zzts = displayMetrics.heightPixels;
        return zzzVar;
    }

    public final <V> Future<V> a(Callable<V> callable) {
        bf.checkNotNull(callable);
        if (!(Thread.currentThread() instanceof q)) {
            return this.bEH.submit(callable);
        }
        FutureTask futureTask = new FutureTask(callable);
        futureTask.run();
        return futureTask;
    }

    public final void a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.bEI = uncaughtExceptionHandler;
    }

    public final void c(Runnable runnable) {
        bf.checkNotNull(runnable);
        this.bEH.submit(runnable);
    }

    final void e(j jVar) {
        if (jVar.zzw()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        }
        if (jVar.Xt()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        }
        j jVarXp = jVar.Xp();
        jVarXp.Xu();
        this.bEH.execute(new r(this, jVarXp));
    }

    public final Context getContext() {
        return this.zzqs;
    }
}
