package com.gameloft.android.ANMP.GloftGF2F;

import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.android.wrapper.NativeUtils;
import com.gameloft.glads.AndroidWebView;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.microedition.midlet.MIDlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: TrackingManager.java */
/* JADX INFO: loaded from: classes.dex */
public class bx extends k {
    private static String bdB;
    private static String bdf;
    private static int bdj;
    private static HashMap<Integer, Integer> bem;
    private String Bh;
    private String bdC;
    private String bdD;
    private String bdF;
    private String bdG;
    private int bdH;
    private boolean bdJ;
    private boolean bdK;
    private boolean bdL;
    private String bdO;
    private long bdR;
    private String bdT;
    private long bdU;
    private String bdY;
    protected n bdc;
    protected bi bdg;
    protected JSONObject bdh;
    private j bdi;
    protected int bdk;
    private int bdl;
    private int bdm;
    private int bdp;
    protected int bdq;
    private long bdr;
    protected long bds;
    private boolean bdt;
    private long bdu;
    private long bdv;
    private long bdw;
    private long bdx;
    private long bdy;
    private int beb;
    private int bee;
    protected p bei;
    private int ben;
    private long beq;
    private long ber;
    private long bes;
    long bet;
    private m beu;
    private static final int[] bde = {165062, 51852, 51806, 51814, 51816, 51907, 51927, 205737, 51909, 209318, 180437, 187101, 193503, 193595, 193597, 51854, 171435, 222353, 222356, 219672, 219666, 209296, 235901, 235900, 235899, 235898, 225261, 254523, 255057, 51820};
    private static bx CC = null;
    protected static String bco = "";
    private static String bdQ = "";
    private static boolean bek = true;
    private String bcp = null;
    private int Bj = 0;
    private boolean bdn = false;
    private int bdo = 0;
    protected long bdz = -1;
    protected long bdA = -1;
    private String bdE = null;
    protected boolean bdI = false;
    private boolean Jn = false;
    private String bdP = null;
    private boolean bdS = false;
    private String bdV = "";
    private String bdW = "";
    private String bdX = "";
    private String bdZ = "";
    private String bea = "";
    private boolean bec = false;
    private String bed = "N/A";
    protected boolean bef = false;
    private boolean beg = false;
    private boolean beh = true;
    protected boolean bej = false;
    private byte[] bel = new byte[48];
    private long beo = 0;
    private long bep = 0;
    private long ast = -1;
    protected boolean bdM = false;
    protected boolean bdN = false;

    public String Ni() {
        return this.bdZ;
    }

    public long Nj() {
        return (long) Math.ceil(this.bdu / 60.0f);
    }

    public long Nk() {
        return this.bdy;
    }

    public int Nl() {
        return this.bdp;
    }

    public String Nm() {
        return this.bea;
    }

    public int Nn() {
        return new Random().nextInt(999999) + 1;
    }

    public long No() {
        return this.beo;
    }

    public long Np() {
        return this.bep;
    }

    public void dS(String str) {
        C0008r.F(str);
    }

    void mv(int i) {
        if (!this.bef && i != 51905) {
            if (bem.containsKey(Integer.valueOf(i))) {
                bem.put(Integer.valueOf(i), Integer.valueOf(bem.get(Integer.valueOf(i)).intValue() + 1));
            } else {
                bem.put(Integer.valueOf(i), 1);
            }
        }
    }

    void Nq() {
        if (!this.bef) {
            for (Map.Entry<Integer, Integer> entry : bem.entrySet()) {
                int iIntValue = entry.getKey().intValue();
                int iIntValue2 = entry.getValue().intValue();
                if (iIntValue2 > BE) {
                    o.u("TrackingManager.DetectSpamOfEventTypes: Spam detected with event type: " + iIntValue + ", amount: " + iIntValue2);
                    c(153001, "[Manager]: (!) Detected spam with event of type: " + iIntValue, "N/A");
                }
            }
            bem.clear();
        }
    }

    public boolean aU(int i) {
        return C0008r.aU(i);
    }

    private boolean mw(int i) {
        for (int i2 = 0; i2 < bde.length; i2++) {
            if (bde[i2] == i) {
                return true;
            }
        }
        return false;
    }

