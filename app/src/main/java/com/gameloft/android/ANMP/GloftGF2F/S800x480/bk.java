package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
public class bk {
    private boolean ajA;
    private cf ajB;
    private cf ajC;
    long ajz;

    public bk() {
        init();
        r(0L);
    }

    public bk(long j) {
        init();
        r(j);
    }

    private void init() {
        this.ajC = new cf();
        this.ajB = new cf();
        this.ajA = false;
    }

    public long getValue() {
        long j = (this.ajB.get() ^ this.ajC.get()) ^ (-1);
        if (al.q(j) == this.ajz) {
            return j;
        }
        r(0L);
        this.ajA = true;
        return 0L;
    }

    public void r(long j) {
        this.ajC.set(bo.next());
        this.ajB.set(((-1) ^ j) ^ this.ajC.get());
        this.ajz = al.q(j);
    }
}
