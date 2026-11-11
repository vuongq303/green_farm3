package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.common.d.s;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzabl {
    public static zzalc zza(Context context, com.google.android.gms.ads.internal.zza zzaVar, zzaji zzajiVar, zzci zzciVar, zzaqw zzaqwVar, zzxn zzxnVar, zzabm zzabmVar, zznx zznxVar) {
        zzalc zzabtVar;
        zzaej zzaejVar = zzajiVar.zzcos;
        if (zzaejVar.zzceq) {
            zzabtVar = new zzabr(context, zzajiVar, zzxnVar, zzabmVar, zznxVar, zzaqwVar);
        } else if (zzaejVar.zzare || (zzaVar instanceof com.google.android.gms.ads.internal.zzbc)) {
            zzabtVar = (zzaejVar.zzare && (zzaVar instanceof com.google.android.gms.ads.internal.zzbc)) ? new zzabt(context, (com.google.android.gms.ads.internal.zzbc) zzaVar, zzajiVar, zzciVar, zzabmVar, zznxVar) : new zzabo(zzajiVar, zzabmVar);
        } else {
            zzabtVar = (((Boolean) zzkb.zzik().zzd(zznk.zzaxd)).booleanValue() && s.abA() && !s.abC() && zzaqwVar != null && zzaqwVar.zzud().zzvs()) ? new zzabq(context, zzajiVar, zzaqwVar, zzabmVar) : new zzabn(context, zzajiVar, zzaqwVar, zzabmVar);
        }
        String strValueOf = String.valueOf(zzabtVar.getClass().getName());
        zzakb.zzck(strValueOf.length() != 0 ? "AdRenderer: ".concat(strValueOf) : new String("AdRenderer: "));
        zzabtVar.zznt();
        return zzabtVar;
    }
}
