package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.quest.Milestone;
import com.google.android.gms.games.quest.Quest;
import com.google.android.gms.games.quest.e;

/* JADX INFO: loaded from: classes.dex */
final class zzbw implements e {
    private final /* synthetic */ Status zzbc;

    zzbw(zzbv zzbvVar, Status status) {
        this.zzbc = status;
    }

    public final Milestone getMilestone() {
        return null;
    }

    public final Quest getQuest() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
