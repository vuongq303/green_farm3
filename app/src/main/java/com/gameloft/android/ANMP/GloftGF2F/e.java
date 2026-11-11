package com.gameloft.android.ANMP.GloftGF2F;

import android.util.DisplayMetrics;
import android.view.DisplayCutout;

/* JADX INFO: compiled from: CutoutHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class e {
    private DisplayCutout AI;
    private DisplayMetrics AJ = new DisplayMetrics();
    private int[] AK = new int[2];
    Start AH = Start.bcR;

    public e(DisplayCutout displayCutout) {
        this.AH.getWindowManager().getDefaultDisplay().getMetrics(this.AJ);
        this.AI = displayCutout;
    }
}
