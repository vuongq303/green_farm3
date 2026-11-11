package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.d.s;
import com.google.firebase.FirebaseApp;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class zzw {

    @GuardedBy("this")
    private String zzbrk;

    @GuardedBy("this")
    private String zzbrl;

    @GuardedBy("this")
    private int zzbrm;

    @GuardedBy("this")
    private int zzbrn = 0;
    private final Context zzqs;

    public zzw(Context context) {
        this.zzqs = context;
    }

    public static String zza(FirebaseApp firebaseApp) {
        String gcmSenderId = firebaseApp.getOptions().getGcmSenderId();
        if (gcmSenderId != null) {
            return gcmSenderId;
        }
        String applicationId = firebaseApp.getOptions().getApplicationId();
        if (!applicationId.startsWith("1:")) {
            return applicationId;
        }
        String[] strArrSplit = applicationId.split(":");
        if (strArrSplit.length < 2) {
            return null;
        }
        String str = strArrSplit[1];
        if (str.isEmpty()) {
            return null;
        }
        return str;
    }

    public static String zza(KeyPair keyPair) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            bArrDigest[0] = (byte) ((bArrDigest[0] & 15) + 112);
            return Base64.encodeToString(bArrDigest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("FirebaseInstanceId", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    private final PackageInfo zzey(String str) {
        try {
            return this.zzqs.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            String strValueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf).length() + 23).append("Failed to find package ").append(strValueOf).toString());
            return null;
        }
    }

    private final synchronized void zzsy() {
        PackageInfo packageInfoZzey = zzey(this.zzqs.getPackageName());
        if (packageInfoZzey != null) {
            this.zzbrk = Integer.toString(packageInfoZzey.versionCode);
            this.zzbrl = packageInfoZzey.versionName;
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0051 A[Catch: all -> 0x0027, TryCatch #0 {, blocks: (B:4:0x0002, B:6:0x0006, B:9:0x000a, B:11:0x001d, B:16:0x002a, B:18:0x0030, B:20:0x0045, B:22:0x004b, B:23:0x0051, B:25:0x0066, B:27:0x006c, B:28:0x0072, B:30:0x0081, B:31:0x0084, B:32:0x0087), top: B:34:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0072 A[Catch: all -> 0x0027, TryCatch #0 {, blocks: (B:4:0x0002, B:6:0x0006, B:9:0x000a, B:11:0x001d, B:16:0x002a, B:18:0x0030, B:20:0x0045, B:22:0x004b, B:23:0x0051, B:25:0x0066, B:27:0x006c, B:28:0x0072, B:30:0x0081, B:31:0x0084, B:32:0x0087), top: B:34:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x0081 A[Catch: all -> 0x0027, TryCatch #0 {, blocks: (B:4:0x0002, B:6:0x0006, B:9:0x000a, B:11:0x001d, B:16:0x002a, B:18:0x0030, B:20:0x0045, B:22:0x004b, B:23:0x0051, B:25:0x0066, B:27:0x006c, B:28:0x0072, B:30:0x0081, B:31:0x0084, B:32:0x0087), top: B:34:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0087 A[Catch: all -> 0x0027, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0002, B:6:0x0006, B:9:0x000a, B:11:0x001d, B:16:0x002a, B:18:0x0030, B:20:0x0045, B:22:0x004b, B:23:0x0051, B:25:0x0066, B:27:0x006c, B:28:0x0072, B:30:0x0081, B:31:0x0084, B:32:0x0087), top: B:34:0x0002 }] */
    public final synchronized int zzsu() {
        List<ResolveInfo> listQueryBroadcastReceivers;
        int i = 0;
        synchronized (this) {
            if (this.zzbrn != 0) {
                i = this.zzbrn;
            } else {
                PackageManager packageManager = this.zzqs.getPackageManager();
                if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms") == -1) {
                    Log.e("FirebaseInstanceId", "Google Play services missing or without correct permission.");
                } else if (s.ax()) {
                    Intent intent = new Intent("com.google.iid.TOKEN_REQUEST");
                    intent.setPackage("com.google.android.gms");
                    listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 0);
                    if (listQueryBroadcastReceivers != null) {
                        Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
                        if (s.ax()) {
                            this.zzbrn = 2;
                        } else {
                            this.zzbrn = 1;
                        }
                        i = this.zzbrn;
                    } else {
                        Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
                        if (s.ax()) {
                            this.zzbrn = 2;
                        } else {
                            this.zzbrn = 1;
                        }
                        i = this.zzbrn;
                    }
                } else {
                    Intent intent2 = new Intent("com.google.android.c2dm.intent.REGISTER");
                    intent2.setPackage("com.google.android.gms");
                    List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent2, 0);
                    if (listQueryIntentServices == null || listQueryIntentServices.size() <= 0) {
                        Intent intent3 = new Intent("com.google.iid.TOKEN_REQUEST");
                        intent3.setPackage("com.google.android.gms");
                        listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent3, 0);
                        if (listQueryBroadcastReceivers != null || listQueryBroadcastReceivers.size() <= 0) {
                            Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
                            if (s.ax()) {
                                this.zzbrn = 2;
                            } else {
                                this.zzbrn = 1;
                            }
                            i = this.zzbrn;
                        } else {
                            this.zzbrn = 2;
                            i = this.zzbrn;
                        }
                    } else {
                        this.zzbrn = 1;
                        i = this.zzbrn;
                    }
                }
            }
        }
        return i;
    }

    public final synchronized String zzsv() {
        if (this.zzbrk == null) {
            zzsy();
        }
        return this.zzbrk;
    }

    public final synchronized String zzsw() {
        if (this.zzbrl == null) {
            zzsy();
        }
        return this.zzbrl;
    }

    public final synchronized int zzsx() {
        PackageInfo packageInfoZzey;
        if (this.zzbrm == 0 && (packageInfoZzey = zzey("com.google.android.gms")) != null) {
            this.zzbrm = packageInfoZzey.versionCode;
        }
        return this.zzbrm;
    }
}
