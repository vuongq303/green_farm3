package com.google.android.gms.common.api;

/* JADX INFO: loaded from: classes.dex */
public class q extends Exception {
    protected final Status mStatus;

    /* JADX WARN: Illegal instructions before constructor call */
    public q(Status status) {
        int statusCode = status.getStatusCode();
        String strYR = status.YR() != null ? status.YR() : "";
        super(new StringBuilder(String.valueOf(strYR).length() + 13).append(statusCode).append(": ").append(strYR).toString());
        this.mStatus = status;
    }
}
