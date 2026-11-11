package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.support.v4.content.v;
import android.support.v4.e.r;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class zzz {
    private static zzz zzbru;

    @GuardedBy("serviceClassNames")
    private final r<String, String> zzbrv = new r<>();
    private Boolean zzbrw = null;
    final Queue<Intent> zzbrx = new ArrayDeque();
    private final Queue<Intent> zzbry = new ArrayDeque();

    private zzz() {
    }

    public static PendingIntent zza(Context context, int i, Intent intent, int i2) {
        Intent intent2 = new Intent(context, (Class<?>) FirebaseInstanceIdReceiver.class);
        intent2.setAction("com.google.firebase.MESSAGING_EVENT");
        intent2.putExtra("wrapped_intent", intent);
        return PendingIntent.getBroadcast(context, i, intent2, 1073741824);
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:46:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:53:0x010b  */
    private final int zzb(Context context, Intent intent) {
        String strConcat;
        String strValueOf;
        String str;
        ComponentName componentNameStartService;
        synchronized (this.zzbrv) {
            strConcat = this.zzbrv.get(intent.getAction());
        }
        if (strConcat != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                strValueOf = String.valueOf(strConcat);
                if (strValueOf.length() != 0) {
                    str = "Restricting intent to a specific service: ".concat(strValueOf);
                } else {
                    str = new String("Restricting intent to a specific service: ");
                }
                Log.d("FirebaseInstanceId", str);
            }
            intent.setClassName(context.getPackageName(), strConcat);
        } else {
            ResolveInfo resolveInfoResolveService = context.getPackageManager().resolveService(intent, 0);
            if (resolveInfoResolveService == null || resolveInfoResolveService.serviceInfo == null) {
                Log.e("FirebaseInstanceId", "Failed to resolve target intent service, skipping classname enforcement");
            } else {
                ServiceInfo serviceInfo = resolveInfoResolveService.serviceInfo;
                if (!context.getPackageName().equals(serviceInfo.packageName) || serviceInfo.name == null) {
                    String str2 = serviceInfo.packageName;
                    String str3 = serviceInfo.name;
                    Log.e("FirebaseInstanceId", new StringBuilder(String.valueOf(str2).length() + 94 + String.valueOf(str3).length()).append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ").append(str2).append("/").append(str3).toString());
                } else {
                    strConcat = serviceInfo.name;
                    if (strConcat.startsWith(".")) {
                        String strValueOf2 = String.valueOf(context.getPackageName());
                        String strValueOf3 = String.valueOf(strConcat);
                        strConcat = strValueOf3.length() != 0 ? strValueOf2.concat(strValueOf3) : new String(strValueOf2);
                    }
                    synchronized (this.zzbrv) {
                        this.zzbrv.put(intent.getAction(), strConcat);
                    }
                    if (Log.isLoggable("FirebaseInstanceId", 3)) {
                        strValueOf = String.valueOf(strConcat);
                        if (strValueOf.length() != 0) {
                            str = "Restricting intent to a specific service: ".concat(strValueOf);
                        } else {
                            str = new String("Restricting intent to a specific service: ");
                        }
                        Log.d("FirebaseInstanceId", str);
                    }
                    intent.setClassName(context.getPackageName(), strConcat);
                }
            }
        }
        try {
            if (this.zzbrw == null) {
                this.zzbrw = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
            }
            if (this.zzbrw.booleanValue()) {
                componentNameStartService = v.startWakefulService(context, intent);
            } else {
                componentNameStartService = context.startService(intent);
                Log.d("FirebaseInstanceId", "Missing wake lock permission, service start may be delayed");
            }
            if (componentNameStartService != null) {
                return -1;
            }
            Log.e("FirebaseInstanceId", "Error while delivering the message: ServiceIntent not found.");
            return 404;
        } catch (IllegalStateException e) {
            String strValueOf4 = String.valueOf(e);
            Log.e("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf4).length() + 45).append("Failed to start service while in background: ").append(strValueOf4).toString());
            return 402;
        } catch (SecurityException e2) {
            Log.e("FirebaseInstanceId", "Error while delivering the message to the serviceIntent", e2);
            return 401;
        }
    }

    public static synchronized zzz zzta() {
        if (zzbru == null) {
            zzbru = new zzz();
        }
        return zzbru;
    }

    public final int zza(Context context, String str, Intent intent) {
        switch (str) {
            case "com.google.firebase.INSTANCE_ID_EVENT":
                this.zzbrx.offer(intent);
                break;
            case "com.google.firebase.MESSAGING_EVENT":
                this.zzbry.offer(intent);
                break;
            default:
                String strValueOf = String.valueOf(str);
                Log.w("FirebaseInstanceId", strValueOf.length() != 0 ? "Unknown service action: ".concat(strValueOf) : new String("Unknown service action: "));
                return 500;
        }
        Intent intent2 = new Intent(str);
        intent2.setPackage(context.getPackageName());
        return zzb(context, intent2);
    }

    public final Intent zztb() {
        return this.zzbry.poll();
    }
}
