package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.achievement.d;
import com.google.android.gms.games.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzr extends g<d> {
    private final String zzji;

    public zzr(String str, w wVar) {
        super(wVar);
        this.zzji = str;
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzs(this, status);
    }
}
