package com.google.android.gms.internal.auth;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.b;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.d;

/* JADX INFO: loaded from: classes.dex */
public final class zzaw {
    public static PendingIntent zzd(Context context, b bVar, HintRequest hintRequest) {
        bf.d(context, "context must not be null");
        bf.d(hintRequest, "request must not be null");
        if (bVar == null) {
        }
        PasswordSpecification passwordSpecificationXB = (bVar == null || bVar.XB() == null) ? PasswordSpecification.bFY : bVar.XB();
        Intent intentPutExtra = new Intent("com.google.android.gms.auth.api.credentials.PICKER").putExtra("com.google.android.gms.credentials.hintRequestVersion", 2).putExtra("com.google.android.gms.credentials.RequestType", "Hints").putExtra("com.google.android.gms.credentials.ClaimedCallingPackage", (String) null);
        d.a(passwordSpecificationXB, intentPutExtra, "com.google.android.gms.credentials.PasswordSpecification");
        d.a(hintRequest, intentPutExtra, "com.google.android.gms.credentials.HintRequest");
        return PendingIntent.getActivity(context, 2000, intentPutExtra, 134217728);
    }
}
