package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzaz {
    public static int zzd(int i) {
        if ((i < 0 || i > 2) && (i < 1000 || i > 1000)) {
            throw new IllegalArgumentException(new StringBuilder(43).append(i).append(" is not a valid enum EnumBoolean").toString());
        }
        return i;
    }

    public static int zze(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException(new StringBuilder(41).append(i).append(" is not a valid enum ProtoName").toString());
        }
        return i;
    }

    public static int zzf(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException(new StringBuilder(48).append(i).append(" is not a valid enum EncryptionMethod").toString());
        }
        return i;
    }
}
