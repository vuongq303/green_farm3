package com.quanhoang.greenfarm3;

import com.gameloft.glads.AndroidWebView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: NetworkByteOrderStream.java */
/* JADX INFO: loaded from: classes.dex */
public class be {
    public static ByteArrayOutputStream EK = null;

    public be(ByteArrayOutputStream byteArrayOutputStream) {
        EK = byteArrayOutputStream;
    }

    public static byte a(bf bfVar) {
        return (byte) (((bfVar.EL << 3) | (bfVar.EM - 1)) & 255);
    }

    public static void a(int i, ByteArrayOutputStream byteArrayOutputStream) {
        boolean z;
        int i2;
        if (i == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        if (i == Integer.MIN_VALUE) {
            byteArrayOutputStream.write(64);
            return;
        }
        if (i < 0) {
            i = -i;
            z = true;
        } else {
            z = false;
        }
        boolean z2 = true;
        while (i > 0) {
            if (z2) {
                byte b = (byte) (i & 63);
                i2 = i >> 6;
                if (z) {
                    b = (byte) (b | 64);
                }
                if (i2 > 0) {
                    b = (byte) (b | 128);
                }
                byteArrayOutputStream.write(b);
            } else {
                byte b2 = (byte) (i & 127);
                i2 = i >> 7;
                if (i2 > 0) {
                    b2 = (byte) (b2 | 128);
                }
                byteArrayOutputStream.write(b2);
            }
            int i3 = i2;
            z2 = false;
            i = i3;
        }
    }

    public static void b(int i, ByteArrayOutputStream byteArrayOutputStream) {
        if (i == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        while (i > 0) {
            byte b = (byte) (i & 127);
            i >>= 7;
            if (i > 0) {
                b = (byte) (b | 128);
            }
            byteArrayOutputStream.write(b);
        }
    }

    public void a(byte b) {
        EK.write(b);
    }

    public void ba(int i) {
        bf bfVar = new bf();
        bfVar.EL = i;
        bfVar.EM = 4;
        a(a(bfVar));
    }

    public void f(int i, boolean z) {
        bf bfVar = new bf();
        if (z) {
            bfVar.EL = 8;
        } else {
            bfVar.EL = 7;
        }
        bfVar.EM = 4;
        a(a(bfVar));
        if (z) {
            b(i, EK);
        } else {
            a(i, EK);
        }
    }

    public void f(float f) {
        bf bfVar = new bf();
        bfVar.EL = 5;
        bfVar.EM = 4;
        a(a(bfVar));
        a(f, EK);
    }

    public void a(double d) {
        bf bfVar = new bf();
        bfVar.EL = 16;
        bfVar.EM = 8;
        a(a(bfVar));
        a(d, EK);
    }

    public void a(float f, ByteArrayOutputStream byteArrayOutputStream) {
        try {
            byteArrayOutputStream.write(ByteBuffer.allocate(4).putFloat(f).array());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(double d, ByteArrayOutputStream byteArrayOutputStream) {
        try {
            byteArrayOutputStream.write(ByteBuffer.allocate(8).putDouble(d).array());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aa(String str) {
        bf bfVar = new bf();
        bfVar.EL = 17;
        bfVar.EM = 4;
        a(a(bfVar));
        byte[] bytes = str.getBytes(Charset.forName(AndroidWebView.UTF_8));
        b(bytes.length, EK);
        try {
            EK.write(bytes);
        } catch (IOException e) {
            o.u("NetworkByteOrderStream:ToBinary<string>: Exception when write bytes array!");
            e.printStackTrace();
        }
    }

    public void ab(String str) {
        bf bfVar = new bf();
        bfVar.EL = 17;
        bfVar.EM = 4;
        a(a(bfVar));
        byte[] bytes = o.s(str).getBytes(Charset.forName(AndroidWebView.UTF_8));
        b(bytes.length, EK);
        try {
            EK.write(bytes);
        } catch (IOException e) {
            o.u("NetworkByteOrderStream:ToBinary<string>: Exception when write bytes array!");
            e.printStackTrace();
        }
    }
}
