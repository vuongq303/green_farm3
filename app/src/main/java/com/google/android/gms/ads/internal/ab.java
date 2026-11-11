package com.google.android.gms.ads.internal;

import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class ab implements View.OnTouchListener {
    private final /* synthetic */ zzbp bCi;

    ab(zzbp zzbpVar) {
        this.bCi = zzbpVar;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.bCi.bCg == null) {
            return false;
        }
        this.bCi.bCg.zza(motionEvent);
        return false;
    }
}
