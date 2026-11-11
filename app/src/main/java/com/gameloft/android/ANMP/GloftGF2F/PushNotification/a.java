package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.os.Bundle;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: AsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    private boolean Fc;

    protected abstract Integer e(Bundle bundle);

    public a() {
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
        au.getActivity().runOnUiThread(new b(this, num));
    }

    public final void f(Bundle bundle) {
        onPreExecute();
        new Thread(new c(this, bundle), "Thread-doInBG").start();
    }
}
