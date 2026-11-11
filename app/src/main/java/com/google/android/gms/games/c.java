package com.google.android.gms.games;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.games.zzad;
import com.google.android.gms.internal.games.zzai;
import com.google.android.gms.internal.games.zzam;
import com.google.android.gms.internal.games.zzbc;
import com.google.android.gms.internal.games.zzbd;
import com.google.android.gms.internal.games.zzbe;
import com.google.android.gms.internal.games.zzbo;
import com.google.android.gms.internal.games.zzbz;
import com.google.android.gms.internal.games.zzca;
import com.google.android.gms.internal.games.zzci;
import com.google.android.gms.internal.games.zzcw;
import com.google.android.gms.internal.games.zzcx;
import com.google.android.gms.internal.games.zzdb;
import com.google.android.gms.internal.games.zzdy;
import com.google.android.gms.internal.games.zze;
import com.google.android.gms.internal.games.zzep;
import com.google.android.gms.internal.games.zzf;
import com.google.android.gms.internal.games.zzt;
import com.google.android.gms.internal.games.zzv;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    static final com.google.android.gms.common.api.m<com.google.android.gms.games.internal.i> CLIENT_KEY = new com.google.android.gms.common.api.m<>();
    private static final com.google.android.gms.common.api.b<com.google.android.gms.games.internal.i, d> CLIENT_BUILDER = new x();
    private static final com.google.android.gms.common.api.b<com.google.android.gms.games.internal.i, d> bRQ = new y();
    public static final Scope bGK = new Scope("https://www.googleapis.com/auth/games");
    public static final Scope bGJ = new Scope("https://www.googleapis.com/auth/games_lite");

    @Deprecated
    public static final com.google.android.gms.common.api.a<d> API = new com.google.android.gms.common.api.a<>("Games.API", CLIENT_BUILDER, CLIENT_KEY);
    public static final Scope bRR = new Scope("https://www.googleapis.com/auth/games.firstparty");
    private static final com.google.android.gms.common.api.a<d> bRS = new com.google.android.gms.common.api.a<>("Games.API_1P", bRQ, CLIENT_KEY);

    @Deprecated
    public static final l bRT = new zzad();

    @Deprecated
    public static final com.google.android.gms.games.achievement.b bRU = new zzf();
    private static final zze bRV = new zzt();

    @Deprecated
    public static final com.google.android.gms.games.event.b bRW = new zzv();

    @Deprecated
    public static final com.google.android.gms.games.a.j bRX = new zzam();

    @Deprecated
    public static final com.google.android.gms.games.multiplayer.c bRY = new zzai();

    @Deprecated
    public static final com.google.android.gms.games.multiplayer.turnbased.e bRZ = new zzdb();

    @Deprecated
    public static final com.google.android.gms.games.multiplayer.realtime.c bSa = new zzbz();
    private static final com.google.android.gms.games.multiplayer.e bSb = new zzbc();

    @Deprecated
    public static final r bSc = new zzbe();

    @Deprecated
    public static final o bSd = new zzbd();

    @Deprecated
    public static final com.google.android.gms.games.quest.c bSe = new zzbo();

    @Deprecated
    public static final com.google.android.gms.games.request.c bSf = new zzca();

    @Deprecated
    public static final com.google.android.gms.games.snapshot.d bSg = new zzci();

    @Deprecated
    public static final com.google.android.gms.games.stats.b bSh = new zzcx();

    @Deprecated
    public static final com.google.android.gms.games.video.b bSi = new zzdy();
    private static final zzep bSj = new zzcw();

    @Deprecated
    public static com.google.android.gms.common.api.aa<f> a(com.google.android.gms.common.api.w wVar, String str) {
        bf.d(str, (Object) "Please provide a valid serverClientId");
        return wVar.b(new z(wVar, str));
    }

    public static com.google.android.gms.games.internal.i a(com.google.android.gms.common.api.w wVar) {
        return a(wVar, true);
    }

    public static com.google.android.gms.games.internal.i a(com.google.android.gms.common.api.w wVar, boolean z) {
        bf.b(wVar != null, "GoogleApiClient parameter is required.");
        bf.a(wVar.isConnected(), "GoogleApiClient must be connected.");
        return b(wVar, z);
    }

    @Deprecated
    public static com.google.android.gms.common.api.aa<Status> b(com.google.android.gms.common.api.w wVar) {
        return wVar.b(new aa(wVar));
    }

    public static com.google.android.gms.games.internal.i b(com.google.android.gms.common.api.w wVar, boolean z) {
        bf.a(wVar.a(API), "GoogleApiClient is not configured to use the Games Api. Pass Games.API into GoogleApiClient.Builder#addApi() to use this feature.");
        boolean zB = wVar.b(API);
        if (z && !zB) {
            throw new IllegalStateException("GoogleApiClient has an optional Games.API and is not connected to Games. Use GoogleApiClient.hasConnectedApi(Games.API) to guard this call.");
        }
        if (zB) {
            return (com.google.android.gms.games.internal.i) wVar.a(CLIENT_KEY);
        }
        return null;
    }
}
