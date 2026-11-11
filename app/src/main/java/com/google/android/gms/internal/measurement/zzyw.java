package com.google.android.gms.internal.measurement;

import com.gameloft.glads.AndroidWebView;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public final class zzyw {
    public static final byte[] zzbvc;
    private static final ByteBuffer zzbvd;
    private static final zzyj zzbve;
    static final Charset UTF_8 = Charset.forName(AndroidWebView.UTF_8);
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    static {
        byte[] bArr = new byte[0];
        zzbvc = bArr;
        zzbvd = ByteBuffer.wrap(bArr);
        byte[] bArr2 = zzbvc;
        zzbve = zzyj.zza(bArr2, 0, bArr2.length, false);
    }

    static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static <T> T zza(T t, String str) {
        if (t == null) {
            throw new NullPointerException(str);
        }
        return t;
    }
}
