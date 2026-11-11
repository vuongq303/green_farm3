package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class zzbo implements Parcelable {

    @Deprecated
    public static final Parcelable.Creator<zzbo> CREATOR = new zzbp();
    private String value;
    private String zznj;
    private String zzxp;

    @Deprecated
    public zzbo() {
    }

    @Deprecated
    zzbo(Parcel parcel) {
        this.zznj = parcel.readString();
        this.zzxp = parcel.readString();
        this.value = parcel.readString();
    }

    @Override // android.os.Parcelable
    @Deprecated
    public final int describeContents() {
        return 0;
    }

    public final String getId() {
        return this.zznj;
    }

    public final String getValue() {
        return this.value;
    }

    @Override // android.os.Parcelable
    @Deprecated
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.zznj);
        parcel.writeString(this.zzxp);
        parcel.writeString(this.value);
    }
}
