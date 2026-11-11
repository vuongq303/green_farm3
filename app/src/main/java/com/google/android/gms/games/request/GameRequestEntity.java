package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.zzd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class GameRequestEntity extends zzd implements GameRequest {
    public static final Parcelable.Creator<GameRequestEntity> CREATOR = new f();
    private final String bUI;
    private final GameEntity bVP;
    private final long bVQ;
    private final PlayerEntity bWB;
    private final ArrayList<PlayerEntity> bWC;
    private final long bWD;
    private final Bundle bWE;
    private final byte[] data;
    private final int status;
    private final int type;

    GameRequestEntity(GameEntity gameEntity, PlayerEntity playerEntity, byte[] bArr, String str, ArrayList<PlayerEntity> arrayList, int i, long j, long j2, Bundle bundle, int i2) {
        this.bVP = gameEntity;
        this.bWB = playerEntity;
        this.data = bArr;
        this.bUI = str;
        this.bWC = arrayList;
        this.type = i;
        this.bVQ = j;
        this.bWD = j2;
        this.bWE = bundle;
        this.status = i2;
    }

    public GameRequestEntity(GameRequest gameRequest) {
        this.bVP = new GameEntity(gameRequest.adP());
        this.bWB = new PlayerEntity(gameRequest.afl());
        this.bUI = gameRequest.getRequestId();
        this.type = gameRequest.getType();
        this.bVQ = gameRequest.aeq();
        this.bWD = gameRequest.getExpirationTimestamp();
        this.status = gameRequest.getStatus();
        byte[] data = gameRequest.getData();
        if (data == null) {
            this.data = null;
        } else {
            this.data = new byte[data.length];
            System.arraycopy(data, 0, this.data, 0, data.length);
        }
        List<Player> recipients = gameRequest.getRecipients();
        int size = recipients.size();
        this.bWC = new ArrayList<>(size);
        this.bWE = new Bundle();
        for (int i = 0; i < size; i++) {
            Player playerFreeze = recipients.get(i).freeze();
            String strAcq = playerFreeze.acq();
            this.bWC.add((PlayerEntity) playerFreeze);
            this.bWE.putInt(strAcq, gameRequest.ix(strAcq));
        }
    }

    static int a(GameRequest gameRequest) {
        return (Arrays.hashCode(b(gameRequest)) * 31) + bd.hashCode(gameRequest.adP(), gameRequest.getRecipients(), gameRequest.getRequestId(), gameRequest.afl(), Integer.valueOf(gameRequest.getType()), Long.valueOf(gameRequest.aeq()), Long.valueOf(gameRequest.getExpirationTimestamp()));
    }

    static boolean a(GameRequest gameRequest, Object obj) {
        if (!(obj instanceof GameRequest)) {
            return false;
        }
        if (gameRequest == obj) {
            return true;
        }
        GameRequest gameRequest2 = (GameRequest) obj;
        return bd.b(gameRequest2.adP(), gameRequest.adP()) && bd.b(gameRequest2.getRecipients(), gameRequest.getRecipients()) && bd.b(gameRequest2.getRequestId(), gameRequest.getRequestId()) && bd.b(gameRequest2.afl(), gameRequest.afl()) && Arrays.equals(b(gameRequest2), b(gameRequest)) && bd.b(Integer.valueOf(gameRequest2.getType()), Integer.valueOf(gameRequest.getType())) && bd.b(Long.valueOf(gameRequest2.aeq()), Long.valueOf(gameRequest.aeq())) && bd.b(Long.valueOf(gameRequest2.getExpirationTimestamp()), Long.valueOf(gameRequest.getExpirationTimestamp()));
    }

    private static int[] b(GameRequest gameRequest) {
        List<Player> recipients = gameRequest.getRecipients();
        int size = recipients.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = gameRequest.ix(recipients.get(i).acq());
        }
        return iArr;
    }

    static String c(GameRequest gameRequest) {
        return bd.C(gameRequest).c("Game", gameRequest.adP()).c("Sender", gameRequest.afl()).c("Recipients", gameRequest.getRecipients()).c("Data", gameRequest.getData()).c("RequestId", gameRequest.getRequestId()).c("Type", Integer.valueOf(gameRequest.getType())).c("CreationTimestamp", Long.valueOf(gameRequest.aeq())).c("ExpirationTimestamp", Long.valueOf(gameRequest.getExpirationTimestamp())).toString();
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final Game adP() {
        return this.bVP;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final long aeq() {
        return this.bVQ;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final Player afl() {
        return this.bWB;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: afm, reason: merged with bridge method [inline-methods] */
    public final GameRequest freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final byte[] getData() {
        return this.data;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final long getExpirationTimestamp() {
        return this.bWD;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final List<Player> getRecipients() {
        return new ArrayList(this.bWC);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final String getRequestId() {
        return this.bUI;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getStatus() {
        return this.status;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getType() {
        return this.type;
    }

    public final int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int ix(String str) {
        return this.bWE.getInt(str, 0);
    }

    public final String toString() {
        return c(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, (Parcelable) adP(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable) afl(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, getData(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, getRequestId(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 5, getRecipients(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 7, getType());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, aeq());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, getExpirationTimestamp());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 11, this.bWE, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 12, getStatus());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
