package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.a;
import com.google.android.gms.games.multiplayer.d;

/* JADX INFO: loaded from: classes.dex */
final class zzal implements d {
    private final /* synthetic */ Status zzbc;

    zzal(zzak zzakVar, Status status) {
        this.zzbc = status;
    }

    public final a getInvitations() {
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
