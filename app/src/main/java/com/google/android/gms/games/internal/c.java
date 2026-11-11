package com.google.android.gms.games.internal;

import android.os.Binder;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class c {
    protected i bTN;
    protected e bTO;

    private c(i iVar, int i) {
        this.bTN = iVar;
        zzm(i);
    }

    public static c a(i iVar, int i) {
        return com.google.android.gms.common.d.s.abu() ? new f(iVar, i) : new c(iVar, i);
    }

    public void YZ() {
        this.bTN.a(this.bTO.bTM, this.bTO.adg());
    }

    public void zzb(View view) {
    }

    protected void zzm(int i) {
        this.bTO = new e(i, new Binder());
    }
}
