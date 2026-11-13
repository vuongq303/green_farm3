package com.gameloft.android.ANMP.GloftGF2F.S800x480

import android.media.MediaPlayer

class ab : MediaPlayer.OnCompletionListener {
    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        if (w.Wd) GLLib.b("Complete playing MediaPlayer with session id " + mediaPlayer!!.audioSessionId, "GLLib_snd", 6)
    }
}
