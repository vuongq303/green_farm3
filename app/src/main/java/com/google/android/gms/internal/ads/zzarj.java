package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzarj implements com.google.android.gms.ads.internal.gmsg.zzv<zzaqw> {
    private final /* synthetic */ zzari zzdec;

    zzarj(zzari zzariVar) {
        this.zzdec = zzariVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzaqw zzaqwVar, Map map) {
        if (map != null) {
            String str = (String) map.get("height");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            try {
                int i = Integer.parseInt(str);
                synchronized (this.zzdec) {
                    if (this.zzdec.zzddu != i) {
                        this.zzdec.zzddu = i;
                        this.zzdec.requestLayout();
                    }
                }
            } catch (Exception e) {
                zzakb.zzc("Exception occurred while getting webview content height", e);
            }
        }
    }
}
