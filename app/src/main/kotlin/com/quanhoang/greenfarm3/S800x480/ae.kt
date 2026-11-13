package com.quanhoang.greenfarm3.S800x480

import android.media.MediaPlayer

class ae : MediaPlayer.OnCompletionListener {
    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        if (w.Wd) GameRuntime.b("Complete playing MediaPlayer with session id " + mediaPlayer!!.audioSessionId, "GLLib_snd", 6)
    }
}
