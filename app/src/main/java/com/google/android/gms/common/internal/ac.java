package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public final class ac {
    private final ComponentName E;
    private final String bOj;
    private final String zztp;
    private final int zztq;

    public ac(ComponentName componentName, int i) {
        this.bOj = null;
        this.zztp = null;
        this.E = (ComponentName) bf.checkNotNull(componentName);
        this.zztq = i;
    }

    public ac(String str, String str2, int i) {
        this.bOj = bf.id(str);
        this.zztp = bf.id(str2);
        this.E = null;
        this.zztq = i;
    }

    public final Intent aI(Context context) {
        return this.bOj != null ? new Intent(this.bOj).setPackage(this.zztp) : new Intent().setComponent(this.E);
    }

    public final int aav() {
        return this.zztq;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ac)) {
            return false;
        }
        ac acVar = (ac) obj;
        return bd.b(this.bOj, acVar.bOj) && bd.b(this.zztp, acVar.zztp) && bd.b(this.E, acVar.E) && this.zztq == acVar.zztq;
    }

    public final ComponentName getComponentName() {
        return this.E;
    }

    public final String getPackage() {
        return this.zztp;
    }

    public final int hashCode() {
        return bd.hashCode(this.bOj, this.zztp, this.E, Integer.valueOf(this.zztq));
    }

    public final String toString() {
        return this.bOj == null ? this.E.flattenToString() : this.bOj;
    }
}
