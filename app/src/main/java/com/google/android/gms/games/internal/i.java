package com.google.android.gms.games.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.BinderWrapper;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.zze;
import com.google.android.gms.internal.games.zzel;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class i extends com.google.android.gms.common.internal.y<dn> {
    private boolean bTQ;
    private zzel bTR;
    private final String bTS;
    private PlayerEntity bTT;
    private GameEntity bTU;
    private final c bTV;
    private final Binder bTW;
    private final long bTX;
    private final com.google.android.gms.games.d bTY;
    private boolean bTZ;
    private Bundle bUa;

    public i(Context context, Looper looper, com.google.android.gms.common.internal.r rVar, com.google.android.gms.games.d dVar, com.google.android.gms.common.api.y yVar, com.google.android.gms.common.api.z zVar) {
        super(context, looper, 1, rVar, yVar, zVar);
        this.bTR = new dg(this);
        this.bTQ = false;
        this.bTZ = false;
        this.bTS = rVar.aai();
        this.bTW = new Binder();
        this.bTV = c.a(this, rVar.aae());
        this.bTX = hashCode();
        this.bTY = dVar;
        if (this.bTY.bSr) {
            return;
        }
        if (rVar.aak() != null || (context instanceof Activity)) {
            P(rVar.aak());
        }
    }

    private static <R> void a(com.google.android.gms.common.api.a.f<R> fVar, SecurityException securityException) {
        if (fVar != null) {
            fVar.setFailedResult(com.google.android.gms.games.k.og(4));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(SecurityException securityException) {
        di.b("GamesClientImpl", "Is player signed out?", securityException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Room aa(DataHolder dataHolder) {
        com.google.android.gms.games.multiplayer.realtime.l lVar = new com.google.android.gms.games.multiplayer.realtime.l(dataHolder);
        try {
            return lVar.getCount() > 0 ? lVar.get(0).freeze() : null;
        } finally {
            lVar.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(RemoteException remoteException) {
        di.a("GamesClientImpl", "service died", remoteException);
    }

    public final void P(View view) {
        this.bTV.zzb(view);
    }

    public final void Q(String str, int i) {
        try {
            zzb(str, i);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void R(String str, int i) {
        ((dn) getService()).R(str, i);
    }

    public final void S(String str, int i) {
        try {
            R(str, i);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final boolean ZA() {
        return ((dn) getService()).ZA();
    }

    public final boolean ZB() {
        try {
            return ZA();
        } catch (RemoteException e) {
            c(e);
            return false;
        }
    }

    public final void ZC() {
        try {
            adH();
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void ZO() {
        ((dn) getService()).zzb(this.bTX);
    }

    public final int a(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.realtime.d> vVar, byte[] bArr, String str, String str2) {
        return ((dn) getService()).a(new bg(vVar), bArr, str, str2);
    }

    public final int a(byte[] bArr, String str, String[] strArr) {
        com.google.android.gms.common.internal.bf.d(strArr, "Participant IDs must not be null");
        try {
            com.google.android.gms.common.internal.bf.d(strArr, "Participant IDs must not be null");
            return ((dn) getService()).b(bArr, str, strArr);
        } catch (RemoteException e) {
            c(e);
            return -1;
        }
    }

    public final Intent a(int i, byte[] bArr, int i2, Bitmap bitmap, String str) {
        try {
            Intent intentA = ((dn) getService()).a(i, bArr, i2, str);
            com.google.android.gms.common.internal.bf.d(bitmap, "Must provide a non null icon");
            intentA.putExtra("com.google.android.gms.games.REQUEST_ITEM_ICON", bitmap);
            return intentA;
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent a(PlayerEntity playerEntity) {
        return ((dn) getService()).a(playerEntity);
    }

    public final Intent a(Room room, int i) {
        return ((dn) getService()).a((RoomEntity) room.freeze(), i);
    }

    public final Intent a(String str, boolean z, boolean z2, int i) {
        return ((dn) getService()).a(str, z, z2, i);
    }

    public final void a(IBinder iBinder, Bundle bundle) {
        if (isConnected()) {
            try {
                ((dn) getService()).a(iBinder, bundle);
            } catch (RemoteException e) {
                c(e);
            }
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.m> fVar) {
        try {
            ((dn) getService()).b(new dd(fVar));
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.d> fVar, int i) {
        try {
            ((dn) getService()).a((dj) new o(fVar), i);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.request.d> fVar, int i, int i2, int i3) {
        try {
            ((dn) getService()).a(new bk(fVar), i, i2, i3);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.s> fVar, int i, boolean z, boolean z2) {
        try {
            ((dn) getService()).a(new ay(fVar), i, z, z2);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.j> fVar, int i, int[] iArr) {
        try {
            ((dn) getService()).a(new cf(fVar), i, iArr);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.m> fVar, com.google.android.gms.games.a.f fVar2, int i, int i2) {
        try {
            ((dn) getService()).a(new r(fVar), fVar2.aeb().aeo(), i, i2);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.g> fVar, com.google.android.gms.games.multiplayer.turnbased.d dVar) {
        try {
            ((dn) getService()).a(new ca(fVar), dVar.getVariant(), dVar.aeV(), dVar.aeL(), dVar.aeD());
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.e> fVar, Snapshot snapshot, com.google.android.gms.games.snapshot.b bVar) {
        SnapshotContents snapshotContentsAfo = snapshot.afo();
        com.google.android.gms.common.internal.bf.a(!snapshotContentsAfo.isClosed(), "Snapshot already closed");
        BitmapTeleporter bitmapTeleporterAfA = bVar.afA();
        if (bitmapTeleporterAfA != null) {
            bitmapTeleporterAfA.e(getContext().getCacheDir());
        }
        Contents contentsAfp = snapshotContentsAfo.afp();
        snapshotContentsAfo.close();
        try {
            ((dn) getService()).a(new bt(fVar), snapshot.afn().getSnapshotId(), (zze) bVar, contentsAfp);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.d> fVar, String str) {
        try {
            ((dn) getService()).a(fVar == null ? null : new ck(fVar), str, this.bTV.bTO.bTM, this.bTV.bTO.adg());
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.d> fVar, String str, int i) {
        try {
            ((dn) getService()).a(fVar == null ? null : new ck(fVar), str, i, this.bTV.bTO.bTM, this.bTV.bTO.adg());
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.m> fVar, String str, int i, int i2, int i3, boolean z) {
        try {
            ((dn) getService()).a(new r(fVar), str, i, i2, i3, z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.s> fVar, String str, int i, boolean z, boolean z2) {
        switch (str) {
            case "played_with":
                try {
                    ((dn) getService()).a(new ay(fVar), str, i, z, z2);
                    return;
                } catch (SecurityException e) {
                    a(fVar, e);
                    return;
                }
            default:
                String strValueOf = String.valueOf(str);
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "Invalid player collection: ".concat(strValueOf) : new String("Invalid player collection: "));
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.n> fVar, String str, long j, String str2) {
        try {
            ((dn) getService()).a(fVar == null ? null : new bx(fVar), str, j, str2);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.h> fVar, String str, String str2) {
        try {
            ((dn) getService()).a(new cb(fVar), str, str2);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.l> fVar, String str, String str2, int i, int i2) {
        try {
            ((dn) getService()).a(new aw(fVar), (String) null, str2, i, i2);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.h> fVar, String str, String str2, com.google.android.gms.games.snapshot.b bVar, SnapshotContents snapshotContents) {
        com.google.android.gms.common.internal.bf.a(!snapshotContents.isClosed(), "SnapshotContents already closed");
        BitmapTeleporter bitmapTeleporterAfA = bVar.afA();
        if (bitmapTeleporterAfA != null) {
            bitmapTeleporterAfA.e(getContext().getCacheDir());
        }
        Contents contentsAfp = snapshotContents.afp();
        snapshotContents.close();
        try {
            ((dn) getService()).a(new bv(fVar), str, str2, (zze) bVar, contentsAfp);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.s> fVar, String str, boolean z) {
        try {
            ((dn) getService()).b(new ay(fVar), str, z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.h> fVar, String str, boolean z, int i) {
        try {
            ((dn) getService()).a(new bv(fVar), str, z, i);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.k> fVar, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) {
        try {
            ((dn) getService()).a(new ce(fVar), str, bArr, str2, participantResultArr);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.k> fVar, String str, byte[] bArr, ParticipantResult[] participantResultArr) {
        try {
            ((dn) getService()).a(new ce(fVar), str, bArr, participantResultArr);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.s> fVar, boolean z) {
        try {
            ((dn) getService()).c(new ay(fVar), z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.event.c> fVar, boolean z, String... strArr) {
        this.bTR.flush();
        try {
            ((dn) getService()).a(new da(fVar), z, strArr);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.f> fVar, int[] iArr, int i, boolean z) {
        this.bTR.flush();
        try {
            ((dn) getService()).a(new be(fVar), iArr, i, z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.f<com.google.android.gms.games.request.e> fVar, String[] strArr) {
        try {
            ((dn) getService()).a(new bm(fVar), strArr);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void a(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.f> vVar) {
        ((dn) getService()).a(new l(vVar), this.bTX);
    }

    public final void a(com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.j> vVar, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.h> vVar2, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.b> vVar3, com.google.android.gms.games.multiplayer.realtime.e eVar) {
        ((dn) getService()).a(new bo(vVar, vVar2, vVar3), this.bTW, eVar.getVariant(), eVar.aeL(), eVar.aeD(), false, this.bTX);
    }

    public final void a(com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.j> vVar, String str) {
        try {
            ((dn) getService()).a(new bo(vVar), str);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void a(Snapshot snapshot) {
        SnapshotContents snapshotContentsAfo = snapshot.afo();
        com.google.android.gms.common.internal.bf.a(!snapshotContentsAfo.isClosed(), "Snapshot already closed");
        Contents contentsAfp = snapshotContentsAfo.afp();
        snapshotContentsAfo.close();
        ((dn) getService()).a(contentsAfp);
    }

    public final void a(String str, com.google.android.gms.common.api.a.f<com.google.android.gms.games.f> fVar) {
        com.google.android.gms.common.internal.bf.d(str, (Object) "Please provide a valid serverClientId");
        try {
            ((dn) getService()).a(str, new de(fVar));
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final int adA() {
        try {
            return ((dn) getService()).adA();
        } catch (RemoteException e) {
            c(e);
            return -1;
        }
    }

    public final int adB() {
        return ((dn) getService()).adB();
    }

    public final int adC() {
        try {
            return adB();
        } catch (RemoteException e) {
            c(e);
            return -1;
        }
    }

    public final int adD() {
        return ((dn) getService()).adD();
    }

    public final int adE() {
        try {
            return adD();
        } catch (RemoteException e) {
            c(e);
            return -1;
        }
    }

    public final Intent adF() {
        return ((dn) getService()).adL();
    }

    public final Intent adG() {
        try {
            return adF();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final void adH() {
        ((dn) getService()).Q(this.bTX);
    }

    public final Player adi() {
        checkConnected();
        synchronized (this) {
            if (this.bTT == null) {
                com.google.android.gms.games.p pVar = new com.google.android.gms.games.p(((dn) getService()).adJ());
                try {
                    if (pVar.getCount() > 0) {
                        this.bTT = (PlayerEntity) ((Player) pVar.get(0)).freeze();
                    }
                    pVar.release();
                } catch (Throwable th) {
                    pVar.release();
                    throw th;
                }
            }
        }
        return this.bTT;
    }

    public final Player adj() {
        try {
            return adi();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Game adk() {
        checkConnected();
        synchronized (this) {
            if (this.bTU == null) {
                com.google.android.gms.games.a aVar = new com.google.android.gms.games.a(((dn) getService()).adK());
                try {
                    if (aVar.getCount() > 0) {
                        this.bTU = (GameEntity) ((Game) aVar.get(0)).freeze();
                    }
                    aVar.release();
                } catch (Throwable th) {
                    aVar.release();
                    throw th;
                }
            }
        }
        return this.bTU;
    }

    public final Game adl() {
        try {
            return adk();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent adm() {
        return ((dn) getService()).adm();
    }

    public final Intent adn() {
        try {
            return adm();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent ado() {
        try {
            return ((dn) getService()).ado();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent adp() {
        try {
            return ((dn) getService()).adp();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent adq() {
        try {
            return ((dn) getService()).adq();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final void adr() {
        try {
            zzac();
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void ads() {
        try {
            ((dn) getService()).zze(this.bTX);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void adt() {
        try {
            ((dn) getService()).P(this.bTX);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final Intent adu() {
        return ((dn) getService()).adu();
    }

    public final Intent adv() {
        try {
            return adu();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final int adw() {
        return ((dn) getService()).adw();
    }

    public final int adx() {
        try {
            return adw();
        } catch (RemoteException e) {
            c(e);
            return -1;
        }
    }

    public final Intent ady() {
        try {
            return ((dn) getService()).ady();
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final int adz() {
        try {
            return ((dn) getService()).adz();
        } catch (RemoteException e) {
            c(e);
            return -1;
        }
    }

    public final int b(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.realtime.d> vVar, byte[] bArr, String str, String str2) {
        try {
            return a(vVar, bArr, str, str2);
        } catch (RemoteException e) {
            c(e);
            return -1;
        }
    }

    public final Intent b(PlayerEntity playerEntity) {
        try {
            return a(playerEntity);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent b(Room room, int i) {
        try {
            return a(room, i);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent b(String str, boolean z, boolean z2, int i) {
        try {
            return a(str, z, z2, i);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<Status> fVar) {
        this.bTR.flush();
        try {
            ((dn) getService()).a(new bs(fVar));
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.c> fVar, int i) {
        try {
            ((dn) getService()).b((dj) new cn(fVar), i);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.d> fVar, String str) {
        try {
            ((dn) getService()).b(fVar == null ? null : new ck(fVar), str, this.bTV.bTO.bTM, this.bTV.bTO.adg());
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.d> fVar, String str, int i) {
        try {
            ((dn) getService()).b(fVar == null ? null : new ck(fVar), str, i, this.bTV.bTO.bTM, this.bTV.bTO.adg());
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.m> fVar, String str, int i, int i2, int i3, boolean z) {
        try {
            ((dn) getService()).b(new r(fVar), str, i, i2, i3, z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.e> fVar, String str, String str2) {
        this.bTR.flush();
        try {
            ((dn) getService()).b(new bc(fVar, str2), str, str2);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.k> fVar, String str, boolean z) {
        try {
            ((dn) getService()).a(new s(fVar), str, z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.k> fVar, boolean z) {
        try {
            ((dn) getService()).b(new s(fVar), z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.f> fVar, boolean z, String[] strArr) {
        this.bTR.flush();
        try {
            ((dn) getService()).a(new be(fVar), strArr, z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.f<com.google.android.gms.games.request.e> fVar, String[] strArr) {
        try {
            ((dn) getService()).b(new bm(fVar), strArr);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.f> vVar) {
        try {
            a(vVar);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void b(com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.j> vVar, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.h> vVar2, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.b> vVar3, com.google.android.gms.games.multiplayer.realtime.e eVar) {
        try {
            a(vVar, vVar2, vVar3, eVar);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void b(Snapshot snapshot) {
        try {
            a(snapshot);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void c(com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.d> fVar) {
        try {
            ((dn) getService()).c(new cp(fVar));
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void c(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.g> fVar, String str) {
        try {
            ((dn) getService()).b(new ca(fVar), str);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void c(com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.c> fVar, boolean z) {
        try {
            ((dn) getService()).a(new cl(fVar), z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void c(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.turnbased.b> vVar) {
        ((dn) getService()).b(new aj(vVar), this.bTX);
    }

    public final void c(com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.j> vVar, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.h> vVar2, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.b> vVar3, com.google.android.gms.games.multiplayer.realtime.e eVar) {
        ((dn) getService()).a((dj) new bo(vVar, vVar2, vVar3), (IBinder) this.bTW, eVar.getInvitationId(), false, this.bTX);
    }

    public final String cb(boolean z) {
        return this.bTT != null ? this.bTT.acq() : ((dn) getService()).adI();
    }

    public final String cc(boolean z) {
        try {
            return cb(true);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public void connect(com.google.android.gms.common.internal.i iVar) {
        this.bTT = null;
        this.bTU = null;
        super.connect(iVar);
    }

    @Override // com.google.android.gms.common.internal.e
    protected /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesService");
        return iInterfaceQueryLocalInterface instanceof dn ? (dn) iInterfaceQueryLocalInterface : new Cdo(iBinder);
    }

    public final void d(com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.f> fVar) {
        try {
            ((dn) getService()).d(new ct(fVar));
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void d(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.g> fVar, String str) {
        try {
            ((dn) getService()).c(new ca(fVar), str);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void d(com.google.android.gms.common.api.a.f<com.google.android.gms.games.event.c> fVar, boolean z) {
        this.bTR.flush();
        try {
            ((dn) getService()).e(new da(fVar), z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void d(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.turnbased.b> vVar) {
        try {
            c(vVar);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void d(com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.j> vVar, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.h> vVar2, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.b> vVar3, com.google.android.gms.games.multiplayer.realtime.e eVar) {
        try {
            c(vVar, vVar2, vVar3, eVar);
        } catch (RemoteException e) {
            c(e);
        }
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public void disconnect() {
        this.bTQ = false;
        if (isConnected()) {
            try {
                dn dnVar = (dn) getService();
                dnVar.zzbd();
                this.bTR.flush();
                dnVar.zza(this.bTX);
            } catch (RemoteException e) {
                di.ax("GamesClientImpl", "Failed to notify client disconnect.");
            }
        }
        super.disconnect();
    }

    public final int e(byte[] bArr, String str) {
        return ((dn) getService()).b(bArr, str, (String[]) null);
    }

    public final void e(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.h> fVar, String str) {
        try {
            ((dn) getService()).e(new cb(fVar), str);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void e(com.google.android.gms.common.api.a.f<com.google.android.gms.games.stats.c> fVar, boolean z) {
        try {
            ((dn) getService()).f(new ax(fVar), z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void e(com.google.android.gms.common.api.a.v<com.google.android.gms.games.quest.b> vVar) {
        try {
            ((dn) getService()).d(new bd(vVar), this.bTX);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final int f(byte[] bArr, String str) {
        try {
            return e(bArr, str);
        } catch (RemoteException e) {
            c(e);
            return -1;
        }
    }

    public final void f(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.f> fVar, String str) {
        try {
            ((dn) getService()).d(new bz(fVar), str);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void f(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.g> fVar, boolean z) {
        try {
            ((dn) getService()).d(new bw(fVar), z);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void f(com.google.android.gms.common.api.a.v<com.google.android.gms.games.request.b> vVar) {
        try {
            ((dn) getService()).c(new bh(vVar), this.bTX);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void g(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.i> fVar, String str) {
        try {
            ((dn) getService()).f(new cc(fVar), str);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void g(com.google.android.gms.common.api.a.v<com.google.android.gms.games.video.e> vVar) {
        ((dn) getService()).e(new cr(vVar), this.bTX);
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.internal.aa
    public Bundle getConnectionHint() {
        try {
            Bundle connectionHint = ((dn) getService()).getConnectionHint();
            if (connectionHint == null) {
                return connectionHint;
            }
            connectionHint.setClassLoader(i.class.getClassLoader());
            this.bUa = connectionHint;
            return connectionHint;
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    @Override // com.google.android.gms.common.internal.e
    protected Bundle getGetServiceRequestExtraArgs() {
        String string = getContext().getResources().getConfiguration().locale.toString();
        Bundle bundleAcm = this.bTY.acm();
        bundleAcm.putString("com.google.android.gms.games.key.gamePackageName", this.bTS);
        bundleAcm.putString("com.google.android.gms.games.key.desiredLocale", string);
        bundleAcm.putParcelable("com.google.android.gms.games.key.popupWindowToken", new BinderWrapper(this.bTV.bTO.bTM));
        bundleAcm.putInt("com.google.android.gms.games.key.API_VERSION", 6);
        bundleAcm.putBundle("com.google.android.gms.games.key.signInOptions", com.google.android.gms.signin.internal.k.a(getClientSettings()));
        return bundleAcm;
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.e
    protected String getServiceDescriptor() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    @Override // com.google.android.gms.common.internal.e
    protected String getStartServiceAction() {
        return "com.google.android.gms.games.service.START";
    }

    public final void h(com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.d> fVar, String str) {
        this.bTR.flush();
        try {
            ((dn) getService()).h(new ba(fVar), str);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final void h(com.google.android.gms.common.api.a.v<com.google.android.gms.games.video.e> vVar) {
        try {
            g(vVar);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void i(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.f> fVar, String str) {
        try {
            ((dn) getService()).g(new bu(fVar), str);
        } catch (SecurityException e) {
            a(fVar, e);
        }
    }

    public final Intent iw(String str) {
        try {
            return ((dn) getService()).iw(str);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent m(int[] iArr) {
        try {
            return ((dn) getService()).m(iArr);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final void ne(int i) {
        ((dn) getService()).ne(i);
    }

    public final Intent o(int i, int i2, boolean z) {
        return ((dn) getService()).o(i, i2, z);
    }

    @Override // com.google.android.gms.common.internal.e
    public /* synthetic */ void onConnectedLocked(IInterface iInterface) {
        dn dnVar = (dn) iInterface;
        super.onConnectedLocked(dnVar);
        if (this.bTQ) {
            this.bTV.YZ();
            this.bTQ = false;
        }
        if (this.bTY.bSk || this.bTY.bSr) {
            return;
        }
        try {
            dnVar.a(new az(this.bTV), this.bTX);
        } catch (RemoteException e) {
            c(e);
        }
    }

    @Override // com.google.android.gms.common.internal.e
    public void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
        this.bTQ = false;
    }

    @Override // com.google.android.gms.common.internal.e
    protected void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        if (i == 0 && bundle != null) {
            bundle.setClassLoader(i.class.getClassLoader());
            this.bTQ = bundle.getBoolean("show_welcome_popup");
            this.bTZ = this.bTQ;
            this.bTT = (PlayerEntity) bundle.getParcelable("com.google.android.gms.games.current_player");
            this.bTU = (GameEntity) bundle.getParcelable("com.google.android.gms.games.current_game");
        }
        super.onPostInitHandler(i, iBinder, bundle, i2);
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public void onUserSignOut(com.google.android.gms.common.internal.o oVar) {
        try {
            b(new dh(this, oVar));
        } catch (RemoteException e) {
            oVar.ZF();
        }
    }

    public final Intent p(int i, int i2, boolean z) {
        try {
            return o(i, i2, z);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final Intent q(int i, int i2, boolean z) {
        return ((dn) getService()).q(i, i2, z);
    }

    public final Intent r(int i, int i2, boolean z) {
        try {
            return q(i, i2, z);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public boolean requiresSignIn() {
        return true;
    }

    @Override // com.google.android.gms.common.internal.y
    protected Set<Scope> validateScopes(Set<Scope> set) {
        HashSet hashSet = new HashSet(set);
        boolean zContains = set.contains(com.google.android.gms.games.c.bGK);
        boolean zContains2 = set.contains(com.google.android.gms.games.c.bGJ);
        if (set.contains(com.google.android.gms.games.c.bRR)) {
            com.google.android.gms.common.internal.bf.a(!zContains, "Cannot have both %s and %s!", "https://www.googleapis.com/auth/games", "https://www.googleapis.com/auth/games.firstparty");
        } else {
            com.google.android.gms.common.internal.bf.a(zContains || zContains2, "Games APIs requires %s function.", "https://www.googleapis.com/auth/games_lite");
            if (zContains2 && zContains) {
                hashSet.remove(com.google.android.gms.games.c.bGJ);
            }
        }
        return hashSet;
    }

    public final Intent y(String str, int i, int i2) {
        try {
            return ((dn) getService()).z(str, i, i2);
        } catch (RemoteException e) {
            c(e);
            return null;
        }
    }

    public final void zza(String str, int i) {
        this.bTR.zza(str, i);
    }

    public final void zzab() {
        try {
            ZO();
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void zzac() {
        ((dn) getService()).O(this.bTX);
    }

    public final void zzb(String str) {
        ((dn) getService()).zzf(str);
    }

    public final void zzb(String str, int i) {
        ((dn) getService()).zzb(str, i);
    }

    public final void zzbd() {
        if (isConnected()) {
            try {
                ((dn) getService()).zzbd();
            } catch (RemoteException e) {
                c(e);
            }
        }
    }

    public final void zzc(String str) {
        try {
            zzb(str);
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void zze(String str) {
        try {
            ((dn) getService()).a(str, this.bTV.bTO.bTM, this.bTV.bTO.adg());
        } catch (RemoteException e) {
            c(e);
        }
    }

    public final void zzl(int i) {
        try {
            ne(i);
        } catch (RemoteException e) {
            c(e);
        }
    }
}
