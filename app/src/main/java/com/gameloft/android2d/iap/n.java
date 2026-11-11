package com.gameloft.android2d.iap;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class n extends Thread {
    final /* synthetic */ int bkS;
    final /* synthetic */ String bkT;
    final /* synthetic */ long bkU;

    n(int i, String str, long j) {
        this.bkS = i;
        this.bkT = str;
        this.bkU = j;
    }

    /* JADX WARN: Code duplicated, block: B:86:0x03d3  */
    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        String str;
        String str2;
        String strRP;
        String str3;
        int i;
        int i2;
        String strReplace;
        String strUg;
        String strSU;
        com.gameloft.android2d.iap.utils.r rVar = b.bjp.bjq.Qr().gm(b.bjp.bjv.toLowerCase()).get(Integer.valueOf(b.bjp.bju));
        com.gameloft.android2d.iap.a.a aVarSM = rVar.SM();
        String strRH = aVarSM.RH();
        String strRQ = aVarSM.RQ();
        String strRR = aVarSM.RR();
        String strUd = b.bjp.bjq.Qr().Ud();
        boolean zEE = b.eE(b.f(this.bkS, this.bkT));
        int i3 = com.b.b.cfu;
        int i4 = com.b.b.cfv;
        int i5 = com.b.b.cfw;
        com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgO);
        if (zEE) {
            strRH = com.gameloft.android2d.iap.utils.v.mI(com.b.d.ciJ);
            strRQ = com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfW);
            strRR = com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfT);
        }
        if (!b.bkj) {
            str = "";
            str2 = "";
            strRP = strRR;
            str3 = strRQ;
        } else {
            aVarSM.RL();
            String strRM = aVarSM.RM();
            String strRN = aVarSM.RN();
            String strRO = aVarSM.RO();
            str = strRN;
            str2 = strRM;
            strRP = aVarSM.RP();
            str3 = strRO;
        }
        String strReplace2 = strRH.replace("|", "").replace("\\n", System.getProperty("line.separator"));
        if (strReplace2 == null || str3 == null || strRP == null) {
            b.c(b.bjp.bju, b.bjp.bjv, 0L);
            return;
        }
        int iB = b.b(rVar);
        if (com.gameloft.android2d.iap.utils.v.getContext().getResources().getConfiguration().orientation != 1) {
            i = 7;
        } else {
            i = 10;
        }
        b.bjp.bjJ = null;
        b.bjp.bjJ = new javax.microedition.lcdui.q(b.bjp.mainActivity);
        b.bjp.bjJ.requestWindowFeature(1);
        b.bjp.bjJ.setContentView(iB);
        if (iB == com.b.c.cfG || iB == com.b.c.cfH) {
            TextView textView = (TextView) b.bjp.bjJ.findViewById(com.b.b.cfC);
            textView.setMaxLines(i);
            textView.setTextSize(16.0f);
            textView.setMovementMethod(ScrollingMovementMethod.getInstance());
            String strRK = aVarSM.RK();
            String strRJ = aVarSM.RJ();
            textView.setText(strRK);
            i2 = iB;
            strReplace = strRJ;
        } else if (zEE) {
            int i6 = com.b.c.cfI;
            b.bjp.bjJ.setContentView(i6);
            RadioButton radioButton = (RadioButton) b.bjp.bjJ.findViewById(i3);
            RadioButton radioButton2 = (RadioButton) b.bjp.bjJ.findViewById(i4);
            RadioButton radioButton3 = (RadioButton) b.bjp.bjJ.findViewById(i5);
            if (rVar.fF(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgP))) {
                radioButton2.setText("PayPal");
                radioButton2.setChecked(true);
            } else {
                radioButton2.setVisibility(8);
            }
            if (rVar.fF(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgN))) {
                radioButton3.setText("Amazon");
                radioButton3.setChecked(true);
            } else {
                radioButton3.setVisibility(8);
            }
            if (rVar.fF(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgO))) {
                radioButton.setText(com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfZ));
                radioButton.setChecked(true);
                i2 = i6;
                strReplace = strReplace2;
            } else {
                radioButton.setVisibility(8);
                i2 = i6;
                strReplace = strReplace2;
            }
        } else {
            i2 = iB;
            strReplace = strReplace2;
        }
        b.bjp.bjJ.setCancelable(false);
        b.bjp.bjJ.setTitle((CharSequence) null);
        Button button = (Button) b.bjp.bjJ.findViewById(com.b.b.button1);
        Button button2 = (Button) b.bjp.bjJ.findViewById(com.b.b.button2);
        button.setTextSize(16.0f);
        button2.setTextSize(16.0f);
        TextView textView2 = (TextView) b.bjp.bjJ.findViewById(com.b.b.cfB);
        LinearLayout linearLayout = (LinearLayout) b.bjp.bjJ.findViewById(com.b.b.cfy);
        if (b.getLanguage().equalsIgnoreCase("AR")) {
            textView2.setGravity(5);
        }
        int width = ((WindowManager) com.gameloft.android2d.iap.utils.v.getContext().getSystemService("window")).getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        if (width > 700 && com.gameloft.android2d.iap.utils.v.getContext().getResources().getConfiguration().orientation == 2) {
            if (i2 == com.b.c.cfJ || i2 == com.b.c.cfF || i2 == com.b.c.cfH) {
                layoutParams.width = (width * 90) / 100;
            } else {
                layoutParams.width = (width * 70) / 100;
            }
        }
        if (!zEE && (strSU = rVar.SU()) != null && !strSU.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO) && i2 != com.b.c.cfH && i2 != com.b.c.cfG) {
            String strRI = aVarSM.RI();
            if (!TextUtils.isEmpty(strRI)) {
                strReplace = strRI.replace("|", "");
            }
        }
        if (i2 == com.b.c.cfJ || i2 == com.b.c.cfF || i2 == com.b.c.cfH) {
            textView2.setMaxLines(5);
        } else {
            textView2.setMaxLines(i);
        }
        textView2.setTextSize(16.0f);
        textView2.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView2.setText(b.a(strReplace, rVar));
        button2.setText(str3);
        button.setText(strRP);
        button2.setTransformationMethod(null);
        button.setTransformationMethod(null);
        if (b.bkj) {
            b.ab(str, str2);
        }
        button2.setOnClickListener(new o(this, zEE, i3, i4, i5));
        button.setOnClickListener(new p(this));
        b.bjp.bjJ.setOnKeyListener(new q(this));
        if (i2 == com.b.c.cfJ || i2 == com.b.c.cfF || i2 == com.b.c.cfH) {
            Button button3 = (Button) b.bjp.bjJ.findViewById(com.b.b.cfz);
            String strSU2 = rVar.SU();
            if (strSU2 == null || strSU2.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                strUg = strUd;
            } else {
                strUg = b.bjp.bjq.Qr().Ug();
                if (TextUtils.isEmpty(strUg)) {
                    strUg = strUd;
                }
            }
            String strReplace3 = strUg.replace("|", "");
            button3.setTextSize(16.0f);
            button3.setText(strReplace3);
            button3.setTransformationMethod(null);
            b.bkP = false;
            button3.setOnClickListener(new r(this));
        }
    }
}
