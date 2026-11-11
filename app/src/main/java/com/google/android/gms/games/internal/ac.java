package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.stats.PlayerStats;
import com.google.android.gms.games.stats.zza;

/* JADX INFO: loaded from: classes.dex */
final class ac extends dc implements com.google.android.gms.games.stats.c {
    private final PlayerStats bUo;

    ac(DataHolder dataHolder) {
        super(dataHolder);
        com.google.android.gms.games.stats.a aVar = new com.google.android.gms.games.stats.a(dataHolder);
        try {
            if (aVar.getCount() > 0) {
                this.bUo = new zza((PlayerStats) aVar.get(0));
            } else {
                this.bUo = null;
            }
        } finally {
            aVar.release();
        }
    }
}
