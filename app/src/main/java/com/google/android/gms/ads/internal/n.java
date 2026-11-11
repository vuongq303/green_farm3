package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzxz;
import com.google.android.gms.internal.ads.zzyc;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class n implements zzv<zzaqw> {
    private final /* synthetic */ zzxz bBB;
    private final /* synthetic */ zzac bBC;
    private final /* synthetic */ zzyc bBD;

    n(zzxz zzxzVar, zzac zzacVar, zzyc zzycVar) {
        this.bBB = zzxzVar;
        this.bBC = zzacVar;
        this.bBD = zzycVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzaqw zzaqwVar, Map map) {
        zzaqw zzaqwVar2 = zzaqwVar;
        View view = zzaqwVar2.getView();
        if (view != null) {
            try {
                if (this.bBB != null) {
                    if (this.bBB.getOverrideClickHandling()) {
                        zzas.a(zzaqwVar2);
                    } else {
                        this.bBB.zzj(com.google.android.gms.c.d.F(view));
                        this.bBC.bAR.onAdClicked();
                    }
                } else if (this.bBD != null) {
                    if (this.bBD.getOverrideClickHandling()) {
                        zzas.a(zzaqwVar2);
                    } else {
                        this.bBD.zzj(com.google.android.gms.c.d.F(view));
                        this.bBC.bAR.onAdClicked();
                    }
                }
            } catch (RemoteException e) {
                zzakb.zzc("Unable to call handleClick on mapper", e);
            }
        }
    }
}
