package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
class zzfp extends BroadcastReceiver {
    private static final String zzaar = zzfp.class.getName();
    private boolean zzaas;
    private boolean zzaat;
    private final zzgl zzacr;

    zzfp(zzgl zzglVar) {
        bf.checkNotNull(zzglVar);
        this.zzacr = zzglVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.zzacr.zzch();
        String action = intent.getAction();
        this.zzacr.zzgg().zzir().zzg("NetworkBroadcastReceiver received action", action);
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            this.zzacr.zzgg().zzin().zzg("NetworkBroadcastReceiver received unknown action", action);
            return;
        }
        boolean zZzew = this.zzacr.zzjq().zzew();
        if (this.zzaat != zZzew) {
            this.zzaat = zZzew;
            this.zzacr.zzgf().zzc(new zzfq(this, zZzew));
        }
    }

    public final void unregister() {
        this.zzacr.zzch();
        this.zzacr.zzgf().zzab();
        this.zzacr.zzgf().zzab();
        if (this.zzaas) {
            this.zzacr.zzgg().zzir().log("Unregistering connectivity change receiver");
            this.zzaas = false;
            this.zzaat = false;
            try {
                this.zzacr.getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzacr.zzgg().zzil().zzg("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    public final void zzet() {
        this.zzacr.zzch();
        this.zzacr.zzgf().zzab();
        if (this.zzaas) {
            return;
        }
        this.zzacr.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.zzaat = this.zzacr.zzjq().zzew();
        this.zzacr.zzgg().zzir().zzg("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzaat));
        this.zzaas = true;
    }
}
