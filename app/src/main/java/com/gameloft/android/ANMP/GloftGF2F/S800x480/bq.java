package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

/* JADX INFO: compiled from: SaveGameManager.java */
/* JADX INFO: loaded from: classes.dex */
public class bq {
    private static String ada;
    private static String adc;
    private static int add;
    private static int adh;
    private static boolean adw;
    private static String ajQ;
    private static e ajR;
    private static javax.microedition.b.b ajS;
    private static javax.microedition.b.b ajT;
    private static Vector ajU;
    private static bc ajV;
    private static byte[] ajW;
    private static int ajX;
    public static int ajY;
    private static int ajZ;
    private static boolean aka;
    private static boolean akb;
    private static boolean akc;
    private static String akd;
    private static int ake = -1;
    private static int adb = -1;
    private static Exception ade = null;
    private static boolean adu = false;

    public static int my() {
        if (!adw) {
            return -1;
        }
        adw = false;
        return ake;
    }

    public static void a(int i, String str, String str2, int i2, String str3, Exception exc) {
        if (str2.startsWith("cloud")) {
            adb = i;
            adc = str;
            ada = str2;
            add = i2;
            ade = exc;
        }
    }

    private static void mC() {
        adb = -1;
        adc = "";
        ada = "";
        ade = null;
    }

