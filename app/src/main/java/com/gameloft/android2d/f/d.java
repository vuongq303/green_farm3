package com.gameloft.android2d.f;

import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: SystemKeyboard.java */
/* JADX INFO: loaded from: classes.dex */
public class d {
    public static InputMethodManager bsa;
    static String brX = "";
    public static b brY = null;
    public static FrameLayout.LayoutParams brZ = new FrameLayout.LayoutParams(1, 1);
    public static boolean bsb = false;
    static int bsc = 0;
    static int bsd = 0;

    public static void VA() {
        bsd = 2;
        mX(0);
    }

    public static void mX(int i) {
        brX = a.Dy();
        au.getActivity().runOnUiThread(new e(i));
    }

    public static String VB() {
        return brX;
    }

    public static boolean isRunning() {
        return bsb;
    }
}
