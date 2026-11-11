package com.google.android.gms.internal.ads;

import com.gameloft.glads.AndroidWebView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzwk {
    private static final Charset UTF_8 = Charset.forName(AndroidWebView.UTF_8);
    public static final zzwj<JSONObject> zzbrc = new zzwm();
    private static final zzwh<InputStream> zzbrd = zzwl.zzbre;

    static final /* synthetic */ InputStream zzf(JSONObject jSONObject) {
        return new ByteArrayInputStream(jSONObject.toString().getBytes(UTF_8));
    }
}
