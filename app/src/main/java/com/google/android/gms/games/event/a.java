package com.google.android.gms.games.event;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class a extends com.google.android.gms.common.data.a<Event> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.c
    /* JADX INFO: renamed from: ok, reason: merged with bridge method [inline-methods] */
    public final Event get(int i) {
        return new EventRef(this.mDataHolder, i);
    }
}
