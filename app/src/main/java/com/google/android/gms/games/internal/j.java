package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.realtime.Room;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
abstract class j extends bl {
    private final ArrayList<String> bUb;

    j(DataHolder dataHolder, String[] strArr) {
        super(dataHolder);
        this.bUb = new ArrayList<>();
        for (String str : strArr) {
            this.bUb.add(str);
        }
    }

    @Override // com.google.android.gms.games.internal.bl
    protected final void a(com.google.android.gms.games.multiplayer.realtime.h hVar, Room room) {
        a(hVar, room, this.bUb);
    }

    protected abstract void a(com.google.android.gms.games.multiplayer.realtime.h hVar, Room room, ArrayList<String> arrayList);
}
