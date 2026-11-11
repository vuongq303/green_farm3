package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class ab extends dc implements com.google.android.gms.games.a.l {
    private final com.google.android.gms.games.a.g bUn;

    ab(DataHolder dataHolder) {
        super(dataHolder);
        com.google.android.gms.games.a.f fVar = new com.google.android.gms.games.a.f(dataHolder);
        try {
            if (fVar.getCount() > 0) {
                this.bUn = (com.google.android.gms.games.a.g) ((com.google.android.gms.games.a.e) fVar.get(0)).freeze();
            } else {
                this.bUn = null;
            }
        } finally {
            fVar.release();
        }
    }
}
