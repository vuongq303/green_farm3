package com.google.android.gms.ads;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzjd;
import com.google.android.gms.internal.ads.zzly;

/* JADX INFO: loaded from: classes.dex */
class a extends ViewGroup {
    protected final zzly bzr;

    public a(Context context, int i) {
        super(context);
        this.bzr = new zzly(this, i);
    }

    public a(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.bzr = new zzly(this, attributeSet, false, i);
    }

    public a(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.bzr = new zzly(this, attributeSet, false, i2);
    }

    public void destroy() {
        this.bzr.destroy();
    }

    public AdListener getAdListener() {
        return this.bzr.getAdListener();
    }

    public AdSize getAdSize() {
        return this.bzr.getAdSize();
    }

    public String getAdUnitId() {
        return this.bzr.getAdUnitId();
    }

    public String getMediationAdapterClassName() {
        return this.bzr.getMediationAdapterClassName();
    }

    public boolean isLoading() {
        return this.bzr.isLoading();
    }

    public void loadAd(AdRequest adRequest) {
        this.bzr.zza(adRequest.zzay());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getVisibility() == 8) {
            return;
        }
        int measuredWidth = childAt.getMeasuredWidth();
        int measuredHeight = childAt.getMeasuredHeight();
        int i5 = ((i3 - i) - measuredWidth) / 2;
        int i6 = ((i4 - i2) - measuredHeight) / 2;
        childAt.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int heightInPixels;
        int i3;
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getVisibility() == 8) {
            AdSize adSize = null;
            try {
                adSize = getAdSize();
            } catch (NullPointerException e) {
                zzane.zzb("Unable to retrieve ad size.", e);
            }
            if (adSize != null) {
                Context context = getContext();
                int widthInPixels = adSize.getWidthInPixels(context);
                heightInPixels = adSize.getHeightInPixels(context);
                i3 = widthInPixels;
            } else {
                heightInPixels = 0;
                i3 = 0;
            }
        } else {
            measureChild(childAt, i, i2);
            int measuredWidth = childAt.getMeasuredWidth();
            heightInPixels = childAt.getMeasuredHeight();
            i3 = measuredWidth;
        }
        setMeasuredDimension(View.resolveSize(Math.max(i3, getSuggestedMinimumWidth()), i), View.resolveSize(Math.max(heightInPixels, getSuggestedMinimumHeight()), i2));
    }

    public void pause() {
        this.bzr.pause();
    }

    public void resume() {
        this.bzr.resume();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAdListener(AdListener adListener) {
        this.bzr.setAdListener(adListener);
        if (adListener == 0) {
            this.bzr.zza((zzjd) null);
            this.bzr.setAppEventListener(null);
            return;
        }
        if (adListener instanceof zzjd) {
            this.bzr.zza((zzjd) adListener);
        }
        if (adListener instanceof AppEventListener) {
            this.bzr.setAppEventListener((AppEventListener) adListener);
        }
    }

    public void setAdSize(AdSize adSize) {
        this.bzr.setAdSizes(adSize);
    }

    public void setAdUnitId(String str) {
        this.bzr.setAdUnitId(str);
    }
}
