package com.google.android.gms.games.event;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.e;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;
import com.google.firebase.analytics.FirebaseAnalytics;

/* JADX INFO: loaded from: classes.dex */
public final class EventRef extends e implements Event {
    EventRef(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    @Override // com.google.android.gms.games.event.Event
    public final Uri abV() {
        return parseUri("icon_image_uri");
    }

    @Override // com.google.android.gms.games.event.Event
    public final String abW() {
        return getString("icon_image_url");
    }

    @Override // com.google.android.gms.games.event.Event
    public final Player acR() {
        return new PlayerRef(this.mDataHolder, this.mDataRow);
    }

    @Override // com.google.android.gms.games.event.Event
    public final String acX() {
        return getString("external_event_id");
    }

    @Override // com.google.android.gms.games.event.Event
    public final String acY() {
        return getString("formatted_value");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return EventEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ Event freeze() {
        return new EventEntity(this);
    }

    @Override // com.google.android.gms.games.event.Event
    public final String getDescription() {
        return getString("description");
    }

    @Override // com.google.android.gms.games.event.Event
    public final String getName() {
        return getString("name");
    }

    @Override // com.google.android.gms.games.event.Event
    public final long getValue() {
        return getLong(FirebaseAnalytics.Param.VALUE);
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return EventEntity.a(this);
    }

    @Override // com.google.android.gms.games.event.Event
    public final boolean isVisible() {
        return getBoolean("visibility");
    }

    public final String toString() {
        return EventEntity.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((EventEntity) ((Event) freeze())).writeToParcel(parcel, i);
    }
}
