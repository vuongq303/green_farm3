package com.quanhoang.greenfarm3.PushNotification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import java.sql.Timestamp;

/* JADX INFO: compiled from: GCMRegistrar.java */
/* JADX INFO: loaded from: classes.dex */
public final class h {
    private static f Fp;
    private static Context Fq;
    private static String Fr;
    private static PendingIntent Fs;

    static void a(Context context, String... strArr) {
        String strB = b(strArr);
        a(context, 2, "Registering app for senders %s", strB);
        Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
        intent.setPackage("com.google.android.gsf");
        d(context, intent);
        intent.putExtra("sender", strB);
        context.startService(intent);
    }

    static void A(Context context) {
        a(context, 2, "Unregistering app", new Object[0]);
        Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
        intent.setPackage("com.google.android.gsf");
        d(context, intent);
        context.startService(intent);
    }

    static String b(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder sb = new StringBuilder(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            sb.append(',').append(strArr[i]);
        }
        return sb.toString();
    }

    private static synchronized void d(Context context, Intent intent) {
        if (Fs == null) {
            a(context, 2, "Creating pending intent to get package name", new Object[0]);
            Fs = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
        }
        intent.putExtra("app", Fs);
    }

    static synchronized void B(Context context) {
        if (Fp == null) {
            if (Fr == null) {
                a(context, 6, "internal error: retry receiver class not set yet", new Object[0]);
                Fp = new f();
            } else {
                try {
                    Fp = (f) Class.forName(Fr).newInstance();
                } catch (Exception e) {
                    a(context, 6, "Could not create instance of %s. Using %s directly.", Fr, f.class.getName());
                    Fp = new f();
                }
            }
            String packageName = context.getPackageName();
            IntentFilter intentFilter = new IntentFilter("com.google.android.gcm.intent.RETRY");
            intentFilter.addCategory(packageName);
            a(context, 2, "Registering retry receiver", new Object[0]);
            Fq = context;
            Fq.registerReceiver(Fp, intentFilter);
        }
    }

    static synchronized void j(Context context, String str) {
        a(context, 2, "Setting the name of retry receiver class to %s", str);
        Fr = str;
    }

    public static String C(Context context) {
        SharedPreferences sharedPreferencesJ = J(context);
        String string = sharedPreferencesJ.getString("regId", "");
        int i = sharedPreferencesJ.getInt("appVersion", Integer.MIN_VALUE);
        int iG = G(context);
        if (i != Integer.MIN_VALUE && i != iG) {
            a(context, 2, "App version changed from %d to %d;resetting registration id", Integer.valueOf(i), Integer.valueOf(iG));
            E(context);
            return "";
        }
        return string;
    }

    public static boolean D(Context context) {
        return C(context).length() > 0;
    }

    static String E(Context context) {
        a(context, null, 0L);
        return k(context, "");
    }

    static String k(Context context, String str) {
        SharedPreferences sharedPreferencesJ = J(context);
        String string = sharedPreferencesJ.getString("regId", "");
        int iG = G(context);
        a(context, 2, "Saving regId on app version %d", Integer.valueOf(iG));
        SharedPreferences.Editor editorEdit = sharedPreferencesJ.edit();
        editorEdit.putString("regId", str);
        editorEdit.putInt("appVersion", iG);
        editorEdit.commit();
        return string;
    }

    private static void a(Context context, Boolean bool, long j) {
        SharedPreferences.Editor editorEdit = J(context).edit();
        if (bool != null) {
            editorEdit.putBoolean("onServer", bool.booleanValue());
            a(context, 2, "Setting registeredOnServer flag as %b until %s", bool, new Timestamp(j));
        } else {
            a(context, 2, "Setting registeredOnServer expiration to %s", new Timestamp(j));
        }
        editorEdit.putLong("onServerExpirationTime", j);
        editorEdit.commit();
    }

    public static boolean F(Context context) {
        SharedPreferences sharedPreferencesJ = J(context);
        boolean z = sharedPreferencesJ.getBoolean("onServer", false);
        a(context, 2, "Is registered on server: %b", Boolean.valueOf(z));
        if (z) {
            long j = sharedPreferencesJ.getLong("onServerExpirationTime", -1L);
            if (System.currentTimeMillis() > j) {
                a(context, 2, "flag expired on: %s", new Timestamp(j));
                return false;
            }
        }
        return z;
    }

    private static int G(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Coult not get package name: " + e);
        }
    }

    static void H(Context context) {
        a(context, 2, "Resetting backoff", new Object[0]);
        s(context, 3000);
    }

    static int I(Context context) {
        return J(context).getInt("backoff_ms", 3000);
    }

    static void s(Context context, int i) {
        SharedPreferences.Editor editorEdit = J(context).edit();
        editorEdit.putInt("backoff_ms", i);
        editorEdit.commit();
    }

    private static SharedPreferences J(Context context) {
        return context.getSharedPreferences("com.google.android.gcm", 0);
    }

    private static void a(Context context, int i, String str, Object... objArr) {
        if (Log.isLoggable("GCMRegistrar", i)) {
            String str2 = "[" + context.getPackageName() + "]: " + String.format(str, objArr);
        }
    }
}
