package com.google.android.gms.ads.internal.gmsg;

import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzy implements zzv<Object> {
    private final zzz zzbmu;

    public zzy(zzz zzzVar) {
        this.zzbmu = zzzVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        float f;
        boolean zEquals = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("transparentBackground"));
        boolean zEquals2 = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("blur"));
        try {
            f = map.get("blurRadius") != null ? Float.parseFloat(map.get("blurRadius")) : 0.0f;
        } catch (NumberFormatException e) {
            zzakb.zzb("Fail to parse float", e);
        }
        this.zzbmu.zzd(zEquals);
        this.zzbmu.zza(zEquals2, f);
    }
}
