package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzxc implements Runnable {
    private final /* synthetic */ zzxa zzbts;
    private final /* synthetic */ zzxb zzbtt;

    zzxc(zzxb zzxbVar, zzxa zzxaVar) {
        this.zzbtt = zzxbVar;
        this.zzbts = zzxaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzbtt.mLock) {
            if (this.zzbtt.zzbtq != -2) {
                return;
            }
            this.zzbtt.zzbtp = this.zzbtt.zzmj();
            if (this.zzbtt.zzbtp == null) {
                this.zzbtt.zzx(4);
                return;
            }
            if (!this.zzbtt.zzmk() || this.zzbtt.zzy(1)) {
                this.zzbts.zza(this.zzbtt);
                this.zzbtt.zza(this.zzbts);
            } else {
                String str = this.zzbtt.zzbth;
                zzakb.zzdk(new StringBuilder(String.valueOf(str).length() + 56).append("Ignoring adapter ").append(str).append(" as delayed impression is not supported").toString());
                this.zzbtt.zzx(2);
            }
        }
    }
}
