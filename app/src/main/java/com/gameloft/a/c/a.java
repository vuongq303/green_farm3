package com.gameloft.a.c;

import android.view.Display;
import android.view.WindowManager;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: PopUpsControl.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private static a bxC;
    static String bxY = "config.json";
    static String[] bxZ = {"left", "leftOffset", "right", "rightOffset", "top", "topOffset", "bottom", "bottomOffset", "aspectRatio", "keepAspectRatioUsingWidth", "keepAspectRatioUsingHeight"};
    private com.gameloft.a.c.a.b bxK;
    private com.gameloft.a.c.a.b bxL;
    private int bxM;
    private int bxN;
    final String bxD = "xpromo:";
    final String bxE = "exit:";
    final String bxF = "goto:";
    final String bxG = "autoexit:";
    private String bxH = "";
    private final int bxQ = 51853;
    private final int bxR = 51854;
    private final int bxS = 171435;
    private final int bxT = 52453;
    private final int bxU = 52454;
    private final int bxV = 52455;
    private final int bxW = 176673;
    private final int bxX = 172309;
    private boolean bxB = false;
    private boolean bxO = false;
    private String bxP = "";
    private d bxJ = new d(this);
    private e bxI = new e();

    a() {
        Display defaultDisplay = ((WindowManager) au.getContext().getSystemService("window")).getDefaultDisplay();
        co(defaultDisplay.getWidth(), defaultDisplay.getHeight());
        b(new com.gameloft.a.c.a.b());
        a(new com.gameloft.a.c.a.b());
    }

    public static a WB() {
        if (bxC == null) {
            bxC = new a();
        }
        return bxC;
    }

    public void co(int i, int i2) {
        this.bxM = i;
        this.bxN = i2;
    }

    public void a(com.gameloft.a.c.a.b bVar) {
        this.bxK = bVar;
    }

    public void b(com.gameloft.a.c.a.b bVar) {
        this.bxL = bVar;
    }

    public boolean WC() {
        return !this.bxJ.byi.isEmpty();
    }

    public boolean hw(String str) {
        if (!this.bxI.hy(str)) {
            return false;
        }
        return true;
    }

    public boolean hx(String str) {
        return com.gameloft.a.c.a.a.ar("popup_lib_wd", str);
    }
}
