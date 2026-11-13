package com.quanhoang.greenfarm3.S800x480

import android.media.SoundPool

class ad : SoundPool.OnLoadCompleteListener {
    override fun onLoadComplete(soundPool: SoundPool?, sampleId: Int, status: Int) {
        var soundIndex = -1
        var index = 0
        while (index < ac.ma().size) {
            if (ac.ma()[index] == sampleId) soundIndex = index
            index++
        }
        GameRuntime.c(status == 0, "Sound " + soundIndex + " loading ERROR!!!")
        if (w.Wd) GameRuntime.b("onLoadComplete for sound " + soundIndex + "; SoundPool " + soundPool + "; with state " + status, "GLLib_snd", 3)
        if (status == 0) {
            if (w.Wd) GameRuntime.b("Loading completed for sound " + soundIndex, "GLLib_snd", 3)
            ac.mb()[soundIndex] = true
        }
    }
}
