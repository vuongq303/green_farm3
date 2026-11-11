package com.google.android.gms.internal.measurement;

import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.g;

/* JADX INFO: loaded from: classes.dex */
public final class zzas {
    public static final String VERSION = String.valueOf(g.GOOGLE_PLAY_SERVICES_VERSION_CODE / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS).replaceAll("(\\d+)(\\d)(\\d\\d)", "$1.$2.$3");
    public static final String zzvk;

    static {
        String strValueOf = String.valueOf(VERSION);
        zzvk = strValueOf.length() != 0 ? "ma".concat(strValueOf) : new String("ma");
    }
}
