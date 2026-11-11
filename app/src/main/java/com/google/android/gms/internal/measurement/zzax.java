package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.os.RemoteException;
import com.google.android.gms.a.n;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.stats.a;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public final class zzax extends zzar {
    private final zzaz zzwg;
    private zzci zzwh;
    private final zzbw zzwi;
    private final zzcz zzwj;

    protected zzax(zzat zzatVar) {
        super(zzatVar);
        this.zzwj = new zzcz(zzatVar.zzbt());
        this.zzwg = new zzaz(this);
        this.zzwi = new zzay(this, zzatVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onServiceDisconnected(ComponentName componentName) {
        n.zzab();
        if (this.zzwh != null) {
            this.zzwh = null;
            zza("Disconnected from device AnalyticsService", componentName);
            zzby().zzbq();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzci zzciVar) {
        n.zzab();
        this.zzwh = zzciVar;
        zzcu();
        zzby().onServiceConnected();
    }

    private final void zzcu() {
        this.zzwj.start();
        this.zzwi.zzh(zzcc.zzzp.get().longValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzcv() {
        n.zzab();
        if (isConnected()) {
            zzq("Inactivity, disconnecting from device AnalyticsService");
            disconnect();
        }
    }

    public final boolean connect() {
        n.zzab();
        zzch();
        if (this.zzwh != null) {
            return true;
        }
        zzci zzciVarZzcw = this.zzwg.zzcw();
        if (zzciVarZzcw == null) {
            return false;
        }
        this.zzwh = zzciVarZzcw;
        zzcu();
        return true;
    }

    public final void disconnect() {
        n.zzab();
        zzch();
        try {
            a.aaY().a(getContext(), this.zzwg);
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e2) {
        }
        if (this.zzwh != null) {
            this.zzwh = null;
            zzby().zzbq();
        }
    }

    public final boolean isConnected() {
        n.zzab();
        zzch();
        return this.zzwh != null;
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
    }

    public final boolean zzb(zzch zzchVar) {
        bf.checkNotNull(zzchVar);
        n.zzab();
        zzch();
        zzci zzciVar = this.zzwh;
        if (zzciVar == null) {
            return false;
        }
        try {
            zzciVar.zza(zzchVar.zzcs(), zzchVar.zzen(), zzchVar.zzeo() ? zzbu.zzdz() : zzbu.zzea(), Collections.emptyList());
            zzcu();
            return true;
        } catch (RemoteException e) {
            zzq("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    public final boolean zzct() {
        n.zzab();
        zzch();
        zzci zzciVar = this.zzwh;
        if (zzciVar == null) {
            return false;
        }
        try {
            zzciVar.zzbn();
            zzcu();
            return true;
        } catch (RemoteException e) {
            zzq("Failed to clear hits from AnalyticsService");
            return false;
        }
    }
}
