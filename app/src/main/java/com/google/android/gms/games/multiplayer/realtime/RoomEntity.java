package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class RoomEntity extends GamesDowngradeableSafeParcel implements Room {
    public static final Parcelable.Creator<RoomEntity> CREATOR = new f();
    private final long bVQ;
    private final ArrayList<ParticipantEntity> bVS;
    private final String bVZ;
    private final Bundle bWa;
    private final String description;
    private final String zzgt;
    private final int zzoe;
    private final int zzpd;
    private final int zzpe;

    public RoomEntity(Room room) {
        this.zzgt = room.aeB();
        this.bVZ = room.aeC();
        this.bVQ = room.aeq();
        this.zzpd = room.getStatus();
        this.description = room.getDescription();
        this.zzoe = room.getVariant();
        this.bWa = room.aeD();
        ArrayList<Participant> arrayListAet = room.aet();
        int size = arrayListAet.size();
        this.bVS = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.bVS.add((ParticipantEntity) arrayListAet.get(i).freeze());
        }
        this.zzpe = room.aeE();
    }

    RoomEntity(String str, String str2, long j, int i, String str3, int i2, Bundle bundle, ArrayList<ParticipantEntity> arrayList, int i3) {
        this.zzgt = str;
        this.bVZ = str2;
        this.bVQ = j;
        this.zzpd = i;
        this.description = str3;
        this.zzoe = i2;
        this.bWa = bundle;
        this.bVS = arrayList;
        this.zzpe = i3;
    }

    static int a(Room room) {
        return bd.hashCode(room.aeB(), room.aeC(), Long.valueOf(room.aeq()), Integer.valueOf(room.getStatus()), room.getDescription(), Integer.valueOf(room.getVariant()), Integer.valueOf(com.google.android.gms.games.internal.h.y(room.aeD())), room.aet(), Integer.valueOf(room.aeE()));
    }

    static boolean a(Room room, Object obj) {
        if (!(obj instanceof Room)) {
            return false;
        }
        if (room == obj) {
            return true;
        }
        Room room2 = (Room) obj;
        return bd.b(room2.aeB(), room.aeB()) && bd.b(room2.aeC(), room.aeC()) && bd.b(Long.valueOf(room2.aeq()), Long.valueOf(room.aeq())) && bd.b(Integer.valueOf(room2.getStatus()), Integer.valueOf(room.getStatus())) && bd.b(room2.getDescription(), room.getDescription()) && bd.b(Integer.valueOf(room2.getVariant()), Integer.valueOf(room.getVariant())) && com.google.android.gms.games.internal.h.a(room2.aeD(), room.aeD()) && bd.b(room2.aet(), room.aet()) && bd.b(Integer.valueOf(room2.aeE()), Integer.valueOf(room.aeE()));
    }

    static String b(Room room) {
        return bd.C(room).c("RoomId", room.aeB()).c("CreatorId", room.aeC()).c("CreationTimestamp", Long.valueOf(room.aeq())).c("RoomStatus", Integer.valueOf(room.getStatus())).c("Description", room.getDescription()).c("Variant", Integer.valueOf(room.getVariant())).c("AutoMatchCriteria", room.aeD()).c("Participants", room.aet()).c("AutoMatchWaitEstimateSeconds", Integer.valueOf(room.aeE())).toString();
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String aeB() {
        return this.zzgt;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String aeC() {
        return this.bVZ;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final Bundle aeD() {
        return this.bWa;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int aeE() {
        return this.zzpe;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: aeM, reason: merged with bridge method [inline-methods] */
    public final Room freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final long aeq() {
        return this.bVQ;
    }

    @Override // com.google.android.gms.games.multiplayer.h
    public final ArrayList<Participant> aet() {
        return new ArrayList<>(this.bVS);
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int getStatus() {
        return this.zzpd;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int getVariant() {
        return this.zzoe;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (!aas()) {
            int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, aeB(), false);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, aeC(), false);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, aeq());
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 4, getStatus());
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, getDescription(), false);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 6, getVariant());
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, aeD(), false);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 8, aet(), false);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 9, aeE());
            com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
            return;
        }
        parcel.writeString(this.zzgt);
        parcel.writeString(this.bVZ);
        parcel.writeLong(this.bVQ);
        parcel.writeInt(this.zzpd);
        parcel.writeString(this.description);
        parcel.writeInt(this.zzoe);
        parcel.writeBundle(this.bWa);
        int size = this.bVS.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.bVS.get(i2).writeToParcel(parcel, i);
        }
    }
}
