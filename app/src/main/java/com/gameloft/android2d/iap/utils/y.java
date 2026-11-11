package com.gameloft.android2d.iap.utils;

import com.facebook.appevents.AppEventsConstants;
import com.gameloft.glads.AndroidWebView;
import java.net.URLEncoder;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: XPlayer.java */
/* JADX INFO: loaded from: classes.dex */
public final class y {
    static d boY;
    public static boolean boZ = true;
    public static String bpa = null;
    public static long bpb = 0;
    public static String bpc = null;
    public static z[] bpg = new z[0];
    public long Cd;
    private int bjx;
    protected g bpf;
    private String url;
    private JSONObject bpd = null;
    private String bpe = null;
    private String bnE = null;

    public y(d dVar, boolean z) {
        a(dVar, z, "");
    }

    public y(d dVar, boolean z, String str) {
        a(dVar, z, str);
    }

    private void a(d dVar, boolean z, String str) {
        boY = dVar;
        Ui();
        this.bpf = new g(this, z, str);
        if (bpc == null) {
            bpc = "";
        } else {
            bpc = bpc.trim();
        }
    }

    public static d Uh() {
        return boY;
    }

    void Ui() {
        bpc = null;
        if (this.url != null && this.url.length() != 0) {
            this.url = this.url.trim();
        }
    }

    public void cancel() {
        this.Cd = 0L;
        this.bpf.cancel();
    }

    public static b SF() {
        return boY.SF();
    }

    public void mO(int i) {
        this.bjx = i;
    }

    public int Uj() {
        return this.bjx;
    }

    public static String encodeString(String str) {
        try {
            return URLEncoder.encode(str, AndroidWebView.UTF_8);
        } catch (Exception e) {
            return str;
        }
    }

