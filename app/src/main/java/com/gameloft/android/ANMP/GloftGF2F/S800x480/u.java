package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: FederationResponse.java */
/* JADX INFO: loaded from: classes.dex */
public class u {
    private int Qe;
    private String Qf;
    private byte[] Qg;
    private int Qh;
    private String Qi;
    private Exception exception;
    private String tag;

    public u(int i, String str, byte[] bArr, String str2, int i2, String str3, Exception exc) {
        this.Qe = i;
        this.Qf = str;
        this.Qg = bArr;
        this.tag = str2;
        this.Qh = i2;
        this.Qi = str3;
        this.exception = exc;
    }

    public int jw() {
        return this.Qe;
    }

    public Exception jx() {
        return this.exception;
    }

    public int jy() {
        return this.Qh;
    }

    public String jz() {
        return this.Qi;
    }

    public String jA() {
        return this.tag;
    }

    public String jB() {
        if (this.Qf != null) {
            return this.Qf;
        }
        if (this.Qg != null) {
            this.Qf = new String(this.Qg);
            return this.Qf;
        }
        return null;
    }
}
