package com.google.android.gms.internal.ads;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public class zzaop<T> implements zzaol<T> {
    private final Object mLock = new Object();
    private int zzbqb = 0;
    private final BlockingQueue<zzaoq> zzcwi = new LinkedBlockingQueue();
    private T zzcwj;

    public final int getStatus() {
        return this.zzbqb;
    }

    public final void reject() {
        synchronized (this.mLock) {
            if (this.zzbqb != 0) {
                throw new UnsupportedOperationException();
            }
            this.zzbqb = -1;
            Iterator it = this.zzcwi.iterator();
            while (it.hasNext()) {
                ((zzaoq) it.next()).zzcwl.run();
            }
            this.zzcwi.clear();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaol
    public final void zza(zzaoo<T> zzaooVar, zzaom zzaomVar) {
        synchronized (this.mLock) {
            if (this.zzbqb == 1) {
                zzaooVar.zze(this.zzcwj);
            } else if (this.zzbqb == -1) {
                zzaomVar.run();
            } else if (this.zzbqb == 0) {
                this.zzcwi.add(new zzaoq(this, zzaooVar, zzaomVar));
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaol
    public final void zzk(T t) {
        synchronized (this.mLock) {
            if (this.zzbqb != 0) {
                throw new UnsupportedOperationException();
            }
            this.zzcwj = t;
            this.zzbqb = 1;
            Iterator it = this.zzcwi.iterator();
            while (it.hasNext()) {
                ((zzaoq) it.next()).zzcwk.zze(t);
            }
            this.zzcwi.clear();
        }
    }
}
