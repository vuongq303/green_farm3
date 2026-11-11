package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.quest.Quest;
import com.google.android.gms.games.quest.QuestEntity;

/* JADX INFO: loaded from: classes.dex */
final class cj extends dc implements com.google.android.gms.games.quest.d {
    private final Quest bUD;

    cj(DataHolder dataHolder) {
        super(dataHolder);
        com.google.android.gms.games.quest.a aVar = new com.google.android.gms.games.quest.a(dataHolder);
        try {
            if (aVar.getCount() > 0) {
                this.bUD = new QuestEntity(aVar.get(0));
            } else {
                this.bUD = null;
            }
        } finally {
            aVar.release();
        }
    }
}
