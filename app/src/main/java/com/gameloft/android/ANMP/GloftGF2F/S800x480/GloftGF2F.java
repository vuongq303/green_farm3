package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import javax.microedition.midlet.MIDlet;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class GloftGF2F extends MIDlet {
    public static ci acw;
    public static GloftGF2F acx;

    public GloftGF2F() {
        acx = this;
    }

    @Override // javax.microedition.midlet.MIDlet
    public void mj() {
        if (acw == null) {
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
    public void al(boolean z) throws JSONException {
        if (acw != null) {
            ci ciVar = acw;
            ci.HH();
            ci ciVar2 = acw;
            ci.EZ();
            ci ciVar3 = acw;
            ci.Ms();
            ci.rE();
            ci ciVar4 = acw;
            GLLib.be("GLLib.quit");
            GLLib.Qn = -1;
        }
    }
}
