package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import java.util.Vector;
import org.json.JSONException;

/* JADX INFO: compiled from: CrmManager.java */
/* JADX INFO: loaded from: classes.dex */
class i {
    bc KL;
    String KN;
    public static int KJ = 0;
    private static boolean AX = false;
    private static i KK = null;
    private static String KQ = "CRMPopup";
    private static String KR = "";
    private static String KS = "CRMActionData";
    Vector KM = new Vector();
    Vector KO = new Vector();
    Vector KP = new Vector();

    public static i ic() {
        if (KK == null) {
            KK = new i();
        }
        return KK;
    }

    public void ie() {
        if (AX) {
            ij();
            this.KM.removeAllElements();
            this.KO.removeAllElements();
            this.KP.removeAllElements();
            AX = false;
        }
    }

    private int a(bc bcVar, String str) {
        GLLib.be("Call function: LogEventViaGLOT. Param: " + bcVar.toString());
        return 0;
    }

    public boolean a(boolean z, bc bcVar) throws JSONException {
        GLLib.be("Call function: ALogEventCallback. Param: " + bcVar.toString());
        if (bcVar.cb(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
            bc bcVar2 = (bc) bcVar.cd(ShareConstants.WEB_DIALOG_PARAM_DATA);
            if (!bcVar2.cb("pointcut_id") || bcVar2.cd("pointcut_id").toString().equals("")) {
                bcVar2.a("pointcut_id", bcVar.cd("pointcut_id").toString());
            }
        }
        bc bcVar3 = new bc();
        bcVar3.t(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, 12);
        bcVar3.a(ShareConstants.WEB_DIALOG_PARAM_DATA, bcVar);
        a(bcVar3, NativeProtocol.WEB_DIALOG_ACTION);
        b(z, bcVar);
        return true;
    }

    private boolean b(boolean z, bc bcVar) throws JSONException {
        GLLib.be("Call function: GlotWrapperCallback. Param: " + bcVar.toString());
        try {
            if (com.gameloft.android2d.d.b.Qm() == null) {
                GLLib.be("TrackingManager.GetManager(): null");
                return false;
            }
            if (!bcVar.cb("type")) {
                return false;
            }
            int i = Integer.parseInt(bcVar.cd("type").toString());
            if (!bcVar.cb(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
                return false;
            }
            bc bcVar2 = (bc) bcVar.cd(ShareConstants.WEB_DIALOG_PARAM_DATA);
            switch (i) {
                case 51852:
                    if (!bcVar2.cb("campaign_list")) {
                        return false;
                    }
                    if (bcVar2.cr("campaign_list")) {
                        try {
                            com.gameloft.android2d.d.b.Qm().b(i, bcVar2.ci("campaign_list"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    } else {
                        com.gameloft.android2d.d.b.Qm().b(i, new String[]{bcVar2.cd("campaign_list").toString()});
                    }
                    break;
                case 51853:
                    if (bcVar2.cb("pointcut_id") && bcVar2.cb("popup_id")) {
                        com.gameloft.android2d.d.b.Qm().b(i, new String[]{bcVar2.cd("pointcut_id").toString(), bcVar2.cd("popup_id").toString()});
                        break;
                    }
                    return false;
                case 51854:
                    if (bcVar2.cb("click_type") && bcVar2.cb("pointcut_id") && bcVar2.cb("popup_id")) {
                        com.gameloft.android2d.d.b.Qm().b(i, new String[]{"INT:" + bcVar2.cd("click_type").toString(), bcVar2.cd("pointcut_id").toString(), bcVar2.cd("popup_id").toString()});
                        break;
                    }
                    return false;
                case 51855:
                    if (bcVar2.cb("pointcut_id") && bcVar2.cb("reward_amount") && bcVar2.cb("reward_name")) {
                        com.gameloft.android2d.d.b.Qm().b(i, new String[]{bcVar2.cd("pointcut_id").toString(), "INT:" + bcVar2.cd("reward_amount").toString(), bcVar2.cd("reward_name").toString()});
                        break;
                    }
                    return false;
            }
            return true;
        } catch (NoClassDefFoundError e2) {
            GLLib.be(e2.toString());
            GLLib.be("Call function: GlotWrapperCallback: Please enable GLOTv3 feature in Java-Android Wrapper(SET USE_GLOT3=1).");
            return false;
        }
    }

    public int aw(String str) throws Exception {
        String[] strArrCi;
        bc[] bcVarArrCj;
        if (AX) {
            return 0;
        }
        bc bcVar = new bc(str);
        this.KO.removeAllElements();
        this.KP.removeAllElements();
        a(bcVar, "config_object");
        if (bcVar.cb("tags")) {
            try {
                strArrCi = bcVar.ci("tags");
            } catch (Exception e) {
                strArrCi = null;
            }
            if (strArrCi != null && strArrCi.length > 0) {
                this.KN = "";
                for (int i = 0; i < strArrCi.length; i++) {
                    if (i != 0) {
                        this.KN += ", ";
                    }
                    this.KN += strArrCi[i];
                }
                if (ax(this.KN)) {
                    bc bcVar2 = new bc();
                    bcVar2.t("type", 51852);
                    bc bcVar3 = new bc();
                    bcVar3.a("campaign_list", this.KN);
                    bcVar2.a(ShareConstants.WEB_DIALOG_PARAM_DATA, bcVar3);
                    a(12, true, bcVar2);
                }
            }
        }
        if (bcVar.cb("pointcut_actions")) {
            ig();
            m5if();
            try {
                bcVarArrCj = bcVar.cj("pointcut_actions");
            } catch (Exception e2) {
                bcVarArrCj = null;
            }
            int iB = b(bcVarArrCj);
            if (iB != 0) {
                return iB;
            }
            ik();
            AX = true;
            return 0;
        }
        return -44;
    }

    /* JADX INFO: renamed from: if, reason: not valid java name */
    private void m5if() {
        this.KM.removeAllElements();
    }

    private void ig() {
        this.KL = new bc("{\"pointcut_definitions\":{ \"launch\":[{\"p\":\"first_time\"}, {\"p\":\"resumed\"}],\"pause\":[],\"enter_section\":[{\"p\":\"section\"}],\"purchase\":[{\"p\":\"item\"}, {\"p\":\"quantity\"}, {\"p\":\"store\"}],\"level_up\":[{\"p\":\"level\"}],\"start_mission\":[{\"p\":\"mission\"}],\"abort_mission\":[{\"p\":\"mission\"}],\"finish_mission\":[{\"p\":\"mission\"},{\"p\":\"success\"}],\"unlock_achievement\":[{\"p\":\"trophy\"},{\"p\":\"first_time\"}],\"resource_empty\":[{\"p\":\"item\"}]}}");
    }

    private int b(bc[] bcVarArr) throws Exception {
        if (bcVarArr == null) {
            return -34;
        }
        for (int i = 0; i < bcVarArr.length; i++) {
            if (bcVarArr[i] == null) {
                return -34;
            }
            f fVar = new f();
            int iB = fVar.b(bcVarArr[i]);
            if (iB == 0) {
                this.KM.addElement(fVar);
                if (fVar.ia() == 6) {
                    String str = (String) fVar.ib().cd("popup_id");
                    boolean z = false;
                    for (int i2 = 0; i2 < this.KO.size(); i2++) {
                        if (((String) this.KO.elementAt(i2)).equals(str)) {
                            z = true;
                        }
                    }
                    if (!z) {
                        this.KO.addElement(str);
                    }
                }
                if (fVar.ia() == 7) {
                    String str2 = (String) fVar.ib().cd("tags");
                    boolean z2 = false;
                    for (int i3 = 0; i3 < this.KO.size(); i3++) {
                        if (((String) this.KO.elementAt(i3)).equals(str2)) {
                            z2 = true;
                        }
                    }
                    if (!z2) {
                        this.KO.addElement(str2);
                    }
                }
            } else {
                return iB;
            }
        }
        Vector vector = new Vector();
        ii();
        for (int i4 = 0; i4 < this.KP.size(); i4++) {
            String str3 = (String) this.KP.elementAt(i4);
            boolean z3 = false;
            for (int i5 = 0; i5 < this.KO.size(); i5++) {
                if (str3.equals((String) this.KO.elementAt(i5))) {
                    z3 = true;
                }
            }
            if (!z3) {
                vector.addElement(str3);
            }
        }
        a(vector);
        this.KP = this.KO;
        ih();
        return b(this.KO);
    }

    private int ih() {
        if (this.KP.size() > 0) {
            try {
                bc bcVar = new bc();
                bc[] bcVarArr = new bc[this.KP.size()];
                for (int i = 0; i < this.KP.size(); i++) {
                    bcVarArr[i] = new bc();
                    bcVarArr[i].a("json", (String) this.KP.elementAt(i));
                }
                bcVar.a(ShareConstants.WEB_DIALOG_PARAM_DATA, bcVarArr);
                GLLib.e(KQ, bcVar.toString().getBytes());
            } catch (Exception e) {
                return -32;
            }
        }
        return 0;
    }

    private int ii() throws Exception {
        bc[] bcVarArrCj;
        byte[] bArrBF = GLLib.bF(KQ);
        if (bArrBF == null) {
            return -33;
        }
        bc bcVar = new bc(new String(bArrBF));
        if (!bcVar.cb(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
            return 0;
        }
        try {
            bcVarArrCj = bcVar.cj(ShareConstants.WEB_DIALOG_PARAM_DATA);
        } catch (Exception e) {
            bcVarArrCj = null;
        }
        if (bcVarArrCj == null) {
            return 0;
        }
        for (bc bcVar2 : bcVarArrCj) {
            this.KP.addElement((String) bcVar2.cd("json"));
        }
        return 0;
    }

    private boolean ax(String str) {
        if (KR.equals(str)) {
            return false;
        }
        KR = str;
        return true;
    }

    private int ij() {
        if (this.KM.size() <= 0) {
            return 0;
        }
        bc bcVar = new bc();
        Vector vector = new Vector();
        bcVar.a("tags", this.KN);
        for (int i = 0; i < this.KM.size(); i++) {
            bc bcVarHZ = ((f) this.KM.elementAt(i)).hZ();
            if (bcVarHZ != null) {
                vector.addElement(bcVarHZ);
            }
        }
        if (vector.size() > 0) {
            bc[] bcVarArr = new bc[vector.size()];
            for (int i2 = 0; i2 < vector.size(); i2++) {
                bcVarArr[i2] = (bc) vector.elementAt(i2);
            }
            bcVar.a(ShareConstants.WEB_DIALOG_PARAM_DATA, bcVarArr);
        }
        try {
            GLLib.e(KS, bcVar.toString().getBytes());
            return 0;
        } catch (Exception e) {
            return -32;
        }
    }

    private int ik() throws Exception {
        byte[] bArrBF;
        bc[] bcVarArrCj;
        try {
            bArrBF = GLLib.bF(KS);
            if (bArrBF == null) {
                return -33;
            }
        } catch (Exception e) {
            bArrBF = null;
        }
        bc bcVar = new bc(new String(bArrBF));
        if (!bcVar.cb(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
            return 0;
        }
        try {
            bcVarArrCj = bcVar.cj(ShareConstants.WEB_DIALOG_PARAM_DATA);
        } catch (Exception e2) {
            bcVarArrCj = null;
        }
        if (bcVarArrCj == null) {
            return 0;
        }
        for (int i = 0; i < this.KM.size(); i++) {
            f fVar = (f) this.KM.elementAt(i);
            for (bc bcVar2 : bcVarArrCj) {
                fVar.c(bcVar2);
            }
        }
        return 0;
    }

    private int a(Vector vector) {
        for (int i = 0; i < vector.size(); i++) {
            az((String) vector.elementAt(i));
        }
        return 0;
    }

    public void h(long j) {
        if (AX) {
            n(j);
        }
    }

    private int ay(String str) {
        GLLib.be("Call function: DownloadOfflineWS. Param: " + str);
        try {
            com.gameloft.android2d.g.a.mY(0);
            com.gameloft.android2d.g.a.x(str, KJ);
            com.gameloft.android2d.g.a.mY(1);
        } catch (NoClassDefFoundError e) {
            GLLib.be(e.toString());
            GLLib.be("Call function: DownloadOfflineWS: Please enable Welcome Screen feature in Java-Android Wrapper(SET USE_WELCOME_SCREEN=1).");
        }
        return 0;
    }

    private int az(String str) {
        GLLib.be("Call function: DeleteOfflineWS. Param: " + str);
        try {
            com.gameloft.android2d.g.a.mY(0);
            com.gameloft.android2d.g.a.dQ(str);
            com.gameloft.android2d.g.a.mY(1);
        } catch (NoClassDefFoundError e) {
            GLLib.be(e.toString());
            GLLib.be("Call function: DeleteOfflineWS: Please enable Welcome Screen feature in Java-Android Wrapper(SET USE_WELCOME_SCREEN=1).");
        }
        return 0;
    }

    private boolean aA(String str) {
        GLLib.be("Call function: IsOfflineWSAvailable. Param: " + str);
        try {
            com.gameloft.android2d.g.a.mY(0);
            boolean z = com.gameloft.android2d.g.a.y(str, KJ) == 1;
            com.gameloft.android2d.g.a.mY(1);
            return z;
        } catch (NoClassDefFoundError e) {
            GLLib.be(e.toString());
            GLLib.be("Call function: IsOfflineWSAvailable: Please enable Welcome Screen feature in Java-Android Wrapper(SET USE_WELCOME_SCREEN=1).");
            return false;
        }
    }

    void a(int i, boolean z, bc bcVar) throws JSONException {
        if (i == 12 || i == 5) {
            b(z, bcVar);
            return;
        }
        bc bcVar2 = new bc();
        bcVar2.t(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, i);
        bcVar2.a(ShareConstants.WEB_DIALOG_PARAM_DATA, bcVar);
        a(bcVar2, NativeProtocol.WEB_DIALOG_ACTION);
        GLLib.QD.c(z, bcVar);
    }

    private int b(Vector vector) {
        String str;
        if (vector.size() <= 0) {
            return 0;
        }
        String str2 = "";
        int i = 0;
        while (i < vector.size()) {
            String str3 = (String) vector.elementAt(i);
            if (aA(str3)) {
                str = str2;
            } else if (str2 == "") {
                str = str2 + str3;
            } else {
                str = (str2 + ",") + str3;
            }
            i++;
            str2 = str;
        }
        if (str2.length() > 0) {
            return ay(str2);
        }
        return 0;
    }

    private void n(long j) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.KM.size()) {
                ((f) this.KM.elementAt(i2)).m(j);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }
}
