package com.google.android.gms.ads.internal.gmsg;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzaig;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzah implements zzv<Object> {
    private final zzai bAc;

    public zzah(zzai zzaiVar) {
        this.bAc = zzaiVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        zzaig zzaigVar;
        String str = map.get(NativeProtocol.WEB_DIALOG_ACTION);
        if ("grant".equals(str)) {
            try {
                int i = Integer.parseInt(map.get("amount"));
                String str2 = map.get("type");
                zzaigVar = !TextUtils.isEmpty(str2) ? new zzaig(str2, i) : null;
            } catch (NumberFormatException e) {
                zzakb.zzc("Unable to parse reward amount.", e);
            }
            this.bAc.zzb(zzaigVar);
            return;
        }
        if ("video_start".equals(str)) {
            this.bAc.zzdk();
            return;
        }
        if ("video_complete".equals(str)) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzaxv)).booleanValue()) {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzaxv)).booleanValue()) {
                    this.bAc.zzdl();
                }
            }
        }
    }
}
