package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class zzer extends AbstractSafeParcelable implements Iterable<String> {
    public static final Parcelable.Creator<zzer> CREATOR = new zzet();
    private final Bundle zzafw;

    zzer(Bundle bundle) {
        this.zzafw = bundle;
    }

    final Object get(String str) {
        return this.zzafw.get(str);
    }

    final Long getLong(String str) {
        return Long.valueOf(this.zzafw.getLong(str));
    }

    final String getString(String str) {
        return this.zzafw.getString(str);
    }

    @Override // java.lang.Iterable
    public final Iterator<String> iterator() {
        return new zzes(this);
    }

    public final int size() {
        return this.zzafw.size();
    }

    public final String toString() {
        return this.zzafw.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, zzif(), false);
        c.G(parcel, iX);
    }

    final Double zzbc(String str) {
        return Double.valueOf(this.zzafw.getDouble(str));
    }

    public final Bundle zzif() {
        return new Bundle(this.zzafw);
    }
}
