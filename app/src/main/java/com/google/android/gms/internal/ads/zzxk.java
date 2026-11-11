package com.google.android.gms.internal.ads;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzxk implements zzww {
    private final Context mContext;
    private final long mStartTime;
    private final boolean zzael;
    private final zzwy zzbtj;
    private final boolean zzbtn;
    private final boolean zzbto;
    private final zzaef zzbuc;
    private final long zzbud;
    private final String zzbuh;
    private zzxb zzbum;
    private final zznx zzvr;
    private final zzxn zzwh;
    private final Object mLock = new Object();
    private boolean zzbuf = false;
    private List<zzxe> zzbui = new ArrayList();

    public zzxk(Context context, zzaef zzaefVar, zzxn zzxnVar, zzwy zzwyVar, boolean z, boolean z2, String str, long j, long j2, zznx zznxVar, boolean z3) {
        this.mContext = context;
        this.zzbuc = zzaefVar;
        this.zzwh = zzxnVar;
        this.zzbtj = zzwyVar;
        this.zzael = z;
        this.zzbtn = z2;
        this.zzbuh = str;
        this.mStartTime = j;
        this.zzbud = j2;
        this.zzvr = zznxVar;
        this.zzbto = z3;
    }

    @Override // com.google.android.gms.internal.ads.zzww
    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbuf = true;
            if (this.zzbum != null) {
                this.zzbum.cancel();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:48:0x01a6 A[EDGE_INSN: B:48:0x01a6->B:12:0x0043 BREAK  A[LOOP:0: B:7:0x0037->B:28:0x0098]] */
    @Override // com.google.android.gms.internal.ads.zzww
    public final zzxe zzh(List<zzwx> list) {
        zzjn zzjnVar;
        zzakb.zzck("Starting mediation.");
        ArrayList arrayList = new ArrayList();
        zznv zznvVarZzjj = this.zzvr.zzjj();
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
            for (String str : zzwxVar.zzbrt) {
                zznv zznvVarZzjj2 = this.zzvr.zzjj();
                synchronized (this.mLock) {
                    if (this.zzbuf) {
                        return new zzxe(-1);
                    }
                    this.zzbum = new zzxb(this.mContext, str, this.zzwh, this.zzbtj, zzwxVar, this.zzbuc.zzccv, zzjnVar, this.zzbuc.zzacr, this.zzael, this.zzbtn, this.zzbuc.zzadj, this.zzbuc.zzads, this.zzbuc.zzcdk, this.zzbuc.zzcef, this.zzbto);
                    zzxe zzxeVarZza = this.zzbum.zza(this.mStartTime, this.zzbud);
                    this.zzbui.add(zzxeVarZza);
                    if (zzxeVarZza.zzbtv == 0) {
                        zzakb.zzck("Adapter succeeded.");
                        this.zzvr.zze("mediation_network_succeed", str);
                        if (!arrayList.isEmpty()) {
                            this.zzvr.zze("mediation_networks_fail", TextUtils.join(",", arrayList));
                        }
                        this.zzvr.zza(zznvVarZzjj2, "mls");
                        this.zzvr.zza(zznvVarZzjj, "ttm");
                        return zzxeVarZza;
                    }
                    arrayList.add(str);
                    this.zzvr.zza(zznvVarZzjj2, "mlf");
                    if (zzxeVarZza.zzbtx != null) {
                        zzakk.zzcrm.post(new zzxl(this, zzxeVarZza));
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            this.zzvr.zze("mediation_networks_fail", TextUtils.join(",", arrayList));
        }
        return new zzxe(1);
    }

    @Override // com.google.android.gms.internal.ads.zzww
    public final List<zzxe> zzme() {
        return this.zzbui;
    }
}
