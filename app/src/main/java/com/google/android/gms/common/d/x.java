package com.google.android.gms.common.d;

import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class x {
    private static final Pattern bQJ = Pattern.compile("\\$\\{(.*?)\\}");

    public static String il(String str) {
        return str == null ? "" : str;
    }

    public static boolean im(String str) {
        return str == null || str.trim().isEmpty();
    }
}
