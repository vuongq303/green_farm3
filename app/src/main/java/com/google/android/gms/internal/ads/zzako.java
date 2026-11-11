package com.google.android.gms.internal.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
final class zzako extends BroadcastReceiver {
    private zzako(zzakk zzakkVar) {
    }

    /* synthetic */ zzako(zzakk zzakkVar, zzakl zzaklVar) {
        this(zzakkVar);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        zzamy.zzsj();
        zzajz.zzai(context);
    }
}
