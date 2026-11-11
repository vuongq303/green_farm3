package com.google.android.gms.signin;

import com.google.android.gms.common.api.j;

/* JADX INFO: loaded from: classes.dex */
public final class d implements j {
    public static final d bYk = new e().agh();
    private final boolean bGO;
    private final boolean bGQ;
    private final boolean bYl;
    private final boolean bYm;
    private final Long bYn;
    private final Long bYo;
    private final String zzw;
    private final String zzx;

    private d(boolean z, boolean z2, String str, boolean z3, String str2, boolean z4, Long l, Long l2) {
        this.bYl = z;
        this.bGO = z2;
        this.zzw = str;
        this.bGQ = z3;
        this.bYm = z4;
        this.zzx = str2;
        this.bYn = l;
        this.bYo = l2;
    }

    public final boolean XS() {
        return this.bGO;
    }

    public final String XT() {
        return this.zzw;
    }

    public final boolean Yj() {
        return this.bGQ;
    }

    public final String Yk() {
        return this.zzx;
    }

    public final boolean agd() {
        return this.bYl;
    }

    public final boolean age() {
        return this.bYm;
    }

    public final Long agf() {
        return this.bYn;
    }

    public final Long agg() {
        return this.bYo;
    }
}
