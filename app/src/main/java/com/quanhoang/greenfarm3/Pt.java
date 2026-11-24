package com.quanhoang.greenfarm3;

import com.facebook.appevents.codeless.internal.Constants;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class Pt {
    private static Pt ET = null;
    private boolean EU = false;
    private byte[] EV = {82, 30, 86, 55, 83, 24, 79, 78, 45, 50, 52, 48, 56};
    private byte[] EW = {0, 2, 38, -68};
    private byte[] EX = {46, 100, 101, 120};
    private byte[] EY = {114, 101, 115, 53, 49, 50, 51};
    private int EZ = Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
    public final int Fa = 276856832;

    public static Pt hb() {
        if (ET == null) {
            ET = new Pt();
        }
        return ET;
    }

    private Pt() {
    }

    public void init() {
        if (!this.EU) {
            this.EU = true;
            this.EV[1] = 69;
            this.EV[3] = 73;
            this.EV[5] = 73;
            new Thread(new bh(this)).start();
        }
    }

    public int a(byte[] bArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        for (int i = 0; i < bArr.length; i++) {
            byteBufferAllocate.put(i, bArr[i]);
        }
        return byteBufferAllocate.getInt();
    }

    public String hc() {
        return new String(this.EY);
    }
}
