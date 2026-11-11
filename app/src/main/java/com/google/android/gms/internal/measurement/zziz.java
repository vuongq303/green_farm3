package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.f;
import com.google.android.gms.common.internal.g;
import com.google.android.gms.common.stats.a;

/* JADX INFO: loaded from: classes.dex */
public final class zziz implements ServiceConnection, f, g {
    final /* synthetic */ zzil zzapy;
    private volatile boolean zzaqf;
    private volatile zzff zzaqg;

    protected zziz(zzil zzilVar) {
        this.zzapy = zzilVar;
    }

    static /* synthetic */ boolean zza(zziz zzizVar, boolean z) {
        zzizVar.zzaqf = false;
        return false;
    }

    @Override // com.google.android.gms.common.internal.f
    public final void onConnected(Bundle bundle) {
        bf.hV("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                zzey service = this.zzaqg.getService();
                this.zzaqg = null;
                this.zzapy.zzgf().zzc(new zzjc(this, service));
            } catch (DeadObjectException | IllegalStateException e) {
                this.zzaqg = null;
                this.zzaqf = false;
            }
        }
    }

    @Override // com.google.android.gms.common.internal.g
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        bf.hV("MeasurementServiceConnection.onConnectionFailed");
        zzfg zzfgVarZzjl = this.zzapy.zzacr.zzjl();
        if (zzfgVarZzjl != null) {
            zzfgVarZzjl.zzin().zzg("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzaqf = false;
            this.zzaqg = null;
        }
        this.zzapy.zzgf().zzc(new zzje(this));
    }

    @Override // com.google.android.gms.common.internal.f
    public final void onConnectionSuspended(int i) {
        bf.hV("MeasurementServiceConnection.onConnectionSuspended");
        this.zzapy.zzgg().zziq().log("Service connection suspended");
        this.zzapy.zzgf().zzc(new zzjd(this));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzey zzfaVar;
        bf.hV("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            try {
                if (iBinder == null) {
                    this.zzaqf = false;
                    this.zzapy.zzgg().zzil().log("Service connected with null binder");
                    return;
                }
                try {
                    String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                    if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                        if (iBinder == null) {
                            zzfaVar = null;
                        } else {
                            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                            zzfaVar = iInterfaceQueryLocalInterface instanceof zzey ? (zzey) iInterfaceQueryLocalInterface : new zzfa(iBinder);
                        }
                        try {
                            this.zzapy.zzgg().zzir().log("Bound to IMeasurementService interface");
                        } catch (RemoteException e) {
                            this.zzapy.zzgg().zzil().log("Service connect failed to get IMeasurementService");
                        }
                    } else {
                        this.zzapy.zzgg().zzil().zzg("Got binder with a wrong descriptor", interfaceDescriptor);
                        zzfaVar = null;
                    }
                } catch (RemoteException e2) {
                    zzfaVar = null;
                }
                if (zzfaVar == null) {
                    this.zzaqf = false;
                    try {
                        a.aaY().a(this.zzapy.getContext(), this.zzapy.zzapr);
                    } catch (IllegalArgumentException e3) {
                    }
                } else {
                    this.zzapy.zzgf().zzc(new zzja(this, zzfaVar));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        bf.hV("MeasurementServiceConnection.onServiceDisconnected");
        this.zzapy.zzgg().zziq().log("Service disconnected");
        this.zzapy.zzgf().zzc(new zzjb(this, componentName));
    }

    public final void zzc(Intent intent) {
        this.zzapy.zzab();
        Context context = this.zzapy.getContext();
        a aVarAaY = a.aaY();
        synchronized (this) {
            if (this.zzaqf) {
                this.zzapy.zzgg().zzir().log("Connection attempt already in progress");
                return;
            }
            this.zzapy.zzgg().zzir().log("Using local app measurement service");
            this.zzaqf = true;
            aVarAaY.a(context, intent, this.zzapy.zzapr, 129);
        }
    }

    public final void zzkp() {
        this.zzapy.zzab();
        Context context = this.zzapy.getContext();
        synchronized (this) {
            if (this.zzaqf) {
                this.zzapy.zzgg().zzir().log("Connection attempt already in progress");
                return;
            }
            if (this.zzaqg != null) {
                this.zzapy.zzgg().zzir().log("Already awaiting connection attempt");
                return;
            }
            this.zzaqg = new zzff(context, Looper.getMainLooper(), this, this);
            this.zzapy.zzgg().zzir().log("Connecting to remote service");
            this.zzaqf = true;
            this.zzaqg.checkAvailabilityAndConnect();
        }
    }
}
