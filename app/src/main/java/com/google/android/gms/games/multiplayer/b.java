package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class b extends i {
    b() {
    }

    @Override // com.google.android.gms.games.multiplayer.i
    /* JADX INFO: renamed from: J */
    public final InvitationEntity createFromParcel(Parcel parcel) {
        if (InvitationEntity.f(InvitationEntity.aar()) || InvitationEntity.hZ(InvitationEntity.class.getCanonicalName())) {
            return super.createFromParcel(parcel);
        }
        GameEntity gameEntityCreateFromParcel = GameEntity.CREATOR.createFromParcel(parcel);
        String string = parcel.readString();
        long j = parcel.readLong();
        int i = parcel.readInt();
        ParticipantEntity participantEntityCreateFromParcel = ParticipantEntity.CREATOR.createFromParcel(parcel);
        int i2 = parcel.readInt();
        ArrayList arrayList = new ArrayList(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
        }
        return new InvitationEntity(gameEntityCreateFromParcel, string, j, i, participantEntityCreateFromParcel, arrayList, -1, 0);
    }

    @Override // com.google.android.gms.games.multiplayer.i, android.os.Parcelable.Creator
    public final /* synthetic */ InvitationEntity createFromParcel(Parcel parcel) {
        return createFromParcel(parcel);
    }
}
