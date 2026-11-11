package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzald;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class d extends RelativeLayout {
    private zzald bAH;
    boolean bAI;

    public d(Context context, String str, String str2) {
        super(context);
        this.bAH = new zzald(context, str);
        this.bAH.zzda(str2);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.bAI) {
            return false;
        }
        this.bAH.zze(motionEvent);
        return false;
    }
}
