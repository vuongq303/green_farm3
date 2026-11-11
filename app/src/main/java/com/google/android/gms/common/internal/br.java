package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.HashMap;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class br extends ab implements Handler.Callback {
    private final Context bIe;

    @GuardedBy("mConnectionStatus")
    private final HashMap<ac, bs> bOG = new HashMap<>();
    private final com.google.android.gms.common.stats.a bOH = com.google.android.gms.common.stats.a.aaY();
    private final long bOI = 5000;
    private final long bOJ = 300000;
    private final Handler mHandler;

    br(Context context) {
        this.bIe = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
    }

    @Override // com.google.android.gms.common.internal.ab
    protected final boolean a(ac acVar, ServiceConnection serviceConnection, String str) {
        boolean zIsBound;
        bf.d(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.bOG) {
            bs bsVar = this.bOG.get(acVar);
            if (bsVar != null) {
                this.mHandler.removeMessages(0, acVar);
                if (!bsVar.a(serviceConnection)) {
                    bsVar.a(serviceConnection, str);
                    switch (bsVar.getState()) {
                        case 1:
                            serviceConnection.onServiceConnected(bsVar.getComponentName(), bsVar.getBinder());
                            break;
                        case 2:
                            bsVar.zzj(str);
                            break;
                    }
                } else {
                    String strValueOf = String.valueOf(acVar);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append(strValueOf).toString());
                }
            } else {
                bsVar = new bs(this, acVar);
                bsVar.a(serviceConnection, str);
                bsVar.zzj(str);
                this.bOG.put(acVar, bsVar);
            }
            zIsBound = bsVar.isBound();
        }
        return zIsBound;
    }

    @Override // com.google.android.gms.common.internal.ab
    protected final void b(ac acVar, ServiceConnection serviceConnection, String str) {
        bf.d(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.bOG) {
            bs bsVar = this.bOG.get(acVar);
            if (bsVar == null) {
                String strValueOf = String.valueOf(acVar);
                throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 50).append("Nonexistent connection status for service config: ").append(strValueOf).toString());
            }
            if (!bsVar.a(serviceConnection)) {
                String strValueOf2 = String.valueOf(acVar);
                throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf2).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append(strValueOf2).toString());
            }
            bsVar.b(serviceConnection, str);
            if (bsVar.zzcv()) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, acVar), this.bOI);
            }
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                synchronized (this.bOG) {
                    ac acVar = (ac) message.obj;
                    bs bsVar = this.bOG.get(acVar);
                    if (bsVar != null && bsVar.zzcv()) {
                        if (bsVar.isBound()) {
                            bsVar.zzk("GmsClientSupervisor");
                        }
                        this.bOG.remove(acVar);
                    }
                    break;
                }
                return true;
            case 1:
                synchronized (this.bOG) {
                    ac acVar2 = (ac) message.obj;
                    bs bsVar2 = this.bOG.get(acVar2);
                    if (bsVar2 != null && bsVar2.getState() == 3) {
                        String strValueOf = String.valueOf(acVar2);
                        Log.wtf("GmsClientSupervisor", new StringBuilder(String.valueOf(strValueOf).length() + 47).append("Timeout waiting for ServiceConnection callback ").append(strValueOf).toString(), new Exception());
                        ComponentName componentName = bsVar2.getComponentName();
                        if (componentName == null) {
                            componentName = acVar2.getComponentName();
                        }
                        bsVar2.onServiceDisconnected(componentName == null ? new ComponentName(acVar2.getPackage(), "unknown") : componentName);
                    }
                    break;
                }
                return true;
            default:
                return false;
        }
    }
}
