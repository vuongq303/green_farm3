package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.stats.a;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class zzm implements ServiceConnection {

    @GuardedBy("this")
    int state;
    final Messenger zzbqz;
    zzr zzbra;

    @GuardedBy("this")
    final Queue<zzt<?>> zzbrb;

    @GuardedBy("this")
    final SparseArray<zzt<?>> zzbrc;
    final /* synthetic */ zzk zzbrd;

    private zzm(zzk zzkVar) {
        this.zzbrd = zzkVar;
        this.state = 0;
        this.zzbqz = new Messenger(new Handler(Looper.getMainLooper(), new Handler.Callback(this) { // from class: com.google.firebase.iid.zzn
            private final zzm zzbre;

            {
                this.zzbre = this;
            }

            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.zzbre.zza(message);
            }
        }));
        this.zzbrb = new ArrayDeque();
        this.zzbrc = new SparseArray<>();
    }

    private final void zzsq() {
        this.zzbrd.zzbqw.execute(new Runnable(this) { // from class: com.google.firebase.iid.zzp
            private final zzm zzbre;

            {
                this.zzbre = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                final zzt<?> zztVarPoll;
                final zzm zzmVar = this.zzbre;
                while (true) {
                    synchronized (zzmVar) {
                        if (zzmVar.state != 2) {
                            return;
                        }
                        if (zzmVar.zzbrb.isEmpty()) {
                            zzmVar.zzsr();
                            return;
                        } else {
                            zztVarPoll = zzmVar.zzbrb.poll();
                            zzmVar.zzbrc.put(zztVarPoll.zzbrh, zztVarPoll);
                            zzmVar.zzbrd.zzbqw.schedule(new Runnable(zzmVar, zztVarPoll) { // from class: com.google.firebase.iid.zzq
                                private final zzm zzbre;
                                private final zzt zzbrf;

                                {
                                    this.zzbre = zzmVar;
                                    this.zzbrf = zztVarPoll;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.zzbre.zzae(this.zzbrf.zzbrh);
                                }
                            }, 30L, TimeUnit.SECONDS);
                        }
                    }
                    if (Log.isLoggable("MessengerIpcClient", 3)) {
                        String strValueOf = String.valueOf(zztVarPoll);
                        Log.d("MessengerIpcClient", new StringBuilder(String.valueOf(strValueOf).length() + 8).append("Sending ").append(strValueOf).toString());
                    }
                    Context context = zzmVar.zzbrd.zzqs;
                    Messenger messenger = zzmVar.zzbqz;
                    Message messageObtain = Message.obtain();
                    messageObtain.what = zztVarPoll.what;
                    messageObtain.arg1 = zztVarPoll.zzbrh;
                    messageObtain.replyTo = messenger;
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("oneWay", zztVarPoll.zzst());
                    bundle.putString("pkg", context.getPackageName());
                    bundle.putBundle(ShareConstants.WEB_DIALOG_PARAM_DATA, zztVarPoll.zzbrj);
                    messageObtain.setData(bundle);
                    try {
                        zzmVar.zzbra.send(messageObtain);
                    } catch (RemoteException e) {
                        zzmVar.zzb(2, e.getMessage());
                    }
                }
            }
        });
    }

    @Override // android.content.ServiceConnection
    public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service connected");
        }
        if (iBinder == null) {
            zzb(0, "Null service connection");
        } else {
            try {
                this.zzbra = new zzr(iBinder);
                this.state = 2;
                zzsq();
            } catch (RemoteException e) {
                zzb(0, e.getMessage());
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final synchronized void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service disconnected");
        }
        zzb(2, "Service disconnected");
    }

    final boolean zza(Message message) {
        int i = message.arg1;
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            Log.d("MessengerIpcClient", new StringBuilder(41).append("Received response to request: ").append(i).toString());
        }
        synchronized (this) {
            zzt<?> zztVar = this.zzbrc.get(i);
            if (zztVar == null) {
                Log.w("MessengerIpcClient", new StringBuilder(50).append("Received response for unknown request: ").append(i).toString());
            } else {
                this.zzbrc.remove(i);
                zzsr();
                Bundle data = message.getData();
                if (data.getBoolean("unsupported", false)) {
                    zztVar.zza(new zzu(4, "Not supported by GmsCore"));
                } else {
                    zztVar.zzh(data);
                }
            }
        }
        return true;
    }

    final synchronized void zzae(int i) {
        zzt<?> zztVar = this.zzbrc.get(i);
        if (zztVar != null) {
            Log.w("MessengerIpcClient", new StringBuilder(31).append("Timing out request: ").append(i).toString());
            this.zzbrc.remove(i);
            zztVar.zza(new zzu(3, "Timed out waiting for response"));
            zzsr();
        }
    }

    final synchronized void zzb(int i, String str) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String strValueOf = String.valueOf(str);
            Log.d("MessengerIpcClient", strValueOf.length() != 0 ? "Disconnected: ".concat(strValueOf) : new String("Disconnected: "));
        }
        switch (this.state) {
            case 0:
                throw new IllegalStateException();
            case 1:
            case 2:
                if (Log.isLoggable("MessengerIpcClient", 2)) {
                    Log.v("MessengerIpcClient", "Unbinding service");
                }
                this.state = 4;
                a.aaY().a(this.zzbrd.zzqs, this);
                zzu zzuVar = new zzu(i, str);
                Iterator<zzt<?>> it = this.zzbrb.iterator();
                while (it.hasNext()) {
                    it.next().zza(zzuVar);
                }
                this.zzbrb.clear();
                for (int i2 = 0; i2 < this.zzbrc.size(); i2++) {
                    this.zzbrc.valueAt(i2).zza(zzuVar);
                }
                this.zzbrc.clear();
                break;
            case 3:
                this.state = 4;
                break;
            case 4:
                break;
            default:
                throw new IllegalStateException(new StringBuilder(26).append("Unknown state: ").append(this.state).toString());
        }
    }

    final synchronized boolean zzb(zzt zztVar) {
        boolean z = true;
        synchronized (this) {
            switch (this.state) {
                case 0:
                    this.zzbrb.add(zztVar);
                    bf.bZ(this.state == 0);
                    if (Log.isLoggable("MessengerIpcClient", 2)) {
                        Log.v("MessengerIpcClient", "Starting bind to GmsCore");
                    }
                    this.state = 1;
                    Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                    intent.setPackage("com.google.android.gms");
                    if (!a.aaY().a(this.zzbrd.zzqs, intent, this, 1)) {
                        zzb(0, "Unable to bind to service");
                    } else {
                        this.zzbrd.zzbqw.schedule(new Runnable(this) { // from class: com.google.firebase.iid.zzo
                            private final zzm zzbre;

                            {
                                this.zzbre = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                this.zzbre.zzss();
                            }
                        }, 30L, TimeUnit.SECONDS);
                    }
                    break;
                case 1:
                    this.zzbrb.add(zztVar);
                    break;
                case 2:
                    this.zzbrb.add(zztVar);
                    zzsq();
                    break;
                case 3:
                case 4:
                    z = false;
                    break;
                default:
                    throw new IllegalStateException(new StringBuilder(26).append("Unknown state: ").append(this.state).toString());
            }
        }
        return z;
    }

    final synchronized void zzsr() {
        if (this.state == 2 && this.zzbrb.isEmpty() && this.zzbrc.size() == 0) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Finished handling requests, unbinding");
            }
            this.state = 3;
            a.aaY().a(this.zzbrd.zzqs, this);
        }
    }

    final synchronized void zzss() {
        if (this.state == 1) {
            zzb(1, "Timed out while binding");
        }
    }
}
