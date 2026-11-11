package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.c.d;
import com.google.android.gms.c.e;
import com.google.android.gms.c.f;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzrw extends e<zzqi> {
    public zzrw() {
        super("com.google.android.gms.ads.NativeAdViewHolderDelegateCreatorImpl");
    }

    @Override // com.google.android.gms.c.e
    protected final /* synthetic */ zzqi getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewHolderDelegateCreator");
        return iInterfaceQueryLocalInterface instanceof zzqi ? (zzqi) iInterfaceQueryLocalInterface : new zzqj(iBinder);
    }

    public final zzqf zzb(View view, HashMap<String, View> map, HashMap<String, View> map2) {
        zzqf zzqhVar;
        try {
            IBinder iBinderZza = getRemoteCreatorInstance(view.getContext()).zza(d.F(view), d.F(map), d.F(map2));
            if (iBinderZza == null) {
                zzqhVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinderZza.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewHolderDelegate");
                zzqhVar = iInterfaceQueryLocalInterface instanceof zzqf ? (zzqf) iInterfaceQueryLocalInterface : new zzqh(iBinderZza);
            }
            return zzqhVar;
        } catch (RemoteException | f e) {
            zzane.zzc("Could not create remote NativeAdViewHolderDelegate.", e);
            return null;
        }
    }
}
