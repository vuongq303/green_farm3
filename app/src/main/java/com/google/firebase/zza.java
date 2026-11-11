package com.google.firebase;

import com.google.android.gms.common.api.a.c;

/* JADX INFO: loaded from: classes.dex */
final class zza implements c {
    zza() {
    }

    @Override // com.google.android.gms.common.api.a.c
    public final void onBackgroundStateChanged(boolean z) {
        FirebaseApp.onBackgroundStateChanged(z);
    }
}
