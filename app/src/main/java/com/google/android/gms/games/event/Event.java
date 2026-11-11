package com.google.android.gms.games.event;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.k;
import com.google.android.gms.games.Player;

/* JADX INFO: loaded from: classes.dex */
public interface Event extends Parcelable, k<Event> {
    Uri abV();

    @Deprecated
    String abW();

    Player acR();

    String acX();

    String acY();

    String getDescription();

    String getName();

    long getValue();

    boolean isVisible();
}
