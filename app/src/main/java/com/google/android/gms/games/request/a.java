package com.google.android.gms.games.request;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.j;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class a extends j<GameRequest> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.j
    protected final String ZX() {
        return "external_request_id";
    }

    @Override // com.google.android.gms.common.data.j
    protected final /* synthetic */ GameRequest cq(int i, int i2) {
        return new zzb(this.mDataHolder, i, i2);
    }
}
