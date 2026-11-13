package com.quanhoang.greenfarm3.S800x480;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.ads.AdRequest;
import java.io.IOException;
import java.lang.reflect.Array;

/* JADX INFO: compiled from: GLLibPlayer.java */
/* JADX INFO: loaded from: classes.dex */
public class aa implements Runnable {
    private static boolean[] abA;
    private static long[] abB;
    private static long abC;
    private static int[] abD;
    private static boolean[] abE;
    private static int[] abF;
    private static boolean[] abG;
    private static SoundPool abH;
    private static int[][] abI;
    private static boolean[][] abJ;
    private static int[] abK;
    private static AudioManager abM;
    private static boolean abN;
    private static int abO;
    private static int abP;
    private static int abQ;
    private static int abR;
    public static int[] abS;
    public static int[][] abT;
    private static byte[][][] abU;
    private static a[] abV;
    public static int abW;
    static cb[][][] abX;
    private static int abe;
    private static boolean abf;
    private static boolean abi;
    private static boolean abk;
    private static boolean[] abl;
    private static MediaPlayer[] abm;
    private static long[] abn;
    private static int[] abo;
    private static int[] abp;
    private static int[] abq;
    private static int[] abr;
    private static int[] abs;
    private static Thread abt;
    private static int[] abu;
    private static int[] abv;
    private static int[] abw;
    private static boolean abx;
    private static int[] aby;
    private static int[] abz;
    public int aaL;
    public int aaM;
    protected int aaN;
    public a aaO;
    public int aaP;
    public int aaQ;
    protected int aaR;
    protected int aaS;
    public int aaT;
    protected boolean aaU;
    public int aaV;
    public int aaW;
    public int aaY;
    public a[] aaZ;
    private byte[] aba;
    private short[] abb;
    protected static final int aaX = Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS / w.Wx;
    private static final int abc = w.VX;
    private static final int abd = w.VY;
    private static boolean abg = w.VW;
    private static boolean abh = true;
    private static final int abj = w.Wf;
    private static Context abL = GLLib.Qu.getApplicationContext();

    static {
        abM = w.VW ? (AudioManager) abL.getSystemService("audio") : null;
        abN = false;
        abO = w.Wp;
        abP = -1;
        abQ = -1;
        abR = 0;
        abW = 100;
    }

    public aa() {
        ip();
    }

    public aa(a aVar, int i, int i2) {
        GLLib.c(w.Wx <= 1000, "GLLibPlayer : Invalid value of GLLibConfig.sprite_animFPS > 1000");
        ip();
        this.aaL = i;
        this.aaM = i2;
        a(aVar);
    }

    public void ip() {
        this.aaL = 0;
        this.aaM = 0;
        this.aaP = -1;
        this.aaQ = 0;
        this.aaO = null;
        this.aaN = 0;
        dS(0);
        this.aaT = 1;
        this.aaY = -1;
        this.aaU = true;
        if (w.Zo) {
            this.aaW = -1;
        }
        if (w.Zl) {
            this.aaV = -1;
        }
    }

    public int lL() {
        return this.aaR;
    }

    public void dS(int i) {
        this.aaR = i;
    }

    public void a(a aVar) {
        this.aaO = aVar;
        if (aVar != null) {
            Y(-1, -1);
        } else {
            this.aaP = -1;
        }
    }

    public void Y(int i, int i2) {
        q(i, i2, -1);
    }

    public void q(int i, int i2, int i3) {
        GLLib.c(this.aaO != null, "GLLibPlayer.SetAnim().sprite is not set");
        GLLib.c(this.aaO != null, "GLLibPlayer.GetNbanim().sprite is not set");
        GLLib.c(i < this.aaO.hu(), "GLLibPlayer.SetAnim().anim out of range");
        GLLib.c(i2 != 0, "GLLibPlayer.SetAnim().nbLoop can not be 0. Use -1 for infinite animation");
        if (this.aaU || i != this.aaP) {
            this.aaP = i;
            this.aaS = i3 == -1 ? aaX : i3 / ak(false);
            dT(0);
            this.aaT = i2 - 1;
            this.aaU = false;
        }
    }

    public int dT(int i) {
        GLLib.c(this.aaO != null, "GLLibPlayer.SetFrame().sprite is not set");
        GLLib.c(i >= 0, "GLLibPlayer.SetFrame().frame is negative");
        if (this.aaP < 0) {
            return -1;
        }
        int iLO = i % lO();
        this.aaQ = iLO;
        dS(0);
        return iLO;
    }

    public void lM() {
        this.aaQ = GLLib.L(0, lO());
        dS(0);
    }

    public final int lN() {
        if (this.aaO == null || this.aaP == -1) {
            return -1;
        }
        return this.aaO.w(this.aaP, this.aaQ);
    }

    public final void dU(int i) {
        switch (i) {
            case 0:
                this.aaN = 0;
                break;
            case 1:
                this.aaN = 2;
                break;
            case 2:
                this.aaN = 1;
                break;
            case 3:
                this.aaN = 3;
                break;
            case 4:
                this.aaN = 5;
                break;
            case 5:
                this.aaN = 4;
                break;
            case 6:
                this.aaN = 7;
                break;
            case 7:
                this.aaN = 6;
                break;
        }
    }

    public int lO() {
        return ak(true);
    }

    public int ak(boolean z) {
        return k(this.aaP, z);
    }

    public int k(int i, boolean z) {
        GLLib.c(this.aaO != null, "GLLibPlayer.GetNbFrame().sprite is not set");
        if (i >= 0) {
            if (z) {
                return this.aaO.bq(i);
            }
            int i2 = 0;
            for (int i3 = 0; i3 < this.aaO.bq(i); i3++) {
                a aVar = this.aaO;
                i2 += aVar.Hj[aVar.Hg[i] + i3] & 255;
            }
            return i2;
        }
        return -1;
    }

    public final int lP() {
        GLLib.c(this.aaO != null, "GLLibPlayer.GetDuration().sprite is not set");
        if (this.aaP < 0) {
            return 0;
        }
        a aVar = this.aaO;
        return this.aaS * (aVar.Hj[aVar.Hg[this.aaP] + this.aaQ] & 255);
    }

