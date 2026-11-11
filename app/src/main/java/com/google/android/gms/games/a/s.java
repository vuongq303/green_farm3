package com.google.android.gms.games.a;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class s extends com.google.android.gms.common.data.e implements i {
    s(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    @Override // com.google.android.gms.games.a.i
    public final int aec() {
        return getInteger("timespan");
    }

    @Override // com.google.android.gms.games.a.i
    public final int aed() {
        return getInteger("collection");
    }

    @Override // com.google.android.gms.games.a.i
    public final boolean aee() {
        return !hasNull("player_raw_score");
    }

    @Override // com.google.android.gms.games.a.i
    public final long aef() {
        if (hasNull("player_raw_score")) {
            return -1L;
        }
        return getLong("player_raw_score");
    }

    @Override // com.google.android.gms.games.a.i
    public final String aeg() {
        return getString("player_display_score");
    }

    @Override // com.google.android.gms.games.a.i
    public final long aeh() {
        if (hasNull("player_rank")) {
            return -1L;
        }
        return getLong("player_rank");
    }

    @Override // com.google.android.gms.games.a.i
    public final String aei() {
        return getString("player_display_rank");
    }

    @Override // com.google.android.gms.games.a.i
    public final String aej() {
        return getString("player_score_tag");
    }

    @Override // com.google.android.gms.games.a.i
    public final long aek() {
        if (hasNull("total_scores")) {
            return -1L;
        }
        return getLong("total_scores");
    }

    @Override // com.google.android.gms.games.a.i
    public final String ael() {
        return getString("top_page_token_next");
    }

    @Override // com.google.android.gms.games.a.i
    public final String aem() {
        return getString("window_page_token_prev");
    }

    @Override // com.google.android.gms.games.a.i
    public final String aen() {
        return getString("window_page_token_next");
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return r.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ i freeze() {
        return new r(this);
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return r.a(this);
    }

    public final String toString() {
        return r.b(this);
    }
}
