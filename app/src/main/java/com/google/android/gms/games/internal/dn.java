package com.google.android.gms.games.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.android.gms.games.snapshot.zze;

/* JADX INFO: loaded from: classes.dex */
public interface dn extends IInterface {
    void O(long j);

    void P(long j);

    void Q(long j);

    void R(String str, int i);

    boolean ZA();

    int a(dj djVar, byte[] bArr, String str, String str2);

    Intent a(int i, byte[] bArr, int i2, String str);

    Intent a(PlayerEntity playerEntity);

    Intent a(RoomEntity roomEntity, int i);

    Intent a(String str, boolean z, boolean z2, int i);

    void a(IBinder iBinder, Bundle bundle);

    void a(Contents contents);

    void a(dj djVar);

    void a(dj djVar, int i);

    void a(dj djVar, int i, int i2, int i3);

    void a(dj djVar, int i, int i2, String[] strArr, Bundle bundle);

    void a(dj djVar, int i, boolean z, boolean z2);

    void a(dj djVar, int i, int[] iArr);

    void a(dj djVar, long j);

    void a(dj djVar, Bundle bundle, int i, int i2);

    void a(dj djVar, IBinder iBinder, int i, String[] strArr, Bundle bundle, boolean z, long j);

    void a(dj djVar, IBinder iBinder, String str, boolean z, long j);

    void a(dj djVar, String str);

    void a(dj djVar, String str, int i, int i2, int i3, boolean z);

    void a(dj djVar, String str, int i, IBinder iBinder, Bundle bundle);

    void a(dj djVar, String str, int i, boolean z, boolean z2);

    void a(dj djVar, String str, long j, String str2);

    void a(dj djVar, String str, IBinder iBinder, Bundle bundle);

    void a(dj djVar, String str, zze zzeVar, Contents contents);

    void a(dj djVar, String str, String str2);

    void a(dj djVar, String str, String str2, int i, int i2);

    void a(dj djVar, String str, String str2, zze zzeVar, Contents contents);

    void a(dj djVar, String str, boolean z);

    void a(dj djVar, String str, boolean z, int i);

    void a(dj djVar, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr);

    void a(dj djVar, String str, byte[] bArr, ParticipantResult[] participantResultArr);

    void a(dj djVar, boolean z);

    void a(dj djVar, boolean z, String[] strArr);

    void a(dj djVar, int[] iArr, int i, boolean z);

    void a(dj djVar, String[] strArr);

    void a(dj djVar, String[] strArr, boolean z);

    void a(dl dlVar, long j);

    void a(String str, IBinder iBinder, Bundle bundle);

    void a(String str, dj djVar);

    int adA();

    int adB();

    int adD();

    String adI();

    DataHolder adJ();

    DataHolder adK();

    Intent adL();

    Intent adm();

    Intent ado();

    Intent adp();

    Intent adq();

    Intent adu();

    int adw();

    Intent ady();

    int adz();

    int b(byte[] bArr, String str, String[] strArr);

    void b(dj djVar);

    void b(dj djVar, int i);

    void b(dj djVar, long j);

    void b(dj djVar, String str);

    void b(dj djVar, String str, int i, int i2, int i3, boolean z);

    void b(dj djVar, String str, int i, IBinder iBinder, Bundle bundle);

    void b(dj djVar, String str, IBinder iBinder, Bundle bundle);

    void b(dj djVar, String str, String str2);

    void b(dj djVar, String str, boolean z);

    void b(dj djVar, boolean z);

    void b(dj djVar, String[] strArr);

    void c(dj djVar);

    void c(dj djVar, long j);

    void c(dj djVar, String str);

    void c(dj djVar, boolean z);

    void d(dj djVar);

    void d(dj djVar, long j);

    void d(dj djVar, String str);

    void d(dj djVar, boolean z);

    void e(dj djVar, long j);

    void e(dj djVar, String str);

    void e(dj djVar, boolean z);

    void f(dj djVar, String str);

    void f(dj djVar, boolean z);

    void g(dj djVar, String str);

    Bundle getConnectionHint();

    void h(dj djVar, String str);

    Intent iw(String str);

    Intent m(int[] iArr);

    void ne(int i);

    Intent o(int i, int i2, boolean z);

    Intent q(int i, int i2, boolean z);

    Intent z(String str, int i, int i2);

    void zza(long j);

    void zza(String str, int i);

    void zzb(long j);

    void zzb(String str, int i);

    void zzbd();

    void zze(long j);

    void zzf(String str);
}
