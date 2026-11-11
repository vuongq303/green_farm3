package com.google.android.gms.common.c;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static b bOQ;

    public static synchronized b aaG() {
        if (bOQ == null) {
            bOQ = aaH();
        }
        return bOQ;
    }

    public static b aaH() {
        return new c();
    }
}
