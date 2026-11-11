package com.gameloft.android2d.iap.utils;

import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: compiled from: ShopProfile.java */
/* JADX INFO: loaded from: classes.dex */
public class x {
    private int[] boE;
    private int[] boF;
    private Hashtable<String, Hashtable<Integer, r>> boz = new Hashtable<>();
    private Hashtable<String, r> boA = new Hashtable<>();
    private Hashtable<String, Integer> boB = new Hashtable<>();
    private String boC = "1,2,3,4,5,6,";
    private String boD = "1,2,3,4,5,6,";
    private String boG = "";
    private String boH = "";
    private String boI = "";
    private String boJ = "";
    private String boK = "";
    private String productId = "";
    private String boL = "";
    private String boM = "";
    private String boN = "";
    private String boO = "";
    private String boP = "";
    private String boQ = "";
    private String boR = "";
    private int boS = 0;
    private Hashtable<String, String> blB = new Hashtable<>();
    private String boT = "";
    private String LQ = "";
    private String boU = "";
    private int blk = 0;
    private String boV = "";
    private String boW = "";
    private String name = "";
    private boolean boX = false;

    public Hashtable<Integer, r> gm(String str) {
        if (this.boz.containsKey(str)) {
            return this.boz.get(str);
        }
        return null;
    }

    public boolean TU() {
        return this.boz.isEmpty();
    }

    public Hashtable<String, Hashtable<Integer, r>> TV() {
        return this.boz;
    }

    public r gn(String str) {
        if (str != null) {
            Enumeration<String> enumerationKeys = this.boA.keys();
            while (enumerationKeys.hasMoreElements()) {
                r rVar = this.boA.get(enumerationKeys.nextElement());
                if (str.equals(rVar.SM().Ry())) {
                    return rVar;
                }
            }
        }
        return null;
    }

    public void e(r rVar) {
        if (rVar != null) {
            try {
                String type = rVar.getType();
                String strRE = (type.equalsIgnoreCase(v.mJ(com.b.d.cgy)) || type.equalsIgnoreCase(v.mJ(com.b.d.cgB))) ? "" : rVar.SM().RE();
                if (this.boz.containsKey(type)) {
                    if (strRE != "") {
                        this.boB.put(type.toLowerCase() + "_" + this.boz.get(type).size(), Integer.valueOf(Integer.parseInt(strRE)));
                        this.boz.get(type).put(Integer.valueOf(Integer.parseInt(strRE)), rVar);
                    } else {
                        int size = this.boz.get(type).size();
                        this.boB.put(type.toLowerCase() + "_" + size, Integer.valueOf(size));
                        Float fValueOf = Float.valueOf(rVar.SM().getPrice());
                        int i = 0;
                        int i2 = 0;
                        while (i < size && Float.valueOf(this.boz.get(type).get(Integer.valueOf(i)).SM().getPrice()).floatValue() <= fValueOf.floatValue()) {
                            i++;
                            i2++;
                        }
                        for (int i3 = size; i3 > i2; i3--) {
                            this.boz.get(type).put(Integer.valueOf(i3), this.boz.get(type).get(Integer.valueOf(i3 - 1)));
                            this.boz.get(type).get(Integer.valueOf(i3 - 1)).SM().fh(String.valueOf(i3));
                        }
                        rVar.SM().fh(String.valueOf(i2));
                        this.boz.get(type).put(Integer.valueOf(i2), rVar);
                    }
                } else {
                    Hashtable<Integer, r> hashtable = new Hashtable<>();
                    if (strRE != "") {
                        hashtable.put(Integer.valueOf(Integer.parseInt(strRE)), rVar);
                        this.boB.put(type.toLowerCase() + "_0", Integer.valueOf(Integer.parseInt(strRE)));
                    } else {
                        rVar.SM().fh(String.valueOf(0));
                        hashtable.put(0, rVar);
                        this.boB.put(type.toLowerCase() + "_0", 0);
                    }
                    this.boz.put(type, hashtable);
                }
                this.boA.put(rVar.getId(), rVar);
            } catch (Exception e) {
            }
        }
    }

    public void go(String str) {
        this.boG = str;
    }

    public void gp(String str) {
        this.boI = str;
    }

    public void gq(String str) {
        this.boH = str;
    }

    public void gr(String str) {
        this.boC = str;
    }

    public void gs(String str) {
        this.boD = str;
    }

    public void gt(String str) {
        this.boJ = str;
    }

