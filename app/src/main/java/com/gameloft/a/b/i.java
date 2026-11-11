package com.gameloft.a.b;

import com.facebook.share.internal.ShareConstants;
import com.gameloft.a.a.n;
import com.gameloft.adsmanager.AdsManager;
import com.gameloft.glads.GLAds;
import java.io.IOException;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: JW_PrivacyPolicyChecker.java */
/* JADX INFO: loaded from: classes.dex */
public class i {
    static String bgt = "OF_PP.bin";
    static String bxk = "m_age";
    static String bxl = "m_temporaryAge";
    static String bxm = "m_privacyReceived";
    static String bxn = "m_privacyRequestedByGame";
    static String bxo = "m_privacyPolicyRawAnswer";
    static String bxp = "m_country";
    static String bxq = ShareConstants.WEB_DIALOG_PARAM_DATA;
    static String bxr = "compliant";
    static String bxs = "COPPA";
    static String bxt = "GDPR";
    private static int[] bxu = {16432350, -559038801, -1095770451, 303305746};
    private static byte[] bxv = null;
    static i bxw = null;
    k bxj = null;
    private m bxe = new m(this);
    String bxg = "";
    int bxf = 0;
    boolean bxh = false;
    boolean bxi = false;

    public static i Wv() {
        if (bxw == null) {
            bxw = new i();
        }
        return bxw;
    }

    public i() {
        Init();
    }

    public boolean hv(String str) {
        return (!str.equalsIgnoreCase(this.bxe.bxz) || this.bxe.bxz.isEmpty() || this.bxe.bxA.isEmpty()) ? false : true;
    }

    public m Ww() {
        return this.bxe;
    }

    public l a(int i, String str, k kVar) {
        if (!a.fZ()) {
            return new l(this, 4);
        }
        if (this.bxj != null) {
            return new l(this, 3);
        }
        this.bxj = kVar;
        l lVar = new l(this, 0);
        a(false, i, str, "");
        if (Wz().getValue() != 0) {
            lVar = new l(this, 1);
        }
        a.d(str, i, "lib_janus_privacy_policy");
        return lVar;
    }

    public void b(g gVar) {
        if (gVar.bxb == 200) {
            c(gVar);
        }
        this.bxj = null;
    }

    private void Init() {
        Wy();
        bQ(false);
    }

    private byte[] Wx() {
        int i = 0;
        if (bxv == null) {
            bxv = new byte[bxu.length * 4];
            int i2 = 0;
            while (true) {
                int i3 = i;
                if (i2 >= bxu.length) {
                    break;
                }
                int i4 = bxu[i2];
                int i5 = i3 + 1;
                bxv[i3] = (byte) (i4 >> 24);
                int i6 = i5 + 1;
                bxv[i5] = (byte) (i4 >> 16);
                int i7 = i6 + 1;
                bxv[i6] = (byte) (i4 >> 8);
                i = i7 + 1;
                bxv[i7] = (byte) i4;
                i2++;
            }
        }
        return bxv;
    }

    private void a(l lVar, m mVar, String str) {
        if (this.bxj != null) {
            this.bxj.a(lVar, mVar);
            this.bxj = null;
        }
    }

    private l K(JSONObject jSONObject) {
        l lVar = new l(this, 0);
        if (jSONObject == null || !jSONObject.has(bxr)) {
            return new l(this, 7);
        }
        return lVar;
    }

    private void c(g gVar) {
        int i = gVar.bxb;
        m mVar = new m(this);
        if (i != 200) {
            a(new l(this, 6), mVar, "Received invalid response from Janus!");
            return;
        }
        String str = gVar.bxa;
        if (str.isEmpty()) {
            a(new l(this, 6), mVar, "Received empty response!");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                l lVarK = K(jSONObject.optJSONObject(next));
                if (lVarK.getValue() != 0) {
                    a(lVarK, mVar, "Received unknown json! JSON: \"" + str + "\"");
                    return;
                } else if (!L(jSONObject.optJSONObject(next))) {
                    this.bxf = 0;
                    break;
                }
            }
            a(true, this.bxf, this.bxg, str);
            a(new l(this, 0), this.bxe, "SUCCESS. Sent game callback.");
            bQ(true);
            Wz();
        } catch (JSONException e) {
            a(new l(this, 7), mVar, "Received unknown json! JSON: " + str);
        }
    }

    private boolean L(JSONObject jSONObject) {
        return jSONObject.optBoolean(bxr);
    }

    private l Wy() {
        byte[] bArrHl = n.hl(bgt);
        if (bArrHl == null) {
            WA();
            return new l(this, 2);
        }
        try {
            byte[] bArrB = n.b(n.bJ(new String(bArrHl)), Wx(), true);
            if (bArrB == null) {
                WA();
                return new l(this, 2);
            }
            try {
                JSONObject jSONObject = new JSONObject(new String(bArrB));
                if (!jSONObject.has(bxk) || !jSONObject.has(bxo) || !jSONObject.has(bxp) || !jSONObject.has(bxm) || !jSONObject.has(bxn)) {
                    return new l(this, 2);
                }
                this.bxe.bxy = jSONObject.optInt(bxk);
                this.bxe.bxA = jSONObject.optString(bxo);
                this.bxe.bxz = jSONObject.optString(bxp);
                this.bxh = jSONObject.optBoolean(bxm);
                this.bxi = jSONObject.optBoolean(bxn);
                return new l(this, 0);
            } catch (JSONException e) {
                return new l(this, 2);
            }
        } catch (IOException e2) {
            WA();
            return new l(this, 2);
        }
    }

    private l Wz() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(bxk, this.bxe.bxy);
            jSONObject.put(bxo, this.bxe.bxA);
            jSONObject.put(bxp, this.bxe.bxz);
            jSONObject.put(bxm, this.bxh);
            jSONObject.put(bxn, this.bxi);
        } catch (Exception e) {
        }
        try {
            String strE = n.e(n.a(jSONObject.toString().getBytes(), Wx(), true));
            if (strE == null) {
                return new l(this, 1);
            }
            if (!n.f(bgt, strE.getBytes())) {
                return new l(this, 1);
            }
            return new l(this, 0);
        } catch (Exception e2) {
            return new l(this, 1);
        }
    }

    private void WA() {
        this.bxe.bxy = 0;
        this.bxe.bxA = "";
        this.bxe.bxz = "";
        this.bxf = 0;
        this.bxh = false;
        this.bxi = false;
        this.bxg = "";
        Wz();
    }

    private void a(boolean z, int i, String str, String str2) {
        if (z) {
            this.bxe.bxy = i;
            this.bxe.bxA = str2;
            this.bxe.bxz = str;
            this.bxf = 0;
            this.bxh = true;
            this.bxi = false;
            return;
        }
        this.bxf = i;
        this.bxh = false;
        this.bxi = true;
        this.bxg = str;
    }

    public void bQ(boolean z) {
        if (this.bxh) {
            GLAds.SetAge(this.bxe.bxy);
        }
        AdsManager.SetAge(this.bxe.bxy);
    }
}
