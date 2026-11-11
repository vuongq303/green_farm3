package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.media.MediaPlayer;

/* JADX INFO: loaded from: classes.dex */
final class ae implements MediaPlayer.OnCompletionListener {
    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (w.Wd) {
            GLLib.b("Complete playing MediaPlayer with session id " + mediaPlayer.getAudioSessionId(), "GLLib_snd", 6);
        }
    }
}
