package com.gameloft.android2d.iap.a;

import com.b.d;
import com.facebook.appevents.AppEventsConstants;
import com.gameloft.android2d.iap.utils.v;
import java.util.Hashtable;

/* JADX INFO: compiled from: Billing.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a implements com.gameloft.android2d.iap.a {
    private Hashtable<String, String> blh = new Hashtable<>();
    private String bli = "";
    private String LI = "";
    private String currency = "";
    private String blj = "";
    private String LM = "";
    private String type = "";
    private int blk = 0;
    private String profileId = "";
    private String bll = "";
    private String url = "";
    private String blm = "";
    private String bln = "";
    private boolean blo = false;
    private String blp = "";
    private String blq = "";
    private String blr = "";
    private String bls = "";
    private String blt = "";
    private String blu = "";
    private String blv = "";
    private String blw = "";
    private String blx = "";
    private String bly = "";
    private String blz = "";
    private String blA = "";
    private Hashtable<String, String> blB = new Hashtable<>();
    private String blC = "";
    private String blD = "";
    private String blE = "";
    private String blF = "";
    private String blG = "";
    private String blH = "";
    private String blI = "";
    private String blJ = "";
    private String blK = "";
    private String blL = "";

    public abstract void eP(String str);

    public String Rw() {
        return this.bli;
    }

    public void eQ(String str) {
        this.bli = str;
    }

    public String getPrice() {
        return this.LI;
    }

    public void setPrice(String str) {
        this.LI = str;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String str) {
        this.currency = str;
    }

    public String Rx() {
        return this.LM;
    }

    public void eR(String str) {
        this.LM = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String Ry() {
        return this.blC;
    }

    public void eS(String str) {
        this.blC = str;
    }

    public String Rz() {
        return this.blD;
    }

    public void eT(String str) {
        this.blD = str;
    }

    public void eU(String str) {
        this.blo = false;
        if (str.trim().equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
            this.blo = true;
        }
    }

    public void eV(String str) {
        this.blF = str;
    }

    public String RA() {
        return this.blj;
    }

    public String RB() {
        return this.blj + Sd();
    }

    public void eW(String str) {
        this.blj = str;
    }

    public String getURL() {
        return this.url;
    }

    public void eX(String str) {
        this.url = str;
    }

    public String RC() {
        return this.blp;
    }

    public void eY(String str) {
        this.blp = str;
    }

    public void eZ(String str) {
        this.blr = str;
    }

    public void fa(String str) {
        this.bls = str;
    }

    public void fb(String str) {
        this.blq = str;
    }

    public void fc(String str) {
        this.blt = str;
    }

    public void fd(String str) {
        this.blw = str;
    }

    public void fe(String str) {
        this.blx = str;
    }

    public void ff(String str) {
        this.blA = str;
    }

    public String RD() {
        return this.blu;
    }

    public void fg(String str) {
        this.blu = str;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public void setProfileId(String str) {
        this.profileId = str;
    }

    public String RE() {
        return this.bll;
    }

    public void fh(String str) {
        this.bll = str;
    }

    public String RF() {
        return this.blm;
    }

    public void fi(String str) {
        this.blm = str;
    }

    public String RG() {
        return this.bln;
    }

    public void fj(String str) {
        this.bln = str;
    }

    public String toString() {
        return "ProfID: '" + this.profileId + "' BillingType: '" + this.bli + "' Price: '" + this.LI + "' Currency: '" + this.currency + "(' Formatted Price: '" + this.blj + ")' TNC: '" + this.LM + "' URL: '" + this.url + "' Proxy Server: '" + this.blm + "' Proxy Port: '" + this.bln + "' Alias: '" + this.blq + "' ServerNumber: '" + this.blp + "\n";
    }

    public void addText(String str, String str2) {
        if (str != null && str2 != null) {
            this.blB.put(str, str2);
        }
    }

    public String fk(String str) {
        if (this.blB.isEmpty() || !this.blB.containsKey(str)) {
            return null;
        }
        return this.blB.get(str);
    }

    public String RH() {
        return fk("purchase_confirm");
    }

    public String RI() {
        return fk("direct_iap_purchase_confirm") == null ? fk("purchase_confirm") : fk("direct_iap_purchase_confirm");
    }

    public String RJ() {
        return fk("purchase_confirm_above_buttons");
    }

    public String RK() {
        return fk("purchase_confirm_below_buttons");
    }

    public String RL() {
        return fk("hint_wp8");
    }

    public String RM() {
        return fk("send_sms_wp8");
    }

    public String RN() {
        return fk("phone_account_notification_wp8");
    }

    public String RO() {
        return fk("confirm_wp8");
    }

    public String RP() {
        return fk("back_wp8");
    }

    public String RQ() {
        return fk("ok");
    }

    public String RR() {
        return fk("later");
    }

    public String RS() {
        if (com.gameloft.android2d.iap.b.Qw()) {
            fl(v.mJ(d.cga));
        }
        return this.blK;
    }

    public void fl(String str) {
        this.blK = str;
    }

    public int RT() {
        return this.blk;
    }

    public void mC(int i) {
        this.blk = i;
    }

    public void RU() {
    }

    public boolean RV() {
        return false;
    }

    public boolean RW() {
        return false;
    }

    public String RX() {
        return this.blJ;
    }

    public void fm(String str) {
        this.blJ = str;
    }

    public void fn(String str) {
    }

    public String RY() {
        return null;
    }

    public void Rc() {
    }

    public void RZ() {
    }

    public void G(long j) {
    }

    public void Sa() {
    }

    public void Sb() {
    }

    public String Sc() {
        return fk("carrier_name");
    }

    public String Sd() {
        return fk("build_price_postfix") != null ? " " + fk("build_price_postfix") : "";
    }

    public void i(String str, String str2, String str3, String str4, String str5, String str6) {
    }
}
