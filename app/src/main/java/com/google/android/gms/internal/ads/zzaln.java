package com.google.android.gms.internal.ads;

import android.content.Context;
import java.io.File;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaln extends zzaj {
    private final Context mContext;

    private zzaln(Context context, zzar zzarVar) {
        super(zzarVar);
        this.mContext = context;
    }

    public static zzv zzba(Context context) {
        zzv zzvVar = new zzv(new zzam(new File(context.getCacheDir(), "admob_volley")), new zzaln(context, new zzas()));
        zzvVar.start();
        return zzvVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaj, com.google.android.gms.internal.ads.zzm
    public final zzp zzc(zzr<?> zzrVar) {
        if (zzrVar.zzh() && zzrVar.getMethod() == 0) {
            if (Pattern.matches((String) zzkb.zzik().zzd(zznk.zzbdw), zzrVar.getUrl())) {
                zzkb.zzif();
                if (zzamu.zzbe(this.mContext)) {
                    zzp zzpVarZzc = new zzsm(this.mContext).zzc(zzrVar);
                    if (zzpVarZzc != null) {
                        String strValueOf = String.valueOf(zzrVar.getUrl());
                        zzakb.v(strValueOf.length() != 0 ? "Got gmscore asset response: ".concat(strValueOf) : new String("Got gmscore asset response: "));
                        return zzpVarZzc;
                    }
                    String strValueOf2 = String.valueOf(zzrVar.getUrl());
                    zzakb.v(strValueOf2.length() != 0 ? "Failed to get gmscore asset response: ".concat(strValueOf2) : new String("Failed to get gmscore asset response: "));
                }
            }
        }
        return super.zzc(zzrVar);
    }
}
