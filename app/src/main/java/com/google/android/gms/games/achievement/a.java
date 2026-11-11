package com.google.android.gms.games.achievement;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class a extends com.google.android.gms.common.data.a<Achievement> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.c
    /* JADX INFO: renamed from: oj, reason: merged with bridge method [inline-methods] */
    public final Achievement get(int i) {
        return new AchievementRef(this.mDataHolder, i);
    }
}
