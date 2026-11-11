package com.google.android.gms.games.quest;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class MilestoneEntity extends zzd implements Milestone {
    public static final Parcelable.Creator<MilestoneEntity> CREATOR = new g();
    private final String bSZ;
    private final long bWo;
    private final long bWp;
    private final byte[] bWq;
    private final int state;
    private final String zzho;

    public MilestoneEntity(Milestone milestone) {
        this.zzho = milestone.aeX();
        this.bWo = milestone.aeY();
        this.bWp = milestone.aeZ();
        this.state = milestone.getState();
        this.bSZ = milestone.acX();
        byte[] bArrAfa = milestone.afa();
        if (bArrAfa == null) {
            this.bWq = null;
        } else {
            this.bWq = new byte[bArrAfa.length];
            System.arraycopy(bArrAfa, 0, this.bWq, 0, bArrAfa.length);
        }
    }

    MilestoneEntity(String str, long j, long j2, byte[] bArr, int i, String str2) {
        this.zzho = str;
        this.bWo = j;
        this.bWp = j2;
        this.bWq = bArr;
        this.state = i;
        this.bSZ = str2;
    }

    static int a(Milestone milestone) {
        return bd.hashCode(milestone.aeX(), Long.valueOf(milestone.aeY()), Long.valueOf(milestone.aeZ()), Integer.valueOf(milestone.getState()), milestone.acX());
    }

    static boolean a(Milestone milestone, Object obj) {
        if (!(obj instanceof Milestone)) {
            return false;
        }
        if (milestone == obj) {
            return true;
        }
        Milestone milestone2 = (Milestone) obj;
        return bd.b(milestone2.aeX(), milestone.aeX()) && bd.b(Long.valueOf(milestone2.aeY()), Long.valueOf(milestone.aeY())) && bd.b(Long.valueOf(milestone2.aeZ()), Long.valueOf(milestone.aeZ())) && bd.b(Integer.valueOf(milestone2.getState()), Integer.valueOf(milestone.getState())) && bd.b(milestone2.acX(), milestone.acX());
    }

    static String b(Milestone milestone) {
        return bd.C(milestone).c("MilestoneId", milestone.aeX()).c("CurrentProgress", Long.valueOf(milestone.aeY())).c("TargetProgress", Long.valueOf(milestone.aeZ())).c("State", Integer.valueOf(milestone.getState())).c("CompletionRewardData", milestone.afa()).c("EventId", milestone.acX()).toString();
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final String acX() {
        return this.bSZ;
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final String aeX() {
        return this.zzho;
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final long aeY() {
        return this.bWo;
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final long aeZ() {
        return this.bWp;
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final byte[] afa() {
        return this.bWq;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: afb, reason: merged with bridge method [inline-methods] */
    public final Milestone freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final int getState() {
        return this.state;
    }

    public final int hashCode() {
        return a(this);
    }

    public final String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, aeX(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, aeY());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, aeZ());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, afa(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 5, getState());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, acX(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
