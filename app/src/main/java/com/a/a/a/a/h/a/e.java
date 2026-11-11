package com.a.a.a.a.h.a;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class e implements c {
    private final ArrayDeque<b> cfm = new ArrayDeque<>();
    private b cfn = null;
    private final BlockingQueue<Runnable> cfk = new LinkedBlockingQueue();
    private final ThreadPoolExecutor cfl = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, this.cfk);

    private void a() {
        this.cfn = this.cfm.poll();
        if (this.cfn != null) {
            this.cfn.a(this.cfl);
        }
    }

    @Override // com.a.a.a.a.h.a.c
    public void a(b bVar) {
        this.cfn = null;
        a();
    }

    public void b(b bVar) {
        bVar.a(this);
        this.cfm.add(bVar);
        if (this.cfn == null) {
            a();
        }
    }
}
