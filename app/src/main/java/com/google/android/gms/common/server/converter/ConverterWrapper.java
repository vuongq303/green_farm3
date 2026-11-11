package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class ConverterWrapper extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ConverterWrapper> CREATOR = new a();
    private final StringToIntConverter bOR;
    private final int zzal;

    ConverterWrapper(int i, StringToIntConverter stringToIntConverter) {
        this.zzal = i;
        this.bOR = stringToIntConverter;
    }

    private ConverterWrapper(StringToIntConverter stringToIntConverter) {
        this.zzal = 1;
        this.bOR = stringToIntConverter;
    }

    public static ConverterWrapper a(com.google.android.gms.common.server.response.a<?, ?> aVar) {
        if (aVar instanceof StringToIntConverter) {
            return new ConverterWrapper((StringToIntConverter) aVar);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    public com.google.android.gms.common.server.response.a<?, ?> aaI() {
        if (this.bOR != null) {
            return this.bOR;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable) this.bOR, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
