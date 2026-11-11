package com.google.android.gms.games.a;

import com.google.android.gms.common.internal.bd;

/* JADX INFO: loaded from: classes.dex */
public final class p {
    public final String bVE;
    public final boolean bVF;
    public final long bVt;
    public final String bVz;

    public p(long j, String str, String str2, boolean z) {
        this.bVt = j;
        this.bVE = str;
        this.bVz = str2;
        this.bVF = z;
    }

    public final String toString() {
        return bd.C(this).c("RawScore", Long.valueOf(this.bVt)).c("FormattedScore", this.bVE).c("ScoreTag", this.bVz).c("NewBest", Boolean.valueOf(this.bVF)).toString();
    }
}
