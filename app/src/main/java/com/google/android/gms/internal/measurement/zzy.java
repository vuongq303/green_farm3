package com.google.android.gms.internal.measurement;

import com.google.android.gms.a.l;
import com.google.android.gms.common.internal.bf;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzy extends l<zzy> {
    private final Map<String, Object> zzsi = new HashMap();

    public final void set(String str, String str2) {
        bf.id(str);
        if (str != null && str.startsWith("&")) {
            str = str.substring(1);
        }
        bf.d(str, (Object) "Name can not be empty or \"&\"");
        this.zzsi.put(str, str2);
    }

    public final String toString() {
        return zza((Object) this.zzsi);
    }

    public final Map<String, Object> zzas() {
        return Collections.unmodifiableMap(this.zzsi);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzy zzyVar = (zzy) lVar;
        bf.checkNotNull(zzyVar);
        zzyVar.zzsi.putAll(this.zzsi);
    }
}
