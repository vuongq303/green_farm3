package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbfc;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzbfc<M extends zzbfc<M>> extends zzbfi {
    protected zzbfe zzebk;

    @Override // com.google.android.gms.internal.ads.zzbfi
    public /* synthetic */ Object clone() {
        zzbfc zzbfcVar = (zzbfc) super.clone();
        zzbfg.zza(this, zzbfcVar);
        return zzbfcVar;
    }

    @Override // com.google.android.gms.internal.ads.zzbfi
    public void zza(zzbfa zzbfaVar) {
        if (this.zzebk == null) {
            return;
        }
        for (int i = 0; i < this.zzebk.size(); i++) {
            this.zzebk.zzdg(i).zza(zzbfaVar);
        }
    }

    protected final boolean zza(zzbez zzbezVar, int i) {
        int position = zzbezVar.getPosition();
        if (!zzbezVar.zzbq(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzbfk zzbfkVar = new zzbfk(i, zzbezVar.zzab(position, zzbezVar.getPosition() - position));
        zzbff zzbffVarZzdf = null;
        if (this.zzebk == null) {
            this.zzebk = new zzbfe();
        } else {
            zzbffVarZzdf = this.zzebk.zzdf(i2);
        }
        if (zzbffVarZzdf == null) {
            zzbffVarZzdf = new zzbff();
            this.zzebk.zza(i2, zzbffVarZzdf);
        }
        zzbffVarZzdf.zza(zzbfkVar);
        return true;
    }

    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzago */
    public final /* synthetic */ zzbfi clone() {
        return (zzbfc) clone();
    }

    @Override // com.google.android.gms.internal.ads.zzbfi
    protected int zzr() {
        int iZzr = 0;
        if (this.zzebk == null) {
            return 0;
        }
        int i = 0;
        while (true) {
            int i2 = iZzr;
            if (i >= this.zzebk.size()) {
                return i2;
            }
            iZzr = this.zzebk.zzdg(i).zzr() + i2;
            i++;
        }
    }
}
