package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import com.google.android.gms.common.api.a.bc;
import com.google.android.gms.common.api.a.cw;
import com.google.android.gms.common.api.a.de;
import com.google.android.gms.common.internal.bf;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public final class x {
    private final Set<Scope> bIA;
    private int bIB;
    private View bIC;
    private final Map<a<?>, com.google.android.gms.common.internal.t> bID;
    private final Map<a<?>, e> bIE;
    private com.google.android.gms.common.api.a.s bIF;
    private int bIG;
    private z bIH;
    private com.google.android.gms.common.e bII;
    private b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bIJ;
    private final ArrayList<y> bIK;
    private final ArrayList<z> bIL;
    private boolean bIM;
    private final Set<Scope> bIz;
    private final Context mContext;
    private Looper zzcn;
    private String zzcz;
    private String zzda;
    private Account zzs;

    public x(Context context) {
        this.bIz = new HashSet();
        this.bIA = new HashSet();
        this.bID = new android.support.v4.e.a();
        this.bIE = new android.support.v4.e.a();
        this.bIG = -1;
        this.bII = com.google.android.gms.common.e.YB();
        this.bIJ = com.google.android.gms.signin.a.CLIENT_BUILDER;
        this.bIK = new ArrayList<>();
        this.bIL = new ArrayList<>();
        this.bIM = false;
        this.mContext = context;
        this.zzcn = context.getMainLooper();
        this.zzcz = context.getPackageName();
        this.zzda = context.getClass().getName();
    }

    public x(Context context, y yVar, z zVar) {
        this(context);
        bf.d(yVar, "Must provide a connected listener");
        this.bIK.add(yVar);
        bf.d(zVar, "Must provide a connection failed listener");
        this.bIL.add(zVar);
    }

    public final com.google.android.gms.common.internal.r YO() {
        com.google.android.gms.signin.d dVar = com.google.android.gms.signin.d.bYk;
        if (this.bIE.containsKey(com.google.android.gms.signin.a.API)) {
            dVar = (com.google.android.gms.signin.d) this.bIE.get(com.google.android.gms.signin.a.API);
        }
        return new com.google.android.gms.common.internal.r(this.zzs, this.bIz, this.bID, this.bIB, this.bIC, this.zzcz, this.zzda, dVar);
    }

    public final w YP() {
        bf.b(!this.bIE.isEmpty(), "must call addApi() to add at least one API");
        com.google.android.gms.common.internal.r rVarYO = YO();
        a<?> aVar = null;
        Map<a<?>, com.google.android.gms.common.internal.t> mapAah = rVarYO.aah();
        android.support.v4.e.a aVar2 = new android.support.v4.e.a();
        android.support.v4.e.a aVar3 = new android.support.v4.e.a();
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (a<?> aVar4 : this.bIE.keySet()) {
            e eVar = this.bIE.get(aVar4);
            boolean z2 = mapAah.get(aVar4) != null;
            aVar2.put(aVar4, Boolean.valueOf(z2));
            de deVar = new de(aVar4, z2);
            arrayList.add(deVar);
            b<?, O> bVarYJ = aVar4.YJ();
            l lVarA = bVarYJ.a(this.mContext, this.zzcn, rVarYO, eVar, deVar, deVar);
            aVar3.put(aVar4.getClientKey(), lVarA);
            boolean z3 = bVarYJ.getPriority() == 1 ? eVar != null : z;
            if (!lVarA.providesSignIn()) {
                aVar4 = aVar;
            } else if (aVar != null) {
                String name = aVar4.getName();
                String name2 = aVar.getName();
                throw new IllegalStateException(new StringBuilder(String.valueOf(name).length() + 21 + String.valueOf(name2).length()).append(name).append(" cannot be used with ").append(name2).toString());
            }
            z = z3;
            aVar = aVar4;
        }
        if (aVar != null) {
            if (z) {
                String name3 = aVar.getName();
                throw new IllegalStateException(new StringBuilder(String.valueOf(name3).length() + 82).append("With using ").append(name3).append(", GamesOptions can only be specified within GoogleSignInOptions.Builder").toString());
            }
            bf.a(this.zzs == null, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", aVar.getName());
            bf.a(this.bIz.equals(this.bIA), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", aVar.getName());
        }
        bc bcVar = new bc(this.mContext, new ReentrantLock(), this.zzcn, rVarYO, this.bII, this.bIJ, aVar2, this.bIK, this.bIL, aVar3, this.bIG, bc.a(aVar3.values(), true), arrayList, false);
        synchronized (w.bIy) {
            w.bIy.add(bcVar);
        }
        if (this.bIG >= 0) {
            cw.b(this.bIF).a(this.bIG, bcVar, this.bIH);
        }
        return bcVar;
    }

    public final x a(Scope scope) {
        bf.d(scope, "Scope must not be null");
        this.bIz.add(scope);
        return this;
    }

    public final <O extends h> x a(a<O> aVar, O o) {
        bf.d(aVar, "Api must not be null");
        bf.d(o, "Null options are not permitted for this Api");
        this.bIE.put(aVar, o);
        List<Scope> listV = aVar.YI().v(o);
        this.bIA.addAll(listV);
        this.bIz.addAll(listV);
        return this;
    }
}
