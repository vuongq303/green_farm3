package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;

/* JADX INFO: loaded from: classes.dex */
public final class ParticipantRef extends com.google.android.gms.common.data.e implements Participant {
    private final PlayerRef bVW;

    public ParticipantRef(DataHolder dataHolder, int i) {
        super(dataHolder, i);
        this.bVW = new PlayerRef(dataHolder, i);
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Uri abV() {
        return hasNull("external_player_id") ? parseUri("default_display_image_uri") : this.bVW.abV();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String abW() {
        return hasNull("external_player_id") ? getString("default_display_image_url") : this.bVW.abW();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Uri abX() {
        return hasNull("external_player_id") ? parseUri("default_display_hi_res_image_uri") : this.bVW.abX();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String abY() {
        return hasNull("external_player_id") ? getString("default_display_hi_res_image_url") : this.bVW.abY();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Player acR() {
        if (hasNull("external_player_id")) {
            return null;
        }
        return this.bVW;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String aev() {
        return getString("client_address");
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final boolean aew() {
        return getInteger("connected") > 0;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String aex() {
        return getString("external_participant_id");
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final ParticipantResult aey() {
        if (hasNull("result_type")) {
            return null;
        }
        return new ParticipantResult(aex(), getInteger("result_type"), getInteger("placing"));
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return ParticipantEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ Participant freeze() {
        return new ParticipantEntity(this);
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final int getCapabilities() {
        return getInteger("capabilities");
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String getDisplayName() {
        return hasNull("external_player_id") ? getString("default_display_name") : this.bVW.getDisplayName();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final int getStatus() {
        return getInteger("player_status");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return ParticipantEntity.a(this);
    }

    public final String toString() {
        return ParticipantEntity.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((ParticipantEntity) ((Participant) freeze())).writeToParcel(parcel, i);
    }
}
