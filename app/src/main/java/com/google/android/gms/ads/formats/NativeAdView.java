package com.google.android.gms.ads.formats;

import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.c.d;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzqa;

/* JADX INFO: loaded from: classes.dex */
public class NativeAdView extends FrameLayout {
    private final zzqa bzD;
    private final FrameLayout zzvh;

    public NativeAdView(Context context) {
        super(context);
        this.zzvh = av(context);
        this.bzD = WK();
    }

    public NativeAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.zzvh = av(context);
        this.bzD = WK();
    }

    public NativeAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzvh = av(context);
        this.bzD = WK();
    }

    public NativeAdView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.zzvh = av(context);
        this.bzD = WK();
    }

    private final zzqa WK() {
        bf.d(this.zzvh, "createDelegate must be called after mOverlayFrame has been created");
        if (isInEditMode()) {
            return null;
        }
        return zzkb.zzig().zza(this.zzvh.getContext(), this, this.zzvh);
    }

    private final FrameLayout av(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(frameLayout);
        return frameLayout;
    }

    protected final void a(String str, View view) {
        try {
            this.bzD.zzb(str, d.F(view));
        } catch (RemoteException e) {
            zzane.zzb("Unable to call setAssetView on delegate", e);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        super.bringChildToFront(this.zzvh);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void bringChildToFront(View view) {
        super.bringChildToFront(view);
        if (this.zzvh != view) {
            super.bringChildToFront(this.zzvh);
        }
    }

    public void destroy() {
        try {
            this.bzD.destroy();
        } catch (RemoteException e) {
            zzane.zzb("Unable to destroy native ad view", e);
        }
    }

    public AdChoicesView getAdChoicesView() {
        View viewHD = hD(NativeAd.ASSET_ADCHOICES_CONTAINER_VIEW);
        if (viewHD instanceof AdChoicesView) {
            return (AdChoicesView) viewHD;
        }
        return null;
    }

    protected final View hD(String str) {
        try {
            com.google.android.gms.c.a aVarZzak = this.bzD.zzak(str);
            if (aVarZzak != null) {
                return (View) d.b(aVarZzak);
            }
        } catch (RemoteException e) {
            zzane.zzb("Unable to call getAssetView on delegate", e);
        }
        return null;
    }

    @Override // android.view.View
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (this.bzD != null) {
            try {
                this.bzD.zzb(d.F(view), i);
            } catch (RemoteException e) {
                zzane.zzb("Unable to call onVisibilityChanged on delegate", e);
            }
        }
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        super.removeAllViews();
        super.addView(this.zzvh);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        if (this.zzvh == view) {
            return;
        }
        super.removeView(view);
    }

    public void setAdChoicesView(AdChoicesView adChoicesView) {
        a(NativeAd.ASSET_ADCHOICES_CONTAINER_VIEW, adChoicesView);
    }

    public void setNativeAd(NativeAd nativeAd) {
        try {
            this.bzD.zza((com.google.android.gms.c.a) nativeAd.zzbe());
        } catch (RemoteException e) {
            zzane.zzb("Unable to call setNativeAd on delegate", e);
        }
    }
}
