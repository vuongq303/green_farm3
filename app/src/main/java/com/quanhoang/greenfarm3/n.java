package com.quanhoang.greenfarm3;

import android.os.SystemClock;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: GlotNetwork.java */
/* JADX INFO: loaded from: classes.dex */
public class n extends k implements Runnable {
    private bx AG;
    private int Bj;
    private d Ce;
    private bv Ch;
    private bd Ck;
    private long Co;
    private Thread BG = null;
    protected boolean Cg = false;
    private int Ci = 0;
    private int Cj = -1;
    private String Cl = "N/A";
    int Cm = -1;
    private int Cn = 0;
    private boolean Cf = false;

    n(bx bxVar) {
        this.Bj = 0;
        this.AG = bxVar;
        this.Ce = d.a(this.AG);
        this.Bj = 0;
    }

    public void gk() {
        if (this.BG != null) {
            try {
                this.BG.join();
            } catch (Exception e) {
            }
        }
        this.BG = new Thread(this);
        this.BG.start();
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        boolean z2;
        int i;
        boolean z3;
        boolean z4;
        while (!this.Cf) {
            try {
                synchronized (this) {
                    try {
                        wait(50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
            }
            if (!this.Cf) {
                switch (this.Bj) {
                    case 0:
                        if (this.AG.bdI && bx.bco.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) != 0) {
                            this.Ch = new bv(bx.bco);
                            this.Ch.a(this);
                            this.Ch.b(this.AG);
                            this.Bj = 1;
                        }
                        break;
                    case 1:
                        if (this.AG.bej && this.AG.NO() && !go()) {
                            if (this.Ce.aD(1)) {
                                this.Ck = aM(1);
                                if (this.Ck != null) {
                                    c(this.Ck, 1);
                                    this.AG.Ob();
                                    this.Bj = 2;
                                    break;
                                }
                            }
                            if (this.Ce.aD(0)) {
                                this.Ck = aM(0);
                                if (this.Ck != null) {
                                    c(this.Ck, 0);
                                    this.AG.Ob();
                                    this.Bj = 2;
                                    break;
                                }
                            }
                            if (this.Ce.aD(2)) {
                                this.Ck = aM(2);
                                if (this.Ck != null) {
                                    try {
                                        JSONArray jSONArray = this.Ck.EJ.getJSONArray("events");
                                        if (jSONArray.length() > 0) {
                                            boolean z5 = false;
                                            int i2 = 0;
                                            while (i2 < jSONArray.length()) {
                                                int i3 = ((JSONObject) jSONArray.get(i2)).getInt("type");
                                                JSONObject jSONObject = this.AG.bdh.getJSONObject("" + i3);
                                                if (jSONObject.has("batch_size")) {
                                                    i = jSONObject.getInt("batch_size");
                                                } else if (!jSONObject.has("pointcut_batch_size")) {
                                                    i = 0;
                                                } else {
                                                    i = jSONObject.getInt("pointcut_batch_size");
                                                }
                                                JSONArray jSONArray2 = jSONObject.getJSONArray(NativeProtocol.WEB_DIALOG_PARAMS);
                                                int length = jSONArray2.length();
                                                int i4 = 0;
                                                while (true) {
                                                    if (i4 >= length) {
                                                        z3 = false;
                                                    } else if (!jSONArray2.getJSONObject(i4).has("batching")) {
                                                        i4++;
                                                    } else {
                                                        z3 = true;
                                                    }
                                                }
                                                boolean z6 = true;
                                                while (z6) {
                                                    JSONObject jSONObject2 = ((JSONObject) jSONArray.get(i2)).getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                                                    if (this.AG.bef || jSONObject2.getInt("count") >= i) {
                                                        if (z3) {
                                                            jSONObject2.put("count_sb", jSONObject2.getInt("count"));
                                                            jSONObject2.put("count", 1);
                                                        } else {
                                                            jSONObject2.put("count_sb", 1);
                                                        }
                                                        bw bwVar = new bw();
                                                        bwVar.type = 0;
                                                        bwVar.id = i3;
                                                        bwVar.bdd = (JSONObject) jSONArray.get(i2);
                                                        bwVar.bdd.put("token", "" + this.AG.NM());
                                                        long jGm = this.AG.gm();
                                                        long jNN = this.AG.NN();
                                                        bwVar.bdd.put("ts", jGm);
                                                        bwVar.bdd.put("ts_local", jNN);
                                                        bwVar.bdd.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).put("connectivity", this.AG.NI());
                                                        this.AG.bei.a(bwVar, true, false);
                                                        z5 = true;
                                                        this.AG.Ob();
                                                        o.b("Promoted batch event " + i3 + " to normal events:" + bwVar.toString(), 1);
                                                        jSONArray = o.a(jSONArray, i2);
                                                        i2--;
                                                        z6 = false;
                                                    } else {
                                                        int i5 = i2 + 1;
                                                        while (true) {
                                                            if (i5 >= jSONArray.length()) {
                                                                z6 = false;
                                                            }
                                                            if (((JSONObject) jSONArray.get(i5)).getInt("type") == i3) {
                                                                JSONObject jSONObject3 = ((JSONObject) jSONArray.get(i5)).getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                                                                int i6 = 0;
                                                                while (true) {
                                                                    if (i6 >= length) {
                                                                        z4 = true;
                                                                    } else {
                                                                        JSONObject jSONObject4 = jSONArray2.getJSONObject(i6);
                                                                        String string = jSONObject4.getString(BC ? "k" : "p");
                                                                        if (jSONObject4.has("batching") || jSONObject3.get(string).equals(jSONObject2.get(string))) {
                                                                            i6++;
                                                                        } else {
                                                                            z4 = false;
                                                                        }
                                                                    }
                                                                }
                                                                if (z4) {
                                                                    jSONObject2.put("count", jSONObject3.getInt("count") + jSONObject2.getInt("count"));
                                                                    if (z3) {
                                                                        for (int i7 = 0; i7 < length; i7++) {
                                                                            JSONObject jSONObject5 = jSONArray2.getJSONObject(i7);
                                                                            String string2 = jSONObject5.getString(BC ? "k" : "p");
                                                                            if (jSONObject5.has("batching")) {
                                                                                jSONObject2.put(string2, jSONObject3.getInt(string2) + jSONObject2.getInt(string2));
                                                                            }
                                                                        }
                                                                    }
                                                                    z6 = true;
                                                                    jSONArray = o.a(jSONArray, i5);
                                                                }
                                                                break;
                                                            }
                                                            i5++;
                                                        }
                                                    }
                                                    break;
                                                }
                                                i2++;
                                            }
                                            int i8 = 0;
                                            boolean z7 = z5;
                                            while (i8 < jSONArray.length()) {
                                                int i9 = ((JSONObject) jSONArray.get(i8)).getInt("type");
                                                bw bwVar2 = new bw();
                                                bwVar2.type = 2;
                                                bwVar2.id = i9;
                                                if (this.Cg) {
                                                    bwVar2.bdd = (JSONObject) jSONArray.get(i8);
                                                    this.AG.bei.a(bwVar2, false, true);
                                                    o.b("Appended back batch event " + i9 + ":" + bwVar2.toString(), 1);
                                                    z2 = z7;
                                                } else {
                                                    JSONArray jSONArray3 = this.AG.bdh.getJSONObject("" + i9).getJSONArray(NativeProtocol.WEB_DIALOG_PARAMS);
                                                    int length2 = jSONArray3.length();
                                                    int i10 = 0;
                                                    while (true) {
                                                        if (i10 >= length2) {
                                                            z = false;
                                                        } else if (!jSONArray3.getJSONObject(i10).has("batching")) {
                                                            i10++;
                                                        } else {
                                                            z = true;
                                                        }
                                                    }
                                                    JSONObject jSONObject6 = ((JSONObject) jSONArray.get(i8)).getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                                                    if (z) {
                                                        jSONObject6.put("count_sb", jSONObject6.getInt("count"));
                                                        jSONObject6.put("count", 1);
                                                    } else {
                                                        jSONObject6.put("count_sb", 1);
                                                    }
                                                    bwVar2.type = 0;
                                                    bwVar2.bdd = (JSONObject) jSONArray.get(i8);
                                                    bwVar2.bdd.put("token", "" + this.AG.NM());
                                                    long jGm2 = this.AG.gm();
                                                    long jNN2 = this.AG.NN();
                                                    bwVar2.bdd.put("ts", jGm2);
                                                    bwVar2.bdd.put("ts_local", jNN2);
                                                    bwVar2.bdd.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).put("connectivity", this.AG.NI());
                                                    this.AG.bei.a(bwVar2, true, false);
                                                    z2 = true;
                                                    this.AG.Ob();
                                                    o.b("Promoted [first time] batch event " + i9 + " to normal events:" + bwVar2.toString(), 1);
                                                }
                                                i8++;
                                                z7 = z2;
                                            }
                                            this.Cg = true;
                                            if (z7) {
                                                this.AG.bei.a(new bw(0, 3), true, false);
                                            }
                                        }
                                    } catch (Exception e2) {
                                        o.d(e2);
                                    }
                                    this.Ce.aE(2);
                                    this.AG.Ob();
                                }
                            }
                        }
                        break;
                    case 2:
                        if (this.Ch != null) {
                            this.Ch.mg();
                        }
                        break;
                }
            } else {
                return;
            }
        }
    }

