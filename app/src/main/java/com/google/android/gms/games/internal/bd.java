package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.quest.Quest;

/* JADX INFO: loaded from: classes.dex */
final class bd extends a {
    private final com.google.android.gms.common.api.a.v<com.google.android.gms.games.quest.b> bUc;

    bd(com.google.android.gms.common.api.a.v<com.google.android.gms.games.quest.b> vVar) {
        this.bUc = vVar;
    }

    private static Quest ac(DataHolder dataHolder) {
        com.google.android.gms.games.quest.a aVar = new com.google.android.gms.games.quest.a(dataHolder);
        try {
            return aVar.getCount() > 0 ? aVar.get(0).freeze() : null;
        } finally {
            aVar.release();
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void K(DataHolder dataHolder) {
        Quest questAc = ac(dataHolder);
        if (questAc != null) {
            this.bUc.a(new bb(questAc));
        }
    }
}
