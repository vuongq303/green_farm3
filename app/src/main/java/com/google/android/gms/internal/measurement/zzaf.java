package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.a.l;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzaf extends l<zzaf> {
    public String zzun;
    public String zzuo;
    public String zzup;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("network", this.zzun);
        map.put(NativeProtocol.WEB_DIALOG_ACTION, this.zzuo);
        map.put("target", this.zzup);
        return zza((Object) map);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzaf zzafVar = (zzaf) lVar;
        if (!TextUtils.isEmpty(this.zzun)) {
            zzafVar.zzun = this.zzun;
        }
        if (!TextUtils.isEmpty(this.zzuo)) {
            zzafVar.zzuo = this.zzuo;
        }
        if (TextUtils.isEmpty(this.zzup)) {
            return;
        }
        zzafVar.zzup = this.zzup;
    }
}
