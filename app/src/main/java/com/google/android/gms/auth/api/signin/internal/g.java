package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.common.internal.y;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class g extends y<j> {
    private final GoogleSignInOptions bHn;

    public g(Context context, Looper looper, r rVar, GoogleSignInOptions googleSignInOptions, com.google.android.gms.common.api.y yVar, z zVar) {
        super(context, looper, 91, rVar, yVar, zVar);
        googleSignInOptions = googleSignInOptions == null ? new com.google.android.gms.auth.api.signin.c().Yp() : googleSignInOptions;
        if (!rVar.aag().isEmpty()) {
            com.google.android.gms.auth.api.signin.c cVar = new com.google.android.gms.auth.api.signin.c(googleSignInOptions);
            Iterator<Scope> it = rVar.aag().iterator();
            while (it.hasNext()) {
                cVar.a(it.next(), new Scope[0]);
            }
            googleSignInOptions = cVar.Yp();
        }
        this.bHn = googleSignInOptions;
    }

    @Override // com.google.android.gms.common.internal.e
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.signin.internal.ISignInService");
        return iInterfaceQueryLocalInterface instanceof j ? (j) iInterfaceQueryLocalInterface : new k(iBinder);
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.auth.api.signin.internal.ISignInService";
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final Intent getSignInIntent() {
        return h.a(getContext(), this.bHn);
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getStartServiceAction() {
        return "com.google.android.gms.auth.api.signin.service.START";
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final boolean providesSignIn() {
        return true;
    }
}
