package com.google.android.gms.internal.ads;

import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
final class zzazm extends ThreadLocal<SecureRandom> {
    zzazm() {
    }

    @Override // java.lang.ThreadLocal
    protected final /* synthetic */ SecureRandom initialValue() {
        return zzazl.zzaar();
    }
}
