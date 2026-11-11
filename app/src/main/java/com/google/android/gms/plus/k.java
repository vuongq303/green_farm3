package com.google.android.gms.plus;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.l;
import com.google.android.gms.common.api.y;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.d.v;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import com.google.android.gms.plus.internal.zzn;

/* JADX INFO: loaded from: classes.dex */
final class k extends com.google.android.gms.common.api.b<com.google.android.gms.plus.internal.f, e> {
    k() {
    }

    @Override // com.google.android.gms.common.api.b
    public final /* synthetic */ l a(Context context, Looper looper, r rVar, e eVar, y yVar, z zVar) {
        e eVar2 = eVar;
        if (eVar2 == null) {
            eVar2 = new e(null);
        }
        return new com.google.android.gms.plus.internal.f(context, looper, rVar, new zzn(rVar.getAccountOrDefault().name, v.b(rVar.aag()), (String[]) eVar2.bXS.toArray(new String[0]), new String[0], context.getPackageName(), context.getPackageName(), null, new PlusCommonExtras()), yVar, zVar);
    }

    @Override // com.google.android.gms.common.api.k
    public final int getPriority() {
        return 2;
    }
}
