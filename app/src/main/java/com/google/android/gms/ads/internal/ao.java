package com.google.android.gms.ads.internal;

import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class ao implements View.OnTouchListener {
    private final /* synthetic */ am bDw;
    private final /* synthetic */ zzx bDx;

    ao(am amVar, zzx zzxVar) {
        this.bDw = amVar;
        this.bDx = zzxVar;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        this.bDx.recordClick();
        if (this.bDw.bDu == null) {
            return false;
        }
        this.bDw.bDu.zzpi();
        return false;
    }
}
