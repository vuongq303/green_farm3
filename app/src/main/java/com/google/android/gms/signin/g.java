package com.google.android.gms.signin;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.l;
import com.google.android.gms.common.api.y;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.signin.internal.k;

/* JADX INFO: loaded from: classes.dex */
final class g extends com.google.android.gms.common.api.b<k, b> {
    g() {
    }

    @Override // com.google.android.gms.common.api.b
    public final /* synthetic */ l a(Context context, Looper looper, r rVar, b bVar, y yVar, z zVar) {
        return new k(context, looper, false, rVar, bVar.agb(), yVar, zVar);
    }
}
