package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.games.Game;

/* JADX INFO: loaded from: classes.dex */
public interface TurnBasedMatch extends Parcelable, com.google.android.gms.common.data.k<TurnBasedMatch>, com.google.android.gms.games.multiplayer.h {
    public static final int[] bWf = {0, 1, 2, 3};

    long acU();

    Game adP();

    String aeC();

    Bundle aeD();

    int aeN();

    String aeO();

    String aeP();

    String aeQ();

    byte[] aeR();

    int aeS();

    boolean aeT();

    String aeU();

    long aeq();

    int aes();

    byte[] getData();

    String getDescription();

    String getMatchId();

    int getStatus();

    int getVariant();

    int getVersion();
}
