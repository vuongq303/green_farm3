package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import com.facebook.appevents.codeless.internal.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class ac implements Runnable {
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
    private static boolean abY;
    private static ac abZ;
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
    private static byte[] aca;
    public static boolean acb;
    private static int[] acc;
    private static int[] acd;
    private static int ace;
    public int aaL;
    public int aaM;
    protected int aaN;
    public a aaO;
    public int aaP;
    public int aaQ;
    protected int aaR;
    public int aaT;
    protected boolean aaU;
    public int aaV;
    public int aaW;
    public int aaY;
    protected static final int aaX = Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS / w.Wx;
    private static final int abc = w.VX;
    private static final int abd = w.VY;
    private static boolean abg = w.VW;
    private static boolean abh = true;
    private static final int abj = w.Wf;
    private static Context abL = GLLib.Qu.getApplicationContext();
    private static AudioManager abM = (AudioManager) abL.getSystemService("audio");
    private static boolean abN = false;
    private static int abO = w.Wp;
    private static int abP = -1;
    private static int abQ = -1;
    private static int abR = 0;
    public static int abW = 100;

    public ac() {
        ip();
    }

    static final void Z(int i, int i2) {
        if (w.VW && abk) {
            if (w.We) {
            }
            if (i < 0) {
                if (w.Wd) {
                    GLLib.b("dummy sound skipping", "GLLib_snd", 3);
                }
                if (w.We) {
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
            }
        }
    }

    static int a(int i, int i2, int i3, int i4, boolean z, int i5) {
        int iLW;
        boolean[] zArr;
        int i6;
        if (!w.VW || !abk || !abg) {
            return -1;
        }
        if (w.We) {
        }
        if (i < 0) {
            if (w.Wd) {
                GLLib.b("dummy sound skipping", "GLLib_snd", 3);
            }
            if (w.We) {
            }
            return -1;
        }
        if (abG[i]) {
            if (w.Wd) {
                GLLib.b("Use SoundPool", "GLLib_snd", 3);
            }
            if (!abl[i]) {
                if (!w.Wd) {
                    return -1;
                }
                GLLib.b("Sound " + i + " is not loaded yet!", "GLLib_snd", 6);
                return -1;
            }
            if (w.Wd) {
                GLLib.b("Play SoundPool for sound " + acd[i], "GLLib_snd", 6);
                GLLib.b("Volume " + dX(i3), "GLLib_snd", 6);
                GLLib.b("Priority " + (15 - i4), "GLLib_snd", 6);
                GLLib.b("Loop " + (i2 == 0 ? -1 : 0), "GLLib_snd", 6);
                GLLib.b("Rate " + dY(acc[i]), "GLLib_snd", 6);
            }
            int iPlay = abH.play(acd[i], dX(i3), dX(i3), 15 - i4, i2 == 0 ? -1 : 0, dY(acc[i]));
            if (w.Wd) {
                GLLib.b("StreamID " + iPlay, "GLLib_snd", 6);
            }
            do {
                zArr = abJ[i];
                int[] iArr = abK;
                i6 = iArr[i] + 1;
                iArr[i] = i6;
            } while (zArr[i6 % 20]);
            abI[i][abK[i] % 20] = iPlay;
            if (i2 == 0) {
                abJ[i][abK[i] % 20] = true;
            }
            if (w.We) {
            }
            return -1;
        }
        if (abi) {
            return -1;
        }
        if (w.VW) {
            if (w.Wd) {
                GLLib.b("Use MediaPlayer", "GLLib_snd", 3);
            }
            iLW = lW();
            if (iLW == -1 && (iLW = eo(i4)) == -1) {
                if (!z) {
                    if (w.We) {
                    }
                    return -1;
                }
                iLW = ep(i4);
                if (iLW == -1) {
                    if (w.We) {
                    }
                    return -1;
                }
            }
            int i7 = !w.Wc ? 0 : i5;
            if (w.Wd) {
                GLLib.b("Snd_Play: Channel[" + iLW + "], ID[" + i + "], Loop[" + i2 + "], Volume[" + i3 + "] and Priority[" + i4 + "]", "GLLib_snd", 3);
            }
            abD[iLW] = i;
            aca[iLW] = (byte) i4;
            abA[iLW] = false;
            if (abm[iLW] != null && abm[iLW].isPlaying() && !abE[iLW]) {
                j(iLW, 2, -1, abj);
            }
            if (abE[iLW]) {
                try {
                    k(4, iLW, dZ(iLW), abj);
                } catch (Exception e) {
                    GLLib.d(e);
                }
            }
            c(iLW, 0, i, i4, i3, i2, -1, i7);
            abF[iLW] = i3;
            if (abg && !abh) {
                lY();
                abh = true;
            }
        } else {
            iLW = -1;
        }
        if (w.We) {
        }
        return iLW;
    }

    private static void a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (abk) {
            if (w.We) {
            }
            GLLib.c(abw[i] < 5, "SndQueue_Push: Too many sound requests given before Snd_Update!");
            int i9 = abv[i];
            int i10 = abw[i];
            int i11 = i9 + i10;
            if (w.We) {
            }
            while (i11 >= 5) {
                i11 -= 5;
            }
            int i12 = i11;
            while (i12 < 0) {
                i12 += 5;
            }
            if (w.We) {
            }
            for (int i13 = 0; i13 < i10; i13++) {
                int i14 = (i12 - i13) - 1;
                if (w.We) {
                }
                while (i14 >= 5) {
                    i14 -= 5;
                }
                while (i14 < 0) {
                    i14 += 5;
                }
                if (w.We) {
                }
                if (abu[(i14 * 7) + (i * 5 * 7) + 0] == i2) {
                    return;
                }
            }
            int i15 = (i * 5 * 7) + (i12 * 7);
            abu[i15 + 0] = i2;
            abu[i15 + 1] = i3;
            abu[i15 + 2] = i4;
            abu[i15 + 3] = i5;
            abu[i15 + 4] = i6;
            abu[i15 + 5] = i7;
            abu[i15 + 6] = i8;
            int[] iArr = abw;
            iArr[i] = iArr[i] + 1;
            if (w.We) {
            }
        }
    }

    private static void a(int i, int[] iArr) {
        iArr[7] = abw[i];
        if (abw[i] > 0) {
            if (w.We) {
            }
            System.arraycopy(abu, (abv[i] * 7) + (i * 5 * 7), iArr, 0, 7);
            int[] iArr2 = abv;
            int i2 = abv[i] + 1;
            if (w.We) {
            }
            while (i2 >= 5) {
                i2 -= 5;
            }
            while (i2 < 0) {
                i2 += 5;
            }
            if (w.We) {
            }
            iArr2[i] = i2;
            int[] iArr3 = abw;
            iArr3[i] = iArr3[i] - 1;
            if (w.We) {
            }
        }
    }

    static void a(byte[] bArr, int i, boolean z) throws IOException {
        if (w.VW && abk) {
            if (bArr == null || bArr.length <= 0) {
                GLLib.b("Snd_LoadSound: soundData is null. Sound " + i + " not available.", "GLLib_snd", 6);
                return;
            }
            if (w.We) {
            }
            if (i < 0) {
                if (w.Wd) {
                    GLLib.b("dummy sound skipping", "GLLib_snd", 3);
                }
                if (w.We) {
                }
                return;
            }
            if (w.Wd) {
                GLLib.b("Snd_LoadSound for slot : " + i, "GLLib_snd", 3);
            }
            if (!new File(abL.getFilesDir(), i + ".snddmp").exists()) {
                FileOutputStream fileOutputStreamOpenFileOutput = abL.openFileOutput(i + ".snddmp", 0);
                fileOutputStreamOpenFileOutput.write(bArr);
                fileOutputStreamOpenFileOutput.flush();
                fileOutputStreamOpenFileOutput.close();
                if (w.Wd) {
                    GLLib.b("Create file : " + i + ".snddmp", "GLLib_snd", 3);
                    GLLib.b("Size : " + bArr.length, "GLLib_snd", 3);
                }
            }
            if (z) {
                abG[i] = z;
                acd[i] = abH.load(abL.getFilesDir() + "/" + i + ".snddmp", 1);
                if (w.Wd) {
                    ace += bArr.length;
                    GLLib.b("Total size in SoundPool : " + ace, "GLLib_snd", 3);
                    GLLib.b("Loading sound : " + i + " to SoundPool. Given soundID is " + acd[i], "GLLib_snd", 3);
                }
            } else {
                abl[i] = true;
            }
            if (w.We) {
            }
        }
    }

    private static void aa(int i, int i2) {
        if (w.VW && abk && abm[i] != null) {
            if (w.We) {
            }
            try {
                if (abm[i] != null) {
                    abm[i].setVolume(dX(i2), dX(i2));
                    abr[i] = i2;
                }
            } catch (Exception e) {
            }
            if (w.We) {
            }
        }
    }

    private static int ae(int i, int i2) {
        if (w.We) {
        }
        if (w.We) {
        }
        if (i == -1) {
            return i2;
        }
        return (i2 != -1 && abn[i] > abn[i2]) ? i2 : i;
    }

    private static synchronized void b(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        a(i, i2, i3, i4, i5, i6, i7, i8);
    }

    private static synchronized void b(int i, int[] iArr) {
        a(i, iArr);
    }

    static int c(int i, int i2, int i3, int i4, boolean z) {
        return a(i, i2, i3, i4, z, 0);
    }

    private static void c(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (w.VZ && w.Wa) {
            b(i, i2, i3, i4, i5, i6, i7, i8);
        } else {
            a(i, i2, i3, i4, i5, i6, i7, i8);
        }
    }

    private static void c(int i, int[] iArr) {
        if (w.VZ && w.Wa) {
            b(i, iArr);
        } else {
            a(i, iArr);
        }
    }

    static void c(String str, int i, boolean z) throws IOException {
        if (w.VW && abk) {
            GLLib.c(i < abl.length, "resourceIndex is larger that slot count. Read the Warning in the doc.");
            if (w.Wd) {
                GLLib.b("Snd_LoadSound : " + i, "GLLib_snd", 3);
            }
            if (i >= 0) {
                GLLib.c(abl != null, "Snd_LoadSound.snd not correctly initialized");
                GLLib.bk(str);
                a(GLLib.db(i), i, z);
            }
        }
    }

    private static void d(int i, int i2, int i3, int i4, int i5) throws IOException {
        if (!w.VW || GLLib.Qm) {
            abD[i] = -1;
            return;
        }
        if (w.We) {
        }
        if (w.Wd) {
            GLLib.b("Snd_PlayExec for channel : " + i, "GLLib_snd", 3);
        }
        r(i, i2, i3);
        if (GLLib.Qm) {
            if (w.We) {
            }
            return;
        }
        if (abq[i] != 1 || abm[i] == null) {
            if (w.We) {
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
        }
        if (w.We) {
        }
        return abr[i];
    }

    private static void ea(int i) {
        if (w.Wd) {
            GLLib.b("Snd_stopExec", "GLLib_snd", 3);
        }
        if (abm[i] == null) {
            return;
        }
        if (w.We) {
        }
        abm[i].stop();
        abs[i] = 1;
        abq[i] = 1;
        ed(i);
        abn[i] = 0;
        if (w.We) {
        }
    }

    private static void eb(int i) {
        if (w.Wd) {
            GLLib.b("Snd_PauseExec", "GLLib_snd", 3);
        }
        GLLib.c(abm[i] != null, "Snd_pauseExec.player is null");
        if (abm[i] == null) {
            return;
        }
        if (w.We) {
        }
        GLLib.c(abm[i].isPlaying(), "Snd_pauseExec.player wasn't playing");
        abm[i].pause();
        abq[i] = 3;
        if (w.We) {
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
            }
            abm[i].start();
            abq[i] = 2;
            if (w.We) {
            }
        }
    }

    private static void ed(int i) {
        if (w.VW && abk) {
            if (w.We) {
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
            }
        }
    }

    static void ee(int i) {
        if (w.VW && abk) {
            if (w.We) {
            }
            for (int i2 = 0; i2 < abe; i2++) {
                Z(i2, i);
            }
            if (w.We) {
            }
        }
    }

    private static void ef(int i) {
        int i2;
        if (w.We) {
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
        }
    }

    static void el(int i) {
        if (!w.VW || abY) {
            return;
        }
        if (w.We) {
        }
        if (w.Wd) {
            GLLib.b("Snd_Init", "GLLib_snd", 3);
        }
        abe = i;
        abm = new MediaPlayer[abc];
        abF = new int[abc];
        abo = new int[abc];
        abp = new int[abc];
        abq = new int[abc];
        abr = new int[abc];
        abs = new int[abc];
        GLLib.d(abs, 1);
        abu = new int[abc * 5 * 7];
        abv = new int[abc];
        abw = new int[abc];
        aca = new byte[abc];
        aby = new int[8];
        abz = new int[abc * 4];
        abA = new boolean[abc];
        abB = new long[abc];
        abE = new boolean[abc];
        abD = new int[abc];
        abG = new boolean[i];
        acc = new int[i];
        GLLib.d(acc, 100000);
        acd = new int[i];
        abH = new SoundPool(abd, 3, 100);
        abH.setOnLoadCompleteListener(new ad());
        abI = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, abe, 20);
        abJ = (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, abe, 20);
        abK = new int[abe];
        GLLib.d(abD, -1);
        for (int i2 = 0; i2 < abc; i2++) {
            abz[(i2 * 4) + 0] = 0;
        }
        for (int i3 = 0; i3 < abc; i3++) {
            abo[i3] = -1;
            abv[i3] = 0;
            abw[i3] = 0;
        }
        abl = new boolean[abe];
        abn = new long[abc];
        abk = true;
        if (w.VZ && abt == null) {
            abZ = new ac();
            abt = new Thread(abZ, "SoundThread");
            abt.start();
        }
        abY = true;
        abh = false;
        if (w.We) {
        }
    }

    static boolean em(int i) {
        if (abl != null) {
            return abl[i];
        }
        GLLib.b("Sound engine is not initialized!!!", "GLLib_snd", 3);
        return false;
    }

    private static final int eo(int i) {
        if (w.We) {
        }
        int iAe = -1;
        int i2 = i;
        for (int i3 = 0; i3 < abc; i3++) {
            byte b = aca[i3];
            if (i2 < b) {
                i2 = b;
                iAe = i3;
            } else if (i2 == b && i2 > i) {
                iAe = ae(iAe, i3);
            }
        }
        if (w.We) {
        }
        return iAe;
    }

    private static final int ep(int i) {
        if (w.We) {
        }
        int i2 = 0;
        while (true) {
            if (i2 >= abc) {
                i2 = -1;
                break;
            }
            if (aca[i2] == i) {
                break;
            }
            i2++;
        }
        if (w.We) {
        }
        return i2;
    }

    static void eq(int i) {
        GLLib.c(false, "MasterVolume is deprecated now, please don't use it!!!");
        if (w.VW && abk && abk) {
            if (w.We) {
            }
            if (w.We) {
            }
        }
    }

    private static final int er(int i) {
        if (w.We) {
        }
        int i2 = 0;
        while (true) {
            if (i2 >= abc) {
                i2 = -1;
                break;
            }
            try {
                if (abD[i2] == i || (abo[i2] == i && l(i2, true))) {
                    break;
                }
                i2++;
            } catch (Exception e) {
                GLLib.d(e);
            }
        }
        if (w.We) {
        }
        return i2;
    }

    protected static boolean es(int i) {
        if (!w.VW || !abk) {
            return false;
        }
        if (w.We) {
        }
        if (er(i) == -1) {
            return false;
        }
        if (w.We) {
        }
        return true;
    }

    private static final void j(int i, int i2, int i3, int i4) {
        c(i, i2, -1, -1, -1, -1, i3, i4);
    }

    private static void k(int i, int i2, int i3, int i4) {
        if (w.We) {
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
        }
        boolean z2 = true;
        if ((abm[i] != null && !abm[i].isPlaying() && abD[i] == -1) || (abE[i] && !z)) {
            z2 = false;
        }
        if (w.We) {
        }
        return z2;
    }

    private static void lT() {
        if (w.VW && abk) {
            if (w.VZ) {
                if (abx) {
                    return;
                } else {
                    abx = true;
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

    private static final int lW() {
        int i = 0;
        if (w.We) {
        }
        while (true) {
            if (i >= abc) {
                i = -1;
                break;
            }
            try {
                if (abw[i] == 0 && !l(i, false)) {
                    break;
                }
                i++;
            } catch (Exception e) {
                GLLib.b("Snd_GetFreeChannel: Error on channel " + i, "GLLib_snd", 6);
                GLLib.d(e);
            }
        }
        if (w.We) {
        }
        return i;
    }

    static void lX() {
        if (w.VW && abk && abg) {
            if (!w.VZ) {
                lT();
            } else {
                if (abt == null || abt.isAlive()) {
                    return;
                }
                abt.start();
            }
        }
    }

    private static void lY() {
        if (w.VZ) {
            synchronized (abt) {
                GLLib.b("Resume thread", "GLPlayer", 6);
                abt.notify();
            }
        }
    }

    static void lZ() {
        ee(500);
    }

    static void p(String str, int i) throws IOException {
        GLLib.c(false, "This load method is deprecated! Please use Snd_LoadSound(String dataFileName, int resourceIndex, boolean isUseSoundPool)");
        c(str, i, acb);
    }

    private static void r(int i, int i2, int i3) throws IOException {
        if (w.VW) {
            if (w.Wd) {
                GLLib.b("Snd_PrepareExec", "GLLib_snd", 3);
            }
            if (w.We) {
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
                }
                return;
            }
            abm[i] = new MediaPlayer();
            abm[i].setOnCompletionListener(new ae());
            abm[i].setDataSource(abL.openFileInput(i2 + ".snddmp").getFD());
            abm[i].prepare();
            abq[i] = 1;
            abo[i] = i2;
            if (w.We) {
            }
        }
    }

    private static void s(int i, int i2, int i3) {
        if (abk) {
            if (w.We) {
            }
            if (!w.Wc) {
                i3 = 0;
            }
            k(2, i, i2, i3);
            abF[i] = i2;
            if (w.We) {
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

    public void dS(int i) {
        this.aaR = i;
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

    static void en(int i) {
        if (w.VW && abk) {
            if (w.We) {
            }
            if (w.Wd) {
                GLLib.b("Snd_UnLoadSound : unloading sound " + i, "GLLib_snd", 3);
            }
            GLLib.c(abl != null, "Snd_UnLoadSound : array is not initialized");
            if (i < 0) {
                if (w.Wd) {
                    GLLib.b("dummy sound skipping", "GLLib_snd", 3);
                }
                if (w.We) {
                }
                return;
            }
            abl[i] = false;
            if (abG[i]) {
                abH.unload(acd[i]);
                GLLib.d(abI[i], 0);
                for (int i2 = 0; i2 < abJ.length; i2++) {
                    abJ[i][i2] = false;
                }
            }
            if (w.We) {
            }
            if (w.Vf) {
            }
        }
    }
}
