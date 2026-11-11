package com.google.android.gms.common.server.response;

import java.io.BufferedReader;

/* JADX INFO: loaded from: classes.dex */
final class l implements d<Long> {
    l() {
    }

    @Override // com.google.android.gms.common.server.response.d
    public final /* synthetic */ Long h(b bVar, BufferedReader bufferedReader) {
        return Long.valueOf(bVar.e(bufferedReader));
    }
}
