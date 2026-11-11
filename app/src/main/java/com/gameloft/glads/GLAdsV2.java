package com.gameloft.glads;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class GLAdsV2 {
    public static void SetParentView(ViewGroup viewGroup) {
        AndroidUtils.SetParentView(viewGroup);
        OmSDK.StartAnalytics();
        TopLayer.SetContainer(viewGroup);
    }

    public static void SetActivity(Activity activity) {
        AndroidUtils.SetActivity(activity);
        MoatSDK.StartAnalytics();
    }

    public static boolean onActivityResult(int i, int i2, Intent intent) {
        return WebViewFileChooser.onActivityResult(i, i2, intent);
    }
}
