package com.google.android.gms.common;

import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
abstract class t extends i {
    private static final WeakReference<byte[]> bRd = new WeakReference<>(null);
    private WeakReference<byte[]> bRc;

    t(byte[] bArr) {
        super(bArr);
        this.bRc = bRd;
    }

    protected abstract byte[] abP();

    @Override // com.google.android.gms.common.i
    final byte[] getBytes() {
        byte[] bArrAbP;
        synchronized (this) {
            bArrAbP = this.bRc.get();
            if (bArrAbP == null) {
                bArrAbP = abP();
                this.bRc = new WeakReference<>(bArrAbP);
            }
        }
        return bArrAbP;
    }
}
