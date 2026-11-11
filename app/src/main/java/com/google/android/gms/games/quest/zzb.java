package com.google.android.gms.games.quest;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.di;

/* JADX INFO: loaded from: classes.dex */
public final class zzb extends com.google.android.gms.common.data.e implements Milestone {
    zzb(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final String acX() {
        return getString("external_event_id");
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final String aeX() {
        return getString("external_milestone_id");
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final long aeY() {
        long jAeZ;
        long j = 0;
        switch (getState()) {
            case 1:
                jAeZ = 0;
                break;
            case 2:
                jAeZ = getLong("current_value");
                if (getLong("quest_state") != 6) {
                    jAeZ -= getLong("initial_value");
                }
                break;
            case 3:
            case 4:
                jAeZ = aeZ();
                break;
            default:
                jAeZ = 0;
                break;
        }
        if (jAeZ < 0) {
            di.ay("MilestoneRef", "Current progress should never be negative");
        } else {
            j = jAeZ;
        }
        if (j <= aeZ()) {
            return j;
        }
        di.ay("MilestoneRef", "Current progress should never exceed target progress");
        return aeZ();
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final long aeZ() {
        return getLong("target_value");
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final byte[] afa() {
        return getByteArray("completion_reward_data");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return MilestoneEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ Milestone freeze() {
        return new MilestoneEntity(this);
    }

    @Override // com.google.android.gms.games.quest.Milestone
    public final int getState() {
        return getInteger("milestone_state");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return MilestoneEntity.a(this);
    }

    public final String toString() {
        return MilestoneEntity.b(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((MilestoneEntity) ((Milestone) freeze())).writeToParcel(parcel, i);
    }
}
