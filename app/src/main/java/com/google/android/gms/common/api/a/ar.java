package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class ar implements Runnable {
    private final /* synthetic */ aq bKw;

    ar(aq aqVar) {
        this.bKw = aqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bKw.zzgk.cancelAvailabilityErrorNotifications(this.bKw.mContext);
    }
}
