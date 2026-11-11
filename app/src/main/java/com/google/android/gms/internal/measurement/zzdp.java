package com.google.android.gms.internal.measurement;

import android.content.Intent;
import com.google.android.gms.appinvite.c;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public final class zzdp implements c {
    private final Status zzacp;
    private final Intent zzacq;

    public zzdp(Status status, Intent intent) {
        this.zzacp = status;
        this.zzacq = intent;
    }

    public final Intent getInvitationIntent() {
        return this.zzacq;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzacp;
    }
}
