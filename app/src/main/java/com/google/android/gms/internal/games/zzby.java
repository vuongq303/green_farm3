package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.quest.a;
import com.google.android.gms.games.quest.f;

/* JADX INFO: loaded from: classes.dex */
final class zzby implements f {
    private final /* synthetic */ Status zzbc;

    zzby(zzbx zzbxVar, Status status) {
        this.zzbc = status;
    }

    public final a getQuests() {
        return new a(DataHolder.nG(this.zzbc.getStatusCode()));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