    private static void C(int i, String str) {
        mC();
        boolean z = true;
        do {
            if (adb == i && ada.equals(str)) {
                if (add > 200) {
                    adh = add;
                    akd = str + " errorcode:" + add;
                    return;
                }
                if (ade != null) {
                    if ("java.io.IOException".equals(ade.getClass().getName()) || "java.net.ConnectException".equals(ade.getClass().getName())) {
                        GLLib.be("No Internet connection");
                    }
                    adh = -1;
                    return;
                }
                if (ade != null) {
                    if ("java.io.IOException".equals(ade.getClass().getName()) || "java.net.ConnectException".equals(ade.getClass().getName())) {
                        GLLib.be("No Internet connection");
                    }
                    adh = -1;
                    return;
                }
                z = false;
            } else {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                }
            }
        } while (z);
        adh = 0;
    }

    public static int mv() {
        return adh;
    }

    public static String nF() {
        return akd;
    }

    public static int ct(String str) {
        if (w.ZT) {
            if (aka) {
                return -125;
            }
            GLLib.be("Begin saving game.");
            try {
                ajS = javax.microedition.b.b.t(str, true);
            } catch (javax.microedition.b.c e) {
                GLLib.d(e);
            }
            if (ajS == null) {
                return -16;
            }
            ajR = new e();
            akb = true;
            ajY = 0;
            return 0;
        }
        return -1;
    }

    public static int n(byte[] bArr, int i) {
        return a(bArr, i, s.jr());
    }

    private static int a(byte[] bArr, int i, String str) {
        int iA;
        if (w.ZT) {
            if (!akb) {
                return -16;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                iA = ak.a(byteArrayInputStream, byteArrayOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
                iA = 0;
            }
            if (iA == 0) {
                GLLib.be("Error while compressing buffer. Aborting Save\n");
                try {
                    ajS.akW();
                } catch (javax.microedition.b.c e2) {
                    GLLib.d(e2);
                }
                akb = false;
                return -9;
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            GLLib.kQ();
            int iA2 = GLLib.a(bArr, 0, bArr.length, 0);
            byte[] bArrA = GLLib.a(byteArray, str, true);
            ajR.a(new bp(GLLib.g(new int[]{i, iA2, bArrA.length})));
            ajR.a(new bp(bArrA));
            ajY++;
            return 0;
        }
        return -1;
    }

    public static int nG() {
        if (ajR == null || ajR.hT() == null) {
            return 0;
        }
        byte[] bArrG = GLLib.g(new int[]{ajY});
        e eVar = new e();
        eVar.a(ajR.hT());
        eVar.a(new bp(bArrG));
        return GLLib.e(eVar.hT().data).length();
    }

    public static synchronized int i(String str, boolean z) {
        return a(str, z, (byte) 1);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0068  */
    public static synchronized int a(String str, boolean z, byte b) {
        int i = -1;
        synchronized (bq.class) {
            try {
                if (w.ZT) {
                    if (!akb) {
                        i = -16;
                    } else {
                        ajR.a(new bp(GLLib.g(new int[]{ajY})));
                        try {
                            ajS.n(ajR.hT().data, 0, ajR.hT().data.length);
                            ajR.as(str);
                            ajR.at(s.jr());
                            try {
                                ajS.akW();
                            } catch (javax.microedition.b.c e) {
                                GLLib.d(e);
                            }
                            akb = false;
                            if (z) {
                                ajR.b(b);
                                ake = 1;
                                adw = false;
                                if (a(null)) {
                                    i = 0;
                                }
                            } else {
                                i = 0;
                            }
                        } catch (javax.microedition.b.c e2) {
                            GLLib.d(e2);
                            adh = -1;
                            akd = "Can not save local record";
                            i = adh;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    private static boolean a(Object[] objArr) {
        if (!w.ZT || adu) {
            return false;
        }
        adu = true;
        new br(objArr).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void mJ() {
        adu = false;
        adw = true;
        GLLib.be("oss: thread ended");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void nH() {
        if (w.ZT) {
            ajR.l(s.iR() / 1000);
            switch (ajR.hQ()) {
                case 1:
                    nI();
                    break;
                case 2:
                    nJ();
                    break;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:67:0x02bf  */
    private static void nI() {
        bc bcVar;
        int iCe;
        long jCB;
        long jCB2;
        int i;
        GLLib.be("Attempting to upload save to GL cloud");
        adh = 0;
        Vector vector = new Vector();
        vector.removeAllElements();
        if (ajQ != null) {
            vector.addElement(ajQ);
        } else {
            vector.addElement("me");
        }
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < vector.size()) {
                String str = (String) vector.elementAt(i3);
                bc bcVar2 = new bc();
                s.j(str, "$savegamelib.objects.TOC", "cloudUploadGetToc");
                C(t.Os, "cloudUploadGetToc");
                if (adh == 404) {
                    GLLib.be("Table of Contents does not exist. Creating it");
                    bc bcVar3 = new bc();
                    bcVar3.a("TOC", new bc());
                    bcVar = bcVar3;
                } else if (adh == 0) {
                    bc bcVar4 = new bc(adc);
                    if (((bc) bcVar4.cd("TOC")) == null) {
                        akd = "Retrieved Invalid Table Of Contents";
                        adh = -13;
                        GLLib.be(akd);
                        return;
                    }
                    bcVar = bcVar4;
                } else {
                    bcVar = bcVar2;
                }
                bc bcVar5 = (bc) bcVar.cd("TOC");
                s.d(str, null, null, "cloudUploadGetProfile");
                C(t.Ow, "cloudUploadGetProfile");
                if (adh == 404) {
                    s.f(str, null, "{}", null, "set", "cloudUploadSetProfile");
                    C(t.Ov, "cloudUploadSetProfile");
                    s.d(str, null, null, "cloudUploadGetProfile2");
                    C(t.Ow, "cloudUploadGetProfile2");
                }
                if (adh == 0) {
                    bc bcVar6 = new bc(adc);
                    if (bcVar6.cd("@quota") == null) {
                        iCe = -1;
                    } else {
                        bc bcVar7 = (bc) bcVar6.cd("@quota");
                        if (bcVar7.cd("@savegamelib") == null) {
                            iCe = -1;
                        } else {
                            try {
                                iCe = ((bc) bcVar7.cd("@savegamelib")).ce("remaining");
                            } catch (Exception e) {
                                adh = -1;
                                akd = "profile quotas error";
                                GLLib.be(akd);
                                return;
                            }
                        }
                    }
                    int iNo = bcVar5.no();
                    if (iNo == 0 && iCe <= 0) {
                        adh = -126;
                        akd = "server quotas exceed";
                        GLLib.be(akd);
                        return;
                    }
                    if (iNo >= 5 || iCe == 0) {
                        GLLib.be("Maximum number of saves reached. Overwriting oldest save.");
                        int i4 = 0;
                        try {
                            jCB = cB(((bc) bcVar5.nq()[0]).ch("Date"));
                        } catch (Exception e2) {
                            GLLib.be("TIMESTAMP is INVALID. Save 1 Invalid. Replacing it");
                            jCB = 0;
                        }
                        int i5 = 1;
                        long j = jCB;
                        while (i5 < iNo) {
                            try {
                                jCB2 = cB(((bc) bcVar5.nq()[i5]).ch("Date"));
                            } catch (Exception e3) {
                                jCB2 = 0;
                            }
                            if (j >= jCB2) {
                                i = i5;
                            } else {
                                jCB2 = j;
                                i = i4;
                            }
                            i5++;
                            j = jCB2;
                            i4 = i;
                        }
                        String string = Long.toString(ajR.hS());
                        bc bcVar8 = (bc) bcVar5.nq()[i4];
                        bcVar8.a("Date", string);
                        bcVar8.a("Description", ajR.hX());
                        bcVar8.a("Version", ajR.hW());
                        bcVar8.a("GLUID", GLLib.e(ajR.hU().getBytes()));
                        try {
                            ajR.au(bcVar8.ch("SeshatFileKey"));
                        } catch (Exception e4) {
                            adh = -1;
                            akd = "error get field seshatFileKey";
                            return;
                        }
                    } else {
                        String str2 = "$savegamelib.objects.key" + Long.toString(iNo + 1);
                        bc bcVar9 = new bc();
                        bcVar9.a("Description", ajR.hX());
                        bcVar9.a("SeshatFileKey", str2);
                        ajR.au(str2);
                        bcVar9.a("Date", Long.toString(ajR.hS()));
                        bcVar9.a("GLUID", GLLib.e(ajR.hU().getBytes()));
                        bcVar9.a("Version", ajR.hW());
                        bcVar5.a(str2, bcVar9);
                    }
                    s.d(str, ajR.hV(), GLLib.e(ajR.hT().data), "private", "cloudUploadPutKey");
                    C(t.Ot, "cloudUploadPutKey");
                    if (adh == 0) {
                        GLLib.be("Savegame upload to cloud was successful.");
                        String string2 = bcVar.toString();
                        GLLib.be("Updated Table Of Contents.");
                        s.d(str, "$savegamelib.objects.TOC", string2, "private", "cloudUploadPutToc");
                        C(t.Ot, "cloudUploadPutToc");
                        if (adh == 0) {
                            GLLib.be("Table Of Contents upload to cloud was successful.");
                        } else {
                            akd = "Table Of Contents upload to cloud failed.";
                            GLLib.be(akd);
                        }
                    } else {
                        akd = "Savegame upload to cloud failed.";
                    }
                    i2 = i3 + 1;
                } else {
                    adh = -1;
                    akd = "Can not get user standard profile";
                    GLLib.be(akd);
                    return;
                }
            } else {
                return;
            }
        }
    }

    private static void nJ() {
        int i = 0;
        GLLib.be("Attempting to upload save to GAME cloud");
        adh = 0;
        String strE = GLLib.e(ajR.hT().data);
        bc bcVar = new bc();
        bcVar.a("Version", ajR.hW());
        bcVar.a("Date", new Long(ajR.hS()));
        bcVar.a("Description", ajR.hX());
        bcVar.a("GLUID", ajR.hU());
        String string = bcVar.toString();
        String str = string.length() + string + strE;
        Vector vector = new Vector();
        vector.removeAllElements();
        if (ajQ != null) {
            vector.addElement(ajQ);
        } else {
            vector.addElement("me");
        }
        while (true) {
            int i2 = i;
            if (i2 < vector.size()) {
                s.d((String) vector.elementAt(i2), "game_dedicated_slot.dat", str, "private", "cloudUploadGame");
                C(t.Ot, "cloudUploadGame");
                if (adh == 0) {
                    GLLib.be("Savegame upload to GAME cloud successful.");
                } else {
                    GLLib.be("Savegame upload to GAME cloud failed.");
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void cu(String str) {
        GLLib.be("Retrieving saves from dedicated game saves");
        adh = 0;
        Vector vector = new Vector();
        vector.removeAllElements();
        if (ajQ != null) {
            vector.addElement(ajQ);
        } else {
            vector.addElement("me");
        }
        for (int i = 0; i < vector.size(); i++) {
            String str2 = (String) vector.elementAt(i);
            s.j(str2, "game_dedicated_slot.dat", "cloudDownloadFile");
            C(t.Os, "cloudDownloadFile");
            if (adh != 0) {
                GLLib.be("DownloadFileFromSeshat GetData failed");
                return;
            }
            String str3 = adc;
            if (str3 != null) {
                try {
                    int iIndexOf = str3.indexOf("{");
                    int i2 = Integer.parseInt(str3.substring(0, iIndexOf));
                    String strSubstring = str3.substring(iIndexOf, iIndexOf + i2);
                    String strSubstring2 = str3.substring(iIndexOf + i2);
                    ajV = new bc(strSubstring);
                    if (!ajV.cb("GLUID")) {
                        GLLib.be("Dedicated game save missing gluid");
                        adh = -6;
                        return;
                    }
                    String string = ajV.cd("GLUID").toString();
                    byte[] bArrBJ = GLLib.bJ(strSubstring2);
                    if (cA("tempSaveFile")) {
                        javax.microedition.b.b.ji("tempSaveFile");
                    }
                    ajS = javax.microedition.b.b.t("tempSaveFile", true);
                    if (ajS == null) {
                        adh = -16;
                        GLLib.be("can't use tempSaveFile");
                        return;
                    }
                    ajS.n(bArrBJ, 0, bArrBJ.length);
                    ajS.akW();
                    adh = cv("tempSaveFile");
                    if (adh != 0) {
                        GLLib.be("can't load tempSaveFile");
                        return;
                    }
                    int i3 = ajY;
                    adh = ct(str);
                    if (adh != 0) {
                        GLLib.be("can't save " + str);
                        return;
                    }
                    for (int i4 = 0; i4 < i3; i4++) {
                        byte[] bArrCw = cw(string);
                        if (adh != 0) {
                            GLLib.be("load buffer " + i4 + " fail");
                            return;
                        }
                        adh = n(bArrCw, bArrCw.length);
                        if (adh != 0) {
                            GLLib.be("save buffer " + i4 + " fail");
                            return;
                        }
                    }
                    adh = i("", false);
                    if (adh == 0) {
                        adh = nL();
                        try {
                            javax.microedition.b.b.ji("tempSaveFile");
                        } catch (javax.microedition.b.c e) {
                            e.printStackTrace();
                            adh = -1;
                        }
                    } else {
                        return;
                    }
                } catch (Exception e2) {
                    GLLib.be("Decode game cloud save fail");
                }
            }
            if (adh != 0) {
                GLLib.be("GetDedicatedGameSave: DownloadFileFromSeshat - failed for cred " + str2);
                return;
            }
        }
    }

    public static int cv(String str) {
        if (w.ZT) {
            GLLib.be("Begin loading game");
            try {
                ajT = javax.microedition.b.b.t(str, false);
            } catch (javax.microedition.b.c e) {
                GLLib.d(e);
            }
            if (ajT != null) {
                ajZ = 0;
                try {
                    ajW = ajT.pi(1);
                    ajX = 0;
                    ajY = GLLib.l(ajW, ajW.length - 4);
                } catch (Exception e2) {
                    GLLib.d(e2);
                }
                akc = true;
                return 0;
            }
            GLLib.be("Could not load save file");
            return -16;
        }
        return -1;
    }

    public static byte[] nK() {
        return cw(s.jr());
    }

    private static byte[] cw(String str) {
        if (!w.ZT) {
            return null;
        }
        if (!akc) {
            adh = -16;
            return null;
        }
        GLLib.l(ajW, ajX);
        ajX += 4;
        int iL = GLLib.l(ajW, ajX);
        ajX += 4;
        int iL2 = GLLib.l(ajW, ajX);
        ajX += 4;
        byte[] bArr = new byte[iL2];
        ajX = GLLib.b(ajW, ajX, bArr);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(GLLib.b(bArr, str, true));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ak.b(byteArrayInputStream, byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (byteArrayOutputStream.size() == 0) {
            GLLib.be("Error while uncompressing buffer.\n");
            adh = -7;
            return null;
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        GLLib.kQ();
        if (GLLib.a(byteArray, 0, byteArray.length, 0) != iL) {
            akd = "CRC Error.\n";
            GLLib.be(akd);
            adh = -10;
            return null;
        }
        ajZ++;
        adh = 0;
        return byteArray;
    }

    public static int nL() {
        if (w.ZT) {
            try {
                ajT.akW();
            } catch (Exception e) {
                GLLib.d(e);
            }
            akc = false;
            if (ajZ == ajY) {
                return 0;
            }
            akd = "Savegame parsing mismatch! You did not load all the data";
            GLLib.be(akd);
            return -18;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void nM() {
        if (w.ZT) {
            ajU = new Vector();
            GLLib.be("Retrieving saves from cloud");
            adh = 0;
            Vector vector = new Vector();
            vector.removeAllElements();
            if (ajQ != null) {
                vector.addElement(ajQ);
            } else {
                vector.addElement("me");
            }
            for (int i = 0; i < vector.size(); i++) {
                String str = (String) vector.elementAt(i);
                new bc();
                s.j(str, "$savegamelib.objects.TOC", "cloudGetCloudList");
                C(t.Os, "cloudGetCloudList");
                if (adh == 0) {
                    bc bcVar = new bc(adc);
                    if (((bc) bcVar.cd("TOC")) == null) {
                        adh = -13;
                        GLLib.be("field TOC not exist");
                        return;
                    }
                    bc bcVar2 = (bc) bcVar.cd("TOC");
                    int iNo = bcVar2.no();
                    if (iNo != 0) {
                        for (int i2 = 0; i2 < iNo; i2++) {
                            try {
                                bc bcVar3 = (bc) bcVar2.nq()[i2];
                                e eVar = new e();
                                eVar.av(str);
                                eVar.au(bcVar3.ch("SeshatFileKey"));
                                eVar.as(bcVar3.ch("Description"));
                                eVar.l(cB(bcVar3.ch("Date")));
                                String strCh = bcVar3.ch("GLUID");
                                if (strCh.length() > 0) {
                                    eVar.at(new String(GLLib.bJ(strCh)));
                                    ajU.addElement(eVar);
                                } else {
                                    GLLib.be("Savegame does not have ID.\n");
                                }
                            } catch (Exception e) {
                                adh = -13;
                                akd = "Invalid TOC content";
                                GLLib.be(akd);
                                return;
                            }
                        }
                    }
                } else {
                    GLLib.be("Fail retrieve cloud list (TOC)");
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(String str, e eVar) {
        if (w.ZT) {
            GLLib.be("Restoring save from cloud...");
            adh = 0;
            s.j(eVar.hR(), eVar.hV(), "cloudGetCloudSave");
            C(t.Os, "cloudGetCloudSave");
            if (adh != 0) {
                GLLib.be("Could not retrieve savegame");
                return;
            }
            try {
                byte[] bArrBJ = GLLib.bJ(adc);
                if (cA("tempSaveFile")) {
                    javax.microedition.b.b.ji("tempSaveFile");
                }
                ajS = javax.microedition.b.b.t("tempSaveFile", true);
                if (ajS == null) {
                    adh = -16;
                    GLLib.be("can't use tempSaveFile");
                    return;
                }
                ajS.n(bArrBJ, 0, bArrBJ.length);
                ajS.akW();
                adh = cv("tempSaveFile");
                if (adh != 0) {
                    GLLib.be("can't load tempSaveFile");
                    return;
                }
                int i = ajY;
                adh = ct(str);
                if (adh != 0) {
                    GLLib.be("can't save " + str);
                    return;
                }
                for (int i2 = 0; i2 < i; i2++) {
                    byte[] bArrCw = cw(eVar.hU());
                    if (adh != 0) {
                        GLLib.be("load buffer " + i2 + " fail");
                        return;
                    }
                    adh = n(bArrCw, bArrCw.length);
                    if (adh != 0) {
                        GLLib.be("save buffer " + i2 + " fail");
                        return;
                    }
                }
                adh = i("", false);
                if (adh == 0) {
                    adh = nL();
                    try {
                        javax.microedition.b.b.ji("tempSaveFile");
                    } catch (javax.microedition.b.c e) {
                        GLLib.d(e);
                        adh = -1;
                    }
                }
            } catch (Exception e2) {
                GLLib.be(e2.getMessage());
                adh = -1;
            }
        }
    }

    private static int f(String str, String str2, String str3, String str4) {
        if (!w.ZT) {
            return -1;
        }
        adh = 0;
        if (str2 != null) {
            String str5 = "THO DEBUG CLOUD . saveFileName == " + str;
            String str6 = "THO DEBUG CLOUD . token == " + str2;
            String str7 = "THO DEBUG CLOUD . seshatKeyValue == " + str4;
            String str8 = "THO DEBUG CLOUD . key == " + str3;
            String strSubstring = "";
            try {
                int iIndexOf = str2.indexOf("anonymous:");
                String strSubstring2 = str2.substring(iIndexOf == -1 ? str2.indexOf("facebook:") : iIndexOf);
                strSubstring = strSubstring2.substring(0, strSubstring2.indexOf(","));
            } catch (Exception e) {
                String str9 = "THO DEBUG CLOUD . Exception anonymous == ";
                e.printStackTrace();
            }
            String str10 = "THO DEBUG CLOUD . anonymous == " + strSubstring;
            s.c(strSubstring, str4, str2, "cloudRestoreGetKey");
        } else {
            s.j("me", str4, "cloudRestoreGetKey");
        }
        C(t.Os, "cloudRestoreGetKey");
        if (adh != 0) {
            GLLib.be("Could not retrieve savegame");
            return adh;
        }
        try {
            byte[] bArrBJ = GLLib.bJ(adc);
            if (cA("tempSaveFile")) {
                javax.microedition.b.b.ji("tempSaveFile");
            }
            ajS = javax.microedition.b.b.t("tempSaveFile", true);
            if (ajS == null) {
                return -16;
            }
            ajS.n(bArrBJ, 0, bArrBJ.length);
            ajS.akW();
            adh = cv("tempSaveFile");
            if (adh != 0) {
                return adh;
            }
            int i = ajY;
            adh = ct(str);
            if (adh != 0) {
                return adh;
            }
            for (int i2 = 0; i2 < i; i2++) {
                byte[] bArrCw = cw(str3);
                if (adh != 0) {
                    return adh;
                }
                adh = n(bArrCw, bArrCw.length);
                if (adh != 0) {
                    return adh;
                }
            }
            adh = i("", false);
            if (adh != 0) {
                return adh;
            }
            adh = nL();
            try {
                javax.microedition.b.b.ji("tempSaveFile");
            } catch (javax.microedition.b.c e2) {
                GLLib.d(e2);
                adh = -1;
            }
        } catch (Exception e3) {
            GLLib.be(e3.getMessage());
        }
        return adh;
    }

    public static boolean F(String str, String str2) {
        if (!w.ZT) {
            return false;
        }
        ake = 4;
        adw = false;
        return a(new Object[]{str, str2});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void G(String str, String str2) {
        String strCh;
        if (w.ZT) {
            GLLib.be("Restoring save from cloud...");
            adh = 0;
            bc bcVar = new bc(str);
            if (bcVar.no() > 0 && bcVar.np()[0].equals("json")) {
                try {
                    bc[] bcVarArrCj = bcVar.cj("json");
                    for (int i = 0; i < bcVarArrCj.length; i++) {
                        if (bcVarArrCj[i].cd("seshatSaveKey") != null) {
                            bcVar = bcVarArrCj[i];
                        }
                    }
                } catch (Exception e) {
                }
            }
            try {
                if (bcVar.ce("version") == 1) {
                    String strCh2 = bcVar.ch("token");
                    String strCh3 = bcVar.ch("seshatSaveKey");
                    if (strCh3 == null || (strCh = bcVar.ch("key")) == null) {
                        adh = -122;
                    } else {
                        adh = f(str2, strCh2, new String(GLLib.bJ(strCh)), strCh3);
                        if (adh == 0) {
                            aka = true;
                        }
                    }
                } else {
                    adh = -123;
                }
            } catch (Exception e2) {
                akd = e2.getMessage();
                adh = -1;
            }
        }
    }

    public static int cx(String str) {
        aka = false;
        return 0;
    }

    public static int cy(String str) {
        try {
            javax.microedition.b.b.ji(str);
        } catch (javax.microedition.b.c e) {
            GLLib.d(e);
        }
        aka = false;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void nN() {
        GLLib.be("Attempting to reset the clouds");
        adh = 0;
        s.t("$savegamelib.objects.TOC", "cloudDeleteToc");
        C(t.Ou, "cloudDeleteToc");
        s.t("$savegamelib.objects.key1", "cloudDeleteKey1");
        C(t.Ou, "cloudDeleteKey1");
        s.t("$savegamelib.objects.key2", "cloudDeleteKey2");
        C(t.Ou, "cloudDeleteKey2");
        s.t("$savegamelib.objects.key3", "cloudDeleteKey3");
        C(t.Ou, "cloudDeleteKey3");
        s.t("$savegamelib.objects.key4", "cloudDeleteKey4");
        C(t.Ou, "cloudDeleteKey4");
        s.t("$savegamelib.objects.key5", "cloudDeleteKey5");
        C(t.Ou, "cloudDeleteKey5");
    }

    public static int cz(String str) {
        ajQ = str;
        return 0;
    }

    private static boolean cA(String str) {
        boolean z = false;
        javax.microedition.b.b bVar = null;
        try {
            z = true;
            try {
                javax.microedition.b.b.t(str, false).akW();
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            try {
                bVar.akW();
            } catch (Exception e3) {
            }
        } catch (Throwable th) {
            try {
                bVar.akW();
            } catch (Exception e4) {
            }
            throw th;
        }
        return z;
    }

    private static long b(char[] cArr, int i) {
        int i2 = 0;
        long j = 0;
        int i3 = 0;
        while (true) {
            int i4 = i2;
            if (i3 >= cArr.length || cArr[i3] < '0' || cArr[i3] > '9') {
                break;
            }
            j = (j * 10) + ((long) (cArr[i3] - '0'));
            i3++;
            i2 = i4 + 1;
        }
        return j;
    }

    private static long cB(String str) {
        return b(str.toCharArray(), 0);
    }
}
