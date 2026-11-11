package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class cy {
    private int bMi;
    private final android.support.v4.e.a<cv<?>, String> bMg = new android.support.v4.e.a<>();
    private final com.google.android.gms.f.g<Map<cv<?>, String>> bMh = new com.google.android.gms.f.g<>();
    private boolean bMj = false;
    private final android.support.v4.e.a<cv<?>, ConnectionResult> bIu = new android.support.v4.e.a<>();

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public cy(Iterable<? extends com.google.android.gms.common.api.t<?>> iterable) {
        Iterator<? extends com.google.android.gms.common.api.t<?>> it = iterable.iterator();
        while (it.hasNext()) {
            this.bIu.put(it.next().zzm(), null);
        }
        this.bMi = this.bIu.keySet().size();
    }

    public final Set<cv<?>> ZJ() {
        return this.bIu.keySet();
    }

    public final com.google.android.gms.f.f<Map<cv<?>, String>> ZK() {
        return this.bMh.ZK();
    }

    public final void a(cv<?> cvVar, ConnectionResult connectionResult, String str) {
        this.bIu.put(cvVar, connectionResult);
        this.bMg.put(cvVar, str);
        this.bMi--;
        if (!connectionResult.Sp()) {
            this.bMj = true;
        }
        if (this.bMi == 0) {
            if (!this.bMj) {
                this.bMh.setResult(this.bMg);
            } else {
                this.bMh.g(new com.google.android.gms.common.api.r(this.bIu));
            }
        }
    }
}
