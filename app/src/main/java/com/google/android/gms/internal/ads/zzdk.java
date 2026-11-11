package com.google.android.gms.internal.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
final class zzdk extends BroadcastReceiver {
    private final /* synthetic */ zzdi zztj;

    zzdk(zzdi zzdiVar) {
        this.zztj = zzdiVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.zztj.zzaq();
    }
}
