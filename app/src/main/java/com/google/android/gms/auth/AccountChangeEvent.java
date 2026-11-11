package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class AccountChangeEvent extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AccountChangeEvent> CREATOR = new e();
    private final int bEP;
    private final long bEQ;
    private final String bER;
    private final int bES;
    private final String bET;
    private final int zzl;

    AccountChangeEvent(int i, long j, String str, int i2, int i3, String str2) {
        this.bEP = i;
        this.bEQ = j;
        this.bER = (String) bf.checkNotNull(str);
        this.bES = i2;
        this.zzl = i3;
        this.bET = str2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AccountChangeEvent)) {
            return false;
        }
        AccountChangeEvent accountChangeEvent = (AccountChangeEvent) obj;
        return this.bEP == accountChangeEvent.bEP && this.bEQ == accountChangeEvent.bEQ && bd.b(this.bER, accountChangeEvent.bER) && this.bES == accountChangeEvent.bES && this.zzl == accountChangeEvent.zzl && bd.b(this.bET, accountChangeEvent.bET);
    }

    public int hashCode() {
        return bd.hashCode(Integer.valueOf(this.bEP), Long.valueOf(this.bEQ), this.bER, Integer.valueOf(this.bES), Integer.valueOf(this.zzl), this.bET);
    }

    public String toString() {
        String str = "UNKNOWN";
        switch (this.bES) {
            case 1:
                str = "ADDED";
                break;
            case 2:
                str = "REMOVED";
                break;
            case 3:
                str = "RENAMED_FROM";
                break;
            case 4:
                str = "RENAMED_TO";
                break;
        }
        String str2 = this.bER;
        String str3 = this.bET;
        return new StringBuilder(String.valueOf(str2).length() + 91 + String.valueOf(str).length() + String.valueOf(str3).length()).append("AccountChangeEvent {accountName = ").append(str2).append(", changeType = ").append(str).append(", changeData = ").append(str3).append(", eventIndex = ").append(this.zzl).append("}").toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.bEP);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bEQ);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.bER, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 4, this.bES);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 5, this.zzl);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, this.bET, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
