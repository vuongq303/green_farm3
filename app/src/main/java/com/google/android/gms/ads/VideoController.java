package com.google.android.gms.ads;

import android.os.RemoteException;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzlo;
import com.google.android.gms.internal.ads.zzmt;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class VideoController {
    public static final int PLAYBACK_STATE_ENDED = 3;
    public static final int PLAYBACK_STATE_PAUSED = 2;
    public static final int PLAYBACK_STATE_PLAYING = 1;
    public static final int PLAYBACK_STATE_READY = 5;
    public static final int PLAYBACK_STATE_UNKNOWN = 0;

    @GuardedBy("mLock")
    private zzlo bzv;
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private VideoLifecycleCallbacks zzuy;

    public class VideoLifecycleCallbacks {
        public void onVideoEnd() {
        }

        public void onVideoMute(boolean z) {
        }

        public void onVideoPause() {
        }

        public void onVideoPlay() {
        }

        public void onVideoStart() {
        }
    }

    public final float getAspectRatio() {
        float aspectRatio = 0.0f;
        synchronized (this.mLock) {
            if (this.bzv != null) {
                try {
                    aspectRatio = this.bzv.getAspectRatio();
                } catch (RemoteException e) {
                    zzane.zzb("Unable to call getAspectRatio on video controller.", e);
                }
            }
        }
        return aspectRatio;
    }

    public final int getPlaybackState() {
        int playbackState = 0;
        synchronized (this.mLock) {
            if (this.bzv != null) {
                try {
                    playbackState = this.bzv.getPlaybackState();
                } catch (RemoteException e) {
                    zzane.zzb("Unable to call getPlaybackState on video controller.", e);
                }
            }
        }
        return playbackState;
    }

    public final VideoLifecycleCallbacks getVideoLifecycleCallbacks() {
        VideoLifecycleCallbacks videoLifecycleCallbacks;
        synchronized (this.mLock) {
            videoLifecycleCallbacks = this.zzuy;
        }
        return videoLifecycleCallbacks;
    }

    public final boolean hasVideoContent() {
        boolean z;
        synchronized (this.mLock) {
            z = this.bzv != null;
        }
        return z;
    }

    public final boolean isClickToExpandEnabled() {
        boolean zIsClickToExpandEnabled = false;
        synchronized (this.mLock) {
            if (this.bzv != null) {
                try {
                    zIsClickToExpandEnabled = this.bzv.isClickToExpandEnabled();
                } catch (RemoteException e) {
                    zzane.zzb("Unable to call isClickToExpandEnabled.", e);
                }
            }
        }
        return zIsClickToExpandEnabled;
    }

    public final boolean isCustomControlsEnabled() {
        boolean zIsCustomControlsEnabled = false;
        synchronized (this.mLock) {
            if (this.bzv != null) {
                try {
                    zIsCustomControlsEnabled = this.bzv.isCustomControlsEnabled();
                } catch (RemoteException e) {
                    zzane.zzb("Unable to call isUsingCustomPlayerControls.", e);
                }
            }
        }
        return zIsCustomControlsEnabled;
    }

    public final boolean isMuted() {
        boolean zIsMuted = true;
        synchronized (this.mLock) {
            if (this.bzv != null) {
                try {
                    zIsMuted = this.bzv.isMuted();
                } catch (RemoteException e) {
                    zzane.zzb("Unable to call isMuted on video controller.", e);
                }
            }
        }
        return zIsMuted;
    }

    public final void mute(boolean z) {
        synchronized (this.mLock) {
            if (this.bzv == null) {
                return;
            }
            try {
                this.bzv.mute(z);
            } catch (RemoteException e) {
                zzane.zzb("Unable to call mute on video controller.", e);
            }
        }
    }

    public final void pause() {
        synchronized (this.mLock) {
            if (this.bzv == null) {
                return;
            }
            try {
                this.bzv.pause();
            } catch (RemoteException e) {
                zzane.zzb("Unable to call pause on video controller.", e);
            }
        }
    }

    public final void play() {
        synchronized (this.mLock) {
            if (this.bzv == null) {
                return;
            }
            try {
                this.bzv.play();
            } catch (RemoteException e) {
                zzane.zzb("Unable to call play on video controller.", e);
            }
        }
    }

    public final void setVideoLifecycleCallbacks(VideoLifecycleCallbacks videoLifecycleCallbacks) {
        bf.d(videoLifecycleCallbacks, "VideoLifecycleCallbacks may not be null.");
        synchronized (this.mLock) {
            this.zzuy = videoLifecycleCallbacks;
            if (this.bzv == null) {
                return;
            }
            try {
                this.bzv.zza(new zzmt(videoLifecycleCallbacks));
            } catch (RemoteException e) {
                zzane.zzb("Unable to call setVideoLifecycleCallbacks on video controller.", e);
            }
        }
    }

    public final void zza(zzlo zzloVar) {
        synchronized (this.mLock) {
            this.bzv = zzloVar;
            if (this.zzuy != null) {
                setVideoLifecycleCallbacks(this.zzuy);
            }
        }
    }

    public final zzlo zzbc() {
        zzlo zzloVar;
        synchronized (this.mLock) {
            zzloVar = this.bzv;
        }
        return zzloVar;
    }
}
