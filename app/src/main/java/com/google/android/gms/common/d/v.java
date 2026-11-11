package com.google.android.gms.common.d;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.bf;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class v {
    public static String[] a(Scope[] scopeArr) {
        bf.d(scopeArr, "scopes can't be null.");
        String[] strArr = new String[scopeArr.length];
        for (int i = 0; i < scopeArr.length; i++) {
            strArr[i] = scopeArr[i].YQ();
        }
        return strArr;
    }

    public static String[] b(Set<Scope> set) {
        bf.d(set, "scopes can't be null.");
        return a((Scope[]) set.toArray(new Scope[set.size()]));
    }
}
