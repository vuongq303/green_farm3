package com.gameloft.glads;

import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
public class VolumeObserver extends ContentObserver {
    AndroidAds parent;
    int previousVolume;

    public VolumeObserver(AndroidAds androidAds) {
        super(new Handler(AndroidUtils.GetContext().getMainLooper()));
        this.previousVolume = -1;
        this.parent = androidAds;
        AndroidUtils.GetContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this);
    }

    int GetSoundVolume() {
        return ((AudioManager) AndroidUtils.GetContext().getSystemService("audio")).getStreamVolume(3);
    }

    @Override // android.database.ContentObserver
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        int iGetSoundVolume = GetSoundVolume();
        if (iGetSoundVolume != this.previousVolume) {
            this.parent.OnVolumeChanged();
            this.previousVolume = iGetSoundVolume;
        }
    }

    public void Release() {
        AndroidUtils.GetContext().getContentResolver().unregisterContentObserver(this);
    }
}
