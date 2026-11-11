package com.gameloft.android.wrapper;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class ax implements com.gameloft.a.b.d {
    ax() {
    }

    @Override // com.gameloft.a.b.d
    public void a(int i, String str, int i2, String str2, Exception exc, String str3) {
        String string;
        String string2 = null;
        switch (i) {
            case 0:
                String str4 = "RequestCompleted: eveListDataCenters: response: " + str;
                try {
                    JSONObject jSONObject = new JSONArray(str).getJSONObject(0);
                    string = jSONObject.getString("name");
                    try {
                        string2 = jSONObject.getString("_datacenter_id");
                        break;
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                    string = null;
                }
                if (string != null && !string.equals("") && string2 != null && !string2.equals("")) {
                    com.gameloft.a.b.a.aJ(string);
                    com.gameloft.a.b.a.hp(string2);
                    com.gameloft.a.b.a.jn();
                    break;
                }
                break;
            case 1:
                String str5 = "RequestCompleted: eveInit: response: " + str;
                break;
            case 3:
                String str6 = "RequestCompleted: federationInit: response: " + str + " httpResponseCode " + i2 + " httpResponseMessage " + str2 + " httpException " + exc;
                break;
            case 4:
                String str7 = "RequestCompleted: irisGetAssetUrl: response: " + str + " httpResponseCode " + i2 + " httpResponseMessage " + str2 + " httpException " + exc;
                com.gameloft.a.b.a.aM(str);
                break;
            case 5:
                String str8 = "RequestCompleted: irisGetAsset: response: " + str + " httpResponseCode " + i2 + " httpResponseMessage " + str2 + " httpException " + exc;
                break;
        }
    }
}
