package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class al {
    public static long q(long j) {
        long j2 = ((j >> 16) ^ j) * 73244475;
        long j3 = (j2 ^ (j2 >> 16)) * 73244475;
        return j3 ^ (j3 >> 16);
    }
}
