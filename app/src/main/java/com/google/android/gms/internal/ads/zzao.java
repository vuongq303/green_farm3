package com.google.android.gms.internal.ads;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
final class zzao extends FilterInputStream {
    private final long zzcc;
    private long zzcd;

    zzao(InputStream inputStream, long j) {
        super(inputStream);
        this.zzcc = j;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read() throws IOException {
        int i = super.read();
        if (i != -1) {
            this.zzcd++;
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = super.read(bArr, i, i2);
        if (i3 != -1) {
            this.zzcd += (long) i3;
        }
        return i3;
    }

    final long zzo() {
        return this.zzcc - this.zzcd;
    }
}
