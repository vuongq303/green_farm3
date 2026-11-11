package com.google.android.gms.games.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class ct extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.f> bUf;

    public ct(com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.f> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void k(int i, Bundle bundle) {
        this.bUf.setResult(new cu(new Status(i), com.google.android.gms.games.video.a.z(bundle)));
    }
}
