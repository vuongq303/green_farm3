package com.google.firebase.internal;

import com.google.android.gms.common.internal.bd;

/* JADX INFO: loaded from: classes.dex */
public class InternalTokenResult {
    private String zzz;

    public InternalTokenResult(String str) {
        this.zzz = str;
    }

    public boolean equals(Object obj) {
        if (obj instanceof InternalTokenResult) {
            return bd.b(this.zzz, ((InternalTokenResult) obj).zzz);
        }
        return false;
    }

    public String getToken() {
        return this.zzz;
    }

    public int hashCode() {
        return bd.hashCode(this.zzz);
    }

    public String toString() {
        return bd.C(this).c("token", this.zzz).toString();
    }
}
