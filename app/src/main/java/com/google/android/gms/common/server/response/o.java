package com.google.android.gms.common.server.response;

import java.io.BufferedReader;

/* JADX INFO: loaded from: classes.dex */
final class o implements d<Boolean> {
    o() {
    }

    @Override // com.google.android.gms.common.server.response.d
    public final /* synthetic */ Boolean h(b bVar, BufferedReader bufferedReader) {
        return Boolean.valueOf(bVar.a(bufferedReader, false));
    }
}
