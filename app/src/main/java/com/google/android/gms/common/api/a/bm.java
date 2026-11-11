package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class bm implements c {
    private final /* synthetic */ l bJC;

    bm(l lVar) {
        this.bJC = lVar;
    }

    @Override // com.google.android.gms.common.api.a.c
    public final void onBackgroundStateChanged(boolean z) {
        this.bJC.handler.sendMessage(this.bJC.handler.obtainMessage(1, Boolean.valueOf(z)));
    }
}
