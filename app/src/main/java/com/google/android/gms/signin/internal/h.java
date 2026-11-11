package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.facebook.R;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.ai;
import com.google.android.gms.common.internal.ba;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class h extends zzb implements g {
    public static g t(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        return iInterfaceQueryLocalInterface instanceof g ? (g) iInterfaceQueryLocalInterface : new i(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 2:
                a((AuthAccountRequest) zzc.zza(parcel, AuthAccountRequest.CREATOR), e.s(parcel.readStrongBinder()));
                break;
            case 3:
                a((CheckServerAuthResult) zzc.zza(parcel, CheckServerAuthResult.CREATOR));
                break;
            case 4:
                cd(zzc.zza(parcel));
                break;
            case 5:
                a((ResolveAccountRequest) zzc.zza(parcel, ResolveAccountRequest.CREATOR), ba.o(parcel.readStrongBinder()));
                break;
            case 6:
            default:
                return false;
            case 7:
                ot(parcel.readInt());
                break;
            case 8:
                a(parcel.readInt(), (Account) zzc.zza(parcel, Account.CREATOR), e.s(parcel.readStrongBinder()));
                break;
            case 9:
                a(ai.i(parcel.readStrongBinder()), parcel.readInt(), zzc.zza(parcel));
                break;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                a((RecordConsentRequest) zzc.zza(parcel, RecordConsentRequest.CREATOR), e.s(parcel.readStrongBinder()));
                break;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                b(e.s(parcel.readStrongBinder()));
                break;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                a((SignInRequest) zzc.zza(parcel, SignInRequest.CREATOR), e.s(parcel.readStrongBinder()));
                break;
            case 13:
                ce(zzc.zza(parcel));
                break;
        }
        parcel2.writeNoException();
        return true;
    }
}
