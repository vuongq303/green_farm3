package com.google.android.gms.internal.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
final class zzfr extends BroadcastReceiver {
    private final /* synthetic */ zzfp zzagk;

    zzfr(zzfp zzfpVar) {
        this.zzagk = zzfpVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.zzagk.zzm(3);
    }
}
