package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class h extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.plus.c> bXX;

    public h(com.google.android.gms.common.api.a.f<com.google.android.gms.plus.c> fVar) {
        this.bXX = fVar;
    }

    @Override // com.google.android.gms.plus.internal.a
    public final void a(DataHolder dataHolder, String str) {
        Status status = new Status(dataHolder.getStatusCode(), null, dataHolder.ZW() != null ? (PendingIntent) dataHolder.ZW().getParcelable(com.google.android.gms.common.internal.e.KEY_PENDING_INTENT) : null);
        if (!status.Sp() && dataHolder != null) {
            if (!dataHolder.isClosed()) {
                dataHolder.close();
            }
            dataHolder = null;
        }
        this.bXX.setResult(new g(status, dataHolder, str));
    }
}
