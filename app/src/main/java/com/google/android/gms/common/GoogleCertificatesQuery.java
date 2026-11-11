package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.ao;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class GoogleCertificatesQuery extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GoogleCertificatesQuery> CREATOR = new j();
    private final String bIl;

    @Nullable
    private final i bIm;
    private final boolean bIn;

    GoogleCertificatesQuery(String str, @Nullable IBinder iBinder, boolean z) {
        this.bIl = str;
        this.bIm = g(iBinder);
        this.bIn = z;
    }

    GoogleCertificatesQuery(String str, @Nullable i iVar, boolean z) {
        this.bIl = str;
        this.bIm = iVar;
        this.bIn = z;
    }

    @Nullable
    private static i g(@Nullable IBinder iBinder) {
        s sVar;
        if (iBinder == null) {
            return null;
        }
        try {
            com.google.android.gms.c.a aVarYE = ao.k(iBinder).YE();
            byte[] bArr = aVarYE == null ? null : (byte[]) com.google.android.gms.c.d.b(aVarYE);
            if (bArr != null) {
                sVar = new s(bArr);
            } else {
                Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
                sVar = null;
            }
            return sVar;
        } catch (RemoteException e) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", e);
            return null;
        }
    }

    @Nullable
    public IBinder YG() {
        if (this.bIm != null) {
            return this.bIm.asBinder();
        }
        Log.w("GoogleCertificatesQuery", "certificate binder is null");
        return null;
    }

    public boolean YH() {
        return this.bIn;
    }

    public String getCallingPackage() {
        return this.bIl;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, getCallingPackage(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, YG(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, YH());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
