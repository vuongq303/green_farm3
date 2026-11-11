package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.c.d;
import com.google.android.gms.d.a;
import com.google.android.gms.d.c;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzhx {
    zzen zzaln;
    boolean zzalo;

    public zzhx() {
    }

    public zzhx(Context context) {
        zznk.initialize(context);
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbeo)).booleanValue()) {
            try {
                this.zzaln = zzeo.zza(a.a(context, a.bRs, "com.google.android.gms.ads.dynamite").ip("com.google.android.gms.ads.clearcut.DynamiteClearcutLogger"));
                d.F(context);
                this.zzaln.zza(d.F(context), "GMA_SDK");
                this.zzalo = true;
            } catch (RemoteException | c | NullPointerException e) {
                zzane.zzck("Cannot dynamite load clearcut");
            }
        }
    }

    public zzhx(Context context, String str, String str2) {
        zznk.initialize(context);
        try {
            this.zzaln = zzeo.zza(a.a(context, a.bRs, "com.google.android.gms.ads.dynamite").ip("com.google.android.gms.ads.clearcut.DynamiteClearcutLogger"));
            d.F(context);
            this.zzaln.zza(d.F(context), str, null);
            this.zzalo = true;
        } catch (RemoteException | c | NullPointerException e) {
            zzane.zzck("Cannot dynamite load clearcut");
        }
    }

    public final zzhz zzd(byte[] bArr) {
        return new zzhz(this, bArr);
    }
}
