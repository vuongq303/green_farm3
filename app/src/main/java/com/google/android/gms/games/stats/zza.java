package com.google.android.gms.games.stats;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class zza extends zzd implements PlayerStats {
    public static final Parcelable.Creator<zza> CREATOR = new d();
    private final float bWW;
    private final float bWX;
    private final int bWY;
    private final int bWZ;
    private final int bXa;
    private final float bXb;
    private final float bXc;
    private final Bundle bXd;
    private final float bXe;
    private final float bXf;
    private final float bXg;

    zza(float f, float f2, int i, int i2, int i3, float f3, float f4, Bundle bundle, float f5, float f6, float f7) {
        this.bWW = f;
        this.bWX = f2;
        this.bWY = i;
        this.bWZ = i2;
        this.bXa = i3;
        this.bXb = f3;
        this.bXc = f4;
        this.bXd = bundle;
        this.bXe = f5;
        this.bXf = f6;
        this.bXg = f7;
    }

    public zza(PlayerStats playerStats) {
        this.bWW = playerStats.afF();
        this.bWX = playerStats.afG();
        this.bWY = playerStats.afH();
        this.bWZ = playerStats.afI();
        this.bXa = playerStats.afJ();
        this.bXb = playerStats.afK();
        this.bXc = playerStats.afL();
        this.bXe = playerStats.afM();
        this.bXf = playerStats.afN();
        this.bXg = playerStats.afO();
        this.bXd = playerStats.afP();
    }

    static int a(PlayerStats playerStats) {
        return bd.hashCode(Float.valueOf(playerStats.afF()), Float.valueOf(playerStats.afG()), Integer.valueOf(playerStats.afH()), Integer.valueOf(playerStats.afI()), Integer.valueOf(playerStats.afJ()), Float.valueOf(playerStats.afK()), Float.valueOf(playerStats.afL()), Float.valueOf(playerStats.afM()), Float.valueOf(playerStats.afN()), Float.valueOf(playerStats.afO()));
    }

    static boolean a(PlayerStats playerStats, Object obj) {
        if (!(obj instanceof PlayerStats)) {
            return false;
        }
        if (playerStats == obj) {
            return true;
        }
        PlayerStats playerStats2 = (PlayerStats) obj;
        return bd.b(Float.valueOf(playerStats2.afF()), Float.valueOf(playerStats.afF())) && bd.b(Float.valueOf(playerStats2.afG()), Float.valueOf(playerStats.afG())) && bd.b(Integer.valueOf(playerStats2.afH()), Integer.valueOf(playerStats.afH())) && bd.b(Integer.valueOf(playerStats2.afI()), Integer.valueOf(playerStats.afI())) && bd.b(Integer.valueOf(playerStats2.afJ()), Integer.valueOf(playerStats.afJ())) && bd.b(Float.valueOf(playerStats2.afK()), Float.valueOf(playerStats.afK())) && bd.b(Float.valueOf(playerStats2.afL()), Float.valueOf(playerStats.afL())) && bd.b(Float.valueOf(playerStats2.afM()), Float.valueOf(playerStats.afM())) && bd.b(Float.valueOf(playerStats2.afN()), Float.valueOf(playerStats.afN())) && bd.b(Float.valueOf(playerStats2.afO()), Float.valueOf(playerStats.afO()));
    }

    static String b(PlayerStats playerStats) {
        return bd.C(playerStats).c("AverageSessionLength", Float.valueOf(playerStats.afF())).c("ChurnProbability", Float.valueOf(playerStats.afG())).c("DaysSinceLastPlayed", Integer.valueOf(playerStats.afH())).c("NumberOfPurchases", Integer.valueOf(playerStats.afI())).c("NumberOfSessions", Integer.valueOf(playerStats.afJ())).c("SessionPercentile", Float.valueOf(playerStats.afK())).c("SpendPercentile", Float.valueOf(playerStats.afL())).c("SpendProbability", Float.valueOf(playerStats.afM())).c("HighSpenderProbability", Float.valueOf(playerStats.afN())).c("TotalSpendNext28Days", Float.valueOf(playerStats.afO())).toString();
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float afF() {
        return this.bWW;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float afG() {
        return this.bWX;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final int afH() {
        return this.bWY;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final int afI() {
        return this.bWZ;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final int afJ() {
        return this.bXa;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float afK() {
        return this.bXb;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float afL() {
        return this.bXc;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float afM() {
        return this.bXe;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float afN() {
        return this.bXf;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float afO() {
        return this.bXg;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final Bundle afP() {
        return this.bXd;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* bridge */ /* synthetic */ PlayerStats freeze() {
        if (this == null) {
            throw null;
        }
        return this;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, afF());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, afG());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, afH());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 4, afI());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 5, afJ());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, afK());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, afL());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, this.bXd, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, afM());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, afN());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 11, afO());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
