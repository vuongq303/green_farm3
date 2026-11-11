package com.gameloft.android2d.iap.utils;

import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: compiled from: ConnectionTimer.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends TimerTask {
    private Timer bnd;
    private g bne;

    public void a(long j, g gVar) {
        this.bne = gVar;
        this.bnd = new Timer();
        this.bnd.schedule(new c(), j);
    }

    public void stop() {
        if (this.bnd != null) {
            this.bnd.cancel();
        }
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (this.bne != null) {
            this.bne.cancel();
            this.bne.BT = true;
            this.bne.BR = false;
            this.bne = null;
        }
    }
}
