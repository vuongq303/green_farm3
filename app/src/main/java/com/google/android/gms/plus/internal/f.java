package com.google.android.gms.plus.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.ak;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.common.internal.y;
import com.google.android.gms.internal.plus.zzr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class f extends y<d> {
    private com.google.android.gms.plus.a.a.a bXU;
    private final zzn bXV;

    public f(Context context, Looper looper, r rVar, zzn zznVar, com.google.android.gms.common.api.y yVar, z zVar) {
        super(context, looper, 2, rVar, yVar, zVar);
        this.bXV = zznVar;
    }

    public final ak a(com.google.android.gms.common.api.a.f<com.google.android.gms.plus.c> fVar, int i, String str) {
        checkConnected();
        h hVar = new h(fVar);
        try {
            return ((d) getService()).a(hVar, 1, i, -1, str);
        } catch (RemoteException e) {
            hVar.a(DataHolder.nG(8), null);
            return null;
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.plus.c> fVar) {
        checkConnected();
        h hVar = new h(fVar);
        try {
            ((d) getService()).a(hVar, 2, 1, -1, null);
        } catch (RemoteException e) {
            hVar.a(DataHolder.nG(8), null);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.plus.c> fVar, Collection<String> collection) {
        checkConnected();
        h hVar = new h(fVar);
        try {
            ((d) getService()).a(hVar, new ArrayList(collection));
        } catch (RemoteException e) {
            hVar.a(DataHolder.nG(8), null);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.plus.c> fVar, String[] strArr) {
        a(fVar, Arrays.asList(strArr));
    }

    public final String aad() {
        checkConnected();
        try {
            return ((d) getService()).aad();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public final com.google.android.gms.plus.a.a.a afY() {
        checkConnected();
        return this.bXU;
    }

    public final void b(com.google.android.gms.common.api.a.f<Status> fVar) {
        checkConnected();
        zza();
        i iVar = new i(fVar);
        try {
            ((d) getService()).a(iVar);
        } catch (RemoteException e) {
            iVar.e(8, null);
        }
    }

    @Override // com.google.android.gms.common.internal.e
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusService");
        return iInterfaceQueryLocalInterface instanceof d ? (d) iInterfaceQueryLocalInterface : new e(iBinder);
    }

    @Override // com.google.android.gms.common.internal.e
    protected final Bundle getGetServiceRequestExtraArgs() {
        Bundle bundleAga = this.bXV.aga();
        bundleAga.putStringArray("request_visible_actions", this.bXV.afZ());
        bundleAga.putString("auth_package", this.bXV.acc());
        return bundleAga;
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.plus.internal.IPlusService";
    }

    @Override // com.google.android.gms.common.internal.e
    protected final String getStartServiceAction() {
        return "com.google.android.gms.plus.service.START";
    }

    @Override // com.google.android.gms.common.internal.e
    protected final void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        if (i == 0 && bundle != null && bundle.containsKey("loaded_person")) {
            this.bXU = zzr.zza(bundle.getByteArray("loaded_person"));
        }
        super.onPostInitHandler(i, iBinder, bundle, i2);
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public final boolean requiresSignIn() {
        Set<Scope> setE = getClientSettings().e(com.google.android.gms.plus.d.API);
        if (setE == null || setE.isEmpty()) {
            return false;
        }
        return (setE.size() == 1 && setE.contains(new Scope("plus_one_placeholder_scope"))) ? false : true;
    }

    public final void zza() {
        checkConnected();
        try {
            this.bXU = null;
            ((d) getService()).zza();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }
}
