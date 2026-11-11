package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.b;

/* JADX INFO: loaded from: classes.dex */
final class zzcl extends zzco {
    private final /* synthetic */ Snapshot zzef;
    private final /* synthetic */ b zzkd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcl(zzci zzciVar, w wVar, Snapshot snapshot, b bVar) {
        super(wVar, null);
        this.zzef = snapshot;
        this.zzkd = bVar;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzef, this.zzkd);
    }
}
