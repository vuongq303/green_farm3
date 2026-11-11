package com.google.android.gms.ads.internal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzaaw;
import com.google.android.gms.internal.ads.zzabc;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzahe;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaki;
import com.google.android.gms.internal.ads.zzamu;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzci;
import com.google.android.gms.internal.ads.zzcj;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzke;
import com.google.android.gms.internal.ads.zzkh;
import com.google.android.gms.internal.ads.zzkt;
import com.google.android.gms.internal.ads.zzkx;
import com.google.android.gms.internal.ads.zzla;
import com.google.android.gms.internal.ads.zzlg;
import com.google.android.gms.internal.ads.zzlo;
import com.google.android.gms.internal.ads.zzlu;
import com.google.android.gms.internal.ads.zzmu;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzod;
import java.util.Map;
import java.util.concurrent.Future;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzbp extends zzkt {
    private final Future<zzci> bCd = zzaki.zza(new ac(this));
    private final ae bCe;
    private WebView bCf;
    private zzci bCg;
    private AsyncTask<Void, Void, String> bCh;
    private final Context mContext;
    private final zzjn zzaau;
    private zzkh zzxs;
    private final zzang zzyf;

    public zzbp(Context context, zzjn zzjnVar, String str, zzang zzangVar) {
        this.mContext = context;
        this.zzyf = zzangVar;
        this.zzaau = zzjnVar;
        this.bCf = new WebView(this.mContext);
        this.bCe = new ae(str);
        ne(0);
        this.bCf.setVerticalScrollBarEnabled(false);
        this.bCf.getSettings().setJavaScriptEnabled(true);
        this.bCf.setWebViewClient(new aa(this));
        this.bCf.setOnTouchListener(new ab(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String hG(String str) {
        if (this.bCg == null) {
            return str;
        }
        Uri uriZza = Uri.parse(str);
        try {
            uriZza = this.bCg.zza(uriZza, this.mContext, null, null);
        } catch (zzcj e) {
            zzakb.zzc("Unable to process ad data", e);
        }
        return uriZza.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hH(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        this.mContext.startActivity(intent);
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void destroy() {
        bf.hV("destroy must be called on the main UI thread.");
        this.bCh.cancel(true);
        this.bCd.cancel(true);
        this.bCf.destroy();
        this.bCf = null;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final String getAdUnitId() {
        throw new IllegalStateException("getAdUnitId not implemented");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final String getMediationAdapterClassName() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzlo getVideoController() {
        return null;
    }

    final int hF(String str) {
        String queryParameter = Uri.parse(str).getQueryParameter("height");
        if (TextUtils.isEmpty(queryParameter)) {
            return 0;
        }
        try {
            zzkb.zzif();
            return zzamu.zza(this.mContext, Integer.parseInt(queryParameter));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final boolean isLoading() {
        return false;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final boolean isReady() {
        return false;
    }

    final void ne(int i) {
        if (this.bCf == null) {
            return;
        }
        this.bCf.setLayoutParams(new ViewGroup.LayoutParams(-1, i));
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void pause() {
        bf.hV("pause must be called on the main UI thread.");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void resume() {
        bf.hV("resume must be called on the main UI thread.");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void setImmersiveMode(boolean z) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void setManualImpressionsEnabled(boolean z) {
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void setUserId(String str) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void showInterstitial() {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void stopLoading() {
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzaaw zzaawVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzabc zzabcVar, String str) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzahe zzaheVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzjn zzjnVar) {
        throw new IllegalStateException("AdSize must be set before initialization");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzke zzkeVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzkh zzkhVar) {
        this.zzxs = zzkhVar;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzkx zzkxVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzla zzlaVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzlg zzlgVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzlu zzluVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzmu zzmuVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzod zzodVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final boolean zzb(zzjj zzjjVar) {
        bf.d(this.bCf, "This Search Ad has already been torn down");
        this.bCe.a(zzjjVar, this.zzyf);
        this.bCh = new ad(this, null).execute(new Void[0]);
        return true;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final Bundle zzba() {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final com.google.android.gms.c.a zzbj() {
        bf.hV("getAdFrame must be called on the main UI thread.");
        return com.google.android.gms.c.d.F(this.bCf);
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzjn zzbk() {
        return this.zzaau;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zzbm() {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzla zzbw() {
        throw new IllegalStateException("getIAppEventListener not implemented");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzkh zzbx() {
        throw new IllegalStateException("getIAdListener not implemented");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final String zzck() {
        return null;
    }

    final String zzea() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https://").appendEncodedPath((String) zzkb.zzik().zzd(zznk.zzbcz));
        builder.appendQueryParameter("query", this.bCe.getQuery());
        builder.appendQueryParameter("pubId", this.bCe.Xf());
        Map<String, String> mapXg = this.bCe.Xg();
        for (String str : mapXg.keySet()) {
            builder.appendQueryParameter(str, mapXg.get(str));
        }
        Uri uriBuild = builder.build();
        if (this.bCg != null) {
            try {
                uriBuild = this.bCg.zza(uriBuild, this.mContext);
            } catch (zzcj e) {
                zzakb.zzc("Unable to process ad data", e);
            }
        }
        String strZzeb = zzeb();
        String encodedQuery = uriBuild.getEncodedQuery();
        return new StringBuilder(String.valueOf(strZzeb).length() + 1 + String.valueOf(encodedQuery).length()).append(strZzeb).append("#").append(encodedQuery).toString();
    }

    final String zzeb() {
        String strXe = this.bCe.Xe();
        String str = TextUtils.isEmpty(strXe) ? "www.google.com" : strXe;
        String str2 = (String) zzkb.zzik().zzd(zznk.zzbcz);
        return new StringBuilder(String.valueOf(str).length() + 8 + String.valueOf(str2).length()).append("https://").append(str).append(str2).toString();
    }
}
