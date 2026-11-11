package com.gameloft.a.a;

import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.ServerProtocol;
import com.gameloft.android.wrapper.au;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CrmAction.java */
/* JADX INFO: loaded from: classes.dex */
public class c {
    private static Map<String, Integer> bst = new HashMap();
    private double KA;
    private String KN;
    private int Kx;
    private String Ky;
    private JSONArray bsj;
    private int bsk;
    private int bsl;
    private boolean bsm;
    private int bsn;
    private String bso;
    private String bsp;
    private i bsq;
    private String bsr;
    private String bss;
    private long bsu;
    private Vector KD = new Vector();
    private Vector KE = new Vector();
    private Map<String, Integer> bsv = VD();
    private boolean KF = false;
    private String Kz = "";
    private JSONObject bsi = new JSONObject();

    private Map<String, Integer> VD() {
        HashMap map = new HashMap();
        map.put("abort_mission", 194344);
        map.put("acquire", 194340);
        map.put("close_popup", 194349);
        map.put("enter_section", 194334);
        map.put("exit_section", 194335);
        map.put("finish_mission", 194343);
        map.put("launch", 194336);
        map.put(FirebaseAnalytics.Event.LEVEL_UP, 194338);
        map.put("not_enough_resources", 194341);
        map.put("pause", 194333);
        map.put("purchase", 194337);
        map.put("resource_empty", 194342);
        map.put("skip_mission", 194346);
        map.put("start_mission", 194345);
        map.put(FirebaseAnalytics.Event.UNLOCK_ACHIEVEMENT, 194348);
        map.put("unlock_mission", 194347);
        map.put("use", 194339);
        return map;
    }

    public static long hY() {
        return n.VV() / 1000;
    }

    public c() {
        this.KD.clear();
        this.bss = "";
        this.bsp = "";
        this.bsn = 0;
        this.bsm = true;
        this.bsk = -2;
        this.bsl = -2;
        this.bsu = 0L;
        this.bsq = null;
    }

    public int h(JSONObject jSONObject) {
        try {
            return (i(jSONObject) == -34 || k(jSONObject) == -34) ? -34 : 0;
        } catch (JSONException e) {
        }
    }

    private int i(JSONObject jSONObject) throws JSONException {
        try {
            this.Kz = jSONObject.getString(m.btq);
            this.bsi.put(m.btq, this.Kz);
            this.bsi.put(m.btr, this.Kz);
            try {
                this.KN = jSONObject.getString(m.btr);
                this.bsi.put(m.btr, this.KN);
            } catch (Exception e) {
            }
            try {
                this.bsn = jSONObject.getInt(m.btt);
                this.bsi.put(m.btt, this.bsn);
            } catch (Exception e2) {
            }
            try {
                this.bsm = jSONObject.getBoolean(m.btu);
                this.bsi.put(m.btu, this.bsm);
            } catch (Exception e3) {
            }
            try {
                this.bsp = jSONObject.getString(m.btv);
                this.bsi.put(m.btv, this.bsp);
            } catch (Exception e4) {
            }
            this.bss = "";
            if (jSONObject.has(m.btw)) {
                this.bss = jSONObject.getString(m.btw);
                if (this.bss.equals(m.bvw)) {
                    this.bss = "";
                }
                this.bsi.put(m.btw, this.bss);
                bst.put(this.bss, 0);
            } else {
                if (this.bsn > 0) {
                    this.bss = m.bvx;
                }
                this.bsi.put(m.btw, this.bss);
                bst.put(this.bss, 0);
            }
            try {
                this.bsk = jSONObject.getInt(m.bun);
                this.bsi.put(m.bun, this.bsk);
            } catch (Exception e5) {
            }
            try {
                this.bsl = jSONObject.getInt(m.bum);
                this.bsi.put(m.bum, this.bsl);
            } catch (Exception e6) {
            }
            if (j(jSONObject) == -34 || !jSONObject.has(m.btz)) {
                return -34;
            }
            try {
                this.KA = jSONObject.getDouble(m.btz);
            } catch (JSONException e7) {
            }
            if (jSONObject.has(m.btA) && (jSONObject.get(m.btA) instanceof JSONArray)) {
                try {
                    this.bsj = jSONObject.getJSONArray(m.btA);
                } catch (JSONException e8) {
                }
            }
            return 0;
        } catch (Exception e9) {
            return -34;
        }
    }

