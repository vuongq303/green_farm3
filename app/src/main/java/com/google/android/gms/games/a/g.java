package com.google.android.gms.games.a;

import android.net.Uri;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;

/* JADX INFO: loaded from: classes.dex */
public final class g implements e {
    private final String bVA;
    private final String bVB;
    private final long bVq;
    private final String bVr;
    private final String bVs;
    private final long bVt;
    private final long bVu;
    private final String bVv;
    private final Uri bVw;
    private final Uri bVx;
    private final PlayerEntity bVy;
    private final String bVz;

    public g(e eVar) {
        this.bVq = eVar.adQ();
        this.bVr = (String) bf.checkNotNull(eVar.adR());
        this.bVs = (String) bf.checkNotNull(eVar.adS());
        this.bVt = eVar.adT();
        this.bVu = eVar.getTimestampMillis();
        this.bVv = eVar.adU();
        this.bVw = eVar.adV();
        this.bVx = eVar.adX();
        Player playerAdZ = eVar.adZ();
        this.bVy = playerAdZ == null ? null : (PlayerEntity) playerAdZ.freeze();
        this.bVz = eVar.aea();
        this.bVA = eVar.adW();
        this.bVB = eVar.adY();
    }

    static int a(e eVar) {
        return bd.hashCode(Long.valueOf(eVar.adQ()), eVar.adR(), Long.valueOf(eVar.adT()), eVar.adS(), Long.valueOf(eVar.getTimestampMillis()), eVar.adU(), eVar.adV(), eVar.adX(), eVar.adZ());
    }

    static boolean a(e eVar, Object obj) {
        if (!(obj instanceof e)) {
            return false;
        }
        if (eVar == obj) {
            return true;
        }
        e eVar2 = (e) obj;
        return bd.b(Long.valueOf(eVar2.adQ()), Long.valueOf(eVar.adQ())) && bd.b(eVar2.adR(), eVar.adR()) && bd.b(Long.valueOf(eVar2.adT()), Long.valueOf(eVar.adT())) && bd.b(eVar2.adS(), eVar.adS()) && bd.b(Long.valueOf(eVar2.getTimestampMillis()), Long.valueOf(eVar.getTimestampMillis())) && bd.b(eVar2.adU(), eVar.adU()) && bd.b(eVar2.adV(), eVar.adV()) && bd.b(eVar2.adX(), eVar.adX()) && bd.b(eVar2.adZ(), eVar.adZ()) && bd.b(eVar2.aea(), eVar.aea());
    }

    static String b(e eVar) {
        return bd.C(eVar).c("Rank", Long.valueOf(eVar.adQ())).c("DisplayRank", eVar.adR()).c("Score", Long.valueOf(eVar.adT())).c("DisplayScore", eVar.adS()).c("Timestamp", Long.valueOf(eVar.getTimestampMillis())).c("DisplayName", eVar.adU()).c("IconImageUri", eVar.adV()).c("IconImageUrl", eVar.adW()).c("HiResImageUri", eVar.adX()).c("HiResImageUrl", eVar.adY()).c("Player", eVar.adZ() == null ? null : eVar.adZ()).c("ScoreTag", eVar.aea()).toString();
    }

    @Override // com.google.android.gms.games.a.e
    public final long adQ() {
        return this.bVq;
    }

    @Override // com.google.android.gms.games.a.e
    public final String adR() {
        return this.bVr;
    }

    @Override // com.google.android.gms.games.a.e
    public final String adS() {
        return this.bVs;
    }

    @Override // com.google.android.gms.games.a.e
    public final long adT() {
        return this.bVt;
    }

    @Override // com.google.android.gms.games.a.e
    public final String adU() {
        return this.bVy == null ? this.bVv : this.bVy.getDisplayName();
    }

    @Override // com.google.android.gms.games.a.e
    public final Uri adV() {
        return this.bVy == null ? this.bVw : this.bVy.abV();
    }

    @Override // com.google.android.gms.games.a.e
    public final String adW() {
        return this.bVy == null ? this.bVA : this.bVy.abW();
    }

    @Override // com.google.android.gms.games.a.e
    public final Uri adX() {
        return this.bVy == null ? this.bVx : this.bVy.abX();
    }

    @Override // com.google.android.gms.games.a.e
    public final String adY() {
        return this.bVy == null ? this.bVB : this.bVy.abY();
    }

    @Override // com.google.android.gms.games.a.e
    public final Player adZ() {
        return this.bVy;
    }

    @Override // com.google.android.gms.games.a.e
    public final String aea() {
        return this.bVz;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* bridge */ /* synthetic */ e freeze() {
        if (this == null) {
            throw null;
        }
        return this;
    }

    @Override // com.google.android.gms.games.a.e
    public final long getTimestampMillis() {
        return this.bVu;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }
}
