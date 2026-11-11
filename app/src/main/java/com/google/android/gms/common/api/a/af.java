package com.google.android.gms.common.api.a;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public class af {
    public static void a(Status status, com.google.android.gms.f.g<Void> gVar) {
        a(status, null, gVar);
    }

    public static <TResult> void a(Status status, TResult tresult, com.google.android.gms.f.g<TResult> gVar) {
        if (status.Sp()) {
            gVar.setResult(tresult);
        } else {
            gVar.g(new com.google.android.gms.common.api.q(status));
        }
    }
}
