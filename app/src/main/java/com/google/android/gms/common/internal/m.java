package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
public final class m extends p {
    public final IBinder bNB;
    private final /* synthetic */ e bNy;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m(e eVar, int i, IBinder iBinder, Bundle bundle) {
        super(eVar, i, bundle);
        this.bNy = eVar;
        this.bNB = iBinder;
    }

    @Override // com.google.android.gms.common.internal.p
    protected final boolean aac() {
        try {
            String interfaceDescriptor = this.bNB.getInterfaceDescriptor();
            if (!this.bNy.getServiceDescriptor().equals(interfaceDescriptor)) {
                String serviceDescriptor = this.bNy.getServiceDescriptor();
                Log.e("GmsClient", new StringBuilder(String.valueOf(serviceDescriptor).length() + 34 + String.valueOf(interfaceDescriptor).length()).append("service descriptor mismatch: ").append(serviceDescriptor).append(" vs. ").append(interfaceDescriptor).toString());
                return false;
            }
            IInterface iInterfaceCreateServiceInterface = this.bNy.createServiceInterface(this.bNB);
            if (iInterfaceCreateServiceInterface == null) {
                return false;
            }
            if (!this.bNy.zza(2, 4, iInterfaceCreateServiceInterface) && !this.bNy.zza(3, 4, iInterfaceCreateServiceInterface)) {
                return false;
            }
            this.bNy.zzrr = null;
            Bundle connectionHint = this.bNy.getConnectionHint();
            if (this.bNy.zzrn != null) {
                this.bNy.zzrn.onConnected(connectionHint);
            }
            return true;
        } catch (RemoteException e) {
            Log.w("GmsClient", "service probably died");
            return false;
        }
    }

    @Override // com.google.android.gms.common.internal.p
    protected final void k(ConnectionResult connectionResult) {
        if (this.bNy.zzro != null) {
            this.bNy.zzro.onConnectionFailed(connectionResult);
        }
        this.bNy.onConnectionFailed(connectionResult);
    }
}
