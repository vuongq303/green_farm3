package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.a.a;
import com.google.android.gms.games.a.f;
import com.google.android.gms.games.a.m;

/* JADX INFO: loaded from: classes.dex */
final class zzaz implements m {
    private final /* synthetic */ Status zzbc;

    zzaz(zzay zzayVar, Status status) {
        this.zzbc = status;
    }

    public final a getLeaderboard() {
        return null;
    }

    public final f getScores() {
        return new f(DataHolder.nG(14));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
