package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class ParticipantResult extends zzd {
    public static final Parcelable.Creator<ParticipantResult> CREATOR = new k();
    private final String bUB;
    private final int zzom;
    private final int zzon;

    public ParticipantResult(String str, int i, int i2) {
        boolean z;
        this.bUB = (String) bf.checkNotNull(str);
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        bf.bZ(z);
        this.zzom = i;
        this.zzon = i2;
    }

    public final int Qq() {
        return this.zzom;
    }

    public final int aeA() {
        return this.zzon;
    }

    public final String aex() {
        return this.bUB;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ParticipantResult)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ParticipantResult participantResult = (ParticipantResult) obj;
        return participantResult.aeA() == aeA() && participantResult.Qq() == Qq() && bd.b(participantResult.aex(), aex());
    }

    public final int hashCode() {
        return bd.hashCode(Integer.valueOf(aeA()), Integer.valueOf(Qq()), aex());
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, aex(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, Qq());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, aeA());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
