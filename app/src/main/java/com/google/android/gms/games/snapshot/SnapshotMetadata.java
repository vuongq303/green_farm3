package com.google.android.gms.games.snapshot;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Player;

/* JADX INFO: loaded from: classes.dex */
public interface SnapshotMetadata extends Parcelable, com.google.android.gms.common.data.k<SnapshotMetadata> {
    Game adP();

    Player afr();

    Uri afs();

    @Deprecated
    String aft();

    float afu();

    String afv();

    long afw();

    long afx();

    boolean afy();

    long afz();

    String getDescription();

    String getDeviceName();

    String getSnapshotId();

    String getTitle();
}
