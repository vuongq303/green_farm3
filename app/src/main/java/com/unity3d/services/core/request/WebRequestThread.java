package com.unity3d.services.core.request;

import android.os.ConditionVariable;
import com.unity3d.services.core.log.DeviceLog;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class WebRequestThread {
    private static CancelableThreadPoolExecutor _pool;
    private static LinkedBlockingQueue<Runnable> _queue;
    private static boolean _ready = false;
    private static int _corePoolSize = 1;
    private static int _maximumPoolSize = 1;
    private static long _keepAliveTime = 1000;
    private static final Object _readyLock = new Object();

    private static synchronized void init() {
        _queue = new LinkedBlockingQueue<>();
        _pool = new CancelableThreadPoolExecutor(_corePoolSize, _maximumPoolSize, _keepAliveTime, TimeUnit.MILLISECONDS, _queue);
        _pool.prestartAllCoreThreads();
        _queue.add(new Runnable() { // from class: com.unity3d.services.core.request.WebRequestThread.1
            @Override // java.lang.Runnable
            public void run() {
                boolean unused = WebRequestThread._ready = true;
                synchronized (WebRequestThread._readyLock) {
                    WebRequestThread._readyLock.notify();
                }
            }
        });
        while (!_ready) {
            try {
                synchronized (_readyLock) {
                    _readyLock.wait();
                }
            } catch (InterruptedException e) {
                DeviceLog.debug("Couldn't synchronize thread");
            }
        }
    }

    public static synchronized void reset() {
        cancel();
        if (_pool != null) {
            _pool.shutdown();
            try {
                _pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
            }
            _queue.clear();
            _pool = null;
            _queue = null;
            _ready = false;
        }
    }

    public static synchronized void cancel() {
        if (_pool != null) {
            _pool.cancel();
            for (Runnable runnable : _queue) {
                if (runnable instanceof WebRequestRunnable) {
                    ((WebRequestRunnable) runnable).setCancelStatus(true);
                }
            }
            _queue.clear();
            _pool.purge();
        }
    }

    public static synchronized void request(String str, WebRequest.RequestType requestType, Map<String, List<String>> map, Integer num, Integer num2, IWebRequestListener iWebRequestListener) {
        request(str, requestType, map, null, num, num2, iWebRequestListener);
    }

    public static synchronized void request(String str, WebRequest.RequestType requestType, Map<String, List<String>> map, String str2, Integer num, Integer num2, IWebRequestListener iWebRequestListener) {
        if (!_ready) {
            init();
        }
        if (str == null || str.length() < 3) {
            iWebRequestListener.onFailed(str, "Request is NULL or too short");
        } else {
            _queue.add(new WebRequestRunnable(str, requestType.name(), str2, num.intValue(), num2.intValue(), map, iWebRequestListener));
        }
    }

    public static synchronized void setConcurrentRequestCount(int i) {
        _corePoolSize = i;
        _maximumPoolSize = _corePoolSize;
        if (_pool != null) {
            _pool.setCorePoolSize(_corePoolSize);
            _pool.setMaximumPoolSize(_maximumPoolSize);
        }
    }

    public static synchronized void setMaximumPoolSize(int i) {
        _maximumPoolSize = i;
        if (_pool != null) {
            _pool.setMaximumPoolSize(_maximumPoolSize);
        }
    }

    public static synchronized void setKeepAliveTime(long j) {
        _keepAliveTime = j;
        if (_pool != null) {
            _pool.setKeepAliveTime(_keepAliveTime, TimeUnit.MILLISECONDS);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000c A[Catch: all -> 0x0026, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0005, B:11:0x0017, B:7:0x000c), top: B:16:0x0005 }] */
    public static synchronized boolean resolve(final String str, final IResolveHostListener iResolveHostListener) {
        boolean z;
        if (str == null) {
            iResolveHostListener.onFailed(str, ResolveHostError.INVALID_HOST, "Host is NULL");
            z = false;
        } else if (str.length() < 3) {
            iResolveHostListener.onFailed(str, ResolveHostError.INVALID_HOST, "Host is NULL");
            z = false;
        } else {
            new Thread(new Runnable() { // from class: com.unity3d.services.core.request.WebRequestThread.2
                @Override // java.lang.Runnable
                public void run() {
                    Thread thread;
                    final ConditionVariable conditionVariable = new ConditionVariable();
                    try {
                        thread = new Thread(new Runnable() { // from class: com.unity3d.services.core.request.WebRequestThread.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    iResolveHostListener.onResolve(str, InetAddress.getByName(str).getHostAddress());
                                } catch (UnknownHostException e) {
                                    DeviceLog.exception("Unknown host", e);
                                    iResolveHostListener.onFailed(str, ResolveHostError.UNKNOWN_HOST, e.getMessage());
                                }
                                conditionVariable.open();
                            }
                        });
                        try {
                            thread.start();
                        } catch (Exception e) {
                            e = e;
                            DeviceLog.exception("Exception while resolving host", e);
                            iResolveHostListener.onFailed(str, ResolveHostError.UNEXPECTED_EXCEPTION, e.getMessage());
                        }
                    } catch (Exception e2) {
                        e = e2;
                        thread = null;
                    }
                    if (!conditionVariable.block(20000L) && thread != null) {
                        thread.interrupt();
                        iResolveHostListener.onFailed(str, ResolveHostError.TIMEOUT, "Timeout");
                    }
                }
            }).start();
            z = true;
        }
        throw th;
        return z;
    }
}
