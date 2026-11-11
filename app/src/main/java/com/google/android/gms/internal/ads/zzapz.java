package com.google.android.gms.internal.ads;

import android.content.Context;
import android.media.AudioManager;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzapz implements AudioManager.OnAudioFocusChangeListener {
    private final AudioManager mAudioManager;
    private boolean zzcxs;
    private final zzaqa zzdaq;
    private boolean zzdar;
    private boolean zzdas;
    private float zzdat = 1.0f;

    public zzapz(Context context, zzaqa zzaqaVar) {
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.zzdaq = zzaqaVar;
    }

    private final void zztw() {
        boolean z = this.zzcxs && !this.zzdas && this.zzdat > 0.0f;
        if (z && !this.zzdar) {
            if (this.mAudioManager != null && !this.zzdar) {
                this.zzdar = this.mAudioManager.requestAudioFocus(this, 3, 2) == 1;
            }
            this.zzdaq.zzst();
            return;
        }
        if (z || !this.zzdar) {
            return;
        }
        if (this.mAudioManager != null && this.zzdar) {
            this.zzdar = this.mAudioManager.abandonAudioFocus(this) == 0;
        }
        this.zzdaq.zzst();
    }

    public final float getVolume() {
        float f = this.zzdas ? 0.0f : this.zzdat;
        if (this.zzdar) {
            return f;
        }
        return 0.0f;
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public final void onAudioFocusChange(int i) {
        this.zzdar = i > 0;
        this.zzdaq.zzst();
    }

    public final void setMuted(boolean z) {
        this.zzdas = z;
        zztw();
    }

    public final void setVolume(float f) {
        this.zzdat = f;
        zztw();
    }

    public final void zztt() {
        this.zzcxs = true;
        zztw();
    }

    public final void zztu() {
        this.zzcxs = false;
        zztw();
    }
}
