package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.internal.player.zza;
import com.google.android.gms.games.internal.player.zzb;

/* JADX INFO: loaded from: classes.dex */
public final class PlayerEntity extends GamesDowngradeableSafeParcel implements Player {
    public static final Parcelable.Creator<PlayerEntity> CREATOR = new q();
    private final String bFF;
    private String bGD;
    private final Uri bRF;
    private final Uri bRG;
    private final String bRK;
    private final String bRL;
    private final Uri bSA;
    private final String bSB;
    private final Uri bSC;
    private final int bSD;
    private final long bSE;
    private final boolean bSF;
    private final long bSu;
    private final int bSv;
    private final long bSw;
    private final zzb bSx;
    private final PlayerLevelInfo bSy;
    private final boolean bSz;
    private final String name;
    private String zzby;
    private final String zzcc;
    private final boolean zzcg;
    private final String zzch;

    public PlayerEntity(Player player) {
        this(player, true);
    }

    private PlayerEntity(Player player, boolean z) {
        this.zzby = player.acq();
        this.bGD = player.getDisplayName();
        this.bRF = player.abV();
        this.bRK = player.abW();
        this.bRG = player.abX();
        this.bRL = player.abY();
        this.bSu = player.acs();
        this.bSv = player.zzi();
        this.bSw = player.act();
        this.zzcc = player.getTitle();
        this.bSz = player.acu();
        zza zzaVarAcw = player.acw();
        this.bSx = zzaVarAcw == null ? null : new zzb(zzaVarAcw);
        this.bSy = player.acv();
        this.zzcg = player.zzh();
        this.zzch = player.acr();
        this.name = player.getName();
        this.bSA = player.acx();
        this.bSB = player.acy();
        this.bSC = player.acz();
        this.bFF = player.acA();
        this.bSD = player.acB();
        this.bSE = player.acC();
        this.bSF = player.isMuted();
        com.google.android.gms.common.internal.c.A(this.zzby);
        com.google.android.gms.common.internal.c.A(this.bGD);
        com.google.android.gms.common.internal.c.bZ(this.bSu > 0);
    }

    PlayerEntity(String str, String str2, Uri uri, Uri uri2, long j, int i, long j2, String str3, String str4, String str5, zzb zzbVar, PlayerLevelInfo playerLevelInfo, boolean z, boolean z2, String str6, String str7, Uri uri3, String str8, Uri uri4, String str9, int i2, long j3, boolean z3) {
        this.zzby = str;
        this.bGD = str2;
        this.bRF = uri;
        this.bRK = str3;
        this.bRG = uri2;
        this.bRL = str4;
        this.bSu = j;
        this.bSv = i;
        this.bSw = j2;
        this.zzcc = str5;
        this.bSz = z;
        this.bSx = zzbVar;
        this.bSy = playerLevelInfo;
        this.zzcg = z2;
        this.zzch = str6;
        this.name = str7;
        this.bSA = uri3;
        this.bSB = str8;
        this.bSC = uri4;
        this.bFF = str9;
        this.bSD = i2;
        this.bSE = j3;
        this.bSF = z3;
    }

    static int a(Player player) {
        return bd.hashCode(player.acq(), player.getDisplayName(), Boolean.valueOf(player.zzh()), player.abV(), player.abX(), Long.valueOf(player.acs()), player.getTitle(), player.acv(), player.acr(), player.getName(), player.acx(), player.acz(), Integer.valueOf(player.acB()), Long.valueOf(player.acC()), Boolean.valueOf(player.isMuted()));
    }

    static boolean a(Player player, Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        if (player == obj) {
            return true;
        }
        Player player2 = (Player) obj;
        return bd.b(player2.acq(), player.acq()) && bd.b(player2.getDisplayName(), player.getDisplayName()) && bd.b(Boolean.valueOf(player2.zzh()), Boolean.valueOf(player.zzh())) && bd.b(player2.abV(), player.abV()) && bd.b(player2.abX(), player.abX()) && bd.b(Long.valueOf(player2.acs()), Long.valueOf(player.acs())) && bd.b(player2.getTitle(), player.getTitle()) && bd.b(player2.acv(), player.acv()) && bd.b(player2.acr(), player.acr()) && bd.b(player2.getName(), player.getName()) && bd.b(player2.acx(), player.acx()) && bd.b(player2.acz(), player.acz()) && bd.b(Integer.valueOf(player2.acB()), Integer.valueOf(player.acB())) && bd.b(Long.valueOf(player2.acC()), Long.valueOf(player.acC())) && bd.b(Boolean.valueOf(player2.isMuted()), Boolean.valueOf(player.isMuted()));
    }

