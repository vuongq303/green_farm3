package com.google.android.gms.ads.internal.overlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.internal.ads.zzaaq;
import com.google.android.gms.internal.ads.zzadh;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzs extends zzaaq {
    private AdOverlayInfoParcel bAN;
    private boolean bAO = false;
    private boolean bAP = false;
    private Activity zztk;

    public zzs(Activity activity, AdOverlayInfoParcel adOverlayInfoParcel) {
        this.bAN = adOverlayInfoParcel;
        this.zztk = activity;
    }

    private final synchronized void WT() {
        if (!this.bAP) {
            if (this.bAN.zzbyn != null) {
                this.bAN.zzbyn.zzcb();
            }
            this.bAP = true;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onActivityResult(int i, int i2, Intent intent) {
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onBackPressed() {
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onCreate(Bundle bundle) {
        boolean z = bundle != null ? bundle.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false) : false;
        if (this.bAN == null) {
            this.zztk.finish();
            return;
        }
        if (z) {
            this.zztk.finish();
            return;
        }
        if (bundle == null) {
            if (this.bAN.zzbym != null) {
                this.bAN.zzbym.onAdClicked();
            }
            if (this.zztk.getIntent() != null && this.zztk.getIntent().getBooleanExtra("shouldCallOnOverlayOpened", true) && this.bAN.zzbyn != null) {
                this.bAN.zzbyn.zzcc();
            }
        }
        zzbv.zzeh();
        if (zza.zza(this.zztk, this.bAN.zzbyl, this.bAN.zzbyt)) {
            return;
        }
        this.zztk.finish();
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onDestroy() {
        if (this.zztk.isFinishing()) {
            WT();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onPause() {
        if (this.bAN.zzbyn != null) {
            this.bAN.zzbyn.onPause();
        }
        if (this.zztk.isFinishing()) {
            WT();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onRestart() {
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onResume() {
        if (this.bAO) {
            this.zztk.finish();
            return;
        }
        this.bAO = true;
        if (this.bAN.zzbyn != null) {
            this.bAN.zzbyn.onResume();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.bAO);
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onStart() {
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onStop() {
        if (this.zztk.isFinishing()) {
            WT();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void zzax() {
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final boolean zznj() {
        return false;
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void zzo(com.google.android.gms.c.a aVar) {
    }
}
