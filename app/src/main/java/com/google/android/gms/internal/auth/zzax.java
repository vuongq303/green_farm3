package com.google.android.gms.internal.auth;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.api.b;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.common.internal.y;

/* JADX INFO: loaded from: classes.dex */
public final class zzax extends y<zzbc> {
    private final b zzdv;

    public zzax(Context context, Looper looper, r rVar, b bVar, com.google.android.gms.common.api.y yVar, z zVar) {
        super(context, looper, 68, rVar, yVar, zVar);
        this.zzdv = bVar;
    }

    @Override // com.google.android.gms.common.internal.e
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
        return iInterfaceQueryLocalInterface instanceof zzbc ? (zzbc) iInterfaceQueryLocalInterface : new zzbd(iBinder);
    }

    @Override // com.google.android.gms.common.internal.e
    protected final Bundle getGetServiceRequestExtraArgs() {
        return this.zzdv == null ? new Bundle() : this.zzdv.toBundle();
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.auth.api.credentials.internal.ICredentialsService";
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getStartServiceAction() {
        return "com.google.android.gms.auth.api.credentials.service.START";
    }

    final b zzk() {
        return this.zzdv;
    }
}
