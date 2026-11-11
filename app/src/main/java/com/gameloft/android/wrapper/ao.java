package com.gameloft.android.wrapper;

/* JADX INFO: compiled from: TimeHackDetection.java */
/* JADX INFO: loaded from: classes.dex */
/* synthetic */ class ao {
    static final /* synthetic */ int[] bho = new int[aq.values().length];

    static {
        try {
            bho[aq.TIME.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            bho[aq.SYSTEM_CLOCK.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
    }
}
