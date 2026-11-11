package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public class GetServiceRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GetServiceRequest> CREATOR = new x();
    private final int bNQ;
    private int bNR;
    private String bNS;
    private IBinder bNT;
    private Scope[] bNU;
    private Bundle bNV;
    private Account bNW;
    private Feature[] bNX;
    private Feature[] bNY;
    private boolean bNZ;
    private final int version;

    public GetServiceRequest(int i) {
        this.version = 4;
        this.bNR = com.google.android.gms.common.g.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.bNQ = i;
        this.bNZ = true;
    }

    GetServiceRequest(int i, int i2, int i3, String str, IBinder iBinder, Scope[] scopeArr, Bundle bundle, Account account, Feature[] featureArr, Feature[] featureArr2, boolean z) {
        this.version = i;
        this.bNQ = i2;
        this.bNR = i3;
        if ("com.google.android.gms".equals(str)) {
            this.bNS = "com.google.android.gms";
        } else {
            this.bNS = str;
        }
        if (i < 2) {
            this.bNW = h(iBinder);
        } else {
            this.bNT = iBinder;
            this.bNW = account;
        }
        this.bNU = scopeArr;
        this.bNV = bundle;
        this.bNX = featureArr;
        this.bNY = featureArr2;
        this.bNZ = z;
    }

    private static Account h(IBinder iBinder) {
        if (iBinder != null) {
            return a.a(ai.i(iBinder));
        }
        return null;
    }

    public GetServiceRequest a(Feature[] featureArr) {
        this.bNX = featureArr;
        return this;
    }

    public GetServiceRequest b(ah ahVar) {
        if (ahVar != null) {
            this.bNT = ahVar.asBinder();
        }
        return this;
    }

    public GetServiceRequest b(Feature[] featureArr) {
        this.bNY = featureArr;
        return this;
    }

    public GetServiceRequest c(Account account) {
        this.bNW = account;
        return this;
    }

    public GetServiceRequest d(Collection<Scope> collection) {
        this.bNU = (Scope[]) collection.toArray(new Scope[collection.size()]);
        return this;
    }

    public GetServiceRequest ia(String str) {
        this.bNS = str;
        return this;
    }

    public GetServiceRequest v(Bundle bundle) {
        this.bNV = bundle;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.version);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, this.bNQ);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, this.bNR);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bNS, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, this.bNT, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, (Parcelable[]) this.bNU, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, this.bNV, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, (Parcelable) this.bNW, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, (Parcelable[]) this.bNX, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 11, (Parcelable[]) this.bNY, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 12, this.bNZ);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
