package com.a.a.a.a.c;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
class c extends BroadcastReceiver {
    final /* synthetic */ b cen;

    c(b bVar) {
        this.cen = bVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        KeyguardManager keyguardManager;
        if (intent == null) {
            return;
        }
        if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
            this.cen.ch(true);
            return;
        }
        if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
            this.cen.ch(false);
        } else {
            if (!"android.intent.action.SCREEN_ON".equals(intent.getAction()) || (keyguardManager = (KeyguardManager) context.getSystemService("keyguard")) == null || keyguardManager.inKeyguardRestrictedInputMode()) {
                return;
            }
            this.cen.ch(false);
        }
    }
}
