package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public enum zzayd implements zzbbr {
    UNKNOWN_PREFIX(0),
    TINK(1),
    LEGACY(2),
    RAW(3),
    CRUNCHY(4),
    UNRECOGNIZED(-1);

    private static final zzbbs<zzayd> zzall = new zzbbs<zzayd>() { // from class: com.google.android.gms.internal.ads.zzaye
        @Override // com.google.android.gms.internal.ads.zzbbs
        public final /* synthetic */ zzbbr zzq(int i) {
            return zzayd.zzbg(i);
        }
    };
    private final int value;

    zzayd(int i) {
        this.value = i;
    }

    public static zzayd zzbg(int i) {
        switch (i) {
            case 0:
                return UNKNOWN_PREFIX;
            case 1:
                return TINK;
            case 2:
                return LEGACY;
            case 3:
                return RAW;
            case 4:
                return CRUNCHY;
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
