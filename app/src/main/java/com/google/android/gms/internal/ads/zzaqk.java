package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzaqk implements Runnable {
    private final /* synthetic */ String val$message;
    private final /* synthetic */ String zzcce;
    private final /* synthetic */ String zzdba;
    private final /* synthetic */ zzaqh zzdbe;
    private final /* synthetic */ String zzdbf;

    zzaqk(zzaqh zzaqhVar, String str, String str2, String str3, String str4) {
        this.zzdbe = zzaqhVar;
        this.zzcce = str;
        this.zzdba = str2;
        this.zzdbf = str3;
        this.val$message = str4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        HashMap map = new HashMap();
        map.put("event", "precacheCanceled");
        map.put("src", this.zzcce);
        if (!TextUtils.isEmpty(this.zzdba)) {
            map.put("cachedSrc", this.zzdba);
        }
        zzaqh zzaqhVar = this.zzdbe;
        map.put("type", zzaqh.zzdq(this.zzdbf));
        map.put("reason", this.zzdbf);
        if (!TextUtils.isEmpty(this.val$message)) {
            map.put(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, this.val$message);
        }
        this.zzdbe.zza("onPrecacheEvent", (Map<String, String>) map);
    }
}
