package com.gameloft.android2d.socialnetwork;

import java.util.Hashtable;

/* JADX INFO: compiled from: Retry.java */
/* JADX INFO: loaded from: classes.dex */
public class k {
    Hashtable<String, Integer> bpZ = new Hashtable<>();
    Hashtable<String, Integer> bqa = new Hashtable<>();
    final int bqb = 10;
    final int bqc = 3;

    public boolean gY(String str) {
        if (!this.bpZ.containsKey(str)) {
            if (this.bqa.containsKey(str)) {
                int iIntValue = this.bqa.get(str).intValue() - 1;
                if (iIntValue == 0) {
                    gZ(str);
                    return true;
                }
                this.bqa.put(str, Integer.valueOf(iIntValue));
                return false;
            }
            this.bpZ.put(str, 10);
        }
        int iIntValue2 = this.bpZ.get(str).intValue() - 1;
        if (iIntValue2 == 0) {
            remove(str);
            return false;
        }
        this.bpZ.put(str, Integer.valueOf(iIntValue2));
        return true;
    }

    public void remove(String str) {
        this.bpZ.remove(str);
        this.bqa.put(str, 3);
    }

    private void gZ(String str) {
        this.bqa.remove(str);
    }
}
