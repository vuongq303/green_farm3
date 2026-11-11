package com.google.android.gms.games.multiplayer;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class a extends com.google.android.gms.common.data.j<Invitation> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.j
    protected final String ZX() {
        return "external_invitation_id";
    }

    @Override // com.google.android.gms.common.data.j
    protected final /* synthetic */ Invitation cq(int i, int i2) {
        return new zzb(this.mDataHolder, i, i2);
    }
}
