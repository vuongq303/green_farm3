package com.gameloft.android2d.iap.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/* JADX INFO: compiled from: IABAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class j {
    private boolean Fc;
    private Context mContext = null;

    protected abstract Integer e(Bundle bundle);

    public j() {
        this.Fc = false;
        this.Fc = false;
    }

    protected void onPreExecute() {
    }

    protected void a(Integer num) {
    }

    protected void onCancelled() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Integer num) {
        if (this.mContext != null) {
            ((Activity) this.mContext).runOnUiThread(new k(this, num));
        }
    }

    public final void a(Bundle bundle, Context context) {
        this.mContext = context;
        onPreExecute();
        new Thread(new l(this, bundle)).start();
    }
}