    private int j(JSONObject jSONObject) throws JSONException {
        if (!jSONObject.has(m.btB) || !(jSONObject.get(m.btB) instanceof JSONArray)) {
            return 0;
        }
        JSONArray jSONArray = jSONObject.getJSONArray(m.btB);
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            if (jSONObject2.has(m.btC) && jSONObject2.has(m.btD)) {
                e eVar = new e(this);
                eVar.KH = jSONObject2.getInt(m.btD);
                eVar.KG = jSONObject2.getInt(m.btC);
                this.KD.addElement(eVar);
            } else {
                return -34;
            }
        }
        return 0;
    }

    private int k(JSONObject jSONObject) throws JSONException {
        if (!jSONObject.has(m.btE)) {
            return -34;
        }
        this.Ky = jSONObject.getString(m.btE);
        if (this.Ky.equalsIgnoreCase(m.btH)) {
            this.Kx = 6;
        } else if (this.Ky.equalsIgnoreCase(m.btJ)) {
            this.Kx = 7;
        } else if (this.Ky.equalsIgnoreCase(m.btK)) {
            this.Kx = 8;
        } else if (this.Ky.equalsIgnoreCase(m.btL)) {
            this.Kx = 9;
        } else if (this.Ky.equalsIgnoreCase(m.btM)) {
            this.Kx = 10;
        } else if (this.Ky.equalsIgnoreCase(m.btN)) {
            this.Kx = 11;
        } else if (this.Ky.equalsIgnoreCase(m.btO)) {
            this.Kx = 12;
        } else if (this.Ky.equalsIgnoreCase(m.bup)) {
            this.Kx = 13;
        } else if (this.Ky.equalsIgnoreCase(m.buq)) {
            this.Kx = 14;
        } else if (this.Ky.equalsIgnoreCase(m.bur)) {
            this.Kx = 15;
        } else if (this.Ky.equalsIgnoreCase(m.buf)) {
            this.Kx = 17;
        } else if (this.Ky.equalsIgnoreCase(m.bug)) {
            this.Kx = 18;
        } else if (this.Ky.equalsIgnoreCase(m.buh)) {
            this.Kx = 19;
        } else if (this.Ky.equalsIgnoreCase(m.bui)) {
            this.Kx = 20;
        } else if (this.Ky.equalsIgnoreCase(m.btI)) {
            this.Kx = 16;
        } else if (this.Ky.equalsIgnoreCase(m.buj)) {
            this.Kx = 21;
        }
        this.bsi.put(m.buv, this.Kx);
        switch (this.Kx) {
            case 6:
                if (!jSONObject.has(m.buw)) {
                    return -34;
                }
                this.bsi.put(m.buw, jSONObject.getString(m.buw));
                this.bsi.put(m.bud, jSONObject.has(m.bud) ? jSONObject.getBoolean(m.bud) : false);
                return 0;
            case 7:
                return l(jSONObject) == null ? -34 : 0;
            case 8:
                return l(jSONObject) == null ? -34 : 0;
            case 9:
                if (!jSONObject.has(m.buy)) {
                    return -34;
                }
                this.bsi.put(m.buy, jSONObject.getString(m.buy));
                if (!jSONObject.has(m.buE)) {
                    return -34;
                }
                this.bsi.put(m.buE, jSONObject.getInt(m.buE));
                String string = "";
                if (jSONObject.has(m.buA)) {
                    string = jSONObject.getString(m.buA);
                }
                this.bsi.put(m.buA, string);
                return 0;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                if (!jSONObject.has(m.buy)) {
                    return -34;
                }
                this.bsi.put(m.buy, jSONObject.getString(m.buy));
                if (!jSONObject.has(m.buE)) {
                    return -34;
                }
                this.bsi.put(m.buE, jSONObject.getInt(m.buE));
                String string2 = "";
                if (jSONObject.has(m.buA)) {
                    string2 = jSONObject.getString(m.buA);
                }
                this.bsi.put(m.buA, string2);
                return 0;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                if (!jSONObject.has(m.buC)) {
                    return -34;
                }
                String string3 = jSONObject.getString(m.buC);
                this.bsi.put(m.buC, string3);
                this.bsi.put(m.buD, string3);
                String string4 = "";
                if (jSONObject.has(m.buA)) {
                    string4 = jSONObject.getString(m.buA);
                }
                this.bsi.put(m.buA, string4);
                return 0;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                if (!jSONObject.has(m.buC)) {
                    return -34;
                }
                String string5 = jSONObject.getString(m.buC);
                this.bsi.put(m.buC, string5);
                this.bsi.put(m.buD, string5);
                String string6 = "";
                if (jSONObject.has(m.buA)) {
                    string6 = jSONObject.getString(m.buA);
                }
                this.bsi.put(m.buA, string6);
                return 0;
            case 13:
                if (!jSONObject.has(m.buF)) {
                    return -34;
                }
                this.bsi.put(m.buF, jSONObject.getString(m.buF));
                return 0;
            case 14:
            case 15:
            case 20:
            default:
                return 0;
            case 16:
                if (jSONObject.has(m.btY)) {
                    this.bsi.put(m.btY, jSONObject.getString(m.btY));
                }
                if (jSONObject.has(m.btZ)) {
                    this.bsi.put(m.btZ, jSONObject.getString(m.btZ));
                }
                if (!jSONObject.has(m.bua)) {
                    return -34;
                }
                this.bsi.put(m.bua, jSONObject.getString(m.bua));
                if (!jSONObject.has(m.bub)) {
                    return -34;
                }
                this.bsi.put(m.bub, jSONObject.getString(m.bub));
                if (!jSONObject.has(m.buc)) {
                    return -34;
                }
                this.bsi.put(m.buc, jSONObject.getString(m.buc));
                return 0;
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                a(jSONObject.has(m.btF) ? jSONObject.getJSONArray(m.btF) : null);
                return l(jSONObject) == null ? -34 : 0;
            case 18:
                a(jSONObject.has(m.btF) ? jSONObject.getJSONArray(m.btF) : null);
                return l(jSONObject) == null ? -34 : 0;
            case 19:
                a(jSONObject.has(m.btF) ? jSONObject.getJSONArray(m.btF) : null);
                return l(jSONObject) == null ? -34 : 0;
            case 21:
                a(jSONObject.has(m.btF) ? jSONObject.getJSONArray(m.btF) : null);
                return l(jSONObject) == null ? -34 : 0;
        }
    }

    private void a(JSONArray jSONArray) {
        if (jSONArray != null && jSONArray.length() > 0) {
            try {
                this.bso = jSONArray.getString(0);
            } catch (JSONException e) {
            }
        } else {
            this.bso = "";
        }
    }

    private String l(JSONObject jSONObject) {
        String str = "";
        if (!jSONObject.has(m.buG)) {
            return null;
        }
        try {
            JSONArray jSONArray = jSONObject.getJSONArray(m.buG);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                if (i != 0) {
                    str = str + "-";
                }
                str = str + jSONArray.getString(i);
            }
            this.bsi.put(m.buG, str);
            return str;
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean a(i iVar) {
        if (!iVar.od().equals(this.bsp)) {
            return false;
        }
        this.bsq = iVar;
        return true;
    }

    public String VE() {
        return this.bsp;
    }

    public static void VF() {
        Iterator<Map.Entry<String, Integer>> it = bst.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(0);
        }
    }

    public boolean H(long j) {
        if (this.bsq != null) {
            return this.bsq.K(j);
        }
        return true;
    }

    public boolean VG() {
        if (!this.bsm && !au.Pd()) {
            return false;
        }
        if (this.Kx == 6) {
            return k.VR().q(this.bsi);
        }
        if (this.Kx == 10) {
            return au.Pd();
        }
        return true;
    }

    public boolean a(String str, long j, JSONObject jSONObject) {
        if (b(str, jSONObject) == 0) {
            J(j);
            if (VH() == 0 && VJ()) {
                return true;
            }
        }
        return false;
    }

    private int VH() {
        if (!H(VN())) {
            return -36;
        }
        if (I(VN()) != 0) {
            return -35;
        }
        if (!VG()) {
            return -38;
        }
        return 0;
    }

    public void VI() {
        if (!this.KD.isEmpty()) {
            this.KE.add(Long.valueOf(VN()));
        }
        if (this.bsq != null) {
            this.bsq.L(VN());
        }
    }

    private int I(long j) {
        boolean z;
        if (this.KD.size() > 0) {
            for (boolean z2 = true; this.KE.size() > 0 && z2; z2 = z) {
                long j2 = Long.parseLong(this.KE.firstElement().toString());
                z = z2;
                for (int i = 0; i < this.KD.size(); i++) {
                    if (VN() - j2 < ((e) this.KD.elementAt(i)).KH) {
                        z = false;
                    }
                }
                if (z) {
                    this.KE.removeElementAt(0);
                }
            }
        }
        if (((double) ((new Random().nextInt(100) + 1) % 100)) / 100.0d > this.KA) {
            return -35;
        }
        if (this.KD.size() <= 0) {
            return 0;
        }
        for (int i2 = 0; i2 < this.KD.size(); i2++) {
            e eVar = (e) this.KD.elementAt(i2);
            if (eVar.KG <= this.KE.size()) {
                Vector vector = new Vector();
                for (int i3 = 0; i3 < this.KE.size(); i3++) {
                    vector.addElement((Long) this.KE.elementAt(i3));
                }
                while (vector.size() > eVar.KG) {
                    vector.removeElementAt(0);
                }
                if (VN() - Long.parseLong(vector.elementAt(0).toString()) < eVar.KH) {
                    return -35;
                }
            }
        }
        return 0;
    }

    private boolean VJ() {
        if (this.bss.length() == 0) {
            return true;
        }
        return bst.containsKey(this.bss) && Integer.parseInt(bst.get(this.bss).toString()) == 0;
    }

    private void VK() {
        if (this.bss.length() > 0) {
            bst.put(this.bss, 1);
        }
    }

    public int a(String str, JSONObject jSONObject, long j) {
        int iB = b(str, jSONObject);
        if (iB == 0) {
            J(j);
            try {
                this.bsi.put(m.buK, str);
            } catch (JSONException e) {
            }
            if (iB == 0) {
                int iVH = VH();
                if (iVH == 0) {
                    if (VJ()) {
                        VK();
                        switch (this.Kx) {
                            case 6:
                            case R.styleable.CardView_contentPaddingRight /* 10 */:
                            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                                this.KF = true;
                                return iVH;
                            case 7:
                            case 8:
                            case 9:
                            case R.styleable.CardView_contentPaddingTop /* 11 */:
                            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                            default:
                                this.KF = true;
                                k.VR().ij();
                                return iVH;
                        }
                    }
                    return -35;
                }
                return iVH;
            }
            try {
                na(iB);
                return iB;
            } catch (JSONException e2) {
                return iB;
            }
        }
        return iB;
    }

    private void na(int i) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        switch (i) {
            case -38:
                jSONObject.put(m.buN, m.buI);
                k.c(jSONObject, m.buN);
                break;
            case -36:
                jSONObject.put(m.buM, m.buI);
                k.c(jSONObject, m.buM);
                break;
            case -35:
                jSONObject.put(m.buO, m.buI);
                k.c(jSONObject, m.buO);
                break;
        }
    }

    public JSONObject VL() {
        if (this.KE.size() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(m.btq, this.bsi.getString(m.btq));
        } catch (JSONException e) {
        }
        int size = this.KE.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = Long.parseLong(this.KE.firstElement().toString());
            this.KE.removeElementAt(0);
        }
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < jArr.length; i2++) {
            this.KE.addElement(new Long(jArr[i2]));
            jSONArray.put(jArr[i2]);
        }
        try {
            jSONObject.put(m.bts, jSONArray);
        } catch (JSONException e2) {
        }
        return jSONObject;
    }

    public int m(JSONObject jSONObject) {
        if (jSONObject.has(m.btq) && this.Kz.length() > 0 && !this.bsi.getString(m.btq).equalsIgnoreCase(jSONObject.getString(m.btq))) {
            return -33;
        }
        this.KE.clear();
        if (!jSONObject.has(m.bts) || !(jSONObject.get(m.bts) instanceof JSONArray)) {
            return 0;
        }
        try {
            JSONArray jSONArray = jSONObject.getJSONArray(m.bts);
            for (int i = 0; i < jSONArray.length(); i++) {
                this.KE.addElement(new Long(jSONArray.getString(i)));
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public JSONObject a(g gVar, f fVar) {
        JSONObject jSONObject;
        if (this.KF) {
            JSONObject jSONObject2 = new JSONObject();
            this.KF = false;
            try {
                this.bsi.put(m.buP, this.bsr);
            } catch (JSONException e) {
            }
            fVar.set(true);
            gVar.set(this.Kx);
            try {
                jSONObject = new JSONObject(this.bsi.toString());
            } catch (JSONException e2) {
                jSONObject = jSONObject2;
            }
            this.bsr = "";
            try {
                this.bsi.put(m.buK, "");
            } catch (JSONException e3) {
            }
            VI();
            return jSONObject;
        }
        return null;
    }

    public int b(String str, JSONObject jSONObject) {
        boolean zA;
        JSONObject jSONObject2;
        if (VP()) {
            try {
                this.bsi.put(m.bun, this.bsk);
                this.bsi.put(m.bum, this.bsl);
            } catch (JSONException e) {
            }
        }
        for (int i = 0; i < this.bsj.length(); i++) {
            try {
                JSONObject jSONObject3 = this.bsj.getJSONObject(i);
                if (!jSONObject3.getString(m.buV).equals(str)) {
                    continue;
                } else {
                    if (jSONObject3.has(m.buW) && (jSONObject3.get(m.buW) instanceof JSONArray)) {
                        JSONArray jSONArray = jSONObject3.getJSONArray(m.buW);
                        if (jSONArray.length() > 0) {
                            zA = a(jSONArray, jSONObject);
                            if (zA) {
                                this.bsr = "";
                                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                                    if (i2 != 0) {
                                        this.bsr += ", ";
                                    }
                                    this.bsr += jSONArray.getString(i2);
                                }
                            }
                        } else {
                            this.bsr = "";
                            zA = true;
                        }
                    } else {
                        this.bsr = "";
                        zA = true;
                    }
                    if (zA) {
                        if (!jSONObject3.has(m.buk) || (jSONObject2 = jSONObject3.getJSONObject(m.buk)) == null) {
                            return 0;
                        }
                        if (jSONObject2.has(m.buo)) {
                            this.bsi.put(m.buo, jSONObject2.getString(m.buo));
                        }
                        if (jSONObject2.has(m.bum)) {
                            this.bsi.put(m.bum, jSONObject2.getInt(m.bum));
                        }
                        if (!jSONObject2.has(m.bun)) {
                            return 0;
                        }
                        this.bsi.put(m.bun, jSONObject2.getInt(m.bun));
                        return 0;
                    }
                    continue;
                }
            } catch (JSONException e2) {
            }
        }
        return -37;
    }

    public boolean a(JSONArray jSONArray, JSONObject jSONObject) {
        String string = "";
        String strNextToken = "";
        int i = 0;
        String strNextToken2 = "";
        boolean z = true;
        while (i < jSONArray.length()) {
            try {
                string = jSONArray.getString(i);
            } catch (JSONException e) {
            }
            StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
            String strNextToken3 = stringTokenizer.nextToken();
            if (stringTokenizer.hasMoreTokens()) {
                strNextToken2 = stringTokenizer.nextToken(" ");
            }
            if (stringTokenizer.hasMoreTokens()) {
                strNextToken = stringTokenizer.nextToken(" ");
            }
            i++;
            z = z && a(strNextToken3, strNextToken2, strNextToken, jSONObject);
        }
        return z;
    }

    public boolean a(String str, String str2, String str3, JSONObject jSONObject) {
        if (!jSONObject.has(str)) {
            return false;
        }
        Object obj = null;
        try {
            obj = jSONObject.get(str);
        } catch (JSONException e) {
        }
        if (obj instanceof Integer) {
            int iIntValue = ((Integer) obj).intValue();
            int iIntValue2 = Integer.valueOf(str3).intValue();
            if (str2.equals(">")) {
                return iIntValue > iIntValue2;
            }
            if (str2.equals("==")) {
                return iIntValue == iIntValue2;
            }
            if (str2.equals("<")) {
                return iIntValue < iIntValue2;
            }
            return str2.equals("!=") && iIntValue != iIntValue2;
        }
        if (obj instanceof Boolean) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zEquals = str3.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            if (str2.equals("==")) {
                return zBooleanValue == zEquals;
            }
            if (str2.equals("!=")) {
                return zBooleanValue != zEquals;
            }
        } else if (obj instanceof String) {
            String str4 = (String) obj;
            if (str2.equals("==")) {
                return str4.equals(str3);
            }
            if (str2.equals("!=")) {
                return !str4.equals(str3);
            }
        } else {
            if (obj instanceof Long) {
                long jLongValue = ((Long) obj).longValue();
                long jLongValue2 = Long.valueOf(str3).longValue();
                if (str2.equals(">")) {
                    return jLongValue > jLongValue2;
                }
                if (str2.equals("==")) {
                    return jLongValue == jLongValue2;
                }
                if (str2.equals("<")) {
                    return jLongValue < jLongValue2;
                }
                return str2.equals("!=") && jLongValue != jLongValue2;
            }
            if (obj instanceof Double) {
                double dDoubleValue = ((Double) obj).doubleValue();
                double dDoubleValue2 = Double.valueOf(str3).doubleValue();
                if (str2.equals(">")) {
                    return dDoubleValue > dDoubleValue2;
                }
                if (str2.equals("==")) {
                    return dDoubleValue == dDoubleValue2;
                }
                if (str2.equals("<")) {
                    return dDoubleValue < dDoubleValue2;
                }
                return str2.equals("!=") && dDoubleValue != dDoubleValue2;
            }
        }
        return false;
    }

    public int ia() {
        return this.Kx;
    }

    public JSONObject VM() {
        return this.bsi;
    }

    private void J(long j) {
        if (j > this.bsu) {
            this.bsu = j;
        }
    }

    private long VN() {
        return this.bsu > 0 ? this.bsu : hY();
    }

    public int VO() {
        return this.bsn;
    }

    public boolean VP() {
        return this.Kx == 17 || this.Kx == 18 || this.Kx == 19;
    }
}
