package com.google.android.gms.auth.api.a;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.a.ac;
import com.google.android.gms.common.api.e;
import com.google.android.gms.common.api.m;
import com.google.android.gms.common.api.t;
import com.google.android.gms.f.f;
import com.google.android.gms.internal.p001authapiphone.zzi;

/* JADX INFO: loaded from: classes.dex */
public abstract class a extends t<Object> {
    private static final m<zzi> CLIENT_KEY = new m<>();
    private static final com.google.android.gms.common.api.b<zzi, Object> CLIENT_BUILDER = new b();
    private static final com.google.android.gms.common.api.a<Object> API = new com.google.android.gms.common.api.a<>("SmsRetriever.API", CLIENT_BUILDER, CLIENT_KEY);

    public a(Activity activity) {
        super(activity, (com.google.android.gms.common.api.a<e>) API, (e) null, (ac) new com.google.android.gms.common.api.a.a());
    }

    public a(Context context) {
        super(context, (com.google.android.gms.common.api.a<e>) API, (e) null, (ac) new com.google.android.gms.common.api.a.a());
    }

    public abstract f<Void> startSmsRetriever();
}
