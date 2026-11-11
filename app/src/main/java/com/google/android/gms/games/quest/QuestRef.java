package com.google.android.gms.games.quest;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class QuestRef extends com.google.android.gms.common.data.e implements Quest {
    private final Game bVo;
    private final int zzmz;

    QuestRef(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.bVo = new GameRef(dataHolder, i);
        this.zzmz = i2;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Uri abV() {
        return parseUri("quest_icon_image_uri");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String abW() {
        return getString("quest_icon_image_url");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long acU() {
        return getLong("quest_last_updated_ts");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Game adP() {
        return this.bVo;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String afc() {
        return getString("external_quest_id");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Uri afd() {
        return parseUri("quest_banner_image_uri");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String afe() {
        return getString("quest_banner_image_url");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final List<Milestone> aff() {
        ArrayList arrayList = new ArrayList(this.zzmz);
        for (int i = 0; i < this.zzmz; i++) {
            arrayList.add(new zzb(this.mDataHolder, this.mDataRow + i));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long afg() {
        return getLong("accepted_ts");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long afh() {
        return getLong("quest_end_ts");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long afi() {
        return getLong("notification_ts");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long afj() {
        return getLong("quest_start_ts");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return QuestEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ Quest freeze() {
        return new QuestEntity(this);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getDescription() {
        return getString("quest_description");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getName() {
        return getString("quest_name");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final int getState() {
        return getInteger("quest_state");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final int getType() {
        return getInteger("quest_type");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return QuestEntity.a(this);
    }

    public final String toString() {
        return QuestEntity.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((QuestEntity) ((Quest) freeze())).writeToParcel(parcel, i);
    }
}
