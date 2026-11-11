package com.google.android.gms.internal.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
final class zzeu extends BroadcastReceiver {
    private final /* synthetic */ zzet zzafk;

    zzeu(zzet zzetVar) {
        this.zzafk = zzetVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.zzafk.zzl(3);
    }
}
