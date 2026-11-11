package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.plus.internal.f;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
final class zzn extends zzp {
    private final /* synthetic */ Collection zzal;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzn(zzj zzjVar, w wVar, Collection collection) {
        super(wVar, null);
        this.zzal = collection;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((f) cVar).a(this, this.zzal);
    }
}
