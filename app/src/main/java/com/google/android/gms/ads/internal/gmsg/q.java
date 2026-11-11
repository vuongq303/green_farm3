package com.google.android.gms.ads.internal.gmsg;

import com.facebook.internal.NativeProtocol;
import com.google.android.gms.internal.ads.zzaqw;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class q implements zzv<zzaqw> {
    q() {
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzaqw zzaqwVar, Map map) {
        zzaqw zzaqwVar2 = zzaqwVar;
        String str = (String) map.get(NativeProtocol.WEB_DIALOG_ACTION);
        if ("pause".equals(str)) {
            zzaqwVar2.zzcl();
        } else if ("resume".equals(str)) {
            zzaqwVar2.zzcm();
        }
    }
}
