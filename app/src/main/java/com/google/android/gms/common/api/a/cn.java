package com.google.android.gms.common.api.a;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class cn {
    public static final Status bLL = new Status(8, "The connection to Google Play services was lost");
    private static final g<?>[] bLM = new g[0];
    private final Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> bKO;
    final Set<g<?>> bLN = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final cq bLO = new co(this);

    public cn(Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> map) {
        this.bKO = map;
    }

    final void a(g<? extends com.google.android.gms.common.api.ae> gVar) {
        this.bLN.add(gVar);
        gVar.zza(this.bLO);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void release() {
        com.google.android.gms.common.api.al alVar = null;
        byte b = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        com.google.android.gms.common.api.al alVar2 = null;
        for (g gVar : (g[]) this.bLN.toArray(bLM)) {
            gVar.zza((cq) null);
            if (gVar.zzo() != null) {
                gVar.setResultCallback(null);
                IBinder serviceBrokerBinder = this.bKO.get(((e) gVar).getClientKey()).getServiceBrokerBinder();
                if (gVar.isReady()) {
                    gVar.zza(new cp(gVar, b4 == true ? 1 : 0, serviceBrokerBinder, b3 == true ? 1 : 0));
                } else if (serviceBrokerBinder == null || !serviceBrokerBinder.isBinderAlive()) {
                    gVar.zza((cq) null);
                    gVar.cancel();
                    alVar.remove(gVar.zzo().intValue());
                } else {
                    cp cpVar = new cp(gVar, b2 == true ? 1 : 0, serviceBrokerBinder, b == true ? 1 : 0);
                    gVar.zza(cpVar);
                    try {
                        serviceBrokerBinder.linkToDeath(cpVar, 0);
                    } catch (RemoteException e) {
                        gVar.cancel();
                        alVar2.remove(gVar.zzo().intValue());
                    }
                }
                this.bLN.remove(gVar);
            } else if (gVar.zzw()) {
                this.bLN.remove(gVar);
            }
        }
    }

    public final void zzce() {
        for (g gVar : (g[]) this.bLN.toArray(bLM)) {
            gVar.zzb(bLL);
        }
    }
}
