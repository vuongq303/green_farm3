package com.google.android.gms.games.quest;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.zzd;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class QuestEntity extends zzd implements Quest {
    public static final Parcelable.Creator<QuestEntity> CREATOR = new h();
    private final long bSX;
    private final GameEntity bVP;
    private final String bWt;
    private final long bWu;
    private final Uri bWv;
    private final String bWw;
    private final Uri bWx;
    private final String bWy;
    private final ArrayList<MilestoneEntity> bWz;
    private final String description;
    private final String name;
    private final int state;
    private final int type;
    private final long zzqb;
    private final long zzqe;
    private final long zzqf;

    QuestEntity(GameEntity gameEntity, String str, long j, Uri uri, String str2, String str3, long j2, long j3, Uri uri2, String str4, String str5, long j4, long j5, int i, int i2, ArrayList<MilestoneEntity> arrayList) {
        this.bVP = gameEntity;
        this.bWt = str;
        this.bWu = j;
        this.bWv = uri;
        this.bWw = str2;
        this.description = str3;
        this.zzqb = j2;
        this.bSX = j3;
        this.bWx = uri2;
        this.bWy = str4;
        this.name = str5;
        this.zzqe = j4;
        this.zzqf = j5;
        this.state = i;
        this.type = i2;
        this.bWz = arrayList;
    }

    public QuestEntity(Quest quest) {
        this.bVP = new GameEntity(quest.adP());
        this.bWt = quest.afc();
        this.bWu = quest.afg();
        this.description = quest.getDescription();
        this.bWv = quest.afd();
        this.bWw = quest.afe();
        this.zzqb = quest.afh();
        this.bWx = quest.abV();
        this.bWy = quest.abW();
        this.bSX = quest.acU();
        this.name = quest.getName();
        this.zzqe = quest.afi();
        this.zzqf = quest.afj();
        this.state = quest.getState();
        this.type = quest.getType();
        List<Milestone> listAff = quest.aff();
        int size = listAff.size();
        this.bWz = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.bWz.add((MilestoneEntity) listAff.get(i).freeze());
        }
    }

    static int a(Quest quest) {
        return bd.hashCode(quest.adP(), quest.afc(), Long.valueOf(quest.afg()), quest.afd(), quest.getDescription(), Long.valueOf(quest.afh()), quest.abV(), Long.valueOf(quest.acU()), quest.aff(), quest.getName(), Long.valueOf(quest.afi()), Long.valueOf(quest.afj()), Integer.valueOf(quest.getState()));
    }

    static boolean a(Quest quest, Object obj) {
        if (!(obj instanceof Quest)) {
            return false;
        }
        if (quest == obj) {
            return true;
        }
        Quest quest2 = (Quest) obj;
        return bd.b(quest2.adP(), quest.adP()) && bd.b(quest2.afc(), quest.afc()) && bd.b(Long.valueOf(quest2.afg()), Long.valueOf(quest.afg())) && bd.b(quest2.afd(), quest.afd()) && bd.b(quest2.getDescription(), quest.getDescription()) && bd.b(Long.valueOf(quest2.afh()), Long.valueOf(quest.afh())) && bd.b(quest2.abV(), quest.abV()) && bd.b(Long.valueOf(quest2.acU()), Long.valueOf(quest.acU())) && bd.b(quest2.aff(), quest.aff()) && bd.b(quest2.getName(), quest.getName()) && bd.b(Long.valueOf(quest2.afi()), Long.valueOf(quest.afi())) && bd.b(Long.valueOf(quest2.afj()), Long.valueOf(quest.afj())) && bd.b(Integer.valueOf(quest2.getState()), Integer.valueOf(quest.getState()));
    }

    static String b(Quest quest) {
        return bd.C(quest).c("Game", quest.adP()).c("QuestId", quest.afc()).c("AcceptedTimestamp", Long.valueOf(quest.afg())).c("BannerImageUri", quest.afd()).c("BannerImageUrl", quest.afe()).c("Description", quest.getDescription()).c("EndTimestamp", Long.valueOf(quest.afh())).c("IconImageUri", quest.abV()).c("IconImageUrl", quest.abW()).c("LastUpdatedTimestamp", Long.valueOf(quest.acU())).c("Milestones", quest.aff()).c("Name", quest.getName()).c("NotifyTimestamp", Long.valueOf(quest.afi())).c("StartTimestamp", Long.valueOf(quest.afj())).c("State", Integer.valueOf(quest.getState())).toString();
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Uri abV() {
        return this.bWx;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String abW() {
        return this.bWy;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long acU() {
        return this.bSX;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Game adP() {
        return this.bVP;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String afc() {
        return this.bWt;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Uri afd() {
        return this.bWv;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String afe() {
        return this.bWw;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final List<Milestone> aff() {
        return new ArrayList(this.bWz);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long afg() {
        return this.bWu;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long afh() {
        return this.zzqb;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long afi() {
        return this.zzqe;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long afj() {
        return this.zzqf;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: afk, reason: merged with bridge method [inline-methods] */
    public final Quest freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getName() {
        return this.name;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final int getState() {
        return this.state;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final int getType() {
        return this.type;
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
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, (Parcelable) adP(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, afc(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, afg());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) afd(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, afe(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, afh());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, acU());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, (Parcelable) abV(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, abW(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 12, getName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 13, this.zzqe);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 14, afj());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 15, getState());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 16, this.type);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 17, aff(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
