package com.quanhoang.greenfarm3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class GoogleIAPTrackerReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            try {
                if (com.gameloft.android.wrapper.au.getContext() == null) {
                    com.gameloft.android.wrapper.au.an(context);
                }
                com.gameloft.android.wrapper.au.PA();
                com.gameloft.android.wrapper.au.PC();
            } catch (Exception e) {
                String str = "Error saving injected IGP and SerialKey" + e.toString();
            }
        }
    }
}
