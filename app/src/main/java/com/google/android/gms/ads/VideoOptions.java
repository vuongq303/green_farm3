package com.google.android.gms.ads;

import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzmu;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class VideoOptions {
    private final boolean bzw;
    private final boolean bzx;
    private final boolean bzy;

    public final class Builder {
        private boolean bzw = true;
        private boolean bzx = false;
        private boolean bzy = false;

        public final VideoOptions build() {
            return new VideoOptions(this);
        }

        public final Builder setClickToExpandRequested(boolean z) {
            this.bzy = z;
            return this;
        }

        public final Builder setCustomControlsRequested(boolean z) {
            this.bzx = z;
            return this;
        }

        public final Builder setStartMuted(boolean z) {
            this.bzw = z;
            return this;
        }
    }

    private VideoOptions(Builder builder) {
        this.bzw = builder.bzw;
        this.bzx = builder.bzx;
        this.bzy = builder.bzy;
    }

    public VideoOptions(zzmu zzmuVar) {
        this.bzw = zzmuVar.zzato;
        this.bzx = zzmuVar.zzatp;
        this.bzy = zzmuVar.zzatq;
    }

    public final boolean getClickToExpandRequested() {
        return this.bzy;
    }

    public final boolean getCustomControlsRequested() {
        return this.bzx;
    }

    public final boolean getStartMuted() {
        return this.bzw;
    }
}
