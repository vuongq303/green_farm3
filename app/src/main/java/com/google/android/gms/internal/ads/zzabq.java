package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzabq extends zzabn {
    private Object zzbzn;

    @GuardedBy("mPopupWindowLock")
    private PopupWindow zzbzo;

    @GuardedBy("mPopupWindowLock")
    private boolean zzbzp;

    zzabq(Context context, zzaji zzajiVar, zzaqw zzaqwVar, zzabm zzabmVar) {
        super(context, zzajiVar, zzaqwVar, zzabmVar);
        this.zzbzn = new Object();
        this.zzbzp = false;
    }

    private final void zznv() {
        synchronized (this.zzbzn) {
            this.zzbzp = true;
            if ((this.mContext instanceof Activity) && ((Activity) this.mContext).isDestroyed()) {
                this.zzbzo = null;
            }
            if (this.zzbzo != null) {
                if (this.zzbzo.isShowing()) {
                    this.zzbzo.dismiss();
                }
                this.zzbzo = null;
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzabf, com.google.android.gms.internal.ads.zzalc
    public final void cancel() {
        zznv();
        super.cancel();
    }

    @Override // com.google.android.gms.internal.ads.zzabn
    protected final void zznu() {
        Window window = this.mContext instanceof Activity ? ((Activity) this.mContext).getWindow() : null;
        if (window == null || window.getDecorView() == null || ((Activity) this.mContext).isDestroyed()) {
            return;
        }
        FrameLayout frameLayout = new FrameLayout(this.mContext);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        frameLayout.addView(this.zzbnd.getView(), -1, -1);
        synchronized (this.zzbzn) {
            if (this.zzbzp) {
                return;
            }
            this.zzbzo = new PopupWindow((View) frameLayout, 1, 1, false);
            this.zzbzo.setOutsideTouchable(true);
            this.zzbzo.setClippingEnabled(false);
            zzakb.zzck("Displaying the 1x1 popup off the screen.");
            try {
                this.zzbzo.showAtLocation(window.getDecorView(), 0, -1, -1);
            } catch (Exception e) {
                this.zzbzo = null;
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzabf
    protected final void zzz(int i) {
        zznv();
        super.zzz(i);
    }
}
