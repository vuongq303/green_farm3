package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzb extends com.google.android.gms.common.data.e implements Invitation {
    private final ArrayList<Participant> bVS;
    private final Game bVo;
    private final ParticipantRef bWn;

    zzb(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.bVo = new GameRef(dataHolder, i);
        this.bVS = new ArrayList<>(i2);
        ParticipantRef participantRef = null;
        String string = getString("external_inviter_id");
        for (int i3 = 0; i3 < i2; i3++) {
            ParticipantRef participantRef2 = new ParticipantRef(this.mDataHolder, this.mDataRow + i3);
            if (participantRef2.aex().equals(string)) {
                participantRef = participantRef2;
            }
            this.bVS.add(participantRef2);
        }
        this.bWn = (ParticipantRef) bf.d(participantRef, "Must have a valid inviter!");
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final Game adP() {
        return this.bVo;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final Participant aep() {
        return this.bWn;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final long aeq() {
        return Math.max(getLong("creation_timestamp"), getLong("last_modified_timestamp"));
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final int aer() {
        return getInteger("type");
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final int aes() {
        if (getBoolean("has_automatch_criteria")) {
            return getInteger("automatch_max_players");
        }
        return 0;
    }

    @Override // com.google.android.gms.games.multiplayer.h
    public final ArrayList<Participant> aet() {
        return this.bVS;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return InvitationEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ Invitation freeze() {
        return new InvitationEntity(this);
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final String getInvitationId() {
        return getString("external_invitation_id");
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final int getVariant() {
        return getInteger("variant");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return InvitationEntity.a(this);
    }

    public final String toString() {
        return InvitationEntity.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((InvitationEntity) ((Invitation) freeze())).writeToParcel(parcel, i);
    }
}
