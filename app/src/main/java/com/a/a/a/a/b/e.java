package com.a.a.a.a.b;

import com.facebook.internal.AnalyticsEvents;

/* JADX INFO: loaded from: classes.dex */
public enum e {
    HTML("html"),
    NATIVE(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE);

    private final String cdT;

    e(String str) {
        this.cdT = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.cdT;
    }
}
