package com.google.android.gms.common.api.a;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class r {

    @GuardedBy("sLock")
    private static r bJI;
    private static final Object sLock = new Object();
    private final String bJJ;
    private final Status bJK;
    private final boolean bJL;
    private final boolean bJM;

    r(Context context) {
        boolean z = true;
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(com.google.android.gms.common.p.common_google_play_services_unknown_issue));
        if (identifier != 0) {
            boolean z2 = resources.getInteger(identifier) != 0;
            this.bJM = z2 ? false : true;
            z = z2;
        } else {
            this.bJM = false;
        }
        this.bJL = z;
        String strAJ = com.google.android.gms.common.internal.bc.aJ(context);
        strAJ = strAJ == null ? new com.google.android.gms.common.internal.bj(context).getString("google_app_id") : strAJ;
        if (TextUtils.isEmpty(strAJ)) {
            this.bJK = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.bJJ = null;
        } else {
            this.bJJ = strAJ;
            this.bJK = Status.bIO;
        }
    }

    public static String Zh() {
        return hU("getGoogleAppId").bJJ;
    }

    public static boolean Zi() {
        return hU("isMeasurementExplicitlyDisabled").bJM;
    }

    public static Status aE(Context context) {
        Status status;
        com.google.android.gms.common.internal.bf.d(context, "Context must not be null.");
        synchronized (sLock) {
            if (bJI == null) {
                bJI = new r(context);
            }
            status = bJI.bJK;
        }
        return status;
    }

    private static r hU(String str) {
        r rVar;
        synchronized (sLock) {
            if (bJI == null) {
                throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 34).append("Initialize must be called before ").append(str).append(".").toString());
            }
            rVar = bJI;
        }
        return rVar;
    }
}
