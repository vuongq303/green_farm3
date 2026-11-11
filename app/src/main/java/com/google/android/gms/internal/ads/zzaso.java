package com.google.android.gms.internal.ads;

import android.content.Context;
import java.util.concurrent.Callable;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzaso {
    public static zzaqw zza(final Context context, final zzasi zzasiVar, final String str, final boolean z, final boolean z2, final zzci zzciVar, final zzang zzangVar, final zznx zznxVar, final com.google.android.gms.ads.internal.zzbo zzboVar, final com.google.android.gms.ads.internal.zzw zzwVar, final zzhs zzhsVar) throws zzarg {
        try {
            return (zzaqw) zzaml.zzb(new Callable(context, zzasiVar, str, z, z2, zzciVar, zzangVar, zznxVar, zzboVar, zzwVar, zzhsVar) { // from class: com.google.android.gms.internal.ads.zzasp
                private final String zzcah;
                private final Context zzdck;
                private final zzasi zzdco;
                private final boolean zzdcp;
                private final boolean zzdcq;
                private final zzci zzdcr;
                private final zzang zzdcs;
                private final zznx zzdct;
                private final com.google.android.gms.ads.internal.zzbo zzdcu;
                private final com.google.android.gms.ads.internal.zzw zzdcv;
                private final zzhs zzdcw;

                {
                    this.zzdck = context;
                    this.zzdco = zzasiVar;
                    this.zzcah = str;
                    this.zzdcp = z;
                    this.zzdcq = z2;
                    this.zzdcr = zzciVar;
                    this.zzdcs = zzangVar;
                    this.zzdct = zznxVar;
                    this.zzdcu = zzboVar;
                    this.zzdcv = zzwVar;
                    this.zzdcw = zzhsVar;
                }

                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Context context2 = this.zzdck;
                    zzasi zzasiVar2 = this.zzdco;
                    String str2 = this.zzcah;
                    boolean z3 = this.zzdcp;
                    boolean z4 = this.zzdcq;
                    zzasq zzasqVarZzc = zzasq.zzc(context2, zzasiVar2, str2, z3, z4, this.zzdcr, this.zzdcs, this.zzdct, this.zzdcu, this.zzdcv, this.zzdcw);
                    zzarh zzarhVar = new zzarh(zzasqVarZzc);
                    zzasj zzasjVar = new zzasj(zzarhVar, z4);
                    zzasqVarZzc.setWebChromeClient(new zzaqo(zzarhVar));
                    zzasqVarZzc.zza((zzasx) zzasjVar);
                    zzasqVarZzc.zza((zzatb) zzasjVar);
                    zzasqVarZzc.zza((zzata) zzasjVar);
                    zzasqVarZzc.zza((zzasz) zzasjVar);
                    zzasqVarZzc.zza(zzasjVar);
                    return zzarhVar;
                }
            });
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(th, "AdWebViewFactory.newAdWebView2");
            throw new zzarg("Webview initialization failed.", th);
        }
    }
}
