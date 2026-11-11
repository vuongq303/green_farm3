package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zztc extends zzlb {
    private final /* synthetic */ zzst zzbnw;

    zztc(zzst zzstVar) {
        this.zzbnw = zzstVar;
    }

    @Override // com.google.android.gms.internal.ads.zzla
    public final void onAppEvent(String str, String str2) {
        this.zzbnw.zzxo.add(new zztd(this, str, str2));
    }
}