    public void b(int i, String[] strArr) throws JSONException {
        JSONObject jSONObject;
        String string;
        boolean z;
        String strSubstring;
        if (i != 51935) {
            if (!this.Jn && i != 51905) {
                o.u("Adding event " + i + " failed! Please make sure that OnDestroy was not called!");
                return;
            }
            if (this.beg && this.Jn && !mw(i)) {
                c(131765, "[Manager]: Too early for tracking. Invoke [OnResume] before event type: " + i, "N/A");
                return;
            }
            if (this.bdN) {
                o.u("BLOCKED ADDING from server !!!!!!!!!!");
                return;
            }
            try {
                if (this.bdh.has("" + i)) {
                    try {
                        jSONObject = this.bdh.getJSONObject("" + i);
                    } catch (JSONException e) {
                        o.t("AddEvent !!!!!!!!!!!!!! Invalid event parameters\t" + i);
                        jSONObject = null;
                    }
                    if (jSONObject != null) {
                        if (jSONObject.has("dis")) {
                            o.t("AddEvent !!!!!!!!!!!!!! Event disabled:\t" + i);
                            return;
                        }
                        bw bwVar = new bw();
                        bwVar.type = 0;
                        if (jSONObject.has("batch_size") || jSONObject.has("pointcut_batch_size")) {
                            int i2 = 0;
                            try {
                                if (jSONObject.has("batch_size")) {
                                    i2 = jSONObject.getInt("batch_size");
                                } else {
                                    i2 = jSONObject.getInt("pointcut_batch_size");
                                }
                            } catch (JSONException e2) {
                                o.t("AddEvent !!!!!!!!!!!!!!invalid batch_size");
                            }
                            if (i2 > 0) {
                                bwVar.type = 2;
                            }
                        }
                        if (jSONObject.has("prio")) {
                            try {
                                if (jSONObject.getInt("prio") == 1) {
                                    bwVar.type = 1;
                                }
                            } catch (JSONException e3) {
                            }
                        }
                        JSONObject jSONObject2 = bwVar.bdd;
                        bwVar.id = i;
                        jSONObject2.put("type", bwVar.id);
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("ver", bdB);
                        jSONObject2.put(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONObject3);
                        JSONArray jSONArray = jSONObject.getJSONArray(NativeProtocol.WEB_DIALOG_PARAMS);
                        int length = jSONArray.length();
                        if (length != strArr.length && length != 0) {
                            o.t("AddEvent ERROR Too many or too few event parameter " + i + ". Expected: " + length + ", received: " + strArr.length);
                            if (strArr.length < length) {
                                c(131767, "[Manager]: Too few parameters for event type: " + i + ". Expected: " + length + ", received: " + strArr.length, "N/A");
                                return;
                            } else {
                                if (length > strArr.length) {
                                    c(131766, "[Manager]: Too many parameters for event type: " + i + ". Expected: " + length + ", received: " + strArr.length, "N/A");
                                    return;
                                }
                                return;
                            }
                        }
                        if (length != 0) {
                            if (o.Cq && i == 168904) {
                                String str = strArr[0];
                                long j = Long.parseLong(str.substring(str.lastIndexOf(124) + 1));
                                o.u("Adding ZORO EVENT with ts value: " + j + " ->>> " + o.k(j));
                            }
                            for (int i3 = 0; i3 < length; i3++) {
                                JSONObject jSONObject4 = (JSONObject) jSONArray.get(i3);
                                if (!jSONObject4.has("dis")) {
                                    if (jSONObject4.has(BC ? "k" : "p")) {
                                        String string2 = jSONObject4.getString(BC ? "k" : "p");
                                        if (!jSONObject4.has("metric_type")) {
                                            string = "NAN";
                                        } else {
                                            string = jSONObject4.getString("metric_type");
                                        }
                                        if (!jSONObject4.has("encrypt")) {
                                            z = false;
                                        } else {
                                            z = jSONObject4.getString("encrypt").compareTo("yes") == 0;
                                        }
                                        String str2 = strArr[i3];
                                        if (o.B(str2)) {
                                            c(131768, "[Manager]: (!) Parameter with control characters for event type: " + i, "N/A");
                                            o.u("GLOT_PARAMETER_CONTAINS_CONTROL_CHARACTERS \"" + str2 + "\"");
                                            return;
                                        }
                                        String string3 = jSONObject4.getString("type");
                                        try {
                                            if (string3.compareTo("int") == 0 || string3.compareTo("dimension") == 0 || string.compareTo("int") == 0) {
                                                jSONObject3.put(string2, Integer.parseInt(str2.startsWith("INT:") ? str2.substring(4) : str2));
                                            } else if (string3.compareTo("float") == 0 || string.compareTo("float") == 0) {
                                                strSubstring = str2.startsWith("FLOAT:") ? str2.substring(6) : str2;
                                                try {
                                                    jSONObject3.put(string2, Float.valueOf(((int) ((((double) Float.parseFloat(strSubstring)) + 0.005d) * 100.0d)) / 100.0f));
                                                } catch (NumberFormatException e4) {
                                                    o.t("Parse numeric parameter " + strSubstring);
                                                    jSONObject3.put(string2, strSubstring);
                                                }
                                            } else if (z) {
                                                String strDU = dU(str2);
                                                if (strDU == null) {
                                                    strDU = "N/A";
                                                }
                                                jSONObject3.put(string2, strDU);
                                            } else {
                                                jSONObject3.put(string2, str2.isEmpty() ? "N/A" : str2);
                                            }
                                        } catch (NumberFormatException e5) {
                                            strSubstring = str2;
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        } else {
                            o.t("Event has no params !!!!");
                        }
                        if (bwVar.type == 2) {
                            d(bwVar);
                            if (this.bef) {
                                this.bdq = -1;
                                return;
                            }
                            return;
                        }
                        c(bwVar);
                        if (bwVar.type == 1 || this.bef) {
                            this.bdq = -1;
                        }
                        o.b("Add new event " + i + ":" + bwVar.toString(), 1);
                        this.bei.a(bwVar, true, false);
                        mv(bwVar.id);
                        Ob();
                        return;
                    }
                    return;
                }
                o.t("AddEvent !!!!!!!!!!!!!! Event DOES NOT EXIST:\t" + i);
                c(3, "[Manager]: Cannot find in descriptor (EventsJson.java), event type:  " + i, "N/A");
            } catch (JSONException e6) {
                o.t("Addevent Unknown json error !!!");
            }
        }
    }

    public void b(int i, Object[] objArr) throws JSONException {
        JSONObject jSONObject;
        String string;
        boolean z;
        if (i != 51935) {
            if (!this.Jn && i != 51905) {
                o.u("Adding event " + i + " failed! Please make sure that OnDestroy was not called!");
                return;
            }
            if (this.beg && this.Jn && !mw(i)) {
                c(131765, "[Manager]: Too early for tracking. Invoke [OnResume] before event type: " + i, "N/A");
                return;
            }
            if (this.bdN) {
                o.u("BLOCKED ADDING from server !!!!!!!!!!");
                return;
            }
            try {
                if (this.bdh.has("" + i)) {
                    try {
                        jSONObject = this.bdh.getJSONObject("" + i);
                    } catch (JSONException e) {
                        o.t("AddEvent !!!!!!!!!!!!!! Invalid event parameters\t" + i);
                        jSONObject = null;
                    }
                    if (jSONObject != null) {
                        if (jSONObject.has("dis")) {
                            o.t("AddEvent !!!!!!!!!!!!!! Event disabled:\t" + i);
                            return;
                        }
                        bw bwVar = new bw();
                        bwVar.type = 0;
                        if (jSONObject.has("batch_size") || jSONObject.has("pointcut_batch_size")) {
                            int i2 = 0;
                            try {
                                if (jSONObject.has("batch_size")) {
                                    i2 = jSONObject.getInt("batch_size");
                                } else {
                                    i2 = jSONObject.getInt("pointcut_batch_size");
                                }
                            } catch (JSONException e2) {
                                o.t("AddEvent !!!!!!!!!!!!!!invalid batch_size");
                            }
                            if (i2 > 0) {
                                bwVar.type = 2;
                            }
                        }
                        if (jSONObject.has("prio")) {
                            try {
                                if (jSONObject.getInt("prio") == 1) {
                                    bwVar.type = 1;
                                }
                            } catch (JSONException e3) {
                            }
                        }
                        JSONObject jSONObject2 = bwVar.bdd;
                        bwVar.id = i;
                        jSONObject2.put("type", bwVar.id);
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("ver", bdB);
                        jSONObject2.put(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONObject3);
                        JSONArray jSONArray = jSONObject.getJSONArray(NativeProtocol.WEB_DIALOG_PARAMS);
                        int length = jSONArray.length();
                        if (length != objArr.length && length != 0) {
                            o.t("AddEvent ERROR Too many or too few event parameter " + i + ". Expected: " + length + ", received: " + objArr.length);
                            if (objArr.length < length) {
                                c(131767, "[Manager]: Too few parameters for event type: " + i + ". Expected: " + length + ", received: " + objArr.length, "N/A");
                                return;
                            } else {
                                if (length > objArr.length) {
                                    c(131766, "[Manager]: Too many parameters for event type: " + i + ". Expected: " + length + ", received: " + objArr.length, "N/A");
                                    return;
                                }
                                return;
                            }
                        }
                        if (length != 0) {
                            if (o.Cq && i == 168904) {
                                String str = (String) objArr[0];
                                long j = Long.parseLong(str.substring(str.lastIndexOf(124) + 1));
                                o.u("Adding ZORO EVENT with ts value: " + j + " ->>> " + o.k(j));
                            }
                            for (int i3 = 0; i3 < length; i3++) {
                                JSONObject jSONObject4 = (JSONObject) jSONArray.get(i3);
                                if (!jSONObject4.has("dis")) {
                                    if (jSONObject4.has(BC ? "k" : "p")) {
                                        String string2 = jSONObject4.getString(BC ? "k" : "p");
                                        if (!jSONObject4.has("metric_type")) {
                                            string = "NAN";
                                        } else {
                                            string = jSONObject4.getString("metric_type");
                                        }
                                        if (!jSONObject4.has("encrypt")) {
                                            z = false;
                                        } else {
                                            z = jSONObject4.getString("encrypt").compareTo("yes") == 0;
                                        }
                                        Object obj = objArr[i3];
                                        String string3 = jSONObject4.getString("type");
                                        try {
                                            if (string3.compareTo("int") == 0 || string3.compareTo("dimension") == 0 || string.compareTo("int") == 0) {
                                                jSONObject3.put(string2, ((Integer) obj).intValue());
                                            } else if (string3.compareTo("float") == 0 || string.compareTo("float") == 0) {
                                                jSONObject3.put(string2, Float.valueOf(((int) ((((double) ((Float) obj).floatValue()) + 0.005d) * 100.0d)) / 100.0f));
                                            } else if (z) {
                                                if (o.B((String) obj)) {
                                                    c(131768, "[Manager]: (!) Parameter with control characters for event type: " + i, "N/A");
                                                    o.u("GLOT_PARAMETER_CONTAINS_CONTROL_CHARACTERS2 \"" + ((String) obj) + "\"");
                                                    return;
                                                } else {
                                                    String strDU = dU((String) obj);
                                                    if (strDU == null) {
                                                        strDU = "N/A";
                                                    }
                                                    jSONObject3.put(string2, strDU);
                                                }
                                            } else {
                                                if (o.B((String) obj)) {
                                                    c(131768, "[Manager]: (!) Parameter with control characters for event type: " + i, "N/A");
                                                    o.u("GLOT_PARAMETER_CONTAINS_CONTROL_CHARACTERS3 \"" + ((String) obj) + "\"");
                                                    return;
                                                }
                                                jSONObject3.put(string2, ((String) obj).isEmpty() ? "N/A" : (String) obj);
                                            }
                                        } catch (NumberFormatException e4) {
                                            o.t("Parse numeric parameter " + obj.toString());
                                            jSONObject3.put(string2, obj.toString());
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        } else {
                            o.t("Event has no params !!!!\t" + i);
                        }
                        if (bwVar.type == 2) {
                            d(bwVar);
                            if (this.bef) {
                                this.bdq = -1;
                                return;
                            }
                            return;
                        }
                        c(bwVar);
                        if (bwVar.type == 1 || this.bef) {
                            this.bdq = -1;
                        }
                        o.b("Add new event " + i + ":" + bwVar.toString(), 1);
                        this.bei.a(bwVar, true, false);
                        mv(bwVar.id);
                        Ob();
                        return;
                    }
                    return;
                }
                o.t("AddEvent !!!!!!!!!!!!!! Event DOES NOT EXIST:\t" + i);
                c(3, "[Manager]: Cannot find in descriptor (EventsJson.java), event type:  " + i, "N/A");
            } catch (JSONException e5) {
                o.t("Addevent Unknown json error !!!\t" + i);
            }
        }
    }

    public void O(int i, String str) throws JSONException {
        JSONObject jSONObject;
        String string;
        boolean z;
        int iIntValue;
        float fFloatValue;
        if (i != 51935) {
            if (!this.Jn && i != 51905) {
                o.u("Adding event " + i + " failed! Please make sure that OnDestroy was not called!");
                return;
            }
            if (this.beg && this.Jn && !mw(i)) {
                c(131765, "[Manager]: Too early for tracking. Invoke [OnResume] before event type: " + i, "N/A");
                return;
            }
            if (this.bdN) {
                o.u("BLOCKED ADDING from server !!!!!!!!!!");
                return;
            }
            try {
                if (this.bdh.has("" + i)) {
                    try {
                        jSONObject = this.bdh.getJSONObject("" + i);
                    } catch (JSONException e) {
                        o.t("AddEvent !!!!!!!!!!!!!! Invalid event parameters\t" + i);
                        jSONObject = null;
                    }
                    if (jSONObject != null) {
                        if (jSONObject.has("dis")) {
                            o.t("AddEvent !!!!!!!!!!!!!! Event disabled:\t" + i);
                            return;
                        }
                        bw bwVar = new bw();
                        bwVar.type = 0;
                        if (jSONObject.has("batch_size") || jSONObject.has("pointcut_batch_size")) {
                            int i2 = 0;
                            try {
                                if (jSONObject.has("batch_size")) {
                                    i2 = jSONObject.getInt("batch_size");
                                } else {
                                    i2 = jSONObject.getInt("pointcut_batch_size");
                                }
                            } catch (JSONException e2) {
                                o.t("AddEvent !!!!!!!!!!!!!!invalid batch_size");
                            }
                            if (i2 > 0) {
                                bwVar.type = 2;
                            }
                        }
                        if (jSONObject.has("prio")) {
                            try {
                                if (jSONObject.getInt("prio") == 1) {
                                    bwVar.type = 1;
                                }
                            } catch (JSONException e3) {
                            }
                        }
                        JSONObject jSONObject2 = bwVar.bdd;
                        bwVar.id = i;
                        jSONObject2.put("type", bwVar.id);
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("ver", bdB);
                        jSONObject2.put(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONObject3);
                        JSONObject jSONObject4 = new JSONObject(str);
                        JSONArray jSONArray = jSONObject.getJSONArray(NativeProtocol.WEB_DIALOG_PARAMS);
                        int length = jSONArray.length();
                        if (length != jSONObject4.length() && length != 0) {
                            o.t("AddEvent ERROR Too many or too few event parameter " + i + ". Expected: " + length + ", received: " + jSONObject4.length());
                            if (jSONObject4.length() < length) {
                                c(131767, "[Manager]: Too few parameters for event type: " + i + ". Expected: " + length + ", received: " + jSONObject4.length(), "N/A");
                                return;
                            } else {
                                if (length > jSONObject4.length()) {
                                    c(131766, "[Manager]: Too many parameters for event type: " + i + ". Expected: " + length + ", received: " + jSONObject4.length(), "N/A");
                                    return;
                                }
                                return;
                            }
                        }
                        if (length != 0) {
                            for (int i3 = 0; i3 < length; i3++) {
                                JSONObject jSONObject5 = (JSONObject) jSONArray.get(i3);
                                if (!jSONObject5.has("dis")) {
                                    if (jSONObject5.has(BC ? "k" : "p")) {
                                        String string2 = jSONObject5.getString(BC ? "k" : "p");
                                        String string3 = jSONObject5.getString("p");
                                        if (!jSONObject5.has("metric_type")) {
                                            string = "NAN";
                                        } else {
                                            string = jSONObject5.getString("metric_type");
                                        }
                                        if (!jSONObject5.has("encrypt")) {
                                            z = false;
                                        } else {
                                            z = jSONObject5.getString("encrypt").compareTo("yes") == 0;
                                        }
                                        Object obj = jSONObject4.get(string3);
                                        String string4 = jSONObject5.getString("type");
                                        try {
                                            if (string4.compareTo("int") == 0 || string4.compareTo("dimension") == 0 || string.compareTo("int") == 0) {
                                                if (obj instanceof Long) {
                                                    iIntValue = ((Long) obj).intValue();
                                                } else {
                                                    iIntValue = ((Integer) obj).intValue();
                                                }
                                                jSONObject3.put(string2, iIntValue);
                                            } else if (string4.compareTo("float") == 0 || string.compareTo("float") == 0) {
                                                if (obj instanceof Double) {
                                                    fFloatValue = ((Double) obj).floatValue();
                                                } else {
                                                    fFloatValue = ((Float) obj).floatValue();
                                                }
                                                jSONObject3.put(string2, Float.valueOf(((int) ((((double) fFloatValue) + 0.005d) * 100.0d)) / 100.0f));
                                            } else if (z) {
                                                String strDU = dU((String) obj);
                                                if (strDU == null) {
                                                    strDU = "N/A";
                                                }
                                                jSONObject3.put(string2, strDU);
                                            } else {
                                                jSONObject3.put(string2, ((String) obj).isEmpty() ? "N/A" : (String) obj);
                                            }
                                        } catch (NumberFormatException e4) {
                                            o.t("Parse numeric parameter " + obj.toString());
                                            jSONObject3.put(string2, obj.toString());
                                        }
                                    }
                                }
                            }
                        } else {
                            o.t("Event has no params !!!!\t" + i);
                        }
                        if (bwVar.type == 2) {
                            d(bwVar);
                            if (this.bef) {
                                this.bdq = -1;
                                return;
                            }
                            return;
                        }
                        c(bwVar);
                        if (bwVar.type == 1 || this.bef) {
                            this.bdq = -1;
                        }
                        o.b("Add new event " + i + ":" + bwVar.toString(), 1);
                        this.bei.a(bwVar, true, false);
                        mv(bwVar.id);
                        Ob();
                        return;
                    }
                    return;
                }
                o.t("AddEvent !!!!!!!!!!!!!! Event DOES NOT EXIST:\t" + i);
                c(3, "[Manager]: Cannot find in descriptor (EventsJson.java), event type:  " + i, "N/A");
            } catch (JSONException e5) {
                o.t("Addevent Unknown json error !!!\t" + i + " jsonParams: " + str);
            }
        }
    }

    public void c(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
            try {
                JSONArray jSONArray = this.bdh.getJSONObject("" + jSONObject.getInt("type")).getJSONArray(NativeProtocol.WEB_DIALOG_PARAMS);
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject3 = (JSONObject) jSONArray.get(i);
                    if (jSONObject3.has("encrypt")) {
                        String string = jSONObject3.getString(BC ? "k" : "p");
                        String string2 = jSONObject2.getString(string);
                        if (!string2.startsWith("JavaGLOTv3Encrypted")) {
                            string2 = "JavaGLOTv3Encrypted" + string2;
                        }
                        jSONObject2.put(string, string2);
                    }
                }
            } catch (JSONException e) {
                o.t("Error while validating checking for binary the event !!!\t" + e.toString());
                o.t("Error while validating checking for binary the event !!!\t" + jSONObject.toString());
            }
            if (!jSONObject.has("token")) {
                jSONObject.put("token", "" + NM());
            }
            if (!jSONObject.has("uuid")) {
                jSONObject.put("uuid", UUID.randomUUID().toString());
            }
            if (jSONObject.get("uuid") instanceof String) {
                String string3 = (String) jSONObject.get("uuid");
                while (!o.C(string3)) {
                    string3 = UUID.randomUUID().toString();
                    jSONObject.put("uuid", string3);
                }
            }
            if (!jSONObject.has("ts_local")) {
                jSONObject.put("ts_local", NN());
            }
            if (jSONObject.get("ts_local") instanceof String) {
                jSONObject.put("ts_local", Long.parseLong(jSONObject.getString("ts_local")));
            }
            if (!jSONObject.has("ts")) {
                jSONObject.put("ts", gm());
            }
            if (jSONObject.get("ts") instanceof String) {
                jSONObject.put("ts", Long.parseLong(jSONObject.getString("ts")));
            }
            if (!jSONObject2.has("ses_id")) {
                jSONObject2.put("ses_id", this.bdp);
            }
            if (jSONObject2.get("ses_id") instanceof String) {
                jSONObject2.put("ses_id", Integer.parseInt(jSONObject2.getString("ses_id")));
            }
            if (!jSONObject2.has("gt")) {
                jSONObject2.put("gt", Float.valueOf((this.bdl + 999.0f) / 1000.0f));
            }
            if (!jSONObject2.has("ses_t")) {
                float fGp = ((gp() - this.bdx) + 999.0f) / 1000.0f;
                if (fGp == 0.0f) {
                    fGp = 1.0f;
                }
                jSONObject2.put("ses_t", fGp);
            }
            if (jSONObject2.get("ses_t") instanceof String) {
                jSONObject2.put("ses_t", Integer.parseInt(jSONObject2.getString("ses_t")));
            }
            if (!jSONObject2.has("connectivity")) {
                jSONObject2.put("connectivity", NI());
            }
            if (jSONObject2.get("connectivity") instanceof String) {
                jSONObject2.put("connectivity", Integer.parseInt(jSONObject2.getString("connectivity")));
            }
            if (jSONObject2.has("sts")) {
                jSONObject2.remove("sts");
            }
        } catch (JSONException e2) {
            o.t("Error while validating the event !!!\t" + e2.toString());
            o.t("Error while validating the event !!!\t" + jSONObject.toString());
        }
    }

    public static boolean dT(String str) {
        return str.equals("ver");
    }

    public String Nr() {
        return this.bdC;
    }

    public String GetHDIDFV() {
        return this.bdD;
    }

    public String ga() {
        return this.bdE;
    }

    protected String Ns() {
        if (this.bdO == null || this.bdO.equals("")) {
            this.bdO = AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
        return this.bdO;
    }

    protected String gX() {
        return this.Bh;
    }

    protected String gf() {
        if (this.bdP == null || this.bdP.equals("")) {
            String strGf = l.gf();
            if (o.B(strGf)) {
                o.u("m_fedAccessToken contains cotrol characters!");
            } else {
                this.bdP = strGf;
            }
        }
        return this.bdP;
    }

    public static String gg() {
        if (bdQ == null || bdQ.equals("")) {
            bdQ = l.gg();
        }
        return bdQ;
    }

    private void Nt() {
        String strGf = l.gf();
        if (o.B(strGf)) {
            o.u("m_fedAccessToken contains cotrol characters!");
        } else if ((this.bdP != null && strGf != null && !this.bdP.equals(strGf)) || this.bdP == null) {
            this.bdP = strGf;
        }
    }

    protected int Nu() {
        String strEq = com.gameloft.android.wrapper.au.eq("CHANNEL_ID");
        if (strEq != null) {
            return Integer.parseInt(strEq);
        }
        return 0;
    }

    public static int Nv() {
        return bdj;
    }

    public static String Nw() {
        return bco;
    }

    public static String Nx() {
        return bdB;
    }

    protected String Ny() {
        return this.bdG;
    }

    public String Nz() {
        return this.bdF;
    }

    private int NA() {
        int iQe = com.gameloft.android.wrapper.au.Qe();
        if (iQe == 0) {
            return 234359;
        }
        if (iQe == 1 || iQe == 5 || iQe == -2) {
            return 234361;
        }
        return 234360;
    }

    public String NB() {
        if (!MIDlet.d(com.gameloft.android.wrapper.au.getContext(), "GAME-SHOP").equals("googleplay")) {
            return "";
        }
        String strNB = com.gameloft.android.wrapper.au.NB();
        if (strNB.equals("")) {
            return "N/A";
        }
        return strNB;
    }

    public static boolean NC() {
        return bek;
    }

    public static bx h(String str, String str2, String str3, String str4, String str5) throws JSONException {
        if (CC == null) {
            com.gameloft.android.wrapper.au.Qg();
            CC = new bx();
            bdf = str2;
            bem = new HashMap<>();
            if (!CC.Jn) {
                if (o.B(str3)) {
                    o.u("anonID contains control characters!");
                } else {
                    CC.bdO = str3;
                }
                CC.z(str, str4, str5);
            }
        }
        return CC;
    }

    public static bx ND() {
        return CC;
    }

    public String dU(String str) {
        return z(str, bdj);
    }

    public String R(String str, String str2) {
        try {
            return z(str, Integer.parseInt(str2));
        } catch (Exception e) {
            o.d(e);
            return null;
        }
    }

    private String z(String str, int i) {
        byte[] bArrA;
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            int[] iArr = {i, i * i, i ^ (-1), i * 4242};
            while (str.length() < 8) {
                str = str + "\u0000";
            }
            try {
                bArrA = o.a(by.a(o.a(str.getBytes(Charset.forName(AndroidWebView.UTF_8)), false, (int[]) null), iArr), false, (byte[]) null);
            } catch (Exception e) {
                bArrA = null;
            }
            return "JavaGLOTv3Encrypted" + new String(com.gameloft.android.wrapper.c.encode(bArrA));
        } catch (Exception e2) {
            o.d(e2);
            return null;
        }
    }

    public String dV(String str) {
        return A(str, bdj);
    }

    private String A(String str, int i) {
        byte[] bArrA;
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            int[] iArr = {i, i * i, i ^ (-1), i * 4242};
            if (str.startsWith("JavaGLOTv3Encrypted")) {
                str = str.substring("JavaGLOTv3Encrypted".length());
            }
            try {
                bArrA = o.a(by.b(o.a(com.gameloft.android.wrapper.c.decode(str), false, (int[]) null), iArr), false, (byte[]) null);
            } catch (Exception e) {
                bArrA = null;
            }
            return new String(bArrA, Charset.forName(AndroidWebView.UTF_8)).trim();
        } catch (Exception e2) {
            o.d(e2);
            return null;
        }
    }

    private void z(String str, String str2, String str3) throws JSONException {
        if (!this.Jn) {
            this.bdk = By;
            this.bdq = Bz;
            this.Bj = 0;
            Ok();
            this.bdG = "Binary/330";
            this.bdC = NK();
            this.bdD = com.gameloft.android.wrapper.au.Os();
            if (this.bdD == null || this.bdD.equals("")) {
                o.t("Initialize !!!!!!!!!!!!!! Using ETSv2 but has wrong HDIDFV.");
                return;
            }
            this.bdF = NL();
            this.bdT = Oe();
            this.bea = com.gameloft.android.wrapper.au.getAndroidId();
            this.Bh = str;
            this.bdH = Nu();
            dW(this.Bh);
            o.b("**************************************** GLOTv3 Initialize **********************************************", 1);
            o.b("GLOTv3 VERSION: 330", 1);
            o.b("Max number of events per message: " + Bu, 1);
            o.b("Max lengh of one message: " + Bv, 1);
            o.b("Valid time (day) of serverconfig: " + Bw, 1);
            o.b("Connection retry: " + Bx, 1);
            o.b("Default time to connect to server: " + By, 1);
            o.b("Time to connect to server the first time after game start: " + Bz, 1);
            o.b("Request timeout: " + BB, 1);
            o.b("Using game ggi: " + bdj, 1);
            o.b("Using game version: " + bdB, 1);
            o.b("Using device mac: " + this.bdF, 1);
            o.b("Using PHD device identifier for vendor: " + ((this.bdD == null || this.bdD == "") ? "N/A" : this.bdD), 1);
            o.b("Using device identifier: " + this.bdC, 1);
            o.b("Using Download Code: " + this.bdT, 1);
            o.b("Using IGP Code: " + bdf, 1);
            o.b("Using Injected IGP Code: " + Og(), 1);
            o.b("Using ETS version: ETSv2", 1);
            this.bdi = new j();
            NO();
            this.bdn = f.fM();
            dY(str2);
            if (NW() == -100) {
                NV();
            }
            this.bdg = new bi(this.Bh, str3);
            this.bei.gk();
            this.bdc.gk();
            this.beb = NA();
            this.bee = NE();
            Oh();
            Object[] objArr = new Object[12];
            objArr[0] = Integer.valueOf(this.bee);
            objArr[1] = this.bdV;
            objArr[2] = this.bdW;
            objArr[3] = "Android";
            objArr[4] = this.bdX;
            objArr[5] = this.bdY;
            objArr[6] = Integer.valueOf(this.beh ? 1 : 0);
            objArr[7] = this.bed;
            objArr[8] = this.bdZ;
            objArr[9] = "330";
            objArr[10] = Integer.valueOf(this.bec ? 1 : 0);
            objArr[11] = Integer.valueOf(this.beb);
            b(51905, objArr);
            J(51922, 0, 0, 0);
            this.Jn = true;
        }
    }

    private int NE() {
        String strD = MIDlet.d(com.gameloft.android.wrapper.au.getContext(), "IAP-EnableCRM");
        if (strD != null) {
            if (strD.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                return 238952;
            }
            if (strD.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                return 238951;
            }
        }
        return 0;
    }

    public void NF() {
        o.b("OnDestroy() >>>> called!", 1);
        if (!this.Jn) {
            o.b("OnDestroy() >>>> return!", 1);
            return;
        }
        o.b("OnDestroy() EXIT THE GAME !", 1);
        this.bei.gt();
        this.bdc.gl();
        NS();
        this.bdK = true;
        Ob();
        this.Jn = false;
        o.gs();
    }

    public void OnResume() {
    }

    public void OnPause() {
    }

    public void NG() {
        o.b("danh OnResume() >>> called", 1);
        if (!this.beg) {
            o.u("[Manager]: --- AUT has resumed but not previously paused! Application implementation bug!");
            return;
        }
        this.beg = false;
        NO();
        Nt();
        long jGp = ((gp() - this.bdx) + 999) / 1000;
        if (this.Jn && jGp >= 3) {
            this.bdp++;
            o.b("OnResume() RESUME THE GAME, Session ID:" + this.bdp, 1);
            if (this.bdK) {
                this.bdy = ((this.ast - this.bdx) + 999) / 1000;
                this.bdx = gp();
                this.bdu = NQ() / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
                if (!this.bdn) {
                    J(51856, 0, 100004, 100006);
                } else {
                    J(51856, 0, 343557, 100006);
                }
                J(51922, 0, 0, 0);
                if (Oh()) {
                    Object[] objArr = new Object[12];
                    objArr[0] = Integer.valueOf(this.bee);
                    objArr[1] = this.bdV;
                    objArr[2] = this.bdW;
                    objArr[3] = "Android";
                    objArr[4] = this.bdX;
                    objArr[5] = this.bdY;
                    objArr[6] = Integer.valueOf(this.beh ? 1 : 0);
                    objArr[7] = this.bed;
                    objArr[8] = this.bdZ;
                    objArr[9] = "330";
                    objArr[10] = Integer.valueOf(this.bec ? 1 : 0);
                    objArr[11] = Integer.valueOf(this.beb);
                    b(51905, objArr);
                }
            }
            this.bdK = false;
            Ob();
            if (this.bdc != null) {
                this.bdc.Cg = false;
            }
            if (this.bef) {
                this.bdq = -1;
            }
        }
    }

    public void NH() {
        if (!this.beg) {
            this.beg = true;
            if (this.Jn) {
                o.b("danh OnPause() GAME PAUSED", 1);
                this.bdK = true;
                NS();
                Ob();
                o.gs();
            }
        }
    }

    protected void c(bw bwVar) {
        try {
            if (bwVar.type != 2) {
                bwVar.bdd.put("token", "" + NM());
                long jGm = gm();
                long jNN = NN();
                bwVar.bdd.put("ts", jGm);
                bwVar.bdd.put("ts_local", jNN);
            }
            String string = UUID.randomUUID().toString();
            while (!o.C(string)) {
                string = UUID.randomUUID().toString();
            }
            bwVar.bdd.put("uuid", string);
            JSONObject jSONObject = bwVar.bdd.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
            jSONObject.put("connectivity", NI());
            jSONObject.put("gt", Float.valueOf((this.bdl + 999.0f) / 1000.0f));
            jSONObject.put("ses_id", this.bdp);
            float fGp = ((gp() - this.bdx) + 999.0f) / 1000.0f;
            if (fGp == 0.0f) {
                fGp = 1.0f;
            }
            jSONObject.put("ses_t", Float.valueOf(fGp));
        } catch (Exception e) {
            o.t("When adding automatic event params]" + e.getMessage());
            o.d(e);
        }
    }

    protected int NI() {
        switch (com.gameloft.android.wrapper.au.Pg()) {
            case 0:
                return 152141;
            case 1:
                return 152142;
            case 2:
                return 152150;
            case 3:
            case 9:
            default:
                return 152148;
            case 4:
                return 152143;
            case 5:
                return 152144;
            case 6:
                return 152145;
            case 7:
                return 152146;
            case 8:
                return 152147;
            case com.facebook.R.styleable.CardView_contentPaddingRight /* 10 */:
                return 353606;
        }
    }

    protected boolean NJ() {
        return com.gameloft.android.wrapper.au.Pd();
    }

    private String NK() {
        return "";
    }

    private String NL() {
        return null;
    }

    protected bx() {
    }

    private boolean j(char c) {
        return c >= 'a' && c <= 'z';
    }

    private boolean dW(String str) {
        String[] strArrSplit = str.split(":");
        int i = strArrSplit.length == 6 ? 1 : 0;
        bdj = Integer.parseInt(strArrSplit[i + 1]);
        bdB = strArrSplit[i + 2];
        String str2 = MIDlet.cnN;
        if (str2 != null) {
            String lowerCase = str2.toLowerCase();
            int length = lowerCase.length();
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = lowerCase.charAt(i2);
                if (j(cCharAt)) {
                    bdB += cCharAt;
                }
            }
        }
        return true;
    }

    private boolean d(bw bwVar) {
        if (this.bdN) {
            return true;
        }
        if (bwVar == null) {
            return false;
        }
        try {
            int i = bwVar.id;
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = bwVar.bdd.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
            JSONArray jSONArray = this.bdh.getJSONObject("" + i).getJSONArray(NativeProtocol.WEB_DIALOG_PARAMS);
            int length = jSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                String string = jSONArray.getJSONObject(i2).getString(BC ? "k" : "p");
                Object obj = jSONObject2.get(string);
                if (obj instanceof Integer) {
                    jSONObject.put(string, ((Integer) obj).intValue());
                } else if (obj instanceof Float) {
                    jSONObject.put(string, ((Float) obj).floatValue());
                } else {
                    jSONObject.put(string, obj.toString());
                }
            }
            jSONObject2.put("count", 1);
            c(bwVar);
            o.u("Add new batched event " + i + ": " + bwVar.toString());
            this.bei.a(bwVar, true, false);
            return true;
        } catch (Exception e) {
            o.t("PUSH BATCH EVENT" + e.getMessage());
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int iC() {
        String strGa;
        int iNQ = NQ();
        this.bdl += iNQ;
        if (this.Jn && bi.Nb()) {
            if (!this.bdI && this.bdg.MY()) {
                this.bdq = -1;
            }
            this.ben += iNQ;
            if (this.ben >= this.bdk) {
                Nq();
                this.ben = 0;
            }
            switch (this.Bj) {
                case 0:
                    this.bdq -= iNQ;
                    if (this.bdq < 0) {
                        if (NO()) {
                            if (!this.bdt) {
                                Od();
                            }
                            if (!this.bdI) {
                                if (!this.bdg.MZ()) {
                                    o.u("DataCenter is not set");
                                    this.bdq = this.bdk;
                                } else {
                                    String strNa = this.bdg.Na();
                                    if (strNa.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) != 0) {
                                        this.bdI = true;
                                        bco = strNa;
                                        this.bcp = this.bdg.ga();
                                        this.bdg = null;
                                        o.u("Server config loaded: " + strNa);
                                        o.u("Server GDID: " + this.bcp);
                                    } else {
                                        this.bdq = this.bdk;
                                    }
                                }
                            } else if (this.bdE == null || this.bdE.equals("")) {
                                this.Bj = 1;
                            } else {
                                String strGf = gf();
                                if (strGf == null || strGf.equals("")) {
                                    this.Bj = 3;
                                } else {
                                    this.bei.a(new bw(0, 3), true, false);
                                    this.bdq = this.bdk;
                                }
                            }
                        } else if (this.bdL || !this.bdJ) {
                            this.bdq = this.bdk;
                        }
                    }
                    break;
                case 1:
                    if (this.bcp.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) != 0 && this.bdi != null && this.bdi.a(CC, this.Bh, this.bcp) == 0 && (strGa = this.bdi.ga()) != null && strGa != "") {
                        this.bdE = strGa;
                        o.u("GDID: " + this.bdE);
                        this.Bj = 3;
                    } else {
                        this.Bj = 2;
                    }
                    break;
                case 2:
                    if (this.bcp.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) != 0 && this.bdi != null && this.bdi.fZ()) {
                        this.bdi.h(((gp() - this.bdx) + 999) / 1000);
                        String strGa2 = this.bdi.ga();
                        if (strGa2 != null && strGa2 != "") {
                            this.bdE = strGa2;
                            o.u("GDID: " + this.bdE);
                            this.Bj = 3;
                        }
                    }
                    break;
                case 3:
                    if (NC()) {
                        if (Ns().equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                            o.t("Warning!!! ... Try to get fed_access_token but the anon_id has wrong value");
                        }
                        String strGf2 = gf();
                        if (strGf2 != null && strGf2 != "") {
                            this.Bj = 0;
                        } else {
                            o.u("Waiting for fed_access_token...");
                        }
                    } else {
                        this.Bj = 0;
                    }
                    break;
            }
        }
        return iNQ;
    }

    private boolean dX(String str) {
        try {
            this.bdh = new JSONObject(str);
            if (this.bdh.has("events")) {
                this.bdh = this.bdh.getJSONObject("events");
                return true;
            }
            o.t("eventParameters in incorrect format. Please add the correct setup file or tracking WILL NOT WORK \n");
            return false;
        } catch (JSONException e) {
            o.t("eventParameters in incorrect format. Please add the correct setup file or tracking WILL NOT WORK (2)\n");
        }
    }

    protected long NM() {
        long j = this.bdr + 1;
        this.bdr = j;
        return j;
    }

    private boolean J(int i, int i2, int i3, int i4) {
        try {
            bw bwVar = new bw(i, i2);
            JSONObject jSONObject = bwVar.bdd;
            jSONObject.put("type", i);
            JSONObject jSONObject2 = new JSONObject();
            if (i == 51856) {
                jSONObject2.put("launch_type", i3);
                jSONObject2.put("crash_detection", i4);
                long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                long jCeil = (long) Math.ceil(this.bdu / 60.0f);
                if (jCurrentTimeMillis < this.bdv) {
                    jCeil = 0;
                }
                if ((i3 == 106370 || this.bdm == 350116) && jCurrentTimeMillis > this.beq) {
                    jCeil = (long) (jCeil + ((jCurrentTimeMillis - this.beq) / 60.0f));
                }
                jSONObject2.put("time_between_sessions", jCeil);
                if (i4 == 100006) {
                    jSONObject2.put("time_spent", this.bdy);
                } else {
                    jSONObject2.put("time_spent", 0);
                }
                if (MIDlet.d(com.gameloft.android.wrapper.au.getContext(), "GAME-SHOP").equals("googleplay")) {
                    jSONObject2.put("igp_code", "N/A");
                } else {
                    jSONObject2.put("igp_code", bdf);
                }
                jSONObject2.put("download_code", this.bdT);
                jSONObject2.put("channel_id", this.bdH);
            } else if (i == 51922) {
                MIDlet.d(com.gameloft.android.wrapper.au.getContext(), "GAME-SHOP");
                jSONObject2.put("igp_code", bdf);
                jSONObject2.put("injected_igp", Og());
                jSONObject2.put("download_code", Of());
                jSONObject2.put("target_sdk", com.gameloft.android.wrapper.au.Pr());
            } else if (i == 51936) {
                jSONObject2.put("utm_source", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                jSONObject2.put("utm_medium", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                jSONObject2.put("utm_term", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                jSONObject2.put("utm_content", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                jSONObject2.put("utm_campaign", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                if (!d(jSONObject2)) {
                    jSONObject2.put("reverify", "yes");
                }
            }
            jSONObject2.put("ver", bdB);
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONObject2);
            c(bwVar);
            o.b("Add automatic event " + i + ": \t" + bwVar, 1);
            this.bei.a(bwVar, true, false);
            if (bwVar.type == 1) {
                this.bdq = -1;
            }
            Ob();
            return true;
        } catch (JSONException e) {
            o.t("JSON exception on auto generated BI event !!!" + e.getMessage());
            return false;
        }
    }

    protected boolean d(JSONObject jSONObject) {
        String strA = com.gameloft.android.wrapper.au.A("referrer", "", "rsend_referrer");
        o.b("InfoReferral: " + strA, 3);
        if (strA == null || strA == "") {
            return false;
        }
        try {
            String[] strArrSplit = URLDecoder.decode(strA).split("&");
            for (String str : strArrSplit) {
                String[] strArrSplit2 = str.split("=");
                if (strArrSplit2.length == 2) {
                    o.b("InfoReferral: param " + strArrSplit2[0] + " is found", 3);
                    if (jSONObject.has(strArrSplit2[0])) {
                        jSONObject.remove(strArrSplit2[0]);
                    }
                    jSONObject.put(strArrSplit2[0], strArrSplit2[1]);
                }
            }
            return true;
        } catch (JSONException e) {
            o.t("Error when parse info referral value: " + e.getMessage());
            return false;
        }
    }

    protected boolean c(int i, String str, String str2) {
        try {
            bw bwVar = new bw(51885, 0);
            JSONObject jSONObject = bwVar.bdd;
            jSONObject.put("type", 51885);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("error_tracker_id", i);
            jSONObject2.put("glot_version", "330");
            jSONObject2.put("error_reason", str);
            jSONObject2.put("event_or_package_uuid", str2);
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONObject2);
            if (i != 131765) {
                c(bwVar);
            } else {
                long jGm = gm();
                long jNN = NN();
                jSONObject.put("ts", jGm);
                jSONObject.put("ts_local", jNN);
                String string = UUID.randomUUID().toString();
                while (!o.C(string)) {
                    string = UUID.randomUUID().toString();
                }
                jSONObject.put("uuid", string);
            }
            o.b("Add automatic event: ERROR_EVENT:\t" + bwVar, 1);
            if (this.bei == null) {
                return true;
            }
            this.bei.a(bwVar, true, false);
            return true;
        } catch (JSONException e) {
            o.t("JSON exception on auto generated BI event !!!" + e.getMessage());
            return false;
        }
    }

    private boolean mx(int i) {
        int i2 = 100006;
        if (i == -100) {
            this.bdl = 0;
            if (!this.bdn) {
                J(51856, 0, 100002, 100006);
            } else {
                J(51856, 0, 343551, 100006);
            }
            J(51936, 0, 0, 0);
            return false;
        }
        if (!this.bdK) {
            o.b("CRASH DETECED ***************** GENERATE AUTOMATIC BI CRASHED EVENT *****************", 1);
            this.bdy = 0L;
            i2 = 100005;
        }
        J(51856, 0, this.bdm, i2);
        return i2 == 100005;
    }

    protected long gm() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis <= 0) {
            jCurrentTimeMillis = 0;
        }
        return jCurrentTimeMillis / 1000;
    }

    protected long NN() {
        long jPv = com.gameloft.android.wrapper.au.Pv();
        if (jPv <= 0) {
            jPv = 0;
        }
        return jPv / 1000;
    }

    protected boolean NO() {
        if (this.beg) {
            return false;
        }
        if (!NJ()) {
            this.bdJ = true;
            return false;
        }
        this.bdJ = false;
        return (this.bdL || this.bdM) ? false : true;
    }

    private boolean NP() {
        long jGp = gp();
        this.bds = 0L;
        this.bdt = false;
        this.ast = 0L;
        this.bes = 0L;
        this.bdw = 0L;
        this.bdv = 0L;
        this.beq = 0L;
        NT();
        NR();
        if (jGp < this.ast) {
            this.bdS = true;
            if (!this.bdn) {
                this.bdm = 106370;
            } else {
                this.bdm = 350116;
            }
            this.bdu = jGp / 1000;
            if (this.ast > 0 && this.bdx > 0) {
                this.bdy = ((this.ast - this.bdx) / 1000) + 1;
            }
            long j = jGp / 1000;
            if (this.bds > 0) {
                this.bds += j;
            }
            this.bes = j + this.bes;
        } else if (this.ast > 0) {
            if (!this.bdn) {
                this.bdm = 100003;
            } else {
                this.bdm = 343555;
            }
            long j2 = jGp - this.ast;
            this.bdu = j2 / 1000;
            if (this.bdx > 0) {
                this.bdy = ((this.ast - this.bdx) / 1000) + 1;
            }
            long j3 = j2 / 1000;
            if (this.bds > 0) {
                this.bds += j3;
            }
            this.bes = j3 + this.bes;
        } else {
            if (!this.bdn) {
                this.bdm = 100002;
            } else {
                this.bdm = 343551;
            }
            this.bdu = 0L;
            this.bdx = 0L;
            this.bdy = 0L;
            this.bds = 0L;
            this.bes = jGp / 1000;
            o.u("FIRST LAUNCH DETECTED !!!!!!!!!!!!!!!!");
        }
        Od();
        this.ast = jGp;
        this.bdx = jGp;
        NS();
        return true;
    }

    private int NQ() {
        int i;
        long jGp = gp();
        if (jGp < 0) {
            i = 50;
            this.ast += (long) 50;
        } else {
            i = (int) (jGp - this.ast);
            this.ast = jGp;
        }
        this.ber += (long) i;
        if (this.ber >= 1000) {
            if (this.bds > 0) {
                this.bds += this.ber / 1000;
            } else {
                NX();
            }
            this.bes += this.ber / 1000;
            this.bet += this.ber;
            this.ber %= 1000;
            if (this.bet >= 5000) {
                NS();
                this.bet = 0L;
            }
        }
        NR();
        if (NY()) {
            NZ();
            NS();
        }
        return i;
    }

    private void NR() {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long clockMillis = NativeUtils.getClockMillis() / 1000;
        this.bdv = jCurrentTimeMillis;
        this.bdw = clockMillis;
    }

    private long gp() {
        return SystemClock.elapsedRealtime();
    }

    private void NS() {
        boolean z = true;
        try {
            o.a(this.ast, this.bel, 0);
            o.a(this.bds, this.bel, 8);
            o.a(this.bes, this.bel, 16);
            o.a(this.bdx, this.bel, 24);
            o.a(this.bdv, this.bel, 32);
            o.a(this.bdw, this.bel, 40);
            o.u("SAVE LAST up time " + this.ast + "/" + this.bdx + "/" + this.bes);
            if (o.a("glot_save_time", this.bel)) {
                z = false;
            } else {
                o.u("TrackingManager.SaveTimeData: can't save file!");
            }
        } catch (Exception e) {
            o.t("SAVE TIME" + e.getMessage());
            o.d(e);
        }
        if (z) {
            c(131762, "[Manager]: Entire time in game flag cannot be written to file!", "N/A");
        }
    }

    private void NT() {
        boolean z = false;
        try {
            byte[] bArrX = o.x("glot_save_time");
            if (bArrX != null) {
                this.ast = o.b(bArrX, 0);
                this.bds = o.b(bArrX, 8);
                this.bes = o.b(bArrX, 16);
                this.bdx = o.b(bArrX, 24);
                this.bdv = o.b(bArrX, 32);
                this.bdw = o.b(bArrX, 40);
                this.beq = o.b(bArrX, 32);
                o.u("LOAD LAST up time " + this.ast + "/" + this.bdx + "/" + this.bes);
            } else {
                o.u("TrackingManager.LoadTimeData: data == null !!!");
            }
        } catch (Exception e) {
            o.t("LOAD SAVED TIME" + e.getMessage());
            z = true;
        }
        if (z) {
            c(131758, "[Manager]: Entire time in game flag has been reset due to broken file!", "N/A");
        }
    }

    private void NU() {
        this.beo = com.gameloft.android.wrapper.au.gm();
        this.bep = Nn();
    }

    private void NV() {
        boolean z = true;
        NU();
        try {
            byte[] bArr = new byte[16];
            o.a(this.beo, bArr, 0);
            o.a(this.bep, bArr, 8);
            o.u("SAVE INSTALL INFO: " + this.beo + "/" + this.bep);
            if (o.a("glot_install_info", bArr)) {
                z = false;
            } else {
                o.u("TrackingManager.WriteInstallInfo: can't save file!");
            }
        } catch (Exception e) {
            o.t("SAVE INSTALL INFO" + e.getMessage());
            o.d(e);
        }
        if (z) {
            c(131762, "[Manager]: Entire time in game flag cannot be written to file!", "N/A");
        }
    }

    private int NW() {
        int i = 0;
        try {
            byte[] bArrX = o.x("glot_install_info");
            if (bArrX != null) {
                this.beo = o.b(bArrX, 0);
                this.bep = o.b(bArrX, 8);
                o.u("SAVE INSTALL INFO: " + this.beo + "/" + this.bep);
            } else {
                o.u("TrackingManager.ReadInstallInfo: data == null !!!");
                i = -100;
            }
            return i;
        } catch (Exception e) {
            o.d(e);
            return -101;
        }
    }

    private void NX() {
        if (this.bdJ) {
            return;
        }
        if (this.bdU == 0 || System.currentTimeMillis() - this.bdU >= 180000) {
            this.bdU = System.currentTimeMillis();
            if (this.beu == null) {
                this.beu = new m();
            }
            if (!this.beu.gj()) {
                this.beu.c("https://vgold.gameloft.com", "/locate/auth");
            }
        }
    }

    private boolean NY() {
        if (this.beu == null || this.beu.gj()) {
            return false;
        }
        if (this.beu.BM != null) {
            this.bdR = this.bds;
            return true;
        }
        this.beu.gi();
        this.beu = null;
        return false;
    }

    private void NZ() {
        long j = this.beu.BX / 1000;
        this.beu.gh();
        this.beu = null;
        this.bds = j;
        long j2 = this.bds - this.bdR;
        o.u("Get new STS  " + this.bds);
        this.bdt = true;
        if (this.bdR < 1) {
            this.bdR = 0L;
        } else if (this.bdm != 100002 && this.bdm != 343551) {
            o.u("Get new STS. Time different with old value: " + j2);
            this.bdu += j2;
            this.bes = j2 + this.bes;
        }
    }

    private int dY(String str) {
        this.bdI = false;
        NP();
        int iOc = Oc();
        this.bdp++;
        this.bei = new p(CC);
        this.bdc = new n(CC);
        C0008r.gC();
        if (!dX(str)) {
            c(131754, "[Manager]: Events descriptor could not be acquired! Game provided invalid eventsJSON parameter!", "N/A");
        }
        mx(iOc);
        this.bdK = false;
        Ob();
        return iOc;
    }

    public int Oa() {
        int iA;
        try {
            byte[] bArrX = o.x("File_marker");
            if (bArrX != null) {
                iA = o.a(bArrX, 30);
                o.u("sessionId: " + iA);
            } else {
                o.u("TrackingManager.LoadSessionId: data == null !!!");
                iA = 0;
            }
            return iA;
        } catch (Exception e) {
            o.u("TrackingManager.LoadSessionId: data == null!!!");
            c(131756, "[Manager]: Token was reset due to broken file!", "N/A");
            return 0;
        }
    }

    protected int Ob() {
        try {
            byte[] bArr = new byte[34];
            o.a(this.bdr, bArr, 0);
            bArr[8] = (byte) (this.bdK ? 1 : 0);
            o.a(this.bdl, bArr, 9);
            o.a(this.bdz, bArr, 13);
            o.a(this.bdA, bArr, 21);
            bArr[29] = (byte) this.bdo;
            o.a(this.bdp, bArr, 30);
            o.u("SAVE MARKER " + this.bdK);
            if (o.a("File_marker", bArr)) {
                return 0;
            }
            o.u("TrackingManager.SaveMarker: cannot save 'File_marker' file!!!");
        } catch (Exception e) {
            o.t("SAVE MARKER" + e.getMessage());
            o.d(e);
        }
        c(131760, "[Manager]: Token cannot be written to file!", "N/A");
        return -101;
    }

    private int Oc() {
        int i = 0;
        try {
            byte[] bArrX = o.x("File_marker");
            if (bArrX != null) {
                this.bdr = o.b(bArrX, 0);
                this.bdK = bArrX[8] != 0;
                this.bdl = o.a(bArrX, 9);
                this.bdz = o.b(bArrX, 13);
                this.bdA = o.b(bArrX, 21);
                this.bdo = bArrX[29];
                this.bdp = o.a(bArrX, 30);
                o.u("LOAD Marker " + this.bdK + "/" + this.bdr);
            } else {
                o.u("TrackingManager.ReadMarkers: data == null !!!");
                i = -100;
            }
            return i;
        } catch (Exception e) {
            o.d(e);
            return -101;
        }
    }

    private void Od() {
        NX();
    }

    private String Oe() {
        String property = System.getProperty("download.code");
        if (property == null || property.equals("")) {
            return "N/A";
        }
        return property;
    }

    private String Of() {
        String property = System.getProperty("download.code");
        if (!Og().equals("N/A")) {
            property = System.getProperty("injectedserialkey");
        }
        if (property == null || property.equals("")) {
            return "N/A";
        }
        return property;
    }

    private String Og() {
        String property = System.getProperty("injectedigp");
        if (property == null || property.equals("")) {
            return "N/A";
        }
        return property;
    }

    private boolean Oh() {
        String str = this.bed;
        Oi();
        String strPn = com.gameloft.android.wrapper.au.Pn();
        String strPo = com.gameloft.android.wrapper.au.Po();
        if (strPo.equals("")) {
            strPo = com.gameloft.android.wrapper.au.Pl();
        }
        String strPk = com.gameloft.android.wrapper.au.Pk();
        String str2 = Build.MANUFACTURER + " " + Build.MODEL;
        String upperCase = Build.VERSION.RELEASE.toUpperCase();
        boolean zCheckRoot = NativeUtils.CheckRoot();
        boolean zOl = Ol();
        int iNA = NA();
        String str3 = (strPn == null || strPn.isEmpty() || strPn.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) ? "N/A" : strPn;
        String str4 = (strPo == null || strPo.isEmpty() || strPo.equals("XX")) ? "N/A" : strPo;
        String str5 = (strPk == null || strPk.isEmpty() || strPk.equals("XX")) ? "N/A" : strPk;
        if (!str3.equals(this.bdV) || !str4.equals(this.bdW) || !str5.equals(this.bdX) || !str2.equals(this.bdY) || !upperCase.equals(this.bdZ) || zCheckRoot != this.bec || zOl != this.beh || iNA != this.beb || !str.equals(this.bed)) {
            this.bdV = str3;
            this.bdW = str4;
            this.bdX = str5;
            this.bdY = str2;
            this.bdZ = upperCase;
            this.bec = zCheckRoot;
            this.beh = zOl;
            this.beb = iNA;
            this.bed = str;
            Oj();
            o.u("Detect new Device infor !!!!!!! : \n" + this.bdV + "\n" + this.bdW + "\n" + this.bdX + "\n" + this.bdY + "\n" + this.bdZ + "\n" + this.bec + "\n" + this.beb);
            return true;
        }
        o.u("Device infor: \n" + this.bdV + "\n" + this.bdW + "\n" + this.bdX + "\n" + this.bdY + "\n" + this.bdZ + "\n" + this.bec + "\n" + this.beb);
        return false;
    }

    private boolean Oi() {
        boolean z = true;
        try {
            byte[] bArrX = o.x("device");
            if (bArrX != null) {
                String strC = o.c(bArrX, 0);
                if (strC.startsWith("JavaGLOTv3Encrypted")) {
                    strC = dV(strC);
                }
                String str = new String(strC.getBytes(Charset.forName(AndroidWebView.UTF_8)));
                this.bdV = o.a(str, 0, '\n');
                this.bdW = o.a(str, 1, '\n');
                this.bdX = o.a(str, 2, '\n');
                this.bdY = o.a(str, 3, '\n');
                this.bdZ = o.a(str, 4, '\n');
                this.bec = o.a(str, 5, '\n').compareTo(AppEventsConstants.EVENT_PARAM_VALUE_YES) == 0;
                this.beh = o.a(str, 6, '\n').compareTo(AppEventsConstants.EVENT_PARAM_VALUE_YES) == 0;
                this.bed = o.a(str, 7, '\n');
                return true;
            }
            o.u("TrackingManager.ReadDeviceInfor: data = null");
            z = false;
            if (z) {
                c(131757, "[Manager]: Device (identifiers) were reset due to broken file!", "N/A");
            }
            return false;
        } catch (Exception e) {
            o.t("TrackingManager.ReadDeviceInfor: " + e.toString());
        }
    }

    private void Oj() {
        boolean z = true;
        try {
            if (o.a("device", dU(new String((this.bdV + "\n" + this.bdW + "\n" + this.bdX + "\n" + this.bdY + "\n" + this.bdZ + "\n" + (this.bec ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "\n" + (this.beh ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "\n" + this.bed).getBytes(Charset.forName(AndroidWebView.UTF_8)), Charset.forName(AndroidWebView.UTF_8))).getBytes(Charset.forName(AndroidWebView.UTF_8)))) {
                z = false;
            } else {
                o.u("TrackingManager.SaveDeviceInfor: DEVICEID_FILE_CANNOT_BE_WRITTEN");
                c(131761, "[Manager]: Device (identifiers) cannot be written to file!", "N/A");
            }
        } catch (Exception e) {
            o.t("TrackingManager.SaveDeviceInfor: " + e.toString());
        }
        if (z) {
            c(131761, "[Manager]: Device (identifiers) cannot be written to file!", "N/A");
        }
    }

    private void Ok() {
        char c;
        String strPc = com.gameloft.android.wrapper.au.Pc();
        if (strPc != null) {
            try {
                JSONObject jSONObject = new JSONObject(strPc);
                if (!jSONObject.has("GLOT")) {
                    o.u("Cannot get QA DEBUG INFO for GLOT!!!");
                    return;
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject("GLOT");
                if (jSONObject2.getInt("LOG") == 1 && !BA) {
                    o.Cq = true;
                    File file = new File(Environment.getExternalStorageDirectory() + "/GlotDebug/gv3");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    try {
                        String string = jSONObject2.getString("LOG_FILE_MODE");
                        if (string.equals("MULTIPLE_FILES")) {
                            c = 1;
                        } else {
                            c = string.equals("CONTINUOUS_FILE") ? (char) 2 : (char) 0;
                        }
                        String str = "glot_log.txt";
                        if (c == 1) {
                            int iNl = ND().Nl();
                            if (iNl <= 0) {
                                iNl = ND().Oa() + 1;
                            }
                            str = "glot_log_" + iNl + ".txt";
                        }
                        if (c == 2) {
                            com.gameloft.android.wrapper.au.a(str, true, "");
                        } else {
                            com.gameloft.android.wrapper.au.a(str, false, "");
                        }
                    } catch (Exception e) {
                    }
                }
                o.b(jSONObject2.toString(), 1);
                o.Cp = jSONObject2.getInt("LEVEL");
                if (jSONObject2.getInt("INSTANTMODE") == 1) {
                    o.b("DETECT INSTANT MODE ", 1);
                    this.bef = true;
                }
            } catch (JSONException e2) {
                o.d(e2);
            }
        }
    }

    private boolean Ol() {
        String str = "" + com.gameloft.android.wrapper.au.Pu();
        return str == null || !(str.compareTo("false") == 0 || str.compareTo("False") == 0 || str.compareTo("FALSE") == 0);
    }

    public static boolean Om() {
        return MIDlet.coO;
    }
}
