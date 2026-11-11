package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;

/* JADX INFO: loaded from: classes.dex */
class cd extends dc {
    private final TurnBasedMatch bUv;

    cd(DataHolder dataHolder) {
        super(dataHolder);
        com.google.android.gms.games.multiplayer.turnbased.c cVar = new com.google.android.gms.games.multiplayer.turnbased.c(dataHolder);
        try {
            if (cVar.getCount() > 0) {
                this.bUv = cVar.get(0).freeze();
            } else {
                this.bUv = null;
            }
        } finally {
            cVar.release();
        }
    }
}
