package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import com.google.ads.mediation.AdUrlAdapter;
import com.google.ads.mediation.a.b;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.ads.mediation.i;
import com.google.ads.mediation.n;
import com.google.ads.mediation.q;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.customevent.CustomEvent;
import com.google.android.gms.ads.mediation.customevent.CustomEventAdapter;
import com.google.android.gms.ads.mediation.customevent.CustomEventExtras;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzxm extends zzxo {
    private static final zzzo zzbup = new zzzo();
    private Map<Class<? extends NetworkExtras>, NetworkExtras> zzbuo;

    private final <NetworkExtrasT extends q, ServerParametersT extends n> zzxq zzbo(String str) {
        try {
            Class<?> cls = Class.forName(str, false, zzxm.class.getClassLoader());
            if (i.class.isAssignableFrom(cls)) {
                i iVar = (i) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                return new zzyp(iVar, (q) this.zzbuo.get(iVar.WG()));
            }
            if (MediationAdapter.class.isAssignableFrom(cls)) {
                return new zzyk((MediationAdapter) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
            }
            zzane.zzdk(new StringBuilder(String.valueOf(str).length() + 64).append("Could not instantiate mediation adapter: ").append(str).append(" (not a valid adapter).").toString());
            throw new RemoteException();
        } catch (Throwable th) {
            return zzbp(str);
        }
    }

    private final zzxq zzbp(String str) throws RemoteException {
        try {
            zzane.zzck("Reflection failed, retrying using direct instantiation");
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(str)) {
                return new zzyk(new AdMobAdapter());
            }
            if ("com.google.ads.mediation.AdUrlAdapter".equals(str)) {
                return new zzyk(new AdUrlAdapter());
            }
            if ("com.google.android.gms.ads.mediation.customevent.CustomEventAdapter".equals(str)) {
                return new zzyk(new CustomEventAdapter());
            }
            if ("com.google.ads.mediation.customevent.CustomEventAdapter".equals(str)) {
                b bVar = new b();
                return new zzyp(bVar, (CustomEventExtras) this.zzbuo.get(bVar.WG()));
            }
            throw new RemoteException();
        } catch (Throwable th) {
            zzane.zzc(new StringBuilder(String.valueOf(str).length() + 43).append("Could not instantiate mediation adapter: ").append(str).append(". ").toString(), th);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzxn
    public final zzxq zzbm(String str) {
        return zzbo(str);
    }

    @Override // com.google.android.gms.internal.ads.zzxn
    public final boolean zzbn(String str) {
        try {
            return CustomEvent.class.isAssignableFrom(Class.forName(str, false, zzxm.class.getClassLoader()));
        } catch (Throwable th) {
            zzane.zzdk(new StringBuilder(String.valueOf(str).length() + 80).append("Could not load custom event implementation class: ").append(str).append(", assuming old implementation.").toString());
            return false;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzxn
    public final zzzj zzbq(String str) {
        return zzzo.zzbs(str);
    }

    public final void zzj(Map<Class<? extends NetworkExtras>, NetworkExtras> map) {
        this.zzbuo = map;
    }
}
