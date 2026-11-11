package com.gameloft.a.a;

import com.facebook.appevents.UserDataStore;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: UserProfile.java */
/* JADX INFO: loaded from: classes.dex */
public class x {
    private static String adc;
    private static int adh;
    private static int adv;
    private String Kv;
    private String akS;
    private String akT;
    static x bwH = null;
    private static boolean adu = false;
    private static boolean adw = false;
    private static int adb = -1;
    private static Exception ade = null;
    private boolean akR = false;
    private String akU = null;

    public int mv() {
        return adh;
    }

    public static x Wf() {
        if (bwH == null) {
            bwH = new x();
        }
        return bwH;
    }

    public static void Release() {
        bwH = null;
    }

    private boolean a(Object[] objArr) {
        if (adu) {
            return false;
        }
        adu = true;
        adv = ((Integer) objArr[0]).intValue();
        new y(this, objArr).start();
        return true;
    }

    public boolean fZ() {
        return this.akR;
    }

    public boolean J(String str, String str2) {
        Object[] objArr = {new Integer(0), str};
        String strMt = o.VW().mt();
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
        if (!com.gameloft.a.b.a.fZ()) {
            return -21;
        }
        this.akS = "";
        this.Kv = str;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("credential", "gllive:myuser");
            jSONObject.put("created", "2012-01-10 13:37:17Z");
            jSONObject.put("modified", "2012-01-10 13:37:17Z");
            jSONObject.put("last_session", "2012-01-10 13:37:17Z");
            jSONObject.put("total_spent", 123);
            jSONObject.put("total_spent_currency", "eur");
            jSONObject.put("last_purchase", "2012-01-10 13:37:17Z");
            jSONObject.put("adid", "fsdfsfd");
            jSONObject.put("operation", "fasdfsdf");
            JSONObject[] jSONObjectArr = {new JSONObject()};
            jSONObjectArr[0].put(ShareConstants.WEB_DIALOG_PARAM_ID, ShareConstants.WEB_DIALOG_PARAM_ID);
            jSONObjectArr[0].put("model", "Samsung_GT-I9000");
            jSONObjectArr[0].put("carrier", "WIFI Country Operation");
            jSONObjectArr[0].put("name", "ios");
            jSONObjectArr[0].put("currency", "usd");
            JSONObject[] jSONObjectArr2 = {new JSONObject(), new JSONObject()};
            jSONObjectArr2[1].put("name", "creditcard");
            jSONObjectArr2[1].put("currency", "pes");
            jSONObjectArr[0].put("valid_stores", jSONObjectArr2);
            jSONObjectArr[0].put("download_code", "12312313");
            jSONObject.put("devices", jSONObjectArr);
            jSONObject.put(FirebaseAnalytics.Param.LEVEL, 3);
            jSONObject.put("xp", Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
            jSONObject.put("total_playtime", 144);
            jSONObject.put(UserDataStore.COUNTRY, "ca");
            jSONObject.put("language", "fr");
            jSONObject.put("birthdate", "unspecified");
            jSONObject.put("gender", "unspecified");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("cash", 123);
            jSONObject2.put("coins", 123);
            jSONObject.put("inventory", jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("lat", 0.0d);
            jSONObject3.put("lon", 0.0d);
            jSONObject.put(FirebaseAnalytics.Param.LOCATION, jSONObject3);
        } catch (JSONException e) {
        }
        this.akT = J(jSONObject);
        int iOo = oo();
        if (iOo == 0) {
            this.akR = true;
            o.mr();
            return iOo;
        }
        return iOo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int oo() {
        a(com.gameloft.a.b.a.m(this.Kv, null, null, "stdRefreshGetProfile"));
        int iMv = mv();
        if (iMv != 0) {
            if (iMv == 404) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("total_playtime", 0);
                } catch (JSONException e) {
                }
                a(com.gameloft.a.b.a.j(this.Kv, null, jSONObject.toString(), null, "set", "stdRefreshSetProfile"));
                int iMv2 = mv();
                if (iMv2 == 0) {
                    a(com.gameloft.a.b.a.m(this.Kv, null, null, "stdRefreshGetProfile"));
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
            try {
                this.akS = J(new JSONObject(str));
            } catch (JSONException e2) {
            }
            String property = System.getProperty("DebugMode");
            return (property == null || property.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) ? iMv : iMv;
        }
        this.akS = "";
        return -30;
    }

    private void a(com.gameloft.a.b.g gVar) {
        if (gVar == null) {
            adh = -1;
        } else if (gVar.bxb > 200) {
            adh = gVar.bxb;
        } else {
            adh = 0;
            adc = gVar.bxa;
        }
    }

    private int H(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        int iF = f(this.akT, jSONObject2);
        if (iF == 0) {
            JSONObject jSONObject3 = new JSONObject();
            int length = jSONObject.length();
            String string = null;
            JSONArray jSONArrayNames = jSONObject.names();
            for (int i = 0; i < length; i++) {
                try {
                    string = jSONArrayNames.getString(i);
                } catch (JSONException e) {
                }
                if (string != null) {
                    try {
                        if (string.indexOf("_") == 0 || (jSONObject2.get(string) != null && jSONObject.get(string).getClass().isAssignableFrom(jSONObject2.get(string).getClass()))) {
                            jSONObject3.put(string, jSONObject.get(string));
                        }
                    } catch (JSONException e2) {
                    }
                }
            }
            n.a(jSONObject, jSONObject3);
            return 0;
        }
        return iF;
    }

    private int I(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        int iF = f(this.akS, jSONObject2);
        if (iF == 0) {
            int iF2 = f(this.akT, jSONObject3);
            if (iF2 == 0) {
                int length = jSONObject.length();
                String string = null;
                JSONArray jSONArrayNames = jSONObject.names();
                for (int i = 0; i < length; i++) {
                    try {
                        string = jSONArrayNames.getString(i);
                    } catch (JSONException e) {
                    }
                    if (string != null) {
                        try {
                            if (string.indexOf("_") == 0 || (jSONObject3.get(string) != null && jSONObject.get(string).getClass().isAssignableFrom(jSONObject3.get(string).getClass()))) {
                                jSONObject2.put(string, jSONObject.get(string));
                            }
                        } catch (JSONException e2) {
                        }
                    }
                }
                n.a(jSONObject, jSONObject2);
                return 0;
            }
            return iF2;
        }
        return iF;
    }

    private JSONObject Wg() {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (f(this.akS, jSONObject) == 0) {
            int length = jSONObject.length();
            String string = null;
            JSONArray jSONArrayNames = jSONObject.names();
            for (int i = 0; i < length; i++) {
                try {
                    string = jSONArrayNames.getString(i);
                } catch (JSONException e) {
                }
                if (string != null && string.indexOf("_") != 0) {
                    try {
                        jSONObject2.put(string, jSONObject.get(string));
                    } catch (JSONException e2) {
                    }
                }
            }
            return jSONObject2;
        }
        return jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int oq() {
        if (!this.akR) {
            return -28;
        }
        JSONObject jSONObjectWg = Wg();
        a(com.gameloft.a.b.a.hr("stdDelCTDelProfile"));
        int iMv = mv();
        if (iMv == 0) {
            a(com.gameloft.a.b.a.j(this.Kv, null, jSONObjectWg.toString(), null, "set", "stdDelCTSetProfile"));
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
        a(com.gameloft.a.b.a.hr("stdDeleteProfile"));
        int iMv = mv();
        if (iMv == 0) {
            return oo();
        }
        return iMv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int f(JSONObject jSONObject, String str) {
        if (!this.akR) {
            return -28;
        }
        I(jSONObject);
        a(com.gameloft.a.b.a.j(this.Kv, null, jSONObject.toString(), str, "set", "stdUpdateProfile"));
        int iMv = mv();
        if (iMv == 0) {
            return oo();
        }
        return iMv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int g(JSONObject jSONObject, String str) {
        if (!this.akR) {
            return -28;
        }
        H(jSONObject);
        a(com.gameloft.a.b.a.j(this.Kv, null, jSONObject.toString(), str, "merge", "stdMergeProfile"));
        int iMv = mv();
        if (iMv == 0) {
            return oo();
        }
        return iMv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int h(JSONObject jSONObject, String str) {
        if (!this.akR) {
            return -28;
        }
        if (jSONObject.length() <= 0) {
            return -29;
        }
        String string = null;
        try {
            string = jSONObject.names().getString(0);
        } catch (JSONException e) {
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(string, jSONObject.get(string));
            H(jSONObject2);
            if (jSONObject2.get(string).toString().toString().compareTo(jSONObject.get(string).toString().toString()) == 0) {
                a(com.gameloft.a.b.a.j(this.Kv, string, jSONObject2.get(string).toString(), str, "set", "stdSetProfileField"));
                int iMv = mv();
                if (iMv == 0) {
                    return oo();
                }
                return iMv;
            }
        } catch (JSONException e2) {
        }
        return -31;
    }

    public int e(String str, JSONObject jSONObject) {
        if (!this.akR) {
            return -28;
        }
        JSONObject jSONObject2 = new JSONObject();
        int iF = f(this.akS, jSONObject2);
        if (iF == 0) {
            try {
                if (jSONObject2.get(str) != null) {
                    try {
                        jSONObject.put(str, jSONObject2.get(str));
                        return iF;
                    } catch (JSONException e) {
                        return iF;
                    }
                }
                return -27;
            } catch (JSONException e2) {
                return iF;
            }
        }
        return iF;
    }

    private String J(JSONObject jSONObject) {
        return n.e(n.a(jSONObject.toString().getBytes(), this.akU, true));
    }

    private int f(String str, JSONObject jSONObject) {
        try {
            try {
                n.a(jSONObject, new JSONObject(new String(n.b(n.bJ(str), this.akU, true))));
            } catch (JSONException e) {
            }
            return 0;
        } catch (IOException e2) {
            return -24;
        }
    }
}
