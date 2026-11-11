package com.google.android.gms.internal.ads;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public class zzabn extends zzabf {
    zzabn(Context context, zzaji zzajiVar, zzaqw zzaqwVar, zzabm zzabmVar) {
        super(context, zzajiVar, zzaqwVar, zzabmVar);
    }

    @Override // com.google.android.gms.internal.ads.zzabf
    protected final void zzns() {
        if (this.zzbzf.errorCode != -2) {
            return;
        }
        this.zzbnd.zzuf().zza(this);
        zznu();
        zzakb.zzck("Loading HTML in WebView.");
        this.zzbnd.zzc(this.zzbzf.zzbyq, this.zzbzf.zzceo, null);
    }

    protected void zznu() {
    }
}
