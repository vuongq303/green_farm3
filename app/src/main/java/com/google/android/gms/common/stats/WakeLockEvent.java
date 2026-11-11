package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.List;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public final class WakeLockEvent extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new d();
    private final String bBs;
    private final long bQa;
    private int bQb;
    private final String bQc;
    private final String bQd;
    private final String bQe;
    private final int bQf;
    private final List<String> bQg;
    private final String bQh;
    private final long bQi;
    private int bQj;
    private final float bQk;
    private long bQl;
    private final long dz;
    private final int zzal;

    WakeLockEvent(int i, long j, int i2, String str, int i3, List<String> list, String str2, long j2, int i4, String str3, String str4, float f, long j3, String str5) {
        this.zzal = i;
        this.bQa = j;
        this.bQb = i2;
        this.bQc = str;
        this.bQd = str3;
        this.bQe = str5;
        this.bQf = i3;
        this.bQl = -1L;
        this.bQg = list;
        this.bQh = str2;
        this.bQi = j2;
        this.bQj = i4;
        this.bBs = str4;
        this.bQk = f;
        this.dz = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long aaZ() {
        return this.bQl;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final String aba() {
        return this.bQh;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final String abb() {
        String strAbd = abd();
        int iAbg = abg();
        String strJoin = abh() == null ? "" : TextUtils.join(",", abh());
        int iAbi = abi();
        String strAbe = abe() == null ? "" : abe();
        String strAbj = abj() == null ? "" : abj();
        float fAbk = abk();
        String strAbf = abf() == null ? "" : abf();
        return new StringBuilder(String.valueOf(strAbd).length() + 45 + String.valueOf(strJoin).length() + String.valueOf(strAbe).length() + String.valueOf(strAbj).length() + String.valueOf(strAbf).length()).append("\t").append(strAbd).append("\t").append(iAbg).append("\t").append(strJoin).append("\t").append(iAbi).append("\t").append(strAbe).append("\t").append(strAbj).append("\t").append(fAbk).append("\t").append(strAbf).toString();
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long abc() {
        return this.dz;
    }

    public final String abd() {
        return this.bQc;
    }

    public final String abe() {
        return this.bQd;
    }

    public final String abf() {
        return this.bQe;
    }

    public final int abg() {
        return this.bQf;
    }

    @Nullable
    public final List<String> abh() {
        return this.bQg;
    }

    public final int abi() {
        return this.bQj;
    }

    public final String abj() {
        return this.bBs;
    }

    public final float abk() {
        return this.bQk;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long getElapsedRealtime() {
        return this.bQi;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final int getEventType() {
        return this.bQb;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long getTimeMillis() {
        return this.bQa;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getTimeMillis());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, abd(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 5, abg());
        com.google.android.gms.common.internal.safeparcel.c.b(parcel, 6, abh(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, getElapsedRealtime());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, abe(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 11, getEventType());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 12, aba(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 13, abj(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 14, abi());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 15, abk());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 16, abc());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 17, abf(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
