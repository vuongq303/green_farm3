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
public final class zzme extends e<zzlm> {
    public zzme() {
        super("com.google.android.gms.ads.MobileAdsSettingManagerCreatorImpl");
    }

    @Override // com.google.android.gms.c.e
    protected final /* synthetic */ zzlm getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManagerCreator");
        return iInterfaceQueryLocalInterface instanceof zzlm ? (zzlm) iInterfaceQueryLocalInterface : new zzln(iBinder);
    }

    public final zzlj zzg(Context context) {
        zzlj zzllVar;
        try {
            IBinder iBinderZza = getRemoteCreatorInstance(context).zza(d.F(context), 12451000);
            if (iBinderZza == null) {
                zzllVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinderZza.queryLocalInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
                zzllVar = iInterfaceQueryLocalInterface instanceof zzlj ? (zzlj) iInterfaceQueryLocalInterface : new zzll(iBinderZza);
            }
            return zzllVar;
        } catch (RemoteException e) {
            zzane.zzc("Could not get remote MobileAdsSettingManager.", e);
            return null;
        } catch (f e2) {
            zzane.zzc("Could not get remote MobileAdsSettingManager.", e2);
            return null;
        }
    }
}
