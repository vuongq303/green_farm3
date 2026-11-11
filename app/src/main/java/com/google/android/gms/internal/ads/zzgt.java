package com.google.android.gms.internal.ads;

import android.util.Base64OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class zzgt {
    private ByteArrayOutputStream zzajc = new ByteArrayOutputStream(4096);
    private Base64OutputStream zzajd = new Base64OutputStream(this.zzajc, 10);

    public final String toString() {
        String string;
        try {
            this.zzajd.close();
        } catch (IOException e) {
            zzakb.zzb("HashManager: Unable to convert to Base64.", e);
        }
        try {
            this.zzajc.close();
            string = this.zzajc.toString();
        } catch (IOException e2) {
            zzakb.zzb("HashManager: Unable to convert to Base64.", e2);
            string = "";
        } finally {
            this.zzajc = null;
            this.zzajd = null;
        }
        return string;
    }

    public final void write(byte[] bArr) {
        this.zzajd.write(bArr);
    }
}
