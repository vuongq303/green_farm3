package com.gameloft.a.a;

import android.util.Pair;
import com.facebook.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.android.wrapper.au;
import com.gameloft.glads.GLAds;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CrmManager.java */
/* JADX INFO: loaded from: classes.dex */
public class k {
    private String KN;
    private JSONObject bsO;
    private long btb;
    private JSONArray btc;
    public static int KJ = 0;
    private static boolean AX = false;
    private static k bsG = null;
    private static Vector<c> bsP = new Vector<>();
    private static int bsQ = GLAds.AD_NOT_DISPLAY_REASON_TIMEOUT;
    private static int AD_NOT_DISPLAY_REASON_INVALID_FORMAT = 207941;
    private static int AD_NOT_DISPLAY_REASON_NETWORK_ERROR = GLAds.AD_NOT_DISPLAY_REASON_NETWORK_ERROR;
    private static int AD_NOT_DISPLAY_REASON_NO_AD_AVAILABLE = GLAds.AD_NOT_DISPLAY_REASON_NO_AD_AVAILABLE;
    private static int bsR = 207945;
    private static int bsS = 207955;
    private static int bsT = 207954;
    private static int bsU = GLAds.AD_NOT_DISPLAY_REASON_NONE;
    private static int bsV = 182344;
    private static int bsW = 182346;
    private static int bsX = 182345;
    private static int bsY = 0;
    private static int bsZ = 1;
    private static String KQ = "CRMPopup";
    private static String KR = "";
    private static String KS = "CRMActionData";
    private boolean bsD = true;
    private boolean bsE = false;
    private Hashtable bsF = new Hashtable();
    public boolean bsH = false;
    public boolean bsI = false;
    public boolean bsJ = false;
    public boolean bsK = false;
    public boolean bsL = false;
    public boolean bsM = false;
    private boolean btf = false;
    private String btg = "";
    JSONObject bth = new JSONObject();
    private h bti = null;
    Vector<Pair<Integer, JSONObject>> btj = new Vector<>();
    private f btk = new f();
    private g btl = new g();
    private Vector KO = new Vector();
    private Vector KP = new Vector();
    private Map<String, i> bta = new HashMap();
    public Vector bsN = new Vector();
    private l bte = l.PROFILE_INVENTORY;
    private boolean btd = false;

    public static k VR() {
        if (bsG == null) {
            bsG = new k();
        }
        return bsG;
    }

    public void ie() {
        if (AX) {
            ij();
            m1if();
            this.KO.clear();
            this.KP.clear();
            this.bta.clear();
            AX = false;
        }
    }

    public static int c(JSONObject jSONObject, String str) {
        return 0;
    }

    public static int a(JSONObject jSONObject, int i) {
        return 0;
    }

