package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AccountChangeEventsResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AccountChangeEventsResponse> CREATOR = new g();
    private final int bEP;
    private final List<AccountChangeEvent> bEU;

    AccountChangeEventsResponse(int i, List<AccountChangeEvent> list) {
        this.bEP = i;
        this.bEU = (List) bf.checkNotNull(list);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.bEP);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, this.bEU, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
