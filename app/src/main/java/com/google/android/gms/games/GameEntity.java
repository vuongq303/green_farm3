package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;

/* JADX INFO: loaded from: classes.dex */
public final class GameEntity extends GamesDowngradeableSafeParcel implements Game {
    public static final Parcelable.Creator<GameEntity> CREATOR = new b();
    private final String bET;
    private final int bEV;
    private final boolean bEX;
    private final String bGD;
    private final String bGE;
    private final boolean bGP;
    private final boolean bGQ;
    private final boolean bHV;
    private final String bRE;
    private final Uri bRF;
    private final Uri bRG;
    private final Uri bRH;
    private final int bRI;
    private final boolean bRJ;
    private final String bRK;
    private final String bRL;
    private final String bRM;
    private final boolean bRN;
    private final boolean bRO;
    private final String bRP;
    private final String description;
    private final boolean zzag;
    private final String zzq;
    private final String zzw;
    private final int zzy;

    public GameEntity(Game game) {
        this.bET = game.getApplicationId();
        this.bGE = game.abS();
        this.bRE = game.abT();
        this.description = game.getDescription();
        this.zzq = game.abU();
        this.bGD = game.getDisplayName();
        this.bRF = game.abV();
        this.bRK = game.abW();
        this.bRG = game.abX();
        this.bRL = game.abY();
        this.bRH = game.abZ();
        this.bRM = game.aca();
        this.bGP = game.acb();
        this.bGQ = game.zzc();
        this.zzw = game.acc();
        this.bEV = 1;
        this.zzy = game.acd();
        this.bRI = game.ace();
        this.bRJ = game.acf();
        this.bEX = game.acg();
        this.bRN = game.isMuted();
        this.zzag = game.zzb();
        this.bRO = game.ach();
        this.bRP = game.aci();
        this.bHV = game.acj();
    }

    GameEntity(String str, String str2, String str3, String str4, String str5, String str6, Uri uri, Uri uri2, Uri uri3, boolean z, boolean z2, String str7, int i, int i2, int i3, boolean z3, boolean z4, String str8, String str9, String str10, boolean z5, boolean z6, boolean z7, String str11, boolean z8) {
        this.bET = str;
        this.bGD = str2;
        this.bGE = str3;
        this.bRE = str4;
        this.description = str5;
        this.zzq = str6;
        this.bRF = uri;
        this.bRK = str8;
        this.bRG = uri2;
        this.bRL = str9;
        this.bRH = uri3;
        this.bRM = str10;
        this.bGP = z;
        this.bGQ = z2;
        this.zzw = str7;
        this.bEV = i;
        this.zzy = i2;
        this.bRI = i3;
        this.bRJ = z3;
        this.bEX = z4;
        this.bRN = z5;
        this.zzag = z6;
        this.bRO = z7;
        this.bRP = str11;
        this.bHV = z8;
    }

    static int a(Game game) {
        return bd.hashCode(game.getApplicationId(), game.getDisplayName(), game.abS(), game.abT(), game.getDescription(), game.abU(), game.abV(), game.abX(), game.abZ(), Boolean.valueOf(game.acb()), Boolean.valueOf(game.zzc()), game.acc(), Integer.valueOf(game.acd()), Integer.valueOf(game.ace()), Boolean.valueOf(game.acf()), Boolean.valueOf(game.acg()), Boolean.valueOf(game.isMuted()), Boolean.valueOf(game.zzb()), Boolean.valueOf(game.ach()), game.aci(), Boolean.valueOf(game.acj()));
    }

    static boolean a(Game game, Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        if (game == obj) {
            return true;
        }
        Game game2 = (Game) obj;
        return bd.b(game2.getApplicationId(), game.getApplicationId()) && bd.b(game2.getDisplayName(), game.getDisplayName()) && bd.b(game2.abS(), game.abS()) && bd.b(game2.abT(), game.abT()) && bd.b(game2.getDescription(), game.getDescription()) && bd.b(game2.abU(), game.abU()) && bd.b(game2.abV(), game.abV()) && bd.b(game2.abX(), game.abX()) && bd.b(game2.abZ(), game.abZ()) && bd.b(Boolean.valueOf(game2.acb()), Boolean.valueOf(game.acb())) && bd.b(Boolean.valueOf(game2.zzc()), Boolean.valueOf(game.zzc())) && bd.b(game2.acc(), game.acc()) && bd.b(Integer.valueOf(game2.acd()), Integer.valueOf(game.acd())) && bd.b(Integer.valueOf(game2.ace()), Integer.valueOf(game.ace())) && bd.b(Boolean.valueOf(game2.acf()), Boolean.valueOf(game.acf())) && bd.b(Boolean.valueOf(game2.acg()), Boolean.valueOf(game.acg())) && bd.b(Boolean.valueOf(game2.isMuted()), Boolean.valueOf(game.isMuted())) && bd.b(Boolean.valueOf(game2.zzb()), Boolean.valueOf(game.zzb())) && bd.b(Boolean.valueOf(game2.ach()), Boolean.valueOf(game.ach())) && bd.b(game2.aci(), game.aci()) && bd.b(Boolean.valueOf(game2.acj()), Boolean.valueOf(game.acj()));
    }

