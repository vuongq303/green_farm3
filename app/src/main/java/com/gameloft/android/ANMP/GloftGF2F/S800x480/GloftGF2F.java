package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.view.KeyEvent;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: loaded from: classes.dex */
public class GloftGF2F extends MIDlet {
    public static ci acw;
    public static GloftGF2F acx;

    public GloftGF2F() {
        acx = this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && acw != null
                && LanguageRestartController.menuKeyDown()) return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && acw != null
                && LanguageRestartController.menuKeyUp()) return true;
        return super.onKeyUp(keyCode, event);
    }

    @Override // javax.microedition.midlet.MIDlet
    public synchronized void mj() {
        if (acw == null) {
            try {
                ProgressSaveController.initialize();
            } catch (RuntimeException failure) {
                ProgressSaveController.showStartupError();
                return;
            }
            acw = new ci(this, javax.microedition.lcdui.r.b(this));
            acw.Init();
            try {
                acx.jf("MIDlet://stayAwake");
            } catch (Exception e) {
            }
        }
    }

    @Override // javax.microedition.midlet.MIDlet
    public void mk() {
        if (acw != null) {
            acw.Pause();
        }
    }

    @Override // javax.microedition.midlet.MIDlet
    public void al(boolean z) {
        if (acw != null) {
            ci ciVar = acw;
            ci.HH();
            ci ciVar2 = acw;
            ci.EZ();
            ci ciVar3 = acw;
            ci.Ms();
            ci.rE();
            if (!ProgressSaveController.flushForLifecycle()) return;
            ci ciVar4 = acw;
            GLLib.be("GLLib.quit");
            GLLib.Qn = -1;
        }
    }
}
