package com.a.a.a.a.b;

import com.facebook.internal.AnalyticsEvents;

/* JADX INFO: loaded from: classes.dex */
public enum f {
    NATIVE(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE),
    JAVASCRIPT("javascript"),
    NONE("none");

    private final String cdY;

    f(String str) {
        this.cdY = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.cdY;
    }
}
