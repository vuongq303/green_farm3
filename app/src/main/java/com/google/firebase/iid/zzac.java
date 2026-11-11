package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class zzac implements Runnable {
    private final zzw zzbqn;
    private final long zzbsc;
    private final PowerManager.WakeLock zzbsd = ((PowerManager) getContext().getSystemService("power")).newWakeLock(1, "fiid-sync");
    private final FirebaseInstanceId zzbse;

    zzac(FirebaseInstanceId firebaseInstanceId, zzw zzwVar, long j) {
        this.zzbse = firebaseInstanceId;
        this.zzbqn = zzwVar;
        this.zzbsc = j;
        this.zzbsd.setReferenceCounted(false);
    }

    private final boolean zzfg(String str) {
        String[] strArrSplit = str.split("!");
        if (strArrSplit.length != 2) {
            return true;
        }
        String str2 = strArrSplit[0];
        String str3 = strArrSplit[1];
        byte b = -1;
        try {
            switch (str2.hashCode()) {
                case 83:
                    if (str2.equals("S")) {
                        b = 0;
                    }
                    break;
                case 85:
                    if (str2.equals("U")) {
                        b = 1;
                    }
                    break;
            }
            switch (b) {
                case 0:
                    this.zzbse.zzew(str3);
                    if (!FirebaseInstanceId.zzsj()) {
                        return true;
                    }
                    Log.d("FirebaseInstanceId", "subscribe operation succeeded");
                    return true;
                case 1:
                    this.zzbse.zzex(str3);
                    if (!FirebaseInstanceId.zzsj()) {
                        return true;
                    }
                    Log.d("FirebaseInstanceId", "unsubscribe operation succeeded");
                    return true;
                default:
                    return true;
            }
        } catch (IOException e) {
            String strValueOf = String.valueOf(e.getMessage());
            Log.e("FirebaseInstanceId", strValueOf.length() != 0 ? "Topic sync failed: ".concat(strValueOf) : new String("Topic sync failed: "));
            return false;
        }
    }

    private final boolean zzte() {
        zzab zzabVarZzsg = this.zzbse.zzsg();
        if (zzabVarZzsg != null && !zzabVarZzsg.zzff(this.zzbqn.zzsv())) {
            return true;
        }
        try {
            String strZzsh = this.zzbse.zzsh();
            if (strZzsh == null) {
                Log.e("FirebaseInstanceId", "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Token successfully retrieved");
            }
            if (zzabVarZzsg != null && (zzabVarZzsg == null || strZzsh.equals(zzabVarZzsg.zzbsb))) {
                return true;
            }
            Context context = getContext();
            Intent intent = new Intent("com.google.firebase.iid.TOKEN_REFRESH");
            Intent intent2 = new Intent("com.google.firebase.INSTANCE_ID_EVENT");
            intent2.setClass(context, FirebaseInstanceIdReceiver.class);
            intent2.putExtra("wrapped_intent", intent);
            context.sendBroadcast(intent2);
            return true;
        } catch (IOException | SecurityException e) {
            String strValueOf = String.valueOf(e.getMessage());
            Log.e("FirebaseInstanceId", strValueOf.length() != 0 ? "Token retrieval failed: ".concat(strValueOf) : new String("Token retrieval failed: "));
            return false;
        }
    }

    private final boolean zztf() {
        while (true) {
            synchronized (this.zzbse) {
                String strZztc = FirebaseInstanceId.zzsi().zztc();
                if (strZztc == null) {
                    Log.d("FirebaseInstanceId", "topic sync succeeded");
                    return true;
                }
                if (!zzfg(strZztc)) {
                    return false;
                }
                FirebaseInstanceId.zzsi().zzez(strZztc);
            }
        }
    }

    final Context getContext() {
        return this.zzbse.zzsf().getApplicationContext();
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbsd.acquire();
        try {
            this.zzbse.zzu(true);
            if (!(this.zzbqn.zzsu() != 0)) {
                this.zzbse.zzu(false);
                return;
            }
            if (!zztg()) {
                new zzad(this).zzth();
                return;
            }
            if (zzte() && zztf()) {
                this.zzbse.zzu(false);
            } else {
                this.zzbse.zzan(this.zzbsc);
            }
        } finally {
            this.zzbsd.release();
        }
    }

    final boolean zztg() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
