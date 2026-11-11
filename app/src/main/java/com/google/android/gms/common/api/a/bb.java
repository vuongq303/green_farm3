package com.google.android.gms.common.api.a;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class bb implements bh {
    private final bi bKe;

    public bb(bi biVar) {
        this.bKe = biVar;
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t) {
        this.bKe.bLd.bKI.add(t);
        return t;
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void a(ConnectionResult connectionResult, com.google.android.gms.common.api.a<?> aVar, boolean z) {
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void begin() {
        Iterator<com.google.android.gms.common.api.l> it = this.bKe.bKO.values().iterator();
        while (it.hasNext()) {
            it.next().disconnect();
        }
        this.bKe.bLd.bKP = Collections.emptySet();
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void connect() {
        this.bKe.ZC();
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final boolean disconnect() {
        return true;
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void onConnected(Bundle bundle) {
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void onConnectionSuspended(int i) {
    }
}
