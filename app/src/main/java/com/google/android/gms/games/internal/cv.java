package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.quest.Milestone;
import com.google.android.gms.games.quest.Quest;
import com.google.android.gms.games.quest.QuestEntity;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class cv extends dc implements com.google.android.gms.games.quest.e {
    private final Quest bUD;
    private final Milestone bVe;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    cv(DataHolder dataHolder, String str) {
        super(dataHolder);
        com.google.android.gms.games.quest.a aVar = new com.google.android.gms.games.quest.a(dataHolder);
        try {
            if (aVar.getCount() > 0) {
                this.bUD = new QuestEntity(aVar.get(0));
                List<Milestone> listAff = this.bUD.aff();
                int size = listAff.size();
                for (int i = 0; i < size; i++) {
                    if (listAff.get(i).aeX().equals(str)) {
                        this.bVe = listAff.get(i);
                        aVar.release();
                        return;
                    }
                }
                this.bVe = null;
            } else {
                this.bVe = null;
                this.bUD = null;
            }
            aVar.release();
        } catch (Throwable th) {
            aVar.release();
            throw th;
        }
    }
}
