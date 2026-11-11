package com.moat.analytics.mobile.glft;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
public interface WebAdTracker {
    @Deprecated
    void setActivity(Activity activity);

    void startTracking();

    void stopTracking();
}
