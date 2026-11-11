package com.google.android.gms.internal.ads;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaah {
    private final boolean zzbwr;
    private final boolean zzbws;
    private final boolean zzbwt;
    private final boolean zzbwu;
    private final boolean zzbwv;

    private zzaah(zzaaj zzaajVar) {
        this.zzbwr = zzaajVar.zzbwr;
        this.zzbws = zzaajVar.zzbws;
        this.zzbwt = zzaajVar.zzbwt;
        this.zzbwu = zzaajVar.zzbwu;
        this.zzbwv = zzaajVar.zzbwv;
    }

    public final JSONObject zzng() {
        try {
            return new JSONObject().put("sms", this.zzbwr).put("tel", this.zzbws).put("calendar", this.zzbwt).put("storePicture", this.zzbwu).put("inlineVideo", this.zzbwv);
        } catch (JSONException e) {
            zzakb.zzb("Error occured while obtaining the MRAID capabilities.", e);
            return null;
        }
    }
}
