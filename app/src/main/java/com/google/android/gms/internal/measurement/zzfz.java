package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
final class zzfz implements ServiceConnection {
    final /* synthetic */ zzfx zzakx;

    private zzfz(zzfx zzfxVar) {
        this.zzakx = zzfxVar;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            this.zzakx.zzacr.zzgg().zzin().log("Install Referrer connection returned with null binder");
            return;
        }
        try {
            this.zzakx.zzakv = zzs.zza(iBinder);
            if (this.zzakx.zzakv == null) {
                this.zzakx.zzacr.zzgg().zzin().log("Install Referrer Service implementation was not found");
            } else {
                this.zzakx.zzacr.zzgg().zzip().log("Install Referrer Service connected");
                this.zzakx.zzacr.zzgf().zzc(new zzga(this));
            }
        } catch (Exception e) {
            this.zzakx.zzacr.zzgg().zzin().zzg("Exception occurred while calling Install Referrer API", e);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.zzakx.zzakv = null;
        this.zzakx.zzacr.zzgg().zzip().log("Install Referrer Service disconnected");
    }
}
