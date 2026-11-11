package com.google.android.gms.common.api.a;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.internal.SignInResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class aq implements bh {
    private final com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bIJ;
    private final bi bKe;
    private final Lock bKh;
    private ConnectionResult bKi;
    private int bKj;
    private int bKl;
    private com.google.android.gms.signin.c bKo;
    private boolean bKp;
    private boolean bKq;
    private com.google.android.gms.common.internal.ah bKr;
    private boolean bKs;
    private boolean bKt;
    private final Map<com.google.android.gms.common.api.a<?>, Boolean> bKu;
    private final Context mContext;
    private final com.google.android.gms.common.internal.r zzgf;
    private final com.google.android.gms.common.g zzgk;
    private boolean zzho;
    private int bKk = 0;
    private final Bundle bKm = new Bundle();
    private final Set<com.google.android.gms.common.api.d> bKn = new HashSet();
    private ArrayList<Future<?>> bKv = new ArrayList<>();

    public aq(bi biVar, com.google.android.gms.common.internal.r rVar, Map<com.google.android.gms.common.api.a<?>, Boolean> map, com.google.android.gms.common.g gVar, com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bVar, Lock lock, Context context) {
        this.bKe = biVar;
        this.zzgf = rVar;
        this.bKu = map;
        this.zzgk = gVar;
        this.bIJ = bVar;
        this.bKh = lock;
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean Zw() {
        this.bKl--;
        if (this.bKl > 0) {
            return false;
        }
        if (this.bKl < 0) {
            Log.w("GoogleApiClientConnecting", this.bKe.bLd.zzbb());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            f(new ConnectionResult(8, null));
            return false;
        }
        if (this.bKi == null) {
            return true;
        }
        this.bKe.zzje = this.bKj;
        f(this.bKi);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void Zx() {
        if (this.bKl != 0) {
            return;
        }
        if (!this.bKp || this.bKq) {
            ArrayList arrayList = new ArrayList();
            this.bKk = 1;
            this.bKl = this.bKe.bKO.size();
            for (com.google.android.gms.common.api.d<?> dVar : this.bKe.bKO.keySet()) {
                if (!this.bKe.bLa.containsKey(dVar)) {
                    arrayList.add(this.bKe.bKO.get(dVar));
                } else if (Zw()) {
                    Zy();
                }
            }
            if (arrayList.isEmpty()) {
                return;
            }
            this.bKv.add(bl.ZE().submit(new aw(this, arrayList)));
        }
    }

    @GuardedBy("mLock")
    private final void Zy() {
        this.bKe.zzbd();
        bl.ZE().execute(new ar(this));
        if (this.bKo != null) {
            if (this.bKs) {
                this.bKo.a(this.bKr, this.bKt);
            }
            zza(false);
        }
        Iterator<com.google.android.gms.common.api.d<?>> it = this.bKe.bLa.keySet().iterator();
        while (it.hasNext()) {
            this.bKe.bKO.get(it.next()).disconnect();
        }
        this.bKe.bLe.s(this.bKm.isEmpty() ? null : this.bKm);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<Scope> Zz() {
        if (this.zzgf == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(this.zzgf.aaf());
        Map<com.google.android.gms.common.api.a<?>, com.google.android.gms.common.internal.t> mapAah = this.zzgf.aah();
        for (com.google.android.gms.common.api.a<?> aVar : mapAah.keySet()) {
            if (!this.bKe.bLa.containsKey(aVar.getClientKey())) {
                hashSet.addAll(mapAah.get(aVar).mScopes);
            }
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void a(SignInResponse signInResponse) {
        if (nz(0)) {
            ConnectionResult connectionResultZM = signInResponse.ZM();
            if (!connectionResultZM.Sp()) {
                if (!e(connectionResultZM)) {
                    f(connectionResultZM);
                    return;
                } else {
                    zzau();
                    Zx();
                    return;
                }
            }
            ResolveAccountResponse resolveAccountResponseAgm = signInResponse.agm();
            ConnectionResult connectionResultZM2 = resolveAccountResponseAgm.ZM();
            if (!connectionResultZM2.Sp()) {
                String strValueOf = String.valueOf(connectionResultZM2);
                Log.wtf("GoogleApiClientConnecting", new StringBuilder(String.valueOf(strValueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(strValueOf).toString(), new Exception());
                f(connectionResultZM2);
            } else {
                this.bKq = true;
                this.bKr = resolveAccountResponseAgm.aaC();
                this.bKs = resolveAccountResponseAgm.aaD();
                this.bKt = resolveAccountResponseAgm.aaE();
                Zx();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:20:0x003f  */
    /* JADX WARN: Code duplicated, block: B:8:0x0015  */
    @GuardedBy("mLock")
    public final void b(ConnectionResult connectionResult, com.google.android.gms.common.api.a<?> aVar, boolean z) {
        boolean z2 = true;
        int priority = aVar.YI().getPriority();
        if (z) {
            boolean z3 = connectionResult.Yy() || this.zzgk.nt(connectionResult.getErrorCode()) != null;
            if (!z3) {
                z2 = false;
            } else if (this.bKi != null) {
                z2 = false;
            }
        } else if (this.bKi != null && priority >= this.bKj) {
            z2 = false;
        }
        if (z2) {
            this.bKi = connectionResult;
            this.bKj = priority;
        }
        this.bKe.bLa.put(aVar.getClientKey(), connectionResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean e(ConnectionResult connectionResult) {
        return this.zzho && !connectionResult.Yy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void f(ConnectionResult connectionResult) {
        zzav();
        zza(!connectionResult.Yy());
        this.bKe.h(connectionResult);
        this.bKe.bLe.g(connectionResult);
    }

    private static String nA(int i) {
        switch (i) {
            case 0:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case 1:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean nz(int i) {
        if (this.bKk == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.bKe.bLd.zzbb());
        String strValueOf = String.valueOf(this);
        Log.w("GoogleApiClientConnecting", new StringBuilder(String.valueOf(strValueOf).length() + 23).append("Unexpected callback in ").append(strValueOf).toString());
        Log.w("GoogleApiClientConnecting", new StringBuilder(33).append("mRemainingConnections=").append(this.bKl).toString());
        String strNA = nA(this.bKk);
        String strNA2 = nA(i);
        Log.wtf("GoogleApiClientConnecting", new StringBuilder(String.valueOf(strNA).length() + 70 + String.valueOf(strNA2).length()).append("GoogleApiClient connecting is in step ").append(strNA).append(" but received callback for step ").append(strNA2).toString(), new Exception());
        f(new ConnectionResult(8, null));
        return false;
    }

    private final void zza(boolean z) {
        if (this.bKo != null) {
            if (this.bKo.isConnected() && z) {
                this.bKo.agc();
            }
            this.bKo.disconnect();
            this.bKr = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zzau() {
        this.bKp = false;
        this.bKe.bLd.bKP = Collections.emptySet();
        for (com.google.android.gms.common.api.d<?> dVar : this.bKn) {
            if (!this.bKe.bLa.containsKey(dVar)) {
                this.bKe.bLa.put(dVar, new ConnectionResult(17, null));
            }
        }
    }

    private final void zzav() {
        ArrayList<Future<?>> arrayList = this.bKv;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Future<?> future = arrayList.get(i);
            i++;
            future.cancel(true);
        }
        this.bKv.clear();
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t) {
        this.bKe.bLd.bKI.add(t);
        return t;
    }

    @Override // com.google.android.gms.common.api.a.bh
    @GuardedBy("mLock")
    public final void a(ConnectionResult connectionResult, com.google.android.gms.common.api.a<?> aVar, boolean z) {
        if (nz(1)) {
            b(connectionResult, aVar, z);
            if (Zw()) {
                Zy();
            }
        }
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void begin() {
        ar arVar = null;
        this.bKe.bLa.clear();
        this.bKp = false;
        this.bKi = null;
        this.bKk = 0;
        this.zzho = true;
        this.bKq = false;
        this.bKs = false;
        HashMap map = new HashMap();
        boolean z = false;
        for (com.google.android.gms.common.api.a<?> aVar : this.bKu.keySet()) {
            com.google.android.gms.common.api.l lVar = this.bKe.bKO.get(aVar.getClientKey());
            boolean z2 = (aVar.YI().getPriority() == 1) | z;
            boolean zBooleanValue = this.bKu.get(aVar).booleanValue();
            if (lVar.requiresSignIn()) {
                this.bKp = true;
                if (zBooleanValue) {
                    this.bKn.add(aVar.getClientKey());
                } else {
                    this.zzho = false;
                }
            }
            map.put(lVar, new as(this, aVar, zBooleanValue));
            z = z2;
        }
        if (z) {
            this.bKp = false;
        }
        if (this.bKp) {
            this.zzgf.c(Integer.valueOf(System.identityHashCode(this.bKe.bLd)));
            az azVar = new az(this, arVar);
            this.bKo = (com.google.android.gms.signin.c) this.bIJ.a(this.mContext, this.bKe.bLd.getLooper(), this.zzgf, this.zzgf.aal(), azVar, azVar);
        }
        this.bKl = this.bKe.bKO.size();
        this.bKv.add(bl.ZE().submit(new at(this, map)));
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void connect() {
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final boolean disconnect() {
        zzav();
        zza(true);
        this.bKe.h(null);
        return true;
    }

    @Override // com.google.android.gms.common.api.a.bh
    @GuardedBy("mLock")
    public final void onConnected(Bundle bundle) {
        if (nz(1)) {
            if (bundle != null) {
                this.bKm.putAll(bundle);
            }
            if (Zw()) {
                Zy();
            }
        }
    }

    @Override // com.google.android.gms.common.api.a.bh
    @GuardedBy("mLock")
    public final void onConnectionSuspended(int i) {
        f(new ConnectionResult(8, null));
    }
}
