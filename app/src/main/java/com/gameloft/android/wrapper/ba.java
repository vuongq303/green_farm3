package com.gameloft.android.wrapper;

import android.widget.Toast;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class ba implements Runnable {
    final /* synthetic */ String biX;

    ba(String str) {
        this.biX = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (au.bit != null) {
            au.bit.cancel();
        }
        au.bit = Toast.makeText(au.getContext(), this.biX, 0);
        au.bit.show();
    }
}
