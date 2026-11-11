package com.google.android.gms.common.api.a;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public interface bu {
    void ZD();

    <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t);

    boolean a(ab abVar);

    <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t);

    void connect();

    void disconnect();

    void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    boolean isConnected();
}
