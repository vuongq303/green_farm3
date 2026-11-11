package com.google.android.gms.games.a;

import android.util.SparseArray;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.be;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.games.zzei;

/* JADX INFO: loaded from: classes.dex */
public final class o {
    private static final String[] zzmt = {"leaderboardId", "playerId", "timeSpan", "hasResult", "rawScore", "formattedScore", "newBest", "scoreTag"};
    private SparseArray<p> bVD = new SparseArray<>();
    private String bVm;
    private int statusCode;
    private String zzby;

    public o(DataHolder dataHolder) {
        this.statusCode = dataHolder.getStatusCode();
        int count = dataHolder.getCount();
        bf.ca(count == 3);
        for (int i = 0; i < count; i++) {
            int iNF = dataHolder.nF(i);
            if (i == 0) {
                this.bVm = dataHolder.r("leaderboardId", i, iNF);
                this.zzby = dataHolder.r("playerId", i, iNF);
            }
            if (dataHolder.s("hasResult", i, iNF)) {
                this.bVD.put(dataHolder.q("timeSpan", i, iNF), new p(dataHolder.p("rawScore", i, iNF), dataHolder.r("formattedScore", i, iNF), dataHolder.r("scoreTag", i, iNF), dataHolder.s("newBest", i, iNF)));
            }
        }
    }

    public final String toString() {
        be beVarC = bd.C(this).c("PlayerId", this.zzby).c("StatusCode", Integer.valueOf(this.statusCode));
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= 3) {
                return beVarC.toString();
            }
            p pVar = this.bVD.get(i2);
            beVarC.c("TimesSpan", zzei.zzn(i2));
            beVarC.c("Result", pVar == null ? "null" : pVar.toString());
            i = i2 + 1;
        }
    }
}
