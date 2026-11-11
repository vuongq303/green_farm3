package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzar extends zzay {
    private final /* synthetic */ String zzbq;
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ int zzjm;
    private final /* synthetic */ int zzjn;
    private final /* synthetic */ int zzjo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzar(zzam zzamVar, w wVar, String str, int i, int i2, int i3, boolean z) {
        super(wVar, null);
        this.zzbq = str;
        this.zzjm = i;
        this.zzjn = i2;
        this.zzjo = i3;
        this.zzjg = z;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).b(this, this.zzbq, this.zzjm, this.zzjn, this.zzjo, this.zzjg);
    }
}
