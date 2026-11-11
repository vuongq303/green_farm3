package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantRef;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzf extends com.google.android.gms.common.data.e implements Room {
    private final int zzmz;

    zzf(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zzmz = i2;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String aeB() {
        return getString("external_match_id");
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String aeC() {
        return getString("creator_external");
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final Bundle aeD() {
        if (getBoolean("has_automatch_criteria")) {
            return e.a(getInteger("automatch_min_players"), getInteger("automatch_max_players"), getLong("automatch_bit_mask"));
        }
        return null;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int aeE() {
        return getInteger("automatch_wait_estimate_sec");
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final long aeq() {
        return getLong("creation_timestamp");
    }

    @Override // com.google.android.gms.games.multiplayer.h
    public final ArrayList<Participant> aet() {
        ArrayList<Participant> arrayList = new ArrayList<>(this.zzmz);
        for (int i = 0; i < this.zzmz; i++) {
            arrayList.add(new ParticipantRef(this.mDataHolder, this.mDataRow + i));
        }
        return arrayList;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return RoomEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ Room freeze() {
        return new RoomEntity(this);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String getDescription() {
        return getString("description");
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int getStatus() {
        return getInteger(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int getVariant() {
        return getInteger("variant");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return RoomEntity.a(this);
    }

    public final String toString() {
        return RoomEntity.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((RoomEntity) ((Room) freeze())).writeToParcel(parcel, i);
    }
}
