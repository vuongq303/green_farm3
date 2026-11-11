package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.plus.a.a.a;
import com.google.android.gms.plus.b;
import com.google.android.gms.plus.c;
import com.google.android.gms.plus.d;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public final class zzj implements b {
    public final a getCurrentPerson(w wVar) {
        return d.c(wVar, true).afY();
    }

    public final aa<c> load(w wVar, Collection<String> collection) {
        return wVar.a(new zzn(this, wVar, collection));
    }

    public final aa<c> load(w wVar, String... strArr) {
        return wVar.a(new zzo(this, wVar, strArr));
    }

    public final aa<c> loadConnected(w wVar) {
        return wVar.a(new zzm(this, wVar));
    }

    public final aa<c> loadVisible(w wVar, int i, String str) {
        return wVar.a(new zzk(this, wVar, i, str));
    }

    public final aa<c> loadVisible(w wVar, String str) {
        return wVar.a(new zzl(this, wVar, str));
    }
}
