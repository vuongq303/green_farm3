package com.google.android.gms.internal.ads;

import android.os.Bundle;
import com.google.android.gms.c.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzmo extends zzaha {
    private zzahe zzatl;

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void destroy() {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final String getMediationAdapterClassName() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final boolean isLoaded() {
        return false;
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void pause() {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void resume() {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void setImmersiveMode(boolean z) {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void setUserId(String str) {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void show() {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void zza(zzagx zzagxVar) {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void zza(zzahe zzaheVar) {
        this.zzatl = zzaheVar;
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void zza(zzahk zzahkVar) {
        zzane.e("This app is using a lightweight version of the Google Mobile Ads SDK that requires the latest Google Play services to be installed, but Google Play services is either missing or out of date.");
        zzamu.zzsy.post(new zzmp(this));
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void zza(zzkx zzkxVar) {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final Bundle zzba() {
        return new Bundle();
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void zzd(a aVar) {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void zze(a aVar) {
    }

    @Override // com.google.android.gms.internal.ads.zzagz
    public final void zzf(a aVar) {
    }
}
