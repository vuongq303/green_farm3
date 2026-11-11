package com.google.ads.mediation.a;

import android.app.Activity;
import android.view.View;
import com.google.ads.mediation.j;
import com.google.ads.mediation.k;
import com.google.ads.mediation.l;
import com.google.ads.mediation.m;
import com.google.android.gms.ads.mediation.customevent.CustomEventExtras;
import com.google.android.gms.internal.ads.zzane;

/* JADX INFO: loaded from: classes.dex */
public final class b implements j<CustomEventExtras, i>, l<CustomEventExtras, i> {
    private View bzd;
    private e bze;
    private g bzf;

    private static <T> T hB(String str) {
        try {
            return (T) Class.forName(str).newInstance();
        } catch (Throwable th) {
            String message = th.getMessage();
            zzane.zzdk(new StringBuilder(String.valueOf(str).length() + 46 + String.valueOf(message).length()).append("Could not instantiate custom event adapter: ").append(str).append(". ").append(message).toString());
            return null;
        }
    }

    @Override // com.google.ads.mediation.i
    public final Class<CustomEventExtras> WG() {
        return CustomEventExtras.class;
    }

    @Override // com.google.ads.mediation.i
    public final Class<i> WH() {
        return i.class;
    }

    @Override // com.google.ads.mediation.j
    public final void a(k kVar, Activity activity, i iVar, com.google.ads.d dVar, com.google.ads.mediation.h hVar, CustomEventExtras customEventExtras) {
        this.bze = (e) hB(iVar.className);
        if (this.bze == null) {
            kVar.onFailedToReceiveAd(this, com.google.ads.b.INTERNAL_ERROR);
        } else {
            this.bze.a(new c(this, kVar), activity, iVar.label, iVar.bzk, dVar, hVar, customEventExtras == null ? null : customEventExtras.getExtra(iVar.label));
        }
    }

    @Override // com.google.ads.mediation.l
    public final void a(m mVar, Activity activity, i iVar, com.google.ads.mediation.h hVar, CustomEventExtras customEventExtras) {
        this.bzf = (g) hB(iVar.className);
        if (this.bzf == null) {
            mVar.onFailedToReceiveAd(this, com.google.ads.b.INTERNAL_ERROR);
        } else {
            this.bzf.a(new d(this, this, mVar), activity, iVar.label, iVar.bzk, hVar, customEventExtras == null ? null : customEventExtras.getExtra(iVar.label));
        }
    }

    @Override // com.google.ads.mediation.i
    public final void destroy() {
        if (this.bze != null) {
            this.bze.destroy();
        }
        if (this.bzf != null) {
            this.bzf.destroy();
        }
    }

    @Override // com.google.ads.mediation.j
    public final View getBannerView() {
        return this.bzd;
    }

    @Override // com.google.ads.mediation.l
    public final void showInterstitial() {
        this.bzf.showInterstitial();
    }
}
