package com.google.android.gms.internal.ads;

import android.os.ParcelFileDescriptor;

/* JADX INFO: loaded from: classes.dex */
final class zzsn extends zzaoj<ParcelFileDescriptor> {
    private final /* synthetic */ zzsm zzbnn;

    zzsn(zzsm zzsmVar) {
        this.zzbnn = zzsmVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaoj, java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        this.zzbnn.disconnect();
        return super.cancel(z);
    }
}
