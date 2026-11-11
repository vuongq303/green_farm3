package com.gameloft.android.ANMP.GloftGF2F;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import javax.microedition.midlet.BaseActivity;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: loaded from: classes.dex */
public class Start extends BaseActivity {
    public static Start bcR = null;
    public static Start bcW;
    public e bcS = null;
    int bcT;
    int bcU;
    View bcV;

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        bcR = this;
        MIDlet.cnA = this;
        com.gameloft.android.wrapper.d.beG = "sensorLandscape";
        String str = "Phone Model: " + Build.MODEL;
        String str2 = "Phone SDK: " + Build.VERSION.SDK_INT;
        Display defaultDisplay = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        String str3 = "Phone Width: " + width;
        String str4 = "Phone Height: " + height;
        javax.microedition.lcdui.f.setScale(1.0f, 1.0f);
        com.gameloft.android.wrapper.d.beH = "Multiple_axis_auto_align".compareTo("Single_axis_auto_align") == 0;
        com.gameloft.android.wrapper.d.beI = "Multiple_axis_auto_align".compareTo("Multiple_axis_auto_align") == 0;
        javax.microedition.lcdui.f.cm(false);
        javax.microedition.lcdui.f.cn(false);
        javax.microedition.lcdui.f.pc(-7);
        javax.microedition.lcdui.f.pd(-8);
        MIDlet.cnD = false;
        MIDlet.cor = false;
        MIDlet.cnF = false;
        MIDlet.cnG = false;
        MIDlet.cnK = false;
        MIDlet.coq = true;
        MIDlet.cnH = true;
        MIDlet.cnJ = false;
        MIDlet.cnR = false;
        MIDlet.coL = 5;
        com.gameloft.android.wrapper.d.beL = false;
        com.gameloft.android.wrapper.d.beF = false;
        com.gameloft.android.wrapper.d.beP = false;
        com.gameloft.android.wrapper.d.beQ = false;
        com.gameloft.android.wrapper.d.bff = false;
        com.gameloft.android.wrapper.d.bfk = false;
        com.gameloft.android.wrapper.d.bfl = "sensorLandscape".compareTo("sensorLandscape") == 0;
        com.gameloft.android.wrapper.au.biF = 0;
        com.gameloft.android.wrapper.au.biG = 0;
        com.gameloft.android.wrapper.au.biI = 0;
        com.gameloft.android.wrapper.au.biL = 0;
        com.gameloft.android.wrapper.au.biM = 0;
        MIDlet.cnS = true;
        MIDlet.coR = false;
        MIDlet.cnY = true;
        MIDlet.cnZ = "USE_IAP_GOOGLE_BILLING";
        MIDlet.cnT = false;
        MIDlet.cnU = false;
        MIDlet.cnV = false;
        MIDlet.coh = "";
        MIDlet.cnW = 1;
        MIDlet.coa = false;
        MIDlet.cob = false;
        MIDlet.coc = true;
        MIDlet.cod = true;
        MIDlet.coe = true;
        MIDlet.cof = false;
        com.gameloft.android.wrapper.d.beN = false;
        com.gameloft.android.wrapper.d.beO = false;
        MIDlet.cog = false;
        MIDlet.cnO = false;
        MIDlet.cnP = true;
        com.gameloft.android.wrapper.d.beR = "ANMP";
        com.gameloft.android.wrapper.d.beS = false;
        com.gameloft.android.wrapper.d.beT = false;
        com.gameloft.android.wrapper.d.beU = "LBCL";
        com.gameloft.android.wrapper.d.beV = "BFLL";
        com.gameloft.android.wrapper.d.beW = "BFL4";
        com.gameloft.android.wrapper.d.beX = false;
        com.gameloft.android.wrapper.d.beY = false;
        com.gameloft.android.wrapper.d.beZ = "14.0";
        com.gameloft.android.wrapper.d.bfa = true;
        MIDlet.col = false;
        MIDlet.f24com = false;
        MIDlet.cnL = true;
        com.gameloft.android.wrapper.d.beR = com.gameloft.android.wrapper.d.beR;
        MIDlet.VERSION = "44220";
        MIDlet.cnN = "4.4.2";
        com.gameloft.android.wrapper.d.beJ = 2;
        com.gameloft.android.wrapper.d.bfb = false;
        com.gameloft.android.wrapper.d.bfc = false;
        com.gameloft.android.wrapper.d.bfd = false;
        com.gameloft.android.wrapper.d.bfe = false;
        com.gameloft.android.wrapper.d.beD = true;
        com.gameloft.android.wrapper.d.bfg = 20;
        MIDlet.coI = false;
        com.gameloft.android.wrapper.d.bfm = 0;
        com.gameloft.android.wrapper.d.bfn = 0;
        if (com.gameloft.android.wrapper.au.u(this, "")) {
            super.onCreate(bundle);
            return;
        }
        super.onCreate(bundle);
        String str5 = " Phone model " + Build.MODEL;
        String str6 = " Phone sdk " + Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= 19) {
            com.gameloft.android.wrapper.d.beM = true;
        }
        com.gameloft.android.wrapper.z.k(this);
        MIDlet.cos = true;
        com.gameloft.android.wrapper.d.beK = false;
        com.gameloft.android.wrapper.d.bfi = "";
        com.gameloft.android.wrapper.d.bfj = "";
        com.gameloft.android.wrapper.d.bfh = false;
        Nh();
        if (!MIDlet.cos) {
        }
        Pt.hb().init();
    }

    void Nh() {
        LinearLayout linearLayout = new LinearLayout(this);
        bcW = this;
        this.bcV = new TextView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        linearLayout.addView(this.bcV, new FrameLayout.LayoutParams(layoutParams));
        linearLayout.setBackgroundColor(Color.parseColor("#FF000000"));
        this.bcV.setBackgroundColor(Color.parseColor("#FF000000"));
        linearLayout.setOrientation(0);
        linearLayout.setLayoutParams(layoutParams);
        setContentView(linearLayout);
        if (Build.VERSION.SDK_INT >= 28) {
            linearLayout.setOnApplyWindowInsetsListener(new bs(this));
        }
        this.bcV.post(new bt(this));
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
        }
        if (this.bcT != 0 && this.bcU != 0) {
            if (!MIDlet.cos) {
            }
            if (MIDlet.coi && !isFinishing()) {
                com.gameloft.android.wrapper.ae.bfS = 800;
                com.gameloft.android.wrapper.ae.bfT = 480;
                com.gameloft.android.wrapper.ae.bfU = "GloftGF2F";
                com.gameloft.android.wrapper.ae.bfZ = true;
                new com.gameloft.android.wrapper.ae(this, 0.01f, "".split(","), "".split(","), "com.gameloft.android.ANMP.GloftGF2F");
                com.gameloft.android.wrapper.ae.a(getPackageManager(), this.bcT, this.bcU);
            }
        }
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!MIDlet.cos) {
        }
        if (MIDlet.coi) {
            if (i == 274) {
                setResult(274);
                finishActivity(274);
            }
            finish();
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    protected void onDestroy() {
        if (!MIDlet.cos) {
        }
        if (MIDlet.coi && MIDlet.cos) {
            setResult(274);
            finishActivity(274);
            if (bx.ND() != null) {
                bx.ND().NF();
            }
            System.exit(0);
        }
        super.onDestroy();
    }
}
