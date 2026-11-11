package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.a.l;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzab extends l<zzab> {
    private String category;
    private String label;
    private long value;
    private String zztv;

    public final String getAction() {
        return this.zztv;
    }

    public final String getLabel() {
        return this.label;
    }

    public final long getValue() {
        return this.value;
    }

    public final String toString() {
        HashMap map = new HashMap();
        map.put("category", this.category);
        map.put(NativeProtocol.WEB_DIALOG_ACTION, this.zztv);
        map.put("label", this.label);
        map.put(FirebaseAnalytics.Param.VALUE, Long.valueOf(this.value));
        return zza((Object) map);
    }

    public final String zzax() {
        return this.category;
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzab zzabVar = (zzab) lVar;
        if (!TextUtils.isEmpty(this.category)) {
            zzabVar.category = this.category;
        }
        if (!TextUtils.isEmpty(this.zztv)) {
            zzabVar.zztv = this.zztv;
        }
        if (!TextUtils.isEmpty(this.label)) {
            zzabVar.label = this.label;
        }
        if (this.value != 0) {
            zzabVar.value = this.value;
        }
    }
}
