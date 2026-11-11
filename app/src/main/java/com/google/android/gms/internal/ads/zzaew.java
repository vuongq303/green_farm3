package com.google.android.gms.internal.ads;

import com.google.android.gms.common.d.o;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
final class zzaew implements Runnable {
    private final /* synthetic */ OutputStream zzcfw;
    private final /* synthetic */ byte[] zzcfx;

    zzaew(zzaev zzaevVar, OutputStream outputStream, byte[] bArr) {
        this.zzcfw = outputStream;
        this.zzcfx = bArr;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0039  */
    /* JADX WARN: Code duplicated, block: B:18:0x003f  */
    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        DataOutputStream dataOutputStream;
        try {
            try {
                dataOutputStream = new DataOutputStream(this.zzcfw);
                try {
                    dataOutputStream.writeInt(this.zzcfx.length);
                    dataOutputStream.write(this.zzcfx);
                    o.closeQuietly(dataOutputStream);
                } catch (IOException e) {
                    e = e;
                    zzakb.zzb("Error transporting the ad response", e);
                    com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "LargeParcelTeleporter.pipeData.1");
                    if (dataOutputStream == null) {
                        o.closeQuietly(this.zzcfw);
                    } else {
                        o.closeQuietly(dataOutputStream);
                    }
                }
            } catch (IOException e2) {
                e = e2;
                dataOutputStream = null;
            } catch (Throwable th) {
                th = th;
                dataOutputStream = null;
                if (dataOutputStream == null) {
                    o.closeQuietly(this.zzcfw);
                } else {
                    o.closeQuietly(dataOutputStream);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (dataOutputStream == null) {
                o.closeQuietly(this.zzcfw);
            } else {
                o.closeQuietly(dataOutputStream);
            }
            throw th;
        }
    }
}
