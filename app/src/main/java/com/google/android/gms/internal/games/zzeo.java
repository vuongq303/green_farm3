package com.google.android.gms.internal.games;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzeo {
    private HashMap<String, Integer> zzmu = new HashMap<>();
    private int statusCode = 0;

    public final zzem zzca() {
        return new zzem(this.statusCode, this.zzmu);
    }

    public final zzeo zzh(String str, int i) {
        boolean z;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            this.zzmu.put(str, Integer.valueOf(i));
        }
        return this;
    }

    public final zzeo zzo(int i) {
        this.statusCode = i;
        return this;
    }
}
