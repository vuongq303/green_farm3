package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.a.e;
import com.google.android.gms.games.a.l;

/* JADX INFO: loaded from: classes.dex */
final class zzax implements l {
    private final /* synthetic */ Status zzbc;

    zzax(zzaw zzawVar, Status status) {
        this.zzbc = status;
    }

    public final e getScore() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
