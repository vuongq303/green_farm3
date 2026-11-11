package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import com.google.firebase.appinvite.FirebaseAppInvite;

/* JADX INFO: loaded from: classes.dex */
public final class zzwo extends FirebaseAppInvite {
    private final Bundle zzbng;

    public zzwo(Bundle bundle) {
        this.zzbng = bundle;
    }

    @Override // com.google.firebase.appinvite.FirebaseAppInvite
    public final String getInvitationId() {
        return this.zzbng.getString("com.google.firebase.appinvite.fdl.extension.InvitationId", null);
    }
}
