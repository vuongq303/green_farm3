package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.b;

/* JADX INFO: loaded from: classes.dex */
final class zzcn extends zzcu {
    private final /* synthetic */ b zzkd;
    private final /* synthetic */ String zzkf;
    private final /* synthetic */ String zzkg;
    private final /* synthetic */ SnapshotContents zzkh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcn(zzci zzciVar, w wVar, String str, String str2, b bVar, SnapshotContents snapshotContents) {
        super(wVar, null);
        this.zzkf = str;
        this.zzkg = str2;
        this.zzkd = bVar;
        this.zzkh = snapshotContents;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzkf, this.zzkg, this.zzkd, this.zzkh);
    }
}
