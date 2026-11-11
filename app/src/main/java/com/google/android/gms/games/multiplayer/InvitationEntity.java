package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class InvitationEntity extends GamesDowngradeableSafeParcel implements Invitation {
    public static final Parcelable.Creator<InvitationEntity> CREATOR = new b();
    private final String bUe;
    private final GameEntity bVP;
    private final long bVQ;
    private final ParticipantEntity bVR;
    private final ArrayList<ParticipantEntity> bVS;
    private final int zzob;
    private final int zzoe;
    private final int zzof;

    InvitationEntity(GameEntity gameEntity, String str, long j, int i, ParticipantEntity participantEntity, ArrayList<ParticipantEntity> arrayList, int i2, int i3) {
        this.bVP = gameEntity;
        this.bUe = str;
        this.bVQ = j;
        this.zzob = i;
        this.bVR = participantEntity;
        this.bVS = arrayList;
        this.zzoe = i2;
        this.zzof = i3;
    }

    InvitationEntity(Invitation invitation) {
        this.bVP = new GameEntity(invitation.adP());
        this.bUe = invitation.getInvitationId();
        this.bVQ = invitation.aeq();
        this.zzob = invitation.aer();
        this.zzoe = invitation.getVariant();
        this.zzof = invitation.aes();
        String strAex = invitation.aep().aex();
        Participant participant = null;
        ArrayList<Participant> arrayListAet = invitation.aet();
        int size = arrayListAet.size();
        this.bVS = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Participant participant2 = arrayListAet.get(i);
            if (participant2.aex().equals(strAex)) {
                participant = participant2;
            }
            this.bVS.add((ParticipantEntity) participant2.freeze());
        }
        bf.d(participant, "Must have a valid inviter!");
        this.bVR = (ParticipantEntity) participant.freeze();
    }

    static int a(Invitation invitation) {
        return bd.hashCode(invitation.adP(), invitation.getInvitationId(), Long.valueOf(invitation.aeq()), Integer.valueOf(invitation.aer()), invitation.aep(), invitation.aet(), Integer.valueOf(invitation.getVariant()), Integer.valueOf(invitation.aes()));
    }

    static boolean a(Invitation invitation, Object obj) {
        if (!(obj instanceof Invitation)) {
            return false;
        }
        if (invitation == obj) {
            return true;
        }
        Invitation invitation2 = (Invitation) obj;
        return bd.b(invitation2.adP(), invitation.adP()) && bd.b(invitation2.getInvitationId(), invitation.getInvitationId()) && bd.b(Long.valueOf(invitation2.aeq()), Long.valueOf(invitation.aeq())) && bd.b(Integer.valueOf(invitation2.aer()), Integer.valueOf(invitation.aer())) && bd.b(invitation2.aep(), invitation.aep()) && bd.b(invitation2.aet(), invitation.aet()) && bd.b(Integer.valueOf(invitation2.getVariant()), Integer.valueOf(invitation.getVariant())) && bd.b(Integer.valueOf(invitation2.aes()), Integer.valueOf(invitation.aes()));
    }

    static String b(Invitation invitation) {
        return bd.C(invitation).c("Game", invitation.adP()).c("InvitationId", invitation.getInvitationId()).c("CreationTimestamp", Long.valueOf(invitation.aeq())).c("InvitationType", Integer.valueOf(invitation.aer())).c("Inviter", invitation.aep()).c("Participants", invitation.aet()).c("Variant", Integer.valueOf(invitation.getVariant())).c("AvailableAutoMatchSlots", Integer.valueOf(invitation.aes())).toString();
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final Game adP() {
        return this.bVP;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final Participant aep() {
        return this.bVR;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final long aeq() {
        return this.bVQ;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final int aer() {
        return this.zzob;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final int aes() {
        return this.zzof;
    }

    @Override // com.google.android.gms.games.multiplayer.h
    public final ArrayList<Participant> aet() {
        return new ArrayList<>(this.bVS);
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: aeu, reason: merged with bridge method [inline-methods] */
    public final Invitation freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final String getInvitationId() {
        return this.bUe;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final int getVariant() {
        return this.zzoe;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (!aas()) {
            int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, (Parcelable) adP(), i, false);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getInvitationId(), false);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, aeq());
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 4, aer());
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, (Parcelable) aep(), i, false);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 6, aet(), false);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 7, getVariant());
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 8, aes());
            com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
            return;
        }
        this.bVP.writeToParcel(parcel, i);
        parcel.writeString(this.bUe);
        parcel.writeLong(this.bVQ);
        parcel.writeInt(this.zzob);
        this.bVR.writeToParcel(parcel, i);
        int size = this.bVS.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.bVS.get(i2).writeToParcel(parcel, i);
        }
    }
}
