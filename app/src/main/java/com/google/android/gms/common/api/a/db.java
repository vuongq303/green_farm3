package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiActivity;

/* JADX INFO: loaded from: classes.dex */
final class db implements Runnable {
    private final da bMo;
    final /* synthetic */ cz bMp;

    db(cz czVar, da daVar) {
        this.bMp = czVar;
        this.bMo = daVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.bMp.mStarted) {
            ConnectionResult connectionResultZM = this.bMo.ZM();
            if (connectionResultZM.Yy()) {
                this.bMp.bJO.startActivityForResult(GoogleApiActivity.a(this.bMp.getActivity(), connectionResultZM.Yz(), this.bMo.ZL(), false), 1);
                return;
            }
            if (this.bMp.bII.ns(connectionResultZM.getErrorCode())) {
                this.bMp.bII.a(this.bMp.getActivity(), this.bMp.bJO, connectionResultZM.getErrorCode(), 2, this.bMp);
            } else if (connectionResultZM.getErrorCode() != 18) {
                this.bMp.b(connectionResultZM, this.bMo.ZL());
            } else {
                this.bMp.bII.a(this.bMp.getActivity().getApplicationContext(), new dc(this, this.bMp.bII.a(this.bMp.getActivity(), this.bMp)));
            }
        }
    }
}
