package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.glads.AndroidWebView;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class zzpg implements com.google.android.gms.ads.internal.gmsg.zzv<Object> {
    private final /* synthetic */ zzacm zzbji;
    final /* synthetic */ zzpf zzbjj;

    zzpg(zzpf zzpfVar, zzacm zzacmVar) {
        this.zzbjj = zzpfVar;
        this.zzbji = zzacmVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, final Map<String, String> map) {
        zzaqw zzaqwVar = (zzaqw) this.zzbjj.zzbjg.get();
        if (zzaqwVar == null) {
            this.zzbji.zzb("/loadHtml", this);
            return;
        }
        zzasc zzascVarZzuf = zzaqwVar.zzuf();
        final zzacm zzacmVar = this.zzbji;
        zzascVarZzuf.zza(new zzasd(this, map, zzacmVar) { // from class: com.google.android.gms.internal.ads.zzph
            private final zzpg zzbjk;
            private final Map zzbjl;
            private final zzacm zzbjm;

            {
                this.zzbjk = this;
                this.zzbjl = map;
                this.zzbjm = zzacmVar;
            }

            @Override // com.google.android.gms.internal.ads.zzasd
            public final void zze(boolean z) {
                zzpg zzpgVar = this.zzbjk;
                Map map2 = this.zzbjl;
                zzacm zzacmVar2 = this.zzbjm;
                zzpgVar.zzbjj.zzbjh = (String) map2.get(ShareConstants.WEB_DIALOG_PARAM_ID);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("messageType", "htmlLoaded");
                    jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, zzpgVar.zzbjj.zzbjh);
                    zzacmVar2.zza("sendMessageToNativeJs", jSONObject);
                } catch (JSONException e) {
                    zzakb.zzb("Unable to dispatch sendMessageToNativeJs event", e);
                }
            }
        });
        String str = map.get("overlayHtml");
        String str2 = map.get("baseUrl");
        if (TextUtils.isEmpty(str2)) {
            zzaqwVar.loadData(str, "text/html", AndroidWebView.UTF_8);
        } else {
            zzaqwVar.loadDataWithBaseURL(str2, str, "text/html", AndroidWebView.UTF_8, null);
        }
    }
}
