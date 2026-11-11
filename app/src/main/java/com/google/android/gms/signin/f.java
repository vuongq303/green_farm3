package com.google.android.gms.signin;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.l;
import com.google.android.gms.common.api.y;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.signin.internal.k;

/* JADX INFO: loaded from: classes.dex */
final class f extends com.google.android.gms.common.api.b<k, d> {
    f() {
    }

    @Override // com.google.android.gms.common.api.b
    public final /* synthetic */ l a(Context context, Looper looper, r rVar, d dVar, y yVar, z zVar) {
        d dVar2 = dVar;
        return new k(context, looper, true, rVar, dVar2 == null ? d.bYk : dVar2, yVar, zVar);
    }
}
