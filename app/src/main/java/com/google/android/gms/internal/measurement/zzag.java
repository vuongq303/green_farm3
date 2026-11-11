package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.google.android.gms.a.l;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzag extends l<zzag> {
    public String mCategory;
    public String zzuq;
    public long zzur;
    public String zzus;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("variableName", this.zzuq);
        map.put("timeInMillis", Long.valueOf(this.zzur));
        map.put("category", this.mCategory);
        map.put("label", this.zzus);
        return zza((Object) map);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzag zzagVar = (zzag) lVar;
        if (!TextUtils.isEmpty(this.zzuq)) {
            zzagVar.zzuq = this.zzuq;
        }
        if (this.zzur != 0) {
            zzagVar.zzur = this.zzur;
        }
        if (!TextUtils.isEmpty(this.mCategory)) {
            zzagVar.mCategory = this.mCategory;
        }
        if (TextUtils.isEmpty(this.zzus)) {
            return;
        }
        zzagVar.zzus = this.zzus;
    }
}
