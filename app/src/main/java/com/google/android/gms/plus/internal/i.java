package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class i extends a {
    private final com.google.android.gms.common.api.a.f<Status> bXX;

    public i(com.google.android.gms.common.api.a.f<Status> fVar) {
        this.bXX = fVar;
    }

    @Override // com.google.android.gms.plus.internal.a
    public final void e(int i, Bundle bundle) {
        this.bXX.setResult(new Status(i, null, bundle != null ? (PendingIntent) bundle.getParcelable(com.google.android.gms.common.internal.e.KEY_PENDING_INTENT) : null));
    }
}
