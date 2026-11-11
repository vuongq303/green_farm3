package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.event.a;
import com.google.android.gms.games.event.c;

/* JADX INFO: loaded from: classes.dex */
final class zzaa implements c {
    private final /* synthetic */ Status zzbc;

    zzaa(zzz zzzVar, Status status) {
        this.zzbc = status;
    }

    public final a getEvents() {
        return new a(DataHolder.nG(14));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
