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
public final class zzahi extends e<zzahc> {
    public zzahi() {
        super("com.google.android.gms.ads.reward.RewardedVideoAdCreatorImpl");
    }

    @Override // com.google.android.gms.c.e
    protected final /* synthetic */ zzahc getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAdCreator");
        return iInterfaceQueryLocalInterface instanceof zzahc ? (zzahc) iInterfaceQueryLocalInterface : new zzahd(iBinder);
    }

    public final zzagz zza(Context context, zzxn zzxnVar) {
        zzagz zzahbVar;
        try {
            IBinder iBinderZza = getRemoteCreatorInstance(context).zza(d.F(context), zzxnVar, 12451000);
            if (iBinderZza == null) {
                zzahbVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinderZza.queryLocalInterface("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAd");
                zzahbVar = iInterfaceQueryLocalInterface instanceof zzagz ? (zzagz) iInterfaceQueryLocalInterface : new zzahb(iBinderZza);
            }
            return zzahbVar;
        } catch (RemoteException | f e) {
            zzane.zzc("Could not get remote RewardedVideoAd.", e);
            return null;
        }
    }
}
