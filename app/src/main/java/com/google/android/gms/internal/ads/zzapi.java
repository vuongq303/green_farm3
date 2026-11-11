package com.google.android.gms.internal.ads;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.c;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzapi extends FrameLayout implements zzapf {
    private final zzapw zzcxm;
    private final FrameLayout zzcxn;
    private final zznx zzcxo;
    private final zzapy zzcxp;
    private final long zzcxq;
    private zzapg zzcxr;
    private boolean zzcxs;
    private boolean zzcxt;
    private boolean zzcxu;
    private boolean zzcxv;
    private long zzcxw;
    private long zzcxx;
    private String zzcxy;
    private Bitmap zzcxz;
    private ImageView zzcya;
    private boolean zzcyb;

    public zzapi(Context context, zzapw zzapwVar, int i, boolean z, zznx zznxVar, zzapv zzapvVar) {
        super(context);
        this.zzcxm = zzapwVar;
        this.zzcxo = zznxVar;
        this.zzcxn = new FrameLayout(context);
        addView(this.zzcxn, new FrameLayout.LayoutParams(-1, -1));
        c.A(zzapwVar.zzbi());
        this.zzcxr = zzapwVar.zzbi().zzwz.zza(context, zzapwVar, i, z, zznxVar, zzapvVar);
        if (this.zzcxr != null) {
            this.zzcxn.addView(this.zzcxr, new FrameLayout.LayoutParams(-1, -1, 17));
            if (((Boolean) zzkb.zzik().zzd(zznk.zzavg)).booleanValue()) {
                zztd();
            }
        }
        this.zzcya = new ImageView(context);
        this.zzcxq = ((Long) zzkb.zzik().zzd(zznk.zzavk)).longValue();
        this.zzcxv = ((Boolean) zzkb.zzik().zzd(zznk.zzavi)).booleanValue();
        if (this.zzcxo != null) {
            this.zzcxo.zze("spinner_used", this.zzcxv ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        }
        this.zzcxp = new zzapy(this);
        if (this.zzcxr != null) {
            this.zzcxr.zza(this);
        }
        if (this.zzcxr == null) {
            zzg("AdVideoUnderlay Error", "Allocating player failed.");
        }
    }

    public static void zza(zzapw zzapwVar) {
        HashMap map = new HashMap();
        map.put("event", "no_video_view");
        zzapwVar.zza("onVideoEvent", map);
    }

    public static void zza(zzapw zzapwVar, String str) {
        HashMap map = new HashMap();
        map.put("event", "decoderProps");
        map.put("error", str);
        zzapwVar.zza("onVideoEvent", map);
    }

    public static void zza(zzapw zzapwVar, Map<String, List<Map<String, Object>>> map) {
        HashMap map2 = new HashMap();
        map2.put("event", "decoderProps");
        map2.put("mimeTypes", map);
        zzapwVar.zza("onVideoEvent", map2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(String str, String... strArr) {
        HashMap map = new HashMap();
        map.put("event", str);
        int length = strArr.length;
        int i = 0;
        String str2 = null;
        while (i < length) {
            String str3 = strArr[i];
            if (str2 != null) {
                map.put(str2, str3);
                str3 = null;
            }
            i++;
            str2 = str3;
        }
        this.zzcxm.zza("onVideoEvent", map);
    }

    private final boolean zztf() {
        return this.zzcya.getParent() != null;
    }

    private final void zztg() {
        if (this.zzcxm.zzto() == null || !this.zzcxt || this.zzcxu) {
            return;
        }
        this.zzcxm.zzto().getWindow().clearFlags(128);
        this.zzcxt = false;
    }

    public final void destroy() {
        this.zzcxp.pause();
        if (this.zzcxr != null) {
            this.zzcxr.stop();
        }
        zztg();
    }

    public final void finalize() throws Throwable {
        try {
            this.zzcxp.pause();
            if (this.zzcxr != null) {
                zzapg zzapgVar = this.zzcxr;
                Executor executor = zzaoe.zzcvy;
                zzapgVar.getClass();
                executor.execute(zzapj.zza(zzapgVar));
            }
        } finally {
            super.finalize();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void onPaused() {
        zza("pause", new String[0]);
        zztg();
        this.zzcxs = false;
    }

    @Override // android.view.View, com.google.android.gms.internal.ads.zzapf
    public final void onWindowVisibilityChanged(int i) {
        boolean z;
        if (i == 0) {
            this.zzcxp.resume();
            z = true;
        } else {
            this.zzcxp.pause();
            this.zzcxx = this.zzcxw;
            z = false;
        }
        zzakk.zzcrm.post(new zzapm(this, z));
    }

    public final void pause() {
        if (this.zzcxr == null) {
            return;
        }
        this.zzcxr.pause();
    }

    public final void play() {
        if (this.zzcxr == null) {
            return;
        }
        this.zzcxr.play();
    }

    public final void seekTo(int i) {
        if (this.zzcxr == null) {
            return;
        }
        this.zzcxr.seekTo(i);
    }

    public final void setVolume(float f) {
        if (this.zzcxr == null) {
            return;
        }
        zzapg zzapgVar = this.zzcxr;
        zzapgVar.zzcxl.setVolume(f);
        zzapgVar.zzst();
    }

    public final void zza(float f, float f2) {
        if (this.zzcxr != null) {
            this.zzcxr.zza(f, f2);
        }
    }

    public final void zzd(int i, int i2, int i3, int i4) {
        if (i3 == 0 || i4 == 0) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i3, i4);
        layoutParams.setMargins(i, i2, 0, 0);
        this.zzcxn.setLayoutParams(layoutParams);
        requestLayout();
    }

    public final void zzdn(String str) {
        this.zzcxy = str;
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void zzf(int i, int i2) {
        if (this.zzcxv) {
            int iMax = Math.max(i / ((Integer) zzkb.zzik().zzd(zznk.zzavj)).intValue(), 1);
            int iMax2 = Math.max(i2 / ((Integer) zzkb.zzik().zzd(zznk.zzavj)).intValue(), 1);
            if (this.zzcxz != null && this.zzcxz.getWidth() == iMax && this.zzcxz.getHeight() == iMax2) {
                return;
            }
            this.zzcxz = Bitmap.createBitmap(iMax, iMax2, Bitmap.Config.ARGB_8888);
            this.zzcyb = false;
        }
    }

    public final void zzf(MotionEvent motionEvent) {
        if (this.zzcxr == null) {
            return;
        }
        this.zzcxr.dispatchTouchEvent(motionEvent);
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void zzg(String str, String str2) {
        zza("error", "what", str, "extra", str2);
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void zzsu() {
        this.zzcxp.resume();
        zzakk.zzcrm.post(new zzapk(this));
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void zzsv() {
        if (this.zzcxr != null && this.zzcxx == 0) {
            zza("canplaythrough", "duration", String.valueOf(this.zzcxr.getDuration() / 1000.0f), "videoWidth", String.valueOf(this.zzcxr.getVideoWidth()), "videoHeight", String.valueOf(this.zzcxr.getVideoHeight()));
        }
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void zzsw() {
        if (this.zzcxm.zzto() != null && !this.zzcxt) {
            this.zzcxu = (this.zzcxm.zzto().getWindow().getAttributes().flags & 128) != 0;
            if (!this.zzcxu) {
                this.zzcxm.zzto().getWindow().addFlags(128);
                this.zzcxt = true;
            }
        }
        this.zzcxs = true;
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void zzsx() {
        zza("ended", new String[0]);
        zztg();
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void zzsy() {
        if (this.zzcyb && this.zzcxz != null && !zztf()) {
            this.zzcya.setImageBitmap(this.zzcxz);
            this.zzcya.invalidate();
            this.zzcxn.addView(this.zzcya, new FrameLayout.LayoutParams(-1, -1));
            this.zzcxn.bringChildToFront(this.zzcya);
        }
        this.zzcxp.pause();
        this.zzcxx = this.zzcxw;
        zzakk.zzcrm.post(new zzapl(this));
    }

    @Override // com.google.android.gms.internal.ads.zzapf
    public final void zzsz() {
        if (this.zzcxs && zztf()) {
            this.zzcxn.removeView(this.zzcya);
        }
        if (this.zzcxz != null) {
            long jElapsedRealtime = com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime();
            if (this.zzcxr.getBitmap(this.zzcxz) != null) {
                this.zzcyb = true;
            }
            long jElapsedRealtime2 = com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime() - jElapsedRealtime;
            if (zzakb.zzqp()) {
                zzakb.v(new StringBuilder(46).append("Spinner frame grab took ").append(jElapsedRealtime2).append("ms").toString());
            }
            if (jElapsedRealtime2 > this.zzcxq) {
                zzakb.zzdk("Spinner frame grab crossed jank threshold! Suspending spinner.");
                this.zzcxv = false;
                this.zzcxz = null;
                if (this.zzcxo != null) {
                    this.zzcxo.zze("spinner_jank", Long.toString(jElapsedRealtime2));
                }
            }
        }
    }

    public final void zzta() {
        if (this.zzcxr == null) {
            return;
        }
        if (TextUtils.isEmpty(this.zzcxy)) {
            zza("no_src", new String[0]);
        } else {
            this.zzcxr.setVideoPath(this.zzcxy);
        }
    }

    public final void zztb() {
        if (this.zzcxr == null) {
            return;
        }
        zzapg zzapgVar = this.zzcxr;
        zzapgVar.zzcxl.setMuted(true);
        zzapgVar.zzst();
    }

    public final void zztc() {
        if (this.zzcxr == null) {
            return;
        }
        zzapg zzapgVar = this.zzcxr;
        zzapgVar.zzcxl.setMuted(false);
        zzapgVar.zzst();
    }

    public final void zztd() {
        if (this.zzcxr == null) {
            return;
        }
        TextView textView = new TextView(this.zzcxr.getContext());
        String strValueOf = String.valueOf(this.zzcxr.zzsp());
        textView.setText(strValueOf.length() != 0 ? "AdMob - ".concat(strValueOf) : new String("AdMob - "));
        textView.setTextColor(-65536);
        textView.setBackgroundColor(-256);
        this.zzcxn.addView(textView, new FrameLayout.LayoutParams(-2, -2, 17));
        this.zzcxn.bringChildToFront(textView);
    }

    final void zzte() {
        if (this.zzcxr == null) {
            return;
        }
        long currentPosition = this.zzcxr.getCurrentPosition();
        if (this.zzcxw == currentPosition || currentPosition <= 0) {
            return;
        }
        zza("timeupdate", "time", String.valueOf(currentPosition / 1000.0f));
        this.zzcxw = currentPosition;
    }
}
