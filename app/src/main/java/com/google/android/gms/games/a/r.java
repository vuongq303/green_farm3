package com.google.android.gms.games.a;

import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.be;
import com.google.android.gms.internal.games.zzei;

/* JADX INFO: loaded from: classes.dex */
public final class r implements i {
    private final String bNp;
    private final boolean bVH;
    private final long bVI;
    private final String bVJ;
    private final long bVK;
    private final String bVL;
    private final long bVM;
    private final String bVN;
    private final String bVO;
    private final int zznn;
    private final int zzno;
    private final String zznt;

    public r(i iVar) {
        this.zznn = iVar.aec();
        this.zzno = iVar.aed();
        this.bVH = iVar.aee();
        this.bVI = iVar.aef();
        this.bVJ = iVar.aeg();
        this.bVK = iVar.aeh();
        this.zznt = iVar.aei();
        this.bVL = iVar.aej();
        this.bVM = iVar.aek();
        this.bVN = iVar.ael();
        this.bVO = iVar.aem();
        this.bNp = iVar.aen();
    }

    static int a(i iVar) {
        return bd.hashCode(Integer.valueOf(iVar.aec()), Integer.valueOf(iVar.aed()), Boolean.valueOf(iVar.aee()), Long.valueOf(iVar.aef()), iVar.aeg(), Long.valueOf(iVar.aeh()), iVar.aei(), Long.valueOf(iVar.aek()), iVar.ael(), iVar.aen(), iVar.aem());
    }

    static boolean a(i iVar, Object obj) {
        if (!(obj instanceof i)) {
            return false;
        }
        if (iVar == obj) {
            return true;
        }
        i iVar2 = (i) obj;
        return bd.b(Integer.valueOf(iVar2.aec()), Integer.valueOf(iVar.aec())) && bd.b(Integer.valueOf(iVar2.aed()), Integer.valueOf(iVar.aed())) && bd.b(Boolean.valueOf(iVar2.aee()), Boolean.valueOf(iVar.aee())) && bd.b(Long.valueOf(iVar2.aef()), Long.valueOf(iVar.aef())) && bd.b(iVar2.aeg(), iVar.aeg()) && bd.b(Long.valueOf(iVar2.aeh()), Long.valueOf(iVar.aeh())) && bd.b(iVar2.aei(), iVar.aei()) && bd.b(Long.valueOf(iVar2.aek()), Long.valueOf(iVar.aek())) && bd.b(iVar2.ael(), iVar.ael()) && bd.b(iVar2.aen(), iVar.aen()) && bd.b(iVar2.aem(), iVar.aem());
    }

    static String b(i iVar) {
        String str;
        be beVarC = bd.C(iVar).c("TimeSpan", zzei.zzn(iVar.aec()));
        int iAed = iVar.aed();
        switch (iAed) {
            case -1:
                str = "UNKNOWN";
                break;
            case 0:
                str = "PUBLIC";
                break;
            case 1:
                str = "SOCIAL";
                break;
            case 2:
                str = "SOCIAL_1P";
                break;
            default:
                throw new IllegalArgumentException(new StringBuilder(43).append("Unknown leaderboard collection: ").append(iAed).toString());
        }
        return beVarC.c("Collection", str).c("RawPlayerScore", iVar.aee() ? Long.valueOf(iVar.aef()) : "none").c("DisplayPlayerScore", iVar.aee() ? iVar.aeg() : "none").c("PlayerRank", iVar.aee() ? Long.valueOf(iVar.aeh()) : "none").c("DisplayPlayerRank", iVar.aee() ? iVar.aei() : "none").c("NumScores", Long.valueOf(iVar.aek())).c("TopPageNextToken", iVar.ael()).c("WindowPageNextToken", iVar.aen()).c("WindowPagePrevToken", iVar.aem()).toString();
    }

    @Override // com.google.android.gms.games.a.i
    public final int aec() {
        return this.zznn;
    }

    @Override // com.google.android.gms.games.a.i
    public final int aed() {
        return this.zzno;
    }

    @Override // com.google.android.gms.games.a.i
    public final boolean aee() {
        return this.bVH;
    }

    @Override // com.google.android.gms.games.a.i
    public final long aef() {
        return this.bVI;
    }

    @Override // com.google.android.gms.games.a.i
    public final String aeg() {
        return this.bVJ;
    }

    @Override // com.google.android.gms.games.a.i
    public final long aeh() {
        return this.bVK;
    }

    @Override // com.google.android.gms.games.a.i
    public final String aei() {
        return this.zznt;
    }

    @Override // com.google.android.gms.games.a.i
    public final String aej() {
        return this.bVL;
    }

    @Override // com.google.android.gms.games.a.i
    public final long aek() {
        return this.bVM;
    }

    @Override // com.google.android.gms.games.a.i
    public final String ael() {
        return this.bVN;
    }

    @Override // com.google.android.gms.games.a.i
    public final String aem() {
        return this.bVO;
    }

    @Override // com.google.android.gms.games.a.i
    public final String aen() {
        return this.bNp;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* bridge */ /* synthetic */ i freeze() {
        if (this == null) {
            throw null;
        }
        return this;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }
}
