package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzks;
import com.google.android.gms.internal.ads.zzly;

/* JADX INFO: loaded from: classes.dex */
public final class PublisherAdView extends ViewGroup {
    private final zzly bzr;

    public PublisherAdView(Context context) {
        super(context);
        this.bzr = new zzly(this);
    }

    public PublisherAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.bzr = new zzly(this, attributeSet, true);
        bf.d(context, "Context cannot be null");
    }

    public PublisherAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.bzr = new zzly(this, attributeSet, true);
    }

    public final void destroy() {
        this.bzr.destroy();
    }

    public final AdListener getAdListener() {
        return this.bzr.getAdListener();
    }

    public final AdSize getAdSize() {
        return this.bzr.getAdSize();
    }

    public final AdSize[] getAdSizes() {
        return this.bzr.getAdSizes();
    }

    public final String getAdUnitId() {
        return this.bzr.getAdUnitId();
    }

    public final AppEventListener getAppEventListener() {
        return this.bzr.getAppEventListener();
    }

    public final String getMediationAdapterClassName() {
        return this.bzr.getMediationAdapterClassName();
    }

    public final OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener() {
        return this.bzr.getOnCustomRenderedAdLoadedListener();
    }

    public final VideoController getVideoController() {
        return this.bzr.getVideoController();
    }

    public final VideoOptions getVideoOptions() {
        return this.bzr.getVideoOptions();
    }

    public final boolean isLoading() {
        return this.bzr.isLoading();
    }

    public final void loadAd(PublisherAdRequest publisherAdRequest) {
        this.bzr.zza(publisherAdRequest.zzay());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
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
    protected final void onMeasure(int i, int i2) {
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

    public final void pause() {
        this.bzr.pause();
    }

    public final void recordManualImpression() {
        this.bzr.recordManualImpression();
    }

    public final void resume() {
        this.bzr.resume();
    }

    public final void setAdListener(AdListener adListener) {
        this.bzr.setAdListener(adListener);
    }

    public final void setAdSizes(AdSize... adSizeArr) {
        if (adSizeArr == null || adSizeArr.length <= 0) {
            throw new IllegalArgumentException("The supported ad sizes must contain at least one valid ad size.");
        }
        this.bzr.zza(adSizeArr);
    }

    public final void setAdUnitId(String str) {
        this.bzr.setAdUnitId(str);
    }

    public final void setAppEventListener(AppEventListener appEventListener) {
        this.bzr.setAppEventListener(appEventListener);
    }

    public final void setCorrelator(Correlator correlator) {
        this.bzr.setCorrelator(correlator);
    }

    public final void setManualImpressionsEnabled(boolean z) {
        this.bzr.setManualImpressionsEnabled(z);
    }

    public final void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.bzr.setOnCustomRenderedAdLoadedListener(onCustomRenderedAdLoadedListener);
    }

    public final void setVideoOptions(VideoOptions videoOptions) {
        this.bzr.setVideoOptions(videoOptions);
    }

    public final boolean zza(zzks zzksVar) {
        return this.bzr.zza(zzksVar);
    }
}
