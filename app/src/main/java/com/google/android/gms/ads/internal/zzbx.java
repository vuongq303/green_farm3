package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ViewSwitcher;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzald;
import com.google.android.gms.internal.ads.zzamt;
import com.google.android.gms.internal.ads.zzaqw;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzbx extends ViewSwitcher {
    private final zzald bAH;
    private boolean bDq;
    private final zzamt zzaee;

    public zzbx(Context context, String str, String str2, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        super(context);
        this.bAH = new zzald(context);
        this.bAH.setAdUnitId(str);
        this.bAH.zzda(str2);
        this.bDq = true;
        if (context instanceof Activity) {
            this.zzaee = new zzamt((Activity) context, this, onGlobalLayoutListener, onScrollChangedListener);
        } else {
            this.zzaee = new zzamt(null, this, onGlobalLayoutListener, onScrollChangedListener);
        }
        this.zzaee.zzsc();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.zzaee != null) {
            this.zzaee.onAttachedToWindow();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.zzaee != null) {
            this.zzaee.onDetachedFromWindow();
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.bDq) {
            return false;
        }
        this.bAH.zze(motionEvent);
        return false;
    }

    @Override // android.widget.ViewAnimator, android.view.ViewGroup
    public final void removeAllViews() {
        int i = 0;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            KeyEvent.Callback childAt = getChildAt(i2);
            if (childAt != null && (childAt instanceof zzaqw)) {
                arrayList.add((zzaqw) childAt);
            }
        }
        super.removeAllViews();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zzaqw) obj).destroy();
        }
    }

    public final zzald zzfr() {
        return this.bAH;
    }

    public final void zzfs() {
        zzakb.v("Disable position monitoring on adFrame.");
        if (this.zzaee != null) {
            this.zzaee.zzsd();
        }
    }

    public final void zzft() {
        zzakb.v("Enable debug gesture detector on adFrame.");
        this.bDq = true;
    }

    public final void zzfu() {
        zzakb.v("Disable debug gesture detector on adFrame.");
        this.bDq = false;
    }
}
