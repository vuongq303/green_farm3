package javax.microedition.midlet;

import android.media.AudioManager;

/* JADX INFO: compiled from: BaseActivity.java */
/* JADX INFO: loaded from: classes.dex */
class e implements AudioManager.OnAudioFocusChangeListener {
    final /* synthetic */ BaseActivity cny;

    e(BaseActivity baseActivity) {
        this.cny = baseActivity;
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(int i) {
    }
}
