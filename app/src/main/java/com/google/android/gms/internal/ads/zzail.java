package com.google.android.gms.internal.ads;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

/* JADX INFO: loaded from: classes.dex */
final class zzail implements Runnable {
    private final /* synthetic */ Bitmap val$bitmap;
    private final /* synthetic */ zzaii zzcna;

    zzail(zzaii zzaiiVar, Bitmap bitmap) {
        this.zzcna = zzaiiVar;
        this.val$bitmap = bitmap;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.val$bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        synchronized (this.zzcna.mLock) {
            this.zzcna.zzcmn.zzecm = new zzbft();
            this.zzcna.zzcmn.zzecm.zzedl = byteArrayOutputStream.toByteArray();
            this.zzcna.zzcmn.zzecm.mimeType = "image/png";
            this.zzcna.zzcmn.zzecm.zzamf = 1;
        }
    }
}
