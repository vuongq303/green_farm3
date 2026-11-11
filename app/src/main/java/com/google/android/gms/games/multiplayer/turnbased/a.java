package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.di;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private final com.google.android.gms.games.multiplayer.a bWb;
    private final c bWc;
    private final c bWd;
    private final c bWe;

    public a(Bundle bundle) {
        DataHolder dataHolderB = b(bundle, 0);
        if (dataHolderB != null) {
            this.bWb = new com.google.android.gms.games.multiplayer.a(dataHolderB);
        } else {
            this.bWb = null;
        }
        DataHolder dataHolderB2 = b(bundle, 1);
        if (dataHolderB2 != null) {
            this.bWc = new c(dataHolderB2);
        } else {
            this.bWc = null;
        }
        DataHolder dataHolderB3 = b(bundle, 2);
        if (dataHolderB3 != null) {
            this.bWd = new c(dataHolderB3);
        } else {
            this.bWd = null;
        }
        DataHolder dataHolderB4 = b(bundle, 3);
        if (dataHolderB4 != null) {
            this.bWe = new c(dataHolderB4);
        } else {
            this.bWe = null;
        }
    }

    private static DataHolder b(Bundle bundle, int i) {
        String str;
        switch (i) {
            case 0:
                str = "TURN_STATUS_INVITED";
                break;
            case 1:
                str = "TURN_STATUS_MY_TURN";
                break;
            case 2:
                str = "TURN_STATUS_THEIR_TURN";
                break;
            case 3:
                str = "TURN_STATUS_COMPLETE";
                break;
            default:
                di.ay("MatchTurnStatus", new StringBuilder(38).append("Unknown match turn status: ").append(i).toString());
                str = "TURN_STATUS_UNKNOWN";
                break;
        }
        if (bundle.containsKey(str)) {
            return (DataHolder) bundle.getParcelable(str);
        }
        return null;
    }

    public final void release() {
        if (this.bWb != null) {
            this.bWb.release();
        }
        if (this.bWc != null) {
            this.bWc.release();
        }
        if (this.bWd != null) {
            this.bWd.release();
        }
        if (this.bWe != null) {
            this.bWe.release();
        }
    }
}
