package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.SystemClock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public abstract class zzabh extends zzajx {
    protected final Context mContext;
    protected final Object mLock;
    protected final zzabm zzbzd;
    protected final zzaji zzbze;

    @GuardedBy("mLock")
    protected zzaej zzbzf;
    protected final Object zzbzh;

    protected zzabh(Context context, zzaji zzajiVar, zzabm zzabmVar) {
        super(true);
        this.mLock = new Object();
        this.zzbzh = new Object();
        this.mContext = context;
        this.zzbze = zzajiVar;
        this.zzbzf = zzajiVar.zzcos;
        this.zzbzd = zzabmVar;
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public void onStop() {
    }

    protected abstract zzajh zzaa(int i);

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        synchronized (this.mLock) {
            zzakb.zzck("AdRendererBackgroundTask started.");
            int i = this.zzbze.errorCode;
            try {
                zze(SystemClock.elapsedRealtime());
            } catch (zzabk e) {
                int errorCode = e.getErrorCode();
                if (errorCode == 3 || errorCode == -1) {
                    zzakb.zzdj(e.getMessage());
                } else {
                    zzakb.zzdk(e.getMessage());
                }
                if (this.zzbzf == null) {
                    this.zzbzf = new zzaej(errorCode);
                } else {
                    this.zzbzf = new zzaej(errorCode, this.zzbzf.zzbsu);
                }
                zzakk.zzcrm.post(new zzabi(this));
                i = errorCode;
            }
            zzakk.zzcrm.post(new zzabj(this, zzaa(i)));
        }
    }

    protected abstract void zze(long j);
}
