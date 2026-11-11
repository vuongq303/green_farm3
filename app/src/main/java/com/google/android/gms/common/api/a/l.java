package com.google.android.gms.common.api.a;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public class l implements Handler.Callback {

    @GuardedBy("lock")
    private static l bJh;
    private final Context bJi;
    private final com.google.android.gms.common.e bJj;
    private final com.google.android.gms.common.internal.ag bJk;
    private final Handler handler;
    public static final Status bJc = new Status(4, "Sign-out occurred while this API call was in progress.");
    private static final Status bJd = new Status(4, "The user must be signed in to make this API call.");
    private static final Object lock = new Object();
    private long bJe = 5000;
    private long bJf = 120000;
    private long bJg = 10000;
    private final AtomicInteger bJl = new AtomicInteger(1);
    private final AtomicInteger bJm = new AtomicInteger(0);
    private final Map<cv<?>, m<?>> bJn = new ConcurrentHashMap(5, 0.75f, 1);

    @GuardedBy("lock")
    private ak bJo = null;

    @GuardedBy("lock")
    private final Set<cv<?>> bJp = new android.support.v4.e.c();
    private final Set<cv<?>> bJq = new android.support.v4.e.c();

    private l(Context context, Looper looper, com.google.android.gms.common.e eVar) {
        this.bJi = context;
        this.handler = new Handler(looper, this);
        this.bJj = eVar;
        this.bJk = new com.google.android.gms.common.internal.ag(eVar);
        this.handler.sendMessage(this.handler.obtainMessage(6));
    }

    public static l YV() {
        l lVar;
        synchronized (lock) {
            com.google.android.gms.common.internal.bf.d(bJh, "Must guarantee manager is non-null before using getInstance");
            lVar = bJh;
        }
        return lVar;
    }

    public static l aC(Context context) {
        l lVar;
        synchronized (lock) {
            if (bJh == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                bJh = new l(context.getApplicationContext(), handlerThread.getLooper(), com.google.android.gms.common.e.YB());
            }
            lVar = bJh;
        }
        return lVar;
    }

    private final void c(com.google.android.gms.common.api.t<?> tVar) {
        Object objZzm = tVar.zzm();
        m<?> mVar = this.bJn.get(objZzm);
        if (mVar == null) {
            mVar = new m<>(this, tVar);
            this.bJn.put((cv<?>) objZzm, mVar);
        }
        if (mVar.requiresSignIn()) {
            this.bJq.add((cv<?>) objZzm);
        }
        mVar.connect();
    }

    public final int YW() {
        return this.bJl.getAndIncrement();
    }

    public final void YX() {
        this.handler.sendMessage(this.handler.obtainMessage(3));
    }

    final PendingIntent a(cv<?> cvVar, int i) {
        com.google.android.gms.signin.c cVarZg;
        m<?> mVar = this.bJn.get(cvVar);
        if (mVar != null && (cVarZg = mVar.Zg()) != null) {
            return PendingIntent.getActivity(this.bJi, i, cVarZg.getSignInIntent(), 134217728);
        }
        return null;
    }

    public final <O extends com.google.android.gms.common.api.e> com.google.android.gms.f.f<Void> a(com.google.android.gms.common.api.t<O> tVar, aa<com.google.android.gms.common.api.c, ?> aaVar, ag<com.google.android.gms.common.api.c, ?> agVar) {
        com.google.android.gms.f.g gVar = new com.google.android.gms.f.g();
        this.handler.sendMessage(this.handler.obtainMessage(8, new by(new cs(new bz(aaVar, agVar), gVar), this.bJm.get(), tVar)));
        return gVar.ZK();
    }

    public final <O extends com.google.android.gms.common.api.e> com.google.android.gms.f.f<Boolean> a(com.google.android.gms.common.api.t<O> tVar, w<?> wVar) {
        com.google.android.gms.f.g gVar = new com.google.android.gms.f.g();
        this.handler.sendMessage(this.handler.obtainMessage(13, new by(new cu(wVar, gVar), this.bJm.get(), tVar)));
        return gVar.ZK();
    }

    public final com.google.android.gms.f.f<Map<cv<?>, String>> a(Iterable<? extends com.google.android.gms.common.api.t<?>> iterable) {
        cy cyVar = new cy(iterable);
        this.handler.sendMessage(this.handler.obtainMessage(2, cyVar));
        return cyVar.ZK();
    }

    public final void a(ak akVar) {
        synchronized (lock) {
            if (this.bJo != akVar) {
                this.bJo = akVar;
                this.bJp.clear();
            }
            this.bJp.addAll(akVar.Zs());
        }
    }

    public final <O extends com.google.android.gms.common.api.e, ResultT> void a(com.google.android.gms.common.api.t<O> tVar, int i, ad<com.google.android.gms.common.api.c, ResultT> adVar, com.google.android.gms.f.g<ResultT> gVar, ac acVar) {
        this.handler.sendMessage(this.handler.obtainMessage(4, new by(new ct(i, adVar, gVar, acVar), this.bJm.get(), tVar)));
    }

    public final <O extends com.google.android.gms.common.api.e> void a(com.google.android.gms.common.api.t<O> tVar, int i, e<? extends com.google.android.gms.common.api.ae, com.google.android.gms.common.api.c> eVar) {
        this.handler.sendMessage(this.handler.obtainMessage(4, new by(new cr(i, eVar), this.bJm.get(), tVar)));
    }

    final boolean a(ConnectionResult connectionResult, int i) {
        return this.bJj.a(this.bJi, connectionResult, i);
    }

    public final void b(ConnectionResult connectionResult, int i) {
        if (a(connectionResult, i)) {
            return;
        }
        this.handler.sendMessage(this.handler.obtainMessage(5, i, 0, connectionResult));
    }

    final void b(ak akVar) {
        synchronized (lock) {
            if (this.bJo == akVar) {
                this.bJo = null;
                this.bJp.clear();
            }
        }
    }

    public final void b(com.google.android.gms.common.api.t<?> tVar) {
        this.handler.sendMessage(this.handler.obtainMessage(7, tVar));
    }

    public final com.google.android.gms.f.f<Boolean> d(com.google.android.gms.common.api.t<?> tVar) {
        al alVar = new al(tVar.zzm());
        this.handler.sendMessage(this.handler.obtainMessage(14, alVar));
        return alVar.Zu().ZK();
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0145  */
    /* JADX WARN: Code duplicated, block: B:48:0x0191  */
    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v36 com.google.android.gms.common.api.a.m<?>, still in use, count: 2, list:
          (r1v36 com.google.android.gms.common.api.a.m<?>) from 0x013d: INVOKE (r1v36 com.google.android.gms.common.api.a.m<?>) VIRTUAL call: com.google.android.gms.common.api.a.m.getInstanceId():int A[MD:():int (m), WRAPPED]
          (r1v36 com.google.android.gms.common.api.a.m<?>) from 0x0143: PHI (r1 I:??) = (r1v27 com.google.android.gms.common.api.a.m<?>), (r1v36 com.google.android.gms.common.api.a.m<?>) binds: [B:81:0x02e1, B:95:0x0143] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:93)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:132)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:67)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:50)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:96)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:36)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:44)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message r10) {
        /*
            Method dump skipped, instruction units count: 776
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.a.l.handleMessage(android.os.Message):boolean");
    }
}
