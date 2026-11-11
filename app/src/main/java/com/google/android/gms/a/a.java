package com.google.android.gms.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.measurement.zzat;
import com.google.android.gms.internal.measurement.zzbu;
import com.google.android.gms.internal.measurement.zzcm;
import com.google.android.gms.internal.measurement.zzdd;

/* JADX INFO: loaded from: classes.dex */
public class a extends BroadcastReceiver {
    private static Boolean zzqo;

    public static boolean zza(Context context) {
        bf.checkNotNull(context);
        if (zzqo != null) {
            return zzqo.booleanValue();
        }
        boolean zZza = zzdd.zza(context, "com.google.android.gms.analytics.CampaignTrackingReceiver", true);
        zzqo = Boolean.valueOf(zZza);
        return zZza;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        zzat zzatVarZzc = zzat.zzc(context);
        zzcm zzcmVarZzbu = zzatVarZzc.zzbu();
        if (intent == null) {
            zzcmVarZzbu.zzt("CampaignTrackingReceiver received null intent");
            return;
        }
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zzcmVarZzbu.zza("CampaignTrackingReceiver received", action);
        if (!"com.android.vending.INSTALL_REFERRER".equals(action) || TextUtils.isEmpty(stringExtra)) {
            zzcmVarZzbu.zzt("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        zza(context, stringExtra);
        int iZzdu = zzbu.zzdu();
        if (stringExtra.length() > iZzdu) {
            zzcmVarZzbu.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(stringExtra.length()), Integer.valueOf(iZzdu));
            stringExtra = stringExtra.substring(0, iZzdu);
        }
        zzatVarZzc.zzby().zza(stringExtra, (Runnable) new f(this, goAsync()));
    }

    protected void zza(Context context, String str) {
    }
}
