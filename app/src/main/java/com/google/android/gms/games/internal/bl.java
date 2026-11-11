package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.realtime.Room;

/* JADX INFO: loaded from: classes.dex */
abstract class bl extends com.google.android.gms.common.api.a.j<com.google.android.gms.games.multiplayer.realtime.h> {
    bl(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected abstract void a(com.google.android.gms.games.multiplayer.realtime.h hVar, Room room);

    @Override // com.google.android.gms.common.api.a.j
    protected /* synthetic */ void a(com.google.android.gms.games.multiplayer.realtime.h hVar, DataHolder dataHolder) {
        a(hVar, i.aa(dataHolder));
    }
}
