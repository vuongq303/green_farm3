package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.games.Player;

/* JADX INFO: loaded from: classes.dex */
public interface Participant extends Parcelable, com.google.android.gms.common.data.k<Participant> {
    Uri abV();

    @Deprecated
    String abW();

    Uri abX();

    @Deprecated
    String abY();

    Player acR();

    String aev();

    boolean aew();

    String aex();

    ParticipantResult aey();

    int getCapabilities();

    String getDisplayName();

    int getStatus();
}
