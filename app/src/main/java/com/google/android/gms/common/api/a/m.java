package com.google.android.gms.common.api.a;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class m<O extends com.google.android.gms.common.api.e> implements df, com.google.android.gms.common.api.y, com.google.android.gms.common.api.z {
    final /* synthetic */ l bJC;
    private final com.google.android.gms.common.api.l bJs;
    private final com.google.android.gms.common.api.c bJt;
    private final cv<O> bJu;
    private final ah bJv;
    private final cc bJy;
    private boolean bJz;
    private final int zzkf;
    private final Queue<be> bJr = new LinkedList();
    private final Set<cy> bJw = new HashSet();
    private final Map<w<?>, bz> bJx = new HashMap();
    private final List<n> bJA = new ArrayList();
    private ConnectionResult bJB = null;

    public m(l lVar, com.google.android.gms.common.api.t<O> tVar) {
        this.bJC = lVar;
        this.bJs = tVar.zza(lVar.handler.getLooper(), this);
        if (this.bJs instanceof com.google.android.gms.common.internal.bi) {
            this.bJt = ((com.google.android.gms.common.internal.bi) this.bJs).aaF();
        } else {
            this.bJt = this.bJs;
        }
        this.bJu = tVar.zzm();
        this.bJv = new ah();
        this.zzkf = tVar.getInstanceId();
        if (this.bJs.requiresSignIn()) {
            this.bJy = tVar.zza(lVar.bJi, lVar.handler);
        } else {
            this.bJy = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void YZ() {
        zzbo();
        c(ConnectionResult.bHX);
        zzbq();
        Iterator<bz> it = this.bJx.values().iterator();
        while (it.hasNext()) {
            try {
                it.next().bLv.a(this.bJt, new com.google.android.gms.f.g<>());
            } catch (DeadObjectException e) {
                onConnectionSuspended(1);
                this.bJs.disconnect();
            } catch (RemoteException e2) {
            }
        }
        zzbl();
        zzbr();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void Za() {
        zzbo();
        this.bJz = true;
        this.bJv.zzal();
        this.bJC.handler.sendMessageDelayed(Message.obtain(this.bJC.handler, 9, this.bJu), this.bJC.bJe);
        this.bJC.handler.sendMessageDelayed(Message.obtain(this.bJC.handler, 11, this.bJu), this.bJC.bJf);
        this.bJC.bJk.flush();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(n nVar) {
        if (this.bJA.contains(nVar) && !this.bJz) {
            if (this.bJs.isConnected()) {
                zzbl();
            } else {
                connect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void b(n nVar) {
        Feature[] requiredFeatures;
        if (this.bJA.remove(nVar)) {
            this.bJC.handler.removeMessages(15, nVar);
            this.bJC.handler.removeMessages(16, nVar);
            Feature feature = nVar.bIV;
            ArrayList arrayList = new ArrayList(this.bJr.size());
            for (be beVar : this.bJr) {
                if ((beVar instanceof ct) && (requiredFeatures = ((ct) beVar).getRequiredFeatures()) != null && com.google.android.gms.common.d.b.b(requiredFeatures, feature)) {
                    arrayList.add(beVar);
                }
            }
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                be beVar2 = (be) obj;
                this.bJr.remove(beVar2);
                beVar2.b(new com.google.android.gms.common.api.aj(feature));
            }
        }
    }

    private final boolean b(ConnectionResult connectionResult) {
        boolean z;
        synchronized (l.lock) {
            if (this.bJC.bJo == null || !this.bJC.bJp.contains(this.bJu)) {
                z = false;
            } else {
                this.bJC.bJo.c(connectionResult, this.zzkf);
                z = true;
            }
        }
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean b(be beVar) {
        if (!(beVar instanceof ct)) {
            c(beVar);
            return true;
        }
        ct ctVar = (ct) beVar;
        Feature[] requiredFeatures = ctVar.getRequiredFeatures();
        if (requiredFeatures == null || requiredFeatures.length == 0) {
            c(beVar);
            return true;
        }
        Feature[] availableFeatures = this.bJs.getAvailableFeatures();
        if (availableFeatures == null) {
            availableFeatures = new Feature[0];
        }
        android.support.v4.e.a aVar = new android.support.v4.e.a(availableFeatures.length);
        for (Feature feature : availableFeatures) {
            aVar.put(feature.getName(), Long.valueOf(feature.YA()));
        }
        for (Feature feature2 : requiredFeatures) {
            if (!aVar.containsKey(feature2.getName()) || ((Long) aVar.get(feature2.getName())).longValue() < feature2.YA()) {
                if (ctVar.shouldAutoResolveMissingFeatures()) {
                    n nVar = new n(this.bJu, feature2, null);
                    int iIndexOf = this.bJA.indexOf(nVar);
                    if (iIndexOf >= 0) {
                        n nVar2 = this.bJA.get(iIndexOf);
                        this.bJC.handler.removeMessages(15, nVar2);
                        this.bJC.handler.sendMessageDelayed(Message.obtain(this.bJC.handler, 15, nVar2), this.bJC.bJe);
                    } else {
                        this.bJA.add(nVar);
                        this.bJC.handler.sendMessageDelayed(Message.obtain(this.bJC.handler, 15, nVar), this.bJC.bJe);
                        this.bJC.handler.sendMessageDelayed(Message.obtain(this.bJC.handler, 16, nVar), this.bJC.bJf);
                        ConnectionResult connectionResult = new ConnectionResult(2, null);
                        if (!b(connectionResult)) {
                            this.bJC.a(connectionResult, this.zzkf);
                        }
                    }
                } else {
                    ctVar.b(new com.google.android.gms.common.api.aj(feature2));
                }
                return false;
            }
            this.bJA.remove(new n(this.bJu, feature2, null));
        }
        c(beVar);
        return true;
    }

    private final void c(ConnectionResult connectionResult) {
        for (cy cyVar : this.bJw) {
            String endpointPackageName = null;
            if (com.google.android.gms.common.internal.bd.b(connectionResult, ConnectionResult.bHX)) {
                endpointPackageName = this.bJs.getEndpointPackageName();
            }
            cyVar.a(this.bJu, connectionResult, endpointPackageName);
        }
        this.bJw.clear();
    }

    private final void c(be beVar) {
        beVar.a(this.bJv, requiresSignIn());
        try {
            beVar.d(this);
        } catch (DeadObjectException e) {
            onConnectionSuspended(1);
            this.bJs.disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzb(boolean z) {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        if (!this.bJs.isConnected() || this.bJx.size() != 0) {
            return false;
        }
        if (!this.bJv.Zq()) {
            this.bJs.disconnect();
            return true;
        }
        if (!z) {
            return false;
        }
        zzbr();
        return false;
    }

    private final void zzbl() {
        ArrayList arrayList = new ArrayList(this.bJr);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            be beVar = (be) obj;
            if (!this.bJs.isConnected()) {
                return;
            }
            if (b(beVar)) {
                this.bJr.remove(beVar);
            }
        }
    }

    private final void zzbq() {
        if (this.bJz) {
            this.bJC.handler.removeMessages(11, this.bJu);
            this.bJC.handler.removeMessages(9, this.bJu);
            this.bJz = false;
        }
    }

    private final void zzbr() {
        this.bJC.handler.removeMessages(12, this.bJu);
        this.bJC.handler.sendMessageDelayed(this.bJC.handler.obtainMessage(12, this.bJu), this.bJC.bJg);
    }

    public final com.google.android.gms.common.api.l Zb() {
        return this.bJs;
    }

    public final Map<w<?>, bz> Zc() {
        return this.bJx;
    }

    public final ConnectionResult Zd() {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        return this.bJB;
    }

    public final void Ze() {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        if (this.bJz) {
            zzbq();
            c(this.bJC.bJj.isGooglePlayServicesAvailable(this.bJC.bJi) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
            this.bJs.disconnect();
        }
    }

    public final boolean Zf() {
        return zzb(true);
    }

    final com.google.android.gms.signin.c Zg() {
        if (this.bJy == null) {
            return null;
        }
        return this.bJy.Zg();
    }

    public final void a(ConnectionResult connectionResult) {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        this.bJs.disconnect();
        onConnectionFailed(connectionResult);
    }

    @Override // com.google.android.gms.common.api.a.df
    public final void a(ConnectionResult connectionResult, com.google.android.gms.common.api.a<?> aVar, boolean z) {
        if (Looper.myLooper() == this.bJC.handler.getLooper()) {
            onConnectionFailed(connectionResult);
        } else {
            this.bJC.handler.post(new bp(this, connectionResult));
        }
    }

    public final void a(be beVar) {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        if (this.bJs.isConnected()) {
            if (b(beVar)) {
                zzbr();
                return;
            } else {
                this.bJr.add(beVar);
                return;
            }
        }
        this.bJr.add(beVar);
        if (this.bJB == null || !this.bJB.Yy()) {
            connect();
        } else {
            onConnectionFailed(this.bJB);
        }
    }

    public final void a(cy cyVar) {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        this.bJw.add(cyVar);
    }

    public final void c(Status status) {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        Iterator<be> it = this.bJr.iterator();
        while (it.hasNext()) {
            it.next().zza(status);
        }
        this.bJr.clear();
    }

    public final void connect() {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        if (this.bJs.isConnected() || this.bJs.isConnecting()) {
            return;
        }
        int iA = this.bJC.bJk.a(this.bJC.bJi, this.bJs);
        if (iA != 0) {
            onConnectionFailed(new ConnectionResult(iA, null));
            return;
        }
        o oVar = new o(this.bJC, this.bJs, this.bJu);
        if (this.bJs.requiresSignIn()) {
            this.bJy.a(oVar);
        }
        this.bJs.connect(oVar);
    }

    public final int getInstanceId() {
        return this.zzkf;
    }

    final boolean isConnected() {
        return this.bJs.isConnected();
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnected(Bundle bundle) {
        if (Looper.myLooper() == this.bJC.handler.getLooper()) {
            YZ();
        } else {
            this.bJC.handler.post(new bn(this));
        }
    }

    @Override // com.google.android.gms.common.api.z
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        if (this.bJy != null) {
            this.bJy.ZG();
        }
        zzbo();
        this.bJC.bJk.flush();
        c(connectionResult);
        if (connectionResult.getErrorCode() == 4) {
            c(l.bJd);
            return;
        }
        if (this.bJr.isEmpty()) {
            this.bJB = connectionResult;
            return;
        }
        if (b(connectionResult) || this.bJC.a(connectionResult, this.zzkf)) {
            return;
        }
        if (connectionResult.getErrorCode() == 18) {
            this.bJz = true;
        }
        if (this.bJz) {
            this.bJC.handler.sendMessageDelayed(Message.obtain(this.bJC.handler, 9, this.bJu), this.bJC.bJe);
        } else {
            String strZI = this.bJu.ZI();
            c(new Status(17, new StringBuilder(String.valueOf(strZI).length() + 38).append("API: ").append(strZI).append(" is not available on this device.").toString()));
        }
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnectionSuspended(int i) {
        if (Looper.myLooper() == this.bJC.handler.getLooper()) {
            Za();
        } else {
            this.bJC.handler.post(new bo(this));
        }
    }

    public final boolean requiresSignIn() {
        return this.bJs.requiresSignIn();
    }

    public final void resume() {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        if (this.bJz) {
            connect();
        }
    }

    public final void zzbm() {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        c(l.bJc);
        this.bJv.Zr();
        for (w wVar : (w[]) this.bJx.keySet().toArray(new w[this.bJx.size()])) {
            a(new cu(wVar, new com.google.android.gms.f.g()));
        }
        c(new ConnectionResult(4));
        if (this.bJs.isConnected()) {
            this.bJs.onUserSignOut(new bq(this));
        }
    }

    public final void zzbo() {
        com.google.android.gms.common.internal.bf.a(this.bJC.handler);
        this.bJB = null;
    }
}
