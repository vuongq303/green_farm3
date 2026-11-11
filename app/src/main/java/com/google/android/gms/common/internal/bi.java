package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public class bi<T extends IInterface> extends y<T> {
    private final com.google.android.gms.common.api.n<T> bOz;

    public com.google.android.gms.common.api.n<T> aaF() {
        return this.bOz;
    }

    @Override // com.google.android.gms.common.internal.e
    protected T createServiceInterface(IBinder iBinder) {
        return (T) this.bOz.createServiceInterface(iBinder);
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public int getMinApkVersion() {
        return super.getMinApkVersion();
    }

    @Override // com.google.android.gms.common.internal.e
    protected String getServiceDescriptor() {
        return this.bOz.getServiceDescriptor();
    }

    @Override // com.google.android.gms.common.internal.e
    protected String getStartServiceAction() {
        return this.bOz.getStartServiceAction();
    }

    @Override // com.google.android.gms.common.internal.e
    protected void onSetConnectState(int i, T t) {
        this.bOz.a(i, t);
    }
}
