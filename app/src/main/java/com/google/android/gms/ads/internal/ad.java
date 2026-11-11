package com.google.android.gms.ads.internal;

import android.os.AsyncTask;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzci;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
final class ad extends AsyncTask<Void, Void, String> {
    private final /* synthetic */ zzbp bCi;

    private ad(zzbp zzbpVar) {
        this.bCi = zzbpVar;
    }

    /* synthetic */ ad(zzbp zzbpVar, aa aaVar) {
        this(zzbpVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // android.os.AsyncTask
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final String doInBackground(Void... voidArr) {
        try {
            this.bCi.bCg = (zzci) this.bCi.bCd.get(((Long) zzkb.zzik().zzd(zznk.zzbdb)).longValue(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            zzane.zzc("", e);
        }
        return this.bCi.zzea();
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ void onPostExecute(String str) {
        String str2 = str;
        if (this.bCi.bCf == null || str2 == null) {
            return;
        }
        this.bCi.bCf.loadUrl(str2);
    }
}
