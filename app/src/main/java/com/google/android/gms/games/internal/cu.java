package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class cu implements com.google.android.gms.games.video.f {
    private final Status bUl;
    private final com.google.android.gms.games.video.a bVd;

    cu(Status status, com.google.android.gms.games.video.a aVar) {
        this.bUl = status;
        this.bVd = aVar;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }
}
