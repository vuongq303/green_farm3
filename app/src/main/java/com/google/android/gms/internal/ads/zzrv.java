package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.widget.FrameLayout;
import com.google.android.gms.c.d;
import com.google.android.gms.c.e;
import com.google.android.gms.c.f;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzrv extends e<zzqd> {
    public zzrv() {
        super("com.google.android.gms.ads.NativeAdViewDelegateCreatorImpl");
    }

    @Override // com.google.android.gms.c.e
    protected final /* synthetic */ zzqd getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
        return iInterfaceQueryLocalInterface instanceof zzqd ? (zzqd) iInterfaceQueryLocalInterface : new zzqe(iBinder);
    }

    public final zzqa zzb(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        zzqa zzqcVar;
        try {
            IBinder iBinderZza = getRemoteCreatorInstance(context).zza(d.F(context), d.F(frameLayout), d.F(frameLayout2), 12451000);
            if (iBinderZza == null) {
                zzqcVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinderZza.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegate");
                zzqcVar = iInterfaceQueryLocalInterface instanceof zzqa ? (zzqa) iInterfaceQueryLocalInterface : new zzqc(iBinderZza);
            }
            return zzqcVar;
        } catch (RemoteException | f e) {
            zzane.zzc("Could not create remote NativeAdViewDelegate.", e);
            return null;
        }
    }
}
