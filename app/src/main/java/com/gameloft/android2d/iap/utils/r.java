package com.gameloft.android2d.iap.utils;

import com.facebook.share.internal.MessengerShareContentUtility;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.ao;

/* JADX INFO: compiled from: Item.java */
/* JADX INFO: loaded from: classes.dex */
public class r implements Comparable<r> {
    private String bnZ = "";
    private String type = "";
    private String id = "";
    private String boa = "";
    private String bob = "";
    private ao boc = null;
    private Hashtable<String, String> blh = new Hashtable<>();
    private Hashtable<String, com.gameloft.android2d.iap.a.a> bod = new Hashtable<>();
    private ArrayList<String> boe = new ArrayList<>();

    public void SK() {
        if (this.boe == null) {
            this.boe = new ArrayList<>();
        }
        this.boe.clear();
    }

    public void fC(String str) {
        this.boe.add(str);
    }

    public String[] SL() {
        if (this.boe.isEmpty()) {
            return null;
        }
        return (String[]) this.boe.toArray(new String[this.boe.size()]);
    }

    public void ae(String str, String str2) {
        if (str != null && str2 != null) {
            this.blh.put(str, str2);
        }
    }

    public String fD(String str) {
        if (this.blh.isEmpty()) {
            return null;
        }
        return this.blh.get(str);
    }

    public void a(com.gameloft.android2d.iap.a.a aVar) {
        if (aVar != null) {
            this.bod.put(aVar.Rw(), aVar);
        }
    }

    public com.gameloft.android2d.iap.a.a SM() {
        return fE(this.bnZ);
    }

    public com.gameloft.android2d.iap.a.a fE(String str) {
        if (this.bod.isEmpty()) {
            return null;
        }
        return this.bod.get(str);
    }

    public int SN() {
        if (this.bod.isEmpty()) {
            return 0;
        }
        return this.bod.size();
    }

    public boolean fF(String str) {
        return this.bod.containsKey(str);
    }

    public String getId() {
        return this.id;
    }

    public void fG(String str) {
        this.id = str;
    }

    public void fH(String str) {
        this.bnZ = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str.toLowerCase();
    }

    public String toString() {
        String str;
        String str2 = "******Item********\nId: '" + this.id + "' Type: '" + this.type + "' Type_pref: '" + this.bnZ + "'";
        Enumeration<String> enumerationKeys = this.blh.keys();
        while (true) {
            str = str2;
            if (!enumerationKeys.hasMoreElements()) {
                break;
            }
            String strNextElement = enumerationKeys.nextElement();
            str2 = str + "\nName: " + strNextElement + " '" + this.blh.get(strNextElement) + "'";
        }
        Enumeration<String> enumerationKeys2 = this.bod.keys();
        while (enumerationKeys2.hasMoreElements()) {
            str = (str + "\n-----Billing-------") + "\n" + this.bod.get(enumerationKeys2.nextElement()).toString();
        }
        return str;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public int compareTo(r rVar) {
        return Float.valueOf(Float.parseFloat(SM().getPrice())).compareTo(Float.valueOf(Float.parseFloat(rVar.SM().getPrice())));
    }

    public long SO() {
        String strFD = fD("amount");
        if (strFD != null) {
            return v.ga(strFD);
        }
        return 0L;
    }

    public long SP() {
        String strFD = fD("old_amount");
        if (strFD != null) {
            return v.ga(strFD);
        }
        return 0L;
    }

    public String SQ() {
        return fD("old_price");
    }

    public String SR() {
        return fD("old_formatted_price");
    }

    public String getName() {
        return fD("name");
    }

    public String SS() {
        return fD(MessengerShareContentUtility.MEDIA_IMAGE);
    }

    public String ST() {
        return fD("tracking_uid");
    }

    public String SU() {
        return fD("managed");
    }

    public String SV() {
        return this.bob;
    }

    public void fI(String str) {
        this.bob = str;
    }

    public ao SW() {
        return this.boc;
    }

    public void a(ao aoVar) {
        this.boc = aoVar;
    }

    public String SX() {
        String strFD = fD("bundle");
        return strFD != null ? strFD : "";
    }

    public String SY() {
        return SX();
    }

    public String SZ() {
        String strFD = fD("subscription_rewards");
        return strFD != null ? strFD : "";
    }
}
