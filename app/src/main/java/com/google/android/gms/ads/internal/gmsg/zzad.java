package com.google.android.gms.ads.internal.gmsg;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.ads.internal.overlay.zzn;
import com.google.android.gms.ads.internal.overlay.zzt;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.ads.internal.zzx;
import com.google.android.gms.internal.ads.zzaab;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajb;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzarr;
import com.google.android.gms.internal.ads.zzars;
import com.google.android.gms.internal.ads.zzarw;
import com.google.android.gms.internal.ads.zzarz;
import com.google.android.gms.internal.ads.zzasb;
import com.google.android.gms.internal.ads.zzci;
import com.google.android.gms.internal.ads.zzcj;
import com.google.android.gms.internal.ads.zzjd;
import java.net.URISyntaxException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzad<T extends zzarr & zzars & zzarw & zzarz & zzasb> implements zzv<T> {
    private final zzci bzZ;
    private final Context mContext;
    private final zzjd zzapt;
    private final zzb zzbll;
    private final zzd zzblm;
    private final zzx zzbmw;
    private final zzaab zzbmx;
    private final zzt zzbnb;
    private final zzn zzbnc;
    private final zzaqw zzbnd = null;
    private final zzang zzzw;

    public zzad(Context context, zzang zzangVar, zzci zzciVar, zzt zztVar, zzjd zzjdVar, zzb zzbVar, zzd zzdVar, zzn zznVar, zzx zzxVar, zzaab zzaabVar) {
        this.mContext = context;
        this.zzzw = zzangVar;
        this.bzZ = zzciVar;
        this.zzbnb = zztVar;
        this.zzapt = zzjdVar;
        this.zzbll = zzbVar;
        this.zzblm = zzdVar;
        this.zzbmw = zzxVar;
        this.zzbmx = zzaabVar;
        this.zzbnc = zznVar;
    }

    static String a(Context context, zzci zzciVar, String str, View view, Activity activity) {
        if (zzciVar == null) {
            return str;
        }
        try {
            Uri uriZza = Uri.parse(str);
            if (zzciVar.zzc(uriZza)) {
                uriZza = zzciVar.zza(uriZza, context, view, activity);
            }
            return uriZza.toString();
        } catch (zzcj e) {
            return str;
        } catch (Exception e2) {
            zzbv.zzeo().zza(e2, "OpenGmsgHandler.maybeAddClickSignalsToUrl");
            return str;
        }
    }

    private final void bR(boolean z) {
        if (this.zzbmx != null) {
            this.zzbmx.zzm(z);
        }
    }

    private static boolean c(Map<String, String> map) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("custom_close"));
    }

    private static int d(Map<String, String> map) {
        String str = map.get("o");
        if (str != null) {
            if ("p".equalsIgnoreCase(str)) {
                return zzbv.zzem().zzrm();
            }
            if ("l".equalsIgnoreCase(str)) {
                return zzbv.zzem().zzrl();
            }
            if ("c".equalsIgnoreCase(str)) {
                return zzbv.zzem().zzrn();
            }
        }
        return -1;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(Object obj, Map map) throws URISyntaxException {
        Intent uri;
        String strA;
        Uri uri2;
        zzarr zzarrVar = (zzarr) obj;
        String strZzb = zzajb.zzb((String) map.get("u"), zzarrVar.getContext());
        String str = (String) map.get("a");
        if (str == null) {
            zzakb.zzdk("Action missing from an open GMSG.");
            return;
        }
        if (this.zzbmw != null && !this.zzbmw.zzcy()) {
            this.zzbmw.zzs(strZzb);
            return;
        }
        if ("expand".equalsIgnoreCase(str)) {
            if (((zzars) zzarrVar).zzuj()) {
                zzakb.zzdk("Cannot expand WebView that is already expanded.");
                return;
            } else {
                bR(false);
                ((zzarw) zzarrVar).zza(c(map), d(map));
                return;
            }
        }
        if ("webapp".equalsIgnoreCase(str)) {
            bR(false);
            if (strZzb != null) {
                ((zzarw) zzarrVar).zza(c(map), d(map), strZzb);
                return;
            } else {
                ((zzarw) zzarrVar).zza(c(map), d(map), (String) map.get("html"), (String) map.get("baseurl"));
                return;
            }
        }
        if ("app".equalsIgnoreCase(str) && ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equalsIgnoreCase((String) map.get("system_browser"))) {
            bR(true);
            zzarrVar.getContext();
            if (TextUtils.isEmpty(strZzb)) {
                zzakb.zzdk("Destination url cannot be empty.");
                return;
            }
            try {
                ((zzarw) zzarrVar).zza(new com.google.android.gms.ads.internal.overlay.zzc(new zzae(zzarrVar.getContext(), ((zzarz) zzarrVar).zzui(), ((zzasb) zzarrVar).getView()).zzi(map)));
                return;
            } catch (ActivityNotFoundException e) {
                zzakb.zzdk(e.getMessage());
                return;
            }
        }
        bR(true);
        String str2 = (String) map.get("intent_url");
        if (TextUtils.isEmpty(str2)) {
            uri = null;
        } else {
            try {
                uri = Intent.parseUri(str2, 0);
            } catch (URISyntaxException e2) {
                String strValueOf = String.valueOf(str2);
                zzakb.zzb(strValueOf.length() != 0 ? "Error parsing the url: ".concat(strValueOf) : new String("Error parsing the url: "), e2);
                uri = null;
            }
        }
        if (uri != null && uri.getData() != null) {
            Uri data = uri.getData();
            String string = data.toString();
            if (TextUtils.isEmpty(string)) {
                uri2 = data;
            } else {
                try {
                    strA = a(zzarrVar.getContext(), ((zzarz) zzarrVar).zzui(), string, ((zzasb) zzarrVar).getView(), zzarrVar.zzto());
                } catch (Exception e3) {
                    zzakb.zzb("Error occurred while adding signals.", e3);
                    zzbv.zzeo().zza(e3, "OpenGmsgHandler.onGmsg");
                    strA = string;
                }
                try {
                    uri2 = Uri.parse(strA);
                } catch (Exception e4) {
                    String strValueOf2 = String.valueOf(strA);
                    zzakb.zzb(strValueOf2.length() != 0 ? "Error parsing the uri: ".concat(strValueOf2) : new String("Error parsing the uri: "), e4);
                    zzbv.zzeo().zza(e4, "OpenGmsgHandler.onGmsg");
                    uri2 = data;
                }
            }
            uri.setData(uri2);
        }
        if (uri != null) {
            ((zzarw) zzarrVar).zza(new com.google.android.gms.ads.internal.overlay.zzc(uri));
            return;
        }
        if (!TextUtils.isEmpty(strZzb)) {
            strZzb = a(zzarrVar.getContext(), ((zzarz) zzarrVar).zzui(), strZzb, ((zzasb) zzarrVar).getView(), zzarrVar.zzto());
        }
        ((zzarw) zzarrVar).zza(new com.google.android.gms.ads.internal.overlay.zzc((String) map.get("i"), strZzb, (String) map.get("m"), (String) map.get("p"), (String) map.get("c"), (String) map.get("f"), (String) map.get("e")));
    }
}
