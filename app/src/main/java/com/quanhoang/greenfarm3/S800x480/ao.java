package com.quanhoang.greenfarm3.S800x480;

import com.facebook.appevents.AppEventsConstants;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class ao {
    // Transparent Java checked-exception bridge for calls with no DEX handler.
    @SuppressWarnings("unchecked")
    private static <Failure extends Throwable> RuntimeException propagateChecked(Throwable failure) throws Failure {
        throw (Failure) failure;
    }

    private static Vector acV;
    private static int adh;
    bc acQ;
    private String acR;
    private String ada;
    private String adc;
    private int add;
    private boolean adi;
    private boolean adj;
    private String adk;
    private boolean adl;
    private boolean adm;
    private boolean adn;
    private boolean ado;
    private boolean adp;
    private boolean adq;
    private boolean adr;
    private boolean ads;
    private boolean adt;
    private int adv;
    private static boolean acS = true;
    private static boolean acT = false;
    private static boolean acU = true;
    private static boolean adf = false;
    static ao adg = null;
    private final int acW = 0;
    private final int acX = 1;
    private final int acY = 2;
    private final int acZ = 2145938400;
    private int adb = -1;
    private Exception ade = null;
    private boolean adu = false;
    private boolean adw = false;

    public ao() {
        acV = new Vector();
        this.adm = false;
        this.adn = false;
        this.ado = false;
        this.adp = false;
        this.adq = false;
        this.adr = false;
        this.ads = false;
        this.adt = false;
        this.acR = null;
        this.acQ = new bc();
    }

    private void C(int i, String str) {
        mC();
        boolean z = true;
        do {
            if (this.adb != i || !this.ada.equals(str)) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                }
            } else if (this.add > 200) {
                adh = this.add;
                return;
            } else {
                if (this.ade != null) {
                    if ("java.io.IOException".equals(this.ade.getClass().getName()) || "java.net.ConnectException".equals(this.ade.getClass().getName())) {
                        GameRuntime.be("No Internet connection");
                    }
                    adh = -1;
                    return;
                }
                z = false;
            }
        } while (z);
        adh = 0;
    }

    private int E(String str, String str2) {
        if (str == null || str.equals("[]")) {
            return -12;
        }
        Object objCd = new bc(str).cd("expiry");
        if (objCd == null) {
            GameRuntime.be("config invalid: missing expiry field");
            return 3;
        }
        String str3 = (String) objCd;
        byte[] bArrB = b(str.getBytes(), this.acR);
        if (bArrB == null) {
            return -2;
        }
        if (acU) {
            long jBU = (str3.length() <= 0 || str3.equals("null")) ? 2145938400L : bU(str3);
            String str4 = "config" + jBU;
            Vector vector = new Vector();
            vector.addElement(String.valueOf(jBU));
            vector.addElement(str4);
            vector.addElement(str2);
            c(vector);
            mE();
            try {
                GameRuntime.e(str4, bArrB);
            } catch (Exception e) {
                return -6;
            }
        }
        return 0;
    }

    public static final InputStream a(Class cls, String str) {
        return com.gameloft.android.wrapper.au.getResourceAsStream(str);
    }

    private void a(bz bzVar, String str) {
        bzVar.J("me", this.acR);
        boolean z = true;
        do {
            if (adf) {
                z = false;
            } else {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                }
            }
        } while (z);
    }

    private boolean a(Object[] objArr) {
        if (this.adu) {
            return false;
        }
        this.adu = true;
        this.adv = ((Integer) objArr[0]).intValue();
        new ap(this, objArr).start();
        return true;
    }

    private byte[] a(byte[] bArr, String str) {
        try {
            byte[] bArrB = GameRuntime.b(bArr, str, true);
            if (bArrB == null) {
                adh = -3;
                return null;
            }
            int iL = GameRuntime.l(bArrB, 0);
            byte[] bArr2 = new byte[(bArrB.length - iL) - 4];
            GameRuntime.b(bArrB, iL + 4, bArr2);
            try {
                new String(bArr2, "ISO-8859-1");
                byte[] bArr3 = new byte[iL + 4];
                System.arraycopy(bArrB, 0, bArr3, 0, bArr3.length);
                GameRuntime.kM();
                try {
                    GameRuntime.A(new String(bArr3, "ISO-8859-1"), "ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    GameRuntime.kP();
                } catch (Exception e2) {
                }
                byte[] bArr4 = new byte[iL];
                GameRuntime.b(bArr3, 4, bArr4);
                adh = 0;
                return bArr4;
            } catch (Exception e3) {
                return null;
            }
        } catch (Exception e4) {
            adh = -3;
            return null;
        }
    }

    private byte[] b(byte[] bArr, String str) {
        String strKP;
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 4];
        GameRuntime.d(bArr2, 0, length);
        GameRuntime.a(bArr2, 4, bArr);
        GameRuntime.kM();
        try {
            GameRuntime.A(new String(bArr2, "ISO-8859-1"), "ISO-8859-1");
            try {
                strKP = GameRuntime.kP();
            } catch (Exception e) {
                strKP = null;
            }
            try {
                byte[] bytes = strKP.getBytes("ISO-8859-1");
                byte[] bArr3 = new byte[length + 4 + bytes.length];
                GameRuntime.d(bArr3, 0, length);
                GameRuntime.a(bArr3, 4, bArr);
                GameRuntime.a(bArr3, length + 4, bytes);
                try {
                    return GameRuntime.a(bArr3, str, true);
                } catch (Exception e2) {
                    GameRuntime.be("Hestia encrypt config fail");
                    return null;
                }
            } catch (Exception e3) {
                return null;
            }
        } catch (UnsupportedEncodingException e4) {
            GameRuntime.be(e4.toString());
            return null;
        }
    }

    private void bS(String str) {
        bc bcVar = new bc(str);
        if (bcVar.cb("game")) {
            this.acQ = (bc) bcVar.cd("game");
        }
    }

    private String bT(String str) {
        Date date = new Date((Long.parseLong(str) * 1000) - ((long) TimeZone.getDefault().getRawOffset()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(1);
        int i2 = calendar.get(2) + 1;
        int i3 = calendar.get(5);
        int i4 = calendar.get(11);
        int i5 = calendar.get(12);
        int i6 = calendar.get(13);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(i);
        stringBuffer.append("-");
        if (i2 > 9) {
            stringBuffer.append(i2);
        } else {
            stringBuffer.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
            stringBuffer.append(i2);
        }
        stringBuffer.append("-");
        if (i3 > 9) {
            stringBuffer.append(i3);
        } else {
            stringBuffer.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
            stringBuffer.append(i3);
        }
        stringBuffer.append(" ");
        stringBuffer.append(i4);
        stringBuffer.append(":");
        stringBuffer.append(i5);
        stringBuffer.append(":");
        stringBuffer.append(i6);
        stringBuffer.append("Z");
        return stringBuffer.toString();
    }

    private long bU(String str) {
        Calendar calendar = Calendar.getInstance();
        int iIndexOf = str.indexOf("-");
        if (iIndexOf <= 0) {
            return 0L;
        }
        int i = Integer.parseInt(str.substring(0, iIndexOf));
        String strSubstring = str.substring(iIndexOf + 1);
        int iIndexOf2 = strSubstring.indexOf("-");
        if (iIndexOf2 <= 0) {
            return 0L;
        }
        int i2 = Integer.parseInt(strSubstring.substring(0, iIndexOf2));
        String strSubstring2 = strSubstring.substring(iIndexOf2 + 1);
        int iIndexOf3 = strSubstring2.indexOf(" ");
        if (iIndexOf3 <= 0) {
            return 0L;
        }
        int i3 = Integer.parseInt(strSubstring2.substring(0, iIndexOf3));
        String strSubstring3 = strSubstring2.substring(iIndexOf3 + 1);
        int iIndexOf4 = strSubstring3.indexOf(":");
        if (iIndexOf4 <= 0) {
            return 0L;
        }
        int i4 = Integer.parseInt(strSubstring3.substring(0, iIndexOf4));
        String strSubstring4 = strSubstring3.substring(iIndexOf4 + 1);
        int iIndexOf5 = strSubstring4.indexOf(":");
        if (iIndexOf5 <= 0) {
            return 0L;
        }
        int i5 = Integer.parseInt(strSubstring4.substring(0, iIndexOf5));
        String strSubstring5 = strSubstring4.substring(iIndexOf5 + 1);
        int iIndexOf6 = strSubstring5.indexOf("Z");
        if (iIndexOf6 <= 0) {
            return 0L;
        }
        int i6 = Integer.parseInt(strSubstring5.substring(0, iIndexOf6));
        calendar.set(1, i);
        calendar.set(2, i2 - 1);
        calendar.set(5, i3);
        calendar.set(11, i4);
        calendar.set(12, i5);
        calendar.set(13, i6);
        return (calendar.getTime().getTime() + ((long) TimeZone.getDefault().getRawOffset())) / 1000;
    }

    private void c(Vector vector) {
        String str = (String) vector.elementAt(0);
        for (int i = 0; i < acV.size(); i++) {
            if (str.equals((String) ((Vector) acV.elementAt(i)).elementAt(0))) {
                acV.setElementAt(vector, i);
                return;
            }
        }
        acV.addElement(vector);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int ew(int i) {
        try {
            return ms().ex(i);
        } catch (Exception failure) {
            throw ao.<RuntimeException>propagateChecked(failure);
        }
    }

    private int ey(int i) {
        while (i < acV.size()) {
            try {
                GameRuntime.bG((String) ((Vector) acV.elementAt(i)).elementAt(1));
            } catch (Exception e) {
            }
            acV.removeElementAt(i);
            i = (i - 1) + 1;
        }
        return mE();
    }

    private int mA() {
        if (acT) {
            return 0;
        }
        if (mF() != 0) {
            acT = false;
            return -14;
        }
        acT = true;
        return 0;
    }

    private void mB() throws Exception {
        if (this.adi) {
            GameRuntime.be("Config Refresh Failed");
        } else {
            GameRuntime.be("Configs Succesfully Refreshed");
        }
        if (this.adl) {
            ms().mD();
        } else {
            au.mN().bV(mI());
            au.mN().eB(2);
        }
    }

    private void mC() {
        this.adb = -1;
        this.adc = "";
        this.ada = "";
        this.ade = null;
    }

    private int mE() {
        String str = "";
        for (int i = 0; i < acV.size(); i++) {
            Vector vector = (Vector) acV.elementAt(i);
            str = (((((str + vector.elementAt(0)) + ";") + vector.elementAt(1)) + ";") + vector.elementAt(2)) + ";";
        }
        if (acV.isEmpty()) {
            return -12;
        }
        byte[] bArrB = b(str.getBytes(), this.acR);
        if (bArrB == null) {
            return -2;
        }
        try {
            GameRuntime.e("filesConfig", bArrB);
            return 0;
        } catch (Exception e) {
            return -6;
        }
    }

    private int mF() {
        byte[] bArrBF = GameRuntime.bF("filesConfig");
        if (bArrBF == null) {
            return -7;
        }
        byte[] bArrA = a(bArrBF, this.acR);
        if (bArrA == null) {
            return -3;
        }
        String str = new String(bArrA);
        int iIndexOf = str.indexOf(";");
        while (iIndexOf >= 0) {
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + ";".length());
            int iIndexOf2 = strSubstring2.indexOf(";");
            if (iIndexOf2 < 0) {
                return -1;
            }
            String strSubstring3 = strSubstring2.substring(0, iIndexOf2);
            String strSubstring4 = strSubstring2.substring(iIndexOf2 + ";".length());
            int iIndexOf3 = strSubstring4.indexOf(";");
            if (iIndexOf3 < 0) {
                return -1;
            }
            String strSubstring5 = strSubstring4.substring(0, iIndexOf3);
            str = strSubstring4.substring(iIndexOf3 + ";".length());
            Vector vector = new Vector();
            vector.addElement(strSubstring);
            vector.addElement(strSubstring3);
            vector.addElement(strSubstring5);
            c(vector);
            iIndexOf = str.indexOf(";");
        }
        return 0;
    }

    private int mG() {
        return ey(0);
    }

    public static void mr() {
        adf = true;
    }

    public static ao ms() {
        if (adg == null) {
            adg = new ao();
        }
        return adg;
    }

    public int D(String str, String str2) {
        byte[] bArr;
        String str3;
        this.adk = "";
        this.adi = false;
        this.adm = false;
        this.adn = false;
        this.ado = false;
        this.adp = false;
        this.adq = false;
        this.adr = false;
        this.ads = false;
        this.adt = false;
        InputStream inputStreamA = a(str.getClass(), str);
        try {
            bArr = new byte[inputStreamA.available()];
            try {
                inputStreamA.read(bArr);
                inputStreamA.close();
            } catch (Exception e) {
                GameRuntime.be("Error in read file default game config");
            }
        } catch (Exception e2) {
            bArr = null;
        }
        byte[] bArrA = a(bArr, this.acR);
        if (bArrA == null) {
            this.adi = true;
            this.adk += "Default config failed to decrypt,";
            str3 = null;
        } else {
            str3 = new String(bArrA);
        }
        if (mA() == -14) {
            this.adk += "Could not init local data,";
        }
        mH();
        int iMz = mz();
        String strMI = iMz > 0 ? mI() : null;
        if (strMI != null && iMz > 0) {
            bS(strMI);
            this.adp = true;
            int iAw = bg.nv().aw(strMI);
            if (iAw != 0) {
                this.adk += "OfflineItems failed to initialize with the cached game config, err: ";
                this.adk += iAw;
                this.adk += ",";
                if (bg.nv().aw(str3) != 0) {
                    this.adk += "Error initializing OfflineItems with the default game config,";
                    this.adi = true;
                } else {
                    this.adk += "OfflineItems initialized with the default game config\n";
                    this.adm = true;
                }
            } else {
                this.adk += "OfflineItems initialized with the cached game config,";
                this.adm = true;
            }
            int iBV = au.mN().bV(strMI);
            if (iBV != 0) {
                this.adi = true;
                this.adk += "IAP failed to initialize with the cached game config, error: ";
                this.adk += iBV;
                this.adk += ",";
            } else {
                this.adk += "IAP initialized with the cached game config,";
                this.ads = true;
            }
            au.mN().eB(2);
            i.ic().ie();
            int crmResult;
            try {
                crmResult = i.ic().aw(strMI);
            } catch (Exception failure) {
                throw ao.<RuntimeException>propagateChecked(failure);
            }
            if (crmResult != 0) {
                this.adk += "\nCrmManager failed to initialize,";
            } else {
                this.adn = true;
            }
        } else if (str3 != null) {
            this.adk += "Error getting a stored config, the CrmManager will not be Initialized and Offline Items and IAP will be using the default game config \n";
            bS(str3);
            this.adp = true;
            if (bg.nv().aw(str3) != 0) {
                this.adk += "Error initializing OfflineItems with the default game config,";
                this.adi = true;
            } else {
                this.adm = true;
            }
            if (au.mN().bV(str3) != 0) {
                this.adk += "Error initializing IAP with the default game config \n";
            }
            au.mN().eB(2);
        } else {
            au.mN().eB(2);
        }
        if (s.fZ()) {
            bz bzVarOm = bz.om();
            if (bzVarOm.fZ()) {
                String strJv = s.jv();
                bc bcVar = new bc();
                bzVarOm.a("credential", bcVar);
                if (!strJv.equals((String) bcVar.cd("credential"))) {
                    mG();
                    bz.Release();
                    bz bzVarOm2 = bz.om();
                    a(bzVarOm2, "me");
                    if (bzVarOm2.mv() != 0) {
                        this.adi = true;
                        this.adk += "UserProfile failed to initialize with error ";
                        this.adk += bzVarOm2.mv();
                        this.adk += ",";
                    } else {
                        this.ado = true;
                        s.b(str2, com.gameloft.android.wrapper.au.Pn(), com.gameloft.android.wrapper.au.Pl(), com.gameloft.android.wrapper.au.getDeviceName(), "hestiaSetDeviceInfo");
                        C(t.No, "hestiaSetDeviceInfo");
                    }
                }
            } else {
                a(bzVarOm, "me");
                if (bzVarOm.mv() != 0) {
                    this.adi = true;
                    this.adk += "UserProfile failed to initialize with error ";
                    this.adk += bzVarOm.mv();
                    this.adk += ",";
                } else {
                    this.ado = true;
                    s.b(str2, com.gameloft.android.wrapper.au.Pn(), com.gameloft.android.wrapper.au.Pl(), com.gameloft.android.wrapper.au.getDeviceName(), "hestiaSetDeviceInfo");
                    C(t.No, "hestiaSetDeviceInfo");
                }
            }
        }
        return 0;
    }

    public void a(int i, String str, String str2, int i2, String str3, Exception exc) {
        if (str2.startsWith("hestia")) {
            this.adb = i;
            this.adc = str;
            this.ada = str2;
            this.add = i2;
            this.ade = exc;
        }
    }

    public boolean ev(int i) {
        au.mN().eB(1);
        return a(new Object[]{new Integer(1), new Integer(i)});
    }

    public int ex(int i) throws Exception {
        String str;
        int i2;
        int i3;
        int iE;
        this.adi = false;
        this.adj = false;
        this.adk = "";
        this.adl = false;
        acS = false;
        mA();
        mH();
        int i4 = 0;
        int i5 = 0;
        int i6 = i;
        while (i4 < i6) {
            if (acV.size() > i4) {
                String str2 = (String) ((Vector) acV.elementAt(i4)).elementAt(2);
                str = str2.equals("null") ? null : str2;
            } else {
                str = null;
            }
            if (i4 == 0) {
                s.b(null, "myprofile", "default", null, str, "hestiaRefreshConfig");
                C(t.Pz, "hestiaRefreshConfig");
                i2 = i4;
                i3 = i6;
            } else if (acV.size() < i4) {
                s.b(null, "myprofile", "default", null, str, "hestiaRefreshConfig");
                C(t.Pz, "hestiaRefreshConfig");
                i2 = i4 - 1;
                i3 = i6 - 1;
            } else {
                long j = Long.parseLong((String) ((Vector) acV.elementAt(i4 - 1)).elementAt(0));
                if (j == 2145938400) {
                    break;
                }
                s.b(null, null, null, bT(String.valueOf(j + 150)), str, "hestiaRefreshConfig");
                C(t.Pz, "hestiaRefreshConfig");
                i2 = i4;
                i3 = i6;
            }
            int iMv = mv();
            if (iMv == 304) {
                if (i2 == 0) {
                    acS = true;
                }
                this.adj = true;
                this.adl = false;
                iE = i5;
            } else if (iMv == 0) {
                if (str != null && !str.equals("null")) {
                    ey(i2);
                }
                String str3 = (String) s.jj().get("Etag");
                if (str3 == null) {
                    str3 = "null";
                }
                iE = E(this.adc, str3);
                if (iE != 0) {
                    this.adj = false;
                    this.adi = true;
                    this.adk += "Config not stored";
                    this.adl = false;
                } else if (i2 == 0) {
                    acS = true;
                    this.adl = true;
                }
                this.adj = true;
            } else {
                if (i2 == 0) {
                    this.adj = false;
                    this.adi = true;
                    this.adk += "Failed to download config, error: " + iMv;
                    this.adl = false;
                    acS = true;
                }
                iE = i5;
            }
            i4 = i2 + 1;
            i5 = iE;
            i6 = i3;
        }
        mB();
        return i5;
    }

    public boolean m(String str, String str2, String str3) {
        au.mN().eB(1);
        this.acR = str;
        return a(new Object[]{new Integer(0), str2, str3});
    }

    public int mD() throws Exception {
        this.adq = false;
        this.adr = false;
        this.ads = false;
        int iMz = mz();
        if (iMz == -11) {
            return -11;
        }
        if (iMz == 0) {
            this.adi = true;
            this.adk += "No cached config available";
            this.adq = false;
            this.adr = false;
            this.ads = false;
            return -12;
        }
        String strMI = mI();
        if (strMI == null) {
            return -12;
        }
        bS(strMI);
        this.adt = true;
        int iCs = bg.nv().cs(strMI);
        if (iCs != 0) {
            this.adi = true;
            this.adk += "Failed to refresh offline items with cached config, error: " + iCs;
            this.adq = false;
        } else {
            this.adq = true;
        }
        int iBV = au.mN().bV(strMI);
        if (iBV != 0) {
            this.adi = true;
            this.adk += "Failed to refresh IAP with cached config, error: " + iBV;
            this.ads = false;
        } else {
            this.ads = true;
        }
        au.mN().eB(2);
        i.ic().ie();
        int iAw = i.ic().aw(strMI);
        if (iAw == 0) {
            this.adr = true;
            return iAw;
        }
        this.adk += "Failed to refresh crm manager with cached config, error: " + iAw;
        this.adr = false;
        return iAw;
    }

    public void mH() {
        long jIR = s.iR() / 1000;
        int i = 0;
        while (i < acV.size()) {
            Vector vector = (Vector) acV.elementAt(i);
            if (Long.parseLong((String) vector.elementAt(0)) < jIR) {
                try {
                    GameRuntime.bG((String) vector.elementAt(1));
                } catch (Exception e) {
                }
                acV.removeElementAt(i);
                i--;
            }
            i++;
        }
        mE();
    }

    public String mI() {
        byte[] bArrBF = GameRuntime.bF((String) ((Vector) acV.elementAt(0)).elementAt(1));
        if (bArrBF == null) {
            adh = -5;
            return null;
        }
        byte[] bArrA = a(bArrBF, this.acR);
        if (bArrA != null) {
            return new String(bArrA);
        }
        adh = -3;
        return null;
    }

    public String mt() {
        return this.acR;
    }

    public bc mu() {
        return this.acQ;
    }

    public int mv() {
        return adh;
    }

    public boolean mw() {
        return this.adm;
    }

    public boolean mx() {
        return this.adp;
    }

    public int my() {
        if (!this.adw) {
            return -1;
        }
        this.adw = false;
        return this.adv;
    }

    public int mz() {
        if (!acS) {
            return -11;
        }
        if (mA() != 0) {
            return 0;
        }
        return acV.size();
    }
    // Original DEX synthetic accessors, inlined away by JADX.
    static int a(ao owner) { return owner.adv; }
    static int a(ao owner, int value) { return owner.ew(value); }
    static boolean a(ao owner, boolean value) { owner.adu = value; return value; }
    static boolean b(ao owner, boolean value) { owner.adw = value; return value; }
    static int access$302(int value) { adh = value; return value; }
}
