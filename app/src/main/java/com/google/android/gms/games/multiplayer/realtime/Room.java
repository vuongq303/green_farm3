package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public interface Room extends Parcelable, com.google.android.gms.common.data.k<Room>, com.google.android.gms.games.multiplayer.h {
    String aeB();

    String aeC();

    Bundle aeD();

    int aeE();

    long aeq();

    String getDescription();

    int getStatus();

    int getVariant();
}
