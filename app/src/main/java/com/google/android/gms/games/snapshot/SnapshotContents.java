package com.google.android.gms.games.snapshot;

import android.os.Parcelable;
import com.google.android.gms.drive.Contents;

/* JADX INFO: loaded from: classes.dex */
public interface SnapshotContents extends Parcelable {
    Contents afp();

    void close();

    boolean isClosed();
}
