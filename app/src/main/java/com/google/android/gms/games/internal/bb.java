package com.google.android.gms.games.internal;

import com.google.android.gms.games.quest.Quest;

/* JADX INFO: loaded from: classes.dex */
final class bb implements com.google.android.gms.common.api.a.x<com.google.android.gms.games.quest.b> {
    private final Quest bUD;

    bb(Quest quest) {
        this.bUD = quest;
    }

    @Override // com.google.android.gms.common.api.a.x
    public final void YU() {
    }

    @Override // com.google.android.gms.common.api.a.x
    public final /* synthetic */ void z(com.google.android.gms.games.quest.b bVar) {
        bVar.c(this.bUD);
    }
}
