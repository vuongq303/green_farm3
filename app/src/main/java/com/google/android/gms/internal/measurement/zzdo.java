package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.common.internal.y;

/* JADX INFO: loaded from: classes.dex */
public final class zzdo extends y<zzds> {
    private final String zzaco;

    public zzdo(Context context, Looper looper, com.google.android.gms.common.api.y yVar, z zVar, r rVar) {
        super(context, looper, 77, rVar, yVar, zVar);
        this.zzaco = rVar.aai();
    }

    @Override // com.google.android.gms.common.internal.e
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appinvite.internal.IAppInviteService");
        return iInterfaceQueryLocalInterface instanceof zzds ? (zzds) iInterfaceQueryLocalInterface : new zzdt(iBinder);
    }

    @Override // com.google.android.gms.common.internal.e
    protected final Bundle getGetServiceRequestExtraArgs() {
        Bundle bundle = new Bundle();
        bundle.putString("authPackage", this.zzaco);
        return bundle;
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.appinvite.internal.IAppInviteService";
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getStartServiceAction() {
        return "com.google.android.gms.appinvite.service.START";
    }

    public final void zza(zzdq zzdqVar) {
        try {
            ((zzds) getService()).zza(zzdqVar);
        } catch (RemoteException e) {
        }
    }
}
