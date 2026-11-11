package com.google.android.gms.internal.measurement;

import android.app.Activity;
import com.google.android.gms.appinvite.b;
import com.google.android.gms.appinvite.c;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
public final class zzdf implements b {
    public final aa<Status> convertInvitation(w wVar, String str) {
        return wVar.a(new zzdk(this, wVar, str));
    }

    public final aa<c> getInvitation(w wVar, Activity activity, boolean z) {
        return wVar.a(new zzdm(this, wVar, activity, z));
    }

    public final aa<Status> updateInvitationOnInstall(w wVar, String str) {
        return wVar.a(new zzdi(this, wVar, str));
    }
}
