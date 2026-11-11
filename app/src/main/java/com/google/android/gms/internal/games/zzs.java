package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.achievement.d;

/* JADX INFO: loaded from: classes.dex */
final class zzs implements d {
    private final /* synthetic */ Status zzbc;
    private final /* synthetic */ zzr zzjj;

    zzs(zzr zzrVar, Status status) {
        this.zzjj = zzrVar;
        this.zzbc = status;
    }

    public final String getAchievementId() {
        return this.zzjj.zzji;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
