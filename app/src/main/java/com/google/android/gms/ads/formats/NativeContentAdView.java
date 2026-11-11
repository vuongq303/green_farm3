package com.google.android.gms.ads.formats;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.gms.internal.ads.zzane;

/* JADX INFO: loaded from: classes.dex */
public final class NativeContentAdView extends NativeAdView {
    public NativeContentAdView(Context context) {
        super(context);
    }

    public NativeContentAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NativeContentAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public NativeContentAdView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public final View getAdvertiserView() {
        return super.hD(NativeContentAd.ASSET_ADVERTISER);
    }

    public final View getBodyView() {
        return super.hD(NativeContentAd.ASSET_BODY);
    }

    public final View getCallToActionView() {
        return super.hD(NativeContentAd.ASSET_CALL_TO_ACTION);
    }

    public final View getHeadlineView() {
        return super.hD(NativeContentAd.ASSET_HEADLINE);
    }

    public final View getImageView() {
        return super.hD(NativeContentAd.ASSET_IMAGE);
    }

    public final View getLogoView() {
        return super.hD(NativeContentAd.ASSET_LOGO);
    }

    public final MediaView getMediaView() {
        View viewHD = super.hD(NativeContentAd.ASSET_MEDIA_VIDEO);
        if (viewHD instanceof MediaView) {
            return (MediaView) viewHD;
        }
        if (viewHD != null) {
            zzane.zzck("View is not an instance of MediaView");
        }
        return null;
    }

    public final void setAdvertiserView(View view) {
        super.a(NativeContentAd.ASSET_ADVERTISER, view);
    }

    public final void setBodyView(View view) {
        super.a(NativeContentAd.ASSET_BODY, view);
    }

    public final void setCallToActionView(View view) {
        super.a(NativeContentAd.ASSET_CALL_TO_ACTION, view);
    }

    public final void setHeadlineView(View view) {
        super.a(NativeContentAd.ASSET_HEADLINE, view);
    }

    public final void setImageView(View view) {
        super.a(NativeContentAd.ASSET_IMAGE, view);
    }

    public final void setLogoView(View view) {
        super.a(NativeContentAd.ASSET_LOGO, view);
    }

    public final void setMediaView(MediaView mediaView) {
        super.a(NativeContentAd.ASSET_MEDIA_VIDEO, mediaView);
    }
}
