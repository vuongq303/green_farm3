package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public enum zzaxl implements zzbbr {
    UNKNOWN_STATUS(0),
    ENABLED(1),
    DISABLED(2),
    DESTROYED(3),
    UNRECOGNIZED(-1);

    private static final zzbbs<zzaxl> zzall = new zzbbs<zzaxl>() { // from class: com.google.android.gms.internal.ads.zzaxm
        @Override // com.google.android.gms.internal.ads.zzbbs
        public final /* synthetic */ zzbbr zzq(int i) {
            return zzaxl.zzax(i);
        }
    };
    private final int value;

    zzaxl(int i) {
        this.value = i;
    }

    public static zzaxl zzax(int i) {
        switch (i) {
            case 0:
                return UNKNOWN_STATUS;
            case 1:
                return ENABLED;
            case 2:
                return DISABLED;
            case 3:
                return DESTROYED;
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
