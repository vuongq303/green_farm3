package com.gameloft.android2d.socialnetwork;

import java.io.InputStream;
import java.net.URL;

/* JADX INFO: compiled from: GraphUser.java */
/* JADX INFO: loaded from: classes.dex */
public class i {
    private String bpO = "";
    private String bnb = "";
    private String bpP = "";
    private String bpQ = "";
    private String bpR = "";
    private String bpS = "";
    private String bpT = "";
    private String bpU = "";
    private javax.microedition.lcdui.ao bpV = null;
    private boolean bpW = false;
    private int score = 0;
    private j[] bpX = null;
    private URL bpY = null;

    public String getName() {
        return this.bnb;
    }

    public String getFirstName() {
        return this.bpQ;
    }

    public String getLastName() {
        return this.bpR;
    }

    public String getMiddleName() {
        return this.bpS;
    }

    public String getId() {
        return this.bpT;
    }

    public javax.microedition.lcdui.ao UN() {
        javax.microedition.lcdui.ao aoVar = null;
        if (l.getType() != 1) {
            return null;
        }
        try {
            if (this.bpV == null && this.bpT != null && this.bpT.length() > 0) {
                InputStream inputStreamHh = s.hh(this.bpT);
                if (inputStreamHh != null) {
                    this.bpV = javax.microedition.lcdui.ao.a(inputStreamHh, s.bqy, s.bqz);
                    inputStreamHh.close();
                } else {
                    s.hg(this.bpT);
                }
            }
            aoVar = this.bpV;
            return aoVar;
        } catch (Exception e) {
            s.hg(this.bpT);
            return aoVar;
        }
    }

    public boolean UO() {
        return this.bpW;
    }

    public int getScore() {
        return this.score;
    }

    public j[] UP() {
        return this.bpX;
    }

    public String getCountry() {
        return this.bpU;
    }

    public URL UQ() {
        return this.bpY;
    }

    public void setName(String str) {
        this.bnb = str;
    }

    public void gU(String str) {
        this.bpQ = str;
    }

    public void gV(String str) {
        this.bpR = str;
    }

    public void gW(String str) {
        this.bpS = str;
    }

    public void fG(String str) {
        this.bpT = str;
    }

    public void bL(boolean z) {
        this.bpW = z;
    }

    public void gX(String str) {
        this.bpU = str;
    }

    public void mT(int i) {
        this.score = i;
    }

    public void a(j[] jVarArr) {
        this.bpX = jVarArr;
    }

    public void a(URL url) {
        this.bpY = url;
    }
}
