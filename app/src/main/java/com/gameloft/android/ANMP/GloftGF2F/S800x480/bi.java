package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
public class bi {
    private boolean ajA;
    private ce ajx;
    private ce ajy;
    long ajz;

    public bi() {
        init();
        setValue(0);
    }

    public bi(int i) {
        init();
        setValue(i);
    }

    private void init() {
        this.ajy = new ce();
        this.ajx = new ce();
        this.ajA = false;
    }

    public int getValue() {
        int i = (this.ajx.get() ^ this.ajy.get()) ^ (-1);
        if (al.q(i) == this.ajz) {
            return i;
        }
        setValue(0);
        this.ajA = true;
        return 0;
    }

    public void setValue(int i) {
        this.ajy.set(bo.next());
        this.ajx.set((i ^ (-1)) ^ this.ajy.get());
        this.ajz = al.q(i);
    }
}
