package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzfh implements Runnable {
    private final /* synthetic */ int zzaix;
    private final /* synthetic */ String zzaiy;
    private final /* synthetic */ Object zzaiz;
    private final /* synthetic */ Object zzaja;
    private final /* synthetic */ Object zzajb;
    private final /* synthetic */ zzfg zzajc;

    zzfh(zzfg zzfgVar, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzajc = zzfgVar;
        this.zzaix = i;
        this.zzaiy = str;
        this.zzaiz = obj;
        this.zzaja = obj2;
        this.zzajb = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfr zzfrVarZzgh = this.zzajc.zzacr.zzgh();
        if (!zzfrVarZzgh.isInitialized()) {
            this.zzajc.zza(6, "Persisted config not initialized. Not logging error/warn");
            return;
        }
        if (this.zzajc.zzaim == 0) {
            if (this.zzajc.zzgi().zzds()) {
                this.zzajc.zzaim = 'C';
            } else {
                this.zzajc.zzaim = 'c';
            }
        }
        if (this.zzajc.zzadp < 0) {
            zzfg.zza(this.zzajc, 12451L);
        }
        char cCharAt = "01VDIWEA?".charAt(this.zzaix);
        char c = this.zzajc.zzaim;
        long j = this.zzajc.zzadp;
        String strZza = zzfg.zza(true, this.zzaiy, this.zzaiz, this.zzaja, this.zzajb);
        String string = new StringBuilder(String.valueOf(strZza).length() + 24).append("2").append(cCharAt).append(c).append(j).append(":").append(strZza).toString();
        if (string.length() > 1024) {
            string = this.zzaiy.substring(0, 1024);
        }
        zzfrVarZzgh.zzajs.zzc(string, 1L);
    }
}
