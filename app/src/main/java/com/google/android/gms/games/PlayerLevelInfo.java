package com.google.android.gms.games;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class PlayerLevelInfo extends zzd {
    public static final Parcelable.Creator<PlayerLevelInfo> CREATOR = new v();
    private final long bSJ;
    private final long bSK;
    private final PlayerLevel bSL;
    private final PlayerLevel bSM;

    public PlayerLevelInfo(long j, long j2, PlayerLevel playerLevel, PlayerLevel playerLevel2) {
        bf.bZ(j != -1);
        bf.checkNotNull(playerLevel);
        bf.checkNotNull(playerLevel2);
        this.bSJ = j;
        this.bSK = j2;
        this.bSL = playerLevel;
        this.bSM = playerLevel2;
    }

    public final long acH() {
        return this.bSJ;
    }

    public final long acI() {
        return this.bSK;
    }

    public final PlayerLevel acJ() {
        return this.bSL;
    }

    public final PlayerLevel acK() {
        return this.bSM;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof PlayerLevelInfo)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        PlayerLevelInfo playerLevelInfo = (PlayerLevelInfo) obj;
        return bd.b(Long.valueOf(this.bSJ), Long.valueOf(playerLevelInfo.bSJ)) && bd.b(Long.valueOf(this.bSK), Long.valueOf(playerLevelInfo.bSK)) && bd.b(this.bSL, playerLevelInfo.bSL) && bd.b(this.bSM, playerLevelInfo.bSM);
    }

    public final int hashCode() {
        return bd.hashCode(Long.valueOf(this.bSJ), Long.valueOf(this.bSK), this.bSL, this.bSM);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, acH());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, acI());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) acJ(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) acK(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
