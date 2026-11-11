package android.support.v4.c;

import android.os.Build;

/* JADX INFO: compiled from: BuildCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    public static boolean ax() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static boolean ay() {
        return Build.VERSION.CODENAME.equals("P");
    }
}
