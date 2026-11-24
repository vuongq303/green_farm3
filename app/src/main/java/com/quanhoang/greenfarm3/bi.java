package com.quanhoang.greenfarm3;

import android.os.Build;
import com.facebook.appevents.AppEventsConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ServerConfig.java */
/* JADX INFO: loaded from: classes.dex */
public class bi {
    private static boolean bcr = true;
    private static boolean bcs = false;
    private static String bct;
    private String Bh;
    m Bi = null;
    private int Bj = 0;
    private String bco;
    private String bcp;
    private String bcq;

    public bi(String str, String str2) {
        this.Bh = str;
        this.bcq = str2;
    }

    protected void c(String str, String str2) {
        if (this.Bi == null) {
            this.Bi = new m();
        }
        this.Bi.c(str, str2);
    }

    public void MW() {
        if (bcs && bct != null && bct != "") {
            c(bct, "");
        } else {
            c("https://eve.gameloft.com/config/", this.Bh + "/datacenters/" + this.bcq + "/urls");
        }
    }

    public void MX() {
        if (!this.Bi.gj()) {
            if (this.Bi.BM != null) {
                if (this.Bi.BN == 302) {
                    bct = this.Bi.BM;
                    bcs = true;
                    o.u("REDIRECT TO URL: " + bct);
                    this.Bj = 0;
                } else {
                    try {
                        JSONObject jSONObject = new JSONObject(this.Bi.BM);
                        this.bco = jSONObject.getString("etsv2");
                        if (Build.VERSION.SDK_INT < 16 && this.bco.startsWith("https")) {
                            this.bco = this.bco.replaceFirst("https", "http");
                        }
                        this.bcp = jSONObject.getString("gdid");
                        if (this.bco != null || this.bcp != null) {
                            this.Bj = 0;
                        }
                    } catch (JSONException e) {
                        o.t(e.toString());
                    }
                }
            } else if (this.Bi.BM == null) {
                o.t("RESPONSE NULL (https://eve.gameloft.com/config/" + this.Bh + ") - " + this.Bi.BN);
            }
            bcr = this.Bi.BN != 403;
        }
    }

    public boolean MY() {
        switch (this.Bj) {
            case 1:
                MX();
                break;
        }
        return this.bco != null;
    }

    public String ga() {
        return this.bcp != null ? this.bcp : AppEventsConstants.EVENT_PARAM_VALUE_NO;
    }

    public boolean MZ() {
        return (this.bcq == null || this.bcq.isEmpty()) ? false : true;
    }

    public String Na() {
        if (this.bcq == null || this.bcq.isEmpty()) {
            o.t("Invalid dataCenter.");
            return AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
        if (this.bco != null) {
            return this.bco;
        }
        this.Bj = 1;
        MW();
        return AppEventsConstants.EVENT_PARAM_VALUE_NO;
    }

    public static boolean Nb() {
        return bcr;
    }
}
