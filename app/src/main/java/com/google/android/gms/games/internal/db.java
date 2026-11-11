package com.google.android.gms.games.internal;

import android.os.RemoteException;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.internal.games.zzej;

/* JADX INFO: loaded from: classes.dex */
final class db extends zzej {
    private final /* synthetic */ i bVh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public db(i iVar) {
        super(iVar.getContext().getMainLooper(), Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
        this.bVh = iVar;
    }

    @Override // com.google.android.gms.internal.games.zzej
    protected final void zzf(String str, int i) {
        try {
            if (this.bVh.isConnected()) {
                ((dn) this.bVh.getService()).zza(str, i);
            } else {
                di.ay("GamesClientImpl", new StringBuilder(String.valueOf(str).length() + 89).append("Unable to increment event ").append(str).append(" by ").append(i).append(" because the games client is no longer connected").toString());
            }
        } catch (RemoteException e) {
            i iVar = this.bVh;
            i.c(e);
        } catch (SecurityException e2) {
            i iVar2 = this.bVh;
            i.a(e2);
        }
    }
}
