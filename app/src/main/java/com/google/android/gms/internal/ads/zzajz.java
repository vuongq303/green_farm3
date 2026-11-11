package com.google.android.gms.internal.ads;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzajz {
    public static void zzai(Context context) {
        if (zzamy.zzbl(context) && !zzamy.zzsk()) {
            zzanz zzanzVar = (zzanz) new zzaka(context).zznt();
            zzakb.zzdj("Updating ad debug logging enablement.");
            zzanm.zza(zzanzVar, "AdDebugLogUpdater.updateEnablement");
        }
    }
}
