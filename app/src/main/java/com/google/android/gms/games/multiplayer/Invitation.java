package com.google.android.gms.games.multiplayer;

import android.os.Parcelable;
import com.google.android.gms.games.Game;

/* JADX INFO: loaded from: classes.dex */
public interface Invitation extends Parcelable, com.google.android.gms.common.data.k<Invitation>, h {
    Game adP();

    Participant aep();

    long aeq();

    int aer();

    int aes();

    String getInvitationId();

    int getVariant();
}
