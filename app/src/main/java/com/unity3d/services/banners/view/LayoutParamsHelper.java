package com.unity3d.services.banners.view;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: classes.dex */
class LayoutParamsHelper {
    LayoutParamsHelper() {
    }

    static ViewGroup.LayoutParams updateLayoutParamsForPosition(ViewGroup.LayoutParams layoutParams, BannerPosition bannerPosition) {
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            return updateFrameLayoutParamsForPosition((FrameLayout.LayoutParams) layoutParams, bannerPosition);
        }
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            return updateRelativeLayoutParamsForPosition((RelativeLayout.LayoutParams) layoutParams, bannerPosition);
        }
        return layoutParams;
    }

    private static ViewGroup.LayoutParams updateRelativeLayoutParamsForPosition(RelativeLayout.LayoutParams layoutParams, BannerPosition bannerPosition) {
        return bannerPosition.addLayoutRules(layoutParams);
    }

    private static ViewGroup.LayoutParams updateFrameLayoutParamsForPosition(FrameLayout.LayoutParams layoutParams, BannerPosition bannerPosition) {
        layoutParams.gravity = bannerPosition.getGravity();
        return layoutParams;
    }
}
