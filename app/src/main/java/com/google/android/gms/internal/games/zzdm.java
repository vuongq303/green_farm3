package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.multiplayer.turnbased.f;

/* JADX INFO: loaded from: classes.dex */
abstract class zzdm extends g<f> {
    private final String zzji;

    public zzdm(String str, w wVar) {
        super(wVar);
        this.zzji = str;
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzdn(this, status);
    }
}
