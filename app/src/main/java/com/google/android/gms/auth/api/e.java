package com.google.android.gms.auth.api;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.l;
import com.google.android.gms.common.api.y;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.r;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class e extends com.google.android.gms.common.api.b<com.google.android.gms.auth.api.signin.internal.g, GoogleSignInOptions> {
    e() {
    }

    @Override // com.google.android.gms.common.api.b
    public final /* synthetic */ l a(Context context, Looper looper, r rVar, GoogleSignInOptions googleSignInOptions, y yVar, z zVar) {
        return new com.google.android.gms.auth.api.signin.internal.g(context, looper, rVar, googleSignInOptions, yVar, zVar);
    }

    @Override // com.google.android.gms.common.api.k
    public final /* synthetic */ List v(Object obj) {
        GoogleSignInOptions googleSignInOptions = (GoogleSignInOptions) obj;
        return googleSignInOptions == null ? Collections.emptyList() : googleSignInOptions.Yh();
    }
}
