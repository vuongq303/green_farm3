package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzjs extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzjs> CREATOR = new zzjt();
    public final String name;
    private final int versionCode;
    public final String zzaek;
    private final String zzajf;
    public final long zzaqu;
    private final Long zzaqv;
    private final Float zzaqw;
    private final Double zzaqx;

    zzjs(int i, String str, long j, Long l, Float f, String str2, String str3, Double d) {
        this.versionCode = i;
        this.name = str;
        this.zzaqu = j;
        this.zzaqv = l;
        this.zzaqw = null;
        if (i == 1) {
            this.zzaqx = f != null ? Double.valueOf(f.doubleValue()) : null;
        } else {
            this.zzaqx = d;
        }
        this.zzajf = str2;
        this.zzaek = str3;
    }

    zzjs(zzju zzjuVar) {
        this(zzjuVar.name, zzjuVar.zzaqu, zzjuVar.value, zzjuVar.zzaek);
    }

    zzjs(String str, long j, Object obj, String str2) {
        bf.id(str);
        this.versionCode = 2;
        this.name = str;
        this.zzaqu = j;
        this.zzaek = str2;
        if (obj == null) {
            this.zzaqv = null;
            this.zzaqw = null;
            this.zzaqx = null;
            this.zzajf = null;
            return;
        }
        if (obj instanceof Long) {
            this.zzaqv = (Long) obj;
            this.zzaqw = null;
            this.zzaqx = null;
            this.zzajf = null;
            return;
        }
        if (obj instanceof String) {
            this.zzaqv = null;
            this.zzaqw = null;
            this.zzaqx = null;
            this.zzajf = (String) obj;
            return;
        }
        if (!(obj instanceof Double)) {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
        this.zzaqv = null;
        this.zzaqw = null;
        this.zzaqx = (Double) obj;
        this.zzajf = null;
    }

    public final Object getValue() {
        if (this.zzaqv != null) {
            return this.zzaqv;
        }
        if (this.zzaqx != null) {
            return this.zzaqx;
        }
        if (this.zzajf != null) {
            return this.zzajf;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.c(parcel, 1, this.versionCode);
        c.a(parcel, 2, this.name, false);
        c.a(parcel, 3, this.zzaqu);
        c.a(parcel, 4, this.zzaqv, false);
        c.a(parcel, 5, (Float) null, false);
        c.a(parcel, 6, this.zzajf, false);
        c.a(parcel, 7, this.zzaek, false);
        c.a(parcel, 8, this.zzaqx, false);
        c.G(parcel, iX);
    }
}
