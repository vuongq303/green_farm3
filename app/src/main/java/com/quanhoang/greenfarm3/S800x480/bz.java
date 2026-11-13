package com.quanhoang.greenfarm3.S800x480;

import com.facebook.appevents.UserDataStore;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import java.io.IOException;

/* JADX INFO: compiled from: UserProfile.java */
/* JADX INFO: loaded from: classes.dex */
public class bz {
    private static String ada;
    private static String adc;
    private static int add;
    private static int adh;
    private static int adv;
    private String Kv;
    private String akS;
    private String akT;
    static bz akQ = null;
    private static boolean adu = false;
    private static boolean adw = false;
    private static int adb = -1;
    private static Exception ade = null;
    private boolean akR = false;
    private String akU = null;

    public int mv() {
        return adh;
    }

    public static bz om() {
        if (!w.ZU) {
            return null;
        }
        if (akQ == null) {
            akQ = new bz();
        }
        return akQ;
    }

    public static void Release() {
        akQ = null;
    }

    public int my() {
        if (!w.ZU || !adw) {
            return -1;
        }
        adw = false;
        return adv;
    }

    public void a(int i, String str, String str2, int i2, String str3, Exception exc) {
        if (str2.startsWith("std")) {
            adb = i;
            adc = str;
            ada = str2;
            add = i2;
            ade = exc;
        }
    }

    private void mC() {
        adb = -1;
        adc = "";
        ada = "";
        ade = null;
    }

