package com.google.android.gms.common.api.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class bl {
    private static final ExecutorService bLh = Executors.newFixedThreadPool(2, new com.google.android.gms.common.d.a.c("GAC_Executor"));

    public static ExecutorService ZE() {
        return bLh;
    }
}
