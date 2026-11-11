package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.player.zza;
import com.google.android.gms.games.internal.player.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class PlayerRef extends com.google.android.gms.common.data.e implements Player {
    private final com.google.android.gms.games.internal.player.b bSN;
    private final zzd bSO;
    private final PlayerLevelInfo bSy;

    public PlayerRef(DataHolder dataHolder, int i) {
        this(dataHolder, i, null);
    }

    public PlayerRef(DataHolder dataHolder, int i, String str) {
        super(dataHolder, i);
        this.bSN = new com.google.android.gms.games.internal.player.b(str);
        this.bSO = new zzd(dataHolder, i, this.bSN);
        if (!((hasNull(this.bSN.bTo) || getLong(this.bSN.bTo) == -1) ? false : true)) {
            this.bSy = null;
            return;
        }
        int integer = getInteger(this.bSN.bTp);
        int integer2 = getInteger(this.bSN.bTs);
        PlayerLevel playerLevel = new PlayerLevel(integer, getLong(this.bSN.bTq), getLong(this.bSN.bTr));
        this.bSy = new PlayerLevelInfo(getLong(this.bSN.bTo), getLong(this.bSN.bTu), playerLevel, integer != integer2 ? new PlayerLevel(integer2, getLong(this.bSN.bTr), getLong(this.bSN.bTt)) : playerLevel);
    }

    @Override // com.google.android.gms.games.Player
    public final Uri abV() {
        return parseUri(this.bSN.bTh);
    }

    @Override // com.google.android.gms.games.Player
    public final String abW() {
        return getString(this.bSN.bTi);
    }

    @Override // com.google.android.gms.games.Player
    public final Uri abX() {
        return parseUri(this.bSN.bTj);
    }

    @Override // com.google.android.gms.games.Player
    public final String abY() {
        return getString(this.bSN.bTk);
    }

    @Override // com.google.android.gms.games.Player
    public final String acA() {
        return getString(this.bSN.bTH);
    }

    @Override // com.google.android.gms.games.Player
    public final int acB() {
        return getInteger(this.bSN.bTI);
    }

    @Override // com.google.android.gms.games.Player
    public final long acC() {
        return getLong(this.bSN.bTJ);
    }

    @Override // com.google.android.gms.games.Player
    public final String acq() {
        return getString(this.bSN.bJS);
    }

    @Override // com.google.android.gms.games.Player
    public final String acr() {
        return getString(this.bSN.zzch);
    }

    @Override // com.google.android.gms.games.Player
    public final long acs() {
        return getLong(this.bSN.bTl);
    }

    @Override // com.google.android.gms.games.Player
    public final long act() {
        if (!hasColumn(this.bSN.bTn) || hasNull(this.bSN.bTn)) {
            return -1L;
        }
        return getLong(this.bSN.bTn);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean acu() {
        return getBoolean(this.bSN.bTw);
    }

    @Override // com.google.android.gms.games.Player
    public final PlayerLevelInfo acv() {
        return this.bSy;
    }

    @Override // com.google.android.gms.games.Player
    public final zza acw() {
        if (hasNull(this.bSN.bTx)) {
            return null;
        }
        return this.bSO;
    }

    @Override // com.google.android.gms.games.Player
    public final Uri acx() {
        return parseUri(this.bSN.bTE);
    }

    @Override // com.google.android.gms.games.Player
    public final String acy() {
        return getString(this.bSN.bTF);
    }

    @Override // com.google.android.gms.games.Player
    public final Uri acz() {
        return parseUri(this.bSN.bTG);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return PlayerEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ Player freeze() {
        return new PlayerEntity(this);
    }

    @Override // com.google.android.gms.games.Player
    public final String getDisplayName() {
        return getString(this.bSN.bTg);
    }

    @Override // com.google.android.gms.games.Player
    public final String getName() {
        return getString(this.bSN.name);
    }

    @Override // com.google.android.gms.games.Player
    public final String getTitle() {
        return getString(this.bSN.zzcc);
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return PlayerEntity.a(this);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean isMuted() {
        return getBoolean(this.bSN.bTK);
    }

    public final String toString() {
        return PlayerEntity.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((PlayerEntity) ((Player) freeze())).writeToParcel(parcel, i);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean zzh() {
        return getBoolean(this.bSN.bTD);
    }

    @Override // com.google.android.gms.games.Player
    public final int zzi() {
        return getInteger(this.bSN.bTm);
    }
}
