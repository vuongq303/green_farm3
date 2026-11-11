package com.google.android.gms.common;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class s extends i {
    private final byte[] bRb;

    s(byte[] bArr) {
        super(Arrays.copyOfRange(bArr, 0, 25));
        this.bRb = bArr;
    }

    @Override // com.google.android.gms.common.i
    final byte[] getBytes() {
        return this.bRb;
    }
}
