package com.google.android.gms.ads.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzagr;
import com.google.android.gms.internal.ads.zzaib;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzald;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzaoe;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzlk;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzwx;
import com.google.android.gms.internal.ads.zzwy;
import com.google.android.gms.internal.ads.zzxq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzay extends zzlk {

    @GuardedBy("sLock")
    private static zzay bBE;
    private static final Object sLock = new Object();
    private final Context mContext;
    private final Object mLock = new Object();
    private boolean zzzv = false;
    private zzang zzzw;

    private zzay(Context context, zzang zzangVar) {
        this.mContext = context;
        this.zzzw = zzangVar;
    }

    public static zzay zza(Context context, zzang zzangVar) {
        zzay zzayVar;
        synchronized (sLock) {
            if (bBE == null) {
                bBE = new zzay(context.getApplicationContext(), zzangVar);
            }
            zzayVar = bBE;
        }
        return zzayVar;
    }

    final /* synthetic */ void c(Runnable runnable) {
        Context context = this.mContext;
        bf.hV("Adapters must be initialized on the main thread.");
        Map<String, zzwy> mapZzpw = zzbv.zzeo().zzqh().zzra().zzpw();
        if (mapZzpw == null || mapZzpw.isEmpty()) {
            return;
        }
        if (runnable != null) {
            try {
                runnable.run();
            } catch (Throwable th) {
                zzakb.zzc("Could not initialize rewarded ads.", th);
                return;
            }
        }
        zzagr zzagrVarZzox = zzagr.zzox();
        if (zzagrVarZzox != null) {
            Collection<zzwy> collectionValues = mapZzpw.values();
            HashMap map = new HashMap();
            com.google.android.gms.c.a aVarF = com.google.android.gms.c.d.F(context);
            Iterator<zzwy> it = collectionValues.iterator();
            while (it.hasNext()) {
                for (zzwx zzwxVar : it.next().zzbsm) {
                    String str = zzwxVar.zzbsb;
                    for (String str2 : zzwxVar.zzbrt) {
                        if (!map.containsKey(str2)) {
                            map.put(str2, new ArrayList());
                        }
                        if (str != null) {
                            ((Collection) map.get(str2)).add(str);
                        }
                    }
                }
            }
            for (Map.Entry entry : map.entrySet()) {
                String str3 = (String) entry.getKey();
                try {
                    zzaib zzaibVarZzca = zzagrVarZzox.zzca(str3);
                    if (zzaibVarZzca != null) {
                        zzxq zzxqVarZzpe = zzaibVarZzca.zzpe();
                        if (!zzxqVarZzpe.isInitialized() && zzxqVarZzpe.zzms()) {
                            zzxqVarZzpe.zza(aVarF, zzaibVarZzca.zzpf(), (List<String>) entry.getValue());
                            String strValueOf = String.valueOf(str3);
                            zzakb.zzck(strValueOf.length() != 0 ? "Initialized rewarded video mediation adapter ".concat(strValueOf) : new String("Initialized rewarded video mediation adapter "));
                        }
                    }
                } catch (Throwable th2) {
                    zzakb.zzc(new StringBuilder(String.valueOf(str3).length() + 56).append("Failed to initialize rewarded video mediation adapter \"").append(str3).append("\"").toString(), th2);
                }
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzlj
    public final void setAppMuted(boolean z) {
        zzbv.zzfj().setAppMuted(z);
    }

    @Override // com.google.android.gms.internal.ads.zzlj
    public final void setAppVolume(float f) {
        zzbv.zzfj().setAppVolume(f);
    }

    @Override // com.google.android.gms.internal.ads.zzlj
    public final void zza() {
        synchronized (sLock) {
            if (this.zzzv) {
                zzakb.zzdk("Mobile ads is initialized already.");
                return;
            }
            this.zzzv = true;
            zznk.initialize(this.mContext);
            zzbv.zzeo().zzd(this.mContext, this.zzzw);
            zzbv.zzeq().initialize(this.mContext);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzlj
    public final void zza(String str, com.google.android.gms.c.a aVar) {
        Runnable runnable;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        zznk.initialize(this.mContext);
        boolean zBooleanValue = ((Boolean) zzkb.zzik().zzd(zznk.zzbcs)).booleanValue() | ((Boolean) zzkb.zzik().zzd(zznk.zzayd)).booleanValue();
        if (((Boolean) zzkb.zzik().zzd(zznk.zzayd)).booleanValue()) {
            zBooleanValue = true;
            final Runnable runnable2 = (Runnable) com.google.android.gms.c.d.b(aVar);
            runnable = new Runnable(this, runnable2) { // from class: com.google.android.gms.ads.internal.o
                private final zzay bBF;
                private final Runnable zzxi;

                {
                    this.bBF = this;
                    this.zzxi = runnable2;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    final zzay zzayVar = this.bBF;
                    final Runnable runnable3 = this.zzxi;
                    zzaoe.zzcvy.execute(new Runnable(zzayVar, runnable3) { // from class: com.google.android.gms.ads.internal.q
                        private final zzay bBF;
                        private final Runnable zzxi;

                        {
                            this.bBF = zzayVar;
                            this.zzxi = runnable3;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            this.bBF.c(this.zzxi);
                        }
                    });
                }
            };
        } else {
            runnable = null;
        }
        if (zBooleanValue) {
            zzbv.zzes().zza(this.mContext, this.zzzw, str, runnable);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzlj
    public final void zzb(com.google.android.gms.c.a aVar, String str) {
        if (aVar == null) {
            zzakb.e("Wrapped context is null. Failed to open debug menu.");
            return;
        }
        Context context = (Context) com.google.android.gms.c.d.b(aVar);
        if (context == null) {
            zzakb.e("Context is null. Failed to open debug menu.");
            return;
        }
        zzald zzaldVar = new zzald(context);
        zzaldVar.setAdUnitId(str);
        zzaldVar.zzda(this.zzzw.zzcw);
        zzaldVar.showDialog();
    }

    @Override // com.google.android.gms.internal.ads.zzlj
    public final float zzdo() {
        return zzbv.zzfj().zzdo();
    }

    @Override // com.google.android.gms.internal.ads.zzlj
    public final boolean zzdp() {
        return zzbv.zzfj().zzdp();
    }

    @Override // com.google.android.gms.internal.ads.zzlj
    public final void zzt(String str) {
        zznk.initialize(this.mContext);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbcs)).booleanValue()) {
            zzbv.zzes().zza(this.mContext, this.zzzw, str, null);
        }
    }
}
