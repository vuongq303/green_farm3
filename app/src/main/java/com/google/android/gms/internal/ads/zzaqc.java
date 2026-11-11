package com.google.android.gms.internal.ads;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.MessengerShareContentUtility;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaqc implements com.google.android.gms.ads.internal.gmsg.zzv<zzapw> {
    private boolean zzdau;

    private static int zza(Context context, Map<String, String> map, String str, int i) {
        String str2 = map.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            zzkb.zzif();
            return zzamu.zza(context, Integer.parseInt(str2));
        } catch (NumberFormatException e) {
            zzakb.zzdk(new StringBuilder(String.valueOf(str).length() + 34 + String.valueOf(str2).length()).append("Could not parse ").append(str).append(" in a video GMSG: ").append(str2).toString());
            return i;
        }
    }

    private static void zza(zzapi zzapiVar, Map<String, String> map) {
        String str = map.get("minBufferMs");
        String str2 = map.get("maxBufferMs");
        String str3 = map.get("bufferForPlaybackMs");
        String str4 = map.get("bufferForPlaybackAfterRebufferMs");
        if (str != null) {
            try {
                Integer.parseInt(str);
            } catch (NumberFormatException e) {
                zzakb.zzdk(String.format("Could not parse buffer parameters in loadControl video GMSG: (%s, %s)", str, str2));
                return;
            }
        }
        if (str2 != null) {
            Integer.parseInt(str2);
        }
        if (str3 != null) {
            Integer.parseInt(str3);
        }
        if (str4 != null) {
            Integer.parseInt(str4);
        }
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzapw zzapwVar, Map map) {
        int iMin;
        int i;
        zzapw zzapwVar2 = zzapwVar;
        String str = (String) map.get(NativeProtocol.WEB_DIALOG_ACTION);
        if (str == null) {
            zzakb.zzdk("Action missing from video GMSG.");
            return;
        }
        if (zzakb.isLoggable(3)) {
            JSONObject jSONObject = new JSONObject(map);
            jSONObject.remove("google.afma.Notify_dt");
            String string = jSONObject.toString();
            zzakb.zzck(new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(string).length()).append("Video GMSG: ").append(str).append(" ").append(string).toString());
        }
        if ("background".equals(str)) {
            String str2 = (String) map.get("color");
            if (TextUtils.isEmpty(str2)) {
                zzakb.zzdk("Color parameter missing from color video GMSG.");
                return;
            }
            try {
                zzapwVar2.setBackgroundColor(Color.parseColor(str2));
                return;
            } catch (IllegalArgumentException e) {
                zzakb.zzdk("Invalid color parameter in video GMSG.");
                return;
            }
        }
        if ("decoderProps".equals(str)) {
            String str3 = (String) map.get("mimeTypes");
            if (str3 == null) {
                zzakb.zzdk("No MIME types specified for decoder properties inspection.");
                zzapi.zza(zzapwVar2, "missingMimeTypes");
                return;
            }
            if (Build.VERSION.SDK_INT < 16) {
                zzakb.zzdk("Video decoder properties available on API versions >= 16.");
                zzapi.zza(zzapwVar2, "deficientApiVersion");
                return;
            }
            HashMap map2 = new HashMap();
            for (String str4 : str3.split(",")) {
                map2.put(str4, zzams.zzdd(str4.trim()));
            }
            zzapi.zza(zzapwVar2, map2);
            return;
        }
        zzapn zzapnVarZztl = zzapwVar2.zztl();
        if (zzapnVarZztl == null) {
            zzakb.zzdk("Could not get underlay container for a video GMSG.");
            return;
        }
        boolean zEquals = "new".equals(str);
        boolean zEquals2 = "position".equals(str);
        if (zEquals || zEquals2) {
            Context context = zzapwVar2.getContext();
            int iZza = zza(context, map, "x", 0);
            int iZza2 = zza(context, map, "y", 0);
            int iZza3 = zza(context, map, "w", -1);
            int iZza4 = zza(context, map, "h", -1);
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbca)).booleanValue()) {
                iMin = Math.min(iZza3, zzapwVar2.zzts() - iZza);
                iZza4 = Math.min(iZza4, zzapwVar2.zztr() - iZza2);
            } else {
                iMin = iZza3;
            }
            try {
                i = Integer.parseInt((String) map.get("player"));
            } catch (NumberFormatException e2) {
                i = 0;
            }
            boolean z = Boolean.parseBoolean((String) map.get("spherical"));
            if (!zEquals || zzapnVarZztl.zzth() != null) {
                zzapnVarZztl.zze(iZza, iZza2, iMin, iZza4);
                return;
            }
            zzapnVarZztl.zza(iZza, iZza2, iMin, iZza4, i, z, new zzapv((String) map.get("flags")));
            zzapi zzapiVarZzth = zzapnVarZztl.zzth();
            if (zzapiVarZzth != null) {
                zza(zzapiVarZzth, (Map<String, String>) map);
                return;
            }
            return;
        }
        zzapi zzapiVarZzth2 = zzapnVarZztl.zzth();
        if (zzapiVarZzth2 == null) {
            zzapi.zza(zzapwVar2);
            return;
        }
        if ("click".equals(str)) {
            Context context2 = zzapwVar2.getContext();
            int iZza5 = zza(context2, map, "x", 0);
            int iZza6 = zza(context2, map, "y", 0);
            long jUptimeMillis = SystemClock.uptimeMillis();
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, iZza5, iZza6, 0);
            zzapiVarZzth2.zzf(motionEventObtain);
            motionEventObtain.recycle();
            return;
        }
        if ("currentTime".equals(str)) {
            String str5 = (String) map.get("time");
            if (str5 == null) {
                zzakb.zzdk("Time parameter missing from currentTime video GMSG.");
                return;
            }
            try {
                zzapiVarZzth2.seekTo((int) (Float.parseFloat(str5) * 1000.0f));
                return;
            } catch (NumberFormatException e3) {
                String strValueOf = String.valueOf(str5);
                zzakb.zzdk(strValueOf.length() != 0 ? "Could not parse time parameter from currentTime video GMSG: ".concat(strValueOf) : new String("Could not parse time parameter from currentTime video GMSG: "));
                return;
            }
        }
        if (MessengerShareContentUtility.SHARE_BUTTON_HIDE.equals(str)) {
            zzapiVarZzth2.setVisibility(4);
            return;
        }
        if ("load".equals(str)) {
            zzapiVarZzth2.zzta();
            return;
        }
        if ("loadControl".equals(str)) {
            zza(zzapiVarZzth2, (Map<String, String>) map);
            return;
        }
        if ("muted".equals(str)) {
            if (Boolean.parseBoolean((String) map.get("muted"))) {
                zzapiVarZzth2.zztb();
                return;
            } else {
                zzapiVarZzth2.zztc();
                return;
            }
        }
        if ("pause".equals(str)) {
            zzapiVarZzth2.pause();
            return;
        }
        if ("play".equals(str)) {
            zzapiVarZzth2.play();
            return;
        }
        if ("show".equals(str)) {
            zzapiVarZzth2.setVisibility(0);
            return;
        }
        if ("src".equals(str)) {
            zzapiVarZzth2.zzdn((String) map.get("src"));
            return;
        }
        if ("touchMove".equals(str)) {
            Context context3 = zzapwVar2.getContext();
            zzapiVarZzth2.zza(zza(context3, map, "dx", 0), zza(context3, map, "dy", 0));
            if (this.zzdau) {
                return;
            }
            zzapwVar2.zznp();
            this.zzdau = true;
            return;
        }
        if (!"volume".equals(str)) {
            if ("watermark".equals(str)) {
                zzapiVarZzth2.zztd();
                return;
            } else {
                String strValueOf2 = String.valueOf(str);
                zzakb.zzdk(strValueOf2.length() != 0 ? "Unknown video action: ".concat(strValueOf2) : new String("Unknown video action: "));
                return;
            }
        }
        String str6 = (String) map.get("volume");
        if (str6 == null) {
            zzakb.zzdk("Level parameter missing from volume video GMSG.");
            return;
        }
        try {
            zzapiVarZzth2.setVolume(Float.parseFloat(str6));
        } catch (NumberFormatException e4) {
            String strValueOf3 = String.valueOf(str6);
            zzakb.zzdk(strValueOf3.length() != 0 ? "Could not parse volume parameter from volume video GMSG: ".concat(strValueOf3) : new String("Could not parse volume parameter from volume video GMSG: "));
        }
    }
}
