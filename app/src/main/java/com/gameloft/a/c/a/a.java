package com.gameloft.a.c.a;

import com.gameloft.android.wrapper.au;
import java.io.File;

/* JADX INFO: compiled from: PopUpsUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String WD() {
        return au.getContext().getFilesDir().getAbsolutePath() + "/";
    }

    public static String aq(String str, String str2) {
        return str + "/" + hA(str2);
    }

    public static String hA(String str) {
        return String.valueOf(Math.abs(str.hashCode()));
    }

    public static boolean ar(String str, String str2) {
        return new File(WD() + aq("popup_lib_wd", str2) + "/index.html").exists();
    }
}
