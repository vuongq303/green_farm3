package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class BinderWrapper implements Parcelable {
    public static final Parcelable.Creator<BinderWrapper> CREATOR = new bl();
    private IBinder bND;

    public BinderWrapper() {
        this.bND = null;
    }

    public BinderWrapper(IBinder iBinder) {
        this.bND = null;
        this.bND = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.bND = null;
        this.bND = parcel.readStrongBinder();
    }

    /* synthetic */ BinderWrapper(Parcel parcel, bl blVar) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.bND);
    }
}
