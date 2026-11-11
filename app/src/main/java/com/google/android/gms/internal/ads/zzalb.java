package com.google.android.gms.internal.ads;

import android.content.Context;
import android.media.AudioManager;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzalb {
    private boolean zzcdt = false;
    private float zzcdn = 1.0f;

    public static float zzay(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (audioManager == null) {
            return 0.0f;
        }
        int streamMaxVolume = audioManager.getStreamMaxVolume(3);
        int streamVolume = audioManager.getStreamVolume(3);
        if (streamMaxVolume == 0) {
            return 0.0f;
        }
        return streamVolume / streamMaxVolume;
    }

    private final synchronized boolean zzrr() {
        return this.zzcdn >= 0.0f;
    }

    public final synchronized void setAppMuted(boolean z) {
        this.zzcdt = z;
    }

    public final synchronized void setAppVolume(float f) {
        this.zzcdn = f;
    }

    public final synchronized float zzdo() {
        return zzrr() ? this.zzcdn : 1.0f;
    }

    public final synchronized boolean zzdp() {
        return this.zzcdt;
    }
}
