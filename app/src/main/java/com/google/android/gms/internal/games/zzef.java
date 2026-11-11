package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.video.VideoCapabilities;
import com.google.android.gms.games.video.d;

/* JADX INFO: loaded from: classes.dex */
final class zzef implements d {
    private final /* synthetic */ Status zzbc;

    zzef(zzee zzeeVar, Status status) {
        this.zzbc = status;
    }

    public final VideoCapabilities getCapabilities() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
