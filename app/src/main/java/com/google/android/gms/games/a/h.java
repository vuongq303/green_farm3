package com.google.android.gms.games.a;

import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;

/* JADX INFO: loaded from: classes.dex */
public final class h extends com.google.android.gms.common.data.e implements e {
    private final PlayerRef bVC;

    h(DataHolder dataHolder, int i) {
        super(dataHolder, i);
        this.bVC = new PlayerRef(dataHolder, i);
    }

    @Override // com.google.android.gms.games.a.e
    public final long adQ() {
        return getLong("rank");
    }

    @Override // com.google.android.gms.games.a.e
    public final String adR() {
        return getString("display_rank");
    }

    @Override // com.google.android.gms.games.a.e
    public final String adS() {
        return getString("display_score");
    }

    @Override // com.google.android.gms.games.a.e
    public final long adT() {
        return getLong("raw_score");
    }

    @Override // com.google.android.gms.games.a.e
    public final String adU() {
        return hasNull("external_player_id") ? getString("default_display_name") : this.bVC.getDisplayName();
    }

    @Override // com.google.android.gms.games.a.e
    public final Uri adV() {
        return hasNull("external_player_id") ? parseUri("default_display_image_uri") : this.bVC.abV();
    }

    @Override // com.google.android.gms.games.a.e
    public final String adW() {
        return hasNull("external_player_id") ? getString("default_display_image_url") : this.bVC.abW();
    }

    @Override // com.google.android.gms.games.a.e
    public final Uri adX() {
        if (hasNull("external_player_id")) {
            return null;
        }
        return this.bVC.abX();
    }

    @Override // com.google.android.gms.games.a.e
    public final String adY() {
        if (hasNull("external_player_id")) {
            return null;
        }
        return this.bVC.abY();
    }

    @Override // com.google.android.gms.games.a.e
    public final Player adZ() {
        if (hasNull("external_player_id")) {
            return null;
        }
        return this.bVC;
    }

    @Override // com.google.android.gms.games.a.e
    public final String aea() {
        return getString("score_tag");
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return g.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ e freeze() {
        return new g(this);
    }

    @Override // com.google.android.gms.games.a.e
    public final long getTimestampMillis() {
        return getLong("achieved_timestamp");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return g.a(this);
    }

    public final String toString() {
        return g.b(this);
    }
}
