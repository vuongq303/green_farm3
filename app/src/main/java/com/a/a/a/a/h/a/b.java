package com.a.a.a.a.h.a;

import android.os.AsyncTask;
import java.util.concurrent.ThreadPoolExecutor;

/* JADX INFO: loaded from: classes.dex */
public abstract class b extends AsyncTask<Object, Void, String> {
    private c cfi;
    protected final d cfj;

    public b(d dVar) {
        this.cfj = dVar;
    }

    public void a(c cVar) {
        this.cfi = cVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(String str) {
        if (this.cfi != null) {
            this.cfi.a(this);
        }
    }

    public void a(ThreadPoolExecutor threadPoolExecutor) {
        executeOnExecutor(threadPoolExecutor, new Object[0]);
    }
}
