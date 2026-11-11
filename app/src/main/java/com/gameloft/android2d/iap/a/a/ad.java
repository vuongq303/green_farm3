package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: IabResult.java */
/* JADX INFO: loaded from: classes.dex */
public class ad {
    String bmA;
    int bmz;

    public ad(int i, String str) {
        this.bmz = i;
        if (str == null || str.trim().length() == 0) {
            this.bmA = r.mD(i);
        } else {
            this.bmA = str + " (response: " + r.mD(i) + ")";
        }
    }

    public int So() {
        return this.bmz;
    }

    public String getMessage() {
        return this.bmA;
    }

    public boolean Sp() {
        return this.bmz == 0;
    }

    public boolean Sq() {
        return !Sp();
    }

    public String toString() {
        return "IabResult: " + getMessage();
    }
}
