package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class bs implements ServiceConnection {
    private ComponentName E;
    private IBinder bND;
    private boolean bOL;
    private final ac bOM;
    private final /* synthetic */ br bON;
    private final Set<ServiceConnection> bOK = new HashSet();
    private int mState = 2;

    public bs(br brVar, ac acVar) {
        this.bON = brVar;
        this.bOM = acVar;
    }

    public final void a(ServiceConnection serviceConnection, String str) {
        this.bON.bOH.a(this.bON.bIe, serviceConnection, str, this.bOM.aI(this.bON.bIe));
        this.bOK.add(serviceConnection);
    }

    public final boolean a(ServiceConnection serviceConnection) {
        return this.bOK.contains(serviceConnection);
    }

    public final void b(ServiceConnection serviceConnection, String str) {
        this.bON.bOH.b(this.bON.bIe, serviceConnection);
        this.bOK.remove(serviceConnection);
    }

    public final IBinder getBinder() {
        return this.bND;
    }

    public final ComponentName getComponentName() {
        return this.E;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isBound() {
        return this.bOL;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.bON.bOG) {
            this.bON.mHandler.removeMessages(1, this.bOM);
            this.bND = iBinder;
            this.E = componentName;
            Iterator<ServiceConnection> it = this.bOK.iterator();
            while (it.hasNext()) {
                it.next().onServiceConnected(componentName, iBinder);
            }
            this.mState = 1;
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.bON.bOG) {
            this.bON.mHandler.removeMessages(1, this.bOM);
            this.bND = null;
            this.E = componentName;
            Iterator<ServiceConnection> it = this.bOK.iterator();
            while (it.hasNext()) {
                it.next().onServiceDisconnected(componentName);
            }
            this.mState = 2;
        }
    }

    public final boolean zzcv() {
        return this.bOK.isEmpty();
    }

    public final void zzj(String str) {
        this.mState = 3;
        this.bOL = this.bON.bOH.a(this.bON.bIe, str, this.bOM.aI(this.bON.bIe), this, this.bOM.aav());
        if (this.bOL) {
            this.bON.mHandler.sendMessageDelayed(this.bON.mHandler.obtainMessage(1, this.bOM), this.bON.bOJ);
        } else {
            this.mState = 2;
            try {
                this.bON.bOH.a(this.bON.bIe, this);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    public final void zzk(String str) {
        this.bON.mHandler.removeMessages(1, this.bOM);
        this.bON.bOH.a(this.bON.bIe, this);
        this.bOL = false;
        this.mState = 2;
    }
}
