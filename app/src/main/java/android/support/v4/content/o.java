package android.support.v4.content;

import android.os.Binder;
import android.os.Process;

/* JADX INFO: Add missing generic type declarations: [Params, Result] */
/* JADX INFO: compiled from: ModernAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
class o<Params, Result> extends t<Params, Result> {
    final /* synthetic */ m fd;

    o(m mVar) {
        this.fd = mVar;
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
    @Override // java.util.concurrent.Callable
    public Result call() {
        this.fd.fa.set(true);
        Result result = null;
        try {
            try {
                Process.setThreadPriority(10);
                result = (Result) this.fd.doInBackground(this.mParams);
                Binder.flushPendingCommands();
                this.fd.g(result);
                return result;
            } catch (Throwable th) {
                this.fd.eZ.set(true);
                throw th;
            }
        } catch (Throwable th2) {
            this.fd.g(result);
            throw th2;
        }
    }
}
