package com.google.android.gms.common.api.a;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public final class cs extends ce<Void> {
    private final aa<com.google.android.gms.common.api.c, ?> bLU;
    private final ag<com.google.android.gms.common.api.c, ?> bLV;

    public cs(bz bzVar, com.google.android.gms.f.g<Void> gVar) {
        super(3, gVar);
        this.bLU = bzVar.bLv;
        this.bLV = bzVar.bLw;
    }

    @Override // com.google.android.gms.common.api.a.ce, com.google.android.gms.common.api.a.be
    public final /* bridge */ /* synthetic */ void a(ah ahVar, boolean z) {
    }

    @Override // com.google.android.gms.common.api.a.ce, com.google.android.gms.common.api.a.be
    public final /* bridge */ /* synthetic */ void b(RuntimeException runtimeException) {
        super.b(runtimeException);
    }

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
    @Override // com.google.android.gms.common.api.a.ce
    public final void e(m<?> mVar) {
        this.bLU.a(mVar.Zb(), this.bLA);
        if (this.bLU.Zo() != null) {
            mVar.Zc().put(this.bLU.Zo(), new bz(this.bLU, this.bLV));
        }
    }

    @Override // com.google.android.gms.common.api.a.ce, com.google.android.gms.common.api.a.be
    public final /* bridge */ /* synthetic */ void zza(Status status) {
        super.zza(status);
    }
}
