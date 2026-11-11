package com.c.a.a.a.f.a;

import com.facebook.internal.ServerProtocol;

/* JADX INFO: compiled from: SessionType.java */
/* JADX INFO: loaded from: classes.dex */
public enum j {
    DISPLAY(ServerProtocol.DIALOG_PARAM_DISPLAY),
    VIDEO("video"),
    MANAGED_DISPLAY("managedDisplay"),
    MANAGED_VIDEO("managedVideo");

    private final String value;

    j(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }
}
