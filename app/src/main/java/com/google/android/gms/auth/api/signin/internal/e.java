package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.api.a.ab;
import com.google.android.gms.common.api.w;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class e extends android.support.v4.content.a<Void> implements ab {
    private Semaphore bHl;
    private Set<w> bHm;

    public e(Context context, Set<w> set) {
        super(context);
        this.bHl = new Semaphore(0);
        this.bHm = set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // android.support.v4.content.a
    /* JADX INFO: renamed from: Yv, reason: merged with bridge method [inline-methods] */
    public final Void loadInBackground() {
        int i = 0;
        Iterator<w> it = this.bHm.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                try {
                    this.bHl.tryAcquire(i2, 5L, TimeUnit.SECONDS);
                    return null;
                } catch (InterruptedException e) {
                    Log.i("GACSignInLoader", "Unexpected InterruptedException", e);
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            i = it.next().a(this) ? i2 + 1 : i2;
        }
    }

    @Override // com.google.android.gms.common.api.a.ab
    public final void onComplete() {
        this.bHl.release();
    }

    @Override // android.support.v4.content.f
    protected final void onStartLoading() {
        this.bHl.drainPermits();
        forceLoad();
    }
}
