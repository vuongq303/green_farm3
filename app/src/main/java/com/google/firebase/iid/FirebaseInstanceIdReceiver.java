package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.content.v;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.d.s;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class FirebaseInstanceIdReceiver extends v {
    private static boolean zzbqs = false;

    @GuardedBy("FirebaseInstanceIdReceiver.class")
    private static zzh zzbqt;

    @GuardedBy("FirebaseInstanceIdReceiver.class")
    private static zzh zzbqu;

    private final void zza(Context context, Intent intent, String str) {
        String str2 = null;
        boolean z = false;
        int iZza = -1;
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (Build.VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        String stringExtra = intent.getStringExtra("gcm.rawData64");
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra("gcm.rawData64");
        }
        if ("google.com/iid".equals(intent.getStringExtra("from")) || "com.google.firebase.INSTANCE_ID_EVENT".equals(str)) {
            str2 = "com.google.firebase.INSTANCE_ID_EVENT";
        } else if ("com.google.android.c2dm.intent.RECEIVE".equals(str) || "com.google.firebase.MESSAGING_EVENT".equals(str)) {
            str2 = "com.google.firebase.MESSAGING_EVENT";
        } else {
            Log.d("FirebaseInstanceId", "Unexpected intent");
        }
        if (str2 != null) {
            if (s.ax() && context.getApplicationInfo().targetSdkVersion >= 26) {
                z = true;
            }
            if (z) {
                if (isOrderedBroadcast()) {
                    setResultCode(-1);
                }
                zzi(context, str2).zza(intent, goAsync());
            } else {
                iZza = zzz.zzta().zza(context, str2, intent);
            }
        }
        if (isOrderedBroadcast()) {
            setResultCode(iZza);
        }
    }

    private static synchronized zzh zzi(Context context, String str) {
        zzh zzhVar;
        if ("com.google.firebase.MESSAGING_EVENT".equals(str)) {
            if (zzbqu == null) {
                zzbqu = new zzh(context, str);
            }
            zzhVar = zzbqu;
        } else {
            if (zzbqt == null) {
                zzbqt = new zzh(context, str);
            }
            zzhVar = zzbqt;
        }
        return zzhVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Parcelable parcelableExtra = intent.getParcelableExtra("wrapped_intent");
        if (parcelableExtra instanceof Intent) {
            zza(context, (Intent) parcelableExtra, intent.getAction());
        } else {
            zza(context, intent, intent.getAction());
        }
    }
}
