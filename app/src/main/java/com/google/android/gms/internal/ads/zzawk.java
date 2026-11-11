package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public enum zzawk implements zzbbr {
    UNKNOWN_FORMAT(0),
    UNCOMPRESSED(1),
    COMPRESSED(2),
    UNRECOGNIZED(-1);

    private static final zzbbs<zzawk> zzall = new zzbbs<zzawk>() { // from class: com.google.android.gms.internal.ads.zzawl
        @Override // com.google.android.gms.internal.ads.zzbbs
        public final /* synthetic */ zzbbr zzq(int i) {
            return zzawk.zzaq(i);
        }
    };
    private final int value;

    zzawk(int i) {
        this.value = i;
    }

    public static zzawk zzaq(int i) {
        switch (i) {
            case 0:
                return UNKNOWN_FORMAT;
            case 1:
                return UNCOMPRESSED;
            case 2:
                return COMPRESSED;
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
