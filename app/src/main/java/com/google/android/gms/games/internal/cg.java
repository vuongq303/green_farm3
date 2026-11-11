package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class cg implements com.google.android.gms.games.achievement.d {
    private final String bSP;
    private final Status bUl;

    cg(int i, String str) {
        this.bUl = com.google.android.gms.games.n.og(i);
        this.bSP = str;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }
}
