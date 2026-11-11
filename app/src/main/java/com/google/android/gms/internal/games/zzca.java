package com.google.android.gms.internal.games;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.b;
import com.google.android.gms.games.request.c;
import com.google.android.gms.games.request.d;
import com.google.android.gms.games.request.e;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzca implements c {
    public final aa<e> acceptRequest(w wVar, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        return acceptRequests(wVar, arrayList);
    }

    public final aa<e> acceptRequests(w wVar, List<String> list) {
        return wVar.b(new zzcb(this, wVar, list == null ? null : (String[]) list.toArray(new String[list.size()])));
    }

    public final aa<e> dismissRequest(w wVar, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        return dismissRequests(wVar, arrayList);
    }

    public final aa<e> dismissRequests(w wVar, List<String> list) {
        return wVar.b(new zzcc(this, wVar, list == null ? null : (String[]) list.toArray(new String[list.size()])));
    }

    @Override // com.google.android.gms.games.request.c
    public final ArrayList<GameRequest> getGameRequestsFromBundle(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("requests")) {
            return new ArrayList<>();
        }
        ArrayList arrayList = (ArrayList) bundle.get("requests");
        ArrayList<GameRequest> arrayList2 = new ArrayList<>();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList2.add((GameRequest) arrayList.get(i));
        }
        return arrayList2;
    }

    public final ArrayList<GameRequest> getGameRequestsFromInboxResponse(Intent intent) {
        return intent == null ? new ArrayList<>() : getGameRequestsFromBundle(intent.getExtras());
    }

    public final Intent getInboxIntent(w wVar) {
        return com.google.android.gms.games.c.a(wVar).ady();
    }

    public final int getMaxLifetimeDays(w wVar) {
        return com.google.android.gms.games.c.a(wVar).adA();
    }

    public final int getMaxPayloadSize(w wVar) {
        return com.google.android.gms.games.c.a(wVar).adz();
    }

    public final Intent getSendIntent(w wVar, int i, byte[] bArr, int i2, Bitmap bitmap, String str) {
        return com.google.android.gms.games.c.a(wVar).a(i, bArr, i2, bitmap, str);
    }

    public final aa<d> loadRequests(w wVar, int i, int i2, int i3) {
        return wVar.a(new zzcd(this, wVar, i, i2, i3));
    }

    public final void registerRequestListener(w wVar, b bVar) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.f(wVar.y(bVar));
        }
    }

    public final void unregisterRequestListener(w wVar) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.adt();
        }
    }
}
