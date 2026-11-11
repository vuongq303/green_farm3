package com.google.android.gms.common.api;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public interface n<T extends IInterface> extends c {
    void a(int i, T t);

    T createServiceInterface(IBinder iBinder);

    String getServiceDescriptor();

    String getStartServiceAction();
}
