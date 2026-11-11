package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.a.bt;
import com.google.android.gms.common.api.a.cc;
import com.google.android.gms.common.api.a.cv;
import com.google.android.gms.common.api.e;
import com.google.android.gms.common.internal.bf;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class t<O extends e> {
    private final a<O> mApi;
    private final Context mContext;
    private final int mId;
    private final O zzcl;
    private final cv<O> zzcm;
    private final Looper zzcn;
    private final w zzco;
    private final com.google.android.gms.common.api.a.ac zzcp;
    protected final com.google.android.gms.common.api.a.l zzcq;

    @Deprecated
    public t(Activity activity, a<O> aVar, O o, com.google.android.gms.common.api.a.ac acVar) {
        this(activity, (a) aVar, (e) o, new v().a(acVar).a(activity.getMainLooper()).YL());
    }

    public t(Activity activity, a<O> aVar, O o, u uVar) {
        bf.d(activity, "Null activity is not permitted.");
        bf.d(aVar, "Api must not be null.");
        bf.d(uVar, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = activity.getApplicationContext();
        this.mApi = aVar;
        this.zzcl = o;
        this.zzcn = uVar.bIx;
        this.zzcm = cv.a(this.mApi, this.zzcl);
        this.zzco = new bt(this);
        this.zzcq = com.google.android.gms.common.api.a.l.aC(this.mContext);
        this.mId = this.zzcq.YW();
        this.zzcp = uVar.bIw;
        com.google.android.gms.common.api.a.ak.a(activity, this.zzcq, this.zzcm);
        this.zzcq.b((t<?>) this);
    }

    protected t(Context context, a<O> aVar, Looper looper) {
        bf.d(context, "Null context is not permitted.");
        bf.d(aVar, "Api must not be null.");
        bf.d(looper, "Looper must not be null.");
        this.mContext = context.getApplicationContext();
        this.mApi = aVar;
        this.zzcl = null;
        this.zzcn = looper;
        this.zzcm = cv.c(aVar);
        this.zzco = new bt(this);
        this.zzcq = com.google.android.gms.common.api.a.l.aC(this.mContext);
        this.mId = this.zzcq.YW();
        this.zzcp = new com.google.android.gms.common.api.a.a();
    }

    @Deprecated
    public t(Context context, a<O> aVar, O o, Looper looper, com.google.android.gms.common.api.a.ac acVar) {
        this(context, aVar, o, new v().a(looper).a(acVar).YL());
    }

    @Deprecated
    public t(Context context, a<O> aVar, O o, com.google.android.gms.common.api.a.ac acVar) {
        this(context, aVar, o, new v().a(acVar).YL());
    }

    public t(Context context, a<O> aVar, O o, u uVar) {
        bf.d(context, "Null context is not permitted.");
        bf.d(aVar, "Api must not be null.");
        bf.d(uVar, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = context.getApplicationContext();
        this.mApi = aVar;
        this.zzcl = o;
        this.zzcn = uVar.bIx;
        this.zzcm = cv.a(this.mApi, this.zzcl);
        this.zzco = new bt(this);
        this.zzcq = com.google.android.gms.common.api.a.l.aC(this.mContext);
        this.mId = this.zzcq.YW();
        this.zzcp = uVar.bIw;
        this.zzcq.b((t<?>) this);
    }

    private final <A extends c, T extends com.google.android.gms.common.api.a.e<? extends ae, A>> T zza(int i, T t) {
        t.zzx();
        this.zzcq.a(this, i, (com.google.android.gms.common.api.a.e<? extends ae, c>) t);
        return t;
    }

    private final <TResult, A extends c> com.google.android.gms.f.f<TResult> zza(int i, com.google.android.gms.common.api.a.ad<A, TResult> adVar) {
        com.google.android.gms.f.g gVar = new com.google.android.gms.f.g();
        this.zzcq.a(this, i, adVar, gVar, this.zzcp);
        return gVar.ZK();
    }

    public w asGoogleApiClient() {
        return this.zzco;
    }

    protected com.google.android.gms.common.internal.s createClientSettingsBuilder() {
        Account account;
        GoogleSignInAccount googleSignInAccountYq;
        GoogleSignInAccount googleSignInAccountYq2;
        com.google.android.gms.common.internal.s sVar = new com.google.android.gms.common.internal.s();
        if (!(this.zzcl instanceof g) || (googleSignInAccountYq2 = ((g) this.zzcl).Yq()) == null) {
            account = this.zzcl instanceof f ? ((f) this.zzcl).getAccount() : null;
        } else {
            account = googleSignInAccountYq2.getAccount();
        }
        return sVar.b(account).c((!(this.zzcl instanceof g) || (googleSignInAccountYq = ((g) this.zzcl).Yq()) == null) ? Collections.emptySet() : googleSignInAccountYq.Ye()).hY(this.mContext.getClass().getName()).hX(this.mContext.getPackageName());
    }

    protected com.google.android.gms.f.f<Boolean> disconnectService() {
        return this.zzcq.d((t<?>) this);
    }

    public <A extends c, T extends com.google.android.gms.common.api.a.e<? extends ae, A>> T doBestEffortWrite(T t) {
        return (T) zza(2, t);
    }

    public <TResult, A extends c> com.google.android.gms.f.f<TResult> doBestEffortWrite(com.google.android.gms.common.api.a.ad<A, TResult> adVar) {
        return zza(2, adVar);
    }

    public <A extends c, T extends com.google.android.gms.common.api.a.e<? extends ae, A>> T doRead(T t) {
        return (T) zza(0, t);
    }

    public <TResult, A extends c> com.google.android.gms.f.f<TResult> doRead(com.google.android.gms.common.api.a.ad<A, TResult> adVar) {
        return zza(0, adVar);
    }

    public <A extends c, T extends com.google.android.gms.common.api.a.aa<A, ?>, U extends com.google.android.gms.common.api.a.ag<A, ?>> com.google.android.gms.f.f<Void> doRegisterEventListener(T t, U u) {
        bf.checkNotNull(t);
        bf.checkNotNull(u);
        bf.d(t.Zo(), "Listener has already been released.");
        bf.d(u.Zo(), "Listener has already been released.");
        bf.b(t.Zo().equals(u.Zo()), "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
        return this.zzcq.a(this, (com.google.android.gms.common.api.a.aa<c, ?>) t, (com.google.android.gms.common.api.a.ag<c, ?>) u);
    }

    public com.google.android.gms.f.f<Boolean> doUnregisterEventListener(com.google.android.gms.common.api.a.w<?> wVar) {
        bf.d(wVar, "Listener key cannot be null.");
        return this.zzcq.a(this, wVar);
    }

    public <A extends c, T extends com.google.android.gms.common.api.a.e<? extends ae, A>> T doWrite(T t) {
        return (T) zza(1, t);
    }

    public <TResult, A extends c> com.google.android.gms.f.f<TResult> doWrite(com.google.android.gms.common.api.a.ad<A, TResult> adVar) {
        return zza(1, adVar);
    }

    public final a<O> getApi() {
        return this.mApi;
    }

    public O getApiOptions() {
        return this.zzcl;
    }

    public Context getApplicationContext() {
        return this.mContext;
    }

    public final int getInstanceId() {
        return this.mId;
    }

    public Looper getLooper() {
        return this.zzcn;
    }

    public <L> com.google.android.gms.common.api.a.v<L> registerListener(L l, String str) {
        return com.google.android.gms.common.api.a.z.b(l, this.zzcn, str);
    }

    public cc zza(Context context, Handler handler) {
        return new cc(context, handler, createClientSettingsBuilder().aan());
    }

    public l zza(Looper looper, com.google.android.gms.common.api.a.m<O> mVar) {
        return this.mApi.YJ().a(this.mContext, looper, createClientSettingsBuilder().aan(), this.zzcl, mVar, mVar);
    }

    public final cv<O> zzm() {
        return this.zzcm;
    }
}
