package com.google.android.gms.games.internal.player;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.e;

/* JADX INFO: loaded from: classes.dex */
public final class zzd extends e implements zza {
    private final b bSN;

    public zzd(DataHolder dataHolder, int i, b bVar) {
        super(dataHolder, i);
        this.bSN = bVar;
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final String ada() {
        return getString(this.bSN.bTx);
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final String adb() {
        return getString(this.bSN.bTy);
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final long adc() {
        return getLong(this.bSN.bTz);
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final Uri add() {
        return parseUri(this.bSN.bTA);
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final Uri ade() {
        return parseUri(this.bSN.bTB);
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final Uri adf() {
        return parseUri(this.bSN.bTC);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return zzb.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ zza freeze() {
        return new zzb(this);
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return zzb.a(this);
    }

    public final String toString() {
        return zzb.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((zzb) ((zza) freeze())).writeToParcel(parcel, i);
    }
}
