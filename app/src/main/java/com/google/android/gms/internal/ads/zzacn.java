package com.google.android.gms.internal.ads;

import android.content.res.Resources;
import android.os.Bundle;
import com.google.android.gms.ads.impl.R;
import com.unity3d.services.purchasing.core.TransactionErrorDetailsUtilities;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzacn implements zzacd<zzoo> {
    private final boolean zzbto;
    private final boolean zzcbk;
    private final boolean zzcbl;

    public zzacn(boolean z, boolean z2, boolean z3) {
        this.zzcbk = z;
        this.zzcbl = z2;
        this.zzbto = z3;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x010f  */
    @Override // com.google.android.gms.internal.ads.zzacd
    public final /* synthetic */ zzpb zza(zzabv zzabvVar, JSONObject jSONObject) throws JSONException {
        String string;
        List<zzanz<zzon>> listZza = zzabvVar.zza(jSONObject, "images", false, this.zzcbk, this.zzcbl);
        zzanz<zzon> zzanzVarZza = zzabvVar.zza(jSONObject, "app_icon", true, this.zzcbk);
        zzanz<zzaqw> zzanzVarZzc = zzabvVar.zzc(jSONObject, "video");
        zzanz<zzoj> zzanzVarZzg = zzabvVar.zzg(jSONObject);
        ArrayList arrayList = new ArrayList();
        Iterator<zzanz<zzon>> it = listZza.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().get());
        }
        zzaqw zzaqwVarZzc = zzabv.zzc(zzanzVarZzc);
        String string2 = jSONObject.getString("headline");
        if (this.zzbto) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbfr)).booleanValue()) {
                Resources resources = com.google.android.gms.ads.internal.zzbv.zzeo().getResources();
                string = resources != null ? resources.getString(R.string.s7) : "Test Ad";
                if (string2 != null) {
                    string = new StringBuilder(String.valueOf(string).length() + 3 + String.valueOf(string2).length()).append(string).append(" : ").append(string2).toString();
                }
            } else {
                string = string2;
            }
        } else {
            string = string2;
        }
        return new zzoo(string, arrayList, jSONObject.getString("body"), zzanzVarZza.get(), jSONObject.getString("call_to_action"), jSONObject.optDouble("rating", -1.0d), jSONObject.optString(TransactionErrorDetailsUtilities.STORE), jSONObject.optString("price"), zzanzVarZzg.get(), new Bundle(), zzaqwVarZzc != null ? zzaqwVarZzc.zztm() : null, zzaqwVarZzc != null ? zzaqwVarZzc.getView() : null, null, null);
    }
}