    private void C(int i, String str) {
        if (w.ZU) {
            mC();
            boolean z = true;
            do {
                if (adb == i && ada.equals(str)) {
                    if (add > 200) {
                        adh = add;
                        return;
                    }
                    if (ade != null) {
                        if ("java.io.IOException".equals(ade.getClass().getName()) || "java.net.ConnectException".equals(ade.getClass().getName())) {
                            GameRuntime.be("No Internet connection");
                        }
                        adh = -1;
                        return;
                    }
                    if (ade != null) {
                        if ("java.io.IOException".equals(ade.getClass().getName()) || "java.net.ConnectException".equals(ade.getClass().getName())) {
                            GameRuntime.be("No Internet connection");
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
    }

    private boolean a(Object[] objArr) {
        if (!w.ZU || adu) {
            return false;
        }
        adu = true;
        adv = ((Integer) objArr[0]).intValue();
        new ca(this, objArr).start();
        return true;
    }

    public boolean fZ() {
        return this.akR;
    }

    public boolean J(String str, String str2) {
        if (!w.ZU) {
            return false;
        }
        Object[] objArr = {new Integer(0), str};
        String strMt = ao.ms().mt();
        if (strMt == null) {
            this.akU = str2;
        } else {
            this.akU = strMt;
        }
        return a(objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int cF(String str) {
        if (this.akR) {
            return 0;
        }
        if (!s.fZ()) {
            return -21;
        }
        this.akS = "";
        this.Kv = str;
        bc bcVar = new bc();
        bcVar.a("credential", "gllive:myuser");
        bcVar.a("created", "2012-01-10 13:37:17Z");
        bcVar.a("modified", "2012-01-10 13:37:17Z");
        bcVar.a("last_session", "2012-01-10 13:37:17Z");
        bcVar.t("total_spent", 123);
        bcVar.a("total_spent_currency", "eur");
        bcVar.a("last_purchase", "2012-01-10 13:37:17Z");
        bcVar.a("adid", "fsdfsfd");
        bcVar.a("operation", "fasdfsdf");
        bc[] bcVarArr = {new bc()};
        bcVarArr[0].a(ShareConstants.WEB_DIALOG_PARAM_ID, ShareConstants.WEB_DIALOG_PARAM_ID);
        bcVarArr[0].a("model", "Samsung_GT-I9000");
        bcVarArr[0].a("carrier", "WIFI Country Operation");
        bcVarArr[0].a("name", "ios");
        bcVarArr[0].a("currency", "usd");
        bc[] bcVarArr2 = {new bc(), new bc()};
        bcVarArr2[1].a("name", "creditcard");
        bcVarArr2[1].a("currency", "pes");
        bcVarArr[0].a("valid_stores", bcVarArr2);
        bcVarArr[0].a("download_code", "12312313");
        bcVar.a("devices", bcVarArr);
        bcVar.t("level", 3);
        bcVar.t("xp", Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
        bcVar.t("total_playtime", 144);
        bcVar.a(UserDataStore.COUNTRY, "ca");
        bcVar.a("language", "fr");
        bc bcVar2 = new bc();
        bcVar2.t("cash", 123);
        bcVar2.t("coins", 123);
        bcVar.a("inventory", bcVar2);
        this.akT = k(bcVar);
        int iOo = oo();
        if (iOo == 0) {
            this.akR = true;
            ao.mr();
            return iOo;
        }
        return iOo;
    }

    public int f(bc bcVar) {
        if (w.ZU) {
            if (!this.akR) {
                return -28;
            }
            return b(this.akS, bcVar);
        }
        return -1;
    }

    public boolean on() {
        if (w.ZU) {
            return a(new Object[]{new Integer(1)});
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int oo() {
        s.d(this.Kv, null, null, "stdRefreshGetProfile");
        C(t.Ow, "stdRefreshGetProfile");
        int iMv = mv();
        if (iMv != 0) {
            if (iMv == 404) {
                bc bcVar = new bc();
                bcVar.t("total_playtime", 0);
                s.f(this.Kv, null, bcVar.toString(), null, "set", "stdRefreshSetProfile");
                C(t.Ov, "stdRefreshSetProfile");
                int iMv2 = mv();
                if (iMv2 == 0) {
                    s.d(this.Kv, null, null, "stdRefreshGetProfile");
                    C(t.Ow, "stdRefreshGetProfile");
                    iMv = mv();
                    if (iMv != 0) {
                        return iMv;
                    }
                } else {
                    return iMv2;
                }
            } else {
                return iMv;
            }
        }
        String str = adc;
        if (str != null && str.length() > 0) {
            this.akS = k(new bc(str));
            String property = System.getProperty("DebugMode");
            return (property == null || property.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) ? iMv : iMv;
        }
        this.akS = "";
        return -30;
    }

    private int g(bc bcVar) {
        bc bcVar2 = new bc();
        int iB = b(this.akT, bcVar2);
        if (iB == 0) {
            bc bcVar3 = new bc();
            int iNo = bcVar.no();
            for (int i = 0; i < iNo; i++) {
                String str = bcVar.np()[i];
                if (str != null && (str.indexOf("_") == 0 || (bcVar2.cd(str) != null && bcVar.cd(str).getClass().isAssignableFrom(bcVar2.cd(str).getClass())))) {
                    bcVar3.a(str, bcVar.cd(str));
                }
            }
            bcVar.ca(bcVar3.toString());
            return 0;
        }
        return iB;
    }

    private int h(bc bcVar) {
        bc bcVar2 = new bc();
        bc bcVar3 = new bc();
        int iB = b(this.akS, bcVar2);
        if (iB == 0) {
            int iB2 = b(this.akT, bcVar3);
            if (iB2 == 0) {
                int iNo = bcVar.no();
                for (int i = 0; i < iNo; i++) {
                    String str = bcVar.np()[i];
                    if (str != null && (str.indexOf("_") == 0 || (bcVar3.cd(str) != null && bcVar.cd(str).getClass().isAssignableFrom(bcVar3.cd(str).getClass())))) {
                        bcVar2.a(str, bcVar.cd(str));
                    }
                }
                bcVar.ca(bcVar2.toString());
                return 0;
            }
            return iB2;
        }
        return iB;
    }

    private bc op() {
        bc bcVar = new bc();
        bc bcVar2 = new bc();
        if (b(this.akS, bcVar) == 0) {
            int iNo = bcVar.no();
            for (int i = 0; i < iNo; i++) {
                String str = bcVar.np()[i];
                if (str != null && str.indexOf("_") != 0) {
                    bcVar2.a(str, bcVar.cd(str));
                }
            }
            return bcVar2;
        }
        return bcVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int oq() {
        if (!this.akR) {
            return -28;
        }
        bc bcVarOp = op();
        s.aP("stdDelCTDelProfile");
        C(t.Oy, "stdDelCTDelProfile");
        int iMv = mv();
        if (iMv == 0) {
            s.f(this.Kv, null, bcVarOp.toString(), null, "set", "stdDelCTSetProfile");
            C(t.Ov, "stdDelCTSetProfile");
            int iMv2 = mv();
            if (iMv2 == 0) {
                return oo();
            }
            return iMv2;
        }
        return iMv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int or() {
        if (!this.akR) {
            return -28;
        }
        s.aP("stdDeleteProfile");
        C(t.Oy, "stdDeleteProfile");
        int iMv = mv();
        if (iMv == 0) {
            return oo();
        }
        return iMv;
    }

    public boolean i(bc bcVar) {
        return b(bcVar, (String) null);
    }

    public boolean b(bc bcVar, String str) {
        if (w.ZU) {
            return a(new Object[]{new Integer(4), bcVar, str});
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int c(bc bcVar, String str) {
        if (!this.akR) {
            return -28;
        }
        h(bcVar);
        s.f(this.Kv, null, bcVar.toString(), str, "set", "stdUpdateProfile");
        C(t.Ov, "stdUpdateProfile");
        int iMv = mv();
        if (iMv == 0) {
            return oo();
        }
        return iMv;
    }

    public boolean j(bc bcVar) {
        return d(bcVar, null);
    }

    public boolean d(bc bcVar, String str) {
        if (w.ZU) {
            return a(new Object[]{new Integer(5), bcVar, str});
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int e(bc bcVar, String str) {
        if (!this.akR) {
            return -28;
        }
        g(bcVar);
        s.f(this.Kv, null, bcVar.toString(), str, "merge", "stdMergeProfile");
        C(t.Ov, "stdMergeProfile");
        int iMv = mv();
        if (iMv == 0) {
            return oo();
        }
        return iMv;
    }

    public boolean f(bc bcVar, String str) {
        if (w.ZU) {
            return a(new Object[]{new Integer(6), bcVar, str});
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int g(bc bcVar, String str) {
        if (!this.akR) {
            return -28;
        }
        if (bcVar.no() <= 0) {
            return -29;
        }
        String str2 = bcVar.np()[0];
        bc bcVar2 = new bc();
        bcVar2.a(str2, bcVar.cd(str2));
        g(bcVar2);
        if (bcVar2.cd(str2).toString().toString().compareTo(bcVar.cd(str2).toString().toString()) == 0) {
            s.f(this.Kv, str2, bcVar2.cd(str2).toString(), str, "set", "stdSetProfileField");
            C(t.Ov, "stdSetProfileField");
            int iMv = mv();
            if (iMv == 0) {
                return oo();
            }
            return iMv;
        }
        return -31;
    }

    public int a(String str, bc bcVar) {
        if (w.ZU) {
            if (!this.akR) {
                return -28;
            }
            bc bcVar2 = new bc();
            int iB = b(this.akS, bcVar2);
            if (iB == 0) {
                if (bcVar2.cd(str) != null) {
                    bcVar.a(str, bcVar2.cd(str));
                    return iB;
                }
                return -27;
            }
            return iB;
        }
        return -1;
    }

    private String k(bc bcVar) {
        return GameRuntime.e(GameRuntime.a(bcVar.toString().getBytes(), this.akU, true));
    }

    private int b(String str, bc bcVar) {
        try {
            bcVar.ca(new String(GameRuntime.b(decodeBase64(str), this.akU, true)));
            return 0;
        } catch (IOException e) {
            return -24;
        }
    }
    // Original bJ call is protected by an IOException handler in DEX.
    private static byte[] decodeBase64(String value) throws IOException {
        return GameRuntime.bJ(value);
    }

    // Original DEX synthetic accessors, inlined away by JADX.
    static int a(bz owner) { return owner.oo(); }
    static int a(bz owner, bc profile, String value) { return owner.c(profile, value); }
    static boolean access$002(boolean value) { adu = value; return value; }
    static int access$302(int value) { adh = value; return value; }
    static boolean aq(boolean value) { adw = value; return value; }
    static int b(bz owner) { return owner.oq(); }
    static int b(bz owner, bc profile, String value) { return owner.e(profile, value); }
    static int b(bz owner, String value) { return owner.cF(value); }
    static int c(bz owner) { return owner.or(); }
    static int c(bz owner, bc profile, String value) { return owner.g(profile, value); }
    static int os() { return adv; }
}
