package com.google.android.gms.ads.formats;

import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.internal.ads.zzadh;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class NativeAdOptions {
    public static final int ADCHOICES_BOTTOM_LEFT = 3;
    public static final int ADCHOICES_BOTTOM_RIGHT = 2;
    public static final int ADCHOICES_TOP_LEFT = 0;
    public static final int ADCHOICES_TOP_RIGHT = 1;
    public static final int ORIENTATION_ANY = 0;
    public static final int ORIENTATION_LANDSCAPE = 2;
    public static final int ORIENTATION_PORTRAIT = 1;
    private final int bzA;
    private final boolean bzB;
    private final int bzC;
    private final boolean bzz;
    private final VideoOptions zzvg;

    public @interface AdChoicesPlacement {
    }

    public final class Builder {
        private VideoOptions zzvg;
        private boolean bzz = false;
        private int bzA = -1;
        private boolean bzB = false;
        private int bzC = 1;

        public final NativeAdOptions build() {
            return new NativeAdOptions(this);
        }

        public final Builder setAdChoicesPlacement(@AdChoicesPlacement int i) {
            this.bzC = i;
            return this;
        }

        public final Builder setImageOrientation(int i) {
            this.bzA = i;
            return this;
        }

        public final Builder setRequestMultipleImages(boolean z) {
            this.bzB = z;
            return this;
        }

        public final Builder setReturnUrlsForImageAssets(boolean z) {
            this.bzz = z;
            return this;
        }

        public final Builder setVideoOptions(VideoOptions videoOptions) {
            this.zzvg = videoOptions;
            return this;
        }
    }

    private NativeAdOptions(Builder builder) {
        this.bzz = builder.bzz;
        this.bzA = builder.bzA;
        this.bzB = builder.bzB;
        this.bzC = builder.bzC;
        this.zzvg = builder.zzvg;
    }

    public final int getAdChoicesPlacement() {
        return this.bzC;
    }

    public final int getImageOrientation() {
        return this.bzA;
    }

    public final VideoOptions getVideoOptions() {
        return this.zzvg;
    }

    public final boolean shouldRequestMultipleImages() {
        return this.bzB;
    }

    public final boolean shouldReturnUrlsForImageAssets() {
        return this.bzz;
    }
}
