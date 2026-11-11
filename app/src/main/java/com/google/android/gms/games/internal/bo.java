package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;

/* JADX INFO: loaded from: classes.dex */
final class bo extends a {
    private final com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.j> bUL;
    private final com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.h> bUM;
    private final com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.b> bUN;

    public bo(com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.j> vVar) {
        this.bUL = (com.google.android.gms.common.api.a.v) com.google.android.gms.common.internal.bf.d(vVar, "Callbacks must not be null");
        this.bUM = null;
        this.bUN = null;
    }

    public bo(com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.j> vVar, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.h> vVar2, com.google.android.gms.common.api.a.v<? extends com.google.android.gms.games.multiplayer.realtime.b> vVar3) {
        this.bUL = (com.google.android.gms.common.api.a.v) com.google.android.gms.common.internal.bf.d(vVar, "Callbacks must not be null");
        this.bUM = vVar2;
        this.bUN = vVar3;
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void a(DataHolder dataHolder, String[] strArr) {
        if (this.bUM != null) {
            this.bUM.a(new at(dataHolder, strArr));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void a(RealTimeMessage realTimeMessage) {
        if (this.bUN != null) {
            this.bUN.a(new am(realTimeMessage));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void ab(int i, String str) {
        this.bUL.a(new u(i, str));
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void b(DataHolder dataHolder, String[] strArr) {
        if (this.bUM != null) {
            this.bUM.a(new au(dataHolder, strArr));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void c(DataHolder dataHolder, String[] strArr) {
        if (this.bUM != null) {
            this.bUM.a(new av(dataHolder, strArr));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void d(DataHolder dataHolder, String[] strArr) {
        if (this.bUM != null) {
            this.bUM.a(new ar(dataHolder, strArr));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void e(DataHolder dataHolder, String[] strArr) {
        if (this.bUM != null) {
            this.bUM.a(new aq(dataHolder, strArr));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void f(DataHolder dataHolder, String[] strArr) {
        if (this.bUM != null) {
            this.bUM.a(new as(dataHolder, strArr));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void iu(String str) {
        if (this.bUM != null) {
            this.bUM.a(new ao(str));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void iv(String str) {
        if (this.bUM != null) {
            this.bUM.a(new ap(str));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void s(DataHolder dataHolder) {
        this.bUL.a(new br(dataHolder));
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void t(DataHolder dataHolder) {
        this.bUL.a(new p(dataHolder));
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void u(DataHolder dataHolder) {
        if (this.bUM != null) {
            this.bUM.a(new bq(dataHolder));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void v(DataHolder dataHolder) {
        if (this.bUM != null) {
            this.bUM.a(new bn(dataHolder));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void w(DataHolder dataHolder) {
        this.bUL.a(new bp(dataHolder));
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void x(DataHolder dataHolder) {
        if (this.bUM != null) {
            this.bUM.a(new cx(dataHolder));
        }
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void y(DataHolder dataHolder) {
        if (this.bUM != null) {
            this.bUM.a(new cz(dataHolder));
        }
    }
}
