package com.google.android.gms.auth.api;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.m;
import com.google.android.gms.internal.auth.zzao;
import com.google.android.gms.internal.auth.zzax;
import com.google.android.gms.internal.auth.zzbn;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final m<zzax> bFa = new m<>();
    public static final m<com.google.android.gms.auth.api.signin.internal.g> bFb = new m<>();
    private static final com.google.android.gms.common.api.b<zzax, b> bFc = new d();
    private static final com.google.android.gms.common.api.b<com.google.android.gms.auth.api.signin.internal.g, GoogleSignInOptions> bFd = new e();
    public static final com.google.android.gms.common.api.a<h> bFe = f.API;
    public static final com.google.android.gms.common.api.a<b> bFf = new com.google.android.gms.common.api.a<>("Auth.CREDENTIALS_API", bFc, bFa);
    public static final com.google.android.gms.common.api.a<GoogleSignInOptions> bFg = new com.google.android.gms.common.api.a<>("Auth.GOOGLE_SIGN_IN_API", bFd, bFb);
    public static final com.google.android.gms.auth.api.proxy.a bFh = new zzbn();
    public static final com.google.android.gms.auth.api.credentials.c bFi = new zzao();
    public static final com.google.android.gms.auth.api.signin.b bFj = new com.google.android.gms.auth.api.signin.internal.f();
}
