package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.bf;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class a implements ServiceConnection {
    private boolean bHV = false;
    private final BlockingQueue<IBinder> bHW = new LinkedBlockingQueue();

    public IBinder Yx() {
        bf.hW("BlockingServiceConnection.getService() called on main thread");
        if (this.bHV) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.bHV = true;
        return this.bHW.take();
    }

    public IBinder a(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        bf.hW("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.bHV) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.bHV = true;
        IBinder iBinderPoll = this.bHW.poll(j, timeUnit);
        if (iBinderPoll == null) {
            throw new TimeoutException("Timed out waiting for the service connection");
        }
        return iBinderPoll;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.bHW.add(iBinder);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }
}
