package com.gameloft.android2d.iap.utils;

import com.facebook.internal.Utility;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/* JADX INFO: compiled from: GZip.java */
/* JADX INFO: loaded from: classes.dex */
public class f {
    public static String m(byte[] bArr) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream, Utility.DEFAULT_STREAM_BUFFER_SIZE);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[Utility.DEFAULT_STREAM_BUFFER_SIZE];
        int i = 0;
        while (i != -1) {
            i = gZIPInputStream.read(bArr2, 0, Utility.DEFAULT_STREAM_BUFFER_SIZE);
            if (i != -1) {
                byteArrayOutputStream.write(bArr2, 0, i);
            }
        }
        gZIPInputStream.close();
        byteArrayInputStream.close();
        return byteArrayOutputStream.toString();
    }
}
