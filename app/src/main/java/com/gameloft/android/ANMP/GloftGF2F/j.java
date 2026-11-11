package com.gameloft.android.ANMP.GloftGF2F;

import com.facebook.appevents.codeless.internal.Constants;
import com.gameloft.glads.AndroidWebView;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: GlobalDeviceIDManager.java */
/* JADX INFO: loaded from: classes.dex */
public class j {
    static boolean AX = false;
    static boolean AY = false;
    static int AZ = 15;
    private bx AG;
    String Ba;
    int Bg;
    private String Bh;
    private int Bj;
    private int Bk;
    private long Bt;
    m Bi = null;
    private String Bl = "";
    private String Bm = "";
    private String Bn = "";
    private String Bo = "";
    private String Bp = "";
    private String Bq = "";
    private String Br = "";
    private String Bs = "";
    boolean Bd = false;
    boolean Bc = false;
    boolean Bb = false;
    boolean Be = false;
    private boolean Bf = true;

    protected void c(String str, String str2) {
        if (this.Bi == null) {
            this.Bi = new m();
        }
        this.Bj = 1;
        this.Bi.c(str, str2);
    }

    public int a(bx bxVar, String str, String str2) {
        this.Ba = str2;
        this.AG = bxVar;
        if (AX) {
            return 0;
        }
        if (AY) {
            return -23;
        }
        AY = true;
        this.Bh = str;
        int iFX = fX();
        gb();
        AX = true;
        return iFX;
    }

    private void fW() {
        if (!this.Be) {
            this.Bl = Constants.PLATFORM;
            this.Bm = this.AG.Ni();
            this.Bn = "";
            this.Bo = "";
            this.Bp = this.AG.GetHDIDFV();
            this.Bq = this.AG.Nr();
            this.Br = this.AG.Nz();
            if (this.Bn == null) {
                this.Bn = "";
            }
            if (this.Bp == null) {
                this.Bp = "";
            }
            if (this.Bq == null) {
                this.Bq = "";
            }
            if (this.Bo == null) {
                this.Bo = "";
            }
            if (this.Br == null) {
                this.Br = "";
            }
            this.Be = true;
        }
    }

    private int fX() {
        this.Bi = new m();
        if (this.Bi != null) {
            this.Bd = true;
            return 0;
        }
        this.Bd = false;
        AY = false;
        return -14;
    }

    private void fY() {
        if (this.Bd && !this.Bc && this.Bs.equals("")) {
            gb();
        }
    }

    public boolean fZ() {
        return AX;
    }

    public void h(long j) {
        if (this.Bb) {
            b(this.Bg, j);
        }
        switch (this.Bj) {
            case 1:
                j(j);
                break;
        }
    }

    public String ga() {
        return this.Bs;
    }

    private int gb() {
        fW();
        a(this.Bl, this.Bm, "", "", this.Bn, this.Bp, this.Bq, this.Bo, this.Br);
        return 0;
    }

    private void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.Bk = 8001;
        String str10 = this.Ba + "/assign_global_id/?";
        String str11 = ("cid=" + this.Bh) + "&source=JavaGlot_330";
        if (str != null && str != "") {
            str11 = str11 + "&device_type=" + str;
        }
        if (str2 != null && str2 != "") {
            str11 = str11 + "&device_version=" + str2;
        }
        if (str3 != null && str3 != "") {
            str11 = str11 + "&idfv=" + str3;
        }
        if (str4 != null && str4 != "") {
            str11 = str11 + "&idfa=" + str4;
        }
        if (str5 != null && str5 != "") {
            str11 = str11 + "&aid=" + str5;
        }
        if (str9 != null && str9 != "") {
            str11 = str11 + "&mac=" + str9;
        }
        if (str6 != null && str6 != "") {
            str11 = str11 + "&hdidfv=" + str6;
        }
        if (str7 != null && str7 != "") {
            str11 = str11 + "&imei=" + str7;
        }
        if (str8 != null && str8 != "") {
            str11 = str11 + "&sn=" + str8;
        }
        c(str10, str11);
    }

    private boolean i(long j) {
        return j - this.Bt < ((long) AZ);
    }

    private void gd() {
        try {
            if (!o.a("glot_gdid", this.AG.dU(this.Bs).getBytes(Charset.forName(AndroidWebView.UTF_8)))) {
                o.u("GlobalDeviceIDManager.SaveGlobalDeviceIds: cannot save 'glot_gdid' file!!!");
            }
        } catch (Exception e) {
            o.d(e);
        }
    }

    private boolean ge() {
        boolean z = false;
        try {
            byte[] bArrX = o.x("glot_gdid");
            if (bArrX != null) {
                String strDV = this.AG.dV(o.c(bArrX, 0));
                if (p(strDV)) {
                    this.Bs = strDV;
                    z = true;
                }
            } else {
                o.u("GlobalDeviceIDManager.LoadGlobalDeviceIds: data == null!!!");
            }
        } catch (Exception e) {
            o.d(e);
        }
        return z;
    }

    private void b(int i, long j) {
        if (i == 409 || (i == 417 && this.Bc)) {
            this.Bs = "";
            this.Bc = false;
        } else if (i >= 500 && i < 600) {
            if (i(j)) {
                return;
            }
        } else {
            this.Bb = false;
            return;
        }
        this.Bb = false;
        if (AZ < 300) {
            AZ += 5;
            fY();
        }
    }

    private void j(long j) {
        int i;
        String string;
        String str = null;
        if (this.Bi != null) {
            if (this.Bi.gj()) {
                i = 0;
            } else {
                i = this.Bi.BN;
                if (this.Bi.BM != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(this.Bi.BM);
                        string = jSONObject.has("gdid") ? jSONObject.getString("gdid") : null;
                        try {
                            this.Bj = 0;
                        } catch (JSONException e) {
                            e = e;
                            o.t(e.toString());
                        } catch (Exception e2) {
                            e = e2;
                            o.t(e.toString());
                        }
                    } catch (JSONException e3) {
                        e = e3;
                        string = null;
                    } catch (Exception e4) {
                        e = e4;
                        string = null;
                    }
                } else {
                    string = null;
                }
                this.Bi.gh();
                this.Bi = null;
                str = string;
            }
        } else {
            o.u("********* GlobalDeviceIDManager: CallbackFunct(): m_http==null");
            i = 0;
        }
        switch (this.Bk) {
            case 8001:
                if (str != null && p(str)) {
                    this.Bs = str;
                    o.u("Send GDID to GLAdsv2 = " + this.Bs);
                    this.Bc = true;
                    this.Bd = false;
                    gd();
                } else {
                    if (this.Bi == null && this.Bf) {
                        if (ge()) {
                            this.Bc = true;
                            this.Bd = false;
                            o.u("********* GlobalDeviceIDManager: CallbackFunct(): load from local success!");
                        } else {
                            this.Bf = false;
                        }
                    }
                    this.Bc = false;
                    this.Bg = i;
                    this.Bt = j;
                    this.Bb = true;
                }
                break;
        }
    }

    private boolean p(String str) {
        if (str == null) {
            o.u("********* GlobalDeviceIDManager: IsValidValue(): wrong gdid value, null!");
            return false;
        }
        int length = str.length();
        if (str.isEmpty() || length > 20) {
            o.u("********* GlobalDeviceIDManager: IsValidValue(): wrong gdid value, empty or too long! GDID: " + str);
            return false;
        }
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                o.u("********* GlobalDeviceIDManager: IsValidValue(): wrong gdid value, gdid contains non numerical character! GDID: " + str);
                return false;
            }
        }
        return true;
    }
}
