package com.google.android.gms.f;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class y implements Runnable {
    private final /* synthetic */ x bZh;
    private final /* synthetic */ Callable val$callable;

    y(x xVar, Callable callable) {
        this.bZh = xVar;
        this.val$callable = callable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.bZh.setResult(this.val$callable.call());
        } catch (Exception e) {
            this.bZh.g(e);
        }
    }
}
