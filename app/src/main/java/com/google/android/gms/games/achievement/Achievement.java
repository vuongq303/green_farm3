package com.google.android.gms.games.achievement;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.k;
import com.google.android.gms.games.Player;

/* JADX INFO: loaded from: classes.dex */
public interface Achievement extends Parcelable, k<Achievement> {
    Uri acL();

    @Deprecated
    String acM();

    Uri acN();

    @Deprecated
    String acO();

    int acP();

    String acQ();

    Player acR();

    int acS();

    String acT();

    long acU();

    long acV();

    String getAchievementId();

    String getDescription();

    String getName();

    int getState();

    int getType();
}
