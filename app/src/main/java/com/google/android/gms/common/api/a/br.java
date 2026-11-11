package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class br implements Runnable {
    private final /* synthetic */ bq bLk;

    br(bq bqVar) {
        this.bLk = bqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bLk.bLi.bJs.disconnect();
    }
}
