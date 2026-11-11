package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class f extends m {
    f() {
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.m
    /* JADX INFO: renamed from: L */
    public final RoomEntity createFromParcel(Parcel parcel) {
        if (RoomEntity.f(RoomEntity.aar()) || RoomEntity.hZ(RoomEntity.class.getCanonicalName())) {
            return super.createFromParcel(parcel);
        }
        String string = parcel.readString();
        String string2 = parcel.readString();
        long j = parcel.readLong();
        int i = parcel.readInt();
        String string3 = parcel.readString();
        int i2 = parcel.readInt();
        Bundle bundle = parcel.readBundle();
        int i3 = parcel.readInt();
        ArrayList arrayList = new ArrayList(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
        }
        return new RoomEntity(string, string2, j, i, string3, i2, bundle, arrayList, -1);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.m, android.os.Parcelable.Creator
    public final /* synthetic */ RoomEntity createFromParcel(Parcel parcel) {
        return createFromParcel(parcel);
    }
}
