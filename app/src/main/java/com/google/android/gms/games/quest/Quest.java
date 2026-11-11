package com.google.android.gms.games.quest;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.k;
import com.google.android.gms.games.Game;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface Quest extends Parcelable, k<Quest> {
    public static final int[] bWr = {1, 2, 3, 4, 6, 5};
    public static final String[] bWs = {Integer.toString(1), Integer.toString(2), Integer.toString(3)};

    Uri abV();

    @Deprecated
    String abW();

    long acU();

    Game adP();

    String afc();

    Uri afd();

    @Deprecated
    String afe();

    List<Milestone> aff();

    long afg();

    long afh();

    long afi();

    long afj();

    String getDescription();

    String getName();

    int getState();

    int getType();
}
