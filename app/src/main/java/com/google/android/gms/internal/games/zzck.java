package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzck extends zzcu {
    private final /* synthetic */ String zzka;
    private final /* synthetic */ boolean zzkb;
    private final /* synthetic */ int zzkc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzck(zzci zzciVar, w wVar, String str, boolean z, int i) {
        super(wVar, null);
        this.zzka = str;
        this.zzkb = z;
        this.zzkc = i;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzka, this.zzkb, this.zzkc);
    }
}