    private boolean b(boolean z, JSONObject jSONObject) {
        try {
            if (com.gameloft.android2d.d.b.Qm() == null || !jSONObject.has(m.btE)) {
                return false;
            }
            try {
                int i = jSONObject.getInt("type");
                if (!jSONObject.has(m.buQ)) {
                    return false;
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject(m.buQ);
                switch (i) {
                    case 51852:
                        if (!jSONObject2.has(m.bvl)) {
                            return false;
                        }
                        if (com.gameloft.android2d.d.b.Qm() != null) {
                            com.gameloft.android2d.d.b.Qm().b(i, new String[]{jSONObject2.getString(m.bvl)});
                        }
                        break;
                        break;
                    case 51855:
                        if (com.gameloft.android2d.d.b.Qm() != null) {
                            Object[] objArr = new Object[7];
                            objArr[0] = jSONObject2.optString(m.btr);
                            objArr[1] = jSONObject2.optString(m.buA);
                            String strOptString = jSONObject2.optString(m.buD);
                            if (strOptString == null || strOptString.isEmpty()) {
                                strOptString = "N/A";
                            }
                            objArr[2] = strOptString;
                            objArr[3] = jSONObject2.optString(m.buP);
                            objArr[4] = jSONObject2.optString(m.buK);
                            objArr[5] = Integer.valueOf(jSONObject2.optInt(m.buT));
                            objArr[6] = jSONObject2.optString(m.buS);
                            com.gameloft.android2d.d.b.Qm().b(i, objArr);
                        }
                        break;
                    case 221908:
                        if (com.gameloft.android2d.d.b.Qm() != null) {
                            Object[] objArr2 = new Object[9];
                            objArr2[0] = jSONObject2.optString(m.btr);
                            objArr2[1] = jSONObject2.optString(m.buA);
                            String strOptString2 = jSONObject2.optString(m.buD);
                            if (strOptString2 == null || strOptString2.isEmpty()) {
                                strOptString2 = "N/A";
                            }
                            objArr2[2] = strOptString2;
                            objArr2[3] = Integer.valueOf(jSONObject2.optInt(m.bvv));
                            objArr2[4] = jSONObject2.optString(m.bvu);
                            objArr2[5] = jSONObject2.optString(m.buP);
                            objArr2[6] = jSONObject2.optString(m.buK);
                            objArr2[7] = Integer.valueOf(jSONObject2.optInt(m.buE));
                            objArr2[8] = jSONObject2.optString(m.buy);
                            com.gameloft.android2d.d.b.Qm().b(i, objArr2);
                        }
                        break;
                }
                return true;
            } catch (JSONException e) {
            }
        } catch (NoClassDefFoundError e2) {
            return false;
        }
    }

    public int o(JSONObject jSONObject) throws JSONException {
        this.bsH = false;
        this.bsI = false;
        this.bsJ = false;
        this.bsK = false;
        if (jSONObject == null) {
            return -34;
        }
        if (jSONObject.has(m.btP)) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(m.btP);
            if (jSONObject2.has(m.btQ)) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject(m.btQ);
                if (jSONObject3.has(m.btT)) {
                    this.bsH = jSONObject3.getBoolean(m.btT);
                }
            }
            if (jSONObject2.has(m.btR)) {
                JSONObject jSONObject4 = jSONObject2.getJSONObject(m.btR);
                if (jSONObject4.has(m.btT)) {
                    this.bsJ = jSONObject4.getBoolean(m.btT);
                }
            }
            if (jSONObject2.has(m.btS)) {
                JSONObject jSONObject5 = jSONObject2.getJSONObject(m.btS);
                if (jSONObject5.has(m.btT)) {
                    this.bsK = jSONObject5.getBoolean(m.btT);
                }
            }
            if (jSONObject2.has(m.btV)) {
                this.bsN.clear();
                if (jSONObject2.has(m.btV)) {
                    try {
                        JSONArray jSONArray = jSONObject2.getJSONArray(m.btV);
                        for (int i = 0; i < jSONArray.length(); i++) {
                            this.bsN.addElement(new Integer(jSONArray.getString(i)));
                        }
                    } catch (JSONException e) {
                    }
                }
            }
        }
        if (!jSONObject.has(m.aYB)) {
            return 0;
        }
        JSONObject jSONObject6 = jSONObject.getJSONObject(m.aYB);
        if (!jSONObject6.has(m.btU)) {
            return 0;
        }
        JSONObject jSONObject7 = jSONObject6.getJSONObject(m.btU);
        if (!jSONObject7.has(m.btT)) {
            return 0;
        }
        this.bsI = jSONObject7.getBoolean(m.btT);
        return 0;
    }

    public void p(JSONObject jSONObject) throws JSONException {
        this.btc = new JSONArray();
        if (jSONObject != null && jSONObject.has(m.aYA)) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(m.aYA);
            if (jSONObject2.has(m.buG)) {
                this.btc = jSONObject2.getJSONArray(m.buG);
            }
            if (this.bsE) {
                this.btg = jSONObject2.toString();
            }
        }
    }

    public int q(String str, boolean z) {
        JSONObject[] jSONObjectArrD;
        JSONObject[] jSONObjectArrD2 = null;
        if (AX) {
            return 0;
        }
        this.KO.clear();
        this.KP.clear();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(m.btP) && com.gameloft.android2d.d.b.Qm() != null) {
                com.gameloft.android2d.d.b.Qm().dS(jSONObject.getString(m.btP));
            }
            if (jSONObject.has(m.buG)) {
                try {
                    String[] strArrE = n.e(jSONObject, m.buG);
                    if (strArrE != null && strArrE.length > 0) {
                        for (int i = 0; i < strArrE.length - 1; i++) {
                            for (int i2 = i + 1; i2 < strArrE.length; i2++) {
                                if (strArrE[i].compareToIgnoreCase(strArrE[i2]) > 0) {
                                    String str2 = strArrE[i];
                                    strArrE[i] = strArrE[i2];
                                    strArrE[i2] = str2;
                                }
                            }
                        }
                        this.KN = "";
                        for (int i3 = 0; i3 < strArrE.length; i3++) {
                            if (i3 != 0) {
                                this.KN += ", ";
                            }
                            this.KN += strArrE[i3];
                        }
                        if (z && ax(this.KN)) {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put(m.btE, 51852);
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put(m.bvl, this.KN);
                            jSONObject2.put(m.buQ, jSONObject3);
                            a(15, true, jSONObject2);
                        }
                    }
                } catch (Exception e) {
                    return -34;
                }
            }
            if (jSONObject.has(m.bvm)) {
                m1if();
                try {
                    jSONObjectArrD = n.d(jSONObject, m.bvm);
                } catch (Exception e2) {
                    jSONObjectArrD = null;
                }
                b(jSONObjectArrD);
            }
            if (jSONObject.has(m.bvn)) {
                ig();
                m1if();
                try {
                    jSONObjectArrD2 = n.d(jSONObject, m.bvn);
                } catch (Exception e3) {
                }
                int iC = c(jSONObjectArrD2);
                if (iC != 0) {
                    return iC;
                }
                ik();
            }
            o(jSONObject);
            p(jSONObject);
            AX = true;
            return 0;
        } catch (JSONException e4) {
            return -44;
        }
    }

    private int b(JSONObject[] jSONObjectArr) {
        if (jSONObjectArr == null) {
            return -34;
        }
        for (JSONObject jSONObject : jSONObjectArr) {
            if (jSONObject == null) {
                return -34;
            }
            i iVar = new i(jSONObject);
            if (!iVar.IsValid()) {
                return -34;
            }
            this.bta.put(iVar.od(), iVar);
        }
        return 0;
    }

    /* JADX INFO: renamed from: if, reason: not valid java name */
    private void m1if() {
        bsP.clear();
    }

    private void ig() {
        try {
            this.bsO = new JSONObject("{\"pointcut_definitions\":{ \"launch\":[{\"p\":\"first_time\"}, {\"p\":\"resumed\"}],\"pause\":[],\"enter_section\":[{\"p\":\"section\"}],\"purchase\":[{\"p\":\"item\"}, {\"p\":\"quantity\"}, {\"p\":\"store\"}],\"level_up\":[{\"p\":\"level\"}],\"start_mission\":[{\"p\":\"mission\"}],\"abort_mission\":[{\"p\":\"mission\"}],\"finish_mission\":[{\"p\":\"mission\"},{\"p\":\"success\"}],\"unlock_achievement\":[{\"p\":\"trophy\"},{\"p\":\"first_time\"}],\"resource_empty\":[{\"p\":\"item\"}]}}");
        } catch (JSONException e) {
        }
    }

    private int c(JSONObject[] jSONObjectArr) throws JSONException {
        if (jSONObjectArr == null) {
            return -34;
        }
        this.bsL = false;
        this.bsM = false;
        for (int i = 0; i < jSONObjectArr.length; i++) {
            if (jSONObjectArr[i] != null) {
                c cVar = new c();
                int iH = cVar.h(jSONObjectArr[i]);
                if (!cVar.VE().isEmpty()) {
                    String strVE = cVar.VE();
                    if (this.bta.containsKey(strVE)) {
                        cVar.a(this.bta.get(strVE));
                    }
                }
                if (iH != 0) {
                    return iH;
                }
                int i2 = 0;
                while (i2 < bsP.size()) {
                    if (bsP.elementAt(i2).VO() <= cVar.VO()) {
                        i2++;
                    } else {
                        bsP.add(i2, cVar);
                        break;
                    }
                }
                if (i2 == bsP.size()) {
                    bsP.addElement(cVar);
                }
                if (cVar.ia() == 16 && cVar.VM() != null) {
                    String string = "";
                    String string2 = "";
                    try {
                        string = cVar.VM().getString(m.bub);
                        string2 = cVar.VM().getString(m.buc);
                    } catch (JSONException e) {
                    }
                    if (string.equalsIgnoreCase(m.btH)) {
                        hk(string2);
                    }
                }
                if (cVar.ia() == 6) {
                    String string3 = "";
                    try {
                        string3 = cVar.VM().getString(m.buw);
                    } catch (JSONException e2) {
                    }
                    hk(string3);
                }
                if (cVar.ia() == 17) {
                    this.bsL = true;
                }
                if (cVar.ia() == 18 || cVar.ia() == 19) {
                    this.bsM = true;
                }
                if (cVar.ia() == 7) {
                    String string4 = "";
                    try {
                        string4 = cVar.VM().getString(m.buG);
                    } catch (JSONException e3) {
                    }
                    hk(string4);
                }
            }
        }
        Vector vector = new Vector();
        ii();
        for (int i3 = 0; i3 < this.KP.size(); i3++) {
            String str = (String) this.KP.elementAt(i3);
            boolean z = false;
            for (int i4 = 0; i4 < this.KO.size(); i4++) {
                if (str.equals((String) this.KO.elementAt(i4))) {
                    z = true;
                }
            }
            if (!z) {
                vector.addElement(str);
            }
        }
        a(vector);
        this.KP = this.KO;
        ih();
        return b(this.KO);
    }

    private int ih() {
        if (this.bsD && this.KP.size() > 0) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject[] jSONObjectArr = new JSONObject[this.KP.size()];
                for (int i = 0; i < this.KP.size(); i++) {
                    jSONObjectArr[i] = new JSONObject();
                    jSONObjectArr[i].put("json", (String) this.KP.elementAt(i));
                }
                jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONObjectArr);
                n.ap(KQ, jSONObject.toString());
            } catch (Exception e) {
                return -32;
            }
        }
        return 0;
    }

    private int ii() throws JSONException {
        JSONObject[] jSONObjectArrD;
        if (this.bsD) {
            String strHm = n.hm(KQ);
            if (strHm == null) {
                return -33;
            }
            try {
                JSONObject jSONObject = new JSONObject(strHm);
                if (jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
                    try {
                        jSONObjectArrD = n.d(jSONObject, ShareConstants.WEB_DIALOG_PARAM_DATA);
                    } catch (Exception e) {
                        jSONObjectArrD = null;
                    }
                    if (jSONObjectArrD != null) {
                        for (JSONObject jSONObject2 : jSONObjectArrD) {
                            this.KP.addElement((String) jSONObject2.get("json"));
                        }
                    }
                }
            } catch (JSONException e2) {
                return -33;
            }
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

    public int ij() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(m.buG, this.KN);
        } catch (JSONException e) {
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < bsP.size(); i++) {
            JSONObject jSONObjectVL = bsP.elementAt(i).VL();
            if (jSONObjectVL != null) {
                jSONArray.put(jSONObjectVL);
            }
        }
        try {
            jSONObject.put(m.buQ, jSONArray);
        } catch (JSONException e2) {
        }
        JSONArray jSONArray2 = new JSONArray();
        Iterator<Map.Entry<String, i>> it = this.bta.entrySet().iterator();
        while (it.hasNext()) {
            JSONObject jSONObjectVL2 = it.next().getValue().VL();
            if (jSONObjectVL2 != null) {
                jSONArray2.put(jSONObjectVL2);
            }
        }
        try {
            jSONObject.put(m.bvm, jSONArray2);
        } catch (JSONException e3) {
        }
        try {
            n.ap(KS, jSONObject.toString());
            return 0;
        } catch (Exception e4) {
            return -32;
        }
    }

    private int ik() throws JSONException {
        JSONArray jSONArray;
        JSONArray jSONArray2 = null;
        String strHm = n.hm(KS);
        if (strHm == null) {
            return -33;
        }
        try {
            JSONObject jSONObject = new JSONObject(strHm);
            if (jSONObject.has(m.buQ)) {
                try {
                    jSONArray = jSONObject.getJSONArray(m.buQ);
                } catch (Exception e) {
                    jSONArray = null;
                }
                if (jSONArray != null) {
                    for (int i = 0; i < bsP.size(); i++) {
                        c cVarElementAt = bsP.elementAt(i);
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            cVarElementAt.m(jSONArray.getJSONObject(i2));
                        }
                        bsP.set(i, cVarElementAt);
                    }
                }
            }
            if (jSONObject.has(m.bvm)) {
                try {
                    jSONArray2 = jSONObject.getJSONArray(m.bvm);
                } catch (Exception e2) {
                }
                for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i3);
                    String string = jSONObject2.getString(m.bvi);
                    if (this.bta.containsKey(string)) {
                        i iVar = this.bta.get(string);
                        iVar.m(jSONObject2);
                        this.bta.put(string, iVar);
                    }
                }
            }
            return 0;
        } catch (JSONException e3) {
            return -33;
        }
    }

    private int a(Vector vector) {
        if (this.bsD) {
            for (int i = 0; i < vector.size(); i++) {
                az((String) vector.elementAt(i));
            }
        }
        return 0;
    }

    public boolean fZ() {
        return AX;
    }

    private void N(long j) {
        if (j == 0) {
            j = c.hY();
        }
        this.btb = j;
    }

    public void h(long j) {
        if (AX) {
            n(j);
        }
    }

    public int VS() {
        if (fZ()) {
            return ij();
        }
        return 0;
    }

    public int VT() {
        if (!fZ()) {
            return 0;
        }
        b(this.KO);
        return ik();
    }

    private int ay(String str) {
        String[] strArrSplit = str.split(",");
        for (int i = 0; i < strArrSplit.length; i++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(m.bvg, strArrSplit[i]);
            } catch (JSONException e) {
            }
            c(jSONObject, m.bvh);
            com.gameloft.a.c.a.WB().hw(strArrSplit[i]);
        }
        return 0;
    }

    private int az(String str) {
        return 0;
    }

    private boolean aA(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(m.bvd, str);
        } catch (JSONException e) {
        }
        c(jSONObject, m.bve);
        return com.gameloft.a.c.a.WB().hx(str);
    }

    public boolean q(JSONObject jSONObject) {
        if (!jSONObject.has("popup_id")) {
            return false;
        }
        String string = null;
        try {
            string = jSONObject.getString("popup_id");
        } catch (JSONException e) {
        }
        return aA(string);
    }

    public void a(h hVar) {
        this.bti = hVar;
    }

    void a(int i, boolean z, JSONObject jSONObject) {
        if (i == 15) {
            b(z, jSONObject);
            return;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, i);
            jSONObject2.put(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONObject);
        } catch (JSONException e) {
        }
        if (this.bti != null) {
            this.bti.a(z, jSONObject);
        }
    }

    private int b(Vector vector) {
        String str;
        if (!this.bsD || vector.size() <= 0) {
            return 0;
        }
        String str2 = "";
        int i = 0;
        while (true) {
            str = str2;
            if (i >= vector.size()) {
                break;
            }
            String str3 = (String) vector.elementAt(i);
            if (str == "") {
                str2 = str + str3;
            } else {
                str2 = (str + ",") + str3;
            }
            i++;
        }
        if (str.length() > 0) {
            return ay(str);
        }
        return 0;
    }

    private void a(int i, JSONObject jSONObject) {
        switch (i) {
            case 6:
                t(jSONObject);
                break;
            case 7:
                w(jSONObject);
                break;
            case 9:
                u(jSONObject);
                break;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                A(jSONObject);
                break;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                v(jSONObject);
                break;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                B(jSONObject);
                break;
            case 13:
                s(jSONObject);
                break;
            case 14:
                r(jSONObject);
                break;
            case 15:
                F(jSONObject);
                break;
            case 16:
                E(jSONObject);
                break;
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                try {
                    x(jSONObject);
                } catch (JSONException e) {
                    return;
                }
                break;
            case 18:
                try {
                    y(jSONObject);
                } catch (JSONException e2) {
                    return;
                }
                break;
            case 19:
                try {
                    z(jSONObject);
                } catch (JSONException e3) {
                    return;
                }
                break;
            case 20:
                C(jSONObject);
                break;
            case 21:
                D(jSONObject);
                break;
        }
    }

    private void n(long j) {
        this.btj.clear();
        c.VF();
        for (int i = 0; i < bsP.size() && AX; i++) {
            this.btk.set(false);
            JSONObject jSONObjectA = bsP.get(i).a(this.btl, this.btk);
            if (this.btk.VQ()) {
                this.btj.add(new Pair<>(Integer.valueOf(this.btl.value()), jSONObjectA));
            }
        }
        for (int i2 = 0; i2 < this.btj.size(); i2++) {
            a(((Integer) this.btj.get(i2).first).intValue(), (JSONObject) this.btj.get(i2).second);
        }
    }

    public int c(String str, JSONObject jSONObject) {
        return a(str, Boolean.FALSE, false, jSONObject, 0L);
    }

    public boolean d(String str, JSONObject jSONObject) {
        if (!AX) {
            return false;
        }
        for (int i = 0; i < bsP.size(); i++) {
            if (bsP.elementAt(i).a(str, 0L, jSONObject)) {
                return true;
            }
        }
        return false;
    }

    private int a(String str, Boolean bool, boolean z, JSONObject jSONObject, long j) {
        N(j);
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(m.bvp, str);
            jSONObject2.put(m.buQ, jSONObject);
        } catch (JSONException e) {
        }
        if (!AX) {
            return -47;
        }
        for (int i = 0; i < bsP.size(); i++) {
            c cVarElementAt = bsP.elementAt(i);
            if ((!this.btd || (cVarElementAt.ia() != 9 && cVarElementAt.ia() != 10)) && cVarElementAt.a(str, jSONObject, j) == 0) {
                Boolean.valueOf(true);
            }
        }
        return 0;
    }

    private boolean r(JSONObject jSONObject) {
        a(14, true, jSONObject);
        return true;
    }

    private boolean s(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(m.bvr, 13);
            jSONObject2.put(m.buQ, jSONObject);
            c(jSONObject2, m.bvs);
        } catch (JSONException e) {
        }
        a(13, true, jSONObject);
        return true;
    }

    private boolean t(JSONObject jSONObject) {
        if (!fZ()) {
            return false;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(m.bvr, 6);
            jSONObject2.put(m.buQ, jSONObject);
            c(jSONObject2, m.bvs);
        } catch (JSONException e) {
        }
        a(6, true, jSONObject);
        return true;
    }

    private boolean u(JSONObject jSONObject) {
        if (!fZ()) {
            return false;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(m.bvr, 9);
            jSONObject2.put(m.buQ, jSONObject);
            c(jSONObject2, m.bvs);
        } catch (JSONException e) {
        }
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put(m.btE, 51855);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put(m.buK, jSONObject.optString(m.buK));
            jSONObject4.put(m.btr, jSONObject.optString(m.btr));
            jSONObject4.put(m.buS, jSONObject.optString(m.buy));
            jSONObject4.put(m.buT, jSONObject.optString(m.buE));
            jSONObject4.put(m.buP, jSONObject.optString(m.buP));
            jSONObject4.put(m.buA, jSONObject.optString(m.buA));
            jSONObject4.put(m.buD, jSONObject.optString(m.buD));
            jSONObject3.put(m.buQ, jSONObject4);
            a(15, true, jSONObject3);
        } catch (Exception e2) {
        }
        a(9, true, jSONObject);
        return true;
    }

    private boolean v(JSONObject jSONObject) {
        if (!fZ()) {
            return false;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(m.bvr, 11);
            jSONObject2.put(m.buQ, jSONObject);
            c(jSONObject2, m.bvs);
        } catch (JSONException e) {
        }
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put(m.btE, 51855);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put(m.buK, jSONObject.optString(m.buK));
            jSONObject4.put(m.btr, jSONObject.optString(m.btr));
            jSONObject4.put(m.buS, "bundle");
            jSONObject4.put(m.buT, 1);
            jSONObject4.put(m.buP, jSONObject.optString(m.buP));
            jSONObject4.put(m.buD, jSONObject.optString(m.buD));
            jSONObject4.put(m.buA, jSONObject.optString(m.buA));
            jSONObject3.put(m.buQ, jSONObject4);
            a(15, true, jSONObject3);
        } catch (Exception e2) {
        }
        a(11, true, jSONObject);
        return true;
    }

    private boolean w(JSONObject jSONObject) {
        if (!fZ()) {
            return false;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(m.bvr, 7);
            jSONObject2.put(m.buQ, jSONObject);
            c(jSONObject2, m.bvs);
        } catch (JSONException e) {
        }
        a(7, true, jSONObject);
        return true;
    }

    private boolean x(JSONObject jSONObject) {
        a(jSONObject, bsV);
        a(17, true, jSONObject);
        return true;
    }

    private boolean y(JSONObject jSONObject) {
        a(jSONObject, bsX);
        a(18, true, jSONObject);
        return true;
    }

    private boolean z(JSONObject jSONObject) {
        a(jSONObject, bsW);
        a(19, true, jSONObject);
        return true;
    }

    private void A(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(m.bvr, 10);
            jSONObject2.put(m.buQ, jSONObject);
            c(jSONObject2, m.bvs);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(m.btE, 221908);
            jSONObject.put(m.bvv, 221909);
            jSONObject.put(m.bvu, 0);
            jSONObject3.put(m.buQ, jSONObject);
            a(15, true, jSONObject3);
            String string = jSONObject.getString(m.buy);
            String strValueOf = String.valueOf(jSONObject.getInt(m.buE));
            String string2 = jSONObject.getString(m.btr);
            String str = this.bte == l.PENDING_TRANSACTIONS ? m.bwd : m.bwe;
            if (au.Pd()) {
                String str2 = string != null ? "getSecureGift" + String.valueOf(Math.abs(string.hashCode())) : "getSecureGift";
                this.bsF.put(str2, jSONObject);
                com.gameloft.a.b.a.j(string2, string, strValueOf, str, str2);
                return;
            }
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 408);
            jSONObject.put(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE, "N/A");
            jSONObject3.put(m.btE, 221908);
            jSONObject.put(m.bvv, 221911);
            jSONObject.put(m.bvu, "N/A");
            jSONObject3.put(m.buQ, jSONObject);
            a(15, true, jSONObject3);
            a(10, false, jSONObject);
        } catch (Exception e) {
        }
    }

    private void B(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(m.bvr, 12);
            jSONObject2.put(m.buQ, jSONObject);
            c(jSONObject2, m.bvs);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(m.btE, 221908);
            jSONObject.put(m.bvv, 221909);
            jSONObject.put(m.bvu, 0);
            jSONObject.put(m.buy, "bundle");
            jSONObject.put(m.buE, 1);
            jSONObject3.put(m.buQ, jSONObject);
            a(15, true, jSONObject3);
            String strOptString = jSONObject.optString(m.btr);
            String strOptString2 = jSONObject.optString(m.buD);
            String str = this.bte == l.PENDING_TRANSACTIONS ? m.bwd : m.bwe;
            if (!au.Pd()) {
                try {
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 408);
                    jSONObject.put(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE, "N/A");
                    jSONObject3.put(m.btE, 221908);
                    jSONObject.put(m.bvv, 221911);
                    jSONObject.put(m.bvu, "N/A");
                    jSONObject3.put(m.buQ, jSONObject);
                    a(15, true, jSONObject3);
                    a(12, false, jSONObject);
                    return;
                } catch (Exception e) {
                }
            }
            String str2 = strOptString2 != null ? "getSecureBundleGift" + String.valueOf(Math.abs(strOptString2.hashCode())) : "getSecureBundleGift";
            this.bsF.put(str2, jSONObject);
            com.gameloft.a.b.a.n(strOptString, strOptString2, str, str2);
        } catch (Exception e2) {
        }
    }

    boolean C(JSONObject jSONObject) {
        if (!fZ()) {
            return false;
        }
        a(20, true, jSONObject);
        return true;
    }

    public boolean D(JSONObject jSONObject) {
        if (!fZ()) {
            return false;
        }
        a(21, true, jSONObject);
        return true;
    }

    void E(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(m.bvr, 16);
            jSONObject2.put(m.buQ, jSONObject);
            c(jSONObject2, m.bvs);
        } catch (JSONException e) {
        }
        a(16, true, jSONObject);
    }

    private boolean F(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(m.bvr, 15);
        } catch (JSONException e) {
        }
        try {
            jSONObject2.put(m.buQ, jSONObject);
        } catch (JSONException e2) {
        }
        c(jSONObject2, m.bvs);
        a(15, true, jSONObject);
        return true;
    }

    private void hk(String str) {
        if (this.bsD) {
            boolean z = false;
            for (int i = 0; i < this.KO.size(); i++) {
                if (((String) this.KO.elementAt(i)).equals(str)) {
                    z = true;
                }
            }
            if (!z) {
                this.KO.addElement(str);
            }
        }
    }
}
