package com.google.android.gms.games.request;

import android.os.Parcelable;
import com.google.android.gms.common.data.k;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Player;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface GameRequest extends Parcelable, k<GameRequest> {
    Game adP();

    long aeq();

    Player afl();

    byte[] getData();

    long getExpirationTimestamp();

    List<Player> getRecipients();

    String getRequestId();

    int getStatus();

    int getType();

    int ix(String str);
}
