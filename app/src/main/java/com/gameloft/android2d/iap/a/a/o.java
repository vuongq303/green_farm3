package com.gameloft.android2d.iap.a.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: IabBroadcastReceiver.java */
/* JADX INFO: loaded from: classes.dex */
public class o extends BroadcastReceiver {
    private final p blY;

    public o(p pVar) {
        this.blY = pVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.blY != null) {
            this.blY.Sk();
        }
    }
}
