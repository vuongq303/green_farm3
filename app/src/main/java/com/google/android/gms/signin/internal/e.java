package com.google.android.gms.signin.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class e extends zzb implements d {
    public e() {
        super("com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    public static d s(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
        return iInterfaceQueryLocalInterface instanceof d ? (d) iInterfaceQueryLocalInterface : new f(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 3:
                a((ConnectionResult) zzc.zza(parcel, ConnectionResult.CREATOR), (AuthAccountResult) zzc.zza(parcel, AuthAccountResult.CREATOR));
                break;
            case 4:
                f((Status) zzc.zza(parcel, Status.CREATOR));
                break;
            case 5:
            default:
                return false;
            case 6:
                g((Status) zzc.zza(parcel, Status.CREATOR));
                break;
            case 7:
                a((Status) zzc.zza(parcel, Status.CREATOR), (GoogleSignInAccount) zzc.zza(parcel, GoogleSignInAccount.CREATOR));
                break;
            case 8:
                b((SignInResponse) zzc.zza(parcel, SignInResponse.CREATOR));
                break;
        }
        parcel2.writeNoException();
        return true;
    }
}
