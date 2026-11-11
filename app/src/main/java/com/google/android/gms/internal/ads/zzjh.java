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
public final class zzjh extends e<zzkv> {
    public zzjh() {
        super("com.google.android.gms.ads.AdManagerCreatorImpl");
    }

    @Override // com.google.android.gms.c.e
    protected final /* synthetic */ zzkv getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
        return iInterfaceQueryLocalInterface instanceof zzkv ? (zzkv) iInterfaceQueryLocalInterface : new zzkw(iBinder);
    }

    public final zzks zza(Context context, zzjn zzjnVar, String str, zzxn zzxnVar, int i) {
        zzks zzkuVar;
        try {
            IBinder iBinderZza = getRemoteCreatorInstance(context).zza(d.F(context), zzjnVar, str, zzxnVar, 12451000, i);
            if (iBinderZza == null) {
                zzkuVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinderZza.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManager");
                zzkuVar = iInterfaceQueryLocalInterface instanceof zzks ? (zzks) iInterfaceQueryLocalInterface : new zzku(iBinderZza);
            }
            return zzkuVar;
        } catch (RemoteException | f e) {
            zzane.zza("Could not create remote AdManager.", e);
            return null;
        }
    }
}
