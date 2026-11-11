package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public enum zzawy implements zzbbr {
    UNKNOWN_CURVE(0),
    NIST_P224(1),
    NIST_P256(2),
    NIST_P384(3),
    NIST_P521(4),
    UNRECOGNIZED(-1);

    private static final zzbbs<zzawy> zzall = new zzbbs<zzawy>() { // from class: com.google.android.gms.internal.ads.zzawz
        @Override // com.google.android.gms.internal.ads.zzbbs
        public final /* synthetic */ zzbbr zzq(int i) {
            return zzawy.zzat(i);
        }
    };
    private final int value;

    zzawy(int i) {
        this.value = i;
    }

    public static zzawy zzat(int i) {
        switch (i) {
            case 0:
                return UNKNOWN_CURVE;
            case 1:
                return NIST_P224;
            case 2:
                return NIST_P256;
            case 3:
                return NIST_P384;
            case 4:
                return NIST_P521;
            default:
                return null;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbbr
    public final int zzhq() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }
}
