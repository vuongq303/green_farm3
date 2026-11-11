package com.google.android.gms.games.video;

import android.os.Bundle;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private final boolean bXh;
    private final int bXi;
    private final int bXj;
    private final boolean bXk;
    private final boolean zzrs;

    private a(boolean z, int i, int i2, boolean z2, boolean z3) {
        bf.ca(VideoConfiguration.R(i, true));
        bf.ca(VideoConfiguration.Q(i2, true));
        this.bXh = z;
        this.bXi = i;
        this.bXj = i2;
        this.zzrs = z2;
        this.bXk = z3;
    }

    public static a z(Bundle bundle) {
        if (bundle == null || bundle.get("IsCapturing") == null) {
            return null;
        }
        return new a(bundle.getBoolean("IsCapturing", false), bundle.getInt("CaptureMode", -1), bundle.getInt("CaptureQuality", -1), bundle.getBoolean("IsOverlayVisible", false), bundle.getBoolean("IsPaused", false));
    }

    public final String toString() {
        return bd.C(this).c("IsCapturing", Boolean.valueOf(this.bXh)).c("CaptureMode", Integer.valueOf(this.bXi)).c("CaptureQuality", Integer.valueOf(this.bXj)).c("IsOverlayVisible", Boolean.valueOf(this.zzrs)).c("IsPaused", Boolean.valueOf(this.bXk)).toString();
    }
}
