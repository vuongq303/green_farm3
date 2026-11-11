package com.google.a.d;

import com.facebook.appevents.AppEventsConstants;
import java.util.Map;

/* JADX INFO: compiled from: UPCAWriter.java */
/* JADX INFO: loaded from: classes.dex */
public final class q implements com.google.a.h {
    private final k cci = new k();

    @Override // com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (aVar != com.google.a.a.UPC_A) {
            throw new IllegalArgumentException("Can only encode UPC-A, but got " + aVar);
        }
        return this.cci.a(iD(str), com.google.a.a.EAN_13, i, i2, map);
    }

    private static String iD(String str) {
        int length = str.length();
        if (length == 11) {
            int iCharAt = 0;
            for (int i = 0; i < 11; i++) {
                iCharAt += (i % 2 == 0 ? 3 : 1) * (str.charAt(i) - '0');
            }
            str = str + ((1000 - iCharAt) % 10);
        } else if (length != 12) {
            throw new IllegalArgumentException("Requested contents should be 11 or 12 digits long, but got " + str.length());
        }
        return AppEventsConstants.EVENT_PARAM_VALUE_NO + str;
    }
}
