package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.a.n;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.stats.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzaz implements ServiceConnection {
    final /* synthetic */ zzax zzwk;
    private volatile zzci zzwl;
    private volatile boolean zzwm;

    protected zzaz(zzax zzaxVar) {
        this.zzwk = zzaxVar;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzci zzcjVar;
        bf.hV("AnalyticsServiceConnection.onServiceConnected");
        synchronized (this) {
            try {
                if (iBinder == null) {
                    this.zzwk.zzu("Service connected with null binder");
                    notifyAll();
                    return;
                }
                try {
                    String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                    if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(interfaceDescriptor)) {
                        if (iBinder == null) {
                            zzcjVar = null;
                        } else {
                            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                            zzcjVar = iInterfaceQueryLocalInterface instanceof zzci ? (zzci) iInterfaceQueryLocalInterface : new zzcj(iBinder);
                        }
                        try {
                            this.zzwk.zzq("Bound to IAnalyticsService interface");
                        } catch (RemoteException e) {
                            this.zzwk.zzu("Service connect failed to get IAnalyticsService");
                        }
                    } else {
                        this.zzwk.zze("Got binder with a wrong descriptor", interfaceDescriptor);
                        zzcjVar = null;
                    }
                } catch (RemoteException e2) {
                    zzcjVar = null;
                }
                if (zzcjVar == null) {
                    try {
                        a.aaY().a(this.zzwk.getContext(), this.zzwk.zzwg);
                    } catch (IllegalArgumentException e3) {
                    }
                } else if (this.zzwm) {
                    this.zzwl = zzcjVar;
                } else {
                    this.zzwk.zzt("onServiceConnected received after the timeout limit");
                    this.zzwk.zzbw().c(new zzba(this, zzcjVar));
                }
                notifyAll();
            } catch (Throwable th) {
                notifyAll();
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        bf.hV("AnalyticsServiceConnection.onServiceDisconnected");
        this.zzwk.zzbw().c(new zzbb(this, componentName));
    }

    public final zzci zzcw() {
        zzci zzciVar = null;
        n.zzab();
        Intent intent = new Intent("com.google.android.gms.analytics.service.START");
        intent.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.analytics.service.AnalyticsService"));
        Context context = this.zzwk.getContext();
        intent.putExtra("app_package_name", context.getPackageName());
        a aVarAaY = a.aaY();
        synchronized (this) {
            this.zzwl = null;
            this.zzwm = true;
            boolean zA = aVarAaY.a(context, intent, this.zzwk.zzwg, 129);
            this.zzwk.zza("Bind to service requested", Boolean.valueOf(zA));
            if (zA) {
                try {
                    wait(zzcc.zzzq.get().longValue());
                } catch (InterruptedException e) {
                    this.zzwk.zzt("Wait for service connect was interrupted");
                }
                this.zzwm = false;
                zzciVar = this.zzwl;
                this.zzwl = null;
                if (zzciVar == null) {
                    this.zzwk.zzu("Successfully bound to service but never got onServiceConnected callback");
                }
            } else {
                this.zzwm = false;
            }
        }
        return zzciVar;
    }
}
