package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zztd implements zzts {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzbny;

    zztd(zztc zztcVar, String str, String str2) {
        this.val$name = str;
        this.zzbny = str2;
    }

    @Override // com.google.android.gms.internal.ads.zzts
    public final void zzb(zztt zzttVar) {
        if (zzttVar.zzboe != null) {
            zzttVar.zzboe.onAppEvent(this.val$name, this.zzbny);
        }
    }
}
