package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzyt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzyr<FieldDescriptorType extends zzyt<FieldDescriptorType>> {
    private static final zzyr zzbug = new zzyr(true);
    private boolean zzbmk;
    private boolean zzbuf = false;
    private final zzaad<FieldDescriptorType, Object> zzbue = zzaad.zzai(16);

    private zzyr() {
    }

    private zzyr(boolean z) {
        if (this.zzbmk) {
            return;
        }
        this.zzbue.zzqy();
        this.zzbmk = true;
    }

    private static void zza(zzaau zzaauVar, Object obj) {
        boolean z = false;
        zzyw.checkNotNull(obj);
        switch (zzys.zzbuh[zzaauVar.zzvn().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if ((obj instanceof zzyb) || (obj instanceof byte[])) {
                    z = true;
                }
                break;
            case 8:
                if ((obj instanceof Integer) || (obj instanceof zzyx)) {
                    z = true;
                }
                break;
            case 9:
                if ((obj instanceof zzzq) || (obj instanceof zzyz)) {
                    z = true;
                }
                break;
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.util.ArrayList, java.util.List] */
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
    private final void zza(FieldDescriptorType fielddescriptortype, Object obj) {
        ?? arrayList;
        if (!fielddescriptortype.zzuc()) {
            zza(fielddescriptortype.zzub(), obj);
            arrayList = obj;
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fielddescriptortype.zzub(), obj2);
            }
        }
        if (arrayList instanceof zzyz) {
            this.zzbuf = true;
        }
        this.zzbue.put(fielddescriptortype, arrayList);
    }

    public static <T extends zzyt<T>> zzyr<T> zzua() {
        return zzbug;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ Object clone() {
        zzyr zzyrVar = new zzyr();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.zzbue.zzvb()) {
                break;
            }
            Map.Entry<K, Object> entryZzaj = this.zzbue.zzaj(i2);
            zzyrVar.zza((zzyt) entryZzaj.getKey(), entryZzaj.getValue());
            i = i2 + 1;
        }
        Iterator it = this.zzbue.zzvc().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzyrVar.zza((zzyt) entry.getKey(), entry.getValue());
        }
        zzyrVar.zzbuf = this.zzbuf;
        return zzyrVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzyr) {
            return this.zzbue.equals(((zzyr) obj).zzbue);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzbue.hashCode();
    }

    public final Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        return this.zzbuf ? new zzzc(this.zzbue.entrySet().iterator()) : this.zzbue.entrySet().iterator();
    }
}
