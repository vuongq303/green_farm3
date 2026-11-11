package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.video.VideoCapabilities;

/* JADX INFO: loaded from: classes.dex */
final class cp extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.d> bUf;

    cp(com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.d> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void a(int i, VideoCapabilities videoCapabilities) {
        this.bUf.setResult(new cq(new Status(i), videoCapabilities));
    }
}