    void gl() {
        o.b("[Network] FinishNetwork called", 1);
        this.Cf = true;
        this.AG.bej = false;
        if (this.BG != null) {
            try {
                this.BG.join();
            } catch (Exception e) {
            }
        }
        this.BG = null;
    }

    private bd aM(int i) {
        if (this.AG.bds <= 0) {
            o.u("AttempToCreateMessageFromSaveFile NULLLLLLLLLLLLLLLLLL");
            return null;
        }
        bd bdVarAH = this.Ce.aH(i);
        if (bdVarAH != null) {
            if (bdVarAH.gZ() <= 0) {
                this.Ce.aE(i);
                return null;
            }
            a(bdVarAH);
            o.b("MSG Created \n" + bdVarAH, 1);
            return bdVarAH;
        }
        this.Ce.aE(i);
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0074  */
    private void a(bd bdVar) {
        JSONArray jSONArrayA;
        boolean zD;
        if (bdVar != null && this.AG.bds != 0) {
            try {
                JSONArray jSONArray = bdVar.EJ.getJSONArray("events");
                int length = jSONArray.length();
                if (length > 0) {
                    int i = length - 1;
                    while (i >= 0) {
                        if (((JSONObject) jSONArray.get(i)).getInt("type") == 51936) {
                            JSONObject jSONObject = ((JSONObject) jSONArray.get(i)).getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                            if (!jSONObject.has("reverify")) {
                                zD = true;
                            } else {
                                zD = this.AG.d(jSONObject);
                                jSONObject.remove("reverify");
                            }
                            if (zD) {
                                jSONArrayA = jSONArray;
                            } else {
                                o.b("Referral data is not exist, remove install referral event", 3);
                                jSONArrayA = o.a(jSONArray, i);
                                bdVar.EJ.put("events", jSONArrayA);
                            }
                        } else {
                            jSONArrayA = jSONArray;
                        }
                        i--;
                        jSONArray = jSONArrayA;
                    }
                }
            } catch (Exception e) {
                o.d(e);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0068 A[Catch: Exception -> 0x0087, TryCatch #1 {Exception -> 0x0087, blocks: (B:6:0x000a, B:8:0x0014, B:10:0x0030, B:11:0x0036, B:13:0x003a, B:14:0x0047, B:15:0x0057, B:17:0x0068, B:18:0x006e, B:20:0x0072, B:32:0x00ac, B:22:0x007d), top: B:39:0x000a, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:20:0x0072 A[Catch: Exception -> 0x0087, LOOP:1: B:18:0x006e->B:20:0x0072, LOOP_END, TryCatch #1 {Exception -> 0x0087, blocks: (B:6:0x000a, B:8:0x0014, B:10:0x0030, B:11:0x0036, B:13:0x003a, B:14:0x0047, B:15:0x0057, B:17:0x0068, B:18:0x006e, B:20:0x0072, B:32:0x00ac, B:22:0x007d), top: B:39:0x000a, inners: #2 }] */
    private void c(bd bdVar, int i) {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        int i2;
        if (bdVar == null) {
            o.t("!!!!!!!!!!! ERROR !!!!!!!! message NULL");
            return;
        }
        try {
            JSONArray jSONArray3 = bdVar.EJ.getJSONArray("events");
            try {
                this.Cm = a(jSONArray3, jSONArray3.getJSONObject(0).getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getString("ver"));
                if (this.Cm > 0) {
                    JSONArray jSONArray4 = new JSONArray();
                    for (int i3 = 0; i3 < this.Cm; i3++) {
                        jSONArray4.put((JSONObject) jSONArray3.get(i3));
                    }
                    bdVar.EJ.remove("events");
                    bdVar.EJ.put("events", jSONArray4);
                    jSONArray = bdVar.EJ.getJSONArray("events");
                    if (jSONArray.length() > Bu) {
                        jSONArray2 = new JSONArray();
                        for (i2 = 0; i2 < Bu; i2++) {
                            jSONArray2.put(jSONArray.get(i2));
                        }
                        bdVar.EJ.remove("events");
                        bdVar.EJ.put("events", jSONArray2);
                    }
                } else {
                    jSONArray = bdVar.EJ.getJSONArray("events");
                    if (jSONArray.length() > Bu) {
                        jSONArray2 = new JSONArray();
                        while (i2 < Bu) {
                            jSONArray2.put(jSONArray.get(i2));
                        }
                        bdVar.EJ.remove("events");
                        bdVar.EJ.put("events", jSONArray2);
                    }
                }
            } catch (JSONException e) {
                o.u("[Network]: (!) Exception catched when getting game version");
                o.d(e);
            }
        } catch (Exception e2) {
            o.d(e2);
        }
        b(bdVar.EJ);
        try {
            JSONArray jSONArray5 = bdVar.EJ.getJSONArray("events");
            for (int i4 = 0; i4 < jSONArray5.length(); i4++) {
                this.AG.c(jSONArray5.getJSONObject(i4));
            }
        } catch (Exception e3) {
            o.d(e3);
        }
        this.Cj = i;
        this.Ch.dR(bdVar.EJ.toString());
    }

    private void b(JSONObject jSONObject) {
        long jGm = gm();
        long jNN = this.AG.NN();
        try {
            jSONObject.put("ts", jGm);
            jSONObject.put("ts_local", jNN);
            Object objNr = this.AG.Nr();
            Object objNz = this.AG.Nz();
            Object objGetHDIDFV = this.AG.GetHDIDFV();
            Object objGa = this.AG.ga();
            Object objNy = this.AG.Ny();
            String strNs = this.AG.Ns();
            String strGf = this.AG.gf();
            Object objNm = this.AG.Nm();
            Object objGX = this.AG.gX();
            String strNB = this.AG.NB();
            if (objNz != null && objNz != "") {
                jSONObject.put("mac", objNz);
            }
            if (objNr != null && objNr != "") {
                jSONObject.put("imei", objNr);
            }
            if (objNm != null && objNm != "") {
                jSONObject.put("aid", objNm);
            }
            if ("" != 0 && "" != "") {
                jSONObject.put("sn", "");
            }
            if (jSONObject.has("ggid")) {
                jSONObject.put("ggid", bx.Nv());
            }
            jSONObject.put("proto_ver", objNy);
            jSONObject.put("platform", Constants.PLATFORM);
            jSONObject.put("client_id", objGX);
            if (!strNB.equals("")) {
                jSONObject.put("gaid", strNB);
            }
            jSONObject.put("install_ts", bx.ND().No());
            jSONObject.put("install_id", bx.ND().Np());
            Object objGg = bx.gg();
            if (objGg != null && objGg != "") {
                jSONObject.put("data_center_id", objGg);
            }
            String string = UUID.randomUUID().toString();
            while (!o.C(string)) {
                string = UUID.randomUUID().toString();
            }
            jSONObject.put("uuid", string);
            this.Cl = string;
            int iPO = com.gameloft.android.wrapper.au.PO();
            if (iPO == 0) {
                jSONObject.put("latitude", com.gameloft.android.wrapper.au.PP());
                jSONObject.put("longitude", com.gameloft.android.wrapper.au.PQ());
                jSONObject.put("accuracy", com.gameloft.android.wrapper.au.PR());
            } else {
                o.t("[Message][GeoLocation]: (!) Failed for package UUID: " + this.Cl + " with status code: " + iPO);
            }
            jSONObject.put("glot_ver", "330");
            jSONObject.put("ver", bx.Nx());
            if (objGetHDIDFV != null) {
                jSONObject.put("hdidfv", objGetHDIDFV);
            }
            if (objGa != null && objGa != "") {
                jSONObject.put("gdid", objGa);
            }
            if (strNs == null || strNs.equals("")) {
                strNs = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            }
            jSONObject.put("anon_id", strNs);
            if (strGf != null && !strGf.equals("") && bx.NC()) {
                if (strNs.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                    o.t("Warning!!! ... Try to include fed_access_token but the anon_id has wrong value");
                }
                jSONObject.put("fed_access_token", strGf);
            }
            if (jSONObject.has("udid")) {
                jSONObject.remove("udid");
            }
        } catch (JSONException e) {
            o.u("[Network]: Exception when checking missing message params: " + e.getMessage());
        }
    }

    private long gm() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis <= 0) {
            jCurrentTimeMillis = 0;
        }
        return jCurrentTimeMillis / 1000;
    }

    protected String gn() {
        return this.Cl;
    }

    private boolean go() {
        if (this.Cn <= 0) {
            return false;
        }
        if ((((long) ((int) gp())) - this.Co) / 1000 > this.Cn) {
            this.Cn = 0;
            return false;
        }
        return true;
    }

    private long gp() {
        return SystemClock.elapsedRealtime();
    }

    protected void aN(int i) {
        this.Cn = i;
        this.Co = gp();
        o.u("[Network]: (!) ETS has delayed sending for " + this.Cn + " second(s).");
    }

    private int a(JSONArray jSONArray, String str) {
        for (int i = 1; i < jSONArray.length(); i++) {
            try {
                if (jSONArray.getJSONObject(i).getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getString("ver").compareTo(str) != 0) {
                    return i;
                }
            } catch (JSONException e) {
                o.u("[Network]: (!) CheckGameVersion: Exception catched");
                return i;
            }
        }
        return -1;
    }

    protected void a(String str, boolean z) {
        if (z) {
            this.Ci = 0;
            gq();
            this.Ck = null;
            this.AG.Ob();
            this.Bj = 1;
            o.u("Reponse: " + str + " for package UUID: " + this.Cl);
            this.Cl = "";
            return;
        }
        boolean zR = r(str);
        this.Ci++;
        if (this.Ci < Bx && !zR) {
            this.Bj = 1;
            return;
        }
        o.t("SEND MESSAGE FAILD");
        this.Ci = 0;
        this.Bj = 0;
        this.AG.Ob();
    }

    private static int q(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            o.t("" + e.getMessage());
            return 0;
        }
    }

    private boolean r(String str) {
        boolean z = false;
        if (str != null) {
            int iQ = q(str);
            if (iQ == 0 && str != AppEventsConstants.EVENT_PARAM_VALUE_NO && str.length() > 3) {
                iQ = q(str.substring(0, 3));
            }
            if (iQ == 0) {
                this.AG.c(1407, "[Network]: (!) Received non-numeric HTTP status code! Code was: " + str, this.Cl);
            }
            switch (iQ) {
                case 989:
                    this.AG.c(989, "[Network]: (!) ETS - Invalid protocol!", this.Cl);
                    aO(iQ);
                    gq();
                    z = true;
                    break;
                case 990:
                    this.AG.c(990, "[Network]: (!) ETS - GGI/Version rejected by admin!", this.Cl);
                    break;
                case 991:
                    this.AG.c(991, "[Network]: (!) ETS - GGI/Version blocked by admin! Stop sending!", this.Cl);
                    this.AG.bdM = true;
                    this.AG.bdN = false;
                    break;
                case 992:
                    this.AG.c(992, "[Network]: (!) ETS - GGI/Version blocked by admin! Stop sending, erase from device!", this.Cl);
                    aO(iQ);
                    gq();
                    this.AG.bdM = true;
                    this.AG.bdN = false;
                    z = true;
                    break;
                case 993:
                    this.AG.c(993, "[Network]: (!) ETS - GGI/Version blocked by admin! Disable tracking, stop, erase!", this.Cl);
                    aO(iQ);
                    gq();
                    this.AG.bdM = true;
                    this.AG.bdN = true;
                    z = true;
                    break;
                case 994:
                    this.AG.c(994, "[Network]: (!) ETS - Server is shutting down, retrying sending", this.Cl);
                    break;
                case 995:
                    this.AG.c(995, "[Network]: (!) ETS - Batch storage refused, retrying sending", this.Cl);
                    break;
                case 996:
                    this.AG.c(996, "[Network]: (!) ETS - Rejected the events!", this.Cl);
                    aO(iQ);
                    gq();
                    z = true;
                    break;
                case 997:
                    this.AG.c(997, "[Network]: (!) ETS - Rejected the package!", this.Cl);
                    aO(iQ);
                    gq();
                    z = true;
                    break;
                case 998:
                    this.AG.c(998, "[Network]: (!) ETS - Rejected the headers!", this.Cl);
                    aO(iQ);
                    gq();
                    z = true;
                    break;
                case 999:
                    this.AG.c(999, "[Network]: (!) ETS - Time has expired!", this.Cl);
                    break;
                default:
                    this.AG.c(1500, "[Network]: (!) Received unknown status code! Code was: " + iQ, this.Cl);
                    this.AG.bdM = true;
                    this.AG.bdN = false;
                    gq();
                    z = true;
                    break;
            }
        }
        this.Ck = null;
        this.AG.Ob();
        this.Bj = 1;
        return z;
    }

    private void gq() {
        if (this.Cj != -1) {
            bd bdVarAH = this.Ce.aH(this.Cj);
            if (bdVarAH != null) {
                try {
                    JSONArray jSONArray = bdVarAH.EJ.getJSONArray("events");
                    int length = jSONArray.length();
                    if (this.Cm > 0 && this.Cm <= Bu) {
                        JSONArray jSONArray2 = new JSONArray();
                        for (int i = this.Cm; i < length; i++) {
                            jSONArray2.put(jSONArray.get(i));
                        }
                        bdVarAH.EJ.remove("events");
                        bdVarAH.EJ.put("events", jSONArray2);
                        try {
                            JSONArray jSONArray3 = bdVarAH.EJ.getJSONArray("events");
                            int i2 = 0;
                            while (true) {
                                int i3 = i2;
                                if (i3 >= jSONArray3.length()) {
                                    break;
                                }
                                this.AG.c((JSONObject) jSONArray3.get(i3));
                                i2 = i3 + 1;
                            }
                        } catch (Exception e) {
                            o.d(e);
                        }
                        this.Ce.b(bdVarAH, this.Cj);
                    } else if (length > Bu) {
                        JSONArray jSONArray4 = new JSONArray();
                        for (int i4 = Bu; i4 < length; i4++) {
                            jSONArray4.put(jSONArray.get(i4));
                        }
                        bdVarAH.EJ.remove("events");
                        bdVarAH.EJ.put("events", jSONArray4);
                        this.Ce.b(bdVarAH, this.Cj);
                    } else {
                        this.Ce.aE(this.Cj);
                    }
                } catch (Exception e2) {
                    o.d(e2);
                }
            } else {
                this.Ce.aE(this.Cj);
            }
            this.Cj = -1;
            this.Cm = -1;
        }
    }

    private void aO(int i) {
        bd bdVarAH;
        if (this.Cj != -1 && (bdVarAH = this.Ce.aH(this.Cj)) != null) {
            try {
                JSONArray jSONArray = bdVarAH.EJ.getJSONArray("events");
                if (jSONArray.length() > Bu) {
                    JSONArray jSONArray2 = new JSONArray();
                    for (int i2 = 0; i2 < Bu; i2++) {
                        jSONArray2.put(jSONArray.get(i2));
                    }
                    bdVarAH.EJ.remove("events");
                    bdVarAH.EJ.put("events", jSONArray2);
                }
                o.e("Refused_" + i + "_" + String.valueOf(gm()) + ".bin", bdVarAH.toString());
            } catch (Exception e) {
                o.d(e);
            }
        }
    }

    public int gr() {
        return this.Ci;
    }
}
