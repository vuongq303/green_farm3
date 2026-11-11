package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import java.io.InputStream;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzhi extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzhi> CREATOR = new zzhj();

    @GuardedBy("this")
    private ParcelFileDescriptor zzaju;

    public zzhi() {
        this(null);
    }

    public zzhi(ParcelFileDescriptor parcelFileDescriptor) {
        this.zzaju = parcelFileDescriptor;
    }

    private final synchronized ParcelFileDescriptor zzhk() {
        return this.zzaju;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, (Parcelable) zzhk(), i, false);
        c.G(parcel, iX);
    }

    public final synchronized boolean zzhi() {
        return this.zzaju != null;
    }

    public final synchronized InputStream zzhj() {
        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = null;
        synchronized (this) {
            if (this.zzaju != null) {
                autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(this.zzaju);
                this.zzaju = null;
            }
        }
        return autoCloseInputStream;
    }
}
