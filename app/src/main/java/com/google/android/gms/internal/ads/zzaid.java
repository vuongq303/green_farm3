package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.facebook.R;
import com.google.android.gms.c.b;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzaid extends zzek implements zzaic {
    public zzaid() {
        super("com.google.android.gms.ads.internal.reward.mediation.client.IMediationRewardedVideoAdListener");
    }

    public static zzaic zzaa(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.reward.mediation.client.IMediationRewardedVideoAdListener");
        return iInterfaceQueryLocalInterface instanceof zzaic ? (zzaic) iInterfaceQueryLocalInterface : new zzaie(iBinder);
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzq(b.p(parcel.readStrongBinder()));
                break;
            case 2:
                zzc(b.p(parcel.readStrongBinder()), parcel.readInt());
                break;
            case 3:
                zzr(b.p(parcel.readStrongBinder()));
                break;
            case 4:
                zzs(b.p(parcel.readStrongBinder()));
                break;
            case 5:
                zzt(b.p(parcel.readStrongBinder()));
                break;
            case 6:
                zzu(b.p(parcel.readStrongBinder()));
                break;
            case 7:
                zza(b.p(parcel.readStrongBinder()), (zzaig) zzel.zza(parcel, zzaig.CREATOR));
                break;
            case 8:
                zzv(b.p(parcel.readStrongBinder()));
                break;
            case 9:
                zzd(b.p(parcel.readStrongBinder()), parcel.readInt());
                break;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                zzw(b.p(parcel.readStrongBinder()));
                break;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                zzx(b.p(parcel.readStrongBinder()));
                break;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                zzc((Bundle) zzel.zza(parcel, Bundle.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
