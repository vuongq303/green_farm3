package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import com.google.android.gms.c.d;

/* JADX INFO: loaded from: classes.dex */
final class zzahp implements Runnable {
    private final /* synthetic */ zzxq zzclu;
    private final /* synthetic */ zzahn zzclv;
    private final /* synthetic */ zzahv zzclw;
    private final /* synthetic */ zzjj zzyh;

    zzahp(zzahn zzahnVar, zzxq zzxqVar, zzjj zzjjVar, zzahv zzahvVar) {
        this.zzclv = zzahnVar;
        this.zzclu = zzxqVar;
        this.zzyh = zzjjVar;
        this.zzclw = zzahvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zzclu.zza(d.F(this.zzclv.mContext), this.zzyh, (String) null, this.zzclw, this.zzclv.zzcln);
        } catch (RemoteException e) {
            String strValueOf = String.valueOf(this.zzclv.zzbth);
            zzakb.zzc(strValueOf.length() != 0 ? "Fail to initialize adapter ".concat(strValueOf) : new String("Fail to initialize adapter "), e);
            this.zzclv.zza(this.zzclv.zzbth, 0);
        }
    }
}
