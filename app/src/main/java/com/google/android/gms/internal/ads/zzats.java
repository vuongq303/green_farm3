package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.internal.e;
import com.google.android.gms.common.internal.f;
import com.google.android.gms.common.internal.g;

/* JADX INFO: loaded from: classes.dex */
public final class zzats extends e<zzatx> {
    public zzats(Context context, Looper looper, f fVar, g gVar) {
        super(context, looper, 116, fVar, gVar, null);
    }

    @Override // com.google.android.gms.common.internal.e
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.gass.internal.IGassService");
        return iInterfaceQueryLocalInterface instanceof zzatx ? (zzatx) iInterfaceQueryLocalInterface : new zzaty(iBinder);
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.gass.internal.IGassService";
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getStartServiceAction() {
        return "com.google.android.gms.gass.START";
    }

    public final zzatx zzwd() {
        return (zzatx) super.getService();
    }
}
