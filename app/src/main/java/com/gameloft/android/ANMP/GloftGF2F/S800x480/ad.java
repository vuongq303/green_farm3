package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.media.SoundPool;

/* JADX INFO: loaded from: classes.dex */
final class ad implements SoundPool.OnLoadCompleteListener {
    @Override // android.media.SoundPool.OnLoadCompleteListener
    public void onLoadComplete(SoundPool soundPool, int i, int i2) {
        int i3 = -1;
        for (int i4 = 0; i4 < ac.acd.length; i4++) {
            if (ac.acd[i4] == i) {
                i3 = i4;
            }
        }
        GLLib.c(i2 == 0, "Sound " + i3 + " loading ERROR!!!");
        if (w.Wd) {
            GLLib.b("onLoadComplete for sound " + i3 + "; SoundPool " + soundPool + "; with state " + i2, "GLLib_snd", 3);
        }
        if (i2 == 0) {
            if (w.Wd) {
                GLLib.b("Loading completed for sound " + i3, "GLLib_snd", 3);
            }
            ac.abl[i3] = true;
        }
    }
}
