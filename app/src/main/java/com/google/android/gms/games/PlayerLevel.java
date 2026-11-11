package com.google.android.gms.games;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class PlayerLevel extends zzd {
    public static final Parcelable.Creator<PlayerLevel> CREATOR = new u();
    private final int bSG;
    private final long bSH;
    private final long bSI;

    public PlayerLevel(int i, long j, long j2) {
        bf.a(j >= 0, "Min XP must be positive!");
        bf.a(j2 > j, "Max XP must be more than min XP!");
        this.bSG = i;
        this.bSH = j;
        this.bSI = j2;
    }

    public final int acE() {
        return this.bSG;
    }

    public final long acF() {
        return this.bSH;
    }

    public final long acG() {
        return this.bSI;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof PlayerLevel)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PlayerLevel playerLevel = (PlayerLevel) obj;
        return bd.b(Integer.valueOf(playerLevel.acE()), Integer.valueOf(acE())) && bd.b(Long.valueOf(playerLevel.acF()), Long.valueOf(acF())) && bd.b(Long.valueOf(playerLevel.acG()), Long.valueOf(acG()));
    }

    public final int hashCode() {
        return bd.hashCode(Integer.valueOf(this.bSG), Long.valueOf(this.bSH), Long.valueOf(this.bSI));
    }

    public final String toString() {
        return bd.C(this).c("LevelNumber", Integer.valueOf(acE())).c("MinXp", Long.valueOf(acF())).c("MaxXp", Long.valueOf(acG())).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, acE());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, acF());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, acG());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
