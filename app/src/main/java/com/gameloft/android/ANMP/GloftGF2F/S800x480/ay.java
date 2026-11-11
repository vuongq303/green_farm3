package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/* JADX INFO: compiled from: Image.java */
/* JADX INFO: loaded from: classes.dex */
public class ay {
    private aj Qj;
    public int[] ain;
    public int aio;
    public int aip;
    public final int aiq;
    public final int air;
    protected int[] ais;

    public static ay ao(int i, int i2) {
        return c(Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888));
    }

    private static ay c(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArrC = a.c((int[]) null);
        bitmap.getPixels(iArrC, 0, width, 0, 0, width, height);
        return new ay(iArrC, width, height);
    }

    public static ay a(int[] iArr, int i, int i2, boolean z) {
        return new ay(iArr, i, i2);
    }

    public aj nh() {
        if (this.Qj == null) {
            this.Qj = new aj(this.ais[0]);
        }
        return this.Qj;
    }

    public int getWidth() {
        return this.aio;
    }

    public int getHeight() {
        return this.aip;
    }

    public ay(javax.microedition.lcdui.ao aoVar) {
        ay ayVarC = c(aoVar.getBitmap());
        this.ais = ayVarC.ais;
        this.aio = ayVarC.aio;
        this.aip = ayVarC.aip;
        this.aiq = ayVarC.aiq;
        this.air = ayVarC.air;
    }

    public ay(byte[] bArr, int[] iArr, int i, int i2) {
        GLLib.b("-----------------------Create image--------------------------", "OPEN_GL", 3);
        this.ais = new int[1];
        this.aio = i;
        this.aiq = i;
        this.aip = i2;
        this.air = i2;
        GLES20.glActiveTexture(33984);
        GLES20.glGenTextures(1, this.ais, 0);
        GLES20.glBindTexture(3553, this.ais[0]);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10240, 9728);
        GLES20.glTexImage2D(3553, 0, 6409, this.aiq, this.air, 0, 6409, 5121, ByteBuffer.wrap(bArr));
        i(iArr);
    }

    public ay(int i, int[] iArr, int i2, int i3) {
        GLLib.b("-----------------------Create image--------------------------", "OPEN_GL", 3);
        GLES20.glActiveTexture(33984);
        this.ais = new int[1];
        this.ais[0] = i;
        this.aio = i2;
        this.aiq = i2;
        this.aip = i3;
        this.air = i3;
        i(iArr);
    }

    public void i(int[] iArr) {
        this.ain = new int[1];
        GLES20.glGenTextures(1, this.ain, 0);
        GLES20.glBindTexture(3553, this.ain[0]);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10240, 9728);
        int[] iArr2 = new int[256];
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            iArr2[i] = ((i2 >> 16) & 255) | ((-16711936) & i2) | ((i2 & 255) << 16);
        }
        GLES20.glTexImage2D(3553, 0, 6408, 256, 1, 0, 6408, 5121, IntBuffer.wrap(iArr2));
    }

    public int ni() {
        return this.ain[0];
    }

    public boolean nj() {
        return this.ain != null;
    }

    public ay(int[] iArr, int i, int i2) {
        int[] iArrC;
        this.ais = new int[1];
        GLLib.b("-----------------------Create image--------------------------", "OPEN_GL", 3);
        this.aio = i;
        this.aip = i2;
        GLES20.glGenTextures(1, this.ais, 0);
        GLES20.glBindTexture(3553, this.ais[0]);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        GLES20.glTexParameterf(3553, 10241, GLLib.kJ() ? 9729.0f : 9728.0f);
        GLES20.glTexParameterf(3553, 10240, GLLib.kJ() ? 9729.0f : 9728.0f);
        if (w.VD && (i != GLLib.cS(i) || i2 != GLLib.cS(i2))) {
            int iCS = GLLib.cS(i);
            int iCS2 = GLLib.cS(i2);
            if (iArr != null) {
                iArrC = a.c(iArr);
                GLLib.a(iArr, iArrC, i, i2, iCS, iCS2);
            } else {
                iArrC = iArr;
            }
            this.aiq = iCS;
            this.air = iCS2;
            iArr = iArrC;
        } else {
            this.aiq = i;
            this.air = i2;
        }
        int i3 = this.aiq * this.air;
        if (iArr == null) {
            GLES20.glTexImage2D(3553, 0, 6408, this.aiq, this.air, 0, 6408, 5121, null);
            return;
        }
        if (w.Vt && GLLib.kK() == 2) {
            int[] iArr2 = new int[(this.aiq + 2) * 4 * (this.air + 2)];
            short[] sArrA = a.a((short[]) null);
            for (int i4 = 0; i4 < this.air; i4++) {
                int i5 = this.aiq * i4;
                int i6 = (i4 + 1) * (this.aiq + 2);
                for (int i7 = 0; i7 < this.aiq; i7++) {
                    int i8 = ((i6 + i7) + 1) << 2;
                    int i9 = iArr[i5 + i7];
                    iArr2[i8 + 0] = ((i9 >> 16) & 255) << 4;
                    iArr2[i8 + 1] = ((i9 >> 8) & 255) << 4;
                    iArr2[i8 + 2] = (i9 & 255) << 4;
                    iArr2[i8 + 3] = (i9 >> 24) & 255;
                }
            }
            int i10 = (this.aiq + 2) << 2;
            int i11 = i10 - 4;
            int i12 = i10 + 4;
            for (int i13 = 0; i13 < this.air; i13++) {
                int i14 = (i13 + 1) * (this.aiq + 2);
                for (int i15 = 0; i15 < this.aiq; i15++) {
                    int i16 = ((i14 + i15) + 1) << 2;
                    int i17 = iArr2[i16 + 0];
                    int i18 = iArr2[i16 + 1];
                    int i19 = iArr2[i16 + 2];
                    iArr2[i16 + 0] = i17 & (-128);
                    iArr2[i16 + 1] = i18 & (-128);
                    iArr2[i16 + 2] = i19 & (-128);
                    int i20 = i17 - iArr2[i16 + 0];
                    int i21 = i18 - iArr2[i16 + 1];
                    int i22 = i19 - iArr2[i16 + 2];
                    int i23 = i16 + 0 + 4;
                    iArr2[i23] = iArr2[i23] + ((i20 * 7) / 16);
                    int i24 = i16 + 1 + 4;
                    iArr2[i24] = iArr2[i24] + ((i21 * 7) / 16);
                    int i25 = i16 + 2 + 4;
                    iArr2[i25] = iArr2[i25] + ((i22 * 7) / 16);
                    int i26 = i16 + 0 + i11;
                    iArr2[i26] = iArr2[i26] + ((i20 * 3) / 16);
                    int i27 = i16 + 1 + i11;
                    iArr2[i27] = iArr2[i27] + ((i21 * 3) / 16);
                    int i28 = i16 + 2 + i11;
                    iArr2[i28] = iArr2[i28] + ((i22 * 3) / 16);
                    int i29 = i16 + 0 + i10;
                    iArr2[i29] = iArr2[i29] + ((i20 * 5) / 16);
                    int i30 = i16 + 1 + i10;
                    iArr2[i30] = iArr2[i30] + ((i21 * 5) / 16);
                    int i31 = i16 + 2 + i10;
                    iArr2[i31] = iArr2[i31] + ((i22 * 5) / 16);
                    int i32 = i16 + 0 + i12;
                    iArr2[i32] = (i20 / 16) + iArr2[i32];
                    int i33 = i16 + 1 + i12;
                    iArr2[i33] = (i21 / 16) + iArr2[i33];
                    int i34 = i16 + 2 + i12;
                    iArr2[i34] = iArr2[i34] + (i22 / 16);
                    if (iArr2[i16 + 0] < 0) {
                        iArr2[i16 + 0] = 0;
                    } else if (iArr2[i16 + 0] > 4095) {
                        iArr2[i16 + 0] = 4095;
                    }
                    if (iArr2[i16 + 1] < 0) {
                        iArr2[i16 + 1] = 0;
                    } else if (iArr2[i16 + 1] > 4095) {
                        iArr2[i16 + 1] = 4095;
                    }
                    if (iArr2[i16 + 2] < 0) {
                        iArr2[i16 + 2] = 0;
                    } else if (iArr2[i16 + 2] > 4095) {
                        iArr2[i16 + 2] = 4095;
                    }
                }
            }
            for (int i35 = 0; i35 < this.air; i35++) {
                int i36 = this.aiq * i35;
                int i37 = (i35 + 1) * (this.aiq + 2);
                for (int i38 = 0; i38 < this.aiq; i38++) {
                    int i39 = ((i37 + i38) + 1) << 2;
                    sArrA[i36 + i38] = (short) ((iArr2[i39 + 3] >> 7) | ((iArr2[i39 + 0] >> 7) << 11) | ((iArr2[i39 + 1] >> 7) << 6) | ((iArr2[i39 + 2] >> 7) << 1));
                }
            }
            GLES20.glTexImage2D(3553, 0, 6408, this.aiq, this.air, 0, 6408, 32820, ShortBuffer.wrap(sArrA));
            return;
        }
        if (w.Vt && GLLib.kK() == 1) {
            short[] sArrA2 = a.a((short[]) null);
            for (int i40 = 0; i40 < i3; i40++) {
                int i41 = iArr[i40];
                sArrA2[i40] = (short) (((short) (((i41 >> 27) & 7) > 1 ? 1 : 0)) | (((short) ((i41 >> 3) & 7)) << 1) | (((short) ((i41 >> 19) & 7)) << 11) | (((short) ((i41 >> 11) & 7)) << 6));
            }
            GLES20.glTexImage2D(3553, 0, 6408, this.aiq, this.air, 0, 6408, 32820, ShortBuffer.wrap(sArrA2));
            return;
        }
        if (w.Vt && GLLib.kK() == 0) {
            short[] sArrA3 = a.a((short[]) null);
            for (int i42 = 0; i42 < i3; i42++) {
                int i43 = iArr[i42];
                sArrA3[i42] = (short) ((((short) ((i43 >> 4) & 15)) << 4) | (((short) ((i43 >> 20) & 15)) << 12) | (((short) ((i43 >> 12) & 15)) << 8) | ((short) ((i43 >> 28) & 15)));
            }
            GLES20.glTexImage2D(3553, 0, 6408, this.aiq, this.air, 0, 6408, 32819, ShortBuffer.wrap(sArrA3));
            return;
        }
        for (int i44 = 0; i44 < i3; i44++) {
            int i45 = iArr[i44];
            iArr[i44] = ((i45 >> 16) & 255) | ((-16711936) & i45) | ((i45 & 255) << 16);
        }
        GLES20.glTexImage2D(3553, 0, 6408, this.aiq, this.air, 0, 6408, 5121, IntBuffer.wrap(iArr));
    }

    public void nk() {
        ap(false);
    }

    public void ap(boolean z) {
        if (!z) {
            GLES20.glDeleteTextures(1, this.ais, 0);
        }
        if (nj()) {
            GLES20.glDeleteTextures(1, this.ain, 0);
        }
    }

    public int nl() {
        return this.ais[0];
    }
}
