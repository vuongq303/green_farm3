package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzoo;
import com.google.android.gms.internal.ads.zzoq;
import com.google.android.gms.internal.ads.zzov;
import com.google.android.gms.internal.ads.zzpb;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class t implements Runnable {
    private final /* synthetic */ zzbc bBN;
    private final /* synthetic */ int bBO;
    private final /* synthetic */ zzpb bBR;
    private final /* synthetic */ List bBS;

    t(zzbc zzbcVar, zzpb zzpbVar, int i, List list) {
        this.bBN = zzbcVar;
        this.bBR = zzpbVar;
        this.bBO = i;
        this.bBS = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if ((this.bBR instanceof zzoq) && this.bBN.zzvw.bDe != null) {
                this.bBN.zzvu = this.bBO != this.bBS.size() + (-1);
                zzov zzovVarA = zzbc.a(this.bBR);
                this.bBN.zzvw.bDe.zza(zzovVarA);
                this.bBN.a(zzovVarA.zzka());
            } else if ((this.bBR instanceof zzoq) && this.bBN.zzvw.bDd != null) {
                this.bBN.zzvu = this.bBO != this.bBS.size() + (-1);
                zzoq zzoqVar = (zzoq) this.bBR;
                this.bBN.zzvw.bDd.zza(zzoqVar);
                this.bBN.a(zzoqVar.zzka());
            } else if ((this.bBR instanceof zzoo) && this.bBN.zzvw.bDe != null) {
                this.bBN.zzvu = this.bBO != this.bBS.size() + (-1);
                zzov zzovVarA2 = zzbc.a(this.bBR);
                this.bBN.zzvw.bDe.zza(zzovVarA2);
                this.bBN.a(zzovVarA2.zzka());
            } else if (!(this.bBR instanceof zzoo) || this.bBN.zzvw.bDc == null) {
                this.bBN.zzc(3, this.bBO != this.bBS.size() + (-1));
            } else {
                this.bBN.zzvu = this.bBO != this.bBS.size() + (-1);
                zzoo zzooVar = (zzoo) this.bBR;
                this.bBN.zzvw.bDc.zza(zzooVar);
                this.bBN.a(zzooVar.zzka());
            }
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
