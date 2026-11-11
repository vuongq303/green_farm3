package com.c.a.a.a;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: AvidStateWatcher.java */
/* JADX INFO: loaded from: classes.dex */
class j extends BroadcastReceiver {
    final /* synthetic */ i ciZ;

    j(i iVar) {
        this.ciZ = iVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        KeyguardManager keyguardManager;
        if (intent != null) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                this.ciZ.cj(true);
                return;
            }
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                this.ciZ.cj(false);
            } else if ("android.intent.action.SCREEN_ON".equals(intent.getAction()) && (keyguardManager = (KeyguardManager) context.getSystemService("keyguard")) != null && !keyguardManager.inKeyguardRestrictedInputMode()) {
                this.ciZ.cj(false);
            }
        }
    }
}
