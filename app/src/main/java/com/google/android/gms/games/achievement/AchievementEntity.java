package com.google.android.gms.games.achievement;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.be;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class AchievementEntity extends zzd implements Achievement {
    public static final Parcelable.Creator<AchievementEntity> CREATOR = new e();
    private final String bHa;
    private final String bSP;
    private final Uri bSQ;
    private final Uri bSR;
    private final int bSS;
    private final String bST;
    private final PlayerEntity bSU;
    private final int bSV;
    private final String bSW;
    private final long bSX;
    private final long bSY;
    private final String description;
    private final String name;
    private final int state;
    private final int type;
    private final String zzfc;

    public AchievementEntity(Achievement achievement) {
        this.bSP = achievement.getAchievementId();
        this.type = achievement.getType();
        this.name = achievement.getName();
        this.description = achievement.getDescription();
        this.bSQ = achievement.acL();
        this.zzfc = achievement.acM();
        this.bSR = achievement.acN();
        this.bHa = achievement.acO();
        this.bSU = (PlayerEntity) achievement.acR().freeze();
        this.state = achievement.getState();
        this.bSX = achievement.acU();
        this.bSY = achievement.acV();
        if (achievement.getType() == 1) {
            this.bSS = achievement.acP();
            this.bST = achievement.acQ();
            this.bSV = achievement.acS();
            this.bSW = achievement.acT();
        } else {
            this.bSS = 0;
            this.bST = null;
            this.bSV = 0;
            this.bSW = null;
        }
        com.google.android.gms.common.internal.c.A(this.bSP);
        com.google.android.gms.common.internal.c.A(this.description);
    }

    AchievementEntity(String str, int i, String str2, String str3, Uri uri, String str4, Uri uri2, String str5, int i2, String str6, PlayerEntity playerEntity, int i3, int i4, String str7, long j, long j2) {
        this.bSP = str;
        this.type = i;
        this.name = str2;
        this.description = str3;
        this.bSQ = uri;
        this.zzfc = str4;
        this.bSR = uri2;
        this.bHa = str5;
        this.bSS = i2;
        this.bST = str6;
        this.bSU = playerEntity;
        this.state = i3;
        this.bSV = i4;
        this.bSW = str7;
        this.bSX = j;
        this.bSY = j2;
    }

    static String a(Achievement achievement) {
        be beVarC = bd.C(achievement).c("Id", achievement.getAchievementId()).c("Type", Integer.valueOf(achievement.getType())).c("Name", achievement.getName()).c("Description", achievement.getDescription()).c("Player", achievement.acR()).c("State", Integer.valueOf(achievement.getState()));
        if (achievement.getType() == 1) {
            beVarC.c("CurrentSteps", Integer.valueOf(achievement.acS()));
            beVarC.c("TotalSteps", Integer.valueOf(achievement.acP()));
        }
        return beVarC.toString();
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final Uri acL() {
        return this.bSQ;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final String acM() {
        return this.zzfc;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final Uri acN() {
        return this.bSR;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final String acO() {
        return this.bHa;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final int acP() {
        com.google.android.gms.common.internal.c.bZ(getType() == 1);
        return this.bSS;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final String acQ() {
        com.google.android.gms.common.internal.c.bZ(getType() == 1);
        return this.bST;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final Player acR() {
        return this.bSU;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final int acS() {
        com.google.android.gms.common.internal.c.bZ(getType() == 1);
        return this.bSV;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final String acT() {
        com.google.android.gms.common.internal.c.bZ(getType() == 1);
        return this.bSW;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final long acU() {
        return this.bSX;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final long acV() {
        return this.bSY;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: acW, reason: merged with bridge method [inline-methods] */
    public final Achievement freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Achievement) {
            if (this == obj) {
                return true;
            }
            Achievement achievement = (Achievement) obj;
            if (achievement.getType() == getType() && ((getType() != 1 || (achievement.acS() == acS() && achievement.acP() == acP())) && achievement.acV() == acV() && achievement.getState() == getState() && achievement.acU() == acU() && bd.b(achievement.getAchievementId(), getAchievementId()) && bd.b(achievement.getName(), getName()) && bd.b(achievement.getDescription(), getDescription()) && bd.b(achievement.acR(), acR()))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final String getAchievementId() {
        return this.bSP;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final String getName() {
        return this.name;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final int getState() {
        return this.state;
    }

    @Override // com.google.android.gms.games.achievement.Achievement
    public final int getType() {
        return this.type;
    }

    public final int hashCode() {
        int iAcP;
        int iAcS;
        if (getType() == 1) {
            iAcS = acS();
            iAcP = acP();
        } else {
            iAcP = 0;
            iAcS = 0;
        }
        return bd.hashCode(getAchievementId(), getName(), Integer.valueOf(getType()), getDescription(), Long.valueOf(acV()), Integer.valueOf(getState()), Long.valueOf(acU()), acR(), Integer.valueOf(iAcS), Integer.valueOf(iAcP));
    }

    public final String toString() {
        return a(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, getAchievementId(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, getType());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, getName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, (Parcelable) acL(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, acM(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, (Parcelable) acN(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, acO(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 9, this.bSS);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, this.bST, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 11, (Parcelable) acR(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 12, getState());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 13, this.bSV);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 14, this.bSW, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 15, acU());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 16, acV());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
