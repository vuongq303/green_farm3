package com.google.android.gms.internal.auth;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.a.f;
import com.google.android.gms.auth.a.g;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.common.internal.y;

/* JADX INFO: loaded from: classes.dex */
public final class zzu extends y<f> {
    public zzu(Context context, Looper looper, r rVar, com.google.android.gms.common.api.y yVar, z zVar) {
        super(context, looper, 120, rVar, yVar, zVar);
    }

    @Override // com.google.android.gms.common.internal.e
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        return g.e(iBinder);
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.auth.account.IWorkAccountService";
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getStartServiceAction() {
        return "com.google.android.gms.auth.account.workaccount.START";
    }
}
