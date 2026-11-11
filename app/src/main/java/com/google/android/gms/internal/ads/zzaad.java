package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaad {
    private final zzaqw zzbnd;
    private final boolean zzbwm;
    private final String zzbwn;

    public zzaad(zzaqw zzaqwVar, Map<String, String> map) {
        this.zzbnd = zzaqwVar;
        this.zzbwn = map.get("forceOrientation");
        if (map.containsKey("allowOrientationChange")) {
            this.zzbwm = Boolean.parseBoolean(map.get("allowOrientationChange"));
        } else {
            this.zzbwm = true;
        }
    }

    public final void execute() {
        int iZzrn;
        if (this.zzbnd == null) {
            zzakb.zzdk("AdWebView is null");
            return;
        }
        if ("portrait".equalsIgnoreCase(this.zzbwn)) {
            iZzrn = com.google.android.gms.ads.internal.zzbv.zzem().zzrm();
        } else if ("landscape".equalsIgnoreCase(this.zzbwn)) {
            iZzrn = com.google.android.gms.ads.internal.zzbv.zzem().zzrl();
        } else {
            iZzrn = this.zzbwm ? -1 : com.google.android.gms.ads.internal.zzbv.zzem().zzrn();
        }
        this.zzbnd.setRequestedOrientation(iZzrn);
    }
}
