package com.quanhoang.greenfarm3.S800x480;

import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.Utility;
import com.gameloft.glads.GLURLConnection;
import com.google.android.gms.ads.AdRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* JADX INFO: compiled from: ASprite.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    // DEX does not require Java checked-throws declarations; retain the original exception instance.
    @SuppressWarnings("unchecked")
    private static <Failure extends Throwable> RuntimeException propagateChecked(Throwable failure) throws Failure {
        throw (Failure) failure;
    }
    private static char[] Go;
    private static boolean Gp;
    private static boolean Gr;
    private static int[] Gs;
    private static short[] Gt;
    private static byte[] Gu;
    private static int[] Gv;
    private static short[] Gw;
    protected static int II;
    public static int IJ;
    public static int IK;
    protected static int IL;
    protected static int IM;
    public static byte[] IW;
    private static int If;
    private static int Ig;
    private static int Ih;
    private static int Ii;
    private static int Im;
    protected static boolean Io;
    protected static char Ip;
    private static boolean It;
    private static int Iv;
    private static int Iw;
    private static boolean Ix;
    public static boolean Iy;
    private static short[] Jd;
    private byte[] GA;
    private byte[] GB;
    private short[] GC;
    private short[] GD;
    public short[] GE;
    public short[] GF;
    public byte[] GG;
    public byte[] GH;
    private short[] GI;
    private short[] GJ;
    private byte[] GK;
    private short[] GL;
    public short[] GM;
    private byte[] GN;
    private short[] GO;
    private byte[] GP;
    private short[] GQ;
    private byte[] GR;
    private short[] GS;
    private short[] GT;
    private byte[] GU;
    private short[] GV;
    private byte[] GW;
    private short[] GX;
    private short[] GY;
    private byte[] GZ;
    public String Gx;
    public int Gy;
    public int Gz;
    public int HA;
    private short[] HB;
    private int[][][] HC;
    private byte[] HD;
    public int HE;
    public int[] HF;
    public int HG;
    public int[] HH;
    private int[] HI;
    public boolean HJ;
    public boolean HK;
    private int HL;
    private short HM;
    private short[] HN;
    private int[] HO;
    private int[] HP;
    private ay[][] HR;
    private boolean[] HZ;
    private byte[] Ha;
    private byte[] Hb;
    private byte[] Hc;
    private byte[] Hd;
    private byte[] He;
    private short[] Hf;
    public short[] Hg;
    private byte[] Hh;
    private short[] Hi;
    public byte[] Hj;
    private short[] Hk;
    private short[] Hl;
    private byte[] Hm;
    private byte[] Hn;
    public byte[] Ho;
    private short[][] Hp;
    private short[][] Hq;
    private byte[][] Hr;
    private int Hs;
    public byte[] Ht;
    public a[] Hu;
    public byte[] Hv;
    public short[] Hw;
    private byte[] Hx;
    private byte[][] Hy;
    public int Hz;
    protected char[] IG;
    protected int[] IH;
    public int IO;
    public int IP;
    public int IQ;
    public int IR;
    protected int IT;
    public short[] IY;
    public int[] IZ;
    public byte[] Ia;
    private byte[] Ib;
    private int[] Ic;
    private byte[][] Ie;
    protected int[] Ja;
    public short Jb;
    private ArrayList Jk;
    private static boolean Gq = true;
    private static int HQ = 0;
    private static float[] HU = new float[8];
    public static final int[] HV = {0, 2, 1, 3, 5, 7, 4, 6};
    private static final int[] HW = {1, 0, 3, 2, 6, 7, 4, 5};
    private static final int[] HX = {2, 3, 0, 1, 5, 4, 7, 6};
    private static final int[] HY = {4, 5, 6, 7, 3, 2, 1, 0};
    private static int[] Ij = new int[4];
    public static boolean Ik = true;
    public static boolean Il = false;
    protected static char In = '|';
    private static boolean Ir = false;
    private static int Is = -1;
    private static int Iu = -1;
    public static int Je = -1;
    public static int Jf = -1;
    public static int Jg = -1;
    private int HS = 0;
    private int HT = 0;
    public byte Id = 1;
    public int Iq = -1;
    private int Iz = 0;
    private int IA = -1;
    private int IB = -1;
    private int IC = -1;
    private int IE = -1;
    private boolean IF = false;
    protected boolean IN = false;
    protected int IS = 0;
    public boolean IU = false;
    public boolean IV = false;
    public byte[] IX = IW;
    protected int Jc = -1;
    public boolean Jh = false;
    public boolean Ji = true;
    public int Jj = 0;

    private void b(l lVar, int i, int i2) throws IOException {
        int i3;
        int i4;
        int unsignedShort;
        byte b = lVar.Lm.readByte();
        aq("padding = " + ((int) b));
        short s = lVar.readShort();
        aq("atlas width = " + ((int) s));
        short s2 = lVar.readShort();
        aq("atlas height = " + ((int) s2));
        this.Id = lVar.Lm.readByte();
        aq("number image = " + ((int) this.Id));
        this.HN = new short[this.Id];
        this.HF = new int[this.Id];
        if (this.HH == null) {
            this.HH = new int[this.Id];
        }
        this.HE = 1;
        bg(this.Id);
        if (this.HR == null) {
            this.HR = new ay[this.HE][];
        }
        bR(this.Id);
        for (int i5 = 0; i5 < this.Id; i5++) {
            short s3 = lVar.readShort();
            if (w.Xy) {
                GameRuntime.be("pixel format = 0x" + Integer.toHexString(s3));
            }
            this.HN[i5] = lVar.readShort();
            int i6 = lVar.Lm.readByte() & 255;
            aq("colors = " + i6);
            this.HF[i5] = i6;
            if ((w.WQ || w.WP || w.WW) && i6 == 0) {
                i6 = 256;
            }
            this.HH[i5] = i6;
            a(lVar, s3, i6, i5, i);
        }
        hy();
        int i7 = lVar.readShort();
        aq("number module image data = " + i7);
        this.Hy = new byte[i7][];
        for (int i8 = 0; i8 < i7; i8++) {
            if (bo(128)) {
                unsignedShort = lVar.readInt();
            } else {
                unsignedShort = lVar.readUnsignedShort();
            }
            aq("module image data size = " + unsignedShort);
            this.Iz += unsignedShort;
            this.Hy[i8] = new byte[unsignedShort];
            lVar.Ln.read(this.Hy[i8], 0, unsignedShort);
        }
        if (this.HR[0] == null) {
            this.HR[0] = new ay[1];
        }
        if (this.HR[0][0] != null) {
            this.HR[0][0].ap(a(this.HR[0][0]));
            this.HR[0][0] = null;
        }
        int i9 = 0;
        while (true) {
            int i10 = i9;
            if (i10 >= this.Gz) {
                break;
            }
            short s4 = lVar.readShort();
            byte[] bArr = this.Hy[s4];
            int iBE = bE(i10);
            if (w.Xy) {
                GameRuntime.be("module = " + i10 + "; modules data index = " + ((int) s4));
            }
            this.HM = this.HN[iBE];
            if (bArr != null) {
                int iBs = bs(i10) - b;
                int iBt = bt(i10) - b;
                if (w.WY) {
                    i3 = this.GE[i10] & 65535;
                } else {
                    i3 = this.GG[i10] & 255;
                }
                int i11 = (b * 2) + i3;
                if (w.WY) {
                    i4 = this.GF[i10] & 65535;
                } else {
                    i4 = this.GH[i10] & 255;
                }
                a(iBE, bArr, 0, iBs, iBt, i11, (b * 2) + i4, (int) s, true);
            }
            i9 = i10 + 1;
        }
        if (w.VB && GameRuntime.kJ()) {
            a(Gs, s, s2, w.VC);
        }
        this.HR[0][0] = ay.a(Gs, s, s2, true);
        this.Hz |= 536870912;
    }

    private static void aq(String str) {
        if (w.Xy) {
            GameRuntime.be(str);
        }
    }

    public void hs() {
        if (this.HR != null) {
            for (int i = 0; i < this.HR.length; i++) {
                if (this.HR[i] != null) {
                    for (int i2 = 0; i2 < this.HR[i].length; i2++) {
                        if (this.HR[i][i2] != null) {
                            this.HR[i][i2].nk();
                            this.HR[i][i2] = null;
                        }
                    }
                }
            }
        }
        if (this.Jk != null) {
            hK();
        }
        if (w.WX) {
            this.GD = null;
            this.GC = null;
        } else {
            this.GA = null;
            this.GB = null;
        }
        if (w.WY) {
            this.GE = null;
            this.GF = null;
        } else {
            this.GG = null;
            this.GH = null;
        }
        if (w.Xb) {
            this.GV = null;
        } else if (w.Xa) {
            this.GV = null;
            this.GU = null;
        } else {
            this.GU = null;
        }
        if (w.Xd) {
            this.GL = null;
        } else if (w.Xc) {
            this.GL = null;
            this.GK = null;
        } else {
            this.GK = null;
        }
        if (w.Xm) {
            this.Hi = null;
        } else if (w.Xl) {
            this.Hi = null;
            this.Hh = null;
        } else {
            this.Hh = null;
        }
        if (w.Xo) {
            this.Hf = null;
        } else if (w.Xn) {
            this.Hf = null;
            this.He = null;
        } else {
            this.He = null;
        }
        this.GM = null;
        this.GN = null;
        this.GO = null;
        this.GP = null;
        this.GQ = null;
        this.GR = null;
        this.GS = null;
        this.GT = null;
        this.Hg = null;
        this.Hp = (short[][]) null;
        this.Hr = (byte[][]) null;
        this.Hq = (short[][]) null;
        this.HC = (int[][][]) null;
    }

    public void d(byte[] bArr, int i) {
        a(bArr, i, 16777215, 1, (ay) null);
    }

    private void bf(int i) {
        if (this.Ia == null) {
            this.Ia = new byte[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.Ia[i2] = 0;
            }
        }
        if (this.Ic == null) {
            this.Ic = new int[i];
        }
    }

    /* JADX WARN: Code duplicated, block: B:108:0x030a  */
    /* JADX WARN: Code duplicated, block: B:125:0x035f  */
    private void a(l lVar) throws IOException {
        int i;
        short[][] sArr;
        this.Gz = lVar.readUnsignedShort();
        aq("nModules = " + this.Gz);
        if (this.Gz > 0) {
            if (w.WX && (this.Hz & 32) != 0) {
                this.GC = new short[this.Gz];
                this.GD = new short[this.Gz];
            } else if ((this.Hz & 2) != 0) {
                this.GA = new byte[this.Gz];
                this.GB = new byte[this.Gz];
            }
            if (w.WY) {
                this.GE = new short[this.Gz];
                this.GF = new short[this.Gz];
            } else {
                this.GG = new byte[this.Gz];
                this.GH = new byte[this.Gz];
            }
            int i2 = 0;
            int i3 = 0;
            short[][] sArr2 = (short[][]) null;
            if ((this.Hz & 4) != 0) {
                this.Ib = new byte[this.Gz];
            }
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            short[][] sArr3 = sArr2;
            for (int i4 = 0; i4 < this.Gz; i4++) {
                boolean z4 = false;
                boolean z5 = false;
                boolean z6 = false;
                boolean z7 = false;
                int unsignedByte = lVar.Lm.readUnsignedByte();
                if (unsignedByte != 0) {
                    bf(this.Gz);
                    B(unsignedByte, i4);
                }
                if (unsignedByte == 0) {
                    z = false;
                    z2 = true;
                    z3 = true;
                    if ((this.Hz & 4) != 0) {
                        this.Ib[i4] = lVar.Lm.readByte();
                    }
                } else if (unsignedByte == 255 || unsignedByte == 254) {
                    z7 = true;
                    z = true;
                    z2 = false;
                    z3 = true;
                } else if (unsignedByte == 253) {
                    z = false;
                    z2 = false;
                    z3 = true;
                } else if (unsignedByte == 252 || unsignedByte == 251) {
                    z7 = true;
                    z = true;
                    z2 = false;
                    z3 = true;
                    z4 = true;
                } else if (unsignedByte == 250 || unsignedByte == 249) {
                    z7 = true;
                    z = true;
                    z2 = false;
                    z3 = false;
                    z5 = true;
                } else if (unsignedByte == 248) {
                    z7 = true;
                    z = true;
                    z2 = false;
                    z3 = true;
                    z5 = false;
                } else if (unsignedByte == 247) {
                    z = true;
                    z6 = true;
                    z2 = false;
                    z3 = true;
                    z5 = false;
                } else {
                    GameRuntime.c(false, "Invalid module type : " + unsignedByte + "   module #" + i4);
                }
                if (z7) {
                    a(lVar, i4);
                }
                if (bL(i4)) {
                    z = false;
                }
                if (z) {
                    this.Ic[i4] = lVar.readInt();
                }
                if (z6) {
                    if (sArr3 == null) {
                        sArr3 = new short[this.Gz][];
                    }
                    int i5 = lVar.readInt();
                    sArr3[i4] = new short[]{(short) (65535 & i5), (short) ((i5 >> 16) & 65535), lVar.Lm.readByte()};
                    i2++;
                    i3 += 3;
                    sArr = sArr3;
                } else {
                    sArr = sArr3;
                }
                if (z2) {
                    if (w.WX && (this.Hz & 32) != 0) {
                        this.GC[i4] = lVar.readShort();
                        this.GD[i4] = lVar.readShort();
                    } else if ((this.Hz & 2) != 0) {
                        this.GA[i4] = lVar.Lm.readByte();
                        this.GB[i4] = lVar.Lm.readByte();
                    }
                }
                if (z3) {
                    if (!w.WY) {
                        this.GG[i4] = lVar.Lm.readByte();
                        this.GH[i4] = lVar.Lm.readByte();
                    } else if ((this.Hz & 16) == 0) {
                        this.GE[i4] = (short) lVar.Lm.readUnsignedByte();
                        this.GF[i4] = (short) lVar.Lm.readUnsignedByte();
                    } else {
                        this.GE[i4] = lVar.readShort();
                        this.GF[i4] = lVar.readShort();
                    }
                }
                if (z4) {
                    if (sArr == null) {
                        sArr = new short[this.Gz][];
                    }
                    sArr[i4] = new short[]{lVar.readShort(), lVar.readShort()};
                    i2++;
                    i3 += 2;
                }
                if (z5) {
                    if (sArr == null) {
                        sArr = new short[this.Gz][];
                    }
                    sArr[i4] = new short[]{lVar.readShort(), lVar.readShort(), lVar.readShort(), lVar.readShort()};
                    i2++;
                    i3 += 4;
                }
                sArr3 = sArr;
            }
            if (i2 > 0) {
                this.GI = new short[i3];
                this.GJ = new short[i2 << 1];
                int i6 = 0;
                short s = 0;
                for (short s2 = 0; s2 < this.Gz; s2 = (short) (s2 + 1)) {
                    if ((this.Ia != null ? this.Ia[s2] & 255 : 0) == 252) {
                        i = 2;
                    } else if ((this.Ia != null ? this.Ia[s2] & 255 : 0) == 251) {
                        i = 2;
                    } else if ((this.Ia != null ? this.Ia[s2] & 255 : 0) == 250) {
                        i = 4;
                    } else if ((this.Ia != null ? this.Ia[s2] & 255 : 0) == 249) {
                        i = 4;
                    } else {
                        i = (this.Ia != null ? this.Ia[s2] & 255 : 0) == 247 ? 3 : -1;
                    }
                    if (i > 0) {
                        this.GJ[(i6 << 1) + 0] = s2;
                        this.GJ[(i6 << 1) + 1] = s;
                        for (int i7 = 0; i7 < i; i7++) {
                            this.GI[s] = sArr3[s2][i7];
                            s = (short) (s + 1);
                        }
                        sArr3[s2] = null;
                        i6++;
                    }
                }
            }
        }
    }

    private void a(l lVar, int i) {
        try {
            if (bp(4194304)) {
                if (this.HZ == null) {
                    this.HZ = new boolean[this.Gz];
                }
                boolean z = lVar.Lm.readByte() == 1;
                a(z, i);
                if (z) {
                    a(lVar.Lm.readByte(), i);
                    b(lVar.Lm.readByte(), i);
                }
            }
        } catch (IOException failure) {
            throw a.<RuntimeException>propagateChecked(failure);
        }
    }

    private void b(l lVar) {
        try {
            int unsignedShort = lVar.readUnsignedShort();
            aq("nFModules = " + unsignedShort);
            if (unsignedShort > 0) {
                if (w.Xb || (w.Xa && (this.Hz & 1048576) != 0)) {
                    this.GV = new short[unsignedShort];
                } else {
                    this.GU = new byte[unsignedShort];
                }
                if (w.Xe || (w.Xf && (this.Hz & 1024) != 0)) {
                    this.GX = new short[unsignedShort];
                    this.GY = new short[unsignedShort];
                } else {
                    this.GZ = new byte[unsignedShort];
                    this.Ha = new byte[unsignedShort];
                }
                this.GW = new byte[unsignedShort];
                if (w.Xg && (this.Hz & GLURLConnection.BUFFER_SIZE) != 0) {
                    this.Hb = new byte[unsignedShort];
                }
                if (w.Xh && (this.HA & 16) != 0) {
                    this.Hc = new byte[unsignedShort];
                    this.Hd = new byte[unsignedShort];
                }
                for (int i = 0; i < unsignedShort; i++) {
                    if ((w.Xb || w.Xa) && (this.Hz & 1048576) != 0) {
                        this.GV[i] = lVar.readShort();
                    } else if (w.Xb) {
                        this.GV[i] = (short) lVar.Lm.readUnsignedByte();
                    } else {
                        this.GU[i] = lVar.Lm.readByte();
                    }
                    if (!w.Xe && (!w.Xf || (this.Hz & 1024) == 0)) {
                        this.GZ[i] = lVar.Lm.readByte();
                        this.Ha[i] = lVar.Lm.readByte();
                    } else if ((this.Hz & 1024) != 0) {
                        this.GX[i] = lVar.readShort();
                        this.GY[i] = lVar.readShort();
                    } else {
                        this.GX[i] = lVar.Lm.readByte();
                        this.GY[i] = lVar.Lm.readByte();
                    }
                    if (w.Xg && (this.Hz & GLURLConnection.BUFFER_SIZE) != 0) {
                        this.Hb[i] = lVar.Lm.readByte();
                    }
                    this.GW[i] = lVar.Lm.readByte();
                    if (w.Xh && (this.HA & 16) != 0) {
                        this.Hc[i] = lVar.Lm.readByte();
                        this.Hd[i] = lVar.Lm.readByte();
                    }
                }
            }
        } catch (IOException failure) {
            throw a.<RuntimeException>propagateChecked(failure);
        }
    }

    private void c(l lVar) throws IOException {
        if (w.Xt && (this.Hz & 32768) != 0) {
            int unsignedShort = lVar.readUnsignedShort() << 2;
            if ((this.Hz & 1024) == 0) {
                this.GR = new byte[unsignedShort];
                lVar.Ln.read(this.GR, 0, unsignedShort);
            } else {
                this.GS = new short[unsignedShort];
                a(lVar, this.GS, 0, unsignedShort);
            }
        }
        int unsignedShort2 = lVar.readUnsignedShort();
        aq("nFrames = " + unsignedShort2);
        if (unsignedShort2 > 0) {
            if (w.Xd || (w.Xc && (this.Hz & 2048) != 0)) {
                this.GL = new short[unsignedShort2];
            } else {
                this.GK = new byte[unsignedShort2];
            }
            this.GM = new short[unsignedShort2];
            if (w.Xt && (this.Hz & 32768) != 0) {
                this.GT = new short[unsignedShort2 + 1];
            }
            short s = 0;
            for (int i = 0; i < unsignedShort2; i++) {
                aq("Frame: " + i);
                if ((w.Xd || w.Xc) && (this.Hz & 2048) != 0) {
                    this.GL[i] = lVar.readShort();
                } else if (w.Xd) {
                    this.GL[i] = (short) lVar.Lm.readUnsignedByte();
                } else {
                    this.GK[i] = lVar.Lm.readByte();
                }
                this.GM[i] = lVar.readShort();
                if (w.Xt && (this.Hz & 32768) != 0) {
                    this.GT[i] = s;
                    s = (short) (s + lVar.Lm.readByte());
                }
            }
            if (w.Xt && (this.Hz & 32768) != 0) {
                this.GT[this.GT.length - 1] = s;
            }
            if (!w.Xu && (this.Hz & 4096) == 0) {
                if (w.WB) {
                    int i2 = unsignedShort2 << 2;
                    if ((this.Hz & 1024) == 0) {
                        this.GN = new byte[i2];
                        for (int i3 = 0; i3 < i2; i3++) {
                            this.GN[i3] = lVar.Lm.readByte();
                        }
                    } else {
                        this.GO = new short[i2];
                        for (int i4 = 0; i4 < i2; i4++) {
                            this.GO[i4] = lVar.readShort();
                        }
                    }
                } else if ((this.Hz & 1024) == 0) {
                    lVar.Lm.skipBytes(unsignedShort2 << 2);
                } else {
                    lVar.Lm.skipBytes(unsignedShort2 << 3);
                }
            }
            if (!w.Xv || (this.Hz & Utility.DEFAULT_STREAM_BUFFER_SIZE) == 0) {
                return;
            }
            int i5 = unsignedShort2 << 2;
            if ((this.Hz & 1024) == 0) {
                this.GP = new byte[i5];
                for (int i6 = 0; i6 < i5; i6++) {
                    this.GP[i6] = lVar.Lm.readByte();
                }
                return;
            }
            this.GQ = new short[i5];
            for (int i7 = 0; i7 < i5; i7++) {
                this.GQ[i7] = lVar.readShort();
            }
        }
    }

    private void d(l lVar) {
        try {
            int unsignedShort = lVar.readUnsignedShort();
            aq("nAFrames = " + unsignedShort);
            if (unsignedShort > 0) {
                if (w.Xm || (w.Xl && (this.Hz & 2097152) != 0)) {
                    this.Hi = new short[unsignedShort];
                } else {
                    this.Hh = new byte[unsignedShort];
                }
                this.Hj = new byte[unsignedShort];
                if (!w.Xj || (this.Hz & 262144) == 0) {
                    this.Hm = new byte[unsignedShort];
                    this.Hn = new byte[unsignedShort];
                } else {
                    this.Hk = new short[unsignedShort];
                    this.Hl = new short[unsignedShort];
                }
                this.Ho = new byte[unsignedShort];
                for (int i = 0; i < unsignedShort; i++) {
                    if ((w.Xm || w.Xl) && (this.Hz & 2097152) != 0) {
                        this.Hi[i] = lVar.readShort();
                    } else if (w.Xm) {
                        this.Hi[i] = (short) lVar.Lm.readUnsignedByte();
                    } else {
                        this.Hh[i] = lVar.Lm.readByte();
                    }
                    this.Hj[i] = lVar.Lm.readByte();
                    if (!w.Xj || (this.Hz & 262144) == 0) {
                        this.Hm[i] = lVar.Lm.readByte();
                        this.Hn[i] = lVar.Lm.readByte();
                    } else {
                        this.Hk[i] = lVar.readShort();
                        this.Hl[i] = lVar.readShort();
                    }
                    this.Ho[i] = lVar.Lm.readByte();
                }
            }
        } catch (IOException failure) {
            throw a.<RuntimeException>propagateChecked(failure);
        }
    }

    private void e(l lVar) {
        try {
            int unsignedShort = lVar.readUnsignedShort();
            aq("nAnims = " + unsignedShort);
            if (unsignedShort > 0) {
                if (w.Xo || (w.Xn && (this.Hz & 524288) != 0)) {
                    this.Hf = new short[unsignedShort];
                } else {
                    this.He = new byte[unsignedShort];
                }
                this.Hg = new short[unsignedShort];
                for (int i = 0; i < unsignedShort; i++) {
                    if ((w.Xo || w.Xn) && (this.Hz & 524288) != 0) {
                        this.Hf[i] = lVar.readShort();
                    } else if (w.Xo) {
                        this.Hf[i] = (short) lVar.Lm.readUnsignedByte();
                    } else {
                        this.He[i] = lVar.Lm.readByte();
                    }
                    this.Hg[i] = lVar.readShort();
                }
            }
        } catch (IOException failure) {
            throw a.<RuntimeException>propagateChecked(failure);
        }
    }

    public void a(byte[] bArr, int i, int i2, int i3, ay ayVar) {
        try {
            if (bArr == null) {
                GameRuntime.c(false, "Cant load sprite, file[] is null");
                return;
            }
            if (w.ZV) {
                this.Gx = GameRuntime.RI;
                this.Gy = GameRuntime.lE();
            }
            aq("ASprite.Load(" + bArr.length + " bytes, " + i + ")..." + this);
            if (w.Xw || Ik) {
            }
            l lVar = new l(new ByteArrayInputStream(bArr));
            lVar.cf(1);
            lVar.Lm.skipBytes(i);
            short s = lVar.readShort();
            aq("  bs_version = 0x" + Integer.toHexString(s));
            GameRuntime.c(s == 1503, "ASprite.Load.Invalid BSprite version !  GLLib supports only (0x" + Integer.toHexString(1503) + ")    this sprite version (0x" + Integer.toHexString(s) + ")");
            this.Hz = lVar.readInt();
            aq("  bs_flags = 0x" + Integer.toHexString(this.Hz));
            if ((this.Hz & 4194304) != 0) {
                this.HA = lVar.readInt();
                aq("  bs_extra_flags0 = 0x" + Integer.toHexString(this.HA));
            }
            a(lVar);
            b(lVar);
            c(lVar);
            d(lVar);
            e(lVar);
            if (this.Gz <= 0) {
                if (w.XA) {
                    GameRuntime.be("WARNING: sprite with num modules = " + this.Gz);
                }
                if (w.Xw || Ik) {
                    return;
                } else {
                    return;
                }
            }
            if ((this.Hz & 536870912) != 0) {
                c(lVar, i2, i3);
            }
            if ((this.Hz & 4194304) != 0 && (this.HA & 1048576) != 0) {
                a(lVar, i2, i3);
            }
            if ((this.Hz & 4194304) != 0 && bp(8388608)) {
                b(lVar, i2, i3);
            }
            if (this.HF != null) {
                bg(this.HF.length);
            } else {
                bg(1);
            }
            this.HG = this.HE;
            if (w.WC) {
                this.Hs = -1;
                if ((this.HA & 131072) != 0) {
                    f(lVar);
                } else {
                    this.Hp = new short[w.WD][];
                }
            }
            if (w.WB && (w.Xu || (this.Hz & 4096) != 0)) {
                int iHv = hv();
                if (iHv > 0) {
                    if ((this.Hz & 1024) == 0 && (this.HL & 4) == 0) {
                        this.GN = new byte[iHv << 2];
                        int i4 = 0;
                        int i5 = 0;
                        while (i4 < iHv) {
                            a(Ij, i4, 0, 0, 0);
                            int i6 = i5 + 1;
                            this.GN[i5] = (byte) Ij[0];
                            int i7 = i6 + 1;
                            this.GN[i6] = (byte) Ij[1];
                            int i8 = i7 + 1;
                            this.GN[i7] = (byte) (Ij[2] - Ij[0]);
                            this.GN[i8] = (byte) (Ij[3] - Ij[1]);
                            i4++;
                            i5 = i8 + 1;
                        }
                    } else {
                        this.GO = new short[iHv << 2];
                        int i9 = 0;
                        int i10 = 0;
                        while (i9 < iHv) {
                            a(Ij, i9, 0, 0, 0);
                            int i11 = i10 + 1;
                            this.GO[i10] = (short) Ij[0];
                            int i12 = i11 + 1;
                            this.GO[i11] = (short) Ij[1];
                            int i13 = i12 + 1;
                            this.GO[i12] = (short) (Ij[2] - Ij[0]);
                            this.GO[i13] = (short) (Ij[3] - Ij[1]);
                            i9++;
                            i10 = i13 + 1;
                        }
                    }
                }
            } else if ((this.HL & 4) != 0) {
                GameRuntime.be("SetForceShortFrameRC: You need to enable sprite_alwaysBsSkipFrameRc or export the sprite with BS_SKIP_FRAME_RC sprite flag to use this functionality!");
                Y(false);
            }
            aq("ASprite.Load --- ok!");
            if (w.Xw || Ik) {
            }
        } catch (Exception e) {
            if (w.XA) {
                GameRuntime.be("ASprite.Load()." + e);
            }
            GameRuntime.c(false, "Asprite.Load. error while loading sprite : " + e);
        }
    }

    private void bg(int i) {
        if (this.HI == null) {
            if (i == 0) {
                this.HI = new int[1];
            } else {
                this.HI = new int[i];
            }
        }
    }

    public void X(boolean z) {
        if ((this.Hz & 536870912) != 0) {
            this.HD = null;
        }
        this.HD = null;
        if (!bp(4194304)) {
            this.HC = (int[][][]) null;
        }
        this.HO = null;
        this.HP = null;
        this.Hx = null;
        if (w.Xw || Ik) {
        }
    }

    private void bh(int i) {
        this.Hp = new short[i][];
        if ((this.HA & 262144) != 0) {
            if ((this.HA & 524288) != 0) {
                this.Hq = new short[i][];
            } else {
                this.Hr = new byte[i][];
            }
        }
    }

    private void f(l lVar) {
        try {
            if (w.WC) {
                short s = lVar.readShort();
                bh(s);
                for (int i = 0; i < s; i++) {
                    if (this.Hp[i] == null) {
                        this.Hp[i] = new short[this.Gz];
                        for (int i2 = 0; i2 < this.Gz; i2++) {
                            this.Hp[i][i2 & 65535] = (short) i2;
                        }
                    }
                    if ((this.HA & 262144) != 0) {
                        if ((this.HA & 524288) != 0) {
                            this.Hq[i] = new short[this.Gz << 1];
                        } else {
                            this.Hr[i] = new byte[this.Gz << 1];
                        }
                    }
                    short s2 = lVar.readShort();
                    for (int i3 = 0; i3 < s2; i3++) {
                        short s3 = lVar.readShort();
                        this.Hp[i][s3 & 65535] = lVar.readShort();
                        if ((this.HA & 262144) != 0) {
                            if ((this.HA & 524288) == 0) {
                                this.Hr[i][s3 << 1] = lVar.Lm.readByte();
                                this.Hr[i][(s3 << 1) + 1] = lVar.Lm.readByte();
                            } else {
                                short s4 = lVar.readShort();
                                short s5 = lVar.readShort();
                                this.Hq[i][s3 << 1] = s4;
                                this.Hq[i][(s3 << 1) + 1] = s5;
                            }
                        }
                    }
                }
            }
        } catch (IOException failure) {
            throw a.<RuntimeException>propagateChecked(failure);
        }
    }

    private final boolean bi(int i) {
        GameRuntime.c(w.WE, "ASprite.IsFrameMappingEnabled: you should enable sprite_useFrameMapping for using this functionality.");
        return w.WE && this.Ht != null && bj(i) >= 0;
    }

    private final int bj(int i) {
        GameRuntime.c(w.WE, "ASprite.GetFrameMappingConfigId: you should enable sprite_useFrameMapping for using this functionality.");
        GameRuntime.c(this.Ht != null, "ASprite.GetFrameMappingConfigId: frame mapping is not enabled for this sprite.");
        if (w.WE) {
            return this.Ht[i];
        }
        return -1;
    }

    private final boolean bk(int i) {
        GameRuntime.c(w.WE, "ASprite.IsFrameMappingEnabled: you should enable sprite_useFrameMapping for using this functionality.");
        return w.WE && bl(i) != null;
    }

    private final a bl(int i) {
        GameRuntime.c(w.WE, "ASprite.GetFrameMappingConfigSprite: you should enable sprite_useFrameMapping for using this functionality.");
        GameRuntime.c(this.Hu != null && i < this.Hu.length, "ASprite.GetFrameMappingConfigSprite: frame mapping data is not set or it has not the correct size.");
        if (w.WE) {
            return this.Hu[i];
        }
        return null;
    }

    private final int bm(int i) {
        GameRuntime.c(w.WE, "ASprite.GetFrameMappingConfigPaletteId: you should enable sprite_useFrameMapping for using this functionality.");
        GameRuntime.c(this.Hv != null && i < this.Hv.length, "ASprite.GetFrameMappingConfigPaletteId: frame mapping data is not set or it has not the correct size.");
        if (w.WE) {
            return this.Hv[i] & 255;
        }
        return 0;
    }

    private final int bn(int i) {
        GameRuntime.c(w.WE, "ASprite.GetFrameMappingConfigFrameOff: you should enable sprite_useFrameMapping for using this functionality.");
        GameRuntime.c(this.Hw != null && i < this.Hw.length, "ASprite.GetFrameMappingConfigFrameOff: frame mapping data is not set or it has not the correct size.");
        if (w.WE) {
            return this.Hw[i];
        }
        return 0;
    }

    public boolean bo(int i) {
        return (this.Hz & i) != 0;
    }

    public boolean bp(int i) {
        return (this.HA & i) != 0;
    }

    public void bQ(int i) {
        byte b = this.Id;
        for (int i2 = 0; i2 < b; i2++) {
            C(i2, i);
        }
    }

    public static int[] ht() {
        return Ij;
    }

    public final int bq(int i) {
        return (w.Xo || (w.Xn && (this.Hz & 524288) != 0)) ? this.Hf[i] : this.He[i] & 255;
    }

    public final int br(int i) {
        return (w.Xd || (w.Xc && (this.Hz & 2048) != 0)) ? this.GL[i] : this.GK[i] & 255;
    }

    public int bH(int i) {
        if ((this.Ia != null ? this.Ia[i] & 255 : 0) == 0 || this.Ic == null) {
            return 0;
        }
        if (bp(4194304) && bL(i)) {
            byte bBJ = bJ(i);
            return d(bBJ, this.HI[bBJ], bK(i));
        }
        return this.Ic[i];
    }

    public final int bs(int i) {
        if (w.WX && (this.Hz & 32) != 0) {
            return this.GC[i] & 65535;
        }
        if ((this.Hz & 2) != 0) {
            return this.GA[i] & 255;
        }
        return 0;
    }

    public final int bt(int i) {
        if (w.WX && (this.Hz & 32) != 0) {
            return this.GD[i] & 65535;
        }
        if ((this.Hz & 2) != 0) {
            return this.GB[i] & 255;
        }
        return 0;
    }

    public void a(aj ajVar, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (w.WY) {
            i5 = this.GE[i] & 65535;
        } else {
            i5 = this.GG[i] & 255;
        }
        if (w.WY) {
            i6 = this.GF[i] & 65535;
        } else {
            i6 = this.GH[i] & 255;
        }
        Ij[0] = i5;
        Ij[1] = i6;
        a(ajVar, i, i2, i3, i4, Ij);
    }

    public final int bu(int i) {
        return (!w.Xj || (this.Hz & 262144) == 0) ? this.Hm[i] : this.Hk[i];
    }

    public final int bv(int i) {
        return (!w.Xj || (this.Hz & 262144) == 0) ? this.Hn[i] : this.Hl[i];
    }

    public final int bw(int i) {
        return (w.Xe || (w.Xf && (this.Hz & 1024) != 0)) ? this.GX[i] : this.GZ[i];
    }

    public final int bx(int i) {
        return (w.Xe || (w.Xf && (this.Hz & 1024) != 0)) ? this.GY[i] : this.Ha[i];
    }

    public int by(int i) {
        if (w.WB) {
            if ((this.Hz & 1024) == 0 && (this.HL & 4) == 0) {
                return this.GN[(i << 2) + 2] & 255;
            }
            return this.GO[(i << 2) + 2] & 65535;
        }
        a(Ij, i, 0, 0, 0);
        return Ij[2] - Ij[0];
    }

    public int bz(int i) {
        if (w.WB) {
            if ((this.Hz & 1024) == 0 && (this.HL & 4) == 0) {
                return this.GN[(i << 2) + 3] & 255;
            }
            return this.GO[(i << 2) + 3] & 65535;
        }
        a(Ij, i, 0, 0, 0);
        return Ij[3] - Ij[1];
    }

    public int bA(int i) {
        if (w.WB) {
            if ((this.Hz & 1024) == 0 && (this.HL & 4) == 0) {
                return this.GN[(i << 2) + 0];
            }
            return this.GO[(i << 2) + 0];
        }
        a(Ij, i, 0, 0, 0);
        return Ij[0];
    }

    public int bB(int i) {
        if (w.WB) {
            if ((this.Hz & 1024) == 0 && (this.HL & 4) == 0) {
                return this.GN[(i << 2) + 1];
            }
            return this.GO[(i << 2) + 1];
        }
        a(Ij, i, 0, 0, 0);
        return Ij[1];
    }

    public int bC(int i) {
        int i2;
        int iBD = bD(i);
        if (w.Xb || (w.Xa && (this.Hz & 1048576) != 0)) {
            i2 = this.GV[i] & 65535;
        } else {
            i2 = this.GU[i] & 255;
        }
        if (!w.WZ || w.Xb) {
            return i2;
        }
        if (!w.Xa || (this.Hz & 1048576) == 0) {
            return i2 | ((iBD & 192) << 2);
        }
        return i2;
    }

    public int t(int i, int i2) {
        return bC(v(i, i2));
    }

    public int bD(int i) {
        return this.GW[i] & 255;
    }

    public int u(int i, int i2) {
        return bD(v(i, i2));
    }

    public int v(int i, int i2) {
        return (this.GM[i] + i2) & 65535;
    }

    public void hz() {
        this.IT = -bx(this.GM[0] + 0);
        this.IO = bx(1 + this.GM[0]) + this.IT;
        hB();
        hE();
    }

    public int w(int i, int i2) {
        int i3;
        int i4 = this.Hg[i] + i2;
        if (w.Xm || (w.Xl && (this.Hz & 2097152) != 0)) {
            i3 = this.Hi[i4];
        } else {
            i3 = this.Hh[i4] & 255;
        }
        if (!w.Xk || w.Xm) {
            return i3;
        }
        if (!w.Xl || (this.Hz & 2097152) == 0) {
            return i3 | ((this.Ho[i4] & 192) << 2);
        }
        return i3;
    }

    public final void a(int[] iArr, int i, int i2, int i3, int i4) {
        a(iArr, i, i2, i3, i4, false, true);
    }

    public void a(int[] iArr, int i, int i2, int i3, int i4, boolean z, boolean z2) {
        int i5;
        int i6;
        int i7;
        if (!w.WB || z2 || br(i) <= 0) {
            If = Integer.MAX_VALUE;
            Ig = Integer.MAX_VALUE;
            Ih = Integer.MIN_VALUE;
            Ii = Integer.MIN_VALUE;
            HQ = 1;
            a((aj) null, i, (float) i2, (float) i3, i4);
            HQ = 0;
        } else {
            int iBA = bA(i);
            int iBB = bB(i);
            int iBy = by(i);
            int iBz = bz(i);
            if ((i4 & 1) != 0) {
                iBA = (-iBA) - iBy;
            }
            if ((i4 & 2) != 0) {
                iBB = (-iBB) - iBz;
            }
            if ((i4 & 4) != 0) {
                i7 = (-iBB) - iBz;
                i5 = iBy;
                i6 = iBz;
                iBB = iBA;
            } else {
                i5 = iBz;
                i6 = iBy;
                i7 = iBA;
            }
            if (w.Zo) {
                if ((GameRuntime.TX & 262144) != 0) {
                    if (GameRuntime.TY[18][5] != 0) {
                        int i8 = GameRuntime.TY[18][1];
                        if (i8 == 100) {
                            i8 = GameRuntime.TY[18][3];
                        }
                        i2 = (i8 * i2) / 100;
                        int i9 = GameRuntime.TY[18][1];
                        if (i9 == 100) {
                            i9 = GameRuntime.TY[18][4];
                        }
                        i3 = (i9 * i3) / 100;
                    }
                    int i10 = GameRuntime.TY[18][1];
                    if (i10 == 100) {
                        i10 = GameRuntime.TY[18][3];
                    }
                    i7 = (i10 * i7) / 100;
                    int i11 = GameRuntime.TY[18][1];
                    if (i11 == 100) {
                        i11 = GameRuntime.TY[18][4];
                    }
                    iBB = (i11 * iBB) / 100;
                    int i12 = GameRuntime.TY[18][1];
                    if (i12 == 100) {
                        i12 = GameRuntime.TY[18][3];
                    }
                    i6 = (i12 * i6) / 100;
                    int i13 = GameRuntime.TY[18][1];
                    if (i13 == 100) {
                        i13 = GameRuntime.TY[18][4];
                    }
                    i5 = (i5 * i13) / 100;
                }
            }
            If = i2 + i7;
            Ig = i3 + iBB;
            Ih = If + i6;
            Ii = i5 + Ig;
        }
        iArr[0] = If;
        iArr[1] = Ig;
        iArr[2] = Ih;
        iArr[3] = Ii;
    }

    public int bE(int i) {
        if (this.Ib == null) {
            return 0;
        }
        return this.Ib[i];
    }

    public int d(int i, int i2, int i3) {
        return ((int[]) x(0, i2))[i3];
    }

    public Object x(int i, int i2) {
        int i3;
        if (i2 >= 0) {
            if ((this.Hz & 1073741824) != 0) {
                i3 = this.HF[i];
            } else {
                i3 = this.HE;
            }
            if (i2 < i3 && this.HC != null && i2 < this.HC[i].length) {
                return this.HC[i][i2];
            }
        }
        return null;
    }

    public int hu() {
        if (w.Xo || (w.Xn && (this.Hz & 524288) != 0)) {
            if (this.Hf == null) {
                return 0;
            }
            return this.Hf.length;
        }
        if (this.He != null) {
            return this.He.length;
        }
        return 0;
    }

    public int hv() {
        if (w.Xd || (w.Xc && (this.Hz & 2048) != 0)) {
            if (this.GL == null) {
                return 0;
            }
            return this.GL.length;
        }
        if (this.GK != null) {
            return this.GK.length;
        }
        return 0;
    }

    public int bF(int i) {
        int iBr = br(i);
        int iBF = iBr;
        for (int i2 = 0; i2 < iBr; i2++) {
            int iT = t(i, i2);
            if ((u(i, i2) & 16) != 0) {
                iBF = (iBF - 1) + bF(iT);
            }
        }
        return iBF;
    }

    public int bG(int i) {
        if (!w.Xt || this.GT == null) {
            return 0;
        }
        return this.GT[i + 1] - this.GT[i];
    }

    public void a(int i, int i2, int[] iArr, int i3) {
        if (w.Xt && this.GT != null && iArr != null) {
            short s = this.GT[i];
            int i4 = this.GT[i + 1] - s;
            if (i4 > 0 && i2 < i4) {
                int i5 = (s + i2) << 2;
                if ((w.Xe || w.Xf) && (this.Hz & 1024) != 0) {
                    if (this.GS != null) {
                        iArr[0] = this.GS[i5 + 0];
                        iArr[1] = this.GS[i5 + 1];
                        iArr[2] = this.GS[i5 + 2] & 65535;
                        iArr[3] = this.GS[i5 + 3] & 65535;
                    }
                } else if (this.GR != null) {
                    iArr[0] = this.GR[i5 + 0];
                    iArr[1] = this.GR[i5 + 1];
                    iArr[2] = this.GR[i5 + 2] & 255;
                    iArr[3] = this.GR[i5 + 3] & 255;
                }
                if ((i3 & 1) != 0) {
                    iArr[0] = (-iArr[0]) - iArr[2];
                }
                if ((i3 & 2) != 0) {
                    iArr[1] = (-iArr[1]) - iArr[3];
                    return;
                }
                return;
            }
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            iArr[3] = 0;
        }
    }

    public static int[] c(int[] iArr) {
        if (iArr == null || iArr != Gs) {
            if (Gs == null) {
                Gs = new int[w.WA];
            }
            return Gs;
        }
        if (iArr == null || iArr != Gv) {
            if (Gv == null) {
                Gv = new int[w.WA];
            }
            return Gv;
        }
        GameRuntime.be("GetPixelBuffer_int: Error - passed buffer equals both temp_int AND transform_int, this means temp_int == transform_int?!?!");
        return null;
    }

    public static short[] a(short[] sArr) {
        if (sArr == null || sArr != Gt) {
            if (Gt == null) {
                Gt = new short[w.WA];
            }
            return Gt;
        }
        if (sArr == null || sArr != Gw) {
            if (Gw == null) {
                Gw = new short[w.WA];
            }
            return Gw;
        }
        GameRuntime.be("GetPixelBuffer_short: Error - passed buffer equals both temp_int AND transform_int, this means temp_short == transform_short?!?!");
        return null;
    }

    public final int hw() {
        return (this.Hz & 1073741824) != 0 ? this.HF[0] : this.HE;
    }

    public final boolean bI(int i) {
        return i >= 0 && this.HR != null && i < this.HR.length && this.HR[i] != null;
    }

    /* JADX WARN: Code duplicated, block: B:56:0x00f9  */
    public void b(int i, int i2, boolean z) {
        byte[] bArr;
        int iNl;
        boolean z2;
        if ((this.HA & 1048576) != 0) {
            int i3 = this.HI[i2];
            ay ayVar = this.HR[i][i2];
            if (ayVar != null) {
                ayVar.ap(a(ayVar));
                this.HR[i][i2] = null;
            }
            int iBU = bU(i2);
            int iBV = bV(i2);
            this.HI[i2] = i;
            if (this.Hy != null) {
                bArr = this.Hy[i2];
            } else {
                bArr = this.Hx;
            }
            if (z) {
                int i4 = 0;
                while (true) {
                    if (i4 >= this.HR.length) {
                        iNl = -1;
                        break;
                    } else if (this.HR[i4] == null || this.HR[i4][i2] == null || !this.HR[i4][i2].nj()) {
                        i4++;
                    } else {
                        iNl = this.HR[i4][i2].nl();
                        break;
                    }
                }
                if (w.VB) {
                    int[] iArrD = d((int[]) x(i2, i));
                    GameRuntime.c(iArrD != null, "BuildCacheSingleImageIndexed: trying to cache indexed image with borber alpha fix (GLLibConfig.ANDROID_OGLES_FIX_TEXTURE_ALPHA = true), but image palette has more than 127 colors. Skipping alpha fix");
                    if (iArrD != null) {
                        a(i2, i, iArrD);
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                } else {
                    z2 = false;
                }
                int[] iArr = (int[]) x(i2, i);
                if (iNl != -1) {
                    this.HR[i][i2] = new ay(iNl, iArr, iBU, iBV);
                } else if (bArr != null && a(i2, bArr, 0, iBU, iBV, false)) {
                    if (z2) {
                        a(Gu, iArr, iBU, iBV, w.VC);
                    }
                    this.HR[i][i2] = new ay(Gu, iArr, iBU, iBV);
                }
            } else if (bArr != null && a(i2, bArr, 0, iBU, iBV)) {
                if (w.VB && GameRuntime.kJ()) {
                    a(Gs, iBU, iBV, w.VC);
                }
                this.HR[i][i2] = ay.a(Gs, iBU, iBV, true);
            }
            this.HI[i2] = i3;
        }
    }

    public static boolean hx() {
        return Gq;
    }

    private byte bJ(int i) {
        return (byte) (this.Ic[i] & 255);
    }

    private byte bK(int i) {
        return (byte) ((this.Ic[i] >> 8) & 255);
    }

    private boolean bL(int i) {
        if (this.HZ == null) {
            return false;
        }
        return this.HZ[i];
    }

    public ay bM(int i) {
        return y(i, 0);
    }

    public ay y(int i, int i2) {
        return this.HR[i][i2];
    }

    public void Y(boolean z) {
        if (w.WB) {
            if (z) {
                this.HL |= 4;
                return;
            } else {
                this.HL &= -5;
                return;
            }
        }
        GameRuntime.be("SetForceShortFrameRC: You need to enable sprite_usePrecomputedFrameRect to use this functionality!");
    }

    public void a(int i, int i2, int[] iArr) {
        GameRuntime.c(this.HC != null, "SetPalette: Palette Array has not been initialized!");
        GameRuntime.c(i2 >= 0, "SetPalette: Palette Index to set is invalid: " + i2);
        GameRuntime.c(i2 < this.HC[i].length, "SetPalette: Palette Index to set is invalid: " + i2);
        this.HC[i][i2] = iArr;
    }

    public final void z(int i, int i2) {
        if (w.Xe || (w.Xf && (this.Hz & 1024) != 0)) {
            this.GX[i] = (short) i2;
        } else {
            this.GZ[i] = (byte) i2;
        }
    }

    public final void A(int i, int i2) {
        if (w.Xe || (w.Xf && (this.Hz & 1024) != 0)) {
            this.GY[i] = (short) i2;
        } else {
            this.Ha[i] = (byte) i2;
        }
    }

    private void a(byte b, int i) {
        int[] iArr = this.Ic;
        iArr[i] = iArr[i] & 65280;
        int[] iArr2 = this.Ic;
        iArr2[i] = iArr2[i] | (b & 255);
    }

    private void b(byte b, int i) {
        int[] iArr = this.Ic;
        iArr[i] = iArr[i] & 255;
        int[] iArr2 = this.Ic;
        iArr2[i] = iArr2[i] | ((b & 255) << 8);
    }

    private void a(boolean z, int i) {
        this.HZ[i] = z;
    }

    private void B(int i, int i2) {
        this.Ia[i2] = (byte) i;
    }

    private void a(l lVar, int i, int i2, int i3, int i4) {
        try {
            int i5;
            int i6 = 0;
            while (true) {
                int i7 = i6;
                if ((this.Hz & 1073741824) != 0) {
                    i5 = this.HF[i3];
                } else {
                    i5 = this.HE;
                }
                if (i7 < i5) {
                    this.HC[i3][i7] = new int[i2];
                    int[] iArr = this.HC[i3][i7];
                    if (w.WG && i == -30584) {
                        for (int i8 = 0; i8 < i2; i8++) {
                            int i9 = lVar.readInt();
                            if (((-16777216) & i9) != -16777216) {
                                this.HJ = true;
                                if (((-16777216) & i9) != 0) {
                                    this.HK = true;
                                }
                            }
                            iArr[i8] = i9;
                        }
                    } else if (w.WI && i == 17476) {
                        for (int i10 = 0; i10 < i2; i10++) {
                            short s = lVar.readShort();
                            if ((61440 & s) != 61440) {
                                this.HJ = true;
                                if ((61440 & s) != 0) {
                                    this.HK = true;
                                }
                            }
                            iArr[i10] = (s & 15) | ((61440 & s) << 16) | ((61440 & s) << 12) | ((s & 3840) << 12) | ((s & 3840) << 8) | ((s & 240) << 8) | ((s & 240) << 4) | ((s & 15) << 4);
                        }
                    } else if (w.WJ && i == 21781) {
                        for (int i11 = 0; i11 < i2; i11++) {
                            short s2 = lVar.readShort();
                            int i12 = -16777216;
                            if ((Short.MIN_VALUE & s2) != 32768) {
                                i12 = 0;
                                this.HJ = true;
                            }
                            int i13 = i12 | ((s2 & 31744) << 9) | ((s2 & 992) << 6) | ((s2 & 31) << 3);
                            if (i13 == 16253176) {
                                i13 = 16711935;
                            }
                            iArr[i11] = i13;
                        }
                    } else if (w.WH && i == -30712) {
                        this.HK = false;
                        for (int i14 = 0; i14 < i2; i14++) {
                            int unsignedByte = lVar.Lm.readUnsignedByte() + (lVar.Lm.readUnsignedByte() << 8) + (lVar.Lm.readUnsignedByte() << 16);
                            if (unsignedByte == 16711935) {
                                this.HJ = true;
                            } else {
                                unsignedByte |= -16777216;
                            }
                            iArr[i14] = unsignedByte;
                        }
                    } else {
                        GameRuntime.c(false, "Sprite Pixel Format Error: Unsupported format! Format ID: 0x" + Integer.toHexString(i));
                    }
                    if (GameRuntime.lB()) {
                        a(i3, i7, GameRuntime.lC());
                    }
                    i6 = i7 + 1;
                } else {
                    return;
                }
            }
        } catch (IOException failure) {
            throw a.<RuntimeException>propagateChecked(failure);
        }
    }

    private void a(l lVar, int i, int i2) throws IOException {
        int i3;
        short s = lVar.readShort();
        this.HM = lVar.readShort();
        if (w.Xy) {
            GameRuntime.be("_pixel_format = 0x" + Integer.toHexString(s));
        }
        this.Id = lVar.Lm.readByte();
        this.HF = new int[this.Id];
        if (this.HH == null) {
            this.HH = new int[this.Id];
        }
        if (this.HB == null) {
            this.HB = new short[this.Id << 1];
        }
        bg(this.Id);
        for (int i4 = 0; i4 < this.Id; i4++) {
            byte b = lVar.Lm.readByte();
            this.HF[i4] = b;
            if (this.HE < b) {
                this.HE = b;
            }
            aq("_palettes = " + this.HE);
            GameRuntime.c(this.HE <= w.Wz, "Sprite is using more palettes than MAX_SPRITE_PALETTES, please increase this value in GLLibConfig!");
            int unsignedByte = lVar.Lm.readUnsignedByte();
            if ((w.WQ || w.WP || w.WW) && unsignedByte == 0) {
                unsignedByte = 256;
            }
            this.HH[i4] = unsignedByte;
            aq("colors = " + unsignedByte);
            bR(this.Id);
            a(lVar, s, unsignedByte, i4, i);
        }
        hy();
        for (int i5 = 0; i5 < this.Id; i5++) {
            short s2 = lVar.readShort();
            short s3 = lVar.readShort();
            this.HB[i5 * 2] = s2;
            this.HB[(i5 * 2) + 1] = s3;
            int i6 = lVar.readInt();
            if ((this.Hz & 1073741824) != 0) {
                i3 = this.HF[i5];
            } else {
                i3 = this.HE;
            }
            if (this.HR == null) {
                this.HR = new ay[this.HE][];
            }
            if ((this.Hz & 1073741824) != 0) {
                if (this.Hy == null) {
                    this.Hy = new byte[this.Id][];
                }
                this.Hy[i5] = new byte[i6];
                lVar.Ln.read(this.Hy[i5], 0, i6);
            } else {
                this.Hx = new byte[i6];
                lVar.Ln.read(this.Hx, 0, i6);
            }
            for (int i7 = 0; i7 < i3; i7++) {
                this.HI[i5] = i7;
                if (this.HR[i7] == null) {
                    this.HR[i7] = new ay[this.Gz];
                }
                if (this.Jj != 2) {
                    b(i7, i5, this.Jj == 1);
                }
                this.Iz += s2 * s3;
            }
            this.HI[i5] = 0;
        }
        if (this.Ji) {
            X(true);
        }
        this.Hz |= 536870912;
    }

    final void hy() {
        if (w.WN || w.WU) {
            if (this.HM == 25840 || this.HM == -22976) {
                if (this.HO == null) {
                    this.HO = new int[this.Id];
                    this.HP = new int[this.Id];
                }
                for (int i = 0; i < this.Id; i++) {
                    int i2 = this.HH[i] - 1;
                    this.HO[i] = 1;
                    this.HP[i] = 0;
                    while (i2 != 0) {
                        i2 >>= 1;
                        int[] iArr = this.HO;
                        iArr[i] = iArr[i] << 1;
                        int[] iArr2 = this.HP;
                        iArr2[i] = iArr2[i] + 1;
                    }
                    int[] iArr3 = this.HO;
                    iArr3[i] = iArr3[i] - 1;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0084  */
    /* JADX WARN: Code duplicated, block: B:50:0x0089  */
    /* JADX WARN: Code duplicated, block: B:54:0x0097  */
    /* JADX WARN: Code duplicated, block: B:57:0x009b  */
    /* JADX WARN: Code duplicated, block: B:66:0x003c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:67:0x003c A[SYNTHETIC] */
    static final void a(int[] iArr, int i, int i2, int i3) {
        int i4;
        boolean z;
        if (i3 <= 0) {
            return;
        }
        int i5 = i * i2;
        for (int i6 = i3; i6 < i5 - i3; i6++) {
            int i7 = iArr[i6];
            if (((i7 >> 24) & 255) == 0 || (i7 & 16777215) == 16711935) {
                for (int i8 = 1; i8 <= i3; i8++) {
                    int i9 = iArr[i6 + i8];
                    if (((i9 >> 24) & 255) == 0 || (i9 & 16777215) == 16711935) {
                        int i10 = iArr[i6 - i8];
                        if (!(((i10 >> 24) & 255) == 0 || (i10 & 16777215) == 16711935)) {
                            iArr[i6] = iArr[i6 - i8] & 16777215;
                        } else if (i6 - (i8 * i) > 0) {
                            int i11 = iArr[i6 - (i8 * i)];
                            if (!(((i11 >> 24) & 255) == 0 || (i11 & 16777215) == 16711935)) {
                                iArr[i6] = iArr[i6 - (i8 * i)] & 16777215;
                            } else if ((i8 * i) + i6 < i5) {
                                i4 = iArr[(i8 * i) + i6];
                                if (((i4 >> 24) & 255) != 0 || (i4 & 16777215) == 16711935) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                if (!z) {
                                    iArr[i6] = iArr[(i8 * i) + i6] & 16777215;
                                }
                            }
                        } else if ((i8 * i) + i6 < i5) {
                            i4 = iArr[(i8 * i) + i6];
                            if (((i4 >> 24) & 255) != 0) {
                                z = true;
                            } else {
                                z = true;
                            }
                            if (!z) {
                                iArr[i6] = iArr[(i8 * i) + i6] & 16777215;
                            }
                        }
                    } else {
                        iArr[i6] = iArr[i6 + i8] & 16777215;
                    }
                }
            }
        }
    }

    static final int[] d(int[] iArr) {
        if (iArr.length > 127) {
            return null;
        }
        int[] iArr2 = new int[iArr.length + 127];
        GameRuntime.a(iArr, 0, iArr2, 0, iArr.length);
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i + 127] = iArr[i] & 16777215;
        }
        return iArr2;
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:50:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:54:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:57:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:66:0x0048 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:67:0x0048 A[SYNTHETIC] */
    static final void a(byte[] bArr, int[] iArr, int i, int i2, int i3) {
        int i4;
        boolean z;
        if (i3 <= 0) {
            return;
        }
        int i5 = i * i2;
        for (int i6 = i3; i6 < i5 - i3; i6++) {
            int i7 = iArr[bArr[i6] & 255];
            if (((i7 >> 24) & 255) == 0 || (i7 & 16777215) == 16711935) {
                for (int i8 = 1; i8 <= i3; i8++) {
                    int i9 = iArr[bArr[i6 + i8] & 255];
                    if (((i9 >> 24) & 255) == 0 || (i9 & 16777215) == 16711935) {
                        int i10 = iArr[bArr[i6 - i8] & 255];
                        if (!(((i10 >> 24) & 255) == 0 || (i10 & 16777215) == 16711935)) {
                            bArr[i6] = (byte) ((bArr[i6 - i8] & 255) + 127);
                        } else if (i6 - (i8 * i) > 0) {
                            int i11 = iArr[bArr[i6 - (i8 * i)] & 255];
                            if (!(((i11 >> 24) & 255) == 0 || (i11 & 16777215) == 16711935)) {
                                bArr[i6] = (byte) ((bArr[i6 - (i8 * i)] & 255) + 127);
                            } else if ((i8 * i) + i6 < i5) {
                                i4 = iArr[bArr[(i8 * i) + i6] & 255];
                                if (((i4 >> 24) & 255) != 0 || (i4 & 16777215) == 16711935) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                if (!z) {
                                    bArr[i6] = (byte) ((bArr[(i8 * i) + i6] & 255) + 127);
                                }
                            }
                        } else if ((i8 * i) + i6 < i5) {
                            i4 = iArr[bArr[(i8 * i) + i6] & 255];
                            if (((i4 >> 24) & 255) != 0) {
                                z = true;
                            } else {
                                z = true;
                            }
                            if (!z) {
                                bArr[i6] = (byte) ((bArr[(i8 * i) + i6] & 255) + 127);
                            }
                        }
                    } else {
                        bArr[i6] = (byte) ((bArr[i6 + i8] & 255) + 127);
                    }
                }
            }
        }
    }

    private void c(l lVar, int i, int i2) throws IOException {
        int i3;
        int unsignedByte;
        if ((this.Hz & 536870912) != 0) {
            if ((this.Hz & 1073741824) != 0) {
                this.Id = lVar.Lm.readByte();
                this.Ie = new byte[this.Id][];
            } else {
                this.Id = (byte) 1;
            }
            this.HF = new int[this.Id];
            this.HH = new int[this.Id];
            this.Hy = new byte[this.Id][];
            for (int i4 = 0; i4 < this.Id; i4++) {
                if ((this.Hz & 1073741824) != 0) {
                    lVar.readShort();
                    lVar.readShort();
                }
                if ((this.Hz & 128) != 0) {
                    i3 = lVar.readInt();
                } else {
                    int unsignedShort = lVar.readUnsignedShort();
                    GameRuntime.c(unsignedShort >= 0, "LoadingSingleImage: size read as short is negative. You need to add BS_IMAGE_SIZE_INT to this sprites export flags");
                    i3 = unsignedShort;
                }
                aq("png size = " + i3);
                if ((this.Hz & 1073741824) != 0) {
                    this.Hy[i4] = new byte[i3];
                    lVar.Ln.read(this.Hy[i4], 0, i3);
                } else {
                    this.Hx = new byte[i3];
                    lVar.Ln.read(this.Hx, 0, i3);
                }
                int unsignedByte2 = lVar.Lm.readUnsignedByte();
                this.HF[i4] = unsignedByte2;
                if (this.HE < unsignedByte2) {
                    this.HE = unsignedByte2;
                }
                aq("_palettes = " + this.HE);
                GameRuntime.c(this.HE <= w.Wz, "Sprite is using more palettes than MAX_SPRITE_PALETTES, please increase this value in GLLibConfig!");
                if ((this.Hz & 1073741824) != 0) {
                    unsignedByte = lVar.Lm.readUnsignedByte();
                    if (unsignedByte == 0) {
                        unsignedByte = 256;
                    }
                    aq("colors = " + unsignedByte);
                    int i5 = (unsignedByte * 3) + 4;
                    int i6 = unsignedByte + 4;
                    int i7 = i5 + i6;
                    aq("pal_size = " + i7);
                    this.Ie[i4] = new byte[unsignedByte2 * i7];
                    System.arraycopy(this.Hy[i4], 41, this.Ie[i4], 0, i5);
                    System.arraycopy(this.Hy[i4], 0 + i5 + 41 + 8, this.Ie[i4], i5, i6);
                    lVar.Ln.read(this.Ie[i4], i7, (unsignedByte2 - 1) * i7);
                } else {
                    unsignedByte = lVar.Lm.readUnsignedByte();
                    if (unsignedByte == 0) {
                        unsignedByte = 256;
                    }
                    aq("colors = " + unsignedByte);
                    int i8 = (unsignedByte * 3) + 4;
                    int i9 = unsignedByte + 4;
                    int i10 = i8 + i9;
                    aq("pal_size = " + i10);
                    this.HD = new byte[unsignedByte2 * i10];
                    System.arraycopy(this.Hx, 41, this.HD, 0, i8);
                    System.arraycopy(this.Hx, 0 + i8 + 41 + 8, this.HD, i8, i9);
                    lVar.Ln.read(this.HD, i10, (unsignedByte2 - 1) * i10);
                    int i11 = i8;
                    for (int i12 = 0; i12 < unsignedByte2; i12++) {
                        for (int i13 = 0; i13 < unsignedByte; i13++) {
                            int i14 = this.HD[i11] & 255;
                            if (i14 != 255) {
                                this.HJ = true;
                                if (i14 != 0) {
                                    this.HK = true;
                                    break;
                                }
                            }
                            i11++;
                        }
                        i11 += i8 + 4;
                    }
                }
                this.HH[i4] = unsignedByte;
                aq("...read pal data");
            }
        }
    }

    private boolean a(int i, byte[] bArr, int i2, int i3, int i4) {
        return a(i, bArr, i2, i3, i4, true);
    }

    private boolean a(int i, byte[] bArr, int i2, int i3, int i4, boolean z) {
        return a(i, bArr, i2, 0, 0, i3, i4, 0, z);
    }

    private boolean a(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18 = (i7 * i4) + i3;
        if (i7 == 0) {
            i8 = i5 * i6;
        } else {
            i8 = (((i6 + i4) - 1) * i7) + i5 + i3;
        }
        if (z) {
            if (Gs == null) {
                Gs = new int[w.WA];
            }
            GameRuntime.c(i5 * i6 <= Gs.length, "DecodeImage_Algorithm: Module WxH > temp_int.length (" + i5 + " x " + i6 + " = " + (i5 * i6) + " > " + Gs.length + ")! Either decrease module size or increase GLLibConfig.TMP_BUFFER_SIZE!");
        } else {
            if (Gu == null) {
                Gu = new byte[w.WA];
            }
            GameRuntime.c(i5 * i6 <= Gu.length, "DecodeImage_Algorithm: Module WxH > temp_byte.length (" + i5 + " x " + i6 + " = " + (i5 * i6) + " > " + Gu.length + ")!  Either decrease module size or increase GLLibConfig.TMP_BUFFER_SIZE!");
        }
        int[] iArr = null;
        if (z) {
            if (w.Xg && (this.Hz & GLURLConnection.BUFFER_SIZE) != 0 && (this.HL & 16) == 0) {
                iArr = (int[]) x(i, bS(0));
            }
            if (iArr == null) {
                iArr = (int[]) x(i, this.HI[i]);
            }
            if (iArr == null) {
                return false;
            }
        }
        int i19 = 0;
        byte b = 0;
        if (w.WN && this.HM == 25840) {
            int i20 = i4;
            while (i18 < i8) {
                int i21 = i2 + 1;
                int i22 = bArr[i2] & 255;
                if (z) {
                    i19 = iArr[this.HO[i] & i22];
                } else {
                    b = (byte) (this.HO[i] & i22);
                }
                int i23 = i22 >> this.HP[i];
                int i24 = i20;
                while (i23-- >= 0) {
                    if (z) {
                        i17 = i18 + 1;
                        Gs[i18] = i19;
                    } else {
                        i17 = i18 + 1;
                        Gu[i18] = b;
                    }
                    if (i7 <= 0 || i17 < (i24 * i7) + i5 + i3) {
                        i18 = i17;
                    } else {
                        i24++;
                        i18 = i17 + (i7 - i5);
                    }
                }
                i20 = i24;
                i2 = i21;
            }
        } else if (w.WO && this.HM == 10225) {
            int i25 = i4;
            while (i18 < i8) {
                int i26 = i2 + 1;
                int i27 = bArr[i2] & 255;
                if (i27 > 127) {
                    i16 = i26 + 1;
                    int i28 = bArr[i26] & 255;
                    if (z) {
                        i19 = iArr[i28];
                    } else {
                        b = (byte) i28;
                    }
                    int i29 = i27 - 128;
                    i15 = i18;
                    while (i29-- > 0) {
                        if (z) {
                            Gs[i15] = i19;
                            i15++;
                        } else {
                            Gu[i15] = b;
                            i15++;
                        }
                        if (i7 > 0 && i15 >= (i25 * i7) + i5 + i3) {
                            i25++;
                            i15 += i7 - i5;
                        }
                    }
                } else {
                    if (z) {
                        i15 = i18 + 1;
                        Gs[i18] = iArr[i27];
                    } else {
                        i15 = i18 + 1;
                        Gu[i18] = (byte) i27;
                    }
                    if (i7 <= 0 || i15 < (i25 * i7) + i5 + i3) {
                        i16 = i26;
                    } else {
                        i25++;
                        i15 += i7 - i5;
                        i16 = i26;
                    }
                }
                i18 = i15;
                i2 = i16;
            }
        } else if (w.WP && this.HM == 22258) {
            int i30 = i2 - 1;
            int i31 = i18 - 1;
            int i32 = i8 - 1;
            if (i31 < i32) {
                do {
                    int i33 = i30 + 1;
                    int i34 = bArr[i33];
                    if (i34 < 0) {
                        int i35 = i34 + 128;
                        while (true) {
                            if (z) {
                                i31++;
                                i30 = i33 + 1;
                                Gs[i31] = iArr[bArr[i30] & 255];
                            } else {
                                i31++;
                                i30 = i33 + 1;
                                Gu[i31] = (byte) (bArr[i30] & 255);
                            }
                            if (i7 > 0 && i31 >= (i4 * i7) + i5 + i3) {
                                i4++;
                                i31 += i7 - i5;
                            }
                            i35--;
                            if (i35 <= 0) {
                                break;
                            }
                            i33 = i30;
                        }
                    } else {
                        if (z) {
                            i30 = i33 + 1;
                            i19 = iArr[bArr[i30] & 255];
                        } else {
                            i30 = i33 + 1;
                            b = (byte) (bArr[i30] & 255);
                        }
                        do {
                            if (z) {
                                i31++;
                                Gs[i31] = i19;
                            } else {
                                i31++;
                                Gu[i31] = b;
                            }
                            if (i7 > 0 && i31 >= (i4 * i7) + i5 + i3) {
                                i4++;
                                i31 += i7 - i5;
                            }
                            i34--;
                        } while (i34 > 0);
                    }
                } while (i31 < i32);
            }
        } else if (w.WM && this.HM == 5632) {
            int i36 = (((i8 & 1) == 0 ? 0 : 2) + i8) >> 1;
            int i37 = i4;
            int i38 = i18;
            while (true) {
                i36--;
                if (i36 < i18) {
                    break;
                }
                int i39 = i2 + 1;
                byte b2 = bArr[i2];
                if (z) {
                    int i40 = i38 + 1;
                    Gs[i38] = iArr[(b2 >> 4) & 15];
                    i38 = i40 + 1;
                    Gs[i40] = iArr[b2 & 15];
                } else {
                    int i41 = i38 + 1;
                    Gu[i38] = (byte) ((b2 >> 4) & 15);
                    i38 = i41 + 1;
                    Gu[i41] = (byte) (b2 & 15);
                }
                if (i7 > 0 && i38 >= (i37 * i7) + i5 + i3) {
                    i37++;
                    i38 += i7 - i5;
                    i36 -= i7 - i5;
                }
                i2 = i39;
            }
        } else if (w.WL && this.HM == 1024) {
            int i42 = (((i8 & 3) == 0 ? 0 : 4) + i8) >> 2;
            int i43 = i4;
            int i44 = i18;
            while (true) {
                i42--;
                if (i42 < i18) {
                    break;
                }
                int i45 = i2 + 1;
                byte b3 = bArr[i2];
                if (z) {
                    int i46 = i44 + 1;
                    Gs[i44] = iArr[(b3 >> 6) & 3];
                    int i47 = i46 + 1;
                    Gs[i46] = iArr[(b3 >> 4) & 3];
                    int i48 = i47 + 1;
                    Gs[i47] = iArr[(b3 >> 2) & 3];
                    i44 = i48 + 1;
                    Gs[i48] = iArr[b3 & 3];
                } else {
                    int i49 = i44 + 1;
                    Gu[i44] = (byte) ((b3 >> 6) & 3);
                    int i50 = i49 + 1;
                    Gu[i49] = (byte) ((b3 >> 4) & 3);
                    int i51 = i50 + 1;
                    Gu[i50] = (byte) ((b3 >> 2) & 3);
                    i44 = i51 + 1;
                    Gu[i51] = (byte) (b3 & 3);
                }
                if (i7 > 0 && i44 >= (i43 * i7) + i5 + i3) {
                    i43++;
                    i44 += i7 - i5;
                    i42 -= i7 - i5;
                }
                i2 = i45;
            }
        } else if (w.WK && this.HM == 512) {
            int i52 = (((i8 & 7) == 0 ? 0 : 8) + i8) >> 3;
            int i53 = i4;
            int i54 = i18;
            while (true) {
                i52--;
                if (i52 < i18) {
                    break;
                }
                int i55 = i2 + 1;
                byte b4 = bArr[i2];
                if (z) {
                    int i56 = i54 + 1;
                    Gs[i54] = iArr[(b4 >> 7) & 1];
                    int i57 = i56 + 1;
                    Gs[i56] = iArr[(b4 >> 6) & 1];
                    int i58 = i57 + 1;
                    Gs[i57] = iArr[(b4 >> 5) & 1];
                    int i59 = i58 + 1;
                    Gs[i58] = iArr[(b4 >> 4) & 1];
                    int i60 = i59 + 1;
                    Gs[i59] = iArr[(b4 >> 3) & 1];
                    int i61 = i60 + 1;
                    Gs[i60] = iArr[(b4 >> 2) & 1];
                    int i62 = i61 + 1;
                    Gs[i61] = iArr[(b4 >> 1) & 1];
                    i54 = i62 + 1;
                    Gs[i62] = iArr[b4 & 1];
                } else {
                    int i63 = i54 + 1;
                    Gu[i54] = (byte) ((b4 >> 7) & 1);
                    int i64 = i63 + 1;
                    Gu[i63] = (byte) ((b4 >> 6) & 1);
                    int i65 = i64 + 1;
                    Gu[i64] = (byte) ((b4 >> 5) & 1);
                    int i66 = i65 + 1;
                    Gu[i65] = (byte) ((b4 >> 4) & 1);
                    int i67 = i66 + 1;
                    Gu[i66] = (byte) ((b4 >> 3) & 1);
                    int i68 = i67 + 1;
                    Gu[i67] = (byte) ((b4 >> 2) & 1);
                    int i69 = i68 + 1;
                    Gu[i68] = (byte) ((b4 >> 1) & 1);
                    i54 = i69 + 1;
                    Gu[i69] = (byte) (b4 & 1);
                }
                if (i7 > 0 && i54 >= (i53 * i7) + i5 + i3) {
                    i53++;
                    i54 += i7 - i5;
                    i52 -= i7 - i5;
                }
                i2 = i55;
            }
        } else if (w.WQ && this.HM == 22018) {
            int i70 = i18;
            while (true) {
                i8--;
                if (i8 < i18) {
                    break;
                }
                if (z) {
                    i14 = i70 + 1;
                    Gs[i70] = iArr[bArr[i2++] & 255];
                } else {
                    i14 = i70 + 1;
                    Gu[i70] = (byte) (bArr[i2++] & 255);
                }
                if (i7 <= 0 || i14 < (i4 * i7) + i5 + i3) {
                    i70 = i14;
                } else {
                    i4++;
                    i8 -= i7 - i5;
                    i70 = i14 + (i7 - i5);
                }
            }
        } else if (w.WT && this.HM == -23198) {
            int i71 = i18;
            while (true) {
                i8--;
                if (i8 < i18) {
                    break;
                }
                if (z) {
                    i13 = i71 + 1;
                    int alpha = bArr[i2] & 255;
                    int i72 = i2 + 1;
                    i2 = i72 + 1;
                    Gs[i71] = (iArr[bArr[i72] & 255] & 16777215) | (alpha << 24);
                } else {
                    i13 = i71 + 1;
                    Gu[i71] = (byte) (bArr[i2++] & 255);
                }
                if (i7 <= 0 || i13 < (i4 * i7) + i5 + i3) {
                    i71 = i13;
                } else {
                    i4++;
                    i8 -= i7 - i5;
                    i71 = i13 + (i7 - i5);
                }
            }
        } else if (w.WR && this.HM == -24476) {
            int i73 = 0;
            int i74 = i4;
            int i75 = i18;
            int i76 = i2;
            while (i75 < i8) {
                switch (i73) {
                    case 0:
                        Gs[i75] = (bArr[i76] & 252) >> 2;
                        i75++;
                        i76 = i76;
                        break;
                    case 1:
                        Gs[i75] = (bArr[i76] & 3) << 4;
                        i76++;
                        Gs[i75] = ((bArr[i76] & 240) >> 4) | Gs[i75];
                        i75++;
                        break;
                    case 2:
                        Gs[i75] = (bArr[i76] & 15) << 2;
                        i76++;
                        Gs[i75] = ((bArr[i76] & 192) >> 6) | Gs[i75];
                        i75++;
                        break;
                    case 3:
                        Gs[i75] = bArr[i76] & 63;
                        i75++;
                        i76++;
                        i73 = -1;
                        break;
                    default:
                        i76 = i76;
                        break;
                }
                i73++;
                if (i7 > 0 && i75 >= (i74 * i7) + i5 + i3) {
                    i74++;
                    i75 += i7 - i5;
                }
            }
            int i77 = i74;
            int i78 = i18;
            while (true) {
                int i79 = i76;
                i8--;
                if (i8 >= i18) {
                    if (z) {
                        i76 = i79 + 1;
                        Gs[i78] = ((bArr[i79] & 255) << 24) | (iArr[Gs[i78] & 255] & 16777215);
                        i12 = i78 + 1;
                    } else {
                        i12 = i78 + 1;
                        i76 = i79 + 1;
                        Gu[i78] = (byte) (bArr[i79] & 255);
                    }
                    if (i7 <= 0 || i12 < (i77 * i7) + i5 + i3) {
                        i78 = i12;
                    } else {
                        i77++;
                        i8 -= i7 - i5;
                        i78 = i12 + (i7 - i5);
                    }
                } else {
                    break;
                }
            }
        } else if (w.WS && this.HM == -24280) {
            int i80 = 0;
            int i81 = i4;
            int i82 = i18;
            int i83 = i2;
            while (i82 < i8) {
                switch (i80) {
                    case 0:
                        Gs[i82] = (bArr[i83] & 254) >> 1;
                        i82++;
                        i83 = i83;
                        break;
                    case 1:
                        Gs[i82] = (bArr[i83] & 1) << 6;
                        i83++;
                        Gs[i82] = ((bArr[i83] & 252) >> 2) | Gs[i82];
                        i82++;
                        break;
                    case 2:
                        Gs[i82] = (bArr[i83] & 3) << 5;
                        i83++;
                        Gs[i82] = ((bArr[i83] & 248) >> 3) | Gs[i82];
                        i82++;
                        break;
                    case 3:
                        Gs[i82] = (bArr[i83] & 7) << 4;
                        i83++;
                        Gs[i82] = ((bArr[i83] & 240) >> 4) | Gs[i82];
                        i82++;
                        break;
                    case 4:
                        Gs[i82] = (bArr[i83] & 15) << 3;
                        i83++;
                        Gs[i82] = ((bArr[i83] & 224) >> 5) | Gs[i82];
                        i82++;
                        break;
                    case 5:
                        Gs[i82] = (bArr[i83] & 31) << 2;
                        i83++;
                        Gs[i82] = ((bArr[i83] & 192) >> 6) | Gs[i82];
                        i82++;
                        break;
                    case 6:
                        Gs[i82] = (bArr[i83] & 63) << 1;
                        i83++;
                        Gs[i82] = ((bArr[i83] & 128) >> 7) | Gs[i82];
                        i82++;
                        break;
                    case 7:
                        Gs[i82] = bArr[i83] & 127;
                        i83++;
                        i82++;
                        i80 = -1;
                        break;
                    default:
                        i83 = i83;
                        break;
                }
                i80++;
                if (i7 > 0 && i82 >= (i81 * i7) + i5 + i3) {
                    i81++;
                    i82 += i7 - i5;
                }
            }
            int i84 = i81;
            int i85 = i8;
            int i86 = i18;
            while (true) {
                int i87 = i83;
                i85--;
                if (i85 >= i18) {
                    if (z) {
                        i83 = i87 + 1;
                        Gs[i86] = ((bArr[i87] & 255) << 24) | (iArr[Gs[i86] & 255] & 16777215);
                        i11 = i86 + 1;
                    } else {
                        i11 = i86 + 1;
                        i83 = i87 + 1;
                        Gu[i86] = (byte) (bArr[i87] & 255);
                    }
                    if (i7 <= 0 || i11 < (i84 * i7) + i5 + i3) {
                        i86 = i11;
                    } else {
                        i84++;
                        i86 = (i7 - i5) + i11;
                        i85 -= i7 - i5;
                    }
                } else {
                    break;
                }
            }
        } else if (w.WU && this.HM == -22976) {
            int i88 = i4;
            int i89 = i18;
            int i90 = i2;
            while (i89 < i8) {
                int i91 = i90 + 1;
                int i92 = bArr[i90] & 255;
                if (z) {
                    i19 = iArr[this.HO[i] & i92];
                } else {
                    b = (byte) (this.HO[i] & i92);
                }
                int i93 = i92 >> this.HP[i];
                int i94 = i88;
                int i95 = i89;
                while (i93-- >= 0) {
                    if (z) {
                        i10 = i95 + 1;
                        Gs[i95] = i19;
                    } else {
                        i10 = i95 + 1;
                        Gu[i95] = b;
                    }
                    if (i7 <= 0 || i10 < (i94 * i7) + i5 + i3) {
                        i95 = i10;
                    } else {
                        i94++;
                        i95 = i10 + (i7 - i5);
                    }
                }
                i88 = i94;
                i89 = i95;
                i90 = i91;
            }
            int i96 = i88;
            int i97 = i18;
            while (i97 < i8) {
                int i98 = i90 + 1;
                int i99 = bArr[i90] & 255;
                if (i99 == 254) {
                    int i100 = i98 + 1;
                    int i101 = bArr[i98] & 255;
                    i98 = i100 + 1;
                    int i102 = bArr[i100] & 255;
                    while (i101-- > 0) {
                        if (z) {
                            Gs[i97] = (i102 << 24) | (Gs[i97] & 16777215);
                            i97++;
                        }
                        if (i7 > 0 && i97 >= (i96 * i7) + i5 + i3) {
                            i96++;
                            i97 += i7 - i5;
                        }
                    }
                } else if (z) {
                    Gs[i97] = (i99 << 24) | (Gs[i97] & 16777215);
                    i97++;
                    if (i7 > 0 && i97 >= (i96 * i7) + i5 + i3) {
                        i96++;
                        i97 += i7 - i5;
                    }
                }
                i90 = i98;
            }
        } else if (w.WV && this.HM == -24281) {
            int i103 = i4;
            int i104 = i2;
            for (int i105 = i18; i105 < i8; i105 = i9) {
                int i106 = i104 + 1;
                int i107 = bArr[i104] & 255;
                if (i107 > 127) {
                    i104 = i106 + 1;
                    int i108 = bArr[i106] & 255;
                    if (z) {
                        i19 = iArr[i108];
                    } else {
                        b = (byte) i108;
                    }
                    int i109 = i107 - 128;
                    i9 = i105;
                    while (i109-- > 0) {
                        if (z) {
                            Gs[i9] = i19;
                            i9++;
                        } else {
                            Gu[i9] = b;
                            i9++;
                        }
                        if (i7 > 0 && i9 >= (i103 * i7) + i5 + i3) {
                            i103++;
                            i9 += i7 - i5;
                        }
                    }
                } else {
                    if (z) {
                        i9 = i105 + 1;
                        Gs[i105] = iArr[i107];
                    } else {
                        i9 = i105 + 1;
                        Gu[i105] = (byte) i107;
                    }
                    if (i7 <= 0 || i9 < (i103 * i7) + i5 + i3) {
                        i104 = i106;
                    } else {
                        i103++;
                        i9 += i7 - i5;
                        i104 = i106;
                    }
                }
            }
            int i110 = i103;
            int i111 = i18;
            int i112 = i104;
            while (i111 < i8) {
                int i113 = i112 + 1;
                int i114 = bArr[i112] & 255;
                if (i114 == 254) {
                    int i115 = i113 + 1;
                    int i116 = bArr[i113] & 255;
                    i113 = i115 + 1;
                    int i117 = bArr[i115] & 255;
                    while (i116-- > 0) {
                        if (z) {
                            Gs[i111] = (i117 << 24) | (Gs[i111] & 16777215);
                            i111++;
                            if (i7 > 0 && i111 >= (i110 * i7) + i5 + i3) {
                                i110++;
                                i111 += i7 - i5;
                            }
                        }
                    }
                } else if (z) {
                    Gs[i111] = (i114 << 24) | (Gs[i111] & 16777215);
                    i111++;
                    if (i7 > 0 && i111 >= (i110 * i7) + i5 + i3) {
                        i110++;
                        i111 += i7 - i5;
                    }
                }
                i112 = i113;
            }
        } else if (w.WW && this.HM == -23978) {
            int i118 = i4;
            int i119 = i18;
            int i120 = i2;
            while (i119 < i8 && i120 < bArr.length) {
                int i121 = i120 + 1;
                int i122 = bArr[i120] & 255;
                if (i122 > 127) {
                    int i123 = i122 - 128;
                    i120 = i121;
                    while (i123-- > 0) {
                        if (z) {
                            Gs[i119] = iArr[bArr[i120] & 255];
                            i119++;
                            i120++;
                        } else {
                            Gu[i119] = (byte) (bArr[i120] & 255);
                            i119++;
                            i120++;
                        }
                        if (i7 > 0 && i119 >= (i118 * i7) + i5 + i3) {
                            i118++;
                            i119 += i7 - i5;
                        }
                    }
                } else {
                    if (z) {
                        i120 = i121 + 1;
                        i19 = iArr[bArr[i121] & 255];
                    } else {
                        i120 = i121 + 1;
                        b = (byte) (bArr[i121] & 255);
                    }
                    while (i122-- > 0) {
                        if (z) {
                            Gs[i119] = i19;
                            i119++;
                        } else {
                            Gu[i119] = b;
                            i119++;
                        }
                        if (i7 > 0 && i119 >= (i118 * i7) + i5 + i3) {
                            i118++;
                            i119 += i7 - i5;
                        }
                    }
                }
            }
            int i124 = i4;
            int i125 = i18;
            int i126 = i120;
            while (i125 < i8 && i126 < bArr.length) {
                int i127 = i126 + 1;
                int i128 = bArr[i126] & 255;
                if (i128 == 254) {
                    int i129 = i127 + 1;
                    int i130 = bArr[i127] & 255;
                    i127 = i129 + 1;
                    int i131 = bArr[i129] & 255;
                    while (i130-- > 0) {
                        if (z) {
                            Gs[i125] = (i131 << 24) | (Gs[i125] & 16777215);
                            i125++;
                            if (i7 > 0 && i125 >= (i124 * i7) + i5 + i3) {
                                i124++;
                                i125 += i7 - i5;
                            }
                        }
                    }
                } else if (z) {
                    Gs[i125] = (i128 << 24) | (Gs[i125] & 16777215);
                    i125++;
                    if (i7 > 0 && i125 >= (i124 * i7) + i5 + i3) {
                        i124++;
                        i125 += i7 - i5;
                    }
                }
                i126 = i127;
            }
        } else {
            GameRuntime.c(false, "ERROR : sprite encoding for " + Integer.toHexString(this.HM) + " is not enabled. Check sprite_useEncodeFormat in GllibConfiguration");
        }
        return true;
    }

    protected int a(char c) {
        if (this.IG != null) {
            for (int i = 0; i < this.IG.length; i++) {
                if (c == this.IG[i]) {
                    return this.IH[i * 2];
                }
            }
        }
        return -1;
    }

    protected int b(char c) {
        if (this.IG != null) {
            for (int i = 0; i < this.IG.length; i++) {
                if (c == this.IG[i]) {
                    return this.IH[(i * 2) + 1];
                }
            }
        }
        return -1;
    }

    public boolean c(char c) {
        if (this.IG == null) {
            return false;
        }
        for (int i = 0; i < this.IG.length; i++) {
            if (c == this.IG[i]) {
                return true;
            }
        }
        return false;
    }

    public static void d(char c) {
        In = c;
    }

    public void b(short[] sArr) {
        a(sArr, a((Object) sArr, 0));
        hz();
    }

    public void e(int[] iArr) {
        b(iArr, a(iArr, 1));
        hz();
    }

    private void a(short[] sArr, int i) {
        this.IY = new short[i];
        int i2 = 1;
        for (int i3 = 0; i3 < this.Jb; i3++) {
            int i4 = this.Ja[i3];
            int i5 = i2 + 1;
            this.IY[i4] = sArr[i2];
            i2 = i5 + 1;
            this.IY[i4 + 1] = sArr[i5];
        }
        while (i2 < sArr.length) {
            int i6 = i2 + 1;
            int i7 = this.Ja[sArr[i2]];
            i2 = i6 + 1;
            short s = sArr[i6];
            for (int i8 = 1; i8 <= s; i8++) {
                int i9 = i2 + 1;
                this.IY[(i8 * 2) + i7] = sArr[i2];
                i2 = i9 + 1;
                this.IY[(i8 * 2) + i7 + 1] = sArr[i9];
            }
        }
    }

    private void b(int[] iArr, int i) {
        this.IZ = new int[i];
        int i2 = 1;
        for (int i3 = 0; i3 < this.Jb; i3++) {
            int i4 = this.Ja[i3];
            int i5 = i2 + 1;
            this.IZ[i4] = iArr[i2];
            i2 = i5 + 1;
            this.IZ[i4 + 1] = iArr[i5];
        }
        while (i2 < iArr.length) {
            int i6 = i2 + 1;
            int i7 = this.Ja[iArr[i2]];
            i2 = i6 + 1;
            int i8 = iArr[i6];
            for (int i9 = 1; i9 <= i8; i9++) {
                int i10 = i2 + 1;
                this.IZ[(i9 * 2) + i7] = iArr[i2];
                i2 = i10 + 1;
                this.IZ[(i9 * 2) + i7 + 1] = iArr[i10];
            }
        }
    }

    private int a(Object obj, int i) {
        int[] iArr;
        short[] sArr;
        int length;
        int i2;
        int i3;
        if (i == 0) {
            short[] sArr2 = (short[]) obj;
            length = sArr2.length;
            this.Jb = sArr2[0];
            iArr = null;
            sArr = sArr2;
        } else if (i == 1) {
            int[] iArr2 = (int[]) obj;
            length = iArr2.length;
            this.Jb = (short) iArr2[0];
            iArr = iArr2;
            sArr = null;
        } else {
            iArr = null;
            sArr = null;
            length = -1;
        }
        this.Ja = new int[this.Jb + 1];
        int i4 = this.Jb * 2;
        for (int i5 = 0; i5 < this.Jb; i5++) {
            this.Ja[i5] = i5 * 2;
        }
        int i6 = i4 + 1;
        while (i6 < length) {
            if (i == 0) {
                short s = sArr[i6];
                int i7 = i6 + 1;
                short s2 = sArr[i7];
                i6 = i7 + 1;
                i2 = s2;
                i3 = s;
            } else if (i == 1) {
                int i8 = iArr[i6];
                int i9 = i6 + 1;
                int i10 = iArr[i9];
                i6 = i9 + 1;
                i2 = i10;
                i3 = i8;
            } else {
                i2 = -1;
                i3 = -1;
            }
            int i11 = i2 * 2;
            i4 += i11;
            for (int i12 = i3 + 1; i12 < this.Ja.length; i12++) {
                int[] iArr3 = this.Ja;
                iArr3[i12] = iArr3[i12] + i11;
            }
            i6 += i11;
        }
        this.Ja[this.Ja.length - 1] = i4;
        return i4;
    }

    public int bN(int i) {
        int i2 = 2;
        if (this.IZ == null && this.IY == null) {
            GameRuntime.be("ERROR: _pMapCharInt and _pMapCharShort are null!!! Did you set the Char Map?");
            return 0;
        }
        int i3 = i % this.Jb;
        int i4 = this.Ja[i3];
        if (this.IZ != null) {
            if (this.IZ[i4] == i) {
                return this.IZ[i4 + 1];
            }
            int i5 = this.Ja[i3 + 1] - this.Ja[i3];
            while (i2 < i5 && this.IZ[i4 + i2] != i) {
                i2 += 2;
            }
            if (i2 >= i5) {
                if (!w.XA) {
                    return 1;
                }
                GameRuntime.be("Character not available: char value = " + i + " and actual char = '" + ((char) i) + "'");
                return 1;
            }
            return this.IZ[i2 + i4 + 1];
        }
        if (this.IY[i4] == i) {
            return this.IY[i4 + 1];
        }
        int i6 = this.Ja[i3 + 1] - this.Ja[i3];
        while (i2 < i6 && this.IY[i4 + i2] != i) {
            i2 += 2;
        }
        if (i2 >= i6) {
            if (!w.XA) {
                return 1;
            }
            GameRuntime.be("Character not available: char value = " + i + " and actual char = '" + ((char) i) + "'");
            return 1;
        }
        return this.IY[i2 + i4 + 1];
    }

    public final int hA() {
        return (!w.Zo || this.Jc < 0) ? this.IP : (this.IP * this.Jc) >> 8;
    }

    public void hB() {
        this.IP = bx(2 + this.GM[0]) - bx(1 + this.GM[0]);
    }

    public final int hC() {
        return (!w.Zo || this.Jc < 0) ? this.IO : (this.IO * this.Jc) >> 8;
    }

    public final int hD() {
        return (!w.Zo || this.Jc < 0) ? this.IR : (this.IR * this.Jc) >> 8;
    }

    public void hE() {
        this.IR = bw(this.GM[bN(32)] + 0);
    }

    public final int hF() {
        return (!w.Zo || this.Jc < 0) ? this.IQ : (this.IQ * this.Jc) >> 8;
    }

    public final int hG() {
        return (!w.Zo || this.Jc < 0) ? this.IO : (this.IO * this.Jc) >> 8;
    }

    public static final int b(short[] sArr, int i) {
        int i2;
        int i3;
        if (w.XG) {
            i2 = w.XG ? 5 : 4;
            if (w.XE) {
                i2++;
            }
            i3 = (i2 * i) + 6;
        } else {
            i2 = w.XG ? 5 : 4;
            if (w.XE) {
                i2++;
            }
            i3 = (i2 * i) + 5;
        }
        return sArr[i3];
    }

    /* JADX WARN: Code duplicated, block: B:205:0x04d8  */
    public short[] a(String str, int i, int i2, boolean z) {
        short s;
        short s2;
        int i3;
        boolean z2;
        int iBO;
        int i4;
        int i5;
        boolean z3;
        short s3;
        boolean z4;
        int i6;
        boolean z5;
        short s4;
        int i7;
        int i8;
        short s5;
        int i9;
        int i10;
        char cCharAt;
        short sHD;
        int i11;
        short s6;
        int i12;
        boolean z6;
        boolean z7;
        short s7;
        int i13;
        if (Jd == null) {
            Jd = new short[w.XH];
        }
        int length = str.length();
        short s8 = 0;
        short s9 = 0;
        short s10 = 1;
        int i14 = this.IO;
        short s11 = 0;
        boolean z8 = this.IV;
        boolean z9 = this.IU;
        int i15 = this.HI[0];
        boolean z10 = false;
        short s12 = 0;
        boolean z11 = this.IV;
        boolean z12 = this.IU;
        int i16 = this.HI[0];
        int i17 = 0;
        if (w.XG) {
            GameRuntime.c(i16 < 4095, "Warning: WrapTextB formatting can only save palettes up to 4095");
            i17 = (z12 ? Utility.DEFAULT_STREAM_BUFFER_SIZE : 0) | (i16 & 4095) | (z11 ? 4096 : 0);
        }
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = i17;
        while (i20 < length) {
            char cCharAt2 = str.charAt(i20);
            int iA = c((char) cCharAt2) ? a(cCharAt2) : bP(bN(cCharAt2));
            int iB = c((char) cCharAt2) ? b(cCharAt2) : bO(bN(cCharAt2));
            if (i14 >= iB) {
                iB = i14;
            }
            if (cCharAt2 == ' ' || cCharAt2 == In) {
                short sHD2 = cCharAt2 == ' ' ? (short) (((short) hD()) + s8) : s8;
                s2 = (short) i20;
                if (w.XG) {
                    i3 = i16;
                    z2 = z12;
                } else {
                    i3 = i15;
                    z2 = z9;
                }
                if (sHD2 > i && !z) {
                    if (str.charAt(s2) == ' ') {
                        sHD2 = (short) (sHD2 - ((short) hD()));
                    }
                    short s13 = (short) (s10 + 1);
                    Jd[s10] = s9;
                    short s14 = (short) (s13 + 1);
                    Jd[s13] = (short) (s2 - s9);
                    short s15 = (short) (s14 + 1);
                    Jd[s14] = sHD2;
                    short s16 = (short) (s15 + 1);
                    Jd[s15] = (short) iB;
                    if (w.XG) {
                        s5 = (short) (s16 + 1);
                        Jd[s16] = (short) i21;
                        GameRuntime.c(i16 < 4095, "Warning: WrapTextB formatting can only save palettes up to 4095");
                        i7 = (z2 ? Utility.DEFAULT_STREAM_BUFFER_SIZE : 0) | (i3 & 4095) | (z11 ? 4096 : 0);
                        i8 = i3;
                        z12 = z2;
                    } else {
                        i7 = i21;
                        i8 = i16;
                        s5 = s16;
                    }
                    if (w.XE) {
                        if (this.IN) {
                            Jd[s5] = (short) i18;
                            i10 = 0;
                            i9 = 0;
                        } else {
                            i9 = i19;
                            i10 = i18;
                        }
                        s5 = (short) (s5 + 1);
                    } else {
                        i9 = i19;
                        i10 = i18;
                    }
                    s4 = 0;
                    i19 = i9;
                    iBO = i10;
                    i4 = i7;
                    i5 = i8;
                    z3 = z11;
                    s3 = 0;
                    z4 = false;
                    i6 = i3;
                    z9 = z2;
                    z5 = z11;
                    iB = this.IO;
                    s10 = s5;
                    s9 = (short) (((short) ((s2 - s9) + 1)) + s9);
                } else if (!w.XE || !this.IN) {
                    iBO = i18;
                    i4 = i21;
                    i5 = i16;
                    z3 = z11;
                    s3 = 0;
                    z4 = true;
                    i6 = i3;
                    z9 = z2;
                    z5 = z11;
                    s4 = sHD2;
                } else {
                    i19 = i18;
                    iBO = i18 + bO(bN(cCharAt2));
                    i4 = i21;
                    i5 = i16;
                    z3 = z11;
                    s3 = 0;
                    z4 = true;
                    i6 = i3;
                    z9 = z2;
                    z5 = z11;
                    s4 = sHD2;
                }
            } else if (w.Xq && cCharAt2 == '\\') {
                int i22 = i20 + 1;
                char cCharAt3 = str.charAt(i22);
                if (cCharAt3 == '^') {
                    i20 = i22;
                    iBO = i18;
                    i4 = i21;
                    i5 = i16;
                    z3 = !z11;
                    s3 = s12;
                    z4 = z10;
                    i6 = i15;
                    z5 = z8;
                    s2 = s11;
                    s4 = s8;
                } else if (!w.XG) {
                    i20 = i22;
                    iBO = i18;
                    i4 = i21;
                    i5 = i16;
                    z3 = z11;
                    s3 = s12;
                    z4 = z10;
                    i6 = i15;
                    z5 = z8;
                    s2 = s11;
                    s4 = s8;
                } else if (cCharAt3 == '_') {
                    i20 = i22;
                    iBO = i18;
                    i4 = i21;
                    i5 = i16;
                    z12 = !z12;
                    z3 = z11;
                    s3 = s12;
                    z4 = z10;
                    i6 = i15;
                    z5 = z8;
                    s2 = s11;
                    s4 = s8;
                } else {
                    int i23 = (cCharAt3 & 255) - 48;
                    if (i23 < this.HE) {
                        i20 = i22;
                        iBO = i18;
                        i4 = i21;
                        i5 = i23;
                        z3 = z11;
                        s3 = s12;
                        z4 = z10;
                        i6 = i15;
                        z5 = z8;
                        s2 = s11;
                        s4 = s8;
                    } else {
                        i20 = i22;
                        iBO = i18;
                        i4 = i21;
                        i5 = i16;
                        z3 = z11;
                        s3 = s12;
                        z4 = z10;
                        i6 = i15;
                        z5 = z8;
                        s2 = s11;
                        s4 = s8;
                    }
                }
            } else if (cCharAt2 == '\n') {
                short s17 = (short) (s10 + 1);
                Jd[s10] = s9;
                short s18 = (short) (s17 + 1);
                Jd[s17] = (short) (i20 - s9);
                short s19 = (short) (s18 + 1);
                Jd[s18] = s8;
                short s20 = (short) (s19 + 1);
                Jd[s19] = (short) iB;
                s9 = (short) (i20 + 1);
                s4 = 0;
                int i24 = this.IO;
                if (w.XG) {
                    short s21 = (short) (s20 + 1);
                    Jd[s20] = (short) i21;
                    GameRuntime.c(i16 < 4095, "Warning: WrapTextB formatting can only save palettes up to 4095");
                    i21 = (i16 & 4095) | (z11 ? 4096 : 0) | (z12 ? Utility.DEFAULT_STREAM_BUFFER_SIZE : 0);
                    s20 = s21;
                }
                if (!w.XE) {
                    iBO = i18;
                    i4 = i21;
                    i5 = i16;
                    z3 = z11;
                    s3 = s12;
                    z4 = false;
                    i6 = i15;
                    z5 = z8;
                    s2 = s11;
                    iB = i24;
                    s10 = s20;
                } else {
                    if (this.IN) {
                        Jd[s20] = (short) i18;
                        i18 = 0;
                        i19 = 0;
                    }
                    iBO = i18;
                    i4 = i21;
                    i5 = i16;
                    z3 = z11;
                    s3 = s12;
                    z4 = false;
                    i6 = i15;
                    z5 = z8;
                    s2 = s11;
                    iB = i24;
                    s10 = (short) (s20 + 1);
                }
            } else {
                plainCharacter: {
                    if (cCharAt2 >= ' ') {
                        cCharAt = cCharAt2;
                    } else if (cCharAt2 == 1) {
                        iBO = i18;
                        i4 = i21;
                        i5 = i16;
                        z3 = z11;
                        s3 = s12;
                        z4 = z10;
                        i6 = i15;
                        z5 = z8;
                        s2 = s11;
                        s4 = s8;
                        break plainCharacter;
                    } else if (cCharAt2 == 2) {
                        cCharAt = str.charAt(i20);
                    } else {
                        iBO = i18;
                        i4 = i21;
                        i5 = i16;
                        z3 = z11;
                        s3 = s12;
                        z4 = z10;
                        i6 = i15;
                        z5 = z8;
                        s2 = s11;
                        s4 = s8;
                        break plainCharacter;
                    }
                    int iHD = cCharAt == ' ' ? hD() : iA + hF();
                    iBO = (w.XE && this.IN) ? i18 + bO(cCharAt) : i18;
                    int i25 = z11 ? iHD + 1 : iHD;
                    short s22 = (short) (((short) i25) + s12);
                    short s23 = (short) (((short) i25) + s8);
                    if (s23 > i) {
                        boolean z13 = z || !z10;
                        int i26 = 0;
                        if (z13) {
                            sHD = (short) (s23 - ((short) i25));
                            s6 = 0;
                            i11 = i20 - 1;
                            i26 = 1;
                            s22 = 0;
                        } else {
                            sHD = (short) (s23 - ((short) hD()));
                            i11 = s11;
                            s6 = s11;
                        }
                        z4 = false;
                        short s24 = (short) (s10 + 1);
                        Jd[s10] = s9;
                        short s25 = (short) (s24 + 1);
                        Jd[s24] = (short) (i26 + (i11 - s9));
                        short s26 = (short) (s25 + 1);
                        Jd[s25] = (short) (sHD - s22);
                        short s27 = (short) (s26 + 1);
                        Jd[s26] = (short) iB;
                        if (z13) {
                            s22 = 0;
                            i12 = i16;
                            z6 = z12;
                            z7 = z11;
                        } else {
                            i12 = i15;
                            z6 = z9;
                            z7 = z8;
                        }
                        if (w.XG) {
                            s7 = (short) (s27 + 1);
                            Jd[s27] = (short) i21;
                            GameRuntime.c(i16 < 4095, "Warning: WrapTextB formatting can only save palettes up to 4095");
                            i21 = (z6 ? Utility.DEFAULT_STREAM_BUFFER_SIZE : 0) | (i12 & 4095) | (z7 ? 4096 : 0);
                            i16 = i12;
                            z12 = z6;
                        } else {
                            s7 = s27;
                        }
                        if (w.XE) {
                            if (this.IN) {
                                Jd[s7] = (short) i19;
                                i13 = 0;
                                i19 = 0;
                            } else {
                                i13 = iBO;
                            }
                            s7 = (short) (s7 + 1);
                        } else {
                            i13 = iBO;
                        }
                        s4 = 0;
                        i20 = i11;
                        iBO = i13;
                        i4 = i21;
                        i5 = i16;
                        z3 = z7;
                        s3 = s22;
                        i6 = i12;
                        z9 = z6;
                        z5 = z7;
                        s2 = s6;
                        iB = this.IO;
                        s10 = s7;
                        s9 = (short) (i11 + 1);
                    } else {
                        i4 = i21;
                        i5 = i16;
                        z3 = z11;
                        s3 = s22;
                        z4 = z10;
                        i6 = i15;
                        z5 = z8;
                        s2 = s11;
                        s4 = s23;
                    }
                }
            }
            i20++;
            i18 = iBO;
            i21 = i4;
            i16 = i5;
            z11 = z3;
            s12 = s3;
            z10 = z4;
            i15 = i6;
            z8 = z5;
            s11 = s2;
            i14 = iB;
            s8 = s4;
        }
        short s28 = (short) (s10 + 1);
        Jd[s10] = s9;
        short s29 = (short) (s28 + 1);
        Jd[s28] = (short) (str.length() - s9);
        short s30 = (short) (s29 + 1);
        Jd[s29] = s8;
        short s31 = (short) (s30 + 1);
        Jd[s30] = (short) i14;
        if (w.XG) {
            s = (short) (s31 + 1);
            Jd[s31] = (short) i21;
        } else {
            s = s31;
        }
        if (w.XE && this.IN) {
            if (this.IN) {
                Jd[s] = (short) i18;
            }
            s = (short) (s + 1);
        }
        short[] sArr = Jd;
        int i27 = 4;
        if (w.XG) {
            i27 = 5;
        }
        if (w.XE) {
            i27++;
        }
        sArr[0] = (short) (s / i27);
        return Jd;
    }

    /* JADX WARN: Code duplicated, block: B:161:0x022e  */
    /* JADX WARN: Code duplicated, block: B:162:0x0232  */
    public void a(aj ajVar, String str, short[] sArr, int i, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int i8;
        int iB;
        int i9;
        int i10;
        int i11;
        short s = sArr[0];
        int i12 = i4 == -1 ? s : i4;
        int i13 = i3 + i12 > s ? s - i3 : i12;
        int i14 = w.XG ? 5 : 4;
        if (w.XE) {
            i14++;
        }
        int i15 = 1;
        int i16 = sArr[(i14 * 0) + 4];
        while (i15 < i13) {
            int i17 = w.XG ? 5 : 4;
            if (w.XE) {
                i17++;
            }
            int iHA = i16 + sArr[(i17 * i15) + 4] + hA();
            i15++;
            i16 = iHA;
        }
        if ((i5 & 32) != 0) {
            i7 = i2 - i16;
        } else if ((i5 & 2) != 0) {
            i7 = i2 - (i16 >> 1);
        } else {
            i7 = i2;
        }
        II = this.HI[0];
        boolean z = this.IV;
        boolean z2 = this.IU;
        if (i6 >= 0) {
            if (i3 > 0) {
                int i18 = i3 - 1;
                int i19 = w.XG ? 5 : 4;
                if (w.XE) {
                    i19++;
                }
                short s2 = sArr[(i19 * i18) + 1];
                int i20 = i3 - 1;
                int i21 = w.XG ? 5 : 4;
                if (w.XE) {
                    i21++;
                }
                i11 = sArr[(i21 * i20) + 2] + s2;
            } else {
                i11 = 0;
            }
            Jg = i11;
            Jg += i6;
        }
        int i22 = GameRuntime.SF;
        int i23 = i22 - i16;
        int i24 = GameRuntime.SH + i22 + i16;
        int i25 = i3;
        while (i25 < i3 + i13) {
            int i26 = w.XG ? 5 : 4;
            if (w.XE) {
                i26++;
            }
            Je = sArr[(i26 * i25) + 1];
            int i27 = Je;
            int i28 = w.XG ? 5 : 4;
            if (w.XE) {
                i28++;
            }
            Jf = sArr[(i28 * i25) + 2] + i27;
            if (Je < str.length() && str.charAt(Je) == '\n') {
                Je++;
            }
            if (i25 == i3) {
                int i29 = w.XG ? 5 : 4;
                if (w.XE) {
                    i29++;
                }
                i8 = sArr[(i29 * i25) + 4] - this.IO;
            } else {
                int iHA2 = hA();
                int i30 = w.XG ? 5 : 4;
                if (w.XE) {
                    i30++;
                }
                i8 = sArr[(i30 * i25) + 4] + iHA2;
            }
            int i31 = i7 + i8;
            if (i31 >= i23) {
                if (i31 > i24) {
                    break;
                }
                if ((i5 & 43) != 0) {
                    if (!w.Zo || this.Jc < 0) {
                        if ((i5 & 8) != 0) {
                            int i32 = w.XG ? 5 : 4;
                            if (w.XE) {
                                i32++;
                            }
                            i10 = i - sArr[(i32 * i25) + 3];
                        } else if ((i5 & 1) != 0) {
                            int i33 = w.XG ? 5 : 4;
                            if (w.XE) {
                                i33++;
                            }
                            i10 = i - (sArr[(i33 * i25) + 3] >> 1);
                        } else {
                            i10 = i;
                        }
                    } else if ((i5 & 8) != 0) {
                        int i34 = w.XG ? 5 : 4;
                        if (w.XE) {
                            i34++;
                        }
                        i10 = i - ((sArr[(i34 * i25) + 3] * this.Jc) >> 8);
                    } else if ((i5 & 1) != 0) {
                        int i35 = w.XG ? 5 : 4;
                        if (w.XE) {
                            i35++;
                        }
                        i10 = i - ((sArr[(i35 * i25) + 3] * this.Jc) >> 9);
                    } else {
                        i10 = i;
                    }
                    if (!w.XE || !this.IN) {
                        iB = i31;
                        i9 = i10;
                    } else if ((i5 & 8) != 0) {
                        iB = i31 - b(sArr, i25);
                        i9 = i10;
                    } else if ((i5 & 1) != 0) {
                        iB = i31 - (b(sArr, i25) >> 1);
                        i9 = i10;
                    } else {
                        iB = i31;
                        i9 = i10;
                    }
                } else {
                    iB = i31;
                    i9 = i;
                }
                if (w.XG) {
                    int i36 = w.XG ? 5 : 4;
                    if (w.XE) {
                        i36++;
                    }
                    short s3 = sArr[(i36 * i25) + 5];
                    this.IV = (s3 & 4096) != 0;
                    this.IU = (s3 & 8192) != 0;
                    bQ(s3 & 4095);
                }
                a(ajVar, str, i9, iB, 0, false);
            }
            i25++;
            i7 = i31;
        }
        Je = -1;
        Jf = -1;
        Jg = -1;
        this.HI[0] = II;
        if (w.XG) {
            this.IV = z;
            this.IU = z2;
        }
    }

    protected char a(String str, char[] cArr, int i) {
        return str != null ? str.charAt(i) : cArr[i];
    }

    /* JADX WARN: Code duplicated, block: B:128:0x018d  */
    /* JADX WARN: Code duplicated, block: B:129:0x0195  */
    /* JADX WARN: Code duplicated, block: B:169:0x0255  */
    /* JADX WARN: Code duplicated, block: B:170:0x025a A[PHI: r6
  0x025a: PHI (r6v6 int) = (r6v2 int), (r6v9 int) binds: [B:126:0x0183, B:40:0x0065] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:171:0x025d  */
    /* JADX WARN: Code duplicated, block: B:68:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:75:0x00dd  */
    public void a(String str, char[] cArr) {
        int length;
        int iA;
        boolean z;
        int i;
        int iHH;
        int iHF;
        int iAbs;
        if (str != null || cArr != null) {
            IJ = 0;
            if ((w.XE && this.IN) || (w.XF && this.IS != 0)) {
                IK = 0;
            } else {
                IK = hG();
            }
            int i2 = 0;
            int iAbs2 = 0;
            int i3 = 0;
            int i4 = 0;
            if (w.XE && this.IN) {
                IL = 0;
                IM = 0;
            }
            boolean z2 = str != null;
            int i5 = Je >= 0 ? Je : 0;
            if (z2) {
                length = Jf >= 0 ? Jf : str.length();
            } else {
                length = Jf >= 0 ? Jf : cArr.length;
            }
            boolean z3 = this.IV;
            while (i5 < length) {
                char cA = a(str, cArr, i5);
                int iA2 = -1;
                boolean z4 = true;
                if (w.Xs && c(cA)) {
                    z4 = false;
                    iA2 = a(cA);
                }
                if (w.Xq && cA == '\\') {
                    i5++;
                    if (cA == '^') {
                        z = !z3;
                    } else {
                        z = z3;
                    }
                } else if (cA > ' ') {
                    if (Io && cA == Ip) {
                        z = z3;
                    } else {
                        iA = bN(cA);
                        if (w.XA && iA > hv()) {
                            GameRuntime.be("Character not available: c = " + iA);
                            iA = 0;
                        }
                        if (z4) {
                            iHF = bP(iA) + hF() + i2;
                        } else {
                            iHF = hF() + iA2 + i2;
                        }
                        if (!w.XE && this.IN) {
                            iAbs = Math.abs(bO(iA)) + iAbs2;
                        } else {
                            iAbs = Math.abs(this.IS) + iAbs2;
                        }
                        if (z3) {
                            z = z3;
                            iAbs2 = iAbs;
                            i2 = iHF + 1;
                        } else {
                            z = z3;
                            iAbs2 = iAbs;
                            i2 = iHF;
                        }
                    }
                } else if (cA == In) {
                    z = z3;
                } else if (cA == ' ') {
                    int iHD = i2 + hD();
                    if (w.XE && this.IN) {
                        int iAbs3 = Math.abs(bO(bN(cA))) + iAbs2;
                        z = z3;
                        iAbs2 = iAbs3;
                        i2 = iHD;
                    } else if (!w.XF || this.IS == 0) {
                        z = z3;
                        i2 = iHD;
                    } else {
                        z = z3;
                        iAbs2 = Math.abs(this.IS) + iAbs2;
                        i2 = iHD;
                    }
                } else if (cA == '\n') {
                    if ((w.XE && this.IN) || (w.XF && this.IS != 0)) {
                        if (i2 > IJ) {
                            IJ = i2;
                            if (w.XE && this.IN) {
                                i = i3;
                            } else {
                                i = i4;
                            }
                        } else {
                            i = i4;
                        }
                        if (iAbs2 > IK) {
                            IK = iAbs2;
                        }
                        i3++;
                        if (w.XE && this.IN) {
                            iHH = hH() * i3;
                        } else {
                            iHH = 0;
                        }
                        iAbs2 = 0;
                        z = z3;
                        i4 = i;
                        i2 = iHH;
                    } else {
                        if (i2 > IJ) {
                            IJ = i2;
                        }
                        IK += hA() + hG();
                        z = z3;
                        i2 = 0;
                    }
                } else if (cA == 1) {
                    i5++;
                    z = z3;
                } else if (cA == 2) {
                    i5++;
                    iA = a(str, cArr, i5);
                    if (w.XA) {
                        GameRuntime.be("Character not available: c = " + iA);
                        iA = 0;
                    }
                    if (z4) {
                        iHF = bP(iA) + hF() + i2;
                    } else {
                        iHF = hF() + iA2 + i2;
                    }
                    if (!w.XE) {
                        iAbs = Math.abs(this.IS) + iAbs2;
                    } else {
                        iAbs = Math.abs(this.IS) + iAbs2;
                    }
                    if (z3) {
                        z = z3;
                        iAbs2 = iAbs;
                        i2 = iHF + 1;
                    } else {
                        z = z3;
                        iAbs2 = iAbs;
                        i2 = iHF;
                    }
                } else {
                    z = z3;
                }
                i5++;
                z3 = z;
            }
            if ((w.XE && this.IN) || (w.XF && this.IS != 0)) {
                if (i2 > IJ) {
                    IJ = i2;
                    if (w.XE && this.IN) {
                        i4 = i3;
                    }
                }
                if (iAbs2 > IK) {
                    IK = iAbs2;
                }
                i3++;
            } else if (i2 > IJ) {
                IJ = i2;
            }
            if (IJ > 0) {
                IJ -= hF();
            }
            if (w.XE && this.IN) {
                int iHA = (hA() + hG()) * i3;
                int iHH2 = hH() * i3;
                IK += iHA;
                IJ += (i3 - i4) * hH();
                IL = IJ - iHH2;
                IM = IK - iHA;
                if (bx(this.GM[0] + 0) >= 0) {
                    IM = -IM;
                    return;
                }
                return;
            }
            if (w.XF && this.IS != 0) {
                int iHA2 = (hA() + hG()) * i3;
                IK += iHA2 - Math.abs(this.IS);
                IL = IJ;
                IM = IK - iHA2;
                if (this.IS > 0) {
                    IM = -IM;
                }
            }
        }
    }

    public void a(aj ajVar, String str, int i, int i2, int i3) {
        a(ajVar, str, i, i2, i3, true);
    }

    public void a(aj ajVar, String str, int i, int i2, int i3, boolean z) {
        a(ajVar, str, (char[]) null, i, i2, i3, -1, z, true);
    }

    public void a(aj ajVar, String str, char[] cArr, int i, int i2, int i3, int i4, boolean z, boolean z2) {
        a(ajVar, str, cArr, i, i2, i3, i4, z, z2, false);
    }

    /* JADX WARN: Code duplicated, block: B:128:0x0234  */
    /* JADX WARN: Code duplicated, block: B:132:0x0255  */
    /* JADX WARN: Code duplicated, block: B:135:0x026a  */
    /* JADX WARN: Code duplicated, block: B:137:0x0287  */
    /* JADX WARN: Code duplicated, block: B:140:0x029e  */
    /* JADX WARN: Code duplicated, block: B:142:0x02a2  */
    /* JADX WARN: Code duplicated, block: B:144:0x02b3  */
    /* JADX WARN: Code duplicated, block: B:147:0x02c4  */
    /* JADX WARN: Code duplicated, block: B:210:0x0405  */
    /* JADX WARN: Code duplicated, block: B:211:0x040e  */
    /* JADX WARN: Code duplicated, block: B:213:0x0412  */
    /* JADX WARN: Code duplicated, block: B:218:0x0434  */
    /* JADX WARN: Code duplicated, block: B:219:0x043e  */
    public void a(aj ajVar, String str, char[] cArr, int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
        int iAbs;
        int iBw;
        int length;
        int i5;
        char[] cArr2;
        String str2;
        int i6;
        int iA;
        int i7;
        int iHA;
        int i8;
        int i9;
        int i10;
        int iHF;
        int iBN;
        int iBP;
        char[] cArr3;
        boolean z4 = str != null;
        if (str != null || cArr != null) {
            if (z3) {
                iAbs = i2;
                iBw = i;
            } else {
                if (w.Zo && this.Jc >= 0) {
                    iAbs = i2 + ((this.IT * this.Jc) >> 8);
                    iBw = i;
                } else {
                    iAbs = i2 + this.IT;
                    iBw = (w.XE && this.IN) ? i - bw(this.GM[0] + 0) : i;
                }
                if (z2) {
                    a(str, cArr);
                }
                if ((i3 & 43) != 0) {
                    if (!z2) {
                        a(str, cArr);
                    }
                    if ((w.XE && this.IN) || (w.XF && this.IS != 0)) {
                        if ((i3 & 8) != 0) {
                            iBw -= IL;
                            iAbs += IM;
                        } else if ((i3 & 1) != 0) {
                            iBw -= IL >> 1;
                            iAbs += IM >> 1;
                        }
                        if ((i3 & 32) != 0) {
                            iBw -= IJ - IL;
                            iAbs -= IK - Math.abs(IM);
                        } else if ((i3 & 2) != 0) {
                            iBw -= (IJ - IL) >> 1;
                            iAbs -= (IK - Math.abs(IM)) >> 1;
                        }
                    } else {
                        if ((i3 & 8) != 0) {
                            iBw -= IJ;
                        } else if ((i3 & 1) != 0) {
                            iBw -= IJ >> 1;
                        }
                        if ((i3 & 32) != 0) {
                            iAbs -= IK;
                        } else if ((i3 & 2) != 0) {
                            iAbs -= IK >> 1;
                        }
                    }
                }
            }
            if (z) {
                II = this.HI[0];
            }
            int i11 = Je >= 0 ? Je : 0;
            if (z4) {
                length = Jf >= 0 ? Jf : str.length();
            } else {
                length = Jf >= 0 ? Jf : cArr.length;
            }
            if (Jg >= 0 && length > Jg) {
                length = Jg;
            }
            if (length > i11) {
                if (Gp) {
                    i5 = (length - i11) + 4;
                    if (Go == null) {
                        Go = new char[Math.max(1024, i5)];
                    }
                    if (Go.length < i5) {
                        Go = new char[i5];
                    }
                    if (cArr == null) {
                        str.getChars(i11, length, Go, 2);
                        str = null;
                        cArr3 = Go;
                    } else {
                        GameRuntime.a(cArr, i11, Go, 2, length - i11);
                        cArr3 = Go;
                    }
                    Go[0] = 1;
                    Go[1] = (char) this.HI[0];
                    Go[i5 - 2] = 1;
                    Go[i5 - 1] = (char) this.HI[0];
                    i11 = 0;
                    GameRuntime.a(cArr3, i5, true, true);
                    cArr2 = cArr3;
                    str2 = str;
                } else {
                    i5 = length;
                    cArr2 = cArr;
                    str2 = str;
                }
                if (i4 != -1) {
                    a(ajVar, str2, cArr2, iBw, iAbs, i11, i5, i4);
                }
                int i12 = iAbs;
                int i13 = iAbs;
                int i14 = iBw;
                int i15 = iBw;
                while (i11 < i5) {
                    char cA = a(str2, cArr2, i11);
                    int iA2 = -1;
                    boolean z5 = true;
                    if (w.Xs && c(cA)) {
                        z5 = false;
                        iA2 = a(cA);
                        GameRuntime.QD.a(this, cA, i14, i13);
                    }
                    if (w.Xq && cA == '\\') {
                        i6 = i11 + 1;
                        char cA2 = a(str2, cArr2, i6);
                        if (cA2 == '_') {
                            this.IU = !this.IU;
                        } else if (cA2 == '^') {
                            this.IV = !this.IV;
                        } else {
                            int i16 = cA2 & 255;
                            if (!Gr) {
                                bQ(i16 - 48);
                            }
                        }
                        if (cA2 != '\\') {
                            i11 = i6;
                            i7 = i12;
                            iHA = i13;
                            i8 = i14;
                            i9 = i15;
                        } else {
                            iA = bN(cA2);
                            if (w.XA) {
                                i10 = iA;
                            } else {
                                i10 = iA;
                            }
                            if (z5) {
                                a(ajVar, i10, (float) i14, (float) i13, 0);
                            }
                            if (this.IU) {
                                iBN = bN(95);
                                iBP = ((bP(i10) - bP(iBN)) >> 1) + i14;
                                if (z5) {
                                    a(ajVar, iBN, (float) iBP, (float) i13, 0);
                                }
                            }
                            if (this.IV) {
                                i14++;
                                if (z5) {
                                    a(ajVar, i10, (float) i14, (float) i13, 0);
                                }
                            }
                            if (z5) {
                                iHF = bP(i10) + hF() + i14;
                            } else {
                                iHF = hF() + iA2 + i14;
                            }
                            if (!w.XE) {
                                if (w.XF) {
                                    i11 = i6;
                                    i7 = i12;
                                    iHA = i13;
                                    i8 = iHF;
                                    i9 = i15;
                                } else {
                                    i11 = i6;
                                    i7 = i12;
                                    iHA = i13;
                                    i8 = iHF;
                                    i9 = i15;
                                }
                            } else if (w.XF) {
                                i11 = i6;
                                i7 = i12;
                                iHA = i13;
                                i8 = iHF;
                                i9 = i15;
                            } else {
                                i11 = i6;
                                i7 = i12;
                                iHA = i13;
                                i8 = iHF;
                                i9 = i15;
                            }
                        }
                    } else if (cA == In) {
                        i7 = i12;
                        iHA = i13;
                        i8 = i14;
                        i9 = i15;
                    } else if (cA > ' ') {
                        if (Io && cA == Ip) {
                            i7 = i12;
                            iHA = i13;
                            i8 = i14;
                            i9 = i15;
                        } else {
                            iA = bN(cA);
                            i6 = i11;
                            if (w.XA || iA <= hv()) {
                                i10 = iA;
                            } else {
                                GameRuntime.be("Character not available: c = " + iA);
                                i10 = 0;
                            }
                            if (z5) {
                                a(ajVar, i10, (float) i14, (float) i13, 0);
                            }
                            if (this.IU) {
                                iBN = bN(95);
                                iBP = ((bP(i10) - bP(iBN)) >> 1) + i14;
                                if (z5) {
                                    a(ajVar, iBN, (float) iBP, (float) i13, 0);
                                }
                            }
                            if (this.IV) {
                                i14++;
                                if (z5) {
                                    a(ajVar, i10, (float) i14, (float) i13, 0);
                                }
                            }
                            if (z5) {
                                iHF = bP(i10) + hF() + i14;
                            } else {
                                iHF = hF() + iA2 + i14;
                            }
                            if (!w.XE && this.IN) {
                                i11 = i6;
                                i7 = i12;
                                iHA = i13 + bO(i10);
                                i8 = iHF;
                                i9 = i15;
                            } else if (w.XF || this.IS == 0) {
                                i11 = i6;
                                i7 = i12;
                                iHA = i13;
                                i8 = iHF;
                                i9 = i15;
                            } else {
                                i11 = i6;
                                i7 = i12;
                                iHA = i13 + this.IS;
                                i8 = iHF;
                                i9 = i15;
                            }
                        }
                    } else if (cA == ' ') {
                        if (this.IU) {
                            int iBN2 = bN(95);
                            int iHD = ((hD() - bP(iBN2)) >> 1) + i14;
                            if (z5) {
                                a(ajVar, iBN2, (float) iHD, (float) i13, 0);
                            }
                        }
                        int iHD2 = hD() + i14;
                        if (w.XE && this.IN) {
                            i7 = i12;
                            iHA = i13 + bO(bN(cA));
                            i8 = iHD2;
                            i9 = i15;
                        } else if (!w.XF || this.IS == 0) {
                            i7 = i12;
                            iHA = i13;
                            i8 = iHD2;
                            i9 = i15;
                        } else {
                            i7 = i12;
                            iHA = i13 + this.IS;
                            i8 = iHD2;
                            i9 = i15;
                        }
                    } else if (cA == '\n') {
                        if ((w.XE && this.IN) || (w.XF && this.IS != 0)) {
                            int iHH = (w.XE && this.IN) ? hH() + i15 : i15;
                            int iHG = hG() + hA() + i12;
                            i7 = iHG;
                            iHA = iHG;
                            i8 = iHH;
                            i9 = iHH;
                        } else {
                            i7 = i12;
                            iHA = i13 + hA() + hG();
                            i8 = i15;
                            i9 = i15;
                        }
                    } else {
                        if (cA == 1) {
                            i11++;
                            char cA3 = a(str2, cArr2, i11);
                            if (cA3 < this.HE && !Gr) {
                                bQ(cA3);
                            }
                            if (cA3 == 255 && !Gr) {
                                this.HI[0] = II;
                                i7 = i12;
                                iHA = i13;
                                i8 = i14;
                                i9 = i15;
                            }
                        } else if (cA == 2) {
                            i6 = i11 + 1;
                            iA = a(str2, cArr2, i6);
                            if (w.XA) {
                                i10 = iA;
                            } else {
                                i10 = iA;
                            }
                            if (z5) {
                                a(ajVar, i10, (float) i14, (float) i13, 0);
                            }
                            if (this.IU) {
                                iBN = bN(95);
                                iBP = ((bP(i10) - bP(iBN)) >> 1) + i14;
                                if (z5) {
                                    a(ajVar, iBN, (float) iBP, (float) i13, 0);
                                }
                            }
                            if (this.IV) {
                                i14++;
                                if (z5) {
                                    a(ajVar, i10, (float) i14, (float) i13, 0);
                                }
                            }
                            if (z5) {
                                iHF = bP(i10) + hF() + i14;
                            } else {
                                iHF = hF() + iA2 + i14;
                            }
                            if (!w.XE) {
                                if (w.XF) {
                                    i11 = i6;
                                    i7 = i12;
                                    iHA = i13;
                                    i8 = iHF;
                                    i9 = i15;
                                } else {
                                    i11 = i6;
                                    i7 = i12;
                                    iHA = i13;
                                    i8 = iHF;
                                    i9 = i15;
                                }
                            } else if (w.XF) {
                                i11 = i6;
                                i7 = i12;
                                iHA = i13;
                                i8 = iHF;
                                i9 = i15;
                            } else {
                                i11 = i6;
                                i7 = i12;
                                iHA = i13;
                                i8 = iHF;
                                i9 = i15;
                            }
                        }
                        i7 = i12;
                        iHA = i13;
                        i8 = i14;
                        i9 = i15;
                    }
                    i11++;
                    i12 = i7;
                    i13 = iHA;
                    i14 = i8;
                    i15 = i9;
                }
                if (z) {
                    this.HI[0] = II;
                }
            }
        }
    }

    protected void a(aj ajVar, String str, char[] cArr, int i, int i2, int i3, int i4, int i5) {
        boolean z = Gp;
        boolean z2 = Gr;
        Gr = false;
        int iHI = hI();
        bQ(i5);
        aa(false);
        Z(false);
        int i6 = Je;
        int i7 = Jf;
        Je = i3;
        Jf = i4;
        a(ajVar, str, cArr, i - 1, i2 - 1, 0, -1, false, false, true);
        a(ajVar, str, cArr, i - 1, i2 + 1, 0, -1, false, false, true);
        a(ajVar, str, cArr, i + 1, i2 + 1, 0, -1, false, false, true);
        a(ajVar, str, cArr, i + 1, i2 - 1, 0, -1, false, false, true);
        Je = i6;
        Jf = i7;
        Z(!z2);
        aa(z);
        bQ(iHI);
    }

    public static void Z(boolean z) {
        Gr = !z;
    }

    public static void aa(boolean z) {
        Gp = z;
    }

    protected final int hH() {
        if (w.XE && this.IN) {
            return bw(2 + this.GM[0]) - bw(0 + this.GM[0]);
        }
        return 0;
    }

    protected final int bO(int i) {
        return (w.XE && this.IN) ? bx(this.GM[i] + 0) - bx(1 + this.GM[0]) : hG();
    }

    protected final int bP(int i) {
        if (w.XE && this.IN) {
            return bw(this.GM[i] + 0) - bw(1 + this.GM[0]);
        }
        return (!w.Zo || this.Jc < 0) ? bw(this.GM[i] + 0) : (bw(this.GM[i] + 0) * this.Jc) >> 8;
    }

    public void C(int i, int i2) {
        if (this.HF != null && i2 < this.HF[i] && i2 >= 0) {
            this.HI[i] = i2;
        }
    }

    public void bR(int i) {
        if (this.HC == null) {
            this.HC = (int[][][]) Array.newInstance((Class<?>) int[].class, i, w.Wz);
        }
    }

    public int hI() {
        return bS(0);
    }

    public int bS(int i) {
        if (this.HI == null) {
            return 0;
        }
        return this.HI[i];
    }

    public void D(int i, int i2) {
        a(0, i, i2, this.HJ, this.HK);
    }

    public void a(int i, int i2, int i3, boolean z, boolean z2) {
        GameRuntime.c(i2 >= 0, "ModifyPaletteAlpha: Trying to modify palette of negative index!");
        GameRuntime.c(i2 < this.HE, "ModifyPaletteAlpha: Trying to modify palette which is out-of-range: " + i2);
        int[] iArr = (int[]) x(i, i2);
        GameRuntime.c(this.HC != null, "ModifyPaletteAlpha: This palette data is null!");
        int length = iArr.length;
        if (z2) {
            for (int i4 = length - 1; i4 >= 0; i4--) {
                iArr[i4] = ((((((iArr[i4] >> 24) & 255) * i3) >> 8) & 255) << 24) | (iArr[i4] & 16777215);
            }
        } else if (z) {
            int i5 = (i3 & 255) << 24;
            for (int i6 = length - 1; i6 >= 0; i6--) {
                if ((iArr[i6] & 16777215) != 16711935 && (iArr[i6] >> 24) != 0) {
                    iArr[i6] = (iArr[i6] & 16777215) | i5;
                }
            }
        } else {
            int i7 = (i3 & 255) << 24;
            for (int i8 = length - 1; i8 >= 0; i8--) {
                iArr[i8] = (iArr[i8] & 16777215) | i7;
            }
        }
        if ((this.Hz & 536870912) != 0) {
            a(i, i2, iArr);
        }
    }

    public void E(int i, int i2) {
        e(0, i, i2);
    }

    public void e(int i, int i2, int i3) {
        GameRuntime.c(i2 >= 0, "ModifyPaletteAlphaUsingAltPalette: trying to modify palette of negative index!");
        GameRuntime.c(i2 < this.HE, "ModifyPaletteAlphaUsingAltPalette: trying to modify palette which is out-of-range: " + i2);
        GameRuntime.c(i3 >= 0, "ModifyPaletteAlphaUsingAltPalette: trying to use alpha palette of negative index!");
        GameRuntime.c(i3 < this.HE, "ModifyPaletteAlphaUsingAltPalette: trying to use alpha palette which is out-of-range: " + i3);
        this.HJ = true;
        this.HK = true;
        int[] iArr = (int[]) x(i, i2);
        int[] iArr2 = (int[]) x(i, i3);
        for (int i4 = 0; i4 < iArr.length; i4++) {
            if ((iArr[i4] & 16777215) != 16711935 && (iArr[i4] >> 24) != 0) {
                iArr[i4] = iArr[i4] & 16777215;
                iArr[i4] = iArr[i4] | ((iArr2[i4] & 16711680) << 8);
            }
        }
        if ((this.Hz & 536870912) != 0) {
            a(i, i2, iArr);
        }
    }

    public void bT(int i) {
        E(i, this.HE - 1);
    }

    public void a(int i, int i2, be beVar) {
        int[] iArr = (int[]) x(i, i2);
        for (int i3 = 0; i3 < iArr.length; i3++) {
            if ((iArr[i3] & 16777215) != 16711935 && (iArr[i3] >> 24) != 0) {
                iArr[i3] = GameRuntime.a(iArr[i3], beVar);
            }
        }
    }

    private void a(l lVar, short[] sArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            sArr[i3 + i] = lVar.readShort();
        }
    }

    private int bU(int i) {
        if (this.HB == null) {
            return 0;
        }
        return this.HB[i * 2];
    }

    private int bV(int i) {
        if (this.HB == null) {
            return 0;
        }
        return this.HB[(i * 2) + 1];
    }

    private boolean a(ay ayVar) {
        for (int i = 0; i < this.HR.length; i++) {
            if (this.HR[i] != null) {
                for (int i2 = 0; i2 < this.HR[i].length; i2++) {
                    if (this.HR[i][i2] != null && this.HR[i][i2].nj() && ayVar != this.HR[i][i2] && ayVar.nl() == this.HR[i][i2].nl()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:66:0x012c A[PHI: r5
  0x012c: PHI (r5v3 int) = (r5v2 int), (r5v5 int), (r5v5 int) binds: [B:28:0x007f, B:37:0x00a8, B:39:0x00b6] A[DONT_GENERATE, DONT_INLINE]] */
    public void a(aj ajVar, int i, int i2, int i3, int i4, int i5) {
        int i6;
        if (w.Xz) {
            GameRuntime.be("Sprite paint: aframe = " + i2);
        }
        int i7 = this.Hg[i] + i2;
        int i8 = (w.Xm || (w.Xl && (this.Hz & 2097152) != 0)) ? this.Hi[i7] : this.Hh[i7] & 255;
        int i9 = (!w.Xk || w.Xm || (w.Xl && (this.Hz & 2097152) != 0)) ? i8 : i8 | ((this.Ho[i7] & 192) << 2);
        int iBu = bu(i7);
        int iBv = bv(i7);
        int i10 = this.Ho[i7] & 15;
        if ((i5 & 1) != 0) {
            i10 = HW[i10 & 7] | (i10 & (-8));
            iBu = -iBu;
        }
        if ((i5 & 2) != 0) {
            i10 = HX[i10 & 7] | (i10 & (-8));
            iBv = -iBv;
        }
        if ((i5 & 4) != 0) {
            i10 = HY[i10 & 7] | (i10 & (-8));
            if (w.Zo && ((GameRuntime.TX & 262144) != 0) && (GameRuntime.TY[18][6] % GameRuntime.Rk != 0 || GameRuntime.TY[18][10] != 0)) {
                i6 = iBu;
            } else {
                i6 = -iBv;
                iBv = iBu;
            }
        } else {
            i6 = iBu;
        }
        if (w.Zo) {
            if ((GameRuntime.TX & 262144) != 0) {
                if (!(GameRuntime.TY[18][5] != 0)) {
                    int i11 = GameRuntime.TY[18][1];
                    if (i11 == 100) {
                        i11 = GameRuntime.TY[18][3];
                    }
                    int i12 = GameRuntime.TY[18][1];
                    if (i12 == 100) {
                        i12 = GameRuntime.TY[18][4];
                    }
                    i6 = (i11 * i6) / 100;
                    iBv = (iBv * i12) / 100;
                }
            }
        }
        a(ajVar, i9, (float) (i3 + i6), (float) (iBv + i4), i10);
    }

    public void a(aj ajVar, int i, float f, float f2, int i2, int i3) {
        float f3;
        float f4;
        if (w.WB) {
            if ((this.Hz & 1024) == 0 && (this.HL & 4) == 0) {
                Ij[0] = this.GN[i << 2];
                Ij[1] = this.GN[(i << 2) + 1];
                Ij[2] = (this.GN[(i << 2) + 2] & 255) + Ij[0];
                Ij[3] = (this.GN[(i << 2) + 3] & 255) + Ij[1];
            } else {
                Ij[0] = this.GO[i << 2];
                Ij[1] = this.GO[(i << 2) + 1];
                Ij[2] = (this.GO[(i << 2) + 2] & 65535) + Ij[0];
                Ij[3] = (this.GO[(i << 2) + 3] & 65535) + Ij[1];
            }
        } else if (w.Xv) {
            if ((this.Hz & 1024) == 0) {
                Ij[0] = this.GP[i << 2];
                Ij[1] = this.GP[(i << 2) + 1];
                Ij[2] = (this.GP[(i << 2) + 2] & 255) + Ij[0];
                Ij[3] = (this.GP[(i << 2) + 3] & 255) + Ij[1];
            } else {
                Ij[0] = this.GQ[i << 2];
                Ij[1] = this.GQ[(i << 2) + 1];
                Ij[2] = (this.GQ[(i << 2) + 2] & 65535) + Ij[0];
                Ij[3] = (this.GQ[(i << 2) + 3] & 65535) + Ij[1];
            }
        } else if (w.Xp) {
            a(Ij, i, 0, 0, 0);
        } else {
            a(ajVar, i, f, f2, i2);
            return;
        }
        if ((i3 & 4) != 0) {
            f3 = f - Ij[0];
        } else if ((i3 & 1) != 0) {
            f3 = f - (Ij[0] + ((Ij[2] - Ij[0]) >> 1));
        } else {
            f3 = (i3 & 8) != 0 ? f - Ij[2] : f;
        }
        if ((i3 & 16) != 0) {
            f4 = f2 - Ij[1];
        } else if ((i3 & 2) != 0) {
            f4 = f2 - (Ij[1] + ((Ij[3] - Ij[1]) >> 1));
        } else {
            f4 = (i3 & 32) != 0 ? f2 - Ij[3] : f2;
        }
        a(ajVar, i, f3, f4, i2);
    }

    public final void a(aj ajVar, int i, float f, float f2, int i2) {
        if (w.WE && bi(i)) {
            int iBj = bj(i);
            if (bk(iBj)) {
                a aVarBl = bl(iBj);
                int iBm = bm(iBj);
                int iBn = bn(iBj);
                aVarBl.bQ(iBm);
                aVarBl.a(ajVar, i + iBn, f, f2, i2);
                return;
            }
            return;
        }
        b(ajVar, i, f, f2, i2);
    }

    public int hJ() {
        return this.HT;
    }

    public void b(aj ajVar, int i, float f, float f2, int i2) {
        int i3;
        this.HS++;
        if (w.Xz) {
            GameRuntime.be("Sprite paint: frame = " + i);
        }
        int iBr = br(i);
        if (iBr > 0) {
            if (w.XM && (this.HL & 2) != 0) {
                int i4 = 0;
                int i5 = 0;
                while (i5 < iBr) {
                    int iT = t(i, i5);
                    int iU = u(i, i5);
                    int i6 = -1;
                    if (!w.Xi || (iU & 16) == 0) {
                        if (this.Ia != null) {
                            i6 = this.Ia[iT] & 255;
                        } else {
                            i6 = 0;
                        }
                    }
                    if (i6 == 253 && iT < 32) {
                        i4 = 1 << iT;
                        if (w.XN && (this.HL & 32) != 0) {
                            Ir = true;
                            Is = iT;
                            It = (Im & i4) != 0;
                            a(ajVar, i, i5, f, f2, i2);
                        }
                    } else if ((Im & i4) != 0) {
                        a(ajVar, i, i5, f, f2, i2);
                    }
                    i5++;
                    i4 = i4;
                }
            } else {
                for (int i7 = 0; i7 < iBr; i7++) {
                    if (w.XN && (this.HL & 32) != 0) {
                        int iT2 = t(i, i7);
                        int iU2 = u(i, i7);
                        if (!w.Xi || (iU2 & 16) == 0) {
                            if (this.Ia != null) {
                                i3 = this.Ia[iT2] & 255;
                            } else {
                                i3 = 0;
                            }
                            if (i3 == 253) {
                                Ir = true;
                                Is = iT2;
                                It = true;
                            }
                        }
                        a(ajVar, i, i7, f, f2, i2);
                    } else {
                        a(ajVar, i, i7, f, f2, i2);
                    }
                }
            }
            if (w.XN && (this.HL & 32) != 0 && Iu >= 0) {
                GameRuntime.QD.a(this, i, Iu, Iv, Iw, Ix);
                Iu = -1;
            }
        }
        this.HS--;
    }

    /* JADX WARN: Code duplicated, block: B:129:0x03e3  */
    /* JADX WARN: Code duplicated, block: B:137:0x0412  */
    /* JADX WARN: Code duplicated, block: B:138:0x0415 A[PHI: r6
  0x0415: PHI (r6v13 int) = (r6v12 int), (r6v43 int), (r6v43 int) binds: [B:47:0x0116, B:56:0x013e, B:58:0x014c] A[DONT_GENERATE, DONT_INLINE]] */
    public void a(aj ajVar, int i, int i2, float f, float f2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        float f3;
        int i10;
        float f4;
        if (w.Xz) {
            GameRuntime.be("Sprite paint: fmodule = " + i2);
        }
        int iV = v(i, i2);
        int iBD = bD(iV);
        int iBC = bC(iV);
        if (w.Xg && (this.Hz & GLURLConnection.BUFFER_SIZE) != 0 && (this.HL & 16) == 0) {
            this.HI[0] = this.Hb[iV] & 255;
        }
        float f5 = 0.0f;
        float f6 = 0.0f;
        if (!w.WC || ((w.Xi && (iBD & 16) != 0) || this.Hs < 0)) {
            i4 = iBC;
        } else {
            if ((this.HA & 262144) != 0) {
                if ((this.HA & 524288) != 0) {
                    f5 = this.Hq[this.Hs][iBC << 1];
                    f6 = this.Hq[this.Hs][(iBC << 1) + 1];
                } else {
                    f5 = this.Hr[this.Hs][iBC << 1];
                    f6 = this.Hr[this.Hs][(iBC << 1) + 1];
                }
            }
            i4 = this.Hp[this.Hs][iBC] & 65535;
        }
        if (!w.Xi || (iBD & 16) == 0) {
            int i11 = w.WY ? this.GE[i4] & 65535 : this.GG[i4] & 255;
            i5 = w.WY ? this.GF[i4] & 65535 : this.GH[i4] & 255;
            i6 = i11;
        } else {
            i5 = 0;
            i6 = 0;
        }
        float fBw = f5 + bw(iV);
        float fBx = f6 + bx(iV);
        if ((iBD & 4) != 0) {
            i7 = i6;
            i8 = i5;
        } else {
            i7 = i5;
            i8 = i6;
        }
        if ((i3 & 1) != 0) {
            i9 = (iBD & (-8)) | HW[iBD & 7];
            fBw = (-fBw) - i8;
        } else {
            i9 = iBD;
        }
        if ((i3 & 2) != 0) {
            i9 = (i9 & (-8)) | HX[i9 & 7];
            fBx = (-fBx) - i7;
        }
        if ((i3 & 4) != 0) {
            i9 = (i9 & (-8)) | HY[i9 & 7];
            if (w.Zo && ((GameRuntime.TX & 262144) != 0) && (GameRuntime.TY[18][6] % GameRuntime.Rk != 0 || GameRuntime.TY[18][10] != 0)) {
                f3 = fBw;
                i10 = i9;
            } else {
                f3 = (-fBx) - i7;
                fBx = fBw;
                i10 = i9;
            }
        } else {
            f3 = fBw;
            i10 = i9;
        }
        if (w.Zo) {
            if (!((GameRuntime.TX & 262144) != 0) || (w.Xp && HQ == 1)) {
                f4 = f3;
            } else {
                if (GameRuntime.TY[18][6] % GameRuntime.Rk != 0 || GameRuntime.TY[18][10] != 0) {
                    GameRuntime.l(18, 7, (int) (-f3));
                    GameRuntime.l(18, 8, (int) (-fBx));
                }
                if (GameRuntime.TY[18][5] != 0) {
                    f4 = f3;
                } else {
                    int i12 = GameRuntime.TY[18][1];
                    if (i12 == 100) {
                        i12 = GameRuntime.TY[18][3];
                    }
                    int i13 = GameRuntime.TY[18][1];
                    if (i13 == 100) {
                        i13 = GameRuntime.TY[18][4];
                    }
                    int i14 = w.Zp ? Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS : 100;
                    fBx = (fBx * i13) / i14;
                    f4 = (i12 * f3) / i14;
                }
            }
        } else {
            f4 = f3;
        }
        if (w.Zn) {
            if ((GameRuntime.TX & 32768) != 0) {
                float f7 = GameRuntime.TY[15][1];
                float f8 = GameRuntime.TY[15][2];
                float f9 = GameRuntime.TY[15][3];
                float f10 = GameRuntime.TY[15][4];
                float f11 = GameRuntime.TY[15][5];
                float f12 = GameRuntime.TY[15][6];
                int iBy = by(i);
                int iBz = bz(i);
                float fBA = f4 - bA(i);
                float fBB = fBx - bB(i);
                GameRuntime.a(f, f2, f7, f8, f9, f10, f11, f12, fBA, fBB, iBy, iBz);
                HU[0] = GameRuntime.kA();
                HU[1] = GameRuntime.kB();
                GameRuntime.a(f, f2, f7, f8, f9, f10, f11, f12, fBA + i8, fBB, iBy, iBz);
                HU[2] = GameRuntime.kA();
                HU[3] = GameRuntime.kB();
                GameRuntime.a(f, f2, f7, f8, f9, f10, f11, f12, fBA + i8, fBB + i7, iBy, iBz);
                HU[4] = GameRuntime.kA();
                HU[5] = GameRuntime.kB();
                GameRuntime.a(f, f2, f7, f8, f9, f10, f11, f12, fBA, fBB + i7, iBy, iBz);
                HU[6] = GameRuntime.kA();
                HU[7] = GameRuntime.kB();
            } else {
                f += f4;
                f2 += fBx;
            }
        } else {
            f += f4;
            f2 += fBx;
        }
        if (w.XN && (this.HL & 32) != 0 && Ir) {
            Ir = false;
            if (Iu >= 0) {
                GameRuntime.QD.a(this, i, Iu, Iv, Iw, Ix);
            }
            GameRuntime.QD.a(this, i, Is, i10 & 15, (int) f, (int) f2, It);
            Iu = Is;
            Iv = (int) f;
            Iw = (int) f2;
            Ix = It;
        }
        if (!w.Xi || (i10 & 16) == 0) {
            if (hJ() == 1 || hJ() == 0 || this.HS > 1) {
                a(ajVar, iBC, f, f2, i10 & 15, i6, i5);
                return;
            }
            return;
        }
        if (w.Xz) {
            GameRuntime.be("Sprite paint: paint hyper frame");
        }
        if (hJ() == 2 || hJ() == 0) {
            a(ajVar, i4, f, f2, i10 & 15);
        }
    }

    public void b(aj ajVar, int i, int i2, int i3, int i4, int i5) {
        a(ajVar, i, i2, i3, i4, i5, i5, false);
    }

    public void a(aj ajVar, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        if (w.Zo) {
            GameRuntime.h(18, false);
            GameRuntime.l(18, 1, 100);
            GameRuntime.l(18, 3, i5);
            GameRuntime.l(18, 1, 100);
            GameRuntime.l(18, 4, i6);
            a(ajVar, i, (float) i2, (float) i3, i4);
            GameRuntime.i(18, false);
            return;
        }
        GameRuntime.be("PaintFrameScaled: Functionality is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
    }

    private int bW(int i) {
        if (this.GJ != null) {
            for (int i2 = 0; i2 < this.GJ.length; i2 += 2) {
                if (this.GJ[i2] == i) {
                    return this.GJ[i2 + 1];
                }
            }
        }
        return -1;
    }

    /* JADX WARN: Code duplicated, block: B:173:0x031f  */
    /* JADX WARN: Code duplicated, block: B:175:0x0323  */
    /* JADX WARN: Code duplicated, block: B:177:0x0329  */
    /* JADX WARN: Code duplicated, block: B:180:0x032d  */
    /* JADX WARN: Code duplicated, block: B:181:0x0338  */
    /* JADX WARN: Code duplicated, block: B:23:0x0058  */
    /* JADX WARN: Code duplicated, block: B:25:0x005c  */
    /* JADX WARN: Code duplicated, block: B:27:0x0062  */
    /* JADX WARN: Code duplicated, block: B:30:0x0066  */
    /* JADX WARN: Code duplicated, block: B:31:0x0071  */
    /* JADX WARN: Code duplicated, block: B:32:0x0073  */
    private void a(aj ajVar, int i, float f, float f2, float f3, float f4, int i2) {
        int iBH;
        boolean z;
        be beVar;
        boolean z2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float fAbs;
        float fAbs2;
        float f11;
        float f12;
        float f13;
        int i8;
        if (w.Zj) {
            if ((GameRuntime.TX & AdRequest.MAX_CONTENT_URL_LENGTH) != 0) {
                iBH = GameRuntime.dx(this.Ic[i]);
            } else if (w.Zm) {
                if ((GameRuntime.TX & GLURLConnection.BUFFER_SIZE) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    iBH = GameRuntime.TY[14][0];
                } else {
                    iBH = bH(i);
                }
            } else {
                iBH = bH(i);
            }
        } else if (!w.Zm) {
            iBH = bH(i);
        } else {
            if ((GameRuntime.TX & GLURLConnection.BUFFER_SIZE) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                iBH = GameRuntime.TY[14][0];
            } else {
                iBH = bH(i);
            }
        }
        if (GameRuntime.lB()) {
            be beVarLD = GameRuntime.lD();
            GameRuntime.a(GameRuntime.o(this.Gx.substring(this.Gx.lastIndexOf(47)), this.Gy));
            GameRuntime.dk(iBH);
            beVar = beVarLD;
        } else if (((-16777216) & iBH) != 0) {
            GameRuntime.dl(iBH);
            beVar = null;
        } else {
            GameRuntime.dk(iBH);
            beVar = null;
        }
        switch (this.Ia != null ? this.Ia[i] & 255 : 0) {
            case 247:
                int iBW = bW(i);
                if (iBW != -1) {
                    int iDx = (this.GI[iBW + 0] & 65535) | ((this.GI[iBW + 1] << 16) & (-65536));
                    if (w.Zj) {
                        if ((GameRuntime.TX & AdRequest.MAX_CONTENT_URL_LENGTH) != 0) {
                            iDx = GameRuntime.dx(iDx);
                        } else if (w.Zm) {
                            if ((GameRuntime.TX & GLURLConnection.BUFFER_SIZE) != 0) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (z2) {
                                iDx = GameRuntime.TY[14][0];
                            }
                        }
                    } else if (w.Zm) {
                        if ((GameRuntime.TX & GLURLConnection.BUFFER_SIZE) != 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (z2) {
                            iDx = GameRuntime.TY[14][0];
                        }
                    }
                    short s = this.GI[iBW + 2];
                    if ((i2 & 1) == 0 || s >= 2) {
                        i3 = iDx;
                        i4 = iBH;
                    } else {
                        i3 = iBH;
                        i4 = iDx;
                    }
                    if ((i2 & 2) == 0 || s <= 1) {
                        i5 = i3;
                        i6 = i4;
                    } else {
                        i5 = i4;
                        i6 = i3;
                    }
                    if (s == 0) {
                        i7 = 4;
                    } else if (s == 1) {
                        i7 = 8;
                    } else {
                        i7 = s == 2 ? 16 : 32;
                    }
                    if ((i2 & 4) != 0) {
                        if (i7 == 4) {
                            i7 = 16;
                        } else if (i7 == 8) {
                            i7 = 32;
                        } else if (i7 == 16) {
                            i7 = 8;
                        } else if (i7 == 32) {
                            i7 = 4;
                        }
                    }
                    if (((i6 >> 24) & 255) == 255 && ((i5 >> 24) & 255) == 255) {
                        GameRuntime.b(ajVar, f3 + 0, f4 + 0, f, f2, i6, i5, i7);
                    } else {
                        GameRuntime.c(ajVar, f3 + 0, f4 + 0, f, f2, i6, i5, i7);
                    }
                }
                break;
            case 248:
                float f14 = f3 + f;
                float f15 = f4 + f2;
                if ((i2 & 1) != 0) {
                    f5 = f3;
                    f6 = f14;
                } else {
                    f5 = f14;
                    f6 = f3;
                }
                if ((i2 & 2) != 0) {
                    f7 = f4;
                    f8 = f15;
                } else {
                    f7 = f15;
                    f8 = f4;
                }
                if ((i2 & 4) != 0) {
                    f9 = f6;
                    f10 = f5;
                } else {
                    f9 = f5;
                    f10 = f6;
                }
                GameRuntime.a(ajVar, f10, f8, f9, f7, true);
                break;
            case 249:
            case 250:
                int iBW2 = bW(i);
                if (iBW2 != -1) {
                    float f16 = this.GI[iBW2 + 0];
                    float f17 = this.GI[iBW2 + 1];
                    float f18 = this.GI[iBW2 + 2];
                    float f19 = this.GI[iBW2 + 3];
                    if (w.Zo) {
                        if ((GameRuntime.TX & 262144) != 0) {
                            int i9 = GameRuntime.TY[18][1];
                            if (i9 == 100) {
                                i9 = GameRuntime.TY[18][3];
                            }
                            int i10 = GameRuntime.TY[18][1];
                            if (i10 == 100) {
                                i10 = GameRuntime.TY[18][4];
                            }
                            int i11 = w.Zp ? Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS : 100;
                            f16 = (f16 * i9) / i11;
                            f17 = (f17 * i10) / i11;
                            f18 = (i9 * f18) / i11;
                            f19 = (i10 * f19) / i11;
                        }
                    }
                    if ((i2 & 1) != 0) {
                        f16 = -f16;
                        f18 = -f18;
                        fAbs = f3 + (Math.abs(f16) > Math.abs(f18) ? Math.abs(f16) : Math.abs(f18));
                    } else {
                        fAbs = f3;
                    }
                    if ((i2 & 2) != 0) {
                        f17 = -f17;
                        f19 = -f19;
                        fAbs2 = f4 + (Math.abs(f17) > Math.abs(f19) ? Math.abs(f17) : Math.abs(f19));
                    } else {
                        fAbs2 = f4;
                    }
                    if ((i2 & 4) != 0) {
                        float f20 = fAbs2 - f4;
                        fAbs2 = f4 + (fAbs - f3);
                        fAbs = (f3 - f20) + (Math.abs(f17) > Math.abs(f19) ? Math.abs(f17) : Math.abs(f19));
                        f13 = -f17;
                        f11 = f18;
                        f12 = -f19;
                        f17 = f16;
                    } else {
                        f11 = f19;
                        f12 = f18;
                        f13 = f16;
                    }
                    if ((this.Ia != null ? this.Ia[i] & 255 : 0) != 250) {
                        GameRuntime.a(ajVar, fAbs, fAbs2, fAbs + f13, fAbs2 + f17, fAbs + f12, fAbs2 + f11, true);
                    } else {
                        GameRuntime.a(ajVar, fAbs, fAbs2, fAbs + f13, fAbs2 + f17, true);
                        GameRuntime.a(ajVar, fAbs + f13, fAbs2 + f17, fAbs + f12, fAbs2 + f11, true);
                        GameRuntime.a(ajVar, fAbs, fAbs2, fAbs + f12, fAbs2 + f11, true);
                    }
                }
                break;
            case 251:
            case 252:
                int iBW3 = bW(i);
                if (iBW3 != -1) {
                    short s2 = this.GI[iBW3 + 0];
                    short s3 = this.GI[iBW3 + 1];
                    if ((i2 & 1) != 0) {
                        i8 = s2;
                        i8 = 90 - s2;
                    }
                    i8 = s2;
                    int i12 = i8;
                    int i13 = s3;
                    if ((i2 & 2) != 0) {
                        i12 = -i8;
                        i13 = -s3;
                    }
                    int i14 = i12;
                    if ((i2 & 4) != 0) {
                        i14 = i12 - 90;
                    }
                    if ((this.Ia != null ? this.Ia[i] & 255 : 0) != 252) {
                        GameRuntime.a(ajVar, f3, f4, f, f2, i14, i13, true);
                    } else {
                        GameRuntime.b(ajVar, f3, f4, f, f2, i14, i13, true);
                    }
                }
                break;
            case 254:
                if ((((-16777216) & iBH) == -16777216 || ((-16777216) & iBH) == 0) && w.Zl) {
                    if ((GameRuntime.TX & 4096) != 0) {
                        GameRuntime.dl(iBH | (GameRuntime.TY[12][1] << 24));
                    }
                }
                GameRuntime.b(ajVar, f3, f4, f, f2, true);
                break;
            case 255:
                float f21 = f - 1.0f;
                float f22 = f2 - 1.0f;
                if (w.Zl) {
                    if ((GameRuntime.TX & 4096) != 0) {
                        GameRuntime.dl((iBH & 16777215) | (GameRuntime.TY[12][1] << 24));
                    }
                }
                GameRuntime.c(ajVar, f3, f4, f21, f22, true);
                break;
        }
        if (!GameRuntime.lB() || beVar == null) {
            return;
        }
        GameRuntime.a(beVar);
    }

    private static boolean a(aj ajVar, float f, float f2, float f3, float f4) {
        int i = GameRuntime.SE;
        int i2 = GameRuntime.SF;
        int i3 = GameRuntime.SG;
        int i4 = GameRuntime.SH;
        float fKw = GameRuntime.kw();
        float fKx = GameRuntime.kx();
        if (fKw != 1.0f || fKx != 1.0f) {
            f *= fKw;
            f2 *= fKx;
            f3 *= fKw;
            f4 *= fKx;
        }
        if (w.Zo) {
            if ((GameRuntime.TX & 262144) != 0) {
                int i5 = GameRuntime.TY[18][1];
                if (i5 == 100) {
                    i5 = GameRuntime.TY[18][3];
                }
                int i6 = GameRuntime.TY[18][1];
                if (i6 == 100) {
                    i6 = GameRuntime.TY[18][4];
                }
                f3 = (i5 * f3) / 100.0f;
                f4 = (i6 * f4) / 100.0f;
                if (GameRuntime.TY[18][5] != 0) {
                    f = (i5 * f) / 100.0f;
                    f2 = (i5 * f2) / 100.0f;
                }
                int i7 = GameRuntime.TY[18][6];
                if (GameRuntime.TY[18][10] != 0) {
                    i7 = GameRuntime.TY[18][10] >> w.VP;
                }
                if (i7 % GameRuntime.Rk != 0) {
                    int i8 = (int) f3;
                    int i9 = (int) f4;
                    int[] iArr = Ij;
                    GameRuntime.c(GameRuntime.Rp != null, "!!ERROR!! Math_Sin.s_math_cosTable is null, call Math_Init first");
                    GameRuntime.b(GameRuntime.cQ(GameRuntime.Rs - i7), GameRuntime.cQ(i7), i8, i9, iArr);
                    f3 = Ij[0];
                    f4 = Ij[1];
                }
            }
        }
        if (w.Zn) {
            if ((GameRuntime.TX & 32768) != 0) {
                return true;
            }
        }
        return f + f3 >= ((float) i) && f2 + f4 >= ((float) i2) && f < ((float) (i + i3)) && f2 < ((float) (i2 + i4));
    }

    public void a(aj ajVar, int i, float f, float f2, int i2, int i3, int i4) {
        Ij[0] = i3;
        Ij[1] = i4;
        a(ajVar, i, f, f2, i2, Ij);
    }

    /* JADX WARN: Code duplicated, block: B:121:0x027e A[PHI: r3 r10
  0x027e: PHI (r3v34 float) = (r3v6 float), (r3v6 float), (r3v37 float) binds: [B:68:0x0128, B:73:0x0133, B:85:0x0170] A[DONT_GENERATE, DONT_INLINE]
  0x027e: PHI (r10v4 float) = (r10v1 float), (r10v1 float), (r10v6 float) binds: [B:68:0x0128, B:73:0x0133, B:85:0x0170] A[DONT_GENERATE, DONT_INLINE]] */
    private void a(aj ajVar, int i, float f, float f2, int i2, int[] iArr) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        if (w.XD && Il) {
            return;
        }
        int i3 = (!w.WC || this.Hs < 0) ? i : this.Hp[this.Hs][i] & 65535;
        float f11 = iArr[0];
        float f12 = iArr[1];
        if ((i2 & 4) != 0) {
            f3 = f11;
            f4 = f12;
        } else {
            f3 = f12;
            f4 = f11;
        }
        if (w.Xp) {
            if (HQ == 1) {
                if (w.Zo) {
                    if ((GameRuntime.TX & 262144) != 0) {
                        if (GameRuntime.TY[18][5] != 0) {
                            int i4 = GameRuntime.TY[18][1];
                            if (i4 == 100) {
                                i4 = GameRuntime.TY[18][3];
                            }
                            int i5 = GameRuntime.TY[18][1];
                            if (i5 == 100) {
                                i5 = GameRuntime.TY[18][4];
                            }
                            f = (i4 * f) / 100.0f;
                            f2 = (i5 * f2) / 100.0f;
                            f4 = (i5 * f4) / 100.0f;
                            f3 = (i5 * f4) / 100.0f;
                        }
                    }
                }
                if ((this.Ia != null ? this.Ia[i3] & 255 : 0) == 253) {
                    f -= f4 / 2.0f;
                    f2 -= f3 / 2.0f;
                }
                if (f < If) {
                    If = (int) f;
                }
                if (f2 < Ig) {
                    Ig = (int) f2;
                }
                if (f + f4 > Ih) {
                    Ih = (int) (f + f4);
                }
                if (f2 + f3 > Ii) {
                    Ii = (int) (f3 + f2);
                    return;
                }
                return;
            }
        } else if (HQ == 1) {
            GameRuntime.c(false, "Error: Trying to use COMPUTE RECT OPERATION but sprite_useOperationRect is FALSE!!!");
        }
        if ((this.Ia != null ? this.Ia[i3] & 255 : 0) == 0 || ajVar == null) {
            if ((this.Hz & 536870912) == 0) {
                GameRuntime.c(false, "Only single image for all modules is supported in Android OpenGL. Please export your sprite with BS_SINGLE_IMAGE_INDEX flag");
                return;
            }
            if ((i2 & 4) != 0) {
                f5 = f4;
                f6 = f3;
            } else {
                f5 = f3;
                f6 = f4;
            }
            if (!hx() || a(ajVar, f, f2, f6, f5)) {
                int iBs = bs(i3);
                int iBt = bt(i3);
                int iBE = bE(i3);
                int i6 = this.HI[iBE];
                if (bp(8388608)) {
                    iBE = 0;
                }
                ay ayVar = this.HR[i6][iBE];
                if ((this.HL & 64) != 0) {
                    GameRuntime.ah(ayVar.nj());
                } else {
                    GameRuntime.ah(false);
                }
                if (w.Zn) {
                    if ((GameRuntime.TX & 32768) != 0) {
                        GameRuntime.a(ajVar, ayVar, iBs, iBt, (int) f6, (int) f5, HV[i2 & 7], HU[0], HU[1], HU[2], HU[3], HU[4], HU[5], HU[6], HU[7]);
                        return;
                    }
                }
                GameRuntime.a(ajVar, ayVar, iBs, iBt, (int) f6, (int) f5, HV[i2 & 7], f, f2, 20);
                return;
            }
            return;
        }
        if (w.Zo) {
            if ((GameRuntime.TX & 262144) != 0) {
                int i7 = GameRuntime.TY[18][1];
                if (i7 == 100) {
                    i7 = GameRuntime.TY[18][3];
                }
                int i8 = GameRuntime.TY[18][1];
                if (i8 == 100) {
                    i8 = GameRuntime.TY[18][4];
                }
                int i9 = w.Zp ? Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS : 100;
                f4 = (i7 * f4) / i9;
                f3 = (f3 * i8) / i9;
                if (GameRuntime.TY[18][5] != 0) {
                    float f13 = (i7 * f) / i9;
                    float f14 = (i8 * f2) / i9;
                    f7 = f3;
                    f8 = f4;
                    f9 = f14;
                    f10 = f13;
                } else {
                    f7 = f3;
                    f8 = f4;
                    f9 = f2;
                    f10 = f;
                }
            } else {
                f7 = f3;
                f8 = f4;
                f9 = f2;
                f10 = f;
            }
        } else {
            f7 = f3;
            f8 = f4;
            f9 = f2;
            f10 = f;
        }
        a(ajVar, i3, f8, f7, f10, f9, i2);
    }

    public void hK() {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.Jk.size()) {
                ((cb) this.Jk.get(i2)).Destroy();
                i = i2 + 1;
            } else {
                this.Jk = null;
                return;
            }
        }
    }
}
