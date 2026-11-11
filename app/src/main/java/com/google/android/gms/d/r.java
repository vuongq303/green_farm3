package com.google.android.gms.d;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
final class r implements d {
    r() {
    }

    @Override // com.google.android.gms.d.d
    public final f a(Context context, String str, e eVar) {
        f fVar = new f();
        fVar.bRz = eVar.D(context, str);
        fVar.bRA = eVar.a(context, str, true);
        if (fVar.bRz == 0 && fVar.bRA == 0) {
            fVar.bRB = 0;
        } else if (fVar.bRz >= fVar.bRA) {
            fVar.bRB = -1;
        } else {
            fVar.bRB = 1;
        }
        return fVar;
    }
}
