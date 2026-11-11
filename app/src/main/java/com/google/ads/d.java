package com.google.ads;

import com.gameloft.adsmanager.JavaUtils;
import com.google.android.gms.ads.AdSize;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class d {
    public static final d byP = new d(-1, -2, "mb");
    public static final d byQ = new d(JavaUtils.Constants.BANNER_WIDTH_DP, 50, "mb");
    public static final d byR = new d(JavaUtils.Constants.NATIVE_HEIGHT_DP_300, 250, "as");
    public static final d byS = new d(468, 60, "as");
    public static final d byT = new d(JavaUtils.Constants.TABLET_WIDTH_DP, 90, "as");
    public static final d byU = new d(160, 600, "as");
    private final AdSize byO;

    private d(int i, int i2, String str) {
        this(new AdSize(i, i2));
    }

    public d(AdSize adSize) {
        this.byO = adSize;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof d) {
            return this.byO.equals(((d) obj).byO);
        }
        return false;
    }

    public final int getHeight() {
        return this.byO.getHeight();
    }

    public final int getWidth() {
        return this.byO.getWidth();
    }

    public final int hashCode() {
        return this.byO.hashCode();
    }

    public final String toString() {
        return this.byO.toString();
    }
}
