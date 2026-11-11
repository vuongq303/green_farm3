package com.google.android.gms.games.a;

import android.net.Uri;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class c implements a {
    private final String bGD;
    private final Uri bRF;
    private final String bRK;
    private final String bVm;
    private final ArrayList<r> bVn;
    private final Game bVo;
    private final int zzmw;

    public c(a aVar) {
        this.bVm = aVar.adM();
        this.bGD = aVar.getDisplayName();
        this.bRF = aVar.abV();
        this.bRK = aVar.abW();
        this.zzmw = aVar.adN();
        Game gameAdP = aVar.adP();
        this.bVo = gameAdP == null ? null : new GameEntity(gameAdP);
        ArrayList<i> arrayListAdO = aVar.adO();
        int size = arrayListAdO.size();
        this.bVn = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.bVn.add((r) arrayListAdO.get(i).freeze());
        }
    }

    static int a(a aVar) {
        return bd.hashCode(aVar.adM(), aVar.getDisplayName(), aVar.abV(), Integer.valueOf(aVar.adN()), aVar.adO());
    }

    static boolean a(a aVar, Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        if (aVar == obj) {
            return true;
        }
        a aVar2 = (a) obj;
        return bd.b(aVar2.adM(), aVar.adM()) && bd.b(aVar2.getDisplayName(), aVar.getDisplayName()) && bd.b(aVar2.abV(), aVar.abV()) && bd.b(Integer.valueOf(aVar2.adN()), Integer.valueOf(aVar.adN())) && bd.b(aVar2.adO(), aVar.adO());
    }

    static String b(a aVar) {
        return bd.C(aVar).c("LeaderboardId", aVar.adM()).c("DisplayName", aVar.getDisplayName()).c("IconImageUri", aVar.abV()).c("IconImageUrl", aVar.abW()).c("ScoreOrder", Integer.valueOf(aVar.adN())).c("Variants", aVar.adO()).toString();
    }

    @Override // com.google.android.gms.games.a.a
    public final Uri abV() {
        return this.bRF;
    }

    @Override // com.google.android.gms.games.a.a
    public final String abW() {
        return this.bRK;
    }

    @Override // com.google.android.gms.games.a.a
    public final String adM() {
        return this.bVm;
    }

    @Override // com.google.android.gms.games.a.a
    public final int adN() {
        return this.zzmw;
    }

    @Override // com.google.android.gms.games.a.a
    public final ArrayList<i> adO() {
        return new ArrayList<>(this.bVn);
    }

    @Override // com.google.android.gms.games.a.a
    public final Game adP() {
        return this.bVo;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* bridge */ /* synthetic */ a freeze() {
        if (this == null) {
            throw null;
        }
        return this;
    }

    @Override // com.google.android.gms.games.a.a
    public final String getDisplayName() {
        return this.bGD;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }
}
