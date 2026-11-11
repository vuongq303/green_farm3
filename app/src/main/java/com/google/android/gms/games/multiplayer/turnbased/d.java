package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public abstract class d {
    public static Bundle a(int i, int i2, long j) {
        Bundle bundle = new Bundle();
        bundle.putInt("min_automatch_players", i);
        bundle.putInt("max_automatch_players", i2);
        bundle.putLong("exclusive_bit_mask", j);
        return bundle;
    }

    public abstract Bundle aeD();

    public abstract String[] aeL();

    public abstract int aeV();

    public abstract int getVariant();
}
