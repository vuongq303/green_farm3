package com.google.android.gms.internal.ads;

import com.google.ads.b;
import com.google.ads.c;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class zzzd {
    private static final /* synthetic */ int[] zzbvf;
    static final /* synthetic */ int[] zzbvg = new int[b.values().length];

    static {
        try {
            zzbvg[b.INTERNAL_ERROR.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            zzbvg[b.INVALID_REQUEST.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            zzbvg[b.NETWORK_ERROR.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            zzbvg[b.NO_FILL.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        zzbvf = new int[c.values().length];
        try {
            zzbvf[c.FEMALE.ordinal()] = 1;
        } catch (NoSuchFieldError e5) {
        }
        try {
            zzbvf[c.MALE.ordinal()] = 2;
        } catch (NoSuchFieldError e6) {
        }
        try {
            zzbvf[c.UNKNOWN.ordinal()] = 3;
        } catch (NoSuchFieldError e7) {
        }
    }
}
