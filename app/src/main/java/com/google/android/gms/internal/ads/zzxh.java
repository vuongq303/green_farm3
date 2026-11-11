package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzxh implements zzww {
    private final Context mContext;
    private final long mStartTime;
    private final boolean zzael;
    private final zzwy zzbtj;
    private final boolean zzbtn;
    private final boolean zzbto;
    private final zzaef zzbuc;
    private final long zzbud;
    private final String zzbuh;
    private final zzxn zzwh;
    private final Object mLock = new Object();
    private boolean zzbuf = false;
    private final Map<zzanz<zzxe>, zzxb> zzbug = new HashMap();
    private List<zzxe> zzbui = new ArrayList();
    private final int zzbue = 2;

    public zzxh(Context context, zzaef zzaefVar, zzxn zzxnVar, zzwy zzwyVar, boolean z, boolean z2, String str, long j, long j2, int i, boolean z3) {
        this.mContext = context;
        this.zzbuc = zzaefVar;
        this.zzwh = zzxnVar;
        this.zzbtj = zzwyVar;
        this.zzael = z;
        this.zzbtn = z2;
        this.zzbuh = str;
        this.mStartTime = j;
        this.zzbud = j2;
        this.zzbto = z3;
    }

    private final void zza(zzanz<zzxe> zzanzVar) {
        zzakk.zzcrm.post(new zzxj(this, zzanzVar));
    }

    private final zzxe zzi(List<zzanz<zzxe>> list) {
        synchronized (this.mLock) {
            if (this.zzbuf) {
                return new zzxe(-1);
            }
            for (zzanz<zzxe> zzanzVar : list) {
                try {
                    zzxe zzxeVar = zzanzVar.get();
                    this.zzbui.add(zzxeVar);
                    if (zzxeVar != null && zzxeVar.zzbtv == 0) {
                        zza(zzanzVar);
                        return zzxeVar;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    zzakb.zzc("Exception while processing an adapter; continuing with other adapters", e);
                }
            }
            zza((zzanz<zzxe>) null);
            return new zzxe(1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:53:0x00c3  */
    private final zzxe zzj(List<zzanz<zzxe>> list) {
        zzxe zzxeVar;
        zzxw zzxwVar;
        int iZzmm = -1;
        synchronized (this.mLock) {
            if (this.zzbuf) {
                return new zzxe(-1);
            }
            long j = this.zzbtj.zzbsy != -1 ? this.zzbtj.zzbsy : 10000L;
            long jMax = j;
            zzxe zzxeVar2 = null;
            zzanz<zzxe> zzanzVar = null;
            for (zzanz<zzxe> zzanzVar2 : list) {
                long jCurrentTimeMillis = com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis();
                if (jMax == 0) {
                    try {
                        try {
                            if (zzanzVar2.isDone()) {
                                zzxeVar = zzanzVar2.get();
                            }
                            this.zzbui.add(zzxeVar);
                            if (zzxeVar != null || zzxeVar.zzbtv != 0 || (zzxwVar = zzxeVar.zzbua) == null || zzxwVar.zzmm() <= iZzmm) {
                                zzxeVar = zzxeVar2;
                                zzanzVar2 = zzanzVar;
                            } else {
                                iZzmm = zzxwVar.zzmm();
                            }
                            jMax = Math.max(jMax - (com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis() - jCurrentTimeMillis), 0L);
                            zzxeVar2 = zzxeVar;
                            zzanzVar = zzanzVar2;
                        } catch (RemoteException | InterruptedException | ExecutionException | TimeoutException e) {
                            zzakb.zzc("Exception while processing an adapter; continuing with other adapters", e);
                            jMax = Math.max(jMax - (com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis() - jCurrentTimeMillis), 0L);
                        }
                    } catch (Throwable th) {
                        Math.max(jMax - (com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis() - jCurrentTimeMillis), 0L);
                        throw th;
                    }
                }
                zzxeVar = zzanzVar2.get(jMax, TimeUnit.MILLISECONDS);
                this.zzbui.add(zzxeVar);
                if (zzxeVar != null) {
                    zzxeVar = zzxeVar2;
                    zzanzVar2 = zzanzVar;
                } else {
                    zzxeVar = zzxeVar2;
                    zzanzVar2 = zzanzVar;
                }
                jMax = Math.max(jMax - (com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis() - jCurrentTimeMillis), 0L);
                zzxeVar2 = zzxeVar;
                zzanzVar = zzanzVar2;
            }
            zza(zzanzVar);
            return zzxeVar2 == null ? new zzxe(1) : zzxeVar2;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzww
    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbuf = true;
            Iterator<zzxb> it = this.zzbug.values().iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00f7 A[EDGE_INSN: B:29:0x00f7->B:12:0x003b BREAK  A[LOOP:0: B:7:0x002f->B:22:0x00d4]] */
    @Override // com.google.android.gms.internal.ads.zzww
    public final zzxe zzh(List<zzwx> list) {
        zzjn zzjnVar;
        zzakb.zzck("Starting mediation.");
        ArrayList arrayList = new ArrayList();
        zzjn zzjnVar2 = this.zzbuc.zzacv;
        int[] iArr = new int[2];
        if (zzjnVar2.zzard == null) {
            zzjnVar = zzjnVar2;
            break;
        }
        com.google.android.gms.ads.internal.zzbv.zzfd();
        if (!zzxg.zza(this.zzbuh, iArr)) {
            zzjnVar = zzjnVar2;
            break;
        }
        int i = iArr[0];
        int i2 = iArr[1];
        zzjn[] zzjnVarArr = zzjnVar2.zzard;
        int length = zzjnVarArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                zzjnVar = zzjnVar2;
                break;
            }
            zzjnVar = zzjnVarArr[i3];
            if (i == zzjnVar.width && i2 == zzjnVar.height) {
                break;
            }
            i3++;
        }
        for (zzwx zzwxVar : list) {
            String strValueOf = String.valueOf(zzwxVar.zzbrs);
            zzakb.zzdj(strValueOf.length() != 0 ? "Trying mediation network: ".concat(strValueOf) : new String("Trying mediation network: "));
            Iterator<String> it = zzwxVar.zzbrt.iterator();
            while (it.hasNext()) {
                zzxb zzxbVar = new zzxb(this.mContext, it.next(), this.zzwh, this.zzbtj, zzwxVar, this.zzbuc.zzccv, zzjnVar, this.zzbuc.zzacr, this.zzael, this.zzbtn, this.zzbuc.zzadj, this.zzbuc.zzads, this.zzbuc.zzcdk, this.zzbuc.zzcef, this.zzbto);
                zzanz<zzxe> zzanzVarZza = zzaki.zza(new zzxi(this, zzxbVar));
                this.zzbug.put(zzanzVarZza, zzxbVar);
                arrayList.add(zzanzVarZza);
            }
        }
        switch (this.zzbue) {
            case 2:
                return zzj(arrayList);
            default:
                return zzi(arrayList);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzww
    public final List<zzxe> zzme() {
        return this.zzbui;
    }
}
