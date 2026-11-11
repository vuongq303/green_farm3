package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.video.a;
import com.google.android.gms.games.video.f;

/* JADX INFO: loaded from: classes.dex */
final class zzeh implements f {
    private final /* synthetic */ Status zzbc;

    zzeh(zzeg zzegVar, Status status) {
        this.zzbc = status;
    }

    public final a getCaptureState() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
