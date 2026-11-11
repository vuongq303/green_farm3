package com.c.a.a.a.h.a;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: AvidAsyncTaskQueue.java */
/* JADX INFO: loaded from: classes.dex */
public class e implements c {
    private final ArrayDeque<b> ckG = new ArrayDeque<>();
    private b ckH = null;
    private final BlockingQueue<Runnable> ckE = new LinkedBlockingQueue();
    private final ThreadPoolExecutor ckF = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, this.ckE);

    public void b(b bVar) {
        bVar.a(this);
        this.ckG.add(bVar);
        if (this.ckH == null) {
            ajE();
        }
    }

    private void ajE() {
        this.ckH = this.ckG.poll();
        if (this.ckH != null) {
            this.ckH.b(this.ckF);
        }
    }

    @Override // com.c.a.a.a.h.a.c
    public void a(b bVar) {
        this.ckH = null;
        ajE();
    }
}
