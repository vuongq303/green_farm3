package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class GameRef extends com.google.android.gms.common.data.e implements Game {
    public GameRef(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    @Override // com.google.android.gms.games.Game
    public final String abS() {
        return getString("primary_category");
    }

    @Override // com.google.android.gms.games.Game
    public final String abT() {
        return getString("secondary_category");
    }

    @Override // com.google.android.gms.games.Game
    public final String abU() {
        return getString("developer_name");
    }

    @Override // com.google.android.gms.games.Game
    public final Uri abV() {
        return parseUri("game_icon_image_uri");
    }

    @Override // com.google.android.gms.games.Game
    public final String abW() {
        return getString("game_icon_image_url");
    }

    @Override // com.google.android.gms.games.Game
    public final Uri abX() {
        return parseUri("game_hi_res_image_uri");
    }

    @Override // com.google.android.gms.games.Game
    public final String abY() {
        return getString("game_hi_res_image_url");
    }

    @Override // com.google.android.gms.games.Game
    public final Uri abZ() {
        return parseUri("featured_image_uri");
    }

    @Override // com.google.android.gms.games.Game
    public final String aca() {
        return getString("featured_image_url");
    }

    @Override // com.google.android.gms.games.Game
    public final boolean acb() {
        return getBoolean("play_enabled_game");
    }

    @Override // com.google.android.gms.games.Game
    public final String acc() {
        return getString("package_name");
    }

    @Override // com.google.android.gms.games.Game
    public final int acd() {
        return getInteger("achievement_total_count");
    }

    @Override // com.google.android.gms.games.Game
    public final int ace() {
        return getInteger("leaderboard_count");
    }

    @Override // com.google.android.gms.games.Game
    public final boolean acf() {
        return getInteger("real_time_support") > 0;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean acg() {
        return getInteger("turn_based_support") > 0;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean ach() {
        return getInteger("snapshots_enabled") > 0;
    }

    @Override // com.google.android.gms.games.Game
    public final String aci() {
        return getString("theme_color");
    }

    @Override // com.google.android.gms.games.Game
    public final boolean acj() {
        return getInteger("gamepad_support") > 0;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return GameEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ Game freeze() {
        return new GameEntity(this);
    }

    @Override // com.google.android.gms.games.Game
    public final String getApplicationId() {
        return getString("external_game_id");
    }

    @Override // com.google.android.gms.games.Game
    public final String getDescription() {
        return getString("game_description");
    }

    @Override // com.google.android.gms.games.Game
    public final String getDisplayName() {
        return getString("display_name");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return GameEntity.a(this);
    }

    @Override // com.google.android.gms.games.Game
    public final boolean isMuted() {
        return getBoolean("muted");
    }

    public final String toString() {
        return GameEntity.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((GameEntity) ((Game) freeze())).writeToParcel(parcel, i);
    }

    @Override // com.google.android.gms.games.Game
    public final boolean zzb() {
        return getBoolean("identity_sharing_confirmed");
    }

    @Override // com.google.android.gms.games.Game
    public final boolean zzc() {
        return getInteger("installed") > 0;
    }
}
