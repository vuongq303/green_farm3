package com.google.android.gms.internal.ads;

import android.support.v4.e.r;
import com.facebook.share.internal.MessengerShareContentUtility;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzacp implements zzacd<zzos> {
    private final boolean zzcbk;

    public zzacp(boolean z) {
        this.zzcbk = z;
    }

    @Override // com.google.android.gms.internal.ads.zzacd
    public final /* synthetic */ zzpb zza(zzabv zzabvVar, JSONObject jSONObject) throws JSONException {
        int i = 0;
        r rVar = new r();
        r rVar2 = new r();
        zzanz<zzoj> zzanzVarZzg = zzabvVar.zzg(jSONObject);
        zzanz<zzaqw> zzanzVarZzc = zzabvVar.zzc(jSONObject, "video");
        JSONArray jSONArray = jSONObject.getJSONArray("custom_assets");
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
            String string = jSONObject2.getString("type");
            if ("string".equals(string)) {
                rVar2.put(jSONObject2.getString("name"), jSONObject2.getString("string_value"));
            } else if (MessengerShareContentUtility.MEDIA_IMAGE.equals(string)) {
                rVar.put(jSONObject2.getString("name"), zzabvVar.zza(jSONObject2, "image_value", this.zzcbk));
            } else {
                String strValueOf = String.valueOf(string);
                zzakb.zzdk(strValueOf.length() != 0 ? "Unknown custom asset type: ".concat(strValueOf) : new String("Unknown custom asset type: "));
            }
        }
        zzaqw zzaqwVarZzc = zzabv.zzc(zzanzVarZzc);
        String string2 = jSONObject.getString("custom_template_id");
        r rVar3 = new r();
        while (true) {
            int i3 = i;
            if (i3 >= rVar.size()) {
                break;
            }
            rVar3.put(rVar.keyAt(i3), ((Future) rVar.valueAt(i3)).get());
            i = i3 + 1;
        }
        return new zzos(string2, rVar3, rVar2, zzanzVarZzg.get(), zzaqwVarZzc != null ? zzaqwVarZzc.zztm() : null, zzaqwVarZzc != null ? zzaqwVarZzc.getView() : null);
    }
}
