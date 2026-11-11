package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class by extends dc implements com.google.android.gms.games.a.n {
    private final com.google.android.gms.games.a.o bUR;

    public by(DataHolder dataHolder) {
        super(dataHolder);
        try {
            this.bUR = new com.google.android.gms.games.a.o(dataHolder);
        } finally {
            dataHolder.close();
        }
    }
}
