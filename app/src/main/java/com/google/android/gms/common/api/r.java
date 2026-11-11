package com.google.android.gms.common.api;

import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.a.cv;
import com.google.android.gms.common.internal.bf;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class r extends Exception {
    private final android.support.v4.e.a<cv<?>, ConnectionResult> bIu;

    public r(android.support.v4.e.a<cv<?>, ConnectionResult> aVar) {
        this.bIu = aVar;
    }

    public final android.support.v4.e.a<cv<?>, ConnectionResult> YK() {
        return this.bIu;
    }

    public ConnectionResult a(t<? extends e> tVar) {
        Object objZzm = tVar.zzm();
        bf.b(this.bIu.get(objZzm) != null, "The given API was not part of the availability request.");
        return this.bIu.get(objZzm);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (cv<?> cvVar : this.bIu.keySet()) {
            ConnectionResult connectionResult = this.bIu.get(cvVar);
            if (connectionResult.Sp()) {
                z = false;
            }
            String strZI = cvVar.ZI();
            String strValueOf = String.valueOf(connectionResult);
            arrayList.add(new StringBuilder(String.valueOf(strZI).length() + 2 + String.valueOf(strValueOf).length()).append(strZI).append(": ").append(strValueOf).toString());
        }
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append("None of the queried APIs are available. ");
        } else {
            sb.append("Some of the queried APIs are unavailable. ");
        }
        sb.append(TextUtils.join("; ", arrayList));
        return sb.toString();
    }
}
