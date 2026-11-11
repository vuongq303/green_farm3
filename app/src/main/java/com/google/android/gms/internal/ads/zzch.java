package com.google.android.gms.internal.ads;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public final class zzch extends zzcg {
    private zzch(Context context, String str, boolean z) {
        super(context, str, z);
    }

    public static zzch zza(String str, Context context, boolean z) {
        zza(context, z);
        return new zzch(context, str, z);
    }

    @Override // com.google.android.gms.internal.ads.zzcg
    protected final List<Callable<Void>> zza(zzcz zzczVar, zzba zzbaVar, zzax zzaxVar) {
        if (zzczVar.zzab() == null || !this.zzqt) {
            return super.zza(zzczVar, zzbaVar, zzaxVar);
        }
        int iZzx = zzczVar.zzx();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(super.zza(zzczVar, zzbaVar, zzaxVar));
        arrayList.add(new zzds(zzczVar, "1QeH3Cf7T53ayw17Ebbo9YTdhU+IFx0X5nCtC5gZQym4uicOVPXxYWmMK9k58i8n", "bHJRpFJ+2R5LAbYQUBDMyfYpLd1oiGixlpIqMJOBQPY=", zzbaVar, iZzx, 24));
        return arrayList;
    }
}
