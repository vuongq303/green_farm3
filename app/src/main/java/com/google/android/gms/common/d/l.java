package com.google.android.gms.common.d;

import com.facebook.appevents.codeless.internal.Constants;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class l {
    private static Pattern bQz = null;

    public static int oe(int i) {
        if (i == -1) {
            return -1;
        }
        return i / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
    }
}
