package com.google.android.gms.common.api.a;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.e;

/* JADX INFO: loaded from: classes.dex */
public final class dk<O extends com.google.android.gms.common.api.e> extends com.google.android.gms.common.api.t<O> {
    private final com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bIJ;
    private final com.google.android.gms.common.api.l bME;
    private final de bMF;
    private final com.google.android.gms.common.internal.r zzgf;

    public dk(Context context, com.google.android.gms.common.api.a<O> aVar, Looper looper, com.google.android.gms.common.api.l lVar, de deVar, com.google.android.gms.common.internal.r rVar, com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bVar) {
        super(context, aVar, looper);
        this.bME = lVar;
        this.bMF = deVar;
        this.zzgf = rVar;
        this.bIJ = bVar;
        this.zzcq.b(this);
    }

    public final com.google.android.gms.common.api.l Zb() {
        return this.bME;
    }

    @Override // com.google.android.gms.common.api.t
    public final cc zza(Context context, Handler handler) {
        return new cc(context, handler, this.zzgf, this.bIJ);
    }

    @Override // com.google.android.gms.common.api.t
    public final com.google.android.gms.common.api.l zza(Looper looper, m<O> mVar) {
        this.bMF.a(mVar);
        return this.bME;
    }
}
