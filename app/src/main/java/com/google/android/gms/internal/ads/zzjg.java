package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.c.d;
import com.google.android.gms.c.e;
import com.google.android.gms.c.f;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzjg extends e<zzkq> {
    public zzjg() {
        super("com.google.android.gms.ads.AdLoaderBuilderCreatorImpl");
    }

    @Override // com.google.android.gms.c.e
    protected final /* synthetic */ zzkq getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdLoaderBuilderCreator");
        return iInterfaceQueryLocalInterface instanceof zzkq ? (zzkq) iInterfaceQueryLocalInterface : new zzkr(iBinder);
    }

    public final zzkn zza(Context context, String str, zzxn zzxnVar) {
        zzkn zzkpVar;
        try {
            IBinder iBinderZza = getRemoteCreatorInstance(context).zza(d.F(context), str, zzxnVar, 12451000);
            if (iBinderZza == null) {
                zzkpVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinderZza.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdLoaderBuilder");
                zzkpVar = iInterfaceQueryLocalInterface instanceof zzkn ? (zzkn) iInterfaceQueryLocalInterface : new zzkp(iBinderZza);
            }
            return zzkpVar;
        } catch (RemoteException | f e) {
            zzane.zzc("Could not create remote builder for AdLoader.", e);
            return null;
        }
    }
}
