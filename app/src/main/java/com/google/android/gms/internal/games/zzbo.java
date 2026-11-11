package com.google.android.gms.internal.games;

import android.content.Intent;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.quest.b;
import com.google.android.gms.games.quest.c;
import com.google.android.gms.games.quest.d;
import com.google.android.gms.games.quest.e;
import com.google.android.gms.games.quest.f;

/* JADX INFO: loaded from: classes.dex */
public final class zzbo implements c {
    public final aa<d> accept(w wVar, String str) {
        return wVar.b(new zzbp(this, wVar, str));
    }

    public final aa<e> claim(w wVar, String str, String str2) {
        return wVar.b(new zzbq(this, wVar, str, str2));
    }

    public final Intent getQuestIntent(w wVar, String str) {
        return com.google.android.gms.games.c.a(wVar).iw(str);
    }

    public final Intent getQuestsIntent(w wVar, int[] iArr) {
        return com.google.android.gms.games.c.a(wVar).m(iArr);
    }

    public final aa<f> load(w wVar, int[] iArr, int i, boolean z) {
        return wVar.a(new zzbr(this, wVar, iArr, i, z));
    }

    public final aa<f> loadByIds(w wVar, boolean z, String... strArr) {
        return wVar.a(new zzbs(this, wVar, z, strArr));
    }

    public final void registerQuestUpdateListener(w wVar, b bVar) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.e(wVar.y(bVar));
        }
    }

    public final void showStateChangedPopup(w wVar, String str) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.zze(str);
        }
    }

    public final void unregisterQuestUpdateListener(w wVar) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.ads();
        }
    }
}
