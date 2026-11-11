package com.gameloft.glads;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class AndroidAds {
    long parent;
    VolumeObserver volumeObserver;

    public static native void NativeOnVolumeChanged(long j);

    public AndroidAds(long j) {
        this.volumeObserver = null;
        this.parent = j;
        if (Build.VERSION.SDK_INT >= 19) {
            this.volumeObserver = new VolumeObserver(this);
        }
    }

    public void EnableWebContentsDebugging() {
    }

    public void OnVolumeChanged() {
        NativeOnVolumeChanged(this.parent);
    }

    public void Release() {
        if (this.volumeObserver != null) {
            this.volumeObserver.Release();
        }
    }
}
