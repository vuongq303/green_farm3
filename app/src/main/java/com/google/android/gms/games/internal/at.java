package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.realtime.Room;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class at extends j {
    at(DataHolder dataHolder, String[] strArr) {
        super(dataHolder, strArr);
    }

    @Override // com.google.android.gms.games.internal.j
    protected final void a(com.google.android.gms.games.multiplayer.realtime.h hVar, Room room, ArrayList<String> arrayList) {
        hVar.a(room, arrayList);
    }
}
