package com.google.android.gms.ads.internal.gmsg;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zznx;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zze implements zzv<zzaqw> {
    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzaqw zzaqwVar, Map map) {
        zzaqw zzaqwVar2 = zzaqwVar;
        String str = (String) map.get(NativeProtocol.WEB_DIALOG_ACTION);
        if ("tick".equals(str)) {
            String str2 = (String) map.get("label");
            String str3 = (String) map.get("start_label");
            String str4 = (String) map.get("timestamp");
            if (TextUtils.isEmpty(str2)) {
                zzakb.zzdk("No label given for CSI tick.");
                return;
            }
            if (TextUtils.isEmpty(str4)) {
                zzakb.zzdk("No timestamp given for CSI tick.");
                return;
            }
            try {
                long j = (Long.parseLong(str4) - zzbv.zzer().currentTimeMillis()) + zzbv.zzer().elapsedRealtime();
                if (TextUtils.isEmpty(str3)) {
                    str3 = "native:view_load";
                }
                zzaqwVar2.zztp().zza(str2, str3, j);
                return;
            } catch (NumberFormatException e) {
                zzakb.zzc("Malformed timestamp for CSI tick.", e);
                return;
            }
        }
        if ("experiment".equals(str)) {
            String str5 = (String) map.get(FirebaseAnalytics.Param.VALUE);
            if (TextUtils.isEmpty(str5)) {
                zzakb.zzdk("No value given for CSI experiment.");
                return;
            }
            zznx zznxVarZzji = zzaqwVar2.zztp().zzji();
            if (zznxVarZzji == null) {
                zzakb.zzdk("No ticker for WebView, dropping experiment ID.");
                return;
            } else {
                zznxVarZzji.zze("e", str5);
                return;
            }
        }
        if ("extra".equals(str)) {
            String str6 = (String) map.get("name");
            String str7 = (String) map.get(FirebaseAnalytics.Param.VALUE);
            if (TextUtils.isEmpty(str7)) {
                zzakb.zzdk("No value given for CSI extra.");
                return;
            }
            if (TextUtils.isEmpty(str6)) {
                zzakb.zzdk("No name given for CSI extra.");
                return;
            }
            zznx zznxVarZzji2 = zzaqwVar2.zztp().zzji();
            if (zznxVarZzji2 == null) {
                zzakb.zzdk("No ticker for WebView, dropping extra parameter.");
            } else {
                zznxVarZzji2.zze(str6, str7);
            }
        }
    }
}
