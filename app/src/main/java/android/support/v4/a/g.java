package android.support.v4.a;

import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: TypefaceCompatUtil.java */
/* JADX INFO: loaded from: classes.dex */
class g extends InputStream {
    private ByteBuffer fy;

    g(ByteBuffer byteBuffer) {
        this.fy = byteBuffer;
    }

    @Override // java.io.InputStream
    public int read() {
        if (this.fy.hasRemaining()) {
            return this.fy.get() & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) {
        if (!this.fy.hasRemaining()) {
            return -1;
        }
        int iMin = Math.min(i2, this.fy.remaining());
        this.fy.get(bArr, i, iMin);
        return iMin;
    }
}
