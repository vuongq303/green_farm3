package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class TurnBasedMatchEntity extends com.google.android.gms.games.internal.zzd implements TurnBasedMatch {
    public static final Parcelable.Creator<TurnBasedMatchEntity> CREATOR = new l();
    private final long bSX;
    private final String bUu;
    private final GameEntity bVP;
    private final long bVQ;
    private final ArrayList<ParticipantEntity> bVS;
    private final String bVZ;
    private final Bundle bWa;
    private final String bWg;
    private final String bWh;
    private final String bWi;
    private final byte[] bWj;
    private final int bWk;
    private final boolean bWl;
    private final String bWm;
    private final byte[] data;
    private final String description;
    private final int version;
    private final int zzoe;
    private final int zzpn;
    private final int zzpr;

    TurnBasedMatchEntity(GameEntity gameEntity, String str, String str2, long j, String str3, long j2, String str4, int i, int i2, int i3, byte[] bArr, ArrayList<ParticipantEntity> arrayList, String str5, byte[] bArr2, int i4, Bundle bundle, int i5, boolean z, String str6, String str7) {
        this.bVP = gameEntity;
        this.bUu = str;
        this.bVZ = str2;
        this.bVQ = j;
        this.bWg = str3;
        this.bSX = j2;
        this.bWh = str4;
        this.zzpn = i;
        this.zzpr = i5;
        this.zzoe = i2;
        this.version = i3;
        this.data = bArr;
        this.bVS = arrayList;
        this.bWi = str5;
        this.bWj = bArr2;
        this.bWk = i4;
        this.bWa = bundle;
        this.bWl = z;
        this.description = str6;
        this.bWm = str7;
    }

    public TurnBasedMatchEntity(TurnBasedMatch turnBasedMatch) {
        this.bVP = new GameEntity(turnBasedMatch.adP());
        this.bUu = turnBasedMatch.getMatchId();
        this.bVZ = turnBasedMatch.aeC();
        this.bVQ = turnBasedMatch.aeq();
        this.bWg = turnBasedMatch.aeO();
        this.bSX = turnBasedMatch.acU();
        this.bWh = turnBasedMatch.aeP();
        this.zzpn = turnBasedMatch.getStatus();
        this.zzpr = turnBasedMatch.aeN();
        this.zzoe = turnBasedMatch.getVariant();
        this.version = turnBasedMatch.getVersion();
        this.bWi = turnBasedMatch.aeQ();
        this.bWk = turnBasedMatch.aeS();
        this.bWa = turnBasedMatch.aeD();
        this.bWl = turnBasedMatch.aeT();
        this.description = turnBasedMatch.getDescription();
        this.bWm = turnBasedMatch.aeU();
        byte[] data = turnBasedMatch.getData();
        if (data == null) {
            this.data = null;
        } else {
            this.data = new byte[data.length];
            System.arraycopy(data, 0, this.data, 0, data.length);
        }
        byte[] bArrAeR = turnBasedMatch.aeR();
        if (bArrAeR == null) {
            this.bWj = null;
        } else {
            this.bWj = new byte[bArrAeR.length];
            System.arraycopy(bArrAeR, 0, this.bWj, 0, bArrAeR.length);
        }
        ArrayList<Participant> arrayListAet = turnBasedMatch.aet();
        int size = arrayListAet.size();
        this.bVS = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.bVS.add((ParticipantEntity) arrayListAet.get(i).freeze());
        }
    }

    static boolean a(TurnBasedMatch turnBasedMatch, Object obj) {
        if (!(obj instanceof TurnBasedMatch)) {
            return false;
        }
        if (turnBasedMatch == obj) {
            return true;
        }
        TurnBasedMatch turnBasedMatch2 = (TurnBasedMatch) obj;
        return bd.b(turnBasedMatch2.adP(), turnBasedMatch.adP()) && bd.b(turnBasedMatch2.getMatchId(), turnBasedMatch.getMatchId()) && bd.b(turnBasedMatch2.aeC(), turnBasedMatch.aeC()) && bd.b(Long.valueOf(turnBasedMatch2.aeq()), Long.valueOf(turnBasedMatch.aeq())) && bd.b(turnBasedMatch2.aeO(), turnBasedMatch.aeO()) && bd.b(Long.valueOf(turnBasedMatch2.acU()), Long.valueOf(turnBasedMatch.acU())) && bd.b(turnBasedMatch2.aeP(), turnBasedMatch.aeP()) && bd.b(Integer.valueOf(turnBasedMatch2.getStatus()), Integer.valueOf(turnBasedMatch.getStatus())) && bd.b(Integer.valueOf(turnBasedMatch2.aeN()), Integer.valueOf(turnBasedMatch.aeN())) && bd.b(turnBasedMatch2.getDescription(), turnBasedMatch.getDescription()) && bd.b(Integer.valueOf(turnBasedMatch2.getVariant()), Integer.valueOf(turnBasedMatch.getVariant())) && bd.b(Integer.valueOf(turnBasedMatch2.getVersion()), Integer.valueOf(turnBasedMatch.getVersion())) && bd.b(turnBasedMatch2.aet(), turnBasedMatch.aet()) && bd.b(turnBasedMatch2.aeQ(), turnBasedMatch.aeQ()) && bd.b(Integer.valueOf(turnBasedMatch2.aeS()), Integer.valueOf(turnBasedMatch.aeS())) && com.google.android.gms.games.internal.h.a(turnBasedMatch2.aeD(), turnBasedMatch.aeD()) && bd.b(Integer.valueOf(turnBasedMatch2.aes()), Integer.valueOf(turnBasedMatch.aes())) && bd.b(Boolean.valueOf(turnBasedMatch2.aeT()), Boolean.valueOf(turnBasedMatch.aeT()));
    }

    static int b(TurnBasedMatch turnBasedMatch) {
        return bd.hashCode(turnBasedMatch.adP(), turnBasedMatch.getMatchId(), turnBasedMatch.aeC(), Long.valueOf(turnBasedMatch.aeq()), turnBasedMatch.aeO(), Long.valueOf(turnBasedMatch.acU()), turnBasedMatch.aeP(), Integer.valueOf(turnBasedMatch.getStatus()), Integer.valueOf(turnBasedMatch.aeN()), turnBasedMatch.getDescription(), Integer.valueOf(turnBasedMatch.getVariant()), Integer.valueOf(turnBasedMatch.getVersion()), turnBasedMatch.aet(), turnBasedMatch.aeQ(), Integer.valueOf(turnBasedMatch.aeS()), Integer.valueOf(com.google.android.gms.games.internal.h.y(turnBasedMatch.aeD())), Integer.valueOf(turnBasedMatch.aes()), Boolean.valueOf(turnBasedMatch.aeT()));
    }

    static String c(TurnBasedMatch turnBasedMatch) {
        return bd.C(turnBasedMatch).c("Game", turnBasedMatch.adP()).c("MatchId", turnBasedMatch.getMatchId()).c("CreatorId", turnBasedMatch.aeC()).c("CreationTimestamp", Long.valueOf(turnBasedMatch.aeq())).c("LastUpdaterId", turnBasedMatch.aeO()).c("LastUpdatedTimestamp", Long.valueOf(turnBasedMatch.acU())).c("PendingParticipantId", turnBasedMatch.aeP()).c("MatchStatus", Integer.valueOf(turnBasedMatch.getStatus())).c("TurnStatus", Integer.valueOf(turnBasedMatch.aeN())).c("Description", turnBasedMatch.getDescription()).c("Variant", Integer.valueOf(turnBasedMatch.getVariant())).c("Data", turnBasedMatch.getData()).c("Version", Integer.valueOf(turnBasedMatch.getVersion())).c("Participants", turnBasedMatch.aet()).c("RematchId", turnBasedMatch.aeQ()).c("PreviousData", turnBasedMatch.aeR()).c("MatchNumber", Integer.valueOf(turnBasedMatch.aeS())).c("AutoMatchCriteria", turnBasedMatch.aeD()).c("AvailableAutoMatchSlots", Integer.valueOf(turnBasedMatch.aes())).c("LocallyModified", Boolean.valueOf(turnBasedMatch.aeT())).c("DescriptionParticipantId", turnBasedMatch.aeU()).toString();
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final long acU() {
        return this.bSX;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final Game adP() {
        return this.bVP;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String aeC() {
        return this.bVZ;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final Bundle aeD() {
        return this.bWa;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int aeN() {
        return this.zzpr;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String aeO() {
        return this.bWg;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String aeP() {
        return this.bWh;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String aeQ() {
        return this.bWi;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final byte[] aeR() {
        return this.bWj;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int aeS() {
        return this.bWk;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final boolean aeT() {
        return this.bWl;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String aeU() {
        return this.bWm;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: aeW, reason: merged with bridge method [inline-methods] */
    public final TurnBasedMatch freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final long aeq() {
        return this.bVQ;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int aes() {
        if (this.bWa == null) {
            return 0;
        }
        return this.bWa.getInt("max_automatch_players");
    }

    @Override // com.google.android.gms.games.multiplayer.h
    public final ArrayList<Participant> aet() {
        return new ArrayList<>(this.bVS);
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final byte[] getData() {
        return this.data;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getMatchId() {
        return this.bUu;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getStatus() {
        return this.zzpn;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getVariant() {
        return this.zzoe;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getVersion() {
        return this.version;
    }

    public final int hashCode() {
        return b(this);
    }

    public final String toString() {
        return c(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, (Parcelable) adP(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getMatchId(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, aeC(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, aeq());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, aeO(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, acU());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, aeP(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 8, getStatus());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 10, getVariant());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 11, getVersion());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 12, getData(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 13, aet(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 14, aeQ(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 15, aeR(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 16, aeS());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 17, aeD(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 18, aeN());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 19, aeT());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 20, getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 21, aeU(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
