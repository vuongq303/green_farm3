package com.google.android.gms.internal.ads;

import com.google.android.gms.common.internal.bd;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzagp extends zzagv {
    private final String zzclb;
    private final int zzclc;

    public zzagp(String str, int i) {
        this.zzclb = str;
        this.zzclc = i;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof zzagp)) {
            return false;
        }
        zzagp zzagpVar = (zzagp) obj;
        return bd.b(this.zzclb, zzagpVar.zzclb) && bd.b(Integer.valueOf(this.zzclc), Integer.valueOf(zzagpVar.zzclc));
    }

    @Override // com.google.android.gms.internal.ads.zzagu
    public final int getAmount() {
        return this.zzclc;
    }

    @Override // com.google.android.gms.internal.ads.zzagu
    public final String getType() {
        return this.zzclb;
    }
}
