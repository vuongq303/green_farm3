package com.google.android.gms.signin;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.m;
import com.google.android.gms.signin.internal.k;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final m<k> CLIENT_KEY = new m<>();
    public static final m<k> bYg = new m<>();
    public static final com.google.android.gms.common.api.b<k, d> CLIENT_BUILDER = new f();
    private static final com.google.android.gms.common.api.b<k, b> bYh = new g();
    public static final Scope bGG = new Scope("profile");
    public static final Scope bGH = new Scope("email");
    public static final com.google.android.gms.common.api.a<d> API = new com.google.android.gms.common.api.a<>("SignIn.API", CLIENT_BUILDER, CLIENT_KEY);
    public static final com.google.android.gms.common.api.a<b> bYi = new com.google.android.gms.common.api.a<>("SignIn.INTERNAL_API", bYh, bYg);
}
