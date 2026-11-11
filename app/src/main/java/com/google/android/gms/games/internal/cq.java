package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.video.VideoCapabilities;

/* JADX INFO: loaded from: classes.dex */
final class cq implements com.google.android.gms.games.video.d {
    private final Status bUl;
    private final VideoCapabilities bVb;

    cq(Status status, VideoCapabilities videoCapabilities) {
        this.bUl = status;
        this.bVb = videoCapabilities;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }
}
