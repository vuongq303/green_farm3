package com.google.android.gms.games.quest;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.j;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class a extends j<Quest> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.j
    protected final String ZX() {
        return "external_quest_id";
    }

    @Override // com.google.android.gms.common.data.j
    protected final /* synthetic */ Quest cq(int i, int i2) {
        return new QuestRef(this.mDataHolder, i, i2);
    }
}