    public boolean lQ() {
        GLLib.c(this.aaO != null, "GLLibPlayer.isAnimOver().sprite is not set");
        if (this.aaP < 0) {
            return true;
        }
        if (this.aaT < 0) {
            return false;
        }
        return this.aaU;
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:55:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:58:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:60:0x00dc  */
    public void lR() {
        boolean z;
        boolean z2;
        GLLib.c(this.aaO != null, "GLLibPlayer.render().sprite is not set");
        if (this.aaP < 0) {
            return;
        }
        if (w.XO || !this.aaU) {
            if (w.Xz) {
                GLLib.be("Sprite paint: anim = " + this.aaP);
            }
            if (!w.Zo && !w.Zl) {
                z2 = false;
            } else if (!w.Zo || this.aaW == -1 || this.aaW == 100) {
                if (w.Zl && this.aaV != -1) {
                    if ((GLLib.TX & 4096) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z) {
                        GLLib.h(12, false);
                        GLLib.l(12, 1, this.aaV);
                        z2 = true;
                    }
                }
                z2 = false;
            } else {
                if ((GLLib.TX & 262144) != 0) {
                    if (w.Zl) {
                        if ((GLLib.TX & 4096) != 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (!z) {
                            GLLib.h(12, false);
                            GLLib.l(12, 1, this.aaV);
                        }
                    }
                    z2 = false;
                } else {
                    GLLib.h(18, false);
                    int i = this.aaW;
                    GLLib.l(18, 1, i);
                    GLLib.l(18, 3, i);
                    GLLib.l(18, 4, i);
                    if (w.Zl && this.aaV != -1) {
                        GLLib.l(18, 2, this.aaV);
                        z2 = true;
                    }
                }
                z2 = true;
            }
            if (w.WE) {
                lS();
            }
            if (this.aaY != -1) {
                int iHI = this.aaO.hI();
                this.aaO.bQ(this.aaY);
                this.aaO.a(GLLib.Qj, this.aaP, this.aaQ, this.aaL, this.aaM, this.aaN);
                this.aaO.bQ(iHI);
            } else {
                this.aaO.a(GLLib.Qj, this.aaP, this.aaQ, this.aaL, this.aaM, this.aaN);
            }
            if ((w.Zo || w.Zl) && z2) {
                GLLib.kZ();
            }
        }
    }

    public void dV(int i) {
        int iLP;
        GLLib.c(i >= 0, "GLLibPlayer.Update.DT is negative");
        if (!this.aaU && this.aaP >= 0 && (iLP = lP()) != 0) {
            if (w.Wy) {
                if (lL() >= iLP) {
                    dS(lL() - iLP);
                    if (this.aaQ < this.aaO.bq(this.aaP) - 1) {
                        this.aaQ++;
                    } else if (this.aaT == 0) {
                        this.aaU = true;
                    } else {
                        if (this.aaT > 0) {
                            this.aaT--;
                        }
                        this.aaQ = 0;
                    }
                }
            } else {
                while (lL() >= iLP) {
                    dS(lL() - iLP);
                    if (this.aaQ < this.aaO.bq(this.aaP) - 1) {
                        this.aaQ++;
                    } else if (this.aaT == 0) {
                        this.aaU = true;
                        break;
                    } else {
                        if (this.aaT > 0) {
                            this.aaT--;
                        }
                        this.aaQ = 0;
                    }
                    iLP = lP();
                    if (iLP == 0) {
                        break;
                    }
                }
            }
            dS(lL() + i);
        }
    }

    private final void lS() {
        GLLib.c(w.WE, "GLLibPlayer.IsFrameMappingEnabled: you should enable sprite_useFrameMapping for using this functionality.");
        if (w.WE && this.aaZ != null) {
            a aVar = this.aaO;
            a[] aVarArr = this.aaZ;
            byte[] bArr = this.aba;
            short[] sArr = this.abb;
            GLLib.c(w.WE, "ASprite.SetFrameMappingData: you should enable sprite_useFrameMapping for using this functionality.");
            if (!w.WE) {
                return;
            }
            aVar.Hu = aVarArr;
            aVar.Hv = bArr;
            aVar.Hw = sArr;
        }
    }

    private static final int dW(int i) {
        int i2;
        if (w.We) {
            GLLib.bH("SndQueue_NormalizeIndex");
        }
        while (true) {
            i2 = i;
            if (i2 < 5) {
                break;
            }
            i = i2 - 5;
        }
        while (i2 < 0) {
            i2 += 5;
        }
        if (w.We) {
            GLLib.bI("SndQueue_NormalizeIndex");
        }
        return i2;
    }

    private static void a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (abk) {
            if (w.We) {
                GLLib.bH("SndQueue_Push_Normal");
            }
            GLLib.c(abw[i] < 5, "SndQueue_Push: Too many sound requests given before Snd_Update!");
            int i9 = abv[i];
            int i10 = abw[i];
            int iDW = dW(i9 + i10);
            for (int i11 = 0; i11 < i10; i11++) {
                if (abu[(dW((iDW - i11) - 1) * 7) + (i * 5 * 7) + 0] == i2) {
                    return;
                }
            }
            int i12 = (iDW * 7) + (i * 5 * 7);
            abu[i12 + 0] = i2;
            abu[i12 + 1] = i3;
            abu[i12 + 2] = i4;
            abu[i12 + 3] = i5;
            abu[i12 + 4] = i6;
            abu[i12 + 5] = i7;
            abu[i12 + 6] = i8;
            int[] iArr = abw;
            iArr[i] = iArr[i] + 1;
            if (w.We) {
                GLLib.bI("SndQueue_Push_Normal");
            }
        }
    }

    private static synchronized void b(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        a(i, i2, i3, i4, i5, i6, i7, i8);
    }

    private static void c(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (w.VZ && w.Wa) {
            b(i, i2, i3, i4, i5, i6, i7, i8);
        } else {
            a(i, i2, i3, i4, i5, i6, i7, i8);
        }
    }

    private static final void j(int i, int i2, int i3, int i4) {
        c(i, i2, -1, -1, -1, -1, i3, i4);
    }

    private static void a(int i, int[] iArr) {
        iArr[7] = abw[i];
        if (abw[i] > 0) {
            if (w.We) {
                GLLib.bH("SndQueue_Pop_Normal");
            }
            System.arraycopy(abu, (abv[i] * 7) + (i * 5 * 7), iArr, 0, 7);
            abv[i] = dW(abv[i] + 1);
            int[] iArr2 = abw;
            iArr2[i] = iArr2[i] - 1;
            if (w.We) {
                GLLib.bI("SndQueue_Pop_Normal");
            }
        }
    }

    private static synchronized void b(int i, int[] iArr) {
        a(i, iArr);
    }

    private static void c(int i, int[] iArr) {
        if (w.VZ && w.Wa) {
            b(i, iArr);
        } else {
            a(i, iArr);
        }
    }

    static final void Z(int i, int i2) {
        if (w.VW && abk) {
            if (w.We) {
                GLLib.bH("Snd_Stop");
            }
            if (i < 0) {
                if (w.Wd) {
                    GLLib.b("dummy sound skipping", "GLLib_snd", 3);
                }
                if (w.We) {
                    GLLib.bI("Snd_Stop");
                    return;
                }
                return;
            }
            if (abG[i]) {
                t(i, -1, 0);
                GLLib.d(abI[i], 0);
                for (int i3 = 0; i3 < abJ[i].length; i3++) {
                    abJ[i][i3] = false;
                }
                abK[i] = 0;
            } else {
                if (!w.Wc) {
                    i2 = 0;
                }
                for (int i4 = 0; i4 < abc; i4++) {
                    if (abo[i4] == i) {
                        abA[i4] = false;
                        j(i4, 1, -1, i2);
                    }
                }
            }
            if (w.We) {
                GLLib.bI("Snd_Stop");
            }
        }
    }

    private static void r(int i, int i2, int i3) throws IOException {
        if (w.VW) {
            if (w.Wd) {
                GLLib.b("Snd_PrepareExec", "GLLib_snd", 3);
            }
            if (w.We) {
                GLLib.bH("Snd_PrepareExec");
            }
            ed(i);
            if (w.Wd) {
                GLLib.b("prepareexec : channel : " + i, "GLLib_snd", 3);
                GLLib.b("prepareexec : index   : " + i2, "GLLib_snd", 3);
            }
            if (!abl[i2]) {
                if (w.Wd) {
                    GLLib.b("create player: sound is not loaded yet!!", "GLLib_snd", 6);
                }
                if (w.We) {
                    GLLib.bI("Snd_PrepareExec");
                    return;
                }
                return;
            }
            abm[i] = new MediaPlayer();
            abm[i].setOnCompletionListener(new ab());
            abm[i].setDataSource(abL.openFileInput(i2 + ".snddmp").getFD());
            abm[i].prepare();
            abq[i] = 1;
            abo[i] = i2;
            if (w.We) {
                GLLib.bI("Snd_PrepareExec");
            }
        }
    }

    private static void d(int i, int i2, int i3, int i4, int i5) throws IOException {
        if (!w.VW || GLLib.Qm) {
            abD[i] = -1;
            return;
        }
        if (w.We) {
            GLLib.bH("Snd_PlayExec");
        }
        if (w.Wd) {
            GLLib.b("Snd_PlayExec for channel : " + i, "GLLib_snd", 3);
        }
        r(i, i2, i3);
        if (GLLib.Qm) {
            if (w.We) {
                GLLib.bI("Snd_PlayExec");
                return;
            }
            return;
        }
        if (abq[i] != 1 || abm[i] == null) {
            if (w.We) {
                GLLib.bI("Snd_PlayExec");
            }
            if (w.Wd) {
                GLLib.b("Snd_playExec : Cant play, state not ready or player is null", "GLLib_snd", 6);
            }
            abD[i] = -1;
            return;
        }
        abm[i].setLooping(i4 == 0);
        abm[i].setVolume(dX(i5), dX(i5));
        if (w.Wd) {
            GLLib.b("Start player", "GLLib_snd", 3);
        }
        abm[i].start();
        abD[i] = -1;
        abq[i] = 2;
        abr[i] = i5;
        abs[i] = i4;
        abp[i] = i3;
        abo[i] = i2;
        abn[i] = System.currentTimeMillis();
        if (w.We) {
            GLLib.bI("Snd_PlayExec");
        }
    }

    private static float dX(int i) {
        if (i < 0) {
            return 0.0f;
        }
        if (i > 100) {
            return 1.0f;
        }
        return i / 100.0f;
    }

    private static float dY(int i) {
        if (i < 50000) {
            return 0.5f;
        }
        if (i > 200000) {
            return 2.0f;
        }
        return i / 100000.0f;
    }

    private static int dZ(int i) {
        if (!w.VW || !abk) {
            return 0;
        }
        if (w.We) {
            GLLib.bH("");
        }
        if (w.We) {
            GLLib.bI("");
        }
        return abr[i];
    }

    private static void aa(int i, int i2) {
        if (w.VW && abk && abm[i] != null) {
            if (w.We) {
                GLLib.bH("Snd_SetChannelVolume");
            }
            try {
                if (abm[i] != null) {
                    abm[i].setVolume(dX(i2), dX(i2));
                    abr[i] = i2;
                }
            } catch (Exception e) {
            }
            if (w.We) {
                GLLib.bI("Snd_SetChannelVolume");
            }
        }
    }

    private static void ea(int i) {
        if (w.Wd) {
            GLLib.b("Snd_stopExec", "GLLib_snd", 3);
        }
        if (abm[i] != null) {
            if (w.We) {
                GLLib.bH("Snd_StopExec");
            }
            abm[i].stop();
            abs[i] = 1;
            abq[i] = 1;
            ed(i);
            abn[i] = 0;
            if (w.We) {
                GLLib.bI("Snd_StopExec");
            }
        }
    }

    private static void eb(int i) {
        if (w.Wd) {
            GLLib.b("Snd_PauseExec", "GLLib_snd", 3);
        }
        GLLib.c(abm[i] != null, "Snd_pauseExec.player is null");
        if (abm[i] != null) {
            if (w.We) {
                GLLib.bH("Snd_PauseExec");
            }
            GLLib.c(abm[i].isPlaying(), "Snd_pauseExec.player wasn't playing");
            abm[i].pause();
            abq[i] = 3;
            if (w.We) {
                GLLib.bI("Snd_PauseExec");
            }
        }
    }

    private static void ec(int i) {
        if (w.Wd) {
            GLLib.b("Snd_ResumeExec", "GLLib_snd", 3);
        }
        if (abq[i] != 3) {
            if (w.Wd) {
                GLLib.b("Snd_ResumeExec. Can't resume channel " + i + " because state is not paused!", "GLLib_snd", 6);
                return;
            }
            return;
        }
        GLLib.c(abm[i] != null, "Snd_ResumeExec.player is null");
        if (abm[i] != null) {
            if (w.We) {
                GLLib.bH("Snd_ResumeExec");
            }
            abm[i].start();
            abq[i] = 2;
            if (w.We) {
                GLLib.bI("Snd_ResumeExec");
            }
        }
    }

    private static void lT() {
        if (w.VW && abk) {
            if (w.VZ) {
                if (!abx) {
                    abx = true;
                } else {
                    return;
                }
            }
            abh = false;
            for (int i = 0; i < abc; i++) {
                try {
                    if (l(i, true)) {
                        abh = true;
                    } else if (abm[i] != null) {
                        ed(i);
                    }
                } catch (Exception e) {
                    GLLib.d(e);
                }
                if (!abA[i] && !abE[i]) {
                    c(i, aby);
                    if (aby[7] > 0) {
                        try {
                            if (w.Wd) {
                                GLLib.b("Snd_update", "GLLib_snd", 3);
                            }
                            switch (aby[0]) {
                                case 0:
                                    abm[i] = null;
                                    int i2 = aby[6] == 0 ? aby[3] : 0;
                                    d(i, aby[1], aby[2], aby[4], i2);
                                    if (i2 == 0 && aby[6] != 0) {
                                        k(2, i, aby[3], aby[6]);
                                    }
                                    break;
                                case 1:
                                    k(3, i, 0, aby[6]);
                                    abE[i] = true;
                                    break;
                                case 2:
                                    k(4, i, 0, aby[6]);
                                    abE[i] = true;
                                    break;
                                case 3:
                                    if (!abE[i]) {
                                        k(5, i, 0, aby[6]);
                                    }
                                    break;
                                case 4:
                                    if (!abE[i]) {
                                        k(7, i, abF[i], aby[6]);
                                    }
                                    break;
                            }
                        } catch (Exception e2) {
                            GLLib.b("Snd_update.error on channel (" + i + ")." + e2, "GLLib_snd", 6);
                            GLLib.d(e2);
                        }
                    }
                }
                ef(i);
            }
            if (w.VZ) {
                if (abf || !abh) {
                    abf = false;
                    synchronized (abt) {
                        try {
                            GLLib.b("Pause thread", "GLPlayer", 6);
                            abt.wait();
                        } catch (InterruptedException e3) {
                            GLLib.d(e3);
                        }
                    }
                }
                abx = false;
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (abt != null) {
            lT();
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean l(int i, boolean z) {
        if (!w.VW || !abk || i >= abc) {
            return false;
        }
        if (abm[i] == null && abD[i] == -1) {
            return false;
        }
        if (w.We) {
            GLLib.bH("Snd_IsChannelPlaying");
        }
        boolean z2 = true;
        if ((abm[i] != null && !abm[i].isPlaying() && abD[i] == -1) || (abE[i] && !z)) {
            z2 = false;
        }
        if (w.We) {
            GLLib.bI("Snd_IsChannelPlaying");
        }
        return z2;
    }

    private static void ed(int i) {
        if (w.VW && abk) {
            if (w.We) {
                GLLib.bH("Snd_FreeChannelExec");
            }
            if (w.Wd) {
                GLLib.b("Snd_FreeChannelExec = " + i, "GLLib_snd", 3);
            }
            if (abm[i] != null) {
                abm[i].stop();
                abm[i].release();
                abm[i] = null;
            }
            abq[i] = 0;
            abo[i] = -1;
            if (w.We) {
                GLLib.bI("Snd_FreeChannelExec");
            }
        }
    }

    static void ee(int i) {
        if (w.VW && abk) {
            if (w.We) {
                GLLib.bH("Snd_StopAllSounds");
            }
            for (int i2 = 0; i2 < abe; i2++) {
                Z(i2, i);
            }
            if (w.We) {
                GLLib.bI("Snd_StopAllSounds");
            }
        }
    }

    private static void k(int i, int i2, int i3, int i4) {
        if (w.We) {
            GLLib.bH("Snd_FadeCommand");
        }
        int i5 = i2 * 4;
        if (!w.Wc) {
            i4 = 0;
        }
        abz[i5 + 0] = i;
        abz[i5 + 1] = i3;
        abz[i5 + 2] = i4;
        abz[i5 + 3] = dZ(i2);
        abB[i2] = System.currentTimeMillis();
        abA[i2] = true;
        if (w.We) {
            GLLib.bI("Snd_FadeCommand");
        }
    }

    private static void s(int i, int i2, int i3) {
        if (abk) {
            if (w.We) {
                GLLib.bH("Snd_FadeVolumeOnChannel");
            }
            if (!w.Wc) {
                i3 = 0;
            }
            k(2, i, i2, i3);
            abF[i] = i2;
            if (w.We) {
                GLLib.bI("Snd_FadeVolumeOnChannel");
            }
        }
    }

    private static void ef(int i) {
        int i2;
        if (w.We) {
            GLLib.bH("SndFade_Update");
        }
        int i3 = i * 4;
        int i4 = abz[i3 + 0];
        if (i4 != 0 && i4 != 1) {
            boolean z = abz[i3 + 3] >= abz[i3 + 1];
            int i5 = abz[i3 + 1];
            int i6 = abz[i3 + 3];
            try {
                dZ(i);
                abC = System.currentTimeMillis();
                int i7 = abz[i3 + 2];
                if (i7 == 0) {
                    i2 = i5;
                } else {
                    i2 = (int) (((long) i6) + ((((long) (i5 - i6)) * (abC - abB[i])) / ((long) i7)));
                    if ((z && i2 < i5) || (!z && i2 > i5)) {
                        abr[i] = i5;
                        i2 = i5;
                    }
                }
                if (w.Wd) {
                    GLLib.b("FADE VOLUME TO " + i2, "GLLib_snd", 3);
                }
            } catch (Exception e) {
                abr[i] = i5;
                i2 = i5;
            }
            aa(i, i2);
            if (i4 == 7) {
                if (abq[i] == 3) {
                    try {
                        ec(i);
                    } catch (Exception e2) {
                        GLLib.d(e2);
                    }
                }
                s(i, abz[i3 + 1], abz[i3 + 2]);
            }
            if ((z && i2 <= i5) || (!z && i2 >= i5)) {
                switch (i4) {
                    case 2:
                        abq[i] = 2;
                        break;
                    case 3:
                    case 4:
                        try {
                            ea(i);
                            abE[i] = false;
                        } catch (Exception e3) {
                            GLLib.d(e3);
                        }
                        break;
                    case 5:
                        try {
                            eb(i);
                        } catch (Exception e4) {
                            GLLib.d(e4);
                        }
                        break;
                }
                k(0, i, 0, 0);
                abA[i] = false;
            }
        }
        if (w.We) {
            GLLib.bI("SndFade_Update");
        }
    }

    static void lU() {
        if (w.VW && abk && w.Wb) {
            if (w.We) {
                GLLib.bH("Snd_PauseNotify");
            }
            try {
                ee(0);
                if (w.VZ && GLLib.Qm) {
                    lT();
                }
            } catch (Exception e) {
                GLLib.b("Snd_PauseNotify: exception!", "GLLib_snd", 6);
                GLLib.d(e);
            }
            if (w.We) {
                GLLib.bI("Snd_PauseNotify");
            }
        }
    }

    static void lV() {
        if (w.Wg) {
            GLLib.b("Snd_ResumeNotify: music is active: " + abM.isMusicActive(), "GLLib_snd", 6);
            if (abM.isMusicActive()) {
                abi = true;
            } else {
                abi = false;
            }
        }
    }

    private static void t(int i, int i2, int i3) {
        if (!abl[i]) {
            if (w.Wd) {
                GLLib.b("Sound " + i + " is not loaded yet!", "GLLib_snd", 6);
                return;
            }
            return;
        }
        for (int i4 = 0; i4 < abI[i].length; i4++) {
            if (abI[i][i4] != 0) {
                switch (i3) {
                    case 0:
                        if (w.Wd) {
                            GLLib.b("Stopping stream " + abI[i][i4], "GLLib_snd", 6);
                        }
                        abH.stop(abI[i][i4]);
                        break;
                    case 1:
                        abH.pause(abI[i][i4]);
                        break;
                    case 2:
                        abH.resume(abI[i][i4]);
                        break;
                    case 3:
                        abH.setRate(abI[i][i4], dY(i2));
                        break;
                }
            }
        }
    }

    public static void l(int i, int i2, int i3, int i4) {
        abX = new cb[abO][][];
        abS = new int[8];
        abT = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, abO, 20);
        if (i3 > 0 && i4 > 0) {
            abU = (byte[][][]) Array.newInstance((Class<?>) byte[].class, abO, 2);
            abV = new a[abO];
            if (w.Wo) {
                int iCP = GLLib.cP(i3);
                int iCP2 = GLLib.cP(i4);
                abS[3] = iCP;
                abS[2] = 1 << iCP;
                abS[4] = abS[2] - 1;
                abS[6] = iCP2;
                abS[5] = 1 << iCP2;
                abS[7] = abS[5] - 1;
            } else {
                abS[2] = i3;
                abS[4] = 0;
                abS[5] = i4;
                abS[7] = 0;
            }
        }
        abS[0] = i;
        abS[1] = i2;
        abN = true;
    }

    private static boolean ab(int i, int i2) {
        return (abT[i][13] & i2) != 0;
    }

    private static void d(int i, int i2, boolean z) {
        if (z) {
            int[] iArr = abT[i];
            iArr[13] = iArr[13] | i2;
        } else {
            int[] iArr2 = abT[i];
            iArr2[13] = iArr2[13] & (i2 ^ (-1));
        }
    }

    public static void a(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, a aVar, int i2, int i3, int i4, int i5) {
        a(i, bArr, bArr2, bArr3, aVar, i2, i3, i4, i5, false);
    }

    public static void a(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, a aVar, int i2, int i3, int i4, int i5, boolean z) {
        a(i, bArr, bArr2, bArr3, aVar, i2, i3, i4, i5, z, 0, 0);
    }

    public static void a(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, a aVar, int i2, int i3, int i4, int i5, boolean z, int i6, int i7) {
        GLLib.c(bArr != null, "GLLibPlayer.Tileset_LoadLayer.MapSizes is null");
        GLLib.c(i4 == 0 || i4 == 1 || i4 == 2, "GLLibPlayer.Tileset_LoadLayer. X wrapping is not valid");
        GLLib.c(i5 == 0 || i5 == 1 || i5 == 2, "GLLibPlayer.Tileset_LoadLayer. Y wrapping is not valid");
        GLLib.c(i3 == 16 || i3 == 32, "GLLibPlayer.Tileset_LoadLayer. origin is not valid");
        if (abN) {
            eg(i);
            abT[i][16] = i6;
            abT[i][17] = i7;
            if (i6 == 0) {
                abT[i][16] = abS[0];
            }
            if (i7 == 0) {
                abT[i][17] = abS[1];
            }
            if (w.Ww && bArr3 != null) {
                int length = bArr3.length >> 1;
                int length2 = bArr3.length & 1;
                byte[] bArr4 = new byte[(length2 == 0 ? 0 : 1) + length];
                int length3 = (bArr3.length - length2) - 1;
                int i8 = length;
                while (true) {
                    int i9 = i8 - 1;
                    if (i8 == 0) {
                        break;
                    }
                    int i10 = length3 - 1;
                    int i11 = (bArr3[length3] & 15) << 4;
                    length3 = i10 - 1;
                    bArr4[i9] = (byte) ((bArr3[i10] & 15) | i11);
                    i8 = i9;
                }
                if (length2 != 0) {
                    bArr4[length] = (byte) (bArr3[bArr3.length - 1] & 15);
                }
                bArr3 = bArr4;
            } else if (w.Wv && bArr3 != null) {
                int length4 = bArr3.length >> 2;
                int length5 = bArr3.length - (length4 << 2);
                byte[] bArr5 = new byte[(length5 == 0 ? 0 : 1) + length4];
                int length6 = (bArr3.length - length5) - 1;
                int i12 = length4;
                while (true) {
                    int i13 = i12 - 1;
                    if (i12 == 0) {
                        break;
                    }
                    int i14 = length6 - 1;
                    int i15 = i14 - 1;
                    int i16 = ((bArr3[length6] & 3) << 6) | ((bArr3[i14] & 3) << 4);
                    int i17 = i15 - 1;
                    int i18 = ((bArr3[i15] & 3) << 2) | i16;
                    length6 = i17 - 1;
                    bArr5[i13] = (byte) ((bArr3[i17] & 3) | i18);
                    i12 = i13;
                }
                int length7 = bArr3.length - 1;
                while (length5 != 0) {
                    bArr5[length4] = (byte) (bArr5[length4] | ((bArr3[length7] & 3) << ((length5 - 1) << 1)));
                    length5--;
                    length7--;
                }
                bArr3 = bArr5;
            }
            abU[i][0] = bArr2;
            abU[i][1] = bArr3;
            abT[i][2] = GLLib.k(bArr, 0);
            abT[i][3] = GLLib.k(bArr, 2);
            abT[i][5] = abT[i][2] * abS[2];
            abT[i][6] = abT[i][3] * abS[5];
            abV[i] = aVar;
            abT[i][7] = -1;
            abT[i][8] = -1;
            abT[i][9] = -1;
            abT[i][10] = -1;
            abT[i][0] = 1;
            abT[i][1] = 1;
            abT[i][11] = 0;
            abT[i][12] = 0;
            abT[i][14] = 0;
            abT[i][15] = 0;
            d(i, 1, i4 == 1);
            d(i, 16, i4 == 0);
            d(i, 2, i5 == 1);
            d(i, 32, i5 == 0);
            d(i, 8, i3 == 32);
        }
    }

    public static void eg(int i) {
        if (abN) {
            if (ab(i, AdRequest.MAX_CONTENT_URL_LENGTH)) {
                ej(i);
            }
            abT[i] = new int[20];
            if (abU != null) {
                abU[i] = new byte[2][];
                abV[i] = null;
            }
        }
    }

    public static void a(aj ajVar, int i, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        if (abN) {
            int i10 = abS[0];
            int i11 = abS[1];
            if (i3 == -1) {
                for (int i12 = 0; i12 < abO; i12++) {
                    if (!w.Wr) {
                        a(ajVar, i, i2, i12);
                    } else if (i12 != abQ) {
                        a(ajVar, i, i2, i12);
                    }
                }
                return;
            }
            if (ajVar != null && ab(i3, AdRequest.MAX_CONTENT_URL_LENGTH)) {
                a(i, i2, i3);
                return;
            }
            int i13 = (abT[i3][16] * 100) / abW;
            int i14 = (abT[i3][17] * 100) / abW;
            if (i13 == 0) {
                abT[i3][16] = abS[0];
                i13 = abT[i3][16];
            }
            if (i14 == 0) {
                abT[i3][17] = abS[1];
                i13 = abT[i3][16];
            }
            int[] iArr = abT[i3];
            if (iArr[0] == 1 && iArr[1] == 1) {
                int i15 = iArr[11] + iArr[14];
                int i16 = iArr[15] + iArr[12];
                if (w.Wo) {
                    int i17 = i15 >> abS[3];
                    i4 = i16 >> abS[6];
                    int i18 = i13 >> abS[3];
                    if ((i18 << abS[3]) < i13) {
                        i18++;
                    }
                    int i19 = i14 >> abS[6];
                    if ((i19 << abS[6]) < i14) {
                        i19++;
                    }
                    i5 = (i17 << abS[3]) - i15;
                    i6 = (i4 << abS[6]) - i16;
                    i7 = i19;
                    i8 = i18;
                    i9 = i17;
                } else {
                    int i20 = i15 < 0 ? i15 - abS[2] : i15;
                    int i21 = i16 < 0 ? i16 - abS[5] : i16;
                    int i22 = i20 / abS[2];
                    i4 = i21 / abS[5];
                    int i23 = i13 / abS[2];
                    if (abS[2] * i23 < i13) {
                        i23++;
                    }
                    int i24 = i14 / abS[5];
                    if (abS[5] * i24 < i14) {
                        i24++;
                    }
                    i5 = (abS[2] * i22) - i15;
                    i6 = (abS[5] * i4) - i16;
                    i7 = i24;
                    i8 = i23;
                    i9 = i22;
                }
                a(ajVar, i3, i9, i4, i8, i7, i5 + i, i6 + i2);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:101:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:104:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:105:0x01b4  */
    /* JADX WARN: Code duplicated, block: B:106:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:108:0x01c4  */
    /* JADX WARN: Code duplicated, block: B:109:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:116:0x01ea  */
    /* JADX WARN: Code duplicated, block: B:119:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:122:0x0214  */
    /* JADX WARN: Code duplicated, block: B:123:0x0217  */
    /* JADX WARN: Code duplicated, block: B:124:0x0219  */
    /* JADX WARN: Code duplicated, block: B:125:0x021d  */
    /* JADX WARN: Code duplicated, block: B:132:0x01f2 A[EDGE_INSN: B:132:0x01f2->B:117:0x01f2 BREAK  A[LOOP:4: B:37:0x00ad->B:136:0x00ad], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:133:0x020a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:134:0x01f2 A[EDGE_INSN: B:134:0x01f2->B:117:0x01f2 BREAK  A[LOOP:4: B:37:0x00ad->B:136:0x00ad], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:135:0x01e8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:140:0x01de A[EDGE_INSN: B:140:0x01de->B:113:0x01de BREAK  A[LOOP:5: B:40:0x00b5->B:144:0x00b5], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:141:0x0210 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:142:0x01de A[EDGE_INSN: B:142:0x01de->B:113:0x01de BREAK  A[LOOP:5: B:40:0x00b5->B:144:0x00b5], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:143:0x0159 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:148:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:21:0x005e A[ADDED_TO_REGION, LOOP:2: B:21:0x005e->B:22:0x0060, LOOP_START, PHI: r29
  0x005e: PHI (r29v8 int) = (r29v0 int), (r29v11 int) binds: [B:20:0x005c, B:22:0x0060] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:22:0x0060 A[LOOP:2: B:21:0x005e->B:22:0x0060, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:25:0x0069 A[LOOP:3: B:23:0x0063->B:25:0x0069, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:26:0x006c  */
    /* JADX WARN: Code duplicated, block: B:28:0x0076 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:29:0x0078  */
    /* JADX WARN: Code duplicated, block: B:32:0x0086  */
    /* JADX WARN: Code duplicated, block: B:36:0x0093  */
    /* JADX WARN: Code duplicated, block: B:39:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:42:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:44:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:46:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:50:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:52:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:61:0x0113  */
    /* JADX WARN: Code duplicated, block: B:63:0x0122  */
    /* JADX WARN: Code duplicated, block: B:65:0x0125  */
    /* JADX WARN: Code duplicated, block: B:67:0x0132  */
    /* JADX WARN: Code duplicated, block: B:79:0x015b  */
    /* JADX WARN: Code duplicated, block: B:80:0x0160  */
    /* JADX WARN: Code duplicated, block: B:81:0x0163  */
    /* JADX WARN: Code duplicated, block: B:83:0x0170  */
    /* JADX WARN: Code duplicated, block: B:84:0x0173  */
    /* JADX WARN: Code duplicated, block: B:85:0x0176  */
    /* JADX WARN: Code duplicated, block: B:87:0x017a  */
    /* JADX WARN: Code duplicated, block: B:93:0x0193  */
    /* JADX WARN: Code duplicated, block: B:95:0x0197  */
    /* JADX WARN: Code duplicated, block: B:96:0x019d  */
    /* JADX WARN: Code duplicated, block: B:98:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:99:0x01a6  */
    // Recovered from original DEX register joins at cond_58/cond_225 and draw-loop branches.
    private static void a(aj ajVar, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        boolean wrapX = ab(i, 1);
        boolean wrapY = ab(i, 2);
        int mapWidth = abT[i][2];
        int mapHeight = abT[i][3];
        int tileWidth = abS[2];
        int tileHeight = abS[5];
        int tileCount = mapWidth * mapHeight;
        int firstColumn;
        if (wrapX) {
            firstColumn = i2;
            while (firstColumn < 0) {
                firstColumn += mapWidth;
            }
            while (firstColumn >= mapWidth) {
                firstColumn -= mapWidth;
            }
        } else if (ab(i, 16)) {
            firstColumn = i2;
        } else {
            if (i2 < 0) {
                i6 -= i2 * tileWidth;
                i4 += i2;
                firstColumn = 0;
            } else {
                firstColumn = i2;
            }
            if (firstColumn + i4 >= mapWidth) {
                i4 = (mapWidth - firstColumn) - 1;
            }
            if (i4 < 0) {
                return;
            }
        }
        // Both original horizontal paths join with v16=p6 and v14=p4.
        int firstX = i6;
        int columns = i4;
        if (wrapY) {
            while (i3 < 0) {
                i3 += mapHeight;
            }
            while (i3 >= mapHeight) {
                i3 -= mapHeight;
            }
        } else if (!ab(i, 32)) {
            if (i3 < 0) {
                i7 -= i3 * tileHeight;
                i5 += i3;
                i3 = 0;
            }
            if (i3 + i5 >= mapHeight) {
                i5 = (mapHeight - i3) - 1;
            }
            if (i5 < 0) {
                return;
            }
        }
        if (abW != 100) {
            GLLib.h(18, false);
            GLLib.l(18, 1, abW);
            GLLib.l(18, 5, 1);
        }
        while (i5-- >= 0) {
            int column = firstColumn;
            int remainingColumns = columns;
            int x = firstX;
            while (remainingColumns-- >= 0) {
                int index = column + (i3 * mapWidth);
                if (index < tileCount) {
                    int tile = w.Wq ? a(i, 0, index, true) : w(i, 0, index);
                    if (tile != (w.Wq ? 65535 : 255)) {
                        int flags = w(i, 1, index);
                        if (abV[i].hv() == 0) {
                            abV[i].a(ajVar, tile, (float) x, (float) i7, flags, abS[2], abS[5]);
                        } else {
                            int drawX;
                            int drawY;
                            if ((flags & 4) != 0) {
                                if ((flags & 1) != 0 && (flags & 2) != 0) {
                                    drawX = x;
                                    drawY = i7 + tileWidth;
                                } else if ((flags & 1) != 0) {
                                    drawX = x + tileHeight;
                                    drawY = i7 + tileWidth;
                                } else if ((flags & 2) == 0) {
                                    drawX = x + tileHeight;
                                    drawY = i7;
                                } else {
                                    drawX = x;
                                    drawY = i7;
                                }
                            } else {
                                drawX = (flags & 1) != 0 ? x + tileWidth : x;
                                drawY = (flags & 2) != 0 ? i7 + tileHeight : i7;
                            }
                            abV[i].a(ajVar, tile, (float) drawX, (float) drawY, flags);
                        }
                        if (w.Wr && i == abP && abQ > 0) {
                            int overlay = w.Wq ? a(abQ, 0, index, true) : w(abQ, 0, index);
                            if (overlay != (w.Wq ? 65535 : 255)) {
                                if (abV[abQ].hv() != 0) {
                                    overlay = abV[abQ].t(overlay, 0);
                                }
                                if (abR == 1) {
                                    abV[abQ].a(ajVar, overlay, x, i7, 0);
                                }
                            }
                        }
                    }
                }
                x += tileWidth;
                column++;
                if (column >= mapWidth) {
                    if (!wrapX) {
                        break;
                    }
                    column = 0;
                }
            }
            i7 += tileHeight;
            i3++;
            if (i3 >= mapHeight) {
                if (!wrapY) {
                    break;
                }
                i3 = 0;
            }
        }
        if (abW != 100) {
            GLLib.l(18, 5, 0);
            GLLib.i(18, false);
        }
    }

    private static final int ac(int i, int i2) {
        if (ab(i, 8)) {
            return (abT[i][6] - abS[1]) - i2;
        }
        return i2;
    }

    public static final void u(int i, int i2, int i3) {
        abT[i][11] = i2;
        abT[i][12] = ac(i, i3);
        if (ab(i, 16)) {
            if (abT[i][11] < 0) {
                abT[i][11] = 0;
            } else if (abT[i][11] + abS[0] >= abT[i][5]) {
                abT[i][11] = (abT[i][5] - abS[0]) - 1;
            }
        }
        if (ab(i, 32)) {
            if (abT[i][12] < 0) {
                abT[i][12] = 0;
            } else if (abT[i][12] + abS[1] >= abT[i][6]) {
                abT[i][12] = (abT[i][6] - abS[1]) - 1;
            }
        }
    }

    public static final int eh(int i) {
        return ab(i, 8) ? (abT[i][6] - abS[1]) - abT[i][12] : abT[i][12];
    }

    public static final int v(int i, int i2, int i3) {
        return n(i, 0, i2, ac(i, i3));
    }

    public static void ei(int i) {
        if (abN) {
            abT[i][7] = -1;
            abT[i][8] = -1;
            abT[i][9] = -1;
            abT[i][10] = -1;
        }
    }

    public static void e(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int iAc = ac(i, i3);
        if (w.Wo) {
            i6 = i2 >> abS[3];
            i7 = iAc >> abS[6];
        } else {
            i6 = i2 / abS[2];
            i7 = iAc / abS[5];
        }
        int i8 = (i7 * abT[i][2]) + i6;
        if (i4 != -1) {
            if (w.Wq) {
                a(i, 0, i8, i4, true);
            } else {
                m(i, 0, i8, i4);
            }
        }
        if (i5 != -1) {
            m(i, 1, i8, i5);
        }
    }

    private static final void m(int i, int i2, int i3, int i4) {
        a(i, i2, i3, i4, false);
    }

    private static void a(int i, int i2, int i3, int i4, boolean z) {
        if (z) {
            GLLib.a(abU[i][i2], i3 << 1, (short) i4);
        } else {
            abU[i][i2][i3] = (byte) i4;
        }
    }

    private static final int w(int i, int i2, int i3) {
        return a(i, i2, i3, false);
    }

    private static int a(int i, int i2, int i3, boolean z) {
        if (z) {
            return GLLib.k(abU[i][i2], i3 << 1) & 65535;
        }
        if (w.Ww && i2 == 1) {
            return (abU[i][i2][i3 >> 1] >> ((i3 & 1) << 2)) & 15;
        }
        if (w.Wv && i2 == 1) {
            return (abU[i][i2][i3 >> 2] >> ((i3 & 3) << 1)) & 3;
        }
        return abU[i][i2][i3] & 255;
    }

    private static final int n(int i, int i2, int i3, int i4) {
        return ((w.Wv || w.Ww) && i2 == 1) ? b(i, i2, i3, i4, false) : b(i, i2, i3, i4, w.Wq);
    }

    private static int b(int i, int i2, int i3, int i4, boolean z) {
        return a(i, i2, (abT[i][2] * i4) + i3, z);
    }

    private static void ej(int i) {
        if (abX[i] != null) {
            for (int i2 = 0; i2 < abX[i].length; i2++) {
                for (int i3 = 0; i3 < abX[i][i2].length; i3++) {
                    abX[i][i2][i3].Destroy();
                }
            }
            abX[i] = (cb[][]) null;
        }
    }

    private static void ek(int i) {
        ay ayVarBM = abV[i].bM(abV[i].hI());
        for (int i2 = 0; i2 < abX[i].length; i2++) {
            for (int i3 = 0; i3 < abX[i][i2].length; i3++) {
                abX[i][i2][i3].b(ayVarBM);
            }
        }
    }

    private static void a(float f, float f2, int i) {
        int iMin;
        int iMin2;
        int i2;
        GLLib.kC();
        GLLib.a(GLLib.Qj, 2, 3);
        GLLib.m2do(0);
        int[] iArr = abT[i];
        if (iArr[0] == 1 && iArr[1] == 1) {
            ek(i);
            boolean zAb = ab(i, 1);
            boolean zAb2 = ab(i, 2);
            float f3 = (iArr[11] + iArr[14]) - f;
            float f4 = (iArr[15] + iArr[12]) - f2;
            int iKH = (GLLib.kH() * 100) / abW;
            int iKI = (GLLib.kI() * 100) / abW;
            int i3 = abS[2];
            int i4 = abS[5];
            int i5 = abT[i][2] * i3;
            int i6 = abT[i][3] * i4;
            int i7 = i3 * abT[i][18];
            int i8 = abT[i][19] * i4;
            int iAd = ad((int) f3, i7);
            int iAd2 = ad((int) f4, i8);
            int i9 = (((int) f3) + iKH) / i7;
            int i10 = (((int) f4) + iKI) / i8;
            if (zAb) {
                iMin = i9;
            } else {
                iAd = Math.max(iAd, 0);
                iMin = Math.min(abX[i].length - 1, i9);
            }
            if (zAb2) {
                iMin2 = i10;
                i2 = iAd2;
            } else {
                int iMax = Math.max(iAd2, 0);
                iMin2 = Math.min(abX[i][0].length - 1, i10);
                i2 = iMax;
            }
            float f5 = 3.402823E38f;
            float[] fArrA = null;
            int i11 = iAd;
            float f6 = 3.402823E38f;
            while (i11 <= iMin) {
                int i12 = i2;
                float f7 = f6;
                while (i12 <= iMin2) {
                    int length = ((i11 % abX[i].length) + abX[i].length) % abX[i].length;
                    int length2 = ((i12 % abX[i][0].length) + abX[i][0].length) % abX[i][0].length;
                    float fAd = f3 - (ad(i11, abX[i].length) * i5);
                    float fAd2 = f4 - (ad(i12, abX[i][0].length) * i6);
                    if (fAd == f5 && fAd2 == f7) {
                        fAd2 = f7;
                        fAd = f5;
                    } else {
                        fArrA = GLLib.a(fAd, iKH + fAd, iKI + fAd2, fAd2, GLLib.ST, GLLib.SU);
                    }
                    GLLib.a(abX[i][length][length2], fArrA);
                    i12++;
                    f7 = fAd2;
                    f5 = fAd;
                }
                i11++;
                f6 = f7;
            }
        }
    }

    private static int ad(int i, int i2) {
        return (i % i2 >= 0 ? 0 : -1) + (i / i2);
    }
}
