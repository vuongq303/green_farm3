package com.google.android.gms.internal.ads;

import android.net.Uri;
import com.google.android.gms.common.d.t;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public abstract class zzus<ReferenceT> {
    private final Map<String, CopyOnWriteArrayList<com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT>>> zzbpn = new HashMap();

    private final synchronized void zzb(String str, final Map<String, String> map) {
        if (zzakb.isLoggable(2)) {
            String strValueOf = String.valueOf(str);
            zzakb.v(strValueOf.length() != 0 ? "Received GMSG: ".concat(strValueOf) : new String("Received GMSG: "));
            for (String str2 : map.keySet()) {
                String str3 = map.get(str2);
                zzakb.v(new StringBuilder(String.valueOf(str2).length() + 4 + String.valueOf(str3).length()).append("  ").append(str2).append(": ").append(str3).toString());
            }
        }
        CopyOnWriteArrayList<com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT>> copyOnWriteArrayList = this.zzbpn.get(str);
        if (copyOnWriteArrayList != null) {
            for (final com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT> zzvVar : copyOnWriteArrayList) {
                zzaoe.zzcvy.execute(new Runnable(this, zzvVar, map) { // from class: com.google.android.gms.internal.ads.zzut
                    private final zzus zzbpo;
                    private final com.google.android.gms.ads.internal.gmsg.zzv zzbpp;
                    private final Map zzbpq;

                    {
                        this.zzbpo = this;
                        this.zzbpp = zzvVar;
                        this.zzbpq = map;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        zzus zzusVar = this.zzbpo;
                        this.zzbpp.zza(zzusVar.getReference(), this.zzbpq);
                    }
                });
            }
        }
    }

    public abstract ReferenceT getReference();

    public synchronized void reset() {
        this.zzbpn.clear();
    }

    public final synchronized void zza(String str, com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT> zzvVar) {
        CopyOnWriteArrayList<com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT>> copyOnWriteArrayList = this.zzbpn.get(str);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.zzbpn.put(str, copyOnWriteArrayList);
        }
        copyOnWriteArrayList.add(zzvVar);
    }

    public final synchronized void zza(String str, t<com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT>> tVar) {
        CopyOnWriteArrayList<com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT>> copyOnWriteArrayList = this.zzbpn.get(str);
        if (copyOnWriteArrayList != null) {
            ArrayList arrayList = new ArrayList();
            for (com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT> zzvVar : copyOnWriteArrayList) {
                if (tVar.apply(zzvVar)) {
                    arrayList.add(zzvVar);
                }
            }
            copyOnWriteArrayList.removeAll(arrayList);
        }
    }

    public final synchronized void zzb(String str, com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT> zzvVar) {
        CopyOnWriteArrayList<com.google.android.gms.ads.internal.gmsg.zzv<? super ReferenceT>> copyOnWriteArrayList = this.zzbpn.get(str);
        if (copyOnWriteArrayList != null) {
            copyOnWriteArrayList.remove(zzvVar);
        }
    }

    public final boolean zzf(Uri uri) {
        if (!"gmsg".equalsIgnoreCase(uri.getScheme()) || !"mobileads.google.com".equalsIgnoreCase(uri.getHost())) {
            return false;
        }
        String path = uri.getPath();
        com.google.android.gms.ads.internal.zzbv.zzek();
        zzb(path, zzakk.zzg(uri));
        return true;
    }
}
