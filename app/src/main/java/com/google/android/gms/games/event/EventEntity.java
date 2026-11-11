package com.google.android.gms.games.event;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class EventEntity extends zzd implements Event {
    public static final Parcelable.Creator<EventEntity> CREATOR = new d();
    private final boolean bKy;
    private final Uri bRF;
    private final String bRK;
    private final PlayerEntity bSU;
    private final String bSZ;
    private final String bTa;
    private final String description;
    private final String name;
    private final long value;

    public EventEntity(Event event) {
        this.bSZ = event.acX();
        this.name = event.getName();
        this.description = event.getDescription();
        this.bRF = event.abV();
        this.bRK = event.abW();
        this.bSU = (PlayerEntity) event.acR().freeze();
        this.value = event.getValue();
        this.bTa = event.acY();
        this.bKy = event.isVisible();
    }

    EventEntity(String str, String str2, String str3, Uri uri, String str4, Player player, long j, String str5, boolean z) {
        this.bSZ = str;
        this.name = str2;
        this.description = str3;
        this.bRF = uri;
        this.bRK = str4;
        this.bSU = new PlayerEntity(player);
        this.value = j;
        this.bTa = str5;
        this.bKy = z;
    }

    static int a(Event event) {
        return bd.hashCode(event.acX(), event.getName(), event.getDescription(), event.abV(), event.abW(), event.acR(), Long.valueOf(event.getValue()), event.acY(), Boolean.valueOf(event.isVisible()));
    }

    static boolean a(Event event, Object obj) {
        if (!(obj instanceof Event)) {
            return false;
        }
        if (event == obj) {
            return true;
        }
        Event event2 = (Event) obj;
        return bd.b(event2.acX(), event.acX()) && bd.b(event2.getName(), event.getName()) && bd.b(event2.getDescription(), event.getDescription()) && bd.b(event2.abV(), event.abV()) && bd.b(event2.abW(), event.abW()) && bd.b(event2.acR(), event.acR()) && bd.b(Long.valueOf(event2.getValue()), Long.valueOf(event.getValue())) && bd.b(event2.acY(), event.acY()) && bd.b(Boolean.valueOf(event2.isVisible()), Boolean.valueOf(event.isVisible()));
    }

    static String b(Event event) {
        return bd.C(event).c("Id", event.acX()).c("Name", event.getName()).c("Description", event.getDescription()).c("IconImageUri", event.abV()).c("IconImageUrl", event.abW()).c("Player", event.acR()).c("Value", Long.valueOf(event.getValue())).c("FormattedValue", event.acY()).c("isVisible", Boolean.valueOf(event.isVisible())).toString();
    }

    @Override // com.google.android.gms.games.event.Event
    public final Uri abV() {
        return this.bRF;
    }

    @Override // com.google.android.gms.games.event.Event
    public final String abW() {
        return this.bRK;
    }

    @Override // com.google.android.gms.games.event.Event
    public final Player acR() {
        return this.bSU;
    }

    @Override // com.google.android.gms.games.event.Event
    public final String acX() {
        return this.bSZ;
    }

    @Override // com.google.android.gms.games.event.Event
    public final String acY() {
        return this.bTa;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: acZ, reason: merged with bridge method [inline-methods] */
    public final Event freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.event.Event
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.event.Event
    public final String getName() {
        return this.name;
    }

    @Override // com.google.android.gms.games.event.Event
    public final long getValue() {
        return this.value;
    }

    public final int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.games.event.Event
    public final boolean isVisible() {
        return this.bKy;
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, acX(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) abV(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, abW(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, (Parcelable) acR(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, getValue());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, acY(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, isVisible());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
