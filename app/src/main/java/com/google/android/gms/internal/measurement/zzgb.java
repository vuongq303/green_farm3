package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzgb {
    private final zzge zzakz;

    public zzgb(zzge zzgeVar) {
        bf.checkNotNull(zzgeVar);
        this.zzakz = zzgeVar;
    }

    public static boolean zza(Context context) {
        ActivityInfo receiverInfo;
        bf.checkNotNull(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            return (packageManager == null || (receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 0)) == null || !receiverInfo.enabled) ? false : true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public final void onReceive(Context context, Intent intent) {
        zzgl zzglVarZzg = zzgl.zzg(context);
        zzfg zzfgVarZzgg = zzglVarZzg.zzgg();
        if (intent == null) {
            zzfgVarZzgg.zzin().log("Receiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzfgVarZzgg.zzir().zzg("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzfgVarZzgg.zzir().log("Starting wakeful intent.");
            this.zzakz.doStartService(context, className);
            return;
        }
        if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            try {
                zzglVarZzg.zzgf().zzc(new zzgc(this, zzglVarZzg, zzfgVarZzgg));
            } catch (Exception e) {
                zzfgVarZzgg.zzin().zzg("Install Referrer Reporter encountered a problem", e);
            }
            BroadcastReceiver.PendingResult pendingResultDoGoAsync = this.zzakz.doGoAsync();
            String stringExtra = intent.getStringExtra("referrer");
            if (stringExtra == null) {
                zzfgVarZzgg.zzir().log("Install referrer extras are null");
                if (pendingResultDoGoAsync != null) {
                    pendingResultDoGoAsync.finish();
                    return;
                }
                return;
            }
            zzfgVarZzgg.zzip().zzg("Install referrer extras are", stringExtra);
            if (!stringExtra.contains("?")) {
                String strValueOf = String.valueOf(stringExtra);
                stringExtra = strValueOf.length() != 0 ? "?".concat(strValueOf) : new String("?");
            }
            Bundle bundleZza = zzglVarZzg.zzgc().zza(Uri.parse(stringExtra));
            if (bundleZza == null) {
                zzfgVarZzgg.zzir().log("No campaign defined in install referrer broadcast");
                if (pendingResultDoGoAsync != null) {
                    pendingResultDoGoAsync.finish();
                    return;
                }
                return;
            }
            long longExtra = 1000 * intent.getLongExtra("referrer_timestamp_seconds", 0L);
            if (longExtra == 0) {
                zzfgVarZzgg.zzin().log("Install referrer is missing timestamp");
            }
            zzglVarZzg.zzgf().zzc(new zzgd(this, zzglVarZzg, longExtra, bundleZza, context, zzfgVarZzgg, pendingResultDoGoAsync));
        }
    }
}