    static String b(Player player) {
        return bd.C(player).c("PlayerId", player.acq()).c("DisplayName", player.getDisplayName()).c("HasDebugAccess", Boolean.valueOf(player.zzh())).c("IconImageUri", player.abV()).c("IconImageUrl", player.abW()).c("HiResImageUri", player.abX()).c("HiResImageUrl", player.abY()).c("RetrievedTimestamp", Long.valueOf(player.acs())).c("Title", player.getTitle()).c("LevelInfo", player.acv()).c("GamerTag", player.acr()).c("Name", player.getName()).c("BannerImageLandscapeUri", player.acx()).c("BannerImageLandscapeUrl", player.acy()).c("BannerImagePortraitUri", player.acz()).c("BannerImagePortraitUrl", player.acA()).c("GamerFriendStatus", Integer.valueOf(player.acB())).c("GamerFriendUpdateTimestamp", Long.valueOf(player.acC())).c("IsMuted", Boolean.valueOf(player.isMuted())).toString();
    }

    @Override // com.google.android.gms.games.Player
    public final Uri abV() {
        return this.bRF;
    }

    @Override // com.google.android.gms.games.Player
    public final String abW() {
        return this.bRK;
    }

    @Override // com.google.android.gms.games.Player
    public final Uri abX() {
        return this.bRG;
    }

    @Override // com.google.android.gms.games.Player
    public final String abY() {
        return this.bRL;
    }

    @Override // com.google.android.gms.games.Player
    public final String acA() {
        return this.bFF;
    }

    @Override // com.google.android.gms.games.Player
    public final int acB() {
        return this.bSD;
    }

    @Override // com.google.android.gms.games.Player
    public final long acC() {
        return this.bSE;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: acD, reason: merged with bridge method [inline-methods] */
    public final Player freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.Player
    public final String acq() {
        return this.zzby;
    }

    @Override // com.google.android.gms.games.Player
    public final String acr() {
        return this.zzch;
    }

    @Override // com.google.android.gms.games.Player
    public final long acs() {
        return this.bSu;
    }

    @Override // com.google.android.gms.games.Player
    public final long act() {
        return this.bSw;
    }

    @Override // com.google.android.gms.games.Player
    public final boolean acu() {
        return this.bSz;
    }

    @Override // com.google.android.gms.games.Player
    public final PlayerLevelInfo acv() {
        return this.bSy;
    }

    @Override // com.google.android.gms.games.Player
    public final zza acw() {
        return this.bSx;
    }

    @Override // com.google.android.gms.games.Player
    public final Uri acx() {
        return this.bSA;
    }

    @Override // com.google.android.gms.games.Player
    public final String acy() {
        return this.bSB;
    }

    @Override // com.google.android.gms.games.Player
    public final Uri acz() {
        return this.bSC;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.Player
    public final String getDisplayName() {
        return this.bGD;
    }

    @Override // com.google.android.gms.games.Player
    public final String getName() {
        return this.name;
    }

    @Override // com.google.android.gms.games.Player
    public final String getTitle() {
        return this.zzcc;
    }

    public final int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean isMuted() {
        return this.bSF;
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (aas()) {
            parcel.writeString(this.zzby);
            parcel.writeString(this.bGD);
            parcel.writeString(this.bRF == null ? null : this.bRF.toString());
            parcel.writeString(this.bRG != null ? this.bRG.toString() : null);
            parcel.writeLong(this.bSu);
            return;
        }
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, acq(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getDisplayName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) abV(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) abX(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, acs());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 6, this.bSv);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, act());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, abW(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, abY(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 14, getTitle(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 15, (Parcelable) this.bSx, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 16, (Parcelable) acv(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 18, this.bSz);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 19, this.zzcg);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 20, this.zzch, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 21, this.name, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 22, (Parcelable) acx(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 23, acy(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 24, (Parcelable) acz(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 25, acA(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 26, this.bSD);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 27, this.bSE);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 28, this.bSF);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean zzh() {
        return this.zzcg;
    }

    @Override // com.google.android.gms.games.Player
    public final int zzi() {
        return this.bSv;
    }
}
