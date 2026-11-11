package com.google.android.gms.games.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.android.gms.games.snapshot.zze;
import com.google.android.gms.internal.games.zza;
import com.google.android.gms.internal.games.zzc;

/* JADX INFO: renamed from: com.google.android.gms.games.internal.do, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class Cdo extends zza implements dn {
    Cdo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.games.internal.IGamesService");
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void O(long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(8013, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void P(long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(10002, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void Q(long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(22027, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void R(String str, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(5028, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final boolean ZA() {
        Parcel parcelTransactAndReadException = transactAndReadException(22030, obtainAndWriteInterfaceToken());
        boolean zZza = zzc.zza(parcelTransactAndReadException);
        parcelTransactAndReadException.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final int a(dj djVar, byte[] bArr, String str, String str2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeByteArray(bArr);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        Parcel parcelTransactAndReadException = transactAndReadException(5033, parcelObtainAndWriteInterfaceToken);
        int i = parcelTransactAndReadException.readInt();
        parcelTransactAndReadException.recycle();
        return i;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent a(int i, byte[] bArr, int i2, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeByteArray(bArr);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        Parcel parcelTransactAndReadException = transactAndReadException(10012, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent a(PlayerEntity playerEntity) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, playerEntity);
        Parcel parcelTransactAndReadException = transactAndReadException(15503, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent a(RoomEntity roomEntity, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, roomEntity);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        Parcel parcelTransactAndReadException = transactAndReadException(9011, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent a(String str, boolean z, boolean z2, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z2);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        Parcel parcelTransactAndReadException = transactAndReadException(12001, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(IBinder iBinder, Bundle bundle) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeStrongBinder(iBinder);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        transactAndReadExceptionReturnVoid(5005, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(Contents contents) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, (Parcelable) contents);
        transactAndReadExceptionReturnVoid(12019, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        transactAndReadExceptionReturnVoid(5002, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(10016, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, int i, int i2, int i3) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        parcelObtainAndWriteInterfaceToken.writeInt(i3);
        transactAndReadExceptionReturnVoid(10009, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, int i, int i2, String[] strArr, Bundle bundle) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        parcelObtainAndWriteInterfaceToken.writeStringArray(strArr);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        transactAndReadExceptionReturnVoid(8004, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, int i, boolean z, boolean z2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z2);
        transactAndReadExceptionReturnVoid(5015, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, int i, int[] iArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeIntArray(iArr);
        transactAndReadExceptionReturnVoid(10018, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(5058, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, Bundle bundle, int i, int i2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        transactAndReadExceptionReturnVoid(5021, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, IBinder iBinder, int i, String[] strArr, Bundle bundle, boolean z, long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeStrongBinder(iBinder);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeStringArray(strArr);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzc.zza(parcelObtainAndWriteInterfaceToken, false);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(5030, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, IBinder iBinder, String str, boolean z, long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeStrongBinder(iBinder);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(parcelObtainAndWriteInterfaceToken, false);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(5031, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(5032, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, int i, int i2, int i3, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        parcelObtainAndWriteInterfaceToken.writeInt(i3);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(5019, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, int i, IBinder iBinder, Bundle bundle) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeStrongBinder(iBinder);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        transactAndReadExceptionReturnVoid(5025, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, int i, boolean z, boolean z2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z2);
        transactAndReadExceptionReturnVoid(9020, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, long j, String str2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        transactAndReadExceptionReturnVoid(7002, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, IBinder iBinder, Bundle bundle) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeStrongBinder(iBinder);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        transactAndReadExceptionReturnVoid(5023, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, zze zzeVar, Contents contents) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzeVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, (Parcelable) contents);
        transactAndReadExceptionReturnVoid(12007, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, String str2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        transactAndReadExceptionReturnVoid(8011, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, String str2, int i, int i2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(null);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        transactAndReadExceptionReturnVoid(8001, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, String str2, zze zzeVar, Contents contents) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzeVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, (Parcelable) contents);
        transactAndReadExceptionReturnVoid(12033, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(6504, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, boolean z, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(15001, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeByteArray(bArr);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        parcelObtainAndWriteInterfaceToken.writeTypedArray(participantResultArr, 0);
        transactAndReadExceptionReturnVoid(8007, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String str, byte[] bArr, ParticipantResult[] participantResultArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeByteArray(bArr);
        parcelObtainAndWriteInterfaceToken.writeTypedArray(participantResultArr, 0);
        transactAndReadExceptionReturnVoid(8008, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(6001, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, boolean z, String[] strArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        parcelObtainAndWriteInterfaceToken.writeStringArray(strArr);
        transactAndReadExceptionReturnVoid(12031, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, int[] iArr, int i, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeIntArray(iArr);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(12010, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String[] strArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeStringArray(strArr);
        transactAndReadExceptionReturnVoid(10006, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dj djVar, String[] strArr, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeStringArray(strArr);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(12029, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(dl dlVar, long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, dlVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(15501, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(String str, IBinder iBinder, Bundle bundle) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeStrongBinder(iBinder);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        transactAndReadExceptionReturnVoid(13002, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void a(String str, dj djVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        transactAndReadExceptionReturnVoid(20001, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final int adA() {
        Parcel parcelTransactAndReadException = transactAndReadException(10023, obtainAndWriteInterfaceToken());
        int i = parcelTransactAndReadException.readInt();
        parcelTransactAndReadException.recycle();
        return i;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final int adB() {
        Parcel parcelTransactAndReadException = transactAndReadException(12035, obtainAndWriteInterfaceToken());
        int i = parcelTransactAndReadException.readInt();
        parcelTransactAndReadException.recycle();
        return i;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final int adD() {
        Parcel parcelTransactAndReadException = transactAndReadException(12036, obtainAndWriteInterfaceToken());
        int i = parcelTransactAndReadException.readInt();
        parcelTransactAndReadException.recycle();
        return i;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final String adI() {
        Parcel parcelTransactAndReadException = transactAndReadException(5012, obtainAndWriteInterfaceToken());
        String string = parcelTransactAndReadException.readString();
        parcelTransactAndReadException.recycle();
        return string;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final DataHolder adJ() {
        Parcel parcelTransactAndReadException = transactAndReadException(5013, obtainAndWriteInterfaceToken());
        DataHolder dataHolder = (DataHolder) zzc.zza(parcelTransactAndReadException, DataHolder.CREATOR);
        parcelTransactAndReadException.recycle();
        return dataHolder;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final DataHolder adK() {
        Parcel parcelTransactAndReadException = transactAndReadException(5502, obtainAndWriteInterfaceToken());
        DataHolder dataHolder = (DataHolder) zzc.zza(parcelTransactAndReadException, DataHolder.CREATOR);
        parcelTransactAndReadException.recycle();
        return dataHolder;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent adL() {
        Parcel parcelTransactAndReadException = transactAndReadException(19002, obtainAndWriteInterfaceToken());
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent adm() {
        Parcel parcelTransactAndReadException = transactAndReadException(9003, obtainAndWriteInterfaceToken());
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent ado() {
        Parcel parcelTransactAndReadException = transactAndReadException(9005, obtainAndWriteInterfaceToken());
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent adp() {
        Parcel parcelTransactAndReadException = transactAndReadException(9006, obtainAndWriteInterfaceToken());
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent adq() {
        Parcel parcelTransactAndReadException = transactAndReadException(9007, obtainAndWriteInterfaceToken());
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent adu() {
        Parcel parcelTransactAndReadException = transactAndReadException(9010, obtainAndWriteInterfaceToken());
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final int adw() {
        Parcel parcelTransactAndReadException = transactAndReadException(8024, obtainAndWriteInterfaceToken());
        int i = parcelTransactAndReadException.readInt();
        parcelTransactAndReadException.recycle();
        return i;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent ady() {
        Parcel parcelTransactAndReadException = transactAndReadException(10015, obtainAndWriteInterfaceToken());
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final int adz() {
        Parcel parcelTransactAndReadException = transactAndReadException(10013, obtainAndWriteInterfaceToken());
        int i = parcelTransactAndReadException.readInt();
        parcelTransactAndReadException.recycle();
        return i;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final int b(byte[] bArr, String str, String[] strArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeByteArray(bArr);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeStringArray(strArr);
        Parcel parcelTransactAndReadException = transactAndReadException(5034, parcelObtainAndWriteInterfaceToken);
        int i = parcelTransactAndReadException.readInt();
        parcelTransactAndReadException.recycle();
        return i;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        transactAndReadExceptionReturnVoid(5026, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(22016, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(8012, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(8005, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, String str, int i, int i2, int i3, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        parcelObtainAndWriteInterfaceToken.writeInt(i3);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(5020, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, String str, int i, IBinder iBinder, Bundle bundle) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeStrongBinder(iBinder);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        transactAndReadExceptionReturnVoid(7003, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, String str, IBinder iBinder, Bundle bundle) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeStrongBinder(iBinder);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        transactAndReadExceptionReturnVoid(5024, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, String str, String str2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        transactAndReadExceptionReturnVoid(12009, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, String str, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(13006, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(6503, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void b(dj djVar, String[] strArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeStringArray(strArr);
        transactAndReadExceptionReturnVoid(10007, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void c(dj djVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        transactAndReadExceptionReturnVoid(21007, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void c(dj djVar, long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(10001, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void c(dj djVar, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(8006, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void c(dj djVar, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(8027, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void d(dj djVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        transactAndReadExceptionReturnVoid(22028, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void d(dj djVar, long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(12011, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void d(dj djVar, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(8009, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void d(dj djVar, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(12002, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void e(dj djVar, long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(22026, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void e(dj djVar, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(8010, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void e(dj djVar, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(12016, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void f(dj djVar, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(8014, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void f(dj djVar, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(17001, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void g(dj djVar, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(12020, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Bundle getConnectionHint() {
        Parcel parcelTransactAndReadException = transactAndReadException(5004, obtainAndWriteInterfaceToken());
        Bundle bundle = (Bundle) zzc.zza(parcelTransactAndReadException, Bundle.CREATOR);
        parcelTransactAndReadException.recycle();
        return bundle;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void h(dj djVar, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, djVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(12008, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent iw(String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        Parcel parcelTransactAndReadException = transactAndReadException(12034, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent m(int[] iArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeIntArray(iArr);
        Parcel parcelTransactAndReadException = transactAndReadException(12030, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void ne(int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(5036, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent o(int i, int i2, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        Parcel parcelTransactAndReadException = transactAndReadException(9008, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent q(int i, int i2, boolean z) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        Parcel parcelTransactAndReadException = transactAndReadException(9009, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final Intent z(String str, int i, int i2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        Parcel parcelTransactAndReadException = transactAndReadException(18001, parcelObtainAndWriteInterfaceToken);
        Intent intent = (Intent) zzc.zza(parcelTransactAndReadException, Intent.CREATOR);
        parcelTransactAndReadException.recycle();
        return intent;
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void zza(long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(5001, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void zza(String str, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(12017, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void zzb(long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(5059, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void zzb(String str, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(5029, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void zzbd() {
        transactAndReadExceptionReturnVoid(5006, obtainAndWriteInterfaceToken());
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void zze(long j) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(12012, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.games.internal.dn
    public final void zzf(String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(8002, parcelObtainAndWriteInterfaceToken);
    }
}
