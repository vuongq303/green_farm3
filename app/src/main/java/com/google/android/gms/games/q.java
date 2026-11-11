package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
final class q extends t {
    q() {
    }

    @Override // com.google.android.gms.games.t
    /* JADX INFO: renamed from: I */
    public final PlayerEntity createFromParcel(Parcel parcel) {
        if (PlayerEntity.f(PlayerEntity.aar()) || PlayerEntity.hZ(PlayerEntity.class.getCanonicalName())) {
            return super.createFromParcel(parcel);
        }
        String string = parcel.readString();
        String string2 = parcel.readString();
        String string3 = parcel.readString();
        String string4 = parcel.readString();
        return new PlayerEntity(string, string2, string3 == null ? null : Uri.parse(string3), string4 == null ? null : Uri.parse(string4), parcel.readLong(), -1, -1L, null, null, null, null, null, true, false, parcel.readString(), parcel.readString(), null, null, null, null, -1, -1L, false);
    }

    @Override // com.google.android.gms.games.t, android.os.Parcelable.Creator
    public final /* synthetic */ PlayerEntity createFromParcel(Parcel parcel) {
        return createFromParcel(parcel);
    }
}
