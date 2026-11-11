package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.games.internal.player.zza;

/* JADX INFO: loaded from: classes.dex */
public interface Player extends Parcelable, com.google.android.gms.common.data.k<Player> {
    Uri abV();

    @Deprecated
    String abW();

    Uri abX();

    @Deprecated
    String abY();

    @Deprecated
    String acA();

    int acB();

    long acC();

    String acq();

    String acr();

    long acs();

    long act();

    boolean acu();

    PlayerLevelInfo acv();

    zza acw();

    Uri acx();

    @Deprecated
    String acy();

    Uri acz();

    String getDisplayName();

    String getName();

    String getTitle();

    boolean isMuted();

    boolean zzh();

    @Deprecated
    int zzi();
}
