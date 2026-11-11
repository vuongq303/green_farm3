package com.gameloft.android.ANMP.GloftGF2F;

import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: Start.java */
/* JADX INFO: loaded from: classes.dex */
class bt implements Runnable {
    final /* synthetic */ Start bcX;

    bt(Start start) {
        this.bcX = start;
    }

    @Override // java.lang.Runnable
    public void run() {
        Display defaultDisplay = ((WindowManager) Start.bcW.getApplicationContext().getSystemService("window")).getDefaultDisplay();
        if (com.gameloft.android.wrapper.d.beM && Build.VERSION.SDK_INT < 24 && !Build.MODEL.equals("SM-N915FY") && com.gameloft.android.wrapper.d.bfm == 0 && com.gameloft.android.wrapper.d.bfn == 0) {
            Point point = new Point();
            try {
                Method declaredMethod = Class.forName("android.view.Display").getDeclaredMethod("getRealSize", Point.class);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                declaredMethod.invoke(defaultDisplay, point);
            } catch (Exception e) {
            }
            this.bcX.bcT = point.x;
            this.bcX.bcU = point.y;
        } else {
            this.bcX.bcT = this.bcX.bcV.getWidth();
            this.bcX.bcU = this.bcX.bcV.getHeight();
        }
        String str = "Orientation:" + com.gameloft.android.wrapper.d.beG;
        if ((com.gameloft.android.wrapper.d.beG.compareTo("sensorLandscape") == 0 && this.bcX.bcT < this.bcX.bcU) || (com.gameloft.android.wrapper.d.beG.compareTo("portrait") == 0 && this.bcX.bcT > this.bcX.bcU)) {
            int i = this.bcX.bcT;
            this.bcX.bcT = this.bcX.bcU;
            this.bcX.bcU = i;
        }
        String str2 = "display_w:" + this.bcX.bcT + " display_h:" + this.bcX.bcU;
        Start.bcW.runOnUiThread(new bu(this));
    }
}
