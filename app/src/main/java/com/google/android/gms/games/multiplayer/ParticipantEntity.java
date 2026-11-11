package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;

/* JADX INFO: loaded from: classes.dex */
public final class ParticipantEntity extends GamesDowngradeableSafeParcel implements Participant {
    public static final Parcelable.Creator<ParticipantEntity> CREATOR = new g();
    private final String bGD;
    private final Uri bRF;
    private final Uri bRG;
    private final String bRK;
    private final String bRL;
    private final PlayerEntity bSU;
    private final String bUB;
    private final String bVT;
    private final boolean bVU;
    private final ParticipantResult bVV;
    private final int status;
    private final int zzoj;

    public ParticipantEntity(Participant participant) {
        this.bUB = participant.aex();
        this.bGD = participant.getDisplayName();
        this.bRF = participant.abV();
        this.bRG = participant.abX();
        this.status = participant.getStatus();
        this.bVT = participant.aev();
        this.bVU = participant.aew();
        Player playerAcR = participant.acR();
        this.bSU = playerAcR == null ? null : new PlayerEntity(playerAcR);
        this.zzoj = participant.getCapabilities();
        this.bVV = participant.aey();
        this.bRK = participant.abW();
        this.bRL = participant.abY();
    }

    ParticipantEntity(String str, String str2, Uri uri, Uri uri2, int i, String str3, boolean z, PlayerEntity playerEntity, int i2, ParticipantResult participantResult, String str4, String str5) {
        this.bUB = str;
        this.bGD = str2;
        this.bRF = uri;
        this.bRG = uri2;
        this.status = i;
        this.bVT = str3;
        this.bVU = z;
        this.bSU = playerEntity;
        this.zzoj = i2;
        this.bVV = participantResult;
        this.bRK = str4;
        this.bRL = str5;
    }

    static int a(Participant participant) {
        return bd.hashCode(participant.acR(), Integer.valueOf(participant.getStatus()), participant.aev(), Boolean.valueOf(participant.aew()), participant.getDisplayName(), participant.abV(), participant.abX(), Integer.valueOf(participant.getCapabilities()), participant.aey(), participant.aex());
    }

    static boolean a(Participant participant, Object obj) {
        if (!(obj instanceof Participant)) {
            return false;
        }
        if (participant == obj) {
            return true;
        }
        Participant participant2 = (Participant) obj;
        return bd.b(participant2.acR(), participant.acR()) && bd.b(Integer.valueOf(participant2.getStatus()), Integer.valueOf(participant.getStatus())) && bd.b(participant2.aev(), participant.aev()) && bd.b(Boolean.valueOf(participant2.aew()), Boolean.valueOf(participant.aew())) && bd.b(participant2.getDisplayName(), participant.getDisplayName()) && bd.b(participant2.abV(), participant.abV()) && bd.b(participant2.abX(), participant.abX()) && bd.b(Integer.valueOf(participant2.getCapabilities()), Integer.valueOf(participant.getCapabilities())) && bd.b(participant2.aey(), participant.aey()) && bd.b(participant2.aex(), participant.aex());
    }

    static String b(Participant participant) {
        return bd.C(participant).c("ParticipantId", participant.aex()).c("Player", participant.acR()).c("Status", Integer.valueOf(participant.getStatus())).c("ClientAddress", participant.aev()).c("ConnectedToRoom", Boolean.valueOf(participant.aew())).c("DisplayName", participant.getDisplayName()).c("IconImage", participant.abV()).c("IconImageUrl", participant.abW()).c("HiResImage", participant.abX()).c("HiResImageUrl", participant.abY()).c("Capabilities", Integer.valueOf(participant.getCapabilities())).c("Result", participant.aey()).toString();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Uri abV() {
        return this.bSU == null ? this.bRF : this.bSU.abV();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String abW() {
        return this.bSU == null ? this.bRK : this.bSU.abW();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Uri abX() {
        return this.bSU == null ? this.bRG : this.bSU.abX();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String abY() {
        return this.bSU == null ? this.bRL : this.bSU.abY();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Player acR() {
        return this.bSU;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String aev() {
        return this.bVT;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final boolean aew() {
        return this.bVU;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String aex() {
        return this.bUB;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final ParticipantResult aey() {
        return this.bVV;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: aez, reason: merged with bridge method [inline-methods] */
    public final Participant freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final int getCapabilities() {
        return this.zzoj;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String getDisplayName() {
        return this.bSU == null ? this.bGD : this.bSU.getDisplayName();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final int getStatus() {
        return this.status;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (aas()) {
            parcel.writeString(this.bUB);
            parcel.writeString(this.bGD);
            parcel.writeString(this.bRF == null ? null : this.bRF.toString());
            parcel.writeString(this.bRG != null ? this.bRG.toString() : null);
            parcel.writeInt(this.status);
            parcel.writeString(this.bVT);
            parcel.writeInt(this.bVU ? 1 : 0);
            parcel.writeInt(this.bSU != null ? 1 : 0);
            if (this.bSU != null) {
                this.bSU.writeToParcel(parcel, i);
                return;
            }
            return;
        }
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, aex(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getDisplayName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) abV(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) abX(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 5, getStatus());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, this.bVT, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, aew());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, (Parcelable) acR(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 9, this.zzoj);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, (Parcelable) aey(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 11, abW(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 12, abY(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
