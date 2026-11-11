package com.gameloft.android2d.iap.a.a;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.billing.IInAppBillingService;

/* JADX INFO: compiled from: IabHelper.java */
/* JADX INFO: loaded from: classes.dex */
class s implements ServiceConnection {
    final /* synthetic */ ab bmm;
    final /* synthetic */ r bmn;

    s(r rVar, ab abVar) {
        this.bmn = rVar;
        this.bmm = abVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.bmn.ft("Billing service disconnected.");
        this.bmn.bmg = null;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.bmn.ft("Billing service connected.");
        if (!a.Se()) {
            this.bmn.bmg = IInAppBillingService.Stub.d(iBinder);
            String packageName = this.bmn.mContext.getPackageName();
            try {
                this.bmn.ft("Checking for in-app billing 3 support.");
                int iA = this.bmn.bmg.a(3, packageName, "inapp");
                if (iA != 0) {
                    if (this.bmm != null) {
                        this.bmm.a(new ad(iA, "Error checking for billing v3 support."));
                    }
                    this.bmn.bmd = false;
                    return;
                }
                this.bmn.ft("In-app billing version 3 supported for " + packageName);
                int iA2 = this.bmn.bmg.a(3, packageName, "subs");
                if (iA2 == 0) {
                    this.bmn.ft("Subscriptions AVAILABLE.");
                    this.bmn.bmd = true;
                } else {
                    this.bmn.ft("Subscriptions NOT AVAILABLE. Response: " + iA2);
                }
                this.bmn.bmc = true;
                if (this.bmm != null) {
                    this.bmm.a(new ad(0, "Setup successful."));
                }
            } catch (RemoteException e) {
                if (this.bmm != null) {
                    this.bmm.a(new ad(-1001, "RemoteException while setting up in-app billing."));
                }
            }
        }
    }
}
