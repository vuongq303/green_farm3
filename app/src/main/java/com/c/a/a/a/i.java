package com.c.a.a.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import java.util.Iterator;

/* JADX INFO: compiled from: AvidStateWatcher.java */
/* JADX INFO: loaded from: classes.dex */
public class i {
    private static i ciV = new i();
    private boolean ciW;
    private boolean ciX;
    private k ciY;
    private Context context;
    private BroadcastReceiver receiver;

    public static i aiH() {
        return ciV;
    }

    public void init(Context context) {
        aiJ();
        this.context = context;
        aiI();
    }

    public void start() {
        this.ciW = true;
        aiK();
    }

    public void stop() {
        aiJ();
        this.context = null;
        this.ciW = false;
        this.ciX = false;
        this.ciY = null;
    }

    public boolean isActive() {
        return !this.ciX;
    }

    public void a(k kVar) {
        this.ciY = kVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cj(boolean z) {
        if (this.ciX != z) {
            this.ciX = z;
            if (this.ciW) {
                aiK();
                if (this.ciY != null) {
                    this.ciY.ci(isActive());
                }
            }
        }
    }

    private void aiI() {
        this.receiver = new j(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        this.context.registerReceiver(this.receiver, intentFilter);
    }

    private void aiJ() {
        if (this.context != null && this.receiver != null) {
            this.context.unregisterReceiver(this.receiver);
            this.receiver = null;
        }
    }

    private void aiK() {
        boolean z = !this.ciX;
        Iterator<com.c.a.a.a.f.a.a> it = com.c.a.a.a.e.a.aiW().aiX().iterator();
        while (it.hasNext()) {
            it.next().ck(z);
        }
    }
}
