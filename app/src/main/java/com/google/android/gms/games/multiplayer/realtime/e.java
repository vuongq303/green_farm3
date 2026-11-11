package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public abstract class e {
    public static Bundle a(int i, int i2, long j) {
        Bundle bundle = new Bundle();
        bundle.putInt("min_automatch_players", i);
        bundle.putInt("max_automatch_players", i2);
        bundle.putLong("exclusive_bit_mask", j);
        return bundle;
    }

    public abstract Bundle aeD();

    @Deprecated
    public abstract j aeF();

    public abstract i aeG();

    @Deprecated
    public abstract h aeH();

    public abstract g aeI();

    @Deprecated
    public abstract b aeJ();

    public abstract a aeK();

    public abstract String[] aeL();

    public abstract String getInvitationId();

    public abstract int getVariant();
}
