package com.google.android.gms.internal.games;

import android.content.Intent;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.c;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.video.b;
import com.google.android.gms.games.video.d;
import com.google.android.gms.games.video.e;
import com.google.android.gms.games.video.f;

/* JADX INFO: loaded from: classes.dex */
public final class zzdy implements b {
    public final aa<d> getCaptureCapabilities(w wVar) {
        return wVar.a(new zzdz(this, wVar));
    }

    public final Intent getCaptureOverlayIntent(w wVar) {
        return c.a(wVar).adG();
    }

    public final aa<f> getCaptureState(w wVar) {
        return wVar.a(new zzea(this, wVar));
    }

    public final aa<com.google.android.gms.games.video.c> isCaptureAvailable(w wVar, int i) {
        return wVar.a(new zzeb(this, wVar, i));
    }

    public final boolean isCaptureSupported(w wVar) {
        return c.a(wVar).ZB();
    }

    public final void registerCaptureOverlayStateChangedListener(w wVar, e eVar) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            iVarA.h(wVar.y(eVar));
        }
    }

    public final void unregisterCaptureOverlayStateChangedListener(w wVar) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            iVarA.ZC();
        }
    }
}
