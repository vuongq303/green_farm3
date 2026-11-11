package com.google.android.gms.auth.api;

import android.os.Bundle;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.android.gms.common.api.j;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class b implements j {
    private static final b bFk = new c().XC();
    private final String bFl = null;
    private final PasswordSpecification bFm;
    private final boolean bFn;

    public b(c cVar) {
        this.bFm = cVar.bFm;
        this.bFn = cVar.bFo.booleanValue();
    }

    public final PasswordSpecification XB() {
        return this.bFm;
    }

    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("consumer_package", null);
        bundle.putParcelable("password_specification", this.bFm);
        bundle.putBoolean("force_save_dialog", this.bFn);
        return bundle;
    }
}
