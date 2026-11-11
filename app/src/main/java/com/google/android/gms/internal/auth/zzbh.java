package com.google.android.gms.internal.auth;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.auth.api.f;
import com.google.android.gms.auth.api.h;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.common.internal.y;

/* JADX INFO: loaded from: classes.dex */
public final class zzbh extends y<zzbk> {
    private final Bundle zzcf;

    public zzbh(Context context, Looper looper, r rVar, h hVar, com.google.android.gms.common.api.y yVar, z zVar) {
        super(context, looper, 16, rVar, yVar, zVar);
        if (hVar != null) {
            throw new NoSuchMethodError();
        }
        this.zzcf = new Bundle();
    }

    @Override // com.google.android.gms.common.internal.e
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.internal.IAuthService");
        return iInterfaceQueryLocalInterface instanceof zzbk ? (zzbk) iInterfaceQueryLocalInterface : new zzbl(iBinder);
    }

    @Override // com.google.android.gms.common.internal.e
    protected final Bundle getGetServiceRequestExtraArgs() {
        return this.zzcf;
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.auth.api.internal.IAuthService";
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getStartServiceAction() {
        return "com.google.android.gms.auth.service.START";
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final boolean requiresSignIn() {
        r clientSettings = getClientSettings();
        return (TextUtils.isEmpty(clientSettings.aad()) || clientSettings.e(f.API).isEmpty()) ? false : true;
    }
}
