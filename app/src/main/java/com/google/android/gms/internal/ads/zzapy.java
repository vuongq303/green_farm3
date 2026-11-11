package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class zzapy implements Runnable {
    private boolean zzahs = false;
    private zzapi zzdap;

    zzapy(zzapi zzapiVar) {
        this.zzdap = zzapiVar;
    }

    private final void zztv() {
        zzakk.zzcrm.removeCallbacks(this);
        zzakk.zzcrm.postDelayed(this, 250L);
    }

    public final void pause() {
        this.zzahs = true;
    }

    public final void resume() {
        this.zzahs = false;
        zztv();
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzahs) {
            return;
        }
        this.zzdap.zzte();
        zztv();
    }
}
