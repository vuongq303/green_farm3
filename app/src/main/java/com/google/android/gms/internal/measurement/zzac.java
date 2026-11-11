package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.google.android.gms.a.l;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzac extends l<zzac> {
    public String zztw;
    public boolean zztx;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("description", this.zztw);
        map.put("fatal", Boolean.valueOf(this.zztx));
        return zza((Object) map);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzac zzacVar = (zzac) lVar;
        if (!TextUtils.isEmpty(this.zztw)) {
            zzacVar.zztw = this.zztw;
        }
        if (this.zztx) {
            zzacVar.zztx = this.zztx;
        }
    }
}
