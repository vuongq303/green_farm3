package com.google.android.gms.games.internal.player;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzb extends com.google.android.gms.games.internal.zzd implements zza {
    public static final Parcelable.Creator<zzb> CREATOR = new a();
    private final String bLr;
    private final String bTb;
    private final long bTc;
    private final Uri bTd;
    private final Uri bTe;
    private final Uri bTf;

    public zzb(zza zzaVar) {
        this.bLr = zzaVar.ada();
        this.bTb = zzaVar.adb();
        this.bTc = zzaVar.adc();
        this.bTd = zzaVar.add();
        this.bTe = zzaVar.ade();
        this.bTf = zzaVar.adf();
    }

    zzb(String str, String str2, long j, Uri uri, Uri uri2, Uri uri3) {
        this.bLr = str;
        this.bTb = str2;
        this.bTc = j;
        this.bTd = uri;
        this.bTe = uri2;
        this.bTf = uri3;
    }

    static int a(zza zzaVar) {
        return bd.hashCode(zzaVar.ada(), zzaVar.adb(), Long.valueOf(zzaVar.adc()), zzaVar.add(), zzaVar.ade(), zzaVar.adf());
    }

    static boolean a(zza zzaVar, Object obj) {
        if (!(obj instanceof zza)) {
            return false;
        }
        if (zzaVar == obj) {
            return true;
        }
        zza zzaVar2 = (zza) obj;
        return bd.b(zzaVar2.ada(), zzaVar.ada()) && bd.b(zzaVar2.adb(), zzaVar.adb()) && bd.b(Long.valueOf(zzaVar2.adc()), Long.valueOf(zzaVar.adc())) && bd.b(zzaVar2.add(), zzaVar.add()) && bd.b(zzaVar2.ade(), zzaVar.ade()) && bd.b(zzaVar2.adf(), zzaVar.adf());
    }

    static String b(zza zzaVar) {
        return bd.C(zzaVar).c("GameId", zzaVar.ada()).c("GameName", zzaVar.adb()).c("ActivityTimestampMillis", Long.valueOf(zzaVar.adc())).c("GameIconUri", zzaVar.add()).c("GameHiResUri", zzaVar.ade()).c("GameFeaturedUri", zzaVar.adf()).toString();
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final String ada() {
        return this.bLr;
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final String adb() {
        return this.bTb;
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final long adc() {
        return this.bTc;
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final Uri add() {
        return this.bTd;
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final Uri ade() {
        return this.bTe;
    }

    @Override // com.google.android.gms.games.internal.player.zza
    public final Uri adf() {
        return this.bTf;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* bridge */ /* synthetic */ zza freeze() {
        if (this == null) {
            throw null;
        }
        return this;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 1, this.bLr, false);
        c.a(parcel, 2, this.bTb, false);
        c.a(parcel, 3, this.bTc);
        c.a(parcel, 4, (Parcelable) this.bTd, i, false);
        c.a(parcel, 5, (Parcelable) this.bTe, i, false);
        c.a(parcel, 6, (Parcelable) this.bTf, i, false);
        c.G(parcel, iX);
    }
}
