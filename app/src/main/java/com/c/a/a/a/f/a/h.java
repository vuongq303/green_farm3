package com.c.a.a.a.f.a;

import com.facebook.internal.ServerProtocol;

/* JADX INFO: compiled from: MediaType.java */
/* JADX INFO: loaded from: classes.dex */
public enum h {
    DISPLAY(ServerProtocol.DIALOG_PARAM_DISPLAY),
    VIDEO("video");

    private final String value;

    h(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }
}
