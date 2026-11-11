package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.k;
import com.google.android.gms.common.l;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class zzaka extends zzajx {
    private Context mContext;

    zzaka(Context context) {
        this.mContext = context;
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        boolean isAdIdFakeForDebugLogging;
        try {
            isAdIdFakeForDebugLogging = AdvertisingIdClient.getIsAdIdFakeForDebugLogging(this.mContext);
        } catch (k | l | IOException | IllegalStateException e) {
            zzakb.zzb("Fail to get isAdIdFakeForDebugLogging", e);
            isAdIdFakeForDebugLogging = false;
        }
        zzamy.zzaf(isAdIdFakeForDebugLogging);
        zzakb.zzdk(new StringBuilder(43).append("Update ad debug logging enablement as ").append(isAdIdFakeForDebugLogging).toString());
    }
}
