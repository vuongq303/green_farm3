package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.games.PlayerEntity;

/* JADX INFO: loaded from: classes.dex */
final class g extends j {
    g() {
    }

    @Override // com.google.android.gms.games.multiplayer.j
    /* JADX INFO: renamed from: K */
    public final ParticipantEntity createFromParcel(Parcel parcel) {
        if (ParticipantEntity.f(ParticipantEntity.aar()) || ParticipantEntity.hZ(ParticipantEntity.class.getCanonicalName())) {
            return super.createFromParcel(parcel);
        }
        String string = parcel.readString();
        String string2 = parcel.readString();
        String string3 = parcel.readString();
        Uri uri = string3 == null ? null : Uri.parse(string3);
        String string4 = parcel.readString();
        return new ParticipantEntity(string, string2, uri, string4 == null ? null : Uri.parse(string4), parcel.readInt(), parcel.readString(), parcel.readInt() > 0, parcel.readInt() > 0 ? PlayerEntity.CREATOR.createFromParcel(parcel) : null, 7, null, null, null);
    }

    @Override // com.google.android.gms.games.multiplayer.j, android.os.Parcelable.Creator
    public final /* synthetic */ ParticipantEntity createFromParcel(Parcel parcel) {
        return createFromParcel(parcel);
    }
}
