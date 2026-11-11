package com.google.android.gms.ads.mediation;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public interface MediationAdapter {

    public final class zza {
        private int bDF;

        public final zza zzaj(int i) {
            this.bDF = 1;
            return this;
        }

        public final Bundle zzvx() {
            Bundle bundle = new Bundle();
            bundle.putInt("capabilities", this.bDF);
            return bundle;
        }
    }

    void onDestroy();

    void onPause();

    void onResume();
}
