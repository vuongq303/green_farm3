package com.quanhoang.greenfarm3.S800x480;

import android.opengl.GLES20;
import android.os.SystemClock;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.login.widget.ToolTipPopup;
import com.gameloft.glads.AndroidWebView;
import com.gameloft.glads.GLURLConnection;
import com.google.android.gms.ads.AdRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: loaded from: classes.dex */
public abstract class GameRuntime extends javax.microedition.lcdui.f implements Runnable {
    // DEX has no Java checked-exception restriction; preserve the original thrown instance.
    @SuppressWarnings("unchecked")
    private static <Failure extends Throwable> RuntimeException propagateChecked(Throwable failure) throws Failure {
        throw (Failure) failure;
    }

    public static int QA;
    public static int QB;
    static int QC;
    public static GameRuntime QD;
    static int QG;
    static int QH;
    static int QI;
    static int QJ;
    static int QK;
    static int QL;
    public static int QN;
    private static Hashtable QO;
    private static Hashtable QP;
    private static boolean QX;
    private static String[] QY;
    private static long[] QZ;
    private static ay Ql;
    public static boolean Qm;
    public static int Qn;
    public static long Qo;
    public static boolean Qp;
    static javax.microedition.lcdui.r Qs;
    private static boolean Qt;
    public static MIDlet Qu;
    public static int Qy;
    private static long Qz;
    private static int RC;
    private static int RD;
    private static String[][] RE;
    private static int[] RF;
    private static String[] RG;
    private static int RH;
    public static String RI;
    private static InputStream RJ;
    private static int RK;
    private static byte[] RL;
    private static int RM;
    private static int RN;
    public static short RO;
    private static int[] RP;
    private static short RQ;
    private static short[] RR;
    private static int RS;
    public static int RT;
    public static boolean RU;
    private static byte[] RV;
    private static byte[][] RW;
    private static String RY;
    private static int[] RZ;
    private static int Ra;
    private static int Rb;
    private static long Rc;
    private static long Rd;
    private static int[][] Rf;
    private static String Rg;
    private static String[] Rh;
    private static int Ri;
    private static a Rj;
    public static Random Rm;
    private static int Rn;
    private static int Ro;
    public static int[] Rp;
    private static int[] Rq;
    private static int SA;
    public static int SB;
    private static ay SC;
    public static int SE;
    public static int SF;
    public static int SG;
    public static int SH;
    private static FloatBuffer SI;
    private static float[] SJ;
    private static ShortBuffer SK;
    public static short[] SL;
    public static int SM;
    private static int[] Sa;
    private static int[] Sb;
    private static int[][] Sc;
    private static int[] Sd;
    private static int[] Se;
    private static String Sg;
    private static boolean Sh;
    private static String[] Si;
    private static String[] Sj;
    private static int[] Sk;
    static byte[] Sl;
    static int Sm;
    static long Sn;
    static long So;
    static int Sp;
    static byte[] Sq;
    static int Sr;
    private static short[] Ss;
    private static aj Sy;
    private static int Sz;
    private static int[] TB;
    private static boolean TC;
    private static int TD;
    private static byte[] TF;
    private static int[] TG;
    private static String[] TH;
    private static int[] TI;
    private static byte[][] TJ;
    static int[] TK;
    private static int[][] TL;
    private static String[][] TM;
    private static javax.microedition.b.b TN;
    private static HashMap<String, bl> TO;
    private static bm TP;
    private static ArrayList<String> TQ;
    private static boolean TT;
    static final boolean TU;
    public static final boolean TV;
    public static boolean TW;
    public static int TX;
    public static int[][] TY;
    public static ay TZ;
    static int[][] Tb;
    private static int[] Tc;
    public static float[] Th;
    private static float Ti;
    private static float Tj;
    public static long Tn;
    public static long To;
    private static InputStream Tq;
    static int[] Tt;
    static int[] Tu;
    static long[] Tv;
    static long[] Tw;
    static byte[] Tx;
    static byte[] Ty;
    private static int[] UA;
    private static int[] UB;
    private static boolean[] UC;
    private static boolean[] UD;
    private static boolean[] UE;
    private static final char[] UF;
    private static int[] UG;
    public static long UH;
    private static Hashtable UI;
    public static boolean UJ;
    private static be UK;
    private static Vector UL;
    public static String UM;
    public static int UN;
    private static cd UO;
    private static cd UP;
    private static javax.microedition.lcdui.u[] UQ;
    private static ay[] UR;
    private static int[] US;
    private static String[] UT;
    static int UU;
    static int UV;
    static int UW;
    static int[][] UX;
    private static final char[][] UY;
    private static final int UZ;
    public static aj Ua;
    private static ay Ub;
    public static int Uc;
    public static int Ud;
    public static boolean Ue;
    public static int Uf;
    public static int Ug;
    public static int Uh;
    public static int Ui;
    private static int Uj;
    private static int Uk;
    private static int Ul;
    private static int Um;
    private static int Un;
    private static int Uo;
    private static int Up;
    private static int Uq;
    private static boolean Ur;
    private static boolean Us;
    private static boolean Ut;
    protected static int Uu;
    private static int[] Uv;
    private static int[] Uw;
    private static int[] Ux;
    private static int[] Uy;
    private static int[] Uz;
    private static int Va;
    private long Qw;
    public static aj Qj = null;
    public static javax.microedition.lcdui.u Qk = javax.microedition.lcdui.u.aki();
    private static int Qq = w.screenWidth;
    private static int Qr = w.screenHeight;
    private static int Qv = Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS / w.Vn;
    public static int Qx = -1;
    static boolean QE = false;
    public static String QF = null;
    static int QM = -9999;
    private static int QQ = 25;
    private static int QR = w.Zu;
    private static int QS = w.Zv;
    private static int QT = w.Zw;
    private static int QU = w.Zx;
    private static boolean QV = w.Zy;
    private static boolean QW = true;
    private static int Re = -1;
    public static final int Rk = 1 << w.VP;
    static final int Rl = Rk >> 1;
    public static final int Rr = 1 << w.VQ;
    public static final int Rs = (90 * Rr) / 360;
    public static final int Rt = (180 * Rr) / 360;
    public static final int Ru = (270 * Rr) / 360;
    public static final int Rv = (360 * Rr) / 360;
    public static float Rw = 3.1415927f;
    public static final int Rx = 1686629713 >> (29 - w.VP);
    public static final int Ry = 1459366444 >> (29 - w.VP);
    public static final int Rz = M(180 << w.VP, Rx);
    public static final int RA = M(1 << w.VQ, 360);
    private static int[][] RB = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 2, 2);
    private static int RX = -1;
    private static int Sf = 0;
    private static int St = 0;
    private static int Su = -1;
    private static int Sv = 3;
    private static boolean Sw = false;
    private static boolean Sx = false;
    private static boolean SD = true;
    private static int SN = 2;
    public static int SO = 255;
    public static int SP = 255;
    public static int SQ = 255;
    public static int SR = 255;
    private static float SS = 1.0f;
    public static float ST = 0.0f;
    public static float SU = 1.0f;
    private static float SV = 1.0f;
    private static float SW = 1.0f;
    private static boolean SX = false;
    private static float SY = 1.0f;
    private static float SZ = 1.0f;
    private static int Ta = -1;
    public static int Td = 255;
    public static int Te = 255;
    public static int Tf = 255;
    public static int Tg = 255;
    private static int Tk = 0;
    private static int Tl = 0;
    private static boolean Tm = false;
    private static int Tp = 0;
    private static int Tr = 0;
    private static final char[] Ts = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    static byte[] Tz = {-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final char[] TA = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static String TE = AndroidWebView.UTF_8;
    private static boolean TR = false;
    private static int TS = 0;

    public static final InputStream a(Class cls, String str) {
        return com.gameloft.android.wrapper.au.getResourceAsStream(str);
    }

    public abstract void jC();

    static {
        TU = w.YX || w.YY || w.YZ || w.Za || w.Zb || w.Zc || w.Zf;
        TV = w.Ze || w.Zf || w.Zj || w.Zi || w.Zk || w.Zl || w.Zo;
        TW = false;
        TX = 0;
        TY = (int[][]) null;
        TZ = null;
        Ua = null;
        Ub = null;
        Uc = 0;
        Ud = 0;
        Ue = false;
        UF = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        UH = 0L;
        UI = new Hashtable();
        UJ = false;
        UO = new cd();
        UP = new cd();
        UU = 0;
        UV = 1;
        UW = 2;
        UY = new char[][]{new char[]{3, 65152}, new char[]{1, 65153}, new char[]{1, 65155}, new char[]{1, 65157}, new char[]{1, 65159}, new char[]{2, 65161}, new char[]{1, 65165}, new char[]{2, 65167}, new char[]{1, 65171}, new char[]{2, 65173}, new char[]{2, 65177}, new char[]{2, 65181}, new char[]{2, 65185}, new char[]{2, 65189}, new char[]{1, 65193}, new char[]{1, 65195}, new char[]{1, 65197}, new char[]{1, 65199}, new char[]{2, 65201}, new char[]{2, 65205}, new char[]{2, 65209}, new char[]{2, 65213}, new char[]{2, 65217}, new char[]{2, 65221}, new char[]{2, 65225}, new char[]{2, 65229}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{4, 0}, new char[]{2, 65233}, new char[]{2, 65237}, new char[]{2, 65241}, new char[]{2, 65245}, new char[]{2, 65249}, new char[]{2, 65253}, new char[]{2, 65257}, new char[]{1, 65261}, new char[]{1, 65263}, new char[]{2, 65265}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{5, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{5, 0}, new char[]{1, 64336}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{2, 64358}, new char[]{2, 64350}, new char[]{2, 64338}, new char[]{3, 0}, new char[]{3, 0}, new char[]{2, 64342}, new char[]{2, 64354}, new char[]{2, 64346}, new char[]{3, 0}, new char[]{3, 0}, new char[]{2, 64374}, new char[]{2, 64370}, new char[]{3, 0}, new char[]{2, 64378}, new char[]{2, 64382}, new char[]{1, 64392}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{1, 64388}, new char[]{1, 64386}, new char[]{1, 64390}, new char[]{3, 0}, new char[]{3, 0}, new char[]{1, 64396}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{1, 64394}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{2, 64362}, new char[]{3, 0}, new char[]{2, 64366}, new char[]{3, 0}, new char[]{3, 0}, new char[]{2, 64398}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{2, 64467}, new char[]{3, 0}, new char[]{2, 64402}, new char[]{3, 0}, new char[]{2, 64410}, new char[]{3, 0}, new char[]{2, 64406}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{1, 64414}, new char[]{2, 64416}, new char[]{3, 0}, new char[]{3, 0}, new char[]{2, 64426}, new char[]{3, 0}, new char[]{1, 64420}, new char[]{2, 64422}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{1, 64480}, new char[]{1, 64473}, new char[]{1, 64471}, new char[]{1, 64475}, new char[]{1, 64482}, new char[]{3, 0}, new char[]{1, 64478}, new char[]{2, 64508}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{3, 0}, new char[]{1, 64430}};
        UZ = (UY.length + 1569) - 1;
        Va = 7;
    }

    public void jD() {
    }

    public void a(a aVar, int i, int i2, int i3, int i4, int i5, boolean z) {
    }

    public void a(a aVar, char c, int i, int i2) {
    }

    public void a(a aVar, int i, int i2, int i3, int i4, boolean z) {
    }

    public GameRuntime(Object obj, Object obj2) {
        be("GLLib.constructor");
        QD = this;
        Qn = -1;
        Qt = true;
        Qu = (MIDlet) obj;
        Qs = (javax.microedition.lcdui.r) obj2;
        jH();
        jI();
        Qz = w.Vl ? (SystemClock.elapsedRealtime() + Tn) - To : System.currentTimeMillis();
        this.Qw = Qz;
    }

    protected void Init() {
        Vector vectorLA;
        be("GLLib.init");
        if (Qn < 0) {
            Qq = w.screenWidth;
            Qr = w.screenHeight;
            if (w.VO) {
                QQ = 62;
            }
            la();
            kQ();
            if (w.ZV && (vectorLA = lA()) != null) {
                int size = vectorLA.size();
                float[] fArr = new float[13];
                if (size == 13) {
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= 13) {
                            break;
                        }
                        fArr[i2] = Float.parseFloat((String) vectorLA.elementAt(i2));
                        i = i2 + 1;
                    }
                    UK = a(new cc(fArr[0], fArr[1], fArr[2]), new cc(fArr[3], fArr[4], fArr[5]), new cc(fArr[6], fArr[7], fArr[8]), new cc(fArr[9], fArr[10], fArr[11]), fArr[12]);
                } else {
                    int i3 = 0;
                    while (i3 < size) {
                        if (i3 + 15 <= size) {
                            int i4 = i3 + 1;
                            String str = (String) vectorLA.elementAt(i3);
                            i3 = i4 + 1;
                            int i5 = Integer.parseInt((String) vectorLA.elementAt(i4));
                            int i6 = 0;
                            while (i6 < 13) {
                                fArr[i6] = Float.parseFloat((String) vectorLA.elementAt(i3));
                                i6++;
                                i3++;
                            }
                            cc ccVar = new cc(fArr[0], fArr[1], fArr[2]);
                            cc ccVar2 = new cc(fArr[3], fArr[4], fArr[5]);
                            cc ccVar3 = new cc(fArr[6], fArr[7], fArr[8]);
                            cc ccVar4 = new cc(fArr[9], fArr[10], fArr[11]);
                            float f = fArr[12];
                            if (str.equals("/all")) {
                                UK = a(ccVar, ccVar2, ccVar3, ccVar4, f);
                            } else {
                                if (UL == null) {
                                    UL = new Vector();
                                }
                                v vVar = new v(this, str, i5);
                                vVar.Vd = a(ccVar, ccVar2, ccVar3, ccVar4, f);
                                UL.addElement(vVar);
                            }
                        }
                    }
                }
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis = (SystemClock.elapsedRealtime() + Tn) - To;
            }
            if (Rm == null) {
                Rm = new Random(jCurrentTimeMillis);
            } else {
                Rm.setSeed(jCurrentTimeMillis);
            }
            Qn = 0;
            kG();
            Qt = false;
            aa.lV();
        }
    }

    protected void jE() {
        be("GLLib.deInit");
        RW = (byte[][]) null;
        if (w.Vf) {
        }
    }

    protected void Pause() {
        be("GLLib.Pause Call");
        if (!Qm) {
            Qm = true;
            be("GLLib.Pausing");
            aa.lU();
            onPause();
        }
    }

    protected void Resume() {
        be("GLLib.Resume Call");
        if (Qm) {
            be("GLLib.Resuming");
            aa.lV();
            onResume();
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis = (SystemClock.elapsedRealtime() + Tn) - To;
            }
            Qo = jCurrentTimeMillis;
            Qz = jCurrentTimeMillis;
            this.Qw = jCurrentTimeMillis;
            Qm = false;
            jH();
            Qp = true;
            if (w.Vj && !Qt) {
                aka();
            }
            jM();
        }
    }

    @Override // javax.microedition.lcdui.f
    public void jF() {
        Pause();
    }

    @Override // javax.microedition.lcdui.f
    public void jG() {
        Resume();
    }

    protected void jH() {
        co(true);
        if (Qs != null && Qs.akf() != this) {
            Qs.a(this);
        }
    }

    protected static void jI() {
        jL();
        QO.put(new Integer(48), new Integer(6));
        QO.put(new Integer(49), new Integer(7));
        QO.put(new Integer(50), new Integer(1));
        QO.put(new Integer(51), new Integer(9));
        QO.put(new Integer(52), new Integer(3));
        QO.put(new Integer(53), new Integer(5));
        QO.put(new Integer(54), new Integer(4));
        QO.put(new Integer(55), new Integer(13));
        QO.put(new Integer(56), new Integer(2));
        QO.put(new Integer(57), new Integer(15));
        QO.put(new Integer(35), new Integer(17));
        QO.put(new Integer(42), new Integer(16));
        QO.put(new Integer(w.VG), new Integer(18));
        QO.put(new Integer(w.VH), new Integer(19));
        QO.put(new Integer(w.VN), new Integer(24));
        QP.put(new Integer(w.VI), new Integer(5));
        QP.put(new Integer(w.VJ), new Integer(1));
        QP.put(new Integer(w.VK), new Integer(2));
        QP.put(new Integer(w.VL), new Integer(3));
        QP.put(new Integer(w.VM), new Integer(4));
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    private void jJ() throws InterruptedException {
        aj ajVar;
        if (w.Zr) {
            kW();
        }
        if (w.Zs) {
            jS();
        }
        if (Qm) {
            long j = this.Qw;
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis = (SystemClock.elapsedRealtime() + Tn) - To;
            }
            this.Qw = Math.min(j, jCurrentTimeMillis);
            if (w.Vo) {
                Thread.sleep(1L);
                return;
            } else {
                Thread.yield();
                return;
            }
        }
        if (w.Vz) {
            ajVar = Qj;
            ay ayVar = Ql;
            if (ayVar == null) {
                Qj = null;
            } else {
                Qj = ayVar.nh();
            }
            GLES20.glViewport(0, 0, kH(), kI());
        } else {
            ajVar = null;
        }
        a(Qj);
        if (w.Zr) {
            bH("UPDATE");
            jD();
            bI("UPDATE");
        } else {
            jD();
        }
        if (w.XY) {
            try {
                s.ae(true);
            } catch (Exception e) {
                be("Error in Federation update!");
                d(e);
            }
        }
        kC();
        if (w.Vz) {
            int iKH = kH();
            int iKI = kI();
            int scaleX = (int) (iKH * getScaleX());
            int scaleY = (int) (iKI * getScaleY());
            Qj = ajVar;
            f(0, 0, iKH, iKI);
            GLES20.glViewport(ajV(), ajW(), scaleX, scaleY);
            a(Qj, Ql, 0, 0, 20, true);
            kC();
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + Tn) - To;
        }
        this.Qw = Math.min(this.Qw, jCurrentTimeMillis2);
        if (!w.Vp || Qx < 0) {
            if (!w.Vo || (w.Vl && Tm)) {
                while (jCurrentTimeMillis2 - this.Qw < Qv) {
                    Thread.yield();
                    jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (w.Vl) {
                        jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + Tn) - To;
                    }
                    this.Qw = Math.min(this.Qw, jCurrentTimeMillis2);
                }
            } else {
                Thread.sleep(Math.max(1L, ((long) Qv) - (jCurrentTimeMillis2 - this.Qw)));
            }
        } else if (Qx == 0) {
            Thread.yield();
        } else {
            Thread.sleep(Qx);
        }
        long jCurrentTimeMillis3 = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis3 = (SystemClock.elapsedRealtime() + Tn) - To;
        }
        this.Qw = jCurrentTimeMillis3;
    }

    @Override // javax.microedition.lcdui.f, javax.microedition.lcdui.t
    public void onDrawFrame(GL10 gl10) {
        if (Qn >= 0) {
            try {
                if (w.Vy) {
                    GLES20.glClear((w.Vw ? 0 : 1024) | 16640);
                }
                if (w.Vw) {
                    f(SE, SF, SG, SH);
                }
                jJ();
                if (w.VA) {
                    be("OpenGL GPU paint calls count: " + Tk);
                }
            } catch (Exception e) {
                d(e);
            }
            Qp = false;
            return;
        }
        jE();
        Qu.akD();
    }

    @Override // javax.microedition.lcdui.f, javax.microedition.lcdui.t
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        b("-----------------------onSurfaceCreated--------------------------", "OPEN_GL", 3);
        a(gl10);
    }

    @Override // javax.microedition.lcdui.f, javax.microedition.lcdui.t
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        b("-----------------------onSurfaceChanged--------------------------", "OPEN_GL", 3);
        ku();
    }

    private void a(aj ajVar) {
        if (w.Zr) {
            bH("RENDER");
        }
        if (!Qm && !Qt) {
            Qt = true;
            jK();
            aj(false);
            lb();
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis = (SystemClock.elapsedRealtime() + Tn) - To;
            }
            Qo = jCurrentTimeMillis;
            if (w.Vg) {
                Qy = (int) (Qo - Qz);
                if (Qy < 0) {
                    Qy = 0;
                }
                if (Qy > 1000) {
                    Qy = Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
                }
                Qz = Qo;
                QA += Qy;
                QB = (100000 * QC) / (QA + 1);
            }
            QC++;
            Tk = 0;
            try {
                jC();
                if (w.Vr) {
                    jV();
                }
                if (w.ZC) {
                    jW();
                }
            } catch (Exception e) {
                be("!!FATAL ERROR!! in Game_paint()." + e);
                Qn = -1;
            }
            Qt = false;
            if (w.Zr) {
                bI("RENDER");
            }
            if (w.Zs) {
                jT();
            }
        }
    }

    public static final void bd(String str) {
        QF = str;
        if (w.Vh) {
            new Thread(new bj()).start();
            return;
        }
        if (QF != null) {
            Qu.jf(QF);
        }
        if (w.Vi) {
            be("GLLib.quit");
            Qn = -1;
            Qu.akD();
        }
    }

    @Override // javax.microedition.lcdui.f
    protected void cM(int i) {
        int iCO = 1 << cO(i);
        QK |= iCO;
        QJ = iCO | QJ;
    }

    @Override // javax.microedition.lcdui.f
    protected void cN(int i) {
        int iCO = 1 << cO(i);
        QL |= iCO;
        QJ = (iCO ^ (-1)) & QJ;
    }

    private void jK() {
        if (Qn != -1) {
            QG = QK;
            QH = QL;
            QI = QJ;
            QK = 0;
            QL = 0;
            if (QN > 0) {
                if (w.Vg && QN != Integer.MAX_VALUE) {
                    QN -= Qy;
                }
                jM();
            }
        }
    }

    public static void jL() {
        QP = new Hashtable();
        QO = new Hashtable();
    }

    public static void b(boolean z, int i, int i2) {
        Hashtable hashtable;
        Integer num = new Integer(i);
        if (z) {
            hashtable = QP;
        } else {
            hashtable = QO;
        }
        if (((Integer) hashtable.get(num)) != null) {
            hashtable.remove(num);
        }
        hashtable.put(num, new Integer(i2));
    }

    private byte cO(int i) {
        Integer num = new Integer(i);
        if (QO == null) {
            return (byte) 0;
        }
        Integer num2 = (Integer) QO.get(num);
        if (num2 != null) {
            return num2.byteValue();
        }
        Integer num3 = (Integer) QP.get(num);
        if (num3 != null) {
            return num3.byteValue();
        }
        return (byte) 0;
    }

    public static void jM() {
        QG = 0;
        QH = 0;
        QI = 0;
        QJ = 0;
        QK = 0;
        QL = 0;
    }

    public static int jN() {
        if (QG == 0) {
            return -1;
        }
        int i = QQ;
        do {
            i--;
            if (i < 0) {
                return -1;
            }
        } while ((QG & (1 << i)) == 0);
        return i;
    }

    public static int jO() {
        if (QH == 0) {
            return -1;
        }
        int i = QQ;
        do {
            i--;
            if (i < 0) {
                return -1;
            }
        } while ((QH & (1 << i)) == 0);
        return i;
    }

    public static void be(String str) {
        if (Rg == null) {
            Rg = Qu == null ? null : Qu.bY("MIDlet-Name");
        }
        b(str, Rg, 4);
    }

    public static void b(String str, String str2, int i) {
        if (str != null) {
            if (w.Zs && w.Zt) {
                r(str);
            }
            if (w.Zz && w.ZA) {
                q(str);
            }
        }
    }

    public static void q(Object obj) {
        if (w.Zz) {
            if (Rh == null) {
                Rh = new String[300];
            }
            if (Ri == Rh.length) {
                jP();
            }
            synchronized (Rh) {
                String[] strArr = Rh;
                int i = Ri;
                Ri = i + 1;
                strArr[i] = obj.toString();
            }
        }
    }

    public static void jP() {
        if (w.Zz) {
            try {
                if (System.getProperty("microedition.io.file.FileConnection.version") != null) {
                    String property = System.getProperty("fileconn.dir.photos");
                    if (!((javax.microedition.a.a.a) javax.microedition.a.c.jd(property)).exists()) {
                        r("Filelog. Write to file: couldn't open photo dir");
                    } else {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (w.Vl) {
                            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + Tn) - To;
                        }
                        Date date = new Date(jCurrentTimeMillis);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        javax.microedition.a.a.a aVar = (javax.microedition.a.a.a) javax.microedition.a.c.V(property + "/log_" + (((((("" + calendar.get(1)) + "." + calendar.get(2)) + "." + calendar.get(5)) + "_" + calendar.get(11)) + "." + calendar.get(12)) + "." + calendar.get(13)) + ".txt", 3);
                        if (!aVar.exists()) {
                            aVar.create();
                        }
                        DataOutputStream dataOutputStreamAjR = aVar.ajR();
                        for (int i = 0; i < Ri; i++) {
                            dataOutputStreamAjR.write((Rh[i] + "\n").getBytes());
                        }
                        dataOutputStreamAjR.close();
                        aVar.close();
                    }
                }
            } catch (Exception e) {
                if (w.Zs) {
                    r("Filelog. Write to file failed");
                    r(e.toString());
                    jU();
                }
            }
            Ri = 0;
        }
    }

    private static void jQ() {
        if (w.Zs) {
            QY = new String[QR];
            if (QV) {
                QZ = new long[QR];
            }
            Rb = 0;
            Ra = 0;
            Rf = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 4, 4);
            Rf[0] = new int[]{0, 0, 50, 50};
            Rf[1] = new int[]{w.screenWidth - 50, 0, 50, 50};
            Rf[2] = new int[]{w.screenWidth - 50, w.screenHeight - 50, 50, 50};
            Rf[3] = new int[]{0, w.screenHeight - 50, 50, 50};
        }
    }

    public static void r(Object obj) {
        if (w.Zs) {
            if (QY == null) {
                jQ();
            }
            if (Ra == QR) {
                jQ();
            }
            if (QV) {
                long[] jArr = QZ;
                int i = Ra;
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis = (SystemClock.elapsedRealtime() + Tn) - To;
                }
                jArr[i] = jCurrentTimeMillis;
            }
            String[] strArr = QY;
            int i2 = Ra;
            Ra = i2 + 1;
            strArr[i2] = obj.toString();
            if (QW && Ra - Rb > QS) {
                Rb++;
            }
        }
    }

    public static void jR() {
        if (w.Zs) {
            jQ();
        }
    }

    private static void jS() {
        if (w.Zs) {
            if (Rf == null) {
                jQ();
            }
            if (dB(0) && h(Rf[0])) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis = (SystemClock.elapsedRealtime() + Tn) - To;
                }
                Rc = jCurrentTimeMillis;
                if (!QX) {
                    Rd = 0L;
                }
            }
            if (dF(0) && h(Rf[0])) {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + Tn) - To;
                }
                Rd += jCurrentTimeMillis2 - Rc;
                Rc = jCurrentTimeMillis2;
                if (Rd > 2000) {
                    QX = !QX;
                    Rd = 0L;
                }
            }
            Re = -1;
            if ((dB(0) || dF(0)) && h(Rf[1])) {
                Re = 1;
            } else if ((dB(0) || dF(0)) && h(Rf[2])) {
                Re = 2;
            } else if (dB(0) && h(Rf[3])) {
                Re = 3;
            }
            if (QX) {
                if (Re == 1) {
                    if (Rb > 0) {
                        Rb--;
                    }
                } else if (Re == 2) {
                    if (Rb + QS < Ra) {
                        Rb++;
                    }
                } else if (Re == 3) {
                    jR();
                }
            }
        }
    }

    private static void jT() {
        if (w.Zs) {
            if (!QX) {
                if (Qj != null) {
                    dk(QU);
                    b(Qj, 0.0f, 0.0f, Qk.je("WARNING: SCREEN CONSOLE SUPPORTED!"), Qk.getHeight(), true);
                    dk(QT);
                    a("WARNING: SCREEN CONSOLE SUPPORTED!", 0, 0, 20);
                    return;
                }
                return;
            }
            int i = Rb;
            int i2 = QS + i;
            if (Ra < QS + i) {
                i2 = Ra;
            }
            if (Ra == 0) {
                r("All debug log slots are emtpy!");
            }
            if (Qj != null) {
                if (Rj != null) {
                    J(i, i2);
                } else {
                    K(i, i2);
                }
            }
        }
    }

    private static final void J(int i, int i2) {
        if (Rj == null) {
            be("Trying to paint screen console, but system font is not set. Please specify it by calling GLLib.SetSystemFont(ASprite font) first.");
            return;
        }
        dk(QU);
        String str = "";
        while (i < i2) {
            String str2 = "[" + i + "]='" + QY[i] + "'";
            if (QV && i > 0) {
                str2 = str2 + ",dt=" + (QZ[i] - QZ[i - 1]) + "ms";
            }
            i++;
            str = str + str2 + "\n";
        }
        short[] sArrA = Rj.a(str, w.screenWidth, w.screenHeight, a.Iy);
        Rj.a(str, (char[]) null);
        b(Qj, 0.0f, 0.0f, Math.min(w.screenWidth, a.IJ), Math.min(sArrA[0] * (Rj.hC() + Rj.hA()), w.screenHeight), true);
        Rj.a(Qj, str, sArrA, 0, 0, 0, -1, 20, -1);
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 < Rf.length) {
                int i5 = Rf[i4][0];
                int i6 = Rf[i4][1];
                int i7 = Rf[i4][2];
                int i8 = Rf[i4][3];
                dk(QT);
                c(Qj, i5, i6, i7, i8, true);
                i3 = i4 + 1;
            } else {
                return;
            }
        }
    }

    private static final void K(int i, int i2) {
        int height = 0;
        while (i < i2) {
            String str = "[" + i + "]='" + QY[i] + "'";
            if (QV && i > 0) {
                str = str + ",dt=" + (QZ[i] - QZ[i - 1]) + "ms";
            }
            char[] charArray = str.toCharArray();
            int i3 = 0;
            int i4 = 0;
            String str2 = "";
            int height2 = height;
            while (i3 < charArray.length) {
                int iZ = Qk.z(charArray[i3]);
                if (i4 + iZ > w.screenWidth) {
                    dk(QU);
                    b(Qj, 0.0f, height2, i4, Qk.getHeight(), true);
                    dk(QT);
                    a(str2, 0, height2, 20);
                    str2 = "";
                    height2 += Qk.getHeight();
                    i4 = 0;
                }
                String str3 = str2 + charArray[i3];
                i3++;
                i4 += iZ;
                str2 = str3;
            }
            dk(QU);
            b(Qj, 0.0f, height2, i4, Qk.getHeight(), true);
            dk(QT);
            a(str2, 0, height2, 20);
            height = height2 + Qk.getHeight();
            i++;
        }
        for (int i5 = 0; i5 < Rf.length; i5++) {
            int i6 = Rf[i5][0];
            int i7 = Rf[i5][1];
            int i8 = Rf[i5][2];
            int i9 = Rf[i5][3];
            dk(QT);
            c(Qj, i6, i7, i8, i9, true);
        }
    }

    public static void jU() {
        QX = true;
    }

    public static void c(boolean z, String str) {
    }

    public static void d(Exception exc) {
    }

    private static void jV() {
        if (Rj == null) {
            be("You have enabled GLLibConfig.enableFPSMemoryDebug. Trying to paint FPS debug info but system font is not set. Please specify it by calling GLLib.SetSystemFont(ASprite font) first.");
            return;
        }
        aj ajVar = Qj;
        int i = SE;
        aj ajVar2 = Qj;
        int i2 = SF;
        aj ajVar3 = Qj;
        int i3 = SG;
        aj ajVar4 = Qj;
        int i4 = SH;
        int iHC = Rj.hC();
        int i5 = Qy;
        if (i5 == 0) {
            i5 = 2;
        }
        int i6 = ((i5 >> 1) + Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS) / i5;
        a(Qj, 0, 0, kH(), kI(), true);
        dk(0);
        b(Qj, 0.0f, 0.0f, 250.0f, (iHC * 2) + 5, true);
        Rj.a(Qj, "Mem free/total " + (Runtime.getRuntime().freeMemory() / 1024) + "/" + (Runtime.getRuntime().totalMemory() / 1024), 1, 1, 20);
        Rj.a(Qj, "FPS: " + i6, 1, iHC + 3, 20);
        a(Qj, i, i2, i3, i4, true);
    }

    private static void jW() {
        if (Rj == null) {
            be("You have enabled GLLibConfig.touchScreen_enableDebug. Trying to paint debug info but system font is not set. Please specify it by calling GLLib.SetSystemFont(ASprite font) first.");
            return;
        }
        switch (dy(0)) {
            case 1:
                dk(255);
                break;
            case 2:
                dk(16711680);
                break;
            case 3:
            case 4:
                dk(65280);
                break;
            default:
                dk(16711680);
                break;
        }
        aj ajVar = Qj;
        int i = SE;
        aj ajVar2 = Qj;
        int i2 = SF;
        aj ajVar3 = Qj;
        int i3 = SG;
        aj ajVar4 = Qj;
        int i4 = SH;
        int iHC = Rj.hC();
        int i5 = w.Vr ? (iHC * 2) + 5 : 0;
        int iDG = dG(0);
        int iDH = dH(0);
        a(Qj, iDG - 11, iDH - 11, 22, 22, true);
        a((float) (iDG - 10), (float) (iDH - 10), 20.0f, 20.0f, 0, 360);
        dk(0);
        b(Qj, iDG - 1, iDH - 1, 2.0f, 2.0f, true);
        a(Qj, 0, i5, 101, 31, true);
        b(Qj, 0.0f, i5, 150.0f, (iHC * 2) + 5, true);
        Rj.a(Qj, "TSX: " + iDG, 1, i5 + 1, 20);
        Rj.a(Qj, "TSY: " + iDH, 1, i5 + iHC + 3, 20);
        a(Qj, i, i2, i3, i4, true);
    }

    public static void c(String str, int i, int i2) {
        c(str != null, "Math_Init.packName is null");
        bk(str);
        if (i >= 0) {
            Rp = (int[]) de(i);
        } else {
            Rp = null;
        }
        if (i2 >= 0) {
            Rq = (int[]) de(i2);
        } else {
            Rq = null;
        }
        ke();
    }

    public static final int cP(int i) {
        int i2 = 0;
        c(i >= 0, "Math_Log2.value is negative");
        while ((i >> i2) > 1) {
            i2++;
        }
        return i2;
    }

    public static int L(int i, int i2) {
        c(Rm != null, "Math_Rand.GLLib mut be initialised prior to using this function");
        c(i <= i2, "Math_Rand.a must be <= b");
        if (i2 != i) {
            int iNextInt = Rm.nextInt();
            if (iNextInt < 0) {
                iNextInt *= -1;
            }
            return i + (iNextInt % (i2 - i));
        }
        return i2;
    }

    public static int cQ(int i) {
        c(Rp != null, "!!ERROR!! Math_Cos.s_math_cosTable is null, call Math_Init first");
        if (i < 0) {
            i *= -1;
        }
        int i2 = (Rv - 1) & i;
        if (i2 <= Rs) {
            return Rp[i2];
        }
        if (i2 < Rt) {
            return -Rp[Rt - i2];
        }
        if (i2 <= Ru) {
            return -Rp[i2 - Rt];
        }
        return Rp[Rv - i2];
    }

    public static int cR(int i) {
        if (i >= 65536) {
            if (i >= 16777216) {
                if (i >= 268435456) {
                    if (i >= 1073741824) {
                        return Rq[i >> 24] << 8;
                    }
                    return Rq[i >> 22] << 7;
                }
                if (i >= 67108864) {
                    return Rq[i >> 20] << 6;
                }
                return Rq[i >> 18] << 5;
            }
            if (i >= 1048576) {
                if (i >= 4194304) {
                    return Rq[i >> 16] << 4;
                }
                return Rq[i >> 14] << 3;
            }
            if (i >= 262144) {
                return Rq[i >> 12] << 2;
            }
            return Rq[i >> 10] << 1;
        }
        if (i >= 256) {
            if (i >= 4096) {
                if (i >= 16384) {
                    return Rq[i >> 8];
                }
                return Rq[i >> 6] >> 1;
            }
            if (i >= 1024) {
                return Rq[i >> 4] >> 2;
            }
            return Rq[i >> 2] >> 3;
        }
        if (i >= 0) {
            return Rq[i] >> 4;
        }
        return 0;
    }

    public static int jX() {
        return Rn;
    }

    public static int jY() {
        return Ro;
    }

    public static void a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8 = i7 * i7;
        int i9 = Rk - i7;
        int i10 = i9 * i9;
        int i11 = i9 * i7;
        Rn = (((i * i10) + ((i3 * 2) * i11)) + (i5 * i8)) / (1 << (w.VP * 2));
        int i12 = i8 * i6;
        Ro = (i12 + ((i11 * (i4 * 2)) + (i10 * i2))) / (1 << (w.VP * 2));
    }

    public static final int M(int i, int i2) {
        c(i2 != 0, "Math_FixedPoint_Divide(): Division by Zero");
        return ((int) ((((((long) i) << w.VP) << 1) / ((long) i2)) + 1)) >> 1;
    }

    public static int cS(int i) {
        int i2 = 0;
        while ((1 << i2) < i) {
            i2++;
        }
        return 1 << i2;
    }

    private static void bf(String str) {
        c(str != null, "Pack_Open.filename is null");
        RK = 1;
        a(str, (byte[]) null, 0, true);
    }

    private static byte[] cT(int i) {
        byte[] bArr = null;
        c(i >= 0, "Pack_ReadData.idx is invalid");
        c(i < RO, "Pack_ReadData.idx is invalid");
        int iDa = da(i);
        if (w.Wj && RU) {
            try {
                a(RJ, iDa);
                RN = iDa + RN;
                bArr = Sq;
                Sq = null;
            } catch (Exception e) {
                c(false, "LZMA decompression failed : " + e.toString());
            }
            return bArr;
        } else {
            byte[] bArr2 = new byte[iDa];
            c(bArr2, 0, bArr2.length);
            return bArr2;
        }
    }

    private static Object cU(int i) {
        c(i >= 0, "Pack_ReadArray.idx is invalid");
        c(i < RO, "Pack_ReadArray.idx is invalid");
        da(i);
        Tr = 0;
        if (w.Wj && RU) {
            return f(new ByteArrayInputStream(db(i)));
        }
        Object objF = f(RJ);
        RN += Tr;
        return objF;
    }

    private static void jZ() {
        if (RJ != null) {
            try {
                RJ.close();
            } catch (Exception e) {
            }
            RJ = null;
        }
        RN = 0;
    }

    private static void ka() {
        int i;
        if (RS == RQ - 1) {
            i = RO - RR[RS];
        } else {
            i = RR[RS + 1] - RR[RS];
        }
        RP = new int[i + 1];
        for (int i2 = 0; i2 < i + 1; i2++) {
            RP[i2] = ki();
        }
    }

    private static void bg(String str) {
        if (RJ == null || str == null || str.compareTo(RI) != 0) {
            RK = 1;
            h(str, 0);
        }
    }

    private static InputStream h(String str, int i) {
        return a(str, i, (byte[]) null, 0);
    }

    private static InputStream a(String str, int i, byte[] bArr, int i2) {
        try {
            if (RJ != null) {
                if (str != null && str.compareTo(RI) == 0) {
                    if (RC == i) {
                        return RJ;
                    }
                    kb();
                } else {
                    kc();
                }
            }
            if (RJ == null) {
                RI = str;
                RL = bArr;
                RM = i2;
                if (i > 0) {
                    try {
                        str = str + "." + i;
                    } catch (Throwable th) {
                    }
                }
                InputStream inputStreamBl = bl(str);
                RC = i;
                if (i == 0) {
                    RD = inputStreamBl.available();
                    int i3 = (inputStreamBl.read() & 255) + ((inputStreamBl.read() & 255) << 8);
                    RO = (short) i3;
                    int i4 = i3 << 2;
                    RP = new int[i3];
                    for (int i5 = 0; i5 < i3; i5++) {
                        RP[i5] = (inputStreamBl.read() & 255) + ((inputStreamBl.read() & 255) << 8) + ((inputStreamBl.read() & 255) << 16) + ((inputStreamBl.read() & 255) << 24);
                    }
                    RN = RP[0];
                } else {
                    RN = 0;
                }
                RJ = inputStreamBl;
            }
            return RJ;

        } catch (IOException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    private static void kb() {
        try {
            if (RJ != null) {
                try {
                    RJ.close();
                } catch (Throwable th) {
                }
                RJ = null;
            }
        } catch (Throwable th2) {
        }
    }

    private static void kc() {
        kb();
        RI = null;
        RP = null;
    }

    private static int cV(int i) {
        try {
            int i2 = 0;
            c(RI != null, "Pack_PositionAtData_CORE: s_pack_filename is null!");
            int iCX = cX(i);
            int i3 = (RP[i + 1] - iCX) - 1;
            int i4 = iCX;
            while (i4 > RD) {
                try {
                    i2++;
                    i4 -= RD;
                } catch (Throwable th) {
                }
            }
            InputStream inputStreamH = h(RI, i2);
            inputStreamH.available();
            if (RN > i4) {
                kb();
                inputStreamH = h(RI, i2);
            }
            inputStreamH.skip(i4 - RN);
            RT = ((byte) inputStreamH.read()) & 255;
            if (w.Wj && RT >= 127) {
                RT -= 127;
                RU = true;
            }
            RU = false;
            RN = i4 + 1;
            return i3;

        } catch (IOException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    private static byte[] cW(int i) {
        c(RI != null, "Pack_ReadData_CORE: s_pack_filename is null!");
        return a(RI, i, false);
    }

    private static byte[] a(String str, int i, boolean z) {
        byte[] bArr = null;
        bg(str);
        try {
            int iCV = cV(i);
            InputStream inputStreamH = RJ;
            int iAvailable = inputStreamH.available();
            int i2 = 0;
            bArr = new byte[iCV];
            while (iCV > 0) {
                int iA = a(inputStreamH, bArr, i2, Math.min(iCV, iAvailable));
                i2 += iA;
                RN += iA;
                iCV -= iA;
                if (iCV == 0 || iA == -1) {
                    break;
                }
                inputStreamH = h(str, RC + 1);
                iAvailable = inputStreamH.available();
            }
        } catch (Throwable th) {
        }
        if (z) {
            kb();
        }
        return bArr;
    }

    private static int a(InputStream inputStream, byte[] bArr, int i, int i2) {
        try {
            return inputStream.read(bArr, i, i2);
        } catch (Exception e) {
            return -1;
        }
    }

    public static int cX(int i) {
        return RP[i];
    }

    private static void kd() {
        if (w.Wi) {
            be("[CORE].Pack_Close_INIEX " + RI);
        }
        kb();
        RI = null;
        RP = null;
        RF = null;
        kk();
    }

    private static void bh(String str) {
        h(str, w.Wm);
    }

    private static void h(String str, boolean z) {
        if (RJ == null || str == null || str.compareTo(RI) != 0) {
            RK = 1;
            if (w.Wi) {
                be("[CORE].Pack_Open_INIEX " + str);
            }
            RH = bi(str);
            i(str, 0);
            for (int i = 1; i < RQ; i++) {
                i(str, i);
            }
            if (w.Wm && z) {
                bn(str);
            }
        }
    }

    public static int o(int i, String str) {
        for (int i2 = 0; i2 < RE[i].length; i2++) {
            if (RE[i][i2].equals(str)) {
                return i2;
            }
        }
        return -1;
    }

    private static byte[] cY(int i) {
        c(RI != null, "Pack_ReadData_INIEX: s_pack_filename is null!");
        return b(RI, i, false);
    }

    private static byte[] b(String str, int i, boolean z) {
        byte[] bArr = null;
        try {
            int iDa = da(i);
            if (w.Wi) {
                be("resIndex:\t" + i);
            }
            if (i < 0) {
                be("Can't find resource " + RE[i] + " in pack " + str);
            } else {
                if (w.Wi) {
                    be("data size:\t" + iDa);
                }
                InputStream inputStreamI = RJ;
                if (iDa > 0) {
                    if (w.Wj && RU) {
                        try {
                            a(RJ, iDa);
                            RN = iDa + RN;
                            bArr = Sq;
                            Sq = null;
                        } catch (Exception e) {
                            d(e);
                        }
                    } else {
                        int iAvailable = inputStreamI.available();
                        int i2 = 0;
                        bArr = new byte[iDa];
                        while (iDa > 0) {
                            int iA = a(inputStreamI, bArr, i2, Math.min(iDa, iAvailable));
                            i2 += iA;
                            RN += iA;
                            iDa -= iA;
                            if (iDa == 0) {
                                break;
                            }
                            if (iA == -1) {
                                if (!w.Wi) {
                                    break;
                                }
                                be("[CORE].get read ERROR !!!");
                                break;
                            }
                            inputStreamI = i(str, RC + 1);
                            iAvailable = inputStreamI.available();
                        }
                        if (w.Wi) {
                            be("[CORE].get sizeLeft=" + iDa + " dataLen=" + bArr.length);
                        }
                    }
                    if (z) {
                        kb();
                    }
                }
            }
        } catch (Throwable th) {
        }
        return bArr;
    }

    private static InputStream i(String str, int i) {
        return b(str, i, (byte[]) null, 0);
    }

    private static InputStream b(String str, int i, byte[] bArr, int i2) {
        try {
            if (RJ != null) {
                if (str != null && str.compareTo(RI) == 0) {
                    if (RC == i) {
                        return RJ;
                    }
                    kb();
                } else {
                    kd();
                }
            }
            if (RJ == null) {
                RI = str;
                RL = bArr;
                RM = i2;
                if (i > 0) {
                    try {
                        str = str + "." + i;
                    } catch (Throwable th) {
                    }
                }
                InputStream inputStreamBl = bl(str);
                RC = i;
                if (w.Wi) {
                    be("Stream Id = " + RI);
                }
                RD = inputStreamBl.available();
                if (i <= 0) {
                    int iB = b(inputStreamBl);
                    if (w.Wi) {
                        be("read iniex version: " + iB);
                    }
                    int iB2 = b(inputStreamBl);
                    if (w.Wi) {
                        be("read iniex count: " + iB2);
                    }
                    RO = (short) iB2;
                    int iB3 = b(inputStreamBl);
                    if (w.Wi) {
                        be("read iniex subpackCount: " + iB3);
                    }
                    RQ = (short) iB3;
                    if (iB3 > 0) {
                        RR = new short[iB3];
                        for (int i3 = 0; i3 < iB3; i3++) {
                            int iB4 = b(inputStreamBl);
                            RR[i3] = (short) iB4;
                            if (w.Wi) {
                                be("read iniex subpack start index: " + iB4);
                            }
                        }
                    }
                    RP = new int[iB2];
                    RF = new int[iB2];
                }
                if (RE == null) {
                    RE = new String[RG.length][];
                }
                if (RE[RH] == null) {
                    RE[RH] = new String[RO];
                }
                short s = i + 1 < RR.length ? RR[i + 1] : RO;
                for (int i4 = RR[i]; i4 < s; i4++) {
                    int i5 = inputStreamBl.read();
                    if (w.Wi) {
                        be("read iniex nameLength: " + i5);
                    }
                    byte[] bArr2 = new byte[i5];
                    for (int i6 = 0; i6 < i5; i6++) {
                        bArr2[i6] = (byte) inputStreamBl.read();
                    }
                    String str2 = new String(bArr2);
                    RE[RH][i4] = str2;
                    if (w.Wi) {
                        be("read iniex name: " + str2);
                    }
                    int iC = c(inputStreamBl);
                    if (w.Wi) {
                        be("read iniex offset: " + iC);
                    }
                    RP[i4] = iC;
                    int iC2 = c(inputStreamBl);
                    RF[i4] = iC2;
                    if (w.Wi) {
                        be("read iniex resLength: " + iC2);
                    }
                }
                if (w.Wi) {
                    Thread.sleep(ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
                }
                RN = RP[RR[i]];
                RJ = inputStreamBl;
            }
            return RJ;

        } catch (IOException | InterruptedException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    static int b(InputStream inputStream) {
        try {
            return (inputStream.read() & 255) + ((inputStream.read() & 255) << 8);

        } catch (IOException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    static int c(InputStream inputStream) {
        try {
            return (inputStream.read() & 255) + ((inputStream.read() & 255) << 8) + ((inputStream.read() & 255) << 16) + ((inputStream.read() & 255) << 24);

        } catch (IOException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    public static int bi(String str) {
        for (int i = 0; i < RG.length; i++) {
            if (str.equals(RG[i])) {
                return i;
            }
        }
        return -1;
    }

    private static int cZ(int i) {
        int i2;
        int i3;
        c(RI != null, "Pack_PositionAtData_CORE: s_pack_filename is null!");
        try {
            int[] iArrDf = df(i);
            if (iArrDf != null && iArrDf.length == 1) {
                return iArrDf[0];
            }
            int i4 = RQ - 1;
            if (iArrDf == null || iArrDf.length != 2) {
                i2 = 0;
            } else {
                i2 = iArrDf[0];
                i4 = iArrDf[1];
            }
            while (true) {
                if (i4 < i2) {
                    i3 = i4;
                    break;
                }
                if (RR[i4] > i) {
                    i4--;
                } else {
                    i3 = i4 - i2;
                    break;
                }
            }
            if (w.Wi) {
                be("[CORE].get " + RI + "." + i3 + "," + i);
            }
            InputStream inputStreamI = i(RI, i3);
            int i5 = RN;
            int iAvailable = inputStreamI.available();
            int iCX = cX(i);
            int i6 = RF[i];
            if (w.Wi) {
                be("[CORE].get streamPos=" + RN + ", available=" + iAvailable + ", offset=" + iCX + ", size " + i6);
            }
            if (i5 > iCX) {
                if (w.Wi) {
                    be("Pack Close BAD READING ORDER");
                }
                if (inputStreamI.markSupported()) {
                    inputStreamI.reset();
                    i5 = 0;
                } else {
                    kb();
                    inputStreamI = i(RI, i3);
                }
            }
            if (iCX != i5) {
                inputStreamI.skip(iCX - i5);
            }
            RU = false;
            RT = ((byte) inputStreamI.read()) & 255;
            if (w.Wj && RT >= 127) {
                RT -= 127;
                RU = true;
            }
            RJ = inputStreamI;
            RN = iCX + 1;
            return i6 - 1;
        } catch (Throwable th) {
            return 0;
        }
    }

    private static boolean x(String str, String str2) throws Exception {
        try {
            File file = new File(str);
            be("FilePath:" + file.getPath());
            if (!file.exists()) {
                be("Not Exist");
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = str2.getBytes();
            fileOutputStream.write(bytes, 0, bytes.length);
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public InputStream bj(String str) {
        return a("".getClass(), str);
    }

    public static final void bk(String str) {
        UM = str;
        if (w.Wl) {
            bh(str);
        } else if (w.Wk) {
            bg(str);
        } else {
            bf(str);
        }
    }

    private static void a(String str, byte[] bArr, int i, boolean z) {
        if (RI == null || str == null || str.compareTo(RI) != 0) {
            ke();
            RI = str;
            RL = bArr;
            RM = i;
            RJ = bl(RI);
            c(RJ != null, "Pack_Open.unable to find file");
            RO = (short) kh();
            RQ = (short) kh();
            RR = new short[RQ];
            for (int i2 = 0; i2 < RQ; i2++) {
                RR[i2] = (short) kh();
            }
            RS = 0;
            ka();
        }
    }

    private static InputStream bl(String str) {
        if (RK == 2) {
            return new ByteArrayInputStream(RL, RM, RL.length - RM);
        }
        if (RK == 1) {
            return QD.bj(str);
        }
        if (RK == 3) {
            try {
                return new FileInputStream(new File(str));
            } catch (Exception e) {
                be("Exception while reading file from external file system : " + str);
                d(e);
            }
        }
        return null;
    }

    public static final void ke() {
        ag(true);
    }

    private static final void ag(boolean z) {
        kf();
        if (RK == 2) {
            RL = null;
        }
        if (z) {
            RI = null;
        }
        if (w.Vf) {
        }
    }

    private static final void kf() {
        if (w.Wl) {
            kb();
            kk();
            RN = 0;
        } else if (w.Wk) {
            kc();
        } else {
            jZ();
        }
    }

    public static int da(int i) {
        int iAvailable;
        if (w.Wl) {
            return cZ(i);
        }
        if (w.Wk) {
            int iCV = cV(i);
            try {
                iAvailable = RJ.available();
            } catch (Throwable th) {
                iAvailable = 0;
            }
            if (iCV <= iAvailable) {
                return iCV;
            }
            be("Pack_PositionAtData: partially available (" + iAvailable + '/' + iCV + ") when pack_use_CORE_ResourceManager is TRUE!");
            return iAvailable;
        }
        c(i >= 0, "Pack_PositionAtData.idx is invalid");
        c(i < RO, "Pack_PositionAtData.idx is invalid");
        int i2 = RQ - 1;
        while (i2 >= 0 && RR[i2] > i) {
            i2--;
        }
        if (RS != i2) {
            RS = i2;
            ag(false);
            if (RS == 0) {
                String str = RI;
                RI = null;
                bk(str);
            } else {
                RJ = bl(RI + "." + RS);
                ka();
            }
        } else if (RJ == null) {
            if (RS == 0) {
                String str2 = RI;
                RI = null;
                bk(str2);
            } else {
                RJ = bl(RI + "." + RS);
            }
        }
        int i3 = i - RR[RS];
        int iCX = cX(i3);
        int iCX2 = cX(i3 + 1) - iCX;
        dc(iCX);
        RU = false;
        if (iCX2 <= 0) {
            return iCX2;
        }
        RT = kg() & 255;
        if (w.Wj && RT >= 127) {
            RT -= 127;
            RU = true;
        }
        return iCX2 - 1;
    }

    public static final byte[] db(int i) {
        UN = i;
        if (w.Wl) {
            return cY(i);
        }
        if (w.Wk) {
            return cW(i);
        }
        return cT(i);
    }

    public static void dc(int i) {
        if (RN != i) {
            if (RN > i) {
                kb();
                if (RS == 0) {
                    RJ = bl(RI);
                } else {
                    RJ = bl(RI + "." + RS);
                }
                RN = 0;
                c(RJ != null, "Pack_Seek.internal error");
            } else {
                i -= RN;
            }
            dd(i);
        }
    }

    public static void dd(int i) {
        if (i != 0) {
            if (w.Wh == 0 || RK == 2) {
                RN += i;
                while (i > 0) {
                    try {
                        i = (int) (((long) i) - RJ.skip(i));
                    } catch (Exception e) {
                        c(false, "Pack_Skip.IO exception occured");
                        return;
                    }
                }
                return;
            }
            if (RV == null) {
                RV = new byte[w.Wh];
            }
            while (i > w.Wh) {
                c(RV, 0, w.Wh);
                i -= w.Wh;
            }
            if (i > 0) {
                c(RV, 0, i);
            }
        }
    }

    public static int kg() {
        int i;
        try {
            i = RJ.read();
        } catch (Exception e) {
            c(false, "Pack_Read.IO exception occured");
            i = 0;
        }
        c(i >= 0, "Pack_Read.EOF");
        RN++;
        return i;
    }

    public static int kh() {
        return (kg() & 255) | ((kg() & 255) << 8);
    }

    public static int ki() {
        return (kg() & 255) | ((kg() & 255) << 8) | ((kg() & 255) << 16) | ((kg() & 255) << 24);
    }

    public static int c(byte[] bArr, int i, int i2) {
        c(bArr != null, "Pack_ReadFully.array is null");
        c(i >= 0, "Pack_ReadFully.offset is negative");
        c(i2 >= 0, "Pack_ReadFully.length is negative");
        c(i + i2 <= bArr.length, "Pack_ReadFully.offset+length is bigger than array size");
        int i3 = i2;
        while (i3 > 0) {
            try {
                int i4 = RJ.read(bArr, i, i3);
                c(i4 >= 0, "Pack_ReadFully.EOF");
                i += i4;
                i3 -= i4;
            } catch (Exception e) {
                c(false, "Pack_Read.IO exception occured");
            }
        }
        RN += i2;
        return i2;
    }

    public static final Object de(int i) {
        if (w.Wk) {
            c(i >= 0, "Pack_ReadArray.idx is invalid");
            return f(new ByteArrayInputStream(db(i)));
        }
        return cU(i);
    }

    public static void bm(String str) {
        c(str != null, "Pack_LoadMIME.filename is null");
        if (RW == null) {
            InputStream inputStream = RJ;
            RJ = bl(str);
            int iKg = kg();
            RW = new byte[iKg][];
            for (int i = 0; i < iKg; i++) {
                int iKg2 = kg();
                RW[i] = new byte[iKg2];
                c(RW[i], 0, iKg2);
            }
            try {
                RJ.close();
            } catch (Exception e) {
                c(false, "Pack_LoadMIME.IO Error");
            }
            RJ = inputStream;
        }
    }

    public static void a(int i, String str, String str2, int i2) {
        if (str2 != null && str2.startsWith("dlc")) {
            if (i2 == 200 || i2 == 202) {
                p(i, str);
            } else {
                N(i, i2);
            }
        }
    }

    private static void N(int i, int i2) {
        if (i2 == 404) {
            if (i == t.NB) {
                RX = 3;
            }
            if (i == t.Pt || i == t.Pu) {
                if (!kj()) {
                    RX = 4;
                } else {
                    RX = 5;
                }
            }
        }
    }

    private static void p(int i, String str) {
        if (i == t.Pt) {
            RY = bv(str);
            if (RY != null) {
                RX = 1;
                s.h(kp() + "/" + RY, bq(RY), "dlc");
                return;
            } else {
                RX = 2;
                return;
            }
        }
        if (i == t.Pu) {
            RY = bw(str);
            if (RY != null) {
                RX = 1;
                s.h(kp() + "/" + RY, bq(RY), "dlc");
            } else {
                RX = 2;
            }
        }
    }

    private static boolean kj() {
        return RY != null;
    }

    private static void bn(String str) {
        if (w.Wm) {
            RK = 3;
            if (Sh || kn()) {
                br(str);
                kl();
                if (Si != null && Sk != null) {
                    for (int i = 0; i < Sk.length; i++) {
                        if (Sk[i] >= 0) {
                            String str2 = Si[Sk[i]];
                            short s = RQ;
                            InputStream inputStreamD = d(str2, i, 0);
                            if (inputStreamD != null) {
                                try {
                                    inputStreamD.close();
                                } catch (IOException e) {
                                }
                            }
                            for (int i2 = 1; i2 < RQ - s; i2++) {
                                InputStream inputStreamD2 = d(str2, i, i2);
                                if (inputStreamD2 != null) {
                                    try {
                                        inputStreamD2.close();
                                    } catch (IOException e2) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
            RK = 1;
        }
    }

    private static void kk() {
        if (w.Wm) {
            RZ = null;
            Sa = null;
            Sb = null;
            Sd = null;
            Sk = null;
            Se = null;
        }
    }

    private static InputStream d(String str, int i, int i2) {
        try {
            if (RJ != null && str != null && str.compareTo(RI) == 0 && RC == i2) {
                return RJ;
            }
            RK = 3;
            if (i2 > 0) {
                try {
                    str = str + "." + i2;
                } catch (Throwable th) {
                    RK = 1;
                    throw th;
                }
            }
            InputStream inputStreamBl = bl(bq(str));
            if (inputStreamBl == null) {
                RK = 1;
                return null;
            }
            kr();
            if (w.Wi) {
                be("Stream dlc Id = " + str);
            }
            if (i2 <= 0) {
                int iB = b(inputStreamBl);
                if (w.Wi) {
                    be("read dlc iniex version: " + iB);
                }
                int iB2 = b(inputStreamBl);
                if (w.Wi) {
                    be("read dlc iniex count: " + iB2);
                }
                Se[i] = iB2;
                RO = (short) (RO + ((short) iB2));
                int iB3 = b(inputStreamBl);
                if (w.Wi) {
                    be("read dlc iniex subpackCount: " + iB3);
                }
                RQ = (short) (RQ + ((short) iB3));
                if (iB3 > 0) {
                    RR = a(RR, RZ, i, iB3);
                    for (int i3 = 0; i3 < iB3; i3++) {
                        int iB4 = b(inputStreamBl);
                        RR[RZ[i] + i3] = (short) iB4;
                        if (w.Wi) {
                            be("read dlc iniex subpack start index: " + iB4);
                        }
                    }
                }
                RP = a(RP, Sa, i, iB2);
                if (RE == null) {
                    RE = new String[RG.length][];
                }
                if (Sc == null) {
                    Sc = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, RG.length, Sf);
                }
                RE[RH] = a(RE[RH], Sc[RH], i, RO - (RE[RH] == null ? 0 : RE[RH].length));
                Sb = Sc[RH];
                RF = a(RF, Sd, i, iB2);
            }
            int i4 = Se[i];
            int i5 = RZ[i] + i2 + 1;
            int i6 = i5 < RR.length ? RR[i5] : i4;
            for (int i7 = RR[RZ[i] + i2]; i7 < i6; i7++) {
                int i8 = inputStreamBl.read();
                if (w.Wi) {
                    be("read dlc iniex nameLength: " + i8);
                }
                byte[] bArr = new byte[i8];
                for (int i9 = 0; i9 < i8; i9++) {
                    bArr[i9] = (byte) inputStreamBl.read();
                }
                String str2 = new String(bArr);
                RE[RH][Sb[i] + i7] = str2;
                if (w.Wi) {
                    be("read dlc iniex name: " + str2);
                }
                int iC = c(inputStreamBl);
                if (w.Wi) {
                    be("read dlc iniex offset: " + iC);
                }
                RP[Sa[i] + i7] = iC;
                int iC2 = c(inputStreamBl);
                RF[Sd[i] + i7] = iC2;
                if (w.Wi) {
                    be("read dlc iniex resLength: " + iC2);
                }
            }
            if (w.Wi) {
                Thread.sleep(ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
            }
            RK = 1;
            return inputStreamBl;

        } catch (IOException | InterruptedException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    private static int[] df(int i) {
        int i2;
        InputStream inputStreamD;
        if (!w.Wm) {
            return null;
        }
        try {
            int i3 = RQ - 1;
            if (Sf <= 0 || Sb == null || RZ == null) {
                return null;
            }
            int i4 = -1;
            int i5 = 0;
            for (int i6 = 0; i6 < Sb.length; i6++) {
                if (Sb[i6] >= 0 && RZ[i6] >= 0) {
                    if (i >= Sb[i6] && i5 < RZ[i6]) {
                        i5 = RZ[i6];
                        i4 = i6;
                    } else if (i3 > RZ[i6] - 1) {
                        i3 = RZ[i6] - 1;
                    }
                }
            }
            if (i4 < 0) {
                return new int[]{i5, i3};
            }
            int i7 = i - Sb[i4];
            int i8 = i3;
            while (true) {
                if (i8 < i5) {
                    i2 = i8;
                    break;
                }
                if (RR[i8] > i7) {
                    i8--;
                } else {
                    i2 = i8 - i5;
                    break;
                }
            }
            if (w.Wi) {
                be("[CORE].get " + RI + (i4 < 0 ? "" : "dlc" + i4) + "." + i2 + "," + i);
            }
            String strDg = dg(i4);
            if (i2 > 0) {
                strDg = strDg + "." + i2;
            }
            RK = 3;
            InputStream inputStreamBl = bl(bq(strDg));
            RK = 1;
            int iAvailable = inputStreamBl.available();
            int iCX = cX(i);
            int i9 = RF[i];
            if (w.Wi) {
                be("[CORE].get streamPos=" + RN + ", available=" + iAvailable + ", offset=" + iCX + ", size " + i9);
            }
            if (0 > iCX) {
                if (w.Wi) {
                    be("Pack Close BAD READING ORDER");
                }
                if (inputStreamBl.markSupported()) {
                    inputStreamBl.reset();
                    inputStreamD = inputStreamBl;
                } else {
                    inputStreamD = d(dg(i4), i4, i2);
                }
            } else {
                inputStreamD = inputStreamBl;
            }
            if (iCX != 0) {
                inputStreamD.skip(iCX + 0);
            }
            RU = false;
            RT = ((byte) inputStreamD.read()) & 255;
            if (w.Wj && RT >= 127) {
                RT -= 127;
                RU = true;
            }
            RJ = inputStreamD;
            RN = iCX + 1;
            return new int[]{i9 - 1};
        } catch (Throwable th) {
            return new int[]{0};
        }
    }

    private static int[] a(int[] iArr, int[] iArr2, int i, int i2) {
        if (i2 > 0) {
            if (iArr == null) {
                if (iArr2 != null && iArr2.length > i) {
                    iArr2[i] = 0;
                }
                return new int[i2];
            }
            if (iArr2 != null && iArr2.length > i) {
                iArr2[i] = iArr.length;
            }
            int[] iArr3 = new int[iArr.length + i2];
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            return iArr3;
        }
        return iArr;
    }

    private static short[] a(short[] sArr, int[] iArr, int i, int i2) {
        if (i2 > 0) {
            if (sArr == null) {
                if (iArr != null && iArr.length > i) {
                    iArr[i] = 0;
                }
                return new short[i2];
            }
            if (iArr != null && iArr.length > i) {
                iArr[i] = sArr.length;
            }
            short[] sArr2 = new short[sArr.length + i2];
            System.arraycopy(sArr, 0, sArr2, 0, sArr.length);
            return sArr2;
        }
        return sArr;
    }

    private static String[] a(String[] strArr, int[] iArr, int i, int i2) {
        if (i2 > 0) {
            if (strArr == null) {
                if (iArr != null && iArr.length > i) {
                    iArr[i] = 0;
                }
                return new String[i2];
            }
            if (iArr != null && iArr.length > i) {
                iArr[i] = strArr.length;
            }
            String[] strArr2 = new String[strArr.length + i2];
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
            return strArr2;
        }
        return strArr;
    }

    private static void kl() {
        if (RG == null) {
            be("[DLC].addPackNames pack Names are null: no original packs");
            return;
        }
        if (Si == null) {
            be("[DLC].addPackNames s_dlc_packs==null");
            return;
        }
        if (!km()) {
            String[] strArr = RG;
            RG = new String[strArr.length + Sk.length];
            System.arraycopy(strArr, 0, RG, 0, strArr.length);
            for (int i = 0; i < Si.length; i++) {
                String str = Si[i];
                if (str.indexOf(".") < 0) {
                    RG[strArr.length + i] = str;
                }
            }
        }
    }

    private static boolean km() {
        for (int i = 0; i < RG.length; i++) {
            if (RG[i].indexOf("dlc") >= 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean kn() {
        boolean zBo = false;
        String strKo = ko();
        if (strKo == null) {
            be("Error! [DLC].Pack_Dlc_ReadTocFile:tocName is null");
        } else {
            String strBq = bq(strKo);
            try {
                InputStream inputStreamBl = bl(strBq);
                if (inputStreamBl == null) {
                    be("Error! [DLC].readToc Can't open toc file: " + strBq);
                } else {
                    zBo = bo(e(inputStreamBl));
                }
            } catch (Exception e) {
                be("Error! [DLC].readToc " + strBq + ";" + e);
            }
        }
        return zBo;
    }

    private static boolean bo(String str) {
        int iIndexOf;
        int iJ;
        if (str == null) {
            return false;
        }
        Sf = 0;
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            Si = new String[0];
            Sj = new String[0];
            return true;
        }
        int i = 0;
        int i2 = 1;
        while (i < strTrim.length() && (iJ = j(strTrim, i)) >= 0) {
            i2++;
            i = iJ + 2;
        }
        Si = new String[i2];
        Sj = new String[i2];
        int i3 = 0;
        int iJ2 = 0;
        while (iJ2 < strTrim.length() && (iIndexOf = strTrim.indexOf(" ", iJ2)) >= 0) {
            Si[i3] = bu(strTrim.substring(iJ2, iIndexOf));
            int iBt = bt(Si[i3]);
            if (iBt + 1 > Sf) {
                Sf = iBt + 1;
            }
            int i4 = iIndexOf + 1;
            iJ2 = j(strTrim, i4);
            if (iJ2 < 0) {
                iJ2 = strTrim.length();
            }
            Sj[i3] = bu(strTrim.substring(i4, iJ2));
            i3++;
        }
        Sh = true;
        return true;
    }

    public static String ko() {
        String strBY = Qu == null ? null : Qu.bY("MIDlet-Name");
        String strBY2 = Qu == null ? null : Qu.bY("MIDlet-Version");
        c(strBY != null, "Pack_GetTocName.MIDlet-Name is not set");
        c(strBY2 != null, "Pack_GetTocName.MIDlet-Version is not set");
        if (strBY == null || strBY2 == null) {
            return null;
        }
        return bp(strBY) + "_" + strBY2 + ".toc";
    }

    private static String bp(String str) {
        String strTrim = str.trim();
        int iIndexOf = strTrim.indexOf(" ");
        while (iIndexOf >= 0) {
            strTrim = strTrim.substring(0, iIndexOf) + strTrim.substring(iIndexOf + 1, strTrim.length());
            iIndexOf = strTrim.indexOf(" ", iIndexOf + 1);
        }
        return strTrim;
    }

    private static boolean y(String str, String str2) {
        try {
            RK = 3;
            InputStream inputStreamBl = bl(str);
            RK = 1;
            byte[] bArrD = d(inputStreamBl);
            inputStreamBl.close();
            String strC = c(bArrD);
            be("FileHash:" + str + ";" + strC);
            be("Hash:" + str2);
            return strC.equals(str2);
        } catch (Exception e) {
            be("Error! [DLC].Pack_VerifyHash " + str + ";" + e);
            return false;
        }
    }

    private static byte[] d(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        byte[] bArr = new byte[256];
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                inputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    public static String bq(String str) {
        if (Sg == null) {
            Sg = Qu.getApplicationContext().getExternalFilesDir(null).getPath() + "/dlc/" + kp();
        }
        File file = new File(Sg);
        if (file.exists() && file.isDirectory()) {
            return file.getPath() + "/" + str;
        }
        file.mkdirs();
        return null;
    }

    private static String kp() {
        if (Qu == null) {
            return null;
        }
        return Qu.bY("RESOLUTION-TYPE");
    }

    private static void br(String str) {
        if (Si == null) {
            Sk = null;
            return;
        }
        if (Sk == null || Sk.length != Sf) {
            Sk = new int[Sf];
        }
        d(Sk, -1);
        for (int i = 0; i < Si.length; i++) {
            if (Si[i] != null && bs(Si[i]).equals(str)) {
                int iBt = bt(Si[i]);
                if (Si[i].indexOf(46) < 0) {
                    Sk[iBt] = i;
                }
            }
        }
    }

    private static String bs(String str) {
        if (str == null) {
            return null;
        }
        return "/" + str.substring(0, str.indexOf("_"));
    }

    private static int bt(String str) {
        if (str == null) {
            return -1;
        }
        int iIndexOf = str.indexOf("dlc");
        int iIndexOf2 = str.indexOf(".");
        if (iIndexOf2 < 0) {
            iIndexOf2 = str.length();
        }
        return Integer.parseInt(str.substring(iIndexOf + 3, iIndexOf2)) - 1;
    }

    private static int j(String str, int i) {
        for (String str2 : kq()) {
            int iIndexOf = str.indexOf(str2, i);
            if (iIndexOf >= 0) {
                return iIndexOf;
            }
        }
        return -1;
    }

    private static String bu(String str) {
        String strTrim = str.trim();
        String[] strArrKq = kq();
        boolean z = false;
        do {
            for (int i = 0; i < strArrKq.length; i++) {
                if (strTrim.startsWith(strArrKq[i])) {
                    strTrim = strTrim.substring(strArrKq[i].length()).trim();
                    z = true;
                }
                if (strTrim.endsWith(strArrKq[i])) {
                    strTrim = strTrim.substring(0, strTrim.length() - strArrKq[i].length()).trim();
                    z = true;
                }
            }
        } while (z);
        return strTrim.trim();
    }

    private static String[] kq() {
        return new String[]{"\r\n", "\r", "\n"};
    }

    private static String e(InputStream inputStream) {
        int i;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            byte[] bArr = new byte[1024];
            do {
                i = inputStream.read(bArr);
                stringBuffer.append(new String(bArr, 0, i));
            } while (i > 0);
            inputStream.close();
        } catch (Exception e) {
        }
        return stringBuffer.toString();
    }

    private static void kr() {
        Sb = f(Sb);
        Sd = f(Sd);
        RZ = f(RZ);
        Sa = f(Sa);
        Se = f(Se);
    }

    private static int[] f(int[] iArr) {
        if (iArr == null || iArr.length != Sf) {
            int[] iArr2 = new int[Sf];
            d(iArr2, -1);
            return iArr2;
        }
        return iArr;
    }

    private static String dg(int i) {
        int i2;
        if (Sk != null && (i2 = Sk[i]) >= 0) {
            return Si[i2];
        }
        return null;
    }

    public static String bv(String str) {
        try {
            x(bq(ko()), str);
        } catch (Exception e) {
            be("Error:" + e);
        }
        if (bo(str) && Si != null && Sj != null) {
            for (int i = 0; i < Si.length; i++) {
                if (!y(bq(Si[i]), Sj[i])) {
                    return Si[i];
                }
            }
        }
        return null;
    }

    public static String bw(String str) {
        if (Si == null || Sj == null) {
            return null;
        }
        for (int i = 0; i < Si.length; i++) {
            if (Si[i].equals(str)) {
                if (i + 1 >= Si.length) {
                    return null;
                }
                if (!y(bq(Si[i + 1]), Sj[i + 1])) {
                    return Si[i + 1];
                }
            }
        }
        return null;
    }

    public static String c(byte[] bArr) {
        k kVar = new k(1);
        kVar.b(bArr, 0, bArr.length);
        byte[] bArr2 = new byte[kVar.im()];
        kVar.f(bArr2, 0);
        return kVar.b(bArr2);
    }

    private static void i(byte[] bArr, int i) {
        Sl = bArr;
        Sm = i;
        Sr = 13;
        Sp = 0;
        So = 0L;
        Sn = 4294967295L;
        bArr[Sm - 1] = -1;
        for (int i2 = 0; i2 < 5; i2++) {
            long j = So << 8;
            byte[] bArr2 = Sl;
            int i3 = Sr;
            Sr = i3 + 1;
            So = j | ((long) (bArr2[i3] & 255));
        }
    }

    private static int dh(int i) {
        long j = (Sn >> 11) * ((long) Ss[i]);
        if (So < j) {
            Sn = j;
            short[] sArr = Ss;
            sArr[i] = (short) (sArr[i] + ((2048 - Ss[i]) >> 5));
            if (Sn < 16777216) {
                long j2 = So << 8;
                byte[] bArr = Sl;
                int i2 = Sr;
                Sr = i2 + 1;
                So = j2 | ((long) (bArr[i2] & 255));
                Sn <<= 8;
            }
            return 0;
        }
        Sn -= j;
        So -= j;
        short[] sArr2 = Ss;
        sArr2[i] = (short) (sArr2[i] - (Ss[i] >> 5));
        if (Sn < 16777216) {
            long j3 = So << 8;
            byte[] bArr2 = Sl;
            int i3 = Sr;
            Sr = i3 + 1;
            So = j3 | ((long) (bArr2[i3] & 255));
            Sn <<= 8;
        }
        return 1;
    }

    private static int a(int i, byte b) {
        int iDh = 1;
        do {
            int i2 = (b >> 7) & 1;
            b = (byte) (b << 1);
            int iDh2 = dh(((i2 + 1) << 8) + i + iDh);
            iDh = (iDh << 1) | iDh2;
            if (i2 != iDh2) {
                while (iDh < 256) {
                    iDh = dh(iDh + i) | (iDh << 1);
                }
                break;
            }
        } while (iDh < 256);
        return iDh;
    }

    private static int di(int i) {
        int iDh = 1;
        do {
            iDh = dh(iDh + i) | (iDh << 1);
        } while (iDh < 256);
        return iDh;
    }

    private static int O(int i, int i2) {
        int iDh = 1;
        for (int i3 = i2; i3 > 0; i3--) {
            iDh = dh(iDh + i) + (iDh << 1);
        }
        return iDh - (1 << i2);
    }

    private static int P(int i, int i2) {
        if (dh(i + 0) == 0) {
            return O(i + 2 + (i2 << 3), 3);
        }
        if (dh(i + 1) == 0) {
            return O(i + 130 + (i2 << 3), 3) + 8;
        }
        return O(i + 258, 8) + 16;
    }

    private static int Q(int i, int i2) {
        int i3 = 1;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            int iDh = dh(i + i3);
            i3 = (i3 << 1) + iDh;
            int i6 = (iDh << i4) | i5;
            i4++;
            i5 = i6;
        }
        return i5;
    }

    private static int dj(int i) {
        long j = Sn;
        long j2 = So;
        int i2 = 0;
        while (i > 0) {
            j >>= 1;
            i2 <<= 1;
            if (j2 >= j) {
                j2 -= j;
                i2 |= 1;
            }
            if (j < 16777216) {
                j <<= 8;
                byte[] bArr = Sl;
                int i3 = Sr;
                Sr = i3 + 1;
                j2 = (j2 << 8) | ((long) (bArr[i3] & 255));
            }
            i--;
        }
        Sn = j;
        So = j2;
        return i2;
    }

    private static void a(int i, int i2, int i3, int i4, byte[] bArr, int i5) {
        int iDi;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int iP;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15 = (768 << (i2 + i3)) + 1846;
        short[] sArr = Ss;
        int i16 = 0;
        boolean z = false;
        int i17 = 1;
        int i18 = 1;
        int i19 = 1;
        int i20 = 1;
        int i21 = 0;
        int i22 = (1 << i4) - 1;
        int i23 = (1 << i3) - 1;
        if (i >= (i15 << 1)) {
            for (int i24 = 0; i24 < i15; i24++) {
                sArr[i24] = 1024;
            }
            i(bArr, bArr.length);
            int i25 = 0;
            while (i21 < i5) {
                int i26 = i21 & i22;
                if (dh((i16 << 4) + 0 + i26) == 0) {
                    int i27 = ((((i21 & i23) << i2) + ((i25 & 255) >> (8 - i2))) * 768) + 1846;
                    if (i16 < 4) {
                        i16 = 0;
                    } else if (i16 < 10) {
                        i16 -= 3;
                    } else {
                        i16 -= 6;
                    }
                    if (z) {
                        iDi = a(i27, Sq[i21 - i17]) & 255;
                        z = false;
                    } else {
                        iDi = di(i27) & 255;
                    }
                    i6 = i21 + 1;
                    Sq[i21] = (byte) iDi;
                    i7 = i20;
                    i8 = i19;
                    i9 = i18;
                    i10 = i17;
                } else {
                    if (dh(i16 + 192) == 1) {
                        if (dh(i16 + 204) == 0) {
                            if (dh((i16 << 4) + 240 + i26) == 0) {
                                int i28 = i16 < 7 ? 9 : 11;
                                int i29 = Sq[i21 - i17] & 255;
                                Sq[i21] = (byte) i29;
                                i21++;
                                i25 = i29;
                                z = true;
                                i16 = i28;
                                // Original DEX branches directly to the outer decode loop.
                                continue;
                            } else {
                                i13 = i17;
                            }
                        } else {
                            if (dh(i16 + 216) == 0) {
                                i13 = i18;
                            } else {
                                if (dh(i16 + 228) == 0) {
                                    i13 = i19;
                                    i14 = i20;
                                } else {
                                    i13 = i20;
                                    i14 = i19;
                                }
                                i20 = i14;
                                i19 = i18;
                            }
                            i18 = i17;
                        }
                        iP = P(1332, i26);
                        i7 = i20;
                        i8 = i19;
                        i9 = i18;
                        i10 = i13;
                        i16 = i16 < 7 ? 8 : 11;
                    } else {
                        int i30 = i16 < 7 ? 7 : 10;
                        int iP2 = P(818, i26);
                        int iO = O(((iP2 < 4 ? iP2 : 3) << 6) + 432, 6);
                        if (iO >= 4) {
                            int i31 = (iO >> 1) - 1;
                            int i32 = ((iO & 1) | 2) << i31;
                            if (iO < 14) {
                                iO = Q(((i32 + 688) - iO) - 1, i31) + i32;
                            } else {
                                iO = (dj(i31 - 4) << 4) + i32 + Q(802, 4);
                            }
                        }
                        iP = iP2;
                        i7 = i19;
                        i8 = i18;
                        i9 = i17;
                        i10 = iO + 1;
                        i16 = i30;
                    }
                    int i33 = iP + 2;
                    int i34 = i21;
                    while (true) {
                        i11 = Sq[i34 - i10] & 255;
                        i12 = i34 + 1;
                        Sq[i34] = (byte) i11;
                        i33--;
                        if (i33 <= 0 || i12 >= i5) {
                            break;
                        } else {
                            i34 = i12;
                        }
                    }
                    i6 = i12;
                    iDi = i11;
                    z = true;
                }
                i21 = i6;
                i20 = i7;
                i19 = i8;
                i18 = i9;
                i17 = i10;
                i25 = iDi;
            }
        }
    }

    private static void a(InputStream inputStream, int i) {
        if (w.Wj) {
            byte[] bArr = new byte[i + 1];
            b(inputStream, bArr, 0, i);
            int i2 = 0;
            for (int i3 = 0; i3 < 4; i3++) {
                i2 += (bArr[i3 + 5] & 255) << (i3 << 3);
            }
            int i4 = bArr[0] & 255;
            int i5 = i4 / 45;
            int i6 = i4 % 45;
            int i7 = i6 / 9;
            int i8 = i6 % 9;
            int i9 = (768 << (i8 + i7)) + 1846;
            Sq = new byte[i2];
            Ss = new short[i9];
            a(i9 * 2, i8, i7, i5, bArr, i2);
            Ss = null;
            Sl = null;
            if (w.Vf) {
            }
        }
    }

    public static final int dx(int i) {
        int i2 = (((((i >> 16) & 255) * 30) + (((i >> 8) & 255) * 59)) + ((i & 255) * 11)) / 100;
        return (((i >> 24) & 255) << 24) | (i2 << 16) | (i2 << 8) | i2;
    }

    public static final void dk(int i) {
        k((i >> 16) & 255, (i >> 8) & 255, i & 255);
    }

    public static final void dl(int i) {
        int iDL = dL(i);
        SR = (iDL >> 24) & 255;
        SO = (iDL >> 16) & 255;
        SP = (iDL >> 8) & 255;
        SQ = iDL & 255;
    }

    public static final void k(int i, int i2, int i3) {
        int iDL = dL(((i & 255) << 16) | ((i2 & 255) << 8) | (i3 & 255));
        SR = 255;
        SO = (iDL >> 16) & 255;
        SP = (iDL >> 8) & 255;
        SQ = iDL & 255;
    }

    private static int bN(String str) {
        javax.microedition.lcdui.u uVar = Qk;
        int i = (SR << 24) | (SO << 16) | (SP << 8) | SQ;
        for (int i2 = 0; i2 < w.VF; i2++) {
            if (str.equals(UT[i2]) && i == US[i2] && a(uVar, UQ[i2])) {
                return i2;
            }
        }
        return -1;
    }

    private static void i(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        javax.microedition.lcdui.u uVar = Qk;
        String str = UT[i];
        ay ayVar = UR[i];
        int iJe = uVar.je(str);
        int height = uVar.getHeight();
        if ((i4 & 2) != 0) {
            i5 = i3 - (height / 2);
        } else {
            i5 = (i4 & 32) != 0 ? i3 - height : i3;
        }
        if ((i4 & 1) != 0) {
            i6 = i2 - (iJe / 2);
        } else {
            i6 = (i4 & 8) != 0 ? i2 - iJe : i2;
        }
        a(Qj, UR[i], i6, i5, 20, true);
        while (i > 0) {
            UT[i] = UT[i - 1];
            US[i] = US[i - 1];
            UQ[i] = UQ[i - 1];
            UR[i] = UR[i - 1];
            i--;
        }
        US[0] = (SR << 24) | (SO << 16) | (SP << 8) | SQ;
        UQ[0] = uVar;
        UT[0] = str;
        UR[0] = ayVar;
    }

    public static final void a(aj ajVar, int i, int i2, int i3, int i4, boolean z) {
        f(i, i2, i3, i4);
    }

    public static final void a(aj ajVar, float f, float f2, float f3, float f4, boolean z) {
        b(ajVar, f, f2, f3, f4);
    }

    public static final void b(aj ajVar, float f, float f2, float f3, float f4, boolean z) {
        d(ajVar, f, f2, f3, f4);
    }

    public static final void c(aj ajVar, float f, float f2, float f3, float f4, boolean z) {
        c(ajVar, f, f2, f3, f4);
    }

    public static final void a(float f, float f2, float f3, float f4, int i, int i2) {
        a(Qj, f, f2, f3, f4, i, i2, true);
    }

    public static final void a(aj ajVar, float f, float f2, float f3, float f4, int i, int i2, boolean z) {
        b(ajVar, f, f2, f3, f4, i, i2);
    }

    public static final void b(aj ajVar, float f, float f2, float f3, float f4, int i, int i2, boolean z) {
        a(ajVar, f, f2, f3, f4, i, i2);
    }

    public static final void a(String str, int i, int i2, int i3) {
        int i4;
        int i5;
        c(str != null, "DrawString.str is null");
        c((i3 & 50) != 0, "DrawString.anchor miss vertical positionning");
        c((i3 & 13) != 0, "DrawString.anchor miss horizontal positionning");
        try {
            if (Rj != null) {
                Rj.a(Qj, str, i, i2, i3);
                return;
            }
            if (!w.VE) {
                int iJe = Qk.je(str);
                int height = Qk.getHeight();
                int iCS = cS(iJe);
                int iCS2 = cS(height);
                javax.microedition.lcdui.ao aoVarCL = javax.microedition.lcdui.ao.cL(iCS, iCS2);
                javax.microedition.lcdui.al alVarAkr = aoVarCL.akr();
                alVarAkr.a(Qk);
                alVarAkr.ah(SO, SP, SQ);
                alVarAkr.b(str, 0, 0, 20);
                int[] iArrC = a.c((int[]) null);
                aoVarCL.a(iArrC, 0, iCS, 0, 0, iCS, iCS2);
                if ((i3 & 2) != 0) {
                    i4 = i2 - (height / 2);
                } else {
                    i4 = (i3 & 32) != 0 ? i2 - height : i2;
                }
                if ((i3 & 1) != 0) {
                    i5 = i - (iJe / 2);
                } else {
                    i5 = (i3 & 8) != 0 ? i - iJe : i;
                }
                a(Qj, iArrC, 0, iCS, i5, i4, iCS, iCS2, true, true, 20);
                return;
            }
            if (!lH()) {
                lI();
            }
            int iBN = bN(str);
            if (iBN == -1) {
                bO(str);
                i(0, i, i2, i3);
            } else {
                i(iBN, i, i2, i3);
            }
        } catch (Exception e) {
        }
    }

    public static final void a(aj ajVar, ay ayVar, int i, int i2, int i3, boolean z) {
        c(ayVar != null, "DrawImage.data is null");
        c((i3 & 50) != 0, "DrawImage.anchor miss vertical positioning");
        c((i3 & 13) != 0, "DrawImage.anchor miss horizontal positioning");
        a(ajVar, ayVar, 0, 0, ayVar.getWidth(), ayVar.getHeight(), 0, i, i2, i3);
    }

    public static final void a(aj ajVar, ay ayVar, int i, int i2, int i3, int i4) {
        c(ayVar != null, "DrawImage.data is null");
        c((i3 & 50) != 0, "DrawImage.anchor miss vertical positioning");
        c((i3 & 13) != 0, "DrawImage.anchor miss horizontal positioning");
        c(i4, i4, false);
        a(ajVar, ayVar, 0, 0, ayVar.getWidth(), ayVar.getHeight(), 0, i, i2, i3);
        c(100.0f, 100.0f, false);
    }

    public static final void a(aj ajVar, ay ayVar, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        c(ayVar != null, "DrawRegion.src is null");
        c((i8 & 50) != 0, "DrawRegion.anchor miss vertical positionning");
        c((i8 & 13) != 0, "DrawRegion.anchor miss horizontal positionning");
        c(i5 >= 0, "DrawRegion.transform is invalid");
        c(i5 <= 7, "DrawRegion.transform is invalid");
        c(i >= 0, "DrawRegion.x_src is negative");
        c(i2 >= 0, "DrawRegion.y_src is negative");
        a(ajVar, ayVar, i, i2, i3, i4, i5, i6, i7, i8);
    }

    public static final void a(aj ajVar, float f, float f2, float f3, float f4, float f5, float f6, boolean z) {
        a(ajVar, f, f2, f3, f4, f5, f6);
        if (w.Vm) {
            a(ajVar, f, f2, f3, f4, false);
            a(ajVar, f, f2, f5, f6, false);
            a(ajVar, f5, f6, f3, f4, false);
        }
    }

    public static final void a(aj ajVar, int[] iArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, int i7) {
        a(ajVar, iArr, i, i2, i3, i4, i5, i6, z, z2, i7, -1, true);
    }

    public static final void a(aj ajVar, int[] iArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, int i7, int i8, boolean z3) {
        c(iArr != null, "DrawRGB. invalid parameter: rgbData is null");
        c(i >= 0, "DrawRGB. invalid parameter: offset < 0");
        c(i5 >= 0, "DrawRGB. invalid parameter: width < 0");
        c(i6 >= 0, "DrawRGB. invalid parameter: height < 0");
        c(i2 > 0, "DrawRGB. invalid parameter: scanlength <= 0");
        c((i + i5) + ((i6 + (-1)) * i2) <= iArr.length, "DrawRGB. invalid parameter(s) out of array bounds");
        c(i7 == 0 || i2 == i5, "DrawRGB. invalid parameter mix: flags are not 0 and scanlength is not the width!");
        a(ajVar, iArr, i, i2, i3, i4, i5, i6, z);
    }

    private static void a(GL10 gl10) {
        Qj = new aj();
        Th = new float[16];
        int iKH = kH();
        int iKI = kI();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        m2do(0);
        if (w.Vx) {
            GLES20.glEnable(2929);
            GLES20.glDepthRangef(ST, SU);
            GLES20.glDepthFunc(515);
        }
        Tc = new int[1];
        GLES20.glGenFramebuffers(1, Tc, 0);
        a(new float[w.Vs * 8], new short[w.Vs]);
        kt();
        ku();
        if (w.Vz) {
            Ql = new ay(null, iKH, iKI);
        }
    }

    public static void a(float[] fArr, short[] sArr) {
        SJ = fArr;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(SJ.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        SI = byteBufferAllocateDirect.asFloatBuffer();
        SL = sArr;
        ByteBuffer byteBufferAllocateDirect2 = ByteBuffer.allocateDirect(SL.length * 2);
        byteBufferAllocateDirect2.order(ByteOrder.nativeOrder());
        SK = byteBufferAllocateDirect2.asShortBuffer();
        Sz = 0;
        SA = 0;
        SB = 0;
    }

    private static int a(ay ayVar, int i, int i2) {
        switch (i2) {
            case 0:
            case 1:
                return Tb[i][0];
            case 2:
                if (ayVar.nj()) {
                    return Tb[i][kE() ? (char) 3 : (char) 2];
                }
                return Tb[i][1];
            default:
                return -1;
        }
    }

    public static void ks() {
        int iKH = kH();
        int iKI = kI();
        if ((Sy != null ? Sy.mm() : -1) == -1) {
            Th = a(0.0f, iKH / SY, iKI / SZ, 0.0f, ST, SU);
        } else {
            Th = a(0.0f, iKH / SY, 0.0f, iKI / SZ, ST, SU);
        }
        f(SE, SF, SG, SH);
    }

    public static float[] a(float f, float f2, float f3, float f4, float f5, float f6) {
        return new float[]{2.0f / (f2 - f), 0.0f, 0.0f, (-(f2 + f)) / (f2 - f), 0.0f, 2.0f / (f4 - f3), 0.0f, (-(f4 + f3)) / (f4 - f3), 0.0f, 0.0f, 2.0f / (f6 - f5), (-(f6 + f5)) / (f6 - f5), 0.0f, 0.0f, 0.0f, 1.0f};
    }

    private static void kt() {
        String str;
        String lowerCase = GLES20.glGetString(7937).toLowerCase();
        if (lowerCase.indexOf("mali") != -1) {
            str = "#define INDEX_SHIFT\n";
        } else if (lowerCase.indexOf("powervr") != -1) {
            str = "#define PRECISION_HP\n";
        } else if (lowerCase.indexOf("tegra") == -1) {
            str = "";
        } else {
            str = ("#define PRECISION_HP\n") + "#define INDEX_SHIFT\n";
        }
        Tb = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 5, 4);
        for (int i = 0; i < Tb.length; i++) {
            for (int i2 = 0; i2 < Tb[i].length; i2++) {
                Tb[i][i2] = -1;
            }
        }
        b("Load Shaders. Defines: " + str, "OPEN_GL", 3);
        Tb[0][0] = z("attribute vec4 vPosition;attribute vec4 vColor;varying vec4 v_color;uniform mat4 projectionMatrix;void main() {\tgl_Position = vPosition * projectionMatrix;\tv_color = vColor;}", "precision mediump float;varying vec4 v_color;uniform vec4 fxColor;void main() {\tgl_FragColor = vec4(v_color.rgb, v_color.a * fxColor.a);}");
        Tb[0][1] = z("uniform mat4 projectionMatrix;attribute vec4 vPosition;attribute vec2 texCoord;varying highp vec2 uv;void main() {\tgl_Position = vPosition * projectionMatrix;\tuv = texCoord;}", "precision mediump float;varying vec2 uv;uniform vec4 fxColor;uniform sampler2D textureSampler;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\tvec4 color = vec4(texture2D(textureSampler, uv));\tgl_FragColor = vec4(color.rgb, color.a * fxColor.a);}");
        Tb[0][2] = z("uniform mat4 projectionMatrix;attribute vec4 vPosition;attribute vec2 texCoord;varying highp vec2 uv;void main() {\tgl_Position = vPosition * projectionMatrix;\tuv = texCoord;}", str + "#ifdef PRECISION_HP\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nvarying vec2 uv;uniform vec4 fxColor;uniform sampler2D textureSampler;uniform sampler2D paletteSampler;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\n\t#ifdef INDEX_SHIFT\n\tvec2 index = vec2(texture2D(textureSampler, uv).r * 255.0/256.0 + 0.5/256.0, 0);\n\t#else\n\tvec2 index = vec2(texture2D(textureSampler, uv).r, 0);\n\t#endif\n\tvec4 color = texture2D(paletteSampler, index);\tgl_FragColor = vec4(color.rgb, color.a * fxColor.a);}");
        Tb[0][3] = z("uniform mat4 projectionMatrix;uniform float width;uniform float height;attribute vec4 vPosition;attribute vec4 texCoord;varying highp vec2 uv00;varying highp vec2 uv01;varying highp vec2 uv10;varying highp vec2 uv11;varying highp vec2 uvForFrac;void main() {\tgl_Position = vPosition * projectionMatrix;\thighp float texelSizeX = 1.0 / width;\n\thighp float texelSizeY = 1.0 / height;\n \tuv00 = texCoord.xy - vec2(texelSizeX * 0.5 , texelSizeY * 0.5);\tuv10 = uv00 + vec2(texelSizeX, 0);\tuv01 = uv00 + vec2(0, texelSizeY);\tuv11 = uv00 + vec2(texelSizeX, texelSizeY);\tuvForFrac =  texCoord.zw - vec2(0.5, 0.5);}", str + "#ifdef PRECISION_HP\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nvarying highp vec2 uv00;varying highp vec2 uv01;varying highp vec2 uv10;varying highp vec2 uv11;uniform vec4 fxColor;uniform sampler2D textureSampler;uniform sampler2D paletteSampler;varying highp vec2 uvForFrac;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);vec4 getColor(sampler2D textureSampler_i, sampler2D paletteSampler_i, vec2 texCoord_i){\n\t#ifdef INDEX_SHIFT\n\tvec2 index = vec2(texture2D(textureSampler_i, texCoord_i).r * 255.0/256.0 + 0.5/256.0, 0);\n\t#else\n\tvec2 index = vec2(texture2D(textureSampler_i, texCoord_i).r, 0);\n\t#endif\n\treturn texture2D(paletteSampler_i, index);}\nvec4 tex2DBiLinear(sampler2D textureSampler_i, sampler2D paletteSampler_i){\tvec4 p00 = getColor(textureSampler_i, paletteSampler_i, uv00);\n\tvec4 p10 = getColor(textureSampler_i, paletteSampler_i, uv10);\n\tvec4 p01 = getColor(textureSampler_i, paletteSampler_i, uv01);\n\tvec4 p11 = getColor(textureSampler_i, paletteSampler_i, uv11);\n\tfloat a = fract(uvForFrac.x);\n\tvec4 pInterp0 = mix(p00, p10, a);\n\tvec4 pInterp1 = mix(p01, p11, a);\n\tfloat b = fract(uvForFrac.y);\n\treturn mix(pInterp0, pInterp1, b);\n}\nvoid main() {\n\tvec4 color = tex2DBiLinear(textureSampler, paletteSampler);\tgl_FragColor = vec4(color.rgb, color.a * fxColor.a);}");
        Tb[2][0] = z("attribute vec4 vPosition;attribute vec4 vColor;varying vec4 v_color;uniform mat4 projectionMatrix;void main() {\tgl_Position = vPosition * projectionMatrix;\tv_color = vColor;}", "precision mediump float;varying vec4 v_color;uniform vec4 fxColor;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\tfloat lum = dot(v_color.rgb, coef);\tgl_FragColor = vec4(vec3(lum), v_color.a * fxColor.a);}");
        Tb[2][1] = z("uniform mat4 projectionMatrix;attribute vec4 vPosition;attribute vec2 texCoord;varying highp vec2 uv;void main() {\tgl_Position = vPosition * projectionMatrix;\tuv = texCoord;}", "precision mediump float;varying vec2 uv;uniform vec4 fxColor;uniform sampler2D textureSampler;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\tvec4 color = vec4(texture2D(textureSampler, uv));\tfloat lum = dot(color.rgb, coef);\tgl_FragColor = vec4(vec3(lum), color.a * fxColor.a);}");
        Tb[2][2] = z("uniform mat4 projectionMatrix;attribute vec4 vPosition;attribute vec2 texCoord;varying highp vec2 uv;void main() {\tgl_Position = vPosition * projectionMatrix;\tuv = texCoord;}", str + "#ifdef PRECISION_HP\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nvarying vec2 uv;uniform vec4 fxColor;uniform sampler2D textureSampler;uniform sampler2D paletteSampler;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\n\t#ifdef INDEX_SHIFT\n\tvec2 index = vec2(texture2D(textureSampler, uv).r * 255.0/256.0 + 0.5/256.0, 0);\n\t#else\n\tvec2 index = vec2(texture2D(textureSampler, uv).r, 0);\n\t#endif\n\tvec4 color = texture2D(paletteSampler, index);\tfloat lum = dot(color.rgb, coef);\tgl_FragColor = vec4(vec3(lum), color.a * fxColor.a);}");
        Tb[2][3] = z("uniform mat4 projectionMatrix;uniform float width;uniform float height;attribute vec4 vPosition;attribute vec4 texCoord;varying highp vec2 uv00;varying highp vec2 uv01;varying highp vec2 uv10;varying highp vec2 uv11;varying highp vec2 uvForFrac;void main() {\tgl_Position = vPosition * projectionMatrix;\thighp float texelSizeX = 1.0 / width;\n\thighp float texelSizeY = 1.0 / height;\n \tuv00 = texCoord.xy - vec2(texelSizeX * 0.5 , texelSizeY * 0.5);\tuv10 = uv00 + vec2(texelSizeX, 0);\tuv01 = uv00 + vec2(0, texelSizeY);\tuv11 = uv00 + vec2(texelSizeX, texelSizeY);\tuvForFrac =  texCoord.zw - vec2(0.5, 0.5);}", str + "#ifdef PRECISION_HP\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nvarying highp vec2 uv00;varying highp vec2 uv01;varying highp vec2 uv10;varying highp vec2 uv11;uniform vec4 fxColor;uniform sampler2D textureSampler;uniform sampler2D paletteSampler;varying highp vec2 uvForFrac;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);vec4 getColor(sampler2D textureSampler_i, sampler2D paletteSampler_i, vec2 texCoord_i){\n\t#ifdef INDEX_SHIFT\n\tvec2 index = vec2(texture2D(textureSampler_i, texCoord_i).r * 255.0/256.0 + 0.5/256.0, 0);\n\t#else\n\tvec2 index = vec2(texture2D(textureSampler_i, texCoord_i).r, 0);\n\t#endif\n\treturn texture2D(paletteSampler_i, index);}\nvec4 tex2DBiLinear(sampler2D textureSampler_i, sampler2D paletteSampler_i){\tvec4 p00 = getColor(textureSampler_i, paletteSampler_i, uv00);\n\tvec4 p10 = getColor(textureSampler_i, paletteSampler_i, uv10);\n\tvec4 p01 = getColor(textureSampler_i, paletteSampler_i, uv01);\n\tvec4 p11 = getColor(textureSampler_i, paletteSampler_i, uv11);\n\tfloat a = fract(uvForFrac.x);\n\tvec4 pInterp0 = mix(p00, p10, a);\n\tvec4 pInterp1 = mix(p01, p11, a);\n\tfloat b = fract(uvForFrac.y);\n\treturn mix(pInterp0, pInterp1, b);\n}\nvoid main() {\n\tvec4 color = tex2DBiLinear(textureSampler, paletteSampler);\tfloat lum = dot(color.rgb, coef);\tgl_FragColor = vec4(vec3(lum), color.a * fxColor.a);}");
        Tb[1][0] = z("attribute vec4 vPosition;attribute vec4 vColor;varying vec4 v_color;uniform mat4 projectionMatrix;void main() {\tgl_Position = vPosition * projectionMatrix;\tv_color = vColor;}", "precision mediump float;varying vec4 v_color;uniform vec4 fxColor;void main() {\tgl_FragColor = vec4(fxColor.rgb, v_color.a * fxColor.a);}");
        Tb[1][1] = z("uniform mat4 projectionMatrix;attribute vec4 vPosition;attribute vec2 texCoord;varying highp vec2 uv;void main() {\tgl_Position = vPosition * projectionMatrix;\tuv = texCoord;}", "precision mediump float;varying vec2 uv;uniform vec4 fxColor;uniform sampler2D textureSampler;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\tvec4 color = vec4(texture2D(textureSampler, uv));\tgl_FragColor = vec4(fxColor.rgb, color.a * fxColor.a);}");
        Tb[1][2] = z("uniform mat4 projectionMatrix;attribute vec4 vPosition;attribute vec2 texCoord;varying highp vec2 uv;void main() {\tgl_Position = vPosition * projectionMatrix;\tuv = texCoord;}", str + "#ifdef PRECISION_HP\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nvarying vec2 uv;uniform vec4 fxColor;uniform sampler2D textureSampler;uniform sampler2D paletteSampler;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\n\t#ifdef INDEX_SHIFT\n\tvec2 index = vec2(texture2D(textureSampler, uv).r * 255.0/256.0 + 0.5/256.0, 0);\n\t#else\n\tvec2 index = vec2(texture2D(textureSampler, uv).r, 0);\n\t#endif\n\tvec4 color = texture2D(paletteSampler, index);\tgl_FragColor = vec4(fxColor.rgb, color.a * fxColor.a);}");
        Tb[1][3] = z("uniform mat4 projectionMatrix;uniform float width;uniform float height;attribute vec4 vPosition;attribute vec4 texCoord;varying highp vec2 uv00;varying highp vec2 uv01;varying highp vec2 uv10;varying highp vec2 uv11;varying highp vec2 uvForFrac;void main() {\tgl_Position = vPosition * projectionMatrix;\thighp float texelSizeX = 1.0 / width;\n\thighp float texelSizeY = 1.0 / height;\n \tuv00 = texCoord.xy - vec2(texelSizeX * 0.5 , texelSizeY * 0.5);\tuv10 = uv00 + vec2(texelSizeX, 0);\tuv01 = uv00 + vec2(0, texelSizeY);\tuv11 = uv00 + vec2(texelSizeX, texelSizeY);\tuvForFrac =  texCoord.zw - vec2(0.5, 0.5);}", str + "#ifdef PRECISION_HP\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nvarying highp vec2 uv00;varying highp vec2 uv01;varying highp vec2 uv10;varying highp vec2 uv11;uniform vec4 fxColor;uniform sampler2D textureSampler;uniform sampler2D paletteSampler;varying highp vec2 uvForFrac;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);vec4 getColor(sampler2D textureSampler_i, sampler2D paletteSampler_i, vec2 texCoord_i){\n\t#ifdef INDEX_SHIFT\n\tvec2 index = vec2(texture2D(textureSampler_i, texCoord_i).r * 255.0/256.0 + 0.5/256.0, 0);\n\t#else\n\tvec2 index = vec2(texture2D(textureSampler_i, texCoord_i).r, 0);\n\t#endif\n\treturn texture2D(paletteSampler_i, index);}\nvec4 tex2DBiLinear(sampler2D textureSampler_i, sampler2D paletteSampler_i){\tvec4 p00 = getColor(textureSampler_i, paletteSampler_i, uv00);\n\tvec4 p10 = getColor(textureSampler_i, paletteSampler_i, uv10);\n\tvec4 p01 = getColor(textureSampler_i, paletteSampler_i, uv01);\n\tvec4 p11 = getColor(textureSampler_i, paletteSampler_i, uv11);\n\tfloat a = fract(uvForFrac.x);\n\tvec4 pInterp0 = mix(p00, p10, a);\n\tvec4 pInterp1 = mix(p01, p11, a);\n\tfloat b = fract(uvForFrac.y);\n\treturn mix(pInterp0, pInterp1, b);\n}\nvoid main() {\n\tvec4 color = tex2DBiLinear(textureSampler, paletteSampler);\tgl_FragColor = vec4(fxColor.rgb, color.a * fxColor.a);}");
        Tb[4][0] = z("attribute vec4 vPosition;attribute vec4 vColor;varying vec4 v_color;uniform mat4 projectionMatrix;void main() {\tgl_Position = vPosition * projectionMatrix;\tv_color = vColor;}", "precision mediump float;varying vec4 v_color;uniform vec4 fxColor;void main() {\tvec4 color = v_color;\tcolor.rgb = mix(color.rgb, fxColor.rgb, fxColor.a);\tgl_FragColor = color;}");
        Tb[4][1] = z("uniform mat4 projectionMatrix;attribute vec4 vPosition;attribute vec2 texCoord;varying highp vec2 uv;void main() {\tgl_Position = vPosition * projectionMatrix;\tuv = texCoord;}", "precision mediump float;varying vec2 uv;uniform vec4 fxColor;uniform sampler2D textureSampler;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\tvec4 color = vec4(texture2D(textureSampler, uv));\tcolor.rgb = mix(color.rgb, fxColor.rgb, fxColor.a);\tgl_FragColor = color;}");
        Tb[4][2] = z("uniform mat4 projectionMatrix;attribute vec4 vPosition;attribute vec2 texCoord;varying highp vec2 uv;void main() {\tgl_Position = vPosition * projectionMatrix;\tuv = texCoord;}", str + "#ifdef PRECISION_HP\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nvarying vec2 uv;uniform vec4 fxColor;uniform sampler2D textureSampler;uniform sampler2D paletteSampler;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);void main() {\n\t#ifdef INDEX_SHIFT\n\tvec2 index = vec2(texture2D(textureSampler, uv).r * 255.0/256.0 + 0.5/256.0, 0);\n\t#else\n\tvec2 index = vec2(texture2D(textureSampler, uv).r, 0);\n\t#endif\n\tvec4 color = texture2D(paletteSampler, index);\tcolor.rgb = mix(color.rgb, fxColor.rgb, fxColor.a);\tgl_FragColor = color;}");
        Tb[4][3] = z("uniform mat4 projectionMatrix;uniform float width;uniform float height;attribute vec4 vPosition;attribute vec4 texCoord;varying highp vec2 uv00;varying highp vec2 uv01;varying highp vec2 uv10;varying highp vec2 uv11;varying highp vec2 uvForFrac;void main() {\tgl_Position = vPosition * projectionMatrix;\thighp float texelSizeX = 1.0 / width;\n\thighp float texelSizeY = 1.0 / height;\n \tuv00 = texCoord.xy - vec2(texelSizeX * 0.5 , texelSizeY * 0.5);\tuv10 = uv00 + vec2(texelSizeX, 0);\tuv01 = uv00 + vec2(0, texelSizeY);\tuv11 = uv00 + vec2(texelSizeX, texelSizeY);\tuvForFrac =  texCoord.zw - vec2(0.5, 0.5);}", str + "#ifdef PRECISION_HP\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nvarying highp vec2 uv00;varying highp vec2 uv01;varying highp vec2 uv10;varying highp vec2 uv11;uniform vec4 fxColor;uniform sampler2D textureSampler;uniform sampler2D paletteSampler;varying highp vec2 uvForFrac;const vec3 coef = vec3(0.2125, 0.7154, 0.0721);vec4 getColor(sampler2D textureSampler_i, sampler2D paletteSampler_i, vec2 texCoord_i){\n\t#ifdef INDEX_SHIFT\n\tvec2 index = vec2(texture2D(textureSampler_i, texCoord_i).r * 255.0/256.0 + 0.5/256.0, 0);\n\t#else\n\tvec2 index = vec2(texture2D(textureSampler_i, texCoord_i).r, 0);\n\t#endif\n\treturn texture2D(paletteSampler_i, index);}\nvec4 tex2DBiLinear(sampler2D textureSampler_i, sampler2D paletteSampler_i){\tvec4 p00 = getColor(textureSampler_i, paletteSampler_i, uv00);\n\tvec4 p10 = getColor(textureSampler_i, paletteSampler_i, uv10);\n\tvec4 p01 = getColor(textureSampler_i, paletteSampler_i, uv01);\n\tvec4 p11 = getColor(textureSampler_i, paletteSampler_i, uv11);\n\tfloat a = fract(uvForFrac.x);\n\tvec4 pInterp0 = mix(p00, p10, a);\n\tvec4 pInterp1 = mix(p01, p11, a);\n\tfloat b = fract(uvForFrac.y);\n\treturn mix(pInterp0, pInterp1, b);\n}\nvoid main() {\n\tvec4 color = tex2DBiLinear(textureSampler, paletteSampler);\tcolor.rgb = mix(color.rgb, fxColor.rgb, fxColor.a);\tgl_FragColor = color;}");
        Tb[3][0] = Tb[0][0];
        Tb[3][1] = Tb[0][1];
        Tb[3][2] = Tb[0][2];
        Tb[3][3] = Tb[0][3];
    }

    private static final int z(String str, String str2) {
        int iGlCreateShader = GLES20.glCreateShader(35633);
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        int iGlCreateShader2 = GLES20.glCreateShader(35632);
        GLES20.glShaderSource(iGlCreateShader2, str2);
        GLES20.glCompileShader(iGlCreateShader2);
        if (iGlCreateShader == -1) {
        }
        if (iGlCreateShader2 == -1) {
        }
        if (iGlCreateShader == -1 || iGlCreateShader2 == -1) {
            return -1;
        }
        int iGlCreateProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(iGlCreateProgram, iGlCreateShader);
        GLES20.glAttachShader(iGlCreateProgram, iGlCreateShader2);
        GLES20.glLinkProgram(iGlCreateProgram);
        return iGlCreateProgram;
    }

    private static void ku() {
        int iKH = kH();
        int iKI = kI();
        int scaleX = (int) (iKH * getScaleX());
        int scaleY = (int) (iKI * getScaleY());
        if (w.Vz) {
            GLES20.glViewport(0, 0, iKH, iKI);
        } else {
            GLES20.glViewport(ajV(), ajW(), scaleX, scaleY);
        }
        ks();
        if (w.Vw) {
            GLES20.glEnable(2960);
            GLES20.glClearStencil(0);
            GLES20.glDisable(3089);
        } else {
            GLES20.glEnable(3089);
        }
        f(0, 0, kH(), kI());
    }

    public static void c(float f, float f2, boolean z) {
        SV = f / 100.0f;
        SW = f2 / 100.0f;
        SX = z;
    }

    private static boolean kv() {
        return (SV == 1.0f && SW == 1.0f) ? false : true;
    }

    public static float kw() {
        return SY;
    }

    public static float kx() {
        return SZ;
    }

    private static final void a(int i, float f, float f2) {
        SJ[i + 0] = f;
        SJ[i + 1] = f2;
        SJ[i + 2] = SS;
    }

    private static final void dm(int i) {
        if (SB + i >= SL.length) {
            if (kD()) {
                ky();
            } else {
                kC();
            }
        }
    }

    private static final void ky() {
        String str = "-----------------------increase buffer capacity, new size = " + (SJ.length << 1);
        float[] fArr = new float[SJ.length << 1];
        short[] sArr = new short[SL.length << 1];
        a(SJ, 0, fArr, 0, SJ.length);
        a(SL, 0, sArr, 0, SL.length);
        SL = sArr;
        SJ = fArr;
    }

    private static void a(aj ajVar, float f, float f2, float f3, float f4, int i, int i2) {
        a(ajVar, 0, 3);
        dm(i2 * 2);
        a(f, f2, f3, f4, i, i2, false);
        dn(i2 + 1);
        SA += (i2 + 1) * SM;
        Sz += i2 + 1;
    }

    private static void b(aj ajVar, float f, float f2, float f3, float f4, int i, int i2) {
        a(ajVar, 1, 3);
        dm(i2 * 3);
        a(f, f2, f3, f4, i, i2, true);
        dn(i2 + 2);
        SA += (i2 + 2) * SM;
        Sz += i2 + 2;
    }

    private static void a(float f, float f2, float f3, float f4, int i, int i2, boolean z) {
        int i3;
        int i4 = ((i % 360) + 360) % 360;
        if (i2 % 360 == 0) {
            i3 = 360;
        } else {
            i3 = ((i2 % 360) + 360) % 360;
        }
        float f5 = f + (f3 / 2.0f);
        float f6 = f2 + (f4 / 2.0f);
        float f7 = f3 / 2.0f;
        float f8 = f4 / 2.0f;
        int i5 = SA;
        if (z) {
            a(i5, f5, f6);
            i5 += SM;
        }
        int i6 = i5;
        for (int i7 = 0; i7 <= i3; i7++) {
            float f9 = ((i4 + i7) * Rw) / 180.0f;
            a(i6, (((float) Math.cos(f9)) * f7) + f5, f6 - (((float) Math.sin(f9)) * f8));
            i6 += SM;
            if (i7 > 0) {
                if (z) {
                    short[] sArr = SL;
                    int i8 = SB;
                    SB = i8 + 1;
                    sArr[i8] = (short) (Sz + 0);
                    short[] sArr2 = SL;
                    int i9 = SB;
                    SB = i9 + 1;
                    sArr2[i9] = (short) (Sz + i7);
                    short[] sArr3 = SL;
                    int i10 = SB;
                    SB = i10 + 1;
                    sArr3[i10] = (short) (Sz + i7 + 1);
                } else {
                    short[] sArr4 = SL;
                    int i11 = SB;
                    SB = i11 + 1;
                    sArr4[i11] = (short) ((Sz + i7) - 1);
                    short[] sArr5 = SL;
                    int i12 = SB;
                    SB = i12 + 1;
                    sArr5[i12] = (short) (Sz + i7);
                }
            }
        }
    }

    private static void a(int i, int i2, float f, float f2, float f3, float f4) {
        int i3 = SA + Sv;
        SJ[i3 + 0] = f / i;
        SJ[i3 + 1] = f2 / i2;
        if (SN == 4) {
            SJ[i3 + 2] = 0.0f;
            SJ[i3 + 3] = 0.0f;
        }
        int i4 = i3 + SM;
        SJ[i4 + 0] = (f + f3) / i;
        SJ[i4 + 1] = f2 / i2;
        if (SN == 4) {
            SJ[i4 + 2] = f3;
            SJ[i4 + 3] = 0.0f;
        }
        int i5 = i4 + SM;
        SJ[i5 + 0] = (f + f3) / i;
        SJ[i5 + 1] = (f2 + f4) / i2;
        if (SN == 4) {
            SJ[i5 + 2] = f3;
            SJ[i5 + 3] = f4;
        }
        int i6 = i5 + SM;
        SJ[i6 + 0] = f / i;
        SJ[i6 + 1] = (f2 + f4) / i2;
        if (SN == 4) {
            SJ[i6 + 2] = 0.0f;
            SJ[i6 + 3] = f4;
        }
    }

    private static void a(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        a(f, f2, f3, f4, f5, f6, f7, f8, false);
    }

    private static void a(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean z) {
        float f9;
        float f10;
        float f11;
        float f12;
        float f13;
        float f14;
        int i = SA;
        if (Sv == 4) {
            float f15 = 1.0f;
            float f16 = 1.0f;
            if (z) {
                if (f - f7 == 0.0f) {
                    f += 1.0f;
                }
                if (f4 - f2 == 0.0f) {
                    f4 += 1.0f;
                }
                f16 = (f3 - f5) / (f - f7);
                f10 = (f6 - f8) / (f4 - f2);
                f9 = f16 * f10;
                f11 = f * f9;
                f12 = f2 * f9;
                f13 = f3 * f10;
                f14 = f4 * f10;
                f7 *= f16;
                f8 *= f16;
            } else {
                if (f3 - f == 0.0f) {
                    f3 += 1.0f;
                }
                if (f6 - f4 == 0.0f) {
                    f6 += 1.0f;
                }
                f9 = (f5 - f7) / (f3 - f);
                f15 = (f8 - f2) / (f6 - f4);
                f10 = f9 * f15;
                f11 = f * f9;
                f12 = f2 * f9;
                f13 = f3 * f10;
                f14 = f4 * f10;
                f5 *= f15;
                f6 *= f15;
            }
            a(i, f11, f12);
            SJ[i + 3] = f9;
            int i2 = SM + i;
            a(i2, f13, f14);
            SJ[i2 + 3] = f10;
            int i3 = SM + i2;
            a(i3, f5, f6);
            SJ[i3 + 3] = f15;
            int i4 = SM + i3;
            a(i4, f7, f8);
            SJ[i4 + 3] = f16;
        } else {
            a(i, f, f2);
            int i5 = SM + i;
            a(i5, f3, f4);
            int i6 = i5 + SM;
            a(i6, f5, f6);
            a(i6 + SM, f7, f8);
        }
        short[] sArr = SL;
        int i7 = SB;
        SB = i7 + 1;
        sArr[i7] = (short) (Sz + 0);
        short[] sArr2 = SL;
        int i8 = SB;
        SB = i8 + 1;
        sArr2[i8] = (short) (Sz + 1);
        short[] sArr3 = SL;
        int i9 = SB;
        SB = i9 + 1;
        sArr3[i9] = (short) (Sz + 3);
        short[] sArr4 = SL;
        int i10 = SB;
        SB = i10 + 1;
        sArr4[i10] = (short) (Sz + 1);
        short[] sArr5 = SL;
        int i11 = SB;
        SB = i11 + 1;
        sArr5[i11] = (short) (Sz + 2);
        short[] sArr6 = SL;
        int i12 = SB;
        SB = i12 + 1;
        sArr6[i12] = (short) (Sz + 3);
        Sz += 4;
    }

    private static void a(float f, float f2, float f3, float f4, int i) {
        switch (i) {
            case 1:
                a(f, f2 + f4, f + f3, f2 + f4, f + f3, f2, f, f2);
                break;
            case 2:
                a(f + f3, f2, f, f2, f, f2 + f4, f + f3, f2 + f4);
                break;
            case 3:
                a(f + f3, f2 + f4, f, f2 + f4, f, f2, f + f3, f2);
                break;
            case 4:
                a(f, f2, f, f2 + f3, f + f4, f2 + f3, f + f4, f2);
                break;
            case 5:
                a(f + f4, f2, f + f4, f2 + f3, f, f2 + f3, f, f2);
                break;
            case 6:
                a(f, f2 + f3, f, f2, f + f4, f2, f + f4, f2 + f3);
                break;
            case 7:
                a(f + f4, f2 + f3, f + f4, f2, f, f2, f, f2 + f3);
                break;
            default:
                a(f, f2, f + f3, f2, f + f3, f2 + f4, f, f2 + f4);
                break;
        }
    }

    public static void a(aj ajVar, int i, int i2) {
        if (St != i || Sv != i2 || Sy != ajVar) {
            kC();
            if (Sy != ajVar) {
                Sy = ajVar;
                int iMm = ajVar != null ? ajVar.mm() : -1;
                if (iMm == -1) {
                    GLES20.glBindFramebuffer(36160, 0);
                } else {
                    GLES20.glBindFramebuffer(36160, Tc[0]);
                    GLES20.glFramebufferTexture2D(36160, 36064, 3553, iMm, 0);
                }
                ks();
                if (iMm != -1) {
                    GLES20.glClear(GLURLConnection.BUFFER_SIZE);
                }
                g(Su, true);
            }
        }
        St = i;
        Sv = i2;
        SM = (St == 2 ? SN : 4) + Sv;
    }

    private static void a(int i, int i2, float f, float f2) {
        b(i, i2, f, f2);
    }

    private static void b(int i, float f, float f2, float f3) {
        int i2 = SA;
        float f4 = (Rw * f) / 180.0f;
        float fSin = (float) Math.sin(f4);
        float fCos = (float) Math.cos(f4);
        for (int i3 = i2; i3 < (SM * i) + i2; i3 += SM) {
            float f5 = SJ[i3] - f2;
            float f6 = SJ[i3 + 1] - f3;
            SJ[i3] = ((f5 * fCos) - (f6 * fSin)) + f2;
            SJ[i3 + 1] = (f5 * fSin) + (f6 * fCos) + f3;
        }
    }

    private static void a(aj ajVar, int[] iArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        ay ayVarA = ay.a(iArr, i2, i6, true);
        a(ajVar, ayVarA, 0, 0, i5, i6, 0, i3, i4, 20);
        kC();
        ayVarA.nk();
    }

    /* JADX WARN: Code duplicated, block: B:66:0x0154 A[PHI: r2 r3 r4 r5
  0x0154: PHI (r2v4 float) = (r2v2 float), (r2v30 float) binds: [B:60:0x0118, B:30:0x0059] A[DONT_GENERATE, DONT_INLINE]
  0x0154: PHI (r3v3 float) = (r3v1 float), (r3v22 float) binds: [B:60:0x0118, B:30:0x0059] A[DONT_GENERATE, DONT_INLINE]
  0x0154: PHI (r4v5 float) = (r4v2 float), (r4v13 float) binds: [B:60:0x0118, B:30:0x0059] A[DONT_GENERATE, DONT_INLINE]
  0x0154: PHI (r5v6 float) = (r5v3 float), (r5v8 float) binds: [B:60:0x0118, B:30:0x0059] A[DONT_GENERATE, DONT_INLINE]] */
    public static void a(aj ajVar, ay ayVar, int i, int i2, int i3, int i4, int i5, float f, float f2, int i6) {
        float f3;
        float f4;
        float f5;
        float f6;
        if (i3 <= 0 || i4 <= 0) {
            return;
        }
        a(ajVar, 2, 3);
        if (SC != ayVar && SC != null) {
            kC();
        }
        dm(6);
        SC = ayVar;
        int i7 = ayVar.aiq;
        int i8 = ayVar.air;
        if (SX && kv()) {
            float f7 = f * SV;
            f3 = f2 * SW;
            f4 = f7;
        } else {
            f3 = f2;
            f4 = f;
        }
        float f8 = i3;
        float f9 = i4;
        if (i5 <= 3) {
            if (kv()) {
                f8 *= SV;
                f9 *= SW;
            }
            if ((i6 & 8) != 0) {
                f4 -= f8;
            }
            if ((i6 & 32) != 0) {
                f3 -= f9;
            }
            if ((i6 & 2) != 0) {
                f3 -= f9 / 2.0f;
            }
            if ((i6 & 1) != 0) {
                f5 = f3;
                f6 = f4 - (f8 / 2.0f);
            } else {
                f5 = f3;
                f6 = f4;
            }
        } else {
            if (kv()) {
                f8 *= SW;
                f9 *= SV;
            }
            if ((i6 & 1) != 0) {
                f4 -= f9 / 2.0f;
            }
            if ((i6 & 8) != 0) {
                f4 -= f9;
            }
            if ((i6 & 2) != 0) {
                f3 -= f8 / 2.0f;
            }
            if ((i6 & 32) != 0) {
                f5 = f3 - f8;
                f6 = f4;
            } else {
                f5 = f3;
                f6 = f4;
            }
        }
        a(f6, f5, f8, f9, i5);
        // DEX calls a(IIFFFF)V, not the touch overload a(IIIIII)Z.
        a(i7, i8, (float) i, (float) i2, (float) i3, (float) i4);
        if (w.Zo) {
            if ((TX & 262144) != 0) {
                int i9 = TY[18][7];
                int i10 = TY[18][1];
                if (i10 == 100) {
                    i10 = TY[18][3];
                }
                float f10 = (i10 * i9) / 100.0f;
                int i11 = TY[18][8];
                int i12 = TY[18][1];
                if (i12 == 100) {
                    i12 = TY[18][4];
                }
                float f11 = (i12 * i11) / 100.0f;
                if (TY[18][10] != 0) {
                    b(6, (((TY[18][10] * 360) >> w.VQ) + 0.5f) / Rk, f10 + f6, f11 + f5);
                } else {
                    a(6, (TY[18][6] * 360) >> w.VQ, f10 + f6, f11 + f5);
                }
            }
        }
        SA += SM * 4;
    }

    public static float kA() {
        return Ti;
    }

    public static float kB() {
        return Tj;
    }

    private static void b(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float fB = b(f, f2, f3, f4);
        float fB2 = b(f5, f6, f7, f8);
        float f9 = f - f3;
        float f10 = f2 - f4;
        float f11 = f5 - f7;
        float f12 = f6 - f8;
        float fB3 = b(f9, f10, f11, f12);
        if (fB3 != 0.0f) {
            Ti = b(fB, f9, fB2, f11) / fB3;
            Tj = b(fB, f10, fB2, f12) / fB3;
        }
    }

    private static float b(float f, float f2, float f3, float f4) {
        return (f * f4) - (f2 * f3);
    }

    static void a(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        float f13 = f9 / f11;
        float f14 = f10 / f12;
        float f15 = (f5 - f7) * (1.0f - f14);
        float f16 = f14 * (f3 - f);
        float f17 = f16 / (f15 + f16);
        float f18 = (f6 - f4) * (1.0f - f13);
        float f19 = f13 * (f8 - f2);
        float f20 = f19 / (f18 + f19);
        b(((f7 - f) * f17) + f, ((f8 - f2) * f17) + f2, ((f5 - f3) * f17) + f3, (f17 * (f6 - f4)) + f4, ((f3 - f) * f20) + f, ((f4 - f2) * f20) + f2, ((f5 - f7) * f20) + f7, (f20 * (f6 - f8)) + f8);
    }

    public static void a(aj ajVar, ay ayVar, int i, int i2, int i3, int i4, int i5, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        if (i3 > 0 && i4 > 0) {
            a(ajVar, 2, 4);
            if (SC != ayVar && SC != null) {
                kC();
            }
            dm(6);
            SC = ayVar;
            switch (i5) {
                case 1:
                    a(f7, f8, f5, f6, f3, f4, f, f2);
                    break;
                case 2:
                    a(f3, f4, f, f2, f7, f8, f5, f6);
                    break;
                case 3:
                    a(f5, f6, f7, f8, f, f2, f3, f4);
                    break;
                case 4:
                    a(f, f2, f7, f8, f5, f6, f3, f4, true);
                    break;
                case 5:
                    a(f3, f4, f5, f6, f7, f8, f, f2, true);
                    break;
                case 6:
                    a(f7, f8, f, f2, f3, f4, f5, f6, true);
                    break;
                case 7:
                    a(f5, f6, f3, f4, f, f2, f7, f8, true);
                    break;
                default:
                    a(f, f2, f3, f4, f5, f6, f7, f8);
                    break;
            }
            // Preserve the original texture-coordinate descriptor a(IIFFFF)V.
            a(ayVar.aiq, ayVar.air, (float) i, (float) i2, (float) i3, (float) i4);
            SA += SM * 4;
        }
    }

    private static void b(aj ajVar, float f, float f2, float f3, float f4) {
        a(ajVar, 0, 3);
        dm(2);
        int i = SA;
        a(i, f, f2);
        a(i + SM, f3, f4);
        short[] sArr = SL;
        int i2 = SB;
        SB = i2 + 1;
        sArr[i2] = (short) (Sz + 0);
        short[] sArr2 = SL;
        int i3 = SB;
        SB = i3 + 1;
        sArr2[i3] = (short) (Sz + 1);
        dn(2);
        SA += SM * 2;
        Sz += 2;
    }

    private static void c(aj ajVar, float f, float f2, float f3, float f4) {
        b(ajVar, f, f2, f + f3, f2);
        b(ajVar, f + f3, f2, f + f3, f2 + f4);
        b(ajVar, f + f3, f2 + f4, f, f2 + f4);
        b(ajVar, f, f2, f, f2 + f4 + 1.0f);
    }

    private static void d(aj ajVar, float f, float f2, float f3, float f4) {
        a(ajVar, 1, 3);
        dm(6);
        a(f, f2, f3, f4, 0);
        dn(4);
        SA += SM * 4;
    }

    private static void a(aj ajVar, float f, float f2, float f3, float f4, float f5, float f6) {
        a(ajVar, 1, 3);
        dm(3);
        int i = SA;
        a(i, f, f2);
        int i2 = i + SM;
        a(i2, f3, f4);
        a(i2 + SM, f5, f6);
        short[] sArr = SL;
        int i3 = SB;
        SB = i3 + 1;
        sArr[i3] = (short) (Sz + 0);
        short[] sArr2 = SL;
        int i4 = SB;
        SB = i4 + 1;
        sArr2[i4] = (short) (Sz + 1);
        short[] sArr3 = SL;
        int i5 = SB;
        SB = i5 + 1;
        sArr3[i5] = (short) (Sz + 2);
        dn(3);
        SA += SM * 3;
        Sz += 3;
    }

    private static void f(int i, int i2, int i3, int i4) {
        kC();
        SE = i;
        SF = i2;
        SG = i3;
        SH = i4;
        if (w.Vw) {
            GLES20.glClear(1024);
            GLES20.glColorMask(false, false, false, false);
            GLES20.glStencilFunc(519, 1, 1);
            GLES20.glStencilOp(7681, 7681, 7681);
            d(Qj, SE, SF, SG, SH);
            kC();
            GLES20.glColorMask(true, true, true, true);
            GLES20.glStencilFunc(514, 1, 1);
            GLES20.glStencilOp(7680, 7680, 7680);
            return;
        }
        int iKI = kI();
        if ((Sy != null ? Sy.mm() : -1) == -1) {
            iKI = (int) (iKI * getScaleY());
            i = (int) ((i * getScaleX()) + ajV());
            i2 = (int) ((i2 * getScaleY()) - ajW());
            i3 = (int) (i3 * getScaleX());
            i4 = (int) (i4 * getScaleY());
        }
        GLES20.glScissor(i, (iKI - i2) - i4, i3, i4);
    }

    private static void a(aj ajVar, float f, float f2, float f3, float f4, int i, int i2, int i3) {
        a(ajVar, f, f2, f3, f4, i, i2, i3, false);
    }

    private static void a(aj ajVar, float f, float f2, float f3, float f4, int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        a(ajVar, 1, 3);
        dm(6);
        if (!z) {
            i |= -16777216;
            i2 |= -16777216;
        }
        a(f, f2, f3, f4, 0);
        if (i3 == 8 || i3 == 32) {
            i4 = i;
            i5 = i2;
        } else {
            i4 = i2;
            i5 = i;
        }
        switch (i3) {
            case 4:
            case 8:
                dl(i4);
                dn(1);
                dl(i5);
                R(1, 1);
                R(1, 2);
                dl(i4);
                R(1, 3);
                break;
            case 16:
            case 32:
                dl(i4);
                dn(2);
                dl(i5);
                R(2, 2);
                break;
        }
        SA += SM * 4;
    }

    private static void dn(int i) {
        R(i, 0);
    }

    private static void R(int i, int i2) {
        int i3 = (SM * i2) + SA + Sv;
        for (int i4 = 0; i4 < i; i4++) {
            SJ[i3 + 0] = SO / 255.0f;
            SJ[i3 + 1] = SP / 255.0f;
            SJ[i3 + 2] = SQ / 255.0f;
            SJ[i3 + 3] = SR / 255.0f;
            i3 += SM;
        }
    }

    public static final void a(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int[] iArr3 = new int[i3];
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (true) {
            int i9 = i6 + 1;
            if (i6 >= i2) {
                break;
            }
            a(iArr, i8, iArr2, i7, i);
            a(iArr3, 0, iArr2, i7 + i, i5);
            i8 += i;
            i7 += i3;
            i6 = i9;
        }
        int i10 = i3 * i4;
        while (i7 < i10) {
            a(iArr3, 0, iArr2, i7, i3);
            i7 += i3;
        }
    }

    public static boolean kC() {
        if (Sz == 0) {
            return false;
        }
        a((cb) null, Th);
        SC = null;
        Sz = 0;
        SA = 0;
        SB = 0;
        Tk++;
        Tl += Sz;
        return true;
    }

    /* JADX INFO: renamed from: do, reason: not valid java name */
    public static void m2do(int i) {
        g(i, false);
    }

    public static void g(int i, boolean z) {
        if (i != Su || z) {
            kC();
            Su = i;
            int iMm = Sy != null ? Sy.mm() : -1;
            switch (Su) {
                case 3:
                    GLES20.glBlendFunc(770, 1);
                    break;
                default:
                    GLES20.glColorMask(true, true, true, iMm == -1);
                    GLES20.glBlendFunc(770, 771);
                    break;
            }
        }
    }

    public static void g(int i, int i2, int i3, int i4) {
        if (Tg != i4 || Td != i || Te != i2 || Tf != i3) {
            kC();
            Tg = i4;
            Td = i;
            Te = i2;
            Tf = i3;
        }
    }

    public static void a(cb cbVar, float[] fArr) {
        ay ayVarOB;
        int iOw;
        int iOx;
        int iOy;
        int iOz;
        int iOv;
        int iOu;
        float f;
        float f2;
        float f3;
        float f4;
        if (fArr == null) {
            fArr = Th;
        }
        if (cbVar == null) {
            SI.clear();
            SI.put(SJ);
            SI.position(0);
            SK.clear();
            SK.put(SL);
            SK.position(0);
            ayVarOB = SC;
            iOw = Su;
            iOx = St;
            iOy = Sv;
            iOz = SN;
            iOv = SB;
            iOu = SM * 4;
            float f5 = Td / 255.0f;
            float f6 = Te / 255.0f;
            float f7 = Tf / 255.0f;
            f = Tg / 255.0f;
            f2 = f7;
            f3 = f6;
            f4 = f5;
        } else {
            ayVarOB = cbVar.oB();
            iOw = cbVar.ow();
            iOx = cbVar.ox();
            iOy = cbVar.oy();
            iOz = cbVar.oz();
            iOv = cbVar.ov();
            iOu = cbVar.ou();
            int iOA = cbVar.oA();
            f = (iOA & 255) / 255.0f;
            f2 = ((iOA >> 8) & 255) / 255.0f;
            f3 = ((iOA >> 16) & 255) / 255.0f;
            f4 = ((iOA >> 24) & 255) / 255.0f;
        }
        if (iOv > 0) {
            int iA = a(ayVarOB, iOw, iOx);
            GLES20.glUseProgram(iA);
            int iGlGetAttribLocation = GLES20.glGetAttribLocation(iA, "vPosition");
            if (iGlGetAttribLocation != -1) {
                GLES20.glEnableVertexAttribArray(iGlGetAttribLocation);
                if (cbVar == null) {
                    GLES20.glVertexAttribPointer(iGlGetAttribLocation, iOy, 5126, false, iOu, (Buffer) SI);
                } else {
                    GLES20.glBindBuffer(34962, cbVar.ot()[0]);
                    GLES20.glBindBuffer(34963, cbVar.ot()[1]);
                    GLES20.glVertexAttribPointer(iGlGetAttribLocation, iOy, 5126, false, iOu, 0);
                }
            }
            int iGlGetUniformLocation = GLES20.glGetUniformLocation(iA, "fxColor");
            if (iGlGetUniformLocation != -1) {
                GLES20.glUniform4f(iGlGetUniformLocation, f4, f3, f2, f);
            }
            int iGlGetUniformLocation2 = GLES20.glGetUniformLocation(iA, "projectionMatrix");
            if (iGlGetUniformLocation2 != -1) {
                GLES20.glUniformMatrix4fv(iGlGetUniformLocation2, 1, false, fArr, 0);
            }
            switch (iOx) {
                case 0:
                case 1:
                    int iGlGetAttribLocation2 = GLES20.glGetAttribLocation(iA, "vColor");
                    GLES20.glEnableVertexAttribArray(iGlGetAttribLocation2);
                    SI.position(iOy);
                    if (cbVar == null) {
                        GLES20.glVertexAttribPointer(iGlGetAttribLocation2, 4, 5126, false, iOu, (Buffer) SI);
                        GLES20.glDrawElements(iOx == 0 ? 1 : 4, iOv, 5123, SK);
                    } else {
                        GLES20.glVertexAttribPointer(iGlGetAttribLocation2, 4, 5126, false, iOu, iOy * 4);
                        GLES20.glDrawElements(iOx == 0 ? 1 : 4, iOv, 5123, 0);
                    }
                    GLES20.glDisableVertexAttribArray(iGlGetAttribLocation2);
                    break;
                case 2:
                    GLES20.glActiveTexture(33984);
                    GLES20.glBindTexture(3553, ayVarOB.nl());
                    if (ayVarOB.nj()) {
                        GLES20.glActiveTexture(33985);
                        GLES20.glBindTexture(3553, ayVarOB.ni());
                        if (kE()) {
                            int iGlGetUniformLocation3 = GLES20.glGetUniformLocation(iA, "width");
                            int iGlGetUniformLocation4 = GLES20.glGetUniformLocation(iA, "height");
                            if (iGlGetUniformLocation3 != -1 && iGlGetUniformLocation4 != -1) {
                                GLES20.glUniform1f(iGlGetUniformLocation3, ayVarOB.aiq);
                                GLES20.glUniform1f(iGlGetUniformLocation4, ayVarOB.air);
                            }
                        }
                    }
                    GLES20.glUniform1i(GLES20.glGetUniformLocation(iA, "textureSampler"), 0);
                    GLES20.glUniform1i(GLES20.glGetUniformLocation(iA, "paletteSampler"), 1);
                    int iGlGetAttribLocation3 = GLES20.glGetAttribLocation(iA, "texCoord");
                    GLES20.glEnableVertexAttribArray(iGlGetAttribLocation3);
                    if (cbVar == null) {
                        SI.position(iOy);
                        GLES20.glVertexAttribPointer(iGlGetAttribLocation3, iOz, 5126, false, iOu, (Buffer) SI);
                        GLES20.glDrawElements(4, iOv, 5123, SK);
                    } else {
                        GLES20.glVertexAttribPointer(iGlGetAttribLocation3, iOz, 5126, false, iOu, iOy * 4);
                        GLES20.glDrawElements(4, iOv, 5123, 0);
                    }
                    GLES20.glDisableVertexAttribArray(iGlGetAttribLocation3);
                    break;
            }
            GLES20.glDisableVertexAttribArray(iGlGetAttribLocation);
            if (cbVar != null) {
                GLES20.glBindBuffer(34962, 0);
                GLES20.glBindBuffer(34963, 0);
            }
            int iGlGetError = GLES20.glGetError();
            if (iGlGetError != 0) {
                String str = "GL  Error:  errorID = " + iGlGetError;
            }
        }
    }

    private static boolean kD() {
        return Sw;
    }

    public static void ah(boolean z) {
        if (Sx != z) {
            kC();
            SN = z ? 4 : 2;
            Sx = z;
        }
    }

    private static boolean kE() {
        return Sx;
    }

    private static void kF() {
        if (w.Vl) {
            Tm = com.gameloft.android.wrapper.au.isRooted();
        }
    }

    public static void kG() {
        if (w.Vl) {
            kF();
            Tn = System.currentTimeMillis();
            To = SystemClock.elapsedRealtime();
        }
    }

    public static final int kH() {
        return w.Vk ? QD.getWidth() : Qq;
    }

    public static final int kI() {
        return w.Vk ? QD.getHeight() : Qr;
    }

    public static final boolean bx(String str) {
        try {
            return Qu.jf(str);
        } catch (Exception e) {
            be("PlatformRequest.Failed with url " + str + " with exception " + e.getMessage());
            d(e);
            return false;
        }
    }

    public static void ai(boolean z) {
        SD = z;
        if (!w.Vu) {
            be("OGLES_SetTexLinearFilterEnabled: To use texture linear filtering you need to enable GLLibConfig.ANDROID_OGLES_ALLOW_TEX_LINEAR_FILTERING flag");
        }
    }

    public static boolean kJ() {
        return SD && w.Vu;
    }

    public static void dp(int i) {
        c(w.Vt, "OGLES_SetTextureOptimizationMode: OPTIMIZATION_RGBA4444 will not have effect because GLLibConfig.ANDROID_OGLES_OPTIMIZE_TEXTURES is disabled");
        c(w.Vt, "OGLES_SetTextureOptimizationMode: OPTIMIZATION_RGBA5551_DITHER will not have effect because GLLibConfig.ANDROID_OGLES_OPTIMIZE_TEXTURES is disabled");
        Ta = i;
    }

    public static int kK() {
        return Ta;
    }

    public static int a(byte[] bArr, int i, byte b) {
        int i2 = i + 1;
        bArr[i] = b;
        return i2;
    }

    public static int a(byte[] bArr, int i, short s) {
        int i2 = i + 1;
        bArr[i] = (byte) (s & 255);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((s >>> 8) & 255);
        return i3;
    }

    public static int d(byte[] bArr, int i, int i2) {
        int i3 = i + 1;
        bArr[i] = (byte) (i2 & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i2 >>> 8) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i2 >>> 16) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((i2 >>> 24) & 255);
        return i6;
    }

    public static int a(byte[] bArr, int i, long j) {
        int i2 = i + 1;
        bArr[i] = (byte) (j & 255);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((j >>> 8) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((j >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((j >>> 24) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((j >>> 32) & 255);
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((j >>> 40) & 255);
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((j >>> 48) & 255);
        int i9 = i8 + 1;
        bArr[i8] = (byte) ((j >>> 56) & 255);
        return i9;
    }

    public static byte j(byte[] bArr, int i) {
        return bArr[i];
    }

    public static short k(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return (short) (((bArr[i2] & 255) << 8) | (bArr[i] & 255));
    }

    public static int l(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = ((bArr[i2] & 255) << 8) | (bArr[i] & 255);
        int i5 = i3 + 1;
        int i6 = i4 | ((bArr[i3] & 255) << 16);
        int i7 = i5 + 1;
        return i6 | ((bArr[i5] & 255) << 24);
    }

    public static long m(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        long j = ((long) (bArr[i] & 255)) | (((long) (bArr[i2] & 255)) << 8);
        int i4 = i3 + 1;
        long j2 = j | (((long) (bArr[i3] & 255)) << 16);
        int i5 = i4 + 1;
        long j3 = j2 | (((long) (bArr[i4] & 255)) << 24);
        int i6 = i5 + 1;
        long j4 = j3 | (((long) (bArr[i5] & 255)) << 32);
        int i7 = i6 + 1;
        long j5 = j4 | (((long) (bArr[i6] & 255)) << 40);
        int i8 = i7 + 1;
        long j6 = j5 | (((long) (bArr[i7] & 255)) << 48);
        int i9 = i8 + 1;
        return (((long) (bArr[i8] & 255)) << 56) | j6;
    }

    public static void a(Object obj, int i, Object obj2, int i2, int i3) {
        System.arraycopy(obj, i, obj2, i2, i3);
    }

    public static int a(byte[] bArr, int i, byte[] bArr2) {
        a(bArr2, 0, bArr, i, bArr2.length);
        return bArr2.length + i;
    }

    public static int b(byte[] bArr, int i, byte[] bArr2) {
        a(bArr, i, bArr2, 0, bArr2.length);
        return bArr2.length + i;
    }

    public static void d(int[] iArr, int i) {
        int length = iArr.length;
        if (length > 0) {
            iArr[0] = i;
        }
        int i2 = 1;
        while (i2 < length) {
            System.arraycopy(iArr, 0, iArr, i2, length - i2 < i2 ? length - i2 : i2);
            i2 += i2;
        }
    }

    protected static Object dq(int i) {
        Object objF = null;
        try {
            if (Tq == null || i < Tp) {
                if (Tq != null) {
                    Tq.close();
                }
                Tq = QD.bj("/888");
                Tp = 0;
            }
            Tq.skip(i - Tp);
            int iAvailable = Tq.available();
            objF = f(Tq);
            Tp = (iAvailable - Tq.available()) + i;
            return objF;
        } catch (IOException e) {
            return objF;
        }
    }

    static Object f(InputStream inputStream) {
        int iG;
        Object[] objArr;
        c(inputStream != null, "Mem_ReadArray.is is null");
        try {
            int iG2 = g(inputStream);
            int i = iG2 >> 4;
            int i2 = iG2 & 7;
            if ((iG2 & 8) != 0) {
                iG = h(inputStream);
            } else {
                iG = g(inputStream);
            }
            switch (i2) {
                case 0:
                    byte[] bArr = new byte[iG];
                    for (int i3 = 0; i3 < iG; i3++) {
                        bArr[i3] = (byte) g(inputStream);
                    }
                    return bArr;
                case 1:
                    short[] sArr = new short[iG];
                    if (i == 0) {
                        for (int i4 = 0; i4 < iG; i4++) {
                            sArr[i4] = (byte) g(inputStream);
                        }
                        return sArr;
                    }
                    for (int i5 = 0; i5 < iG; i5++) {
                        sArr[i5] = (short) h(inputStream);
                    }
                    return sArr;
                case 2:
                    int[] iArr = new int[iG];
                    if (i == 0) {
                        for (int i6 = 0; i6 < iG; i6++) {
                            iArr[i6] = (byte) g(inputStream);
                        }
                        return iArr;
                    }
                    if (i == 1) {
                        for (int i7 = 0; i7 < iG; i7++) {
                            iArr[i7] = (short) h(inputStream);
                        }
                        return iArr;
                    }
                    for (int i8 = 0; i8 < iG; i8++) {
                        iArr[i8] = i(inputStream);
                    }
                    return iArr;
                default:
                    switch (i2 & 3) {
                        case 0:
                            if (i == 2) {
                                objArr = new byte[iG][];
                            } else {
                                objArr = new byte[iG][][];
                            }
                            break;
                        case 1:
                            if (i == 2) {
                                objArr = new short[iG][];
                            } else {
                                objArr = new short[iG][][];
                            }
                            break;
                        default:
                            if (i == 2) {
                                objArr = new int[iG][];
                            } else {
                                objArr = new int[iG][][];
                            }
                            break;
                    }
                    for (int i9 = 0; i9 < iG; i9++) {
                        objArr[i9] = f(inputStream);
                    }
                    return objArr;
            }
        } catch (Exception e) {
            c(false, "Mem_ReadArray.IO error");
            return null;
        }
    }

    static int g(InputStream inputStream) throws IOException {
        int i = inputStream.read();
        if (i >= 0) {
            Tr++;
        }
        return i;
    }

    static int h(InputStream inputStream) {
        try {
            return (g(inputStream) & 255) | ((g(inputStream) & 255) << 8);

        } catch (IOException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    static int i(InputStream inputStream) {
        try {
            return (g(inputStream) & 255) | ((g(inputStream) & 255) << 8) | ((g(inputStream) & 255) << 16) | ((g(inputStream) & 255) << 24);

        } catch (IOException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    static int b(InputStream inputStream, byte[] bArr, int i, int i2) {
        c(bArr != null, "Stream_ReadFully.array is null");
        c(i >= 0, "Stream_ReadFully.offset is negative");
        c(i2 >= 0, "Stream_ReadFully.length is negative");
        c(i + i2 <= bArr.length, "Stream_ReadFully.offset+length is bigger than array size");
        int i3 = i2;
        while (i3 > 0) {
            try {
                int i4 = inputStream.read(bArr, i, i3);
                c(i4 >= 0, "Pack_ReadFully.EOF");
                i += i4;
                i3 -= i4;
            } catch (Exception e) {
                c(false, "Stream_ReadFully.IO exception occured");
            }
        }
        Tr += i2;
        return i2;
    }

    public static void b(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 24);
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 16);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i >>> 8);
        bArr[i4 + 1] = (byte) i;
    }

    public static String jr() {
        String strBY = Qu.bY("DeviceID");
        if (strBY == null) {
            if (w.ZN) {
                String property = System.getProperty("MAC");
                if (property == null) {
                    String strBY2 = Qu.bY("MAC");
                    if (strBY2 != null && strBY2.compareTo("00:00:00:00:00:00") != 0) {
                        return strBY2;
                    }
                } else {
                    return property;
                }
            }
            return null;
        }
        return strBY;
    }

    public static String kL() {
        long j = 0;
        byte[] bArr = new byte[16];
        new bs().nextBytes(bArr);
        bArr[6] = (byte) (bArr[6] & 15);
        bArr[6] = (byte) (bArr[6] | 64);
        bArr[8] = (byte) (bArr[8] & 63);
        bArr[8] = (byte) (bArr[8] | 128);
        long j2 = 0;
        for (int i = 0; i < 8; i++) {
            j2 = (j2 << 8) | ((long) (bArr[i] & 255));
        }
        for (int i2 = 8; i2 < 16; i2++) {
            j = (j << 8) | ((long) (bArr[i2] & 255));
        }
        return b(j2 >> 32, 8) + "-" + b(j2 >> 16, 4) + "-" + b(j2, 4) + "-" + b(j >> 48, 4) + "-" + b(j, 12);
    }

    private static String b(long j, int i) {
        long j2 = 1 << (i * 4);
        long j3 = j2 | ((j2 - 1) & j);
        return (Integer.toHexString((int) (j3 >> 32)) + Integer.toHexString((int) (j3 & (-1)))).substring(1);
    }

    public static byte[] g(int[] iArr) {
        return a(iArr, false, (byte[]) null);
    }

    public static byte[] a(int[] iArr, boolean z, byte[] bArr) {
        int length = iArr.length << 2;
        if (z) {
            int i = iArr[iArr.length - 1];
            if (i > length) {
                return null;
            }
            length = i;
        }
        if (bArr == null) {
            bArr = new byte[length];
        }
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = (byte) ((iArr[i2 >>> 2] >>> ((i2 & 3) << 3)) & 255);
        }
        return bArr;
    }

    public static int[] a(byte[] bArr, boolean z, int[] iArr) {
        int length = (bArr.length & 3) == 0 ? bArr.length >>> 2 : (bArr.length >>> 2) + 1;
        if (z) {
            if (iArr == null) {
                iArr = new int[length + 1];
            }
            iArr[length] = bArr.length;
        } else if (iArr == null) {
            iArr = new int[length];
        }
        int length2 = bArr.length;
        for (int i = 0; i < length2; i++) {
            int i2 = i >>> 2;
            iArr[i2] = iArr[i2] | ((bArr[i] & 255) << ((i & 3) << 3));
        }
        return iArr;
    }

    public static String by(String str) {
        int iIndexOf = str.indexOf("://");
        if (iIndexOf != -1) {
            return str.substring(0, iIndexOf);
        }
        return null;
    }

    public static String bz(String str) {
        if (by(str) != null) {
            str = bD(str);
        }
        int iIndexOf = str.indexOf("/");
        if (iIndexOf != -1) {
            str = str.substring(0, iIndexOf);
        }
        return "" + str;
    }

    public static String bA(String str) {
        String strBz = bz(str);
        int iIndexOf = strBz.indexOf(":");
        if (iIndexOf != -1) {
            return strBz.substring(0, iIndexOf);
        }
        return strBz;
    }

    public static String bB(String str) {
        if (by(str) != null) {
            str = bD(str);
        }
        int iIndexOf = str.indexOf("/");
        return iIndexOf != -1 ? str.substring(iIndexOf) : "";
    }

    public static String bC(String str) {
        String strBz = bz(str);
        int iIndexOf = strBz.indexOf(":");
        if (iIndexOf != -1) {
            return strBz.substring(iIndexOf + 1, strBz.length());
        }
        return null;
    }

    public static String bD(String str) {
        return str.substring(by(str).length() + 3);
    }

    public static void kM() {
        Tx = new byte[64];
        Tv = new long[1];
        Tv[0] = 0;
        Tt = new int[4];
        Tt[0] = 1732584193;
        Tt[1] = -271733879;
        Tt[2] = -1732584194;
        Tt[3] = 271733878;
    }

    private static void kN() {
        Ty = new byte[64];
        Tw = new long[1];
        Tw[0] = 0;
        Tu = new int[4];
        System.arraycopy(Tx, 0, Ty, 0, Ty.length);
        System.arraycopy(Tt, 0, Tu, 0, Tu.length);
        Tw = Tv;
    }

    private static void a(byte[] bArr, int i, int[] iArr) {
        iArr[0] = (bArr[i + 0] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | (bArr[i + 3] << 24);
        iArr[1] = (bArr[i + 4] & 255) | ((bArr[i + 5] & 255) << 8) | ((bArr[i + 6] & 255) << 16) | (bArr[i + 7] << 24);
        iArr[2] = (bArr[i + 8] & 255) | ((bArr[i + 9] & 255) << 8) | ((bArr[i + 10] & 255) << 16) | (bArr[i + 11] << 24);
        iArr[3] = (bArr[i + 12] & 255) | ((bArr[i + 13] & 255) << 8) | ((bArr[i + 14] & 255) << 16) | (bArr[i + 15] << 24);
        iArr[4] = (bArr[i + 16] & 255) | ((bArr[i + 17] & 255) << 8) | ((bArr[i + 18] & 255) << 16) | (bArr[i + 19] << 24);
        iArr[5] = (bArr[i + 20] & 255) | ((bArr[i + 21] & 255) << 8) | ((bArr[i + 22] & 255) << 16) | (bArr[i + 23] << 24);
        iArr[6] = (bArr[i + 24] & 255) | ((bArr[i + 25] & 255) << 8) | ((bArr[i + 26] & 255) << 16) | (bArr[i + 27] << 24);
        iArr[7] = (bArr[i + 28] & 255) | ((bArr[i + 29] & 255) << 8) | ((bArr[i + 30] & 255) << 16) | (bArr[i + 31] << 24);
        iArr[8] = (bArr[i + 32] & 255) | ((bArr[i + 33] & 255) << 8) | ((bArr[i + 34] & 255) << 16) | (bArr[i + 35] << 24);
        iArr[9] = (bArr[i + 36] & 255) | ((bArr[i + 37] & 255) << 8) | ((bArr[i + 38] & 255) << 16) | (bArr[i + 39] << 24);
        iArr[10] = (bArr[i + 40] & 255) | ((bArr[i + 41] & 255) << 8) | ((bArr[i + 42] & 255) << 16) | (bArr[i + 43] << 24);
        iArr[11] = (bArr[i + 44] & 255) | ((bArr[i + 45] & 255) << 8) | ((bArr[i + 46] & 255) << 16) | (bArr[i + 47] << 24);
        iArr[12] = (bArr[i + 48] & 255) | ((bArr[i + 49] & 255) << 8) | ((bArr[i + 50] & 255) << 16) | (bArr[i + 51] << 24);
        iArr[13] = (bArr[i + 52] & 255) | ((bArr[i + 53] & 255) << 8) | ((bArr[i + 54] & 255) << 16) | (bArr[i + 55] << 24);
        iArr[14] = (bArr[i + 56] & 255) | ((bArr[i + 57] & 255) << 8) | ((bArr[i + 58] & 255) << 16) | (bArr[i + 59] << 24);
        iArr[15] = (bArr[i + 60] & 255) | ((bArr[i + 61] & 255) << 8) | ((bArr[i + 62] & 255) << 16) | (bArr[i + 63] << 24);
    }

    private static void a(int[] iArr, byte[] bArr, int i, int[] iArr2) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        a(bArr, i, iArr2);
        int i6 = i2 + ((((i3 & i4) | ((i3 ^ (-1)) & i5)) + iArr2[0]) - 680876936);
        int i7 = ((i6 >>> 25) | (i6 << 7)) + i3;
        int i8 = i5 + ((((i7 & i3) | ((i7 ^ (-1)) & i4)) + iArr2[1]) - 389564586);
        int i9 = ((i8 >>> 20) | (i8 << 12)) + i7;
        int i10 = i4 + ((i9 & i7) | ((i9 ^ (-1)) & i3)) + iArr2[2] + 606105819;
        int i11 = ((i10 >>> 15) | (i10 << 17)) + i9;
        int i12 = i3 + ((((i11 & i9) | ((i11 ^ (-1)) & i7)) + iArr2[3]) - 1044525330);
        int i13 = ((i12 >>> 10) | (i12 << 22)) + i11;
        int i14 = i7 + ((((i13 & i11) | ((i13 ^ (-1)) & i9)) + iArr2[4]) - 176418897);
        int i15 = ((i14 >>> 25) | (i14 << 7)) + i13;
        int i16 = i9 + ((i15 & i13) | ((i15 ^ (-1)) & i11)) + iArr2[5] + 1200080426;
        int i17 = ((i16 >>> 20) | (i16 << 12)) + i15;
        int i18 = i11 + ((((i17 & i15) | ((i17 ^ (-1)) & i13)) + iArr2[6]) - 1473231341);
        int i19 = ((i18 >>> 15) | (i18 << 17)) + i17;
        int i20 = i13 + ((((i19 & i17) | ((i19 ^ (-1)) & i15)) + iArr2[7]) - 45705983);
        int i21 = ((i20 >>> 10) | (i20 << 22)) + i19;
        int i22 = i15 + ((i21 & i19) | ((i21 ^ (-1)) & i17)) + iArr2[8] + 1770035416;
        int i23 = ((i22 >>> 25) | (i22 << 7)) + i21;
        int i24 = i17 + ((((i23 & i21) | ((i23 ^ (-1)) & i19)) + iArr2[9]) - 1958414417);
        int i25 = ((i24 >>> 20) | (i24 << 12)) + i23;
        int i26 = i19 + ((((i25 & i23) | ((i25 ^ (-1)) & i21)) + iArr2[10]) - 42063);
        int i27 = ((i26 >>> 15) | (i26 << 17)) + i25;
        int i28 = i21 + ((((i27 & i25) | ((i27 ^ (-1)) & i23)) + iArr2[11]) - 1990404162);
        int i29 = ((i28 >>> 10) | (i28 << 22)) + i27;
        int i30 = i23 + ((i29 & i27) | ((i29 ^ (-1)) & i25)) + iArr2[12] + 1804603682;
        int i31 = ((i30 >>> 25) | (i30 << 7)) + i29;
        int i32 = i25 + ((((i31 & i29) | ((i31 ^ (-1)) & i27)) + iArr2[13]) - 40341101);
        int i33 = ((i32 >>> 20) | (i32 << 12)) + i31;
        int i34 = i27 + ((((i33 & i31) | ((i33 ^ (-1)) & i29)) + iArr2[14]) - 1502002290);
        int i35 = ((i34 >>> 15) | (i34 << 17)) + i33;
        int i36 = i29 + ((i35 & i33) | ((i35 ^ (-1)) & i31)) + iArr2[15] + 1236535329;
        int i37 = ((i36 >>> 10) | (i36 << 22)) + i35;
        int i38 = i31 + ((((i37 & i33) | ((i33 ^ (-1)) & i35)) + iArr2[1]) - 165796510);
        int i39 = ((i38 >>> 27) | (i38 << 5)) + i37;
        int i40 = i33 + ((((i39 & i35) | ((i35 ^ (-1)) & i37)) + iArr2[6]) - 1069501632);
        int i41 = ((i40 >>> 23) | (i40 << 9)) + i39;
        int i42 = i35 + ((i41 & i37) | ((i37 ^ (-1)) & i39)) + iArr2[11] + 643717713;
        int i43 = ((i42 >>> 18) | (i42 << 14)) + i41;
        int i44 = i37 + ((((i43 & i39) | ((i39 ^ (-1)) & i41)) + iArr2[0]) - 373897302);
        int i45 = ((i44 >>> 12) | (i44 << 20)) + i43;
        int i46 = i39 + ((((i45 & i41) | ((i41 ^ (-1)) & i43)) + iArr2[5]) - 701558691);
        int i47 = ((i46 >>> 27) | (i46 << 5)) + i45;
        int i48 = i41 + ((i47 & i43) | ((i43 ^ (-1)) & i45)) + iArr2[10] + 38016083;
        int i49 = ((i48 >>> 23) | (i48 << 9)) + i47;
        int i50 = i43 + ((((i49 & i45) | ((i45 ^ (-1)) & i47)) + iArr2[15]) - 660478335);
        int i51 = ((i50 >>> 18) | (i50 << 14)) + i49;
        int i52 = i45 + ((((i51 & i47) | ((i47 ^ (-1)) & i49)) + iArr2[4]) - 405537848);
        int i53 = ((i52 >>> 12) | (i52 << 20)) + i51;
        int i54 = i47 + ((i53 & i49) | ((i49 ^ (-1)) & i51)) + iArr2[9] + 568446438;
        int i55 = ((i54 >>> 27) | (i54 << 5)) + i53;
        int i56 = i49 + ((((i55 & i51) | ((i51 ^ (-1)) & i53)) + iArr2[14]) - 1019803690);
        int i57 = ((i56 >>> 23) | (i56 << 9)) + i55;
        int i58 = i51 + ((((i57 & i53) | ((i53 ^ (-1)) & i55)) + iArr2[3]) - 187363961);
        int i59 = ((i58 >>> 18) | (i58 << 14)) + i57;
        int i60 = i53 + ((i59 & i55) | ((i55 ^ (-1)) & i57)) + iArr2[8] + 1163531501;
        int i61 = ((i60 >>> 12) | (i60 << 20)) + i59;
        int i62 = i55 + ((((i61 & i57) | ((i57 ^ (-1)) & i59)) + iArr2[13]) - 1444681467);
        int i63 = ((i62 >>> 27) | (i62 << 5)) + i61;
        int i64 = i57 + ((((i63 & i59) | ((i59 ^ (-1)) & i61)) + iArr2[2]) - 51403784);
        int i65 = ((i64 >>> 23) | (i64 << 9)) + i63;
        int i66 = i59 + ((i65 & i61) | ((i61 ^ (-1)) & i63)) + iArr2[7] + 1735328473;
        int i67 = ((i66 >>> 18) | (i66 << 14)) + i65;
        int i68 = i61 + ((((i67 & i63) | ((i63 ^ (-1)) & i65)) + iArr2[12]) - 1926607734);
        int i69 = ((i68 >>> 12) | (i68 << 20)) + i67;
        int i70 = i63 + ((((i69 ^ i67) ^ i65) + iArr2[5]) - 378558);
        int i71 = ((i70 >>> 28) | (i70 << 4)) + i69;
        int i72 = i65 + ((((i71 ^ i69) ^ i67) + iArr2[8]) - 2022574463);
        int i73 = ((i72 >>> 21) | (i72 << 11)) + i71;
        int i74 = i67 + ((i73 ^ i71) ^ i69) + iArr2[11] + 1839030562;
        int i75 = ((i74 >>> 16) | (i74 << 16)) + i73;
        int i76 = i69 + ((((i75 ^ i73) ^ i71) + iArr2[14]) - 35309556);
        int i77 = ((i76 >>> 9) | (i76 << 23)) + i75;
        int i78 = i71 + ((((i77 ^ i75) ^ i73) + iArr2[1]) - 1530992060);
        int i79 = ((i78 >>> 28) | (i78 << 4)) + i77;
        int i80 = i73 + ((i79 ^ i77) ^ i75) + iArr2[4] + 1272893353;
        int i81 = ((i80 >>> 21) | (i80 << 11)) + i79;
        int i82 = i75 + ((((i81 ^ i79) ^ i77) + iArr2[7]) - 155497632);
        int i83 = ((i82 >>> 16) | (i82 << 16)) + i81;
        int i84 = i77 + ((((i83 ^ i81) ^ i79) + iArr2[10]) - 1094730640);
        int i85 = ((i84 >>> 9) | (i84 << 23)) + i83;
        int i86 = i79 + ((i85 ^ i83) ^ i81) + iArr2[13] + 681279174;
        int i87 = ((i86 >>> 28) | (i86 << 4)) + i85;
        int i88 = i81 + ((((i87 ^ i85) ^ i83) + iArr2[0]) - 358537222);
        int i89 = ((i88 >>> 21) | (i88 << 11)) + i87;
        int i90 = i83 + ((((i89 ^ i87) ^ i85) + iArr2[3]) - 722521979);
        int i91 = ((i90 >>> 16) | (i90 << 16)) + i89;
        int i92 = i85 + ((i91 ^ i89) ^ i87) + iArr2[6] + 76029189;
        int i93 = ((i92 >>> 9) | (i92 << 23)) + i91;
        int i94 = i87 + ((((i93 ^ i91) ^ i89) + iArr2[9]) - 640364487);
        int i95 = ((i94 >>> 28) | (i94 << 4)) + i93;
        int i96 = i89 + ((((i95 ^ i93) ^ i91) + iArr2[12]) - 421815835);
        int i97 = ((i96 >>> 21) | (i96 << 11)) + i95;
        int i98 = i91 + ((i97 ^ i95) ^ i93) + iArr2[15] + 530742520;
        int i99 = ((i98 >>> 16) | (i98 << 16)) + i97;
        int i100 = i93 + ((((i99 ^ i97) ^ i95) + iArr2[2]) - 995338651);
        int i101 = ((i100 >>> 9) | (i100 << 23)) + i99;
        int i102 = i95 + (((((i97 ^ (-1)) | i101) ^ i99) + iArr2[0]) - 198630844);
        int i103 = ((i102 >>> 26) | (i102 << 6)) + i101;
        int i104 = i97 + (((i99 ^ (-1)) | i103) ^ i101) + iArr2[7] + 1126891415;
        int i105 = ((i104 >>> 22) | (i104 << 10)) + i103;
        int i106 = i99 + (((((i101 ^ (-1)) | i105) ^ i103) + iArr2[14]) - 1416354905);
        int i107 = ((i106 >>> 17) | (i106 << 15)) + i105;
        int i108 = i101 + (((((i103 ^ (-1)) | i107) ^ i105) + iArr2[5]) - 57434055);
        int i109 = ((i108 >>> 11) | (i108 << 21)) + i107;
        int i110 = i103 + (((i105 ^ (-1)) | i109) ^ i107) + iArr2[12] + 1700485571;
        int i111 = ((i110 >>> 26) | (i110 << 6)) + i109;
        int i112 = i105 + (((((i107 ^ (-1)) | i111) ^ i109) + iArr2[3]) - 1894986606);
        int i113 = ((i112 >>> 22) | (i112 << 10)) + i111;
        int i114 = i107 + (((((i109 ^ (-1)) | i113) ^ i111) + iArr2[10]) - 1051523);
        int i115 = ((i114 >>> 17) | (i114 << 15)) + i113;
        int i116 = i109 + (((((i111 ^ (-1)) | i115) ^ i113) + iArr2[1]) - 2054922799);
        int i117 = ((i116 >>> 11) | (i116 << 21)) + i115;
        int i118 = i111 + (((i113 ^ (-1)) | i117) ^ i115) + iArr2[8] + 1873313359;
        int i119 = ((i118 >>> 26) | (i118 << 6)) + i117;
        int i120 = i113 + (((((i115 ^ (-1)) | i119) ^ i117) + iArr2[15]) - 30611744);
        int i121 = ((i120 >>> 22) | (i120 << 10)) + i119;
        int i122 = i115 + (((((i117 ^ (-1)) | i121) ^ i119) + iArr2[6]) - 1560198380);
        int i123 = ((i122 >>> 17) | (i122 << 15)) + i121;
        int i124 = i117 + (((i119 ^ (-1)) | i123) ^ i121) + iArr2[13] + 1309151649;
        int i125 = ((i124 >>> 11) | (i124 << 21)) + i123;
        int i126 = i119 + (((((i121 ^ (-1)) | i125) ^ i123) + iArr2[4]) - 145523070);
        int i127 = ((i126 >>> 26) | (i126 << 6)) + i125;
        int i128 = i121 + (((((i123 ^ (-1)) | i127) ^ i125) + iArr2[11]) - 1120210379);
        int i129 = ((i128 >>> 22) | (i128 << 10)) + i127;
        int i130 = i123 + (((i125 ^ (-1)) | i129) ^ i127) + iArr2[2] + 718787259;
        int i131 = ((i130 >>> 17) | (i130 << 15)) + i129;
        int i132 = i125 + (((((i127 ^ (-1)) | i131) ^ i129) + iArr2[9]) - 343485551);
        iArr[0] = i127 + iArr[0];
        iArr[1] = iArr[1] + ((i132 >>> 11) | (i132 << 21)) + i131;
        iArr[2] = iArr[2] + i131;
        iArr[3] = iArr[3] + i129;
    }

    private static void a(byte[] bArr, int[] iArr, long[] jArr, byte[] bArr2, int i, int i2) {
        int i3;
        if (i2 - i > bArr2.length) {
            i2 = bArr2.length - i;
        }
        int i4 = (int) (jArr[0] & 63);
        jArr[0] = jArr[0] + ((long) i2);
        int i5 = 64 - i4;
        if (i2 >= i5) {
            int[] iArr2 = new int[16];
            if (i5 == 64) {
                i5 = 0;
            } else {
                for (int i6 = 0; i6 < i5; i6++) {
                    bArr[i6 + i4] = bArr2[i6 + i];
                }
                a(iArr, bArr, 0, iArr2);
            }
            i3 = i5;
            while (i3 + 63 < i2) {
                a(iArr, bArr2, i3 + i, iArr2);
                i3 += 64;
            }
            i4 = 0;
        } else {
            i3 = 0;
        }
        if (i3 < i2) {
            for (int i7 = i3; i7 < i2; i7++) {
                bArr[(i4 + i7) - i3] = bArr2[i7 + i];
            }
        }
    }

    public static void A(String str, String str2) throws UnsupportedEncodingException {
        if (str2 == null) {
            str2 = "ISO8859_1";
        }
        byte[] bytes = str.getBytes(str2);
        a(Tx, Tt, Tv, bytes, 0, bytes.length);
    }

    private static byte[] e(int[] iArr, int i) {
        byte[] bArr = new byte[i];
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            bArr[i2 + 0] = (byte) (iArr[i3] & 255);
            bArr[i2 + 1] = (byte) ((iArr[i3] >>> 8) & 255);
            bArr[i2 + 2] = (byte) ((iArr[i3] >>> 16) & 255);
            bArr[i2 + 3] = (byte) ((iArr[i3] >>> 24) & 255);
            i2 += 4;
            i3++;
        }
        return bArr;
    }

    private static byte[] kO() {
        kN();
        byte[] bArrE = e(new int[]{(int) (Tw[0] << 3), (int) (Tw[0] >> 29)}, 8);
        int i = (int) (Tw[0] & 63);
        a(Ty, Tu, Tw, Tz, 0, i < 56 ? 56 - i : 120 - i);
        a(Ty, Tu, Tw, bArrE, 0, 8);
        return e(Tu, 16);
    }

    public static String d(byte[] bArr) {
        int i = 0;
        char[] cArr = new char[bArr.length * 2];
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i3 < bArr.length) {
                int i4 = i2 + 1;
                cArr[i2] = TA[(bArr[i3] >>> 4) & 15];
                i2 = i4 + 1;
                cArr[i4] = TA[bArr[i3] & 15];
                i = i3 + 1;
            } else {
                return new String(cArr);
            }
        }
    }

    public static String kP() {
        return d(kO());
    }

    public static void kQ() {
        int i;
        if (!TC) {
            TB = new int[256];
            for (int i2 = 0; i2 < 256; i2++) {
                int i3 = 8;
                int i4 = i2;
                while (i3 > 0) {
                    if ((i4 & 1) == 1) {
                        i = (i4 >>> 1) ^ (-306674912);
                    } else {
                        i = i4 >>> 1;
                    }
                    i3--;
                    i4 = i;
                }
                TB[i2] = i4;
            }
            TC = true;
        }
    }

    public static int a(byte[] bArr, int i, int i2, int i3) {
        int i4 = i3 ^ (-1);
        while (true) {
            int i5 = i2 - 1;
            if (i2 != 0) {
                i4 = (i4 >>> 8) ^ TB[(bArr[i] ^ i4) & 255];
                i2 = i5;
                i++;
            } else {
                return i4 ^ (-1);
            }
        }
    }

    public static byte[] a(byte[] bArr, String str, boolean z) {
        c(bArr != null, "Error. XXTEA data is null!");
        c(str != null, "Error. XXTEA key is null!");
        byte[] bytes = null;
        if (!"".equals(str) && str != null) {
            bytes = str.getBytes();
        }
        return a(bArr, bytes, z);
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, boolean z) {
        c(bArr != null, "Error. XXTEA data is null!");
        c(bArr2 != null, "Error. XXTEA key is null!");
        return bArr.length == 0 ? bArr : a(a(a(bArr, z, (int[]) null), a(bArr2, false, (int[]) null)), false, (byte[]) null);
    }

    public static byte[] b(byte[] bArr, String str, boolean z) {
        c(bArr != null, "Error. XXTEA data is null!");
        c(str != null, "Error. XXTEA key is null!");
        byte[] bytes = null;
        if (!"".equals(str) && str != null) {
            bytes = str.getBytes();
        }
        return b(bArr, bytes, z);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2, boolean z) {
        c(bArr != null, "Error. XXTEA data is null!");
        c(bArr2 != null, "Error. XXTEA key is null!");
        return (bArr == null || bArr.length == 0) ? bArr : a(b(a(bArr, false, (int[]) null), a(bArr2, false, (int[]) null)), z, (byte[]) null);
    }

    public static int[] a(int[] iArr, int[] iArr2) {
        c(iArr != null, "Error. XXTEA data is null!");
        c(iArr2 != null, "Error. XXTEA key is null!");
        int length = iArr.length - 1;
        if (length >= 1) {
            if (iArr2.length < 4) {
                int[] iArr3 = new int[4];
                System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
                iArr2 = iArr3;
            }
            int i = iArr[length];
            int i2 = iArr[0];
            int i3 = (52 / (length + 1)) + 6;
            int i4 = 0;
            while (true) {
                int i5 = i3 - 1;
                if (i3 <= 0) {
                    break;
                }
                i4 -= 1640531527;
                int i6 = (i4 >>> 2) & 3;
                int i7 = 0;
                while (i7 < length) {
                    int i8 = iArr[i7 + 1];
                    i = (((i ^ iArr2[(i7 & 3) ^ i6]) + (i8 ^ i4)) ^ (((i >>> 5) ^ (i8 << 2)) + ((i8 >>> 3) ^ (i << 4)))) + iArr[i7];
                    iArr[i7] = i;
                    i7++;
                }
                int i9 = iArr[0];
                i = iArr[length] + (((iArr2[(i7 & 3) ^ i6] ^ i) + (i9 ^ i4)) ^ (((i >>> 5) ^ (i9 << 2)) + ((i9 >>> 3) ^ (i << 4))));
                iArr[length] = i;
                i3 = i5;
            }
        }
        return iArr;
    }

    public static int[] b(int[] iArr, int[] iArr2) {
        c(iArr != null, "Error. XXTEA data is null!");
        c(iArr2 != null, "Error. XXTEA key is null!");
        int length = iArr.length - 1;
        if (length >= 1) {
            if (iArr2.length < 4) {
                int[] iArr3 = new int[4];
                System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
                iArr2 = iArr3;
            }
            int i = iArr[length];
            int i2 = iArr[0];
            for (int i3 = ((52 / (length + 1)) + 6) * (-1640531527); i3 != 0; i3 -= -1640531527) {
                int i4 = (i3 >>> 2) & 3;
                int i5 = length;
                while (i5 > 0) {
                    int i6 = iArr[i5 - 1];
                    i2 = iArr[i5] - (((i2 ^ i3) + (i6 ^ iArr2[(i5 & 3) ^ i4])) ^ (((i6 >>> 5) ^ (i2 << 2)) + ((i2 >>> 3) ^ (i6 << 4))));
                    iArr[i5] = i2;
                    i5--;
                }
                int i7 = iArr[length];
                i2 = iArr[0] - (((iArr2[(i5 & 3) ^ i4] ^ i7) + (i2 ^ i3)) ^ (((i7 >>> 5) ^ (i2 << 2)) + ((i2 >>> 3) ^ (i7 << 4))));
                iArr[0] = i2;
            }
        }
        return iArr;
    }

    public int kR() {
        int i = -1;
        try {
            String property = System.getProperty("microedition.locale");
            if (property != null) {
                String upperCase = property.toUpperCase();
                if (upperCase.indexOf("EN") >= 0) {
                    i = 0;
                } else if (upperCase.indexOf("DE") >= 0) {
                    i = 1;
                } else if (upperCase.indexOf("FR") >= 0) {
                    i = 2;
                } else if (upperCase.indexOf("IT") >= 0) {
                    i = 3;
                } else if (upperCase.indexOf("ES") >= 0 || upperCase.indexOf("SP") > 0) {
                    String property2 = System.getProperty("microedition.local.country");
                    if (property2.indexOf("ES") >= 0 || property2.indexOf("SP") > 0) {
                        i = 4;
                    } else {
                        i = 20;
                    }
                } else if (upperCase.indexOf("BR") >= 0) {
                    i = 5;
                } else if (upperCase.indexOf("PT") >= 0) {
                    i = System.getProperty("microedition.local.country").indexOf("BR") >= 0 ? 5 : 6;
                } else if (upperCase.indexOf("JA") >= 0 || upperCase.indexOf("JP") >= 0) {
                    i = 7;
                } else if (upperCase.indexOf("ZH") >= 0 || upperCase.indexOf("CN") >= 0) {
                    i = 8;
                } else if (upperCase.indexOf("KO") >= 0 || upperCase.indexOf("KP") >= 0 || upperCase.indexOf("KR") >= 0) {
                    i = 9;
                } else if (upperCase.indexOf("RU") >= 0) {
                    i = 10;
                } else if (upperCase.indexOf("PL") >= 0) {
                    i = 12;
                } else if (upperCase.indexOf("TR") >= 0) {
                    i = 11;
                } else if (upperCase.indexOf("CZ") >= 0) {
                    i = 13;
                } else if (upperCase.indexOf("NL") >= 0) {
                    i = 14;
                } else if (upperCase.indexOf("TH") >= 0) {
                    i = 15;
                } else if (upperCase.indexOf("VI") >= 0 || upperCase.indexOf("VN") >= 0) {
                    i = 16;
                } else if (upperCase.indexOf("AR") >= 0) {
                    i = 17;
                }
            }
        } catch (Exception e) {
        }
        return i;
    }

    public String dr(int i) {
        switch (i) {
            case 0:
                return "EN";
            case 1:
                return "DE";
            case 2:
                return "FR";
            case 3:
                return "IT";
            case 4:
                return "ES";
            case 5:
                return "BR";
            case 6:
                return "PT";
            case 7:
                return "JP";
            case 8:
                return "CN";
            case 9:
                return "KR";
            case 10:
                return "RU";
            case 11:
                return "TR";
            case 12:
                return "PL";
            case 13:
                return "CZ";
            case 14:
                return "NL";
            case 15:
                return "TH";
            case 16:
                return "VI";
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                return "AR";
            case 18:
            case 19:
            default:
                return null;
            case 20:
                return "ES_LATAM";
        }
    }

    static void k(String str, int i) {
        int iJ = j(new ByteArrayInputStream(db(i)));
        if (w.VS) {
            TI[i] = iJ;
            du(TI[i]);
        } else {
            kT();
        }
        ke();
    }

    static void l(String str, int i) {
        da(i);
        if (w.VS) {
            if (w.Wj && RU) {
                TI[i] = j(new ByteArrayInputStream(db(i)));
            } else {
                TI[i] = j(RJ);
            }
            ke();
            du(TI[i]);
            return;
        }
        if (w.Wj && RU) {
            j(new ByteArrayInputStream(db(i)));
        } else {
            j(RJ);
        }
        ke();
        kT();
    }

    static void m(String str, int i) {
        int iJ = j(new ByteArrayInputStream(db(i)));
        if (w.VS) {
            TI[i] = iJ;
            du(TI[i]);
        } else {
            kT();
        }
        ke();
    }

    private static void kS() {
        TI = new int[w.VT];
        for (int i = 0; i < w.VT; i++) {
            TI[i] = -1;
        }
        TJ = new byte[w.VT][];
        TL = new int[w.VT][];
        TK = new int[w.VT];
        TM = new String[w.VT][];
    }

    private static int j(InputStream inputStream) {
        int iG = 0;
        if (w.VS) {
            try {
                c(TK != null, "Text_LoadTextFromStream: text_multiple_nbStrings is null");
                iG = g(inputStream);
                c(iG < w.VT, "Text_LoadTextFromStream: Trying to load a tab higher than GLLibConfig.text_maxPacks!");
                TK[iG] = i(inputStream);
                if (TK[iG] > (1 << w.VU)) {
                    c(false, "WARNING! You are loading a text pack with more than " + (1 << w.VU) + " strings! All extras will be ignored");
                    TK[iG] = 1 << w.VU;
                }
                TL[iG] = new int[TK[iG] + 1];
                for (int i = 1; i < TK[iG] + 1; i++) {
                    TL[iG][i] = i(inputStream);
                }
                TJ[iG] = new byte[TL[iG][TK[iG]]];
                b(inputStream, TJ[iG], 0, TJ[iG].length);
                return iG;
            } catch (Exception e) {
                d(e);
                return iG;
            }
        } else {
            try {
                TD = i(inputStream);
                TG = new int[TD + 1];
                for (int i2 = 1; i2 < TD + 1; i2++) {
                    TG[i2] = i(inputStream);
                }
                TF = new byte[TG[TD]];
                b(inputStream, TF, 0, TF.length);
            } catch (Exception e3) {
            }
            return TF.length + ((TD + 1) << 2);
        }
    }

    public static void n(String str, int i) {
        a(str, i, (String) null);
    }

    public static void a(String str, int i, String str2) {
        bk(str);
        if (w.Wl && str2 != null) {
            i = o(RH, str2);
        }
        if (w.VS) {
            dv(i);
        } else {
            kU();
        }
        if (w.VS && TI == null) {
            kS();
        }
        if (w.Wl) {
            k(str, i);
        } else if (w.Wk) {
            m(str, i);
        } else {
            l(str, i);
        }
    }

    public static int ds(int i) {
        return i >> w.VU;
    }

    public static String dt(int i) {
        c(TE != null, "Text_GetString.current text encoding is not set, use Text_SetEncoding()");
        if (w.VS) {
            int iDs = ds(i);
            int i2 = i & ((1 << w.VU) - 1);
            if (TM != null && TM[iDs] != null) {
                return TM[iDs][i2];
            }
            try {
                int i3 = TL[iDs][i2 + 1] - TL[iDs][i2];
                if (i3 == 0) {
                    return null;
                }
                return new String(TJ[iDs], TL[iDs][i2], i3, TE);
            } catch (Exception e) {
                c(false, "Text_GetString.unable to create string, might try another encoding." + e);
                return null;
            }
        }
        if (TH != null) {
            return TH[i];
        }
        try {
            int i4 = TG[i + 1] - TG[i];
            if (i4 == 0) {
                return null;
            }
            return new String(TF, TG[i], i4, TE);
        } catch (Exception e2) {
            c(false, "Text_GetString.unable to create string, might try another encoding." + e2);
            return null;
        }
    }

    public static void kT() {
        int i = 0;
        if (w.VS) {
            while (i < w.VT) {
                du(i);
                i++;
            }
            return;
        }
        String[] strArr = new String[TD];
        while (i < TD) {
            strArr[i] = dt(i);
            i++;
        }
        TH = strArr;
        TG = null;
        TF = null;
        if (w.Vf) {
        }
    }

    public static void du(int i) {
        if (!w.VS) {
            c(false, "WARNING! Trying to call buildStringCache with an index! You can only do that when text_useMultipleTextPacks is true");
            return;
        }
        if (TK[i] != 0) {
            String[] strArr = new String[TK[i]];
            for (int i2 = 0; i2 < TK[i]; i2++) {
                strArr[i2] = dt((i << w.VU) + i2);
            }
            TM[i] = strArr;
            TL[i] = null;
            TJ[i] = null;
            if (w.Vf) {
            }
        }
    }

    public static void kU() {
        if (w.VS) {
            for (int i = 0; i < w.VT; i++) {
                dv(i);
            }
            return;
        }
        if (TH != null) {
            for (int i2 = 0; i2 < TD; i2++) {
                TH[i2] = null;
            }
            TH = null;
        }
        TG = null;
        TF = null;
        TD = 0;
    }

    public static void dv(int i) {
        int i2;
        if (!w.VS) {
            c(false, "Warning! Trying to call Text_FreeIndex(int, String) when GLLibConfig.text_useMultipleTextPacks is false!");
            return;
        }
        if (TI != null && (i2 = TI[i]) != -1) {
            if (TM[i2] != null) {
                for (int i3 = 0; i3 < TK[i2]; i3++) {
                    TM[i2][i3] = null;
                }
                TM[i2] = null;
            }
            TL[i2] = null;
            TJ[i2] = null;
            TK[i2] = 0;
            TI[i] = -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:13:0x004d  */
    /* JADX WARN: Code duplicated, block: B:17:0x0057  */
    /* JADX WARN: Code duplicated, block: B:24:0x0095  */
    /* JADX WARN: Code duplicated, block: B:28:0x009f  */
    /* JADX WARN: Code duplicated, block: B:30:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:31:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:36:0x00da A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:37:0x00de A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:38:0x007b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:40:0x0049 A[SYNTHETIC] */
    public static String a(long j, int i, String str) {
        if (j < 1000) {
            return "" + j;
        }
        switch (i) {
            case 0:
            case 7:
            case 8:
            case 9:
            case 15:
            case 17:
                str = ",";
                break;
            case 1:
            case 3:
            case 5:
            case 16:
                str = ".";
                break;
            case 2:
            case 4:
            case 6:
            case 10:
            case 20:
                if (j < 10000) {
                    str = "";
                }
                break;
            case 11:
            case 14:
                str = j >= 10000 ? "." : "";
                break;
            case 12:
            case 13:
                break;
            default:
                return "" + j;
        }
        String result = "";
        long group = Math.abs(j % 1000);
        long remaining = j / 1000;
        while (group != 0 || remaining != 0) {
            if (group < 10) {
                result = "00" + Math.abs(group) + result;
            } else if (group < 100) {
                result = "0" + Math.abs(group) + result;
            } else {
                result = Math.abs(group) + result;
            }
            group = remaining % 1000;
            remaining /= 1000;
            if (remaining != 0) {
                result = str + result;
            } else if (group != 0) {
                result = group + str + result;
                group = 0;
            }
        }
        return result;
    }

    public static String a(String str, String[] strArr) {
        int i = 0;
        String str2 = "";
        if (str.indexOf(37) >= 0) {
            int i2 = 0;
            do {
                int iIndexOf = str.indexOf(37, i);
                if (iIndexOf < 0 || iIndexOf == str.length() - 1) {
                    str2 = str2 + str.substring(i2);
                    i = -1;
                } else if (str.charAt(iIndexOf + 1) == 's') {
                    int iCharAt = iIndexOf + 2 < str.length() ? str.charAt(iIndexOf + 2) - '0' : -1;
                    if (iCharAt >= 0 && iCharAt <= 9) {
                        str2 = (str2 + str.substring(i2, iIndexOf)) + strArr[iCharAt];
                        int i3 = iIndexOf + 3;
                        i2 = i3;
                        i = i3;
                    } else {
                        be("Invalid string format pattern '" + str + "'");
                        i = iIndexOf + 1;
                    }
                } else {
                    i = iIndexOf + 1;
                }
            } while (i >= 0);
            return str2;
        }
        return str;
    }

    public static String l(String str, String str2, String str3) {
        int iIndexOf = str.indexOf(str2);
        while (iIndexOf != -1) {
            str = str.substring(0, iIndexOf) + str3 + str.substring(str2.length() + iIndexOf);
            iIndexOf = str.indexOf(str2, iIndexOf + str3.length());
        }
        return str;
    }

    private static void kV() {
        if (TN != null) {
            try {
                TN.akW();
            } catch (Exception e) {
                if (!(e instanceof javax.microedition.b.c)) {
                    throw GameRuntime.<RuntimeException>propagateChecked(e);
                }
                be("ERROR! Failed closing RMS: " + e);
            }
            TN = null;
        }
    }

    private static void bE(String str) {
        be(" Open recordstore : " + str);
        TN = javax.microedition.b.b.t(str, true);
    }

    public static byte[] bF(String str) {
        return B(str, "");
    }

    public static byte[] B(String str, String str2) {
        byte[] bytes = null;
        if (!"".equals(str2) && str2 != null) {
            bytes = str2.getBytes();
        }
        return c(str, bytes);
    }

    public static byte[] c(String str, byte[] bArr) {
        return d(str, bArr);
    }

    private static byte[] d(String str, byte[] bArr) {
        byte[] bArrB = GameSaveStore.read(str);
        if (w.Wn && bArr != null) {
            bArrB = b(bArrB, bArr, true);
        }
        return bArrB;
    }

    public static void e(String str, byte[] bArr) {
        a(str, bArr, "");
    }

    public static void a(String str, byte[] bArr, String str2) {
        byte[] bytes = null;
        if (!"".equals(str2) && str2 != null) {
            bytes = str2.getBytes();
        }
        a(str, bArr, bytes);
    }

    public static void a(String str, byte[] bArr, byte[] bArr2) {
        a(str, bArr, 0, bArr.length, bArr2);
    }

    private static void a(String str, byte[] bArr, int i, int i2, byte[] bArr2) {
        b(str, bArr, i, i2, bArr2);
    }

    private static void b(String str, byte[] bArr, int i, int i2, byte[] bArr2) {
        if (w.Wn && bArr2 != null) {
            bArr = a(bArr, bArr2, true);
            i2 = bArr.length;
        }
        GameSaveStore.write(str, bArr, i, i2);
    }

    public static void bG(String str) {
        GameSaveStore.delete(str);
    }

    public static void kW() {
        TR = true;
        if (TO != null && TT) {
            Iterator<bl> it = TO.values().iterator();
            while (it.hasNext()) {
                it.next().nz();
            }
        }
    }

    public static void bH(String str) {
        bl blVar;
        if (TT && w.Zr) {
            if (TO.containsKey(str)) {
                blVar = (bl) TO.get(str);
                blVar.nA();
            } else {
                blVar = new bl(str);
                TO.put(str, blVar);
                if (TR) {
                    blVar.nz();
                }
            }
            if (TP == null) {
                TP = new bm(str, 0);
            } else {
                bm bmVar = new bm(str, blVar.getCount() - 1);
                TP.a(bmVar);
                TP = bmVar;
            }
            TS++;
        }
    }

    public static void bI(String str) {
        if (TT && w.Zr && TO.containsKey(str)) {
            TO.get(str).nC();
            bm bmVarND = TP;
            if (TQ == null || !TQ.remove(str)) {
                while (bmVarND != null && !bmVarND.getName().equals(str)) {
                    if (TQ == null) {
                        TQ = new ArrayList<>();
                    }
                    TQ.add(str);
                    bmVarND = bmVarND.nD();
                }
                if (bmVarND != null) {
                    TP = bmVarND.nD();
                }
            }
        }
    }

    static final void kX() {
        TX = 0;
        Uj = 0;
        Uf = 0;
        Ug = 0;
        Uh = kH();
        Ui = kI();
        TY = new int[20][];
        if (w.YX) {
            TY[0] = new int[5];
            dw(0);
        }
        if (w.YY) {
            TY[1] = new int[6];
            dw(1);
        }
        if (w.Zi) {
            TY[19] = new int[2];
            dw(19);
        }
        if (w.Zj) {
            TY[9] = new int[3];
            dw(9);
        }
        if (w.Zk) {
            TY[10] = new int[6];
            dw(10);
        }
        if (w.Zc) {
            TY[11] = new int[20];
            dw(11);
        }
        if (w.Zl) {
            TY[12] = new int[2];
            dw(12);
        }
        if (w.Zo) {
            TY[18] = new int[11];
            dw(18);
        }
        if (w.Zm) {
            TY[14] = new int[1];
            dw(14);
        }
        if (w.Zn) {
            TY[15] = new int[7];
            dw(15);
        }
        if (w.Ze) {
            TY[5] = new int[1];
            dw(5);
        }
        if (w.Zf) {
            TY[6] = new int[1];
            dw(6);
        }
        if (w.Zg) {
            TY[7] = new int[2];
            dw(7);
        }
        if (w.Zh) {
            TY[8] = new int[2];
            dw(8);
        }
        Uc = 0;
        TW = true;
    }

    static final void dw(int i) {
        switch (i) {
            case 0:
                if (w.YX) {
                    TY[0][4] = 250;
                }
                break;
            case 1:
                if (w.YY) {
                    TY[1][3] = 250;
                }
                break;
            case 5:
                if (w.Ze) {
                    TY[5][0] = 255;
                }
                break;
            case 6:
                if (w.Zf) {
                    TY[6][0] = 255;
                }
                break;
            case 7:
                if (w.Zg) {
                    TY[7][0] = 255;
                    TY[7][1] = 0;
                }
                break;
            case 8:
                if (w.Zh) {
                    TY[8][0] = 255;
                }
                break;
            case 9:
                if (w.Zj) {
                    TY[9][1] = -1;
                    TY[9][0] = -1;
                    TY[9][2] = 0;
                }
                break;
            case 10:
                if (w.Zk) {
                    TY[10][1] = 0;
                    TY[10][2] = 40;
                    TY[10][3] = 16777215;
                    TY[10][4] = 0;
                    TY[10][5] = 8;
                    TY[10][0] = -1;
                }
                break;
            case 11:
                if (w.Zc) {
                    TY[11][0] = 172;
                    TY[11][1] = 104;
                    TY[11][2] = 2;
                    TY[11][3] = 0;
                    TY[11][4] = 0;
                    TY[11][5] = 0;
                    TY[11][6] = 1 << w.VP;
                    TY[11][7] = 2 << w.VP;
                    TY[11][8] = 4;
                    TY[11][9] = 4;
                    TY[11][10] = 16;
                    TY[11][11] = 112;
                    TY[11][12] = 64;
                    TY[11][13] = 72;
                    TY[11][14] = 3;
                }
                break;
            case 12:
                if (w.Zl) {
                    TY[12][1] = 255;
                    TY[12][0] = -1;
                }
                break;
            case 14:
                if (w.Zm) {
                    TY[14][0] = -16777216;
                }
                break;
            case 15:
                if (w.Zn) {
                    TY[15][0] = 0;
                    TY[15][1] = -1;
                    TY[15][2] = -1;
                    TY[15][3] = -1;
                    TY[15][4] = -1;
                    TY[15][5] = -1;
                    TY[15][6] = -1;
                }
                break;
            case 18:
                if (w.Zo) {
                    TY[18][1] = 100;
                    TY[18][2] = -1;
                    TY[18][0] = -1;
                    TY[18][3] = 100;
                    TY[18][4] = 100;
                    TY[18][5] = 0;
                    TY[18][6] = 0;
                    TY[18][7] = 0;
                    TY[18][8] = 0;
                    TY[18][9] = 255;
                }
                break;
            case 19:
                if (w.Zi) {
                    TY[19][0] = -1;
                    TY[19][1] = 0;
                }
                break;
        }
    }

    static final void h(int i, boolean z) {
        TX |= 1 << i;
        if (!TU && ((1 << i) & 2559) != 0) {
            be("PFX_EnableEffect: Setting a full-screen effect when neither NokiaUI is available nor a full-screen buffer!");
            TX &= -2560;
        }
        if (((1 << i) & 31) != 0) {
            Uc |= 1 << i;
        }
        if (i == 14 && !w.Zm) {
            be("PFX_EnableEffect: Stencil Effect is not enabled, you must set pfx_useSpriteEffectStencil to TRUE!");
        } else if (i == 18 && !w.Zo) {
            be("PFX_EnableEffect: Free Rotate Scale Effect is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
        } else if (i == 12 && !w.Zl) {
            be("PFX_EnableEffect: Blend Effect is not enabled, you must set pfx_useSpriteEffectBlend to TRUE!");
        } else if (i == 9 && !w.Zj) {
            be("PFX_EnableEffect: Grayscale Effect is not enabled, you must set pfx_useSpriteEffectGrayscale to TRUE!");
        } else if (i == 10 && !w.Zk) {
            be("PFX_EnableEffect: Shine Effect is not enabled, you must set pfx_useSpriteEffectShine to TRUE!");
        } else if (i == 5 && !w.Ze) {
            be("PFX_EnableEffect: Additive Effect is not enabled, you must set pfx_useSpriteEffectAdditive to TRUE!");
        } else if (i == 6 && !w.Zf) {
            be("PFX_EnableEffect: Multiplicative Effect is not enabled, you must set pfx_useSpriteEffectMultiplicative to TRUE!");
        } else if (i == 7 && !w.Zg) {
            be("PFX_EnableEffect: Overlay Effect is not enabled, you must set pfx_useSpriteEffectOverlay to TRUE!");
        } else if (i == 8 && !w.Zh) {
            be("PFX_EnableEffect: Color Dodge Effect is not enabled, you must set pfx_useSpriteEffectColorDodge to TRUE!");
        } else if (i == 15 && !w.Zn) {
            be("PFX_EnableEffect: Perspective Effect is not enabled, you must set pfx_useSpriteEffectPerspective to TRUE!");
        }
        if (z) {
            dw(i);
        }
        kY();
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0066  */
    /* JADX WARN: Code duplicated, block: B:31:0x006a  */
    /* JADX WARN: Code duplicated, block: B:33:0x0071  */
    /* JADX WARN: Code duplicated, block: B:38:0x0081  */
    /* JADX WARN: Code duplicated, block: B:49:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:52:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:54:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:57:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:59:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:61:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:62:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:63:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:65:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:67:0x0101  */
    /* JADX WARN: Code duplicated, block: B:70:0x0105  */
    /* JADX WARN: Code duplicated, block: B:72:0x0110  */
    /* JADX WARN: Code duplicated, block: B:73:0x0119  */
    /* JADX WARN: Code duplicated, block: B:74:0x011b  */
    /* JADX WARN: Code duplicated, block: B:76:0x011f  */
    /* JADX WARN: Code duplicated, block: B:80:0x0128  */
    /* JADX WARN: Code duplicated, block: B:81:0x012f  */
    /* JADX WARN: Code duplicated, block: B:83:0x0135  */
    private static final void kY() {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        boolean z4;
        boolean z5;
        if (w.Zo) {
            if ((TX & 262144) != 0) {
                int i2 = TY[18][1];
                if (i2 == 100) {
                    i2 = TY[18][3];
                }
                float f = i2;
                int i3 = TY[18][1];
                if (i3 == 100) {
                    i3 = TY[18][4];
                }
                float f2 = i3;
                if (w.Zp) {
                    f /= 10.0f;
                    f2 /= 10.0f;
                }
                c(f, f2, TY[18][5] != 0);
                int i4 = TY[18][2] & 255;
                if (i4 != 255) {
                    g(255, 255, 255, i4);
                    m2do(0);
                    z = false;
                    z2 = false;
                } else {
                    z = false;
                    z2 = true;
                }
            } else {
                z = true;
                z2 = true;
            }
        } else {
            z = true;
            z2 = true;
        }
        if (w.Zj) {
            if ((TX & AdRequest.MAX_CONTENT_URL_LENGTH) != 0) {
                m2do(2);
                z2 = false;
            } else {
                if (w.Zo) {
                    if ((TX & 262144) != 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (!z5) {
                    }
                }
                if (!w.Zi) {
                    if ((TX & 524288) != 0) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    if (z4) {
                        int i5 = TY[19][0];
                        g((i5 >> 16) & 255, (i5 >> 8) & 255, i5 & 255, (i5 >> 24) & 255);
                        if (TY[19][1] == 0) {
                            m2do(1);
                        } else {
                            m2do(4);
                        }
                        z2 = false;
                    } else if (!w.Zl) {
                        if ((TX & 4096) != 0) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (z3) {
                            i = TY[12][1];
                            if (i != 255) {
                                g(255, 255, 255, i);
                                m2do(0);
                                z2 = false;
                            }
                        } else if (w.Ze) {
                            if ((TX & 32) != 0) {
                                m2do(3);
                                z2 = false;
                            }
                        }
                    } else if (w.Ze) {
                        if ((TX & 32) != 0) {
                            m2do(3);
                            z2 = false;
                        }
                    }
                } else if (!w.Zl) {
                    if ((TX & 4096) != 0) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (z3) {
                        i = TY[12][1];
                        if (i != 255) {
                            g(255, 255, 255, i);
                            m2do(0);
                            z2 = false;
                        }
                    } else if (w.Ze) {
                        if ((TX & 32) != 0) {
                            m2do(3);
                            z2 = false;
                        }
                    }
                } else if (w.Ze) {
                    if ((TX & 32) != 0) {
                        m2do(3);
                        z2 = false;
                    }
                }
            }
        } else {
            if (w.Zo) {
                if ((TX & 262144) != 0) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                if (!z5 && TY[18][9] != 255) {
                    m2do(2);
                    z2 = false;
                }
            }
            if (!w.Zi) {
                if ((TX & 524288) != 0) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (z4) {
                    int i6 = TY[19][0];
                    g((i6 >> 16) & 255, (i6 >> 8) & 255, i6 & 255, (i6 >> 24) & 255);
                    if (TY[19][1] == 0) {
                        m2do(1);
                    } else {
                        m2do(4);
                    }
                    z2 = false;
                } else if (!w.Zl) {
                    if ((TX & 4096) != 0) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (z3) {
                        i = TY[12][1];
                        if (i != 255) {
                            g(255, 255, 255, i);
                            m2do(0);
                            z2 = false;
                        }
                    } else if (w.Ze) {
                        if ((TX & 32) != 0) {
                            m2do(3);
                            z2 = false;
                        }
                    }
                } else if (w.Ze) {
                    if ((TX & 32) != 0) {
                        m2do(3);
                        z2 = false;
                    }
                }
            } else if (!w.Zl) {
                if ((TX & 4096) != 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3) {
                    i = TY[12][1];
                    if (i != 255) {
                        g(255, 255, 255, i);
                        m2do(0);
                        z2 = false;
                    }
                } else if (w.Ze) {
                    if ((TX & 32) != 0) {
                        m2do(3);
                        z2 = false;
                    }
                }
            } else if (w.Ze) {
                if ((TX & 32) != 0) {
                    m2do(3);
                    z2 = false;
                }
            }
        }
        if (z2) {
            g(255, 255, 255, 255);
            m2do(0);
        }
        if (z) {
            c(100.0f, 100.0f, false);
        }
    }

    public static final void i(int i, boolean z) {
        if (w.YX && i == 0 && TY[0][3] != 3) {
            if (TY[0][3] < 2) {
                TY[0][3] = 2;
            }
        } else {
            TX &= (1 << i) ^ (-1);
            if (((1 << i) & 31) != 0) {
                Uc &= (1 << i) ^ (-1);
            }
            if (w.YY && i == 1) {
                Ue = false;
            }
        }
        if (z) {
            dw(i);
        }
        kY();
    }

    static final void kZ() {
        TX &= -841697;
        kY();
    }

    public static final void l(int i, int i2, int i3) {
        if (TY[i][i2] != i3) {
            TY[i][i2] = i3;
            kY();
        }
    }

    public static final void a(int i, int i2, int i3, int i4, int[] iArr) {
        iArr[0] = (((i3 * i2) - (i4 * i)) + (1 << (w.VP - 1))) >> w.VP;
        iArr[1] = (((i4 * i2) + (i3 * i)) + (1 << (w.VP - 1))) >> w.VP;
    }

    public static final void b(int i, int i2, int i3, int i4, int[] iArr) {
        int[] iArrHt = a.ht();
        a(i, i2, 0, i4, iArrHt);
        int i5 = iArrHt[0];
        int i6 = iArrHt[1];
        a(i, i2, i3, i4, iArrHt);
        int i7 = iArrHt[0];
        int i8 = iArrHt[1];
        a(i, i2, i3, 0, iArrHt);
        int i9 = iArrHt[0];
        int i10 = iArrHt[1];
        iArr[0] = Math.max(Math.abs(i7 - 0), Math.abs(i9 - i5));
        iArr[1] = Math.max(Math.abs(i8 - 0), Math.abs(i10 - i6));
    }

    public static void b(aj ajVar, float f, float f2, float f3, float f4, int i, int i2, int i3) {
        a(ajVar, f, f2, f3, f4, i, i2, i3);
    }

    static void a(float f, float f2, float f3, float f4, int i, int i2, int i3) {
        c(Qj, f, f2, f3, f4, i, i2, i3);
    }

    public static void c(aj ajVar, float f, float f2, float f3, float f4, int i, int i2, int i3) {
        c(i3 == 4 || i3 == 8 || i3 == 16 || i3 == 32, "DrawAlphaGradientRect: direction parameter is not valid: " + i3);
        a(ajVar, f, f2, f3, f4, i, i2, i3, true);
    }

    @Override // javax.microedition.lcdui.t
    public void a(javax.microedition.lcdui.o oVar) {
        if (w.ZB > 1 && oVar.getLabel() == "GLSetPointer") {
            Pointer_SetCurrentPointer(oVar.ake());
        } else {
            super.a(oVar);
        }
    }

    public static void Pointer_SetCurrentPointer(int i) {
        if (w.ZB > 1) {
            c(i < w.ZB, "Pointer_SetCurrentPointer: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
            if (i < w.ZB) {
                Uu = i;
            }
        }
    }

    public static int Pointer_GetNumPointers() {
        return w.ZB;
    }

    public static final void la() {
        Uk = 0;
        if (w.ZB > 1) {
            if (UC == null) {
                Uv = new int[w.ZB];
                Uw = new int[w.ZB];
                Ux = new int[w.ZB];
                Uy = new int[w.ZB];
                UA = new int[w.ZB];
                UB = new int[w.ZB];
                Uz = new int[w.ZB];
                UC = new boolean[w.ZB];
                UD = new boolean[w.ZB];
                UE = new boolean[w.ZB];
            }
            for (int i = 0; i < w.ZB; i++) {
                Uz[i] = 0;
            }
        }
    }

    public static final void lb() {
        if (Ur) {
            dz(1);
            Ur = false;
        } else if (Ut) {
            dz(3);
            Ut = false;
        } else if (Us) {
            dz(2);
            Us = false;
            Ut = false;
        }
        Up = Ul;
        Uq = Um;
        if (w.ZB > 1) {
            for (int i = 0; i < w.ZB; i++) {
                if (UC[i]) {
                    T(i, 1);
                    UC[i] = false;
                } else if (UE[i]) {
                    T(i, 3);
                    UE[i] = false;
                } else if (UD[i]) {
                    T(i, 2);
                    UD[i] = false;
                    UE[i] = false;
                }
                UA[i] = Uv[i];
                UB[i] = Uw[i];
            }
        }
    }

    public static final void aj(boolean z) {
        switch (Uk) {
            case 1:
                dz(4);
                break;
            case 2:
                dz(0);
                break;
        }
        Un = Up;
        Uo = Uq;
        if (z) {
            Ul = -1;
            Um = -1;
            if (Uv != null) {
                for (int i = 0; i < Uv.length; i++) {
                    Uv[i] = -1;
                    Uw[i] = -1;
                }
            }
        }
        if (w.ZB > 1 && Uz != null) {
            for (int i2 = 0; i2 < w.ZB; i2++) {
                switch (Uz[i2]) {
                    case 1:
                        T(i2, 4);
                        break;
                    case 2:
                        T(i2, 0);
                        break;
                }
                Ux[i2] = UA[i2];
                Uy[i2] = UB[i2];
            }
        }
    }

    public static final int dy(int i) {
        c(i < w.ZB, "Pointer_GetState: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uz[i];
        }
        return Uk;
    }

    public static void S(int i, int i2) {
        if (w.ZB > 1) {
            m(0, i, i2);
        }
    }

    public static void m(int i, int i2, int i3) {
        c(i < w.ZB, "Pointer_ChangeStateCallback: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB <= 1) {
            S(i2, i3);
        }
    }

    private static final void dz(int i) {
        T(0, i);
    }

    private static void T(int i, int i2) {
        c(i < w.ZB, "Pointer_SwitchState: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            m(i, Uz[i], i2);
            Uz[i] = i2;
            if (i == 0) {
                Uk = i2;
                return;
            }
            return;
        }
        S(Uk, i2);
        Uk = i2;
    }

    @Override // javax.microedition.lcdui.f
    protected void U(int i, int i2) {
        n(Uu, i, i2);
    }

    private void n(int i, int i2, int i3) {
        c(i < w.ZB, "pointerReleased: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            if (i == 0) {
                Ul = i2;
                Um = i3;
            }
            Uv[i] = i2;
            Uw[i] = i3;
            UD[i] = true;
            if (i == 0) {
                Us = true;
                return;
            }
            return;
        }
        Ul = i2;
        Um = i3;
        Us = true;
    }

    @Override // javax.microedition.lcdui.f
    protected void V(int i, int i2) {
        o(Uu, i, i2);
    }

    private void o(int i, int i2, int i3) {
        c(i < w.ZB, "pointerPressed: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            if (i == 0) {
                Ul = i2;
                Um = i3;
            }
            Uv[i] = i2;
            Uw[i] = i3;
            UC[i] = true;
            if (i == 0) {
                Ur = true;
                return;
            }
            return;
        }
        Ul = i2;
        Um = i3;
        Ur = true;
    }

    @Override // javax.microedition.lcdui.f
    protected void W(int i, int i2) {
        p(Uu, i, i2);
    }

    private void p(int i, int i2, int i3) {
        c(i < w.ZB, "pointerDragged: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            if (i == 0) {
                Ul = i2;
                Um = i3;
            }
            Uv[i] = i2;
            Uw[i] = i3;
            UE[i] = true;
            if (i == 0) {
                Ut = true;
                return;
            }
            return;
        }
        Ul = i2;
        Um = i3;
        Ut = true;
    }

    public static final boolean dA(int i) {
        c(i < w.ZB, "Pointer_IsReleased: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uz[i] == 2;
        }
        return Uk == 2;
    }

    public static final boolean dB(int i) {
        c(i < w.ZB, "Pointer_IsPressed: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uz[i] == 1;
        }
        return Uk == 1;
    }

    public static final boolean dC(int i) {
        c(i < w.ZB, "Pointer_IsDragged: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uz[i] == 3;
        }
        return Uk == 3;
    }

    public static final boolean dD(int i) {
        c(i < w.ZB, "Pointer_IsIdle: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uz[i] == 0;
        }
        return Uk == 0;
    }

    public static final boolean dE(int i) {
        c(i < w.ZB, "Pointer_IsDown: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uz[i] == 4;
        }
        return Uk == 4;
    }

    public static final boolean dF(int i) {
        c(i < w.ZB, "Pointer_IsHeldDown: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB <= 1) {
            return Uk == 4 || Uk == 1 || Uk == 3;
        }
        int i2 = Uz[i];
        return i2 == 4 || i2 == 1 || i2 == 3;
    }

    public static final int dG(int i) {
        c(i < w.ZB, "Pointer_GetX: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uv[i];
        }
        return Ul;
    }

    public static final int dH(int i) {
        c(i < w.ZB, "Pointer_GetY: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uw[i];
        }
        return Um;
    }

    public static final int dI(int i) {
        c(i < w.ZB, "Pointer_GetPrevX: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Ux[i];
        }
        return Un;
    }

    public static final int dJ(int i) {
        c(i < w.ZB, "Pointer_GetPrevY: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            return Uy[i];
        }
        return Uo;
    }

    public static final boolean h(int[] iArr) {
        c(iArr != null, "Pointer_IsInRect: rect array parameter is null");
        c(iArr.length == 4, "Pointer_IsInRect: rect array length must be 4 (rect[0] = x, rect[1] = y, rect[2] = width, rect[3] = height)");
        return h(iArr[0], iArr[1], iArr[2], iArr[3]);
    }

    public static final boolean h(int i, int i2, int i3, int i4) {
        return w.ZB > 1 ? a(0, Uz[0], i, i2, i3, i4) : a(0, Uk, i, i2, i3, i4);
    }

    public static boolean a(int i, int i2, int i3, int i4, int i5, int i6) {
        c(i < w.ZB, "Pointer_IsInRect: pointer ID " + i + " out of range.  Change GLLibConfig.touchScreen_numPointers.");
        if (w.ZB > 1) {
            if (Uv[i] <= i3 || Uv[i] >= i3 + i5 || Uw[i] <= i4 || Uw[i] >= i4 + i6) {
                return false;
            }
            switch (i2) {
                case 0:
                    return dD(i);
                case 1:
                    return dB(i);
                case 2:
                    return dA(i);
                case 3:
                    return dC(i);
                case 4:
                    return dE(i);
                default:
                    return false;
            }
        }
        if (Ul <= i3 || Ul >= i3 + i5 || Um <= i4 || Um >= i4 + i6) {
            return false;
        }
        switch (i2) {
            case 0:
                return dD(0);
            case 1:
                return dB(0);
            case 2:
                return dA(0);
            case 3:
                return dC(0);
            case 4:
                return dE(0);
            default:
                return false;
        }
    }

    public void b(u uVar) {
    }

    public void c(boolean z, bc bcVar) {
    }

    private static boolean lc() {
        return UG != null;
    }

    private static void ld() {
        UG = new int[128];
        for (int i = 0; i < UG.length; i++) {
            UG[i] = -1;
        }
        for (int i2 = 0; i2 < UF.length; i2++) {
            UG[UF[i2]] = i2;
        }
    }

    public static String e(byte[] bArr) {
        return e(bArr, 0, bArr.length);
    }

    public static String e(byte[] bArr, int i, int i2) {
        int i3 = 0;
        if (!lc()) {
            ld();
        }
        char[] cArr = new char[((i2 + 2) / 3) * 4];
        int i4 = 0;
        while (true) {
            int i5 = i3;
            if (i4 < cArr.length) {
                a(bArr, i + i5, i2 - i5, cArr, i4);
                i3 = i5 + 3;
                i4 += 4;
            } else {
                return new String(cArr);
            }
        }
    }

    private static void a(byte[] bArr, int i, int i2, char[] cArr, int i3) {
        byte b = bArr[i];
        cArr[i3] = UF[(b >>> 2) & 63];
        if (i2 > 2) {
            byte b2 = bArr[i + 1];
            byte b3 = bArr[i + 2];
            cArr[i3 + 1] = UF[((b << 4) & 48) + ((b2 >>> 4) & 15)];
            cArr[i3 + 2] = UF[((b2 << 2) & 60) + ((b3 >>> 6) & 3)];
            cArr[i3 + 3] = UF[b3 & 63];
            return;
        }
        if (i2 > 1) {
            byte b4 = bArr[i + 1];
            cArr[i3 + 1] = UF[((b << 4) & 48) + ((b4 >>> 4) & 15)];
            cArr[i3 + 2] = UF[((b4 << 2) & 60) + 0];
            cArr[i3 + 3] = '=';
            return;
        }
        cArr[i3 + 1] = UF[((b << 4) & 48) + 0];
        cArr[i3 + 2] = '=';
        cArr[i3 + 3] = '=';
    }

    public static byte[] bJ(String str) {
        try {
            return e(str, 0, str.length());

        } catch (IOException failure) {
            throw GameRuntime.<RuntimeException>propagateChecked(failure);
        }
    }

    public static byte[] e(String str, int i, int i2) throws IOException {
        if (!lc()) {
            ld();
        }
        if (w.XZ && i2 % 4 != 0) {
            throw new IOException("Base64 string length is not multiple of 4");
        }
        int i3 = (i2 / 4) * 3;
        if (str.charAt((i + i2) - 1) == '=') {
            i3--;
            if (str.charAt((i + i2) - 2) == '=') {
                i3--;
            }
        }
        byte[] bArr = new byte[i3];
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5 += 4) {
            a(str.charAt(i + i5), str.charAt(i + i5 + 1), str.charAt(i + i5 + 2), str.charAt(i + i5 + 3), bArr, i4);
            i4 += 3;
        }
        return bArr;
    }

    private static void a(char c, char c2, char c3, char c4, byte[] bArr, int i) throws IOException {
        int i2;
        int i3;
        char c5;
        int i4 = UG[c & 127];
        int i5 = UG[c2 & 127];
        if (c4 == '=') {
            if (c3 == '=') {
                c5 = 2;
                i3 = 0;
                i2 = 0;
            } else {
                i2 = UG[c3 & 127];
                c5 = 1;
                i3 = 0;
            }
        } else {
            i2 = UG[c3 & 127];
            i3 = UG[c4 & 127];
            c5 = 0;
        }
        if (w.XZ && (i4 < 0 || i5 < 0 || i2 < 0 || i3 < 0)) {
            throw new IOException("Invalid character in Base64 string");
        }
        bArr[i] = (byte) (((i4 << 2) & 252) | ((i5 >>> 4) & 3));
        if (c5 < 2) {
            bArr[i + 1] = (byte) (((i5 << 4) & 240) | ((i2 >>> 2) & 15));
            if (c5 < 1) {
                bArr[i + 2] = (byte) (((i2 << 6) & 192) | (i3 & 63));
            }
        }
    }

    static String a(boolean z, boolean z2, int i, int i2) {
        String strBY;
        String str;
        byte[] bArrAa;
        if (w.ZE && !w.ZF) {
            strBY = com.gameloft.android2d.iap.b.eG(Qu.bY("URL-SUPPORT"));
        } else {
            strBY = Qu.bY("URL-SUPPORT");
        }
        if (strBY != null && !strBY.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
            if (i2 == 2) {
                str = (strBY + "&opref=") + e(com.gameloft.android.wrapper.au.getSerial().getBytes());
            } else {
                String property = System.getProperty("IMEI");
                if (property != null) {
                    property = e(property.getBytes());
                }
                str = strBY + "&opref=" + property;
            }
            String strJs = s.js();
            if (w.Yj && (strJs.indexOf("null:") >= 0 || !strJs.startsWith("anonymous"))) {
                strJs = "anonymous:" + s.iY();
            }
            String str2 = str + "&extra_4=" + s.bc(strJs);
            if (z) {
                str2 = str2 + "&extra_7=" + e(("facebook:" + bt.nZ().getId()).getBytes());
            }
            String property2 = System.getProperty("MAC");
            if (property2 == null) {
                property2 = Qu.bY("MAC");
            }
            if (property2 != null && property2.compareTo("00:00:00:00:00:00") != 0 && (bArrAa = com.gameloft.android.wrapper.au.aa(property2, "G4m3l0ft_s3cr3t0")) != null) {
                str2 = (str2 + "&extra_8=") + e(bArrAa);
            }
            String strBY2 = Qu == null ? null : Qu.bY("MIDlet-Version");
            if (strBY2 != null) {
                strBY = (str2 + "&extra_13=") + s.bc(strBY2);
            } else {
                strBY = str2;
            }
            if (z2) {
                int iIndexOf = strBY.indexOf("SUPPORT");
                strBY = strBY.substring(0, iIndexOf) + "BANNED" + strBY.substring("SUPPORT".length() + iIndexOf, strBY.length());
                if (z2) {
                    strBY = (strBY + "&extra_14=") + i;
                }
            }
            if (i2 > 0) {
                String strGetHDIDFV = GetHDIDFV();
                if (strGetHDIDFV != null && !strGetHDIDFV.equals("")) {
                    strGetHDIDFV = e(strGetHDIDFV.getBytes());
                }
                return (strBY + "&extra_15=") + strGetHDIDFV;
            }
        }
        return strBY;
    }

    public static String GetHDIDFV() {
        return com.gameloft.android.wrapper.au.Os();
    }

    boolean le() {
        if (w.ZF) {
            return true;
        }
        return com.gameloft.android2d.iap.b.QJ();
    }

    void q(int i, String str) {
        a(i, str, 0L);
    }

    void a(int i, String str, long j) {
        int iY = y(i, str);
        if (w.ZF) {
            n.e(iY, str);
        } else {
            com.gameloft.android2d.iap.b.b(iY, str, j);
        }
    }

    void lf() {
        if (w.ZF) {
            n.iA();
        } else {
            com.gameloft.android2d.iap.b.iA();
        }
    }

    int lg() {
        return w.ZF ? n.iC() : com.gameloft.android2d.iap.b.iC();
    }

    boolean dK(int i) {
        return w.ZF ? n.cg(i) : com.gameloft.android2d.iap.b.cg(i);
    }

    boolean lh() {
        return w.ZF ? n.iD() : com.gameloft.android2d.iap.b.iD();
    }

    static int li() {
        if (w.ZF) {
            return n.iz();
        }
        if (!w.ZD) {
            return com.gameloft.android2d.iap.b.iz();
        }
        return com.gameloft.android2d.iap.b.QM();
    }

    static String lj() {
        return w.ZF ? n.iH() : com.gameloft.android2d.iap.b.iH();
    }

    int lk() {
        return w.ZF ? n.getError() : com.gameloft.android2d.iap.b.getError();
    }

    String r(int i, String str) {
        int iY = y(i, str);
        return w.ZF ? n.b(iY, str) : com.gameloft.android2d.iap.b.b(iY, str);
    }

    String s(int i, String str) {
        int iY = y(i, str);
        return w.ZF ? n.f(iY, str) : com.gameloft.android2d.iap.b.f(iY, str);
    }

    String ll() {
        return com.gameloft.android2d.iap.b.QO();
    }

    void lm() {
        if (w.ZF) {
            n.iE();
        } else {
            com.gameloft.android2d.iap.b.iE();
        }
    }

    public static void bK(String str) {
        if (w.ZF) {
            n.setLanguage(str);
            return;
        }
        if (w.ZK && bz.om().fZ() && au.mN().mO()) {
            bc bcVar = new bc();
            bcVar.a("language", str.toLowerCase());
            bz.om().j(bcVar);
        }
        com.gameloft.android2d.iap.b.setLanguage(str);
    }

    String ln() {
        return com.gameloft.android2d.iap.b.getLanguage();
    }

    static long t(int i, String str) {
        int iY = y(i, str);
        return w.ZF ? n.g(iY, str) : com.gameloft.android2d.iap.b.g(iY, str);
    }

    static long u(int i, String str) {
        int iY = y(i, str);
        return w.ZF ? n.l(iY, str) : com.gameloft.android2d.iap.b.l(iY, str);
    }

    static String v(int i, String str) {
        int iY = y(i, str);
        return w.ZF ? n.k(iY, str) : com.gameloft.android2d.iap.b.k(iY, str);
    }

    static float w(int i, String str) {
        int iY = y(i, str);
        return !w.ZF ? com.gameloft.android2d.iap.b.c(iY, str) : n.c(iY, str);
    }

    static float x(int i, String str) {
        int iY = y(i, str);
        return !w.ZF ? com.gameloft.android2d.iap.b.Q(iY, str) : n.d(iY, str);
    }

    static long lo() {
        if (w.ZF) {
            return 200000L;
        }
        return com.gameloft.android2d.iap.b.QT();
    }

    static void lp() {
        if (!w.ZF) {
            com.gameloft.android2d.iap.b.QN();
        }
    }

    static int lq() {
        return !w.ZF ? com.gameloft.android2d.iap.b.QP() : w.ZG;
    }

    public static String a(int i, String str, int i2) {
        int iY = y(i, str);
        if (w.ZF) {
            return null;
        }
        return com.gameloft.android2d.iap.b.b(iY, str, i2);
    }

    static int bL(String str) {
        return w.ZF ? n.aB(str) : com.gameloft.android2d.iap.b.aB(str);
    }

    static int y(int i, String str) {
        if (w.ZD) {
            if (w.ZF) {
                return n.j(i, str);
            }
            return com.gameloft.android2d.iap.b.j(i, str);
        }
        return i;
    }

    public static String z(int i, String str) {
        int iY = y(i, str);
        return !w.ZF ? com.gameloft.android2d.iap.b.U(iY, str) : str + "_" + iY;
    }

    public static String A(int i, String str) {
        int iY = y(i, str);
        return !w.ZF ? com.gameloft.android2d.iap.b.T(iY, str) : str + "_" + iY;
    }

    static long lr() {
        return w.ZF ? n.iI() : com.gameloft.android2d.iap.b.iI();
    }

    public static String ls() {
        return w.ZF ? "USD" : com.gameloft.android2d.iap.b.QX();
    }

    static String B(int i, String str) {
        return w.ZF ? "USD" : com.gameloft.android2d.iap.b.S(y(i, str), str);
    }

    public static void lt() {
        if (!w.ZF) {
            com.gameloft.android2d.iap.b.QY();
        }
    }

    static float lu() {
        return w.ZF ? n.iJ() : com.gameloft.android2d.iap.b.iJ();
    }

    public static void lv() {
    }

    public static String lw() {
        if (w.ZF) {
            return null;
        }
        return com.gameloft.android2d.iap.b.QZ();
    }

    public static void bM(String str) {
        if (!w.ZF) {
            com.gameloft.android2d.iap.b.eD(str);
        }
    }

    public static void lx() {
        if (!w.ZF) {
            com.gameloft.android2d.iap.b.Rf();
        }
    }

    public static void ly() {
        if (!w.ZF) {
            com.gameloft.android2d.iap.b.Re();
        }
    }

    static void j(int i, boolean z) {
        if (w.ZO) {
            com.gameloft.android2d.e.a.M(i, z);
        }
    }

    static boolean lz() {
        if (w.ZO) {
            return com.gameloft.android2d.e.a.isRunning();
        }
        return false;
    }

    public static Vector lA() {
        if (w.ZV) {
            return com.gameloft.android.wrapper.au.Ps();
        }
        return null;
    }

    public static be a(cc ccVar, cc ccVar2, cc ccVar3, cc ccVar4, float f) {
        new be().nr();
        be beVar = new be();
        be beVar2 = new be();
        be beVarG = be.g((45.0f * Rw) / 180.0f);
        be beVarH = be.h(((-35.2643f) * Rw) / 180.0f);
        be beVarB = beVarG.b(beVarH);
        be beVarI = be.i((ccVar.alf[0] * Rw) / 180.0f);
        be beVarB2 = beVarB.b(beVarI);
        beVarG.ns();
        beVarH.ns();
        be beVarB3 = beVarB2.b(beVarH).b(beVarG);
        float f2 = ccVar.alf[1] + ccVar2.alf[0];
        float f3 = ccVar.alf[1] + ccVar2.alf[1];
        float f4 = ccVar.alf[1] + ccVar2.alf[2];
        beVar.nr();
        float f5 = 1.0f - f2;
        float f6 = 1.0f - f3;
        float f7 = 1.0f - f4;
        beVar.aiV[0][0] = 0.3086f * f5;
        beVar.aiV[0][1] = 0.3086f * f6;
        beVar.aiV[0][2] = 0.3086f * f7;
        beVar.aiV[0][3] = 0.0f;
        beVar.aiV[1][0] = 0.6094f * f5;
        beVar.aiV[1][1] = 0.6094f * f6;
        beVar.aiV[1][2] = 0.6094f * f7;
        beVar.aiV[1][3] = 0.0f;
        beVar.aiV[2][0] = f5 * 0.082f;
        beVar.aiV[2][1] = f6 * 0.082f;
        beVar.aiV[2][2] = f7 * 0.082f;
        beVar.aiV[2][3] = 0.0f;
        beVar.aiV[3][0] = 0.0f;
        beVar.aiV[3][1] = 0.0f;
        beVar.aiV[3][2] = 0.0f;
        beVar.aiV[3][3] = 1.0f;
        beVarI.nr();
        beVarI.aiV[0][0] = f2;
        beVarI.aiV[1][1] = f3;
        beVarI.aiV[2][2] = f4;
        beVarI.aiV[3][3] = 1.0f;
        be beVarC = beVar.c(beVarI);
        beVarC.aiV[3][3] = 1.0f;
        beVarI.nr();
        beVar2.nr();
        beVar2.aiV[0][0] = ccVar4.alf[0] + f;
        beVar2.aiV[1][1] = ccVar4.alf[1] + f;
        beVar2.aiV[2][2] = ccVar4.alf[2] + f;
        beVar2.aiV[3][3] = 1.0f;
        be beVarB4 = beVarB3.b(beVar2).b(beVarC);
        beVarB4.aiV[3][0] = (ccVar.alf[2] - 1.0f) + ccVar3.alf[0];
        beVarB4.aiV[3][1] = (ccVar.alf[2] - 1.0f) + ccVar3.alf[1];
        beVarB4.aiV[3][2] = (ccVar.alf[2] - 1.0f) + ccVar3.alf[2];
        return beVarB4;
    }

    public static boolean lB() {
        return w.ZV && UK != null;
    }

    public static be lC() {
        if (UL != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= UL.size()) {
                    break;
                }
                v vVar = (v) UL.elementAt(i2);
                if (!UM.equals(vVar.Vb) || UN != vVar.Vc) {
                    i = i2 + 1;
                } else {
                    return vVar.lJ();
                }
            }
        }
        return UK;
    }

    public static be o(String str, int i) {
        if (UL != null) {
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 >= UL.size()) {
                    break;
                }
                v vVar = (v) UL.elementAt(i3);
                if (!str.equals(vVar.Vb) || i != vVar.Vc) {
                    i2 = i3 + 1;
                } else {
                    return vVar.lJ();
                }
            }
        }
        return UK;
    }

    public static be lD() {
        return UK;
    }

    public static void a(be beVar) {
        UK = beVar;
    }

    public static int lE() {
        return UN;
    }

    public static int dL(int i) {
        return a(i, UK);
    }

    public static int a(int i, be beVar) {
        if (lB() && beVar != null) {
            UO.alf[0] = ((i >> 16) & 255) / 255.0f;
            UO.alf[1] = ((i >> 8) & 255) / 255.0f;
            UO.alf[2] = (i & 255) / 255.0f;
            UO.alf[3] = 1.0f;
            cd cdVarA = beVar.a(UO, UP);
            return ((-16777216) & i) | Math.max(Math.min((int) (cdVarA.alf[2] * 255.0f), 255), 0) | (Math.max(Math.min((int) (cdVarA.alf[0] * 255.0f), 255), 0) << 16) | (Math.max(Math.min((int) (cdVarA.alf[1] * 255.0f), 255), 0) << 8);
        }
        return i;
    }

    public static void dM(int i) {
        if (w.ZQ) {
            com.gameloft.android2d.g.a.mZ(i);
        }
    }

    public static boolean lF() {
        if (w.ZQ) {
            return com.gameloft.android2d.g.a.isRunning();
        }
        return false;
    }

    public static void lG() {
        if (w.ZQ) {
            com.gameloft.android2d.g.a.VC();
        }
    }

    private static boolean lH() {
        return UQ != null;
    }

    private static void lI() {
        UT = new String[w.VF];
        US = new int[w.VF];
        UQ = new javax.microedition.lcdui.u[w.VF];
        UR = new ay[w.VF];
    }

    private static void bO(String str) {
        int i = (SR << 24) | (SO << 16) | (SP << 8) | SQ;
        javax.microedition.lcdui.u uVar = Qk;
        int iJe = uVar.je(str);
        int height = uVar.getHeight();
        int iCS = cS(iJe);
        int iCS2 = cS(height);
        javax.microedition.lcdui.ao aoVarCL = javax.microedition.lcdui.ao.cL(iCS, iCS2);
        javax.microedition.lcdui.al alVarAkr = aoVarCL.akr();
        alVarAkr.a(Qk);
        alVarAkr.ah(SO, SP, SQ);
        alVarAkr.b(str, 0, 0, 20);
        int[] iArrC = a.c((int[]) null);
        aoVarCL.a(iArrC, 0, iCS, 0, 0, iCS, iCS2);
        ay ayVar = new ay(iArrC, iCS, iCS2);
        if (UR[w.VF - 1] != null) {
            UR[w.VF - 1].nk();
        }
        for (int i2 = w.VF - 1; i2 > 0; i2--) {
            UT[i2] = UT[i2 - 1];
            US[i2] = US[i2 - 1];
            UQ[i2] = UQ[i2 - 1];
            UR[i2] = UR[i2 - 1];
        }
        UT[0] = str;
        US[0] = i;
        UQ[0] = uVar;
        UR[0] = ayVar;
    }

    private static boolean a(javax.microedition.lcdui.u uVar, javax.microedition.lcdui.u uVar2) {
        return uVar.akj() == uVar2.akj() && uVar.getSize() == uVar2.getSize() && uVar.getHeight() == uVar2.getHeight() && uVar.getStyle() == uVar2.getStyle();
    }

    public static boolean a(char[] cArr, int i, boolean z, boolean z2) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (UX == null) {
            UX = (int[][]) dq(530);
        }
        boolean z3 = false;
        for (int i6 = 0; i6 < i; i6++) {
            int iDN = dN(cArr[i6]);
            if (iDN == 3 || iDN == 4) {
                z3 = true;
                break;
            }
        }
        if (!z3) {
            return false;
        }
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        byte[] bArr = new byte[i];
        int i7 = 0;
        if (!z) {
            for (int i8 = 0; i8 < i; i8++) {
                int iDN2 = dN(cArr[i8]);
                if (iDN2 == 3 || iDN2 == 4) {
                    i7 = 1;
                    break;
                }
                if (iDN2 == 0) {
                    break;
                }
            }
        } else {
            i7 = 1;
        }
        int i9 = 18;
        boolean z4 = false;
        byte b = 0;
        int i10 = 0;
        int i11 = i7;
        while (true) {
            int i12 = i10;
            if (i12 >= i) {
                if (z4) {
                    for (int i13 = 0; i13 < i; i13++) {
                        iArr2[i13] = iArr2[i13] & 63;
                    }
                }
                for (int i14 = 0; i14 < i; i14++) {
                    switch (iArr[i14]) {
                        case 1:
                        case 2:
                        case 5:
                        case 6:
                        case 7:
                            iArr[i14] = 14;
                            cArr[i14] = 31;
                            break;
                    }
                }
                int i15 = 0;
                int i16 = i7;
                for (int i17 = 0; i17 < i; i17++) {
                    if (iArr2[i17] != i16) {
                        i16 = iArr2[i17];
                        i15++;
                    }
                }
                int i18 = i15 + 1;
                int[] iArr3 = new int[i18];
                int i19 = 0;
                int i20 = 0;
                int i21 = 0;
                int i22 = i7;
                while (i21 < i) {
                    if (iArr2[i21] != i22) {
                        i2 = i19 + 1;
                        iArr3[i19] = i20;
                        i20 = i21;
                        i22 = iArr2[i21];
                    } else {
                        i2 = i19;
                    }
                    i21++;
                    i19 = i2;
                }
                int i23 = i19 + 1;
                iArr3[i19] = i20;
                int i24 = i7;
                int i25 = 0;
                while (i25 < i18) {
                    int i26 = iArr3[i25];
                    int i27 = i25 == iArr3.length + (-1) ? i : iArr3[i25 + 1];
                    int i28 = iArr2[i26];
                    int i29 = i25 == i18 + (-1) ? i7 : iArr2[iArr3[i25 + 1]];
                    int iMax = Math.max(i24, i28) % 2;
                    int iMax2 = Math.max(i28, i29) % 2;
                    int i30 = iMax;
                    int i31 = iMax;
                    for (int i32 = i26; i32 < i27; i32++) {
                        if (iArr[i32] == 13) {
                            iArr[i32] = i31;
                        }
                        if (iArr[i32] == 8 && i30 == 4) {
                            iArr[i32] = 11;
                        }
                        if (iArr[i32] == 4) {
                            i30 = iArr[i32];
                            iArr[i32] = 3;
                        } else if (iArr[i32] == 3 || iArr[i32] == 0) {
                            i30 = iArr[i32];
                        }
                        i31 = iArr[i32];
                    }
                    int i33 = iMax;
                    int i34 = iMax;
                    while (i26 < i27) {
                        int i35 = i26 == i27 + (-1) ? iMax2 : iArr[i26 + 1];
                        if (i34 == 8 && i35 == 8) {
                            if (iArr[i26] == 9 || iArr[i26] == 12) {
                                iArr[i26] = i35;
                            }
                        } else if (i34 == 11 && i35 == 11 && iArr[i26] == 12) {
                            iArr[i26] = i35;
                        }
                        if (iArr[i26] == 10 || iArr[i26] == 14) {
                            if (i34 == 8) {
                                iArr[i26] = i34;
                            } else {
                                int i36 = i26 + 1;
                                while (i36 < i27 && (iArr[i36] == 10 || iArr[i36] == 14)) {
                                    i36++;
                                }
                                if (i36 < i27 && iArr[i36] == 8) {
                                    for (int i37 = i26; i37 < i36; i37++) {
                                        iArr[i37] = 8;
                                    }
                                }
                            }
                        }
                        switch (iArr[i26]) {
                            case 9:
                            case 10:
                            case 12:
                            case 14:
                                iArr[i26] = 18;
                                break;
                        }
                        if (i33 == 0 && iArr[i26] == 8) {
                            iArr[i26] = i33;
                        }
                        if (iArr[i26] == 3 || iArr[i26] == 0 || iArr[i26] == 4) {
                            i33 = iArr[i26];
                        }
                        int i38 = iArr[i26];
                        i26++;
                        i34 = i38;
                    }
                    i24 = i28;
                    i25++;
                }
                int i39 = i7;
                int i40 = 0;
                while (i40 < i18) {
                    int i41 = iArr3[i40];
                    int i42 = i40 == iArr3.length + (-1) ? i : iArr3[i40 + 1];
                    int i43 = iArr2[i41];
                    int i44 = i43 % 2 == 0 ? 0 : 3;
                    int i45 = i40 == i18 + (-1) ? i7 : iArr2[iArr3[i40 + 1]];
                    int iMax3 = Math.max(i39, i43) % 2;
                    int iMax4 = Math.max(i43, i45) % 2;
                    int i46 = -1;
                    int i47 = iMax3;
                    while (i41 <= i42) {
                        int i48 = -1;
                        switch (i41 == i42 ? iMax4 : iArr[i41]) {
                            case 0:
                                i48 = 0;
                                break;
                            case 3:
                            case 8:
                            case 11:
                                i48 = 3;
                                break;
                            case 14:
                            case 15:
                            case 16:
                            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                            case 18:
                                if (i46 == -1) {
                                    i46 = i41;
                                }
                                break;
                        }
                        if (i48 != -1) {
                            if (i46 != -1) {
                                if (i47 != i48) {
                                    i47 = i44;
                                }
                                while (i46 < i41) {
                                    iArr[i46] = i47;
                                    i46++;
                                }
                            }
                            i46 = -1;
                        } else {
                            i48 = i47;
                        }
                        i41++;
                        i47 = i48;
                    }
                    i39 = i43;
                    i40++;
                }
                for (int i49 = 0; i49 < i; i49++) {
                    if ((iArr2[i49] & 1) == 0) {
                        if (iArr[i49] == 3) {
                            iArr2[i49] = iArr2[i49] + 1;
                        } else if (iArr[i49] == 11 || iArr[i49] == 8) {
                            iArr2[i49] = iArr2[i49] + 2;
                        }
                    } else if (iArr[i49] == 0 || iArr[i49] == 8 || iArr[i49] == 11) {
                        iArr2[i49] = iArr2[i49] + 1;
                    }
                }
                int i50 = i - 1;
                while (i50 > 0 && dN(cArr[i50]) == 17) {
                    i50--;
                }
                if (i50 < i - 1) {
                    while (true) {
                        i50++;
                        if (i50 < i) {
                            iArr2[i50] = i7;
                        }
                    }
                }
                for (int i51 = 0; i51 < i; i51++) {
                    int iDN3 = dN(cArr[i51]);
                    if (iDN3 == 17) {
                        int i52 = i51;
                        while (i52 < i && dN(cArr[i52]) == 17) {
                            i52++;
                        }
                        if (i52 == i || dN(cArr[i52]) == 15 || dN(cArr[i52]) == 16) {
                            for (int i53 = i52 - 1; i53 >= i51; i53--) {
                                iArr2[i53] = i7;
                            }
                        }
                    } else if (iDN3 == 15 || iDN3 == 16) {
                        iArr2[i51] = i7;
                    }
                }
                for (int i54 = 0; i54 < i; i54++) {
                    if (iArr2[i54] % 2 == 1) {
                        cArr[i54] = f(cArr[i54]);
                    }
                }
                int i55 = iArr2[0];
                for (int i56 = 0; i56 < i; i56++) {
                    if (iArr2[i56] > i55) {
                        i55 = iArr2[i56];
                    }
                }
                for (int i57 = i55; i57 > 0; i57--) {
                    a(cArr, bArr, iArr2, i57, i55, i);
                }
                if (z2) {
                    byte b2 = 0;
                    for (int i58 = 0; i58 < i - 1; i58++) {
                        if (bArr[i58] == -1) {
                            cArr[i58] = 1;
                            if (i58 < i - 2) {
                                b2 = bArr[i58 + 2];
                                cArr[i58 + 1] = (char) b2;
                                bArr[i58 + 1] = 0;
                            } else {
                                cArr[i58 + 1] = (char) b2;
                                bArr[i58 + 1] = 0;
                            }
                        }
                    }
                }
                return true;
            }
            int iDN4 = dN(cArr[i12]);
            if (z2) {
                if ((cArr[i12] == 1 || (w.Xq && cArr[i12] == '\\')) && i12 < i - 1) {
                    b = cArr[i12] == '\\' ? (byte) (cArr[i12 + 1] - '0') : (byte) cArr[i12 + 1];
                    cArr[i12] = 14;
                    cArr[i12 + 1] = 14;
                    bArr[i12] = -1;
                    bArr[i12 + 1] = -1;
                    iDN4 = 14;
                }
                if (bArr[i12] != -1) {
                    bArr[i12] = b;
                }
            }
            byte b3 = b;
            int i59 = iDN4;
            switch (i59) {
                case 1:
                    int i60 = (i11 + 2) & (-2);
                    iArr2[i12] = i60;
                    iArr2[i12] = X(iArr2[i12], i9);
                    i3 = 18;
                    i11 = i60;
                    break;
                case 2:
                    int i61 = (i11 + 2) & (-2);
                    iArr2[i12] = i61;
                    z4 = true;
                    i59 = 0;
                    i3 = 0;
                    i11 = i61;
                    break;
                case 5:
                    int i62 = (i11 + 1) | 1;
                    iArr2[i12] = i62;
                    iArr2[i12] = X(iArr2[i12], i9);
                    i3 = 18;
                    i11 = i62;
                    break;
                case 6:
                    int i63 = (i11 + 1) | 1;
                    iArr2[i12] = i63;
                    z4 = true;
                    i59 = 3;
                    i3 = 3;
                    i11 = i63;
                    break;
                case 7:
                    int iF = f(iArr2, i12);
                    if (iF == -1) {
                        i5 = 18;
                        i4 = i7;
                    } else {
                        i4 = iF & 63;
                        int i64 = iF & 192;
                        i5 = (i64 & 64) != 0 ? 3 : (i64 & 128) != 0 ? 0 : 18;
                    }
                    iArr2[i12] = i4;
                    i3 = i5;
                    i11 = i4;
                    break;
                case 16:
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    iArr2[i12] = i11;
                    if (i9 != 18) {
                        i59 = i9;
                        i3 = i9;
                    } else {
                        i59 = 18;
                        i3 = i9;
                    }
                    break;
                default:
                    iArr2[i12] = i11;
                    if (i9 != 18) {
                        i59 = i9;
                        i3 = i9;
                    } else {
                        i3 = i9;
                    }
                    break;
            }
            iArr[i12] = i59;
            i10 = i12 + 1;
            b = b3;
            i9 = i3;
        }
    }

    private static char f(char c) {
        if ((c & 65280) == 0) {
            switch (c) {
                case '(':
                    return ')';
                case ')':
                    return '(';
                case '<':
                    return '>';
                case '>':
                    return '<';
                case '[':
                    return ']';
                case ']':
                    return '[';
                case '{':
                    return '}';
                case '}':
                    return '{';
                case 171:
                    return (char) 187;
                case 187:
                    return (char) 171;
                default:
                    return c;
            }
        }
        if ((c & 65280) == 8192) {
            switch (c) {
                case 8249:
                    return (char) 8250;
                case 8250:
                    return (char) 8249;
                case 8261:
                    return (char) 8262;
                case 8262:
                    return (char) 8261;
                case 8317:
                    return (char) 8318;
                case 8318:
                    return (char) 8317;
                case 8333:
                    return (char) 8334;
                case 8334:
                    return (char) 8333;
                default:
                    return c;
            }
        }
        if ((c & 65280) == 8704) {
            switch (c) {
                case 8712:
                    return (char) 8715;
                case 8713:
                    return (char) 8716;
                case 8714:
                    return (char) 8717;
                case 8715:
                    return (char) 8712;
                case 8716:
                    return (char) 8713;
                case 8717:
                    return (char) 8714;
                case 8718:
                case 8719:
                case 8720:
                case 8721:
                case 8722:
                case 8723:
                case 8724:
                case 8726:
                case 8727:
                case 8728:
                case 8729:
                case 8730:
                case 8731:
                case 8732:
                case 8733:
                case 8734:
                case 8735:
                case 8736:
                case 8737:
                case 8738:
                case 8739:
                case 8740:
                case 8741:
                case 8742:
                case 8743:
                case 8744:
                case 8745:
                case 8746:
                case 8747:
                case 8748:
                case 8749:
                case 8750:
                case 8751:
                case 8752:
                case 8753:
                case 8754:
                case 8755:
                case 8756:
                case 8757:
                case 8758:
                case 8759:
                case 8760:
                case 8761:
                case 8762:
                case 8763:
                case 8766:
                case 8767:
                case 8768:
                case 8769:
                case 8770:
                case 8772:
                case 8773:
                case 8774:
                case 8775:
                case 8776:
                case 8777:
                case 8778:
                case 8779:
                case 8780:
                case 8781:
                case 8782:
                case 8783:
                case 8784:
                case 8785:
                case 8790:
                case 8791:
                case 8792:
                case 8793:
                case 8794:
                case 8795:
                case 8796:
                case 8797:
                case 8798:
                case 8799:
                case 8800:
                case 8801:
                case 8802:
                case 8803:
                case 8812:
                case 8813:
                case 8844:
                case 8845:
                case 8846:
                case 8851:
                case 8852:
                case 8853:
                case 8854:
                case 8855:
                case 8857:
                case 8858:
                case 8859:
                case 8860:
                case 8861:
                case 8862:
                case 8863:
                case 8864:
                case 8865:
                case 8868:
                case 8869:
                case 8871:
                case 8874:
                case 8876:
                case 8877:
                case 8878:
                case 8879:
                case 8888:
                case 8889:
                case 8890:
                case 8891:
                case 8892:
                case 8893:
                case 8894:
                case 8895:
                case 8896:
                case 8897:
                case 8898:
                case 8899:
                case 8900:
                case 8901:
                case 8902:
                case 8903:
                case 8904:
                case 8910:
                case 8911:
                case 8914:
                case 8915:
                case 8916:
                case 8917:
                case 8942:
                case 8943:
                case 8949:
                case 8952:
                case 8953:
                default:
                    return c;
                case 8725:
                    return (char) 10741;
                case 8764:
                    return (char) 8765;
                case 8765:
                    return (char) 8764;
                case 8771:
                    return (char) 8909;
                case 8786:
                    return (char) 8787;
                case 8787:
                    return (char) 8786;
                case 8788:
                    return (char) 8789;
                case 8789:
                    return (char) 8788;
                case 8804:
                    return (char) 8805;
                case 8805:
                    return (char) 8804;
                case 8806:
                    return (char) 8807;
                case 8807:
                    return (char) 8806;
                case 8808:
                    return (char) 8809;
                case 8809:
                    return (char) 8808;
                case 8810:
                    return (char) 8811;
                case 8811:
                    return (char) 8810;
                case 8814:
                    return (char) 8815;
                case 8815:
                    return (char) 8814;
                case 8816:
                    return (char) 8817;
                case 8817:
                    return (char) 8816;
                case 8818:
                    return (char) 8819;
                case 8819:
                    return (char) 8818;
                case 8820:
                    return (char) 8821;
                case 8821:
                    return (char) 8820;
                case 8822:
                    return (char) 8823;
                case 8823:
                    return (char) 8822;
                case 8824:
                    return (char) 8825;
                case 8825:
                    return (char) 8824;
                case 8826:
                    return (char) 8827;
                case 8827:
                    return (char) 8826;
                case 8828:
                    return (char) 8829;
                case 8829:
                    return (char) 8828;
                case 8830:
                    return (char) 8831;
                case 8831:
                    return (char) 8830;
                case 8832:
                    return (char) 8833;
                case 8833:
                    return (char) 8832;
                case 8834:
                    return (char) 8835;
                case 8835:
                    return (char) 8834;
                case 8836:
                    return (char) 8837;
                case 8837:
                    return (char) 8836;
                case 8838:
                    return (char) 8839;
                case 8839:
                    return (char) 8838;
                case 8840:
                    return (char) 8841;
                case 8841:
                    return (char) 8840;
                case 8842:
                    return (char) 8843;
                case 8843:
                    return (char) 8842;
                case 8847:
                    return (char) 8848;
                case 8848:
                    return (char) 8847;
                case 8849:
                    return (char) 8850;
                case 8850:
                    return (char) 8849;
                case 8856:
                    return (char) 10680;
                case 8866:
                    return (char) 8867;
                case 8867:
                    return (char) 8866;
                case 8870:
                    return (char) 10974;
                case 8872:
                    return (char) 10980;
                case 8873:
                    return (char) 10979;
                case 8875:
                    return (char) 10981;
                case 8880:
                    return (char) 8881;
                case 8881:
                    return (char) 8880;
                case 8882:
                    return (char) 8883;
                case 8883:
                    return (char) 8882;
                case 8884:
                    return (char) 8885;
                case 8885:
                    return (char) 8884;
                case 8886:
                    return (char) 8887;
                case 8887:
                    return (char) 8886;
                case 8905:
                    return (char) 8906;
                case 8906:
                    return (char) 8905;
                case 8907:
                    return (char) 8908;
                case 8908:
                    return (char) 8907;
                case 8909:
                    return (char) 8771;
                case 8912:
                    return (char) 8913;
                case 8913:
                    return (char) 8912;
                case 8918:
                    return (char) 8919;
                case 8919:
                    return (char) 8918;
                case 8920:
                    return (char) 8921;
                case 8921:
                    return (char) 8920;
                case 8922:
                    return (char) 8923;
                case 8923:
                    return (char) 8922;
                case 8924:
                    return (char) 8925;
                case 8925:
                    return (char) 8924;
                case 8926:
                    return (char) 8927;
                case 8927:
                    return (char) 8926;
                case 8928:
                    return (char) 8929;
                case 8929:
                    return (char) 8928;
                case 8930:
                    return (char) 8931;
                case 8931:
                    return (char) 8930;
                case 8932:
                    return (char) 8933;
                case 8933:
                    return (char) 8932;
                case 8934:
                    return (char) 8935;
                case 8935:
                    return (char) 8934;
                case 8936:
                    return (char) 8937;
                case 8937:
                    return (char) 8936;
                case 8938:
                    return (char) 8939;
                case 8939:
                    return (char) 8938;
                case 8940:
                    return (char) 8941;
                case 8941:
                    return (char) 8940;
                case 8944:
                    return (char) 8945;
                case 8945:
                    return (char) 8944;
                case 8946:
                    return (char) 8954;
                case 8947:
                    return (char) 8955;
                case 8948:
                    return (char) 8956;
                case 8950:
                    return (char) 8957;
                case 8951:
                    return (char) 8958;
                case 8954:
                    return (char) 8946;
                case 8955:
                    return (char) 8947;
                case 8956:
                    return (char) 8948;
                case 8957:
                    return (char) 8950;
                case 8958:
                    return (char) 8951;
            }
        }
        if ((c & 65280) == 8960) {
            switch (c) {
                case 8968:
                    return (char) 8969;
                case 8969:
                    return (char) 8968;
                case 8970:
                    return (char) 8971;
                case 8971:
                    return (char) 8970;
                case 9001:
                    return (char) 9002;
                case 9002:
                    return (char) 9001;
                default:
                    return c;
            }
        }
        if ((c & 65280) == 9984) {
            switch (c) {
                case 10088:
                    return (char) 10089;
                case 10089:
                    return (char) 10088;
                case 10090:
                    return (char) 10091;
                case 10091:
                    return (char) 10090;
                case 10092:
                    return (char) 10093;
                case 10093:
                    return (char) 10092;
                case 10094:
                    return (char) 10095;
                case 10095:
                    return (char) 10094;
                case 10096:
                    return (char) 10097;
                case 10097:
                    return (char) 10096;
                case 10098:
                    return (char) 10099;
                case 10099:
                    return (char) 10098;
                case 10100:
                    return (char) 10101;
                case 10101:
                    return (char) 10100;
                case 10197:
                    return (char) 10198;
                case 10198:
                    return (char) 10197;
                case 10205:
                    return (char) 10206;
                case 10206:
                    return (char) 10205;
                case 10210:
                    return (char) 10211;
                case 10211:
                    return (char) 10210;
                case 10212:
                    return (char) 10213;
                case 10213:
                    return (char) 10212;
                case 10214:
                    return (char) 10215;
                case 10215:
                    return (char) 10214;
                case 10216:
                    return (char) 10217;
                case 10217:
                    return (char) 10216;
                case 10218:
                    return (char) 10219;
                case 10219:
                    return (char) 10218;
                default:
                    return c;
            }
        }
        if ((c & 65280) == 10496) {
            switch (c) {
                case 10627:
                    return (char) 10628;
                case 10628:
                    return (char) 10627;
                case 10629:
                    return (char) 10630;
                case 10630:
                    return (char) 10629;
                case 10631:
                    return (char) 10632;
                case 10632:
                    return (char) 10631;
                case 10633:
                    return (char) 10634;
                case 10634:
                    return (char) 10633;
                case 10635:
                    return (char) 10636;
                case 10636:
                    return (char) 10635;
                case 10637:
                    return (char) 10640;
                case 10638:
                    return (char) 10639;
                case 10639:
                    return (char) 10638;
                case 10640:
                    return (char) 10637;
                case 10641:
                    return (char) 10642;
                case 10642:
                    return (char) 10641;
                case 10643:
                    return (char) 10644;
                case 10644:
                    return (char) 10643;
                case 10645:
                    return (char) 10646;
                case 10646:
                    return (char) 10645;
                case 10647:
                    return (char) 10648;
                case 10648:
                    return (char) 10647;
                case 10680:
                    return (char) 8856;
                case 10688:
                    return (char) 10689;
                case 10689:
                    return (char) 10688;
                case 10692:
                    return (char) 10693;
                case 10693:
                    return (char) 10692;
                case 10703:
                    return (char) 10704;
                case 10704:
                    return (char) 10703;
                case 10705:
                    return (char) 10706;
                case 10706:
                    return (char) 10705;
                case 10708:
                    return (char) 10709;
                case 10709:
                    return (char) 10708;
                case 10712:
                    return (char) 10713;
                case 10713:
                    return (char) 10712;
                case 10714:
                    return (char) 10715;
                case 10715:
                    return (char) 10714;
                case 10741:
                    return (char) 8725;
                case 10744:
                    return (char) 10745;
                case 10745:
                    return (char) 10744;
                case 10748:
                    return (char) 10749;
                case 10749:
                    return (char) 10748;
                default:
                    return c;
            }
        }
        if ((c & 65280) == 10752) {
            switch (c) {
                case 10795:
                    return (char) 10796;
                case 10796:
                    return (char) 10795;
                case 10797:
                    return (char) 10796;
                case 10798:
                    return (char) 10797;
                case 10804:
                    return (char) 10805;
                case 10805:
                    return (char) 10804;
                case 10812:
                    return (char) 10813;
                case 10813:
                    return (char) 10812;
                case 10852:
                    return (char) 10853;
                case 10853:
                    return (char) 10852;
                case 10873:
                    return (char) 10874;
                case 10874:
                    return (char) 10873;
                case 10877:
                    return (char) 10878;
                case 10878:
                    return (char) 10877;
                case 10879:
                    return (char) 10880;
                case 10880:
                    return (char) 10879;
                case 10881:
                    return (char) 10882;
                case 10882:
                    return (char) 10881;
                case 10883:
                    return (char) 10884;
                case 10884:
                    return (char) 10883;
                case 10891:
                    return (char) 10892;
                case 10892:
                    return (char) 10891;
                case 10897:
                    return (char) 10898;
                case 10898:
                    return (char) 10897;
                case 10899:
                    return (char) 10900;
                case 10900:
                    return (char) 10899;
                case 10901:
                    return (char) 10902;
                case 10902:
                    return (char) 10901;
                case 10903:
                    return (char) 10904;
                case 10904:
                    return (char) 10903;
                case 10905:
                    return (char) 10906;
                case 10906:
                    return (char) 10905;
                case 10907:
                    return (char) 10908;
                case 10908:
                    return (char) 10907;
                case 10913:
                    return (char) 10914;
                case 10914:
                    return (char) 10913;
                case 10918:
                    return (char) 10919;
                case 10919:
                    return (char) 10918;
                case 10920:
                    return (char) 10921;
                case 10921:
                    return (char) 10920;
                case 10922:
                    return (char) 10923;
                case 10923:
                    return (char) 10922;
                case 10924:
                    return (char) 10925;
                case 10925:
                    return (char) 10924;
                case 10927:
                    return (char) 10928;
                case 10928:
                    return (char) 10927;
                case 10931:
                    return (char) 10932;
                case 10932:
                    return (char) 10931;
                case 10939:
                    return (char) 10940;
                case 10940:
                    return (char) 10939;
                case 10941:
                    return (char) 10942;
                case 10942:
                    return (char) 10941;
                case 10943:
                    return (char) 10944;
                case 10944:
                    return (char) 10943;
                case 10945:
                    return (char) 10946;
                case 10946:
                    return (char) 10945;
                case 10947:
                    return (char) 10948;
                case 10948:
                    return (char) 10947;
                case 10949:
                    return (char) 10950;
                case 10950:
                    return (char) 10949;
                case 10957:
                    return (char) 10958;
                case 10958:
                    return (char) 10957;
                case 10959:
                    return (char) 10960;
                case 10960:
                    return (char) 10959;
                case 10961:
                    return (char) 10962;
                case 10962:
                    return (char) 10961;
                case 10963:
                    return (char) 10964;
                case 10964:
                    return (char) 10963;
                case 10965:
                    return (char) 10966;
                case 10966:
                    return (char) 10965;
                case 10974:
                    return (char) 8870;
                case 10979:
                    return (char) 8873;
                case 10980:
                    return (char) 8872;
                case 10981:
                    return (char) 8875;
                case 10988:
                    return (char) 10989;
                case 10989:
                    return (char) 10988;
                case 10999:
                    return (char) 11000;
                case 11000:
                    return (char) 10999;
                case 11001:
                    return (char) 11002;
                case 11002:
                    return (char) 11001;
                default:
                    return c;
            }
        }
        if ((c & 65280) == 12288) {
            switch (c) {
                case 12296:
                    return (char) 12297;
                case 12297:
                    return (char) 12296;
                case 12298:
                    return (char) 12299;
                case 12299:
                    return (char) 12298;
                case 12300:
                    return (char) 12301;
                case 12301:
                    return (char) 12300;
                case 12302:
                    return (char) 12303;
                case 12303:
                    return (char) 12302;
                case 12304:
                    return (char) 12305;
                case 12305:
                    return (char) 12304;
                case 12306:
                case 12307:
                default:
                    return c;
                case 12308:
                    return (char) 12309;
                case 12309:
                    return (char) 12308;
                case 12310:
                    return (char) 12311;
                case 12311:
                    return (char) 12310;
                case 12312:
                    return (char) 12313;
                case 12313:
                    return (char) 12312;
                case 12314:
                    return (char) 12315;
                case 12315:
                    return (char) 12314;
            }
        }
        if ((c & 65280) == 65280) {
            switch (c) {
                case 65288:
                    return (char) 65289;
                case 65289:
                    return (char) 65288;
                case 65308:
                    return (char) 65310;
                case 65310:
                    return (char) 65308;
                case 65339:
                    return (char) 65341;
                case 65341:
                    return (char) 65339;
                case 65371:
                    return (char) 65373;
                case 65373:
                    return (char) 65371;
                case 65375:
                    return (char) 65376;
                case 65376:
                    return (char) 65375;
                case 65378:
                    return (char) 65379;
                case 65379:
                    return (char) 65378;
                default:
                    return c;
            }
        }
        return c;
    }

    private static int X(int i, int i2) {
        if (i2 != 18) {
            if (i2 == 3) {
                return i | 64;
            }
            if (i2 == 0) {
                return i | 128;
            }
            return i;
        }
        return i;
    }

    private static int f(int[] iArr, int i) {
        if (i <= 0) {
            return -1;
        }
        int i2 = i - 1;
        int i3 = iArr[i2];
        while (i2 >= 0 && iArr[i2] == i3) {
            i2--;
        }
        if (i2 >= 0) {
            return iArr[i2];
        }
        return -1;
    }

    private static void a(char[] cArr, byte[] bArr, int[] iArr, int i, int i2, int i3) {
        int iB = 0;
        int i4 = 0;
        while (i4 < i3 && iB < i3) {
            iB = b(iArr, i4, i3, i, i2);
            i4 = iB;
            while (i4 < i3 && i2 >= iArr[i4] && i <= iArr[i4]) {
                i4++;
            }
            int i5 = i4 - 1;
            while (i5 > iB) {
                char c = cArr[i5];
                cArr[i5] = cArr[iB];
                cArr[iB] = c;
                byte b = bArr[i5];
                bArr[i5] = bArr[iB];
                bArr[iB] = b;
                i5--;
                iB++;
            }
        }
    }

    private static int b(int[] iArr, int i, int i2, int i3, int i4) {
        for (int i5 = i; i5 < i2; i5++) {
            if (iArr[i5] >= i3 && iArr[i5] <= i4) {
                return i5;
            }
        }
        return i2;
    }

    private static int dN(int i) {
        int length = UX.length;
        int i2 = -1;
        while (length - i2 > 1) {
            int i3 = (i2 + length) / 2;
            if (i < UX[i3][UU]) {
                length = i3;
            } else {
                if (i <= UX[i3][UV]) {
                    return UX[i3][UW];
                }
                i2 = i3;
            }
        }
        return 18;
    }

    private static int g(char c) {
        if (c < 1569 || c > UZ) {
            return 3;
        }
        return UY[c - 1569][0];
    }

    private static char h(char c) {
        return UY[c - 1569][1];
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0058  */
    /* JADX WARN: Code duplicated, block: B:48:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:51:0x00b8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:52:0x00ba A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:54:0x00be  */
    public static void a(char[] cArr, int i) {
        int previous = 3;
        for (int index = 0; index < i; index++) {
            char current = cArr[index];
            int category = g(current);
            if (category == 5) {
                continue;
            }
            int nextCategory = 3;
            char next = 0;
            for (int lookahead = index + 1; lookahead < i; lookahead++) {
                if (g(cArr[lookahead]) != 5) {
                    next = cArr[lookahead];
                    nextCategory = g(cArr[lookahead]);
                    break;
                }
            }
            switch (category) {
                case 1:
                    cArr[index] = previous == 0 || previous == 2 || previous == 4
                            ? (char) (h(current) + 1) : h(current);
                    break;
                case 2:
                    boolean ligature = false;
                    if (current == 1604 && index < i + 1) {
                        boolean connected = previous == 0 || previous == 2 || previous == 4;
                        switch (next) {
                            case 1570:
                                cArr[index] = (char) (connected ? 65270 : 65269);
                                ligature = true;
                                break;
                            case 1571:
                                cArr[index] = (char) (connected ? 65272 : 65271);
                                ligature = true;
                                break;
                            case 1573:
                                cArr[index] = (char) (connected ? 65274 : 65273);
                                ligature = true;
                                break;
                            case 1575:
                                cArr[index] = (char) (connected ? 65276 : 65275);
                                ligature = true;
                                break;
                        }
                    }
                    if (ligature) {
                        cArr[index + 1] = 31;
                    } else if (previous == 0 || previous == 2 || previous == 4) {
                        cArr[index] = nextCategory == 1 || nextCategory == 2 || nextCategory == 4
                                ? (char) (h(current) + 3) : (char) (h(current) + 1);
                    } else {
                        cArr[index] = nextCategory == 1 || nextCategory == 2 || nextCategory == 4
                                ? (char) (h(current) + 2) : h(current);
                    }
                    break;
            }
            previous = category;
        }
    }
}
