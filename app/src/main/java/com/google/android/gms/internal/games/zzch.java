package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.request.e;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class zzch implements e {
    private final /* synthetic */ Status zzbc;

    zzch(zzcg zzcgVar, Status status) {
        this.zzbc = status;
    }

    public final Set<String> getRequestIds() {
        return null;
    }

    public final int getRequestOutcome(String str) {
        String strValueOf = String.valueOf(str);
        throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unknown request ID ".concat(strValueOf) : new String("Unknown request ID "));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
