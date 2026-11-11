package com.google.android.gms.internal.measurement;

import android.os.Process;
import com.google.android.gms.common.internal.bf;
import java.util.concurrent.BlockingQueue;

/* JADX INFO: loaded from: classes.dex */
final class zzgk extends Thread {
    private final /* synthetic */ zzgg zzalx;
    private final Object zzama;
    private final BlockingQueue<zzgj<?>> zzamb;

    public zzgk(zzgg zzggVar, String str, BlockingQueue<zzgj<?>> blockingQueue) {
        this.zzalx = zzggVar;
        bf.checkNotNull(str);
        bf.checkNotNull(blockingQueue);
        this.zzama = new Object();
        this.zzamb = blockingQueue;
        setName(str);
    }

    private final void zza(InterruptedException interruptedException) {
        this.zzalx.zzgg().zzin().zzg(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        boolean z = false;
        while (!z) {
            try {
                this.zzalx.zzalt.acquire();
                z = true;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        try {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            while (true) {
                zzgj<?> zzgjVarPoll = this.zzamb.poll();
                if (zzgjVarPoll == null) {
                    synchronized (this.zzama) {
                        if (this.zzamb.peek() == null && !this.zzalx.zzalu) {
                            try {
                                this.zzama.wait(30000L);
                            } catch (InterruptedException e2) {
                                zza(e2);
                            }
                        }
                    }
                    synchronized (this.zzalx.zzals) {
                        if (this.zzamb.peek() == null) {
                            break;
                        }
                    }
                } else {
                    Process.setThreadPriority(zzgjVarPoll.zzalz ? threadPriority : 10);
                    zzgjVarPoll.run();
                }
            }
            synchronized (this.zzalx.zzals) {
                this.zzalx.zzalt.release();
                this.zzalx.zzals.notifyAll();
                if (this == this.zzalx.zzalm) {
                    zzgg.zza(this.zzalx, null);
                } else if (this == this.zzalx.zzaln) {
                    zzgg.zzb(this.zzalx, null);
                } else {
                    this.zzalx.zzgg().zzil().log("Current scheduler thread is neither worker nor network");
                }
            }
        } catch (Throwable th) {
            synchronized (this.zzalx.zzals) {
                this.zzalx.zzalt.release();
                this.zzalx.zzals.notifyAll();
                if (this == this.zzalx.zzalm) {
                    zzgg.zza(this.zzalx, null);
                } else if (this == this.zzalx.zzaln) {
                    zzgg.zzb(this.zzalx, null);
                } else {
                    this.zzalx.zzgg().zzil().log("Current scheduler thread is neither worker nor network");
                }
                throw th;
            }
        }
    }

    public final void zzjj() {
        synchronized (this.zzama) {
            this.zzama.notifyAll();
        }
    }
}
