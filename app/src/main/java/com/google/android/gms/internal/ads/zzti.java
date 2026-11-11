package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzti extends zzahf {
    private final /* synthetic */ zzst zzbnw;

    zzti(zzst zzstVar) {
        this.zzbnw = zzstVar;
    }

    @Override // com.google.android.gms.internal.ads.zzahe
    public final void onRewardedVideoAdClosed() {
        this.zzbnw.zzxo.add(new zztm(this));
    }

    @Override // com.google.android.gms.internal.ads.zzahe
    public final void onRewardedVideoAdFailedToLoad(int i) {
        this.zzbnw.zzxo.add(new zztp(this, i));
    }

    @Override // com.google.android.gms.internal.ads.zzahe
    public final void onRewardedVideoAdLeftApplication() {
        this.zzbnw.zzxo.add(new zzto(this));
    }

    @Override // com.google.android.gms.internal.ads.zzahe
    public final void onRewardedVideoAdLoaded() {
        this.zzbnw.zzxo.add(new zztj(this));
    }

    @Override // com.google.android.gms.internal.ads.zzahe
    public final void onRewardedVideoAdOpened() {
        this.zzbnw.zzxo.add(new zztk(this));
    }

    @Override // com.google.android.gms.internal.ads.zzahe
    public final void onRewardedVideoCompleted() {
        this.zzbnw.zzxo.add(new zztq(this));
    }

    @Override // com.google.android.gms.internal.ads.zzahe
    public final void onRewardedVideoStarted() {
        this.zzbnw.zzxo.add(new zztl(this));
    }

    @Override // com.google.android.gms.internal.ads.zzahe
    public final void zza(zzagu zzaguVar) {
        this.zzbnw.zzxo.add(new zztn(this, zzaguVar));
    }
}
