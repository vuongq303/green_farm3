package com.google.android.gms.measurement;

import com.facebook.AccessToken;
import com.google.android.gms.internal.measurement.zzjv;
import com.google.firebase.analytics.FirebaseAnalytics;

/* JADX INFO: loaded from: classes.dex */
public final class g extends FirebaseAnalytics.UserProperty {
    public static final String[] bXI = {"firebase_last_notification", "first_open_time", "first_visit_time", "last_deep_link_referrer", AccessToken.USER_ID_KEY, "first_open_after_install", "lifetime_user_engagement"};
    public static final String[] bXJ = {"_ln", "_fot", "_fvt", "_ldl", "_id", "_fi", "_lte"};

    public static String iz(String str) {
        return zzjv.zza(str, bXI, bXJ);
    }
}
