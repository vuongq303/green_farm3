package com.google.android.gms.internal.measurement;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public class zzn implements IInterface {
    private final IBinder zzqd;
    private final String zzqe;

    protected zzn(IBinder iBinder, String str) {
        this.zzqd = iBinder;
        this.zzqe = str;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.zzqd;
    }

    protected final Parcel obtainAndWriteInterfaceToken() {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInterfaceToken(this.zzqe);
        return parcelObtain;
    }

    protected final Parcel transactAndReadException(int i, Parcel parcel) {
        Parcel parcelObtain = Parcel.obtain();
        try {
            try {
                this.zzqd.transact(i, parcel, parcelObtain, 0);
                parcelObtain.readException();
                parcel.recycle();
                return parcelObtain;
            } catch (RuntimeException e) {
                parcelObtain.recycle();
                throw e;
            }
        } catch (Throwable th) {
            parcel.recycle();
            throw th;
        }
    }

    protected final void transactAndReadExceptionReturnVoid(int i, Parcel parcel) {
        Parcel parcelObtain = Parcel.obtain();
        try {
            this.zzqd.transact(i, parcel, parcelObtain, 0);
            parcelObtain.readException();
        } finally {
            parcel.recycle();
            parcelObtain.recycle();
        }
    }

    protected final void transactOneway(int i, Parcel parcel) {
        try {
            this.zzqd.transact(1, parcel, null, 1);
        } finally {
            parcel.recycle();
        }
    }
}
