package com.google.firebase.appinvite;

import android.os.Bundle;
import com.google.android.gms.internal.measurement.zzwo;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

/* JADX INFO: loaded from: classes.dex */
public abstract class FirebaseAppInvite {
    public static FirebaseAppInvite getInvitation(PendingDynamicLinkData pendingDynamicLinkData) {
        Bundle bundleZzrw = pendingDynamicLinkData.zzrw();
        if (bundleZzrw == null || bundleZzrw.getString("com.google.firebase.appinvite.fdl.extension.InvitationId", null) == null) {
            return null;
        }
        return new zzwo(bundleZzrw);
    }

    public abstract String getInvitationId();
}
