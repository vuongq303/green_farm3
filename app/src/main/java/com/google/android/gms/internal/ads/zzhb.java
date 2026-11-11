package com.google.android.gms.internal.ads;

import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public final class zzhb implements Comparator<zzgp> {
    public zzhb(zzha zzhaVar) {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzgp zzgpVar, zzgp zzgpVar2) {
        zzgp zzgpVar3 = zzgpVar;
        zzgp zzgpVar4 = zzgpVar2;
        if (zzgpVar3.zzhc() < zzgpVar4.zzhc()) {
            return -1;
        }
        if (zzgpVar3.zzhc() > zzgpVar4.zzhc()) {
            return 1;
        }
        if (zzgpVar3.zzhb() < zzgpVar4.zzhb()) {
            return -1;
        }
        if (zzgpVar3.zzhb() > zzgpVar4.zzhb()) {
            return 1;
        }
        float fZzhe = (zzgpVar3.zzhe() - zzgpVar3.zzhc()) * (zzgpVar3.zzhd() - zzgpVar3.zzhb());
        float fZzhe2 = (zzgpVar4.zzhe() - zzgpVar4.zzhc()) * (zzgpVar4.zzhd() - zzgpVar4.zzhb());
        if (fZzhe <= fZzhe2) {
            return fZzhe < fZzhe2 ? 1 : 0;
        }
        return -1;
    }
}
