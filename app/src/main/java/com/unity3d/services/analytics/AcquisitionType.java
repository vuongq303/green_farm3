package com.unity3d.services.analytics;

/* JADX INFO: loaded from: classes.dex */
public enum AcquisitionType {
    SOFT,
    PREMIUM;

    @Override // java.lang.Enum
    public String toString() {
        switch (this) {
            case SOFT:
                return "soft";
            case PREMIUM:
                return "premium";
            default:
                return "";
        }
    }
}
