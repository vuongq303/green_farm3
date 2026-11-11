package com.google.android.gms.common.api.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class ca {
    private static final ExecutorService bLh = new ThreadPoolExecutor(0, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new com.google.android.gms.common.d.a.c("GAC_Transform"));

    public static ExecutorService ZE() {
        return bLh;
    }
}
