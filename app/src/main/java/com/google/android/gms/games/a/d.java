package com.google.android.gms.games.a;

import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class d extends com.google.android.gms.common.data.e implements a {
    private final Game bVo;
    private final int zzmz;

    d(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zzmz = i2;
        this.bVo = new GameRef(dataHolder, i);
    }

    @Override // com.google.android.gms.games.a.a
    public final Uri abV() {
        return parseUri("board_icon_image_uri");
    }

    @Override // com.google.android.gms.games.a.a
    public final String abW() {
        return getString("board_icon_image_url");
    }

    @Override // com.google.android.gms.games.a.a
    public final String adM() {
        return getString("external_leaderboard_id");
    }

    @Override // com.google.android.gms.games.a.a
    public final int adN() {
        return getInteger("score_order");
    }

    @Override // com.google.android.gms.games.a.a
    public final ArrayList<i> adO() {
        ArrayList<i> arrayList = new ArrayList<>(this.zzmz);
        for (int i = 0; i < this.zzmz; i++) {
            arrayList.add(new s(this.mDataHolder, this.mDataRow + i));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.games.a.a
    public final Game adP() {
        return this.bVo;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return c.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ a freeze() {
        return new c(this);
    }

    @Override // com.google.android.gms.games.a.a
    public final String getDisplayName() {
        return getString("name");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return c.a(this);
    }

    public final String toString() {
        return c.b(this);
    }
}
