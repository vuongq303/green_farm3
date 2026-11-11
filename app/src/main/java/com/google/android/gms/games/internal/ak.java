package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.realtime.Room;

/* JADX INFO: loaded from: classes.dex */
abstract class ak extends com.google.android.gms.common.api.a.j<com.google.android.gms.games.multiplayer.realtime.j> {
    ak(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected abstract void a(com.google.android.gms.games.multiplayer.realtime.j jVar, Room room, int i);

    @Override // com.google.android.gms.common.api.a.j
    protected /* synthetic */ void a(com.google.android.gms.games.multiplayer.realtime.j jVar, DataHolder dataHolder) {
        a(jVar, i.aa(dataHolder), dataHolder.getStatusCode());
    }
}
