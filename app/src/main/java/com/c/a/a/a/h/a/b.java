package com.c.a.a.a.h.a;

import android.os.AsyncTask;
import android.os.Build;
import java.util.concurrent.ThreadPoolExecutor;

/* JADX INFO: compiled from: AvidAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class b extends AsyncTask<Object, Void, String> {
    private c ckC;
    protected final d ckD;

    public b(d dVar) {
        this.ckD = dVar;
    }

    public void a(c cVar) {
        this.ckC = cVar;
    }

    public void b(ThreadPoolExecutor threadPoolExecutor) {
        if (Build.VERSION.SDK_INT > 11) {
            executeOnExecutor(threadPoolExecutor, new Object[0]);
        } else {
            execute(new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* JADX INFO: renamed from: iO, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(String str) {
        if (this.ckC != null) {
            this.ckC.a(this);
        }
    }
}
