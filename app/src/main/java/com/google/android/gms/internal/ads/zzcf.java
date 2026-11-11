package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzcf implements zzce {
    protected static volatile zzcz zzps = null;
    protected MotionEvent zzpy;
    protected double zzqh;
    private double zzqi;
    private double zzqj;
    protected float zzqk;
    protected float zzql;
    protected float zzqm;
    protected float zzqn;
    protected DisplayMetrics zzqq;
    protected LinkedList<MotionEvent> zzpz = new LinkedList<>();
    protected long zzqa = 0;
    protected long zzqb = 0;
    protected long zzqc = 0;
    protected long zzqd = 0;
    protected long zzqe = 0;
    protected long zzqf = 0;
    protected long zzqg = 0;
    private boolean zzqo = false;
    protected boolean zzqp = false;

    protected zzcf(Context context) {
        try {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbay)).booleanValue()) {
                zzbk.zzv();
            } else {
                zzde.zzb(zzps);
            }
            this.zzqq = context.getResources().getDisplayMetrics();
        } catch (Throwable th) {
        }
    }

    private final String zza(Context context, String str, boolean z, View view, Activity activity, byte[] bArr) {
        zzba zzbaVarZza;
        try {
            if (z) {
                zzbaVarZza = zza(context, view, activity);
                this.zzqo = true;
            } else {
                zzbaVarZza = zza(context, null);
            }
            return (zzbaVarZza == null || zzbaVarZza.zzacw() == 0) ? Integer.toString(5) : zzbk.zza(zzbaVarZza, str);
        } catch (UnsupportedEncodingException e) {
            return Integer.toString(7);
        } catch (GeneralSecurityException e2) {
            return Integer.toString(7);
        } catch (Throwable th) {
            return Integer.toString(3);
        }
    }

    protected abstract long zza(StackTraceElement[] stackTraceElementArr);

    protected abstract zzba zza(Context context, View view, Activity activity);

    protected abstract zzba zza(Context context, zzax zzaxVar);

    @Override // com.google.android.gms.internal.ads.zzce
    public final String zza(Context context) {
        if (zzdg.isMainThread()) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbba)).booleanValue()) {
                throw new IllegalStateException("The caller must not be called from the UI thread.");
            }
        }
        return zza(context, null, false, null, null, null);
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final String zza(Context context, String str, View view) {
        return zza(context, str, view, null);
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final String zza(Context context, String str, View view, Activity activity) {
        return zza(context, str, true, view, activity, null);
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final void zza(int i, int i2, int i3) {
        if (this.zzpy != null) {
            this.zzpy.recycle();
        }
        if (this.zzqq != null) {
            this.zzpy = MotionEvent.obtain(0L, i3, 1, i * this.zzqq.density, i2 * this.zzqq.density, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
        } else {
            this.zzpy = null;
        }
        this.zzqp = false;
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final void zza(MotionEvent motionEvent) {
        if (this.zzqo) {
            this.zzqd = 0L;
            this.zzqc = 0L;
            this.zzqb = 0L;
            this.zzqa = 0L;
            this.zzqe = 0L;
            this.zzqg = 0L;
            this.zzqf = 0L;
            Iterator<MotionEvent> it = this.zzpz.iterator();
            while (it.hasNext()) {
                it.next().recycle();
            }
            this.zzpz.clear();
            this.zzpy = null;
            this.zzqo = false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                this.zzqh = 0.0d;
                this.zzqi = motionEvent.getRawX();
                this.zzqj = motionEvent.getRawY();
                break;
            case 1:
            case 2:
                double rawX = motionEvent.getRawX();
                double rawY = motionEvent.getRawY();
                double d = rawX - this.zzqi;
                double d2 = rawY - this.zzqj;
                this.zzqh = Math.sqrt((d * d) + (d2 * d2)) + this.zzqh;
                this.zzqi = rawX;
                this.zzqj = rawY;
                break;
        }
        switch (motionEvent.getAction()) {
            case 0:
                this.zzqk = motionEvent.getX();
                this.zzql = motionEvent.getY();
                this.zzqm = motionEvent.getRawX();
                this.zzqn = motionEvent.getRawY();
                this.zzqa++;
                break;
            case 1:
                this.zzpy = MotionEvent.obtain(motionEvent);
                this.zzpz.add(this.zzpy);
                if (this.zzpz.size() > 6) {
                    this.zzpz.remove().recycle();
                }
                this.zzqc++;
                try {
                    this.zzqe = zza(new Throwable().getStackTrace());
                } catch (zzcw e) {
                }
                break;
            case 2:
                this.zzqb += (long) (motionEvent.getHistorySize() + 1);
                try {
                    zzdf zzdfVarZzb = zzb(motionEvent);
                    if ((zzdfVarZzb == null || zzdfVarZzb.zzfr == null || zzdfVarZzb.zzst == null) ? false : true) {
                        this.zzqf += zzdfVarZzb.zzfr.longValue() + zzdfVarZzb.zzst.longValue();
                    }
                    if ((this.zzqq == null || zzdfVarZzb == null || zzdfVarZzb.zzfp == null || zzdfVarZzb.zzsu == null) ? false : true) {
                        this.zzqg = zzdfVarZzb.zzsu.longValue() + zzdfVarZzb.zzfp.longValue() + this.zzqg;
                    }
                } catch (zzcw e2) {
                }
                break;
            case 3:
                this.zzqd++;
                break;
        }
        this.zzqp = true;
    }

    protected abstract zzdf zzb(MotionEvent motionEvent);

    @Override // com.google.android.gms.internal.ads.zzce
    public void zzb(View view) {
    }
}
