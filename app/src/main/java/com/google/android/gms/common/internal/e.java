package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public abstract class e<T extends IInterface> {
    public static final int CONNECT_STATE_CONNECTED = 4;
    public static final int CONNECT_STATE_DISCONNECTED = 1;
    public static final int CONNECT_STATE_DISCONNECTING = 5;
    public static final int CONNECT_STATE_LOCAL_CONNECTING = 3;
    public static final int CONNECT_STATE_REMOTE_CONNECTING = 2;
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    public static final String KEY_PENDING_INTENT = "pendingIntent";
    protected i mConnectionProgressReportCallbacks;
    private final Context mContext;
    protected AtomicInteger mDisconnectCount;
    final Handler mHandler;
    private final Object mLock;
    private final Looper zzcn;
    private final com.google.android.gms.common.g zzgk;
    private int zzra;
    private long zzrb;
    private long zzrc;
    private int zzrd;
    private long zzre;
    private af zzrf;
    private final ab zzrg;
    private final Object zzrh;

    @GuardedBy("mServiceBrokerLock")
    private at zzri;

    @GuardedBy("mLock")
    private T zzrj;
    private final ArrayList<e<T>.h<?>> zzrk;

    @GuardedBy("mLock")
    private e<T>.k zzrl;

    @GuardedBy("mLock")
    private int zzrm;
    private final f zzrn;
    private final g zzro;
    private final int zzrp;
    private final String zzrq;
    private ConnectionResult zzrr;
    private boolean zzrs;
    private volatile ConnectionInfo zzrt;
    private static final Feature[] zzqz = new Feature[0];
    public static final String FEATURE_GOOGLE_ME = "service_googleme";
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = {"service_esmobile", FEATURE_GOOGLE_ME};

    public final class k implements ServiceConnection {
        private final int bNA;

        public k(int i) {
            this.bNA = i;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder == null) {
                e.this.zzj(16);
                return;
            }
            synchronized (e.this.zzrh) {
                e.this.zzri = au.m(iBinder);
            }
            e.this.onPostServiceBindingHandler(0, null, this.bNA);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (e.this.zzrh) {
                e.this.zzri = null;
            }
            e.this.mHandler.sendMessage(e.this.mHandler.obtainMessage(6, this.bNA, 1));
        }
    }

    public abstract class h<TListener> {
        private TListener bJQ;
        private boolean bNx = false;

        public h(TListener tlistener) {
            this.bJQ = tlistener;
        }

        protected abstract void B(TListener tlistener);

        protected abstract void aaa();

        public void aab() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.bJQ;
                if (this.bNx) {
                    String strValueOf = String.valueOf(this);
                    Log.w("GmsClient", new StringBuilder(String.valueOf(strValueOf).length() + 47).append("Callback proxy ").append(strValueOf).append(" being reused. This is not safe.").toString());
                }
            }
            if (tlistener != null) {
                try {
                    B(tlistener);
                } catch (RuntimeException e) {
                    aaa();
                    throw e;
                }
            } else {
                aaa();
            }
            synchronized (this) {
                this.bNx = true;
            }
            unregister();
        }

        public void ah() {
            synchronized (this) {
                this.bJQ = null;
            }
        }

        public void unregister() {
            ah();
            synchronized (e.this.zzrk) {
                e.this.zzrk.remove(this);
            }
        }
    }

    protected e(Context context, Handler handler, ab abVar, com.google.android.gms.common.g gVar, int i, f fVar, g gVar2) {
        this.mLock = new Object();
        this.zzrh = new Object();
        this.zzrk = new ArrayList<>();
        this.zzrm = 1;
        this.zzrr = null;
        this.zzrs = false;
        this.zzrt = null;
        this.mDisconnectCount = new AtomicInteger(0);
        this.mContext = (Context) bf.d(context, "Context must not be null");
        this.mHandler = (Handler) bf.d(handler, "Handler must not be null");
        this.zzcn = handler.getLooper();
        this.zzrg = (ab) bf.d(abVar, "Supervisor must not be null");
        this.zzgk = (com.google.android.gms.common.g) bf.d(gVar, "API availability must not be null");
        this.zzrp = i;
        this.zzrn = fVar;
        this.zzro = gVar2;
        this.zzrq = null;
    }

    protected e(Context context, Looper looper, int i, f fVar, g gVar, String str) {
        this(context, looper, ab.aH(context), com.google.android.gms.common.g.YD(), i, (f) bf.checkNotNull(fVar), (g) bf.checkNotNull(gVar), str);
    }

    protected e(Context context, Looper looper, ab abVar, com.google.android.gms.common.g gVar, int i, f fVar, g gVar2, String str) {
        this.mLock = new Object();
        this.zzrh = new Object();
        this.zzrk = new ArrayList<>();
        this.zzrm = 1;
        this.zzrr = null;
        this.zzrs = false;
        this.zzrt = null;
        this.mDisconnectCount = new AtomicInteger(0);
        this.mContext = (Context) bf.d(context, "Context must not be null");
        this.zzcn = (Looper) bf.d(looper, "Looper must not be null");
        this.zzrg = (ab) bf.d(abVar, "Supervisor must not be null");
        this.zzgk = (com.google.android.gms.common.g) bf.d(gVar, "API availability must not be null");
        this.mHandler = new q(this, looper);
        this.zzrp = i;
        this.zzrn = fVar;
        this.zzro = gVar2;
        this.zzrq = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(int i, T t) {
        bf.ca((i == 4) == (t != null));
        synchronized (this.mLock) {
            this.zzrm = i;
            this.zzrj = t;
            onSetConnectState(i, t);
            switch (i) {
                case 1:
                    if (this.zzrl != null) {
                        this.zzrg.b(getStartServiceAction(), getStartServicePackage(), getServiceBindFlags(), this.zzrl, getRealClientName());
                        this.zzrl = null;
                    }
                    break;
                case 2:
                case 3:
                    if (this.zzrl != null && this.zzrf != null) {
                        String strAax = this.zzrf.aax();
                        String packageName = this.zzrf.getPackageName();
                        Log.e("GmsClient", new StringBuilder(String.valueOf(strAax).length() + 70 + String.valueOf(packageName).length()).append("Calling connect() while still connected, missing disconnect() for ").append(strAax).append(" on ").append(packageName).toString());
                        this.zzrg.b(this.zzrf.aax(), this.zzrf.getPackageName(), this.zzrf.aav(), this.zzrl, getRealClientName());
                        this.mDisconnectCount.incrementAndGet();
                    }
                    this.zzrl = new k(this.mDisconnectCount.get());
                    this.zzrf = (this.zzrm != 3 || getLocalStartServiceAction() == null) ? new af(getStartServicePackage(), getStartServiceAction(), false, getServiceBindFlags()) : new af(getContext().getPackageName(), getLocalStartServiceAction(), true, getServiceBindFlags());
                    if (!this.zzrg.a(this.zzrf.aax(), this.zzrf.getPackageName(), this.zzrf.aav(), this.zzrl, getRealClientName())) {
                        String strAax2 = this.zzrf.aax();
                        String packageName2 = this.zzrf.getPackageName();
                        Log.e("GmsClient", new StringBuilder(String.valueOf(strAax2).length() + 34 + String.valueOf(packageName2).length()).append("unable to connect to service: ").append(strAax2).append(" on ").append(packageName2).toString());
                        onPostServiceBindingHandler(16, null, this.mDisconnectCount.get());
                    }
                    break;
                case 4:
                    onConnectedLocked(t);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(ConnectionInfo connectionInfo) {
        this.zzrt = connectionInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zza(int i, int i2, T t) {
        boolean z;
        synchronized (this.mLock) {
            if (this.zzrm != i) {
                z = false;
            } else {
                zza(i2, t);
                z = true;
            }
        }
        return z;
    }

    private final boolean zzcq() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzrm == 3;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzcr() {
        if (this.zzrs || TextUtils.isEmpty(getServiceDescriptor()) || TextUtils.isEmpty(getLocalStartServiceAction())) {
            return false;
        }
        try {
            Class.forName(getServiceDescriptor());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzj(int i) {
        int i2;
        if (zzcq()) {
            i2 = 5;
            this.zzrs = true;
        } else {
            i2 = 4;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i2, this.mDisconnectCount.get(), 16));
    }

    public void checkAvailabilityAndConnect() {
        int iIsGooglePlayServicesAvailable = this.zzgk.isGooglePlayServicesAvailable(this.mContext, getMinApkVersion());
        if (iIsGooglePlayServicesAvailable == 0) {
            connect(new l(this));
        } else {
            zza(1, (IInterface) null);
            triggerNotAvailable(new l(this), iIsGooglePlayServicesAvailable, null);
        }
    }

    protected final void checkConnected() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public void connect(i iVar) {
        this.mConnectionProgressReportCallbacks = (i) bf.d(iVar, "Connection progress callbacks cannot be null.");
        zza(2, (IInterface) null);
    }

    protected abstract T createServiceInterface(IBinder iBinder);

    public void disconnect() {
        this.mDisconnectCount.incrementAndGet();
        synchronized (this.zzrk) {
            int size = this.zzrk.size();
            for (int i = 0; i < size; i++) {
                this.zzrk.get(i).ah();
            }
            this.zzrk.clear();
        }
        synchronized (this.zzrh) {
            this.zzri = null;
        }
        zza(1, (IInterface) null);
    }

    @Deprecated
    public final void doCallbackDEPRECATED(e<T>.h<?> hVar) {
        synchronized (this.zzrk) {
            this.zzrk.add(hVar);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, this.mDisconnectCount.get(), -1, hVar));
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        T t;
        at atVar;
        synchronized (this.mLock) {
            i = this.zzrm;
            t = this.zzrj;
        }
        synchronized (this.zzrh) {
            atVar = this.zzri;
        }
        printWriter.append((CharSequence) str).append("mConnectState=");
        switch (i) {
            case 1:
                printWriter.print("DISCONNECTED");
                break;
            case 2:
                printWriter.print("REMOTE_CONNECTING");
                break;
            case 3:
                printWriter.print("LOCAL_CONNECTING");
                break;
            case 4:
                printWriter.print("CONNECTED");
                break;
            case 5:
                printWriter.print("DISCONNECTING");
                break;
            default:
                printWriter.print("UNKNOWN");
                break;
        }
        printWriter.append(" mService=");
        if (t == null) {
            printWriter.append("null");
        } else {
            printWriter.append((CharSequence) getServiceDescriptor()).append("@").append((CharSequence) Integer.toHexString(System.identityHashCode(t.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (atVar == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(atVar.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzrc > 0) {
            PrintWriter printWriterAppend = printWriter.append((CharSequence) str).append("lastConnectedTime=");
            long j = this.zzrc;
            String str2 = simpleDateFormat.format(new Date(this.zzrc));
            printWriterAppend.println(new StringBuilder(String.valueOf(str2).length() + 21).append(j).append(" ").append(str2).toString());
        }
        if (this.zzrb > 0) {
            printWriter.append((CharSequence) str).append("lastSuspendedCause=");
            switch (this.zzra) {
                case 1:
                    printWriter.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                case 2:
                    printWriter.append("CAUSE_NETWORK_LOST");
                    break;
                default:
                    printWriter.append((CharSequence) String.valueOf(this.zzra));
                    break;
            }
            PrintWriter printWriterAppend2 = printWriter.append(" lastSuspendedTime=");
            long j2 = this.zzrb;
            String str3 = simpleDateFormat.format(new Date(this.zzrb));
            printWriterAppend2.println(new StringBuilder(String.valueOf(str3).length() + 21).append(j2).append(" ").append(str3).toString());
        }
        if (this.zzre > 0) {
            printWriter.append((CharSequence) str).append("lastFailedStatus=").append((CharSequence) com.google.android.gms.common.api.s.nx(this.zzrd));
            PrintWriter printWriterAppend3 = printWriter.append(" lastFailedTime=");
            long j3 = this.zzre;
            String str4 = simpleDateFormat.format(new Date(this.zzre));
            printWriterAppend3.println(new StringBuilder(String.valueOf(str4).length() + 21).append(j3).append(" ").append(str4).toString());
        }
    }

    public Account getAccount() {
        return null;
    }

    public final Account getAccountOrDefault() {
        return getAccount() != null ? getAccount() : new Account(DEFAULT_ACCOUNT, "com.google");
    }

    public Feature[] getApiFeatures() {
        return zzqz;
    }

    public final Feature[] getAvailableFeatures() {
        ConnectionInfo connectionInfo = this.zzrt;
        if (connectionInfo == null) {
            return null;
        }
        return connectionInfo.getAvailableFeatures();
    }

    public Bundle getConnectionHint() {
        return null;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public String getEndpointPackageName() {
        if (!isConnected() || this.zzrf == null) {
            throw new RuntimeException("Failed to connect when checking package");
        }
        return this.zzrf.getPackageName();
    }

    protected Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    public final Handler getHandlerForTesting() {
        return this.mHandler;
    }

    protected String getLocalStartServiceAction() {
        return null;
    }

    public final Looper getLooper() {
        return this.zzcn;
    }

    public int getMinApkVersion() {
        return com.google.android.gms.common.g.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    protected final String getRealClientName() {
        return this.zzrq == null ? this.mContext.getClass().getName() : this.zzrq;
    }

    public void getRemoteService(ah ahVar, Set<Scope> set) {
        GetServiceRequest getServiceRequestV = new GetServiceRequest(this.zzrp).ia(this.mContext.getPackageName()).v(getGetServiceRequestExtraArgs());
        if (set != null) {
            getServiceRequestV.d(set);
        }
        if (requiresSignIn()) {
            getServiceRequestV.c(getAccountOrDefault()).b(ahVar);
        } else if (requiresAccount()) {
            getServiceRequestV.c(getAccount());
        }
        getServiceRequestV.a(getRequiredFeatures());
        getServiceRequestV.b(getApiFeatures());
        try {
            synchronized (this.zzrh) {
                try {
                    if (this.zzri != null) {
                        this.zzri.a(new j(this, this.mDisconnectCount.get()), getServiceRequestV);
                    } else {
                        Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            triggerConnectionSuspended(1);
        } catch (RemoteException e2) {
            e = e2;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.mDisconnectCount.get());
        } catch (SecurityException e3) {
            throw e3;
        } catch (RuntimeException e4) {
            e = e4;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.mDisconnectCount.get());
        }
    }

    public Feature[] getRequiredFeatures() {
        return zzqz;
    }

    protected Set<Scope> getScopes() {
        return Collections.EMPTY_SET;
    }

    public final T getService() {
        T t;
        synchronized (this.mLock) {
            if (this.zzrm == 5) {
                throw new DeadObjectException();
            }
            checkConnected();
            bf.a(this.zzrj != null, "Client is connected but service is null");
            t = this.zzrj;
        }
        return t;
    }

    protected int getServiceBindFlags() {
        return 129;
    }

    public IBinder getServiceBrokerBinder() {
        IBinder iBinderAsBinder;
        synchronized (this.zzrh) {
            iBinderAsBinder = this.zzri == null ? null : this.zzri.asBinder();
        }
        return iBinderAsBinder;
    }

    public final at getServiceBrokerForTesting() {
        at atVar;
        synchronized (this.zzrh) {
            atVar = this.zzri;
        }
        return atVar;
    }

    protected abstract String getServiceDescriptor();

    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    protected abstract String getStartServiceAction();

    protected String getStartServicePackage() {
        return "com.google.android.gms";
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzrm == 4;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzrm == 2 || this.zzrm == 3;
        }
        return z;
    }

    protected void onConnectedLocked(T t) {
        this.zzrc = System.currentTimeMillis();
    }

    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzrd = connectionResult.getErrorCode();
        this.zzre = System.currentTimeMillis();
    }

    protected void onConnectionSuspended(int i) {
        this.zzra = i;
        this.zzrb = System.currentTimeMillis();
    }

    protected void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i2, -1, new m(this, i, iBinder, bundle)));
    }

    protected void onPostServiceBindingHandler(int i, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, i2, -1, new n(this, i, bundle)));
    }

    void onSetConnectState(int i, T t) {
    }

    public void onUserSignOut(o oVar) {
        oVar.ZF();
    }

    public boolean providesSignIn() {
        return false;
    }

    public boolean requiresAccount() {
        return false;
    }

    public boolean requiresGooglePlayServices() {
        return true;
    }

    public boolean requiresSignIn() {
        return false;
    }

    public void setConnectionInfoForTesting(ConnectionInfo connectionInfo) {
        this.zzrt = connectionInfo;
    }

    public final void setServiceBrokerForTesting(at atVar) {
        synchronized (this.zzrh) {
            this.zzri = atVar;
        }
    }

    public final void setServiceForTesting(T t) {
        zza(t != null ? 4 : 1, t);
    }

    public void triggerConnectionSuspended(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, this.mDisconnectCount.get(), i));
    }

    protected void triggerNotAvailable(i iVar, int i, PendingIntent pendingIntent) {
        this.mConnectionProgressReportCallbacks = (i) bf.d(iVar, "Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.mDisconnectCount.get(), i, pendingIntent));
    }
}