    static String b(Game game) {
        return bd.C(game).c("ApplicationId", game.getApplicationId()).c("DisplayName", game.getDisplayName()).c("PrimaryCategory", game.abS()).c("SecondaryCategory", game.abT()).c("Description", game.getDescription()).c("DeveloperName", game.abU()).c("IconImageUri", game.abV()).c("IconImageUrl", game.abW()).c("HiResImageUri", game.abX()).c("HiResImageUrl", game.abY()).c("FeaturedImageUri", game.abZ()).c("FeaturedImageUrl", game.aca()).c("PlayEnabledGame", Boolean.valueOf(game.acb())).c("InstanceInstalled", Boolean.valueOf(game.zzc())).c("InstancePackageName", game.acc()).c("AchievementTotalCount", Integer.valueOf(game.acd())).c("LeaderboardCount", Integer.valueOf(game.ace())).c("RealTimeMultiplayerEnabled", Boolean.valueOf(game.acf())).c("TurnBasedMultiplayerEnabled", Boolean.valueOf(game.acg())).c("AreSnapshotsEnabled", Boolean.valueOf(game.ach())).c("ThemeColor", game.aci()).c("HasGamepadSupport", Boolean.valueOf(game.acj())).toString();
    }

    @Override // com.google.android.gms.games.Game
    public final String abS() {
        return this.bGE;
    }

    @Override // com.google.android.gms.games.Game
    public final String abT() {
        return this.bRE;
    }

    @Override // com.google.android.gms.games.Game
    public final String abU() {
        return this.zzq;
    }

    @Override // com.google.android.gms.games.Game
    public final Uri abV() {
        return this.bRF;
    }

    @Override // com.google.android.gms.games.Game
    public final String abW() {
        return this.bRK;
    }

    @Override // com.google.android.gms.games.Game
    public final Uri abX() {
        return this.bRG;
    }

    @Override // com.google.android.gms.games.Game
    public final String abY() {
        return this.bRL;
    }

    @Override // com.google.android.gms.games.Game
    public final Uri abZ() {
        return this.bRH;
    }

    @Override // com.google.android.gms.games.Game
    public final String aca() {
        return this.bRM;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean acb() {
        return this.bGP;
    }

    @Override // com.google.android.gms.games.Game
    public final String acc() {
        return this.zzw;
    }

    @Override // com.google.android.gms.games.Game
    public final int acd() {
        return this.zzy;
    }

    @Override // com.google.android.gms.games.Game
    public final int ace() {
        return this.bRI;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean acf() {
        return this.bRJ;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean acg() {
        return this.bEX;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean ach() {
        return this.bRO;
    }

    @Override // com.google.android.gms.games.Game
    public final String aci() {
        return this.bRP;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean acj() {
        return this.bHV;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: ack, reason: merged with bridge method [inline-methods] */
    public final Game freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.Game
    public final String getApplicationId() {
        return this.bET;
    }

    @Override // com.google.android.gms.games.Game
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.Game
    public final String getDisplayName() {
        return this.bGD;
    }

    public final int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.games.Game
    public final boolean isMuted() {
        return this.bRN;
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (aas()) {
            parcel.writeString(this.bET);
            parcel.writeString(this.bGD);
            parcel.writeString(this.bGE);
            parcel.writeString(this.bRE);
            parcel.writeString(this.description);
            parcel.writeString(this.zzq);
            parcel.writeString(this.bRF == null ? null : this.bRF.toString());
            parcel.writeString(this.bRG == null ? null : this.bRG.toString());
            parcel.writeString(this.bRH != null ? this.bRH.toString() : null);
            parcel.writeInt(this.bGP ? 1 : 0);
            parcel.writeInt(this.bGQ ? 1 : 0);
            parcel.writeString(this.zzw);
            parcel.writeInt(this.bEV);
            parcel.writeInt(this.zzy);
            parcel.writeInt(this.bRI);
            return;
        }
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, getApplicationId(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getDisplayName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, abS(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, abT(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, abU(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, (Parcelable) abV(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, (Parcelable) abX(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, (Parcelable) abZ(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, this.bGP);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 11, this.bGQ);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 12, this.zzw, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 13, this.bEV);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 14, acd());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 15, ace());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 16, acf());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 17, acg());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 18, abW(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 19, abY(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 20, aca(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 21, this.bRN);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 22, this.zzag);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 23, ach());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 24, aci(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 25, acj());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }

    @Override // com.google.android.gms.games.Game
    public final boolean zzb() {
        return this.zzag;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean zzc() {
        return this.bGQ;
    }
}
