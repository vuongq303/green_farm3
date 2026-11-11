package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.realtime.Room;

/* JADX INFO: loaded from: classes.dex */
final class bp extends ak {
    bp(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.games.internal.ak
    public final void a(com.google.android.gms.games.multiplayer.realtime.j jVar, Room room, int i) {
        jVar.c(i, room);
    }
}