    public void gJ(String str) {
        String str2;
        d.SC();
        d.SA();
        this.bpf.cancel();
        StringBuilder sbAppend = new StringBuilder().append("").append(v.mJ(com.b.d.chh)).append("=").append(encodeString(com.gameloft.android2d.iap.b.Qt())).append("&").append(v.mJ(com.b.d.chp)).append("=").append(encodeString(boY.getNetworkCountryIso())).append("&").append(v.mJ(com.b.d.chq)).append("=").append(encodeString(boY.getNetworkOperator())).append("&").append(v.mJ(com.b.d.chr)).append("=").append(encodeString(boY.getNetworkOperatorName())).append("&").append(v.mJ(com.b.d.chw)).append("=").append(encodeString(boY.getSimCountryIso())).append("&").append(v.mJ(com.b.d.chx)).append("=").append(encodeString(boY.getSimOperator())).append("&").append(v.mJ(com.b.d.chy)).append("=").append(encodeString(boY.getSimOperatorName())).append("&").append(v.mJ(com.b.d.chn)).append("=");
        d dVar = boY;
        String string = sbAppend.append(encodeString(d.Pb())).append("&").append(v.mJ(com.b.d.chs)).append("=").append(boY.SE()).append("&").append(v.mJ(com.b.d.chc)).append("=").append(encodeString(boY.getDevice())).append("&").append(v.mJ(com.b.d.chd)).append("=").append(encodeString(boY.SD())).append("&").append(v.mJ(com.b.d.chI)).append("=1&").append(v.mJ(com.b.d.chj)).append("=").append(com.gameloft.android2d.iap.b.Qu()).append("&").append(v.mJ(com.b.d.chm)).append("=").append(encodeString(com.gameloft.android2d.iap.b.getLanguage().toLowerCase(Locale.US))).append("&").append(v.mJ(com.b.d.cho)).append("=").append(encodeString(com.gameloft.android2d.iap.b.getLocale().toLowerCase(Locale.US))).append("&").append(v.mJ(com.b.d.chg)).append("=").append(encodeString(com.gameloft.android2d.iap.b.Oe())).append("&").append(v.mJ(com.b.d.chb)).append("=").append(com.gameloft.android2d.iap.b.Ro()).append("&").append(v.mJ(com.b.d.chi)).append("=").append(com.gameloft.android2d.iap.b.Rp()).toString();
        if (com.gameloft.android2d.iap.b.bjM.equals(v.mJ(com.b.d.cgs))) {
            str2 = string + "&" + v.mJ(com.b.d.chA) + "=" + (com.gameloft.android2d.iap.b.Qv() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "&" + v.mJ(com.b.d.chJ) + "=" + (com.gameloft.android2d.iap.b.bjY ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "&" + v.mJ(com.b.d.chE) + "=" + (com.gameloft.android2d.iap.b.bjZ ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "&" + v.mJ(com.b.d.chz) + "=" + (com.gameloft.android2d.iap.b.bka ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "&" + v.mJ(com.b.d.chD) + "=" + (com.gameloft.android2d.iap.b.Qz() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + (com.gameloft.android2d.iap.b.QA() ? "&" + v.mJ(com.b.d.chF) + "=0" : "") + (com.gameloft.android2d.iap.b.QB() ? "&" + v.mJ(com.b.d.chC) + "=0" : "") + "&" + v.mJ(com.b.d.chH) + "=" + (com.gameloft.android2d.iap.b.Qx() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "&" + v.mJ(com.b.d.chG) + "=" + (com.gameloft.android2d.iap.b.Qy() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "&" + v.mJ(com.b.d.chB) + "=" + (!com.gameloft.android2d.iap.b.bjO ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO) + "&" + v.mJ(com.b.d.chu) + "=1&" + v.mJ(com.b.d.cht) + "=1&v=1.2";
        } else {
            str2 = string + "&show_2d_tier=1&v=1.1";
        }
        this.bjx = -100;
        this.Cd = System.currentTimeMillis();
        this.bpf.ad(str, str2);
    }

    public boolean Uk() {
        if (this.bpf.isInProgress()) {
            if (System.currentTimeMillis() - this.Cd <= 10000) {
                return false;
            }
            cancel();
            this.bjx = -2;
            return true;
        }
        if (this.bpf.BT) {
            return true;
        }
        if (this.bpf.BM != null && this.bpf.BM != "") {
            this.bjx = 0;
            if (!this.bpf.BM.contains("error")) {
                return true;
            }
            this.bjx = 40;
            return true;
        }
        this.bjx = 40;
        return true;
    }

    public void gK(String str) {
        this.bpf.cancel();
        this.bjx = -100;
        this.Cd = System.currentTimeMillis();
        this.bpf.ad(str, "");
    }

    public boolean Ul() {
        if (this.bpf.isInProgress()) {
            return false;
        }
        if (this.bpf.BT) {
            return true;
        }
        if (this.bpf.BM != null && this.bpf.BM != "") {
            try {
                JSONArray jSONArrayOptJSONArray = new JSONObject(this.bpf.BM).optJSONArray(com.gameloft.android2d.iap.b.eL(v.mJ(com.b.d.cgJ)));
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    com.gameloft.android2d.iap.b.bkK.add(jSONArrayOptJSONArray.getString(i));
                }
            } catch (JSONException e) {
            }
        }
        return true;
    }

    public void ak(String str, String str2) {
        this.bpf.cancel();
        String str3 = "b=contentpurchase" + str2;
        if (!str.equals(v.mJ(com.b.d.ciH))) {
            str2 = str3;
        }
        this.bjx = -100;
        this.Cd = System.currentTimeMillis();
        this.bpf.ad(str, str2);
    }

    public boolean Um() {
        if (this.bpf.isInProgress()) {
            return false;
        }
        if (this.bpf.BT) {
            return true;
        }
        if (this.bpf.BM != null && this.bpf.BM != "") {
            String strG = G(this.bpf.BM, 0);
            try {
                if (strG.equals("FAILURE")) {
                    this.bjx = -1000;
                    return true;
                }
                if (strG.equals("SUCCESS")) {
                    this.bjx = 0;
                    return true;
                }
            } catch (NumberFormatException e) {
                this.bjx = 40;
                String strG2 = G(this.bpf.BM, 1);
                if (strG2.indexOf("PB") != -1) {
                    try {
                        this.bjx = Integer.parseInt(strG2.substring(2, strG2.length()));
                    } catch (NumberFormatException e2) {
                    }
                }
                return true;
            }
        }
        this.bjx = 40;
        return true;
    }

    public void al(String str, String str2) {
        this.bpf.cancel();
        this.bjx = -100;
        this.Cd = System.currentTimeMillis();
        this.bpf.ad(str, "b=validatelimits" + str2);
    }

    public boolean Un() {
        if (this.bpf.isInProgress()) {
            return false;
        }
        if (this.bpf.BT) {
            return true;
        }
        if (this.bpf.BM != null && this.bpf.BM != "") {
            this.bjx = 0;
            return true;
        }
        this.bjx = 40;
        return true;
    }

    public String Qs() {
        return (this.bpf.BM == null || this.bpf.BM == "") ? AppEventsConstants.EVENT_PARAM_VALUE_NO : G(this.bpf.BM, 2);
    }

    public String getTransactionId() {
        return (this.bpf.BM == null || this.bpf.BM == "") ? AppEventsConstants.EVENT_PARAM_VALUE_NO : G(this.bpf.BM, 3);
    }

    public static String G(String str, int i) {
        return b(str, i, '|');
    }

    private static String b(String str, int i, char c) {
        int i2 = 0;
        int iIndexOf = str.indexOf(c, 1);
        int i3 = i;
        while (i3 > 0) {
            if (i2 == -1) {
                return null;
            }
            i3--;
            i2 = iIndexOf;
            iIndexOf = str.indexOf(c, iIndexOf + 1);
        }
        if (i2 == -1) {
            return null;
        }
        if (iIndexOf == -1) {
            iIndexOf = str.length();
        }
        if (i > 0) {
            i2++;
        }
        if (i2 == iIndexOf) {
            return "";
        }
        if (i2 > iIndexOf) {
            return null;
        }
        try {
            char[] cArr = new char[iIndexOf - i2];
            str.getChars(i2, iIndexOf, cArr, 0);
            return new String(cArr);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public g Uo() {
        return this.bpf;
    }

    public void gL(String str) {
        try {
            this.bpd = null;
            if (str != null && !str.equals("")) {
                this.bpd = new JSONObject(str);
            }
        } catch (Exception e) {
        }
    }

    public String gM(String str) {
        return this.bpd != null ? this.bpd.optString(str) : "";
    }

    public void gN(String str) {
        this.bpe = str;
    }

    public String Up() {
        return this.bpe;
    }

    public void gO(String str) {
        this.bnE = str;
    }

    public String Uq() {
        return this.bnE;
    }

    public String Ur() {
        return this.bpf.BM;
    }
}
