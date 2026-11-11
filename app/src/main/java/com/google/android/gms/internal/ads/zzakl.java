package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzakl implements zzoi {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ List zzcrs;
    private final /* synthetic */ zzoh zzcrt;

    zzakl(zzakk zzakkVar, List list, zzoh zzohVar, Context context) {
        this.zzcrs = list;
        this.zzcrt = zzohVar;
        this.val$context = context;
    }

    @Override // com.google.android.gms.internal.ads.zzoi
    public final void zzjp() {
        for (String str : this.zzcrs) {
            String strValueOf = String.valueOf(str);
            zzakb.zzdj(strValueOf.length() != 0 ? "Pinging url: ".concat(strValueOf) : new String("Pinging url: "));
            this.zzcrt.mayLaunchUrl(Uri.parse(str), null, null);
        }
        this.zzcrt.zzc((Activity) this.val$context);
    }

    @Override // com.google.android.gms.internal.ads.zzoi
    public final void zzjq() {
    }
}
