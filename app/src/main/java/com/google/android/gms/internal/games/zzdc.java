package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.multiplayer.turnbased.d;

/* JADX INFO: loaded from: classes.dex */
final class zzdc extends zzdo {
    private final /* synthetic */ d zzki;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdc(zzdb zzdbVar, w wVar, d dVar) {
        super(wVar, null);
        this.zzki = dVar;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzki);
    }
}
