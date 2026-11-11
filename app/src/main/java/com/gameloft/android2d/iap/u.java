package com.gameloft.android2d.iap;

import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class u extends Thread {
    final /* synthetic */ boolean bla;
    final /* synthetic */ int blb;
    final /* synthetic */ com.gameloft.android2d.iap.a.a blc;

    u(boolean z, int i, com.gameloft.android2d.iap.a.a aVar) {
        this.bla = z;
        this.blb = i;
        this.blc = aVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        String strMI = com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfY);
        String strMI2 = com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfX);
        String strMI3 = com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfV);
        if (this.bla && this.blb != 7) {
            strMI = com.gameloft.android2d.iap.utils.v.mI(com.b.d.ciI).replace("\\n", System.getProperty("line.separator"));
        }
        String strReplace = (b.bjp.bjG == null || !b.bjp.bjG.equalsIgnoreCase("th")) ? strMI : strMI.replace("|", "");
        int i = com.gameloft.android2d.iap.utils.v.getContext().getResources().getConfiguration().orientation == 1 ? 10 : 7;
        b.bjp.bjJ = null;
        b.bjp.bjJ = new javax.microedition.lcdui.q(b.bjp.mainActivity);
        b.bjp.bjJ.requestWindowFeature(1);
        b.bjp.bjJ.setContentView(com.b.c.cfE);
        b.bjp.bjJ.setCancelable(false);
        b.bjp.bjJ.setTitle((CharSequence) null);
        Button button = (Button) b.bjp.bjJ.findViewById(com.b.b.button1);
        Button button2 = (Button) b.bjp.bjJ.findViewById(com.b.b.button2);
        TextView textView = (TextView) b.bjp.bjJ.findViewById(com.b.b.cfB);
        LinearLayout linearLayout = (LinearLayout) b.bjp.bjJ.findViewById(com.b.b.cfy);
        int width = ((WindowManager) com.gameloft.android2d.iap.utils.v.getContext().getSystemService("window")).getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        if (width > 700 && com.gameloft.android2d.iap.utils.v.getContext().getResources().getConfiguration().orientation == 2) {
            layoutParams.width = (width * 70) / 100;
        }
        textView.setMaxLines(i);
        textView.setTextSize(16.0f);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.setText(strReplace);
        button2.setText(strMI2);
        button.setText(strMI3);
        button2.setTransformationMethod(null);
        button.setTransformationMethod(null);
        button2.setOnClickListener(new v(this));
        button.setOnClickListener(new w(this));
        b.bjp.bjJ.setOnKeyListener(new x(this));
    }
}
