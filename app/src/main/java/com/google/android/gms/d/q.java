package com.google.android.gms.d;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
final class q implements d {
    q() {
    }

    @Override // com.google.android.gms.d.d
    public final f a(Context context, String str, e eVar) {
        f fVar = new f();
        fVar.bRz = eVar.D(context, str);
        if (fVar.bRz != 0) {
            fVar.bRB = -1;
        } else {
            fVar.bRA = eVar.a(context, str, true);
            if (fVar.bRA != 0) {
                fVar.bRB = 1;
            }
        }
        return fVar;
    }
}