    public void gu(String str) {
        this.boK = str;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public void gv(String str) {
        this.boL = str;
    }

    public void gw(String str) {
        this.boM = str;
    }

    public void gx(String str) {
        this.boN = str;
    }

    public void gy(String str) {
        this.boQ = str;
    }

    public void gz(String str) {
        this.boR = str;
    }

    public String TW() {
        return this.boO;
    }

    public void gA(String str) {
        this.boO = str;
    }

    public String TX() {
        return this.boP;
    }

    public void gB(String str) {
        this.boP = str;
    }

    public String toString() {
        String str = (((("****************ShopProfile*****************\nCountry Id: '" + this.boG + "' [" + this.boH + "]' [" + this.boI + "]") + "Operator Id: '" + this.boJ + "' [" + this.boK + "]") + "Platform Id: '" + this.boM + "' [" + this.boN + "]") + "Product Id: '" + this.productId + "' [" + this.boL + "]") + "Lan Id: '" + this.boQ + "' [" + this.boR + "]";
        Enumeration<String> enumerationKeys = this.boz.keys();
        while (true) {
            String str2 = str;
            if (enumerationKeys.hasMoreElements()) {
                String strNextElement = enumerationKeys.nextElement();
                Enumeration<r> enumerationElements = this.boz.get(strNextElement).elements();
                str = str2 + "\n----------------------List Type: '" + strNextElement + "'---------------------------";
                while (enumerationElements.hasMoreElements()) {
                    str = str + "\n" + enumerationElements.nextElement().toString();
                }
            } else {
                return str2;
            }
        }
    }

    public void gC(String str) {
        this.boT = str;
    }

    public String TY() {
        return this.LQ;
    }

    public void gD(String str) {
        this.LQ = str;
    }

    public String TZ() {
        return this.boU;
    }

    public void gE(String str) {
        this.boU = str;
    }

    public int mK(int i) {
        if (this.boE != null) {
            return this.boE[i];
        }
        return -1;
    }

    public int mL(int i) {
        if (this.boF != null) {
            return this.boF[i];
        }
        return -1;
    }

    public int gF(String str) {
        if (gG(str)) {
            return this.boB.get(str.toLowerCase() + "_0").intValue();
        }
        return -1;
    }

    public int mM(int i) {
        if (gG(v.mJ(com.b.d.cgy))) {
            return this.boB.get(v.mJ(com.b.d.cgy).toLowerCase() + "_" + i).intValue();
        }
        return -1;
    }

    public int mN(int i) {
        if (gG(v.mJ(com.b.d.cgB))) {
            return this.boB.get(v.mJ(com.b.d.cgB).toLowerCase() + "_" + i).intValue();
        }
        return -1;
    }

    public boolean gG(String str) {
        return this.boz.containsKey(str.toLowerCase());
    }

    public void aj(String str, String str2) {
        int i = 0;
        try {
            Uc();
            int size = this.boz.containsKey(str) ? this.boz.get(str).size() : -1;
            if (size > 0) {
                this.boE = new int[size];
                String str3 = "";
                int i2 = 0;
                while (i2 < size) {
                    String str4 = str3 + this.boB.get(str + "_" + i2);
                    this.boB.remove(str + "_" + i2);
                    i2++;
                    str3 = str4;
                }
                int i3 = 0;
                for (int i4 = 0; i4 < this.boC.length() - 1; i4 += 2) {
                    int i5 = Integer.parseInt(this.boC.substring(i4, i4 + 1));
                    if (str3.indexOf("" + i5) != -1) {
                        this.boE[i3] = i5;
                        i3++;
                    }
                }
            }
            int size2 = this.boz.containsKey(str2) ? this.boz.get(str2).size() : -1;
            if (size2 > 0) {
                this.boF = new int[size2];
                String str5 = "";
                int i6 = 0;
                while (i6 < size2) {
                    String str6 = str5 + this.boB.get(str2 + "_" + i6);
                    this.boB.remove(str2 + "_" + i6);
                    i6++;
                    str5 = str6;
                }
                int i7 = 0;
                while (true) {
                    int i8 = i;
                    if (i8 < this.boD.length() - 1) {
                        int i9 = Integer.parseInt(this.boD.substring(i8, i8 + 1));
                        if (str5.indexOf("" + i9) != -1) {
                            this.boF[i7] = i9;
                            i7++;
                        }
                        i = i8 + 2;
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void Ua() {
        this.boC = "1,2,3,4,5,6,";
        this.boD = "0,1,2,3,4,5,";
    }

    public void Ub() {
        u.af(v.mJ(com.b.d.cgw) + d.SB(), this.boC + ";" + this.boD);
    }

    public void Uc() {
        this.boC = "1,2,3,4,5,6,";
        this.boD = "0,1,2,3,4,5,";
    }

    public r F(String str, int i) {
        String lowerCase = str.toLowerCase();
        int iML = -1;
        if (lowerCase.equalsIgnoreCase(v.mJ(com.b.d.cgz))) {
            iML = mK(i);
        } else if (lowerCase.equalsIgnoreCase(v.mJ(com.b.d.cgA))) {
            iML = mL(i);
        }
        Hashtable<Integer, r> hashtableGm = gm(lowerCase);
        if (hashtableGm != null) {
            return hashtableGm.get(Integer.valueOf(iML));
        }
        return null;
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

    public String Ud() {
        return fk("tnc_title");
    }

    public String Ue() {
        return fk("legal");
    }

    public int RT() {
        return this.blk;
    }

    public void mC(int i) {
        this.blk = i;
    }

    public String Uf() {
        return this.boV;
    }

    public void gH(String str) {
        this.boV = str;
    }

    public String Ug() {
        return fk("direct_iap_tnc_title");
    }

    public void gI(String str) {
        this.boW = str;
    }
}
