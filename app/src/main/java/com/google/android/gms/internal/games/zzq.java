package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.achievement.a;
import com.google.android.gms.games.achievement.c;

/* JADX INFO: loaded from: classes.dex */
final class zzq implements c {
    private final /* synthetic */ Status zzbc;

    zzq(zzp zzpVar, Status status) {
        this.zzbc = status;
    }

    public final a getAchievements() {
        return new a(DataHolder.nG(14));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
