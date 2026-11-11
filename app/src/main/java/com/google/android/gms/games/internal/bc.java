package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class bc extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.e> bUE;
    private final String zzho;

    public bc(com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.e> fVar, String str) {
        this.bUE = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
        this.zzho = (String) com.google.android.gms.common.internal.bf.d(str, (Object) "MilestoneId must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void I(DataHolder dataHolder) {
        this.bUE.setResult(new cv(dataHolder, this.zzho));
    }
}
