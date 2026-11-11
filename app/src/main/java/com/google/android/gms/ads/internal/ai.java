package com.google.android.gms.ads.internal;

import android.webkit.CookieManager;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class ai implements Callable<String> {
    private final /* synthetic */ zzd bDs;

    ai(zzd zzdVar) {
        this.bDs = zzdVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() {
        CookieManager cookieManagerZzax;
        return (!((Boolean) zzkb.zzik().zzd(zznk.zzbdj)).booleanValue() || (cookieManagerZzax = zzbv.zzem().zzax(this.bDs.zzvw.zzrt)) == null) ? "" : cookieManagerZzax.getCookie("googleads.g.doubleclick.net");
    }
}
