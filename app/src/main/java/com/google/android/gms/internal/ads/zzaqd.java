package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaqd implements com.google.android.gms.ads.internal.gmsg.zzv<zzapw> {
    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzapw zzapwVar, Map map) {
        zzarl zzarlVar;
        zzapw zzapwVar2 = zzapwVar;
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbae)).booleanValue()) {
            zzarl zzarlVarZztm = zzapwVar2.zztm();
            if (zzarlVarZztm == null) {
                try {
                    zzarl zzarlVar2 = new zzarl(zzapwVar2, Float.parseFloat((String) map.get("duration")), AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("customControlsAllowed")), AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("clickToExpandAllowed")));
                    zzapwVar2.zza(zzarlVar2);
                    zzarlVar = zzarlVar2;
                } catch (NullPointerException | NumberFormatException e) {
                    zzakb.zzb("Unable to parse videoMeta message.", e);
                    com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "VideoMetaGmsgHandler.onGmsg");
                    return;
                }
            } else {
                zzarlVar = zzarlVarZztm;
            }
            boolean zEquals = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("muted"));
            float f = Float.parseFloat((String) map.get("currentTime"));
            int i = Integer.parseInt((String) map.get("playbackState"));
            int i2 = (i < 0 || 3 < i) ? 0 : i;
            String str = (String) map.get("aspectRatio");
            float f2 = TextUtils.isEmpty(str) ? 0.0f : Float.parseFloat(str);
            if (zzakb.isLoggable(3)) {
                zzakb.zzck(new StringBuilder(String.valueOf(str).length() + 79).append("Video Meta GMSG: isMuted : ").append(zEquals).append(" , playbackState : ").append(i2).append(" , aspectRatio : ").append(str).toString());
            }
            zzarlVar.zza(f, i2, zEquals, f2);
        }
    }
}
