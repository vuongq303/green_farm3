package com.gameloft.a.a;

import com.facebook.appevents.AppEventsConstants;
import com.gameloft.android.wrapper.au;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Hestia.java */
/* JADX INFO: loaded from: classes.dex */
public class o {
    private static Vector acV;
    private static int adh;
    private String acR;
    private String adc;
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
    JSONObject bws;
    JSONArray bwt;
    private static boolean acS = true;
    private static boolean acT = false;
    private static boolean acU = true;
    private static boolean adf = false;
    static o bwu = null;
    private static boolean bwv = true;
    private static q bwx = null;
    private final int acW = 0;
    private final int acX = 1;
    private final int acY = 2;
    private final int acZ = 2145938400;
    private String bww = null;
    private boolean adu = false;
    private boolean adw = false;

    public static void mr() {
        adf = true;
    }

    public static o VW() {
        if (bwu == null) {
            bwu = new o();
        }
        return bwu;
    }

    public void bO(boolean z) {
        bwv = !z;
    }

    public o() {
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
        this.bws = new JSONObject();
        this.bwt = null;
    }

    public String mt() {
        return this.acR;
    }

    private void bS(String str) {
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("game")) {
                    this.bws = jSONObject.getJSONObject("game");
                }
            } catch (JSONException e) {
            }
        }
    }

    public JSONObject VX() {
        return this.bws;
    }

    private void hn(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("newsfeeds")) {
                this.bwt = jSONObject.getJSONArray("newsfeeds");
            }
        } catch (JSONException e) {
        }
    }

    public int mv() {
        return adh;
    }

    public boolean VY() {
        return this.adn;
    }

    public int my() {
        if (!this.adw) {
            return -1;
        }
        this.adw = false;
        return this.adv;
    }

    private boolean a(Object[] objArr) {
        if (this.adu) {
            return false;
        }
        this.adu = true;
        this.adv = ((Integer) objArr[0]).intValue();
        new p(this, objArr).start();
        return true;
    }

    public boolean m(String str, String str2, String str3) {
        this.acR = str;
        return a(new Object[]{0, str2, str3});
    }

    public boolean ev(int i) {
        return a(new Object[]{1, Integer.valueOf(i)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int ew(int i) {
        return VW().ex(i);
    }

    private void a(x xVar, String str) {
        xVar.J("me", this.acR);
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

    /* JADX INFO: Access modifiers changed from: private */
    public int D(String str, String str2) {
        InputStream inputStreamOpen;
        byte[] bArr;
        boolean z;
        byte[] bArr2;
        byte[] bArrA;
        String string;
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
        try {
            inputStreamOpen = au.getContext().getAssets().open(str);
        } catch (IOException e) {
            inputStreamOpen = null;
        }
        try {
            bArr2 = new byte[inputStreamOpen.available()];
            try {
                inputStreamOpen.read(bArr2);
                inputStreamOpen.close();
                z = true;
            } catch (Exception e2) {
                bArr = bArr2;
                z = false;
                bArr2 = bArr;
            }
        } catch (Exception e3) {
            bArr = null;
        }
        if (z) {
            try {
                bArrA = a(bArr2, this.acR);
            } catch (Exception e4) {
                e4.printStackTrace();
                bArrA = null;
            }
            if (bArrA == null) {
                this.adi = true;
                this.adk += "Default config failed to decrypt,";
            } else {
                this.bww = new String(bArrA);
            }
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
            hn(strMI);
            int iAw = t.Wc().aw(strMI);
            if (iAw != 0) {
                this.adk += "OfflineItems failed to initialize with the cached game config, err: ";
                this.adk += iAw;
                this.adk += ",";
                if (this.bww != null) {
                    if (t.Wc().aw(this.bww) != 0) {
                        this.adk += "Error initializing OfflineItems with the default game config,";
                        this.adi = true;
                    } else {
                        this.adk += "OfflineItems initialized with the default game config\n";
                        this.adm = true;
                    }
                }
            } else {
                this.adk += "OfflineItems initialized with the cached game config,";
                this.adm = true;
            }
            k.VR().ie();
            if (k.VR().q(strMI, true) != 0) {
                this.adk += "\nCrmManager failed to initialize,";
            } else {
                this.adn = true;
            }
        } else if (this.bww != null) {
            this.adk += "Error getting a stored config, the CrmManager will not be Initialized and Offline Items and IAP will be using the default game config \n";
            bS(this.bww);
            this.adp = true;
            if (t.Wc().aw(this.bww) != 0) {
                this.adk += "Error initializing OfflineItems with the default game config,";
                this.adi = true;
            } else {
                this.adm = true;
            }
            if (k.VR().q(this.bww, false) != 0) {
                this.adk += "\nCrmManager failed to initialize,";
            } else {
                this.adn = true;
            }
        }
        if (com.gameloft.a.b.a.fZ()) {
            x xVarWf = x.Wf();
            if (xVarWf.fZ()) {
                String strJs = com.gameloft.a.b.a.js();
                JSONObject jSONObject = new JSONObject();
                xVarWf.e("credential", jSONObject);
                try {
                    string = jSONObject.getString("credential");
                } catch (JSONException e5) {
                    string = null;
                }
                if (!strJs.equals(string)) {
                    mG();
                    x.Release();
                    x xVarWf2 = x.Wf();
                    a(xVarWf2, "me");
                    if (xVarWf2.mv() != 0) {
                        this.adi = true;
                        this.adk += "UserProfile failed to initialize with error ";
                        this.adk += xVarWf2.mv();
                        this.adk += ",";
                    } else {
                        this.ado = true;
                        String strPo = !au.Pm() ? au.Po() : null;
                        if (strPo == null || strPo.isEmpty()) {
                            strPo = au.Pl();
                        }
                        String deviceName = au.getDeviceName();
                        String strPn = au.Pn();
                        if (strPn == null || strPn.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                            strPn = "NULL";
                        }
                        com.gameloft.a.b.a.a(str2, strPn, strPo, deviceName, null, null, null, "hestiaSetDeviceInfo");
                    }
                }
            } else {
                a(xVarWf, "me");
                if (xVarWf.mv() != 0) {
                    this.adi = true;
                    this.adk += "UserProfile failed to initialize with error ";
                    this.adk += xVarWf.mv();
                    this.adk += ",";
                } else {
                    this.ado = true;
                    String strPo2 = !au.Pm() ? au.Po() : null;
                    if (strPo2 == null || strPo2.isEmpty()) {
                        strPo2 = au.Pl();
                    }
                    String deviceName2 = au.getDeviceName();
                    String strPn2 = au.Pn();
                    if (strPn2 == null || strPn2.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                        strPn2 = "NULL";
                    }
                    com.gameloft.a.b.a.a(str2, strPn2, strPo2, deviceName2, null, null, null, "hestiaSetDeviceInfo");
                }
            }
        }
        return 0;
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

    private void mB() {
        if (!this.adi) {
        }
        if (this.adl) {
            VW().mD();
        }
    }

    private int ex(int i) {
        com.gameloft.a.b.g gVarK;
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
        while (i5 < i6) {
            String str = null;
            if (acV.size() > i5) {
                String str2 = (String) ((Vector) acV.elementAt(i5)).elementAt(2);
                str = str2.equals("null") ? null : str2;
            }
            if (i5 == 0) {
                gVarK = com.gameloft.a.b.a.k(null, "myprofile", "default", null, str, "hestiaRefreshConfig");
                if (gVarK == null) {
                    adh = -1;
                    i2 = i5;
                    i3 = i6;
                } else {
                    this.adc = gVarK.bxa;
                    adh = gVarK.bxb;
                    if (adh == 200) {
                        adh = 0;
                        i2 = i5;
                        i3 = i6;
                    } else {
                        i2 = i5;
                        i3 = i6;
                    }
                }
            } else if (acV.size() < i5) {
                int i7 = i5 - 1;
                int i8 = i6 - 1;
                gVarK = com.gameloft.a.b.a.k(null, "myprofile", "default", null, str, "hestiaRefreshConfig");
                if (gVarK == null) {
                    adh = -1;
                    i2 = i7;
                    i3 = i8;
                } else {
                    this.adc = gVarK.bxa;
                    adh = gVarK.bxb;
                    i2 = i7;
                    i3 = i8;
                }
            } else {
                long j = Long.parseLong((String) ((Vector) acV.elementAt(i5 - 1)).elementAt(0));
                if (j == 2145938400) {
                    break;
                }
                gVarK = com.gameloft.a.b.a.k(null, null, null, bT(String.valueOf(j + 150)), str, "hestiaRefreshConfig");
                if (gVarK == null) {
                    adh = -1;
                    i2 = i5;
                    i3 = i6;
                } else {
                    this.adc = gVarK.bxa;
                    adh = gVarK.bxb;
                    if (adh == 200) {
                        adh = 0;
                    }
                    i2 = i5;
                    i3 = i6;
                }
            }
            int iMv = mv();
            bO(true);
            if (iMv == 304) {
                if (i2 == 0) {
                    acS = true;
                }
                this.adj = true;
                this.adl = false;
                iE = i4;
            } else if (iMv == 0) {
                if (str != null && !str.equals("null")) {
                    ey(i2);
                }
                String str3 = gVarK != null ? gVarK.bxc : null;
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
                bO(false);
                if (i2 == 0) {
                    this.adj = false;
                    this.adi = true;
                    this.adk += "Failed to download config, error: " + iMv;
                    this.adl = false;
                    acS = true;
                }
                iE = i4;
            }
            i5 = i2 + 1;
            i4 = iE;
            i6 = i3;
        }
        mB();
        return i4;
    }

    public int mD() {
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
        hn(strMI);
        int iCs = t.Wc().cs(strMI);
        if (iCs != 0) {
            this.adi = true;
            this.adk += "Failed to refresh offline items with cached config, error: " + iCs;
            this.adq = false;
        } else {
            this.adq = true;
        }
        k.VR().ie();
        int iQ = k.VR().q(strMI, true);
        if (iQ != 0) {
            this.adk += "Failed to refresh crm manager with cached config, error: " + iQ;
            this.adr = false;
            return iQ;
        }
        this.adr = true;
        return iQ;
    }

    public int VZ() {
        if (this.bww == null) {
            return 0;
        }
        this.adq = false;
        this.adr = false;
        this.ads = false;
        bS(this.bww);
        this.adt = true;
        int iCs = t.Wc().cs(this.bww);
        if (iCs != 0) {
            this.adi = true;
            this.adk += "Failed to refresh offline items with default config, error: " + iCs;
            this.adq = false;
        } else {
            this.adq = true;
        }
        k.VR().ie();
        int iQ = k.VR().q(this.bww, true);
        if (iQ != 0) {
            this.adk += "Failed to refresh crm manager with default config, error: " + iQ;
            this.adr = false;
        } else {
            this.adr = true;
        }
        return iQ;
    }

    private byte[] a(byte[] bArr, String str) {
        try {
            byte[] bArrB = n.b(bArr, str, true);
            if (bArrB == null) {
                adh = -3;
                return null;
            }
            int iL = n.l(bArrB, 0);
            byte[] bArr2 = new byte[(bArrB.length - iL) - 4];
            n.b(bArrB, iL + 4, bArr2);
            try {
                String str2 = new String(bArr2, "ISO-8859-1");
                byte[] bArr3 = new byte[iL + 4];
                System.arraycopy(bArrB, 0, bArr3, 0, bArr3.length);
                n.kM();
                try {
                    n.A(new String(bArr3, "ISO-8859-1"), "ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (!str2.equals(n.kP())) {
                    adh = -4;
                    return null;
                }
                byte[] bArr4 = new byte[iL];
                n.b(bArr3, 4, bArr4);
                adh = 0;
                return bArr4;
            } catch (Exception e2) {
                return null;
            }
        } catch (Exception e3) {
            adh = -3;
            return null;
        }
    }

    private byte[] b(byte[] bArr, String str) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 4];
        n.d(bArr2, 0, length);
        n.a(bArr2, 4, bArr);
        n.kM();
        try {
            n.A(new String(bArr2, "ISO-8859-1"), "ISO-8859-1");
            try {
                byte[] bytes = n.kP().getBytes("ISO-8859-1");
                byte[] bArr3 = new byte[length + 4 + bytes.length];
                n.d(bArr3, 0, length);
                n.a(bArr3, 4, bArr);
                n.a(bArr3, length + 4, bytes);
                try {
                    return n.a(bArr3, str, true);
                } catch (Exception e) {
                    return null;
                }
            } catch (Exception e2) {
                return null;
            }
        } catch (UnsupportedEncodingException e3) {
            return null;
        }
    }

    private int mE() {
        String str = "";
        for (int i = 0; i < acV.size(); i++) {
            Vector vector = (Vector) acV.elementAt(i);
            str = (((((str + vector.elementAt(0)) + ";") + vector.elementAt(1)) + ";") + vector.elementAt(2)) + ";";
        }
        if (!acV.isEmpty()) {
            byte[] bArrB = b(str.getBytes(), this.acR);
            if (bArrB == null) {
                return -2;
            }
            try {
                n.f("filesConfig", bArrB);
                return 0;
            } catch (Exception e) {
                return -6;
            }
        }
        if (au.es("filesConfig")) {
            au.er("filesConfig");
        }
        return -12;
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

    private int mF() {
        byte[] bArrHl;
        byte[] bArrA;
        if (!au.es("filesConfig") || (bArrHl = n.hl("filesConfig")) == null) {
            return -7;
        }
        try {
            bArrA = a(bArrHl, this.acR);
        } catch (Exception e) {
            e.printStackTrace();
            bArrA = null;
        }
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

    private int E(String str, String str2) {
        String str3;
        long jBU;
        if (str == null || str.equals("[]")) {
            return -12;
        }
        try {
            Object obj = new JSONObject(str).get("expiry");
            if (obj == null) {
                return 3;
            }
            if (obj == JSONObject.NULL) {
                str3 = "null";
            } else {
                str3 = (String) obj;
            }
            byte[] bArrB = b(str.getBytes(), this.acR);
            if (bArrB == null) {
                return -2;
            }
            if (acU) {
                if (str3.length() > 0 && !str3.equals("null")) {
                    jBU = bU(str3);
                } else {
                    jBU = 2145938400;
                }
                String str4 = "config" + jBU;
                Vector vector = new Vector();
                vector.addElement(String.valueOf(jBU));
                vector.addElement(str4);
                vector.addElement(str2);
                c(vector);
                mE();
                try {
                    n.f(str4, bArrB);
                } catch (Exception e) {
                    return -6;
                }
            }
        } catch (JSONException e2) {
        }
        return 0;
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

    private int mG() {
        return ey(0);
    }

    private int ey(int i) {
        while (i < acV.size()) {
            try {
                au.er((String) ((Vector) acV.elementAt(i)).elementAt(1));
            } catch (Exception e) {
            }
            acV.removeElementAt(i);
            i = (i - 1) + 1;
        }
        return mE();
    }

    public void mH() {
        long jIR = com.gameloft.a.b.a.iR() / 1000;
        int i = 0;
        while (i < acV.size()) {
            Vector vector = (Vector) acV.elementAt(i);
            if (Long.parseLong((String) vector.elementAt(0)) < jIR) {
                try {
                    au.er((String) vector.elementAt(1));
                } catch (Exception e) {
                }
                acV.removeElementAt(i);
                i--;
            }
            i++;
        }
        mE();
        if (acV.size() == 0) {
            VZ();
        }
    }

    public String mI() {
        byte[] bArrA;
        byte[] bArrHl = n.hl((String) ((Vector) acV.elementAt(0)).elementAt(1));
        if (bArrHl == null) {
            adh = -5;
            return null;
        }
        try {
            bArrA = a(bArrHl, this.acR);
        } catch (Exception e) {
            e.printStackTrace();
            bArrA = null;
        }
        if (bArrA == null) {
            adh = -3;
            return null;
        }
        return new String(bArrA);
    }
}
