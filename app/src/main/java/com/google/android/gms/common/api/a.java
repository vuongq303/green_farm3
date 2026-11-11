package com.google.android.gms.common.api;

import com.google.android.gms.common.api.e;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class a<O extends e> {
    private final b<?, O> bIq;
    private final o<?, O> bIr;
    private final m<?> bIs;
    private final p<?> bIt;
    private final String mName;

    /* JADX WARN: Multi-variable type inference failed */
    public <C extends l> a(String str, b<C, O> bVar, m<C> mVar) {
        bf.d(bVar, "Cannot construct an Api with a null ClientBuilder");
        bf.d(mVar, "Cannot construct an Api with a null ClientKey");
        this.mName = str;
        this.bIq = bVar;
        this.bIr = null;
        this.bIs = mVar;
        this.bIt = null;
    }

    public final k<?, O> YI() {
        return this.bIq;
    }

    public final b<?, O> YJ() {
        bf.a(this.bIq != null, "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.bIq;
    }

    public final d<?> getClientKey() {
        if (this.bIs != null) {
            return this.bIs;
        }
        throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
    }

    public final String getName() {
        return this.mName;
    }
}
