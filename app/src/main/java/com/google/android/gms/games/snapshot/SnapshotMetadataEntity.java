package com.google.android.gms.games.snapshot;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class SnapshotMetadataEntity extends zzd implements SnapshotMetadata {
    public static final Parcelable.Creator<SnapshotMetadataEntity> CREATOR = new l();
    private final GameEntity bVP;
    private final String bVg;
    private final Uri bWL;
    private final PlayerEntity bWM;
    private final String bWN;
    private final long bWO;
    private final long bWP;
    private final float bWQ;
    private final String description;
    private final String deviceName;
    private final String zzcc;
    private final String zzra;
    private final boolean zzrb;
    private final long zzrc;

    SnapshotMetadataEntity(GameEntity gameEntity, PlayerEntity playerEntity, String str, Uri uri, String str2, String str3, String str4, long j, long j2, float f, String str5, boolean z, long j3, String str6) {
        this.bVP = gameEntity;
        this.bWM = playerEntity;
        this.bVg = str;
        this.bWL = uri;
        this.bWN = str2;
        this.bWQ = f;
        this.zzcc = str3;
        this.description = str4;
        this.bWO = j;
        this.bWP = j2;
        this.zzra = str5;
        this.zzrb = z;
        this.zzrc = j3;
        this.deviceName = str6;
    }

    public SnapshotMetadataEntity(SnapshotMetadata snapshotMetadata) {
        this.bVP = new GameEntity(snapshotMetadata.adP());
        this.bWM = new PlayerEntity(snapshotMetadata.afr());
        this.bVg = snapshotMetadata.getSnapshotId();
        this.bWL = snapshotMetadata.afs();
        this.bWN = snapshotMetadata.aft();
        this.bWQ = snapshotMetadata.afu();
        this.zzcc = snapshotMetadata.getTitle();
        this.description = snapshotMetadata.getDescription();
        this.bWO = snapshotMetadata.afw();
        this.bWP = snapshotMetadata.afx();
        this.zzra = snapshotMetadata.afv();
        this.zzrb = snapshotMetadata.afy();
        this.zzrc = snapshotMetadata.afz();
        this.deviceName = snapshotMetadata.getDeviceName();
    }

    static boolean a(SnapshotMetadata snapshotMetadata, Object obj) {
        if (!(obj instanceof SnapshotMetadata)) {
            return false;
        }
        if (snapshotMetadata == obj) {
            return true;
        }
        SnapshotMetadata snapshotMetadata2 = (SnapshotMetadata) obj;
        return bd.b(snapshotMetadata2.adP(), snapshotMetadata.adP()) && bd.b(snapshotMetadata2.afr(), snapshotMetadata.afr()) && bd.b(snapshotMetadata2.getSnapshotId(), snapshotMetadata.getSnapshotId()) && bd.b(snapshotMetadata2.afs(), snapshotMetadata.afs()) && bd.b(Float.valueOf(snapshotMetadata2.afu()), Float.valueOf(snapshotMetadata.afu())) && bd.b(snapshotMetadata2.getTitle(), snapshotMetadata.getTitle()) && bd.b(snapshotMetadata2.getDescription(), snapshotMetadata.getDescription()) && bd.b(Long.valueOf(snapshotMetadata2.afw()), Long.valueOf(snapshotMetadata.afw())) && bd.b(Long.valueOf(snapshotMetadata2.afx()), Long.valueOf(snapshotMetadata.afx())) && bd.b(snapshotMetadata2.afv(), snapshotMetadata.afv()) && bd.b(Boolean.valueOf(snapshotMetadata2.afy()), Boolean.valueOf(snapshotMetadata.afy())) && bd.b(Long.valueOf(snapshotMetadata2.afz()), Long.valueOf(snapshotMetadata.afz())) && bd.b(snapshotMetadata2.getDeviceName(), snapshotMetadata.getDeviceName());
    }

    static int b(SnapshotMetadata snapshotMetadata) {
        return bd.hashCode(snapshotMetadata.adP(), snapshotMetadata.afr(), snapshotMetadata.getSnapshotId(), snapshotMetadata.afs(), Float.valueOf(snapshotMetadata.afu()), snapshotMetadata.getTitle(), snapshotMetadata.getDescription(), Long.valueOf(snapshotMetadata.afw()), Long.valueOf(snapshotMetadata.afx()), snapshotMetadata.afv(), Boolean.valueOf(snapshotMetadata.afy()), Long.valueOf(snapshotMetadata.afz()), snapshotMetadata.getDeviceName());
    }

    static String c(SnapshotMetadata snapshotMetadata) {
        return bd.C(snapshotMetadata).c("Game", snapshotMetadata.adP()).c("Owner", snapshotMetadata.afr()).c("SnapshotId", snapshotMetadata.getSnapshotId()).c("CoverImageUri", snapshotMetadata.afs()).c("CoverImageUrl", snapshotMetadata.aft()).c("CoverImageAspectRatio", Float.valueOf(snapshotMetadata.afu())).c("Description", snapshotMetadata.getDescription()).c("LastModifiedTimestamp", Long.valueOf(snapshotMetadata.afw())).c("PlayedTime", Long.valueOf(snapshotMetadata.afx())).c("UniqueName", snapshotMetadata.afv()).c("ChangePending", Boolean.valueOf(snapshotMetadata.afy())).c("ProgressValue", Long.valueOf(snapshotMetadata.afz())).c("DeviceName", snapshotMetadata.getDeviceName()).toString();
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Game adP() {
        return this.bVP;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: afC, reason: merged with bridge method [inline-methods] */
    public final SnapshotMetadata freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Player afr() {
        return this.bWM;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Uri afs() {
        return this.bWL;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String aft() {
        return this.bWN;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final float afu() {
        return this.bWQ;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String afv() {
        return this.zzra;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long afw() {
        return this.bWO;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long afx() {
        return this.bWP;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final boolean afy() {
        return this.zzrb;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long afz() {
        return this.zzrc;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getDeviceName() {
        return this.deviceName;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getSnapshotId() {
        return this.bVg;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getTitle() {
        return this.zzcc;
    }

    public final int hashCode() {
        return b(this);
    }

    public final String toString() {
        return c(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, (Parcelable) adP(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable) afr(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, getSnapshotId(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, (Parcelable) afs(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, aft(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, this.zzcc, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, afw());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, afx());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 11, afu());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 12, afv(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 13, afy());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 14, afz());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 15, getDeviceName(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
