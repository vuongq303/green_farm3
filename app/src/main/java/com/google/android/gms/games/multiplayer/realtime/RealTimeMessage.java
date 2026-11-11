package com.google.android.gms.games.multiplayer.realtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class RealTimeMessage implements Parcelable {
    public static final Parcelable.Creator<RealTimeMessage> CREATOR = new k();
    private final String bVX;
    private final byte[] bVY;
    private final int zzoq;

    private RealTimeMessage(Parcel parcel) {
        this(parcel.readString(), parcel.createByteArray(), parcel.readInt());
    }

    /* synthetic */ RealTimeMessage(Parcel parcel, k kVar) {
        this(parcel);
    }

    private RealTimeMessage(String str, byte[] bArr, int i) {
        this.bVX = (String) bf.checkNotNull(str);
        this.bVY = (byte[]) ((byte[]) bf.checkNotNull(bArr)).clone();
        this.zzoq = i;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.bVX);
        parcel.writeByteArray(this.bVY);
        parcel.writeInt(this.zzoq);
    }
}
