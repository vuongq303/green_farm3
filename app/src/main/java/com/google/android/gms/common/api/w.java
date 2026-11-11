package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.a.ck;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public abstract class w {

    @GuardedBy("sAllClients")
    private static final Set<w> bIy = Collections.newSetFromMap(new WeakHashMap());

    public static Set<w> YM() {
        Set<w> set;
        synchronized (bIy) {
            set = bIy;
        }
        return set;
    }

    public <A extends c, R extends ae, T extends com.google.android.gms.common.api.a.e<R, A>> T a(T t) {
        throw new UnsupportedOperationException();
    }

    public <C extends l> C a(d<C> dVar) {
        throw new UnsupportedOperationException();
    }

    public void a(ck ckVar) {
        throw new UnsupportedOperationException();
    }

    public abstract void a(z zVar);

    public boolean a(com.google.android.gms.common.api.a.ab abVar) {
        throw new UnsupportedOperationException();
    }

    public boolean a(a<?> aVar) {
        throw new UnsupportedOperationException();
    }

    public <A extends c, T extends com.google.android.gms.common.api.a.e<? extends ae, A>> T b(T t) {
        throw new UnsupportedOperationException();
    }

    public void b(ck ckVar) {
        throw new UnsupportedOperationException();
    }

    public abstract void b(z zVar);

    public abstract boolean b(a<?> aVar);

    public abstract void connect();

    public abstract void disconnect();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean isConnected();

    public void ny(int i) {
        throw new UnsupportedOperationException();
    }

    public <L> com.google.android.gms.common.api.a.v<L> y(L l) {
        throw new UnsupportedOperationException();
    }
}
