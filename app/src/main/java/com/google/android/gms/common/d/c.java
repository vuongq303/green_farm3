package com.google.android.gms.common.d;

import android.util.Base64;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        return Base64.decode(str, 0);
    }

    public static byte[] ig(String str) {
        if (str == null) {
            return null;
        }
        return Base64.decode(str, 10);
    }

    public static String o(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 0);
    }

    public static String p(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 10);
    }

    public static String q(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 11);
    }
}
