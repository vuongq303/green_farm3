package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.d.f;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzarl extends zzlp {

    @GuardedBy("lock")
    private boolean zzatp;

    @GuardedBy("lock")
    private boolean zzatq;

    @GuardedBy("lock")
    private zzlr zzbuq;
    private final zzapw zzcyg;
    private final boolean zzded;
    private final boolean zzdee;
    private final float zzdef;

    @GuardedBy("lock")
    private int zzdeg;

    @GuardedBy("lock")
    private boolean zzdeh;

    @GuardedBy("lock")
    private float zzdej;

    @GuardedBy("lock")
    private float zzdek;
    private final Object lock = new Object();

    @GuardedBy("lock")
    private boolean zzdei = true;

    @GuardedBy("lock")
    private boolean zzato = true;

    public zzarl(zzapw zzapwVar, float f, boolean z, boolean z2) {
        this.zzcyg = zzapwVar;
        this.zzdef = f;
        this.zzded = z;
        this.zzdee = z2;
    }

    private final void zzf(String str, Map<String, String> map) {
        final HashMap map2 = map == null ? new HashMap() : new HashMap(map);
        map2.put(NativeProtocol.WEB_DIALOG_ACTION, str);
        zzaoe.zzcvy.execute(new Runnable(this, map2) { // from class: com.google.android.gms.internal.ads.zzarm
            private final Map zzbjl;
            private final zzarl zzdel;

            {
                this.zzdel = this;
                this.zzbjl = map2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzdel.zzo(this.zzbjl);
            }
        });
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final float getAspectRatio() {
        float f;
        synchronized (this.lock) {
            f = this.zzdek;
        }
        return f;
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final int getPlaybackState() {
        int i;
        synchronized (this.lock) {
            i = this.zzdeg;
        }
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0014  */
    @Override // com.google.android.gms.internal.ads.zzlo
    public final boolean isClickToExpandEnabled() {
        boolean z;
        boolean zIsCustomControlsEnabled = isCustomControlsEnabled();
        synchronized (this.lock) {
            if (zIsCustomControlsEnabled) {
                z = false;
            } else if (this.zzatq && this.zzdee) {
                z = true;
            } else {
                z = false;
            }
            throw th;
        }
        return z;
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final boolean isCustomControlsEnabled() {
        boolean z;
        synchronized (this.lock) {
            z = this.zzded && this.zzatp;
        }
        return z;
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final boolean isMuted() {
        boolean z;
        synchronized (this.lock) {
            z = this.zzdei;
        }
        return z;
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final void mute(boolean z) {
        zzf(z ? "mute" : "unmute", null);
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final void pause() {
        zzf("pause", null);
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final void play() {
        zzf("play", null);
    }

    public final void zza(float f, final int i, final boolean z, float f2) {
        final boolean z2;
        final int i2;
        synchronized (this.lock) {
            this.zzdej = f;
            z2 = this.zzdei;
            this.zzdei = z;
            i2 = this.zzdeg;
            this.zzdeg = i;
            float f3 = this.zzdek;
            this.zzdek = f2;
            if (Math.abs(this.zzdek - f3) > 1.0E-4f) {
                this.zzcyg.getView().invalidate();
            }
        }
        zzaoe.zzcvy.execute(new Runnable(this, i2, i, z2, z) { // from class: com.google.android.gms.internal.ads.zzarn
            private final int zzcsi;
            private final int zzcsj;
            private final boolean zzdcp;
            private final boolean zzdcq;
            private final zzarl zzdel;

            {
                this.zzdel = this;
                this.zzcsi = i2;
                this.zzcsj = i;
                this.zzdcp = z2;
                this.zzdcq = z;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzdel.zza(this.zzcsi, this.zzcsj, this.zzdcp, this.zzdcq);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:41:0x004c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:43:0x0053 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:45:0x005a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:64:0x0055 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:68:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:0x004e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    final /* synthetic */ void zza(int i, int i2, boolean z, boolean z2) {
        synchronized (this.lock) {
            boolean z3 = i != i2;
            boolean z4 = !this.zzdeh && i2 == 1;
            boolean z5 = z3 && i2 == 1;
            boolean z6 = z3 && i2 == 2;
            boolean z7 = z3 && i2 == 3;
            boolean z8 = z != z2;
            this.zzdeh = this.zzdeh || z4;
            if (this.zzbuq == null) {
                return;
            }
            if (!z4) {
                if (z5) {
                    if (z6) {
                        if (z7) {
                            if (z8) {
                                this.zzbuq.onVideoMute(z2);
                            }
                            return;
                        } else {
                            this.zzbuq.onVideoEnd();
                            if (z8) {
                                this.zzbuq.onVideoMute(z2);
                            }
                            return;
                        }
                    }
                    this.zzbuq.onVideoPause();
                    if (z7) {
                        if (z8) {
                            this.zzbuq.onVideoMute(z2);
                        }
                        return;
                    } else {
                        this.zzbuq.onVideoEnd();
                        if (z8) {
                            this.zzbuq.onVideoMute(z2);
                        }
                        return;
                    }
                }
                this.zzbuq.onVideoPlay();
                if (z6) {
                    if (z7) {
                        if (z8) {
                            this.zzbuq.onVideoMute(z2);
                        }
                        return;
                    } else {
                        this.zzbuq.onVideoEnd();
                        if (z8) {
                            this.zzbuq.onVideoMute(z2);
                        }
                        return;
                    }
                }
                this.zzbuq.onVideoPause();
                if (z7) {
                    if (z8) {
                        this.zzbuq.onVideoMute(z2);
                    }
                    return;
                } else {
                    this.zzbuq.onVideoEnd();
                    if (z8) {
                        this.zzbuq.onVideoMute(z2);
                    }
                    return;
                }
            }
            try {
                this.zzbuq.onVideoStart();
            } catch (RemoteException e) {
                zzakb.zzc("Unable to call onVideoStart()", e);
            }
            if (z5) {
                if (z6) {
                    if (z7) {
                        if (z8) {
                            this.zzbuq.onVideoMute(z2);
                        }
                        return;
                    } else {
                        this.zzbuq.onVideoEnd();
                        if (z8) {
                            this.zzbuq.onVideoMute(z2);
                        }
                        return;
                    }
                }
                this.zzbuq.onVideoPause();
                if (z7) {
                    if (z8) {
                        this.zzbuq.onVideoMute(z2);
                    }
                    return;
                } else {
                    this.zzbuq.onVideoEnd();
                    if (z8) {
                        this.zzbuq.onVideoMute(z2);
                    }
                    return;
                }
            }
            try {
                this.zzbuq.onVideoPlay();
            } catch (RemoteException e2) {
                zzakb.zzc("Unable to call onVideoPlay()", e2);
            }
            if (z6) {
                if (z7) {
                    if (z8) {
                        this.zzbuq.onVideoMute(z2);
                    }
                    return;
                } else {
                    this.zzbuq.onVideoEnd();
                    if (z8) {
                        this.zzbuq.onVideoMute(z2);
                    }
                    return;
                }
            }
            try {
                this.zzbuq.onVideoPause();
            } catch (RemoteException e3) {
                zzakb.zzc("Unable to call onVideoPause()", e3);
            }
            if (z7) {
                if (z8) {
                    this.zzbuq.onVideoMute(z2);
                }
                return;
            }
            try {
                this.zzbuq.onVideoEnd();
            } catch (RemoteException e4) {
                zzakb.zzc("Unable to call onVideoEnd()", e4);
            }
            if (z8) {
                try {
                    this.zzbuq.onVideoMute(z2);
                } catch (RemoteException e5) {
                    zzakb.zzc("Unable to call onVideoMute()", e5);
                }
            }
            return;
            throw th;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final void zza(zzlr zzlrVar) {
        synchronized (this.lock) {
            this.zzbuq = zzlrVar;
        }
    }

    public final void zzb(zzmu zzmuVar) {
        synchronized (this.lock) {
            this.zzato = zzmuVar.zzato;
            this.zzatp = zzmuVar.zzatp;
            this.zzatq = zzmuVar.zzatq;
        }
        zzf("initialState", f.a("muteStart", zzmuVar.zzato ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO, "customControlsRequested", zzmuVar.zzatp ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO, "clickToExpandRequested", zzmuVar.zzatq ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO));
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final float zzim() {
        return this.zzdef;
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final float zzin() {
        float f;
        synchronized (this.lock) {
            f = this.zzdej;
        }
        return f;
    }

    @Override // com.google.android.gms.internal.ads.zzlo
    public final zzlr zzio() {
        zzlr zzlrVar;
        synchronized (this.lock) {
            zzlrVar = this.zzbuq;
        }
        return zzlrVar;
    }

    final /* synthetic */ void zzo(Map map) {
        this.zzcyg.zza("pubVideoCmd", (Map<String, ?>) map);
    }
}
