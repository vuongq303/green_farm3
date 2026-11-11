package com.google.a.d;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.gameloft.adsmanager.JavaUtils;
import com.gameloft.glads.GLURLConnection;

/* JADX INFO: compiled from: Code93Reader.java */
/* JADX INFO: loaded from: classes.dex */
public final class h extends o {
    private static final char[] cbO = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
    static final int[] cbP;
    private static final int ccb;

    static {
        int[] iArr = {276, 328, 324, 322, 296, 292, 290, 336, 274, 266, 424, 420, 418, 404, 402, 394, 360, 356, 354, 308, 282, 344, 332, 326, JavaUtils.Constants.NATIVE_HEIGHT_DP_300, 278, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, GLURLConnection.HTTP_FOUND, 468, 466, FacebookRequestErrorClassification.ESC_APP_NOT_INSTALLED, 366, 374, 430, 294, 474, 470, 306, 350};
        cbP = iArr;
        ccb = iArr[47];
    }
}
