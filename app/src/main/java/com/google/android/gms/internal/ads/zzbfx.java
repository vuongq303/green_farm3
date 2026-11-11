package com.google.android.gms.internal.ads;

import android.content.ComponentName;
import android.support.a.b;
import android.support.a.g;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class zzbfx extends g {
    private WeakReference<zzbfy> zzedz;

    public zzbfx(zzbfy zzbfyVar) {
        this.zzedz = new WeakReference<>(zzbfyVar);
    }

    @Override // android.support.a.g
    public final void onCustomTabsServiceConnected(ComponentName componentName, b bVar) {
        zzbfy zzbfyVar = this.zzedz.get();
        if (zzbfyVar != null) {
            zzbfyVar.zza(bVar);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        zzbfy zzbfyVar = this.zzedz.get();
        if (zzbfyVar != null) {
            zzbfyVar.zzjo();
        }
    }
}
