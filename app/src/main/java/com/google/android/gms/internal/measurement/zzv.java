package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.a.l;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzv extends l<zzv> {
    private String name;
    private String zznj;
    private String zztf;
    private String zztg;
    private String zzth;
    private String zzti;
    private String zztj;
    private String zztk;
    private String zztl;
    private String zztm;

    public final String getId() {
        return this.zznj;
    }

    public final String getName() {
        return this.name;
    }

    public final String getSource() {
        return this.zztf;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final String toString() {
        HashMap map = new HashMap();
        map.put("name", this.name);
        map.put("source", this.zztf);
        map.put(FirebaseAnalytics.Param.MEDIUM, this.zztg);
        map.put("keyword", this.zzth);
        map.put(FirebaseAnalytics.Param.CONTENT, this.zzti);
        map.put(ShareConstants.WEB_DIALOG_PARAM_ID, this.zznj);
        map.put("adNetworkId", this.zztj);
        map.put("gclid", this.zztk);
        map.put("dclid", this.zztl);
        map.put(FirebaseAnalytics.Param.ACLID, this.zztm);
        return zza((Object) map);
    }

    public final String zzaj() {
        return this.zztg;
    }

    public final String zzak() {
        return this.zzth;
    }

    public final String zzal() {
        return this.zzti;
    }

    public final String zzam() {
        return this.zztj;
    }

    public final String zzan() {
        return this.zztk;
    }

    public final String zzao() {
        return this.zztl;
    }

    public final String zzap() {
        return this.zztm;
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzv zzvVar = (zzv) lVar;
        if (!TextUtils.isEmpty(this.name)) {
            zzvVar.name = this.name;
        }
        if (!TextUtils.isEmpty(this.zztf)) {
            zzvVar.zztf = this.zztf;
        }
        if (!TextUtils.isEmpty(this.zztg)) {
            zzvVar.zztg = this.zztg;
        }
        if (!TextUtils.isEmpty(this.zzth)) {
            zzvVar.zzth = this.zzth;
        }
        if (!TextUtils.isEmpty(this.zzti)) {
            zzvVar.zzti = this.zzti;
        }
        if (!TextUtils.isEmpty(this.zznj)) {
            zzvVar.zznj = this.zznj;
        }
        if (!TextUtils.isEmpty(this.zztj)) {
            zzvVar.zztj = this.zztj;
        }
        if (!TextUtils.isEmpty(this.zztk)) {
            zzvVar.zztk = this.zztk;
        }
        if (!TextUtils.isEmpty(this.zztl)) {
            zzvVar.zztl = this.zztl;
        }
        if (TextUtils.isEmpty(this.zztm)) {
            return;
        }
        zzvVar.zztm = this.zztm;
    }

    public final void zzc(String str) {
        this.zztf = str;
    }

    public final void zzd(String str) {
        this.zztg = str;
    }

    public final void zze(String str) {
        this.zzth = str;
    }

    public final void zzf(String str) {
        this.zzti = str;
    }

    public final void zzg(String str) {
        this.zznj = str;
    }

    public final void zzh(String str) {
        this.zztj = str;
    }

    public final void zzi(String str) {
        this.zztk = str;
    }

    public final void zzj(String str) {
        this.zztl = str;
    }

    public final void zzk(String str) {
        this.zztm = str;
    }
}
